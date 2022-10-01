package com.example.sb101.web.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private Long customerId;
    private String customerName;
    private String countryName;
    private String registrationDate;

}