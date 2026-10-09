package com.smartfleet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.BusinessPayment;
import com.smartfleet.Entity.Trip;
import com.smartfleet.Entity.User;
import com.smartfleet.Repository.BusinessPaymentRepository;
import com.smartfleet.Repository.TripRepository;

@Service
public class BusinessPaymentService {

    private final BusinessPaymentRepository paymentRepository;
    private final TripRepository tripRepository;
    private final CurrentUserService currentUserService;

    public BusinessPaymentService(
            BusinessPaymentRepository paymentRepository,
            TripRepository tripRepository,
            CurrentUserService currentUserService) {

        this.paymentRepository = paymentRepository;
        this.tripRepository = tripRepository;
        this.currentUserService = currentUserService;
    }

    public BusinessPayment addPayment(BusinessPayment payment) {

        User user = currentUserService.getCurrentUser();

        if (payment.getTrip() == null ||
            payment.getTrip().getId() == null) {

            throw new RuntimeException("Trip ID Required");
        }

        Trip trip = tripRepository
                .findById(payment.getTrip().getId())
                .orElseThrow(() ->
                        new RuntimeException("Trip Not Found"));

        if (trip.getUser() == null ||
            !trip.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You Cannot Add Payment To Another User's Trip"
            );
        }

        double oldReceived =
                trip.getAmountReceived() == null
                ? 0
                : trip.getAmountReceived();

        double paymentAmount =
                payment.getAmount() == null
                ? 0
                : payment.getAmount();

        double newReceived =
                oldReceived + paymentAmount;

        trip.setAmountReceived(newReceived);

        if (trip.getTotalFreight() != null) {
            trip.setPendingAmount(
                    trip.getTotalFreight() - newReceived
            );
        }

        tripRepository.save(trip);

        payment.setTrip(trip);

        return paymentRepository.save(payment);
    }

    public List<BusinessPayment> getPayments() {

        User user = currentUserService.getCurrentUser();

        return paymentRepository
                .findByTripUserId(user.getId());
    }
}