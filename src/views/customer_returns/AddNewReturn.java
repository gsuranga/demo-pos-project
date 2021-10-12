/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.customer_returns;

import controllers.CustomerReturnsController;
import controllers.SalesOrderController;
import controllers.StockController;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.CustomerReturn;
import models.CustomerReturnDetail;
import utilities_new.AutoGenerateID;
import utilities_new.CheckConnection;

/**
 *
 * @author shdinesh.99
 */
public class AddNewReturn extends javax.swing.JDialog {

    private DefaultTableModel retTableModel;
    private int orderID;

    /**
     * Creates new form AddNewReturn
     */
    public AddNewReturn(java.awt.Frame parent, boolean modal, int orderID) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        retTableModel = (DefaultTableModel) tblInvoiceItems.getModel();
        this.orderID = orderID;
       
        try {
            CustomerReturnsController.loadAllOrderDataToReturn(orderID, retTableModel);

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
        }
    }

    //set database quantity return.(tbl_customer_return table)
    public void addNewCustomerReturn() throws ClassNotFoundException, SQLException {
        int orderID = this.orderID;
        
        int stockID=0;
        String returnNo = "";
        double totalReturnAmount = 0;
        try {
            returnNo = AutoGenerateID.generateNextID("RN", "tbl_customer_return");
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            returnNo = AutoGenerateID.generateNextDBIDOnEmptyResultSet("RN");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String retDate = sdf.format(new Date());
        sdf.applyPattern("HH:mm:ss");
        String retTime = sdf.format(new Date());
        double retAmount = Double.parseDouble(txtRetAmount.getText());
        int status = 1;
        CustomerReturn cr = new CustomerReturn();
        cr.setRetNumber(returnNo);
        cr.setRetAmount(retAmount);
        cr.setRetDate(retDate);
        cr.setRetTime(retTime);
        cr.setSalesOrderID(orderID);
        cr.setStatus(status);

        int rowCount = tblInvoiceItems.getRowCount();
        ArrayList<CustomerReturnDetail> crDetailList = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            Object isReturnQ = tblInvoiceItems.getValueAt(i, 6).toString();
            boolean isReturn = Boolean.parseBoolean((isReturnQ != null && !isReturnQ.toString().isEmpty() ? isReturnQ.toString() : "0"));
                    
            
            if (isReturn) {
                int itemID = Integer.parseInt(tblInvoiceItems.getValueAt(i, 0).toString());
                double retQty = Double.parseDouble(tblInvoiceItems.getValueAt(i, 7).toString());
                String reason = tblInvoiceItems.getValueAt(i, 8).toString();
                double unitPrice = Double.parseDouble(tblInvoiceItems.getValueAt(i, 9).toString());
                int status2= 1;
                double invQty = Double.parseDouble(tblInvoiceItems.getValueAt(i, 3).toString());
                double invAmount = Double.parseDouble(tblInvoiceItems.getValueAt(i, 4).toString());
                 totalReturnAmount += (invAmount / invQty) * retQty;
                System.out.println(totalReturnAmount);
                CustomerReturnDetail crDetail = new CustomerReturnDetail(itemID, retQty, unitPrice, reason, status);
                crDetailList.add(crDetail);
                
                //Get Stock quantity=remmainng+
                 stockID=StockController.getStockIDForReturn(unitPrice, itemID);
                 System.out.println("++++++++"+stockID);
                StockController.updateStockDetailQuantit(retQty, itemID, stockID);
                 
            }
        }
        boolean isAdded = CustomerReturnsController.addCustomerReturnOrder(cr, crDetailList);
        if (isAdded) {
          
          
           CustomerReturnsController.updateDebtsOnReturn(orderID, Double.parseDouble(String.format("%.2f", totalReturnAmount)));
            JOptionPane.showMessageDialog(this, "Returns added successfully.", "Succeeded", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "An error occured while adding returns", "Error", JOptionPane.INFORMATION_MESSAGE);

        }
    }
    
    //set database financial return.(tbl_customer_return table)
     public void addNewCustomerFinancialReturn() throws ClassNotFoundException, SQLException {
        int orderID = this.orderID;
        String returnNo = "";
        double totalFinancialReturnAmount = 0;
        try {
            returnNo = AutoGenerateID.generateNextID("RN", "tbl_customer_return");
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            returnNo = AutoGenerateID.generateNextDBIDOnEmptyResultSet("RN");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String retDate = sdf.format(new Date());
        sdf.applyPattern("HH:mm:ss");
        String retTime = sdf.format(new Date());
        double retAmount = Double.parseDouble(txtRetAmount.getText());
        int status = 1;
        CustomerReturn cr = new CustomerReturn();
        cr.setRetNumber(returnNo);
        cr.setRetAmount(retAmount);
        cr.setRetDate(retDate);
        cr.setRetTime(retTime);
        cr.setSalesOrderID(orderID);
        cr.setStatus(status);

        int rowCount = tblInvoiceItems.getRowCount();
        ArrayList<CustomerReturnDetail> crDetailList = new ArrayList<>();
        int returns=1;
        for (int i = 0; i < rowCount; i++) {
            Object isFinancial = tblInvoiceItems.getValueAt(i, 10).toString();
            boolean isReturn = Boolean.parseBoolean((isFinancial != null && !isFinancial.toString().isEmpty() ? isFinancial.toString() : "0"));
            
            if (isReturn) {
                int itemID = Integer.parseInt(tblInvoiceItems.getValueAt(i, 0).toString());
                boolean financial = Boolean.parseBoolean(tblInvoiceItems.getValueAt(i, 10).toString());
                if(financial == true){
                    returns = 2;
                }
                cr.setReturns(returns);
                //double retQty = Double.parseDouble(tblInvoiceItems.getValueAt(i, 3).toString());
                double retQty = 0.00;
                double discount= Double.parseDouble(tblInvoiceItems.getValueAt(i, 5).toString());
                String reason = tblInvoiceItems.getValueAt(i, 8).toString();
               // double unitPrice = Double.parseDouble(tblInvoiceItems.getValueAt(i, 9).toString());
                double unitPrice = Double.parseDouble(tblInvoiceItems.getValueAt(i, 11).toString());
                int status2 = 1;
                double invQty = Double.parseDouble(tblInvoiceItems.getValueAt(i, 3).toString());
                double invAmount = Double.parseDouble(tblInvoiceItems.getValueAt(i, 4).toString());
                totalFinancialReturnAmount += Double.parseDouble(tblInvoiceItems.getValueAt(i, 4).toString());
                
                System.out.println(totalFinancialReturnAmount);
                System.out.println(unitPrice);
                CustomerReturnDetail crDetail = new CustomerReturnDetail(itemID, retQty, unitPrice, reason, status);
                crDetailList.add(crDetail);
                
            }
        }
        boolean isAdded = CustomerReturnsController.addCustomerReturnOrder(cr, crDetailList);
        if (isAdded) {

            CustomerReturnsController.updateDebtsOnReturn(orderID, Double.parseDouble(String.format("%.2f", totalFinancialReturnAmount)));
            
            JOptionPane.showMessageDialog(this, "Financial Returns added successfully.", "Succeeded", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "An error occured while adding financial returns", "Error", JOptionPane.INFORMATION_MESSAGE);

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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblInvoiceItems = new javax.swing.JTable();
        txtAddreturn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtRetAmount = new javax.swing.JTextField();
        txtAddfinancial = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Return");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        tblInvoiceItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "itemID", "Part No.", "Description", "Qty.", "Amount", "Discount", "Return", "Return Qty.", "Reason", "unit price", "Financial", "Financial Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true, true, true, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInvoiceItems.setRowHeight(20);
        tblInvoiceItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInvoiceItemsMouseClicked(evt);
            }
        });
        tblInvoiceItems.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblInvoiceItemsKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblInvoiceItemsKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblInvoiceItems);
        if (tblInvoiceItems.getColumnModel().getColumnCount() > 0) {
            tblInvoiceItems.getColumnModel().getColumn(0).setMinWidth(0);
            tblInvoiceItems.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblInvoiceItems.getColumnModel().getColumn(0).setMaxWidth(0);
            tblInvoiceItems.getColumnModel().getColumn(1).setMinWidth(90);
            tblInvoiceItems.getColumnModel().getColumn(1).setPreferredWidth(90);
            tblInvoiceItems.getColumnModel().getColumn(2).setMinWidth(90);
            tblInvoiceItems.getColumnModel().getColumn(2).setPreferredWidth(90);
            tblInvoiceItems.getColumnModel().getColumn(7).setMinWidth(50);
            tblInvoiceItems.getColumnModel().getColumn(7).setPreferredWidth(50);
            tblInvoiceItems.getColumnModel().getColumn(11).setMinWidth(50);
            tblInvoiceItems.getColumnModel().getColumn(11).setPreferredWidth(50);
        }

        txtAddreturn.setText("Add to Quantity Returns");
        txtAddreturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddreturnActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Return Amount :");

        txtRetAmount.setEditable(false);
        txtRetAmount.setText("0.00");

        txtAddfinancial.setText("Add to Financial Return");
        txtAddfinancial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddfinancialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1006, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRetAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 379, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtAddfinancial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAddreturn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(92, 92, 92)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jLabel1)
                        .addComponent(txtRetAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtAddreturn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAddfinancial)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAddreturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddreturnActionPerformed
         
        try {
           addNewCustomerReturn();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
           ex.printStackTrace();
        }    
    }//GEN-LAST:event_txtAddreturnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tblInvoiceItemsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblInvoiceItemsKeyPressed

    }//GEN-LAST:event_tblInvoiceItemsKeyPressed

    private void tblInvoiceItemsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblInvoiceItemsKeyReleased
        int rowCount = tblInvoiceItems.getRowCount();
        // quantity return
        double totalReturnAmount = 0;
         //double finaAmount=0;
        for (int i = 0; i < rowCount; i++) {
            //getdata values in column...
            boolean isReturn = Boolean.parseBoolean(tblInvoiceItems.getValueAt(i, 6).toString());
            if (isReturn) {
                double retQty = Double.parseDouble(tblInvoiceItems.getValueAt(i, 7).toString());
                double invQty = Double.parseDouble(tblInvoiceItems.getValueAt(i, 3).toString());
                double invAmount = Double.parseDouble(tblInvoiceItems.getValueAt(i, 4).toString());
                totalReturnAmount += (invAmount / invQty) * retQty;
                System.out.println(totalReturnAmount);
            }
        }
        txtRetAmount.setText(String.format("%.2f", totalReturnAmount));
        //  financial return amount-discount
        double netAmout = 0;
        for (int i = 0; i < rowCount; i++) {
            //getdata values in column...
            Object isReturn1 = tblInvoiceItems.getValueAt(i, 10).toString();
             boolean isReturn = Boolean.parseBoolean((isReturn1 != null && !isReturn1.toString().isEmpty() ? isReturn1.toString() : "0"));
            
            if (isReturn) {
                //double invoAmount = Double.parseDouble(tblInvoiceItems.getValueAt(i, 4).toString());
                String invAmount = tblInvoiceItems.getValueAt(i, 4).toString();
                double invamountQty = Double.parseDouble((invAmount != null && !invAmount.isEmpty() ? invAmount : "0"));

                //double finaAmount = Double.parseDouble(tblInvoiceItems.getValueAt(i, 11).toString());
                String finaAmount = tblInvoiceItems.getValueAt(i, 11).toString();
                double finaAmountQty = Double.parseDouble((finaAmount != null && !finaAmount.isEmpty() ? finaAmount : "0"));

                //netAmout += invoAmount - finaAmount;
                netAmout += invamountQty - finaAmountQty;
                System.out.println(netAmout);
                txtRetAmount.setText(String.format("%.2f", finaAmountQty));
            }
        }
       // return finaAmount;
            
    }//GEN-LAST:event_tblInvoiceItemsKeyReleased

    private void txtAddfinancialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddfinancialActionPerformed

         try {
           addNewCustomerFinancialReturn();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
           ex.printStackTrace();
        }  
        
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddfinancialActionPerformed

    private void tblInvoiceItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInvoiceItemsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblInvoiceItemsMouseClicked

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
            java.util.logging.Logger.getLogger(AddNewReturn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddNewReturn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddNewReturn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddNewReturn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddNewReturn dialog = new AddNewReturn(new javax.swing.JFrame(), true, 0);
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
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblInvoiceItems;
    private javax.swing.JButton txtAddfinancial;
    private javax.swing.JButton txtAddreturn;
    private javax.swing.JTextField txtRetAmount;
    // End of variables declaration//GEN-END:variables
}
