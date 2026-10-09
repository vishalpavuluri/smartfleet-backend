package com.smartfleet.Controller;

import com.smartfleet.Entity.FastagTransaction;
import com.smartfleet.Service.FastagTransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fastag-transactions")
@CrossOrigin
public class FastagTransactionController {

    private final FastagTransactionService service;

    public FastagTransactionController(FastagTransactionService service) {
        this.service = service;
    }

    @PostMapping
    public FastagTransaction addTransaction(
            @RequestBody FastagTransaction transaction) {
        return service.addTransaction(transaction);
    }

    @GetMapping
    public List<FastagTransaction> getTransactions() {
        return service.getTransactions();
    }
}