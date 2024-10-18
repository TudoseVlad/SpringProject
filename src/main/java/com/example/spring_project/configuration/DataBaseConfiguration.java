package com.example.spring_project.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring_project.repository.OrderRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// usage if we dont use db and create db in memory
@Configuration
public class DataBaseConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DataBaseConfiguration.class);
    @Bean
    CommandLineRunner initDatabase(OrderRepository repository){
        return args -> {};
    }
}
