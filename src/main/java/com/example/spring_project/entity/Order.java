package com.example.spring_project.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;


@Entity
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Double orderAmount;

    @Column(nullable = false)
    private LocalDate orderDate;

    @Column(nullable = false)
    private String orderLabel;

    @Column(nullable = false)
    private Boolean orderProcessed = false;

    public Order(){
    }

    public Order(String orderLabel, double orderAmount, LocalDate orderDate){
        this.orderAmount = orderAmount;
        this.orderDate = orderDate;
        this.orderLabel = orderLabel;
        this.orderProcessed = false;
    }

    public void setOrderProcessed(Boolean orderProcessed) {
        this.orderProcessed = orderProcessed;
    }

    public void setOrderLabel(String orderLabel) {
        this.orderLabel = orderLabel;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getOrderLabel() {
        return orderLabel;
    }

    public Boolean getOrderProcessed() {
        return orderProcessed;
    }

    
}
