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
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.table.DefaultTableModel;
import models.CustomerReturn;
import models.CustomerReturnDetail;
import models.ReturnsSales;

/**
 *
 * @author insaf
 */
public class CustomerReturnsController {

    public static ArrayList<ReturnsSales> getSalesForReturn(ReturnsSales returnsSales) throws ClassNotFoundException, SQLException {
        String sql = "select item.ItemID,item.item_part_no,item.Description,salesorderdetail.quantity As qty,salesorder.OrderID,salesorderdetail.amount As unit_price,salesorderdetail.discount from item item RIGHT JOIN salesorderdetail salesorderdetail on item.ItemID=salesorderdetail.itemid  JOIN salesorder salesorder on salesorderdetail.orderid=salesorder.OrderID AND salesorder.sales_order_no='" + returnsSales.getSalesorderid() + "'  and salesorderdetail.status=1";
        ResultSet rst = DBHandler.getData(sql);
        ArrayList<ReturnsSales> returnList = new ArrayList<>();
        while (rst.next()) {
            ReturnsSales returnsSalesdetail = new ReturnsSales(rst.getString("OrderID"), rst.getInt("ItemID"), rst.getString("item_part_no"), rst.getString("Description"), rst.getDouble("unit_price"), rst.getDouble("discount"), rst.getDouble("qty"));
            returnList.add(returnsSalesdetail);
        }
        return returnList;
    }

    public static boolean addCustomerReturnOrder(CustomerReturn customerReturn, ArrayList<CustomerReturnDetail> crd) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();

        boolean checkInsert = false;
        boolean detailcheck = false;
        String sql = "insert into tbl_customer_return(sales_order_id,return_no,return_date,return_time,amount,status,returns) values(?,?,?,?,?,?,?)";
        Object data[] = {customerReturn.getSalesOrderID(), customerReturn.getRetNumber(), customerReturn.getRetDate(), customerReturn.getRetTime(), customerReturn.getRetAmount(), customerReturn.getStatus(),customerReturn.getReturns()};
        int setData = DBHandler.setDataToDataBase(data, con, sql);
        if (setData > 0) {
            String sql2 = "select max(customer_return_id) as cid from tbl_customer_return";
            ResultSet rst = DBHandler.getData(sql2);
            rst.next();
            int tcr = rst.getInt("cid");
            for (CustomerReturnDetail trs : crd) {
                String detailq = "Insert INTO tbl_customer_return_detail(customer_return_id,item_id,return_quantity,unit_price,reason,status) values(?,?,?,?,?,?)";
                Object detailData[] = {tcr, trs.getItemID(), trs.getRetQty(), trs.getUnitPrice(), trs.getRetReason(), trs.getStatus()};
                int setData1 = DBHandler.setDataToDataBase(detailData, con, detailq);
                if (setData1 > 0) {
                    detailcheck = true;
                } else {
                    detailcheck = false;
                    break;
                }

            }
            return detailcheck;
        } else {
            return false;
        }

    }

    public static ArrayList<ReturnsSales> getAllSalessOrder() throws ClassNotFoundException, SQLException {
        String sql = "SELECT sales_order_no FROM salesorder";
        ResultSet data = DBHandler.getData(sql);
        ArrayList<ReturnsSales> returnsArray = new ArrayList<>();
        while (data.next()) {
            ReturnsSales returnsSales = new ReturnsSales(data.getString("sales_order_no"));
            returnsArray.add(returnsSales);

        }
        return returnsArray;
    }

    public static ArrayList<ReturnsSales> getAllCustomer() throws ClassNotFoundException, SQLException {
        String sql = "Select id,customer_name,customer_acc_no FROM customer where status=1";
        ResultSet cu = DBHandler.getData(sql);
        ArrayList<ReturnsSales> salesReturns = new ArrayList<>();
        while (cu.next()) {
            ReturnsSales returnsSales = new ReturnsSales();
            returnsSales.setCus_id(cu.getInt("id"));
            returnsSales.setCus_name(cu.getString("customer_name"));
            returnsSales.setCus_account_nu(cu.getString("customer_acc_no"));
            salesReturns.add(returnsSales);
        }
        return salesReturns;

    }

    public static void getSalesOrderForReturn(DefaultTableModel dtm3) throws ClassNotFoundException, SQLException {
        String sql = "SELECT salesorder.sales_order_no,salesorder.amount,salesorder.OrderID,sum(tbl_customer_return.amount) As Return_Amount,salesorder.paid_amount As cash_amount,(Select sum(debtdetail.paidamount) from debt debt Inner JOIN debtdetail debtdetail ON debt.debtid =debtdetail.debtid where debt.salesorderid=salesorder.OrderID GROUP BY debtdetail.debtid)  AS Deb_paid_amount  FROM salesorder salesorder LEFT JOIN tbl_customer_return tbl_customer_return ON salesorder.OrderID=tbl_customer_return.sales_order_id where tbl_customer_return.status=1  GROUP BY tbl_customer_return.sales_order_id";
        ResultSet resultSet = DBHandler.getData(sql);
        while (resultSet.next()) {

            String salesOrderNo = resultSet.getString("sales_order_no");
            double invoice_Amount = resultSet.getDouble("amount");
            int orderId = resultSet.getInt("OrderID");
            double return_amount = resultSet.getDouble("Return_Amount");
            double cash_amount = resultSet.getDouble("cash_amount");
            double deb_amount = (invoice_Amount) - (cash_amount + resultSet.getDouble("Deb_paid_amount"));
            double new_deb_Amount = 0;
            double new_cash_Amount = 0;

            double new_invoice_amount = invoice_Amount - return_amount;
            if (deb_amount >= return_amount) {
                new_deb_Amount = deb_amount - return_amount;
                new_cash_Amount = cash_amount;
            } else {
                new_deb_Amount = 0;
                double new_return_amount = return_amount - deb_amount;
                new_cash_Amount = cash_amount - new_return_amount;

            }
            Object row[] = {orderId, salesOrderNo, invoice_Amount, return_amount, new_cash_Amount, new_deb_Amount, new_invoice_amount};
            dtm3.addRow(row);
        }
    }

    public static void getSalesOrderbyCustomer(int cus_id, DefaultTableModel dtm4) throws ClassNotFoundException, SQLException {
        String sql = "SELECT salesorder.sales_order_no,salesorder.amount,salesorder.OrderID,sum(tbl_customer_return.amount) As Return_Amount,salesorder.paid_amount As cash_amount,(Select sum(debtdetail.paidamount) from debt debt Inner JOIN debtdetail debtdetail ON debt.debtid =debtdetail.debtid where debt.salesorderid=salesorder.OrderID GROUP BY debtdetail.debtid)  AS Deb_paid_amount  FROM salesorder salesorder LEFT JOIN tbl_customer_return tbl_customer_return ON salesorder.OrderID=tbl_customer_return.sales_order_id where tbl_customer_return.status=1 AND salesorder.CustomerID='" + cus_id + "' GROUP BY tbl_customer_return.sales_order_id";
        ResultSet resultSet = DBHandler.getData(sql);
        while (resultSet.next()) {

            String salesOrderNo = resultSet.getString("sales_order_no");
            double invoice_Amount = resultSet.getDouble("amount");
            int orderId = resultSet.getInt("OrderID");
            double return_amount = resultSet.getDouble("Return_Amount");
            double cash_amount = resultSet.getDouble("cash_amount");
            double deb_amount = (invoice_Amount) - (cash_amount + resultSet.getDouble("Deb_paid_amount"));
            double new_deb_Amount = 0;
            double new_cash_Amount = 0;

            double new_invoice_amount = invoice_Amount - return_amount;
            if (deb_amount >= return_amount) {
                new_deb_Amount = deb_amount - return_amount;
                new_cash_Amount = cash_amount;
            } else {
                new_deb_Amount = 0;
                double new_return_amount = return_amount - deb_amount;
                new_cash_Amount = cash_amount - new_return_amount;

            }
            Object row[] = {orderId, salesOrderNo, invoice_Amount, return_amount, new_cash_Amount, new_deb_Amount, new_invoice_amount};
            dtm4.addRow(row);
        }
    }

    public static void getgetReturnedOrderTerm(JList jList, int order_id, Map map) throws ClassNotFoundException, SQLException {
        String sql = "SELECT customer_return_id,return_date,return_time,amount FROM tbl_customer_return where sales_order_id='" + order_id + "' AND status=1";
        ResultSet resultSet = DBHandler.getData(sql);
        ArrayList<Object> gf = new ArrayList<>();
        int mapid = 0;
        //map=new HashMap();
        while (resultSet.next()) {  
            int cust_return_id = resultSet.getInt("customer_return_id");
            String retu_date = resultSet.getString("return_date");
            String retu_time = resultSet.getString("return_time");
            String data = retu_date + "     " + retu_time;
            gf.add(data);
            map.put(String.valueOf(mapid), String.valueOf(cust_return_id));
            mapid++;
        }
        jList.setListData(gf.toArray());
    }

//    public static void getReturnDetail(int order_id, DefaultTableModel dtm, JLabel totalLabel) throws ClassNotFoundException, SQLException {
//        // String sql="SELECT item.item_part_no,item.Description,item.search_key,tbl_customer_return_detail.return_quantity,tbl_customer_return_detail.unit_price,tbl_customer_return_detail.return_quantity,tbl_customer_return_detail.reason FROM item item LEFT JOIN tbl_customer_return_detail tbl_customer_return_detail ON item.ItemID=tbl_customer_return_detail.item_id where tbl_customer_return_detail.customer_return_id='"+order_id+"' AND tbl_customer_return_detail.status=1";
//        String sql = "SELECT item.item_part_no,item.Description,item.key_id,(SELECT key_name FROM  tbl_search_key where search_key_id=item.key_id)	AS search_key,tbl_customer_return_detail.return_quantity,tbl_customer_return_detail.unit_price,tbl_customer_return_detail.return_quantity,tbl_customer_return_detail.reason FROM item item LEFT JOIN tbl_customer_return_detail tbl_customer_return_detail ON item.ItemID=tbl_customer_return_detail.item_id where tbl_customer_return_detail.customer_return_id='" + order_id + "' AND tbl_customer_return_detail.status=1";
//        ResultSet data = DBHandler.getData(sql);
//        dtm.setRowCount(0);
//        totalLabel.setText("Rs" + " " + "0.00");
//        double tot = 0;
//        while (data.next()) {
//            Object row[] = {data.getString("search_key"), data.getString("item_part_no"), data.getString("Description"), data.getDouble("unit_price"), data.getInt("return_quantity"), data.getString("reason")};
//            dtm.addRow(row);
//            tot += data.getDouble("unit_price") * data.getInt("return_quantity");
//        }
//        totalLabel.setText("Rs" + " " + String.valueOf(tot));
//
//    }
    public static boolean addCustomerReturnOrder(ReturnsSales mainReturnsales, ArrayList<ReturnsSales> subReturn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void loadAllOrderDataToReturn(int orderID, DefaultTableModel retTableMoldel) throws SQLException, ClassNotFoundException {
        // OLD String sql = "Select item.ItemID, item.item_part_no, item.Description, round(salesorderdetail.quantity, 1) as qty, round(salesorderdetail.amount, 2) as amount, round(salesorderdetail.discount_amount, 2) as discount, false, round(salesorderdetail.unit_price, 2) as unit_price from item inner join salesorderdetail ON item.ItemID = salesorderdetail.ItemID where salesorderdetail.status = 1 and orderid = " + orderID;
        //String sql = "Select item.ItemID, item.item_part_no, item.Description, round(salesorderdetail.quantity, 1) as qty, round(salesorderdetail.amount, 2) as amount, round(salesorderdetail.discount_amount, 2) as discount, false, round(salesorderdetail.unit_price, 2) as unit_price from item inner join salesorderdetail ON item.ItemID = salesorderdetail.ItemID left join (select tcrd.item_id, tcrd.return_quantity from tbl_customer_return tcr inner join tbl_customer_return_detail tcrd ON tcr.customer_return_id = tcrd.customer_return_id where tcr.status = 1 and tcr.sales_order_id = " + orderID + ") tcrds ON salesorderdetail.itemid = tcrds.item_id where salesorderdetail.status = 1 and tcrds.return_quantity is null and salesorderdetail.orderid = " + orderID;
        String sql = "Select item.ItemID, item.item_part_no, item.Description, @tot_qty:=round(salesorderdetail.quantity - ifnull((select sum(tcrd.return_quantity) from tbl_customer_return tcr inner join tbl_customer_return_detail tcrd ON tcr.customer_return_id = tcrd.customer_return_id where tcr.sales_order_id = salesorderdetail.orderid and tcrd.item_id = item.ItemID), 0), 1) as qty, round(((salesorderdetail.unit_price - salesorderdetail.discount_amount / salesorderdetail.quantity) * @tot_qty), 2) as amount, round(salesorderdetail.discount_amount, 2) as discount, false, round(salesorderdetail.unit_price, 2) as unit_price from item inner join salesorderdetail ON item.ItemID = salesorderdetail.ItemID where salesorderdetail.status = 1 and salesorderdetail.orderid = " + orderID + " having qty > 0";
        ResultSet rst = DBHandler.getData(sql);
        
        retTableMoldel.setRowCount(0);
        while (rst.next()) {
            Object obj[] = {rst.getInt(1), rst.getString(2), rst.getString(3), rst.getDouble(4), rst.getDouble(5), rst.getDouble(6), false, "", "", rst.getDouble(8)};
            retTableMoldel.addRow(obj);
        }
    }
///***********************************************////////////////////////////////////////////////////
    public static void loadAllReturnData(int orderID, DefaultTableModel retTableMoldel) throws ClassNotFoundException, SQLException {
        
         // String sql = "select it.item_part_no, it.Description, sum(tcrd.return_quantity) as return_quantity from tbl_customer_return tcr inner join tbl_customer_return_detail tcrd ON tcr.customer_return_id = tcrd.customer_return_id inner join item it ON tcrd.item_id = it.ItemID where tcr.sales_order_id = " + orderID + " group by it.ItemID";
           // String sql = "select it.item_part_no, it.Description, SUM(tcrd.return_quantity) AS return_quantity, ROUND(tcr.amount, 2) FROM tbl_customer_return tcr INNER JOIN tbl_customer_return_detail tcrd ON tcr.customer_return_id = tcrd.customer_return_id INNER JOIN item it ON tcrd.item_id = it.ItemID WHERE tcr.sales_order_id = " + orderID + " GROUP BY it.ItemID";
              String sql = "select it.item_part_no, it.Description, SUM(tcrd.return_quantity) AS return_quantity, ROUND(tcrd.unit_price, 2) FROM tbl_customer_return tcr INNER JOIN tbl_customer_return_detail tcrd ON tcr.customer_return_id = tcrd.customer_return_id INNER JOIN item it ON tcrd.item_id = it.ItemID WHERE tcr.sales_order_id = " + orderID + " GROUP BY it.ItemID";
          ResultSet rst = DBHandler.getData(sql);
        retTableMoldel.setRowCount(0);
        while (rst.next()) {
            Object obj[] = {rst.getString(1), rst.getString(2), rst.getDouble(3), rst.getDouble(4)};
            retTableMoldel.addRow(obj);
        }
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
    public static void updateDebtsOnReturn(int salesOrder, double retAmount) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String sql = "update debt dbt set dbt.debtamount = round(dbt.debtamount- ?,2) where dbt.salesorderid = ?";
        Object row[] = {retAmount, salesOrder};
        DBHandler.setDataToDataBase(row, con, sql);
    }
}
