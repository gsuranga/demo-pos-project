/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views.salesorderviws;

import controllers.CustomerController;
import controllers.CustomerReturnsController;
import controllers.DebtController;
import controllers.EmployeeController;
import controllers.EstimateDetailsController;
import controllers.EstimatePrintController;
import controllers.LossSaleController;
import controllers.ProfileController;
import controllers.SalesOrderController;
import controllers.SalesOrderDetailController;
import controllers.StockController;
import controllers.UserController;
import controllers.VehicleController;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import models.Debt;
import models.EstimateDetails;
import models.EstimatePrint;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utilities_new.AutoGenerateID;
import utilities_new.CheckConnection;
import utilities_new.Combosearch;
import utilities_new.JSonJava;
import utilities_new.ValidateValues;
import views.customer.NewCustomer;
import views.customer_returns.AddNewReturn;
import views.debtviews.NewPayment;
import views.employee.AddEmployee;
import views.vehicleviews.NewVehicle;

/**
 *
 * @author SHdinesh Madushanka
 */
public class SalesOrderPanel extends javax.swing.JPanel {

    private DefaultTableModel allPartsDtm, orderItemDtm, itemDtm, salesOrdersDtm, salesOrderDetailDtm, updateOrderContainsDtm, orderServicesDtm, serviceDetailDtm, savedOrdersDtm, savedPartsDtm,
            savedServicesDtm, returnDetailsDtm;
    private int oid;
    private int eID;
    private int empId;//g
    private TableRowSorter<TableModel> rowSorter;
    private TableRowSorter<TableModel> rowSorterSaved;
    private HashMap<Object, Object> allOrderDetail;
    private HashMap<Object, Object> removedDetails;
    private Combosearch customerSearch, customerSearch1, vehicleSearch, employeeSearch;
    private List ad;
    private JasperReport report2;
     private JasperReport Estireport2;
    private DefaultTableModel salesOrderWisePartDtm;
    private DefaultTableModel salesOrderWiseServiceDtm;
    private boolean isAllSales;
    private boolean isAllEstiPrint;
    private JButton jb;
    private JButton jE;
    private String employeeId = "";//g
    private Map<String, Integer> fillEmployeeNamess; ///g

    /**
     * Creates new form SalesOrderPanel
     */


    public SalesOrderPanel(boolean isAllSales, JButton btn) {
        initComponents();
        
        ad = new ArrayList();

        SimpleDateFormat curdateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = curdateFormat.format(new Date());
        orderItemDtm = (DefaultTableModel) tblOrderContains.getModel();
        orderServicesDtm = (DefaultTableModel) tblOrderServices.getModel();
        serviceDetailDtm = (DefaultTableModel) tblViewSalesOrderServices.getModel();
        returnDetailsDtm = (DefaultTableModel) tblReturnDetails.getModel();
        /*salesOrderWisePartDtm = (DefaultTableModel) tbl_vehicle_wise_part.getModel();
         salesOrderWiseServiceDtm = (DefaultTableModel) tbl_vehicle_wise_service.getModel();*/
        savedOrdersDtm = (DefaultTableModel) tblSavedOrders.getModel();
        rowSorterSaved = new TableRowSorter<TableModel>(savedOrdersDtm);
        
        savedPartsDtm = (DefaultTableModel) tblSavedOrderDetails.getModel();
        savedServicesDtm = (DefaultTableModel) tblSavedOrderServices.getModel();
        checkButtons();
        lblRS.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        itemDtm = (DefaultTableModel) tblOrderContains.getModel();
        salesOrdersDtm = (DefaultTableModel) tblAllSalesOrders.getModel();
        //part table
       // partCommisionDtm = (DefaultTableModel) partDisTbl.getModel();//gg
        salesOrderDetailDtm = (DefaultTableModel) tblViewSalesOrderContains.getModel();
        rowSorter = new TableRowSorter<TableModel>(salesOrdersDtm);
        
        allOrderDetail = new HashMap<>();
        removedDetails = new HashMap<>();
        customerSearch = new Combosearch();
        this.isAllSales = isAllSales;
        this.jb = btn;

        checkButtons();
        checkServiceButtons();
        checkBillButton();
        checkViewButtons();

        try {
            CustomerController.fillCustomerNames(cmbSelectCustomer);
            CustomerController.fillCustomerNames(cmbCustomers);
            CustomerController.fillCustomerNames(cmbCustomers3);
            EmployeeController.fillEmployeeNames(cmbEmployee);
            customerSearch = new Combosearch();
            customerSearch1 = new Combosearch();
            vehicleSearch = new Combosearch();
            employeeSearch = new Combosearch();
            customerSearch.setSearchableCombo(cmbSelectCustomer, true, "Customer not registered.");
            customerSearch1.setSearchableCombo(cmbCustomers, true, "Customer not registered.");
            vehicleSearch.setSearchableCombo(cmbVehicleRegNo, true, "Vehicle not registered.");
            employeeSearch.setSearchableCombo(cmbEmployee, true, "Employee not registered.");
          //  employeeSearch1.setSearchableCombo(cmbEmployee1, true, "Employee not registered.");//g
            //fillVehicleRegNos();
            SalesOrderController.loadAllSalesOrders(salesOrdersDtm);
            SalesOrderController.loadAllSavedSalesOrders(savedOrdersDtm);
          
           //  report2 = JasperCompileManager.compileReport("./report/BillInvoice.jrxml");
            report2 = JasperCompileManager.compileReport("./report/newinvoice.jrxml");
           Estireport2=JasperCompileManager.compileReport("./report/Estimateinvoice.jrxml");
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        } catch (JRException ex) {
        }

        if (isAllSales) {
            jTabbedPane1.setEnabledAt(1, true);
            jTabbedPane1.setEnabledAt(2, true);
            //jTabbedPane1.setEnabledAt(3, true);
        } else {
            jTabbedPane1.setEnabledAt(1, false);
            jTabbedPane1.setEnabledAt(2, false);
            //jTabbedPane1.setEnabledAt(3, false);
        }
        cmbSelectCustomer.requestFocus();
        cmbVehicleRegNo.setSelectedItem("");
        
        lblTotalEarnings.setText(String.format("%.2f", caculateTotalEarnings()));
       lblTotalEarnings1.setText(String.format("%.2f", caculateTotalEarningsSaved()));
        cmbEmployee.getEditor().getEditorComponent().requestFocus();
    }

    
    private static double customFormat(double value) {

        DecimalFormat myFormatter = new DecimalFormat("######.00");
        double output = Double.parseDouble(myFormatter.format(value));
        //  System.out.println(value + "  " + "######.00" + "  " + output);
        return output;

    }

    // refreshCustomers();
    public void fillVehicleRegNos(int customerID) throws ClassNotFoundException, SQLException {
        ResultSet allVehicleDetails;
        if (customerID > 0) {
            allVehicleDetails = VehicleController.getVehicleDetails("customer_id", customerID + "");
        } else {
            allVehicleDetails = VehicleController.getAllVehicleDetails();
        }

        cmbVehicleRegNo.removeAllItems();
        cmbSearchVehicleRegNo.removeAllItems();
        cmbSearchVehicleRegNo3.removeAllItems();
        while (allVehicleDetails.next()) {
            cmbVehicleRegNo.addItem(allVehicleDetails.getString(3));
            cmbSearchVehicleRegNo.addItem(allVehicleDetails.getString(3));
             cmbSearchVehicleRegNo3.addItem(allVehicleDetails.getString(3));
        }
    }

//    public void fillVehicleRegNos() throws ClassNotFoundException, SQLException {
//        ResultSet allVehicleDetails = VehicleController.getAllVehicleDetails();
//        vehicleCombo.removeAllItems();
//        while (allVehicleDetails.next()) {
//            vehicleCombo.addItem(allVehicleDetails.getString(3));
//
//        }
//
//    }
    public void calculate() {
        double serviceCost = Double.parseDouble(txtLabourCost.getText());
        double tot = countTotal(tblOrderContains) + serviceCost;
        txtTotal.setText(String.format("%.2f", tot));

        double paid = Double.parseDouble(txtPaid.getText().trim());
        txtBalance.setText((calculateBalance(paid)));

        double spare_parts_cost = tot - serviceCost;
        sparepartscost.setText(String.format("%.2f", spare_parts_cost));
    }

    public double calculateServiceCost() {
        double total = 0.0;

        int rowCount = tblOrderServices.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            total += Double.parseDouble(((Object) tblOrderServices.getValueAt(i, 6)).toString());

        }
        txtLabourCost.setText(String.format("%.2f", total));

        return total;
    }

    public double calculateTotalDiscount() {
        double totalPartsDisc = 0.0;
        double totalServiceDisc = 0.0;
        int rowCount = tblOrderContains.getRowCount();
        int serviceRowCount = tblOrderServices.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            totalPartsDisc += Double.parseDouble(((Object) tblOrderContains.getValueAt(i, 4)).toString());

        }
        for (int i = 0; i < serviceRowCount; i++) {
            totalServiceDisc += Double.parseDouble(((Object) tblOrderServices.getValueAt(i, 4)).toString());

        }
        double totalDisc = totalPartsDisc + totalServiceDisc;
        return totalDisc;

    }

    public void addServiceDetails(int salesOrderID) throws ClassNotFoundException, SQLException {
        int rowCount = tblOrderServices.getRowCount();
        if (rowCount > 0) {
            for (int i = 0; i < rowCount; i++) {
                int serviceID = (Integer) tblOrderServices.getValueAt(i, 0);
                int discountType = Integer.parseInt(tblOrderServices.getValueAt(i, 7).toString());
                double detailAmount = Double.parseDouble((tblOrderServices.getValueAt(i, 6)).toString());
                double cost = Double.parseDouble(tblOrderServices.getValueAt(i, 5).toString());
                String doneBy = tblOrderServices.getValueAt(i, 8).toString();
                double qty = Double.parseDouble(tblOrderServices.getValueAt(i, 3).toString());
                double discount = Double.parseDouble(tblOrderServices.getValueAt(i, 9).toString());
                double discountTotal = Double.parseDouble(tblOrderServices.getValueAt(i, 4).toString());
                ServiceDetail serviceDetail = new ServiceDetail(salesOrderID, serviceID, discountType, discount, cost, detailAmount, doneBy, qty, discountTotal, 1);
                int value2 = SalesOrderController.addNewServiceDetail(serviceDetail);
            }
        }

    }

    public int addOrderToDB(int status) {
        int rowCount = tblOrderContains.getRowCount();
        int salesOrderID = 0;
        int addedItems[] = new int[rowCount];
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
            System.out.println("count" + itemCount);
            String vRegNo = "-";
            int vehicleID = 0;
            if (itemCount > 1) {
                vRegNo = cmbVehicleRegNo.getSelectedItem().toString();
                // System.out.println("$$$$$$$$" +   vRegNo);
                ResultSet vehicleDetails = VehicleController.getVehicleDetails("vehicle_reg_no", vRegNo);
                vehicleDetails.next();
                vehicleID = vehicleDetails.getInt(1);
            } else {
                vRegNo = "-";
                vehicleID = 0;
            }
            sdf.applyPattern("HH:mm:ss");
            String time = sdf.format(new Date());
            int userID = UserController.getCurrentUser();
            double labourCost = Double.parseDouble(txtLabourCost.getText());
            double amount = Double.parseDouble(txtTotal.getText());
            double paidAmount = Double.parseDouble(txtPaid.getText());
            double commi = Double.parseDouble(txtCommission.getText());
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
            double garageComission=Double.parseDouble(txtCommission.getText());
            String otherComments = txtOtherComments.getText();
            SalesOrder orderModel = new SalesOrder(salesOrderNo, orderDate, customerID, vehicleID, time, userID, amount, paidAmount, labourCost, currentReading, nextDue, empID, totalDisc, otherComments, status,garageComission);

            int value1 = SalesOrderController.addNewSalesOrder(orderModel);
            salesOrderID = SalesOrderController.getMaxSalesOrderID();
            oid = salesOrderID;

            if (value1 > 0 && rowCount > 0) {
                for (int i = 0; i < rowCount; i++) {
                    int itemID = Integer.parseInt(tblOrderContains.getValueAt(i, 0).toString());
                    double qty = Double.parseDouble(tblOrderContains.getValueAt(i, 3).toString());
                    sdf.applyPattern("HH:mm:ss");
                    String deliverdTime = sdf.format(new Date());
                    double detailAmount = Double.parseDouble(((Object) tblOrderContains.getValueAt(i, 6)).toString());
                    double unitPrice = Double.parseDouble(tblOrderContains.getValueAt(i, 5).toString());
                    double buyingPrice = Double.parseDouble(tblOrderContains.getValueAt(i, 9).toString());
                    String brand = tblOrderContains.getValueAt(i, 7).toString();
                    int categoryID = Integer.parseInt(tblOrderContains.getValueAt(i, 8).toString());
                    double totalPartsDisc = Double.parseDouble(tblOrderContains.getValueAt(i, 4).toString());
                    double discount = Double.parseDouble(tblOrderContains.getValueAt(i, 10).toString());
                    int stockID = Integer.parseInt(tblOrderContains.getValueAt(i, 11).toString());
                    SalesOrderDetail orderDetailModel = new SalesOrderDetail(salesOrderID, itemID, qty, discount, orderDate, deliverdTime, unitPrice, buyingPrice, detailAmount, totalPartsDisc, stockID, 1);
                    int value2 = SalesOrderDetailController.addNewSalesOrderDetail(orderDetailModel);

                    StockController.updateStockDetailQuantity(qty, itemID, stockID);
                    if (!"TATA".equals(brand) && value2 > 0 && status < 3) {
                        addNewLossSale(categoryID, itemID, qty, orderDate);
                    }
                }
                addServiceDetails(salesOrderID);
                switch (status) {
                    case 2:
                        JOptionPane.showMessageDialog(this, "Order added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        break; 
                    case 4:// F1
                        JOptionPane.showMessageDialog(this, "Order added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 3:  // saved
                        JOptionPane.showMessageDialog(this, "Order saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                        
                }

            } else {
                addServiceDetails(salesOrderID);
                switch (status) {
                    case 2:
                        JOptionPane.showMessageDialog(this, "Order added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                   case 4:   //F1
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
            if (debtAmount < 0 && status != 3) {
                Debt debt = new Debt(customerID, vehicleID, salesOrderID, orderDate, endDate, Math.abs(debtAmount), 2);
                int debtValue = DebtController.addNewDebt(debt);
                if (debtValue > 0) {
                    JOptionPane.showMessageDialog(this, "Debt details updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                }
            }
            //***************************//
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
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

            double noQty = 0;
            for (int i = 0; i < orderTable; i++) {
                noQty += Double.parseDouble((itemDtm.getValueAt(i, 3).toString()));
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
                    printBill(itemDtm, orderServicesDtm, customer, cusAccountNumber, total, paid, balence, invoiceNo, labAmount, spare_parts_cost, cashAmount, changeAmount, noOFItems, noOfQty, startTime, shopName, address, tel, tot_row_count, email, vehicle_no, cus_tel, ood_meeter, next_meter, total_without_vat, totalDisc, checked_by, employee);
                    break;
                case 4:
                    printBill(itemDtm, orderServicesDtm, customer, cusAccountNumber, total, paid, balence, invoiceNo, labAmount, spare_parts_cost, cashAmount, changeAmount, noOFItems, noOfQty, startTime, shopName, address, tel, tot_row_count, email, vehicle_no, cus_tel, ood_meeter, next_meter, total_without_vat, totalDisc, checked_by, employee);
                    break;
            
                    
            }
            //***********po***********--
            orderServicesDtm.setRowCount(0);
            itemDtm.setRowCount(0);
            clearValues();
            checkBillButton();

            SalesOrderController.loadAllSalesOrders(salesOrdersDtm);
            SalesOrderController.loadAllSavedSalesOrders(savedOrdersDtm);
            //txtSalesOrderNo.setText(AutoGenerateID.generateNextID("", "salesorder"));
            if (!isAllSales) {
                JFrame fr = (JFrame) SwingUtilities.getWindowAncestor(this);
                fr.dispose();
                jb.doClick();
            }

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //}
        cmbSelectCustomer.getEditor().selectAll();
        return salesOrderID;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int addEstimateToDB(int status) {
        int rowCount = tblOrderContains.getRowCount();
        int EstimateId = 0;
        int addedItems[] = new int[rowCount];
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String estimatedate = sdf.format(new Date());
            ResultSet customerDetails = CustomerController.getCustomerDetails("customer_name", cmbSelectCustomer.getSelectedItem().toString());
            ResultSet empDetails = EmployeeController.getEmployeeDetails("employee_name", cmbEmployee.getSelectedItem().toString());
            customerDetails.next();
            empDetails.next();
            int customerID = customerDetails.getInt(1);
            customerID = (customerID == 0 ? 1 : customerID);
            int empID = empDetails.getInt(1);
            empID = (empID == 0 ? 1 : empID);
            sdf.applyPattern("HH:mm:ss");
            String time = sdf.format(new Date());
            int userid = UserController.getCurrentUser();
            double labouramont = Double.parseDouble(txtLabourCost.getText());
            double amount = Double.parseDouble(txtTotal.getText());
        
            String estimateorderno = "-";//AutoGenerateID.generateNextID("", "salesorder");
            if (status == 1) {
                try {
                    estimateorderno = AutoGenerateID.generateNextID("", "tbl_estimatePrint");
                } catch (Exception ex) {
                    estimateorderno = AutoGenerateID.generateNextDBIDOnEmptyResultSet("");
                }
            }
            double totalDisc = calculateTotalDiscount();
            EstimatePrint estimatePrint=new EstimatePrint(estimateorderno, estimatedate, customerID, time, userid, amount, labouramont, empID, totalDisc, status);

            int value1 = EstimatePrintController.AddEstimatePrint(estimatePrint);
            EstimateId = EstimatePrintController.getMaxEstimateID();
            eID = EstimateId;

            if (value1 > 0 && rowCount > 0) {
                for (int i = 0; i < rowCount; i++) {
                    int itemid = Integer.parseInt(tblOrderContains.getValueAt(i, 0).toString());
                    double qty = Double.parseDouble(tblOrderContains.getValueAt(i, 3).toString());

                    sdf.applyPattern("HH:mm:ss");
                    String AddedTime = sdf.format(new Date());
                    String date = sdf.format(new Date());
                    double detailAmount = Double.parseDouble(((Object) tblOrderContains.getValueAt(i, 6)).toString());
                    double unitprice = Double.parseDouble(tblOrderContains.getValueAt(i, 5).toString());
                    double buyingprice = Double.parseDouble(tblOrderContains.getValueAt(i, 9).toString());
                    double discount = Double.parseDouble(tblOrderContains.getValueAt(i, 4).toString());
                    double discountamount = Double.parseDouble(tblOrderContains.getValueAt(i, 10).toString());
                    EstimateDetails estimateDetails = new EstimateDetails(EstimateId, itemid, qty, discount, AddedTime, date, unitprice, buyingprice, detailAmount, discountamount, status);
                    int value2 = EstimateDetailsController.addNewEstimateDetails(estimateDetails);

                }
                switch (status) {
                    case 1:
                        JOptionPane.showMessageDialog(this, "Estimate successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        break;
                }
            }
            //***************************//
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String format = timeFormat.format(new Date());
            int orderTable = itemDtm.getRowCount();
            ResultSet pro = ProfileController.getDealerDetails();
            pro.next();
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
            double noQty = 0;
            for (int i = 0; i < orderTable; i++) {
                noQty += Double.parseDouble((itemDtm.getValueAt(i, 3).toString()));
            }
            int numberofItem = items.size();
            
            String snoofitems = String.valueOf(numberofItem);
            String snoqty = String.format("%.2f", noQty);
            String customer = cmbSelectCustomer.getSelectedItem().toString();
            double total = Double.parseDouble(txtTotal.getText());
            double labAmount = Double.parseDouble(txtLabourCost.getText());
            String cusAccountNumber = "775230";
            String EstimateNO =estimateorderno;
            double spare_parts_cost = total - labAmount;
            //String spare_prices = String.format("%.2f", spare_parts_cost);
            //----Owner Detail-------------------------------
            String shopName = pro.getString(2);
            String address = pro.getString(10);
            String tel = pro.getString(8);
            String email = pro.getString(11);
            double total_without_vat = (amount + totalDisc);
            //----------------------------------
            String startTime = format;
            String noOFItems = snoofitems;
            String noOfQty = snoqty;
            String cus_tel = lblCustomerContactNo.getText();
            String checked_by = "";
            String employee = cmbEmployee.getSelectedItem().toString();
            switch (status) {
                case 1:
                   printEstimateBill(itemDtm, orderServicesDtm, customer, cusAccountNumber, total,EstimateNO , labAmount, spare_parts_cost, noOFItems, noOfQty, startTime, shopName, address, tel, tot_row_count, email, cus_tel, total_without_vat, totalDisc, checked_by, employee);
                    //printEstimateBill(itemDtm, orderServicesDtm, customer, cusAccountNumber,employee);
                    break;        
            }
            //***********po***********--
            orderServicesDtm.setRowCount(0);
            itemDtm.setRowCount(0);
            clearValues();
            checkBillButton();
//            if (!isAllEstiPrint) {
//                JFrame fr = (JFrame) SwingUtilities.getWindowAncestor(this);
//                fr.dispose();
//                jE.doClick();
//            }

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //}
        cmbSelectCustomer.getEditor().selectAll();
        return EstimateId ;
    }
    
///////////////////////////////////////////////////////////////////////////////////////////////////
    public void checkBillButton() {
        int rowCount = tblOrderContains.getRowCount();
        int serviceCount = tblOrderServices.getRowCount();

        double amount = Double.parseDouble(txtTotal.getText().trim());
        if ((rowCount > 0 && amount >= 0) || serviceCount > 0) {
            btnBill.setEnabled(true);
        } else {
            btnBill.setEnabled(false);
        }
    }

    public void clearValues() {
        txtBalance.setText("0.00");
        txtPaid.setText("0.00");
        txtTotal.setText("0.00");
        txtLabourCost.setText("0.00");
        txtCurrentMeterReading.setText("0");
        txtNextServiceDue.setText("0");
        txtCommission.setText("0.00");
        txtOtherComments.setText("");
    }

    public void checkButtons() {
        int tableRowCount = tblOrderContains.getRowCount();
        if (tableRowCount > 0) {
            btnRemoveFromTable.setEnabled(true);
            btnRemoveAllInTable.setEnabled(true);
           
            
        } else {
            btnRemoveFromTable.setEnabled(false);
            btnRemoveAllInTable.setEnabled(false);
           
        }
    }
    
   
    public void checkServiceButtons() {
        int tableRowCount = tblOrderServices.getRowCount();
        if (tableRowCount > 0) {
            btnRemoveService.setEnabled(true);
            btnRemoveAllServices.setEnabled(true);
        } else {
            btnRemoveService.setEnabled(false);
            btnRemoveAllServices.setEnabled(false);
        }
    }

    public double countTotal(JTable table) {
        double total = 0.0;

        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            total += Double.parseDouble(((Object) table.getValueAt(i, 6)).toString());

        }

        return total;
    }

    public void removeTableItems(JTable table) {
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
            checkButtons();

        }
    }

    public void removeAllTableItems(JTable table) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int allRows = table.getRowCount();
        int option = JOptionPane.showConfirmDialog(this, "Do you want to remove these all item?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == 0) {
            while (allRows > 0) {
                dtm.removeRow(0);
                allRows = table.getRowCount();
            }
            checkButtons();
        }

    }

    public String calculateBalance(double paidAmount) {
        double total = Double.parseDouble(txtTotal.getText());
        double balance = paidAmount - total;
        return String.format("%.2f", balance);

    }

      //  public String 
    public void loadSalesOrderDataOnSelect() {
        int selecteRow = tblAllSalesOrders.getSelectedRow();
        txtLabourCost1.setText(tblAllSalesOrders.getValueAt(selecteRow, 9).toString());
        double paidAmount = Double.parseDouble(tblAllSalesOrders.getValueAt(selecteRow, 10).toString());
        double totalAmount = Double.parseDouble(tblAllSalesOrders.getValueAt(selecteRow, 8).toString());
        txtViewPaid.setText("" + paidAmount);
        txtViewTot.setText(totalAmount + "");
        txtViewBalance.setText("" + String.format("%.2f", (paidAmount - totalAmount)));
        txtPendingAmount.setText("" + tblAllSalesOrders.getValueAt(selecteRow, 11));
        txtVCurrentMeter.setText("" + tblAllSalesOrders.getValueAt(selecteRow, 15));
        txtVIewNextService.setText("" + tblAllSalesOrders.getValueAt(selecteRow, 16));
        txtViewComments.setText(tblAllSalesOrders.getValueAt(selecteRow, 21).toString());
        txtViewGarageCommission.setText(tblAllSalesOrders.getValueAt(selecteRow, 22).toString());

    }

    public void loadSavedOrderDataOnSelect() {
        int selecteRow = tblSavedOrders.getSelectedRow();
        // txtLabourCost1.setText(tblSavedOrders.getValueAt(selecteRow, 9).toString());
        double paidAmount = Double.parseDouble(tblSavedOrders.getValueAt(selecteRow, 10).toString());
        double totalAmount = Double.parseDouble(tblSavedOrders.getValueAt(selecteRow, 8).toString());
        txtViewPaid1.setText("" + paidAmount);
        txtViewTot1.setText(totalAmount + "");
        txtViewBalance2.setText("" + String.format("%.2f", (paidAmount - totalAmount)));
        // txtPendingAmount1.setText("" + tblSavedOrders.getValueAt(selecteRow, 11));
        txtVCurrentMeter1.setText("" + tblSavedOrders.getValueAt(selecteRow, 12));
        txtVIewNextService1.setText("" + tblSavedOrders.getValueAt(selecteRow, 13));

    }

//    public void loadSalesOrderDataOnSelectselectrow(int selecteRow1) {
//        int selecteRow = selecteRow1;
//        txtLabourCost1.setText((String) tblAllSalesOrders.getValueAt(selecteRow, 9));
//        double paidAmount = Double.parseDouble(tblAllSalesOrders.getValueAt(selecteRow, 10).toString());
//        double totalAmount = Double.parseDouble(tblAllSalesOrders.getValueAt(selecteRow, 8).toString());
//        txtViewPaid.setText("" + paidAmount);
//        txtViewTot.setText(totalAmount + "");
//        txtViewBalance.setText("" + String.format("%.2f", (paidAmount - totalAmount)));
//        txtPendingAmount.setText("" + tblAllSalesOrders.getValueAt(selecteRow, 11));
//
//    }

    /* OLD--------------------//
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
    public void updateStockDetailsOnSalesOrders(int requested, int itemID, int stockID) {

    }

    public void checkViewButtons() {
        int selectedRows = tblAllSalesOrders.getSelectedRowCount();
        if (selectedRows > 0) {
            btnAddReturn.setEnabled(true);
            btnReprint.setEnabled(true);

        } else {
            btnAddReturn.setEnabled(false);
            btnReprint.setEnabled(false);

        }
    }
    /*
     public void checkViewButtons1() {
        int selectedRows = tblSavedOrders.getSelectedRowCount();
        if (selectedRows > 0) {
            btnComi.setEnabled(true);
           

        } else {
            btnComi.setEnabled(false);
           

        }
    }*/

    public double caculateTotalEarnings() {
        int rowCount = tblAllSalesOrders.getRowCount();
        double totalEarning = 0.00;
        for (int i = 0; i < rowCount; i++) {
            totalEarning += Double.parseDouble(tblAllSalesOrders.getValueAt(i, 8).toString().trim());
        }
        return totalEarning;
    }
    /*
    public double caculateTotalEmployeeCommision() {
        int rowCount = partDisTbl.getRowCount();
        double totalEarning = 0.00;
        for (int i = 0; i < rowCount; i++) {
            totalEarning += Double.parseDouble(partDisTbl.getValueAt(i, 3).toString());
        }
        return totalEarning;
    }
*/
    public void removeSalesOrder() {

        int[] selectedRows = tblAllSalesOrders.getSelectedRows();
        DefaultTableModel tableModel = (DefaultTableModel) tblAllSalesOrders.getModel();
        try {
            while (selectedRows.length > 0) {
                int option = JOptionPane.showConfirmDialog(this, "Are you sure to delete this sales order", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                int value = 0;
                if (option == 0) {
                    int orderID = Integer.parseInt((String) tblAllSalesOrders.getValueAt(selectedRows[0], 0));
                    value = SalesOrderController.removeSalesOrder(orderID);
                    SalesOrderController.removeSalesOrderDetail(orderID);
                    tableModel.removeRow(selectedRows[0]);
                    selectedRows = tblAllSalesOrders.getSelectedRows();

                    if (value > 0) {
                        JOptionPane.showMessageDialog(this, "This sales order has been successfuly deleted", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                        salesOrderDetailDtm.setRowCount(0);
                        checkViewButtons();

                    }
                } else {
                    tblAllSalesOrders.clearSelection();
                    checkViewButtons();
                    break;

                }

            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }//6918

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
        map.put("employee", employee);
        //map.put("imagess", "./src/images/Logo/logo.png");

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
                dataValues[k][0] = (Object) item_dtm.getValueAt(i, 1);
                dataValues[k][1] = (Object) item_dtm.getValueAt(i, 2);
                dataValues[k][2] = (Object) item_dtm.getValueAt(i, 3);
                dataValues[k][3] = (Object) item_dtm.getValueAt(i, 4);
                dataValues[k][4] = (Object) item_dtm.getValueAt(i, 5);
                dataValues[k][5] = (Object) item_dtm.getValueAt(i, 6);
                k++;

            }
            for (int j = 0; j < service_count; j++) {
                //System.out.println(item_dtm.getValueAt(j, 1));
                dataValues[k][0] = (Object) service_dtm.getValueAt(j, 1);
                dataValues[k][1] = (Object) service_dtm.getValueAt(j, 2);
                dataValues[k][2] = (Object) service_dtm.getValueAt(j, 3);
                dataValues[k][3] = (Object) service_dtm.getValueAt(j, 4);
                dataValues[k][4] = (Object) service_dtm.getValueAt(j, 5);
                dataValues[k][5] = (Object) service_dtm.getValueAt(j, 6);
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
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     public void printEstimateBill(DefaultTableModel item_dtm, DefaultTableModel service_dtm, String customer, String cusAccountNumber, double total, String EstimateNO, double labAmount, double spare_prices, String noOFItems, String noOfQty, String startTime, String shopName, String address, String tel, int tot_row_count, String email, String cus_tel,double total_without_vat, double total_discount, String checked_by, String employee) throws ClassNotFoundException, SQLException {
      
        HashMap maps = new HashMap();
        maps.put("customer", customer);
        maps.put("cusAccountNumber", cusAccountNumber);
        maps.put("total", String.format("%.2f", total));
        maps.put("EsNo", EstimateNO);
        maps.put("labAmount", String.format("%.2f", labAmount));
        maps.put("sparepartscost", spare_prices);
        maps.put("cus_tel", cus_tel);
        //-----------------------------------------------//
        maps.put("noOFItems", noOFItems);
        maps.put("noQty", noOfQty);
        maps.put("startTime", startTime);
        maps.put("total_without_vat", String.format("%.2f", total_without_vat));
        maps.put("total_discount", String.format("%.2f", total_discount));
        //--------------------Shop Detail--------------------
        maps.put("shopName", shopName);
        maps.put("address", address);
        maps.put("tel", tel);
        maps.put("email", email);
        //-----------------------Meter -----------
       maps.put("checked_by", checked_by);
      maps.put("employee", employee);
        maps.put("employee", employee);
        //map.put("imagess", "./src/images/Logo/logo.png");

        try {	
            int item_count = item_dtm.getRowCount();
            int service_count = service_dtm.getRowCount();
            int tot_count = item_count + service_count;

            Object[] columnNames = {"Part No", "Description", "Quantity", "Discount", "Unit Price", "Amount"};
            Object dataValues[][] = new Object[tot_count][6];
            int k = 0;
            for (int i = 0; i < item_count; i++) {
                dataValues[k][0] = (Object) item_dtm.getValueAt(i, 1);
                dataValues[k][1] = (Object) item_dtm.getValueAt(i, 2);
                dataValues[k][2] = (Object) item_dtm.getValueAt(i, 3);
                dataValues[k][3] = (Object) item_dtm.getValueAt(i, 4);
                dataValues[k][4] = (Object) item_dtm.getValueAt(i, 5);
                dataValues[k][5] = (Object) item_dtm.getValueAt(i, 6);
                k++;

            }
            for (int j = 0; j < service_count; j++) {
                //System.out.println(item_dtm.getValueAt(j, 1));
                dataValues[k][0] = (Object) service_dtm.getValueAt(j, 1);
                dataValues[k][1] = (Object) service_dtm.getValueAt(j, 2);
                dataValues[k][2] = (Object) service_dtm.getValueAt(j, 3);
                dataValues[k][3] = (Object) service_dtm.getValueAt(j, 4);
                dataValues[k][4] = (Object) service_dtm.getValueAt(j, 5);
                dataValues[k][5] = (Object) service_dtm.getValueAt(j, 6);
                k++;

            }
            DefaultTableModel model = new DefaultTableModel(dataValues, columnNames);
            JTable tot_tabe = new JTable(model);
           // System.out.println("COUNT...."+tot_tabe.getValueAt(1, 1));

            JRTableModelDataSource dataSourc = new JRTableModelDataSource(model);
			         System.out.println("hhh;.mki");
            JasperPrint printEsti = JasperFillManager.fillReport(Estireport2, maps,dataSourc );
           
            JasperViewer.viewReport(printEsti, false);

        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
}
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void checkPaymentButton() {
        int selectedRow = tblAllSalesOrders.getSelectedRow();
        double debtID = Double.parseDouble(tblAllSalesOrders.getValueAt(selectedRow, 11).toString());
        if (debtID > 0) {
            btnAddPayment.setEnabled(true);
        } else {
            btnAddPayment.setEnabled(false);
        }
    }
    
    
   

    public static synchronized int sendSalesDetailsAsJSON(int salesOrderID) {
        int maxSalesOrderID = salesOrderID;
        int aInt = 0;
        try {
            ResultSet rst = ProfileController.getDealerDetails();
            rst.next();
            String accountNo = rst.getString(3);
            String serverURL = rst.getString(9);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dealer_acc_no", accountNo);

            ArrayList<JSONObject> jsonArrayList = SalesOrderController.getSalesOrderDetailsToSend(salesOrderID);
            JSONArray jSONArray = new JSONArray(jsonArrayList);
            jSONArray.put(jsonObject);

            String replaceAll = jSONArray.toString().replaceAll("\\s", "%20");
            String replace = replaceAll.replaceAll("\\n", "%30");
            System.out.println(serverURL + "dimo_lanka/pos_services/addSalesDataToDB?sales_args=" + replace);
            JSONObject returnedObject = new JSonJava().readJsonFromUrl(serverURL + "dimo_lanka/pos_services/addSalesDataToDB?sales_args=" + replace);
            aInt = returnedObject.getInt("value");

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            // ex.printStackTrace();
        } catch (MalformedURLException mex) {
            //mex.printStackTrace();
        } catch (IOException ioex) {
        } catch (JSONException ex) {
            //ex.printStackTrace();
        }
        return aInt;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        buttonGroup2 = new javax.swing.ButtonGroup();
        txtHiddenCustID = new javax.swing.JTextField();
        txtHiddenVehicleID = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        sparepartscost = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtLabourCost = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lblPaid2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtLabourCost1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lblCustomerContactNo = new javax.swing.JLabel();
        btnHIdden = new javax.swing.JButton();
        lblBalance9 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        SalesOrderPane = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblOrderContains = new javax.swing.JTable();
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
        txtOtherComments = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtCommission = new javax.swing.JTextField();
        lblCustName = new javax.swing.JLabel();
        cmbSelectCustomer = new javax.swing.JComboBox();
        btnRemoveFromTable = new javax.swing.JButton();
        btnRemoveAllInTable = new javax.swing.JButton();
        btnAddNewParts = new javax.swing.JButton();
        cmbVehicleRegNo = new javax.swing.JComboBox();
        lblCustName1 = new javax.swing.JLabel();
        btnAddNewCustomer = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblOrderServices = new javax.swing.JTable();
        btnAddNewService = new javax.swing.JButton();
        btnRemoveService = new javax.swing.JButton();
        btnRemoveAllServices = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lblCustName2 = new javax.swing.JLabel();
        cmbEmployee = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtTotalOutstandings = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        salesOrderDate = new de.wannawork.jcalendar.JCalendarComboBox();
        cmbCustomers = new javax.swing.JComboBox();
        cmbSearchVehicleRegNo = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        lblTotalEarnings = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblAllSalesOrders = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblViewSalesOrderContains = new javax.swing.JTable();
        txtVNextServiceDue = new javax.swing.JPanel();
        lblTotal2 = new javax.swing.JLabel();
        txtViewBalance = new javax.swing.JTextField();
        lblRS1 = new javax.swing.JLabel();
        txtViewTot = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtPendingAmount = new javax.swing.JTextField();
        lblRS3 = new javax.swing.JLabel();
        txtVCurrentMeter = new javax.swing.JTextField();
        lblBalance3 = new javax.swing.JLabel();
        txtVIewNextService = new javax.swing.JTextField();
        lblBalance4 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lblBalance2 = new javax.swing.JLabel();
        lblBalance8 = new javax.swing.JLabel();
        lblBalance10 = new javax.swing.JLabel();
        txtViewPaid = new javax.swing.JTextField();
        lblRS9 = new javax.swing.JLabel();
        txtViewGarageCommission = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtViewComments = new javax.swing.JTextArea();
        jPanel11 = new javax.swing.JPanel();
        btnAddReturn = new javax.swing.JButton();
        btnAddPayment = new javax.swing.JButton();
        btnReprint = new javax.swing.JButton();
        lblRS2 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblViewSalesOrderServices = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblReturnDetails = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        salesOrderDate3 = new de.wannawork.jcalendar.JCalendarComboBox();
        cmbCustomers3 = new javax.swing.JComboBox();
        cmbSearchVehicleRegNo3 = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        lblRS6 = new javax.swing.JLabel();
        lblTotalEarnings1 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblSavedOrders = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblSavedOrderDetails = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblSavedOrderServices = new javax.swing.JTable();
        txtVNextServiceDue1 = new javax.swing.JPanel();
        lblTotal3 = new javax.swing.JLabel();
        txtViewPaid1 = new javax.swing.JTextField();
        lblRS7 = new javax.swing.JLabel();
        txtViewTot1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtPendingAmount1 = new javax.swing.JTextField();
        lblRS8 = new javax.swing.JLabel();
        txtVCurrentMeter1 = new javax.swing.JTextField();
        lblBalance6 = new javax.swing.JLabel();
        txtVIewNextService1 = new javax.swing.JTextField();
        lblBalance7 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblBalance55 = new javax.swing.JLabel();
        txtViewBalance2 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnEdit = new javax.swing.JButton();
        btnDelete2 = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");
        jPopupMenu1.add(jMenuItem1);

        jMenuItem4.setText("jMenuItem4");
        jPopupMenu1.add(jMenuItem4);

        jMenuItem5.setText("jMenuItem5");
        jPopupMenu1.add(jMenuItem5);

        txtHiddenCustID.setText("jTextField1");

        txtHiddenVehicleID.setText("jTextField1");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel17.setText("Cost of spare parts:");

        sparepartscost.setEditable(false);
        sparepartscost.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        sparepartscost.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sparepartscost.setText("0.00");
        sparepartscost.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        sparepartscost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sparepartscostActionPerformed(evt);
            }
        });

        jLabel19.setText("Rs.");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel2.setText("Service Charge:");

        jLabel7.setText("Rs.");

        txtLabourCost.setEditable(false);
        txtLabourCost.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtLabourCost.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtLabourCost.setText("0.00");
        txtLabourCost.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtLabourCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLabourCostActionPerformed(evt);
            }
        });
        txtLabourCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLabourCostFocusGained(evt);
            }
        });
        txtLabourCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLabourCostKeyReleased(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLabourCostKeyPressed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setText("Service Charge:");

        lblPaid2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPaid2.setText("Paid :");

        jLabel10.setText("Rs.");

        txtLabourCost1.setEditable(false);
        txtLabourCost1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtLabourCost1.setText("0.00");
        txtLabourCost1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtLabourCost1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLabourCost1ActionPerformed(evt);
            }
        });
        txtLabourCost1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLabourCost1FocusGained(evt);
            }
        });
        txtLabourCost1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLabourCost1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLabourCost1KeyReleased(evt);
            }
        });

        jLabel9.setText("Rs.");

        btnHIdden.setText("jButton4");
        btnHIdden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHIddenActionPerformed(evt);
            }
        });

        lblBalance9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        setToolTipText("Sales Orders");
        setPreferredSize(new java.awt.Dimension(868, 619));

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(0, 0));

        SalesOrderPane.setPreferredSize(new java.awt.Dimension(868, 619));

        jScrollPane2.setBorder(null);

        tblOrderContains.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblOrderContains.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Code", "Part No", "Description", "Quantity", "Discount", "Unit Price", "Amount", "Brand", "CategoryID", "Buying Price", "discount", "stock id", "Rack NO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrderContains.setRowHeight(20);
        tblOrderContains.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblOrderContainsKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblOrderContains);
        if (tblOrderContains.getColumnModel().getColumnCount() > 0) {
            tblOrderContains.getColumnModel().getColumn(0).setMinWidth(0);
            tblOrderContains.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblOrderContains.getColumnModel().getColumn(0).setMaxWidth(0);
            tblOrderContains.getColumnModel().getColumn(1).setMinWidth(120);
            tblOrderContains.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblOrderContains.getColumnModel().getColumn(1).setMaxWidth(120);
            tblOrderContains.getColumnModel().getColumn(7).setMinWidth(0);
            tblOrderContains.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblOrderContains.getColumnModel().getColumn(7).setMaxWidth(0);
            tblOrderContains.getColumnModel().getColumn(8).setMinWidth(0);
            tblOrderContains.getColumnModel().getColumn(8).setPreferredWidth(0);
            tblOrderContains.getColumnModel().getColumn(8).setMaxWidth(0);
            tblOrderContains.getColumnModel().getColumn(9).setMinWidth(0);
            tblOrderContains.getColumnModel().getColumn(9).setPreferredWidth(0);
            tblOrderContains.getColumnModel().getColumn(9).setMaxWidth(0);
            tblOrderContains.getColumnModel().getColumn(10).setMinWidth(0);
            tblOrderContains.getColumnModel().getColumn(10).setPreferredWidth(0);
            tblOrderContains.getColumnModel().getColumn(10).setMaxWidth(0);
            tblOrderContains.getColumnModel().getColumn(11).setMinWidth(0);
            tblOrderContains.getColumnModel().getColumn(11).setPreferredWidth(0);
            tblOrderContains.getColumnModel().getColumn(11).setMaxWidth(0);
        }

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setText("Total :");

        txtTotal.setEditable(false);
        txtTotal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("0.00");
        txtTotal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblPaid.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPaid.setText("Paid :");

        txtPaid.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtPaid.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtPaid.setText("0.00");
        txtPaid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtPaid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPaidFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPaidFocusLost(evt);
            }
        });
        txtPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaidActionPerformed(evt);
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
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCurrentMeterReadingFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCurrentMeterReadingFocusLost(evt);
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
        txtNextServiceDue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNextServiceDueFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNextServiceDueFocusLost(evt);
            }
        });
        txtNextServiceDue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNextServiceDueActionPerformed(evt);
            }
        });
        txtNextServiceDue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNextServiceDueKeyReleased(evt);
            }
        });

        lblRS4.setText("Km");

        lblRS5.setText("Km");

        txtOtherComments.setColumns(20);
        txtOtherComments.setRows(5);
        txtOtherComments.setWrapStyleWord(true);
        txtOtherComments.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Other comments", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N
        jScrollPane1.setViewportView(txtOtherComments);

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel18.setText(" Grage Commission:");

        jLabel35.setText("Rs.");

        txtCommission.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCommission.setText("0.00");
        txtCommission.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCommissionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCommissionFocusLost(evt);
            }
        });
        txtCommission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCommissionActionPerformed(evt);
            }
        });
        txtCommission.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCommissionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCommissionKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblRS4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblRS5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblRS, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                    .addComponent(txtNextServiceDue)
                    .addComponent(txtCurrentMeterReading))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(36, 36, 36)
                        .addComponent(txtCommission))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPaid, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30)
                .addComponent(btnBill, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCurrentMeterReading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNextServiceDue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRS5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotal)
                            .addComponent(lblRS)
                            .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                        .addGap(26, 26, 26))))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(lblRS4)
                        .addGap(0, 34, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap(45, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCommission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 51, Short.MAX_VALUE)
                        .addComponent(jLabel18)))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(lblPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(20, 20, 20))))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnBill, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblTotal, txtBalance, txtCurrentMeterReading, txtNextServiceDue, txtPaid, txtTotal});

        lblCustName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCustName.setText("Customer:");

        cmbSelectCustomer.setEditable(true);
        cmbSelectCustomer.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        cmbSelectCustomer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        cmbSelectCustomer.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbSelectCustomerPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbSelectCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSelectCustomerActionPerformed(evt);
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

        btnAddNewParts.setText("+");
        btnAddNewParts.setToolTipText("New Part");
        btnAddNewParts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewPartsActionPerformed(evt);
            }
        });
        btnAddNewParts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAddNewPartsKeyPressed(evt);
            }
        });

        cmbVehicleRegNo.setEditable(true);
        cmbVehicleRegNo.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        cmbVehicleRegNo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        cmbVehicleRegNo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbVehicleRegNoPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbVehicleRegNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbVehicleRegNoActionPerformed(evt);
            }
        });

        lblCustName1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCustName1.setText("Vehicle:");

        btnAddNewCustomer.setText("+");
        btnAddNewCustomer.setToolTipText("New Customer");
        btnAddNewCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewCustomerActionPerformed(evt);
            }
        });

        tblOrderServices.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblOrderServices.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblOrderServices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "service id", "Service No.", "Description", "Quantity", "Discount", "Cost", "Amount", "Discount type", "Done By", "discount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrderServices.setRowHeight(20);
        tblOrderServices.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblOrderServicesKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tblOrderServices);
        if (tblOrderServices.getColumnModel().getColumnCount() > 0) {
            tblOrderServices.getColumnModel().getColumn(0).setMinWidth(0);
            tblOrderServices.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblOrderServices.getColumnModel().getColumn(0).setMaxWidth(0);
            tblOrderServices.getColumnModel().getColumn(1).setMinWidth(120);
            tblOrderServices.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblOrderServices.getColumnModel().getColumn(1).setMaxWidth(120);
            tblOrderServices.getColumnModel().getColumn(7).setMinWidth(0);
            tblOrderServices.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblOrderServices.getColumnModel().getColumn(7).setMaxWidth(0);
            tblOrderServices.getColumnModel().getColumn(9).setMinWidth(0);
            tblOrderServices.getColumnModel().getColumn(9).setPreferredWidth(0);
            tblOrderServices.getColumnModel().getColumn(9).setMaxWidth(0);
        }

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

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Parts");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Services");

        jButton1.setText("+");
        jButton1.setToolTipText("New Vehicle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblCustName2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCustName2.setText("Employee:");

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

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Outstanding:");

        txtTotalOutstandings.setEditable(false);
        txtTotalOutstandings.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText("Rs.");

        javax.swing.GroupLayout SalesOrderPaneLayout = new javax.swing.GroupLayout(SalesOrderPane);
        SalesOrderPane.setLayout(SalesOrderPaneLayout);
        SalesOrderPaneLayout.setHorizontalGroup(
            SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SalesOrderPaneLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(SalesOrderPaneLayout.createSequentialGroup()
                            .addGroup(SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblCustName, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblCustName2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(33, 33, 33)
                            .addGroup(SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(SalesOrderPaneLayout.createSequentialGroup()
                                    .addComponent(cmbSelectCustomer, 0, 248, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnAddNewCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                                .addGroup(SalesOrderPaneLayout.createSequentialGroup()
                                    .addComponent(cmbEmployee, 0, 248, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblCustName1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTotalOutstandings, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(SalesOrderPaneLayout.createSequentialGroup()
                            .addGroup(SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(SalesOrderPaneLayout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 709, Short.MAX_VALUE)
                                    .addComponent(cmbVehicleRegNo, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane2)
                                .addComponent(jScrollPane4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnAddNewParts)
                                .addComponent(jButton1)
                                .addComponent(btnRemoveFromTable)
                                .addComponent(btnRemoveAllInTable)
                                .addComponent(btnAddNewService)
                                .addComponent(btnRemoveService)
                                .addComponent(btnRemoveAllServices))))
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(SalesOrderPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SalesOrderPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddNewParts, btnRemoveAllInTable, btnRemoveFromTable});

        SalesOrderPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddNewService, btnRemoveAllServices, btnRemoveService});

        SalesOrderPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddNewCustomer, jButton1, jButton3});

        SalesOrderPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbEmployee, cmbSelectCustomer});

        SalesOrderPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbVehicleRegNo, txtTotalOutstandings});

        SalesOrderPaneLayout.setVerticalGroup(
            SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SalesOrderPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCustName2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel24)
                        .addComponent(jLabel15))
                    .addComponent(txtTotalOutstandings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbVehicleRegNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddNewCustomer)
                    .addComponent(cmbSelectCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(lblCustName1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCustName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addGroup(SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SalesOrderPaneLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                        .addGap(4, 4, 4))
                    .addGroup(SalesOrderPaneLayout.createSequentialGroup()
                        .addComponent(btnAddNewParts)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoveFromTable, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRemoveAllInTable, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)))
                .addComponent(jLabel12)
                .addGap(1, 1, 1)
                .addGroup(SalesOrderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SalesOrderPaneLayout.createSequentialGroup()
                        .addComponent(btnAddNewService)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemoveService)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemoveAllServices))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        SalesOrderPaneLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbEmployee, cmbSelectCustomer, cmbVehicleRegNo});

        SalesOrderPaneLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddNewCustomer, jButton1, jButton3});

        SalesOrderPaneLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddNewService, btnRemoveAllServices, btnRemoveService});

        jTabbedPane1.addTab("New Sales Order", SalesOrderPane);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        salesOrderDate.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        salesOrderDate.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        salesOrderDate.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                salesOrderDateStateChanged(evt);
            }
        });
        salesOrderDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                salesOrderDatePropertyChange(evt);
            }
        });

        cmbCustomers.setEditable(true);
        cmbCustomers.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbCustomersPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbCustomers.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCustomersItemStateChanged(evt);
            }
        });

        cmbSearchVehicleRegNo.setEditable(true);
        cmbSearchVehicleRegNo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbSearchVehicleRegNoPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSearchVehicleRegNo, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salesOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );

        jPanel10Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbCustomers, cmbSearchVehicleRegNo, salesOrderDate});

        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(salesOrderDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbCustomers)
                        .addComponent(cmbSearchVehicleRegNo)))
                .addContainerGap())
        );

        jPanel10Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbCustomers, cmbSearchVehicleRegNo, salesOrderDate});

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel1.setText("Total Earnings:");

        lblTotalEarnings.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblTotalEarnings.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        tblAllSalesOrders.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        tblAllSalesOrders.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblAllSalesOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sales Order ID", "Order No.", "Date", "Time", "Customer", "Account No", "Vehicle Reg No.", "Added Person", "Amount", "Labour", "Paid", "Pending Amount", "Status", "debtid", "vehicle id", "Current Meter Reading", "Next Service Due", "Customer TP", "emp ID", "Employee", "tot disc", "Comments", "Garage Commission"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAllSalesOrders.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblAllSalesOrders.setRowHeight(20);
        tblAllSalesOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAllSalesOrdersMouseClicked(evt);
            }
        });
        tblAllSalesOrders.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblAllSalesOrdersKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblAllSalesOrders);
        if (tblAllSalesOrders.getColumnModel().getColumnCount() > 0) {
            tblAllSalesOrders.getColumnModel().getColumn(0).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(0).setMaxWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(1).setMinWidth(100);
            tblAllSalesOrders.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblAllSalesOrders.getColumnModel().getColumn(1).setMaxWidth(100);
            tblAllSalesOrders.getColumnModel().getColumn(4).setMinWidth(130);
            tblAllSalesOrders.getColumnModel().getColumn(4).setPreferredWidth(130);
            tblAllSalesOrders.getColumnModel().getColumn(4).setMaxWidth(130);
            tblAllSalesOrders.getColumnModel().getColumn(5).setMinWidth(70);
            tblAllSalesOrders.getColumnModel().getColumn(5).setPreferredWidth(70);
            tblAllSalesOrders.getColumnModel().getColumn(5).setMaxWidth(70);
            tblAllSalesOrders.getColumnModel().getColumn(6).setMinWidth(130);
            tblAllSalesOrders.getColumnModel().getColumn(6).setPreferredWidth(130);
            tblAllSalesOrders.getColumnModel().getColumn(6).setMaxWidth(130);
            tblAllSalesOrders.getColumnModel().getColumn(7).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(7).setMaxWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(8).setMinWidth(100);
            tblAllSalesOrders.getColumnModel().getColumn(8).setPreferredWidth(100);
            tblAllSalesOrders.getColumnModel().getColumn(8).setMaxWidth(100);
            tblAllSalesOrders.getColumnModel().getColumn(9).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(9).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(9).setMaxWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(10).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(10).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(10).setMaxWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(11).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(11).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(11).setMaxWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(12).setMinWidth(70);
            tblAllSalesOrders.getColumnModel().getColumn(12).setPreferredWidth(70);
            tblAllSalesOrders.getColumnModel().getColumn(12).setMaxWidth(70);
            tblAllSalesOrders.getColumnModel().getColumn(13).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(13).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(13).setMaxWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(14).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(14).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(14).setMaxWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(15).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(15).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(15).setMaxWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(16).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(16).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(16).setMaxWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(17).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(17).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(17).setMaxWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(18).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(18).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(18).setMaxWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(20).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(20).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(20).setMaxWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(21).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(21).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(21).setMaxWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(22).setMinWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(22).setPreferredWidth(0);
            tblAllSalesOrders.getColumnModel().getColumn(22).setMaxWidth(0);
        }

        tblViewSalesOrderContains.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        tblViewSalesOrderContains.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Code", "Part No", "Description", "Quantity", "Discount", "Unit Price", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tblViewSalesOrderContains);
        if (tblViewSalesOrderContains.getColumnModel().getColumnCount() > 0) {
            tblViewSalesOrderContains.getColumnModel().getColumn(0).setMinWidth(0);
            tblViewSalesOrderContains.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblViewSalesOrderContains.getColumnModel().getColumn(0).setMaxWidth(0);
            tblViewSalesOrderContains.getColumnModel().getColumn(2).setMinWidth(120);
            tblViewSalesOrderContains.getColumnModel().getColumn(2).setPreferredWidth(120);
            tblViewSalesOrderContains.getColumnModel().getColumn(2).setMaxWidth(120);
            tblViewSalesOrderContains.getColumnModel().getColumn(6).setMinWidth(50);
            tblViewSalesOrderContains.getColumnModel().getColumn(6).setPreferredWidth(50);
            tblViewSalesOrderContains.getColumnModel().getColumn(6).setMaxWidth(50);
        }

        txtVNextServiceDue.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblTotal2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal2.setText("Total :");

        txtViewBalance.setEditable(false);
        txtViewBalance.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtViewBalance.setText("0.00");
        txtViewBalance.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblRS1.setText("Rs.");

        txtViewTot.setEditable(false);
        txtViewTot.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtViewTot.setText("0.00");
        txtViewTot.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Debts:");

        jLabel11.setText("Rs.");

        txtPendingAmount.setEditable(false);
        txtPendingAmount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPendingAmount.setText("0.00");
        txtPendingAmount.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtPendingAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPendingAmountActionPerformed(evt);
            }
        });

        lblRS3.setText("Rs.");

        txtVCurrentMeter.setEditable(false);
        txtVCurrentMeter.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtVCurrentMeter.setText("0");
        txtVCurrentMeter.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblBalance3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBalance3.setText("Current Meter Reading :");

        txtVIewNextService.setEditable(false);
        txtVIewNextService.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtVIewNextService.setText("0");
        txtVIewNextService.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblBalance4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBalance4.setText("Next Service Due :");

        jLabel22.setText("Km");

        jLabel23.setText("Km");

        lblBalance2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBalance2.setText("Balance :");

        lblBalance8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBalance8.setText("Paid :");

        lblBalance10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBalance10.setText("Garage Commission:");

        txtViewPaid.setEditable(false);
        txtViewPaid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtViewPaid.setText("0.00");
        txtViewPaid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtViewPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtViewPaidActionPerformed(evt);
            }
        });

        lblRS9.setText("Rs.");

        txtViewGarageCommission.setEditable(false);
        txtViewGarageCommission.setText("0.00");

        jLabel32.setText("Rs:");

        txtViewComments.setColumns(20);
        txtViewComments.setRows(5);
        txtViewComments.setWrapStyleWord(true);
        txtViewComments.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Other comments", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N
        jScrollPane6.setViewportView(txtViewComments);

        javax.swing.GroupLayout txtVNextServiceDueLayout = new javax.swing.GroupLayout(txtVNextServiceDue);
        txtVNextServiceDue.setLayout(txtVNextServiceDueLayout);
        txtVNextServiceDueLayout.setHorizontalGroup(
            txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                                .addComponent(lblTotal2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblRS1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtViewTot))
                            .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                                .addComponent(lblBalance4)
                                .addGap(49, 49, 49)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtVIewNextService, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                        .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                                .addGap(187, 187, 187)
                                .addComponent(jLabel23))
                            .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblBalance3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtVCurrentMeter)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblBalance2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblBalance8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblRS3)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRS9)))
                    .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                        .addComponent(lblBalance10)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel32)))
                .addGap(28, 28, 28)
                .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtViewGarageCommission, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtViewPaid, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPendingAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtViewBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(143, Short.MAX_VALUE))
        );
        txtVNextServiceDueLayout.setVerticalGroup(
            txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtVNextServiceDueLayout.createSequentialGroup()
                .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                        .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, txtVNextServiceDueLayout.createSequentialGroup()
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(1, 1, 1))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, txtVNextServiceDueLayout.createSequentialGroup()
                                    .addGap(36, 36, 36)
                                    .addComponent(lblBalance3)))
                            .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(txtVCurrentMeter, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                                .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblBalance4)
                                    .addComponent(txtVIewNextService, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22))
                                .addGap(20, 20, 20)
                                .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblTotal2)
                                    .addComponent(lblRS1)
                                    .addComponent(txtViewTot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtViewBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblRS3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPendingAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblRS9)))))
                    .addGroup(txtVNextServiceDueLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBalance10)
                            .addComponent(jLabel32)
                            .addComponent(txtViewGarageCommission, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(txtVNextServiceDueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtViewPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblBalance8)))
                        .addGap(7, 7, 7)
                        .addComponent(lblBalance2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(11, 11, 11)))
                .addContainerGap())
        );

        txtVNextServiceDueLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtVIewNextService, txtViewTot});

        btnAddReturn.setText("Add Return");
        btnAddReturn.setEnabled(false);
        btnAddReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddReturnActionPerformed(evt);
            }
        });

        btnAddPayment.setText("Payment");
        btnAddPayment.setEnabled(false);
        btnAddPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPaymentActionPerformed(evt);
            }
        });

        btnReprint.setText("Print");
        btnReprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReprintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(btnAddPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAddReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReprint)
                .addGap(17, 17, 17))
        );

        jPanel11Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddPayment, btnAddReturn, btnReprint});

        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddReturn)
                    .addComponent(btnAddPayment)
                    .addComponent(btnReprint))
                .addContainerGap())
        );

        jPanel11Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddPayment, btnAddReturn, btnReprint});

        lblRS2.setText("Rs.");

        tblViewSalesOrderServices.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        tblViewSalesOrderServices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "service no", "Service No.", "Description", "Qty.", "Discount", "Cost", "Amount", "Done By"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tblViewSalesOrderServices);
        if (tblViewSalesOrderServices.getColumnModel().getColumnCount() > 0) {
            tblViewSalesOrderServices.getColumnModel().getColumn(0).setMinWidth(0);
            tblViewSalesOrderServices.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblViewSalesOrderServices.getColumnModel().getColumn(0).setMaxWidth(0);
            tblViewSalesOrderServices.getColumnModel().getColumn(1).setMinWidth(75);
            tblViewSalesOrderServices.getColumnModel().getColumn(1).setPreferredWidth(75);
            tblViewSalesOrderServices.getColumnModel().getColumn(1).setMaxWidth(75);
            tblViewSalesOrderServices.getColumnModel().getColumn(2).setMinWidth(120);
            tblViewSalesOrderServices.getColumnModel().getColumn(2).setPreferredWidth(120);
            tblViewSalesOrderServices.getColumnModel().getColumn(2).setMaxWidth(120);
            tblViewSalesOrderServices.getColumnModel().getColumn(6).setMinWidth(50);
            tblViewSalesOrderServices.getColumnModel().getColumn(6).setPreferredWidth(50);
            tblViewSalesOrderServices.getColumnModel().getColumn(6).setMaxWidth(50);
        }

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Parts:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Services");

        tblReturnDetails.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        tblReturnDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Part No.", "Description", "Return qty.", "Financial Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(tblReturnDetails);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Returns");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(lblRS2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalEarnings, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jScrollPane5)
                            .addComponent(jScrollPane7)
                            .addComponent(jScrollPane11))
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtVNextServiceDue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTotalEarnings, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblRS2)))
                                .addGap(26, 26, 26))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addComponent(txtVNextServiceDue, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jTabbedPane1.addTab("All Sales Orders", jPanel2);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        salesOrderDate.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        salesOrderDate3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        salesOrderDate3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                salesOrderDate3StateChanged(evt);
            }
        });
        salesOrderDate3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                salesOrderDate3PropertyChange(evt);
            }
        });

        cmbCustomers3.setEditable(true);
        cmbCustomers3.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbCustomers3PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbCustomers3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCustomers3ItemStateChanged(evt);
            }
        });

        cmbSearchVehicleRegNo3.setEditable(true);
        cmbSearchVehicleRegNo3.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbSearchVehicleRegNo3PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbCustomers3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSearchVehicleRegNo3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salesOrderDate3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbCustomers3, cmbSearchVehicleRegNo3, salesOrderDate3});

        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbCustomers3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salesOrderDate3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbSearchVehicleRegNo3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbCustomers3, cmbSearchVehicleRegNo3, salesOrderDate3});

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel25.setText("Total Earnings:");

        lblRS6.setText("Rs.");

        lblTotalEarnings1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblTotalEarnings1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        tblSavedOrders.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        tblSavedOrders.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblSavedOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sales Order ID", "Order No.", "Date", "Time", "Customer", "Account No", "Vehicle Reg No.", "Added Person", "Amount", "Labour", "Paid", "vehicle id", "Current Meter Reading", "Next Service Due", "Customer TP", "emp ID", "Employee", "tot disc"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSavedOrders.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblSavedOrders.setRowHeight(20);
        tblSavedOrders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSavedOrdersMouseClicked(evt);
            }
        });
        tblSavedOrders.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblSavedOrdersKeyReleased(evt);
            }
        });
        jScrollPane8.setViewportView(tblSavedOrders);
        if (tblSavedOrders.getColumnModel().getColumnCount() > 0) {
            tblSavedOrders.getColumnModel().getColumn(0).setMinWidth(0);
            tblSavedOrders.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblSavedOrders.getColumnModel().getColumn(0).setMaxWidth(0);
            tblSavedOrders.getColumnModel().getColumn(1).setMinWidth(100);
            tblSavedOrders.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblSavedOrders.getColumnModel().getColumn(1).setMaxWidth(100);
            tblSavedOrders.getColumnModel().getColumn(4).setMinWidth(130);
            tblSavedOrders.getColumnModel().getColumn(4).setPreferredWidth(130);
            tblSavedOrders.getColumnModel().getColumn(4).setMaxWidth(130);
            tblSavedOrders.getColumnModel().getColumn(5).setMinWidth(70);
            tblSavedOrders.getColumnModel().getColumn(5).setPreferredWidth(70);
            tblSavedOrders.getColumnModel().getColumn(5).setMaxWidth(70);
            tblSavedOrders.getColumnModel().getColumn(6).setMinWidth(130);
            tblSavedOrders.getColumnModel().getColumn(6).setPreferredWidth(130);
            tblSavedOrders.getColumnModel().getColumn(6).setMaxWidth(130);
            tblSavedOrders.getColumnModel().getColumn(7).setMinWidth(0);
            tblSavedOrders.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblSavedOrders.getColumnModel().getColumn(7).setMaxWidth(0);
            tblSavedOrders.getColumnModel().getColumn(8).setMinWidth(100);
            tblSavedOrders.getColumnModel().getColumn(8).setPreferredWidth(100);
            tblSavedOrders.getColumnModel().getColumn(8).setMaxWidth(100);
            tblSavedOrders.getColumnModel().getColumn(9).setMinWidth(0);
            tblSavedOrders.getColumnModel().getColumn(9).setPreferredWidth(0);
            tblSavedOrders.getColumnModel().getColumn(9).setMaxWidth(0);
            tblSavedOrders.getColumnModel().getColumn(10).setMinWidth(0);
            tblSavedOrders.getColumnModel().getColumn(10).setPreferredWidth(0);
            tblSavedOrders.getColumnModel().getColumn(10).setMaxWidth(0);
            tblSavedOrders.getColumnModel().getColumn(11).setMinWidth(0);
            tblSavedOrders.getColumnModel().getColumn(11).setPreferredWidth(0);
            tblSavedOrders.getColumnModel().getColumn(11).setMaxWidth(0);
            tblSavedOrders.getColumnModel().getColumn(12).setMinWidth(0);
            tblSavedOrders.getColumnModel().getColumn(12).setPreferredWidth(0);
            tblSavedOrders.getColumnModel().getColumn(12).setMaxWidth(0);
            tblSavedOrders.getColumnModel().getColumn(13).setMinWidth(0);
            tblSavedOrders.getColumnModel().getColumn(13).setPreferredWidth(0);
            tblSavedOrders.getColumnModel().getColumn(13).setMaxWidth(0);
            tblSavedOrders.getColumnModel().getColumn(14).setMinWidth(0);
            tblSavedOrders.getColumnModel().getColumn(14).setPreferredWidth(0);
            tblSavedOrders.getColumnModel().getColumn(14).setMaxWidth(0);
            tblSavedOrders.getColumnModel().getColumn(15).setMinWidth(0);
            tblSavedOrders.getColumnModel().getColumn(15).setPreferredWidth(0);
            tblSavedOrders.getColumnModel().getColumn(15).setMaxWidth(0);
            tblSavedOrders.getColumnModel().getColumn(17).setMinWidth(0);
            tblSavedOrders.getColumnModel().getColumn(17).setPreferredWidth(0);
            tblSavedOrders.getColumnModel().getColumn(17).setMaxWidth(0);
        }

        tblSavedOrderDetails.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        tblSavedOrderDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Code", "Part No", "Description", "Quantity", "Discount", "Unit Price", "Amount", "Commision"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(tblSavedOrderDetails);
        if (tblSavedOrderDetails.getColumnModel().getColumnCount() > 0) {
            tblSavedOrderDetails.getColumnModel().getColumn(0).setMinWidth(0);
            tblSavedOrderDetails.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblSavedOrderDetails.getColumnModel().getColumn(0).setMaxWidth(0);
            tblSavedOrderDetails.getColumnModel().getColumn(1).setMinWidth(75);
            tblSavedOrderDetails.getColumnModel().getColumn(1).setPreferredWidth(75);
            tblSavedOrderDetails.getColumnModel().getColumn(1).setMaxWidth(75);
            tblSavedOrderDetails.getColumnModel().getColumn(2).setMinWidth(120);
            tblSavedOrderDetails.getColumnModel().getColumn(2).setPreferredWidth(120);
            tblSavedOrderDetails.getColumnModel().getColumn(2).setMaxWidth(120);
            tblSavedOrderDetails.getColumnModel().getColumn(6).setMinWidth(50);
            tblSavedOrderDetails.getColumnModel().getColumn(6).setPreferredWidth(50);
            tblSavedOrderDetails.getColumnModel().getColumn(6).setMaxWidth(50);
            tblSavedOrderDetails.getColumnModel().getColumn(7).setMinWidth(0);
            tblSavedOrderDetails.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblSavedOrderDetails.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        tblSavedOrderServices.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        tblSavedOrderServices.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "service no", "Service No.", "Description", "Qty.", "Discount", "Cost", "Amount", "Done By"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(tblSavedOrderServices);
        if (tblSavedOrderServices.getColumnModel().getColumnCount() > 0) {
            tblSavedOrderServices.getColumnModel().getColumn(0).setMinWidth(0);
            tblSavedOrderServices.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblSavedOrderServices.getColumnModel().getColumn(0).setMaxWidth(0);
            tblSavedOrderServices.getColumnModel().getColumn(1).setMinWidth(75);
            tblSavedOrderServices.getColumnModel().getColumn(1).setPreferredWidth(75);
            tblSavedOrderServices.getColumnModel().getColumn(1).setMaxWidth(75);
            tblSavedOrderServices.getColumnModel().getColumn(2).setMinWidth(120);
            tblSavedOrderServices.getColumnModel().getColumn(2).setPreferredWidth(120);
            tblSavedOrderServices.getColumnModel().getColumn(2).setMaxWidth(120);
            tblSavedOrderServices.getColumnModel().getColumn(6).setMinWidth(50);
            tblSavedOrderServices.getColumnModel().getColumn(6).setPreferredWidth(50);
            tblSavedOrderServices.getColumnModel().getColumn(6).setMaxWidth(50);
        }

        txtVNextServiceDue1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblTotal3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal3.setText("Total :");

        txtViewPaid1.setEditable(false);
        txtViewPaid1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtViewPaid1.setText("0.00");
        txtViewPaid1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblRS7.setText("Rs.");

        txtViewTot1.setEditable(false);
        txtViewTot1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtViewTot1.setText("0.00");
        txtViewTot1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel26.setText("Debts:");

        txtPendingAmount1.setEditable(false);
        txtPendingAmount1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPendingAmount1.setText("0.00");
        txtPendingAmount1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblRS8.setText("Rs.");

        txtVCurrentMeter1.setEditable(false);
        txtVCurrentMeter1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtVCurrentMeter1.setText("0");
        txtVCurrentMeter1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblBalance6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBalance6.setText("Current Meter Reading :");

        txtVIewNextService1.setEditable(false);
        txtVIewNextService1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtVIewNextService1.setText("0");
        txtVIewNextService1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblBalance7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBalance7.setText("Next Service Due :");

        jLabel28.setText("Km");

        jLabel29.setText("Km");

        jLabel27.setText("Rs.");

        lblBalance55.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBalance55.setText("Paid :");

        txtViewBalance2.setEditable(false);
        txtViewBalance2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtViewBalance2.setText("0.00");
        txtViewBalance2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel33.setText("Rs.");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel34.setText("Balance :");

        javax.swing.GroupLayout txtVNextServiceDue1Layout = new javax.swing.GroupLayout(txtVNextServiceDue1);
        txtVNextServiceDue1.setLayout(txtVNextServiceDue1Layout);
        txtVNextServiceDue1Layout.setHorizontalGroup(
            txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtVNextServiceDue1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblBalance6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblBalance7)
                    .addComponent(lblTotal3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(txtVNextServiceDue1Layout.createSequentialGroup()
                        .addComponent(lblRS7, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtViewTot1))
                    .addGroup(txtVNextServiceDue1Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVIewNextService1))
                    .addGroup(txtVNextServiceDue1Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVCurrentMeter1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtVNextServiceDue1Layout.createSequentialGroup()
                        .addComponent(lblBalance55, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33))
                    .addGroup(txtVNextServiceDue1Layout.createSequentialGroup()
                        .addGroup(txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRS8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtPendingAmount1)
                    .addComponent(txtViewBalance2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtViewPaid1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        txtVNextServiceDue1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtPendingAmount1, txtVCurrentMeter1, txtVIewNextService1, txtViewBalance2, txtViewPaid1, txtViewTot1});

        txtVNextServiceDue1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel26, jLabel34, lblBalance55});

        txtVNextServiceDue1Layout.setVerticalGroup(
            txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtVNextServiceDue1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(txtVNextServiceDue1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtViewPaid1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(txtViewBalance2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRS8)
                            .addComponent(txtPendingAmount1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)))
                    .addGroup(txtVNextServiceDue1Layout.createSequentialGroup()
                        .addGroup(txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtVCurrentMeter1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBalance6)
                            .addComponent(lblBalance55))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtVIewNextService1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(lblBalance7))
                        .addGap(9, 9, 9)
                        .addGroup(txtVNextServiceDue1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotal3)
                            .addComponent(lblRS7)
                            .addComponent(txtViewTot1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        txtVNextServiceDue1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtPendingAmount1, txtVCurrentMeter1, txtVIewNextService1, txtViewBalance2, txtViewPaid1, txtViewTot1});

        txtVNextServiceDue1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel26, jLabel34, lblBalance55});

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel30.setText("Parts:");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel31.setText("Services");

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete2.setText("Delete");
        btnDelete2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(168, Short.MAX_VALUE)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDelete2, btnEdit});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete2)))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDelete2, btnEdit});

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblRS6)
                        .addGap(9, 9, 9)
                        .addComponent(lblTotalEarnings1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtVNextServiceDue1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalEarnings1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblRS6))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVNextServiceDue1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel25, lblTotalEarnings1});

        jTabbedPane1.addTab("Saved", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 985, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 627, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtLabourCostKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLabourCostKeyReleased
        try {
            double total = Double.parseDouble(txtLabourCost.getText());
            txtTotal.setText("" + (countTotal(tblOrderContains) + total));
            txtBalance.setText(calculateBalance(Double.parseDouble(txtPaid.getText().trim())));
            checkBillButton();
        } catch (NumberFormatException numberFormatException) {
        }
    }//GEN-LAST:event_txtLabourCostKeyReleased

    private void txtLabourCostFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLabourCostFocusGained
        txtLabourCost.selectAll();
    }//GEN-LAST:event_txtLabourCostFocusGained

    private void txtLabourCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLabourCostActionPerformed
        txtPaid.requestFocus();
    }//GEN-LAST:event_txtLabourCostActionPerformed

    private void txtLabourCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLabourCostKeyPressed
//        try {
//            double total = Double.parseDouble(txtLabourCost.getText());
//            txtTotal.setText("" + countTotal(tblOrderContains));
//            checkBillButton();
//        } catch (NumberFormatException numberFormatException) {
//        }
    }//GEN-LAST:event_txtLabourCostKeyPressed

    private void txtLabourCost1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLabourCost1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLabourCost1ActionPerformed

    private void txtLabourCost1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLabourCost1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLabourCost1FocusGained

    private void txtLabourCost1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLabourCost1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLabourCost1KeyPressed

    private void txtLabourCost1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLabourCost1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLabourCost1KeyReleased

    private void sparepartscostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sparepartscostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sparepartscostActionPerformed

    private void btnHIddenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHIddenActionPerformed
        try {
            SalesOrderController.loadAllSalesOrders(salesOrdersDtm);
            SalesOrderController.loadAllSavedSalesOrders(savedOrdersDtm);
            savedPartsDtm.setRowCount(0);
            savedServicesDtm.setRowCount(0);

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnHIddenActionPerformed

    private void btnDelete2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDelete2ActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int selectedRows = tblSavedOrders.getSelectedRowCount();
        if (selectedRows == 1) {
            int selected = tblSavedOrders.getSelectedRow();
            int soID = Integer.parseInt(tblSavedOrders.getValueAt(selected, 0).toString());
            UpdateSalesOrder uSalesOrder = new UpdateSalesOrder(soID, btnHIdden);
            uSalesOrder.setVisible(true);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void tblSavedOrdersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblSavedOrdersKeyReleased
        int keyCode = evt.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            int selectedRow = tblSavedOrders.getSelectedRow();
            int salesOrderID = Integer.parseInt((String) tblSavedOrders.getValueAt(selectedRow, 0));

            try {
                loadSavedOrderDataOnSelect();
                SalesOrderDetailController.loadAllSalesOrderDetails(savedPartsDtm, salesOrderID);
                SalesOrderController.loadAllSalesOrderServices(salesOrderID, savedServicesDtm);
                checkPaymentButton();
              //  checkViewButtons1();

            } catch (ClassNotFoundException ex) {
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            checkViewButtons();
            //checkViewButtons1();
        }
    }//GEN-LAST:event_tblSavedOrdersKeyReleased

    private void tblSavedOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSavedOrdersMouseClicked
        int count = evt.getClickCount();
        int selectedRow = tblSavedOrders.getSelectedRow();
        int selectedRows = tblSavedOrders.getSelectedRowCount();
        int salesOrderID = Integer.parseInt((String) tblSavedOrders.getValueAt(selectedRow, 0));
        loadSavedOrderDataOnSelect();
       // checkViewButtons1();

        try {
            if (count == 1) {

                SalesOrderDetailController.loadAllSalesOrderDetails(savedPartsDtm, salesOrderID);

                SalesOrderController.loadAllSalesOrderServices(salesOrderID, savedServicesDtm);
            } else if (count == 2 && selectedRows == 1) {
                int soID = Integer.parseInt(tblSavedOrders.getValueAt(selectedRow, 0).toString());
                UpdateSalesOrder uSalesOrder = new UpdateSalesOrder(soID, btnHIdden);
                uSalesOrder.setVisible(true);
            }

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_tblSavedOrdersMouseClicked

    private void cmbSearchVehicleRegNo3PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbSearchVehicleRegNo3PopupMenuWillBecomeInvisible
       try {
           savedOrdersDtm= (DefaultTableModel)  tblSavedOrders.getModel();
            String selectedItem = cmbSearchVehicleRegNo.getSelectedItem().toString();

             final TableRowSorter<TableModel> sorter =  new TableRowSorter<TableModel>(savedOrdersDtm);
        tblSavedOrders.setRowSorter(sorter);

            sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + selectedItem)));
             lblTotalEarnings1.setText(String.format("%.2f", caculateTotalEarningsSaved()));

        } catch (Exception e) {

        }        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSearchVehicleRegNo3PopupMenuWillBecomeInvisible

    private void cmbCustomers3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCustomers3ItemStateChanged
        try {
            refreshCustomerComboSaved();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        } catch (Exception ex) {

        }        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCustomers3ItemStateChanged

    private void cmbCustomers3PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbCustomers3PopupMenuWillBecomeInvisible
    
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCustomers3PopupMenuWillBecomeInvisible

    private void salesOrderDate3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_salesOrderDate3PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_salesOrderDate3PropertyChange

    private void salesOrderDate3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_salesOrderDate3StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_salesOrderDate3StateChanged

    private void btnReprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReprintActionPerformed
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String format = timeFormat.format(new Date());
            int orderTable = tblViewSalesOrderContains.getRowCount();
            ResultSet profile = ProfileController.getDealerDetails();
            profile.next();
            ArrayList<String> items = new ArrayList<>();
            int order_count = tblViewSalesOrderContains.getRowCount();
            int service_count = tblViewSalesOrderServices.getRowCount();
            int tot_row_count = order_count + service_count;

            for (int i = 0; i < orderTable; i++) {
                String itempart = String.valueOf(salesOrderDetailDtm.getValueAt(i, 1));

                if (items.contains(itempart)) {
                } else {
                    items.add(String.valueOf(salesOrderDetailDtm.getValueAt(i, 1)));
                }
            }

            double noQty = 0;
            for (int i = 0; i < orderTable; i++) {
                noQty += Double.parseDouble(salesOrderDetailDtm.getValueAt(i, 3).toString());
            }
            int numberofItem = items.size();
            String snoofitems = String.valueOf(numberofItem);
            String snoqty = String.valueOf(noQty);
            int r = tblAllSalesOrders.getSelectedRow();
            String in = String.valueOf(tblAllSalesOrders.getValueAt(r, 1));
            String customer = tblAllSalesOrders.getValueAt(r, 4).toString();
            double total = Double.parseDouble(tblAllSalesOrders.getValueAt(r, 8).toString());
            double paid = Double.parseDouble(tblAllSalesOrders.getValueAt(r, 10).toString());

            double balence = total - paid;
            double labAmount = Double.parseDouble(tblAllSalesOrders.getValueAt(r, 9).toString());
            String cusAccountNumber = "775230";

            String invoiceNo = in;
            double spare_parts_cost = total - labAmount;
            //String spare_prices = String.format("%.2f", spare_parts_cost);
            //----Owner Detail-------------------------------
            String shopName = profile.getString(2);
            String address = profile.getString(10);
            String tel = profile.getString(8);
            String email = profile.getString(11);

            //----------------------------------
            String startTime = format;
            double cashAmount = Double.parseDouble(txtViewPaid.getText());
            double changeAmount = Double.parseDouble(txtViewBalance.getText());

            String noOFItems = snoofitems;
            String noOfQty = snoqty;
            String vehicle_no = tblAllSalesOrders.getValueAt(r, 6).toString();
            String cus_tel = tblAllSalesOrders.getValueAt(r, 17).toString();
            String ood_meeter = tblAllSalesOrders.getValueAt(r, 15).toString();
            String next_meter = tblAllSalesOrders.getValueAt(r, 16).toString();
            double total_without_vat = total + Double.parseDouble(tblAllSalesOrders.getValueAt(r, 20).toString());
            double total_discount = Double.parseDouble(tblAllSalesOrders.getValueAt(r, 20).toString());
            String checked_by = "";
            String employee = tblAllSalesOrders.getValueAt(r, 19).toString();
            printBill(salesOrderDetailDtm, serviceDetailDtm, customer, cusAccountNumber, total, paid, balence, invoiceNo, labAmount, spare_parts_cost, cashAmount, changeAmount, noOFItems, noOfQty, startTime, shopName, address, tel, tot_row_count, email, vehicle_no, cus_tel, ood_meeter, next_meter, total_without_vat, total_discount, checked_by, employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnReprintActionPerformed

    private void btnAddPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPaymentActionPerformed
        try {
            int selectedRow = tblAllSalesOrders.getSelectedRow();
            int debtID = Integer.parseInt(tblAllSalesOrders.getValueAt(selectedRow, 13).toString());
            double pendingAmount = Double.parseDouble(tblAllSalesOrders.getValueAt(selectedRow, 11).toString());
            NewPayment newPayment = new NewPayment(null, true, debtID, pendingAmount, new DefaultTableModel());
            newPayment.setVisible(true);
            SalesOrderController.loadAllSalesOrders(salesOrdersDtm);
            loadSalesOrderDataOnSelect();
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnAddPaymentActionPerformed

    private void btnAddReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddReturnActionPerformed
        try {
            int selectedRow = tblAllSalesOrders.getSelectedRow();
            int orderID = Integer.parseInt(tblAllSalesOrders.getValueAt(selectedRow, 0).toString());
            AddNewReturn newReturn = new AddNewReturn(null, true, orderID);
            newReturn.setVisible(true);
            SalesOrderController.loadAllSalesOrders(salesOrdersDtm);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnAddReturnActionPerformed

    private void txtPendingAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPendingAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPendingAmountActionPerformed

    private void tblAllSalesOrdersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblAllSalesOrdersKeyReleased
        int keyCode = evt.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            int selectedRow = tblAllSalesOrders.getSelectedRow();
            int salesOrderID = Integer.parseInt((String) tblAllSalesOrders.getValueAt(selectedRow, 0));

            try {
                loadSalesOrderDataOnSelect();
                SalesOrderDetailController.loadAllSalesOrderDetails(salesOrderDetailDtm, salesOrderID);
                SalesOrderController.loadAllSalesOrderServices(salesOrderID, serviceDetailDtm);
                CustomerReturnsController.loadAllReturnData(salesOrderID, returnDetailsDtm);
                checkPaymentButton();
                checkViewButtons();
            } catch (ClassNotFoundException ex) {
            } catch (SQLException ex) {
                //ex.printStackTrace();
            }
            checkViewButtons();
        }
    }//GEN-LAST:event_tblAllSalesOrdersKeyReleased

    private void tblAllSalesOrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAllSalesOrdersMouseClicked
        int selectedRow = tblAllSalesOrders.getSelectedRow();
        int salesOrderID = Integer.parseInt((String) tblAllSalesOrders.getValueAt(selectedRow, 0));

        try {
            loadSalesOrderDataOnSelect();
            SalesOrderDetailController.loadAllSalesOrderDetails(salesOrderDetailDtm, salesOrderID);
            SalesOrderController.loadAllSalesOrderServices(salesOrderID, serviceDetailDtm);
            CustomerReturnsController.loadAllReturnData(salesOrderID, returnDetailsDtm);
            checkPaymentButton();
            checkViewButtons();
            //double debtAmount = DebtController.checkPendingAmount(Integer.parseInt(tblAllSalesOrders.getValueAt(selectedRow, 0).toString()));
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //checkViewButtons();
    }//GEN-LAST:event_tblAllSalesOrdersMouseClicked

    private void cmbSearchVehicleRegNoPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbSearchVehicleRegNoPopupMenuWillBecomeInvisible

        try {
            salesOrdersDtm = (DefaultTableModel) tblAllSalesOrders.getModel();
            String selectedItem = cmbSearchVehicleRegNo.getSelectedItem().toString();

            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(salesOrdersDtm);
            tblAllSalesOrders.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + selectedItem)));
            lblTotalEarnings.setText(String.format("%.2f", caculateTotalEarnings()));

        } catch (Exception e) {

        }
    }//GEN-LAST:event_cmbSearchVehicleRegNoPopupMenuWillBecomeInvisible

    private void cmbCustomersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCustomersItemStateChanged
        try {
            refreshCustomerCombo();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_cmbCustomersItemStateChanged

    private void cmbCustomersPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbCustomersPopupMenuWillBecomeInvisible
        /*salesOrdersDtm = (DefaultTableModel) tblAllSalesOrders.getModel();
        String selectedItem = cmbCustomers.getSelectedItem().toString();
        System.out.println("name"+selectedItem);
        try {
            String toString = cmbCustomers.getSelectedItem().toString();
            ResultSet customerDetails = CustomerController.getCustomerDetails("customer_name", toString);
            customerDetails.next();
            int custID = customerDetails.getInt(1);
            System.out.println("custid"+custID);
            fillVehicleRegNos(custID);

            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(salesOrdersDtm);
            tblAllSalesOrders.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + selectedItem)));
            lblTotalEarnings.setText("" + caculateTotalEarnings());

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }//GEN-LAST:event_cmbCustomersPopupMenuWillBecomeInvisible

    private void salesOrderDatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_salesOrderDatePropertyChange
        Date d = salesOrderDate.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = sdf.format(d);

        try {

            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(salesOrdersDtm);
            tblAllSalesOrders.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + curDate)));
            lblTotalEarnings.setText(String.format("%.2f", caculateTotalEarnings()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_salesOrderDatePropertyChange

    private void salesOrderDateStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_salesOrderDateStateChanged
        Date d = salesOrderDate.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = sdf.format(d);

        try {

            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(salesOrdersDtm);
            tblAllSalesOrders.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + curDate)));
            lblTotalEarnings.setText(String.format("%.2f", "" + caculateTotalEarnings()));
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }//GEN-LAST:event_salesOrderDateStateChanged

 
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            AddEmployee newEmployee = new AddEmployee(new JFrame(), true);
            newEmployee.setVisible(true);
            EmployeeController.fillEmployeeNames(cmbEmployee);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cmbEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmployeeActionPerformed
        String actionCString = evt.getActionCommand();
        if (actionCString.equals("comboBoxEdited")) {
            cmbSelectCustomer.requestFocus();
        }
    }//GEN-LAST:event_cmbEmployeeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        NewVehicle nw = new NewVehicle(new JFrame(), true);
        nw.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnRemoveAllServicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAllServicesActionPerformed
        removeAllTableItems(tblOrderServices);
        checkServiceButtons();
        checkBillButton();
        calculateServiceCost();
        calculate();
    }//GEN-LAST:event_btnRemoveAllServicesActionPerformed

    private void btnRemoveServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveServiceActionPerformed
        removeTableItems(tblOrderServices);
        checkServiceButtons();
        checkBillButton();
        calculateServiceCost();
        calculate();
    }//GEN-LAST:event_btnRemoveServiceActionPerformed

    private void btnAddNewServiceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddNewServiceKeyPressed
        int keyCode = evt.getKeyCode();
        if (keyCode == KeyEvent.VK_DOWN) {
            txtCurrentMeterReading.requestFocus();
        }
    }//GEN-LAST:event_btnAddNewServiceKeyPressed

    private void btnAddNewServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewServiceActionPerformed
        SelectService selectService = new SelectService(null, true, orderServicesDtm);
        selectService.setVisible(true);
        checkServiceButtons();
        checkBillButton();
        calculateServiceCost();
        calculate();
    }//GEN-LAST:event_btnAddNewServiceActionPerformed

    private void tblOrderServicesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblOrderServicesKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblOrderServicesKeyReleased

    private void btnAddNewCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewCustomerActionPerformed
        try {
            NewCustomer customer = new NewCustomer(new JFrame(), true);
            customer.setVisible(true);
            CustomerController.fillCustomerNames(cmbSelectCustomer);
            CustomerController.fillCustomerNames(cmbCustomers);
            CustomerController.fillCustomerNames(cmbCustomers3);
            
            //fillVehicleRegNos(0);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnAddNewCustomerActionPerformed

    private void cmbVehicleRegNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbVehicleRegNoActionPerformed
        String actionCString = evt.getActionCommand();
        if (actionCString.equals("comboBoxEdited")) {
            btnAddNewParts.requestFocus();
        }
    }//GEN-LAST:event_cmbVehicleRegNoActionPerformed

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

    private void btnAddNewPartsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddNewPartsKeyPressed

        //new part key press
        int keyCode = evt.getKeyCode();
        if (keyCode == KeyEvent.VK_DOWN) {
            btnAddNewService.requestFocus();
        }
    }//GEN-LAST:event_btnAddNewPartsKeyPressed

    private void btnAddNewPartsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewPartsActionPerformed

        SelectPart sp = new SelectPart(null, true, this, itemDtm);
        sp.setVisible(true);
    }//GEN-LAST:event_btnAddNewPartsActionPerformed

    private void btnRemoveAllInTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAllInTableActionPerformed
        removeAllTableItems(tblOrderContains);
        calculate();
    }//GEN-LAST:event_btnRemoveAllInTableActionPerformed

    private void btnRemoveFromTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveFromTableActionPerformed
        removeTableItems(tblOrderContains);
        calculate();
    }//GEN-LAST:event_btnRemoveFromTableActionPerformed

    private void cmbSelectCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSelectCustomerActionPerformed
        String actionCString = evt.getActionCommand();
        if (actionCString.equals("comboBoxEdited")) {
            cmbVehicleRegNo.requestFocus();
        }
    }//GEN-LAST:event_cmbSelectCustomerActionPerformed

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

    private void txtCommissionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCommissionKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCommissionKeyReleased

    private void txtCommissionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCommissionKeyPressed
        
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCommissionKeyPressed

    private void txtCommissionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCommissionFocusGained
        txtCommission.selectAll();// TODO add your handling code here:
    }//GEN-LAST:event_txtCommissionFocusGained

    private void txtCommissionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCommissionFocusLost
        
    }//GEN-LAST:event_txtCommissionFocusLost

    private void txtCommissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCommissionActionPerformed
        txtPaid.requestFocus();
        //try {
            //	double paidCommissionAmount = Double.parseDouble(txtCommission.getText());
            //	txtBalance.setText("" + calculateBalance(paidCommissionAmount));
            //	} catch (NumberFormatException numberFormatException) {
            //	} catch (Exception ex) {
            //
            //	}
        //	btnBill.doClick();
        //        // TODO add your handling code here:
    }//GEN-LAST:event_txtCommissionActionPerformed

    private void txtNextServiceDueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNextServiceDueKeyReleased
        ValidateValues.validateTextFieldForNumber(txtNextServiceDue);
    }//GEN-LAST:event_txtNextServiceDueKeyReleased

    private void txtNextServiceDueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNextServiceDueFocusLost
        String val = txtNextServiceDue.getText();
        if (val.isEmpty() || val.equals(null)) {
            txtNextServiceDue.setText("0");
        }
    }//GEN-LAST:event_txtNextServiceDueFocusLost

    private void txtNextServiceDueFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNextServiceDueFocusGained
        txtNextServiceDue.selectAll();
    }//GEN-LAST:event_txtNextServiceDueFocusGained

    private void txtNextServiceDueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNextServiceDueActionPerformed

        txtNextServiceDue.requestFocus();
        txtCommission.requestFocus();
    }//GEN-LAST:event_txtNextServiceDueActionPerformed

    private void txtCurrentMeterReadingKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCurrentMeterReadingKeyReleased
        ValidateValues.validateTextFieldForNumber(txtCurrentMeterReading);
    }//GEN-LAST:event_txtCurrentMeterReadingKeyReleased

    private void txtCurrentMeterReadingFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCurrentMeterReadingFocusLost
        String val = txtCurrentMeterReading.getText();
        if (val.isEmpty() || val.equals(null)) {
            txtCurrentMeterReading.setText("0");
        }
    }//GEN-LAST:event_txtCurrentMeterReadingFocusLost

    private void txtCurrentMeterReadingFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCurrentMeterReadingFocusGained
        txtCurrentMeterReading.selectAll();
    }//GEN-LAST:event_txtCurrentMeterReadingFocusGained

    private void txtCurrentMeterReadingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrentMeterReadingActionPerformed

        txtNextServiceDue.requestFocus();
    }//GEN-LAST:event_txtCurrentMeterReadingActionPerformed

    private void btnBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBillActionPerformed

        try {
            Object options[] = {"Bill", "Save", "Estimate"};
            double balance = Double.parseDouble(txtBalance.getText());
            String customer = cmbSelectCustomer.getSelectedItem().toString();
            ResultSet customerDetails = CustomerController.getCustomerDetails("customer_name", customer);

            customerDetails.next();
            int custID = customerDetails.getInt(1);
            int overDueCount = DebtController.getOverdueInvoiceCount(custID);
            if (customer.equals("General") && balance < 0) {
                JOptionPane.showMessageDialog(this, "You can't invoice to the General customer", "Wrong Selection", JOptionPane.WARNING_MESSAGE);
            }  else if (!(customer.equals("General")) && overDueCount > 0) {
                JOptionPane.showMessageDialog(this, "Some overdue invoices exists. You can't invoice to this customer", "Overdue invoices found", JOptionPane.WARNING_MESSAGE);

            } else {
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
                                ex.printStackTrace();
                             }
                        }
                    } else {
                        int salesOrderID = addOrderToDB(2);
                    }
                } else if (option == 1) {
                    int salesOrderID = addOrderToDB(3);
                    
                } else if (option == 2) {
                    int EstimateId =addEstimateToDB(1);

                }else if (option == 3) {  ///new F1
                    int salesOrderID = addOrderToDB(4);
                }
    
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SalesOrderPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SalesOrderPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBillActionPerformed

    private void txtBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBalanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBalanceActionPerformed

    private void txtPaidKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidKeyReleased
try {
      double paidAmount = Double.parseDouble(txtPaid.getText());
            txtBalance.setText("" + calculateBalance(paidAmount));
            checkBillButton();
//        double total = Double.parseDouble(txtTotal.getText());
//        double paid = Double.parseDouble(txtPaid.getText());
//        txtBalance.setText(customFormat(paid-total)+"");      
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

    private void txtPaidFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaidFocusGained
        txtPaid.selectAll();
    }//GEN-LAST:event_txtPaidFocusGained

    private void txtPaidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaidFocusLost
        String val = txtPaid.getText();
        if (val.isEmpty() || val.equals(null)) {
            txtPaid.setText("0.00");
        }
    }//GEN-LAST:event_txtPaidFocusLost

    private void txtPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaidActionPerformed
        try {
            double paidAmount = Double.parseDouble(txtPaid.getText());
            txtBalance.setText("" + calculateBalance(paidAmount));
        } catch (NumberFormatException numberFormatException) {
        } catch (Exception ex) {

        }
        btnBill.doClick();
    }//GEN-LAST:event_txtPaidActionPerformed

    private void tblOrderContainsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblOrderContainsKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblOrderContainsKeyReleased

    private void txtViewPaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtViewPaidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtViewPaidActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel SalesOrderPane;
    private javax.swing.JButton btnAddNewCustomer;
    private javax.swing.JButton btnAddNewParts;
    private javax.swing.JButton btnAddNewService;
    private javax.swing.JButton btnAddPayment;
    private javax.swing.JButton btnAddReturn;
    private javax.swing.JButton btnBill;
    private javax.swing.JButton btnDelete2;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHIdden;
    private javax.swing.JButton btnRemoveAllInTable;
    private javax.swing.JButton btnRemoveAllServices;
    private javax.swing.JButton btnRemoveFromTable;
    private javax.swing.JButton btnRemoveService;
    private javax.swing.JButton btnReprint;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cmbCustomers;
    private javax.swing.JComboBox cmbCustomers3;
    private javax.swing.JComboBox cmbEmployee;
    private javax.swing.JComboBox cmbSearchVehicleRegNo;
    private javax.swing.JComboBox cmbSearchVehicleRegNo3;
    private javax.swing.JComboBox cmbSelectCustomer;
    private javax.swing.JComboBox cmbVehicleRegNo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblBalance;
    private javax.swing.JLabel lblBalance10;
    private javax.swing.JLabel lblBalance2;
    private javax.swing.JLabel lblBalance3;
    private javax.swing.JLabel lblBalance4;
    private javax.swing.JLabel lblBalance55;
    private javax.swing.JLabel lblBalance6;
    private javax.swing.JLabel lblBalance7;
    private javax.swing.JLabel lblBalance8;
    private javax.swing.JLabel lblBalance9;
    private javax.swing.JLabel lblCustName;
    private javax.swing.JLabel lblCustName1;
    private javax.swing.JLabel lblCustName2;
    private javax.swing.JLabel lblCustomerContactNo;
    private javax.swing.JLabel lblPaid;
    private javax.swing.JLabel lblPaid2;
    private javax.swing.JLabel lblRS;
    private javax.swing.JLabel lblRS1;
    private javax.swing.JLabel lblRS2;
    private javax.swing.JLabel lblRS3;
    private javax.swing.JLabel lblRS4;
    private javax.swing.JLabel lblRS5;
    private javax.swing.JLabel lblRS6;
    private javax.swing.JLabel lblRS7;
    private javax.swing.JLabel lblRS8;
    private javax.swing.JLabel lblRS9;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotal2;
    private javax.swing.JLabel lblTotal3;
    private javax.swing.JLabel lblTotalEarnings;
    private javax.swing.JLabel lblTotalEarnings1;
    private de.wannawork.jcalendar.JCalendarComboBox salesOrderDate;
    private de.wannawork.jcalendar.JCalendarComboBox salesOrderDate3;
    private javax.swing.JTextField sparepartscost;
    private javax.swing.JTable tblAllSalesOrders;
    private javax.swing.JTable tblOrderContains;
    private javax.swing.JTable tblOrderServices;
    private javax.swing.JTable tblReturnDetails;
    private javax.swing.JTable tblSavedOrderDetails;
    private javax.swing.JTable tblSavedOrderServices;
    private javax.swing.JTable tblSavedOrders;
    private javax.swing.JTable tblViewSalesOrderContains;
    private javax.swing.JTable tblViewSalesOrderServices;
    private javax.swing.JTextField txtBalance;
    private javax.swing.JTextField txtCommission;
    private javax.swing.JTextField txtCurrentMeterReading;
    private javax.swing.JTextField txtHiddenCustID;
    private javax.swing.JTextField txtHiddenVehicleID;
    private javax.swing.JTextField txtLabourCost;
    private javax.swing.JTextField txtLabourCost1;
    private javax.swing.JTextField txtNextServiceDue;
    private javax.swing.JTextArea txtOtherComments;
    private javax.swing.JTextField txtPaid;
    private javax.swing.JTextField txtPendingAmount;
    private javax.swing.JTextField txtPendingAmount1;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalOutstandings;
    private javax.swing.JTextField txtVCurrentMeter;
    private javax.swing.JTextField txtVCurrentMeter1;
    private javax.swing.JTextField txtVIewNextService;
    private javax.swing.JTextField txtVIewNextService1;
    private javax.swing.JPanel txtVNextServiceDue;
    private javax.swing.JPanel txtVNextServiceDue1;
    private javax.swing.JTextField txtViewBalance;
    private javax.swing.JTextField txtViewBalance2;
    private javax.swing.JTextArea txtViewComments;
    private javax.swing.JTextField txtViewGarageCommission;
    private javax.swing.JTextField txtViewPaid;
    private javax.swing.JTextField txtViewPaid1;
    private javax.swing.JTextField txtViewTot;
    private javax.swing.JTextField txtViewTot1;
    // End of variables declaration//GEN-END:variables

    public void refreshEmployeeCombo() throws ClassNotFoundException, SQLException {
      /*  
        partCommisionDtm = (DefaultTableModel) partDisTbl.getModel();
        String selectedItem = cmbEmployee1.getSelectedItem().toString();
        String toString = cmbEmployee1.getSelectedItem().toString();
        ResultSet employeeDetails = EmployeeController.getEmployeeDetails("employee_name", toString);
        employeeDetails.next();
        int empID = employeeDetails.getInt(1);

        SalesOrderController.loadAllsalesCounterCommistion(partCommisionDtm, empId);
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(partCommisionDtm);
        partDisTbl.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + selectedItem)));
//       txt_EmpCommision.setText(String.format("%.2f", caculateTotalEmployeeCommision()));
*/
    }

    public void refreshCustomers() throws ClassNotFoundException, SQLException {
        String toString = cmbSelectCustomer.getSelectedItem().toString();
        ResultSet customerDetails = CustomerController.getCustomerDetails("customer_name", toString);
        customerDetails.next();
        int custID = customerDetails.getInt(1);
        fillVehicleRegNos(custID);
    }

    public javax.swing.JTable getTblOrderContains() {
        return tblOrderContains;
    }
    
     public javax.swing.JTable getTblSavedOrderDetails() {
        return tblSavedOrderDetails;
    }

    public void refreshCustomerCombo() throws ClassNotFoundException, SQLException {
        salesOrdersDtm = (DefaultTableModel) tblAllSalesOrders.getModel();
        String selectedItem = cmbCustomers.getSelectedItem().toString();
        String toString = cmbCustomers.getSelectedItem().toString();
        ResultSet customerDetails = CustomerController.getCustomerDetails("customer_name", toString);
        customerDetails.next();
        int custID = customerDetails.getInt(1);
        fillVehicleRegNos(custID);

        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(salesOrdersDtm);
        tblAllSalesOrders.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + selectedItem)));
        lblTotalEarnings.setText(String.format("%.2f", caculateTotalEarnings()));

    }
//for saved Tab pane Search
    public double caculateTotalEarningsSaved() {
        int rowCount = tblSavedOrders.getRowCount();
        double totalEarning = 0.00;
        for (int i = 0; i < rowCount; i++) {
            totalEarning += Double.parseDouble(tblSavedOrders.getValueAt(i, 8).toString().trim());
        }
        return totalEarning;
    }
    
    public void refreshCustomerComboSaved() throws ClassNotFoundException, SQLException {
         savedOrdersDtm = (DefaultTableModel) tblSavedOrders.getModel();
        String selectedItem = cmbCustomers3.getSelectedItem().toString();
        String toString = cmbCustomers3.getSelectedItem().toString();
        ResultSet customerDetails = CustomerController.getCustomerDetails("customer_name", toString);
        customerDetails.next();
        int custID = customerDetails.getInt(1);
        fillVehicleRegNos(custID);

        final TableRowSorter<TableModel> sorter =  new TableRowSorter<TableModel>(savedOrdersDtm);
        tblSavedOrders.setRowSorter(sorter);
        
        sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + selectedItem)));
        lblTotalEarnings1.setText(String.format("%.2f", caculateTotalEarningsSaved()));

    }
 
}
