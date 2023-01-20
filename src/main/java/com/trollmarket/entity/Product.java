package com.trollmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "Products")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "SellerId")
    private Long sellerId;

    @ManyToOne
    @JoinColumn(name = "SellerId", insertable = false, updatable = false)
    private Seller seller;

    @Column(name = "CategoryId")
    private Long categoryId;

    @ManyToOne
    @JoinColumn(name = "CategoryId", insertable = false, updatable = false)
    private Category category;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Discontinue")
    private Boolean discontinue;

    public Product(Long id, String name, Long sellerId, Long categoryId,
                   String description, Double price, Boolean discontinue) {
        this.id = id;
        this.name = name;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.description = description;
        this.price = price;
        this.discontinue = discontinue;
    }
}
