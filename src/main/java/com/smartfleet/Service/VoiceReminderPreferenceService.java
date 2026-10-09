package com.smartfleet.Service;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.User;
import com.smartfleet.Entity.VoiceReminderPreference;
import com.smartfleet.Repository.VoiceReminderPreferenceRepository;

@Service
public class VoiceReminderPreferenceService {

    private final VoiceReminderPreferenceRepository repository;
    private final CurrentUserService currentUserService;

    public VoiceReminderPreferenceService(
            VoiceReminderPreferenceRepository repository,
            CurrentUserService currentUserService) {

        this.repository = repository;
        this.currentUserService = currentUserService;
    }

    public VoiceReminderPreference savePreference(
            VoiceReminderPreference preference) {

        User user = currentUserService.getCurrentUser();

        VoiceReminderPreference existing =
                repository.findByUserId(user.getId())
                .orElse(null);

        if (existing != null) {

            existing.setVoiceReminderEnabled(
                    preference.getVoiceReminderEnabled()
            );

            existing.setPreferredLanguage(
                    preference.getPreferredLanguage()
            );

            existing.setPhoneNumber(
                    preference.getPhoneNumber()
            );

            return repository.save(existing);
        }

        if (preference.getVoiceReminderEnabled() == null) {
            preference.setVoiceReminderEnabled(false);
        }

        if (preference.getPreferredLanguage() == null) {
            preference.setPreferredLanguage("ENGLISH");
        }

        preference.setUser(user);

        return repository.save(preference);
    }

    public VoiceReminderPreference getPreference() {

        User user = currentUserService.getCurrentUser();

        return repository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Voice Reminder Preference Not Found"
                        ));
    }

    public VoiceReminderPreference enableVoiceReminder() {

        VoiceReminderPreference preference =
                getPreference();

        preference.setVoiceReminderEnabled(true);

        return repository.save(preference);
    }

    public VoiceReminderPreference disableVoiceReminder() {

        VoiceReminderPreference preference =
                getPreference();

        preference.setVoiceReminderEnabled(false);

        return repository.save(preference);
    }
}