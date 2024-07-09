package com.example.demo.Model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "Clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "cpf")
    private String cpf;

    @NotNull
    @Column(name = "buyer_email")
    private String buyerEmail;

    @NotNull
    @Column(name = "buyer_name")
    private String buyerName;

    @NotNull
    @Column(name = "buyer_phone_number")
    private String buyerPhoneNumber;
}
