/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author insaf
 */
public class purchesorderdetail {

    private int id;
    private int orderid;
    private int itemId;
    private double qty;
    private int status;
    private double unitprice;

    public purchesorderdetail() {
    }

    public purchesorderdetail(int id, int orderid, int itemId, double qty, int status, double unitprice) {
        this.id = id;
        this.orderid = orderid;
        this.itemId = itemId;
        this.qty = qty;
        this.status = status;
        this.unitprice = unitprice;
    }

    public purchesorderdetail(int id, int orderid, int itemId, int qty) {
        this.id = id;
        this.orderid = orderid;
        this.itemId = itemId;
        this.qty = qty;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the orderid
     */
    public int getOrderid() {
        return orderid;
    }

    /**
     * @param orderid the orderid to set
     */
    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    /**
     * @return the item
     */
    public int getItem() {
        return getItemId();
    }

    /**
     * @param item the item to set
     */
    public void setItem(int itemId) {
        this.setItemId(itemId);
    }

    /**
     * @return the qty
     */
    public double getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(double qty) {
        this.qty = qty;
    }

    /**
     * @return the itemId
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
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
     * @return the unitprice
     */
    public double getUnitprice() {
        return unitprice;
    }

    /**
     * @param unitprice the unitprice to set
     */
    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

}
