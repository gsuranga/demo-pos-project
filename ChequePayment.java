/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.debtviews;

import controllers.ChequeBankController;
import controllers.ChequeController;
import controllers.DebtController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Cheque;
import models.Debt;
import models.DebtDetail;




/**
 *
 * @author user-pc
 */
public class ChequePayment extends javax.swing.JFrame {

    private DefaultTableModel checkDtm;
    private int debtID;
    private double pendingAmount;
     private SimpleDateFormat sdf;
   
    /**
     * Creates new form ChequePayment
     */
    public ChequePayment(int debtID, double pendingAmount) {
        try {
            initComponents();
           
            this.debtID = debtID;
            this.pendingAmount = pendingAmount;
            jdate.setDate(new Date());
            checkDtm = (DefaultTableModel) checktbl.getModel();
            ChequeController.loadAllCheque(checkDtm);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            ResultSet banksList = ChequeBankController.getBanksList();
            while(banksList.next()){
                jCombo_Bank.addItem(banksList.getString(1));
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addNewCheque() throws ClassNotFoundException, SQLException {
        try {
            String checkNo = txt_checkNo.getText();
            String deler = txt_deler.getText();
            String vmodel = jCombo_Bank.getSelectedItem().toString();
            String accountNO = txt_acconutNo.getText();
            Date d1 = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String adddedDate = sdf.format(d1);
            String checkDate = sdf.format(jdate.getDate());
            sdf.applyPattern("HH:mm:ss");
            String addedTime = sdf.format(d1);
            double amount = Double.parseDouble(txt_amount.getText());
            int status = 1;
            int chequestatus = 3;

            if (pendingAmount < amount) {
                JOptionPane.showMessageDialog(null, "Invalid Amount");
            } else if (checkNo == null || checkNo == "" || checkNo == " " || checkNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Field is empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (vmodel == null || vmodel == "" || vmodel == " " || vmodel.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Field is empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (accountNO == null || accountNO == "" || accountNO == " " || accountNO.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Field is empty", "Error", JOptionPane.ERROR_MESSAGE);

            } else if (checkDate == "" || checkDate == null || checkDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a date", "Empty date", JOptionPane.ERROR_MESSAGE);
            } else if (amount == 0) {
                JOptionPane.showMessageDialog(this, "Amount cannot be 0 or minus", "Wrong Amount", JOptionPane.ERROR_MESSAGE);
            } else {
                Cheque check = new Cheque(debtID, checkNo, deler, vmodel, accountNO, adddedDate, addedTime, checkDate, amount, status, chequestatus);
                int value = ChequeController.addNewCheque(check);
                DebtDetail debtDetail = null;
                //Debt dt = null;

                SimpleDateFormat dfoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String payiedDate = Calendar.getInstance().getTime().toString();
                if (pendingAmount > amount) {
                    debtDetail = new DebtDetail(debtID, payiedDate, amount, 2, 1);
                    value = DebtController.addNewDebtDetail(debtDetail);
                } /*else if (pendingAmount == amount) {
                 dt = new Debt();
                 dt.setDebtID(debtID);
                 dt.setStatus(1);
                 debtDetail = new DebtDetail(debtID, payiedDate, amount, 2, 1);
                 DebtController.updateStatus(dt);
                 value = DebtController.addNewDebtDetail(debtDetail);
                 */ else if (pendingAmount < amount) {
                    JOptionPane.showMessageDialog(this, "Payment is too much", "Message", JOptionPane.INFORMATION_MESSAGE);
                }

                if (value > 0) {
                    JOptionPane.showMessageDialog(this, "Cheque added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    ChequeController.loadAllCheque(checkDtm);

                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please check your values again.", "Error in values", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_checkNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jCombo_Bank = new javax.swing.JComboBox();
        btnAdd = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_deler = new javax.swing.JTextField();
        txt_acconutNo = new javax.swing.JTextField();
        txt_amount = new javax.swing.JTextField();
        jdate = new com.toedter.calendar.JDateChooser();
        btnadd = new javax.swing.JButton();
        btncancle = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        checktbl = new javax.swing.JTable();
        btnSaved = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jDateStart1 = new com.toedter.calendar.JDateChooser();
        jDateEnd = new com.toedter.calendar.JDateChooser();
        btnSearch1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cheque Payment");

        jLabel1.setText("Cheque No :");

        jLabel2.setText("Bank :");

        jCombo_Bank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCombo_BankActionPerformed(evt);
            }
        });

        btnAdd.setText("+");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jLabel3.setText("Account Number :");

        jLabel4.setText("Cheque Date :");

        jLabel5.setText("Amount :");

        jLabel6.setText("Delear name :");

        btnadd.setText("Add");
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });

        btncancle.setText("Cancle");
        btncancle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(btnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btncancle)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCombo_Bank, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txt_deler, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                        .addComponent(txt_checkNo, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                        .addComponent(txt_amount, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_acconutNo, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_checkNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_deler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCombo_Bank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_acconutNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnadd)
                    .addComponent(btncancle))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        checktbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cheque No", "Delear Name", "Bank", "Account Number", "Cheque Date", "Amount", "Deposit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        checktbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checktblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(checktbl);

        btnSaved.setText("Send Deposit Cheque");
        btnSaved.setActionCommand("Saved");
        btnSaved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSavedActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cheque Search By date :"));

        jLabel7.setText("From :");

        jLabel8.setText("Date :");

        btnSearch1.setText("Search ");
        btnSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(37, 37, 37)
                .addComponent(jDateStart1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jDateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSearch1)
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateStart1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(btnSearch1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSaved)
                        .addGap(200, 200, 200))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaved)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        try {
            addNewCheque();
           ChequeController.loadAllCheque(checkDtm);
            // TODO add your handling code here:
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnaddActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        ChequeBank b = new ChequeBank(this);
        b.setVisible(true);// TODO add your handling code here:
    }//GEN-LAST:event_btnAddActionPerformed

    private void jCombo_BankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCombo_BankActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCombo_BankActionPerformed

    private void btncancleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancleActionPerformed
        dispose(); // TODO add your handling code here:
    }//GEN-LAST:event_btncancleActionPerformed

    private void checktblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checktblMouseClicked

       /* int selectedRow = checktbl.getSelectedRow();
        int selectedColumn = checktbl.getSelectedColumn();
        if (selectedColumn == 9) {
            boolean isAdminAccepted = Boolean.parseBoolean(checkDtm.getValueAt(selectedRow, selectedColumn) + "");
            if (isAdminAccepted == true) {
                Debt dt = null;
        int value=0;
        SimpleDateFormat dfoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String payiedDate = Calendar.getInstance().getTime().toString();  
        DebtDetail debtDetail = null;
        Cheque check=null;
               double amount = Double.parseDouble(txt_amount.getText());
        if(selectedColumn==7){
            if(Boolean.parseBoolean(checkDtm.getValueAt(selectedRow, selectedColumn)+"")== true){
               if(pendingAmount == amount) {
               try{
                    dt = new Debt();
                    dt.setDebtID(debtID);
                    dt.setStatus(1);
                    debtDetail = new DebtDetail(debtID, payiedDate, amount, 2, 1);
                    DebtController.updateStatus(dt);
                    //checktbl.setValueAt("Paid", selectedRow, selectedColumn);
                    value = DebtController.addNewDebtDetail(debtDetail);
                    ChequeController.loadAllCheque(checkDtm);
                
                
                checkDtm.setValueAt(false, selectedRow, 10);
                checkDtm.setValueAt(1 + "", selectedRow, 7);
                checkDtm.setValueAt("Paid", selectedRow, 8);
               }catch(Exception ex){
                   ex.printStackTrace();
           }
                }
            } else if (isAdminAccepted == false) {
                checkDtm.setValueAt(true, selectedRow, 10);
                checkDtm.setValueAt(2 + "", selectedRow, 7);
                checkDtm.setValueAt("Rejected", selectedRow, 8);
            }
        }
        if (selectedColumn == 10) {
            boolean isRejected = Boolean.parseBoolean(checkDtm.getValueAt(selectedRow, selectedColumn) + "");
            if (isRejected == true) {
                checkDtm.setValueAt(false, selectedRow, 9);
                checkDtm.setValueAt(2 + "", selectedRow, 7);
                checkDtm.setValueAt("Rejected", selectedRow, 8);
            } else if (isRejected == false) {
                checkDtm.setValueAt(true, selectedRow, 9);
                checkDtm.setValueAt(1 + "", selectedRow, 7);
                checkDtm.setValueAt("Paid", selectedRow, 8);
            }
        }                
    }//GEN-LAST:event_checktblMouseClicked
        }*/
    }
    private void btnSavedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavedActionPerformed

        for(int i=0;i<checkDtm.getRowCount();i++){
            if(Boolean.parseBoolean(checkDtm.getValueAt(i, 6)+"")== true){
                try {
                    String chequeNo = checkDtm.getValueAt(i, 0)+"";
                    int depositCheque = ChequeController.depositCheque(4, chequeNo);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
        
//        int rows = checkDtm.getRowCount();
//        double totalPaymentByCheque = 0;
//
//        for (int i = 0; i < rows; i++) {
//            try {            
//                int idCheque = Integer.parseInt(checkDtm.getValueAt(i, 0) + "");
//                int cheque_status = Integer.parseInt(checkDtm.getValueAt(i, 7) + "");
//
//                ChequeController.updateChequeStatus(cheque_status, idCheque);
//                
//                if(cheque_status ==1){
//                    totalPaymentByCheque += Double.parseDouble(checkDtm.getValueAt(i, 6) + "");
//                }
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SQLException ex) {
//                Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            
//        }

         
    }//GEN-LAST:event_btnSavedActionPerformed

    private void btnSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch1ActionPerformed
       try {
            Date ChequestartDate = jDateStart1.getDate();
            Date ChequeendDate = jDateEnd.getDate();
            String strStartDate1 = sdf.format(ChequestartDate);
            String strEndDate1 = sdf.format(ChequeendDate);
           ChequeController.SearchCheque1(checkDtm, strStartDate1, strEndDate1);
            //ExpenceController.SearchExpences(expenceModel, strStartDate1, strEndDate1);
            

        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }           // TODO add your handling code here:
    }//GEN-LAST:event_btnSearch1ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnSaved;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JButton btnadd;
    private javax.swing.JButton btncancle;
    private javax.swing.JTable checktbl;
    public javax.swing.JComboBox jCombo_Bank;
    private com.toedter.calendar.JDateChooser jDateEnd;
    private com.toedter.calendar.JDateChooser jDateStart1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdate;
    private javax.swing.JTextField txt_acconutNo;
    private javax.swing.JTextField txt_amount;
    private javax.swing.JTextField txt_checkNo;
    private javax.swing.JTextField txt_deler;
    // End of variables declaration//GEN-END:variables
}
