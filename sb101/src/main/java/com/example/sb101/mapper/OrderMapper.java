package com.example.sb101.mapper;

import com.example.sb101.domain.Order;
import com.example.sb101.web.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source="orderAmount",target="amount")
    @Mapping(source="orderDate",target="date" ,dateFormat = "yyyy-MMM-dd")
    Order toEntity(OrderDto orderDTO);


    @Mapping(source="amount",target="orderAmount")
    @Mapping(source="date",target="orderDate" ,dateFormat = "yyyy-MMM-dd")
    OrderDto toDTO(Order order);


}