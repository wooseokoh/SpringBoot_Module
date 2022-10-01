package com.example.sb101.mapper;

import com.example.sb101.domain.Customer;
import com.example.sb101.web.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(source="customerId",target="id")
    @Mapping(source="customerName",target="name")
    @Mapping(source="countryName",target="country")
    @Mapping(source="registrationDate",target="registrationDate",dateFormat ="dd/MM/yyyy")
    Customer toEntity(CustomerDto customerDto);

    @Mapping(source="id",target="customerId")
    @Mapping(source="name",target="customerName")
    @Mapping(source="country",target="countryName")
    @Mapping(source="registrationDate",target="registrationDate",dateFormat ="dd/MM/yyyy")
    CustomerDto toDTO(Customer customer);

}