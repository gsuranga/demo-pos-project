/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

/**
 *
 * @author user-pc
 */
public class Cheque {

        private int ID;
        private int debtID;
    private String chequeNo;
    private String delearname;
    private String bank;
    private String accountNO;
    private String addedDate;
    private String addedTime;
    private String checkDate;
    private Double amount;
    private int status;
    private int chequestatus;
    
     public Cheque() {
    }

    public Cheque(int debtID, String chequeNo, String delearname,String bank,String accountNO,String addedDate,String addedTime,String checkDate,double amount, int status,int cheque_status) {
        this.debtID = debtID;
        this.chequeNo = chequeNo;
        this.delearname = delearname;
        this.bank=bank;
        this.accountNO=accountNO;
        this.addedDate=addedDate;
        this.addedTime=addedTime;
        this.checkDate=checkDate;
        this.amount=amount;
        this.status = status;
        this.chequestatus=cheque_status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getDebtID() {
        return debtID;
    }

    public void setDebtID(int debtID) {
        this.debtID = debtID;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getDelearname() {
        return delearname;
    }

    public void setDelearname(String delearname) {
        this.delearname = delearname;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountNO() {
        return accountNO;
    }

    public void setAccountNO(String accountNO) {
        this.accountNO = accountNO;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getChequestatus() {
        return chequestatus;
    }

    public void setChequestatus(int chequestatus) {
        this.chequestatus = chequestatus;
    }
    
    
    
}
