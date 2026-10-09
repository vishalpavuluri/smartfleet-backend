package com.smartfleet.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Fastag;
import com.smartfleet.Entity.Finance;
import com.smartfleet.Entity.Insurance;
import com.smartfleet.Entity.Maintenance;
import com.smartfleet.Entity.Reminder;
import com.smartfleet.Entity.Vehicle;
import com.smartfleet.Entity.VehicleDocument;

import com.smartfleet.Repository.FastagRepository;
import com.smartfleet.Repository.FinanceRepository;
import com.smartfleet.Repository.InsuranceRepository;
import com.smartfleet.Repository.MaintenanceRepository;
import com.smartfleet.Repository.ReminderRepository;
import com.smartfleet.Repository.VehicleDocumentRepository;
import com.smartfleet.Repository.VehicleRepository;

@Service
public class AutoReminderService {

    private final FinanceRepository financeRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final InsuranceRepository insuranceRepository;
    private final VehicleDocumentRepository documentRepository;
    private final FastagRepository fastagRepository;
    private final ReminderRepository reminderRepository;
    private final VehicleRepository vehicleRepository;

    public AutoReminderService(
            FinanceRepository financeRepository,
            MaintenanceRepository maintenanceRepository,
            InsuranceRepository insuranceRepository,
            VehicleDocumentRepository documentRepository,
            FastagRepository fastagRepository,
            ReminderRepository reminderRepository,
            VehicleRepository vehicleRepository) {

        this.financeRepository = financeRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.insuranceRepository = insuranceRepository;
        this.documentRepository = documentRepository;
        this.fastagRepository = fastagRepository;
        this.reminderRepository = reminderRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Scheduled(cron = "0 0 7 * * *")
    public void generateAutomaticReminders() {

        checkFinance();
        checkMaintenance();
        checkInsurance();
        checkDocuments();
        checkFastag();
    }

    private void checkFinance() {

        LocalDate today = LocalDate.now();

        List<Finance> finances =
                financeRepository.findAll();

        for (Finance finance : finances) {

            if (finance.getDueDate() == null ||
                finance.getVehicle() == null ||
                finance.getVehicle().getId() == null) {
                continue;
            }

            int dueDay = finance.getDueDate();

            LocalDate dueDate;

            try {
                dueDate = today.withDayOfMonth(dueDay);
            } catch (Exception e) {
                continue;
            }

            if (dueDate.isBefore(today)) {
                dueDate = dueDate.plusMonths(1);
            }

            long daysLeft =
                    ChronoUnit.DAYS.between(
                            today,
                            dueDate
                    );

            if (daysLeft <= 7) {

                createReminder(
                        "EMI",
                        "EMI Payment Due",
                        "Your vehicle EMI is due on " + dueDate,
                        dueDate,
                        getPriority(daysLeft),
                        finance.getVehicle().getId()
                );
            }
        }
    }

    private void checkMaintenance() {

        LocalDate today = LocalDate.now();

        for (Maintenance maintenance :
                maintenanceRepository.findAll()) {

            if (maintenance.getNextServiceDate() == null ||
                maintenance.getVehicle() == null ||
                maintenance.getVehicle().getId() == null) {
                continue;
            }

            try {

                LocalDate serviceDate =
                        LocalDate.parse(
                                maintenance.getNextServiceDate()
                        );

                long daysLeft =
                        ChronoUnit.DAYS.between(
                                today,
                                serviceDate
                        );

                if (daysLeft >= 0 &&
                    daysLeft <= 7) {

                    createReminder(
                            "SERVICE",
                            "Vehicle Service Due",
                            "Vehicle service is due on "
                                    + serviceDate,
                            serviceDate,
                            getPriority(daysLeft),
                            maintenance.getVehicle().getId()
                    );
                }

            } catch (Exception e) {
            }
        }
    }

    private void checkInsurance() {

        LocalDate today = LocalDate.now();

        for (Insurance insurance :
                insuranceRepository.findAll()) {

            if (insurance.getExpiryDate() == null ||
                insurance.getVehicle() == null ||
                insurance.getVehicle().getId() == null) {
                continue;
            }

            try {

                LocalDate expiryDate =
                        LocalDate.parse(
                                insurance.getExpiryDate()
                        );

                long daysLeft =
                        ChronoUnit.DAYS.between(
                                today,
                                expiryDate
                        );

                if (daysLeft >= 0 &&
                    daysLeft <= 7) {

                    createReminder(
                            "INSURANCE",
                            "Insurance Expiry",
                            "Vehicle insurance expires on "
                                    + expiryDate,
                            expiryDate,
                            getPriority(daysLeft),
                            insurance.getVehicle().getId()
                    );
                }

            } catch (Exception e) {
            }
        }
    }

    private void checkDocuments() {

        LocalDate today = LocalDate.now();

        for (VehicleDocument document :
                documentRepository.findAll()) {

            if (document.getExpiryDate() == null ||
                document.getVehicle() == null ||
                document.getVehicle().getId() == null) {
                continue;
            }

            try {

                LocalDate expiryDate =
                        LocalDate.parse(
                                document.getExpiryDate()
                        );

                long daysLeft =
                        ChronoUnit.DAYS.between(
                                today,
                                expiryDate
                        );

                if (daysLeft >= 0 &&
                    daysLeft <= 7) {

                    createReminder(
                            "DOCUMENT",
                            document.getDocumentType()
                                    + " Expiry",
                            document.getDocumentType()
                                    + " expires on "
                                    + expiryDate,
                            expiryDate,
                            getPriority(daysLeft),
                            document.getVehicle().getId()
                    );
                }

            } catch (Exception e) {
            }
        }
    }

    private void checkFastag() {

        LocalDate today = LocalDate.now();

        for (Fastag fastag :
                fastagRepository.findAll()) {

            if (fastag.getBalance() == null ||
                fastag.getMinimumBalance() == null ||
                fastag.getVehicle() == null ||
                fastag.getVehicle().getId() == null) {
                continue;
            }

            if (fastag.getBalance()
                    <= fastag.getMinimumBalance()) {

                createReminder(
                        "FASTAG",
                        "FASTag Low Balance",
                        "FASTag balance is low. Current balance: "
                                + fastag.getBalance(),
                        today,
                        "HIGH",
                        fastag.getVehicle().getId()
                );
            }
        }
    }

    private void createReminder(
            String type,
            String title,
            String message,
            LocalDate date,
            String priority,
            Long vehicleId) {

        boolean exists =
                reminderRepository
                .existsByReminderTypeAndReminderDateAndVehicleId(
                        type,
                        date,
                        vehicleId
                );

        if (exists) {
            return;
        }

        Vehicle vehicle =
                vehicleRepository.findById(vehicleId)
                .orElse(null);

        if (vehicle == null ||
            vehicle.getUser() == null) {
            return;
        }

        Reminder reminder =
                new Reminder();

        reminder.setReminderType(type);
        reminder.setTitle(title);
        reminder.setMessage(message);
        reminder.setReminderDate(date);
        reminder.setPriority(priority);
        reminder.setStatus("PENDING");

        reminder.setVehicle(vehicle);
        reminder.setUser(vehicle.getUser());

        reminderRepository.save(reminder);
    }

    private String getPriority(long daysLeft) {

        if (daysLeft <= 1) {
            return "URGENT";
        }

        if (daysLeft <= 3) {
            return "HIGH";
        }

        return "NORMAL";
    }
}