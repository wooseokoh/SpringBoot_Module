package com.example.sb101.service;

import java.util.Optional;

import com.example.sb101.domain.Employee;
import com.example.sb101.domain.EmployeeRepository;
import com.example.sb101.mapper.EmployeeDetailsMapper;
import com.example.sb101.mapper.EmployeeMapper;
import com.example.sb101.web.dto.EmployeeDetailsDto;
import com.example.sb101.web.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final EmployeeDetailsMapper employeeDetailsMapper;

    public Employee saveEmployee(EmployeeDto employeeDTO) {
        return employeeRepository.save(employeeMapper.toEntity(employeeDTO));
    }

    public EmployeeDto getEmployeeById(Long id) {

        Optional<Employee> emp = employeeRepository.findById(id);
        if(emp.isPresent()) {
            return employeeMapper.toDTO(emp.get(), emp.get().getDepartment());
        }
        return new EmployeeDto();
    }

    public EmployeeDetailsDto getEmployeeDetailsById(Long id) {
        return employeeRepository.findById(id).
                map(emp->{
                    return employeeDetailsMapper.toEmployeeDetailsDTO(emp, emp.getDepartment());
                }).orElse(new EmployeeDetailsDto());
    }
}