package com.cloud.api.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAddresses {
    private String composite;
    private String product;
    private String recommend;
    private String review;
}
