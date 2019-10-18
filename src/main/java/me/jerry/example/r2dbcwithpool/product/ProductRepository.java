package me.jerry.example.r2dbcwithpool.product;

import me.jerry.example.r2dbcwithpool.YesNoType;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

    @Query("SELECT * FROM product WHERE main_category_id = ? AND use_yn = ?")
    Flux<Product> findAllByMainCategoryIdAndUseYn(Integer mainCategoryId, YesNoType useYn);

}
