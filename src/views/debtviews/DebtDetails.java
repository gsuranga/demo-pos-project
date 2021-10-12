/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views.debtviews;

import controllers.DebtController;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SHdinesh Madushanka
 */
public class DebtDetails extends javax.swing.JDialog {

    private DefaultTableModel pendingDebtsDtm, debtDetailsDtm;
    private int custID, vID;

    /**
     * Creates new form DebtDetails
     */
    public DebtDetails(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

    }

    public DebtDetails(java.awt.Frame parent, boolean modal, int customerID, int vID, String customerName) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle(customerName);
        this.custID = customerID;
        this.vID = vID;
        pendingDebtsDtm = (DefaultTableModel) tblPendingDebts.getModel();
        debtDetailsDtm = (DefaultTableModel) tblDebtDetails.getModel();
        try {
            DebtController.loadAllPendingDebts(pendingDebtsDtm, customerID, vID);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
        }
        checkEndDateButton();
        checkPaymentButtons();

    }

    public void checkEndDateButton() {
        int rowCount = tblPendingDebts.getSelectedRowCount();
        if (rowCount > 0) {
            btnManageEndDate.setEnabled(true);
            btnAddPayment.setEnabled(true);
        } else {
            btnManageEndDate.setEnabled(false);
            btnAddPayment.setEnabled(false);

        }
    }

    public void checkPaymentButtons() {
        int selectedRowCount = tblDebtDetails.getSelectedRowCount();
        if (selectedRowCount > 0) {
            btnDelete.setEnabled(true);
            btnUpdate.setEnabled(true);
        } else {
            btnDelete.setEnabled(false);
            btnUpdate.setEnabled(false);
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
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDebtDetails = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPendingDebts = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btnAddPayment = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnManageEndDate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblDebtDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblDebtDetails.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblDebtDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DetailID", "Payment Date", "Amount (Rs.)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDebtDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDebtDetailsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDebtDetails);
        if (tblDebtDetails.getColumnModel().getColumnCount() > 0) {
            tblDebtDetails.getColumnModel().getColumn(0).setMinWidth(0);
            tblDebtDetails.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblDebtDetails.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        tblPendingDebts.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblPendingDebts.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblPendingDebts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Debt ID", "Sales Order No", "Debt Date", "End Date", "Debt Amount (Rs.)", "Pending Amount (Rs.)", "Garge Commission"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPendingDebts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPendingDebtsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblPendingDebtsMousePressed(evt);
            }
        });
        tblPendingDebts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblPendingDebtsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblPendingDebts);
        if (tblPendingDebts.getColumnModel().getColumnCount() > 0) {
            tblPendingDebts.getColumnModel().getColumn(0).setMinWidth(0);
            tblPendingDebts.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblPendingDebts.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        txtSearch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnAddPayment.setText("Add Payment");
        btnAddPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPaymentActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update Payment");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete Payment");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 30, Short.MAX_VALUE)
                .addComponent(btnAddPayment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDelete))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddPayment, btnDelete, btnUpdate});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddPayment)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddPayment, btnDelete, btnUpdate});

        btnManageEndDate.setText("Manage End Date");
        btnManageEndDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageEndDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(484, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnManageEndDate)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton1)
                            .addContainerGap(487, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 628, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                            .addGap(17, 17, 17)))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnManageEndDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 448, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2)
                        .addComponent(jScrollPane1))
                    .addGap(66, 66, 66)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblDebtDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDebtDetailsMouseClicked

        checkPaymentButtons();
    }//GEN-LAST:event_tblDebtDetailsMouseClicked

    private void btnAddPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPaymentActionPerformed
        try {
            int selected = tblPendingDebts.getSelectedRow();
            int debtID = Integer.parseInt(tblPendingDebts.getValueAt(selected, 0).toString());
            double pendingAmount = Double.parseDouble(tblPendingDebts.getValueAt(selected, 5).toString());
            new NewPayment(null, true, debtID, pendingAmount,pendingDebtsDtm).setVisible(true);
            DebtController.loadAllPendingDebts(pendingDebtsDtm, custID, vID);
            debtDetailsDtm.setRowCount(0);
            checkPaymentButtons();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnAddPaymentActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
   }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblPendingDebtsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPendingDebtsMouseClicked
        checkEndDateButton();
        int selectedRow = tblPendingDebts.getSelectedRow();
        int debtID = Integer.parseInt(tblPendingDebts.getValueAt(selectedRow, 0).toString());
        try {
            DebtController.loadAllDebtDetails(debtDetailsDtm, debtID);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
        }
    }//GEN-LAST:event_tblPendingDebtsMouseClicked

    private void tblPendingDebtsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPendingDebtsMousePressed
        // checkEndDateButton();
        int selectedRow = tblPendingDebts.getSelectedRow();
        int debtID = Integer.parseInt(tblPendingDebts.getValueAt(selectedRow, 0).toString());
        try {
            DebtController.loadAllDebtDetails(debtDetailsDtm, debtID);
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
    }//GEN-LAST:event_tblPendingDebtsMousePressed

    private void tblPendingDebtsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblPendingDebtsKeyReleased
        int keyCode = evt.getKeyCode();
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP) {
            int selectedRow = tblPendingDebts.getSelectedRow();
            int debtID = Integer.parseInt(tblPendingDebts.getValueAt(selectedRow, 0).toString());
            try {
                DebtController.loadAllDebtDetails(debtDetailsDtm, debtID);
            } catch (SQLException ex) {
            } catch (ClassNotFoundException ex) {
            }
        }
    }//GEN-LAST:event_tblPendingDebtsKeyReleased

    private void btnManageEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageEndDateActionPerformed
        try {
            int selectedRow = tblPendingDebts.getSelectedRow();
            int debtID = Integer.parseInt(tblPendingDebts.getValueAt(selectedRow, 0).toString());
            new ChangeEndDate(null, true, debtID, pendingDebtsDtm).setVisible(true);
            DebtController.loadAllPendingDebts(pendingDebtsDtm, custID, vID);
            checkEndDateButton();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }

            }//GEN-LAST:event_btnManageEndDateActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DebtDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DebtDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DebtDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DebtDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                DebtDetails dialog = new DebtDetails(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAddPayment;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnManageEndDate;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblDebtDetails;
    private javax.swing.JTable tblPendingDebts;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}