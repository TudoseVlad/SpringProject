package com.example.spring_project.controller;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_project.components.JwtTokenProvider;
import com.example.spring_project.entity.User;
import com.example.spring_project.helpers.CustomErrorResponse;
import com.example.spring_project.service.RedisService;
import com.example.spring_project.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/auth")
public class AuthController {


    private UserService userService;


    private JwtTokenProvider JwtTokenProvider;
    
    private RedisService redisService;

    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider, RedisService redisService){ 
        this.userService = userService;
        this.JwtTokenProvider = jwtTokenProvider;
        this.redisService = redisService;
    }
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @PostMapping()
    public ResponseEntity<?> authUser(@RequestHeader("Authorization") String authHeader) {
        log.info(LocalDateTime.now() + "Auth user endpoint called");
        ResponseEntity<?> response = null;
        try{
            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] values = credentials.split(":", 2);
            String username = values[0];
            if(userService.getUserByUsername(username) != null){
                log.info(LocalDateTime.now() + " User already registered!");
               response = new ResponseEntity<>(new CustomErrorResponse("400", "User already registered"), HttpStatus.BAD_REQUEST);
            }
            else{
                userService.addUser(new User(username, base64Credentials));
                log.info(LocalDateTime.now() + " User added :" + username);

                String JwtToken = JwtTokenProvider.generateToken(username);
                redisService.saveToken(username, JwtToken, 3600);
                log.info(LocalDateTime.now() + " Jwt token added");

                response = ResponseEntity.ok(Collections.singletonMap("token",JwtToken));
            }
        }catch(Exception e) {
            log.warn(LocalDateTime.now() + " Exception encountered while trying to add username ;Exception: " + e.getMessage());
            response = new ResponseEntity<>(new CustomErrorResponse("400", e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return response;
    }

}
