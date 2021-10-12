/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Iresha Lakmali
 */
public class Service {
//service_id, service_name, service_cost, service_code, remarks, added_date, added_time, status

    private int serviceId;
    private String serviceCode;
    private String serviceName;
    private String servicecharge;
    private String serviceCost;
    private String serviceRemark;
    private String addedDate;
    private String addedTime;
    private int status;

    public Service() {
    }

    //service_id, service_name, service_cost, service_code, remarks, added_date, added_time, status
    public Service(String serviceCode, String serviceName, String serviceCost, String serviceRemark, String addedDate, String addedTime, int status) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
        this.serviceRemark = serviceRemark;
        this.addedDate = addedDate;
        this.addedTime = addedTime;
        this.status = status;
    }

    public Service(String serviceCode, String serviceName, String serviceCost, String serviceRemark, String addedDate, String addedTime, String servicecharge, int status) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
        this.serviceRemark = serviceRemark;
        this.addedDate = addedDate;
        this.addedTime = addedTime;
        this.status = status;
        this.servicecharge = servicecharge;
    }

    /**
     * @return the serviceId
     */
    public int getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the serviceCode
     */
    public String getServiceCode() {
        return serviceCode;
    }

    /**
     * @param serviceCode the serviceCode to set
     */
    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the serviceCost
     */
    public String getServiceCost() {
        return serviceCost;
    }

    /**
     * @param serviceCost the serviceCost to set
     */
    public void setServiceCost(String serviceCost) {
        this.serviceCost = serviceCost;
    }

    /**
     * @return the serviceRemark
     */
    public String getServiceRemark() {
        return serviceRemark;
    }

    /**
     * @param serviceRemark the serviceRemark to set
     */
    public void setServiceRemark(String serviceRemark) {
        this.serviceRemark = serviceRemark;
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

    /**
     * @return the addedDate
     */
    public String getAddedDate() {
        return addedDate;
    }

    /**
     * @param addedDate the addedDate to set
     */
    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    /**
     * @return the addedTime
     */
    public String getAddedTime() {
        return addedTime;
    }

    /**
     * @return the servicecharge
     */
    public String getServicecharge() {
        return servicecharge;
    }

    /**
     * @param servicecharge the servicecharge to set
     */
    public void setServicecharge(String servicecharge) {
        this.servicecharge = servicecharge;
    }

    /**
     * @param addedTime the addedTime to set
     */
    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    /**
     * @return the addedTime
     */
}
