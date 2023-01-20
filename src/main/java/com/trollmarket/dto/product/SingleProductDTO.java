package com.trollmarket.dto.product;

import lombok.*;


@Data @Builder
public class SingleProductDTO {
    private Long id;
    private String name;
    private Long sellerId;
    private Long categoryId;
    private String description;
    private Double price;
    private Boolean discontinue;
}
