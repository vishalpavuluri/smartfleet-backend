package com.smartfleet.Controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.smartfleet.Entity.LoginRequest;
import com.smartfleet.Entity.LoginResponse;
import com.smartfleet.Entity.User;
import com.smartfleet.Service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(
            @Valid @RequestBody User user) {

        return service.register(user);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        return service.login(request);
    }
}