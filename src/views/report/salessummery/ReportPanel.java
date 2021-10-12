/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.report.salessummery;
import com.lowagie.text.Row;
import controllers.ChequeController;
import controllers.DebtController;
import controllers.ExpenceIncomeController;
import controllers.PaymentSupplierController;
import controllers.ReportSummeryController;
import controllers.SupplierController;
import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import jxl.Cell;
import jxl.Workbook;
import models.ExpenceIncome;
import models.PaymentSupplier;
import models.Supplier;
import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import utilities_new.Combosearch;
import views.debtviews.SupplierPayment;
import views.debtviews.SupplierPaymentUpdate;

/**
 *
 * @author insaf
 */
public class ReportPanel extends javax.swing.JPanel {
    
    DefaultTableModel dtm, summeryTableModel, tblProfitDtm, fastMovingDtm, totalStockSummaryDtm, movementSummarydtm, commiSummaryDtm, overallTableModel, totalFastStockSummaryDtm, ChequeSummaryDtm,IncomeSummaryDtm,ExpensesSummaryDtm,PaymentSupSummaryDtm,gargeComiDtm,payementSuppModelDtm;
    private TableRowSorter<TableModel> partsSorter, SellspartsSorter, partsSorterFast;
    private TableRowSorter<TableModel> partsSorterGarageComi,partsSorterPaymentSup;
    private Combosearch supplierSearch;
    private SimpleDateFormat sdf;
  

    /**
     * Creates new form ReportPanel
     */
    public ReportPanel() {
        initComponents();
        loadbrand();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        summeryTableModel = (DefaultTableModel) summerytable.getModel();
        tblProfitDtm = (DefaultTableModel) tblProfit.getModel();
        totalStockSummaryDtm = (DefaultTableModel) tblStockReport.getModel();
        partsSorter = new TableRowSorter<TableModel>(totalStockSummaryDtm);
        overallTableModel = (DefaultTableModel) Overalltbl.getModel();
        gargeComiDtm=(DefaultTableModel) tblGarageCommission.getModel();
        partsSorterGarageComi = new TableRowSorter<TableModel>(gargeComiDtm);
        
        commiSummaryDtm = (DefaultTableModel) tblSlesComi.getModel();
        SellspartsSorter= new TableRowSorter<TableModel>(commiSummaryDtm);
        
        ChequeSummaryDtm = (DefaultTableModel) tblChequeSummary.getModel();
        payementSuppModelDtm=(DefaultTableModel)tblSuppPayment.getModel();
       partsSorterPaymentSup = new TableRowSorter<TableModel>(payementSuppModelDtm);
         
        fastMovingDtm = (DefaultTableModel) tblFastMoving.getModel();
        partsSorterFast = new TableRowSorter<TableModel>(fastMovingDtm);
        
        movementSummarydtm = (DefaultTableModel) tblMovement.getModel();
        partsSorter = new TableRowSorter<TableModel>(movementSummarydtm);
       
        IncomeSummaryDtm=(DefaultTableModel)tblIncome.getModel();
        ExpensesSummaryDtm=(DefaultTableModel)tblExpenseRe.getModel();
        PaymentSupSummaryDtm=(DefaultTableModel)tblSupPayment.getModel();
         orderDateChooser.setDate(new Date());
         jDateProfitStart.setDate(new Date());
            
        tblStockReport.setAutoCreateColumnsFromModel(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblStockReport.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tblStockReport.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tblStockReport.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        // tblStockReport.setDefaultRenderer(tblStockReport.getColumnClass(4), new StockCellRenderer());
        try {
            loadAllStockSummary(0);
            loadAllEmployeeSummary(0);
            ReportSummeryController.LoardSummeryOvearall(overallTableModel);
            SupplierController.fillSupplierrNamesABC(cmbABC);
            loadAllStockSummaryFastMovingReport(0);
            loadAllStockSummaryMovementReport(0);
             loadAllGarageCommission(0);
            PaymentSupplierController.loadAllSupplierPayment(payementSuppModelDtm);
           
            fillSupplierNames();

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
       }
        checkButtons();
        supplierSearch = new Combosearch();
        supplierSearch.setSearchableCombo(cmbABC, true, "Supplier Not Registered.");
    }
    
    ///// ////////garage commission
    public void loadAllGarageCommission(int supplier) throws ClassNotFoundException, SQLException {
        ResultSet rst = DebtController.getTotalGarageCommission(supplier);
        int rowCount = gargeComiDtm.getRowCount();
        Object tableRow[] = new Object[9];
        if (rowCount >= 0) {
            gargeComiDtm.setRowCount(0);
        }
        while (rst.next()) {
            for (int i = 0; i < 9; i++) {
                tableRow[i] = rst.getString(i + 1);
                
            }
            gargeComiDtm.addRow(tableRow);
        }
    }
    
    public void generateProfitReport(String ProfitstartDate,String ProfitendDate) throws ClassNotFoundException, SQLException {
        ResultSet totalProfitSummary = ReportSummeryController.getTotalProfitSummary(ProfitstartDate,ProfitendDate);
        String tableRow[] = new String[10];
        tblProfitDtm.setRowCount(0);
         while (totalProfitSummary.next()) {
            for (int i = 0; i < 10; i++) {
                tableRow[i] = totalProfitSummary.getString(i + 1);
            }
            
            tblProfitDtm.addRow(tableRow);
            
        }
        
        calculateTotals();
    }
    
    public void calculateTotals() {
        
        int rowCounts = tblProfit.getRowCount();
        double partExpences = 0;
        double serviceExpenses = 0;
        double partIncome = 0;
        double serviceIncome = 0;
        
        for (int i = 0; i < rowCounts; i++) {
            
            partExpences += Double.parseDouble(tblProfit.getValueAt(i, 5).toString());
            serviceExpenses += Double.parseDouble(tblProfit.getValueAt(i, 7).toString());
            partIncome += Double.parseDouble(tblProfit.getValueAt(i, 6).toString());
            serviceIncome += Double.parseDouble(tblProfit.getValueAt(i, 8).toString());
        }
        double profit = ((partIncome + serviceIncome) - (partExpences + serviceExpenses));
        profitlabel.setText(String.format("%.2f", profit));
        partexpencesfield.setText(String.format("%.2f", partExpences));
        serviceexpencesfield.setText(String.format("%.2f", serviceExpenses));
        partincomefield.setText(String.format("%.2f", partIncome));
        serviceincomefield.setText(String.format("%.2f", serviceIncome));
        
        double totatPartsPercentage = ((partIncome - partExpences) / profit) * 100;
        txtPartsPercentage.setText(String.format("%.2f", totatPartsPercentage));
        
    }
    
    
    public void calIncomeExpenses() {
        
        int rowCounts = tblIncome.getRowCount();
        double cashIncome = 0.0;
        double credit = 0.0;
        double badDebt = 0.0;
        double valueofReturn = 0.0;
        double salesManComi = 0.0;
        double garage = 0.0;
        double CostOfgoods = 0.0;

        double totOtherExpe=getcountTotal();
        double tot=totOtherExpe;
        
        double totSupPayment=getPaymentSupTotal();
        double x=totSupPayment;
       

        for (int i = 0; i < rowCounts; i++) {

            cashIncome += Double.parseDouble(tblIncome.getValueAt(i, 6).toString());
            credit += Double.parseDouble(tblIncome.getValueAt(i, 10).toString());
            badDebt += Double.parseDouble(tblIncome.getValueAt(i, 11).toString());
            valueofReturn += Double.parseDouble(tblIncome.getValueAt(i, 8).toString());
            garage += Double.parseDouble(tblIncome.getValueAt(i, 7).toString());
            salesManComi += Double.parseDouble(tblIncome.getValueAt(i, 9).toString());
            CostOfgoods += Double.parseDouble(tblIncome.getValueAt(i, 5).toString());
        }
       NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        txtIncomeCash.setText(String.format("%.2f", cashIncome));
        txtIncomeCredit.setText(String.format("%.2f", credit));
        txtIncomeRDedts.setText(String.format("%.2f", badDebt));
        txtIncomeValueReturns.setText(String.format("%.2f", valueofReturn));
        txtIncomeCostOfGoods.setText(String.format("%.2f", CostOfgoods));
        txtIncomeGarage.setText(String.format("%.2f", garage));
        txtIncomeSalesMan.setText(String.format("%.2f", salesManComi));
        txtIncomeOtherExpe.setText(String.format("%.2f",tot));
         

        double TotIncome = ((cashIncome + credit + badDebt) - (valueofReturn));
        txtIncomeTotalIncome.setText(String.format("%.2f", TotIncome));
       // txtIncomeTotalIncome.setText(formatter.format(TotIncome));
       
        double GrossProfit = (TotIncome - CostOfgoods);
        txtIncomeGross.setText(String.format("%.2f", GrossProfit));
        
        double TotExpences = (garage + salesManComi+ tot + x);
        txtIncomeTotalExpen.setText(String.format("%.2f", TotExpences));

        double NetAmount = (GrossProfit - TotExpences);
         txtIncomeNetAmount.setText(String.format("%.2f", NetAmount));
        // txtIncomeNetAmount.setText(formatter.format(NetAmount));
    }
    
    public double getcountTotal() {
        double totOtherExpe = 0.0;
        int rowCount = tblExpenseRe.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            totOtherExpe += Double.parseDouble( tblExpenseRe.getValueAt(i, 2).toString());
        }
       txtIncomeOtherExpe.setText(String.format("%.2f",totOtherExpe));
       return  totOtherExpe;
    }
    public double getPaymentSupTotal() {
        double totSupPayment = 0.0;
        int rowCount = tblSupPayment.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            totSupPayment += Double.parseDouble( tblSupPayment.getValueAt(i, 4).toString());
        }
       txtIncomePaymentSupplier.setText(String.format("%.2f",totSupPayment));
       return  totSupPayment;
    }
    
   // cmb sup
    public void loadAllStockSummary(int supplier) throws ClassNotFoundException, SQLException {
        ResultSet rst = ReportSummeryController.getTotalStockReport(supplier);
        int rowCount = totalStockSummaryDtm.getRowCount();
        Object tableRow[] = new Object[10];
        if (rowCount >= 0) {
            totalStockSummaryDtm.setRowCount(0);
        }
        while (rst.next()) {
            for (int i = 0; i < 10; i++) {
                tableRow[i] = rst.getString(i + 1);

            }
            totalStockSummaryDtm.addRow(tableRow);
        }
    }
    
//       
   
   
    
    ////FastMovement
    public void loadAllStockSummaryFastMovingReport(int supplier) throws ClassNotFoundException, SQLException {
        ResultSet rst = ReportSummeryController.getTotalStockReportFastMovingReport(supplier);
        int rowCount = fastMovingDtm.getRowCount();
        Object tableRow[] = new Object[7];
        if (rowCount >= 0) {
            fastMovingDtm.setRowCount(0);
        }
        while (rst.next()) {
            for (int i = 0; i < 7; i++) {
                tableRow[i] = rst.getString(i + 1);
                
            }
            fastMovingDtm.addRow(tableRow);
        }
    }
    
   
    //Movement
    public void loadAllStockSummaryMovementReport(int supplier) throws ClassNotFoundException, SQLException {
        ResultSet rst = ReportSummeryController.getTotalStockReportMovingReport(supplier);
        int rowCount =  movementSummarydtm.getRowCount();
        Object tableRow[] = new Object[6];
        if (rowCount >= 0) {
             movementSummarydtm.setRowCount(0);
        }
        while (rst.next()) {
            for (int i = 0; i < 6; i++) {
                tableRow[i] = rst.getString(i + 1);
                
            }
             movementSummarydtm.addRow(tableRow);
        }
    }
    
   ///// sales Commision
     public void loadAllEmployeeSummary(int employee ) throws ClassNotFoundException, SQLException {
        ResultSet rst = ReportSummeryController.getCommisionSummary(employee);
        int rowCount = commiSummaryDtm.getRowCount();
        Object tableRow[] = new Object[8];
        if (rowCount >= 0) {
            commiSummaryDtm.setRowCount(0);
        }
        
        while (rst.next()) {
            for (int i = 0; i < 8; i++) {
                tableRow[i] = rst.getString(i + 1);
                
            }
            commiSummaryDtm.addRow(tableRow);
        }
        
    }
    
    public void fillSupplierNames() throws ClassNotFoundException, SQLException {
        ResultSet rstSupplier = SupplierController.loadAllSuppliers();
        cmbSupplier.removeAllItems();
        cmbSupplier.addItem("All");
        while (rstSupplier.next()) {
           Supplier supplier = new Supplier();
            cmbSupplier.addItem(rstSupplier.getString(2));
        }
    }

    public void checkButtons() {
        int rowCount = tblSuppPayment.getSelectedRowCount();
        if (rowCount > 0) {
            btnAddSupPayment.setEnabled(true);
            btnUpdateSup.setEnabled(true);
           btnDeleteSup.setEnabled(true);
        } else {
            btnDeleteSup.setEnabled(false);
           btnUpdateSup.setEnabled(false);
        }
    }
    
    
    public void deleteSupplierToPayment() {
        int[] selectedRows = tblSuppPayment.getSelectedRows();

        try {
            while (selectedRows.length > 0) {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to delete this Payment to supplier...?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                int value = 0;
                if (option == 0) {
                    PaymentSupplier sExpence = new PaymentSupplier();
                    sExpence.setSupplierID(Integer.parseInt(tblSuppPayment.getValueAt(selectedRows[0], 0).toString()));
                    value = PaymentSupplierController.deleteSupplierToPayment(sExpence);
                    payementSuppModelDtm.removeRow(selectedRows[0]);
                    selectedRows = tblSuppPayment.getSelectedRows();

                    if (value > 0) {
                        JOptionPane.showMessageDialog(this, "Payment to supplier successfully deleted", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    tblSuppPayment.clearSelection();
                    //      checkCustomerButtons();
                    break;

                }

            }
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }
    }

    public void addNewExpenseIncome() {
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        assertionFailed1 = new jxl.common.AssertionFailed();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        orderDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        jDateProfitStart = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProfit = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        serviceexpencesfield = new javax.swing.JTextField();
        partexpencesfield = new javax.swing.JTextField();
        partincomefield = new javax.swing.JTextField();
        serviceincomefield = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        profittable = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        profitlabel = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtPartsPercentage = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        summerytable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        brandcombo = new javax.swing.JComboBox();
        searchButton = new javax.swing.JButton();
        firstDate = new com.toedter.calendar.JDateChooser();
        secondDate = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblStockReport = new javax.swing.JTable(){

            public Component prepareRenderer(TableCellRenderer renderer, int IndexRow, int IndexCol) {
                // get the current row
                Component comp = super.prepareRenderer(renderer, IndexRow, IndexCol);
                // even index, not selected
                if (IndexCol == 4) {
                    int remainingQty = Integer.parseInt(totalStockSummaryDtm.getValueAt(IndexRow, 4).toString());
                    int reOrderQty = Integer.parseInt(totalStockSummaryDtm.getValueAt(IndexRow, 5).toString());
                    if (remainingQty == reOrderQty && IndexCol==4) {
                        comp.setForeground(Color.BLUE);
                        comp.setSize(20, 25);
                    } else if (remainingQty < reOrderQty && IndexCol==4) {
                        comp.setForeground(Color.RED);
                    }else{
                        comp.setForeground(Color.BLACK);
                    }
                }
                return comp;
            }

        };
        jPanel7 = new javax.swing.JPanel();
        cmbSupplier = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        txtSearchPart = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSlesComi = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        txt_sellsPartNO = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txt_SellsEmp = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jDateStart = new com.toedter.calendar.JDateChooser();
        jDateEnd = new com.toedter.calendar.JDateChooser();
        btnABCSearch = new javax.swing.JButton();
        cmbABC = new javax.swing.JComboBox();
        jScrollPane6 = new javax.swing.JScrollPane();
        Overalltbl = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jDateFrom = new com.toedter.calendar.JDateChooser();
        jDateTo = new com.toedter.calendar.JDateChooser();
        btnSearchDate = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblChequeSummary = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtMovementPartNo = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        txtMovementCustomer = new javax.swing.JTextField();
        txtMovementSup = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblMovement = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtFastMovingPartNo = new javax.swing.JTextField();
        txtFastSupplier = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblFastMoving = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jDateIncomeStr = new com.toedter.calendar.JDateChooser();
        jDateIncomeEnd = new com.toedter.calendar.JDateChooser();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        btnIncomeSearch = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblIncome = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblExpenseRe = new javax.swing.JTable();
        jLabel57 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tblSupPayment = new javax.swing.JTable();
        jLabel58 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        txtIncomeCostOfGoods = new javax.swing.JTextField();
        txtIncomeCredit = new javax.swing.JTextField();
        txtIncomeRDedts = new javax.swing.JTextField();
        txtIncomeValueReturns = new javax.swing.JTextField();
        txtIncomeTotalIncome = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        txtIncomeCash = new javax.swing.JTextField();
        txtIncomeOtherExpe = new javax.swing.JTextField();
        txtIncomeGarage = new javax.swing.JTextField();
        txtIncomeSalesMan = new javax.swing.JTextField();
        txtIncomeGross = new javax.swing.JTextField();
        txtIncomePaymentSupplier = new javax.swing.JTextField();
        txtIncomeTotalExpen = new javax.swing.JTextField();
        txtIncomeNetAmount = new javax.swing.JTextField();
        btnSaveDetails = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txtCusnameGarage = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblGarageCommission = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        txtSearchSup = new javax.swing.JTextField();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblSuppPayment = new javax.swing.JTable();
        jPanel27 = new javax.swing.JPanel();
        btnAddSupPayment = new javax.swing.JButton();
        btnUpdateSup = new javax.swing.JButton();
        btnDeleteSup = new javax.swing.JButton();

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel2.setDoubleBuffered(false);

        orderDateChooser.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel4.setText("TO:");

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel40.setText("From :");

        jDateProfitStart.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel40)
                .addGap(18, 18, 18)
                .addComponent(jDateProfitStart, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(orderDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jButton1)
                .addGap(288, 288, 288))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateProfitStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(orderDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel40)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        tblProfit.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblProfit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Invoice No.", "Customer", "Account No.", "Vehicle Reg. No.", "Part Expense", "Parts Income", "Service Expenses", "Service Income", "Total Profit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProfit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProfitMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblProfit);
        if (tblProfit.getColumnModel().getColumnCount() > 0) {
            tblProfit.getColumnModel().getColumn(0).setMinWidth(0);
            tblProfit.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblProfit.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Part Expences:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Service Expences");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Part Income");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Service Income");

        serviceexpencesfield.setEditable(false);
        serviceexpencesfield.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        serviceexpencesfield.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        partexpencesfield.setEditable(false);
        partexpencesfield.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        partexpencesfield.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        partincomefield.setEditable(false);
        partincomefield.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        partincomefield.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        partincomefield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                partincomefieldActionPerformed(evt);
            }
        });

        serviceincomefield.setEditable(false);
        serviceincomefield.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        serviceincomefield.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel9.setText("Rs.");

        jLabel10.setText("Rs.");

        jLabel11.setText("Rs.");

        jLabel12.setText("Rs.");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Profit:");

        profitlabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        profitlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        profitlabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel14.setText("%");

        txtPartsPercentage.setEditable(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(partexpencesfield, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(partincomefield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(serviceexpencesfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serviceincomefield, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(314, 314, 314)
                        .addComponent(profittable, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(147, 147, 147)
                        .addComponent(jLabel13)
                        .addGap(30, 30, 30)
                        .addComponent(profitlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(txtPartsPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {partexpencesfield, partincomefield, serviceexpencesfield, txtPartsPercentage});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(profittable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13)
                    .addComponent(profitlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(partexpencesfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9)
                    .addComponent(serviceexpencesfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14)
                    .addComponent(txtPartsPercentage, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(partincomefield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11)
                    .addComponent(serviceincomefield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel12))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {partexpencesfield, partincomefield, serviceexpencesfield, serviceincomefield, txtPartsPercentage});

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Total Profit Summary", jPanel3);

        summerytable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        summerytable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Search Key", "Part No", "Description", "Actual Qty", "Total Qty", "Average Qty", "Achievement(%)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
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
        jScrollPane1.setViewportView(summerytable);
        if (summerytable.getColumnModel().getColumnCount() > 0) {
            summerytable.getColumnModel().getColumn(0).setResizable(false);
            summerytable.getColumnModel().getColumn(0).setPreferredWidth(200);
            summerytable.getColumnModel().getColumn(1).setResizable(false);
            summerytable.getColumnModel().getColumn(1).setPreferredWidth(200);
            summerytable.getColumnModel().getColumn(2).setResizable(false);
            summerytable.getColumnModel().getColumn(2).setPreferredWidth(200);
            summerytable.getColumnModel().getColumn(3).setResizable(false);
            summerytable.getColumnModel().getColumn(3).setPreferredWidth(100);
            summerytable.getColumnModel().getColumn(4).setResizable(false);
            summerytable.getColumnModel().getColumn(4).setPreferredWidth(100);
            summerytable.getColumnModel().getColumn(5).setResizable(false);
            summerytable.getColumnModel().getColumn(5).setPreferredWidth(100);
            summerytable.getColumnModel().getColumn(6).setMinWidth(100);
            summerytable.getColumnModel().getColumn(6).setPreferredWidth(100);
            summerytable.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel2.setText("Brand:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel3.setText("Month:");

        brandcombo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        firstDate.setDateFormatString(" yyyy-MM-dd");

        secondDate.setDateFormatString("yyyy-MM-dd");

        jLabel1.setText("TO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(firstDate, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(secondDate, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(brandcombo, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel3});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(brandcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchButton)
                    .addComponent(secondDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(firstDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1181, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Parts Movement Summary", jPanel4);

        tblStockReport.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblStockReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Part ID", "Supplier ID", "Part No.", "Description", "Remaining Qty.", "Re-Order Qty.", "Requested Qty. (Last 7 days)", "First Stock Date", "Last Stock Date", "Supplier"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStockReport.setRowHeight(20);
        jScrollPane3.setViewportView(tblStockReport);
        if (tblStockReport.getColumnModel().getColumnCount() > 0) {
            tblStockReport.getColumnModel().getColumn(0).setMinWidth(0);
            tblStockReport.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblStockReport.getColumnModel().getColumn(0).setMaxWidth(0);
            tblStockReport.getColumnModel().getColumn(1).setMinWidth(0);
            tblStockReport.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblStockReport.getColumnModel().getColumn(1).setMaxWidth(0);
            tblStockReport.getColumnModel().getColumn(4).setMinWidth(80);
            tblStockReport.getColumnModel().getColumn(4).setPreferredWidth(80);
            tblStockReport.getColumnModel().getColumn(4).setMaxWidth(80);
            tblStockReport.getColumnModel().getColumn(5).setMinWidth(80);
            tblStockReport.getColumnModel().getColumn(5).setPreferredWidth(80);
            tblStockReport.getColumnModel().getColumn(5).setMaxWidth(80);
        }

        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        cmbSupplier.setEditable(true);
        cmbSupplier.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbSupplier.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbSupplierPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSupplierActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Supplier :");

        txtSearchPart.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSearchPart.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtSearchPart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchPartActionPerformed(evt);
            }
        });
        txtSearchPart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchPartKeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Parts:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(txtSearchPart, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbSupplier, txtSearchPart});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchPart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap())
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbSupplier, txtSearchPart});

        jLabel17.setBackground(new java.awt.Color(0, 51, 255));
        jLabel17.setOpaque(true);

        jLabel18.setBackground(new java.awt.Color(255, 0, 0));
        jLabel18.setOpaque(true);

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setOpaque(true);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("Remainig qty is over than Re-Order qty.");
        jLabel20.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setText("Remainig qty is less than Re-Order qty.");
        jLabel21.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Remainig qty is equal to Re-Order qty.");
        jLabel22.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                        .addGap(72, 72, 72)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20))))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel17, jLabel18, jLabel19});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addGap(26, 26, 26)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel20, jLabel21, jLabel22});

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel17, jLabel18, jLabel19});

        jTabbedPane1.addTab("Stock Summary", jPanel6);

        tblSlesComi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "detailID", "employee_id", "item_part_No", "Descrition", "Employee_Name", "Date", "Sales Time", "Commision"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tblSlesComi);
        if (tblSlesComi.getColumnModel().getColumnCount() > 0) {
            tblSlesComi.getColumnModel().getColumn(0).setMinWidth(0);
            tblSlesComi.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblSlesComi.getColumnModel().getColumn(0).setMaxWidth(0);
            tblSlesComi.getColumnModel().getColumn(1).setMinWidth(0);
            tblSlesComi.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblSlesComi.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel26.setText("Employee name :");

        txt_sellsPartNO.setToolTipText("Search By Part Number");
        txt_sellsPartNO.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txt_sellsPartNO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_sellsPartNOKeyReleased(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel39.setText("Part  No:");

        txt_SellsEmp.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txt_SellsEmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_SellsEmpKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_SellsEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txt_sellsPartNO, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(266, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sellsPartNO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_SellsEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sales Commision Summary", jPanel11);

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel27.setText("Supplier :");

        jLabel29.setText("From  :");

        jLabel31.setText("TO :");

        btnABCSearch.setText("Search ");
        btnABCSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnABCSearchActionPerformed(evt);
            }
        });

        cmbABC.setEditable(true);
        cmbABC.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbABCPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jDateStart, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel31)
                        .addGap(18, 18, 18)
                        .addComponent(jDateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnABCSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbABC, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnABCSearch))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbABC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel29)
                                .addComponent(jLabel31))
                            .addComponent(jDateStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        Overalltbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "itemID", "OderID", "Item Part Number", "Description", "Model", "Suppiler Name", "Quantity", "Date", "detailID", "Amount", "y", "x", "Category"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(Overalltbl);
        if (Overalltbl.getColumnModel().getColumnCount() > 0) {
            Overalltbl.getColumnModel().getColumn(0).setMinWidth(0);
            Overalltbl.getColumnModel().getColumn(0).setPreferredWidth(0);
            Overalltbl.getColumnModel().getColumn(0).setMaxWidth(0);
            Overalltbl.getColumnModel().getColumn(1).setMinWidth(0);
            Overalltbl.getColumnModel().getColumn(1).setPreferredWidth(0);
            Overalltbl.getColumnModel().getColumn(1).setMaxWidth(0);
            Overalltbl.getColumnModel().getColumn(8).setMinWidth(0);
            Overalltbl.getColumnModel().getColumn(8).setPreferredWidth(0);
            Overalltbl.getColumnModel().getColumn(8).setMaxWidth(0);
            Overalltbl.getColumnModel().getColumn(10).setMinWidth(0);
            Overalltbl.getColumnModel().getColumn(10).setPreferredWidth(0);
            Overalltbl.getColumnModel().getColumn(10).setMaxWidth(0);
            Overalltbl.getColumnModel().getColumn(11).setMinWidth(0);
            Overalltbl.getColumnModel().getColumn(11).setPreferredWidth(0);
            Overalltbl.getColumnModel().getColumn(11).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1181, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("ABC Overoll Report summary", jPanel12);

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Search by date"));

        jLabel34.setText("From :");

        jLabel35.setText("TO:");

        btnSearchDate.setText("Search");
        btnSearchDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jLabel34)
                .addGap(69, 69, 69)
                .addComponent(jDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSearchDate)
                .addGap(31, 31, 31))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(jDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearchDate))
        );

        tblChequeSummary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Cheque No", "Customer  Name", "Bank", "Account No", "Cheque Date", "Amount", "Cheque Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(tblChequeSummary);
        if (tblChequeSummary.getColumnModel().getColumnCount() > 0) {
            tblChequeSummary.getColumnModel().getColumn(0).setMinWidth(0);
            tblChequeSummary.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblChequeSummary.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jButton3.setText("View All Cheque");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 938, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(240, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jButton3))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cheque Settlement Report", jPanel15);

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setText("Supplier:");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setText("Customer:");

        txtMovementPartNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMovementPartNoKeyReleased(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setText("Part No:");

        txtMovementCustomer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMovementCustomerFocusLost(evt);
            }
        });
        txtMovementCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMovementCustomerKeyReleased(evt);
            }
        });

        txtMovementSup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMovementSupKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addGap(18, 18, 18)
                .addComponent(txtMovementSup, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMovementCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMovementPartNo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(txtMovementPartNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMovementCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMovementSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        tblMovement.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Part No", "Description", "Date", "Time", "Supplier Name", "Customer Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tblMovement);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1156, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Movement Report", jPanel14);

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setText("Supplier:");

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setText("Part No:");

        txtFastMovingPartNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFastMovingPartNoKeyReleased(evt);
            }
        });

        txtFastSupplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFastSupplierKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addGap(18, 18, 18)
                .addComponent(txtFastSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtFastMovingPartNo, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFastMovingPartNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFastSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        tblFastMoving.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Part No", "Description", "Supplier", "Quantity", "Added Date", "Time", "Customer Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(tblFastMoving);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(246, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Fast Moving Report", jPanel17);

        jTabbedPane1.addTab("Movement Summary Report", jTabbedPane2);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel41.setText("From :");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel42.setText("TO :");

        btnIncomeSearch.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnIncomeSearch.setText("Search");
        btnIncomeSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncomeSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jDateIncomeStr, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jLabel42)
                .addGap(42, 42, 42)
                .addComponent(jDateIncomeEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                .addComponent(btnIncomeSearch)
                .addGap(47, 47, 47))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIncomeSearch)
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel41)
                        .addComponent(jDateIncomeEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDateIncomeStr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel42)))
                .addGap(37, 37, 37))
        );

        tblIncome.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "OrderID", "ItemId", "SalesOderNo", "Oderdate", "Time", "Cost Of Goods Sold", "Cash Income", "Garage Commission", "value Of Return", "Sales man Commission", "Credit Income", "Realization Bad Debts"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(tblIncome);
        if (tblIncome.getColumnModel().getColumnCount() > 0) {
            tblIncome.getColumnModel().getColumn(0).setMinWidth(0);
            tblIncome.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblIncome.getColumnModel().getColumn(0).setMaxWidth(0);
            tblIncome.getColumnModel().getColumn(1).setMinWidth(0);
            tblIncome.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblIncome.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        tblExpenseRe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Expences_id", "Description", "Amount", "Expences_date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(tblExpenseRe);
        if (tblExpenseRe.getColumnModel().getColumnCount() > 0) {
            tblExpenseRe.getColumnModel().getColumn(0).setMinWidth(0);
            tblExpenseRe.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblExpenseRe.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel57.setText("Expences Table");

        tblSupPayment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice Number", "Supplier", "Date", "Time", "Amonut", "Remarks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane12.setViewportView(tblSupPayment);

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel58.setText("Payment TO Supplier");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 836, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel58))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel58)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel25.setToolTipText("");

        jLabel43.setText("Cash Income:  (RS)");

        jLabel44.setText("Credit Income:(RS)");

        jLabel45.setText("Realization Bad Debts: (RS)");

        jLabel46.setText("Value Returns :(RS)");

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel47.setText("Total Income :(RS)");

        txtIncomeCostOfGoods.setEditable(false);
        txtIncomeCostOfGoods.setText("0.00");

        txtIncomeCredit.setEditable(false);
        txtIncomeCredit.setText("0.00");

        txtIncomeRDedts.setEditable(false);
        txtIncomeRDedts.setText("0.00");

        txtIncomeValueReturns.setEditable(false);
        txtIncomeValueReturns.setText("0.00");

        txtIncomeTotalIncome.setEditable(false);
        txtIncomeTotalIncome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtIncomeTotalIncome.setText("0.00");

        jLabel48.setText("Cost Of Goods Sold:(RS)");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel49.setText("Gross Proft:(RS)");

        jLabel50.setText("Oter Expenses :(RS)");

        jLabel51.setText("Garage Commission:(RS)");

        jLabel52.setText("Sales man Commission:(RS)");

        jLabel54.setText("Payment Supplier :(RS)");

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel55.setText("Total Expenses:(RS)");

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel56.setText("Net Amount :(RS)");

        txtIncomeCash.setEditable(false);
        txtIncomeCash.setText("0.00");

        txtIncomeOtherExpe.setEditable(false);
        txtIncomeOtherExpe.setText("0.00");

        txtIncomeGarage.setEditable(false);
        txtIncomeGarage.setText("0.00");

        txtIncomeSalesMan.setEditable(false);
        txtIncomeSalesMan.setText("0.00");

        txtIncomeGross.setEditable(false);
        txtIncomeGross.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtIncomeGross.setText("0.00");
        txtIncomeGross.setToolTipText("");

        txtIncomePaymentSupplier.setEditable(false);
        txtIncomePaymentSupplier.setText("0.00");

        txtIncomeTotalExpen.setEditable(false);
        txtIncomeTotalExpen.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtIncomeTotalExpen.setText("0.00");

        txtIncomeNetAmount.setEditable(false);
        txtIncomeNetAmount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtIncomeNetAmount.setText("0.00");

        btnSaveDetails.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnSaveDetails.setText("Save Detalis");
        btnSaveDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveDetailsActionPerformed(evt);
            }
        });

        jButton4.setText("+");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel47)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(txtIncomeValueReturns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(441, 441, 441)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel25Layout.createSequentialGroup()
                                        .addComponent(jLabel54)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtIncomePaymentSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel25Layout.createSequentialGroup()
                                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel55)
                                            .addComponent(jLabel56))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtIncomeNetAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtIncomeTotalExpen, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(465, 465, 465)
                                .addComponent(btnSaveDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4)))
                        .addGap(45, 45, 45))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIncomeCash, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIncomeCredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIncomeTotalIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIncomeRDedts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(jLabel52)
                                .addGap(18, 18, 18)
                                .addComponent(txtIncomeSalesMan, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel25Layout.createSequentialGroup()
                                        .addComponent(jLabel49)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtIncomeGross, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel25Layout.createSequentialGroup()
                                        .addComponent(jLabel51)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtIncomeGarage, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel25Layout.createSequentialGroup()
                                        .addComponent(jLabel50)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtIncomeOtherExpe, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel25Layout.createSequentialGroup()
                                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtIncomeCostOfGoods, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );

        jPanel25Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtIncomeCostOfGoods, txtIncomeCredit, txtIncomeRDedts, txtIncomeTotalIncome, txtIncomeValueReturns});

        jPanel25Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel48, jLabel49, jLabel50, jLabel51, jLabel52, jLabel54});

        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel45))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel54)
                                    .addComponent(txtIncomePaymentSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtIncomeTotalExpen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(txtIncomeCostOfGoods, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtIncomeGross, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIncomeOtherExpe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel50))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIncomeValueReturns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46))
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel47))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIncomeTotalIncome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addComponent(txtIncomeNetAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSaveDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4))
                        .addContainerGap())))
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIncomeCash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(jLabel48))
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIncomeCredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44)
                    .addComponent(jLabel49))
                .addGap(18, 18, 18)
                .addComponent(txtIncomeRDedts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(txtIncomeGarage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(txtIncomeSalesMan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(" Expenses and Income report", jPanel22);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search By Customer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel23.setText("Customer Name:");

        txtCusnameGarage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCusnameGarageKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCusnameGarage, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 163, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCusnameGarage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        tblGarageCommission.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Part Number", "Description", "Supplier", "Customer Name", "Quantity", "Amount", "Date", "Time", "Grage Commission"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblGarageCommission);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 698, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Garage Commision", jPanel8);

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search By Supplier", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel24.setText("Supplier ");

        txtSearchSup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchSupKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(txtSearchSup, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtSearchSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        tblSuppPayment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier ID", "Invoice Number", "Supplier", "Date", "Time", "Amount", "Remarks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSuppPayment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSuppPaymentMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(tblSuppPayment);
        if (tblSuppPayment.getColumnModel().getColumnCount() > 0) {
            tblSuppPayment.getColumnModel().getColumn(0).setMinWidth(0);
            tblSuppPayment.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblSuppPayment.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        btnAddSupPayment.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAddSupPayment.setText("Add");
        btnAddSupPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSupPaymentActionPerformed(evt);
            }
        });

        btnUpdateSup.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnUpdateSup.setText("Upadte");
        btnUpdateSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSupActionPerformed(evt);
            }
        });

        btnDeleteSup.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDeleteSup.setText("Delete");
        btnDeleteSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddSupPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdateSup, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(btnDeleteSup, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDeleteSup, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(btnUpdateSup, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddSupPayment, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 1145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 56, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(246, 246, 246))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Paymet To Supplier", jPanel10);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
      summeryTableModel.setRowCount(0);
        Date date1 = firstDate.getDate();
        Date date2 = secondDate.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fdate = sdf.format(date1);
        String sdate = sdf.format(date2);
        String brand = (String) brandcombo.getSelectedItem();
        
        Calendar d1 = firstDate.getCalendar();
        Calendar d2 = secondDate.getCalendar();
        double monthsBetween = monthsBetween(d2, d1);
        //System.out.println(monthsBetween);
        try {
            ReportSummeryController.getSummeryForSales(fdate, sdate, summeryTableModel, brand, monthsBetween);
        } catch (SQLException e) {
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
       // System.out.println(date);
    }//GEN-LAST:event_searchButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date ProfitstartDate = jDateProfitStart.getDate();
            Date ProfitendDate = orderDateChooser.getDate();
            String strStartDate1 = sdf.format(ProfitstartDate);
            String strEndDate1 = sdf.format(ProfitendDate);
            //String DateFormat = sdf.format(orderDateChooser.getDate());
          
            generateProfitReport(strStartDate1,strEndDate1);
            calculateTotals();
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void partincomefieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_partincomefieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_partincomefieldActionPerformed

    private void txtSearchPartKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchPartKeyReleased
        tblStockReport.setRowSorter(partsSorter);
        partsSorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtSearchPart.getText()));
    }//GEN-LAST:event_txtSearchPartKeyReleased

    private void cmbSupplierPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbSupplierPopupMenuWillBecomeInvisible
        
        int supplierID = 0;
        try {
            String supplier = cmbSupplier.getSelectedItem().toString().trim();
            if (supplier.equals("All")) {
                supplierID = 0;
            } else {
                supplierID = SupplierController.getSupplierIDforName(supplier);
              //  System.out.println(supplierID);
            }
            loadAllStockSummary(supplierID);
        } catch (Exception ex) {
            
        }
    }//GEN-LAST:event_cmbSupplierPopupMenuWillBecomeInvisible

    private void cmbSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSupplierActionPerformed

    private void btnABCSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnABCSearchActionPerformed
        try {
        overallTableModel.setRowCount(0);
        Date date1 = jDateStart.getDate();
        Date date2 = jDateEnd.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fdate = sdf.format(date1);
        String sdate = sdf.format(date2);
       
            ReportSummeryController.getSummeryOvearall(fdate, sdate, overallTableModel);
           
          
        } catch (SQLException e) {
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportPanel.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnABCSearchActionPerformed

    private void txtSearchPartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchPartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchPartActionPerformed

    private void btnSearchDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchDateActionPerformed
        try {
            Date startDate1 = jDateFrom.getDate();
            Date endDate1 = jDateTo.getDate();
            String searchStartDate1 = sdf.format(startDate1);
            String searchEndDate1 = sdf.format(endDate1);

            ChequeController.ChequeSummarySearch1(ChequeSummaryDtm, searchStartDate1, searchEndDate1);

        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }                // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchDateActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {        
            ChequeSummaryDtm.setRowCount(0);
            ChequeController.ChequeSummary(ChequeSummaryDtm);
            // TODO add your handling code here:
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtFastMovingPartNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFastMovingPartNoKeyReleased
   tblFastMoving.setRowSorter( partsSorterFast);
          partsSorterFast.setRowFilter(RowFilter.regexFilter("(?i)" + txtFastMovingPartNo.getText()));
    
// TODO add your handling code here:
    }//GEN-LAST:event_txtFastMovingPartNoKeyReleased

    private void txtMovementCustomerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMovementCustomerKeyReleased
  tblMovement.setRowSorter( partsSorter);
          partsSorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtMovementCustomer.getText()));
               // TODO add your handling code here:
    }//GEN-LAST:event_txtMovementCustomerKeyReleased

    private void txtMovementCustomerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMovementCustomerFocusLost
//nameField.setText("");        // TODO add your handling code here:
    }//GEN-LAST:event_txtMovementCustomerFocusLost

    private void txt_sellsPartNOKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_sellsPartNOKeyReleased
      tblSlesComi.setRowSorter( SellspartsSorter);
          SellspartsSorter.setRowFilter(RowFilter.regexFilter("(?i)" + txt_sellsPartNO.getText())); 
          // TODO add your handling code here:
    }//GEN-LAST:event_txt_sellsPartNOKeyReleased

    private void txt_SellsEmpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_SellsEmpKeyReleased
       tblSlesComi.setRowSorter( SellspartsSorter);
          SellspartsSorter.setRowFilter(RowFilter.regexFilter("(?i)" + txt_SellsEmp.getText())); // TODO add your handling code here:
    }//GEN-LAST:event_txt_SellsEmpKeyReleased

    private void cmbABCPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbABCPopupMenuWillBecomeInvisible
    String supplier = cmbABC.getSelectedItem().toString();
        try {

            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(overallTableModel);
             Overalltbl.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + supplier)));
          //  lblTotalPendingAmount.setText("" + calculateAllPendingAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }
                   // TODO add your handling code here:
    }//GEN-LAST:event_cmbABCPopupMenuWillBecomeInvisible

    private void txtMovementSupKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMovementSupKeyReleased
       tblMovement.setRowSorter( partsSorter);
          partsSorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtMovementSup.getText()));   // TODO add your handling code here:
    }//GEN-LAST:event_txtMovementSupKeyReleased

    private void txtFastSupplierKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFastSupplierKeyReleased
         tblFastMoving.setRowSorter( partsSorterFast);
          partsSorterFast.setRowFilter(RowFilter.regexFilter("(?i)" + txtFastSupplier.getText())); // TODO add your handling code here:
    }//GEN-LAST:event_txtFastSupplierKeyReleased

    private void txtMovementPartNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMovementPartNoKeyReleased
 tblMovement.setRowSorter( partsSorter);
          partsSorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtMovementPartNo.getText()));        // TODO add your handling code here:
    }//GEN-LAST:event_txtMovementPartNoKeyReleased

    private void btnIncomeSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncomeSearchActionPerformed
        try {
            Date fromDate = jDateIncomeStr.getDate();
            Date ToDate = jDateIncomeEnd.getDate();
            String IncomeStartDate = sdf.format(fromDate);
            String IncomeEndDate = sdf.format(ToDate); 
            ReportSummeryController.LoardSummeryIncome(IncomeSummaryDtm,IncomeStartDate,IncomeEndDate); 
            ReportSummeryController.loadExpencesForReport(ExpensesSummaryDtm,IncomeStartDate,IncomeEndDate);
            ReportSummeryController.loadpaymentSupp(PaymentSupSummaryDtm, IncomeStartDate, IncomeEndDate);
         
            calIncomeExpenses();
            getcountTotal();
            getPaymentSupTotal();// TODO add your handling code here:
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnIncomeSearchActionPerformed

    private void btnSaveDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveDetailsActionPerformed
    try {
             sdf.applyPattern("HH:mm:ss");
             Date d1 = new Date();
            String addedTime = sdf.format(d1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String addedDate = sdf.format(d1);
   
            double cashIncome = Double.parseDouble(txtIncomeCash.getText());
            double creditIncome = Double.parseDouble(txtIncomeCredit.getText());
            double relization = Double.parseDouble(txtIncomeRDedts.getText());
            double valueReturn = Double.parseDouble(txtIncomeValueReturns.getText());
            double totalIncome = Double.parseDouble(txtIncomeTotalIncome.getText());
            double cost = Double.parseDouble(txtIncomeCostOfGoods.getText());
            double gross = Double.parseDouble(txtIncomeGross.getText());
            double other = Double.parseDouble(txtIncomeOtherExpe.getText());
            double garage = Double.parseDouble(txtIncomeGarage.getText());
            double sales = Double.parseDouble(txtIncomeSalesMan.getText());
            double payment = Double.parseDouble(txtIncomePaymentSupplier.getText());
            double total = Double.parseDouble(txtIncomeTotalExpen.getText());
            double netAmount = Double.parseDouble(txtIncomeNetAmount.getText());
           // int status = 1;

            ExpenceIncome exIn = new ExpenceIncome(addedTime,addedDate,cashIncome, creditIncome, relization, valueReturn, totalIncome, cost, gross, other, garage, sales, payment, total, netAmount,1);

            int value = ExpenceIncomeController.addNewExpenceIncomeDetail(exIn);
            if (value > 0) {
                
                JOptionPane.showMessageDialog(this, "Added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                ExpenseAndIncome ex=new ExpenseAndIncome(null, true);
                 ex.setVisible(true);
               //  ExpenceIncomeController.loadAllExprnce(ExpenceIncomeModelDtm);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please check your values again.", "Error in values", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } 
    // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveDetailsActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
 ExpenseAndIncome ex=new ExpenseAndIncome(null, true);
                 ex.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtCusnameGarageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCusnameGarageKeyReleased
     tblGarageCommission.setRowSorter( partsSorterGarageComi);
          partsSorterGarageComi.setRowFilter(RowFilter.regexFilter("(?i)" +  txtCusnameGarage.getText()));        // TODO add your handling code here:
    }//GEN-LAST:event_txtCusnameGarageKeyReleased

    private void txtSearchSupKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchSupKeyReleased
        tblSuppPayment.setRowSorter( partsSorterPaymentSup);
          partsSorterPaymentSup.setRowFilter(RowFilter.regexFilter("(?i)" +  txtSearchSup.getText()));        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchSupKeyReleased

    private void btnAddSupPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSupPaymentActionPerformed
       try {
            SupplierPayment newSupp= new SupplierPayment(new JFrame(), true);
            newSupp.setVisible(true);
           PaymentSupplierController.loadAllSupplierPayment(payementSuppModelDtm);
            checkButtons();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }           // TODO add your handling code here:
    }//GEN-LAST:event_btnAddSupPaymentActionPerformed

    private void tblSuppPaymentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSuppPaymentMouseClicked
      checkButtons();          // TODO add your handling code here:
    }//GEN-LAST:event_tblSuppPaymentMouseClicked

    private void btnUpdateSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSupActionPerformed
     try {
            int selectedRows = tblSuppPayment.getSelectedRowCount();
            int selected = tblSuppPayment.getSelectedRow();
            if (selectedRows > 0) {
                int supplierID = Integer.parseInt(tblSuppPayment.getValueAt(selected, 0).toString());
                String invoicenumber = tblSuppPayment.getValueAt(selected, 1).toString().trim();
                String supplier = tblSuppPayment.getValueAt(selected, 2).toString().trim();
                String date = tblSuppPayment.getValueAt(selected, 3).toString().trim();
                double amount = Double.parseDouble(tblSuppPayment.getValueAt(selected, 5).toString());
                 String remarks = tblSuppPayment.getValueAt(selected, 6).toString().trim();
            
                 PaymentSupplier supp = new PaymentSupplier();
           
                supp.setInvoicenumber(invoicenumber);
                supp.setSupplierID(supplierID);
                supp.setSupplier(supplier);
                supp.setDate(date);
                supp.setAmount(amount);
                supp.setRemarks(remarks);
                
               
               SupplierPaymentUpdate uSupplier = new SupplierPaymentUpdate(new JFrame(), true, supp);
                uSupplier.setVisible(true);
               PaymentSupplierController.loadAllSupplierPayment(payementSuppModelDtm);
                checkButtons();

            }
        } catch (NumberFormatException numberFormatException) {
        } catch (HeadlessException headlessException) {
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }                  // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateSupActionPerformed

    private void btnDeleteSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSupActionPerformed
      deleteSupplierToPayment();
    checkButtons();        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteSupActionPerformed

    private void tblProfitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProfitMouseClicked
   int selectedRow = tblProfit.getSelectedRow();
        int clickCount = evt.getClickCount();
        if (clickCount == 2) {
       try {
           int salesOrderID = Integer.parseInt((String) tblProfit.getValueAt(selectedRow, 0));
           ViewPartsDetalis dd;
           
               dd = new ViewPartsDetalis(salesOrderID);
               dd.setVisible(true);
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(ReportPanel.class.getName()).log(Level.SEVERE, null, ex);
           }
           
        }           // TODO add your handling code here:
    }//GEN-LAST:event_tblProfitMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Overalltbl;
    private jxl.common.AssertionFailed assertionFailed1;
    private javax.swing.JComboBox brandcombo;
    private javax.swing.JButton btnABCSearch;
    private javax.swing.JButton btnAddSupPayment;
    private javax.swing.JButton btnDeleteSup;
    private javax.swing.JButton btnIncomeSearch;
    private javax.swing.JButton btnSaveDetails;
    private javax.swing.JButton btnSearchDate;
    private javax.swing.JButton btnUpdateSup;
    private javax.swing.JComboBox cmbABC;
    private javax.swing.JComboBox cmbSupplier;
    private com.toedter.calendar.JDateChooser firstDate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateEnd;
    private com.toedter.calendar.JDateChooser jDateFrom;
    private com.toedter.calendar.JDateChooser jDateIncomeEnd;
    private com.toedter.calendar.JDateChooser jDateIncomeStr;
    private com.toedter.calendar.JDateChooser jDateProfitStart;
    private com.toedter.calendar.JDateChooser jDateStart;
    private com.toedter.calendar.JDateChooser jDateTo;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private com.toedter.calendar.JDateChooser orderDateChooser;
    private javax.swing.JTextField partexpencesfield;
    private javax.swing.JTextField partincomefield;
    private javax.swing.JLabel profitlabel;
    private javax.swing.JLabel profittable;
    private javax.swing.JButton searchButton;
    private com.toedter.calendar.JDateChooser secondDate;
    private javax.swing.JTextField serviceexpencesfield;
    private javax.swing.JTextField serviceincomefield;
    private javax.swing.JTable summerytable;
    private javax.swing.JTable tblChequeSummary;
    private javax.swing.JTable tblExpenseRe;
    private javax.swing.JTable tblFastMoving;
    private javax.swing.JTable tblGarageCommission;
    private javax.swing.JTable tblIncome;
    private javax.swing.JTable tblMovement;
    private javax.swing.JTable tblProfit;
    private javax.swing.JTable tblSlesComi;
    private javax.swing.JTable tblStockReport;
    private javax.swing.JTable tblSupPayment;
    private javax.swing.JTable tblSuppPayment;
    private javax.swing.JTextField txtCusnameGarage;
    private javax.swing.JTextField txtFastMovingPartNo;
    private javax.swing.JTextField txtFastSupplier;
    private javax.swing.JTextField txtIncomeCash;
    private javax.swing.JTextField txtIncomeCostOfGoods;
    private javax.swing.JTextField txtIncomeCredit;
    private javax.swing.JTextField txtIncomeGarage;
    private javax.swing.JTextField txtIncomeGross;
    private javax.swing.JTextField txtIncomeNetAmount;
    private javax.swing.JTextField txtIncomeOtherExpe;
    private javax.swing.JTextField txtIncomePaymentSupplier;
    private javax.swing.JTextField txtIncomeRDedts;
    private javax.swing.JTextField txtIncomeSalesMan;
    private javax.swing.JTextField txtIncomeTotalExpen;
    private javax.swing.JTextField txtIncomeTotalIncome;
    private javax.swing.JTextField txtIncomeValueReturns;
    private javax.swing.JTextField txtMovementCustomer;
    private javax.swing.JTextField txtMovementPartNo;
    private javax.swing.JTextField txtMovementSup;
    private javax.swing.JTextField txtPartsPercentage;
    private javax.swing.JTextField txtSearchPart;
    private javax.swing.JTextField txtSearchSup;
    private javax.swing.JTextField txt_SellsEmp;
    private javax.swing.JTextField txt_sellsPartNO;
    // End of variables declaration//GEN-END:variables
  
    private void loadbrand() {
        
        try {
            ReportSummeryController.getBrand(brandcombo);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
   
    public static double monthsBetween(Calendar date1, Calendar date2) {
        double monthsBetween = 0;
        //difference in month for years
        monthsBetween = (date1.get(Calendar.YEAR) - date2.get(Calendar.YEAR)) * 12;
        //difference in month for months
        monthsBetween += date1.get(Calendar.MONTH) - date2.get(Calendar.MONTH);
        //difference in month for days
        if (date1.get(Calendar.DAY_OF_MONTH) != date1.getActualMaximum(Calendar.DAY_OF_MONTH)
                && date1.get(Calendar.DAY_OF_MONTH) != date1.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            monthsBetween += ((date1.get(Calendar.DAY_OF_MONTH) - date2.get(Calendar.DAY_OF_MONTH)) / 31d);
        }
        return monthsBetween;
    }
  
}
