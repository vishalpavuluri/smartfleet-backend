package com.smartfleet.Controller;

import com.smartfleet.Entity.BusinessPayment;
import com.smartfleet.Service.BusinessPaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business-payments")
@CrossOrigin
public class BusinessPaymentController {

    private final BusinessPaymentService service;

    public BusinessPaymentController(BusinessPaymentService service) {
        this.service = service;
    }

    @PostMapping
    public BusinessPayment addPayment(@RequestBody BusinessPayment payment) {
        return service.addPayment(payment);
    }

    @GetMapping
    public List<BusinessPayment> getPayments() {
        return service.getPayments();
    }
}