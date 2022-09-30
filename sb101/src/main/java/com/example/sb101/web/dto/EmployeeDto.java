package com.example.sb101.web.dto;

import lombok.Data;

@Data
public class EmployeeDto {

    private Long empId;
    private String empName;
    private String email;
    private float salary;
    private DepartmentDto departmentDTO;

}