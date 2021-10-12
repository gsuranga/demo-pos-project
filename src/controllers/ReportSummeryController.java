/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import models.ReportSummery;

/**
 *
 * @author insaf
 */
public class ReportSummeryController {

    public static ArrayList<ReportSummery> getBrand(JComboBox jComboBox) throws ClassNotFoundException, SQLException {
        String sql = "select BrandID,BrandName from itembrand where status=1";
        ResultSet rst = DBHandler.getData(sql);
        while (rst.next()) {
            jComboBox.addItem(rst.getString("BrandName"));
        }
        return null;

    }
   
    //Loard Supplier Name for cmbobox in ABC report panel
    public static ArrayList<ReportSummery> getSupplier1(JComboBox jComboBox) throws ClassNotFoundException, SQLException {
        String sql = "select SupplierID,SupplierName from supplier where status=1";
        ResultSet rst = DBHandler.getData(sql);
        while (rst.next()) {
            jComboBox.addItem(rst.getString("SupplierName"));
        }
        return null;

    }
    
    
    
    //ABC Search when Date Range
     public static void getSummeryOvearall(String date1, String date2, DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
      // String sql = "select it.ItemID,sod.orderid, it.item_part_no,it.Description, sup.SupplierName,sod.quantity,sod.amount-(cur.amount+ so.garge_commission) as amount from salesorder so inner join salesorderdetail sod ON so.OrderID = sod.orderid inner join  item it on sod.itemid = it.ItemID  inner join  tbl_customer_return cur ON so.OrderID = cur.sales_order_id inner join supplier sup ON it.supplier_id=sup.SupplierID where so.status = 1"; 
        //  String sql = "select sod.ItemID,sod.orderid, it.item_part_no,it.Description, sup.SupplierName,sod.quantity, sod.date,sod.amount-(cur.amount+ so.garge_commission) as amount from salesorder so inner join salesorderdetail sod ON so.OrderID = sod.orderid inner join  item it on sod.itemid = it.ItemID  inner join  tbl_customer_return cur ON so.OrderID = cur.sales_order_id inner join supplier sup ON it.supplier_id=sup.SupplierID where sod.date BETWEEN '" + date1+ "'AND  '" + date2 + "' and sod.status = 1";
         String sql = "select \n"
                + "    sod.itemid,\n"
                + "	sod.orderid,\n"
                + "    it.item_part_no,\n"
                + "    it.Description,\n"
                + "    ifnull(vm.vehicle_model_name, '-') as vehicle_model_name,\n"
                + "    sup.SupplierName,\n"
                + "    sod.quantity,\n"
                + "	sod.date,\n"
                + "    sod.detailid,\n"
                + "    @unitAmount:=round(sod.amount - (ifnull(so.garge_commission, 0) + sum(ifnull(cur.amount, 0))),\n"
                + "            2) as unitAmount,\n"
                + "    tbl_sum.tot_unitAmount,\n"
                + "    ((round(sod.amount - (ifnull(so.garge_commission, 0) + sum(ifnull(cur.amount, 0))),\n"
                + "            2) * 100) / tbl_sum.tot_unitAmount) tot_unitAmount_percentage\n"
                + "from\n"
                + "    salesorderdetail sod\n"
                + "        left join\n"
                + "    salesorder so ON so.OrderID = sod.orderid\n"
                + "        inner join\n"
                + "    item it ON sod.itemid = it.ItemID\n"
                + "        left join\n"
                + "    tbl_vehicle_models vm ON so.vehicle_id = vm.vehicle_model_id\n"
                + "        inner join\n"
                + "    supplier sup ON it.supplier_id = sup.SupplierID\n"
                + "        left join\n"
                + "    tbl_customer_return cur ON so.OrderID = cur.sales_order_id\n"
                + "        CROSS JOIN\n"
                + "    (select \n"
                + "        sum(unitAmount) as tot_unitAmount\n"
                + "    from\n"
                + "        (select \n"
                + "        sod.detailid as detailid,\n"
                + "            sod.amount,\n"
                + "            ifnull(so.garge_commission, 0) as grage,\n"
                + "            round(sum(ifnull(cur.amount, 0)), 2) as retamount,\n"
                + "            round(sod.amount - (ifnull(so.garge_commission, 0) + sum(ifnull(cur.amount, 0))), 2) as unitAmount\n"
                + "    from\n"
                + "        salesorderdetail sod\n"
                + "    left join salesorder so ON so.OrderID = sod.orderid\n"
                + "    inner join item it ON sod.itemid = it.ItemID\n"
                + "    left join tbl_vehicle_models vm ON so.vehicle_id = vm.vehicle_model_id\n"
                + "    inner join supplier sup ON it.supplier_id = sup.SupplierID\n"
                + "    left join tbl_customer_return cur ON so.OrderID = cur.sales_order_id\n"
                + "    where\n"
                + "        sod.status = 1\n"
                + "    group by sod.orderid) as tbleqe) tbl_sum\n"
                 + "where\n"
                 + "     sod.date between '" + date1 + "' and '" + date2 + "' and \n"
                 + "    sod.status = 1\n"
                 + "group by sod.detailid\n"
                 + "";
          ResultSet rst = DBHandler.getData(sql);
        Object tableRow[] = new Object[13];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }
         String category = "";
         while (rst.next()) {
             double x = 0;
             x = rst.getDouble(12);

             if (x >= 70) {
                 category = "A";
             } else if (20 <= x && x < 70) {
                 category = "B";
             } else if (x < 20) {
                 category = "C";
             }
            Object row[] = {rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getInt(7), rst.getString(8), rst.getInt(9), rst.getDouble(10), rst.getDouble(11),x, category};
            for (int i = 0; i < tableRow.length; i++) {
                tableRow[i] = row[i] + "";
            }
            dtm.addRow(tableRow);
        }   
               
  }
    public static void getSummeryForSales(String fdate, String sedate, DefaultTableModel dtm, String brand, double monthsBetween) throws ClassNotFoundException, SQLException {
        // String sql = "select item.ItemID AS item_id,item.item_part_no,sk.key_name AS s_key ,item.Description,addeddate,(SELECT sum(quantity) from salesorderdetail where itemid=item_id AND salesorderdetail.date between '" + fdate + "' AND '" + sedate + "' GROUP BY item_id ) AS Monthly_sales,(SELECT sum(quantity) from salesorderdetail where itemid=item_id AND date>=addeddate GROUP BY item_id ) AS Total_sales from itembrand itembrand RIGHT JOIN item item on itembrand.BrandID=item.BrandID inner join tbl_search_key sk on item.key_id = sk.search_key_id";
        String sql = "SELECT ItemID AS ITEMID, item_part_no, Description,addeddate, (\n"
                + "\n"
                + "SELECT key_name\n"
                + "FROM tbl_search_key\n"
                + "WHERE search_key_id = ITEMID\n"
                + ") AS s_key, (\n"
                + "\n"
                + "SELECT SUM( quantity ) \n"
                + "FROM salesorderdetail\n"
                + "WHERE salesorderdetail.itemid = item.ItemID\n"
                + "AND salesorderdetail.date\n"
                + "BETWEEN '" + fdate + "'\n"
                + "AND  '" + sedate + "'\n"
                + ")AS Monthly_sales,\n"
                + "(SELECT sum(quantity) from salesorderdetail where itemid=item.ItemID AND date>=addeddate )AS Total_sales\n"
                + "FROM  item";
        ResultSet rst = DBHandler.getData(sql);
        while (rst.next()) {
            double ace = 0;
            if (rst.getDouble("Total_sales") == 0) {
                ace = 0;
            } else {
                ace = (rst.getDouble("Total_sales") / monthsBetween) / rst.getDouble("Total_sales") * 100;
            }
           // System.out.println(rst.getString("item_part_no"));
            Object rowData[] = {rst.getString("s_key"), rst.getString("item_part_no"), rst.getString("Description"), rst.getDouble("Monthly_sales"), rst.getDouble("Total_sales"), rst.getDouble("Total_sales") / monthsBetween, ace};
            dtm.addRow(rowData);
        }
    }

    public static ResultSet getTotalProfitSummary(String ProfitstartDate,String ProfitendDate) throws ClassNotFoundException, SQLException {
        //String sql = "select so.OrderID,so.sales_order_no, c.customer_name,c.customer_acc_no,cv.vehicle_reg_no,@total_expense:=round(coalesce((select sum(buying_price) from salesorderdetail where orderId=so.OrderID and status=1),0),2) as total_expense,@total_parts_income:=round(coalesce((select sum(amount) from salesorderdetail where orderId=so.OrderID),0),2) as total_parts_income,@total_service_income:=round(coalesce((select sum(service_amount) from tbl_service_detail where sales_order_id=so.OrderID and status = 1),0),2) as total_service_income, round(coalesce(((@total_service_income+@total_parts_income)-@total_expense),0),2) as total_orofit from salesorder so inner join customer c on so.CustomerID = c.id left join customer_vehicles cv on so.vehicle_id =cv.vehicle_id where so.OrderDate = '" + orderDate + "' and (so.status = 1 or so.status=2) order by so.OrderID";
//        String sql="select so.OrderID,so.sales_order_no, c.customer_name,c.customer_acc_no,cv.vehicle_reg_no,@total_expense:=round(coalesce((select sum(buying_price) from salesorderdetail where orderId=so.OrderID and status=1),0),2) as total_expense,@total_parts_income:=round(coalesce((select sum(amount) from salesorderdetail where orderId=so.OrderID),0),2) as total_parts_income,@servicecost:=((SELECT sum(tbl_service.service_cost) FROM tbl_service_detail tbl_service_detail inner join tbl_service tbl_service ON tbl_service_detail.service_id=tbl_service.service_id where tbl_service_detail.sales_order_id=OrderID))AS service_charge_Expenses,@total_service_income:=round(coalesce((select sum(service_amount) from tbl_service_detail where sales_order_id=so.OrderID and status = 1),0),2) as total_service_income, round(coalesce(((@total_service_income+@total_parts_income)-(@total_expense+@servicecost)),0),2) as total_orofit from salesorder so inner join customer c on so.CustomerID = c.id left join customer_vehicles cv on so.vehicle_id =cv.vehicle_id where so.OrderDate = '"+orderDate+"' and (so.status = 1 or so.status=2) order by so.OrderID";
        String sql = "select so.OrderID,so.sales_order_no, c.customer_name,c.customer_acc_no,cv.vehicle_reg_no,@total_expense:=round(coalesce((select sum(buying_price) from salesorderdetail where orderId=so.OrderID and status=1),0),2) as total_expense,@total_parts_income:=round(coalesce((select sum(amount) from salesorderdetail where orderId=so.OrderID),0),2) as total_parts_income,@servicecost:=ifnull((SELECT sum(tbl_service.service_cost) FROM tbl_service_detail tbl_service_detail inner join tbl_service tbl_service ON tbl_service_detail.service_id=tbl_service.service_id where tbl_service_detail.sales_order_id=OrderID),0)AS service_charge_Expenses,@total_service_income:=round(coalesce((select sum(service_amount) from tbl_service_detail where sales_order_id=so.OrderID and status = 1),0),2) as total_service_income, round(coalesce(((@total_service_income+@total_parts_income)-(@total_expense+@servicecost)),0),2) as total_orofit from salesorder so inner join customer c on so.CustomerID = c.id left join customer_vehicles cv on so.vehicle_id =cv.vehicle_id where so.OrderDate between '" + ProfitstartDate + "' and '" + ProfitendDate  + "' and (so.status = 1 or so.status=2 or so.status=4) order by so.OrderID";
        ResultSet data = DBHandler.getData(sql);
        return data;

    }
    ///commision loard
   
 public static ResultSet getCommisionSummary(int employeeId) throws ClassNotFoundException, SQLException {
       String queryPart = "and so.employee_id = " + employeeId;
     String query = "select scd.detailID,scd.employee_id,it.item_part_no,it.Description,emp.employee_name,sod.date,sod.time,scd.commision from tbl_sellscommisiondetails scd left join salesorderdetail sod ON scd.OrderID = sod.orderid inner join item it ON scd.ItemID = it.ItemID inner join tbl_employee emp ON scd.employee_id = emp.employee_id where scd.status=1 group by scd.detailID order by sod.date asc";
     String fullSql = "";
     if (employeeId == 0) {
         fullSql = query;
     } else {
         fullSql = query + queryPart;
     }
     ResultSet data = DBHandler.getData(fullSql);
     return data;

    }
    
      
    public static ResultSet getTotalStockReport(int supplierID) throws ClassNotFoundException, SQLException {
        String queryPart = "and it.supplier_id = " + supplierID;
        String query = "select it.ItemID, it.supplier_id, replace(it.item_part_no, ' ', '') as item_part_no, it.Description, coalesce((select round((sum(remainingquantity)), 0) from stockdetail where ItemID = it.ItemID and (status = 1 or status = 2) group by ItemID), 0) as remainingquantity, it.re_order_qty, coalesce((select sum(pod.quantity) from purchesorderdetail pod inner join purcheorder po ON pod.orderId = po.id where (po.status = 1 or po.status = 2) and pod.status = 1 and pod.itemid = it.ItemID and po.date between date_sub(curdate(), interval 7 day) and curdate()group by pod.itemid), 0) as requested_qty,coalesce((select StockDate from stock where StockID = (select min(stockid) from stockdetail where itemid = it.ItemId and (status = 1 or status = 2))), '0000-00-00') as first_stock_date, coalesce((select StockDate from stock where StockID = (select max(stockid) from stockdetail where itemid = it.ItemId and (status = 1 or status = 2))), '0000-00-00') as last_stock_date,sup.SupplierName from item it inner join supplier sup ON it.supplier_id = sup.SupplierID where it.status = 1 ";
        String fullSql = "";
        if (supplierID == 0) {
            fullSql = query;
        } else {
            fullSql = query + queryPart;
        }
        ResultSet rst = DBHandler.getData(fullSql);
        return rst;
    }
    
    ////Fast
    public static ResultSet getTotalStockReportFastMovingReport(int supplierID) throws ClassNotFoundException, SQLException {
        String queryPart = "and i.supplier_id = " + supplierID;
        String query = "select i.item_part_no,\n"
                + "i.Description,\n"
                + "s.SupplierName,\n"
                + "sod.quantity,\n"
                + "sod.date,\n"
                + "sod.time,\n"
                + "cu.customer_name\n"
                + "from\n"
                + " customer cu\n"
                + " inner join\n"
                + " salesorder so ON cu.id = so.CustomerID\n"
                + "        inner join\n"
                + "    salesorderdetail sod ON so.OrderID = sod.orderid\n"
                + "        inner join\n"
                + "    item i ON sod.itemid = i.ItemID\n"
                + "        inner join\n"
                + "    supplier s ON i.supplier_id = s.SupplierID\n"
                + "where\n"
                + " sod.status = 1\n"
                + "Order by sod.quantity DESC";
        String fullSql = "";
        if (supplierID == 0) {
            fullSql = query;
        } else {
            fullSql = query + queryPart;
        }
        ResultSet rst = DBHandler.getData(fullSql);
        return rst;
    }
//moment Report
     public static ResultSet getTotalStockReportMovingReport(int supplierID) throws ClassNotFoundException, SQLException {
        String queryPart = "and it.supplier_id = " + supplierID;
       String query = "select it.item_part_no,it.Description,sod.date,sod.time,sup.SupplierName,cus.customer_name from salesorder as so inner join customer as cus ON so.customerid = cus.id inner join salesorderdetail sod ON so.OrderID = sod.orderid inner join item it ON sod.itemid = it.ItemID inner join tbl_customer_return cur ON so.OrderID = cur.sales_order_id inner join supplier sup ON it.supplier_id = sup.SupplierID where sod.status = 1";
        String fullSql = "";
        if (supplierID == 0) {
            fullSql = query;
        } else {
            fullSql = query + queryPart;
        }
        ResultSet rst = DBHandler.getData(fullSql);
        return rst;
    }
    
    ///
    

    public static ResultSet getCustomerWiseReport(int custID, int vehicleID, int brandID, int partID, String invNO, String invDate) throws ClassNotFoundException, SQLException {
        String queryPart = "";
        if (custID > 0) {
            queryPart += "and cus.id = " + custID;
        }
        if (vehicleID > 0) {
            queryPart += "and cv.vehicle_id = " + vehicleID;
        }
        if (brandID > 0) {
            queryPart += "and ib.BrandID = " + vehicleID;
        }
        if (partID > 0) {
            queryPart += "and it.ItemID = " + partID;
        }
        if (!invNO.equals("") || !invNO.equals(null)) {
            queryPart += "and so.sales_order_no like '%" + invNO + "%'";
        }
        if (!invDate.equals("") || !invDate.equals(null)) {
            queryPart += "and so.OrderDate = '" + invDate + "'";
        }
        String query = "select it.ItemID, cus.id, it.item_part_no, it.Description, ib.BrandName, cus.customer_name, ifnull(cv.vehicle_reg_no, '-') as vehicle_reg_no, so.sales_order_no, so.OrderDate, (sod.quantity - ifnull((select tcrd.return_quantity from tbl_customer_return tcr inner join tbl_customer_return_detail tcrd ON tcr.customer_return_id = tcrd.customer_return_id and tcr.status = 1 where tcr.sales_order_id = sod.orderid and tcrd.item_id = it.ItemID), 0)) as qty, round(sod.discount_amount, 2) as discount_amount, round(sod.amount, 2) as amount from     salesorderdetail sod inner join salesorder so ON sod.orderid = so.OrderID and so.status = 1 inner join item it ON sod.itemid = it.ItemID and it.status = 1 inner join customer cus ON so.CustomerID = cus.id and cus.status = 1 left join customer_vehicles cv ON so.vehicle_id = cv.vehicle_id and cv.status = 1 left join itembrand ib ON it.BrandID = ib.BrandID and ib.Status = 1 where sod.status = 1 " + queryPart + " order by cus.id";
        ResultSet rst = DBHandler.getData(query);
        return rst;
    }
    
    
  public static void LoardSummeryOvearall( DefaultTableModel dtm) throws ClassNotFoundException, SQLException {
      // String sql = "select it.ItemID,sod.orderid, it.item_part_no,it.Description, sup.SupplierName,sod.quantity,sod.amount-(cur.amount+ so.garge_commission) as amount from salesorder so inner join salesorderdetail sod ON so.OrderID = sod.orderid inner join  item it on sod.itemid = it.ItemID  inner join  tbl_customer_return cur ON so.OrderID = cur.sales_order_id inner join supplier sup ON it.supplier_id=sup.SupplierID where so.status = 1"; 
        //  String sql = "select sod.ItemID,sod.orderid, it.item_part_no,it.Description, sup.SupplierName,sod.quantity, sod.date,sod.amount-(cur.amount+ so.garge_commission) as amount from salesorder so inner join salesorderdetail sod ON so.OrderID = sod.orderid inner join  item it on sod.itemid = it.ItemID  inner join  tbl_customer_return cur ON so.OrderID = cur.sales_order_id inner join supplier sup ON it.supplier_id=sup.SupplierID where sod.date BETWEEN '" + date1+ "'AND  '" + date2 + "' and sod.status = 1";
        String sql = "select \n"
                + "    sod.itemid,\n"
                + "	sod.orderid,\n"
                + "    it.item_part_no,\n"
                + "    it.Description,\n"
                + "    ifnull(vm.vehicle_model_name, '-') as vehicle_model_name,\n"
                + "    sup.SupplierName,\n"
                + "    sod.quantity,\n"
                + "	sod.date,\n"
                + "    sod.detailid,\n"
                + "    @unitAmount:=round(sod.amount - (ifnull(so.garge_commission, 0) + sum(ifnull(cur.amount, 0))),\n"
                + "            2) as unitAmount,\n"
                + "    tbl_sum.tot_unitAmount,\n"
                + "    ((round(sod.amount - (ifnull(so.garge_commission, 0) + sum(ifnull(cur.amount, 0))),\n"
                + "            2) * 100) / tbl_sum.tot_unitAmount) tot_unitAmount_percentage\n"
                + "from\n"
                + "    salesorderdetail sod\n"
                + "        left join\n"
                + "    salesorder so ON so.OrderID = sod.orderid\n"
                + "        inner join\n"
                + "    item it ON sod.itemid = it.ItemID\n"
                + "        left join\n"
                + "    tbl_vehicle_models vm ON so.vehicle_id = vm.vehicle_model_id\n"
                + "        inner join\n"
                + "    supplier sup ON it.supplier_id = sup.SupplierID\n"
                + "        left join\n"
                + "    tbl_customer_return cur ON so.OrderID = cur.sales_order_id\n"
                + "        CROSS JOIN\n"
                + "    (select \n"
                + "        sum(unitAmount) as tot_unitAmount\n"
                + "    from\n"
                + "        (select \n"
                + "        sod.detailid as detailid,\n"
                + "            sod.amount,\n"
                + "            ifnull(so.garge_commission, 0) as grage,\n"
                + "            round(sum(ifnull(cur.amount, 0)), 2) as retamount,\n"
                + "            round(sod.amount - (ifnull(so.garge_commission, 0) + sum(ifnull(cur.amount, 0))), 2) as unitAmount\n"
                + "    from\n"
                + "        salesorderdetail sod\n"
                + "    left join salesorder so ON so.OrderID = sod.orderid\n"
                + "    inner join item it ON sod.itemid = it.ItemID\n"
                + "    left join tbl_vehicle_models vm ON so.vehicle_id = vm.vehicle_model_id\n"
                + "    inner join supplier sup ON it.supplier_id = sup.SupplierID\n"
                + "    left join tbl_customer_return cur ON so.OrderID = cur.sales_order_id\n"
                + "    where\n"
                + "        sod.status = 1\n"
                + "    group by sod.orderid) as tbleqe) tbl_sum\n"
                + "where\n"
                + "    sod.status = 1\n"
                + "group by sod.detailid";
        ResultSet rst = DBHandler.getData(sql);
      Object tableRow[] = new Object[13];
      int rowCount = dtm.getRowCount();
      if (rowCount >= 0) {
          for (int i = 0; i < rowCount; i++) {
              dtm.removeRow(0);
          }
      }
      String category = "";
      //   double turnOver = getTurnOver();
      while (rst.next()) {
          double x = 0;
          x = rst.getDouble(12);
          // x = rst.getDouble(10) * 100 / turnOver;
          if (x >= 70) {
              category = "A";
          } else if (20 <= x && x < 70) {
              category = "B";
          } else if (x < 20) {
              category = "C";
          }
          Object row[] = {rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getInt(7), rst.getString(8), rst.getInt(9), rst.getDouble(10), rst.getDouble(11), x, category};
          for (int i = 0; i < tableRow.length; i++) {
              tableRow[i] = row[i] + "";
          }
          dtm.addRow(tableRow);
      }
    }
  
  ///loard Income report
  
  public static void LoardSummeryIncome( DefaultTableModel IncomeSummaryDtm,String fromDate, String ToDate) throws ClassNotFoundException, SQLException {
        String sql = "select \n"
                + "    so.orderId,\n"
                + "    sod.itemid,\n"
                + "    ifnull(so.sales_order_no, '-') as salesOderNo,\n"
                + "    ifnull(so.OrderDate, '-') as OrderDate,\n"
                + "    ifnull(so.time, '-') as Time,\n"
                + "    ifnull(it.buyingprice, 0) as buying_price,\n"
                + "    round((ifnull(so.amount, 0) + ifnull(db.debtamount, 0)),2) as CashIncome,\n"
                + "    ifnull(so.garge_commission, 0) as grage,\n"
                + "    round(sum(ifnull(cur.amount, 0)), 2) as retamount,\n"
                + "    ifnull(tblComi.commision, 0) as sales_Commision,\n"
                + "    dtbl_dbt.debtamount AS Dept_not_setteled,\n"
                + "    ifnull(dtbl_pend.debtamount, 0) AS dept_pending\n"
                + "from\n"
                + "    salesorder so\n"
                + "        left join\n"
                + "    salesorderdetail sod ON so.OrderID = sod.orderid\n"
                + "        left join\n"
                + "    item it ON it.ItemID = sod.itemid\n"
                + "        left join\n"
                + "    tbl_customer_return cur ON so.OrderID = cur.sales_order_id\n"
                + "        left join\n"
                + "    tbl_sellscommisiondetails tblComi ON so.OrderID = tblComi.OrderID\n"
                + "        left join\n"
                + "    debt db ON so.OrderID = db.salesorderid\n"
                + "        AND db.status = 1\n"
                + "        cross join\n"
                + "    (select \n"
                + "        so.OrderID as id, ifnull(db.debtamount, 0) AS debtamount\n"
                + "    from\n"
                + "        salesorder so\n"
                + "    left join debt db ON so.orderid = db.salesorderid\n"
                + "        AND db.status = 2\n"
                + "    where\n"
                + "      (so.status = 1 or so.status = 2 or so.status=4)\n"
                + "    group by so.orderId) AS dtbl_dbt\n"
                + "        left join\n"
                + "    (select \n"
                + "        so.OrderID as did, round(db.debtamount, 2) debtamount\n"
                + "    from\n"
                + "        salesorder so\n"
                + "    left join salesorderdetail sod ON so.OrderID = sod.orderid\n"
                + "    left join debt db ON so.orderid = db.salesorderid\n"
                + "    where\n"
                + "        db.status = 2\n"
                + "            and db.debtenddate < curdate()\n"
                + "            and db.debtamount < 0) as dtbl_pend ON so.OrderID = dtbl_pend.did\n"
                + "where\n"
                + " so.OrderDate between '" + fromDate + "' and '" + ToDate + "' and \n"
                + "(so.status = 1 or so.status = 2 or so.status=4) and so.OrderID = dtbl_dbt.id\n"
                + "group by so.OrderID";
        ResultSet rst = DBHandler.getData(sql);
         String tableRow[] = new String[12];
        int rowCount = IncomeSummaryDtm.getRowCount();
        if (rowCount >= 0) {
            IncomeSummaryDtm.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 12; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            IncomeSummaryDtm.addRow(tableRow);

        }
  }
        
        
       //loard to expense for report
        public static void loadExpencesForReport(DefaultTableModel ExpensesSummaryDtm, String fromDate, String ToDate) throws ClassNotFoundException, SQLException {
        String sql = "select expences_id, description,  round(amount,2) as amount, expence_date from tbl_expences where expence_date between '" + fromDate + "' and '" + ToDate + "' and status = 1 order by expences_id desc";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[4];
        int rowCount = ExpensesSummaryDtm.getRowCount();
        if (rowCount >= 0) {
            ExpensesSummaryDtm.setRowCount(0);
        }
        while (rst.next()) {
            for (int i = 0; i < 4; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            ExpensesSummaryDtm.addRow(tableRow);

        }

    }
 public static void loadpaymentSupp(DefaultTableModel PaymentSupSummaryDtm, String fromDate, String ToDate) throws ClassNotFoundException, SQLException {
        String sql = "select \n"
                + "tblSup.invoice_no,\n"
                + "tblSup.supplier_name,\n"
                + "tblSup.date,\n"
                + "tblSup.time,\n"
                + "tblSup.amount,\n"
                + "tblSup.remarks\n"
                + "from tbl_paymentsupplier tblSup\n"
                + " where \n"
                + "tblSup.date between '" + fromDate + "' and '" + ToDate + "' and\n"
                + "tblSup.status=1";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[6];
        int rowCount = PaymentSupSummaryDtm.getRowCount();
        if (rowCount >= 0) {
            PaymentSupSummaryDtm.setRowCount(0);
        }
        while (rst.next()) {
            for (int i = 0; i < 6; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            PaymentSupSummaryDtm.addRow(tableRow);

        }

    }
  
  
  
  
  
  //get Abc turnover
//   public static double getTurnOver() throws ClassNotFoundException, SQLException {
//         double turnOver = 0;
//         String sql = "(select \n"
//                 + "    sum(unitAmount) as tot_unitAmount\n"
//                 + "from\n"
//                 + "    (select \n"
//                 + "            round(sod.amount - (ifnull(so.garge_commission, 0) + sum(ifnull(cur.amount, 0))), 2) as unitAmount\n"
//                 + "    from\n"
//                 + "        salesorderdetail sod\n"
//                 + "    left join salesorder so ON so.OrderID = sod.orderid\n"
//                 + "    \n"
//                 + "    left join tbl_customer_return cur ON so.OrderID = cur.sales_order_id\n"
//                 + "    where\n"
//                 + "        sod.status = 1\n"
//                 + "    group by sod.orderId) as tbleqe)";
//         ResultSet rst = DBHandler.getData(sql);
//         while (rst.next()) {
//             turnOver = rst.getDouble(1);
//         }
//         return turnOver;
//
//    }
  
//  ResultSet rst = DBHandler.getData(sql);
//         Object tableRow[] = new Object[11];
//         int rowCount = dtm.getRowCount();
//         if (rowCount >= 0) {
//             for (int i = 0; i < rowCount; i++) {
//                 dtm.removeRow(0);
//             }
//         }
//         String category = "";
//       //   double turnOver = getTurnOver();
//         while (rst.next()) {
//             double x = 0;
//            
//          x =   rst.getDouble(9) * 100/turnOver;
//             if (x >= 70) {
//                 category = "A";
//             } else if(20<= x && x<70){
//                 category = "B";
//             } else if( x < 20) {
//                 category = "C";
//             }
//           Object row[] = {rst.getString(1), rst.getString(2), rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getInt(7),rst.getString(8), rst.getDouble(9),x,category};
//            for (int i = 0; i < tableRow.length; i++) {
//                tableRow[i] = row[i] + "";
//            }
//            dtm.addRow(tableRow);
//       }
}
