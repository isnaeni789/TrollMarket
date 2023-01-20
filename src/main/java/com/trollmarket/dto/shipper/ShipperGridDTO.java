package com.trollmarket.dto.shipper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipperGridDTO {
    private Long id;
    private String name;
    private Double price;
    private String service;

    public ShipperGridDTO(Long id, String name, Double price, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.service = getStatus(isActive);
    }

    private String getStatus(Boolean isActive){
        return isActive ? "Yes" : "No";
    }
}
