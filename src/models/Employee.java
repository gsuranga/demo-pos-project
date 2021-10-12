/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author @author Iresha Lakmali
 */
public class Employee {

    private int employeeId;
    private String employeeName;
    private String employeeCode;
    private String employeeContactNo1;
    private String employeeContactNo2;
    private String employeeAddress;
    private String registered_date;
    private String registered_time;
    private int status;

    public Employee() {
    }

    public Employee(int employeeId, String employeeName, String employeeCode, String employeeContactNo1, String employeeContactNo2, String employeeAddress, String registered_date, String registered_time, int status) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeCode = employeeCode;
        this.employeeContactNo1 = employeeContactNo1;
        this.employeeContactNo2 = employeeContactNo2;
        this.employeeAddress = employeeAddress;
        this.registered_date = registered_date;
        this.registered_time = registered_time;
        this.status = status;
    }

    /**
     * @return the employeeId
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * @return the employeeCode
     */
    public String getEmployeeCode() {
        return employeeCode;
    }

    /**
     * @param employeeCode the employeeCode to set
     */
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    /**
     * @return the employeeContactNo1
     */
    public String getEmployeeContactNo1() {
        return employeeContactNo1;
    }

    /**
     * @param employeeContactNo1 the employeeContactNo1 to set
     */
    public void setEmployeeContactNo1(String employeeContactNo1) {
        this.employeeContactNo1 = employeeContactNo1;
    }

    /**
     * @return the employeeContactNo2
     */
    public String getEmployeeContactNo2() {
        return employeeContactNo2;
    }

    /**
     * @param employeeContactNo2 the employeeContactNo2 to set
     */
    public void setEmployeeContactNo2(String employeeContactNo2) {
        this.employeeContactNo2 = employeeContactNo2;
    }

    /**
     * @return the employeeAddress
     */
    public String getEmployeeAddress() {
        return employeeAddress;
    }

    /**
     * @param employeeAddress the employeeAddress to set
     */
    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    /**
     * @return the registered_date
     */
    public String getRegistered_date() {
        return registered_date;
    }

    /**
     * @param registered_date the registered_date to set
     */
    public void setRegistered_date(String registered_date) {
        this.registered_date = registered_date;
    }

    /**
     * @return the registered_time
     */
    public String getRegistered_time() {
        return registered_time;
    }

    /**
     * @param registered_time the registered_time to set
     */
    public void setRegistered_time(String registered_time) {
        this.registered_time = registered_time;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
@Override
    public String toString() {
        if (employeeName == null) {
            return "";
        } else {
            return this.employeeName;
        }
        //To change body of generated methods, choose Tools | Templates.
    }
}
