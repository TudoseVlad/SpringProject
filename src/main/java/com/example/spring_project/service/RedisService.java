package com.example.spring_project.service;



import java.util.concurrent.TimeUnit;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public void saveToken(String username, String token, long expirationInSec) {
        redisTemplate.opsForValue().set(token, username, expirationInSec, TimeUnit.SECONDS);
    }

    public String getToken(String token) {
        return (String) redisTemplate.opsForValue().get(token);
    }

    public boolean tokenExists(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }


    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }
}