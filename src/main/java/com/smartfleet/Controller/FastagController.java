package com.smartfleet.Controller;

import com.smartfleet.Entity.Fastag;
import com.smartfleet.Service.FastagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fastag")
@CrossOrigin
public class FastagController {

    private final FastagService service;

    public FastagController(FastagService service) {
        this.service = service;
    }

    @PostMapping
    public Fastag addFastag(@RequestBody Fastag fastag) {
        return service.addFastag(fastag);
    }

    @GetMapping
    public List<Fastag> getFastags() {
        return service.getFastags();
    }
}