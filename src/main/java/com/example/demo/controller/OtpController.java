package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.OtpService;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestParam String phone) {
        String otp = otpService.generateOtp(phone);
        return ResponseEntity.ok("OTP sent: " + otp); // Simulated sending
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String phone, @RequestParam String otp) {
        boolean isValid = otpService.validateOtp(phone, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP is valid!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP");
        }
    }
}
