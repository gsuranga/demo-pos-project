/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBConnection;
import connections.DBHandler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import models.Vehicle;

/**
 *
 * @author insaf
 */
public class VehicleController {

    public static int addNewVehicle(Vehicle vehicle) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

        String query = "insert into customer_vehicles (customer_id,vehicle_reg_no,vehicle_model,added_date,added_time,status) values (?,?,?,?,?,?)";
        Object[] row = {vehicle.getCustomerID(), vehicle.getVehicleRegNo(), vehicle.getVehicleModelID(), vehicle.getAddedDate(), vehicle.getAddedTime(), vehicle.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }

    public static int updateVehicle(Vehicle vehicle) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String sql = "update customer_vehicles set customer_id = ?, vehicle_reg_no = ?, vehicle_model = ? where status = 1 and vehicle_id = ?";
        Object row[] = {vehicle.getCustomerID(), vehicle.getVehicleRegNo(), vehicle.getVehicleModelID(), vehicle.getVehicleID()};
        int value = DBHandler.setDataToDataBase(row, con, sql);
        return value;
    }

    public static void loadAllCustomerVehicles(DefaultTableModel dtm, int customerID) throws ClassNotFoundException, SQLException {

        String sql = "select vehicle_id,vehicle_reg_no from customer_vehicles where customer_id = '" + customerID + "' and status = '1'";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[2];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }

        while (rst.next()) {
            for (int i = 0; i < 2; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);

        }

    }

    public static ResultSet getAllVehicleDetails() throws ClassNotFoundException, SQLException {
        String sql = "select * from customer_vehicles where status = 1";
        ResultSet data = DBHandler.getData(sql);
        return data;

    }

    public static int deleteVehicle(Vehicle vehicle) throws ClassNotFoundException, SQLException {
        String sql = "update customer_vehicles set status = '0' where vehicle_id = " + vehicle.getVehicleID() + "";
        System.out.println("" + sql);
        int rst = DBHandler.setData(sql);
        return rst;
    }

    public static ResultSet getVehicleDetails(String where, String value) throws ClassNotFoundException, SQLException {
        String sql = "select * from customer_vehicles where " + where + " = '" + value + "' and status=1";
        ResultSet rst = DBHandler.getData(sql);

        return rst;
    }

    public static void viewAllVehicle(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select cv.vehicle_id, cv.vehicle_model, c.id, cv.vehicle_reg_no, tvm.vehicle_model_name, c.customer_name, cv.added_date from customer_vehicles cv left JOIN customer c ON cv.customer_id = c.id left JOIN tbl_vehicle_models tvm ON tvm.vehicle_model_id = cv.vehicle_model where cv.status = 1";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[7];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }

        while (rst.next()) {
            for (int i = 0; i < 7; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);

        }

    }

}
