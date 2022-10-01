package com.example.sb101.service;

import com.example.sb101.domain.Customer;
import com.example.sb101.domain.CustomerRepository;
import com.example.sb101.mapper.CustomerMapper;
import com.example.sb101.web.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public Customer saveCustomer(CustomerDto customerDto) {
        return customerRepository.save(customerMapper.toEntity(customerDto));
    }

    public CustomerDto getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toDTO)
                .orElse(new CustomerDto());
    }

}