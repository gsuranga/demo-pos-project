/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.salesorderviws;

import controllers.CustomerController;
import controllers.DebtController;
import controllers.EmployeeController;
import controllers.LossSaleController;
import controllers.ProfileController;
import controllers.SalesOrderController;
import controllers.SalesOrderDetailController;
import controllers.StockController;
import controllers.UserController;
import controllers.VehicleController;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import models.Debt;
import models.LossSale;
import models.SalesOrder;
import models.SalesOrderDetail;
import models.ServiceDetail;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import utilities_new.AutoGenerateID;
import utilities_new.CheckConnection;
import utilities_new.Combosearch;
import utilities_new.ValidateValues;
import views.customer.NewCustomer;
import views.employee.AddEmployee;
import static views.salesorderviws.SalesOrderPanel.sendSalesDetailsAsJSON;
import views.vehicleviews.NewVehicle;

/**
 *
 * @author shdinesh.99
 */
public class UpdateSalesOrder extends javax.swing.JFrame {

    /**
     * Creates new form UpdateSalesOrder
     */
    private Combosearch customerSearch, vehicleSearch, employeeSearch;
    private DefaultTableModel partsTableModel, serviceTableModel;
    private int salesOrderID;
    private HashSet<Object[]> deletedParts;
    private HashMap<Integer, Double> restorePartStocks;
    private JasperReport report2;
    private JButton hiddenButton;

    public UpdateSalesOrder() {

    }

    public UpdateSalesOrder(int salesOrderID, JButton hiddenButton) {
        initComponents();
        this.salesOrderID = salesOrderID;
        this.hiddenButton = hiddenButton;
        try {
            partsTableModel = (DefaultTableModel) tblUpdateOrderContains.getModel();
            serviceTableModel = (DefaultTableModel) tblUpdateOrderServices.getModel();
            CustomerController.fillCustomerNames(cmbSelectCustomer);
            EmployeeController.fillEmployeeNames(cmbEmployee);
            customerSearch = new Combosearch();
            vehicleSearch = new Combosearch();
            employeeSearch = new Combosearch();
            customerSearch.setSearchableCombo(cmbSelectCustomer, true, "Customer not registered.");
            vehicleSearch.setSearchableCombo(cmbVehicleRegNo, true, "Vehicle not registered.");
            employeeSearch.setSearchableCombo(cmbEmployee, true, "Employee not registered.");
            deletedParts = new HashSet<>();
            restorePartStocks = new HashMap<>();
            setLocationRelativeTo(null);
            loadSalesOrderPartsForUpdate(salesOrderID);
            loadSalesOrderServicesForUpdate(salesOrderID);
            loadSalesOrderData(SalesOrderController.getSalesOrderDataForUpdate(salesOrderID));
            checkButtons();
            checkServiceButtons();
            checkBillButton();
            report2 = JasperCompileManager.compileReport("./report/newinvoice.jrxml");
            SavedOrderPartsListner listner = new SavedOrderPartsListner();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (JRException ex) {
        }
    }

    public void fillVehicleRegNos(int customerID) throws ClassNotFoundException, SQLException {
        ResultSet allVehicleDetails = null;
        if (customerID > 0) {
            allVehicleDetails = VehicleController.getVehicleDetails("customer_id", customerID + "");
        } else {
            allVehicleDetails = VehicleController.getAllVehicleDetails();
        }

        cmbVehicleRegNo.removeAllItems();
        while (allVehicleDetails.next()) {
            cmbVehicleRegNo.addItem(allVehicleDetails.getString(3));
        }
    }

    public void loadSalesOrderPartsForUpdate(int soID) throws ClassNotFoundException, SQLException {
        ResultSet rst = SalesOrderDetailController.getPartsDataToUpdate(soID);
        String tableRow[] = new String[13];
        int rowCount = partsTableModel.getRowCount();
        if (rowCount > 0) {
            partsTableModel.setRowCount(0);
        }
        while (rst.next()) {
            for (int i = 0; i < 13; i++) {
                tableRow[i] = rst.getString(i + 1);
            }
            partsTableModel.addRow(tableRow); 
        }

    }

    public void loadSalesOrderServicesForUpdate(int soID) throws ClassNotFoundException, SQLException {
        ResultSet rst = SalesOrderDetailController.getServiceDataToUpdate(soID);
        String tableRow[] = new String[11];
        int rowCount = serviceTableModel.getRowCount();
        if (rowCount > 0) {
            serviceTableModel.setRowCount(0);
        }
        while (rst.next()) {
            for (int i = 0; i < 11; i++) {
                tableRow[i] = rst.getString(i + 1);
            }
            serviceTableModel.addRow(tableRow);
        }

    }

    public void calculate() {
        double serviceCost = Double.parseDouble(txtLabourCost.getText());
        double tot = countTotal(tblUpdateOrderContains) + serviceCost;
        txtTotal.setText(String.format("%.2f", tot));
        double paid = Double.parseDouble(txtPaid.getText().trim());
        txtBalance.setText((calculateBalance(paid)));
        double spare_parts_cost = tot - serviceCost;
        sparepartscost.setText(String.format("%.2f", spare_parts_cost));
    }

    public double calculateServiceCost() {
        double total = 0.0;

        int rowCount = tblUpdateOrderServices.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            total += Double.parseDouble(((Object) tblUpdateOrderServices.getValueAt(i, 7)).toString());

        }
        txtLabourCost.setText(String.format("%.2f", total));

        return total;
    }

    public String calculateBalance(double paidAmount) {
        double total = Double.parseDouble(txtTotal.getText());
        double balance = paidAmount - total;
        return String.format("%.2f", balance);

    }

    public double calculateTotalDiscount() {
        double totalPartsDisc = 0.0;
        double totalServiceDisc = 0.0;

        int rowCount = tblUpdateOrderContains.getRowCount();
        int serviceRowCount = tblUpdateOrderServices.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            totalPartsDisc += Double.parseDouble((tblUpdateOrderContains.getValueAt(i, 5)).toString());

        }
        for (int i = 0; i < serviceRowCount; i++) {
            totalServiceDisc += Double.parseDouble((tblUpdateOrderServices.getValueAt(i, 5)).toString());

        }
        double totalDisc = totalPartsDisc + totalServiceDisc;
        return totalDisc;

    }

    public double countTotal(JTable table) {
        double total = 0.0;
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            total += Double.parseDouble(((Object) table.getValueAt(i, 7)).toString());

        }
        return total;
    }

    public void checkServiceButtons() {
        int tableRowCount = tblUpdateOrderServices.getRowCount();
        if (tableRowCount > 0) {
            btnRemoveService.setEnabled(true);
            btnRemoveAllServices.setEnabled(true);
        } else {
            btnRemoveService.setEnabled(false);
            btnRemoveAllServices.setEnabled(false);
        }
    }

    public void checkBillButton() {
        int rowCount = tblUpdateOrderContains.getRowCount();
        int serviceCount = tblUpdateOrderServices.getRowCount();

        double amount = Double.parseDouble(txtTotal.getText().trim());
        if ((rowCount > 0 && amount >= 0) || serviceCount > 0) {
            btnBill.setEnabled(true);
        } else {
            btnBill.setEnabled(false);
        }
    }

    public void checkButtons() {
        int tableRowCount = tblUpdateOrderContains.getRowCount();
        if (tableRowCount > 0) {
            btnRemoveFromTable.setEnabled(true);
            btnRemoveAllInTable.setEnabled(true);
        } else {
            btnRemoveFromTable.setEnabled(false);
            btnRemoveAllInTable.setEnabled(false);
        }
    }

    public void removeTableItems(JTable table) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int selectedRow[] = table.getSelectedRows();
        while (selectedRow.length > 0) {
            int option = JOptionPane.showConfirmDialog(this, "Do you want to remove this item?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == 0) {
                int detailID = Integer.parseInt(dtm.getValueAt(selectedRow[0], 0).toString());
                int itemID = Integer.parseInt(dtm.getValueAt(selectedRow[0], 1).toString());
                int stockID = Integer.parseInt(dtm.getValueAt(selectedRow[0], 12).toString());
                double qty = Double.parseDouble(dtm.getValueAt(selectedRow[0], 4).toString());
                Object deletedRow[] = {detailID, itemID, stockID, qty};
                deletedParts.add(deletedRow);
                dtm.removeRow(selectedRow[0]);
                System.out.println(deletedParts.size());
                selectedRow = table.getSelectedRows();
            } else {
                table.clearSelection();
                break;
            }
            checkButtons();

        }
    }

    public void removeTableServices(JTable table) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int selectedRow[] = table.getSelectedRows();
        while (selectedRow.length > 0) {
            int option = JOptionPane.showConfirmDialog(this, "Do you want to remove this item?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == 0) {
                dtm.removeRow(selectedRow[0]);
                selectedRow = table.getSelectedRows();
            } else {
                table.clearSelection();
                break;

            }
            System.out.println(restorePartStocks);
            checkButtons();

        }
    }

    public void removeAllTableItems(JTable table) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int allRows = table.getRowCount();
        int option = JOptionPane.showConfirmDialog(this, "Do you want to remove all these items?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == 0) {
            while (allRows > 0) {
                int detailID = Integer.parseInt(dtm.getValueAt(0, 0).toString());
                int itemID = Integer.parseInt(dtm.getValueAt(0, 1).toString());
                int stockID = Integer.parseInt(dtm.getValueAt(0, 12).toString());
                double qty = Double.parseDouble(dtm.getValueAt(0, 4).toString());
                Object deletedRow[] = {detailID, itemID, stockID, qty};
                deletedParts.add(deletedRow);
                dtm.removeRow(0);
                System.out.println(deletedParts.size());
                allRows = table.getRowCount();
            }
            checkButtons();
        }

    }

    public void removeAllTableServices(JTable table) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int allRows = table.getRowCount();
        int option = JOptionPane.showConfirmDialog(this, "Do you want to remove all these items?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == 0) {
            while (allRows > 0) {
                dtm.removeRow(0);
                allRows = table.getRowCount();
            }
            checkButtons();
        }

    }

    public void loadSalesOrderData(ResultSet rst) throws SQLException {

        if (rst.next()) {
            cmbEmployee.setSelectedItem(rst.getString(18));
            cmbSelectCustomer.setSelectedItem(rst.getString(5));
            cmbVehicleRegNo.setSelectedItem(rst.getString(7));
            txtCurrentMeterReading.setText(rst.getString(14));
            txtNextServiceDue.setText(rst.getString(15));
            lblCustomerContactNo.setText(rst.getString(16));
            String total = rst.getString(9);
            String paid = rst.getString(11);
            double dTotal = Double.parseDouble(total);
            double dPaid = Double.parseDouble(paid);
            txtTotal.setText(String.format("%.2f", dTotal));
            txtPaid.setText(String.format("%.2f", dPaid));
            txtBalance.setText(String.format("%.2f", (dTotal - dPaid)));
          //  txtCommission.setText(String.format("%.2f", dPaid));
        }

    }

    /*OLD----------
     public void updateStockDetailsOnSalesOrders(int requested, int itemID) throws ClassNotFoundException, SQLException {
     int requestedQty = requested;
     ResultSet rst = StockController.getMinimumStockDetailForItem(itemID);
     rst.next();
     int detailID = rst.getInt(1);
     int currentQty = rst.getInt(2);
     if (requestedQty <= currentQty) {
     int remaining = currentQty - requestedQty;
     StockController.updateStockDetailQuantity(detailID, remaining);
     } else {

     StockController.updateStockDetailQuantity(detailID, 0);
     int extraQty = requested - currentQty;
     updateStockDetailsOnSalesOrders(extraQty, itemID);
     }
     }*/
    public void addNewLossSale(int searchCategoryID, int partID, double qty, String date) throws ClassNotFoundException, SQLException {
        ResultSet rst = StockController.getCategoryRemainingStock(searchCategoryID);

        if (rst.next()) {
            int tataPartID = rst.getInt(1);
            double categoryRemainingStock = rst.getDouble(3);
            if (categoryRemainingStock <= 0) {
                LossSale lossSale = new LossSale(tataPartID, qty, 1, date, 2);
                int addNewLossSale = LossSaleController.addNewLossSale(lossSale);
            } else {
                LossSale lossSale = new LossSale(tataPartID, qty, 2, date, 2);
                int addNewLossSale = LossSaleController.addNewLossSale(lossSale);
            }
        }

    }

    public void updateSalesOrderParts(int status) throws ClassNotFoundException, SQLException {
        int rowCount = tblUpdateOrderContains.getRowCount();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date();
        String orderDate = sdf.format(d1);
        for (int i = 0; i < rowCount; i++) {
            int id = Integer.parseInt(tblUpdateOrderContains.getValueAt(i, 0).toString());
            if (id == 0) {
                int itemID = (Integer) tblUpdateOrderContains.getValueAt(i, 1);
                double qty = Double.parseDouble(tblUpdateOrderContains.getValueAt(i, 4).toString());
                double discount = Double.parseDouble(tblUpdateOrderContains.getValueAt(i, 5).toString());
                sdf.applyPattern("HH:mm:ss");
                String deliverdTime = sdf.format(d1);
                double detailAmount = Double.parseDouble(((Object) tblUpdateOrderContains.getValueAt(i, 7)).toString());
                double unitPrice = Double.parseDouble(tblUpdateOrderContains.getValueAt(i, 6).toString());
                double buyingPrice = Double.parseDouble(tblUpdateOrderContains.getValueAt(i, 10).toString());
                String brand = tblUpdateOrderContains.getValueAt(i, 8).toString();
                int categoryID = Integer.parseInt(tblUpdateOrderContains.getValueAt(i, 9).toString());
                double totalPartsDisc = Double.parseDouble(tblUpdateOrderContains.getValueAt(i, 11).toString());
                int stockID = Integer.parseInt(tblUpdateOrderContains.getValueAt(i, 12).toString());
                SalesOrderDetail orderDetailModel = new SalesOrderDetail(salesOrderID, itemID, qty, totalPartsDisc, orderDate, deliverdTime, unitPrice, buyingPrice, detailAmount, discount, stockID, 1);
                orderDetailModel.setSalesOrderID(this.salesOrderID);
                int value2 = SalesOrderDetailController.addNewSalesOrderDetail(orderDetailModel);
                StockController.updateStockDetailQuantity(qty, itemID, stockID);
                if (!"TATA".equals(brand) && value2 > 0 && status < 3) {
                    addNewLossSale(categoryID, itemID, qty, orderDate);
                }
            }

        }
//        if (deletedParts.size() > 0) {
//            Collection<Integer> values = deletedParts.values();
//            Iterator it = values.iterator();
//            while (it.hasNext()) {
//                int id = Integer.parseInt(it.next().toString());
//                SalesOrderDetailController.deleteSalesOrderDetail(id);
//
//            }
//
//        }
    }

    public void addServiceDetails(int salesOrderID) throws ClassNotFoundException, SQLException {
        int rowCount = tblUpdateOrderServices.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            int id = Integer.parseInt(tblUpdateOrderServices.getValueAt(i, 0).toString());
            if (id == 0) {
                int serviceID = (Integer) tblUpdateOrderServices.getValueAt(i, 1);
                int discountType = Integer.parseInt(tblUpdateOrderServices.getValueAt(i, 8).toString());
                double discount = Double.parseDouble(tblUpdateOrderServices.getValueAt(i, 5).toString());
                double detailAmount = Double.parseDouble((tblUpdateOrderServices.getValueAt(i, 6)).toString());
                double cost = Double.parseDouble(tblUpdateOrderServices.getValueAt(i, 6).toString());
                String doneBy = tblUpdateOrderServices.getValueAt(i, 9).toString();
                double qty = Double.parseDouble(tblUpdateOrderServices.getValueAt(i, 4).toString());
                double discountTotal = Double.parseDouble(tblUpdateOrderServices.getValueAt(i, 10).toString());
                ServiceDetail serviceDetail = new ServiceDetail(this.salesOrderID, serviceID, discountType, discount, cost, detailAmount, doneBy, qty, discountTotal, 1);
                int value2 = SalesOrderController.addNewServiceDetail(serviceDetail);
            }

        }
        //}

    }

    public int addOrderToDB(int status) {

        int salesOrderID = 0;
        //int addedItems[] = new int[rowCount];
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String orderDate = sdf.format(new Date());
            ResultSet customerDetails = CustomerController.getCustomerDetails("customer_name", cmbSelectCustomer.getSelectedItem().toString());
            ResultSet empDetails = EmployeeController.getEmployeeDetails("employee_name", cmbEmployee.getSelectedItem().toString());
            customerDetails.next();
            empDetails.next();
            int customerID = customerDetails.getInt(1);
            customerID = (customerID == 0 ? 1 : customerID);
            int empID = empDetails.getInt(1);
            empID = (empID == 0 ? 1 : empID);
            int itemCount = cmbVehicleRegNo.getItemCount();

            String vRegNo = "";
            int vehicleID = 0;
            if (itemCount > 0) {
                vRegNo = cmbVehicleRegNo.getSelectedItem().toString();
                ResultSet vehicleDetails = VehicleController.getVehicleDetails("vehicle_reg_no", vRegNo);
                vehicleDetails.next();
                vehicleID = vehicleDetails.getInt(1);
            } else {
                vRegNo = "";
                vehicleID = 0;
            }
            sdf.applyPattern("HH:mm:ss");
            String time = sdf.format(new Date());
            int userID = UserController.getCurrentUser();
            double labourCost = Double.parseDouble(txtLabourCost.getText());
            double amount = Double.parseDouble(txtTotal.getText());
            double paidAmount = Double.parseDouble(txtPaid.getText());
            String salesOrderNo = "-";//AutoGenerateID.generateNextID("", "salesorder");
            if (status != 3) {
                try {
                    salesOrderNo = AutoGenerateID.generateNextID("", "salesorder");
                } catch (Exception ex) {
                    salesOrderNo = AutoGenerateID.generateNextDBIDOnEmptyResultSet("");
                }
            }

            double currentReading = Double.parseDouble(txtCurrentMeterReading.getText());
            double nextDue = Double.parseDouble(txtNextServiceDue.getText());
            double totalDisc = calculateTotalDiscount();
            String otherComments = txtUpdateOtherComments.getText();
            //sales_order_no = ?, OrderDate = ?, CustomerID = ?, vehicle_id = ?, time = ?, userid = ?, amount = ?, labour_amount = ?, paid_amount = ?, current_meter_reading = ?, next_service_due = ?, employee_id = ?, total_discount = ?, status = ? where orderid = ?
            SalesOrder orderModel = new SalesOrder(salesOrderNo, orderDate, customerID, vehicleID, time, userID, amount, paidAmount, labourCost, currentReading, nextDue, empID, totalDisc, otherComments, status, 1);
            orderModel.setSalesorderID(this.salesOrderID);
            int value1 = SalesOrderController.updateSalesOrder(orderModel);

            if (value1 > 0) {
                updateSalesOrderParts(status);
                addServiceDetails(salesOrderID);
                switch (status) {
                    case 2:
                        JOptionPane.showMessageDialog(this, "Order added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(this, "Order saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                }

            } else {
                addServiceDetails(salesOrderID);
                switch (status) {
                    case 2:
                        JOptionPane.showMessageDialog(this, "Order added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(this, "Order saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                }
            }
            double debtAmount = Double.parseDouble(txtBalance.getText().replaceAll(",", ""));
            Date debtEndDate = new Date(new Date().getTime() + ((long) 2.592e+9));//30*24*60*60*1000
            sdf.applyPattern("yyyy-MM-dd");
            String endDate = sdf.format(debtEndDate);
            //System.out.println(debtAmount);
            if (debtAmount < 0 && status < 3) {
                Debt debt = new Debt(customerID, vehicleID, salesOrderID, orderDate, endDate, Math.abs(debtAmount), 2);
                int debtValue = DebtController.addNewDebt(debt);
                if (debtValue > 0) {
                    JOptionPane.showMessageDialog(this, "Debt details updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                }

            }
            ////***************************
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String format = timeFormat.format(new Date());
            int orderTable = partsTableModel.getRowCount();
            ResultSet profile = ProfileController.getDealerDetails();
            profile.next();
            ArrayList<String> items = new ArrayList<>();
            int order_count = partsTableModel.getRowCount();
            int service_count = serviceTableModel.getRowCount();
            int tot_row_count = order_count + service_count;

            for (int i = 0; i < orderTable; i++) {
                String itempart = String.valueOf(partsTableModel.getValueAt(i, 1));

                if (items.contains(itempart)) {
                } else {
                    items.add(String.valueOf(partsTableModel.getValueAt(i, 1)));
                }
            }

            double noQty = 0;
            for (int i = 0; i < orderTable; i++) {
                noQty += Double.parseDouble((partsTableModel.getValueAt(i, 4).toString()));
            }
            int numberofItem = items.size();
            String snoofitems = String.valueOf(numberofItem);
            String snoqty = String.format("%.2f", noQty);

            String customer = cmbSelectCustomer.getSelectedItem().toString();
            double total = Double.parseDouble(txtTotal.getText());
            double paid = Double.parseDouble(txtPaid.getText());

            double balence = Double.parseDouble(txtBalance.getText().replaceAll(",", ""));
            double labAmount = Double.parseDouble(txtLabourCost.getText());
            String cusAccountNumber = "775230";
            String invoiceNo = salesOrderNo;
            double spare_parts_cost = total - labAmount;
            //String spare_prices = String.format("%.2f", spare_parts_cost);
            //----Owner Detail-------------------------------
            String shopName = profile.getString(2);
            String address = profile.getString(10);
            String tel = profile.getString(8);
            String email = profile.getString(11);
            double total_without_vat = (amount + totalDisc);
            //----------------------------------
            String startTime = format;
            double cashAmount = Double.parseDouble(txtPaid.getText());
            double changeAmount = Double.parseDouble(txtBalance.getText());

            String noOFItems = snoofitems;
            String noOfQty = snoqty;

            String vehicle_no = String.valueOf(cmbVehicleRegNo.getSelectedItem());
            String cus_tel = lblCustomerContactNo.getText();
            String ood_meeter = txtCurrentMeterReading.getText();
            String next_meter = txtNextServiceDue.getText();
            String checked_by = "";
            String employee = cmbEmployee.getSelectedItem().toString();
            switch (status) {
                case 2:
                    printBill(partsTableModel, serviceTableModel, customer, cusAccountNumber, total, paid, balence, invoiceNo, labAmount, spare_parts_cost, cashAmount, changeAmount, noOFItems, noOfQty, startTime, shopName, address, tel, tot_row_count, email, vehicle_no, cus_tel, ood_meeter, next_meter, total_without_vat, totalDisc, checked_by, employee);
                    break;
            }
            //***********po***********--
            partsTableModel.setRowCount(0);
            serviceTableModel.setRowCount(0);
            checkBillButton();
            hiddenButton.doClick();

            this.dispose();

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cmbSelectCustomer.getEditor().selectAll();
        return salesOrderID;
    }

    public void printBill(DefaultTableModel item_dtm, DefaultTableModel service_dtm, String customer, String cusAccountNumber, double total, double paid, double balence, String invoiceNo, double labAmount, double spare_prices, double cashAmount, double changeAmount, String noOFItems, String noOfQty, String startTime, String shopName, String address, String tel, int tot_row_count, String email, String vehicle_no, String cus_tel, String ood_meeter, String next_meter, double total_without_vat, double total_discount, String checked_by, String employee) throws ClassNotFoundException, SQLException {
        /*SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
         String format = timeFormat.format(new Date());
         int orderTable = itemDtm.getRowCount();
         ResultSet profile = ProfileController.getDealerDetails();
         profile.next();
         ArrayList<String> items = new ArrayList<>();
         int order_count = tblOrderContains.getRowCount();
         int service_count = tblOrderServices.getRowCount();
         int tot_row_count = order_count + service_count;

         for (int i = 0; i < orderTable; i++) {
         String itempart = String.valueOf(itemDtm.getValueAt(i, 1));

         if (items.contains(itempart)) {
         } else {
         items.add(String.valueOf(itemDtm.getValueAt(i, 1)));
         }
         }

         int noQty = 0;
         for (int i = 0; i < orderTable; i++) {
         noQty += Integer.parseInt(String.valueOf(itemDtm.getValueAt(i, 5)));
         }
         int numberofItem = items.size();
         String snoofitems = String.valueOf(numberofItem);
         System.out.println(snoofitems + "lopi");
         String snoqty = String.valueOf(noQty);
         */
        HashMap map = new HashMap();
//        String customer = cmbSelectCustomer.getSelectedItem().toString();
//        String total = txtTotal.getText();
//        double paid = Double.parseDouble(txtPaid.getText());
//
//        double balence = Double.parseDouble(txtBalance.getText().replaceAll(",", ""));
//        String labAmount = txtLabourCost.getText();
//        String cusAccountNumber = "775230";
//        String invoiceNo = txtSalesOrderNo.getText();
//        double spare_parts_cost = Double.parseDouble(total) - Double.parseDouble(labAmount);
//        String spare_prices = String.format("%.2f", spare_parts_cost);
//        //----Owner Detail-------------------------------
//        String shopName = profile.getString(2);
//        String address = profile.getString(10);
//      String tel = profile.getString(8);
//
//        //----------------------------------
//        String startTime = format;
//        String cashAmount = txtPaid.getText();
//        String changeAmount = txtBalance.getText();
//
//        String noOFItems = snoofitems;
//        String noOfQty = snoqty;

        map.put("customer", customer);
        map.put("cusAccountNumber", cusAccountNumber);
        map.put("total", String.format("%.2f", total));
        map.put("paid", String.format("%.2f", paid));
        map.put("balence", String.format("%.2f", balence));
        map.put("inNo", invoiceNo);
        map.put("labAmount", String.format("%.2f", labAmount));
        map.put("sparepartscost", spare_prices);
        map.put("vehicleno", vehicle_no);
        map.put("cus_tel", cus_tel);
        //-----------------------------------------------//
        map.put("cash", String.format("%.2f", cashAmount));
        map.put("change", String.format("%.2f", changeAmount));
        map.put("noOFItems", noOFItems);
        map.put("noQty", noOfQty);
        map.put("startTime", startTime);
        map.put("total_without_vat", String.format("%.2f", total_without_vat));
        map.put("total_discount", String.format("%.2f", total_discount));
        //--------------------Shop Detail--------------------
        map.put("shopName", shopName);
        map.put("address", address);
        map.put("tel", tel);
        map.put("email", email);
        //-----------------------Meter ------------
        map.put("ood_meter", ood_meeter);
        map.put("next_meter", next_meter);
        map.put("checked_by", checked_by);
        map.put("employee", employee);

        try {
            // JRTableModelDataSource dataSource = new JRTableModelDataSource(item_dtm);
            //JRTableModelDataSource dataSource2 = new JRTableModelDataSource(service_dtm);
            // map.put("subReportData", dataSource2);

            int item_count = item_dtm.getRowCount();
            int service_count = service_dtm.getRowCount();
            int tot_count = item_count + service_count;

            Object[] columnNames = {"Part No", "Description", "Quantity", "Discount", "Unit Price", "Amount"};
            Object dataValues[][] = new Object[tot_count][6];
            int k = 0;
            for (int i = 0; i < item_count; i++) {
                dataValues[k][0] = (Object) item_dtm.getValueAt(i, 2);
                dataValues[k][1] = (Object) item_dtm.getValueAt(i, 3);
                dataValues[k][2] = (Object) item_dtm.getValueAt(i, 4);
                dataValues[k][3] = (Object) item_dtm.getValueAt(i, 5);
                dataValues[k][4] = (Object) item_dtm.getValueAt(i, 6);
                dataValues[k][5] = (Object) item_dtm.getValueAt(i, 7);
                k++;

            }
            for (int j = 0; j < service_count; j++) {
                //System.out.println(item_dtm.getValueAt(j, 1));
                dataValues[k][0] = (Object) service_dtm.getValueAt(j, 2);
                dataValues[k][1] = (Object) service_dtm.getValueAt(j, 3);
                dataValues[k][2] = (Object) service_dtm.getValueAt(j, 4);
                dataValues[k][3] = (Object) service_dtm.getValueAt(j, 5);
                dataValues[k][4] = (Object) service_dtm.getValueAt(j, 6);
                dataValues[k][5] = (Object) service_dtm.getValueAt(j, 7);
                k++;

            }
            DefaultTableModel model = new DefaultTableModel(dataValues, columnNames);
            JTable tot_tabe = new JTable(model);
            //System.out.println("COUNT...."+tot_tabe.getValueAt(1, 0));

            JRTableModelDataSource dataSource = new JRTableModelDataSource(model);
            // System.out.println(dataSource);
//            report2.getLastPageFooter();
            // banda.setHeight(500);
            JasperPrint print = JasperFillManager.fillReport(report2, map, dataSource);
            //  print.setPageHeight(850 + (tot_row_count * 30));

            JasperViewer.viewReport(print, false);
//             JRDesignBand banda =(JRDesignBand) report2.getLastPageFooter();
//            banda.setHeight(1500);
//            System.out.println("checked");

//            print.setPageHeight(842);
//            print.setPageWidth(217);
//            JasperViewer.viewReport(print);
            //			try {
            //				print.setOrientation(OrientationEnum.PORTRAIT);
            //				JasperPrintManager.printReport(print, false);
            //			} catch (JRException ex) {
            //				Logger.getLogger(SalesOrderPanel.class.getName()).log(Level.SEVERE, null, ex);
            //			}
            //            //-----------------------------------------------------------------------------------//
            //
            //			//------------------------HTML----------------------------------------
            //			JasperExportManager.exportReportToHtmlFile(print, "d://SalesOrders/" + customer + " " + oid + ".html");
            //
            //			//------------------------------Printing----------------------------------------------///
            //			//--------------------------------------------------------------------//
            //			PrintService pp = PrintServiceLookup.lookupDefaultPrintService();
            //			JRExporter exporter = new JRPrintServiceExporter();
            //			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            //			exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, pp.getAttributes());
            //			exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            //			exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            //			exporter.exportReport();
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class SavedOrderPartsListner implements TableModelListener {

        public SavedOrderPartsListner() {
            tblUpdateOrderContains.getModel().addTableModelListener(this);
        }

        @Override
        public void tableChanged(TableModelEvent e) {

            TableModel model = (TableModel) e.getSource();
            int type = e.getType();
            int row = e.getFirstRow();
            int column = e.getColumn();
            System.out.println(row);
            if (type == 0) {
                System.out.println("0");
            } else if (type == 1) {
                System.out.println("1");
            } else if (type == -1) {
//                int detailID = Integer.parseInt(tblUpdateOrderContains.getValueAt(row, 1).toString());
//                int itemID = Integer.parseInt(model.getValueAt(row, 1).toString());
//                int stockID = Integer.parseInt(model.getValueAt(row, 12).toString());
//                double qty = Double.parseDouble(model.getValueAt(row, 4).toString());
//                Object deletedRow[] = {detailID, itemID, stockID, qty};
//                deletedParts.add(deletedRow);
//                System.out.println(deletedParts);
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCustomerContactNo = new javax.swing.JLabel();
        txtLabourCost = new javax.swing.JTextField();
        sparepartscost = new javax.swing.JTextField();
        txtHiddenCustID = new javax.swing.JTextField();
        txtHiddenVehicleID = new javax.swing.JTextField();
        txtViewPaid = new javax.swing.JTextField();
        updatePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUpdateOrderContains = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblUpdateOrderServices = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        lblPaid = new javax.swing.JLabel();
        txtPaid = new javax.swing.JTextField();
        lblBalance = new javax.swing.JLabel();
        txtBalance = new javax.swing.JTextField();
        lblRS = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnBill = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtCurrentMeterReading = new javax.swing.JTextField();
        txtNextServiceDue = new javax.swing.JTextField();
        lblRS4 = new javax.swing.JLabel();
        lblRS5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtUpdateOtherComments = new javax.swing.JTextArea();
        btnAddNewParts = new javax.swing.JButton();
        btnRemoveFromTable = new javax.swing.JButton();
        btnRemoveAllInTable = new javax.swing.JButton();
        btnAddNewService = new javax.swing.JButton();
        btnRemoveService = new javax.swing.JButton();
        btnRemoveAllServices = new javax.swing.JButton();
        lblCustName2 = new javax.swing.JLabel();
        lblCustName = new javax.swing.JLabel();
        cmbSelectCustomer = new javax.swing.JComboBox();
        cmbEmployee = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        btnAddNewCustomer = new javax.swing.JButton();
        lblCustName1 = new javax.swing.JLabel();
        cmbVehicleRegNo = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        txtLabourCost.setText("0");

        sparepartscost.setText("0");

        txtHiddenCustID.setText("jTextField1");

        txtHiddenVehicleID.setText("jTextField1");

        txtViewPaid.setEditable(false);
        txtViewPaid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtViewPaid.setText("0.00");
        txtViewPaid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtViewPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtViewPaidActionPerformed(evt);
            }
        });
        txtViewPaid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtViewPaidFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtViewPaidFocusGained(evt);
            }
        });
        txtViewPaid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtViewPaidKeyReleased(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Sales Order");

        jScrollPane2.setBorder(null);

        tblUpdateOrderContains.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblUpdateOrderContains.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Item Code", "Part No", "Description", "Quantity", "Discount", "Unit Price", "Amount", "Brand", "CategoryID", "Buying Price", "discount amount", "stock id", "inserted", "updated"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUpdateOrderContains.setRowHeight(20);
        tblUpdateOrderContains.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblUpdateOrderContainsKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblUpdateOrderContains);
        if (tblUpdateOrderContains.getColumnModel().getColumnCount() > 0) {
            tblUpdateOrderContains.getColumnModel().getColumn(0).setMinWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(0).setMaxWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(1).setMinWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(1).setMaxWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(8).setMinWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(8).setPreferredWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(8).setMaxWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(9).setMinWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(9).setPreferredWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(9).setMaxWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(10).setMinWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(10).setPreferredWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(10).setMaxWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(11).setMinWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(11).setPreferredWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(11).setMaxWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(12).setMinWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(12).setPreferredWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(12).setMaxWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(13).setMinWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(13).setPreferredWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(13).setMaxWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(14).setMinWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(14).setPreferredWidth(0);
            tblUpdateOrderContains.getColumnModel().getColumn(14).setMaxWidth(0);
        }

        tblUpdateOrderServices.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblUpdateOrderServices.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblUpdateOrderServices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "service id", "Service No.", "Description", "Quantity", "Discount", "Cost", "Amount", "Discount type", "Done By", "discount amount", "inserted", "updated"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUpdateOrderServices.setRowHeight(20);
        tblUpdateOrderServices.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblUpdateOrderServicesKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblUpdateOrderServices);
        if (tblUpdateOrderServices.getColumnModel().getColumnCount() > 0) {
            tblUpdateOrderServices.getColumnModel().getColumn(0).setMinWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(0).setMaxWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(1).setMinWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(1).setMaxWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(8).setMinWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(8).setPreferredWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(8).setMaxWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(10).setMinWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(10).setPreferredWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(10).setMaxWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(11).setMinWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(11).setPreferredWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(11).setMaxWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(12).setMinWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(12).setPreferredWidth(0);
            tblUpdateOrderServices.getColumnModel().getColumn(12).setMaxWidth(0);
        }

        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setText("Total :");

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("0.00");
        txtTotal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblPaid.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lblPaid.setText("Paid :");

        txtPaid.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtPaid.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPaid.setText("0.00");
        txtPaid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaidActionPerformed(evt);
            }
        });
        txtPaid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPaidFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPaidFocusLost(evt);
            }
        });
        txtPaid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPaidKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPaidKeyReleased(evt);
            }
        });

        lblBalance.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lblBalance.setText("Balance :");

        txtBalance.setEditable(false);
        txtBalance.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtBalance.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtBalance.setText("0.00");
        txtBalance.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBalanceActionPerformed(evt);
            }
        });

        lblRS.setText("Rs.");

        jLabel4.setText("Rs.");

        jLabel5.setText("Rs.");

        btnBill.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBill.setText("Bill");
        btnBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBillActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel20.setText("Current Meter Reading :");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel21.setText("Next Service Due :");

        txtCurrentMeterReading.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtCurrentMeterReading.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCurrentMeterReading.setText("0");
        txtCurrentMeterReading.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtCurrentMeterReading.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrentMeterReadingActionPerformed(evt);
            }
        });
        txtCurrentMeterReading.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCurrentMeterReadingFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCurrentMeterReadingFocusGained(evt);
            }
        });
        txtCurrentMeterReading.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCurrentMeterReadingKeyReleased(evt);
            }
        });

        txtNextServiceDue.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNextServiceDue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNextServiceDue.setText("0");
        txtNextServiceDue.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtNextServiceDue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNextServiceDueActionPerformed(evt);
            }
        });
        txtNextServiceDue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNextServiceDueFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNextServiceDueFocusGained(evt);
            }
        });
        txtNextServiceDue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNextServiceDueKeyReleased(evt);
            }
        });

        lblRS4.setText("Km");

        lblRS5.setText("Km");

        txtUpdateOtherComments.setColumns(20);
        txtUpdateOtherComments.setRows(5);
        txtUpdateOtherComments.setWrapStyleWord(true);
        txtUpdateOtherComments.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Other comments", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(102, 102, 102)));
        jScrollPane1.setViewportView(txtUpdateOtherComments);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRS4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRS5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNextServiceDue, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(198, 198, 198)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCurrentMeterReading, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBalance, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(txtPaid))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(btnBill, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(104, 104, 104))))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtBalance, txtPaid});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel4)
                .addGap(34, 125, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCurrentMeterReading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))
                            .addComponent(lblPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBill, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblRS))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblRS4)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblRS5)
                                            .addComponent(txtNextServiceDue, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(88, 88, 88))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTotal)
                                .addGap(6, 6, 6)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtBalance, txtCurrentMeterReading, txtNextServiceDue, txtTotal});

        btnAddNewParts.setText("+");
        btnAddNewParts.setToolTipText("New Part");
        btnAddNewParts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewPartsActionPerformed(evt);
            }
        });

        btnRemoveFromTable.setText("-");
        btnRemoveFromTable.setToolTipText("Remove Part");
        btnRemoveFromTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveFromTableActionPerformed(evt);
            }
        });

        btnRemoveAllInTable.setText("-");
        btnRemoveAllInTable.setToolTipText("Remove All Parts");
        btnRemoveAllInTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAllInTableActionPerformed(evt);
            }
        });

        btnAddNewService.setText("+");
        btnAddNewService.setToolTipText("Add New Service");
        btnAddNewService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewServiceActionPerformed(evt);
            }
        });
        btnAddNewService.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAddNewServiceKeyPressed(evt);
            }
        });

        btnRemoveService.setText("-");
        btnRemoveService.setToolTipText("Remove Service");
        btnRemoveService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveServiceActionPerformed(evt);
            }
        });

        btnRemoveAllServices.setText("-");
        btnRemoveAllServices.setToolTipText("Remove All Services");
        btnRemoveAllServices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAllServicesActionPerformed(evt);
            }
        });

        lblCustName2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCustName2.setText("Employee:");

        lblCustName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCustName.setText("Customer:");

        cmbSelectCustomer.setEditable(true);
        cmbSelectCustomer.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        cmbSelectCustomer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        cmbSelectCustomer.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbSelectCustomerPopupMenuWillBecomeInvisible(evt);
            }
        });
        cmbSelectCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSelectCustomerActionPerformed(evt);
            }
        });

        cmbEmployee.setEditable(true);
        cmbEmployee.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEmployeeActionPerformed(evt);
            }
        });

        jButton3.setText("+");
        jButton3.setToolTipText("New Employee");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnAddNewCustomer.setText("+");
        btnAddNewCustomer.setToolTipText("New Customer");
        btnAddNewCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewCustomerActionPerformed(evt);
            }
        });

        lblCustName1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCustName1.setText("Vehicle:");

        cmbVehicleRegNo.setEditable(true);
        cmbVehicleRegNo.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        cmbVehicleRegNo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        cmbVehicleRegNo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbVehicleRegNoPopupMenuWillBecomeInvisible(evt);
            }
        });
        cmbVehicleRegNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbVehicleRegNoActionPerformed(evt);
            }
        });

        jButton1.setText("+");
        jButton1.setToolTipText("New Vehicle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Parts");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Services");

        javax.swing.GroupLayout updatePanelLayout = new javax.swing.GroupLayout(updatePanel);
        updatePanel.setLayout(updatePanelLayout);
        updatePanelLayout.setHorizontalGroup(
            updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(updatePanelLayout.createSequentialGroup()
                        .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(updatePanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1063, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnRemoveAllInTable)
                                    .addComponent(btnRemoveFromTable)
                                    .addComponent(btnAddNewParts)))
                            .addGroup(updatePanelLayout.createSequentialGroup()
                                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCustName, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCustName2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbSelectCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAddNewCustomer))
                                .addGap(127, 127, 127)
                                .addComponent(lblCustName1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(cmbVehicleRegNo, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1))
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(updatePanelLayout.createSequentialGroup()
                                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1063, Short.MAX_VALUE)
                                    .addGroup(updatePanelLayout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 997, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnRemoveService)
                                    .addComponent(btnRemoveAllServices)
                                    .addComponent(btnAddNewService))))
                        .addContainerGap())))
        );

        updatePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddNewParts, btnAddNewService, btnRemoveAllInTable, btnRemoveAllServices, btnRemoveFromTable, btnRemoveService});

        updatePanelLayout.setVerticalGroup(
            updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updatePanelLayout.createSequentialGroup()
                        .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustName2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbSelectCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCustName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbVehicleRegNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(lblCustName1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, updatePanelLayout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddNewCustomer)))
                .addGap(4, 4, 4)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updatePanelLayout.createSequentialGroup()
                        .addComponent(btnAddNewParts)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveFromTable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveAllInTable))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(updatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updatePanelLayout.createSequentialGroup()
                        .addComponent(btnAddNewService)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveService)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveAllServices)
                        .addGap(0, 84, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        updatePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddNewParts, btnAddNewService, btnRemoveAllInTable, btnRemoveAllServices, btnRemoveFromTable, btnRemoveService});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(updatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(updatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbSelectCustomerPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbSelectCustomerPopupMenuWillBecomeInvisible
        try {
            String toString = cmbSelectCustomer.getSelectedItem().toString();
            ResultSet customerDetails = CustomerController.getCustomerDetails("customer_name", toString);
            customerDetails.next();
            int custID = customerDetails.getInt(1);
            String custTP = customerDetails.getString(4);
            lblCustomerContactNo.setText(custTP);
            fillVehicleRegNos(custID);
            vehicleSearch.setSearchableCombo(cmbVehicleRegNo, true, "Vehicle Not Registered.");

        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_cmbSelectCustomerPopupMenuWillBecomeInvisible

    private void cmbSelectCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSelectCustomerActionPerformed
        String actionCString = evt.getActionCommand();
        if (actionCString.equals("comboBoxEdited")) {
            cmbVehicleRegNo.requestFocus();
        }
    }//GEN-LAST:event_cmbSelectCustomerActionPerformed

    private void cmbEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmployeeActionPerformed
        String actionCString = evt.getActionCommand();
        if (actionCString.equals("comboBoxEdited")) {
            cmbSelectCustomer.requestFocus();
        }
    }//GEN-LAST:event_cmbEmployeeActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            AddEmployee newEmployee = new AddEmployee(new JFrame(), true);
            newEmployee.setVisible(true);
            EmployeeController.fillEmployeeNames(cmbEmployee);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnAddNewCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewCustomerActionPerformed
        try {
            NewCustomer customer = new NewCustomer(new JFrame(), true);
            customer.setVisible(true);
            CustomerController.fillCustomerNames(cmbSelectCustomer);
            //fillVehicleRegNos(0);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnAddNewCustomerActionPerformed

    private void cmbVehicleRegNoPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbVehicleRegNoPopupMenuWillBecomeInvisible
        //        try {
        //            String regNo = cmbVehicleRegNo.getSelectedItem().toString();
        //            ResultSet vehicleData = VehicleController.getVehicleDetails("vehicle_reg_no", regNo);
        //            vehicleData.next();
        //            int custID = vehicleData.getInt(1);
        //            fillVehicleRegNos(custID);
        //
        //        } catch (ClassNotFoundException classNotFoundException) {
        //        } catch (SQLException sQLException) {
        //        } catch (Exception ex) {
        //
        //        }
    }//GEN-LAST:event_cmbVehicleRegNoPopupMenuWillBecomeInvisible

    private void cmbVehicleRegNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbVehicleRegNoActionPerformed
        String actionCString = evt.getActionCommand();
        if (actionCString.equals("comboBoxEdited")) {
            btnAddNewParts.requestFocus();
        }
    }//GEN-LAST:event_cmbVehicleRegNoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        NewVehicle nw = new NewVehicle(new JFrame(), true);
        nw.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblUpdateOrderContainsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblUpdateOrderContainsKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblUpdateOrderContainsKeyReleased

    private void tblUpdateOrderServicesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblUpdateOrderServicesKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblUpdateOrderServicesKeyReleased

    private void btnAddNewServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewServiceActionPerformed
        SelectService selectService = new SelectService(null, true, serviceTableModel, true);
        selectService.setVisible(true);
        checkServiceButtons();
        checkBillButton();
        calculateServiceCost();
        calculate();
    }//GEN-LAST:event_btnAddNewServiceActionPerformed

    private void btnAddNewServiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddNewServiceKeyPressed
        int keyCode = evt.getKeyCode();
        if (keyCode == KeyEvent.VK_DOWN) {
            txtCurrentMeterReading.requestFocus();
        }
    }//GEN-LAST:event_btnAddNewServiceKeyPressed

    private void btnRemoveServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveServiceActionPerformed
        removeTableServices(tblUpdateOrderServices);
        checkServiceButtons();
        checkBillButton();
        calculateServiceCost();
        calculate();
    }//GEN-LAST:event_btnRemoveServiceActionPerformed

    private void btnRemoveAllServicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAllServicesActionPerformed
        removeAllTableServices(tblUpdateOrderServices);
        checkServiceButtons();
        checkBillButton();
        calculateServiceCost();
        calculate();
    }//GEN-LAST:event_btnRemoveAllServicesActionPerformed

    private void btnAddNewPartsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewPartsActionPerformed
        SelectPart sp = new SelectPart(this, true, partsTableModel, true);
        sp.setVisible(true);
        checkButtons();
        checkBillButton();
        calculateServiceCost();
        calculate();
    }//GEN-LAST:event_btnAddNewPartsActionPerformed

    private void btnRemoveFromTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveFromTableActionPerformed
        removeTableItems(tblUpdateOrderContains);
        checkServiceButtons();
        checkBillButton();
        calculateServiceCost();
        calculate();
    }//GEN-LAST:event_btnRemoveFromTableActionPerformed

    private void btnRemoveAllInTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAllInTableActionPerformed
        removeAllTableItems(tblUpdateOrderContains);
        calculateServiceCost();
        calculate();
    }//GEN-LAST:event_btnRemoveAllInTableActionPerformed

    private void txtViewPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtViewPaidActionPerformed

    }//GEN-LAST:event_txtViewPaidActionPerformed

    private void txtViewPaidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtViewPaidFocusLost
        txtViewPaid.setText("0.00");
    }//GEN-LAST:event_txtViewPaidFocusLost

    private void txtViewPaidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtViewPaidFocusGained
        txtViewPaid.selectAll();
    }//GEN-LAST:event_txtViewPaidFocusGained

    private void txtViewPaidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtViewPaidKeyReleased
        try {
            double paidAmount = Double.parseDouble(txtPaid.getText());
            txtBalance.setText("" + calculateBalance(paidAmount));
        } catch (NumberFormatException numberFormatException) {
        }
    }//GEN-LAST:event_txtViewPaidKeyReleased

    private void txtNextServiceDueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNextServiceDueKeyReleased
        ValidateValues.validateTextFieldForNumber(txtNextServiceDue);
}//GEN-LAST:event_txtNextServiceDueKeyReleased

    private void txtNextServiceDueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNextServiceDueFocusGained
        txtNextServiceDue.selectAll();
}//GEN-LAST:event_txtNextServiceDueFocusGained

    private void txtNextServiceDueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNextServiceDueFocusLost
        String val = txtNextServiceDue.getText();
        if (val.isEmpty() || val.equals(null)) {
            txtNextServiceDue.setText("0");
        }
}//GEN-LAST:event_txtNextServiceDueFocusLost

    private void txtNextServiceDueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNextServiceDueActionPerformed
        
        txtNextServiceDue.requestFocus();
        txtPaid.requestFocus();
}//GEN-LAST:event_txtNextServiceDueActionPerformed

    private void txtCurrentMeterReadingKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurrentMeterReadingKeyReleased
        ValidateValues.validateTextFieldForNumber(txtCurrentMeterReading);
}//GEN-LAST:event_txtCurrentMeterReadingKeyReleased

    private void txtCurrentMeterReadingFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCurrentMeterReadingFocusGained
        txtCurrentMeterReading.selectAll();
}//GEN-LAST:event_txtCurrentMeterReadingFocusGained

    private void txtCurrentMeterReadingFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCurrentMeterReadingFocusLost
        String val = txtCurrentMeterReading.getText();
        if (val.isEmpty() || val.equals(null)) {
            txtCurrentMeterReading.setText("0");
        }
}//GEN-LAST:event_txtCurrentMeterReadingFocusLost

    private void txtCurrentMeterReadingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrentMeterReadingActionPerformed
        
        txtNextServiceDue.requestFocus();
}//GEN-LAST:event_txtCurrentMeterReadingActionPerformed

    private void btnBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBillActionPerformed
        Object options[] = {"Bill", "Save"};
        int option = JOptionPane.showOptionDialog(null, "Select an option...", "Option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "Bill");
        System.out.println(option);
        if (option == 0) {
            if (CheckConnection.isInternetReachable()) {
                int salesOrderID = addOrderToDB(2);
                if (salesOrderID > 0) {
                    int job = sendSalesDetailsAsJSON(salesOrderID);
                    try {
                        SalesOrderController.updateSalesOrderStatus(salesOrderID);
                    } catch (ClassNotFoundException ex) {
                    } catch (SQLException ex) {
                    }
                }
            } else {
                int salesOrderID = addOrderToDB(2);
            }
        } else if (option == 1) {
            int salesOrderID = addOrderToDB(3);
        }
    }//GEN-LAST:event_btnBillActionPerformed

    private void txtPaidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidKeyReleased
        try {
            double paidAmount = Double.parseDouble(txtPaid.getText());
            txtBalance.setText("" + calculateBalance(paidAmount));
            checkBillButton();
        } catch (NumberFormatException numberFormatException) {
        }
}//GEN-LAST:event_txtPaidKeyReleased

    private void txtPaidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidKeyPressed
        ValidateValues.validateTextFieldForNumber(txtPaid);
        try {
            double paidAmount = Double.parseDouble(txtPaid.getText());
            txtBalance.setText("" + calculateBalance(paidAmount));
        } catch (NumberFormatException numberFormatException) {
        }
}//GEN-LAST:event_txtPaidKeyPressed

    private void txtPaidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaidFocusLost
        String val = txtPaid.getText();
        if (val.isEmpty() || val.equals(null)) {
            txtPaid.setText("0.00");
        }
}//GEN-LAST:event_txtPaidFocusLost

    private void txtPaidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaidFocusGained
        txtPaid.selectAll();
}//GEN-LAST:event_txtPaidFocusGained

    private void txtPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaidActionPerformed
        //        try {
        //            double paidAmount = Double.parseDouble(txtPaid.getText());
        //            txtBalance.setText("" + calculateBalance(paidAmount));
        //        } catch (NumberFormatException numberFormatException) {
        //        } catch (Exception ex) {
        //
        //        }
        //        btnBill.doClick();
}//GEN-LAST:event_txtPaidActionPerformed

    private void txtBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBalanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBalanceActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UpdateSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateSalesOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateSalesOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNewCustomer;
    private javax.swing.JButton btnAddNewParts;
    private javax.swing.JButton btnAddNewService;
    private javax.swing.JButton btnBill;
    private javax.swing.JButton btnRemoveAllInTable;
    private javax.swing.JButton btnRemoveAllServices;
    private javax.swing.JButton btnRemoveFromTable;
    private javax.swing.JButton btnRemoveService;
    private javax.swing.JComboBox cmbEmployee;
    private javax.swing.JComboBox cmbSelectCustomer;
    private javax.swing.JComboBox cmbVehicleRegNo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblBalance;
    private javax.swing.JLabel lblCustName;
    private javax.swing.JLabel lblCustName1;
    private javax.swing.JLabel lblCustName2;
    private javax.swing.JLabel lblCustomerContactNo;
    private javax.swing.JLabel lblPaid;
    private javax.swing.JLabel lblRS;
    private javax.swing.JLabel lblRS4;
    private javax.swing.JLabel lblRS5;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField sparepartscost;
    private javax.swing.JTable tblUpdateOrderContains;
    private javax.swing.JTable tblUpdateOrderServices;
    private javax.swing.JTextField txtBalance;
    private javax.swing.JTextField txtCurrentMeterReading;
    private javax.swing.JTextField txtHiddenCustID;
    private javax.swing.JTextField txtHiddenVehicleID;
    private javax.swing.JTextField txtLabourCost;
    private javax.swing.JTextField txtNextServiceDue;
    private javax.swing.JTextField txtPaid;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextArea txtUpdateOtherComments;
    private javax.swing.JTextField txtViewPaid;
    private javax.swing.JPanel updatePanel;
    // End of variables declaration//GEN-END:variables
}
