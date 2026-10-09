package com.smartfleet.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfleet.Entity.Customer;
import com.smartfleet.Entity.User;
import com.smartfleet.Repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CurrentUserService currentUserService;

    public CustomerService(
            CustomerRepository customerRepository,
            CurrentUserService currentUserService) {

        this.customerRepository = customerRepository;
        this.currentUserService = currentUserService;
    }

    public Customer addCustomer(Customer customer) {

        User user = currentUserService.getCurrentUser();

        customer.setUser(user);

        return customerRepository.save(customer);
    }

    public List<Customer> getCustomers() {

        User user = currentUserService.getCurrentUser();

        return customerRepository.findByUserId(user.getId());
    }
}