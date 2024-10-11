package com.example.spring_project.service;

import org.springframework.stereotype.Service;

import com.example.spring_project.entity.Order;
import com.example.spring_project.repository.OrderRepository;

import java.util.List;
@Service
public class OrderService {
    private OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository){ this.orderRepository = orderRepository;}
    public List<Order> getOrders() { return orderRepository.findAll();}

    public Order getOrderById(long id){ return orderRepository.findById(id).orElse(null); }

    public void addOrder(Order order) {
        orderRepository.saveAndFlush(order);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setOrderLabel(updatedOrder.getOrderLabel());
            order.setOrderAmount(updatedOrder.getOrderAmount());
            order.setOrderDate(updatedOrder.getOrderDate());
            order.setOrderProcessed(updatedOrder.getOrderProcessed());
            return orderRepository.saveAndFlush(order);
        }).orElse(null);
    }
}
