package com.smartfleet.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfleet.Entity.VoiceReminderPreference;

public interface VoiceReminderPreferenceRepository
        extends JpaRepository<VoiceReminderPreference, Long> {

    Optional<VoiceReminderPreference> findByUserId(Long userId);
}