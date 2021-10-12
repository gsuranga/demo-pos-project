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
import models.ExpenceIncome;

/**
 *
 * @author ceylonlinux
 */
public class ExpenceIncomeController {
    
    public static int addNewExpenceIncomeDetail(ExpenceIncome ExIn) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into tbl_expense_income(added_time,added_date,cash_income,credit_income,relization_bad_dets,value_return,total_income,cost_of_good_sold,gross_profit,other_expenses,garage_commission,sales_man_commission,payment_to_supplier,total_expenses,net_amount,status) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] row = {ExIn.getAddedDate(),ExIn.getAddedTime(),ExIn.getCashIncome(),ExIn.getCreditIncome(),ExIn.getRelization(),ExIn.getValueReturn(),ExIn.getTotalIncome(),ExIn.getCost(),ExIn.getGrossProfit(),ExIn.getOtherExpence(),ExIn.getGarageCommission(),ExIn.getSalesMan(),ExIn.getPaymentSupplier(),ExIn.getTotalExpence(),ExIn.getNetAmount(),ExIn.getStatus()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }
    public static void loadAllExprnce(DefaultTableModel ExpenceIncomeModelDtm) throws ClassNotFoundException, SQLException {

        String sql = "SELECT \n"
                + "    cash_income,\n"
                + "    credit_income,\n"
                + "    relization_bad_dets,\n"
                + "    value_return,\n"
                + "    total_income,\n"
                + "    cost_of_good_sold,\n"
                + "    gross_profit,\n"
                + "    other_expenses,\n"
                + "    garage_commission,\n"
                + "    sales_man_commission,\n"
                + "    payment_to_supplier,\n"
                + "    total_expenses,\n"
                + "    net_amount\n"
                + "FROM\n"
                + "    tbl_expense_income\n"
                + "WHERE\n"
                + "    income_id = (select \n"
                + "            max(income_id)\n"
                + "        from\n"
                + "            tbl_expense_income)\n"
                + "        and status = 1";
       
         ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[13];
        int rowCount = ExpenceIncomeModelDtm.getRowCount();
        if (rowCount >= 0) {
            ExpenceIncomeModelDtm.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 13; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            ExpenceIncomeModelDtm.addRow(tableRow);

        }

        return;
    }
    
    
    
}
