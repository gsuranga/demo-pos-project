/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views.stockviws;

import controllers.StockController;
import controllers.SupplierController;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import models.Stock;
import models.StockDetail;
import utilities_new.ComboSearch1;
import utilities_new.Combosearch;
import views.supplierviews.NewSupplier;

/**
 *
 * @author SHdinesh Madushanka
 */
public class StockDetailPanel extends javax.swing.JPanel {

    private DefaultTableModel allStocksDtm, stockDetailDtm, stockItemsDtm, updateStockDtm;
    private TableRowSorter<TableModel> sorter;

    private Combosearch supplierSearch;
    private ComboSearch1 serch;
    private HashSet<Integer> removedDetails;
    private int currentStockID;
    private Object previousCellValue;
    private Object previousSupplier;
    private StockTableListner stListner;

    /**
     * Creates new form StockDetailPanel
     */
    public StockDetailPanel() {
        initComponents();
        allStocksDtm = (DefaultTableModel) tblAllStock.getModel();
        stockDetailDtm = (DefaultTableModel) tblStockDetails.getModel();
        stockItemsDtm = (DefaultTableModel) tblStockItems.getModel();
        updateStockDtm = (DefaultTableModel) tblUpdateStock.getModel();
        //this.stockDetailDtm = stockDtm;
        checkUpdateStockButton();
        checkButtons();
        try {
            StockController.loadAllStockDetail(allStocksDtm);
            SupplierController.fillSupplierNames(cmbAllSuppliers);
            SupplierController.fillSupplierNames(cmbUpdateStockSupplier);
            jTabbedPane1.setEnabledAt(2, false);

            // updateStockDtm.fireTableDataChanged();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
        sorter = new TableRowSorter<TableModel>(allStocksDtm);
        tblAllStock.setRowSorter(sorter);
        supplierSearch = new Combosearch();
        supplierSearch.setSearchableCombo(cmbAllSuppliers, true, "Supplier is not registered.");
        removedDetails = new HashSet<Integer>();
    }

    public void checkUpdateStockButton() {
        int selectedRows = tblAllStock.getSelectedRowCount();
        if (selectedRows > 0) {
            btnUpdateStock.setEnabled(true);
        } else {
            btnUpdateStock.setEnabled(false);
        }
    }
    
    
 public void checkViewOtherReutnButton() {
        int selectedRow = tblAllStock.getSelectedRow();
        int supplierID = Integer.parseInt(tblAllStock.getValueAt(selectedRow, 1).toString());
        if (supplierID> 1) {
            btnOtherreturn.setEnabled(true);
            btnDIMOReturn.setEnabled(false);
        } else {
            btnOtherreturn.setEnabled(false);
            btnDIMOReturn.setEnabled(true);
        }
    }
    
//    public void fillItemNames() throws ClassNotFoundException, SQLException {
//
//        String query = "select item_part_no from item where status = '1'";
//        ResultSet rstItem = DBHandler.getData(query);
//        cmbAllItems.removeAllItems();
//        while (rstItem.next()) {
//
//            cmbAllItems.addItem(rstItem.getString(1));
//        }
//    }
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

    public void removeTableItems() {
        DefaultTableModel dtm = (DefaultTableModel) tblStockItems.getModel();
        int selectedRow[] = tblStockItems.getSelectedRows();
        while (selectedRow.length > 0) {
            int option = JOptionPane.showConfirmDialog(this, "Do you want to remove this item?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == 0) {
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
        int option = JOptionPane.showConfirmDialog(this, "Do you want to remove these all items?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == 0) {
            while (allRows > 0) {
                dtm.removeRow(0);
                allRows = table.getRowCount();

            }
            checkButtons();
        }

    }

    public void addTableItemsToDB() throws ClassNotFoundException, SQLException {
        String supplier = cmbAllSuppliers.getSelectedItem().toString();
        int supplierID = SupplierController.getSupplierIDforName(supplier);

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String addedDate = sdf.format(currentDate);
        sdf.applyPattern("HH:mm:ss");
        String deliverdTime = sdf.format(currentDate);
        int status = 1;
        System.out.println(deliverdTime);
        Stock stock = new Stock(supplierID, addedDate, deliverdTime, status);
        stock.setDeliverOrderId(0);
        int value = StockController.addNewStock(stock);

        if (value > 0) {
            int rowCount = tblStockItems.getRowCount();
            int addedItems[] = new int[rowCount];
            int stockID = StockController.getMaxStockID();
            for (int i = 0; i < rowCount; i++) {
                int itemID = Integer.parseInt(tblStockItems.getValueAt(i, 0).toString());
                int stockQuantity = Integer.parseInt(tblStockItems.getValueAt(i, 3).toString());
                int stockDetailStatus = 1;
                //int otherSuplierReturn=0;
                double buyingPrice = Double.parseDouble(tblStockItems.getValueAt(i, 4).toString());
                double sellingPrice = Double.parseDouble(tblStockItems.getValueAt(i, 5).toString());
                StockDetail stockDetail = new StockDetail(stockID, itemID, stockQuantity, stockQuantity, buyingPrice, sellingPrice, stockDetailStatus);
                addedItems[i] = StockController.addNewStockDetail(stockDetail);
                //ItemController.updateItemQuantity(new Item(itemID, stockQuantity + StockController.getTotalRemainingStockQuantityOfaItem(itemID)));
                checkButtons();
                //clear

            }
            if (addedItems.length > 0) {
                JOptionPane.showMessageDialog(this, "All items successfully added to the stock.", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                int allRows = tblStockItems.getRowCount();
                while (allRows > 0) {
                    stockItemsDtm.removeRow(0);
                    allRows = tblStockItems.getRowCount();
                }
                checkButtons();
                try {
                    StockController.loadAllStockDetail(allStocksDtm);
                    //StockController.loadAllStockDetail(stockDetailDtm);
                } catch (ClassNotFoundException | SQLException classNotFoundException) {
                }
            } else {
            }

        }

    }

    public class StockTableListner implements TableModelListener {

        public StockTableListner() {
            tblUpdateStock.getModel().addTableModelListener(this);
        }

        @Override
        public void tableChanged(TableModelEvent e) {

            TableModel model = (TableModel) e.getSource();
            int type = e.getType();
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (type == 0) {
                if (column == 3 || column == 4) {
                    try {
                        String sQty = model.getValueAt(row, 3).toString();
                        String rQty = model.getValueAt(row, 4).toString();
                        int status = Integer.parseInt(model.getValueAt(row, 7).toString());

                        int stockQty = Integer.parseInt(sQty);
                        int remainingQty = Integer.parseInt(rQty);
                        if ((remainingQty > stockQty) || (rQty == null) || (rQty == "")) {
                            JOptionPane.showMessageDialog(null, "Remaining qty cannot be over than initial qty.", "Error in quantity", JOptionPane.WARNING_MESSAGE);
                            model.setValueAt(previousCellValue, row, column);
                        } else {
                            model.setValueAt(1, row, 7);
                        }
                    } catch (NumberFormatException numberFormatException) {
                        JOptionPane.showMessageDialog(null, "Stock quantities cannot be empty.", "Error in quantity", JOptionPane.WARNING_MESSAGE);
                        model.setValueAt(previousCellValue, row, column);
                    } catch (HeadlessException headlessException) {
                    }
                } else if (column == 5 || column == 6) {
                    try {
                        String bPrice = model.getValueAt(row, 5).toString();
                        String sPrice = model.getValueAt(row, 6).toString();

                        double buyingPrice = Double.parseDouble(bPrice);
                        double sellingPrice = Double.parseDouble(sPrice);
                        model.setValueAt(1, row, 7);
                    } catch (NumberFormatException numberFormatException) {

                        JOptionPane.showMessageDialog(null, "Price fields cannot empty.", "Empty value", JOptionPane.WARNING_MESSAGE);
                        model.setValueAt(previousCellValue, row, column);

                    }

                }
            } else if (type == 1) {

                model.setValueAt(1, row, 8);
            } else if (type == -1) {
                /// System.out.println("removed");
            }
        }

    }

    public void removeUpdateTableItems() {
        DefaultTableModel dtm = (DefaultTableModel) tblUpdateStock.getModel();
        int selectedRow[] = tblUpdateStock.getSelectedRows();
        while (selectedRow.length > 0) {
            int option = JOptionPane.showConfirmDialog(this, "Do you want to remove this part?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == 0) {
                int itemID = Integer.parseInt(tblUpdateStock.getValueAt(selectedRow[0], 0).toString());
                removedDetails.add(itemID);
                dtm.removeRow(selectedRow[0]);
                selectedRow = tblUpdateStock.getSelectedRows();
            } else {
                tblUpdateStock.clearSelection();
                break;

            }

        }
    }

    public void updateStock() {
        int rows = tblUpdateStock.getRowCount();
        int columns = tblUpdateStock.getColumnCount();
        String supplier = cmbUpdateStockSupplier.getSelectedItem().toString().trim();

        try {
            Stock st = new Stock();
            st.setSupplierID(SupplierController.getSupplierIDforName(supplier));
            st.setStockID(currentStockID);
            int status = StockController.updateStock(st);
            if (status > 0) {
                Iterator it = removedDetails.iterator();
                for (int i = 0; i < removedDetails.size(); i++) {

                    int itemID = Integer.parseInt(it.next().toString());
                    StockDetail std = new StockDetail();
                    std.setStockID(currentStockID);
                    std.setItemID(itemID);
                    StockController.deleteStockDetail(std);
                }

                for (int i = 0; i < rows; i++) {
                    int addStatus = Integer.parseInt((tblUpdateStock.getValueAt(i, 8).toString()));
                    int updateStatus = Integer.parseInt((tblUpdateStock.getValueAt(i, 7).toString()));
                    int partID = Integer.parseInt((tblUpdateStock.getValueAt(i, 0).toString()));
                    int stockQty = Integer.parseInt((tblUpdateStock.getValueAt(i, 3).toString()));
                    int remainingQty = Integer.parseInt((tblUpdateStock.getValueAt(i, 4).toString()));
                    double buyingPrice = Double.parseDouble((tblUpdateStock.getValueAt(i, 5).toString()));
                    double sellingPrice = Double.parseDouble((tblUpdateStock.getValueAt(i, 6).toString()));
                    StockDetail std = new StockDetail();

                    if (addStatus == 1) {
                        std.setBuyingPrice(buyingPrice);
                        std.setSellingPrice(sellingPrice);
                        std.setItemID(partID);
                        std.setRemainingQty(remainingQty);
                        std.setStockQuontity(stockQty);
                        std.setStockID(currentStockID);
                        std.setStatus(1);
                        StockController.addNewStockDetail(std);
                    } else if (addStatus == 0 && updateStatus == 1) {
                        System.out.println("updated");
                        std.setBuyingPrice(buyingPrice);
                        std.setSellingPrice(sellingPrice);
                        std.setItemID(partID);
                        std.setRemainingQty(remainingQty);
                        std.setStockQuontity(stockQty);
                        std.setStockID(currentStockID);
                        std.setStatus(1);
                        StockController.updateStockDetail(std);
                    }
                }
                JOptionPane.showMessageDialog(null, "Stock successfully updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateStockDtm.setRowCount(0);
                jTabbedPane1.setSelectedIndex(1);
                tblAllStock.clearSelection();
                stockDetailDtm.setRowCount(0);
                updateStockDtm.removeTableModelListener(stListner);
                tblUpdateStock.revalidate();
                StockController.loadAllStockDetail(allStocksDtm);
                jTabbedPane1.setEnabledAt(2, false);

            }
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbAllSuppliers = new javax.swing.JComboBox();
        btnAddNewSupplier = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblStockItems = new javax.swing.JTable();
        btnAddToTable = new javax.swing.JButton();
        btnRemoveFromTable = new javax.swing.JButton();
        btnRemoveAllInTable = new javax.swing.JButton();
        btnAddStock = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAllStock = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtSearchSupplierName = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStockDetails = new javax.swing.JTable();
        btnUpdateStock = new javax.swing.JButton();
        btnOtherreturn = new javax.swing.JButton();
        btnDIMOReturn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cmbUpdateStockSupplier = new javax.swing.JComboBox();
        btnAddNewSupplier1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblUpdateStock = new javax.swing.JTable();
        btnAddStock1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnAddToTable1 = new javax.swing.JButton();
        btnRemoveFromTable1 = new javax.swing.JButton();

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel3.setText("Supplier :");

        cmbAllSuppliers.setEditable(true);
        cmbAllSuppliers.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        btnAddNewSupplier.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnAddNewSupplier.setText("New");
        btnAddNewSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewSupplierActionPerformed(evt);
            }
        });

        tblStockItems.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblStockItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PartID", "Part No.", "Description", "Quantity", "Buying Price (Rs.)", "Selling Price (Rs.)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStockItems.setRowHeight(20);
        tblStockItems.setRowMargin(2);
        tblStockItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStockItemsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblStockItems);
        if (tblStockItems.getColumnModel().getColumnCount() > 0) {
            tblStockItems.getColumnModel().getColumn(0).setMinWidth(0);
            tblStockItems.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblStockItems.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        btnAddToTable.setText("+");
        btnAddToTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToTableActionPerformed(evt);
            }
        });

        btnRemoveFromTable.setText("-");
        btnRemoveFromTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveFromTableActionPerformed(evt);
            }
        });

        btnRemoveAllInTable.setText("-");
        btnRemoveAllInTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAllInTableActionPerformed(evt);
            }
        });

        btnAddStock.setText("Add Stock");
        btnAddStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStockActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbAllSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddNewSupplier)
                                .addGap(0, 489, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 810, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRemoveFromTable, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnAddToTable, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnRemoveAllInTable, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddStock, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60))))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
                    .addGap(59, 59, 59)))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddToTable, btnRemoveAllInTable, btnRemoveFromTable});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddStock, jButton2});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbAllSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddNewSupplier))
                .addGap(12, 12, 12)
                .addComponent(btnAddToTable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRemoveFromTable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRemoveAllInTable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 414, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(btnAddStock))
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(58, 58, 58)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addGap(50, 50, 50)))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddToTable, btnRemoveAllInTable, btnRemoveFromTable});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddStock, jButton2});

        jTabbedPane1.addTab("New Stock", jPanel3);

        tblAllStock.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblAllStock.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblAllStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Stock ID", "supplierID", "Supplier", "Stock Date", "Delivered Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAllStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAllStockMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblAllStockMousePressed(evt);
            }
        });
        tblAllStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblAllStockKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblAllStock);
        if (tblAllStock.getColumnModel().getColumnCount() > 0) {
            tblAllStock.getColumnModel().getColumn(0).setMinWidth(0);
            tblAllStock.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblAllStock.getColumnModel().getColumn(0).setMaxWidth(0);
            tblAllStock.getColumnModel().getColumn(1).setMinWidth(0);
            tblAllStock.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblAllStock.getColumnModel().getColumn(1).setMaxWidth(0);
            tblAllStock.getColumnModel().getColumn(2).setResizable(false);
            tblAllStock.getColumnModel().getColumn(2).setPreferredWidth(130);
            tblAllStock.getColumnModel().getColumn(3).setResizable(false);
            tblAllStock.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblAllStock.getColumnModel().getColumn(4).setResizable(false);
            tblAllStock.getColumnModel().getColumn(4).setPreferredWidth(120);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtSearchSupplierName.setText("Type Supplier Name.");
        txtSearchSupplierName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchSupplierNameActionPerformed(evt);
            }
        });
        txtSearchSupplierName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchSupplierNameFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchSupplierNameFocusGained(evt);
            }
        });
        txtSearchSupplierName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchSupplierNameKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchSupplierNameKeyReleased(evt);
            }
        });

        jButton3.setText("Search");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearchSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 401, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearchSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))))
        );

        tblStockDetails.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblStockDetails.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblStockDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "detail id", "Part No.", "Description", "Initial Quantity", "Remaining Quantity", "Buying Price(Rs.)", "Selling Price(Rs.) "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblStockDetails);
        if (tblStockDetails.getColumnModel().getColumnCount() > 0) {
            tblStockDetails.getColumnModel().getColumn(0).setMinWidth(0);
            tblStockDetails.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblStockDetails.getColumnModel().getColumn(0).setMaxWidth(0);
            tblStockDetails.getColumnModel().getColumn(3).setMinWidth(70);
            tblStockDetails.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblStockDetails.getColumnModel().getColumn(3).setMaxWidth(70);
            tblStockDetails.getColumnModel().getColumn(4).setMinWidth(70);
            tblStockDetails.getColumnModel().getColumn(4).setPreferredWidth(70);
            tblStockDetails.getColumnModel().getColumn(4).setMaxWidth(70);
            tblStockDetails.getColumnModel().getColumn(5).setMinWidth(75);
            tblStockDetails.getColumnModel().getColumn(5).setPreferredWidth(75);
            tblStockDetails.getColumnModel().getColumn(5).setMaxWidth(75);
            tblStockDetails.getColumnModel().getColumn(6).setMinWidth(75);
            tblStockDetails.getColumnModel().getColumn(6).setPreferredWidth(75);
            tblStockDetails.getColumnModel().getColumn(6).setMaxWidth(75);
        }

        btnUpdateStock.setText("Update Stock");
        btnUpdateStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateStockActionPerformed(evt);
            }
        });

        btnOtherreturn.setText("Other Supplier Return");
        btnOtherreturn.setEnabled(false);
        btnOtherreturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOtherreturnActionPerformed(evt);
            }
        });

        btnDIMOReturn.setText("DIMO Parts Return");
        btnDIMOReturn.setEnabled(false);
        btnDIMOReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDIMOReturnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDIMOReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOtherreturn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnUpdateStock, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDIMOReturn, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnOtherreturn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdateStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Stock Details", jPanel1);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel4.setText("Supplier :");

        cmbUpdateStockSupplier.setEditable(true);
        cmbUpdateStockSupplier.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        btnAddNewSupplier1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnAddNewSupplier1.setText("New");
        btnAddNewSupplier1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewSupplier1ActionPerformed(evt);
            }
        });

        tblUpdateStock.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblUpdateStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PartID", "Part No.", "Description", "Quantity", "Remaining Qty", "Buying Price (Rs.)", "Selling Price (Rs.)", "updated", "inserted"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUpdateStock.setRowHeight(20);
        tblUpdateStock.setRowMargin(2);
        tblUpdateStock.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblUpdateStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUpdateStockMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblUpdateStock);
        if (tblUpdateStock.getColumnModel().getColumnCount() > 0) {
            tblUpdateStock.getColumnModel().getColumn(0).setMinWidth(0);
            tblUpdateStock.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblUpdateStock.getColumnModel().getColumn(0).setMaxWidth(0);
            tblUpdateStock.getColumnModel().getColumn(1).setResizable(false);
            tblUpdateStock.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblUpdateStock.getColumnModel().getColumn(2).setResizable(false);
            tblUpdateStock.getColumnModel().getColumn(2).setPreferredWidth(300);
            tblUpdateStock.getColumnModel().getColumn(3).setResizable(false);
            tblUpdateStock.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblUpdateStock.getColumnModel().getColumn(4).setMinWidth(100);
            tblUpdateStock.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblUpdateStock.getColumnModel().getColumn(4).setMaxWidth(100);
            tblUpdateStock.getColumnModel().getColumn(5).setMinWidth(150);
            tblUpdateStock.getColumnModel().getColumn(5).setPreferredWidth(150);
            tblUpdateStock.getColumnModel().getColumn(5).setMaxWidth(150);
            tblUpdateStock.getColumnModel().getColumn(6).setMinWidth(150);
            tblUpdateStock.getColumnModel().getColumn(6).setPreferredWidth(150);
            tblUpdateStock.getColumnModel().getColumn(6).setMaxWidth(150);
            tblUpdateStock.getColumnModel().getColumn(7).setMinWidth(0);
            tblUpdateStock.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblUpdateStock.getColumnModel().getColumn(7).setMaxWidth(0);
            tblUpdateStock.getColumnModel().getColumn(8).setMinWidth(0);
            tblUpdateStock.getColumnModel().getColumn(8).setPreferredWidth(0);
            tblUpdateStock.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        btnAddStock1.setText("Update");
        btnAddStock1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStock1ActionPerformed(evt);
            }
        });

        jButton4.setText("Cancel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnAddToTable1.setText("+");
        btnAddToTable1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToTable1ActionPerformed(evt);
            }
        });

        btnRemoveFromTable1.setText("-");
        btnRemoveFromTable1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveFromTable1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbUpdateStockSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddNewSupplier1)
                        .addGap(0, 479, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 576, Short.MAX_VALUE)
                                .addComponent(btnAddStock1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnRemoveFromTable1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddToTable1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddToTable1, btnRemoveFromTable1});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddStock1, jButton4});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbUpdateStockSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddNewSupplier1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnAddToTable1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemoveFromTable1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddStock1)
                    .addComponent(jButton4))
                .addGap(19, 19, 19))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddToTable1, btnRemoveFromTable1});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddStock1, jButton4});

        jTabbedPane1.addTab("Update", jPanel4);

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

    private void txtSearchSupplierNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchSupplierNameFocusLost
        txtSearchSupplierName.setText("Search...");
    }//GEN-LAST:event_txtSearchSupplierNameFocusLost

    private void txtSearchSupplierNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchSupplierNameFocusGained
        txtSearchSupplierName.setText("");
    }//GEN-LAST:event_txtSearchSupplierNameFocusGained

    private void btnUpdateStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateStockActionPerformed
        try {
            int selected = tblAllStock.getSelectedRow();
            int stockID = Integer.parseInt(tblAllStock.getValueAt(selected, 0).toString());
            this.currentStockID = stockID;
            String supplier = tblAllStock.getValueAt(selected, 2).toString().trim();
            jTabbedPane1.setEnabledAt(2, true);
            StockController.loadAllStockDetailsForUpdate(updateStockDtm, stockID);

            cmbUpdateStockSupplier.setSelectedItem(supplier);
            jTabbedPane1.setSelectedIndex(2);
            previousSupplier = supplier;
            stListner = new StockTableListner();
            System.out.println("");
            tblUpdateStock.revalidate();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }


    }//GEN-LAST:event_btnUpdateStockActionPerformed

    private void tblAllStockMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAllStockMousePressed
//        try {
//            int stockID = tblAllStock.getSelectedRow();
//            StockController.loadAllStockDetails(stockDetailDtm, stockID);
//            checkUpdateStockButton();
//        } catch (SQLException ex) {
//        } catch (ClassNotFoundException ex) {
//        }

    }//GEN-LAST:event_tblAllStockMousePressed

    private void tblAllStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAllStockMouseClicked
        try {
            int selectedID = Integer.parseInt(tblAllStock.getValueAt(tblAllStock.getSelectedRow(), 0).toString());
            StockController.loadAllStockDetails(stockDetailDtm, selectedID);
            checkUpdateStockButton();
            checkViewOtherReutnButton();
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
        }
    }//GEN-LAST:event_tblAllStockMouseClicked

    private void tblAllStockKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblAllStockKeyReleased
        int keyCode = evt.getKeyCode();
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP) {
            try {
                int selectedID = Integer.parseInt(tblAllStock.getValueAt(tblAllStock.getSelectedRow(), 0).toString());
                StockController.loadAllStockDetails(stockDetailDtm, selectedID);
                checkUpdateStockButton();
            } catch (NumberFormatException numberFormatException) {
            } catch (SQLException sQLException) {
            } catch (ClassNotFoundException classNotFoundException) {
            }
        }
    }//GEN-LAST:event_tblAllStockKeyReleased

    private void txtSearchSupplierNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchSupplierNameKeyTyped
    }//GEN-LAST:event_txtSearchSupplierNameKeyTyped

    private void txtSearchSupplierNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchSupplierNameActionPerformed
        sorter.setRowFilter(RowFilter.regexFilter(txtSearchSupplierName.getText()));
    }//GEN-LAST:event_txtSearchSupplierNameActionPerformed

    private void txtSearchSupplierNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchSupplierNameKeyReleased
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtSearchSupplierName.getText()));
    }//GEN-LAST:event_txtSearchSupplierNameKeyReleased

    private void btnAddNewSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewSupplierActionPerformed
        try {
            new NewSupplier(null, true).setVisible(true);
            SupplierController.fillSupplierNames(cmbAllSuppliers);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnAddNewSupplierActionPerformed

    private void tblStockItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStockItemsMouseClicked
        checkButtons();
    }//GEN-LAST:event_tblStockItemsMouseClicked

    private void btnAddToTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToTableActionPerformed
        String supplier = cmbAllSuppliers.getSelectedItem().toString();
        if (supplier == null || supplier == "" || supplier == "null" || supplier.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a suplier", "No Supplier", JOptionPane.WARNING_MESSAGE);
        } else {
            SelectPartForStocks partStocks = new SelectPartForStocks(null, true, stockItemsDtm);
            partStocks.setVisible(true);
            checkButtons();
        }

    }//GEN-LAST:event_btnAddToTableActionPerformed

    private void btnRemoveFromTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveFromTableActionPerformed
        removeTableItems();
        checkButtons();
    }//GEN-LAST:event_btnRemoveFromTableActionPerformed

    private void btnRemoveAllInTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAllInTableActionPerformed
        removeAllTableItems(tblStockItems);
        checkButtons();
    }//GEN-LAST:event_btnRemoveAllInTableActionPerformed

    private void btnAddStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStockActionPerformed
        try {
            addTableItemsToDB();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnAddStockActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        stockItemsDtm.setRowCount(0);
        checkButtons();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnAddNewSupplier1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewSupplier1ActionPerformed
        try {
            new NewSupplier(null, true).setVisible(true);
            SupplierController.fillSupplierNames(cmbUpdateStockSupplier);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnAddNewSupplier1ActionPerformed

    private void tblUpdateStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUpdateStockMouseClicked
        Point p = evt.getPoint();
        int row = tblUpdateStock.rowAtPoint(p);
        int column = tblUpdateStock.columnAtPoint(p);
        previousCellValue = tblUpdateStock.getValueAt(row, column);


    }//GEN-LAST:event_tblUpdateStockMouseClicked

    private void btnAddStock1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStock1ActionPerformed
        updateStock();
    }//GEN-LAST:event_btnAddStock1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            updateStockDtm.setRowCount(0);
            jTabbedPane1.setSelectedIndex(1);
            tblAllStock.clearSelection();
            stockDetailDtm.setRowCount(0);
            updateStockDtm.removeTableModelListener(stListner);
            tblUpdateStock.revalidate();
            StockController.loadAllStockDetail(allStocksDtm);
            jTabbedPane1.setEnabledAt(2, false);
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnAddToTable1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToTable1ActionPerformed
        String supplier = cmbUpdateStockSupplier.getSelectedItem().toString();
        if (supplier == null || supplier == "" || supplier == "null" || supplier.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a suplier", "No Supplier", JOptionPane.WARNING_MESSAGE);
        } else {
            SelectPartForUpdateStocks partStocks = new SelectPartForUpdateStocks(null, true, updateStockDtm);
            partStocks.setVisible(true);
            checkButtons();
        }
    }//GEN-LAST:event_btnAddToTable1ActionPerformed

    private void btnRemoveFromTable1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveFromTable1ActionPerformed
        removeUpdateTableItems();
    }//GEN-LAST:event_btnRemoveFromTable1ActionPerformed

    private void btnOtherreturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOtherreturnActionPerformed
     try {
            int selectedRow = tblAllStock.getSelectedRow();
            int stockID = Integer.parseInt(tblAllStock.getValueAt(selectedRow, 0).toString());
             OtherSupplierReturns sup = new OtherSupplierReturns (null, true, stockID);
            sup.setVisible(true);
              StockController.loadAllStockDetail(allStocksDtm);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnOtherreturnActionPerformed

    private void btnDIMOReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDIMOReturnActionPerformed
            try {
            int selectedRow = tblAllStock.getSelectedRow();
            int stockID = Integer.parseInt(tblAllStock.getValueAt(selectedRow, 0).toString());
            
             DIMOPartsStockReturns sup = new DIMOPartsStockReturns(null, true, stockID);
             sup.setVisible(true);
              StockController.loadAllStockDetail(allStocksDtm);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }         // TODO add your handling code here:
    }//GEN-LAST:event_btnDIMOReturnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNewSupplier;
    private javax.swing.JButton btnAddNewSupplier1;
    private javax.swing.JButton btnAddStock;
    private javax.swing.JButton btnAddStock1;
    private javax.swing.JButton btnAddToTable;
    private javax.swing.JButton btnAddToTable1;
    private javax.swing.JButton btnDIMOReturn;
    private javax.swing.JButton btnOtherreturn;
    private javax.swing.JButton btnRemoveAllInTable;
    private javax.swing.JButton btnRemoveFromTable;
    private javax.swing.JButton btnRemoveFromTable1;
    private javax.swing.JButton btnUpdateStock;
    private javax.swing.JComboBox cmbAllSuppliers;
    private javax.swing.JComboBox cmbUpdateStockSupplier;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblAllStock;
    private javax.swing.JTable tblStockDetails;
    private javax.swing.JTable tblStockItems;
    private javax.swing.JTable tblUpdateStock;
    private javax.swing.JTextField txtSearchSupplierName;
    // End of variables declaration//GEN-END:variables
}
