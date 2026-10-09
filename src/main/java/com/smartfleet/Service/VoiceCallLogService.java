package com.smartfleet.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Reminder;
import com.smartfleet.Entity.User;
import com.smartfleet.Entity.VoiceCallLog;
import com.smartfleet.Entity.VoiceReminderPreference;
import com.smartfleet.Repository.ReminderRepository;
import com.smartfleet.Repository.VoiceCallLogRepository;
import com.smartfleet.Repository.VoiceReminderPreferenceRepository;

@Service
public class VoiceCallLogService {

    private final VoiceCallLogRepository voiceCallLogRepository;
    private final ReminderRepository reminderRepository;
    private final VoiceReminderPreferenceRepository preferenceRepository;
    private final CurrentUserService currentUserService;

    public VoiceCallLogService(
            VoiceCallLogRepository voiceCallLogRepository,
            ReminderRepository reminderRepository,
            VoiceReminderPreferenceRepository preferenceRepository,
            CurrentUserService currentUserService) {

        this.voiceCallLogRepository = voiceCallLogRepository;
        this.reminderRepository = reminderRepository;
        this.preferenceRepository = preferenceRepository;
        this.currentUserService = currentUserService;
    }

    public List<VoiceCallLog> getLogs() {

        User user = currentUserService.getCurrentUser();

        return voiceCallLogRepository
                .findByUserId(user.getId());
    }

    public VoiceCallLog createCallLog(Long reminderId) {

        User user = currentUserService.getCurrentUser();

        Reminder reminder = reminderRepository
                .findById(reminderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Reminder Not Found"
                        ));

        if (reminder.getUser() == null ||
            !reminder.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You Cannot Access Another User's Reminder"
            );
        }

        VoiceReminderPreference preference =
                preferenceRepository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Voice Reminder Preference Not Found"
                        ));

        if (!Boolean.TRUE.equals(
                preference.getVoiceReminderEnabled())) {

            throw new RuntimeException(
                    "Voice Reminder Is Disabled"
            );
        }

        if (!"OVERDUE".equalsIgnoreCase(
                reminder.getStatus())) {

            throw new RuntimeException(
                    "Reminder Is Not Overdue"
            );
        }

        if (voiceCallLogRepository
                .existsByReminderId(reminderId)) {

            throw new RuntimeException(
                    "Voice Call Already Created"
            );
        }

        VoiceCallLog log = new VoiceCallLog();

        log.setPhoneNumber(
                preference.getPhoneNumber()
        );

        log.setLanguage(
                preference.getPreferredLanguage()
        );

        log.setCallStatus("PENDING");

        log.setMessage(
                reminder.getMessage()
        );

        log.setCreatedAt(
                LocalDateTime.now()
        );

        log.setReminder(reminder);
        log.setUser(user);

        return voiceCallLogRepository.save(log);
    }

    public VoiceCallLog updateCallStatus(
            Long id,
            String status) {

        User user = currentUserService.getCurrentUser();

        VoiceCallLog log =
                voiceCallLogRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Voice Call Log Not Found"
                        ));

        if (log.getUser() == null ||
            !log.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You Cannot Modify Another User's Voice Call"
            );
        }

        log.setCallStatus(status);

        return voiceCallLogRepository.save(log);
    }
}