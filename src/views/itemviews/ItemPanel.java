/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views.itemviews;

import controllers.BrandController;
import controllers.ItemController;
import controllers.SearchCategoryController;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import models.Brand;
import models.Item;
import models.SearchCategory;
import views.brandviews.NewBrand;
import views.brandviews.UpdateBrand;
import views.searchKey.NewSearchKey;

/**
 *
 * @author SHdinesh Madushanka
 */
public class ItemPanel extends javax.swing.JPanel {

    private DefaultTableModel allItemDtm, allItemBrandsDtm, searchKeyDtm;
    private TableRowSorter<TableModel> itemSorter;
    private TableRowSorter<TableModel> brandSorter;

    /**
     * Creates new form ItemPanel
     */
    public ItemPanel() {
        initComponents();
        allItemDtm = (DefaultTableModel) tblAllItems.getModel();
        allItemBrandsDtm = (DefaultTableModel) tblAllItemBrands.getModel();
        searchKeyDtm = (DefaultTableModel) tblSearchKeys.getModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.LEFT);
        tblAllItems.setDefaultRenderer(String.class, centerRenderer);
        checkItemButtons();
        checkBrandButtons();
        try {
            ItemController.loadAllItems(allItemDtm);
            BrandController.loadAllItemBrands(allItemBrandsDtm);
            SearchCategoryController.loadAllItemSearchKeys(searchKeyDtm);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

    }

    public void checkSupplierButtons() {
        int selectedRows[] = tblSearchKeys.getSelectedRows();

        if (selectedRows.length > 0) {
            btnDeleteKey.setEnabled(true);
            btnUpdateKey.setEnabled(true);
        } else {
            btnDeleteKey.setEnabled(false);
            btnUpdateKey.setEnabled(false);
        }
    }

    public void checkDimoSupplier() {

        int row = tblSearchKeys.getSelectedRow();
        int supplierID = Integer.parseInt(tblSearchKeys.getValueAt(row, 0).toString());

        if (supplierID == 1) {
            btnDeleteKey.setEnabled(false);
            btnUpdateKey.setEnabled(false);
        }

    }

    public void checkItemButtons() {
        int selectedRows[] = tblAllItems.getSelectedRows();

        if (selectedRows.length > 0) {
            btnDeleteItem.setEnabled(true);
            btnUpdateItem.setEnabled(true);
        } else {
            btnDeleteItem.setEnabled(false);
            btnUpdateItem.setEnabled(false);
        }
    }

    public void checkBrandButtons() {
        int selectedRows[] = tblAllItemBrands.getSelectedRows();
        System.out.println("row"+selectedRows.length);
        if (selectedRows.length > 0) {
            btnDeleteBrand.setEnabled(true);
            btnUpdateBrand.setEnabled(true);
        } else {
            btnDeleteBrand.setEnabled(false);
            btnUpdateBrand.setEnabled(false);
        }
    }

    public void checkFirstSelected() {
        int row = tblAllItemBrands.getSelectedRow();
        int brandID = Integer.parseInt(tblAllItemBrands.getValueAt(row, 0).toString());
        if (brandID == 1) {
            btnDeleteBrand.setEnabled(false);
            btnUpdateBrand.setEnabled(false);
        }
    }

    public void deleteItems(JTable table, String tableTitle) {

        int[] selectedRows = table.getSelectedRows();
        int selectedItem = Integer.parseInt((String) table.getValueAt(selectedRows[0], 0));
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        try {
            while (selectedRows.length > 0) {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to delete " + tableTitle + "?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                int value = 0;
                if (option == 0) {
                    Item item = new Item(selectedItem);
                    value = ItemController.deleteItem(item);
                    tableModel.removeRow(selectedRows[0]);
                    selectedRows = table.getSelectedRows();

                    if (value > 0) {
                        JOptionPane.showMessageDialog(this, "This " + tableTitle + " has been successfully deleted", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                        
                        checkItemButtons();
                         ItemController.loadAllItems(allItemDtm);
                    }
                } else {
                    table.clearSelection();
                    checkItemButtons();
                    break;

                }

            }
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }
    }

    public void deleteBrands() {

        int[] selectedRows = tblAllItemBrands.getSelectedRows();

        try {
            while (selectedRows.length > 0) {
                int option = JOptionPane.showConfirmDialog(this, "Do you want to delete this brand?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                int value = 0;
                if (option == 0) {
                    Brand brand = new Brand(Integer.parseInt((String) tblAllItemBrands.getValueAt(selectedRows[0], 0)));
                    value = BrandController.deleteItemBrands(brand);
                    allItemBrandsDtm.removeRow(selectedRows[0]);
                    selectedRows = tblAllItemBrands.getSelectedRows();

                    if (value > 0) {
                        JOptionPane.showMessageDialog(this, "This brand has successfully deleted", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                        checkItemButtons();
                    }
                } else {
                    tblAllItemBrands.clearSelection();
                    checkBrandButtons();
                    break;

                }

            }
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }
    }

    public void deleteSearchKeys() {

        int[] selectedRows = tblSearchKeys.getSelectedRows();

        try {
            while (selectedRows.length > 0) {
                int option = JOptionPane.showConfirmDialog(this, "Do u want to delete this search key...?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                int value = 0;
                if (option == 0) {
                    SearchCategory sk = new SearchCategory();
                    sk.setCategoryID(Integer.parseInt((String) tblSearchKeys.getValueAt(selectedRows[0], 0)));
                    value = SearchCategoryController.deleteSearchKey(sk);
                    searchKeyDtm.removeRow(selectedRows[0]);
                    selectedRows = tblSearchKeys.getSelectedRows();

                    if (value > 0) {
                        JOptionPane.showMessageDialog(this, "Search key successfully deleted", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                        
                        checkItemButtons();
                         SearchCategoryController.loadAllItemSearchKeys(searchKeyDtm);
                    }
                } else {
                    tblSearchKeys.clearSelection();
                    checkBrandButtons();
                    break;

                }

            }
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }
    }

    public void updateBrand() {
        int rowCount = tblAllItemBrands.getSelectedRowCount();
        if (rowCount == 1) {
            int selectedRow = tblAllItemBrands.getSelectedRow();
            int selectedBrandID = Integer.parseInt((String) tblAllItemBrands.getValueAt(selectedRow, 0));

            UpdateBrand updateBrand = new UpdateBrand(allItemBrandsDtm, selectedBrandID);

            updateBrand.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Plese select single brand to update", "Wrong selection", JOptionPane.WARNING_MESSAGE);
            tblAllItemBrands.clearSelection();
        }
    }

    public void updateItem() {
        try {
            int rowCount = tblAllItems.getSelectedRowCount();
            if (rowCount == 1) {
                int selectedRow = tblAllItems.getSelectedRow();
                int selectedItemID = Integer.parseInt((String) tblAllItems.getValueAt(selectedRow, 0));

                UpdateItem updateItem = new UpdateItem(null, true, selectedItemID);
                updateItem.setVisible(true);
                ItemController.loadAllItems(allItemDtm);
            } else {
                JOptionPane.showMessageDialog(this, "Plese select single brand to update", "Wrong selection", JOptionPane.WARNING_MESSAGE);
                tblAllItems.clearSelection();
            }

        } catch (NumberFormatException numberFormatException) {
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        } catch (NullPointerException nullPointerException) {
        } catch (HeadlessException headlessException) {
        }

    }

    public void previwImage(int itemID) {

        try {
            String image = ItemController.getImageForID(itemID);
            BufferedImage bi = ImageIO.read(new File(image));
            Image preview = bi.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);
            lblImage.removeAll();
            lblImage.setIcon(new ImageIcon(preview));

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException iox) {
            lblImage.removeAll();
            lblImage.setIcon(null);
            lblImage.setText("No Image Available");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnDeleteItem = new javax.swing.JButton();
        btnNewItem = new javax.swing.JButton();
        btnUpdateItem = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAllItems = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        txtSeachItem = new javax.swing.JTextField();
        btnSeachCustomer = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblAllItemBrands = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnNewItem1 = new javax.swing.JButton();
        btnUpdateBrand = new javax.swing.JButton();
        btnDeleteBrand = new javax.swing.JButton();
        txtSearchBrand = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        txtSearchBrand1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSearchKeys = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        btnNewSerachKey = new javax.swing.JButton();
        btnDeleteKey = new javax.swing.JButton();
        btnUpdateKey = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        supplierPanel1 = new views.supplierviews.SupplierPanel();

        btnDeleteItem.setText("Delete");
        btnDeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteItemActionPerformed(evt);
            }
        });

        btnNewItem.setText("New");
        btnNewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewItemActionPerformed(evt);
            }
        });

        btnUpdateItem.setText("Update");
        btnUpdateItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateItemActionPerformed(evt);
            }
        });

        jButton3.setText("New Part");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNewItem, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdateItem, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDeleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDeleteItem, btnUpdateItem, jButton3});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewItem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateItem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDeleteItem, btnNewItem, btnUpdateItem, jButton3});

        tblAllItems.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblAllItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Brand", "Part No.", "Description", "Category", "Model", "Supplier", "Buying Price", "Selling Price", "Date registered", "Re-Order Qty", "Remarks", "Commision", "Rack No."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAllItems.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblAllItems.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tblAllItems.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tblAllItemsMouseMoved(evt);
            }
        });
        tblAllItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAllItemsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblAllItemsMousePressed(evt);
            }
        });
        tblAllItems.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblAllItemsKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblAllItems);
        if (tblAllItems.getColumnModel().getColumnCount() > 0) {
            tblAllItems.getColumnModel().getColumn(0).setMinWidth(0);
            tblAllItems.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblAllItems.getColumnModel().getColumn(0).setMaxWidth(0);
            tblAllItems.getColumnModel().getColumn(2).setMinWidth(150);
            tblAllItems.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblAllItems.getColumnModel().getColumn(2).setMaxWidth(150);
            tblAllItems.getColumnModel().getColumn(3).setMinWidth(200);
            tblAllItems.getColumnModel().getColumn(3).setPreferredWidth(200);
            tblAllItems.getColumnModel().getColumn(3).setMaxWidth(200);
            tblAllItems.getColumnModel().getColumn(4).setMinWidth(200);
            tblAllItems.getColumnModel().getColumn(4).setPreferredWidth(200);
            tblAllItems.getColumnModel().getColumn(4).setMaxWidth(200);
            tblAllItems.getColumnModel().getColumn(6).setMinWidth(75);
            tblAllItems.getColumnModel().getColumn(6).setPreferredWidth(75);
            tblAllItems.getColumnModel().getColumn(6).setMaxWidth(75);
            tblAllItems.getColumnModel().getColumn(11).setMinWidth(100);
            tblAllItems.getColumnModel().getColumn(11).setPreferredWidth(100);
            tblAllItems.getColumnModel().getColumn(11).setMaxWidth(100);
        }

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Image"));
        jPanel5.setMaximumSize(new java.awt.Dimension(387, 201));

        lblImage.setMaximumSize(new java.awt.Dimension(387, 201));
        lblImage.setMinimumSize(new java.awt.Dimension(387, 201));
        lblImage.setPreferredSize(new java.awt.Dimension(387, 201));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 187, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        txtSeachItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSeachItemKeyReleased(evt);
            }
        });

        btnSeachCustomer.setText("Search");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtSeachItem, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSeachCustomer)
                        .addGap(0, 449, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSeachItem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeachCustomer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jTabbedPane1.addTab("All Parts", jPanel1);

        tblAllItemBrands.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblAllItemBrands.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblAllItemBrands.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Brand ID", "Brand Name", "Description", "Brand Code"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAllItemBrands.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAllItemBrandsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblAllItemBrandsMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblAllItemBrands);
        if (tblAllItemBrands.getColumnModel().getColumnCount() > 0) {
            tblAllItemBrands.getColumnModel().getColumn(0).setMinWidth(0);
            tblAllItemBrands.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblAllItemBrands.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        btnNewItem1.setText("New");
        btnNewItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewItem1ActionPerformed(evt);
            }
        });

        btnUpdateBrand.setText("Update");
        btnUpdateBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateBrandActionPerformed(evt);
            }
        });

        btnDeleteBrand.setText("Delete");
        btnDeleteBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBrandActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addComponent(btnNewItem1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdateBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDeleteBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDeleteBrand, btnNewItem1, btnUpdateBrand});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewItem1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDeleteBrand, btnNewItem1, btnUpdateBrand});

        txtSearchBrand.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchBrandKeyReleased(evt);
            }
        });

        jButton1.setText("Search");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtSearchBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE))
                        .addGap(10, 10, 10)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Brands", jPanel2);

        txtSearchBrand1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchBrand1KeyReleased(evt);
            }
        });

        jButton2.setText("Search");

        tblSearchKeys.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblSearchKeys.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblSearchKeys.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Key ID", "Search Key", "Description", "Date Registered"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSearchKeys.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSearchKeysMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblSearchKeys);
        if (tblSearchKeys.getColumnModel().getColumnCount() > 0) {
            tblSearchKeys.getColumnModel().getColumn(0).setMinWidth(0);
            tblSearchKeys.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblSearchKeys.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        btnNewSerachKey.setText("New");
        btnNewSerachKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewSerachKeyActionPerformed(evt);
            }
        });

        btnDeleteKey.setText("Delete");
        btnDeleteKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteKeyActionPerformed(evt);
            }
        });

        btnUpdateKey.setText("Update");
        btnUpdateKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateKeyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(btnNewSerachKey, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteKey, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdateKey, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDeleteKey, btnNewSerachKey, btnUpdateKey});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnUpdateKey, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNewSerachKey, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDeleteKey, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDeleteKey, btnNewSerachKey, btnUpdateKey});

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtSearchBrand1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 496, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchBrand1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Categories", jPanel6);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(supplierPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(supplierPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Suppliers", jPanel8);

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

    private void btnNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewItemActionPerformed
        new NewItem(null, true).setVisible(true);
    }//GEN-LAST:event_btnNewItemActionPerformed

    private void btnDeleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteItemActionPerformed
        deleteItems(tblAllItems, "Item");

    }//GEN-LAST:event_btnDeleteItemActionPerformed

    private void btnUpdateItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateItemActionPerformed
        updateItem();
        try {
            ItemController.loadAllItems(allItemDtm);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ItemPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ItemPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(ItemPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnUpdateItemActionPerformed

    private void tblAllItemsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAllItemsMousePressed
        checkItemButtons();
        int selectedRow = tblAllItems.getSelectedRow();
        int itemID = Integer.parseInt(tblAllItems.getValueAt(selectedRow, 0).toString());
        previwImage(itemID);
    }//GEN-LAST:event_tblAllItemsMousePressed

    private void tblAllItemBrandsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAllItemBrandsMouseClicked
        checkBrandButtons();
        int brandSelection = tblAllItemBrands.getSelectedRow();
        tblAllItemBrands.setRowSelectionInterval(brandSelection, brandSelection);
        checkFirstSelected();
    }//GEN-LAST:event_tblAllItemBrandsMouseClicked

    private void tblAllItemBrandsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAllItemBrandsMousePressed
        checkBrandButtons();
        checkFirstSelected();
    }//GEN-LAST:event_tblAllItemBrandsMousePressed

    private void btnNewItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewItem1ActionPerformed
        try {
            new NewBrand(null, true).setVisible(true);
            BrandController.loadAllItemBrands(allItemBrandsDtm);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnNewItem1ActionPerformed

    private void btnDeleteBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBrandActionPerformed
        deleteBrands();
    }//GEN-LAST:event_btnDeleteBrandActionPerformed

    private void btnUpdateBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateBrandActionPerformed
        updateBrand();
    }//GEN-LAST:event_btnUpdateBrandActionPerformed

    private void tblAllItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAllItemsMouseClicked
        checkItemButtons();
        int selectedRow = tblAllItems.getSelectedRow();
        int itemID = Integer.parseInt(tblAllItems.getValueAt(selectedRow, 0).toString());
        previwImage(itemID);

    }//GEN-LAST:event_tblAllItemsMouseClicked

    private void txtSeachItemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSeachItemKeyReleased
        tblAllItems.setRowSorter(itemSorter);
        itemSorter = new TableRowSorter<TableModel>(allItemDtm);
        itemSorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtSeachItem.getText()));
        // tblAllItems.setRowSorter(null);
    }//GEN-LAST:event_txtSeachItemKeyReleased

    private void txtSearchBrandKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchBrandKeyReleased
        tblAllItemBrands.setRowSorter(brandSorter);
        brandSorter = new TableRowSorter<TableModel>(allItemBrandsDtm);
        brandSorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtSearchBrand.getText()));
        //tblAllItemBrands.setRowSorter(null);
    }//GEN-LAST:event_txtSearchBrandKeyReleased

    private void tblAllItemsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblAllItemsKeyPressed
        int keyCode = evt.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            checkItemButtons();
            int selectedRow = tblAllItems.getSelectedRow();
            int itemID = Integer.parseInt(tblAllItems.getValueAt(selectedRow, 0).toString());
            previwImage(itemID);
        }
    }//GEN-LAST:event_tblAllItemsKeyPressed

    private void tblAllItemsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAllItemsMouseMoved
        try {
            Point point = evt.getPoint();
            int row = tblAllItems.rowAtPoint(point);
            int itemID = Integer.parseInt(tblAllItems.getValueAt(row, 0).toString());
            ResultSet rst = ItemController.getAlternativesForItem(itemID);
            String competitorParts = "Comp: ";
            while (rst.next()) {
                competitorParts += "," + rst.getString("part_no");
            }
            setToolTipText("fsdfff");
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }

    }//GEN-LAST:event_tblAllItemsMouseMoved

    private void txtSearchBrand1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchBrand1KeyReleased
        // salesOrdersDtm = (DefaultTableModel) tblAllSalesOrders.getModel();
        String text = txtSearchBrand1.getText();

        try {

            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(searchKeyDtm);
            tblSearchKeys.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + text)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtSearchBrand1KeyReleased

    private void tblSearchKeysMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSearchKeysMousePressed
        checkSupplierButtons();

        checkDimoSupplier();
    }//GEN-LAST:event_tblSearchKeysMousePressed

    private void btnNewSerachKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewSerachKeyActionPerformed
        try {
            NewSearchKey searchKey = new NewSearchKey(null, true);
            searchKey.setVisible(true);
            SearchCategoryController.loadAllItemSearchKeys(searchKeyDtm);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnNewSerachKeyActionPerformed

    private void btnDeleteKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteKeyActionPerformed
        deleteSearchKeys();
    }//GEN-LAST:event_btnDeleteKeyActionPerformed

    private void btnUpdateKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateKeyActionPerformed
        int row = tblSearchKeys.getSelectedRow();
        int supplierID = Integer.parseInt(tblSearchKeys.getValueAt(row, 0).toString());
        //   System.out.println(supplierID);
        String val0 = tblSearchKeys.getValueAt(row, 0).toString();
        String val1 = tblSearchKeys.getValueAt(row, 1).toString();
        String val2 = tblSearchKeys.getValueAt(row, 2).toString();
        String val3 = tblSearchKeys.getValueAt(row, 3).toString();
        

        SearchCategory category = new SearchCategory();
        category.setCategoryID(Integer.parseInt(val0));
        category.setCategoryName(val1);
        category.setDescription(val2);

//         System.out.println("code"+service.getServiceName());
        UpdatePartCategories updatePartCategories = new UpdatePartCategories(null, true);
        updatePartCategories.setUpdateCategories(category);
        updatePartCategories.setVisible(true);
        try {
            SearchCategoryController.loadAllItemSearchKeys(searchKeyDtm);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ItemPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ItemPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdateKeyActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        NewItem newItem = new NewItem(null, true);
        newItem.setVisible(true);
        try {
            ItemController.loadAllItems(allItemDtm);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ItemPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ItemPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(ItemPanel.class.getName()).log(Level.SEVERE, null, ex);
        }



    }//GEN-LAST:event_jButton3ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteBrand;
    private javax.swing.JButton btnDeleteItem;
    private javax.swing.JButton btnDeleteKey;
    private javax.swing.JButton btnNewItem;
    private javax.swing.JButton btnNewItem1;
    private javax.swing.JButton btnNewSerachKey;
    private javax.swing.JButton btnSeachCustomer;
    private javax.swing.JButton btnUpdateBrand;
    private javax.swing.JButton btnUpdateItem;
    private javax.swing.JButton btnUpdateKey;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblImage;
    private views.supplierviews.SupplierPanel supplierPanel1;
    private javax.swing.JTable tblAllItemBrands;
    private javax.swing.JTable tblAllItems;
    private javax.swing.JTable tblSearchKeys;
    private javax.swing.JTextField txtSeachItem;
    private javax.swing.JTextField txtSearchBrand;
    private javax.swing.JTextField txtSearchBrand1;
    // End of variables declaration//GEN-END:variables
}
