package com.smartfleet.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.smartfleet.Entity.VoiceCallLog;
import com.smartfleet.Service.VoiceCallLogService;

@RestController
@RequestMapping("/api/voice-calls")
@CrossOrigin
public class VoiceCallLogController {

    private final VoiceCallLogService service;

    public VoiceCallLogController(
            VoiceCallLogService service) {

        this.service = service;
    }

    @GetMapping
    public List<VoiceCallLog> getLogs() {

        return service.getLogs();
    }

    @PostMapping("/reminder/{reminderId}")
    public VoiceCallLog createCallLog(
            @PathVariable Long reminderId) {

        return service.createCallLog(reminderId);
    }

    @PutMapping("/{id}/status")
    public VoiceCallLog updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return service.updateCallStatus(
                id,
                status
        );
    }
}