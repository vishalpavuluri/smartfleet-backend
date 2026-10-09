package com.smartfleet.Controller;

import com.smartfleet.Entity.TripExpense;
import com.smartfleet.Service.TripExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trip-expenses")
@CrossOrigin
public class TripExpenseController {

    private final TripExpenseService service;

    public TripExpenseController(TripExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public TripExpense addTripExpense(@RequestBody TripExpense expense) {
        return service.addTripExpense(expense);
    }

    @GetMapping
    public List<TripExpense> getTripExpenses() {
        return service.getTripExpenses();
    }
}