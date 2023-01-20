package com.trollmarket.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileTransactionHistory {
    private LocalDate purchaseDate;
    private String productName;
    private Integer quantity;
    private String shipment;
    private Double totalPrice;

    public ProfileTransactionHistory(LocalDate purchaseDate, String productName, Integer quantity,
                                     String shipment, Double unitPrice, Double shipmentPrice) {
        this.purchaseDate = purchaseDate;
        this.productName = productName;
        this.quantity = quantity;
        this.shipment = shipment;
        this.totalPrice = getTotalPrice(unitPrice, shipmentPrice, quantity);
    }

    private Double getTotalPrice(Double unitPrice, Double shipmentPrice, Integer quantity){
        return unitPrice * quantity + shipmentPrice;
    }
}
