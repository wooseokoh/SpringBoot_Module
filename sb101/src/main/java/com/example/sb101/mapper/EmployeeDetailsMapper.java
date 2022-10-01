package com.example.sb101.mapper;

import com.example.sb101.domain.Department;
import com.example.sb101.domain.Employee;
import com.example.sb101.web.dto.EmployeeDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeDetailsMapper {

    @Mapping(source = "employee.id", target = "empId")
    @Mapping(source = "employee.name", target = "empName")
    @Mapping(source = "department.deptName", target = "deptName")
    EmployeeDetailsDto toEmployeeDetailsDTO(Employee employee, Department department);

}