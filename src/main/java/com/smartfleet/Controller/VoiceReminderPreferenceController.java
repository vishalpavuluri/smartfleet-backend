package com.smartfleet.Controller;

import org.springframework.web.bind.annotation.*;

import com.smartfleet.Entity.VoiceReminderPreference;
import com.smartfleet.Service.VoiceReminderPreferenceService;

@RestController
@RequestMapping("/api/voice-reminders")
@CrossOrigin
public class VoiceReminderPreferenceController {

    private final VoiceReminderPreferenceService service;

    public VoiceReminderPreferenceController(
            VoiceReminderPreferenceService service) {

        this.service = service;
    }

    @PostMapping
    public VoiceReminderPreference savePreference(
            @RequestBody VoiceReminderPreference preference) {

        return service.savePreference(preference);
    }

    @GetMapping
    public VoiceReminderPreference getPreference() {

        return service.getPreference();
    }

    @PutMapping("/enable")
    public VoiceReminderPreference enableVoiceReminder() {

        return service.enableVoiceReminder();
    }

    @PutMapping("/disable")
    public VoiceReminderPreference disableVoiceReminder() {

        return service.disableVoiceReminder();
    }
}