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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Cheque;
import models.DebtDetail;

/**
 *
 * @author user-pc
 */
public class ChequePayment extends javax.swing.JDialog {
    private DefaultTableModel checkDtm;
    private int debtID;
    private double pendingAmount;
     private SimpleDateFormat sdf;

    /**
     * Creates new form ChequePayment
     */
     public ChequePayment(java.awt.Frame parent, boolean modal){
      super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
     }
    public ChequePayment(java.awt.Frame parent, boolean modal,int debtID, double pendingAmount) throws SQLException {
        super(parent, modal);
             initComponents();
              setLocationRelativeTo(null);
          
        try {
            this.debtID = debtID;
            this.pendingAmount = pendingAmount;
            jdate.setDate(new Date());
            jDateStart1.setDate(new Date());
            jDateEnd.setDate(new Date());
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
    
    public void clearValues() {
        txt_deler.setText("");
        txt_AccountNo.setText("");
        txt_checkNo.setText("");
        txt_Amount.setText("0.00");
        
    }

    public void addNewCheque() throws ClassNotFoundException, SQLException {
        try {
            String checkNo = txt_checkNo.getText();
            String deler = txt_deler.getText();
            String vmodel = jCombo_Bank.getSelectedItem().toString();
            String accountNO = txt_AccountNo.getText();
            Date d1 = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String adddedDate = sdf.format(d1);
            String checkDate = sdf.format(jdate.getDate());
            sdf.applyPattern("HH:mm:ss");
            String addedTime = sdf.format(d1);
            double amount = Double.parseDouble(txt_Amount.getText());
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

              //  SimpleDateFormat dfoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
              //  String payiedDate = Calendar.getInstance().getTime().toString();
               // if (pendingAmount > amount) {
                   // debtDetail = new DebtDetail(debtID, payiedDate, amount, 1);
                   // value = DebtController.addNewDebtDetail(debtDetail);
               // }
            /*else if (pendingAmount == amount) {
                 dt = new Debt();
                 dt.setDebtID(debtID);
                 dt.setStatus(1);
                 debtDetail = new DebtDetail(debtID, payiedDate, amount, 2, 1);
                 DebtController.updateStatus(dt);
                 value = DebtController.addNewDebtDetail(debtDetail);
//                 */ //else if (pendingAmount < amount) {
//                    JOptionPane.showMessageDialog(this, "Payment is too much", "Message", JOptionPane.INFORMATION_MESSAGE);
//                }

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
        txt_deler = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_AccountNo = new javax.swing.JTextField();
        txt_Amount = new javax.swing.JTextField();
        jCombo_Bank = new javax.swing.JComboBox();
        btnAdd = new javax.swing.JButton();
        jdate = new com.toedter.calendar.JDateChooser();
        btnAddCheque = new javax.swing.JButton();
        btnCancle = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jDateStart1 = new com.toedter.calendar.JDateChooser();
        jDateEnd = new com.toedter.calendar.JDateChooser();
        btnSearch1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        checktbl = new javax.swing.JTable();
        btnSavedDeposit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cheque Payment");

        jLabel1.setText("Cheque No:");

        txt_checkNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_checkNoActionPerformed(evt);
            }
        });

        jLabel2.setText("Customer Name:");

        txt_deler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_delerActionPerformed(evt);
            }
        });

        jLabel3.setText("Bank :");

        jLabel4.setText("Cheque Date :");

        jLabel5.setText("Account Number :");

        jLabel6.setText("Amont :");

        txt_AccountNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AccountNoActionPerformed(evt);
            }
        });

        txt_Amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AmountActionPerformed(evt);
            }
        });

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

        jdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jdateKeyPressed(evt);
            }
        });

        btnAddCheque.setText("ADD");
        btnAddCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddChequeActionPerformed(evt);
            }
        });

        btnCancle.setText("Cancle");
        btnCancle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_checkNo)
                            .addComponent(txt_deler)
                            .addComponent(txt_Amount)
                            .addComponent(txt_AccountNo)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCombo_Bank, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAddCheque)
                        .addGap(79, 79, 79)
                        .addComponent(btnCancle)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_checkNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_deler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCombo_Bank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdd)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_AccountNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddCheque)
                    .addComponent(btnCancle))
                .addGap(40, 40, 40))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search By Cheque Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N

        jLabel7.setText("From:");

        jLabel8.setText("TO:");

        btnSearch1.setText("Search");
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
                .addGap(18, 18, 18)
                .addComponent(jDateStart1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jDateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSearch1)
                .addGap(74, 74, 74))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateStart1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7)))
                .addGap(18, 18, 18)
                .addComponent(btnSearch1)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        checktbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cheque No", "Customer Name", "Bank", "Account Number", "Cheque Date", "Amount", "Deposit"
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
        jScrollPane1.setViewportView(checktbl);

        btnSavedDeposit.setText("Deposit Cheque");
        btnSavedDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSavedDepositActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSavedDeposit)
                        .addGap(119, 119, 119))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSavedDeposit)))
                .addGap(49, 49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddChequeActionPerformed
     try {
            addNewCheque();
           ChequeController.loadAllCheque(checkDtm);
           clearValues();
            // TODO add your handling code here:
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddChequeActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
  
            try {
                
                ChequeBank b = new ChequeBank(new JFrame(), true);
               b.setVisible(true);
//                ChequeBank b = new ChequeBank(this);
//            b.setVisible(true);
                ChequeBankController.fillBankList(jCombo_Bank);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
            
        
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCancleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancleActionPerformed
clearValues();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancleActionPerformed

    private void btnSavedDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSavedDepositActionPerformed
     for(int i=0;i<checkDtm.getRowCount();i++){
            if(Boolean.parseBoolean(checkDtm.getValueAt(i, 6)+"")== true){
               
                    String chequeNo = checkDtm.getValueAt(i, 0)+"";
                try {
                    int depositCheque = ChequeController.depositCheque(4, chequeNo);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ChequePayment.class.getName()).log(Level.SEVERE, null, ex);
                }
                    dispose();
                 //JOptionPane.showMessageDialog(this, "Deposit Cheque successfully.", "Succeeded", JOptionPane.INFORMATION_MESSAGE);
            }
     } // TODO add your handling code here:
    }//GEN-LAST:event_btnSavedDepositActionPerformed

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
        }         // TODO add your handling code here:
    }//GEN-LAST:event_btnSearch1ActionPerformed

    private void txt_checkNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_checkNoActionPerformed
txt_deler.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_checkNoActionPerformed

    private void txt_delerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_delerActionPerformed
jCombo_Bank.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_delerActionPerformed

    private void jCombo_BankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCombo_BankActionPerformed
txt_AccountNo.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_jCombo_BankActionPerformed

    private void txt_AccountNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AccountNoActionPerformed
jdate.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_AccountNoActionPerformed

    private void jdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdateKeyPressed
txt_Amount.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_jdateKeyPressed

    private void txt_AmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AmountActionPerformed
btnAddCheque.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_AmountActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ChequePayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ChequePayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ChequePayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ChequePayment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              
                    ChequePayment dialog = new ChequePayment(new javax.swing.JFrame(),true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddCheque;
    private javax.swing.JButton btnCancle;
    private javax.swing.JButton btnSavedDeposit;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JTable checktbl;
    private javax.swing.JComboBox jCombo_Bank;
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
    private javax.swing.JTextField txt_AccountNo;
    private javax.swing.JTextField txt_Amount;
    private javax.swing.JTextField txt_checkNo;
    private javax.swing.JTextField txt_deler;
    // End of variables declaration//GEN-END:variables
}
