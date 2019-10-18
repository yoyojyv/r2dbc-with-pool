package me.jerry.example.r2dbcwithpool.product;

import lombok.Data;
import me.jerry.example.r2dbcwithpool.YesNoType;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class Product {

    @Id
    private Integer id;
    private String name;
    private String productCategoryCode;
    private Integer supplierId;
    private String supplierProductId;
    private Integer mainCategoryId;
    private YesNoType useYn;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

}
