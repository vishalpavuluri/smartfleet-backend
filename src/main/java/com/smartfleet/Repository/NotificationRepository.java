package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfleet.Entity.Notification;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    boolean existsByReminderId(Long reminderId);

    List<Notification> findByReminderUserId(Long userId);

    List<Notification>
        findByReminderUserIdAndIsReadFalse(Long userId);
}