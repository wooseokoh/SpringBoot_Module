package com.example.sb101.web.dto;

import lombok.Data;

@Data
public class EmployeeDetailsDto {

    private Long empId;
    private String empName;
    private String email;
    private float salary;
    private String deptName;
}