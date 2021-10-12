/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBConnection;
import connections.DBHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import models.Debt;
import models.DebtDetail;

/**
 *
 * @author user
 */
public class DebtController {

    public static int addNewDebt(Debt debt) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "insert into debt (custid,vehicle_id,salesorderid,debtdate,debtenddate,debtamount,status)values(?,?,?,?,?,?,?)";
        Object[] row = {debt.getCustID(), debt.getVehicleID(), debt.getSalesOrderID(), debt.getDebtDate(), debt.getEndDate(), debt.getDebtAmount(), debt.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, connection, sql);
        return setData;
    }



    /*
    public static int addNewDebtDetail(DebtDetail debtDetail) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "insert into debtDetail (debtid,paymentdate,paidamount,status) values (?,?,?,?)";
        Object[] row = {debtDetail.getDebtID(), debtDetail.getPaymentDate(), debtDetail.getPaidAmont(), debtDetail.getStatus()};
        int value = DBHandler.setDataToDataBase(row, connection, sql);
        return value;
    }
*/
    public static double getPendingAmount(int debtID) throws ClassNotFoundException, SQLException{
        String query = "select (debtamount -  (select coalesce(sum(paidamount),0) from debtdetail where debtid=" + debtID + " and status = 1)) as pending from debt where debtid = " + debtID + " and status = 2";
        ResultSet data = DBHandler.getData(query);
        double remining = 0;
        if (data.next()) {
            remining = data.getDouble(1);

        } else {
            remining = 0;
        }
        return remining;
        
        
}
    public static int addNewDebtDetail(DebtDetail debtDetail) throws ClassNotFoundException, SQLException {

        Connection connection = DBConnection.getConnection();
        String sql = "insert into debtDetail (debtid,paymentdate,paidamount,status) values (?,?,?,?)";
        Object[] row = {debtDetail.getDebtID(), debtDetail.getPaymentDate(), debtDetail.getPaidAmont(), debtDetail.getStatus()};
        int value = DBHandler.setDataToDataBase(row, connection, sql);
        return value;
    }
    public static void loadAllPendingDebts(DefaultTableModel dtm, int custID, int vID) throws ClassNotFoundException, SQLException {
        String sql = "select db.debtid, so.sales_order_no, db.debtdate, db.debtenddate, round(db.debtamount, 2) debtamount, round(coalesce((select db.debtamount - (select sum(paidamount) from debtdetail where debtid = db.debtid and status = 1 group by db.debtid) ), db.debtamount), 2) as pending,so.garge_commission from debt db inner join salesorder so ON db.salesorderid = so.OrderID where db.custid = " + custID + " and db.vehicle_id = " + vID + " and db.status = 2";
        //old "select db.debtid,so.sales_order_no,db.debtdate,db.debtenddate,round(db.debtamount,2)debtamount,round(coalesce((select db.debtamount - (select sum(paidamount) from debtdetail where debtid = db.debtid and status = 1 group by db.debtid)), db.debtamount),2) as pending, db.status from debt db inner join customer c ON db.custid = c.id inner join salesorder so ON db.salesorderid = so.OrderID where db.status = 2 and db.custid = " + custID + " order by c.customer_name";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[7];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 7; i++) {

                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);
        }

    }

    public static void loadAllDebtDetails(DefaultTableModel debtDetailmodel, int debtID) throws SQLException, ClassNotFoundException {
        String query = "select detailID, paymentdate, round(paidamount, 2) as paidamount from debtDetail where debtid = " + debtID + " and status = 1";
        ResultSet rst = DBHandler.getData(query);
        int rowCount = debtDetailmodel.getRowCount();
        Object tableRow[] = new Object[3];
        if (rowCount >= 0) {
            debtDetailmodel.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 3; i++) {
                tableRow[i] = rst.getString(i + 1);
            }
            debtDetailmodel.addRow(tableRow);
        }

    }

    public static int updateDebtEndDate(Debt debt) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "update debt set debtenddate =? where debtid=?";
        Object ob[] = {debt.getEndDate(), debt.getDebtID()};
        int value = DBHandler.setDataToDataBase(ob, con, query);
        return value;
    }

    public static double checkPendingAmount(int debtID) throws ClassNotFoundException, SQLException {
        String query = "select (debtamount -  (select coalesce(sum(paidamount),0) from debtdetail where debtid=" + debtID + " and status = 1)) as pending from debt where debtid = " + debtID + " and status = 2";
        ResultSet data = DBHandler.getData(query);
        double remining = 0;
        if (data.next()) {
            remining = data.getDouble(1);

        } else {
            remining = 0;
        }
        return remining;
    }

    public static int updateStatus(Debt dt) throws ClassNotFoundException, SQLException {
        String query = "update debt set status = " + dt.getStatus() + " where debtid = " + dt.getDebtID();
        int data = DBHandler.setData(query);
        return data;
    }
  public static int updateStatusCheque1(int status,int debtid) throws ClassNotFoundException, SQLException {
     
        Connection con = DBConnection.getConnection(); 
          String query ="UPDATE `demolanka`.`debt` SET `status` = '"+status+"' WHERE `debt`.`debtid` = '"+ debtid + "'";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        int res = preparedStatement.executeUpdate();

        return res;

    }
    
    
    public static void loadAllPendingDebtDetails(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
        String sql = "select dbt.custid, dbt.vehicle_id, cs.customer_name, cs.customer_acc_no, cv.vehicle_reg_no, round(sum(dbt.debtamount - coalesce((select sum(paidamount) from debtdetail where debtid = dbt.debtid group by dbt.debtid), 0)), 2) as tot from debt dbt left join customer_vehicles cv ON dbt.vehicle_id = cv.vehicle_id left join customer cs ON dbt.custid = cs.id where dbt.status = 2 group by dbt.vehicle_id , dbt.custid";
//"select cus.id, cus.customer_name, cus.customer_acc_no,round(sum(dbt.debtamount-coalesce((select sum(paidamount) from debtdetail where debtid=dbt.debtid group by dbt.debtid),0)),2) as tot from debt dbt inner join customer cus on dbt.custid=cus.id  where  dbt.status=2 group by dbt.custid";
        ResultSet data = DBHandler.getData(sql);
        String tableRow[] = new String[6];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (data.next()) {
            for (int i = 0; i < 6; i++) {

                tableRow[i] = data.getString(i + 1);
            }
            dtm.addRow(tableRow);
        }

    }

    //iresha
    public static void loadAllCompletedPayments(DefaultTableModel dtm, String s) throws ClassNotFoundException, SQLException {
        String sql = "select db.debtid,c.customer_name,so.sales_order_no,db.debtdate,db.debtenddate,round(db.debtamount,2)debtamount, db.status from debt db inner join customer c ON db.custid = c.id inner join salesorder so ON db.salesorderid = so.OrderID where db.status = 1  order by c.customer_name";
        ResultSet data = DBHandler.getData(sql);
        String tableRow[] = new String[6];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (data.next()) {
            for (int i = 0; i < 6; i++) {

                tableRow[i] = data.getString(i + 1);
            }

            dtm.addRow(tableRow);
        }

    }

    public static void loadAllCompletedPaymentsDetails(DefaultTableModel dtm, int id) throws ClassNotFoundException, SQLException {
        String sql = "select dd.paymentdate, round(dd.paidamount, 2) as paidamount, d.debtid from debt d inner join debtdetail dd on d.debtid = dd.debtid where dd.debtid = " + id + " and dd.status = 1";
        ResultSet data = DBHandler.getData(sql);
        String tableRow[] = new String[4];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (data.next()) {
            for (int i = 0; i < 2; i++) {

                tableRow[i] = data.getString(i + 1);
            }

            dtm.addRow(tableRow);
        }

    }

    public static int getOverdueInvoiceCount(int customerID) throws ClassNotFoundException, SQLException {
        String sql = "select count(salesorderid) as inv_count from debt where custid = " + customerID + " and status = 2 and debtenddate < curdate()";
        ResultSet rst = DBHandler.getData(sql);
        rst.next();
        int invCount = rst.getInt(1);
        return invCount;
    }
    ///garage commission

 public static ResultSet getTotalGarageCommission(int supplierID) throws ClassNotFoundException, SQLException {
        String queryPart = "and i.supplier_id = " + supplierID;
        String query = "select i.item_part_no,i.Description,s.SupplierName,cu.customer_name,sod.quantity,so.amount,sod.date,sod.time,so.garge_commission from customer cu inner join salesorder so ON cu.id = so.CustomerID inner join salesorderdetail sod ON so.OrderID = sod.orderid inner join item i ON sod.itemid = i.ItemID inner join supplier s ON i.supplier_id = s.SupplierID where sod.status = 1";
        String fullSql = "";
        if (supplierID == 0) {
            fullSql = query;
        } else {
            fullSql = query + queryPart;
        }
        ResultSet rst = DBHandler.getData(fullSql);
        return rst;
    }
 

}
