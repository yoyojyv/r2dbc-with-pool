package me.jerry.example.r2dbcwithpool.product;

import lombok.RequiredArgsConstructor;
import me.jerry.example.r2dbcwithpool.YesNoType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public Flux<Product> productsByCategoryAndUseYn(@RequestParam Integer mainCategoryId, @RequestParam YesNoType useYn) {
        return this.productService.getAllProductsByMainCategoryIdAndUseYn(mainCategoryId, useYn);
    }

    @GetMapping("/products/by-id/{id}")
    public Mono<Product> productById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

}
