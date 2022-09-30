package com.example.sb101.mapper;

import com.example.sb101.domain.Department;
import com.example.sb101.web.dto.DepartmentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toEntity(DepartmentDto departmentDTO);

    DepartmentDto toDTO(Department department);

}