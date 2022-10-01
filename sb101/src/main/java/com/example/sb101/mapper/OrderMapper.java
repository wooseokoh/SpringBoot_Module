package com.example.sb101.mapper;

import com.example.sb101.domain.Order;
import com.example.sb101.web.dto.OrderDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @BeforeMapping
    default void validate(OrderDto orderDto) {
        if(orderDto.getQuantity()==0) {
            orderDto.setQuantity(1);
        }
    }

    @Mapping(source="orderAmount",target="amount")
    @Mapping(source="orderDate",target="date" ,dateFormat = "yyyy-MMM-dd")
    @Mapping(source="orderStatus",target="status",qualifiedByName = "checkOrderStatus")
    Order toEntity(OrderDto orderDTO);


    @Mapping(source="amount",target="orderAmount")
    @Mapping(source="date",target="orderDate" ,dateFormat = "yyyy-MMM-dd")
    @Mapping(source="status",target="orderStatus",qualifiedByName="checkOrderStatusInString")
    OrderDto toDTO(Order order);

    @AfterMapping
    default void calculateSum(Order order,@MappingTarget OrderDto orderDto) {
        float sum = 0;
        if(order.getQuantity()!=0 && order.getAmount() !=0) {
            sum = sum + (order.getQuantity()*order.getAmount());
            orderDto.setSum(sum);
        }

    }

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