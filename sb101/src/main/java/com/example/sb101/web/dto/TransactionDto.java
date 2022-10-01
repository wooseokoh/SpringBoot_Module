package com.example.sb101.web.dto;

import lombok.Data;

@Data
public class TransactionDto {
    private String transactionId;
    private PaymentType paymentType;
}