package com.leporonitech.payments.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    UUID id;
    @Column(name = "product_name")
    String productName;
    @Column(name = "product_price")
    double productPrice;
    @Column(name = "payment_line")
    String paymentLine;
    @Column(name = "generation_date")
    LocalDate generationDate;
    @Column(name = "due_date")
    LocalDate dueDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getPaymentLine() {
        return paymentLine;
    }

    public void setPaymentLine(String paymentLine) {
        this.paymentLine = paymentLine;
    }

    public LocalDate getGenerationDate() {
        return generationDate;
    }

    public void setGenerationDate(LocalDate generationDate) {
        this.generationDate = generationDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
