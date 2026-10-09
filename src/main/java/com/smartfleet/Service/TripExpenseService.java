package com.smartfleet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Trip;
import com.smartfleet.Entity.TripExpense;
import com.smartfleet.Entity.User;
import com.smartfleet.Repository.TripExpenseRepository;
import com.smartfleet.Repository.TripRepository;

@Service
public class TripExpenseService {

    private final TripExpenseRepository tripExpenseRepository;
    private final TripRepository tripRepository;
    private final CurrentUserService currentUserService;

    public TripExpenseService(
            TripExpenseRepository tripExpenseRepository,
            TripRepository tripRepository,
            CurrentUserService currentUserService) {

        this.tripExpenseRepository = tripExpenseRepository;
        this.tripRepository = tripRepository;
        this.currentUserService = currentUserService;
    }

    public TripExpense addTripExpense(TripExpense expense) {

        User user = currentUserService.getCurrentUser();

        if (expense.getTrip() == null ||
            expense.getTrip().getId() == null) {

            throw new RuntimeException("Trip ID Required");
        }

        Trip trip = tripRepository
                .findById(expense.getTrip().getId())
                .orElseThrow(() ->
                        new RuntimeException("Trip Not Found"));

        if (trip.getUser() == null ||
            !trip.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You Cannot Add Expense To Another User's Trip"
            );
        }

        expense.setTrip(trip);

        return tripExpenseRepository.save(expense);
    }

    public List<TripExpense> getTripExpenses() {

        User user = currentUserService.getCurrentUser();

        return tripExpenseRepository
                .findByTripUserId(user.getId());
    }
}