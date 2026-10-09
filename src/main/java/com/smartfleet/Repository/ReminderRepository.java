package com.smartfleet.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfleet.Entity.Reminder;

public interface ReminderRepository
        extends JpaRepository<Reminder, Long> {

    List<Reminder> findByReminderDate(LocalDate reminderDate);

    List<Reminder> findByReminderDateBeforeAndStatus(
            LocalDate reminderDate,
            String status
    );

    boolean existsByReminderTypeAndReminderDateAndVehicleId(
            String reminderType,
            LocalDate reminderDate,
            Long vehicleId
    );

    List<Reminder> findByUserId(Long userId);
}