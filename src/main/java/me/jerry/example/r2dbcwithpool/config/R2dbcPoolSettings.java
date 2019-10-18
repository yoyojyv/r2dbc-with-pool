package me.jerry.example.r2dbcwithpool.config;

import lombok.Data;

import java.time.Duration;

/**
 * see https://github.com/r2dbc/r2dbc-pool
 */
@Data
public class R2dbcPoolSettings {

    private String host;
    private int port;
    private String databaseName;
    private String username;
    private String password;
    private Duration connectionTimeout;
    private String poolName;
    private int initialSize;
    private int maxSize;
    private Duration maxIdleTime;
    private Duration maxCreateConnectionTime;
    private Duration maxAcquireTime;
    private Duration maxLifeTime;
}
