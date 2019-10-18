package me.jerry.example.r2dbcwithpool.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.pool.PoolingConnectionFactoryProvider;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Option;
import io.r2dbc.spi.ValidationDepth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.r2dbc.support.R2dbcExceptionTranslator;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;
import static java.time.Duration.ofSeconds;

@Configuration
@EnableTransactionManagement
public class R2dbcConfig {

    @Configuration
    @EnableR2dbcRepositories(basePackages = "me.jerry.example.r2dbcwithpool.user", databaseClientRef = "userDatabaseClient")
    class UserDbR2dbcConfig extends AbstractR2dbcConfiguration {

        @Autowired
        @Qualifier("userDbConnectionPool")
        private ConnectionPool userDbConnectionPool;

        @Bean(name = "userDbPoolSettings")
        @ConfigurationProperties(prefix = "datasource.user")
        public R2dbcPoolSettings userWriteDbPoolSettings() {
            return new R2dbcPoolSettings();
        }

        @Bean(name = "userDbConnectionPool")
        public ConnectionPool userDbConnectionFactory(@Qualifier("userDbPoolSettings") R2dbcPoolSettings settings) {
            return getNewConnectionPool(settings);
        }

        @Bean(name = "userDbTransactionManager")
        public ReactiveTransactionManager userDbTransactionManager(@Qualifier("userDbConnectionPool") ConnectionFactory connectionFactory) {
            return new R2dbcTransactionManager(connectionFactory);
        }

        @Override
        @Bean(name = "userDatabaseClient")
        public DatabaseClient databaseClient(ReactiveDataAccessStrategy dataAccessStrategy, R2dbcExceptionTranslator exceptionTranslator) {
            return super.databaseClient(dataAccessStrategy, exceptionTranslator);
        }

        @Override
        public ConnectionFactory connectionFactory() {
            return userDbConnectionPool;
        }

    }

    @Configuration
    @EnableR2dbcRepositories(basePackages = "me.jerry.example.r2dbcwithpool.product", databaseClientRef = "channelDatabaseClient")
    class ChannelDbR2dbcConfig extends AbstractR2dbcConfiguration {

        @Autowired
        @Qualifier("channelDbConnectionPool")
        private ConnectionPool channelDbConnectionPool;

        @Bean(name = "channelDbPoolSettings")
        @ConfigurationProperties(prefix = "datasource.channel")
        public R2dbcPoolSettings channelDbPoolSettings() {
            return new R2dbcPoolSettings();
        }

        @Bean(name = "channelDbConnectionPool")
        public ConnectionPool channelDbConnectionFactory(@Qualifier("channelDbPoolSettings") R2dbcPoolSettings settings) {
            return getNewConnectionPool(settings);
        }

        @Bean(name = "channelDbTransactionManager")
        public ReactiveTransactionManager channelDbTransactionManager(@Qualifier("channelDbConnectionPool") ConnectionFactory connectionFactory) {
            return new R2dbcTransactionManager(connectionFactory);
        }

        @Override
        @Bean(name = "channelDatabaseClient")
        public DatabaseClient databaseClient(ReactiveDataAccessStrategy dataAccessStrategy, R2dbcExceptionTranslator exceptionTranslator) {
            return super.databaseClient(dataAccessStrategy, exceptionTranslator);
        }

        @Override
        public ConnectionFactory connectionFactory() {
            return channelDbConnectionPool;
        }

    }

    // ============================= private methods =============================
    private ConnectionPool getNewConnectionPool(R2dbcPoolSettings settings) {
        ConnectionFactory connectionFactory = ConnectionFactories.get(builder()
            .option(DRIVER, "pool")
            .option(PROTOCOL, "mysql")
            .option(HOST, settings.getHost())
            .option(PORT, settings.getPort())
            .option(USER, settings.getUsername())
            .option(PASSWORD, settings.getPassword())
            .option(DATABASE, settings.getDatabaseName())
            .option(CONNECT_TIMEOUT, settings.getConnectionTimeout())
            .option(SSL, false)
            .option(Option.valueOf("zeroDate"), "use_null")
            .option(PoolingConnectionFactoryProvider.MAX_SIZE, settings.getMaxSize())
            .option(PoolingConnectionFactoryProvider.VALIDATION_QUERY, "select 1")
            .option(PoolingConnectionFactoryProvider.VALIDATION_DEPTH, ValidationDepth.LOCAL)
            .build()
        );

        ConnectionPoolConfiguration configuration = getNewConnectionPoolBuilder(connectionFactory, settings).build();
        return new ConnectionPool(configuration);
    }

    private ConnectionPoolConfiguration.Builder getNewConnectionPoolBuilder(ConnectionFactory connectionFactory, R2dbcPoolSettings settings) {
        // TODO 설정을 더 넣고싶으면 ConnectionPoolConfiguration.Builder builder 로 따로 변수 선언 후에 설정하도록 수정할 것
        return ConnectionPoolConfiguration.builder(connectionFactory)
            .name(settings.getPoolName())
            .initialSize(settings.getInitialSize())
            .maxSize(settings.getMaxSize())
            .maxIdleTime(settings.getMaxIdleTime())
            .maxLifeTime(settings.getMaxLifeTime())
            .maxAcquireTime(ofSeconds(10))
            .maxCreateConnectionTime(ofSeconds(5))
            .validationQuery("select 1")
            .validationDepth(ValidationDepth.LOCAL)
            .registerJmx(true);
    }

}
