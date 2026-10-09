package com.smartfleet.Controller;

import com.smartfleet.Entity.MonthlyProfitResponse;
import com.smartfleet.Service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/monthly-profit")
    public MonthlyProfitResponse getMonthlyProfit(
            @RequestParam String month) {

        return service.getMonthlyProfit(month);
    }
}