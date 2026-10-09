package com.smartfleet.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fastag")
public class Fastag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;
    private String fastagNumber;
    private Double balance;
    private Double minimumBalance;
    private String lastRechargeDate;
    private Double lastRechargeAmount;
    private String status;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public Fastag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getFastagNumber() {
        return fastagNumber;
    }

    public void setFastagNumber(String fastagNumber) {
        this.fastagNumber = fastagNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public String getLastRechargeDate() {
        return lastRechargeDate;
    }

    public void setLastRechargeDate(String lastRechargeDate) {
        this.lastRechargeDate = lastRechargeDate;
    }

    public Double getLastRechargeAmount() {
        return lastRechargeAmount;
    }

    public void setLastRechargeAmount(Double lastRechargeAmount) {
        this.lastRechargeAmount = lastRechargeAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}