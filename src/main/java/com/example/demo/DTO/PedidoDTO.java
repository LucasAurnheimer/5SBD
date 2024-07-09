package com.example.demo.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class PedidoDTO {

    private long orderId;

    @NotNull
    private LocalDate purchaseDate;

    private LocalDate paymentsDate;

    @NotNull
    private String buyerEmail;

    @NotNull
    private String buyerName;

    @NotNull
    private String cpf;

    private String buyerPhoneNumber;

    private String shipServiceLevel;

    private String recipientName;

    private String shipAddress1;

    private String shipAddress2;

    private String shipAddress3;

    private String shipCity;

    private String shipState;

    private String shipPostalCode;

    private String shipCountry;

    private String iossNumber;

    private List<ItemPedidoDTO> itensPedido;
}
