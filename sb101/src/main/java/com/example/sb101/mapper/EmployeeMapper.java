package com.example.sb101.mapper;

import com.example.sb101.domain.Department;
import com.example.sb101.domain.Employee;
import com.example.sb101.web.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring",uses= {DepartmentMapper.class})
public interface EmployeeMapper {

    @Mapping(source = "empId", target = "id")
    @Mapping(source = "empName", target = "name")
//    @Mapping(source = "deptName", target = "department.deptName")
    @Mapping(source = "departmentDTO.deptName", target = "department.deptName")
    Employee toEntity(EmployeeDto employeeDTO);

    @Mapping(source = "employee.id", target = "empId")
    @Mapping(source = "employee.name", target = "empName")
//    @Mapping(source = "department.deptName", target = "deptName")
    @Mapping(source = "department.deptName",target = "departmentDTO.deptName")
    EmployeeDto toDTO(Employee employee, Department department);
}