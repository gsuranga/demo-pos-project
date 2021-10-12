/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.dealer_returns;

import controllers.DealerReturnController;
import controllers.StockController;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.StockDetail;

/**
 *
 * @author shdinesh.99
 */
public class ReturnParts extends javax.swing.JDialog {

    private DefaultTableModel returnTableDtm;
    private int deliverOrderID;
    private int dealerReturnID;
    private int repReturnID;
    private int adminReturnID;
    private String returnNoteNo;

    /**
     * Creates new form ReturnParts
     */
    public ReturnParts(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.returnTableDtm = (DefaultTableModel) tblReturnDetails.getModel();
        try {
            loadAllDealerReturnDetails(dealerReturnID);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
        if (repReturnID > 0) {
            btnBalanceStock.setEnabled(true);
        } else {
            btnBalanceStock.setEnabled(false);
        }
    }

    public ReturnParts(java.awt.Frame parent, boolean modal, int deliverOrderID, int dealerReturnID, int repReturnID, int adminReturnID, String returnNoteNo) {
        super(parent, modal);
        initComponents();
        this.returnTableDtm = (DefaultTableModel) tblReturnDetails.getModel();
        this.deliverOrderID = deliverOrderID;
        this.dealerReturnID = dealerReturnID;
        this.repReturnID = repReturnID;
       // this.adminReturnID = adminReturnID;
        this.returnNoteNo = returnNoteNo;
        setTitle("Return Note: " + returnNoteNo);
        setLocationRelativeTo(null);
        try {
            loadAllDealerReturnDetails(dealerReturnID);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }

    public void loadAllDealerReturnDetails(int dealerReturnID) throws ClassNotFoundException, SQLException {
        ResultSet rst = DealerReturnController.viewAllReturnedParts(dealerReturnID);
        String tableRow[] = new String[11];
        returnTableDtm.setRowCount(0);
        while (rst.next()) {
            for (int i = 0; i <= 10; i++) {
                if (i == 6) {
                    if (rst.getString(i + 1).equals("0")) {
                        tableRow[i] = "No";
                    } else if (rst.getString(i + 1).equals("1")) {
                        tableRow[i] = "Yes";
                    }
                } else {
                    tableRow[i] = rst.getString(i + 1);
                }

            }

            returnTableDtm.addRow(tableRow);
        }
    }

    public void balanceStock() throws ClassNotFoundException, SQLException {
        int rowCount = tblReturnDetails.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            int itemID = Integer.parseInt(tblReturnDetails.getValueAt(i, 0).toString());
            double unitPrice = Double.parseDouble(tblReturnDetails.getValueAt(i, 5).toString());
            double dealerReturnQty = Double.parseDouble(tblReturnDetails.getValueAt(i, 3).toString());
            double repAcceptedQty = Double.parseDouble(tblReturnDetails.getValueAt(i, 7).toString());
            double adminAcceptedQty = Double.parseDouble(tblReturnDetails.getValueAt(i, 9).toString());
            double backToStock = 0;
            if (dealerReturnQty != repAcceptedQty) {
                backToStock += dealerReturnQty - repAcceptedQty;
            }
//            if (repAcceptedQty != adminAcceptedQty) {
//                backToStock += repAcceptedQty - adminAcceptedQty;
//            }
            if (backToStock > 0) {
                StockDetail stockDetail = new StockDetail();
                stockDetail.setStockQuontity(backToStock);
                stockDetail.setBuyingPrice(unitPrice);
                stockDetail.setSellingPrice(unitPrice);
                stockDetail.setStatus(1);
                stockDetail.setRemainingQty(backToStock);
                stockDetail.setStockID(StockController.getStockIDForDeliverOrder(deliverOrderID));
                stockDetail.setItemID(itemID);
                int rowCountUpdated = StockController.updateStockONStockBalance(stockDetail);
                if (rowCountUpdated == 0) {
                    StockController.addNewStockDetail(stockDetail);
                }
            }
        }
        if (this.repReturnID > 0) {
            DealerReturnController.updateCompletedStatus(dealerReturnID);
        }
        JOptionPane.showMessageDialog(this, "Stock updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblReturnDetails = new javax.swing.JTable();
        btnBalanceStock = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblReturnDetails.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblReturnDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "item id", "Part No.", "Description", "Dealer returned qty.", "Dealer returned reason", "Unit Price", "Delivered", "Accepted qty - Rep", "Remarks - Rep", "Accepted qty - Admin", "Remarks - Admin"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblReturnDetails);
        if (tblReturnDetails.getColumnModel().getColumnCount() > 0) {
            tblReturnDetails.getColumnModel().getColumn(0).setMinWidth(0);
            tblReturnDetails.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblReturnDetails.getColumnModel().getColumn(0).setMaxWidth(0);
            tblReturnDetails.getColumnModel().getColumn(6).setMinWidth(50);
            tblReturnDetails.getColumnModel().getColumn(6).setPreferredWidth(50);
            tblReturnDetails.getColumnModel().getColumn(6).setMaxWidth(50);
            tblReturnDetails.getColumnModel().getColumn(9).setMinWidth(0);
            tblReturnDetails.getColumnModel().getColumn(9).setPreferredWidth(0);
            tblReturnDetails.getColumnModel().getColumn(9).setMaxWidth(0);
            tblReturnDetails.getColumnModel().getColumn(10).setMinWidth(0);
            tblReturnDetails.getColumnModel().getColumn(10).setPreferredWidth(0);
            tblReturnDetails.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        btnBalanceStock.setText("Balance Stock");
        btnBalanceStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBalanceStockActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBalanceStock, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBalanceStock)
                    .addComponent(btnCancel))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBalanceStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBalanceStockActionPerformed
        try {
            balanceStock();
            dispose();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnBalanceStockActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

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
            java.util.logging.Logger.getLogger(ReturnParts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReturnParts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReturnParts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReturnParts.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ReturnParts dialog = new ReturnParts(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBalanceStock;
    private javax.swing.JButton btnCancel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblReturnDetails;
    // End of variables declaration//GEN-END:variables
}
