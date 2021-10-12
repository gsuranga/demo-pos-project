/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBConnection;
import connections.DBHandler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import models.Service;

/**
 *
 * @author Iresha Lakmali
 */
public class ServiceController {

    public static void loadAllServices(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select service_id, service_code, service_name, service_cost, service_charge, remarks, added_date from tbl_service where status = 1";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[7];
        int rowCount = dtm.getRowCount();
        if (rowCount > 0) {
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
//    public static void loadAllServices2(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
//
//        String sql = "select service_id, service_code,service_name,service_cost,service_charge,remarks,added_date from tbl_service where status=1";
//        ResultSet rst = DBHandler.getData(sql);
//        String tableRow[] = new String[7];
//        int rowCount = dtm.getRowCount();
//        if (rowCount > 0) {
//            for (int i = 0; i < rowCount; i++) {
//                dtm.removeRow(0);
//            }
//        }
//
//        while (rst.next()) {
//
//            for (int i = 0; i < 7; i++) {
//                tableRow[i] = rst.getString(i + 1);
//            }
//
//            dtm.addRow(tableRow);
//        }
//
//    }

    public static int addNewService(Service service) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into tbl_service (service_name,service_cost,service_code,remarks,added_date,added_time,status,service_charge) values (?,?,?,?,?,?,?,?)";
        Object[] row = {service.getServiceName(), service.getServiceCost(), service.getServiceCode(), service.getServiceRemark(), service.getAddedDate(), service.getAddedTime(), service.getStatus(), service.getServicecharge()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }

    public static int updateService(Service service) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "update tbl_service set service_name = ?, service_cost = ?, service_charge = ?, service_code = ?, remarks = ? where status = 1 and service_id = ?";
        Object[] row = {service.getServiceName(), service.getServiceCost(), service.getServicecharge(), service.getServiceCode(), service.getServiceRemark(), service.getServiceId()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }

    public static int deleteService(Service service) throws ClassNotFoundException, SQLException {
        String qurey = "update tbl_service set status = 0 where service_id = " + service.getServiceId();
        int value = DBHandler.setData(qurey);
        return value;
    }

}
