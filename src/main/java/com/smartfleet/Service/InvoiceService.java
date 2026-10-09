package com.smartfleet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Invoice;
import com.smartfleet.Entity.Trip;
import com.smartfleet.Entity.User;
import com.smartfleet.Repository.InvoiceRepository;
import com.smartfleet.Repository.TripRepository;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final TripRepository tripRepository;
    private final CurrentUserService currentUserService;

    public InvoiceService(
            InvoiceRepository invoiceRepository,
            TripRepository tripRepository,
            CurrentUserService currentUserService) {

        this.invoiceRepository = invoiceRepository;
        this.tripRepository = tripRepository;
        this.currentUserService = currentUserService;
    }

    public Invoice addInvoice(Invoice invoice) {

        User user = currentUserService.getCurrentUser();

        if (invoice.getTrip() == null ||
            invoice.getTrip().getId() == null) {

            throw new RuntimeException("Trip ID Required");
        }

        Trip trip = tripRepository
                .findById(invoice.getTrip().getId())
                .orElseThrow(() ->
                        new RuntimeException("Trip Not Found"));

        if (trip.getUser() == null ||
            !trip.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You Cannot Create Invoice For Another User's Trip"
            );
        }

        invoice.setTrip(trip);

        if (invoice.getTotalAmount() != null &&
            invoice.getAmountReceived() != null) {

            invoice.setPendingAmount(
                    invoice.getTotalAmount()
                    - invoice.getAmountReceived()
            );
        }

        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getInvoices() {

        User user = currentUserService.getCurrentUser();

        return invoiceRepository
                .findByTripUserId(user.getId());
    }
}