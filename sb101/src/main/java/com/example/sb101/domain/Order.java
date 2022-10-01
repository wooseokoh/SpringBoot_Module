package com.example.sb101.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="order_details")
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="amount")
    private float amount;

    @Column(name="description")
    private String description;

    @Column(name="order_date")
    private LocalDate date;

    @Column(name="status")
    private boolean status;

    @Column(name="quantity")
    private int quantity;
}