package com.smartfleet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Finance;
import com.smartfleet.Entity.User;
import com.smartfleet.Entity.Vehicle;
import com.smartfleet.Repository.FinanceRepository;
import com.smartfleet.Repository.VehicleRepository;

@Service
public class FinanceService {

    private final FinanceRepository financeRepository;
    private final VehicleRepository vehicleRepository;
    private final CurrentUserService currentUserService;

    public FinanceService(
            FinanceRepository financeRepository,
            VehicleRepository vehicleRepository,
            CurrentUserService currentUserService) {

        this.financeRepository = financeRepository;
        this.vehicleRepository = vehicleRepository;
        this.currentUserService = currentUserService;
    }

    public Finance addFinance(Finance finance) {

        User user = currentUserService.getCurrentUser();

        if (finance.getVehicle() == null ||
            finance.getVehicle().getId() == null) {

            throw new RuntimeException("Vehicle ID Required");
        }

        Vehicle vehicle = vehicleRepository
                .findById(finance.getVehicle().getId())
                .orElseThrow(() ->
                        new RuntimeException("Vehicle Not Found"));

        if (vehicle.getUser() == null ||
            !vehicle.getUser().getId().equals(user.getId())) {

            throw new RuntimeException(
                    "You Can Add Finance Only To Your Own Vehicle"
            );
        }

        finance.setVehicle(vehicle);

        return financeRepository.save(finance);
    }

    public List<Finance> getFinance() {

        User user = currentUserService.getCurrentUser();

        return financeRepository.findByVehicleUserId(user.getId());
    }
}