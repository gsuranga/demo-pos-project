/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.brandviews;

import connections.DBHandler;
import controllers.BrandController;
import java.awt.Dialog;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Brand;

/**
 *
 * @author user
 */
public class UpdateBrand extends javax.swing.JDialog {

    /**
     * Creates new form NewBrand
     */
    private int brandID;
    private DefaultTableModel itemBrandDtm;

    public UpdateBrand(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

    }

    public UpdateBrand(DefaultTableModel itemBrandDtm, int selectedBrand) {

        initComponents();
        setLocationRelativeTo(null);
        this.brandID = selectedBrand;
        this.itemBrandDtm = itemBrandDtm;
        try {
            fillBrandNames();

            fillFieldsToUpdate(selectedBrand);

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        cmbBrandName.requestFocus();
    }

    public void updateBrand() {
        try {

            String brandCode = txtUpdateBrandCode.getText();
          String brandName = (String) cmbBrandName.getSelectedItem();
          
            String desc = txtUpdateDesc.getText();
            int status = 1;

            Brand brandModel = new Brand(brandCode, brandName, desc, status, brandID);
            int value = BrandController.updateBrand(brandModel);
            if (value > 0) {
                JOptionPane.showMessageDialog(this, "Brand successfully updated", "Successful", JOptionPane.INFORMATION_MESSAGE);
                refreshFields();
                BrandController.loadAllItemBrands(itemBrandDtm);
                fillBrandNames();

            }

        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Check your updated values again..", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            JOptionPane.showMessageDialog(this, "Check your updated values again..", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshFields() {
        txtUpdateBrandCode.setText("");
        cmbBrandName.setSelectedItem("");
        txtUpdateDesc.setText("");
    }

    public void fillFieldsToUpdate(int selectedItem) {
        try {

            //int itemID = BrandController.getBrandIDforBrandName(selectedItem);
            String sql = "select * from itemBrand where brandID = '" + selectedItem + "'";
            ResultSet rst = DBHandler.getData(sql);
            while (rst.next()) {
                cmbBrandName.setSelectedItem(rst.getString(2));
                txtUpdateBrandCode.setText(rst.getString(4));
                txtUpdateDesc.setText(rst.getString(3));
                cmbBrandName.requestFocus();

            }

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }

    }

    public void fillBrandNames() throws ClassNotFoundException, SQLException {

        String query = "select BrandName from ItemBrand where status = '1'";
        ResultSet rstItemBrand = DBHandler.getData(query);
        cmbBrandName.removeAllItems();
        while (rstItemBrand.next()) {

            cmbBrandName.addItem(rstItemBrand.getString(1));

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
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbBrandName = new javax.swing.JComboBox();
        txtUpdateBrandCode = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtUpdateDesc = new javax.swing.JTextArea();
        btnUpdateBrand = new javax.swing.JButton();
        btnCancelUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Upate Brand");
        setResizable(false);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Brand Name:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Brand Code:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Description:");

        cmbBrandName.setEditable(true);
        cmbBrandName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbBrandName.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbBrandNamePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbBrandName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBrandNameActionPerformed(evt);
            }
        });
        cmbBrandName.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbBrandNamePropertyChange(evt);
            }
        });

        txtUpdateBrandCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUpdateBrandCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUpdateBrandCodeActionPerformed(evt);
            }
        });

        txtUpdateDesc.setColumns(20);
        txtUpdateDesc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUpdateDesc.setRows(5);
        txtUpdateDesc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUpdateDescKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(txtUpdateDesc);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(cmbBrandName, 0, 180, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(txtUpdateBrandCode, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbBrandName, txtUpdateBrandCode});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbBrandName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUpdateBrandCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbBrandName, txtUpdateBrandCode});

        btnUpdateBrand.setText("Update");
        btnUpdateBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateBrandActionPerformed(evt);
            }
        });

        btnCancelUpdate.setText("Cancel");
        btnCancelUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnUpdateBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelUpdate)
                    .addComponent(btnUpdateBrand))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateBrandActionPerformed
        updateBrand();
        this.dispose();
    }//GEN-LAST:event_btnUpdateBrandActionPerformed

    private void btnCancelUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelUpdateActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelUpdateActionPerformed

    private void cmbBrandNamePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbBrandNamePropertyChange
        //brandID = Integer.parseInt((String) cmbBrandName.getSelectedItem());
    }//GEN-LAST:event_cmbBrandNamePropertyChange

    private void cmbBrandNamePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbBrandNamePopupMenuWillBecomeInvisible
        try {
            brandID = BrandController.getBrandIDforBrandName((String) cmbBrandName.getSelectedItem());
            fillFieldsToUpdate(brandID);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_cmbBrandNamePopupMenuWillBecomeInvisible

    private void cmbBrandNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBrandNameActionPerformed
      txtUpdateBrandCode.requestFocus();  // TODO add your handling code here:
    }//GEN-LAST:event_cmbBrandNameActionPerformed

    private void txtUpdateBrandCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUpdateBrandCodeActionPerformed
txtUpdateDesc.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtUpdateBrandCodeActionPerformed

    private void txtUpdateDescKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUpdateDescKeyReleased
btnUpdateBrand.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtUpdateDescKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */


        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                UpdateBrand dialog = new UpdateBrand(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelUpdate;
    private javax.swing.JButton btnUpdateBrand;
    private javax.swing.JComboBox cmbBrandName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtUpdateBrandCode;
    private javax.swing.JTextArea txtUpdateDesc;
    // End of variables declaration//GEN-END:variables
}