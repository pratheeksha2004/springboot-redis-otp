package com.example.demo.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String generateOtp(String phone) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit OTP
        redisTemplate.opsForValue().set("OTP:" + phone, otp, 5, TimeUnit.MINUTES);
        return otp;
    }

    public boolean validateOtp(String phone, String otp) {
        String key = "OTP:" + phone;
        String savedOtp = redisTemplate.opsForValue().get(key);
        if (savedOtp != null && savedOtp.equals(otp)) {
            redisTemplate.delete(key); // OTP used, delete it
            return true;
        }
        return false;
    }
}
