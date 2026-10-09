package com.smartfleet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Driver;
import com.smartfleet.Entity.User;
import com.smartfleet.Repository.DriverRepository;

@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final CurrentUserService currentUserService;

    public DriverService(
            DriverRepository driverRepository,
            CurrentUserService currentUserService) {

        this.driverRepository = driverRepository;
        this.currentUserService = currentUserService;
    }

    public Driver addDriver(Driver driver) {

        User user = currentUserService.getCurrentUser();

        driver.setUser(user);

        return driverRepository.save(driver);
    }

    public List<Driver> getDrivers() {

        User user = currentUserService.getCurrentUser();

        return driverRepository.findByUserId(user.getId());
    }
}