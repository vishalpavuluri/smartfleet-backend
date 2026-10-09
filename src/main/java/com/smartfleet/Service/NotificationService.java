package com.smartfleet.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Notification;
import com.smartfleet.Entity.Reminder;
import com.smartfleet.Entity.User;
import com.smartfleet.Repository.NotificationRepository;
import com.smartfleet.Repository.ReminderRepository;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ReminderRepository reminderRepository;
    private final CurrentUserService currentUserService;

    public NotificationService(
            NotificationRepository notificationRepository,
            ReminderRepository reminderRepository,
            CurrentUserService currentUserService) {

        this.notificationRepository = notificationRepository;
        this.reminderRepository = reminderRepository;
        this.currentUserService = currentUserService;
    }

    public List<Notification> getNotifications() {

        User user =
                currentUserService.getCurrentUser();

        return notificationRepository
                .findByReminderUserId(
                        user.getId()
                );
    }

    public List<Notification> getUnreadNotifications() {

        User user =
                currentUserService.getCurrentUser();

        return notificationRepository
                .findByReminderUserIdAndIsReadFalse(
                        user.getId()
                );
    }

    public Notification markAsRead(Long id) {

        User user =
                currentUserService.getCurrentUser();

        Notification notification =
                notificationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Notification Not Found"
                        ));

        if (notification.getReminder() == null ||
            notification.getReminder().getUser() == null ||
            !notification.getReminder()
                    .getUser()
                    .getId()
                    .equals(user.getId())) {

            throw new RuntimeException(
                    "You Cannot Access Another User's Notification"
            );
        }

        notification.setIsRead(true);

        return notificationRepository.save(
                notification
        );
    }

    @Scheduled(cron = "0 0 * * * *")
    public void generateNotifications() {

        List<Reminder> reminders =
                reminderRepository.findAll();

        for (Reminder reminder : reminders) {

            if (reminder.getId() == null ||
                reminder.getUser() == null) {
                continue;
            }

            if ("COMPLETED".equalsIgnoreCase(
                    reminder.getStatus())) {
                continue;
            }

            boolean exists =
                    notificationRepository
                    .existsByReminderId(
                            reminder.getId()
                    );

            if (exists) {
                continue;
            }

            Notification notification =
                    new Notification();

            notification.setTitle(
                    reminder.getTitle()
            );

            notification.setMessage(
                    reminder.getMessage()
            );

            notification.setNotificationType(
                    reminder.getReminderType()
            );

            notification.setPriority(
                    reminder.getPriority()
            );

            notification.setIsRead(false);

            notification.setCreatedAt(
                    LocalDateTime.now()
            );

            notification.setReminder(
                    reminder
            );

            notificationRepository.save(
                    notification
            );
        }
    }
}