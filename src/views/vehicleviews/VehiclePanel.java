/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.vehicleviews;

import controllers.VehicleController;
import controllers.VehicleModelController;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import models.Vehicle;
import models.VehicleModel;

/**
 *
 * @author shdinesh.99
 */
public class VehiclePanel extends javax.swing.JPanel {

    private DefaultTableModel vModelDtm;
    private DefaultTableModel allVehicle;
    private TableRowSorter<TableModel> supplierSorter;

    /**
     * Creates new form VehiclePanel
     */
    public VehiclePanel() {
        initComponents();
        vModelDtm = (DefaultTableModel) tblVehicleModels.getModel();
        allVehicle = (DefaultTableModel) tblAllVehicle.getModel();
        checkVehicalButtons();
        try {
            VehicleModelController.loadAllVehicleModels(vModelDtm);
            VehicleController.viewAllVehicle(allVehicle);
            checkVehicleModelButtons();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }

    }

    public void checkSupplierButtons() {
        int selectedRows[] = tblAllVehicle.getSelectedRows();

        if (selectedRows.length > 0) {
            btnDelete.setEnabled(true);
            btnUpdate.setEnabled(true);
        } else {
            btnDelete.setEnabled(false);
            btnUpdate.setEnabled(false);
        }
    }

    public void checkDimoSupplier() {

        int row = tblAllVehicle.getSelectedRow();
        System.out.println("sd" + row);
        int supplierID = Integer.parseInt(tblAllVehicle.getValueAt(row, 0).toString());

        if (supplierID == 1) {
            btnDelete.setEnabled(false);
            btnUpdate.setEnabled(false);
        }

    }

    public void deleteVehicle() {

        int[] selectedRows1 = tblAllVehicle.getSelectedRows();
        //int supplierID = Integer.parseInt(tblAllVehicle.getValueAt(row, 0).toString());

        int selectedRows = tblAllVehicle.getSelectedRow();

        int supplierID = Integer.parseInt(tblAllVehicle.getValueAt(selectedRows, 0).toString());
        try {
            if (selectedRows > 0) {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to delete this Vehicle?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                int value = 0;
                if (option == 0) {
                    Vehicle vehicle = new Vehicle();
                    vehicle.setVehicleID(supplierID);
                    System.out.println("delete " + supplierID);
                    //  value = ServiceController.deleteService(service);

                    value = VehicleController.deleteVehicle(vehicle);

                    if (value > 0) {
                        JOptionPane.showMessageDialog(this, "This Vehicle has been successfuly deleted", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                        checkSupplierButtons();

                    }
                } else {
                    tblAllVehicle.clearSelection();
                    checkSupplierButtons();

                }

            }
            VehicleController.viewAllVehicle(allVehicle);
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }
    }

    public void deleteVehicleModel() {

        int[] selectedRows = tblVehicleModels.getSelectedRows();

        try {
            while (selectedRows.length > 0) {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to delete this vehicle model...?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                int value = 0;
                if (option == 0) {
                    VehicleModel vModel = new VehicleModel();
                    vModel.setModelID(Integer.parseInt((String) tblVehicleModels.getValueAt(selectedRows[0], 0)));
                    value = VehicleModelController.deleteVehicleModel(vModel);
                    vModelDtm.removeRow(selectedRows[0]);
                    selectedRows = tblVehicleModels.getSelectedRows();

                    if (value > 0) {
                        JOptionPane.showMessageDialog(this, "vehicle model successfuly removed", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                        checkVehicleModelButtons();
                        VehicleModelController.loadAllVehicleModels(vModelDtm);
                    }
                } else {
                    tblVehicleModels.clearSelection();
                    checkVehicleModelButtons();
                    break;

                }

            }
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }
    }

    public void checkVehicleModelButtons() {
        int selectedRows[] = tblVehicleModels.getSelectedRows();

        if (selectedRows.length > 0) {
            btnDeleteModel.setEnabled(true);
            btnUpdateVehicleModel.setEnabled(true);
        } else {
            btnDeleteModel.setEnabled(false);
            btnUpdateVehicleModel.setEnabled(false);
        }
    }

    public void checkVehicalButtons() {
        int selectedRows[] = tblAllVehicle.getSelectedRows();

        if (selectedRows.length > 0) {
            btnUpdate.setEnabled(true);
            btnDelete.setEnabled(true);
        } else {
            btnUpdate.setEnabled(false);
            btnDelete.setEnabled(false);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAllVehicle = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txt_serch_vehicle = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtSearchBrand1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblVehicleModels = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        btnNewModel = new javax.swing.JButton();
        btnDeleteModel = new javax.swing.JButton();
        btnUpdateVehicleModel = new javax.swing.JButton();

        tblAllVehicle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "vehical_id", "vmodel_id", "customer_id", "Vehicle Reg No.", "Vehicle Model", "Customer", "Registered Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAllVehicle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblAllVehicleMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblAllVehicle);
        if (tblAllVehicle.getColumnModel().getColumnCount() > 0) {
            tblAllVehicle.getColumnModel().getColumn(0).setMinWidth(0);
            tblAllVehicle.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblAllVehicle.getColumnModel().getColumn(0).setMaxWidth(0);
            tblAllVehicle.getColumnModel().getColumn(1).setMinWidth(0);
            tblAllVehicle.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblAllVehicle.getColumnModel().getColumn(1).setMaxWidth(0);
            tblAllVehicle.getColumnModel().getColumn(2).setMinWidth(0);
            tblAllVehicle.getColumnModel().getColumn(2).setPreferredWidth(0);
            tblAllVehicle.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Search :");

        txt_serch_vehicle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_serch_vehicleKeyReleased(evt);
            }
        });

        jButton1.setText("New");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 920, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30)
                        .addComponent(txt_serch_vehicle, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDelete, btnUpdate, jButton1});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_serch_vehicle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete))
                .addGap(44, 44, 44))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDelete, btnUpdate, jButton1});

        jTabbedPane1.addTab("All Vehicles", jPanel1);

        txtSearchBrand1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchBrand1KeyReleased(evt);
            }
        });

        jButton2.setText("Search");

        tblVehicleModels.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblVehicleModels.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblVehicleModels.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Model ID", "Model", "Description", "Date Registered"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVehicleModels.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVehicleModelsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblVehicleModelsMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblVehicleModels);
        if (tblVehicleModels.getColumnModel().getColumnCount() > 0) {
            tblVehicleModels.getColumnModel().getColumn(0).setMinWidth(0);
            tblVehicleModels.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblVehicleModels.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        btnNewModel.setText("New");
        btnNewModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewModelActionPerformed(evt);
            }
        });

        btnDeleteModel.setText("Delete");
        btnDeleteModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteModelActionPerformed(evt);
            }
        });

        btnUpdateVehicleModel.setText("Update");
        btnUpdateVehicleModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateVehicleModelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(btnNewModel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteModel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdateVehicleModel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDeleteModel, btnNewModel, btnUpdateVehicleModel});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnUpdateVehicleModel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDeleteModel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnNewModel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDeleteModel, btnNewModel, btnUpdateVehicleModel});

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtSearchBrand1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(0, 468, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchBrand1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Vehicle Models", jPanel2);

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

    private void btnNewModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewModelActionPerformed
        NewVehicleModel nvm = new NewVehicleModel(null, true);
        nvm.setVisible(true);
        try {
            VehicleModelController.loadAllVehicleModels(vModelDtm);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VehiclePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VehiclePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnNewModelActionPerformed

    private void btnDeleteModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteModelActionPerformed
        deleteVehicleModel();

    }//GEN-LAST:event_btnDeleteModelActionPerformed

    private void btnUpdateVehicleModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateVehicleModelActionPerformed
        try {
            int selected = tblVehicleModels.getSelectedRow();
            UpdateVehicleModel updateVmodel = new UpdateVehicleModel(null, true, Integer.parseInt(tblVehicleModels.getValueAt(selected, 0).toString()));
            updateVmodel.setVisible(true);
            VehicleModelController.loadAllVehicleModels(vModelDtm);
            tblVehicleModels.clearSelection();
            checkVehicleModelButtons();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnUpdateVehicleModelActionPerformed

    private void tblVehicleModelsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVehicleModelsMouseClicked
        checkVehicleModelButtons();
    }//GEN-LAST:event_tblVehicleModelsMouseClicked

    private void tblVehicleModelsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVehicleModelsMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblVehicleModelsMousePressed

    private void txtSearchBrand1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchBrand1KeyReleased
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(vModelDtm);
        tblVehicleModels.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtSearchBrand1.getText()));
    }//GEN-LAST:event_txtSearchBrand1KeyReleased

    private void txt_serch_vehicleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_serch_vehicleKeyReleased
        tblAllVehicle.setRowSorter(supplierSorter);
        supplierSorter = new TableRowSorter<TableModel>(allVehicle);
        supplierSorter.setRowFilter(RowFilter.regexFilter("(?i)" + txt_serch_vehicle.getText()));
    }//GEN-LAST:event_txt_serch_vehicleKeyReleased

    private void tblAllVehicleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAllVehicleMousePressed
        checkSupplierButtons();
        checkDimoSupplier();
    }//GEN-LAST:event_tblAllVehicleMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        NewVehicle newVehicle = new NewVehicle(null, true);
        newVehicle.setVisible(true);
        try {
            VehicleController.viewAllVehicle(allVehicle);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VehiclePanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VehiclePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int row = tblAllVehicle.getSelectedRow();

        int vehicleiD = Integer.parseInt(tblAllVehicle.getValueAt(row, 0).toString());
        String vModel = tblAllVehicle.getValueAt(row, 4).toString();
        String regNo = tblAllVehicle.getValueAt(row, 3).toString();
        String custName = tblAllVehicle.getValueAt(row, 5).toString().trim();

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleID(vehicleiD);
        vehicle.setModelName(vModel);
        vehicle.setVehicleRegNo(regNo);
        vehicle.setCustName(custName);

        UpdateVehicle updateVehicle = new UpdateVehicle(null, true);
        updateVehicle.setUpdateVehicle(vehicle);
        updateVehicle.setVisible(true);
        try {
            VehicleController.viewAllVehicle(allVehicle);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }


    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteVehicle();
    }//GEN-LAST:event_btnDeleteActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteModel;
    private javax.swing.JButton btnNewModel;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnUpdateVehicleModel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblAllVehicle;
    private javax.swing.JTable tblVehicleModels;
    private javax.swing.JTextField txtSearchBrand1;
    private javax.swing.JTextField txt_serch_vehicle;
    // End of variables declaration//GEN-END:variables
}
