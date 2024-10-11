package com.example.spring_project.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring_project.entity.Order;
import com.example.spring_project.repository.OrderRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Configuration
public class DataBaseConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DataBaseConfiguration.class);
    @Bean
    CommandLineRunner initDatabase(OrderRepository repository){
        return args -> {
            log.info("Loading entity into db: " + repository.save(new Order("Dorna",500d,LocalDate.of(2024,10,11))));
            log.info("Loading entity into db: " + repository.save(new Order("Borsec",600d,LocalDate.of(2024,10,12))));
        };
    }
}
