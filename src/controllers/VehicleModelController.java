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
import models.VehicleModel;

/**
 *
 * @author shdinesh.99
 */
public class VehicleModelController {

    public static int addNewVehicleModel(VehicleModel vm) throws ClassNotFoundException, SQLException {

        Connection con = DBConnection.getConnection();
        String query = "insert into tbl_vehicle_models (vehicle_model_name,added_date,description,added_time,status) values (?,?,?,?,?)";
        Object[] row = {vm.getModelName(), vm.getAddedDate(), vm.getDescription(), vm.getAddedTime(), vm.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }

    public static void loadAllVehicleModels(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select * from tbl_vehicle_models where status=1";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[4];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }

        while (rst.next()) {
            for (int i = 0; i < 4; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);

        }

    }

    public static int deleteVehicleModel(VehicleModel vModel) throws ClassNotFoundException, SQLException {
        String sql = "update tbl_vehicle_models set status = '0' where vehicle_model_id = " + vModel.getModelID() + "";
        int rst = DBHandler.setData(sql);
        return rst;
    }

    public static ResultSet getModelDetails(String search, String val) throws ClassNotFoundException, SQLException {
        String sql = "select * from tbl_vehicle_models where " + search + "='" + val + "'";
        ResultSet data = DBHandler.getData(sql);
        return data;

    }

    public static int updateVehicleModel(VehicleModel vModel) throws ClassNotFoundException, SQLException {
        String sql = "update tbl_vehicle_models set vehicle_model_name='" + vModel.getModelName() + "', description='" + vModel.getDescription() + "' where vehicle_model_id=" + vModel.getModelID() + " and status=1";
        int data = DBHandler.setData(sql);
        return data;

    }
}
