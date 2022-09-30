package com.example.sb101.web.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String contactNo;
}