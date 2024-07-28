package com.example.demo.Model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "Pedidos")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @NotNull
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "payments_date")
    private LocalDate paymentsDate;

    @NotNull
    @Column(name = "buyer_email")
    private String buyerEmail;

    @NotNull
    @Column(name = "buyer_name")
    private String buyerName;

    @NotNull
    @Column(name = "cpf")
    private String cpf;

    @Column(name = "buyer_phone_number")
    private String buyerPhoneNumber;

    @Column(name = "ship_service_level")
    private String shipServiceLevel;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "ship_address_1")
    private String shipAddress1;

    @Column(name = "ship_address_2")
    private String shipAddress2;

    @Column(name = "ship_address_3")
    private String shipAddress3;

    @Column(name = "ship_city")
    private String shipCity;

    @Column(name = "ship_state")
    private String shipState;

    @Column(name = "ship_postal_code")
    private String shipPostalCode;

    @Column(name = "ship_country")
    private String shipCountry;

    @Column(name = "ioss_number")
    private String iossNumber;

    // Constructors, getters, and setters
    // Constructors

    public Pedido() {
    }

    // Getters and setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getPaymentsDate() {
        return paymentsDate;
    }

    public void setPaymentsDate(LocalDate paymentsDate) {
        this.paymentsDate = paymentsDate;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBuyerPhoneNumber() {
        return buyerPhoneNumber;
    }

    public void setBuyerPhoneNumber(String buyerPhoneNumber) {
        this.buyerPhoneNumber = buyerPhoneNumber;
    }

    public String getShipServiceLevel() {
        return shipServiceLevel;
    }

    public void setShipServiceLevel(String shipServiceLevel) {
        this.shipServiceLevel = shipServiceLevel;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getShipAddress1() {
        return shipAddress1;
    }

    public void setShipAddress1(String shipAddress1) {
        this.shipAddress1 = shipAddress1;
    }

    public String getShipAddress2() {
        return shipAddress2;
    }

    public void setShipAddress2(String shipAddress2) {
        this.shipAddress2 = shipAddress2;
    }

    public String getShipAddress3() {
        return shipAddress3;
    }

    public void setShipAddress3(String shipAddress3) {
        this.shipAddress3 = shipAddress3;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipState() {
        return shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    public String getShipPostalCode() {
        return shipPostalCode;
    }

    public void setShipPostalCode(String shipPostalCode) {
        this.shipPostalCode = shipPostalCode;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public String getIossNumber() {
        return iossNumber;
    }

    public void setIossNumber(String iossNumber) {
        this.iossNumber = iossNumber;
    }
}
