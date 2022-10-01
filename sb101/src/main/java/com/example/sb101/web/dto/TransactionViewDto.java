package com.example.sb101.web.dto;

import lombok.Data;

@Data
public class TransactionViewDto {

    private String transactionId;
    private PaymentViewType paymentViewType;
}