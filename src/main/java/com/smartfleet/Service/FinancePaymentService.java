package com.smartfleet.Service;

import com.smartfleet.Entity.FinancePayment;
import com.smartfleet.Repository.FinancePaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancePaymentService {

    private final FinancePaymentRepository repository;

    public FinancePaymentService(FinancePaymentRepository repository) {
        this.repository = repository;
    }

    public FinancePayment addPayment(FinancePayment payment) {
        return repository.save(payment);
    }

    public List<FinancePayment> getPayments() {
        return repository.findAll();
    }
}