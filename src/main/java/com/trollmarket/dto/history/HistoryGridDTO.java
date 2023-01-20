package com.trollmarket.dto.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryGridDTO {
    private LocalDate purchaseDate;
    private String seller;
    private String buyer;
    private String productName;
    private Integer quantity;
    private String shipment;
    private Double totalPrice;

    public HistoryGridDTO(LocalDate purchaseDate, String seller, String buyer,
                          String productName, Integer quantity, String shipment,
                          Double unitPrice, Double shipmentPrice) {
        this.purchaseDate = purchaseDate;
        this.seller = seller;
        this.buyer = buyer;
        this.productName = productName;
        this.quantity = quantity;
        this.shipment = shipment;
        this.totalPrice = getTotalPrice(unitPrice, shipmentPrice, quantity);
    }

    private Double getTotalPrice(Double unitPrice, Double shipmentPrice, Integer quantity){
        return unitPrice * quantity + shipmentPrice;
    }
}
