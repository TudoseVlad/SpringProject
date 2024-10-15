package com.example.spring_project.repository;

import org.springframework.stereotype.Repository;

import com.example.spring_project.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    @Query("SELECT o FROM Order o WHERE o.orderProcessed = false ORDER BY o.id ASC")
    List<Order> findUnprocessedOrders();

    @Query("SELECT o FROM Order o WHERE o.id BETWEEN :startIndex AND :endIndex ORDER BY o.id ASC")
    List<Order> findOrdersInRange(Long startIndex, Long endIndex);
}
