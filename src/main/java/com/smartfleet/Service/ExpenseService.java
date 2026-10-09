package com.smartfleet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Expense;
import com.smartfleet.Entity.User;
import com.smartfleet.Entity.Vehicle;
import com.smartfleet.Repository.ExpenseRepository;
import com.smartfleet.Repository.VehicleRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final VehicleRepository vehicleRepository;
    private final CurrentUserService currentUserService;

    public ExpenseService(
            ExpenseRepository expenseRepository,
            VehicleRepository vehicleRepository,
            CurrentUserService currentUserService) {

        this.expenseRepository = expenseRepository;
        this.vehicleRepository = vehicleRepository;
        this.currentUserService = currentUserService;
    }

    public Expense addExpense(Expense expense) {

        User user = currentUserService.getCurrentUser();

        if (expense.getVehicle() == null ||
            expense.getVehicle().getId() == null) {

            throw new RuntimeException("Vehicle ID Required");
        }

        Vehicle vehicle = vehicleRepository
                .findById(expense.getVehicle().getId())
                .orElseThrow(() ->
                        new RuntimeException("Vehicle Not Found"));

        if (vehicle.getUser() == null ||
            !vehicle.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You Can Add Expense Only To Your Own Vehicle"
            );
        }

        expense.setVehicle(vehicle);

        return expenseRepository.save(expense);
    }

    public List<Expense> getExpenses() {

        User user = currentUserService.getCurrentUser();

        return expenseRepository
                .findByVehicleUserId(user.getId());
    }
}