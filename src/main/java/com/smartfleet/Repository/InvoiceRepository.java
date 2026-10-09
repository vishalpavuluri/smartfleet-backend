package com.smartfleet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfleet.Entity.Invoice;

public interface InvoiceRepository
        extends JpaRepository<Invoice, Long> {

    List<Invoice> findByTripUserId(Long userId);
}