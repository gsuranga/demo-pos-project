/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import connections.DBConnection;
import connections.DBHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import models.Cheque;
import models.Debt;

/**
 *
 * @author user-pc
 */
public class ChequeController {

    /* insert data*/
    public static int addNewCheque(Cheque cheque) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "insert into cheque (debtid,cheque_no,delear_Name,bank,account_number,added_date,added_time,check_date,amount,status,cheque_status) values (?,?,?,?,?,?,?,?,?,?,?)";
        Object[] row = {cheque.getDebtID(), cheque.getChequeNo(), cheque.getDelearname(), cheque.getBank(), cheque.getAccountNO(), cheque.getAddedDate(), cheque.getAddedTime(), cheque.getCheckDate(), cheque.getAmount(), cheque.getStatus(), cheque.getChequestatus()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;
    }
    /*cheque delete*/

    public static int deleteCheque(Cheque cheque) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "update cheque set status = 0 where id = ?";
        Object[] row = {cheque.getID()};
        int setData = DBHandler.setDataToDataBase(row, con, query);
        return setData;

    }

    public static int getLastInsertedChequeID() throws ClassNotFoundException, SQLException {
        String sql = "select last_insert_id() as last_id from cheque";
        ResultSet data = DBHandler.getData(sql);
        data.next();
        int id = data.getInt("last_id");
        return id;
    }

    public static ResultSet getChequeDetalis(String where, String value) throws ClassNotFoundException, SQLException {
        String sql = "select * from cheque where " + where + " = '" + value + "' and status = 1";
        ResultSet rst = DBHandler.getData(sql);

        return rst;
    }
    /*loadAll Data cheque payment view*/

    public static void loadAllCheque(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select cheque_no,\n"
                + "delear_Name\n"
                + ",bank,\n"
                + "account_number,\n"
                + "check_date,\n"
                + "amount from cheque c inner join debt db ON c.debtid=db.debtid where c.status = 1 and c.cheque_status = 3 ";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[6];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 6; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);

        }

    }

    public static ResultSet getBanktypes() throws ClassNotFoundException, SQLException {
        String sql = "select bank_name from chque_bank where status=1";
        ResultSet rst = DBHandler.getData(sql);
        return rst;
    }

    public static double checkPendingAmount(int debtID) throws ClassNotFoundException, SQLException {
        String query = "select (debtamount -  (select coalesce(sum(paidamount),0) from cheque where debtid=" + debtID + " and status = 1)) as pending from debt where debtid = " + debtID + " and status = 2";
        ResultSet data = DBHandler.getData(query);
        double remining = 0;
        if (data.next()) {
            remining = data.getDouble(1);

        } else {
            remining = 0;
        }
        return remining;
    }
    ///search deposit cheque when the enter date

    public static void SearchCheque(DefaultTableModel dtm, String startDate, String endDate) throws ClassNotFoundException, SQLException {
        String sql = "select db.debtid,c.id,c.cheque_no,c.delear_Name,c.bank,c.account_number,c.check_date,c.amount,c.cheque_status from cheque c left join debt db ON c.debtid=db.debtid where c.check_date between '" + startDate + "' and '" + endDate + "' and c.status = 1 and  c.cheque_status = 4";
        //String sql = "select cheque_no,delear_Name,bank,account_number,check_date,amount from cheque  where check_date between '" + startDate + "' and '" + endDate + "' and status = 1";
        ResultSet rst = DBHandler.getData(sql);
        Object tableRow[] = new Object[10];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }
        while (rst.next()) {
            String cheque_status = "Pending";
            boolean accepted = false;
            if (rst.getInt(9) == 4) {
                cheque_status = "Deposit";
                accepted = false;
            } else if (rst.getInt(9) == 2) {
                cheque_status = "Reject";
                accepted = false;
            } else if (rst.getInt(9) == 1) {
                cheque_status = "Paid";
                accepted = true;
            }

            Object[] row = {rst.getString(1) ,rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6),rst.getString(7), rst.getDouble(8),
                rst.getInt(9), cheque_status, accepted};
            for (int i = 0; i < tableRow.length; i++) {
                tableRow[i] = row[i] + "";
            }

            dtm.addRow(tableRow);

        }
        
    }

    //Search All Cheque when the enter Date cheque payement.java viwes
    public static void SearchCheque1(DefaultTableModel dtm, String ChequestartDate, String ChequeendDate) throws ClassNotFoundException, SQLException {
        String sql = "select  cheque_no,delear_Name,bank,account_number,check_date,amount from cheque c inner join debt db ON c.debtid=db.debtid where c.check_date between '" + ChequestartDate + "' and '" + ChequeendDate + "' and c.status = 1 and c.cheque_status=3 order by check_date asc";
        ResultSet rst = DBHandler.getData(sql);

        String tableRow[] = new String[6];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }

        while (rst.next()) {
            for (int i = 0; i < 6; i++) {
                tableRow[i] = rst.getString(i + 1);
            }

            dtm.addRow(tableRow);

        }
    }

//detalis view cheque  added after deposit cheque
    public static void loadAllChequeDetalis(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select \n"
                + "    db.debtid,\n"
                + "    c.id,\n"
                + "    c.cheque_no,\n"
                + "    c.delear_Name,\n"
                + "    c.bank,\n"
                + "    c.account_number,\n"
                + "    c.check_date,\n"
                + "    c.amount,\n"
                + "    c.cheque_status\n"
                + "from\n"
                + "    cheque c\n"
                + "        left join\n"
                + "    debt db ON c.debtid = db.debtid\n"
                + "where\n"
                + "    c.status = '1' and c.cheque_status = 4\n"
                + "group by c.id";
        ResultSet rst = DBHandler.getData(sql);
        Object tableRow[] = new Object[10];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }
        while (rst.next()) {
            String cheque_status = "Pending";
            boolean accepted = false;
            if (rst.getInt(9) == 4) {
                cheque_status = "Deposit";
                accepted = false;
            } else if (rst.getInt(9) == 2) {
                cheque_status = "Reject";
                accepted = false;
            } else if (rst.getInt(9) == 1) {
                cheque_status = "Paid";
                accepted = true;
            }

            Object[] row = {rst.getString(1) ,rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6),rst.getString(7), rst.getDouble(8),
                rst.getInt(9), cheque_status, accepted};
            for (int i = 0; i < tableRow.length; i++) {
                tableRow[i] = row[i] + "";
            }

            dtm.addRow(tableRow);

        }

    }

     //loard  cheque summary detalis for report penal for view all btn
    public static void ChequeSummary(DefaultTableModel dtm) throws ClassNotFoundException, SQLException {

        String sql = "select"
                + " id,"
                + "cheque_no,"
                + "delear_Name,"
                + "bank,"
                + "account_number,"
                + "check_date,"
                + "amount,"
                + "cheque_status "
                + "from cheque c inner join debt db ON c.debtid=db.debtid where  (c.cheque_status = 2 or c.cheque_status=1) and c.status = '1' ";
        ResultSet rst = DBHandler.getData(sql);
        Object tableRow[] = new Object[8];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            for (int i = 0; i < rowCount; i++) {
                dtm.removeRow(0);
            }
        }

        String cheque_status = "";
        while (rst.next()) {

            if (rst.getInt(8) == 1) {
                cheque_status = "Paid";

            } else if (rst.getInt(8) == 2) {
                cheque_status = "Reject";

            }

            Object[] row = {rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4),
                rst.getString(5), rst.getString(6), rst.getDouble(7),
                cheque_status};
            for (int i = 0; i < tableRow.length; i++) {
                tableRow[i] = row[i] + "";
            }
            dtm.addRow(tableRow);
        }

    }

    // loard cheque when the enter date
    public static void ChequeSummarySearch1(DefaultTableModel dtm, String searchStartDate1, String searchEndDate1) throws ClassNotFoundException, SQLException {

        String sql = "select\n"
                + " id,\n"
                + "cheque_no,\n"
                + "delear_Name,\n"
                + "bank,\n"
                + "account_number,\n"
                + "check_date,\n"
                + "amount,\n"
                + "cheque_status\n"
                + "from cheque c inner join debt db ON c.debtid=db.debtid where  check_date between '" + searchStartDate1 + "' and '" + searchEndDate1 + "' and c.status = '1' and (c.cheque_status = 2 or c.cheque_status=1)";
        //String sql = "select cheque_no,delear_Name,bank,account_number,check_date,amount from cheque  where check_date between '" + startDate + "' and '" + endDate + "' and status = 1";
        ResultSet rst = DBHandler.getData(sql);
        String tableRow[] = new String[8];
        int rowCount = dtm.getRowCount();
        if (rowCount >= 0) {
            dtm.setRowCount(0);
        }
        String cheque_status = "";
        while (rst.next()) {

            if (rst.getInt(8) == 1) {
                cheque_status = "Paid";

            } else if (rst.getInt(8) == 2) {
                cheque_status = "Reject";

            }
            Object[] row = {rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4),
                rst.getString(5), rst.getString(6), rst.getDouble(7),
                cheque_status};

            for (int i = 0; i < tableRow.length; i++) {
                tableRow[i] = row[i] + "";
            }

            dtm.addRow(tableRow);

        }
    }

    public static int getBanktypeForName(String bankTypeName) throws ClassNotFoundException, SQLException {
        String sql = "select model_id from cheque_bank where bank_name = '" + bankTypeName + "' and status=1";
        ResultSet rst = DBHandler.getData(sql);
        rst.next();
        int typeID = rst.getInt(1);
        return typeID;
    }

    public static int updateStatus(Debt dt) throws ClassNotFoundException, SQLException {
        String query = "update debt set status = " + dt.getStatus() + " where debtid = " + dt.getDebtID();
        int data = DBHandler.setData(query);
        return data;
    }

    public static void fillBankNames(JComboBox cmb) throws ClassNotFoundException, SQLException {
        String query = "select id from cheque where status = '1'";
        ResultSet rstItem = DBHandler.getData(query);
        cmb.removeAllItems();
        while (rstItem.next()) {

            cmb.addItem(rstItem.getString(1));
            //lbl.setText(rstItem.getString(2));

        }
    }

    public static int updateChequeStatus(int cheque_status, int cheque_id) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "UPDATE `demolanka`.`cheque` SET `cheque_status` = '" + cheque_status + "' WHERE `cheque`.`id` = '" + cheque_id + "'";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        int res = preparedStatement.executeUpdate();

        return res;

    }

    public static int depositCheque(int cheque_status, String cheque_no) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "UPDATE `demolanka`.`cheque` SET `cheque_status` = '" + cheque_status + "' WHERE `cheque`.`cheque_no` = '" + cheque_no + "'";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        int res = preparedStatement.executeUpdate();

        return res;

    }

    public static int updateChequeStatusByChequeNumber(int cheque_status, String cheque_id) throws ClassNotFoundException, SQLException {
        Connection con = DBConnection.getConnection();
        String query = "UPDATE `demolanka`.`cheque` SET `cheque_status` = '" + cheque_status + "' WHERE `cheque`.`id` = '" + cheque_id + "'";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        int res = preparedStatement.executeUpdate();

        return res;
    }

}
