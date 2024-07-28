package com.example.demo.DTO;

import lombok.Data;

@Data
public class ProdutoDTO {

    private long id;

    private String sku;

    private String productName;

    private int quantity;

    private String currency;

    private double itemPrice;
}
