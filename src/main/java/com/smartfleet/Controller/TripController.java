package com.smartfleet.Controller;

import com.smartfleet.Entity.Trip;
import com.smartfleet.Service.TripService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin
public class TripController {

    private final TripService service;

    public TripController(TripService service) {
        this.service = service;
    }

    @PostMapping
    public Trip addTrip(@RequestBody Trip trip) {
        return service.addTrip(trip);
    }

    @GetMapping
    public List<Trip> getTrips() {
        return service.getTrips();
    }

    @PutMapping("/{id}/complete")
    public Trip completeTrip(@PathVariable Long id) {
        return service.completeTrip(id);
    }
}