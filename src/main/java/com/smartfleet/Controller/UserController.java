package com.smartfleet.Controller;

import com.smartfleet.Entity.User;
import com.smartfleet.Service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return service.getUsers();
    }
}