package com.smartfleet.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Reminder;
import com.smartfleet.Entity.User;
import com.smartfleet.Repository.ReminderRepository;

@Service
public class ReminderService {

    private final ReminderRepository repository;
    private final CurrentUserService currentUserService;

    public ReminderService(
            ReminderRepository repository,
            CurrentUserService currentUserService) {

        this.repository = repository;
        this.currentUserService = currentUserService;
    }

    public Reminder addReminder(Reminder reminder) {

        User user = currentUserService.getCurrentUser();

        reminder.setUser(user);

        if (reminder.getStatus() == null) {
            reminder.setStatus("PENDING");
        }

        return repository.save(reminder);
    }

    public List<Reminder> getReminders() {

        User user = currentUserService.getCurrentUser();

        return repository.findByUserId(user.getId());
    }

    public Reminder markCompleted(Long id) {

        User user = currentUserService.getCurrentUser();

        Reminder reminder = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Reminder Not Found"));

        if (reminder.getUser() == null ||
            !reminder.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You Cannot Modify Another User's Reminder"
            );
        }

        reminder.setStatus("COMPLETED");

        return repository.save(reminder);
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void checkReminders() {

        LocalDate today = LocalDate.now();

        List<Reminder> todayReminders =
                repository.findByReminderDate(today);

        for (Reminder reminder : todayReminders) {

            if (!"COMPLETED".equalsIgnoreCase(
                    reminder.getStatus())) {

                reminder.setStatus("DUE");

                repository.save(reminder);
            }
        }

        List<Reminder> overdueReminders =
                repository.findByReminderDateBeforeAndStatus(
                        today,
                        "PENDING"
                );

        for (Reminder reminder : overdueReminders) {

            reminder.setStatus("OVERDUE");

            repository.save(reminder);
        }
    }
}