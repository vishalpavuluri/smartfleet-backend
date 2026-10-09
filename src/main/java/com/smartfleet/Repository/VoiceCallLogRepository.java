package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfleet.Entity.VoiceCallLog;

public interface VoiceCallLogRepository
        extends JpaRepository<VoiceCallLog, Long> {

    boolean existsByReminderId(Long reminderId);

    List<VoiceCallLog> findByUserId(Long userId);
}