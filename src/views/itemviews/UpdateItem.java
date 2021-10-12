/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views.itemviews;

import controllers.BrandController;
import controllers.ItemController;
import controllers.SearchCategoryController;
import controllers.SupplierController;
import controllers.VehicleModelController;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Brand;
import models.Item;
import models.SearchCategory;
import models.Supplier;
import models.VehicleModel;
import utilities_new.ValidateValues;
import views.brandviews.NewBrand;
import views.searchKey.NewSearchKey;
import views.supplierviews.NewSupplier;
import views.vehicleviews.NewVehicleModel;

/**
 *
 * @author SHdinesh Madushanka
 */
public class UpdateItem extends javax.swing.JDialog {

    private DefaultTableModel updatetemDtm;
    private int itemID;
    private String itemImage = "No Image";

    /**
     * Creates new form NewItem
     */
    public UpdateItem(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public UpdateItem(java.awt.Frame parent, boolean modal, int selectedItem) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        //setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        updatetemDtm = (DefaultTableModel) tblUpdateItems.getModel();

        this.itemID = selectedItem;
        // this.itemDtm = itemDtm;
        checkButtons();
        try {
            fillBrandNames();
            fillPartTypes();
            fillSearchKeys();
            fillSupplierNames();
            fillFieldsToUpdate(selectedItem);

            int brandID = ItemController.getBrandForPart(itemID);
            if (brandID == 1) {
                txtUpdateReOrderQty.setEditable(false);
            } else {
                txtUpdateReOrderQty.setEditable(true);
            }
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }

    public void addNewItemsToTable() throws ClassNotFoundException, SQLException, NullPointerException {
        Brand itemBrandName = ((Brand) cmbItemBrand.getSelectedItem());

        String itemName = txtItemName.getText();
        String image = itemImage;
        String rackNo = txtUpdateRackNo.getText();

        String buyingPrize = txtUpdateBuyingPrice.getText();
        String sellingPrize = txtUpdateSellingPrice.getText();
        String itemDesc = "-";
        itemDesc = txtUpdateItemDesc.getText();
        String reOrderQty = txtUpdateReOrderQty.getText();
        VehicleModel vmoeModel = ((VehicleModel) cmbPartType.getSelectedItem());//== null ? "" : ((String) cmbPartType.getSelectedItem());
        Supplier supplier = ((Supplier) cmbSupplier.getSelectedItem());//== null ? "" : ((String) cmbSupplier.getSelectedItem());
        String remarks = txtUpdateRemarks.getText();
        String commision=txtUpdateCommi.getText();
        SearchCategory searchCategory = ((SearchCategory) cmbSearchCategory.getSelectedItem());
        Object obj[] = new Object[13];
        if (itemBrandName == null) {
            JOptionPane.showMessageDialog(this, "You must select an item brand for item", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else if (vmoeModel == null) {
            JOptionPane.showMessageDialog(this, "You must select a type for item", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else if (searchCategory == null) {
            JOptionPane.showMessageDialog(this, "You must select a search category for item", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else if (supplier == null) {
            JOptionPane.showMessageDialog(this, "You must select a supplier for item", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else if (itemName == null || itemName == "" || itemName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You enter a name for item", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            obj[0] = itemBrandName;
            obj[1] = itemName;
            obj[2] = vmoeModel;
            obj[3] = buyingPrize;
            obj[4] = sellingPrize;
            obj[5] = supplier;
            obj[6] = searchCategory;
            obj[7] = reOrderQty;
            obj[8] = itemDesc;
            obj[9] = remarks;
            obj[10]=commision;
            obj[11] = image;
            obj[12] = rackNo;
            updatetemDtm.addRow(obj);
            clearItemFields();
            checkButtons();
            this.itemImage = "No Image";
            lblUpdateImage.setIcon(null);
        }
    }

    public void clearItemFields() {
        cmbItemBrand.setSelectedIndex(-1);
        cmbPartType.setSelectedIndex(-1);
        cmbSearchCategory.setSelectedIndex(-1);
        cmbSupplier.setSelectedIndex(-1);
        txtItemName.setText("");
        txtUpdateBuyingPrice.setText("");
        txtUpdateItemDesc.setText("");
        txtUpdateReOrderQty.setText("");
        txtUpdateRemarks.setText("");
        txtUpdateSellingPrice.setText("");
        txtUpdateCommi.setText("");
        txtUpdateRackNo.setText("");
    }

    public void checkButtons() {
        int status = -1;
        status = tblUpdateItems.getRowCount();
        if (status > 0) {
            btnRemoveItem.setEnabled(true);
            btnRemoveAllItems.setEnabled(true);
            btnUpdate.setEnabled(true);
        } else {
            btnRemoveItem.setEnabled(false);
            btnRemoveAllItems.setEnabled(false);
            btnUpdate.setEnabled(false);

        }
    }

    public void fillBrandNames() throws ClassNotFoundException, SQLException {
        ResultSet rstItemBrand = BrandController.getAllBrandNames();
        cmbItemBrand.removeAllItems();

        while (rstItemBrand.next()) {
            Brand br = new Brand();
            br.setBrandID(rstItemBrand.getInt(1));
            br.setBrandName(rstItemBrand.getString(2));
            cmbItemBrand.addItem(br);

        }
    }

    public void fillPartTypes() throws ClassNotFoundException, SQLException {
        ResultSet rstItemTypes = VehicleModelController.getModelDetails("status", (1 + ""));
        cmbPartType.removeAllItems();
        while (rstItemTypes.next()) {
            VehicleModel vm = new VehicleModel();
            vm.setModelName(rstItemTypes.getString(2));
            vm.setModelID(rstItemTypes.getInt(1));
            cmbPartType.addItem(vm);
        }
    }

    public void fillSupplierNames() throws ClassNotFoundException, SQLException {
        ResultSet rstSupplier = SupplierController.loadAllSuppliers();
        cmbSupplier.removeAllItems();
        while (rstSupplier.next()) {
            Supplier supplier = new Supplier();
            supplier.setSupplierID(rstSupplier.getInt(1));
            supplier.setSupplierName(rstSupplier.getString(2));
            cmbSupplier.addItem(supplier);
        }
    }

    public void fillSearchKeys() throws ClassNotFoundException, SQLException {
        ResultSet rstSearchCategories = SearchCategoryController.getAllSearchCategories();
        cmbSearchCategory.removeAllItems();
        while (rstSearchCategories.next()) {
            SearchCategory sc = new SearchCategory();
            sc.setCategoryID(rstSearchCategories.getInt(1));
            sc.setCategoryName(rstSearchCategories.getString(2));
            cmbSearchCategory.addItem(sc);
        }
    }

    public void removeTableItems() {
        int selectedRow[] = tblUpdateItems.getSelectedRows();
        while (selectedRow.length > 0) {
            int option = JOptionPane.showConfirmDialog(this, "Do u want to remove  this item...?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == 0) {
                updatetemDtm.removeRow(selectedRow[0]);
                selectedRow = tblUpdateItems.getSelectedRows();
            } else {
                tblUpdateItems.clearSelection();
                break;

            }
            checkButtons();

        }
    }

    public void removeAllTableItems() {
        int allRows = tblUpdateItems.getRowCount();
        int option = JOptionPane.showConfirmDialog(this, "Do u want to remove  these all item...?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == 0) {
            while (allRows > 0) {
                updatetemDtm.removeRow(0);
                allRows = tblUpdateItems.getRowCount();
                checkButtons();
            }
        }

    }

    public void updateAllTableItemsInDB() {
        int rowCount = tblUpdateItems.getRowCount();
        Item itemModel = null;
        ArrayList<Integer> addedItems = new ArrayList();
        int option = JOptionPane.showConfirmDialog(this, "Do you want to update all these items to the database..?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == 0) {
            for (int i = 0; i < rowCount; i++) {
                try {

                    int itemBrandID = ((Brand) tblUpdateItems.getValueAt(i, 0)).getBrandID();//BrandController.getBrandIDforBrandName(((String) tblItems.getValueAt(i, 0)).trim());
                    int modelID = ((VehicleModel) tblUpdateItems.getValueAt(i, 2)).getModelID(); //PartTypeController.getPartTypeIDForName(((String) tblItems.getValueAt(i, 2)).trim());
                    int keyID = ((SearchCategory) tblUpdateItems.getValueAt(i, 6)).getCategoryID();//SearchCategoryController.getCategoryIDForName(((String) tblItems.getValueAt(i, 6)).trim());
                    int supplierID = ((Supplier) tblUpdateItems.getValueAt(i, 5)).getSupplierID();
                    String itemName = (String) tblUpdateItems.getValueAt(i, 1);

                    String buyingPrice = (String) tblUpdateItems.getValueAt(i, 3);
                    double doubleBuyingPrize = 0.0;
                    if (buyingPrice.equals("") || buyingPrice.equals(null)) {
                        doubleBuyingPrize = 0.0;
                    } else {
                        doubleBuyingPrize = Double.parseDouble(buyingPrice);
                    }

                    String sellingPrice = (String) tblUpdateItems.getValueAt(i, 4);

                    double doubleSellingPrize = 0.0;
                    if (sellingPrice.equals("") || sellingPrice.equals(null)) {
                        doubleSellingPrize = 0.0;
                    } else {
                        doubleSellingPrize = Double.parseDouble(sellingPrice);
                    }
                    
                    
                     String commision = (String) tblUpdateItems.getValueAt(i, 10);
                    double doublecommision = 0;
                    if (commision.equals("") || commision.equals(null)) {
                        doublecommision = 0;
                    } else {
                        doublecommision= Double.parseDouble(commision);
                    }

                    String searchKey = (String) tblUpdateItems.getValueAt(i, 6).toString();
                    String stringReOrderQty = tblUpdateItems.getValueAt(i, 7).toString();
                    double reOrderQty = 0.00;
                    if (stringReOrderQty.equals("") || stringReOrderQty.equals(null)) {
                        reOrderQty = 0.0;
                    } else {
                        reOrderQty = Double.parseDouble(stringReOrderQty);
                    }
                    String itemDesc = (String) tblUpdateItems.getValueAt(i, 8);
                    String remarks = tblUpdateItems.getValueAt(i, 9).toString();

                    int status = 1;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String addedDate = sdf.format(new Date());
                    String image = (String) tblUpdateItems.getValueAt(i, 11);
                    String rackNo = tblUpdateItems.getValueAt(i, 12).toString();
                    Item item = new Item(itemID, itemBrandID, itemName, doubleBuyingPrize, doubleSellingPrize, image, itemDesc, remarks,doublecommision, reOrderQty, keyID, supplierID, modelID, rackNo);

                    addedItems.add(ItemController.updateItem(item));
                } catch (Exception ex) {
                }

            }
            if (addedItems.size() == rowCount) {
                JOptionPane.showMessageDialog(this, "All the items has successfully updated", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                int allRows = tblUpdateItems.getRowCount();

                updatetemDtm.setRowCount(0);
                checkButtons();
            } else {
            }

        }
    }

    public void fillFieldsToUpdate(int selectedItem) {
        System.out.println(selectedItem);
        try {

            ResultSet rst = ItemController.getAllPartDataToSelect(selectedItem);
            Brand br = new Brand();
            VehicleModel vm = new VehicleModel();
            SearchCategory sc = new SearchCategory();
            Supplier supplier = new Supplier();
            while (rst.next()) {
                txtItemName.setText(rst.getString(3));
                br.setBrandID(rst.getInt(2));
                br.setBrandName(rst.getString(17));
                vm.setModelID(rst.getInt(14));
                vm.setModelName(rst.getString(19));
                sc.setCategoryID(rst.getInt(12));
                sc.setCategoryName(rst.getString(18));
                supplier.setSupplierID(rst.getInt(13));
                supplier.setSupplierName(rst.getString(20));
                cmbItemBrand.setSelectedItem(br);
                cmbPartType.setSelectedItem(vm);
                cmbSearchCategory.setSelectedItem(sc);
                cmbSupplier.setSelectedItem(supplier);
                txtUpdateBuyingPrice.setText(rst.getString(4));
                txtUpdateSellingPrice.setText(rst.getString(5));
                txtUpdateItemDesc.setText(rst.getString(8));
                txtUpdateReOrderQty.setText(rst.getString(11));
                txtUpdateCommi.setText(rst.getString(10));
                txtUpdateRemarks.setText(rst.getString(9));
                txtUpdateRackNo.setText(rst.getString(15));
                String img = rst.getString(7);
                itemImage = img;
                BufferedImage bi = ImageIO.read(new File(img));

                Image preview = bi.getScaledInstance(lblUpdateImage.getWidth(), lblUpdateImage.getHeight(), Image.SCALE_SMOOTH);
                lblUpdateImage.removeAll();
                lblUpdateImage.setIcon(new ImageIcon(preview));
            }

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException io) {
            lblUpdateImage.setText("No image found");
            itemImage = "No Image";
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageChooser = new javax.swing.JFileChooser();
        jPanel5 = new javax.swing.JPanel();
        btnUpdate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnAddItemToTable = new javax.swing.JButton();
        btnRemoveAllItems = new javax.swing.JButton();
        btnRemoveItem = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cmbItemBrand = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        txtItemName = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnAddImage = new javax.swing.JButton();
        btnNewBrand = new javax.swing.JButton();
        lblUpdateImage = new javax.swing.JLabel();
        btnRemoveImage = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtUpdateReOrderQty = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        cmbSearchCategory = new javax.swing.JComboBox();
        txtUpdateItemDesc = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtUpdateRemarks = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        cmbPartType = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        cmbSupplier = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        txtUpdateBuyingPrice = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtUpdateSellingPrice = new javax.swing.JTextField();
        btnNewType = new javax.swing.JButton();
        btnNewSupplier = new javax.swing.JButton();
        btnNewSearchKey = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        txtUpdateRackNo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtUpdateCommi = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUpdateItems = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Item");
        setResizable(false);

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancel, btnUpdate});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        btnAddItemToTable.setText("Add");
        btnAddItemToTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemToTableActionPerformed(evt);
            }
        });
        btnAddItemToTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAddItemToTableKeyPressed(evt);
            }
        });

        btnRemoveAllItems.setText("Remove All");
        btnRemoveAllItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAllItemsActionPerformed(evt);
            }
        });
        btnRemoveAllItems.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnRemoveAllItemsKeyPressed(evt);
            }
        });

        btnRemoveItem.setText("Remove");
        btnRemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveItemActionPerformed(evt);
            }
        });
        btnRemoveItem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnRemoveItemKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addComponent(btnAddItemToTable, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoveItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoveAllItems, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddItemToTable, btnRemoveItem});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnAddItemToTable, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnRemoveItem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnRemoveAllItems, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddItemToTable, btnRemoveAllItems, btnRemoveItem});

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true)));
        jPanel4.setMaximumSize(new java.awt.Dimension(821, 181));
        jPanel4.setMinimumSize(new java.awt.Dimension(821, 181));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Item Brand :");

        cmbItemBrand.setEditable(true);
        cmbItemBrand.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbItemBrand.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbItemBrandPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbItemBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbItemBrandActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Part No.:");

        txtItemName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtItemName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItemNameActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Description :");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel14.setText("Image:");

        btnAddImage.setText("Add Image");
        btnAddImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddImageActionPerformed(evt);
            }
        });

        btnNewBrand.setText("+");
        btnNewBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewBrandActionPerformed(evt);
            }
        });

        lblUpdateImage.setBorder(javax.swing.BorderFactory.createTitledBorder("Preview"));

        btnRemoveImage.setText("X");
        btnRemoveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveImageActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Re-Order Qty:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel16.setText("Category:");

        cmbSearchCategory.setEditable(true);
        cmbSearchCategory.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbSearchCategoryPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbSearchCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSearchCategoryActionPerformed(evt);
            }
        });

        txtUpdateItemDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUpdateItemDescActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel17.setText("Remarks:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Model:");

        cmbPartType.setEditable(true);
        cmbPartType.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbPartType.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbPartTypePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbPartType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPartTypeActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("Supplier:");

        cmbSupplier.setEditable(true);
        cmbSupplier.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbSupplierPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSupplierActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("Buying Price :");

        txtUpdateBuyingPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUpdateBuyingPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUpdateBuyingPriceActionPerformed(evt);
            }
        });
        txtUpdateBuyingPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUpdateBuyingPriceKeyReleased(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setText("Selling Price :");

        txtUpdateSellingPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtUpdateSellingPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUpdateSellingPriceActionPerformed(evt);
            }
        });
        txtUpdateSellingPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUpdateSellingPriceKeyReleased(evt);
            }
        });

        btnNewType.setText("+");
        btnNewType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewTypeActionPerformed(evt);
            }
        });

        btnNewSupplier.setText("+");
        btnNewSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewSupplierActionPerformed(evt);
            }
        });

        btnNewSearchKey.setText("+");
        btnNewSearchKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewSearchKeyActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel22.setText("Rack No.");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Commision(%) :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUpdateItemDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                            .addComponent(cmbSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbPartType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbItemBrand, 0, 178, Short.MAX_VALUE)
                            .addComponent(cmbSearchCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNewBrand)
                            .addComponent(btnNewType)
                            .addComponent(btnNewSearchKey)
                            .addComponent(btnNewSupplier))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                    .addComponent(jLabel20)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(35, 35, 35)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtUpdateSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUpdateBuyingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUpdateReOrderQty, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUpdateRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtUpdateRackNo, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUpdateCommi, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUpdateImage, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddImage, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveImage)))
                .addGap(4, 4, 4))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtItemName, txtUpdateBuyingPrice, txtUpdateReOrderQty, txtUpdateRemarks, txtUpdateSellingPrice});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel13, jLabel16, jLabel18, jLabel19, jLabel6});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddImage)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemoveImage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUpdateImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(cmbItemBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cmbPartType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbSearchCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtUpdateItemDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNewBrand)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUpdateBuyingPrice))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNewType)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUpdateSellingPrice))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNewSupplier)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtUpdateReOrderQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNewSearchKey)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUpdateRemarks))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUpdateCommi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUpdateRackNo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtItemName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtItemName, txtUpdateBuyingPrice, txtUpdateReOrderQty, txtUpdateRemarks, txtUpdateSellingPrice});

        tblUpdateItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Brand", "Part No.", "Model", "Buying Price", "Selling Price", "Supplier", "Category", "Re-Order Qty.", "Description", "Remarks", "Commision", "Image", "Rack No"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true, true, false, false, false, true, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUpdateItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblUpdateItemsMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblUpdateItems);
        if (tblUpdateItems.getColumnModel().getColumnCount() > 0) {
            tblUpdateItems.getColumnModel().getColumn(11).setMinWidth(0);
            tblUpdateItems.getColumnModel().getColumn(11).setPreferredWidth(0);
            tblUpdateItems.getColumnModel().getColumn(11).setMaxWidth(0);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 608, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddItemToTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemToTableActionPerformed
        try {
            addNewItemsToTable();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnAddItemToTableActionPerformed

    private void btnAddItemToTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddItemToTableKeyPressed
//        int keyCode = evt.getKeyCode();
//        if (keyCode == KeyEvent.VK_ENTER) {
//            addNewItemsToTable();
//        } else if (keyCode == KeyEvent.VK_RIGHT) {
//            btnRemoveItem.requestFocus();
//        }
    }//GEN-LAST:event_btnAddItemToTableKeyPressed

    private void btnRemoveAllItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAllItemsActionPerformed
        removeAllTableItems();
    }//GEN-LAST:event_btnRemoveAllItemsActionPerformed

    private void btnRemoveAllItemsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnRemoveAllItemsKeyPressed
//        int keyCode = evt.getKeyCode();
//        if (keyCode == KeyEvent.VK_ENTER) {
//            removeAllTableItems();
//        } else if (keyCode == KeyEvent.VK_LEFT) {
//            btnRemoveItem.requestFocus();
//        }
    }//GEN-LAST:event_btnRemoveAllItemsKeyPressed

    private void btnRemoveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveItemActionPerformed
        removeTableItems();
    }//GEN-LAST:event_btnRemoveItemActionPerformed

    private void btnRemoveItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnRemoveItemKeyPressed
//        int keyCode = evt.getKeyCode();
//        if (keyCode == KeyEvent.VK_ENTER) {
//            removeTableItems();
//        } else if (keyCode == KeyEvent.VK_LEFT) {
//            btnAddItemToTable.requestFocus();
//        } else if (keyCode == KeyEvent.VK_RIGHT) {
//            btnRemoveAllItems.requestFocus();
//        }
    }//GEN-LAST:event_btnRemoveItemKeyPressed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updateAllTableItemsInDB();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void cmbItemBrandPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbItemBrandPopupMenuWillBecomeInvisible

        //        int rowCount = tblAllItems.getRowCount();
        //        if (rowCount == 0) {
        //            generateItemModelNumber();
        //        } else {
        //            String lastID = ((String) tblAllItems.getValueAt(rowCount - 1, 3)).trim();
        //            generateTableItemModelNumber(lastID);
        //        }
        //        txtItemName.requestFocus();
    }//GEN-LAST:event_cmbItemBrandPopupMenuWillBecomeInvisible

    private void txtItemNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemNameActionPerformed
btnAddItemToTable.requestFocus();        //txtSerialNo.requestFocus();
    }//GEN-LAST:event_txtItemNameActionPerformed

    private void btnAddImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddImageActionPerformed
        int value = imageChooser.showOpenDialog(this);
        if (value == JFileChooser.APPROVE_OPTION) {
            File file = imageChooser.getSelectedFile();
            itemImage = file.getAbsolutePath().replace("\\", "/");
            BufferedImage bImage = null;
            try {
                bImage = ImageIO.read(new File(itemImage));
                Image dimg = bImage.getScaledInstance(lblUpdateImage.getWidth(), lblUpdateImage.getHeight(), Image.SCALE_SMOOTH);

                lblUpdateImage.setIcon(new javax.swing.ImageIcon(dimg));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException npe) {
                itemImage = "No Image";
                JOptionPane.showMessageDialog(this, "No Image selected..", "Wrong selection", JOptionPane.WARNING_MESSAGE);
            }

        }
    }//GEN-LAST:event_btnAddImageActionPerformed

    private void btnNewBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewBrandActionPerformed
        NewBrand newBrand = new NewBrand(this, true);
        newBrand.setVisible(true);
        try {
            fillBrandNames();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnNewBrandActionPerformed

    private void btnRemoveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveImageActionPerformed
        lblUpdateImage.setIcon(null);
        lblUpdateImage.setText("No Image");
        itemImage = "No Image";
    }//GEN-LAST:event_btnRemoveImageActionPerformed

    private void cmbSearchCategoryPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbSearchCategoryPopupMenuWillBecomeInvisible
        //        String selectedItem = cmbSearchCategory.getSelectedItem().toString().trim();
        //        if (selectedItem.equals("Select")) {
        //            //txtItemSearchKey.setEditable(true);
        //        } else {
        //            //txtItemSearchKey.setEditable(false);
        //        }
    }//GEN-LAST:event_cmbSearchCategoryPopupMenuWillBecomeInvisible

    private void cmbPartTypePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbPartTypePopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPartTypePopupMenuWillBecomeInvisible

    private void cmbSupplierPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbSupplierPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSupplierPopupMenuWillBecomeInvisible

    private void txtUpdateBuyingPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUpdateBuyingPriceActionPerformed
        txtUpdateSellingPrice.requestFocus();
    }//GEN-LAST:event_txtUpdateBuyingPriceActionPerformed

    private void txtUpdateBuyingPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUpdateBuyingPriceKeyReleased
        ValidateValues.validateTextFieldForNumber(txtUpdateBuyingPrice);
    }//GEN-LAST:event_txtUpdateBuyingPriceKeyReleased

    private void txtUpdateSellingPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUpdateSellingPriceActionPerformed
        //txtItemQty.requestFocus();
    }//GEN-LAST:event_txtUpdateSellingPriceActionPerformed

    private void txtUpdateSellingPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUpdateSellingPriceKeyReleased
        ValidateValues.validateTextFieldForNumber(txtUpdateSellingPrice);
    }//GEN-LAST:event_txtUpdateSellingPriceKeyReleased

    private void btnNewTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTypeActionPerformed
        try {
            NewVehicleModel vModel = new NewVehicleModel(new JFrame(), true);
            vModel.setVisible(true);
            fillPartTypes();
        } catch (HeadlessException headlessException) {
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }
    }//GEN-LAST:event_btnNewTypeActionPerformed

    private void btnNewSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewSupplierActionPerformed
        try {
            new NewSupplier(null, true).setVisible(true);
            fillSupplierNames();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnNewSupplierActionPerformed

    private void btnNewSearchKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewSearchKeyActionPerformed
        try {
            NewSearchKey searchKey = new NewSearchKey(new JFrame(), true);
            searchKey.setVisible(true);
            fillSearchKeys();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnNewSearchKeyActionPerformed

    private void tblUpdateItemsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUpdateItemsMousePressed
        checkButtons();
    }//GEN-LAST:event_tblUpdateItemsMousePressed

    private void cmbItemBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbItemBrandActionPerformed
cmbPartType.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_cmbItemBrandActionPerformed

    private void cmbPartTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPartTypeActionPerformed
cmbSupplier.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPartTypeActionPerformed

    private void cmbSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSupplierActionPerformed
cmbSearchCategory.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSupplierActionPerformed

    private void cmbSearchCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSearchCategoryActionPerformed
txtUpdateItemDesc.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSearchCategoryActionPerformed

    private void txtUpdateItemDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUpdateItemDescActionPerformed
txtItemName.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtUpdateItemDescActionPerformed

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
                UpdateItem dialog = new UpdateItem(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAddImage;
    private javax.swing.JButton btnAddItemToTable;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnNewBrand;
    private javax.swing.JButton btnNewSearchKey;
    private javax.swing.JButton btnNewSupplier;
    private javax.swing.JButton btnNewType;
    private javax.swing.JButton btnRemoveAllItems;
    private javax.swing.JButton btnRemoveImage;
    private javax.swing.JButton btnRemoveItem;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cmbItemBrand;
    private javax.swing.JComboBox cmbPartType;
    private javax.swing.JComboBox cmbSearchCategory;
    private javax.swing.JComboBox cmbSupplier;
    private javax.swing.JFileChooser imageChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblUpdateImage;
    private javax.swing.JTable tblUpdateItems;
    private javax.swing.JTextField txtItemName;
    private javax.swing.JTextField txtUpdateBuyingPrice;
    private javax.swing.JTextField txtUpdateCommi;
    private javax.swing.JTextField txtUpdateItemDesc;
    private javax.swing.JTextField txtUpdateRackNo;
    private javax.swing.JTextField txtUpdateReOrderQty;
    private javax.swing.JTextField txtUpdateRemarks;
    private javax.swing.JTextField txtUpdateSellingPrice;
    // End of variables declaration//GEN-END:variables
}
