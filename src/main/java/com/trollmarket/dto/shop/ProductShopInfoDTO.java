package com.trollmarket.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductShopInfoDTO {
    private Long id;
    private String name;
    private String categoryName;
    private String description;
    private Double price;
    private String sellerName;
}
