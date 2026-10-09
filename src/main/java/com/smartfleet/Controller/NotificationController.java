package com.smartfleet.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.smartfleet.Entity.Notification;
import com.smartfleet.Service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin
public class NotificationController {

    private final NotificationService service;

    public NotificationController(
            NotificationService service) {

        this.service = service;
    }

    @GetMapping
    public List<Notification> getNotifications() {
        return service.getNotifications();
    }

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications() {
        return service.getUnreadNotifications();
    }

    @PutMapping("/{id}/read")
    public Notification markAsRead(
            @PathVariable Long id) {

        return service.markAsRead(id);
    }
}