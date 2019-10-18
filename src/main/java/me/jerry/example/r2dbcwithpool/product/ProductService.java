package me.jerry.example.r2dbcwithpool.product;

import lombok.RequiredArgsConstructor;
import me.jerry.example.r2dbcwithpool.YesNoType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true, transactionManager = "channelDbTransactionManager")
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    Flux<Product> getAllProductsByMainCategoryIdAndUseYn(Integer mainCategoryId, YesNoType useYn) {
        return productRepository.findAllByMainCategoryIdAndUseYn(mainCategoryId, useYn);
    }

    public Mono<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

}
