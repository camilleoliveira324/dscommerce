package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductMinDTO {

    private Long id;

    private String name;

    private Double price;
    private String imgUrl;

    public ProductMinDTO() {}

    public ProductMinDTO(Product p) {
        id = p.getId();
        name = p.getName();
        price = p.getPrice();
        imgUrl = p.getImgUrl();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
