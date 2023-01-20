package com.trollmarket.dto.merchandise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductMerchandiseInfoDTO {
    private Long id;
    private String name;
    private String categoryName;
    private String description;
    private Double price;
    private String discontinue;

    public ProductMerchandiseInfoDTO(Long id, String name, String categoryName,
                                     String description, Double price, Boolean discontinue) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
        this.description = description;
        this.price = price;
        this.discontinue = getStatus(discontinue);
    }

    private String getStatus(Boolean discontinue){
        return discontinue ? "Yes" : "No";
    }
}
