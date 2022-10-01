package com.example.sb101.web.dto;

import lombok.Data;

@Data
public class OrderDto {

    private Long id;

    private String orderAmount;

    private String description;

    private String orderDate;


}