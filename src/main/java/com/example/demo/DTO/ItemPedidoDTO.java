package com.example.demo.DTO;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class ItemPedidoDTO {

    private long id;
    
    private PedidoDTO pedido;

    private ProdutoDTO produto;

    @NotNull
    private int quantityPurchased;
}
