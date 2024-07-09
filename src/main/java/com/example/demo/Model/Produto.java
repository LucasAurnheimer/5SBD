package com.example.demo.Model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "Produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "sku")
    private String sku;

    @NotNull
    @Column(name = "product_name")
    private String productName;

    @NotNull
    @Column(name = "quantity")
    private int quantity;

    @NotNull
    @Column(name = "currency")
    private String currency;

    @NotNull
    @Column(name = "item_price")
    private double itemPrice;
}
