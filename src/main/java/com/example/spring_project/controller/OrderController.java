package com.example.spring_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_project.service.OrderService;
import com.example.spring_project.entity.Order;
import com.example.spring_project.entity.Range;
import com.example.spring_project.helpers.CustomErrorResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    
    private OrderService orderService;

    public OrderController(OrderService orderService){ this.orderService = orderService;}

    @GetMapping("/info")
    public ResponseEntity<?> info(){
        log.info(LocalDateTime.now() + "Info endpoint called");
        return new ResponseEntity<>("Order Products API is up and running!", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getOrderByIndex(@RequestParam(name = "index") Long index) {
        log.info(LocalDateTime.now() + "Get order by index endpoint called");
        ResponseEntity<?> response = null;
        try {
            Order order = orderService.getOrderById(index);
            log.info(LocalDateTime.now() + " Successfully retrieved order with index: " + index + "; order: \n" + order);
            if(order != null) {
                response = new ResponseEntity<>(order, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "No order order for index = " + index), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.warn(LocalDateTime.now() + " Exception encountered while trying to retrieve order with index: " + index + "; Exception: " + e.getMessage());
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    public ResponseEntity<?>  getOrdersInRange(@RequestParam(name ="startIndex") Long startIndex, @RequestParam(name = "endIndex") Long endIndex) {
        log.info(LocalDateTime.now() + "Get order in ranges endpoint called");
        ResponseEntity<?> response = null;
        try {
            List<Order> sol = orderService.getOrdersInRange(startIndex,endIndex,100);
            log.info(LocalDateTime.now() + " Successfully retrieved order with indexes!");
            if(sol.size() != endIndex - startIndex) {
                response = new ResponseEntity<>(sol, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new CustomErrorResponse("404", "Invalid list of orders = " + sol.size() + "instead of " +(endIndex-startIndex)) , HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.warn(LocalDateTime.now() + " Exception encountered while trying to fetch indexes" + e.getMessage());
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return response;
    }




    @GetMapping("/unprocessed")
    public ResponseEntity<?> getUnprocessedOrders() {
        log.info(LocalDateTime.now() + "Get unprocessed orders by index endpoint called");
        ResponseEntity<?> response = null;
        try {
            Range sol = orderService.getUnprocessedOrders();
            log.info(LocalDateTime.now() + " Successfully retrieved order with indexes!");
            if(sol.getFirst() != 0 && sol.getSecond() != 0) {
                response = new ResponseEntity<>(sol.toString(), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>("All the orders have been processed", HttpStatus.OK);
            }
        } catch (Exception e) {
            log.warn(LocalDateTime.now() + " Exception encountered while trying to fetch indexes" + e.getMessage());
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @PostMapping()
    public ResponseEntity<?> addOrder(@RequestBody Order order) {
        log.info(LocalDateTime.now() + " Post endpoint for creating order called");
        ResponseEntity<?> response = null;

        try {
            orderService.addOrder(order);
            log.info(LocalDateTime.now() + "; Successfully created entry for order:\n" + order);
            response = new ResponseEntity<>("Order added successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            log.warn(LocalDateTime.now() + " Exception encountered while trying to create order. Exception: " + e.getMessage());
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}
