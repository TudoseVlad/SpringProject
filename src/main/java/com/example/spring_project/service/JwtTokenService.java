package com.example.spring_project.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class JwtTokenService {
    private RedisTemplate<String, Object> redisTemplate;

    private static final String TOKEN_KEY = "jwt_root";

    public void storeToken(String token, long expirationTimeSec){
        redisTemplate.opsForValue().set(TOKEN_KEY, token, Duration.ofSeconds(expirationTimeSec));
    }

    public String getToken(){
        return (String) redisTemplate.opsForValue().get(TOKEN_KEY);
    }

    public boolean isTokenPresent(){
        return redisTemplate.hasKey(TOKEN_KEY);
    }

    public void deleteToken(){
        redisTemplate.delete(TOKEN_KEY);
    }
}
