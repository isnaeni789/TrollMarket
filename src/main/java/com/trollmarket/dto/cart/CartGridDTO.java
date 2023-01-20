package com.trollmarket.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CartGridDTO {
    private Long id;
    private Long sellerId;
    private String productName;
    private Integer quantity;
    private String shipment;
    private String seller;
    private Double unitPrice;
    private Double totalPrice;

    public CartGridDTO(Long id, Long sellerId, String productName, Integer quantity,
                       String shipment, String seller, Double productPrice,
                       Double shipmentPrice) {
        this.id = id;
        this.sellerId = sellerId;
        this.productName = productName;
        this.quantity = quantity;
        this.shipment = shipment;
        this.seller = seller;
        this.unitPrice = productPrice;
        this.totalPrice = getTotalPrice(productPrice, shipmentPrice, quantity);
    }

    private Double getTotalPrice(Double unitPrice, Double shipmentPrice, Integer quantity){
        return unitPrice * quantity + shipmentPrice;
    }
}
