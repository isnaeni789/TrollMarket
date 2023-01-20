package com.trollmarket.dto.merchandise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductMerchandiseGridDTO {
    private Long id;
    private Long sellerId;
    private String name;
    private String categoryName;
    private String discontinue;

    public ProductMerchandiseGridDTO(Long id, Long sellerId, String name, String categoryName,
                                     Boolean discontinue) {
        this.id = id;
        this.sellerId = sellerId;
        this.name = name;
        this.categoryName = categoryName;
        this.discontinue = getStatus(discontinue);
    }

    private String getStatus(Boolean discontinue){
        return discontinue ? "Yes" : "No";
    }
}
