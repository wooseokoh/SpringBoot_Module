package com.example.sb101.mapper;

import com.example.sb101.domain.Order;
import com.example.sb101.web.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source="orderAmount",target="amount")
    @Mapping(source="orderDate",target="date" ,dateFormat = "yyyy-MMM-dd")
    @Mapping(source="orderStatus",target="status",qualifiedByName = "checkOrderStatus")
    Order toEntity(OrderDto orderDTO);


    @Mapping(source="amount",target="orderAmount")
    @Mapping(source="date",target="orderDate" ,dateFormat = "yyyy-MMM-dd")
    @Mapping(source="status",target="orderStatus",qualifiedByName="checkOrderStatusInString")
    OrderDto toDTO(Order order);

    @Named("checkOrderStatus")
    default boolean checkOrderStatus(String orderStatus) {
        boolean flag;
        if(orderStatus.equals("Delivered")) {
            flag = true;
        }else {
            flag = false;
        }
        return flag;
    }

    @Named("checkOrderStatusInString")
    default String checkOrderStatusInString(boolean status) {
        String orderStatus;
        if(status) {
            orderStatus = "Delivered";
        }else {
            orderStatus = "Pending";
        }
        return orderStatus;
    }
}