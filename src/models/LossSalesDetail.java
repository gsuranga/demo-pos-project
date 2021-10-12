/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author shdinesh.99
 */
public class LossSalesDetail {

    private int lossSalesDetailID;
    private int partID;
    private double lossQty;
    private int lossType;
    private String timeStamp;
    private String lossDate;
    private int status;

    public LossSalesDetail() {
    }

    public LossSalesDetail(int partID, double lossQty, int lossType, String timeStamp, String lossDate, int status) {
        this.partID = partID;
        this.lossQty = lossQty;
        this.lossType = lossType;
        this.timeStamp = timeStamp;
        this.lossDate = lossDate;
        this.status = status;
    }

}
