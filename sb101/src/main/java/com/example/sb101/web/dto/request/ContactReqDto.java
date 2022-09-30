package com.example.sb101.web.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactReqDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
}