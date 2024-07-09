package com.example.demo.DTO;

import lombok.Data;

@Data
public class MovimentacaoEstoqueDTO {

    private long movimentoId;

    private String sku;

    private int quantidade;
}
