/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author ceylonlinux
 */
public class ExpenceIncome {
    
    private double incomeId;
    private String addedTime;
    private String addedDate;
    private double cashIncome;
    private double creditIncome;
    private double relization;
    private double valueReturn;
    private double totalIncome;
    private double cost;
    private double grossProfit;
    private double otherExpence;
    private double garageCommission;
    private double salesMan;
    private double paymentSupplier;
    private double totalExpence;
    private double netAmount;
    private int status;
    
    
     public ExpenceIncome() {
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }
    
      public ExpenceIncome(String addedDate,String addedTime,double cashIncome,double creditIncome,double relization,double valueReturn,double totalIncome,double cost,double grossProfit,double otherExpence,double garageCommission,double salesMan,double paymentSupplier,double totalExpence,double netAmount,int status) {
        this.addedDate=addedDate;
        this.addedTime=addedTime;
        this.cashIncome = cashIncome;
        this.creditIncome = creditIncome;
        this.relization = relization;
        this.valueReturn = valueReturn;
        this.totalIncome = totalIncome;
        this.cost = cost;
        this.grossProfit = grossProfit;
        this.otherExpence = otherExpence;
        this.garageCommission = garageCommission;
        this.salesMan = salesMan;
        this.paymentSupplier =paymentSupplier;
        this.totalExpence = totalExpence;
        this.netAmount = netAmount;
        this.status = status;
    }

    public double getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(double incomeId) {
        this.incomeId = incomeId;
    }

    public double getCashIncome() {
        return cashIncome;
    }

    public void setCashIncome(double cashIncome) {
        this.cashIncome = cashIncome;
    }

    public double getCreditIncome() {
        return creditIncome;
    }

    public void setCreditIncome(double creditIncome) {
        this.creditIncome = creditIncome;
    }

    public double getRelization() {
        return relization;
    }

    public void setRelization(double relization) {
        this.relization = relization;
    }

    public double getValueReturn() {
        return valueReturn;
    }

    public void setValueReturn(double valueReturn) {
        this.valueReturn = valueReturn;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public double getOtherExpence() {
        return otherExpence;
    }

    public void setOtherExpence(double otherExpence) {
        this.otherExpence = otherExpence;
    }

    public double getGarageCommission() {
        return garageCommission;
    }

    public void setGarageCommission(double garageCommission) {
        this.garageCommission = garageCommission;
    }

    public double getSalesMan() {
        return salesMan;
    }

    public void setSalesMan(double salesMan) {
        this.salesMan = salesMan;
    }

    public double getPaymentSupplier() {
        return paymentSupplier;
    }

    public void setPaymentSupplier(double paymentSupplier) {
        this.paymentSupplier = paymentSupplier;
    }

    public double getTotalExpence() {
        return totalExpence;
    }

    public void setTotalExpence(double totalExpence) {
        this.totalExpence = totalExpence;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
      
}
