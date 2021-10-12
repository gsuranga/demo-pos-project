/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import models.Suggest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author insaf
 */
public class SuggestController {

    public static ArrayList<Suggest> getSuggestOrder() throws MalformedURLException, IOException, JSONException, ClassNotFoundException, SQLException {

        ResultSet rst = ProfileController.getDealerDetails();
        rst.next();
        String server_url = rst.getString(9);
        String acc_no = rst.getString(3);

        URL url = new URL(server_url + "dimo_lanka/service/posDealerSuggestPurchaseOrder?accountnumber=" + acc_no);
        System.out.println(url);
//        URL url = new URL("http://192.168.1.40/TATA/TATA/service/posDealerSuggestPurchaseOrder?accountnumber=721S0002");
        URLConnection conn = url.openConnection();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line, str = "";
        while ((line = rd.readLine()) != null) {
            str += line;
        }
        rd.close();

        System.out.println(str);
        JSONObject jsonArray = new JSONObject(str);
        JSONArray jsonArray1 = jsonArray.getJSONArray("suggesOrder");

        //--------------------------------------------------------------------
        ArrayList<Suggest> suggestOrderDetail = new ArrayList<>();
        for (int i = 0; i < jsonArray1.length(); i++) {
            Object get = jsonArray1.getJSONObject(i).get("purchase_order_id");
            int order_id = Integer.parseInt(String.valueOf(get));

            String sales_officer = (String) jsonArray1.getJSONObject(i).get("sales_officer_name");

            Object dates = jsonArray1.getJSONObject(i).get("date");
            String date = String.valueOf(dates);
            Object time = jsonArray1.getJSONObject(i).get("time");
            String times = String.valueOf(time);

            Object discount = jsonArray1.getJSONObject(i).get("discount_percentage");
            double discounts = Double.parseDouble(String.valueOf(discount));

            //double amount=(double) jsonArray1.getJSONObject(i).get("amount");
            Suggest suggest = new Suggest();
            suggest.setOrder_no(order_id);
            suggest.setSales_officer_name(sales_officer);
            suggest.setDate(date);
            suggest.setTime(times);
            suggest.setDiscount_percentage(discounts);

            suggestOrderDetail.add(suggest);

        }

        return suggestOrderDetail;

    }

    public static Suggest getSuggestOrderItems(int pur_order, DefaultTableModel dtm, JLabel totalLabel) throws MalformedURLException, IOException, JSONException, ClassNotFoundException, SQLException {
        ResultSet rst = ProfileController.getDealerDetails();
        rst.next();
        String server_url = rst.getString("server_url");
        URL url = new URL(server_url + "dimo_lanka/service/getItemForSuggestOrder?pur_order_id=" + pur_order);
//        URL url = new URL("http://192.168.1.40/TATA/TATA/service/getItemForSuggestOrder?pur_order_id=" + pur_order);
        URLConnection conn = url.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line2, str2 = "";
        while ((line2 = rd.readLine()) != null) {
            str2 += line2;
        }
        System.out.println(str2);
        rd.close();

        JSONObject jsonArray = new JSONObject(str2);
        JSONArray jsonArray1 = jsonArray.getJSONArray("itemDetail");
        double to = 0;
        for (int i = 0; i < jsonArray1.length(); i++) {
            String part_nu = String.valueOf(jsonArray1.getJSONObject(i).get("item_part_no"));
            String description = String.valueOf(jsonArray1.getJSONObject(i).get("description"));

            String s_item = String.valueOf(jsonArray1.getJSONObject(i).get("item_qty"));
            int item = Integer.parseInt(s_item);

            String s_unit_price = String.valueOf(jsonArray1.getJSONObject(i).get("unit_price"));
            double unit_price = Double.parseDouble(s_unit_price);
            double amo = item * unit_price;
            to += amo;
            Object row[] = {0, part_nu, description, item, unit_price, amo};
            dtm.addRow(row);

        }
        // totalLabel.setText(String.valueOf(to));
        Suggest suggest = new Suggest();
        return null;

    }

    public static int acceptPurchaseOrder(int pur_order) throws MalformedURLException, IOException, ClassNotFoundException, SQLException {
        ResultSet rst = ProfileController.getDealerDetails();
        rst.next();
        String server_url = rst.getString("server_url");
        URL url = new URL(server_url + "dimo_lanka/service/acceptsuggestPurchaseOrder?pur_order_id=" + pur_order);
//        URL url = new URL("http://192.168.1.40/TATA/TATA/service/acceptsuggestPurchaseOrder?pur_order_id=" + pur_order);
        URLConnection uRLConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRLConnection.getInputStream()));
        String line3, str3 = "";
        while ((line3 = bufferedReader.readLine()) != null) {
            str3 += line3;

        }
        bufferedReader.close();
        int result = Integer.parseInt(str3);

        return result;

    }

    public static void set_part_to_table(int type, DefaultTableModel dtm, String letter) throws ClassNotFoundException, SQLException {
        String sql = "";
        if (type == 1) {
            sql = "SELECT `item_part_no`,`Description`,`buyingprice` FROM `item` WHERE `BrandID`=1 AND item_part_no LIKE '" + letter + "%'";
        } else {
            sql = "SELECT `item_part_no`,`Description`,`buyingprice` FROM `item` WHERE `BrandID`=1 AND Description LIKE '" + letter + "%'";
        }
       // System.out.println(sql);
        ResultSet rst = DBHandler.getData(sql);

        while (rst.next()) {
           // System.out.println(rst.getString("item_part_no"));
            Object row[] = {rst.getString("item_part_no"), rst.getString("Description"), rst.getString("buyingprice")};
            dtm.addRow(row);

        }
    }

    public static String get_vat() throws MalformedURLException, IOException, JSONException, ClassNotFoundException, SQLException {
        ResultSet rst = ProfileController.getDealerDetails();
        rst.next();
        String server_url = rst.getString("server_url");
        URL url = new URL(server_url + "dimo_lanka/service/get_vat_for_pos");
//        URL url = new URL("http://192.168.1.40/TATA/TATA/service/posDealerSuggestPurchaseOrder?accountnumber=721S0002");
        URLConnection conn = url.openConnection();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line, str = "";
        while ((line = rd.readLine()) != null) {
            str += line;
        }
        rd.close();

        // System.out.println(str);
        JSONObject jsonArray = new JSONObject(str);
        JSONArray jsonArray1 = jsonArray.getJSONArray("vat");
        Object get = jsonArray1.getJSONObject(0).get("amount");
        String vat = String.valueOf(get);
        System.out.println("vat" + get);

        //--------------------------------------------------------------------
//        ArrayList<Suggest> suggestOrderDetail = new ArrayList<>();
//        for (int i = 0; i < jsonArray1.length(); i++) {
//            Object get = jsonArray1.getJSONObject(i).get("purchase_order_id");
//            int order_id = Integer.parseInt(String.valueOf(get));
//
//            String sales_officer = (String) jsonArray1.getJSONObject(i).get("sales_officer_name");
//
//            Object dates = jsonArray1.getJSONObject(i).get("date");
//            String date=String.valueOf(dates);
//            Object time = jsonArray1.getJSONObject(i).get("time");
//            String times=String.valueOf(time);
//            
//            Object discount = jsonArray1.getJSONObject(i).get("discount_percentage");
//            double discounts=Double.parseDouble(String.valueOf(discount)); 
//            
//                     
//            //double amount=(double) jsonArray1.getJSONObject(i).get("amount");
//            Suggest suggest = new Suggest();
//            suggest.setOrder_no(order_id);
//            suggest.setSales_officer_name(sales_officer);
//            suggest.setDate(date);
//            suggest.setTime(times);
//            suggest.setDiscount_percentage(discounts);
//
//            suggestOrderDetail.add(suggest);
//
//        }
        return vat;
    }

    public static String accept_purchase_order(int pur_order, String json, double tawd_double, double tawv_double) throws MalformedURLException, IOException, ClassNotFoundException, SQLException {
        ResultSet rst = ProfileController.getDealerDetails();
        rst.next();
        String server_url = rst.getString("server_url");
        URL url = new URL(server_url + "dimo_lanka/service/accept_purchase_order_by_dealer?pur_order_id=" + pur_order + "&items=" + json + "&tawd_double=" + tawd_double + "&tawv_double=" + tawv_double);
        URLConnection conn = url.openConnection();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line, str = "";
        while ((line = rd.readLine()) != null) {
            str += line;
        }
        rd.close();

        return str;

    }

    public static String reject_pur_order(int pur_order, String reason) throws MalformedURLException, IOException, ClassNotFoundException, SQLException {
        ResultSet rst = ProfileController.getDealerDetails();
        rst.next();
        String server_url = rst.getString("server_url");
        URL url = new URL(server_url + "dimo_lanka/service/reject_fun?pur_order_id=" + pur_order + "&reason=" + reason);
        URLConnection conn = url.openConnection();

        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line, str = "";
        while ((line = rd.readLine()) != null) {
            str += line;
        }
        rd.close();

        return str;
    }

}
