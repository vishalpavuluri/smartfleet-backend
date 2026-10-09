package com.smartfleet.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.smartfleet.Entity.Reminder;
import com.smartfleet.Service.ReminderService;

@RestController
@RequestMapping("/api/reminders")
@CrossOrigin
public class ReminderController {

    private final ReminderService service;

    public ReminderController(ReminderService service) {
        this.service = service;
    }

    @PostMapping
    public Reminder addReminder(
            @RequestBody Reminder reminder) {

        return service.addReminder(reminder);
    }

    @GetMapping
    public List<Reminder> getReminders() {
        return service.getReminders();
    }

    @PutMapping("/{id}/complete")
    public Reminder completeReminder(
            @PathVariable Long id) {

        return service.markCompleted(id);
    }
}