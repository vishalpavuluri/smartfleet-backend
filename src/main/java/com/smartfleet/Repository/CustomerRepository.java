package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfleet.Entity.Customer;

public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    List<Customer> findByUserId(Long userId);
}