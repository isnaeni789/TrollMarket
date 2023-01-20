package com.trollmarket.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "Orders")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "BuyerId")
    private Long buyerId;

    @ManyToOne
    @JoinColumn(name = "BuyerId", insertable = false, updatable = false)
    private Buyer buyer;

    @Column(name = "ShipperId")
    private Long shipperId;

    @ManyToOne
    @JoinColumn(name = "ShipperId", insertable = false, updatable = false)
    private Shipper shipper;

    @Column(name = "ProductId")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "ProductId", insertable = false, updatable = false)
    private Product product;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "UnitPrice")
    private Double unitPrice;

    @Column(name = "PurchaseDate")
    private LocalDate purchaseDate;

    public Order(Long id, Long buyerId, Long shipperId, Long productId,
                 Integer quantity, Double unitPrice) {
        this.id = id;
        this.buyerId = buyerId;
        this.shipperId = shipperId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
