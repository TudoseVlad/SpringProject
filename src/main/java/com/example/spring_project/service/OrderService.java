package com.example.spring_project.service;

import org.springframework.stereotype.Service;

import com.example.spring_project.entity.Order;
import com.example.spring_project.entity.Range;
import com.example.spring_project.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
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

    public Range getUnprocessedOrders(){
        List<Order> unprocessedOrders = orderRepository.findUnprocessedOrders();
        Long startIndex = Long.valueOf(0);
        Long endIndex = Long.valueOf(0);

        if(unprocessedOrders.isEmpty()){
            return new Range(startIndex, endIndex);
        }

        startIndex = unprocessedOrders.get(0).getId();
        endIndex = unprocessedOrders.get(unprocessedOrders.size() -1).getId();
        return new Range(startIndex, endIndex);
        
    }

    public List<Order> getOrdersInRange(Long startIndex, Long endIndex, int chunkSize) {
        List<List<Order>> chunks = LongStream.rangeClosed(startIndex, endIndex)
                                             .boxed()
                                             .collect(Collectors.groupingBy(i -> (i - startIndex) / chunkSize))
                                             .values()
                                             .parallelStream()
                                             .map(chunk -> orderRepository.findOrdersInRange(chunk.get(0), chunk.get(chunk.size() - 1)))
                                             .collect(Collectors.toList());

        return chunks.stream()
                     .flatMap(List::stream)
                     .collect(Collectors.toList());
    }

}
