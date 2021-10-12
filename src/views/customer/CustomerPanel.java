/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.customer;

import controllers.CustomerController;
import controllers.CustomerTypeController;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import models.Customer;
import models.CustomerType;

/**
 *
 * @author insaf
 */
public class CustomerPanel extends javax.swing.JPanel {

    private DefaultTableModel dtm, custTypeDtm;
    private Map<String, String> keepData;

    /**
     * Creates new form ViewPane
     */
    public CustomerPanel() {
        try {
            initComponents();
            checkCustomerButtons();
            keepData = new HashMap<String, String>();

            dtm = (DefaultTableModel) viewCustomer.getModel();
            custTypeDtm = (DefaultTableModel) tblAllCustomerTyps.getModel();
            CustomerController.loadAllCustomers(dtm);
            CustomerTypeController.loadAllCustomerTypes(custTypeDtm);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }

    public void checkSupplierButtons() {
        int selectedRows[] = tblAllCustomerTyps.getSelectedRows();

        if (selectedRows.length > 0) {
            btnDeleteCustomerType.setEnabled(true);
            btnUpdateCustomerType.setEnabled(true);
        } else {
            btnDeleteCustomerType.setEnabled(false);
            btnUpdateCustomerType.setEnabled(false);
        }
    }

    public void checkDimoSupplier() {

        int row = tblAllCustomerTyps.getSelectedRow();
        int supplierID = Integer.parseInt(tblAllCustomerTyps.getValueAt(row, 0).toString());

        if (supplierID == 1) {
            btnDeleteCustomerType.setEnabled(false);
            btnUpdateCustomerType.setEnabled(false);
        }

    }

    public void deleteCustomer() {

        int[] selectedRows = viewCustomer.getSelectedRows();

        try {
            while (selectedRows.length > 0) {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to delete this customer...?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                int value = 0;
                if (option == 0) {
                    Customer customer = new Customer(Integer.parseInt((String) viewCustomer.getValueAt(selectedRows[0], 0)));
                    value = CustomerController.deleteCustomer(customer);
                    dtm.removeRow(selectedRows[0]);
                    selectedRows = viewCustomer.getSelectedRows();

                    if (value > 0) {
                        JOptionPane.showMessageDialog(this, "Customer successfully deleted", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                        checkCustomerButtons();
                    }
                } else {
                    viewCustomer.clearSelection();
                    checkCustomerButtons();
                    break;

                }

            }
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }
    }

    public void deleteCustomerType() {
        int[] selectedRows = tblAllCustomerTyps.getSelectedRows();

        try {
            while (selectedRows.length > 0) {
                int option = JOptionPane.showConfirmDialog(this, "Do u want to delete this search key...?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                int value = 0;
                if (option == 0) {
                    CustomerType customerType = new CustomerType();
                    customerType.setCustomerTypeID(Integer.parseInt((String) tblAllCustomerTyps.getValueAt(selectedRows[0], 0)));

                    value = CustomerTypeController.deleteCustomerType(customerType);
                    custTypeDtm.removeRow(selectedRows[0]);
                    selectedRows = tblAllCustomerTyps.getSelectedRows();

                    if (value > 0) {
                        JOptionPane.showMessageDialog(this, "Search key successfully deleted", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                        checkDimoSupplier();

//                        checkItemButtons();
//                         SearchCategoryController.loadAllItemSearchKeys(searchKeyDtm);
                    }
                } else {
                    tblAllCustomerTyps.clearSelection();
                    //     checkBrandButtons();
                    checkSupplierButtons();
                    break;

                }

            }
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }

//        int[] selectedRows = tblAllCustomerTyps.getSelectedRows();
//
//        try {
//            while (selectedRows.length > 0) {
//                int option = JOptionPane.showConfirmDialog(this, "Do you want to delete this customer...?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//                int value = 0;
//                if (option == 0) {
//                    CustomerType customerType = new CustomerType();
//                    CustomerTypeController.deleteCustomerType(customerType);
//                  //  custTypeDtm.removeRow(selectedRows[0]);
//                    selectedRows = tblAllCustomerTyps.getSelectedRows();
//
//                    if (value > 0) {
//                        JOptionPane.showMessageDialog(this, "Customer type successfully deleted", "Succeed", JOptionPane.INFORMATION_MESSAGE);
//                        checkCustomerButtons();
//                    }
//                } else {
//                    tblAllCustomerTyps.clearSelection();
//                    checkCustomerButtons();
//                    break;
//
//                }
//
//            }
//        } catch (ClassNotFoundException classNotFoundException) {
//        } catch (SQLException sQLException) {
//        }
    }

    public void checkCustomerButtons() {
        int selectedRows[] = viewCustomer.getSelectedRows();

        if (selectedRows.length > 0) {
            btnDeleteCustomer.setEnabled(true);
            btnUpdateCustomer.setEnabled(true);
        } else {
            btnDeleteCustomer.setEnabled(false);
            btnUpdateCustomer.setEnabled(false);
        }
    }

    public DefaultTableModel getCustomerDtm() {
        return this.dtm;
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
        viewCustomer = new javax.swing.JTable();
        nameField = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        btnNewCustomer = new javax.swing.JButton();
        btnUpdateCustomer = new javax.swing.JButton();
        btnDeleteCustomer = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAllCustomerTyps = new javax.swing.JTable();
        nameField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        btnNewCustomerType = new javax.swing.JButton();
        btnUpdateCustomerType = new javax.swing.JButton();
        btnDeleteCustomerType = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 51, 255)));

        viewCustomer.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        viewCustomer.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        viewCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cust id", "Name", "Account No", "Contact no.", "Address", "Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        viewCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                viewCustomerMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(viewCustomer);
        if (viewCustomer.getColumnModel().getColumnCount() > 0) {
            viewCustomer.getColumnModel().getColumn(0).setMinWidth(0);
            viewCustomer.getColumnModel().getColumn(0).setPreferredWidth(0);
            viewCustomer.getColumnModel().getColumn(0).setMaxWidth(0);
            viewCustomer.getColumnModel().getColumn(1).setResizable(false);
            viewCustomer.getColumnModel().getColumn(3).setResizable(false);
            viewCustomer.getColumnModel().getColumn(4).setResizable(false);
        }

        nameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameFieldFocusLost(evt);
            }
        });
        nameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameFieldKeyReleased(evt);
            }
        });

        jButton2.setText("Search");

        btnNewCustomer.setText("New");
        btnNewCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewCustomerActionPerformed(evt);
            }
        });

        btnUpdateCustomer.setText("Update");
        btnUpdateCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateCustomerActionPerformed(evt);
            }
        });

        btnDeleteCustomer.setText("Delete");
        btnDeleteCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 458, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNewCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnUpdateCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDeleteCustomer, btnNewCustomer, btnUpdateCustomer});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewCustomer)
                    .addComponent(btnUpdateCustomer)
                    .addComponent(btnDeleteCustomer))
                .addGap(23, 23, 23))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDeleteCustomer, btnNewCustomer, btnUpdateCustomer});

        jTabbedPane1.addTab("Customers", jPanel1);

        tblAllCustomerTyps.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblAllCustomerTyps.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblAllCustomerTyps.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "typeID", "Type", "Type Code"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAllCustomerTyps.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblAllCustomerTypsMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblAllCustomerTyps);
        if (tblAllCustomerTyps.getColumnModel().getColumnCount() > 0) {
            tblAllCustomerTyps.getColumnModel().getColumn(0).setMinWidth(0);
            tblAllCustomerTyps.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblAllCustomerTyps.getColumnModel().getColumn(0).setMaxWidth(0);
            tblAllCustomerTyps.getColumnModel().getColumn(1).setResizable(false);
            tblAllCustomerTyps.getColumnModel().getColumn(2).setResizable(false);
        }

        nameField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameField1ActionPerformed(evt);
            }
        });
        nameField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameField1FocusLost(evt);
            }
        });
        nameField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameField1KeyReleased(evt);
            }
        });

        jButton3.setText("Search");

        btnNewCustomerType.setText("New");
        btnNewCustomerType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewCustomerTypeActionPerformed(evt);
            }
        });

        btnUpdateCustomerType.setText("Update");
        btnUpdateCustomerType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateCustomerTypeActionPerformed(evt);
            }
        });

        btnDeleteCustomerType.setText("Delete");
        btnDeleteCustomerType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCustomerTypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(nameField1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 462, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNewCustomerType, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdateCustomerType, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteCustomerType, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewCustomerType)
                    .addComponent(btnUpdateCustomerType)
                    .addComponent(btnDeleteCustomerType))
                .addGap(23, 23, 23))
        );

        jTabbedPane1.addTab("Customer Types", jPanel3);

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

    private void nameFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameFieldKeyReleased
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dtm);
        viewCustomer.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + nameField.getText()));
    }//GEN-LAST:event_nameFieldKeyReleased

    private void nameFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameFieldFocusLost
        nameField.setText("");
    }//GEN-LAST:event_nameFieldFocusLost

    private void btnUpdateCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateCustomerActionPerformed
        try {
            int row = viewCustomer.getSelectedRow();
            int customerID = Integer.parseInt(viewCustomer.getValueAt(row, 0).toString());
            String custName = viewCustomer.getValueAt(row, 1).toString();
            String accountNo = viewCustomer.getValueAt(row, 2).toString();
            String contactNo = viewCustomer.getValueAt(row, 3).toString();
            String address = viewCustomer.getValueAt(row, 4).toString();
            String type = viewCustomer.getValueAt(row, 5).toString().trim();

            Customer customer = new Customer(customerID);

            customer.setCustomerName(custName);
            customer.setAccountNo(accountNo);
            customer.setContactNo(contactNo);
            customer.setAddress(address);
            customer.setTypeName(type);
            UpdateCus updateCus = new UpdateCus(null, true);
            updateCus.setUpdateService(customer);
            updateCus.setVisible(true);

            CustomerController.loadAllCustomers(dtm);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }

    }//GEN-LAST:event_btnUpdateCustomerActionPerformed

    private void btnNewCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewCustomerActionPerformed
        try {
            NewCustomer newCustomer = new NewCustomer(null, true);
            newCustomer.setVisible(true);
            CustomerController.loadAllCustomers(dtm);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnNewCustomerActionPerformed

    private void btnDeleteCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCustomerActionPerformed
        deleteCustomer();
    }//GEN-LAST:event_btnDeleteCustomerActionPerformed

    private void nameField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameField1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_nameField1FocusLost

    private void nameField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameField1KeyReleased
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(custTypeDtm);
        tblAllCustomerTyps.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + nameField1.getText()));
    }//GEN-LAST:event_nameField1KeyReleased

    private void btnNewCustomerTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewCustomerTypeActionPerformed
        try {
            NewCustomerType ct = new NewCustomerType(null, true, custTypeDtm);
            ct.setVisible(true);
            CustomerController.loadAllCustomers(dtm);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnNewCustomerTypeActionPerformed

    private void btnUpdateCustomerTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateCustomerTypeActionPerformed
        int row = tblAllCustomerTyps.getSelectedRow();
        int supplierID = Integer.parseInt(tblAllCustomerTyps.getValueAt(row, 0).toString());
        String val0 = tblAllCustomerTyps.getValueAt(row, 0).toString();
        String val1 = tblAllCustomerTyps.getValueAt(row, 1).toString();
        String val2 = tblAllCustomerTyps.getValueAt(row, 2).toString();

        System.out.println(val0);
        System.out.println(val1);
        System.out.println(val2);

        CustomerType customerType = new CustomerType();
        customerType.setCustomerTypeID(Integer.parseInt(val0));
        customerType.setCustomerTypeName(val1);
        customerType.setCustomerTypeCode(val2);

//         System.out.println("code"+service.getServiceName());
        UpdateCustomerType updateCustomerType = new UpdateCustomerType(null, true);
        updateCustomerType.setUpdateCustomerType(customerType);
        updateCustomerType.setVisible(true);
        try {
            CustomerTypeController.loadAllCustomerTypes(custTypeDtm);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CustomerPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdateCustomerTypeActionPerformed

    private void btnDeleteCustomerTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCustomerTypeActionPerformed
        deleteCustomerType();
    }//GEN-LAST:event_btnDeleteCustomerTypeActionPerformed

    private void viewCustomerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewCustomerMousePressed
        checkCustomer_Buttons();
        checkDimoCustomer();
    }//GEN-LAST:event_viewCustomerMousePressed

    private void nameField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameField1ActionPerformed

    private void tblAllCustomerTypsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAllCustomerTypsMousePressed
        checkSupplierButtons();

        checkDimoSupplier();
    }//GEN-LAST:event_tblAllCustomerTypsMousePressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteCustomer;
    private javax.swing.JButton btnDeleteCustomerType;
    private javax.swing.JButton btnNewCustomer;
    private javax.swing.JButton btnNewCustomerType;
    private javax.swing.JButton btnUpdateCustomer;
    private javax.swing.JButton btnUpdateCustomerType;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField nameField1;
    private javax.swing.JTable tblAllCustomerTyps;
    private javax.swing.JTable viewCustomer;
    // End of variables declaration//GEN-END:variables

//    public void ViewAllCustomer() {
//        dtm.setRowCount(0);
//        try {
//            int i = 0;
//            ArrayList<Customer> allCustomer = CustomerController.getAllCustomer();
//            for (Customer c : allCustomer) {
//                keepData.put(String.valueOf(i), String.valueOf(c.getCid()));
//                Object[] rows = {c.getName(), c.getTel(), c.getAddress(), c.getType()};
//                dtm.addRow(rows);
//                String get = keepData.get(String.valueOf(i));
//                System.out.println(get);
//                i++;
//
//            }
//
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(CustomerPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void checkCustomer_Buttons() {
        int selectedRows[] = viewCustomer.getSelectedRows();

        if (selectedRows.length > 0) {
            btnDeleteCustomer.setEnabled(true);
            btnUpdateCustomer.setEnabled(true);
        } else {
            btnDeleteCustomer.setEnabled(false);
            btnUpdateCustomer.setEnabled(false);
        }
    }

    public void checkDimoCustomer() {
        int row = viewCustomer.getSelectedRow();
        int supplierID = Integer.parseInt(viewCustomer.getValueAt(row, 0).toString());

        if (supplierID == 1) {
            btnDeleteCustomer.setEnabled(false);
            btnUpdateCustomer.setEnabled(false);
        }
    }
}
