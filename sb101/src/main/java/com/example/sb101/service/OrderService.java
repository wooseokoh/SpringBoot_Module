package com.example.sb101.service;

import com.example.sb101.domain.Order;
import com.example.sb101.domain.OrderRepository;
import com.example.sb101.mapper.OrderMapper;
import com.example.sb101.web.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Order saveOrder(OrderDto orderDto) {
        return orderRepository.save(orderMapper.toEntity(orderDto));
    }

    public OrderDto getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElse(new OrderDto());
    }

}