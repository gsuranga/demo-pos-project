/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import models.Employee;

/**
 *
 * @author Iresha Lakmali
 */
public class EmployeeController {

    public static void loadAllEmployee(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select * from tbl_employee where status = 1";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[6];
        int rowCount = dtm.getRowCount();
        if (rowCount > 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }

        while (rst.next()) {

            for (int i = 0; i < 6; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);
        }

    }

    public static int addNewEmployee(Employee employee) throws ClassNotFoundException, SQLException {

        String query = "insert into tbl_employee (employee_name,employee_code,contact_no_1,contact_no_2,address,registered_date,registered_time,status) values ('" + employee.getEmployeeName() + "','" + employee.getEmployeeCode() + "','" + employee.getEmployeeContactNo1() + "','" + employee.getEmployeeContactNo2() + "','" + employee.getEmployeeAddress() + "','" + employee.getRegistered_date() + "','" + employee.getRegistered_time() + "','" + employee.getStatus() + "')";
        int value = DBHandler.setData(query);
        return value;
    }

    public static int updateEmployee(Employee employee) throws ClassNotFoundException, SQLException {
        String query = "update tbl_employee set  employee_name = '" + employee.getEmployeeName() + "', employee_code = '" + employee.getEmployeeCode() + "',contact_no_1= '" + employee.getEmployeeContactNo1() + "',contact_no_2='" + employee.getEmployeeContactNo2() + "',address = '" + employee.getEmployeeAddress() + "' where  employee_id = '" + employee.getEmployeeId() + "'";
        int value = DBHandler.setData(query);
        return value;
    }

    public static int deleteEmployee(Employee employee) throws ClassNotFoundException, SQLException {
        String sql = "update tbl_employee set status = '0' where employee_id = '" + employee.getEmployeeId() + "'";
        int rst = DBHandler.setData(sql);
        return rst;
    }

    public static void fillEmployeeNames(JComboBox cmb) throws ClassNotFoundException, SQLException {
        String query = "select employee_name,employee_id from tbl_employee where status = 1";
        ResultSet rstItem = DBHandler.getData(query);
        cmb.removeAllItems();
        while (rstItem.next()) {
            cmb.addItem(rstItem.getString(1));
        }
    }
    
        /*
//  connect to empid for tbl_counter table  
      public static Map<String,Integer> fillEmployeeNames(JComboBox cmb) throws ClassNotFoundException, SQLException {
        String query = "select employee_name,employee_id from tbl_employee where status = 1";
        Map<String,Integer> nameAndId = new HashMap<>();
        ResultSet rstItem = DBHandler.getData(query);
         cmb.removeAllItems();
        while (rstItem.next()) {
            nameAndId.put(rstItem.getString(1), rstItem.getInt(2));
            rstItem.getInt(2);
             cmb.addItem(rstItem.getString(1));
           
        }
        return nameAndId;
    }*/
      //sels comii
      public static int getEmpIDforName(String employeeName) throws ClassNotFoundException, SQLException {

        String query = "select employeeid from tbl_employee where employee_name ='" + employeeName + "'";
        ResultSet rst = DBHandler.getData(query);
        int employeeId = 0;
        while (rst.next()) {
            employeeId = rst.getInt(1);
        }
        return employeeId;
    }
      
     //sels comi
      public static ResultSet loadAllEmployeeComi() throws ClassNotFoundException, SQLException {
        String query = "select employee_id,employee_name from tbl_employee where status=1 order by employee_id";
        ResultSet rst = DBHandler.getData(query);
        return rst;

    }
      //Sales comi
      public static void fillEmpolyeeNamesComi(JComboBox cmb) throws ClassNotFoundException, SQLException {
         ResultSet rstEmployee = EmployeeController.loadAllEmployeeComi();
        cmb.removeAllItems();
        while (rstEmployee.next()) {
             Employee employee=new Employee();
             cmb.addItem(rstEmployee.getString(2));
            
           
        }
    }
      
      
    public static ResultSet getEmployeeDetails(String where, String value) throws ClassNotFoundException, SQLException {
        String sql = "select * from tbl_employee where " + where + " = '" + value + "' and status = 1";
        ResultSet rst = DBHandler.getData(sql);
        return rst;
    }

}
