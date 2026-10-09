package com.smartfleet.Controller;

import com.smartfleet.Entity.FinancePayment;
import com.smartfleet.Service.FinancePaymentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance-payments")
@CrossOrigin
public class FinancePaymentController {

    private final FinancePaymentService service;

    public FinancePaymentController(FinancePaymentService service) {
        this.service = service;
    }

    @PostMapping
    public FinancePayment addPayment(@RequestBody FinancePayment payment) {
        return service.addPayment(payment);
    }

    @GetMapping
    public List<FinancePayment> getPayments() {
        return service.getPayments();
    }
}