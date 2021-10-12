/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.stockviws;

import connections.DBHandler;
import controllers.ItemController;
import controllers.StockController;
import controllers.SupplierController;
import java.awt.Dialog;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Stock;
import models.StockDetail;
import utilities_new.ValidateValues;
import views.itemviews.NewItem;
import views.supplierviews.NewSupplier;

/**
 *
 * @author user
 */
public class UpdateStock extends javax.swing.JDialog {

    private DefaultTableModel stockItemsDtm;
    private DefaultTableModel stockDetailDtm;
    private int stockID;
    private HashMap<Object, Object> allDetail;
    private HashMap<Object, Object> removedDetails;

    /**
     * Creates new form NewStock
     */
    public UpdateStock(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public UpdateStock(DefaultTableModel stockDtm, int selectedStockID) {
        initComponents();
        setLocationRelativeTo(null);
        checkButtons();
        stockItemsDtm = (DefaultTableModel) tblStockItems.getModel();
        this.stockDetailDtm = stockDtm;
        this.stockID = selectedStockID;
        allDetail = new HashMap<>();
        removedDetails = new HashMap<>();
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        try {
            fillSupplierNames();
            fillItemNames();
            FillStockDataToUpdate(selectedStockID);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }

        cmbUpdateAllItems.setSelectedIndex(-1);
        //cmbUpdateAllSuppliers.setSelectedIndex(-1);
    }

    public void fillSupplierNames() throws ClassNotFoundException, SQLException {

        String query = "select distinct suppliername from supplier where status = '1'";
        ResultSet rstItem = DBHandler.getData(query);
        cmbUpdateAllSuppliers.removeAllItems();
        while (rstItem.next()) {

            cmbUpdateAllSuppliers.addItem(rstItem.getString(1));

        }
    }

    public void fillItemNames() throws ClassNotFoundException, SQLException {

        String query = "select distinct item_part_no from item where status = '1'";
        ResultSet rstItem = DBHandler.getData(query);
        cmbUpdateAllItems.removeAllItems();
        while (rstItem.next()) {

            cmbUpdateAllItems.addItem(rstItem.getString(1));

        }
    }

    public void addNewItemsToTable() throws ClassNotFoundException, SQLException {
        String supplierName = (String) cmbUpdateAllSuppliers.getSelectedItem();
        String itemName = ((String) cmbUpdateAllItems.getSelectedItem());
        int itemID = ItemController.getItemIDforItemName(itemName);
        String itemQty = txtItemQty.getText().toString();
        Object obj[] = new Object[2];

        if (supplierName == "null" || supplierName == null) {
        } else if (itemName == "null" || itemName == null) {
        } else if (itemQty == "null" || itemQty == null) {
        } else {

            obj[0] = itemName;
            obj[1] = itemQty;
            stockItemsDtm.addRow(obj);

        }

        clearFields();
        checkButtons();

    }

    public void clearFields() {
        cmbUpdateAllItems.setSelectedIndex(-1);
        txtItemQty.setText("");
    }

    public void checkButtons() {
        int tableRowCount = tblStockItems.getRowCount();
        if (tableRowCount > 0) {
            btnAddStock.setEnabled(true);
            btnRemoveFromTable.setEnabled(true);
            btnRemoveAllInTable.setEnabled(true);
        } else {
            btnAddStock.setEnabled(false);
            btnRemoveFromTable.setEnabled(false);
            btnRemoveAllInTable.setEnabled(false);
        }
    }

    public void removeTableItems() throws ClassNotFoundException, SQLException {
        DefaultTableModel dtm = (DefaultTableModel) tblStockItems.getModel();
        int selectedRow[] = tblStockItems.getSelectedRows();
        while (selectedRow.length > 0) {
            int option = JOptionPane.showConfirmDialog(this, "Do u want to remove  this item...?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == 0) {
                Object itemID = ItemController.getItemIDforItemName(tblStockItems.getValueAt(selectedRow[0], 0).toString());
                removedDetails.put(itemID, itemID);
                allDetail.remove(itemID);
                dtm.removeRow(selectedRow[0]);
                selectedRow = tblStockItems.getSelectedRows();
            } else {
                tblStockItems.clearSelection();
                break;

            }
            checkButtons();

        }
    }

    public void removeAllTableItems(JTable table) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int allRows = table.getRowCount();
        int option = JOptionPane.showConfirmDialog(this, "Do u want to remove  these all item...?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == 0) {
            while (allRows > 0) {
                dtm.removeRow(0);
                allRows = table.getRowCount();

            }
            checkButtons();
        }

    }

//    public void updateStockDetail() throws ClassNotFoundException, SQLException, NumberFormatException {
//        Object ob = cmbUpdateAllSuppliers.getSelectedItem();
//        int supplierID = SupplierController.getSupplierIDforName(ob.toString().trim());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String addedDate = sdf.format(new Date());
//        int status = 1;
//        Stock stock = new Stock(stockID, supplierID);
//        int value = StockController.updateStock(stock);
//        ArrayList removed = new ArrayList(removedDetails.values());
//        if (removed.size() > 0) {
//            for (int i = 0; i < removed.size(); i++) {
//                StockDetail stockDetail = new StockDetail(Integer.parseInt(removed.get(i).toString()), stockID);
//                StockController.deleteStockDetail(stockDetail);
//            }
//        }
//
//        //ArrayList remainedList = new ArrayList(allDetail.values());
//        for (int i = 0; i < tblStockItems.getRowCount(); i++) {
//            Object objectValue = tblStockItems.getValueAt(i, 0);
//            int itemID = ItemController.getItemIDforItemName(objectValue.toString());
//            int stockQty = Integer.parseInt((tblStockItems.getValueAt(i, 1)).toString());
//            if (allDetail.containsKey(itemID)) {
//                StockDetail stockDetail = new StockDetail(stockID, itemID, stockQty, stockQty, 1);
//                int val = StockController.updateStockDetail(stockDetail);
//            } else {
//                StockDetail stockDetail = new StockDetail(stockID, itemID, stockQty, stockQty, 1);
//                int val = StockController.addNewStockDetail(stockDetail);
//            }
//        }
//        allDetail.clear();
//        removedDetails.clear();
//        stockItemsDtm.setRowCount(0);
//    }

    public void FillStockDataToUpdate(int stockID) throws ClassNotFoundException, SQLException {
        String query = "select * from stock where stockID = '" + stockID + "'";
        ResultSet rst = DBHandler.getData(query);

        while (rst.next()) {
            cmbUpdateAllSuppliers.setSelectedItem(SupplierController.getSupplierNameforID(rst.getInt(2)));
            StockController.loadAllStockDetails(stockItemsDtm, stockID);
        }
        int itemRowCount = tblStockItems.getRowCount();
        if (itemRowCount > 0) {
            for (int i = 0; i < itemRowCount; i++) {
                int itemID = ItemController.getItemIDforItemName(tblStockItems.getValueAt(i, 0).toString());
                allDetail.put(itemID, itemID);
            }

        }
    }

    public void deleteStock() throws SQLException, ClassNotFoundException {
        Stock stock = new Stock(stockID);
        int option = JOptionPane.showConfirmDialog(this, "Are you sure to delete this stock..?", "Delete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == 0) {
            int value = StockController.deleteStock(stock);
            this.dispose();
            StockController.loadAllStockDetail(stockDetailDtm);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbUpdateAllSuppliers = new javax.swing.JComboBox();
        btnAddNewSupplier = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cmbUpdateAllItems = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStockItems = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        btnAddToTable = new javax.swing.JButton();
        btnRemoveFromTable = new javax.swing.JButton();
        btnRemoveAllInTable = new javax.swing.JButton();
        btnAddStock = new javax.swing.JButton();
        btnCancelUpdate = new javax.swing.JButton();
        btnAddNewItem = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtItemQty = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Stock");

        jLabel1.setText("Supplier :");

        btnAddNewSupplier.setText("+");
        btnAddNewSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewSupplierActionPerformed(evt);
            }
        });

        jLabel2.setText("Part No. :");

        cmbUpdateAllItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUpdateAllItemsActionPerformed(evt);
            }
        });

        tblStockItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Part No.", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStockItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStockItemsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblStockItems);

        btnAddToTable.setText("Add");
        btnAddToTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToTableActionPerformed(evt);
            }
        });

        btnRemoveFromTable.setText("Remove");
        btnRemoveFromTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveFromTableActionPerformed(evt);
            }
        });

        btnRemoveAllInTable.setText("Remove All");
        btnRemoveAllInTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAllInTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddToTable, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnRemoveFromTable, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRemoveAllInTable)
                .addGap(8, 8, 8))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddToTable, btnRemoveAllInTable, btnRemoveFromTable});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddToTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemoveFromTable, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(btnRemoveAllInTable, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddToTable, btnRemoveAllInTable, btnRemoveFromTable});

        btnAddStock.setText("Update Stock");
        btnAddStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStockActionPerformed(evt);
            }
        });

        btnCancelUpdate.setText("Cancel");
        btnCancelUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelUpdateActionPerformed(evt);
            }
        });

        btnAddNewItem.setText("+");
        btnAddNewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewItemActionPerformed(evt);
            }
        });

        jLabel3.setText("Quantity :");

        txtItemQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtItemQtyKeyReleased(evt);
            }
        });

        jButton1.setText("Delete Stock");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbUpdateAllSuppliers, 0, 223, Short.MAX_VALUE)
                                    .addComponent(cmbUpdateAllItems, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAddNewSupplier)
                                    .addComponent(btnAddNewItem)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtItemQty, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 206, Short.MAX_VALUE)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddStock)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddStock, btnCancelUpdate, jButton1});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbUpdateAllSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbUpdateAllItems, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAddNewSupplier)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddNewItem)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtItemQty, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddStock)
                    .addComponent(btnCancelUpdate)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddStock, btnCancelUpdate, jButton1});

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

    private void btnAddToTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToTableActionPerformed
        try {
            addNewItemsToTable();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnAddToTableActionPerformed

    private void btnRemoveFromTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveFromTableActionPerformed
        try {
            removeTableItems();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
        checkButtons();
    }//GEN-LAST:event_btnRemoveFromTableActionPerformed

    private void btnRemoveAllInTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAllInTableActionPerformed
        removeAllTableItems(tblStockItems);
        checkButtons();

    }//GEN-LAST:event_btnRemoveAllInTableActionPerformed

    private void cmbUpdateAllItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUpdateAllItemsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbUpdateAllItemsActionPerformed

    private void btnAddNewSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewSupplierActionPerformed
        new NewSupplier(null, true).setVisible(true);
    }//GEN-LAST:event_btnAddNewSupplierActionPerformed

    private void btnAddNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewItemActionPerformed
        new NewItem(null, true).setVisible(true);
    }//GEN-LAST:event_btnAddNewItemActionPerformed

    private void btnCancelUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelUpdateActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelUpdateActionPerformed

    private void tblStockItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStockItemsMouseClicked
        checkButtons();
    }//GEN-LAST:event_tblStockItemsMouseClicked

    private void btnAddStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStockActionPerformed
//        try {
//            updateStockDetail();
//        } catch (ClassNotFoundException ex) {
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
    }//GEN-LAST:event_btnAddStockActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            deleteStock();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtItemQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemQtyKeyReleased
        ValidateValues.validateTextFieldForNumber(txtItemQty);
    }//GEN-LAST:event_txtItemQtyKeyReleased

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
            java.util.logging.Logger.getLogger(UpdateStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                UpdateStock dialog = new UpdateStock(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAddNewItem;
    private javax.swing.JButton btnAddNewSupplier;
    private javax.swing.JButton btnAddStock;
    private javax.swing.JButton btnAddToTable;
    private javax.swing.JButton btnCancelUpdate;
    private javax.swing.JButton btnRemoveAllInTable;
    private javax.swing.JButton btnRemoveFromTable;
    private javax.swing.JComboBox cmbUpdateAllItems;
    private javax.swing.JComboBox cmbUpdateAllSuppliers;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblStockItems;
    private javax.swing.JTextField txtItemQty;
    // End of variables declaration//GEN-END:variables
}
