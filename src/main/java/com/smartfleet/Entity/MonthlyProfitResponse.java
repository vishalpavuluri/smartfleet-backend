package com.smartfleet.Entity;

public class MonthlyProfitResponse {

    private String month;

    private Double totalRevenue;
    private Double tripExpenses;
    private Double vehicleExpenses;
    private Double emiPayments;
    private Double maintenanceExpenses;
    private Double insuranceExpenses;
    private Double fastagExpenses;

    private Double totalExpenses;
    private Double finalProfit;

    public MonthlyProfitResponse() {
    }

    public MonthlyProfitResponse(
            String month,
            Double totalRevenue,
            Double tripExpenses,
            Double vehicleExpenses,
            Double emiPayments,
            Double maintenanceExpenses,
            Double insuranceExpenses,
            Double fastagExpenses,
            Double totalExpenses,
            Double finalProfit) {

        this.month = month;
        this.totalRevenue = totalRevenue;
        this.tripExpenses = tripExpenses;
        this.vehicleExpenses = vehicleExpenses;
        this.emiPayments = emiPayments;
        this.maintenanceExpenses = maintenanceExpenses;
        this.insuranceExpenses = insuranceExpenses;
        this.fastagExpenses = fastagExpenses;
        this.totalExpenses = totalExpenses;
        this.finalProfit = finalProfit;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getTripExpenses() {
        return tripExpenses;
    }

    public void setTripExpenses(Double tripExpenses) {
        this.tripExpenses = tripExpenses;
    }

    public Double getVehicleExpenses() {
        return vehicleExpenses;
    }

    public void setVehicleExpenses(Double vehicleExpenses) {
        this.vehicleExpenses = vehicleExpenses;
    }

    public Double getEmiPayments() {
        return emiPayments;
    }

    public void setEmiPayments(Double emiPayments) {
        this.emiPayments = emiPayments;
    }

    public Double getMaintenanceExpenses() {
        return maintenanceExpenses;
    }

    public void setMaintenanceExpenses(Double maintenanceExpenses) {
        this.maintenanceExpenses = maintenanceExpenses;
    }

    public Double getInsuranceExpenses() {
        return insuranceExpenses;
    }

    public void setInsuranceExpenses(Double insuranceExpenses) {
        this.insuranceExpenses = insuranceExpenses;
    }

    public Double getFastagExpenses() {
        return fastagExpenses;
    }

    public void setFastagExpenses(Double fastagExpenses) {
        this.fastagExpenses = fastagExpenses;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Double getFinalProfit() {
        return finalProfit;
    }

    public void setFinalProfit(Double finalProfit) {
        this.finalProfit = finalProfit;
    }
}