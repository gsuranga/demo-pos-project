/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views.debtviews;

import controllers.ChequeController;
import controllers.CustomerController;
import controllers.DebtController;
import controllers.EmployeeController;
import controllers.PaymentSupplierController;
import controllers.sellsComissionController;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import models.Cheque;
import models.Debt;
import models.DebtDetail;
import models.PaymentSupplier;
import models.sellsCommi;
import models.sellsCommiDetail;
import utilities_new.Combosearch;
import utilities_new.ValidateValues;

/**
 *
 * @author SHdinesh Madushanka
 */
public class AllDebtsDetail extends javax.swing.JPanel {

    private DefaultTableModel peningDetailsDtm;
    private Combosearch customerSearch,empSearch;
    private DefaultTableModel completedPaymentDtm;
    private DefaultTableModel PaymentDetailsDtm;
    private SimpleDateFormat sdf;
    private DefaultTableModel chequeDetalisDtm;
    
    private int orderId;
    private DefaultTableModel pendingSellsComi;
    private DefaultTableModel payementSuppModelDtm;
    private int employeeId;
   
      
    private TableRowSorter<TableModel> partsSorterGarageComi,partsSorterPaymentSup;
    
   
    /**
     * Creates new form AllDebtsDetail
     */
    public AllDebtsDetail() {
        initComponents();
       this.orderId=orderId;
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        peningDetailsDtm = (DefaultTableModel) tblAllPendingDetails.getModel();
        completedPaymentDtm = (DefaultTableModel) tbl_completed_payment.getModel();
        PaymentDetailsDtm = (DefaultTableModel) tblPaymentDetails.getModel();
        chequeDetalisDtm = (DefaultTableModel) tblChequeDetails.getModel();
        pendingSellsComi=(DefaultTableModel)tblPendigSellsComi.getModel();
       
       
   
        
        try {
            DebtController.loadAllPendingDebtDetails(peningDetailsDtm);
            CustomerController.fillCustomerNames(cmbAllCustomers);
            EmployeeController.fillEmployeeNames(jCmbSellscomi);    // for sells
            DebtController.loadAllCompletedPayments(completedPaymentDtm, null);
            ChequeController.loadAllChequeDetalis(chequeDetalisDtm);
            sellsComissionController.loadAllPendingSellsCommi1(pendingSellsComi);
         
           
            
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
      
        customerSearch = new Combosearch();
        customerSearch.setSearchableCombo(cmbAllCustomers, true, "Customer Not Registered.");
        empSearch=new Combosearch();
        empSearch.setSearchableCombo(jCmbSellscomi, true, "employee Not Rejister");
        lblTotalPendingAmount.setText("" + calculateAllPendingAmount());
         lblTotSellsComi.setText(String.format("%.2f", calculateTotalSellsComi()));
    }
    
   
    
    public String calculateBalanceComi(double paidAmount) {
        double total = Double.parseDouble(txtTotComi.getText());
        double balance = paidAmount - total;
        return String.format("%.2f", balance);

    }


    public double calculateAllPendingAmount() {
        int rowCount = tblAllPendingDetails.getRowCount();
        double totalPendingAmount = 0;
        for (int i = 0; i < rowCount; i++) {
            totalPendingAmount += Double.parseDouble(tblAllPendingDetails.getValueAt(i, 5).toString());
        }
        return totalPendingAmount;
    }
    
    

    
    
   //for cal sells commision 
   public double calculateTotalSellsComi(){
         int rowCount = tblPendigSellsComi.getRowCount();
        double TotSellsComi = 0;
        for (int i = 0; i < rowCount; i++) {
            TotSellsComi += Double.parseDouble(tblPendigSellsComi.getValueAt(i, 8).toString());
        }
        return TotSellsComi;
       
   }
    public void clearValues() {
        txtPaidComi.setText("0.00");
        txtTotComi.setText("0.00");
        lblBalanceComi.setText("0.00");
        }
   
   
   
     public double calculateSelectedSellsComi(){   
        int rowCount = tblPendigSellsComi.getRowCount();
        double TotSelectedSellsComi = 0;
        for (int i = 0; i < rowCount; i++) {
            boolean isRet = Boolean.parseBoolean(tblPendigSellsComi.getValueAt(i, 9).toString());
            if (isRet) {
                TotSelectedSellsComi += Double.parseDouble(tblPendigSellsComi.getValueAt(i, 8).toString());
            }
        }
        return TotSelectedSellsComi;
    }
     
     //Supplier to payment delete
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        cmbAllCustomers = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        lblTotalPendingAmount = new javax.swing.JLabel();
        txtSearchVehicle = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAllPendingDetails = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txt_search = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_completed_payment = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPaymentDetails = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        startDateSelect = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        endDateSelect = new com.toedter.calendar.JDateChooser();
        btnSearch = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblChequeDetails = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblPendigSellsComi = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jCmbSellscomi = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton5 = new javax.swing.JButton();
        lblTotSellsComi = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblBalanceComi = new javax.swing.JLabel();
        txtPaidComi = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtTotComi = new javax.swing.JTextField();

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        cmbAllCustomers.setEditable(true);
        cmbAllCustomers.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbAllCustomersPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbAllCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAllCustomersActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel1.setText("Total Pending Amount");

        lblTotalPendingAmount.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        lblTotalPendingAmount.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        txtSearchVehicle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchVehicleKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Vehicle:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Customer:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbAllCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearchVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addComponent(lblTotalPendingAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblTotalPendingAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbAllCustomers)
                        .addComponent(txtSearchVehicle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel4, txtSearchVehicle});

        tblAllPendingDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CustID", "vehicle ID", "Customer", "Vehicle Reg No.", "Account No", "Pending Amount (Rs.)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAllPendingDetails.setRowHeight(20);
        tblAllPendingDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAllPendingDetailsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAllPendingDetails);
        if (tblAllPendingDetails.getColumnModel().getColumnCount() > 0) {
            tblAllPendingDetails.getColumnModel().getColumn(0).setMinWidth(0);
            tblAllPendingDetails.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblAllPendingDetails.getColumnModel().getColumn(0).setMaxWidth(0);
            tblAllPendingDetails.getColumnModel().getColumn(1).setMinWidth(0);
            tblAllPendingDetails.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblAllPendingDetails.getColumnModel().getColumn(1).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addGap(52, 52, 52))
        );

        jTabbedPane1.addTab("Pending Payments", jPanel1);

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Search   :");

        jButton2.setText("Search");

        tbl_completed_payment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "debt_id", "Customer name", "Sales order No.", "Debt date", "Debt end date", "Amount (Rs.)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_completed_payment.setColumnSelectionAllowed(true);
        tbl_completed_payment.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tbl_completed_payment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_completed_paymentMouseClicked(evt);
            }
        });
        tbl_completed_payment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_completed_paymentKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_completed_payment);
        tbl_completed_payment.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tbl_completed_payment.getColumnModel().getColumnCount() > 0) {
            tbl_completed_payment.getColumnModel().getColumn(0).setMinWidth(0);
            tbl_completed_payment.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbl_completed_payment.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        tblPaymentDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Payment Date", "Amount (Rs.)"
            }
        ));
        jScrollPane3.setViewportView(tblPaymentDetails);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(26, 26, 26)
                        .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton2))
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Completed Payments", jPanel2);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search By Cheque Issues", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 12))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("From :");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("TO:");

        btnSearch.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startDateSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(endDateSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(endDateSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(startDateSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tblChequeDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "debtID", "id", "Cheque Number", "Customer Name", "Bank", "Account No", "Cheque Date", "Amount", "Cheque Status", " Status", "Cheque Realize", "Return Cheque"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChequeDetails.setToolTipText("Cheque Realize Or Return Cheque");
        tblChequeDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChequeDetailsMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblChequeDetails);
        if (tblChequeDetails.getColumnModel().getColumnCount() > 0) {
            tblChequeDetails.getColumnModel().getColumn(0).setMinWidth(0);
            tblChequeDetails.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblChequeDetails.getColumnModel().getColumn(0).setMaxWidth(0);
            tblChequeDetails.getColumnModel().getColumn(1).setMinWidth(0);
            tblChequeDetails.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblChequeDetails.getColumnModel().getColumn(1).setMaxWidth(0);
            tblChequeDetails.getColumnModel().getColumn(8).setMinWidth(0);
            tblChequeDetails.getColumnModel().getColumn(8).setPreferredWidth(0);
            tblChequeDetails.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton3.setText("Save Cheque");
        jButton3.setToolTipText("");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 969, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        jTabbedPane1.addTab("Cheque Payments", jPanel5);

        tblPendigSellsComi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ItemID", "OderID", "EmployeeID", "Item part no", "Description", "Employee Name", "Date", "Sales Time", "Commision (RS.)", "Accepted", "DetailID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPendigSellsComi.setToolTipText("Sales man Commission View");
        tblPendigSellsComi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPendigSellsComiMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblPendigSellsComi);
        if (tblPendigSellsComi.getColumnModel().getColumnCount() > 0) {
            tblPendigSellsComi.getColumnModel().getColumn(0).setMinWidth(0);
            tblPendigSellsComi.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblPendigSellsComi.getColumnModel().getColumn(0).setMaxWidth(0);
            tblPendigSellsComi.getColumnModel().getColumn(1).setMinWidth(0);
            tblPendigSellsComi.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblPendigSellsComi.getColumnModel().getColumn(1).setMaxWidth(0);
            tblPendigSellsComi.getColumnModel().getColumn(2).setMinWidth(0);
            tblPendigSellsComi.getColumnModel().getColumn(2).setPreferredWidth(0);
            tblPendigSellsComi.getColumnModel().getColumn(2).setMaxWidth(0);
            tblPendigSellsComi.getColumnModel().getColumn(10).setMinWidth(0);
            tblPendigSellsComi.getColumnModel().getColumn(10).setPreferredWidth(0);
            tblPendigSellsComi.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Total All Pending Sales man Commision : (Rs:)");

        jButton4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton4.setText("Saved Sales Commision");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("Employee Name :");

        jCmbSellscomi.setEditable(true);
        jCmbSellscomi.setToolTipText("Enter Employee  Name");
        jCmbSellscomi.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jCmbSellscomiPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setText("From :");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setText("TO:");

        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton5.setText("Search");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCmbSellscomi, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(143, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCmbSellscomi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jButton5))))
                .addContainerGap())
        );

        lblTotSellsComi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTotSellsComi.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Total Of selected Commision:(RS)");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Paid (RS):");

        lblBalanceComi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblBalanceComi.setText("0.00");
        lblBalanceComi.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        txtPaidComi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPaidComi.setText("0.00");
        txtPaidComi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPaidComiActionPerformed(evt);
            }
        });
        txtPaidComi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPaidComiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPaidComiFocusLost(evt);
            }
        });
        txtPaidComi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPaidComiKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPaidComiKeyReleased(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Balance :(RS)");

        txtTotComi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTotComi.setText("0.00");
        txtTotComi.setEnabled(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 883, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(106, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotSellsComi, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPaidComi)
                            .addComponent(lblBalanceComi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTotComi, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                        .addGap(271, 271, 271)
                        .addComponent(jButton4)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotSellsComi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(txtPaidComi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(txtTotComi)
                                .addGap(1, 1, 1))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel13)))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblBalanceComi, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pending Sales  man Commission ", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblAllPendingDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAllPendingDetailsMouseClicked
        int selectedRow = tblAllPendingDetails.getSelectedRow();
        int clickCount = evt.getClickCount();
        if (clickCount == 2) {
            try {
                int custID = Integer.parseInt(tblAllPendingDetails.getValueAt(selectedRow, 0).toString());
                String custName = tblAllPendingDetails.getValueAt(selectedRow, 2).toString();
                int vID = Integer.parseInt(tblAllPendingDetails.getValueAt(selectedRow, 1).toString());
                DebtDetails dd = new DebtDetails(null, true, custID, vID, custName);
                dd.setVisible(true);
                DebtController.loadAllPendingDebtDetails(peningDetailsDtm);
            } catch (ClassNotFoundException ex) {
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_tblAllPendingDetailsMouseClicked

    private void cmbAllCustomersPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbAllCustomersPopupMenuWillBecomeInvisible
        String customer = cmbAllCustomers.getSelectedItem().toString();
        try {
            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(peningDetailsDtm);
            tblAllPendingDetails.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + customer)));
            lblTotalPendingAmount.setText("" + calculateAllPendingAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_cmbAllCustomersPopupMenuWillBecomeInvisible

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(completedPaymentDtm);
        tbl_completed_payment.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + txt_search.getText()));
    }//GEN-LAST:event_txt_searchKeyReleased

    private void tbl_completed_paymentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_completed_paymentMouseClicked
        int row = tbl_completed_payment.getSelectedRow();
        int rows = Integer.parseInt(tbl_completed_payment.getValueAt(row, 0).toString());
        try {
            DebtController.loadAllCompletedPaymentsDetails(PaymentDetailsDtm, rows);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AllDebtsDetail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AllDebtsDetail.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_tbl_completed_paymentMouseClicked

    private void tbl_completed_paymentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_completed_paymentKeyReleased

    }//GEN-LAST:event_tbl_completed_paymentKeyReleased

    private void txtSearchVehicleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchVehicleKeyReleased
        try {
            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(peningDetailsDtm);
            tblAllPendingDetails.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + txtSearchVehicle.getText())));
            lblTotalPendingAmount.setText("" + calculateAllPendingAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtSearchVehicleKeyReleased

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        try {
            Date startDate = startDateSelect.getDate();
            Date endDate = endDateSelect.getDate();
            String strStartDate = sdf.format(startDate);
            String strEndDate = sdf.format(endDate);
            ChequeController.SearchCheque(chequeDetalisDtm, strStartDate, strEndDate);

        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tblChequeDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChequeDetailsMouseClicked

        int selectedRow = tblChequeDetails.getSelectedRow();
        int selectedColumn = tblChequeDetails.getSelectedColumn();

        if (selectedColumn == 10) {
            //     chequeDetalisDtm.getValueAt(selectedRow, 5)+"";
            boolean isAdminAccepted = Boolean.parseBoolean(chequeDetalisDtm.getValueAt(selectedRow, selectedColumn) + "");
            boolean isRejected = Boolean.parseBoolean(chequeDetalisDtm.getValueAt(selectedRow, 11) + "");
            if (isAdminAccepted == true) {
                if (isRejected == true) {
                    chequeDetalisDtm.setValueAt(false, selectedRow, 11);
                }
                chequeDetalisDtm.setValueAt(1 + "", selectedRow, 8);
                chequeDetalisDtm.setValueAt("Paid", selectedRow, 9);

            } else if (isAdminAccepted == false) {

                chequeDetalisDtm.setValueAt(2 + "", selectedRow, 8);
                chequeDetalisDtm.setValueAt("Pending", selectedRow, 9);

            }
        }
        if (selectedColumn == 11) {
            boolean isRejected = Boolean.parseBoolean(chequeDetalisDtm.getValueAt(selectedRow, selectedColumn) + "");
            boolean isAdminAccepted = Boolean.parseBoolean(chequeDetalisDtm.getValueAt(selectedRow, 10) + "");
            if (isRejected == true) {
                if (isAdminAccepted == true) {
                    chequeDetalisDtm.setValueAt(false, selectedRow, 10);
                }
                chequeDetalisDtm.setValueAt(2 + "", selectedRow, 8);
                chequeDetalisDtm.setValueAt("Rejected", selectedRow, 9);
            } else if (isRejected == false) {

                chequeDetalisDtm.setValueAt(1 + "", selectedRow, 8);
                chequeDetalisDtm.setValueAt("Pending", selectedRow, 9);
            }
        }  
        //// TODO add your handling code here:
       
    }//GEN-LAST:event_tblChequeDetailsMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        double pendingAmount = 0.0;
         DebtDetail debtDetail = null;
        Debt dt = null;

        SimpleDateFormat dfoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String payiedDate = Calendar.getInstance().getTime().toString();
        for (int i = 0; i < chequeDetalisDtm.getRowCount(); i++) {
            try {
                int debtid = Integer.parseInt(chequeDetalisDtm.getValueAt(i, 0) + "");
                double amount = Double.parseDouble(chequeDetalisDtm.getValueAt(i, 7) + "");
                String chequeId = chequeDetalisDtm.getValueAt(i, 1) + "";
                String cheque_status = chequeDetalisDtm.getValueAt(i, 8) + "";
                ChequeController.updateChequeStatusByChequeNumber(Integer.parseInt(cheque_status), chequeId);
                System.out.println("^^^^^^"+cheque_status);
                pendingAmount=DebtController.getPendingAmount(debtid);
                
                // System.out.println("$$$$$$$$$$$$$$"+pendingAmount);
                if ((cheque_status!=null)&&(cheque_status.equals("1"))) {

                    int value = 0;
                   // System.out.println(pendingAmount);
                    
                    if (pendingAmount > amount) {
                        debtDetail = new DebtDetail(debtid, payiedDate, amount, 1);
                        value = DebtController.addNewDebtDetail(debtDetail);
                    } else if (pendingAmount == amount) {
                        dt = new Debt();
                        dt.setDebtID(debtid);
                        dt.setStatus(1);
                        debtDetail = new DebtDetail(debtid, payiedDate, amount, 1);
                        DebtController.updateStatus(dt);
                        value = DebtController.addNewDebtDetail(debtDetail);

                    }
                    if (value > 0) {
                        JOptionPane.showMessageDialog(this, "payment added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AllDebtsDetail.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(AllDebtsDetail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
             //   old Code
//      for (int i = 0; i < chequeDetalisDtm.getRowCount(); i++) {
//          try {
//                String chequeId = chequeDetalisDtm.getValueAt(i, 0) + "";
//                String cheque_status = chequeDetalisDtm.getValueAt(i, 7) + "";
//                ChequeController.updateChequeStatusByChequeNumber(Integer.parseInt(cheque_status), chequeId);
//                System.out.println(cheque_status);
////              
//        if (cheque_status == null ? 1 + "" == null : cheque_status.equals(1 + "")) {
//               // if ( cheque_status.equals( 1+ "")) {
//                    double amount = Double.parseDouble(chequeDetalisDtm.getValueAt(i, 6) + "");
//                   // double pendingAmount = this.pendingAmount;
//                    if (pendingAmount == amount) {
//                        System.out.println("pendingAmount == amount");
//                        dt = new Debt();
//                        dt.setDebtID(debtID);
//                        dt.setStatus(1);
//                        
//                        DebtController.updateStatus(dt);
//                        debtDetail = new DebtDetail(debtID, payiedDate, amount, 1);
//                        
//                        int value = DebtController.addNewDebtDetail(debtDetail);
//        
//       
//              
////                
//                           JOptionPane.showMessageDialog(this, "Saved successfully.", "Succeeded", JOptionPane.INFORMATION_MESSAGE);  
//
//                    }
//                }   
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cmbAllCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAllCustomersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAllCustomersActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String addedDate = sdf.format(new Date());
            sdf.applyPattern("HH:mm:ss");
            String addedTime = sdf.format(new Date());
            double amount = Double.parseDouble(txtPaidComi.getText());
            int status = 1;
            int paidStatus = 0;
            
            sellsCommi sc = new sellsCommi();
            sc.setAddedDate(addedDate);
            sc.setAddedTime(addedTime);
            sc.setAmount(amount);
            sc.setStatus(status);
            sc.setPaidStatus(paidStatus);
            
            int rowCount =tblPendigSellsComi.getRowCount();
            ArrayList<sellsCommiDetail> crDetailList1 = new ArrayList<>();
            for (int i = 0; i < rowCount; i++) {
                boolean isRet = Boolean.parseBoolean(tblPendigSellsComi.getValueAt(i, 9).toString());
                if (isRet) {
                    int ItemID = Integer.parseInt(tblPendigSellsComi.getValueAt(i, 0).toString());
                    int orderID = Integer.parseInt(tblPendigSellsComi.getValueAt(i, 1).toString());
                    int empId = Integer.parseInt(tblPendigSellsComi.getValueAt(i, 2).toString());
                    double commision = Double.parseDouble(tblPendigSellsComi.getValueAt(i, 8).toString());
                    int detailid = Integer.parseInt(tblPendigSellsComi.getValueAt(i, 10).toString());
                    int status2 = 1;
                    sellsCommiDetail srDetail = new sellsCommiDetail(ItemID, orderID, empId, commision, status);
                    crDetailList1.add(srDetail);

                    int paids = sellsComissionController.UpdatePaidStatusSellComi(1, detailid);
                }
            }
            boolean isAdded = sellsComissionController.addNewSellsCommision(sc, crDetailList1);
            if (isAdded) {
                clearValues();
                JOptionPane.showMessageDialog(this, "commision paid successfully.", "Succeeded", JOptionPane.INFORMATION_MESSAGE);
                for (int i = 0; i < pendingSellsComi.getRowCount(); i++) {
                    Boolean checked = (Boolean) pendingSellsComi.getValueAt(i, 9);
                    if (checked) {
                        pendingSellsComi.removeRow(i);
                        i--;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "An error occured while adding", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AllDebtsDetail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AllDebtsDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jCmbSellscomiPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jCmbSellscomiPopupMenuWillBecomeInvisible
        String employee = jCmbSellscomi.getSelectedItem().toString();
        try {

            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(pendingSellsComi);
            tblPendigSellsComi.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + employee)));
            lblTotSellsComi.setText(String.format("%.2f", calculateTotalSellsComi()));

        } catch (Exception e) {
            e.printStackTrace();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jCmbSellscomiPopupMenuWillBecomeInvisible

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       try {
            Date startDateComi = jDateChooser1.getDate();
            Date endDateComi = jDateChooser2.getDate();
            String StartDate1 = sdf.format(startDateComi);
            String EndDate1 = sdf.format(endDateComi);
            sellsComissionController.SearchSellsCommi(pendingSellsComi, StartDate1, EndDate1);
            lblTotSellsComi.setText(String.format("%.2f", calculateTotalSellsComi()));
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }        // TODO add your handling code here:
           // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tblPendigSellsComiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPendigSellsComiMouseClicked
    try {
            txtTotComi.setText(String.format("%.2f", calculateSelectedSellsComi()));
          
        } catch (NumberFormatException numberFormatException) {
        } catch (Exception ex) {

        }    
       
     // TODO add your handling code here:
    }//GEN-LAST:event_tblPendigSellsComiMouseClicked

    private void txtPaidComiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPaidComiActionPerformed
         try {
            double paidAmount = Double.parseDouble(txtPaidComi.getText());
            lblBalanceComi.setText("" + calculateBalanceComi(paidAmount));
        } catch (NumberFormatException numberFormatException) {
        } catch (Exception ex) {

        }           // TODO add your handling code here:
    }//GEN-LAST:event_txtPaidComiActionPerformed

    private void txtPaidComiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaidComiFocusGained
          txtPaidComi.selectAll();  // TODO add your handling code here:
    }//GEN-LAST:event_txtPaidComiFocusGained

    private void txtPaidComiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaidComiFocusLost
      String val = txtPaidComi.getText();
        if (val.isEmpty() || val.equals(null)) {
            txtPaidComi.setText("0.00");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaidComiFocusLost

    private void txtPaidComiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidComiKeyPressed
        ValidateValues.validateTextFieldForNumber(txtPaidComi);
        try {
            double paidAmount = Double.parseDouble(txtPaidComi.getText());
            lblBalanceComi.setText("" + calculateBalanceComi(paidAmount));
        } catch (NumberFormatException numberFormatException) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaidComiKeyPressed

    private void txtPaidComiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaidComiKeyReleased
            try {
            double paidAmount = Double.parseDouble(txtPaidComi.getText());
            lblBalanceComi.setText("" + calculateBalanceComi(paidAmount));
           
        } catch (NumberFormatException numberFormatException) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtPaidComiKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox cmbAllCustomers;
    private com.toedter.calendar.JDateChooser endDateSelect;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jCmbSellscomi;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblBalanceComi;
    private javax.swing.JLabel lblTotSellsComi;
    private javax.swing.JLabel lblTotalPendingAmount;
    private com.toedter.calendar.JDateChooser startDateSelect;
    private javax.swing.JTable tblAllPendingDetails;
    private javax.swing.JTable tblChequeDetails;
    private javax.swing.JTable tblPaymentDetails;
    private javax.swing.JTable tblPendigSellsComi;
    private javax.swing.JTable tbl_completed_payment;
    private javax.swing.JTextField txtPaidComi;
    private javax.swing.JTextField txtSearchVehicle;
    private javax.swing.JTextField txtTotComi;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables
}
