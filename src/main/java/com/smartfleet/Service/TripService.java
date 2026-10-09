package com.smartfleet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Customer;
import com.smartfleet.Entity.Driver;
import com.smartfleet.Entity.Trip;
import com.smartfleet.Entity.TripExpense;
import com.smartfleet.Entity.User;
import com.smartfleet.Entity.Vehicle;

import com.smartfleet.Repository.CustomerRepository;
import com.smartfleet.Repository.DriverRepository;
import com.smartfleet.Repository.TripExpenseRepository;
import com.smartfleet.Repository.TripRepository;
import com.smartfleet.Repository.VehicleRepository;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final TripExpenseRepository tripExpenseRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final CustomerRepository customerRepository;
    private final CurrentUserService currentUserService;

    public TripService(
            TripRepository tripRepository,
            TripExpenseRepository tripExpenseRepository,
            VehicleRepository vehicleRepository,
            DriverRepository driverRepository,
            CustomerRepository customerRepository,
            CurrentUserService currentUserService) {

        this.tripRepository = tripRepository;
        this.tripExpenseRepository = tripExpenseRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.customerRepository = customerRepository;
        this.currentUserService = currentUserService;
    }

    public Trip addTrip(Trip trip) {

        User user = currentUserService.getCurrentUser();

        if (trip.getVehicle() == null ||
            trip.getVehicle().getId() == null) {
            throw new RuntimeException("Vehicle ID Required");
        }

        if (trip.getDriver() == null ||
            trip.getDriver().getId() == null) {
            throw new RuntimeException("Driver ID Required");
        }

        if (trip.getCustomer() == null ||
            trip.getCustomer().getId() == null) {
            throw new RuntimeException("Customer ID Required");
        }

        Vehicle vehicle = vehicleRepository
                .findById(trip.getVehicle().getId())
                .orElseThrow(() ->
                        new RuntimeException("Vehicle Not Found"));

        Driver driver = driverRepository
                .findById(trip.getDriver().getId())
                .orElseThrow(() ->
                        new RuntimeException("Driver Not Found"));

        Customer customer = customerRepository
                .findById(trip.getCustomer().getId())
                .orElseThrow(() ->
                        new RuntimeException("Customer Not Found"));

        if (vehicle.getUser() == null ||
            !vehicle.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "Vehicle Does Not Belong To Logged In User"
            );
        }

        if (driver.getUser() == null ||
            !driver.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "Driver Does Not Belong To Logged In User"
            );
        }

        if (customer.getUser() == null ||
            !customer.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "Customer Does Not Belong To Logged In User"
            );
        }

        trip.setVehicle(vehicle);
        trip.setDriver(driver);
        trip.setCustomer(customer);
        trip.setUser(user);

        if (trip.getTotalFreight() != null &&
            trip.getAmountReceived() != null) {

            trip.setPendingAmount(
                    trip.getTotalFreight()
                    - trip.getAmountReceived()
            );
        }

        return tripRepository.save(trip);
    }

    public List<Trip> getTrips() {

        User user = currentUserService.getCurrentUser();

        return tripRepository.findByUserId(user.getId());
    }

    public Trip completeTrip(Long tripId) {

        User user = currentUserService.getCurrentUser();

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() ->
                        new RuntimeException("Trip Not Found"));

        if (trip.getUser() == null ||
            !trip.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You Cannot Complete Another User's Trip"
            );
        }

        List<TripExpense> expenses =
                tripExpenseRepository.findByTripId(tripId);

        double totalExpense = 0;

        for (TripExpense expense : expenses) {
            if (expense.getAmount() != null) {
                totalExpense += expense.getAmount();
            }
        }

        trip.setTotalTripExpense(totalExpense);

        if (trip.getTotalFreight() != null) {
            trip.setTripProfit(
                    trip.getTotalFreight() - totalExpense
            );
        }

        trip.setStatus("COMPLETED");

        return tripRepository.save(trip);
    }
}