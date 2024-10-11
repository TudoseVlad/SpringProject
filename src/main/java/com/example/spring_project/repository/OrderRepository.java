package com.example.spring_project.repository;

import org.springframework.stereotype.Repository;

import com.example.spring_project.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
}
