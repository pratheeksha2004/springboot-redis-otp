package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.OtpService;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private JavaMailSender javaMailSender;

    // ✅ Send OTP to a specific email (hardcoded)
    @PostMapping("/send")
    public ResponseEntity<String> sendOtp() {
        String fixedEmail = "email@gmail.com"; // 🔒 Send OTP always to this email
        String otp = otpService.generateOtp(fixedEmail);

        // Compose and send OTP email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(fixedEmail);
        message.setSubject("Your OTP Code");
        message.setText("Your One-Time Password (OTP) is: " + otp + "\n\nNote: This OTP is valid for 5 minutes.");
        message.setText("(Do not replay)");
        javaMailSender.send(message);

        return ResponseEntity.ok("OTP has been sent to " + fixedEmail);
    }

    // ✅ Verify OTP
    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String otp) {
        String fixedEmail = "email@gmail.com"; // 🔒 Match with same email
        boolean isValid = otpService.validateOtp(fixedEmail, otp);
        if (isValid) {
            return ResponseEntity.ok("✅ OTP is valid!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Invalid or expired OTP");
        }
    }
}
