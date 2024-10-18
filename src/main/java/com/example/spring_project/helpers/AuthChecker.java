package com.example.spring_project.helpers;

import com.example.spring_project.service.RedisService;

public class AuthChecker {
    private RedisService redisService;

    public AuthChecker(RedisService redisService){
        this.redisService = redisService;
    }

    public boolean checkCredentials(String authHeader){
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); 
        }
        else{
            return false;
        }


        return redisService.tokenExists(token);
    }
}
