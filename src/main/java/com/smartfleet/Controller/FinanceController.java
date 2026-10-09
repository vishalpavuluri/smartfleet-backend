package com.smartfleet.Controller;

import com.smartfleet.Entity.Finance;
import com.smartfleet.Service.FinanceService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance")
@CrossOrigin
public class FinanceController {

    private final FinanceService service;

    public FinanceController(FinanceService service) {
        this.service = service;
    }

    @PostMapping
    public Finance addFinance(@RequestBody Finance finance) {
        return service.addFinance(finance);
    }

    @GetMapping
    public List<Finance> getFinance() {
        return service.getFinance();
    }
}