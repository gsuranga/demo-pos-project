/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.salesorderviws;

import controllers.ItemController;
import controllers.SearchCategoryController;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.junit.runner.manipulation.Sorter;
import utilities_new.Combosearch;
import views.itemviews.NewItem;

/**
 *
 * @author SHDINESH
 */
public class SelectPart extends javax.swing.JDialog {

    private DefaultTableModel selectedPartsDtm, selectPartsDtm;
    private SalesOrderPanel mainPanel;
    private Combosearch keySearch;
    private boolean isForUpdate = false;

    /**
     * Creates new form SelectPart
     */
    public SelectPart(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    //
    public SelectPart(java.awt.Frame parent, boolean modal, SalesOrderPanel soPanel, DefaultTableModel dtm) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.selectedPartsDtm = dtm;
        this.mainPanel = soPanel;
        try {
            selectPartsDtm = (DefaultTableModel) tblSelectItem.getModel();
            loadAllPartDataToSelect(selectPartsDtm);
            SearchCategoryController.fillSearchKeys(cmbSearchKeys);
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }
        keySearch = new Combosearch();
        keySearch.setSearchableCombo(cmbSearchKeys, true, "Search Key is not registered");
        txtPartSearch.requestFocus();
    }

    public SelectPart(java.awt.Frame parent, boolean modal, DefaultTableModel dtm, boolean isForUpdate) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.selectedPartsDtm = dtm;
        this.isForUpdate = isForUpdate;
        try {
            selectPartsDtm = (DefaultTableModel) tblSelectItem.getModel();
            loadAllPartDataToSelect(selectPartsDtm);
            SearchCategoryController.fillSearchKeys(cmbSearchKeys);
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }
        keySearch = new Combosearch();
        keySearch.setSearchableCombo(cmbSearchKeys, true, "Search Key is not registered");
        txtPartSearch.requestFocus();
    }
//#########################################################################
    public void addNewItemsToTable() throws ClassNotFoundException, SQLException {
        //String custName = ((String) cmbSelectCustomer.getSelectedItem());
        int selectedRow = tblSelectItem.getSelectedRow();
        String partNo = tblSelectItem.getValueAt(selectedRow, 2).toString();
        String description = tblSelectItem.getValueAt(selectedRow, 3).toString();
        int itemCode = Integer.parseInt(tblSelectItem.getValueAt(selectedRow, 0).toString());
        String rackNO= tblSelectItem.getValueAt(selectedRow, 17).toString();
        
        double sellingPrice = 0;
        int spMnemonic = sellingPriceGroup.getSelection().getMnemonic();

        switch (spMnemonic) {
            case 49:
                sellingPrice = Double.parseDouble(tblSelectItem.getValueAt(selectedRow, 4).toString());
                break;
            case 50:
                sellingPrice = Double.parseDouble(tblSelectItem.getValueAt(selectedRow, 6).toString());
        }

        double buyingPrice = Double.parseDouble(tblSelectItem.getValueAt(selectedRow, 14).toString());
        double quantity = Integer.parseInt(txtQty.getText().trim());
        int actualQuantity = Integer.parseInt(tblSelectItem.getValueAt(selectedRow, 12).toString());//StockController.getTotalRemainingStockQuantityOfaItem(itemCode);
        double discount = 0.00;
        double amount = 0.00;
        double discountAmount = 0;
        String brand = tblSelectItem.getValueAt(selectedRow, 1).toString();
        String stockID = tblSelectItem.getValueAt(selectedRow, 15).toString();

        //String category = tblSelectItem.getValueAt(selectedRow, 10).toString();
        int categoryID = 0;
        categoryID = Integer.parseInt(tblSelectItem.getValueAt(selectedRow, 13).toString());
        int mnemonic = 0;
        mnemonic = buttonGroup1.getSelection().getMnemonic();
        System.out.println(mnemonic);
        if (mnemonic == 49) {
            discount = Double.parseDouble(txtDiscount.getText());
            discountAmount = ((sellingPrice * quantity) * discount / 100);
            amount = (sellingPrice * quantity) - discountAmount;
        } else if (mnemonic == 50) {
            discount = Double.parseDouble(txtDiscountAmount.getText());
            discountAmount = discount;
            amount = (sellingPrice * quantity) - discount;
        } else {
            discount = 0.00;
            discountAmount = 0.00;
            amount = sellingPrice * quantity;

        }
        if (partNo == null || partNo == "") {
            JOptionPane.showMessageDialog(this, "Please select an item to add", "Error in items", JOptionPane.WARNING_MESSAGE);
        } else if (quantity == 0 || txtQty.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Quantity cannot be empty or 0", "Error in quantity", JOptionPane.WARNING_MESSAGE);
        } else if (actualQuantity < quantity) {
            JOptionPane.showMessageDialog(this, "Quantity is not enough. Remaining " + partNo + " quantity is " + actualQuantity, "Error in quantity", JOptionPane.WARNING_MESSAGE);
        } else {
            Object obj[] = {itemCode, partNo, description, quantity, String.format("%.2f", discountAmount), String.format("%.2f", sellingPrice), String.format("%.2f", amount), brand, categoryID, buyingPrice, String.format("%.2f", discount), stockID,rackNO};
            selectedPartsDtm.addRow(obj);
            clearFields();
            mainPanel.checkButtons();
            mainPanel.checkBillButton();
            mainPanel.calculate();
            dispose();
        }
    }

    public void addNewItemsToTableOnUpdate() throws ClassNotFoundException, SQLException {
        //String custName = ((String) cmbSelectCustomer.getSelectedItem());
        int selectedRow = tblSelectItem.getSelectedRow();
        String partNo = tblSelectItem.getValueAt(selectedRow, 2).toString();
        String description = tblSelectItem.getValueAt(selectedRow, 3).toString();
        int itemCode = Integer.parseInt(tblSelectItem.getValueAt(selectedRow, 0).toString());
        double sellingPrice = 0;
        int spMnemonic = sellingPriceGroup.getSelection().getMnemonic();

        switch (spMnemonic) {
            case 49:
                sellingPrice = Double.parseDouble(tblSelectItem.getValueAt(selectedRow, 4).toString());
                break;
            case 50:
                sellingPrice = Double.parseDouble(tblSelectItem.getValueAt(selectedRow, 6).toString());
        }
        double buyingPrice = Double.parseDouble(tblSelectItem.getValueAt(selectedRow, 14).toString());
        double quantity = Integer.parseInt(txtQty.getText().trim());
        int actualQuantity = Integer.parseInt(tblSelectItem.getValueAt(selectedRow, 12).toString());//StockController.getTotalRemainingStockQuantityOfaItem(itemCode);
        double discount = 0.00;
        double amount = 0.00;
        double discountAmount = 0;
        String brand = tblSelectItem.getValueAt(selectedRow, 1).toString();
        String stockID = tblSelectItem.getValueAt(selectedRow, 15).toString();
        //String category = tblSelectItem.getValueAt(selectedRow, 10).toString();
        int categoryID = 0;
        categoryID = Integer.parseInt(tblSelectItem.getValueAt(selectedRow, 13).toString());
        int mnemonic = 0;
        mnemonic = buttonGroup1.getSelection().getMnemonic();
        System.out.println(mnemonic);
        if (mnemonic == 49) {
            discount = Double.parseDouble(txtDiscount.getText());
            discountAmount = ((sellingPrice * quantity) * discount / 100);
            amount = (sellingPrice * quantity) - discountAmount;
        } else if (mnemonic == 50) {
            discount = Double.parseDouble(txtDiscountAmount.getText());
            discountAmount = discount;
            amount = (sellingPrice * quantity) - discount;
        } else {
            discount = 0.00;
            discountAmount = 0.00;
            amount = sellingPrice * quantity;

        }
        if (partNo == null || partNo == "") {
            JOptionPane.showMessageDialog(this, "Please select an item to add", "Error in items", JOptionPane.WARNING_MESSAGE);
        } else if (quantity == 0 || txtQty.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Quantity cannot be empty or 0", "Error in quantity", JOptionPane.WARNING_MESSAGE);
        } else if (actualQuantity < quantity) {
            JOptionPane.showMessageDialog(this, "Quantity is not enough. Remaining " + partNo + " quantity is " + actualQuantity, "Error in quantity", JOptionPane.WARNING_MESSAGE);
        } else {
            Object obj[] = {0, itemCode, partNo, description, quantity, String.format("%.2f", discountAmount), String.format("%.2f", sellingPrice), String.format("%.2f", amount), brand, categoryID, buyingPrice, String.format("%.2f", discount), stockID, 1, 0};
            selectedPartsDtm.addRow(obj);
            clearFields();
            dispose();
        }
    }

    public void loadAllPartDataToSelect(DefaultTableModel allPartsDtm) throws ClassNotFoundException, SQLException {
        ResultSet data = ItemController.getAllPartDataToSelect();
        String tableRow[] = new String[18];
        int rowCount = allPartsDtm.getRowCount();
        if (rowCount >= 0) {
            allPartsDtm.setRowCount(0);
        }

        while (data.next()) {
            for (int i = 0; i < 18; i++) {
                if (i == 18) {
                    tableRow[i] = "All";
                } else {
                    tableRow[i] = data.getString(i + 1);
                }

            }
            allPartsDtm.addRow(tableRow);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        sellingPriceGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        cmbSearchKeys = new javax.swing.JComboBox();
        txtPartSearch = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSelectItem = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        txtQty = new javax.swing.JTextField();
        lblQty = new javax.swing.JLabel();
        lblDiscount1 = new javax.swing.JLabel();
        rdbPercentage = new javax.swing.JRadioButton();
        txtDiscount = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        txtDiscountAmount = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        btnAdd = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("All Parts");
        setResizable(false);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        cmbSearchKeys.setEditable(true);
        cmbSearchKeys.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbSearchKeysPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbSearchKeys.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cmbSearchKeysKeyReleased(evt);
            }
        });

        txtPartSearch.setText("Search...");
        txtPartSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPartSearchActionPerformed(evt);
            }
        });
        txtPartSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPartSearchFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPartSearchFocusGained(evt);
            }
        });
        txtPartSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPartSearchKeyReleased(evt);
            }
        });

        jButton3.setText("New Part");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Search Category :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Part No. :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSearchKeys, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPartSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbSearchKeys, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPartSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3)
                        .addComponent(jLabel3))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbSearchKeys, jLabel2, jLabel3, txtPartSearch});

        tblSelectItem.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        tblSelectItem.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblSelectItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "part id", "Brand", "Part No.", "Description", "Previous  Price", "Current Old  Price", "Current  Price", "Average Cost %", "Category", "Model", "Supplier", "Re Order Qty", "Remaining Qty", "CategoryID", "Buying Price", "StockID", "Commision", "Rack No.", "All"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSelectItem.setRowHeight(20);
        tblSelectItem.setRowMargin(2);
        tblSelectItem.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblSelectItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblSelectItemKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblSelectItem);
        if (tblSelectItem.getColumnModel().getColumnCount() > 0) {
            tblSelectItem.getColumnModel().getColumn(0).setMinWidth(0);
            tblSelectItem.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblSelectItem.getColumnModel().getColumn(0).setMaxWidth(0);
            tblSelectItem.getColumnModel().getColumn(2).setMinWidth(110);
            tblSelectItem.getColumnModel().getColumn(2).setPreferredWidth(110);
            tblSelectItem.getColumnModel().getColumn(2).setMaxWidth(110);
            tblSelectItem.getColumnModel().getColumn(3).setMinWidth(100);
            tblSelectItem.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblSelectItem.getColumnModel().getColumn(3).setMaxWidth(100);
            tblSelectItem.getColumnModel().getColumn(5).setMinWidth(0);
            tblSelectItem.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblSelectItem.getColumnModel().getColumn(5).setMaxWidth(0);
            tblSelectItem.getColumnModel().getColumn(6).setMinWidth(100);
            tblSelectItem.getColumnModel().getColumn(6).setPreferredWidth(100);
            tblSelectItem.getColumnModel().getColumn(6).setMaxWidth(100);
            tblSelectItem.getColumnModel().getColumn(7).setMinWidth(110);
            tblSelectItem.getColumnModel().getColumn(7).setPreferredWidth(110);
            tblSelectItem.getColumnModel().getColumn(7).setMaxWidth(110);
            tblSelectItem.getColumnModel().getColumn(13).setMinWidth(0);
            tblSelectItem.getColumnModel().getColumn(13).setPreferredWidth(0);
            tblSelectItem.getColumnModel().getColumn(13).setMaxWidth(0);
            tblSelectItem.getColumnModel().getColumn(14).setMinWidth(0);
            tblSelectItem.getColumnModel().getColumn(14).setPreferredWidth(0);
            tblSelectItem.getColumnModel().getColumn(14).setMaxWidth(0);
            tblSelectItem.getColumnModel().getColumn(15).setMinWidth(0);
            tblSelectItem.getColumnModel().getColumn(15).setPreferredWidth(0);
            tblSelectItem.getColumnModel().getColumn(15).setMaxWidth(0);
            tblSelectItem.getColumnModel().getColumn(18).setMinWidth(0);
            tblSelectItem.getColumnModel().getColumn(18).setPreferredWidth(0);
            tblSelectItem.getColumnModel().getColumn(18).setMaxWidth(0);
        }

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtQty.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtQty.setText("0");
        txtQty.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQtyFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQtyFocusLost(evt);
            }
        });
        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });
        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtyKeyReleased(evt);
            }
        });

        lblQty.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lblQty.setText("Quantity :");

        lblDiscount1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        lblDiscount1.setText("Discount :");

        buttonGroup1.add(rdbPercentage);
        rdbPercentage.setMnemonic('1');
        rdbPercentage.setSelected(true);
        rdbPercentage.setText("%");
        rdbPercentage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbPercentageActionPerformed(evt);
            }
        });

        txtDiscount.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtDiscount.setText("0.00");
        txtDiscount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiscountFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiscountFocusLost(evt);
            }
        });
        txtDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountActionPerformed(evt);
            }
        });
        txtDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscountKeyReleased(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setMnemonic('2');
        jRadioButton1.setText("Amount");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        txtDiscountAmount.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtDiscountAmount.setText("0");
        txtDiscountAmount.setEnabled(false);
        txtDiscountAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiscountAmountFocusGained(evt);
            }
        });
        txtDiscountAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountAmountActionPerformed(evt);
            }
        });
        txtDiscountAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscountAmountKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel1.setText("Price : ");

        sellingPriceGroup.add(jRadioButton2);
        jRadioButton2.setMnemonic('1');
        jRadioButton2.setSelected(true);
        jRadioButton2.setText("Previous Price");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        sellingPriceGroup.add(jRadioButton3);
        jRadioButton3.setMnemonic('2');
        jRadioButton3.setText("Current Price");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(lblDiscount1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdbPercentage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDiscountAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton2))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(lblQty, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addComponent(jRadioButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtDiscount, txtDiscountAmount});

        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQty, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDiscount1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(rdbPercentage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDiscountAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jRadioButton2)
                        .addComponent(jRadioButton3))
                    .addComponent(jRadioButton1))
                .addGap(15, 15, 15))
        );

        jPanel14Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtDiscount, txtDiscountAmount});

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 822, Short.MAX_VALUE)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed
      txtDiscount.requestFocus();
        //btnAdd.doClick();
    }//GEN-LAST:event_txtQtyActionPerformed

    private void txtQtyFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQtyFocusGained
        txtQty.selectAll();
    }//GEN-LAST:event_txtQtyFocusGained

    private void txtQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyReleased
        int rowCount = tblSelectItem.getRowCount();
        int keyCode = evt.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            tblSelectItem.requestFocus();
            tblSelectItem.changeSelection(0, 0, false, false);
           // txtDiscount.requestFocus();
        }
    }//GEN-LAST:event_txtQtyKeyReleased

    private void rdbPercentageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbPercentageActionPerformed
        txtDiscount.setEnabled(true);
        txtDiscount.selectAll();
        txtDiscountAmount.setText("0");
        txtDiscountAmount.setEnabled(false);
    }//GEN-LAST:event_rdbPercentageActionPerformed

    private void txtDiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscountActionPerformed
        btnAdd.doClick();
       // jRadioButton1.requestFocus();
    }//GEN-LAST:event_txtDiscountActionPerformed

    private void txtDiscountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountFocusGained
        txtDiscount.selectAll();
    }//GEN-LAST:event_txtDiscountFocusGained

    private void txtDiscountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountFocusLost
        String val = txtDiscount.getText();
        if (val.isEmpty() || val.equals(null)) {
            txtDiscount.setText("0");
        }

    }//GEN-LAST:event_txtDiscountFocusLost

    private void txtDiscountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountKeyReleased
        //ValidateValues.validateTextFieldForNumber(txtDiscount);
        if (evt.getKeyCode() == evt.VK_UP) {
            txtQty.requestFocus();
        }
    }//GEN-LAST:event_txtDiscountKeyReleased

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        txtDiscount.setText("0.00");
        txtDiscount.setEnabled(false);
        txtDiscountAmount.setEnabled(true);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void txtDiscountAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscountAmountActionPerformed
        //btnAddToTable.doClick();
    }//GEN-LAST:event_txtDiscountAmountActionPerformed

    private void txtDiscountAmountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountAmountFocusGained
        txtDiscountAmount.selectAll();
    }//GEN-LAST:event_txtDiscountAmountFocusGained

    private void txtDiscountAmountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountAmountKeyReleased
        // ValidateValues.validateTextFieldForNumber(txtDiscountAmount);
        if (evt.getKeyCode() == evt.VK_ENTER) {
            txtQty.requestFocus();
        }
    }//GEN-LAST:event_txtDiscountAmountKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtPartSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPartSearchKeyReleased
        try {
            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(selectPartsDtm);
            tblSelectItem.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + txtPartSearch.getText())));
            int keyCode = evt.getKeyCode();
//if (keyCode == KeyEvent.VK_DOWN) {
            if (keyCode == KeyEvent.VK_ENTER) {
                tblSelectItem.requestFocus();
                tblSelectItem.changeSelection(0, 0, false, false);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtPartSearchKeyReleased

    private void cmbSearchKeysKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbSearchKeysKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSearchKeysKeyReleased

    private void cmbSearchKeysPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbSearchKeysPopupMenuWillBecomeInvisible
        String selectedItem = cmbSearchKeys.getSelectedItem().toString();

        try {

            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(selectPartsDtm);
            tblSelectItem.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + selectedItem)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_cmbSearchKeysPopupMenuWillBecomeInvisible

    private void tblSelectItemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblSelectItemKeyReleased
        int keyCode = evt.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER) {
            tblSelectItem.requestFocus(false);
            tblSelectItem.changeSelection(tblSelectItem.getSelectedRow() - 1, 0, false, false);
            txtQty.requestFocus();
            //txtDiscount.requestFocus();
        }
    }//GEN-LAST:event_tblSelectItemKeyReleased

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
       //
        try {
            if (!isForUpdate) {
                addNewItemsToTable();
            } else if (isForUpdate) {
                addNewItemsToTableOnUpdate();
            }
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            // ex.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            JOptionPane.showMessageDialog(this, "Please enter values again correctly", "Error in values", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            JOptionPane.showMessageDialog(this, "Please enter values again correctly", "Error in values", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtPartSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPartSearchFocusLost
        txtPartSearch.setText("Search...");
        
    }//GEN-LAST:event_txtPartSearchFocusLost

    private void txtPartSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPartSearchFocusGained
        txtPartSearch.setText("");
        
    }//GEN-LAST:event_txtPartSearchFocusGained

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        try {
            NewItem newItem = new NewItem(null, true);
            newItem.setVisible(true);
            loadAllPartDataToSelect(selectPartsDtm);
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtPartSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPartSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPartSearchActionPerformed

    private void txtQtyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQtyFocusLost
       
        String val = txtQty.getText();
        if (val.isEmpty() || val.equals(null)) {
            txtQty.setText("0");
        }
        
    }//GEN-LAST:event_txtQtyFocusLost

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        System.out.println(sellingPriceGroup.getSelection().getMnemonic());
        
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        System.out.println(sellingPriceGroup.getSelection().getMnemonic());
        
    }//GEN-LAST:event_jRadioButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(SelectPart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectPart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectPart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectPart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                SelectPart dialog = new SelectPart(new javax.swing.JFrame(), true);
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbSearchKeys;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDiscount1;
    private javax.swing.JLabel lblQty;
    private javax.swing.JRadioButton rdbPercentage;
    private javax.swing.ButtonGroup sellingPriceGroup;
    private javax.swing.JTable tblSelectItem;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtDiscountAmount;
    private javax.swing.JTextField txtPartSearch;
    private javax.swing.JTextField txtQty;
    // End of variables declaration//GEN-END:variables

    private void clearFields() {
        txtPartSearch.setText("");
        txtQty.setText("0");
        txtDiscount.setText("0.00");
        txtDiscountAmount.setText("0");

    }
}
