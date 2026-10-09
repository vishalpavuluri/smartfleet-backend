package com.smartfleet.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "voice_reminder_preferences")
public class VoiceReminderPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean voiceReminderEnabled;
    private String preferredLanguage;
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public VoiceReminderPreference() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getVoiceReminderEnabled() {
        return voiceReminderEnabled;
    }

    public void setVoiceReminderEnabled(Boolean voiceReminderEnabled) {
        this.voiceReminderEnabled = voiceReminderEnabled;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}