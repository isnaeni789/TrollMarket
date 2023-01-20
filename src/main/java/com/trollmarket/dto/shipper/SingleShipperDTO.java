package com.trollmarket.dto.shipper;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class SingleShipperDTO {
    private Long id;
    private String name;
    private Double price;
    private Boolean isActive;
}
