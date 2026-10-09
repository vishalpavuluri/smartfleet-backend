package com.smartfleet.Service;

import com.smartfleet.Entity.FastagTransaction;
import com.smartfleet.Repository.FastagTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FastagTransactionService {

    private final FastagTransactionRepository repository;

    public FastagTransactionService(FastagTransactionRepository repository) {
        this.repository = repository;
    }

    public FastagTransaction addTransaction(FastagTransaction transaction) {
        return repository.save(transaction);
    }

    public List<FastagTransaction> getTransactions() {
        return repository.findAll();
    }
}