package com.trollmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "Sellers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Address")
    private String address;

    @Column(name = "Balance")
    private Double balance;

    @Column(name = "Username")
    private String username;

    public Seller(String name, String address, Double balance, String username) {
        this.name = name;
        this.address = address;
        this.balance = balance;
        this.username = username;
    }
}
