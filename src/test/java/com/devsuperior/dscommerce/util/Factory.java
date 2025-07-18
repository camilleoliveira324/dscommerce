package com.devsuperior.dscommerce.util;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Category;
import com.devsuperior.dscommerce.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct() {
        var p = new Product(1L, "Phone", "Good phone", 800.0, "Good phone");
        p.getCategories().add(new Category(2L, "Electronics"));
        return p;
    }

    public static ProductDTO createProductDTO() {
        var p = new Product(1L, "Phone", "Good phone", 800.0, "Good phone"), 800.0, "Good phone","https://raw.githubusercontent.com/devsuperior");
        p.getCategories().add(new Category(2L, "Electronics"));
        return new ProductDTO(p);
    }

    }
}
