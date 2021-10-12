/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import connections.DBConnection;
import connections.DBHandler;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import javax.swing.table.DefaultTableModel;
import models.DealerReturn;
import models.DealerReturnDetail;
import models.Item;
import models.RepReturnDetail;
import models.ReturnAdmin;
import models.ReturnAdminDetail;
import models.ReturnRep;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utilities_new.JSonJava;

/**
 *
 * @author SHDINESH
 */
public class DealerReturnController {

    public static int addNewDealerReturn(DealerReturn dealerReturn) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "insert into tbl_dealer_return (deliver_order_id,ret_date,ret_time,status)values(?,?,?,?)";
        Object[] row = {dealerReturn.getDeliverOrderID(), dealerReturn.getReturnDate(), dealerReturn.getReturnTime(), dealerReturn.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, connection, sql);
        return setData;
    }

    public static int getLastInsertedReturnID() throws ClassNotFoundException, SQLException {
        String sql = "select last_insert_id() as last_id from tbl_dealer_return";
        ResultSet data = DBHandler.getData(sql);
        data.next();
        int id = data.getInt("last_id");
        return id;
    }

    public static int addNewDealerReturnDetail(DealerReturnDetail returnDetail) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "insert into tbl_dealer_return_detail (ret_id,item_id,ret_qty,ret_reason,delivered,unit_price_with_vat,status)values(?,?,?,?,?,?,?)";
        Object[] row = {returnDetail.getReturnID(), returnDetail.getItemID(), returnDetail.getReturnQty(), returnDetail.getReturnReason(), returnDetail.getDelivered(), returnDetail.getUnitPriceWithVat(), returnDetail.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, connection, sql);
        return setData;
    }

    public static int updateDealerReturn(DealerReturn dReturn) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "update tbl_dealer_return set admin_ret_id = ? , time_stamp = ? where dealer_return_id = ?";
        Object[] row = {dReturn.getAdmintReturnID(), dReturn.getTimeStamp(), dReturn.getReturnID()};
        int setData = DBHandler.setDataToDataBase(row, connection, sql);
        return setData;

    }

    public static int addNewRepReturn(ReturnRep returnRep) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
//         String sql = "insert into tbl_return_rep (dealer_return_id,accepted_date,accepted_time,rep,time_stamp,status)values(?,?,?,?,?,?)";
//         Object[] row = {returnRep.getDealerReturnId(), returnRep.getAcceptedDate(), returnRep.getAcceptedTime(), returnRep.getRep(), returnRep.getTimeStamp(), returnRep.getStatus()};
        String sql = "insert into tbl_return_rep (dealer_return_id,accepted_date,accepted_time,rep,time_stamp,status,return_note_no)values(?,?,?,?,?,?,?)";
        Object[] row = {returnRep.getDealerReturnId(), returnRep.getAcceptedDate(), returnRep.getAcceptedTime(), returnRep.getRep(), returnRep.getTimeStamp(), returnRep.getStatus(),returnRep.getReturn_note_no()};
        int setData = DBHandler.setDataToDataBase(row, connection, sql);
        return setData;
    }

    public static int addNewRepReturnDetail(RepReturnDetail repReturnDetail) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "insert into tbl_return_rep_detail (return_rep_id,item_id,rep_accepted_qty,rep_remarks,status)values(?,?,?,?,?)";
        Object[] row = {repReturnDetail.getReturnRepID(), repReturnDetail.getItemID(), repReturnDetail.getRepAcceptedQty(), repReturnDetail.getRepRemarks(), repReturnDetail.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, connection, sql);
        return setData;
    }

    public static int addNewAdminReturn(ReturnAdmin returnAdmin) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "insert into tbl_return_admin (dealer_return_id,return_note_no,accepted_date,accepted_time,time_stamp,status)values(?,?,?,?,?,?)";
        Object[] row = {returnAdmin.getDealerReturnID(), returnAdmin.getReturnNoteNo(), returnAdmin.getAcceptedDate(), returnAdmin.getAcceptedTime(), returnAdmin.getTimeStamp(), returnAdmin.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, connection, sql);
        return setData;
    }

    public static int addNewAdminReturnDetail(ReturnAdminDetail raDetail) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "insert into tbl_return_admin_detail (return_admin_id,item_id,admin_accepted_qty,admin_remarks,status)values(?,?,?,?,?)";
        Object[] row = {raDetail.getReturnAdminID(), raDetail.getItemID(), raDetail.getAdminAcceptedQty(), raDetail.getAdminRemarks(), raDetail.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, connection, sql);
        return setData;
    }

    public static ArrayList getDealerReturnDetails(int dealerReturnID) throws ClassNotFoundException, SQLException, JSONException {

        String sql = "select dr.dealer_return_id, i.item_part_no, dr.return_qty,dr.dealer_return_reason,dr.delivered,dr.Status from tbl_dealer_return_detail as dr inner join item as i on dr.item_id = i.ItemID where dr.dealer_return_id = " + dealerReturnID + " and dr.status=1";

        ResultSet rst = DBHandler.getData(sql);
        ArrayList jsonArray = new ArrayList();
        JSONObject jSONObject = null;
        while (rst.next()) {
            jSONObject = new JSONObject();
            //jSONObject.putOpt("dealer_return_id", rst.getInt(1));
            jSONObject.put("item_part_no", rst.getString(2));
            jSONObject.put("return_qty", rst.getDouble(3));
            jSONObject.put("dealer_return_reason", rst.getString(4));
            jSONObject.put("delivered", rst.getString(5));
            jsonArray.add(jSONObject);
        }
        return jsonArray;

    }

//    public static int saveAdminReturnID(DealerReturn dealerReturn) throws ClassNotFoundException, SQLException {
//        String sql = "update tbl_dealer_return set admin_return_id=" + dealerReturn.getAdminReturnId() + " ,status=" + dealerReturn.getStatus() + " where dealer_return_id=" + dealerReturn.getDealerReturnID();
//        int value = DBHandler.setData(sql);
//        return value;
//
//    }
    public static ResultSet getAllUnAssignedReturnsToSend() throws ClassNotFoundException, SQLException {
        String sql = "select *  from tbl_dealer_return where status=2";
        ResultSet rst = DBHandler.getData(sql);
        return rst;

    }

    public static void loadAllCompletedReturns(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
        String sql = "select sup.SupplierName,do.*,dr.* from tbl_dealer_return dr inner join deliverorder do on dr.deliver_order_id = do.deliver_order_id inner join supplier sup on dr.supplier_id = sup.SupplierID where dr.completed_status = 1 and dr.status=1 order by SupplierName";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[17];
        int rowCount = dtm.getRowCount();
        if (rowCount > 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }

        while (rst.next()) {

            for (int i = 0; i < 17; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);
        }
    }

    public static void loadAllNotSentDealerReturns(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
        String sql = "select sup.SupplierName,do.*,dr.* from tbl_dealer_return dr inner join deliverorder do on dr.deliver_order_id = do.deliver_order_id inner join supplier sup on dr.supplier_id = sup.SupplierID where dr.status = 2 order by SupplierName";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[17];
        int rowCount = dtm.getRowCount();
        if (rowCount > 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }

        while (rst.next()) {

            for (int i = 0; i < 17; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);
        }
    }

    public static String getLastRepReturnTimeStamp() throws ClassNotFoundException, SQLException {
        String sql = "select (max(time_stamp)) as time_stamp from tbl_return_rep where status = 1";
        ResultSet rst = DBHandler.getData(sql);
        String timeStamp = null;
        rst.next();
        timeStamp = rst.getString(1);
        if (timeStamp == null || timeStamp == "null") {
            timeStamp = "0";
        } else {

        }

        return timeStamp;
    }

    public static String getLastAdminReturnTimeStamp() throws ClassNotFoundException, SQLException {
        String sql = "select (max(time_stamp)) as time_stamp from tbl_return_admin where status = 1";
        ResultSet rst = DBHandler.getData(sql);
        String timeStamp = null;
        rst.next();
        timeStamp = rst.getString(1);
        if (timeStamp == null || timeStamp == "null") {
            timeStamp = "0";
        } else {

        }

        return timeStamp;
    }

    public static int getLastInsertedRepRetID() throws ClassNotFoundException, SQLException {
        String sql = "select max(return_rep_id) as last_id from tbl_return_rep where status = 1";
        ResultSet data = DBHandler.getData(sql);
        data.next();
        int id = data.getInt("last_id");
        return id;
    }

    public static int getLastInsertedAdminRetID() throws ClassNotFoundException, SQLException {
        String sql = "select max(return_admin_id) as last_id from tbl_return_admin where status = 1";
        ResultSet data = DBHandler.getData(sql);
        data.next();
        int id = data.getInt("last_id");
        return id;
    }

    public static int getAllRepAcceptedReturns() {
        int status = 0;
        JSONObject orderIDObject = new JSONObject();
        DecimalFormat df = new DecimalFormat("#.00");
        df.setMinimumFractionDigits(2);

        try {
            ResultSet rst = ProfileController.getDealerDetails();
            String timestamp = getLastRepReturnTimeStamp();
            //System.out.println("time" + timestamp);
            rst.next();
            String accountNo = rst.getString(3);
            String serverURL = rst.getString(9);
            orderIDObject.put("acc_no", accountNo);
            orderIDObject.put("time_stamp", timestamp);
            JSONArray ja = new JSONArray();

            ja.put(orderIDObject);
            String replaceAll = orderIDObject.toString().replaceAll("\\s", "%20");
            String replace = replaceAll.replaceAll("\\n", "%30");
            String url = serverURL + "dimo_lanka/pos_services/getRepAcceptedReturns";
            
            
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            System.out.println(url + "?dealer_data=" + orderIDObject);
            JSONObject returnObj = new JSonJava().postJSONObject(orderIDObject, url, "dealer_data");
            status = returnObj.getInt("status");

           // System.out.println(status);
//
            if (status == 1) {
                HashSet<Integer> hs = new HashSet<Integer>();
                int repRetID = 0;
                JSONObject jsa = returnObj.getJSONObject("rep_accepted");
                //System.out.println(jsa.length());
                for (int i = 0; i < jsa.length(); i++) {
                    JSONObject jsonObject = jsa.getJSONObject(i + "");
                    int posRetID = jsonObject.getInt("dealer_pos_ret_id");
                    String partNo = jsonObject.getString("item_part_no").trim();
                    String acceptedDate = jsonObject.getString("accepted_date").trim();
                    String acceptedTime = jsonObject.getString("accepted_time").trim();
                    String timeStamp = jsonObject.getString("time_stamp").trim();
                    double acceptedQty = jsonObject.getDouble("accepted_qty");
                    String remarks = jsonObject.getString("remarks");
                    String desc = jsonObject.getString("description").trim();
                    String rep = jsonObject.getString("sales_officer_name").trim();
                   String retNoteNo = jsonObject.getString("return_note_no").trim(); ///1
                    int partID = ItemController.getItemIDforItemName(partNo);
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat();
                    sdf.applyPattern("yyyy-MM-dd");
                    String addedDate = sdf.format(date);
                    sdf.applyPattern("HH:mm:ss");
                    String addedTime = sdf.format(date);
                    // System.out.println(partNo);

                    if (!hs.contains(posRetID)) {
                        hs.add(posRetID);
                        ReturnRep returnRep = new ReturnRep();
                        returnRep.setDealerReturnId(posRetID);
                        returnRep.setAcceptedDate(acceptedDate);
                        returnRep.setAcceptedTime(acceptedTime);
                        returnRep.setRep(rep);
                        returnRep.setTimeStamp(timeStamp);
                        returnRep.setStatus(1);
                       returnRep.setReturn_note_no(retNoteNo); //2
                        repRetID = DealerReturnController.addNewRepReturn(returnRep);

                        int lastID = DealerReturnController.getLastInsertedRepRetID();
                        //System.out.println("last id " + lastID);
                        if (repRetID > 0) {
                            RepReturnDetail rrDetail = new RepReturnDetail();
                            rrDetail.setReturnRepID(lastID);
                            if (partID == 0) {
                                Item item = new Item(1, partNo, 0, 0, addedDate, null, desc, "-",0, 0, 0, 1, 0, 1, "0");
                                try {
                                    ItemController.addNewItem(item);
                                } catch (MySQLIntegrityConstraintViolationException micveException) {
                                    ItemController.updateItemStatus(item);
                                } catch (SQLException sQLException) {
                                }
                                partID = ItemController.getItemIDforItemName(partNo.trim());

                            }
                            rrDetail.setItemID(partID);
                            rrDetail.setRepAcceptedQty(acceptedQty);
                            rrDetail.setRepRemarks(remarks);
                            rrDetail.setStatus(1);
                            DealerReturnController.addNewRepReturnDetail(rrDetail);
                        }
                    } else {
                        int lastID = DealerReturnController.getLastInsertedRepRetID();
                        if (repRetID > 0) {
                            RepReturnDetail rrDetail = new RepReturnDetail();
                            rrDetail.setReturnRepID(lastID);
                            if (partID == 0) {
                                Item item = new Item(1, partNo, 0, 0, addedDate, null, desc, "-",0, 0, 0, 1, 0, 1, "0");
                                try {
                                    ItemController.addNewItem(item);
                                } catch (MySQLIntegrityConstraintViolationException micveException) {
                                    ItemController.updateItemStatus(item);
                                } catch (SQLException sQLException) {
                                }
                                partID = ItemController.getItemIDforItemName(partNo.trim());

                            }
                            rrDetail.setItemID(partID);
                            rrDetail.setRepAcceptedQty(acceptedQty);
                            rrDetail.setRepRemarks(remarks);
                            rrDetail.setStatus(1);
                            DealerReturnController.addNewRepReturnDetail(rrDetail);
                        }
                    }
                }

            } else if (status == 0) {
                // JOptionPane.showMessageDialog(null, "No new deliver orders found", "Status", JOptionPane.WARNING_MESSAGE);
            }
        } catch (JSONException ex) {
            //JOptionPane.showMessageDialog(null, "Error in response", "Retry", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }

        return status;
    }

    public static int getAllAdminAcceptedReturns() {
        int status = 0;
        JSONObject orderIDObject = new JSONObject();
        DecimalFormat df = new DecimalFormat("#.00");
        df.setMinimumFractionDigits(2);

        try {
            ResultSet rst = ProfileController.getDealerDetails();
            String timestamp = getLastAdminReturnTimeStamp();
            //System.out.println("time" + timestamp);
            rst.next();
            String accountNo = rst.getString(3);
            String serverURL = rst.getString(9);
            orderIDObject.put("acc_no", accountNo);
            orderIDObject.put("time_stamp", timestamp);
            JSONArray ja = new JSONArray();

            ja.put(orderIDObject);
            String replaceAll = orderIDObject.toString().replaceAll("\\s", "%20");
            String replace = replaceAll.replaceAll("\\n", "%30");
            String url = serverURL + "dimo_lanka/pos_services/getAdminAcceptedReturns";
            //System.out.println(url + "?dealer_data=" + orderIDObject);
            JSONObject returnObj = new JSonJava().postJSONObject(orderIDObject, url, "dealer_data");
            status = returnObj.getInt("status");

            //System.out.println(returnObj);
//
            if (status == 1) {
                HashSet<Integer> hs = new HashSet<Integer>();
                int adminRetID = 0;
                JSONObject jsa = returnObj.getJSONObject("admin_accepted");
                //System.out.println(jsa.length());
                for (int i = 0; i < jsa.length(); i++) {
                    JSONObject jsonObject = jsa.getJSONObject(i + "");
                    int posRetID = jsonObject.getInt("dealer_pos_ret_id");
                    String partNo = jsonObject.getString("item_part_no").trim();
                    String acceptedDate = jsonObject.getString("accepted_date").trim();
                    String acceptedTime = jsonObject.getString("accepted_time").trim();
                    String timeStamp = jsonObject.getString("time_stamp").trim();
                    double acceptedQty = jsonObject.getDouble("admin_accepted_qty");
                    String remarks = jsonObject.getString("admin_remarks");
                    String desc = jsonObject.getString("description").trim();
                    String retNoteNo = jsonObject.getString("return_note_no").trim();
                    int partID = ItemController.getItemIDforItemName(partNo);
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat();
                    sdf.applyPattern("yyyy-MM-dd");
                    String addedDate = sdf.format(date);
                    sdf.applyPattern("HH:mm:ss");
                    String addedTime = sdf.format(date);
                    //System.out.println(partNo);

                    if (!hs.contains(posRetID)) {
                        hs.add(posRetID);
                        ReturnAdmin returnAdmin = new ReturnAdmin();
                        returnAdmin.setAcceptedDate(acceptedDate);
                        returnAdmin.setAcceptedTime(acceptedTime);
                        returnAdmin.setDealerReturnID(posRetID);
                        returnAdmin.setReturnNoteNo(retNoteNo);
                        returnAdmin.setStatus(1);
                        returnAdmin.setTimeStamp(timeStamp);
                        adminRetID = DealerReturnController.addNewAdminReturn(returnAdmin);

                        int lastID = DealerReturnController.getLastInsertedAdminRetID();
                        //System.out.println("last id " + lastID);
                        if (adminRetID > 0) {
                            ReturnAdminDetail raDetail = new ReturnAdminDetail();
                            raDetail.setReturnAdminID(lastID);
                            if (partID == 0) {
                                Item item = new Item(1, partNo, 0, 0, addedDate, null, desc, "-",0, 0, 0, 1, 0, 1, "0");
                                try {
                                    ItemController.addNewItem(item);
                                } catch (MySQLIntegrityConstraintViolationException micveException) {
                                    ItemController.updateItemStatus(item);
                                } catch (SQLException sQLException) {
                                }
                                partID = ItemController.getItemIDforItemName(partNo.trim());

                            }
                            raDetail.setAdminAcceptedQty(acceptedQty);
                            raDetail.setAdminRemarks(remarks);
                            raDetail.setItemID(partID);
                            raDetail.setStatus(1);
                            DealerReturnController.addNewAdminReturnDetail(raDetail);
                        }
                    } else {
                        int lastID = DealerReturnController.getLastInsertedRepRetID();
                        // System.out.println("last id " + lastID);
                        if (adminRetID > 0) {
                            ReturnAdminDetail raDetail = new ReturnAdminDetail();
                            raDetail.setReturnAdminID(lastID);
                            if (partID == 0) {
                                Item item = new Item(1, partNo, 0, 0, addedDate, null, desc, "-",0, 0, 0, 1, 0, 1, "0");
                                try {
                                    ItemController.addNewItem(item);
                                } catch (MySQLIntegrityConstraintViolationException micveException) {
                                    ItemController.updateItemStatus(item);
                                } catch (SQLException sQLException) {
                                }
                                partID = ItemController.getItemIDforItemName(partNo.trim());

                            }
                            raDetail.setAdminAcceptedQty(acceptedQty);
                            raDetail.setAdminRemarks(remarks);
                            raDetail.setItemID(partID);
                            raDetail.setStatus(1);
                            DealerReturnController.addNewAdminReturnDetail(raDetail);
                        }
                    }
                }

            } else if (status == 0) {
                // JOptionPane.showMessageDialog(null, "No new deliver orders found", "Status", JOptionPane.WARNING_MESSAGE);
            }
        } catch (JSONException ex) {
            //JOptionPane.showMessageDialog(null, "Error in response", "Retry", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }

        return status;
    }
//I change This trr.ret_no
    public static ResultSet loadAllDealerReturns() throws ClassNotFoundException, SQLException {
       // String sql = "select  tdr.dealer_return_id, tdr.deliver_order_id, ifnull(tra.return_note_no, 'Pending') as return_note_no, tdr.ret_date, tdr.ret_time, ifnull(trr.accepted_date, 'Pending') as accepted_date_rep, ifnull(trr.accepted_time, 'Pending') as accepted_time_rep, ifnull(trr.rep, 'Pending') as rep, ifnull(tra.accepted_date, 'Pending') as accepted_date_admin, ifnull(tra.accepted_time, 'Pending') as accepted_time_admin, ifnull(trr.return_rep_id, 0) as return_rep_id, ifnull(tra.return_admin_id, 0) as return_admin_id from tbl_dealer_return tdr left join tbl_return_rep trr ON tdr.dealer_return_id = trr.dealer_return_id left join tbl_return_admin tra ON tdr.dealer_return_id = tra.dealer_return_id where tdr.complete_status = 0";
        String sql = "select  tdr.dealer_return_id, tdr.deliver_order_id, ifnull(trr.return_note_no, 'Pending') as return_note_no, tdr.ret_date, tdr.ret_time, ifnull(trr.accepted_date, 'Pending') as accepted_date_rep, ifnull(trr.accepted_time, 'Pending') as accepted_time_rep, ifnull(trr.rep, 'Pending') as rep, ifnull(tra.accepted_date, 'Pending') as accepted_date_admin, ifnull(tra.accepted_time, 'Pending') as accepted_time_admin, ifnull(trr.return_rep_id, 0) as return_rep_id, ifnull(tra.return_admin_id, 0) as return_admin_id from tbl_dealer_return tdr left join tbl_return_rep trr ON tdr.dealer_return_id = trr.dealer_return_id left join tbl_return_admin tra ON tdr.dealer_return_id = tra.dealer_return_id where tdr.complete_status = 0";
        ResultSet rst = DBHandler.getData(sql);
        return rst;
    }
    ////New //////////////////DIMO Reyturns
    public static void lordAllViewDimoReturns(DefaultTableModel dtm, String ViewstartDate, String ViewendDate) throws ClassNotFoundException, SQLException {
        String sql = "select tdrd.dealer_return_detail_id,tdrd.ret_id,ti.itemid,ti.item_part_no,ti.description,round(tdrd.unit_price_with_vat,2)as unit_price_with_vat,tdrd.ret_qty as dealer_ret_qty,tdrd.ret_reason as dealer_ret_reason,tdr.ret_time,tdr.ret_date from  tbl_dealer_return_detail tdrd left join tbl_dealer_return tdr ON tdr.dealer_return_id = tdrd.ret_id and tdr.status = 1 and tdrd.status = 1 left join item ti ON tdrd.item_id = ti.ItemID and ti.status = 1 where tdr.ret_date between '" + ViewstartDate + "' and '" + ViewendDate +"'  order by  tdr.ret_date asc";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[10];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 10; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);

        }
    }
    
   
    public static ResultSet viewAllReturnedParts(int dealerRetID) throws ClassNotFoundException, SQLException {
        String sql = "select   ti.itemid,  ti.item_part_no,  ti.description,  tdrd.ret_qty as dealer_ret_qty,  tdrd.ret_reason as dealer_ret_reason,  tdrd.unit_price_with_vat,  tdrd.delivered,  ifnull(trrd.rep_accepted_qty, 0) as rep_accepted_qty,  ifnull(trrd.rep_remarks, 'Pending') as rep_remarks,  ifnull(trad.admin_accepted_qty, 0) as admin_accepted_qty,  ifnull(trad.admin_remarks, 'Pending') as admin_remarks  from  tbl_dealer_return tdr  inner join  tbl_dealer_return_detail tdrd ON tdr.dealer_return_id = tdrd.ret_id  and tdr.status = 1  and tdrd.status = 1  inner join  item ti ON tdrd.item_id = ti.ItemID  and ti.status = 1  left join  tbl_return_rep trr ON tdr.dealer_return_id = trr.dealer_return_id  and trr.status = 1  left join  tbl_return_rep_detail trrd ON trr.return_rep_id = trrd.return_rep_id  and tdrd.item_id = trrd.item_id  and trrd.status = 1  left join  tbl_return_admin tra ON tdr.dealer_return_id = tra.dealer_return_id  and tra.status = 1  left join  tbl_return_admin_detail trad ON tra.return_admin_id = trad.return_admin_id  and trad.item_id = trrd.item_id  and trad.status = 1  where  tdr.dealer_return_id = " + dealerRetID;
        ResultSet rst = DBHandler.getData(sql);
        return rst;
    }

    public static int updateCompletedStatus(int dealerReturnID) throws ClassNotFoundException, SQLException {
        String sql = "update tbl_dealer_return set complete_status = 1 where dealer_return_id = " + dealerReturnID;
        int rest = DBHandler.setData(sql);
        return rest;
    }
}
