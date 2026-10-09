package com.smartfleet.Service;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.MonthlyProfitResponse;
import com.smartfleet.Entity.User;
import com.smartfleet.Repository.ExpenseRepository;
import com.smartfleet.Repository.FastagTransactionRepository;
import com.smartfleet.Repository.FinancePaymentRepository;
import com.smartfleet.Repository.InsuranceRepository;
import com.smartfleet.Repository.MaintenanceRepository;
import com.smartfleet.Repository.TripExpenseRepository;
import com.smartfleet.Repository.TripRepository;

@Service
public class DashboardService {

    private final TripRepository tripRepository;
    private final TripExpenseRepository tripExpenseRepository;
    private final ExpenseRepository expenseRepository;
    private final FinancePaymentRepository financePaymentRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final InsuranceRepository insuranceRepository;
    private final FastagTransactionRepository fastagTransactionRepository;
    private final CurrentUserService currentUserService;

    public DashboardService(
            TripRepository tripRepository,
            TripExpenseRepository tripExpenseRepository,
            ExpenseRepository expenseRepository,
            FinancePaymentRepository financePaymentRepository,
            MaintenanceRepository maintenanceRepository,
            InsuranceRepository insuranceRepository,
            FastagTransactionRepository fastagTransactionRepository,
            CurrentUserService currentUserService) {

        this.tripRepository = tripRepository;
        this.tripExpenseRepository = tripExpenseRepository;
        this.expenseRepository = expenseRepository;
        this.financePaymentRepository = financePaymentRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.insuranceRepository = insuranceRepository;
        this.fastagTransactionRepository = fastagTransactionRepository;
        this.currentUserService = currentUserService;
    }

    public MonthlyProfitResponse getMonthlyProfit(String month) {

        User user = currentUserService.getCurrentUser();

        Long userId = user.getId();

        double revenue =
                tripRepository.getMonthlyRevenue(userId, month);

        double tripExpenses =
                tripExpenseRepository.getMonthlyTripExpenses(userId, month);

        double vehicleExpenses =
                expenseRepository.getMonthlyExpenses(userId, month);

        double emiPayments =
                financePaymentRepository.getMonthlyEmiPayments(userId, month);

        double maintenanceExpenses =
                maintenanceRepository.getMonthlyMaintenanceCost(userId, month);

        double insuranceExpenses =
                insuranceRepository.getMonthlyInsuranceCost(userId, month);

        double fastagExpenses =
                fastagTransactionRepository.getMonthlyFastagExpense(userId, month);

        double totalExpenses =
                tripExpenses
                + vehicleExpenses
                + emiPayments
                + maintenanceExpenses
                + insuranceExpenses
                + fastagExpenses;

        double finalProfit =
                revenue - totalExpenses;

        return new MonthlyProfitResponse(
                month,
                revenue,
                tripExpenses,
                vehicleExpenses,
                emiPayments,
                maintenanceExpenses,
                insuranceExpenses,
                fastagExpenses,
                totalExpenses,
                finalProfit
        );
    }
}