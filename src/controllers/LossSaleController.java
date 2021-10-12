/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBConnection;
import connections.DBHandler;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import models.LossSale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utilities_new.JSonJava;

/**
 *
 * @author shdinesh.99
 */
public class LossSaleController {

    public static int addNewLossSale(LossSale lossSale) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into tbl_loss_sales_detail (part_id,loss_qty,loss_type,loss_date,status) values (?,?,?,?,?)";
        Object[] row = {lossSale.getPartID(), lossSale.getLossQty(), lossSale.getLossType(), lossSale.getLossDate(), lossSale.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }

    public static ArrayList loadAllUnAssignedLossSales() throws ClassNotFoundException, SQLException, JSONException {
        String query = "select i.item_part_no, ls.* from tbl_loss_sales_detail ls inner join item i on ls.part_id = i.ItemID where ls.status=2";
        ResultSet rst = DBHandler.getData(query);
        ArrayList jsonArray = new ArrayList();
        JSONObject jSONObject = null;
        while (rst.next()) {
            jSONObject = new JSONObject();
            jSONObject.putOpt("part_no", rst.getString(1));
            jSONObject.put("loss_id", rst.getInt(2));
            jSONObject.put("loss_qty", rst.getDouble(4));
            jSONObject.put("loss_type", rst.getInt(5));
            jSONObject.put("loss_date", rst.getString(6));
            jSONObject.put("status", rst.getInt(7));

            jsonArray.add(jSONObject);
        }
        return jsonArray;
    }

    public static synchronized int sendLossSalesAsJSON(ArrayList arrayList) throws JSONException {
        int status = 0;
        HashSet<Integer> hs = new HashSet<Integer>();
        try {
            ResultSet rst = ProfileController.getDealerDetails();
            rst.next();
            String accountNo = rst.getString(3);
            String serverURL = rst.getString(9);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("delar_account_no", accountNo);

            ArrayList<JSONObject> jsonArrayList = arrayList;
            for (int i = 0; i < jsonArrayList.size(); i++) {
                hs.add((Integer) (jsonArrayList.get(i)).get("loss_id"));
            }
            System.out.println(hs.size() + " size");
            JSONArray jSONArray = new JSONArray(jsonArrayList);
            jSONArray.put(jsonObject);

            String replaceAll = jSONArray.toString().replaceAll("\\s", "%20");
            String replace = replaceAll.replaceAll("\\n", "%30");
            System.out.println(serverURL + "dimo_lanka/pos_services/addLossSaleDetails?loss_sales_args=" + replace);
            JSONObject returnedObject = new JSonJava().readJsonFromUrl(serverURL + "dimo_lanka/pos_services/addLossSaleDetails?loss_sales_args=" + replace);
            status = returnedObject.getInt("status");
            if (status == 1) {
                updateLossSaleStatus(hs);
            }

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            //ex.printStackTrace();
        } catch (MalformedURLException mex) {
            //mex.printStackTrace();
        } catch (IOException ioex) {
        } catch (JSONException ex) {
            //ex.printStackTrace();
        }
        return status;

    }

    public static int updateLossSaleStatus(HashSet<Integer> hs) throws ClassNotFoundException, SQLException {
        int setData = 0;
        Connection con = DBConnection.getConnection();
        Iterator<Integer> iterator = hs.iterator();
        String query = "update tbl_loss_sales_detail set status =1 where loss_sales_detail_id=?";
        for (int i = 0; i < hs.size(); i++) {
            Object[] row = {iterator.next()};
            setData = DBHandler.setDataToDataBase(row, con, query);
        }

        return setData;
    }
}
