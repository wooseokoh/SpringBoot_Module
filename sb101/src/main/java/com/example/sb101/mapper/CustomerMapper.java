package com.example.sb101.mapper;

import com.example.sb101.domain.Customer;
import com.example.sb101.web.dto.CustomerDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring",imports= {LocalDate.class})
public interface CustomerMapper {

    @Mapping(source="customerId",target="id", ignore = true) // ignore 일치하지 않는 필드 무시
    @Mapping(source="customerName",target="name")
    @Mapping(source="countryName",target="country",defaultValue = "Korea")
    @Mapping(source="registrationDate",target="registrationDate",dateFormat ="dd/MM/yyyy",defaultExpression = "java(LocalDate.now())")
    @Mapping(target="type",constant="New") // constant 필드가 없지만 기본값을 필요로 할 경우
    Customer toEntity(CustomerDto customerDto);

//    @Mapping(source="id",target="customerId")
//    @Mapping(source="name",target="customerName")
//    @Mapping(source="country",target="countryName")
//    @Mapping(source="registrationDate",target="registrationDate",dateFormat ="dd/MM/yyyy")
    @InheritInverseConfiguration // 중첩된 bean 프로퍼티 현재 타겟에 매핑시키기
    @Mapping(source="id",target="customerId")
    CustomerDto toDTO(Customer customer);

}