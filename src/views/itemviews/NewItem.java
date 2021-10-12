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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Brand;
import models.Item;
import models.PartType;
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
public class NewItem extends javax.swing.JDialog {

    private DefaultTableModel itemDtm;
    private String itemImage = "No Image";
    private ArrayList<Integer> brandIDs;

    /**
     * Creates new form NewItem
     */
    public NewItem(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        //setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.itemDtm = (DefaultTableModel) tblItems.getModel();

        checkButtons();
        try {
            fillBrandNames();
            fillPartTypes();
            fillSupplierNames();
            fillSearchKeys();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
        cmbItemBrand.setSelectedIndex(-1);
        cmbPartType.setSelectedIndex(-1);
        cmbSearchCategory.setSelectedIndex(-1);
        cmbSupplier.setSelectedIndex(-1);
        //cmbItemBrand.requestFocus();
    }

    public void addNewItemsToTable() throws ClassNotFoundException, SQLException, NullPointerException {
        Brand itemBrandName = ((Brand) cmbItemBrand.getSelectedItem());

        String itemName = txtItemName.getText();
        String image = itemImage;
        String rackNo = txtRackNo.getText();
        

        String buyingPrize = txtBuyingPrice.getText();
        String sellingPrize = txtSellingPrice.getText();
        String itemDesc = "-";
        itemDesc = txtItemDesc.getText();
        String reOrderQty = txtReOrderQty.getText();
        VehicleModel type = ((VehicleModel) cmbPartType.getSelectedItem());//== null ? "" : ((String) cmbPartType.getSelectedItem());

        Supplier supplier = ((Supplier) cmbSupplier.getSelectedItem());//== null ? "" : ((String) cmbSupplier.getSelectedItem());
        String remarks = txtRemarks.getText();
        String commision=txt_commi.getText();
        // double commision= Double.parseDouble(txt_commi.getText()); //
        
        SearchCategory searchCategory = ((SearchCategory) cmbSearchCategory.getSelectedItem());
        Object obj[] = new Object[13];

        if (itemBrandName == null) {
            JOptionPane.showMessageDialog(this, "You must select an item brand for item", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else if (type == null) {
            JOptionPane.showMessageDialog(this, "You must select a type for item", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else if (searchCategory == null) {
            JOptionPane.showMessageDialog(this, "You must select a search category for item", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else if (supplier == null) {
            JOptionPane.showMessageDialog(this, "You must select a supplier for item", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else if (itemName == null || itemName == "" || itemName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You enter a name for item", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else if (ItemController.checkItemDuplicate(itemName)) {
            int option = JOptionPane.showConfirmDialog(this, "This Item already exists in database. Do you want to add this as a new item?", "exicts", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (option == 0) {
                obj[0] = itemBrandName;
                obj[1] = itemName;
                obj[2] = type;
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

                itemDtm.addRow(obj);
                clearItemFields();
                checkButtons();
                this.itemImage = "No Image";
                lblImage.setIcon(null);

            } else {
                clearItemFields();
            }
        } else {
            obj[0] = itemBrandName;
            obj[1] = itemName;
            obj[2] = type;
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
            itemDtm.addRow(obj);
            clearItemFields();
            checkButtons();
            this.itemImage = "No Image";
            lblImage.setIcon(null);
        }
    }

    public void clearItemFields() {
        cmbItemBrand.setSelectedIndex(-1);
        txtBuyingPrice.setText("");
        txtItemDesc.setText("");
        txtItemName.setText("");
        txtSellingPrice.setText("");
        cmbSearchCategory.setSelectedIndex(-1);
        cmbPartType.setSelectedIndex(-1);
        cmbSupplier.setSelectedIndex(-1);
        txtRemarks.setText("");
        txt_commi.setText("");
        txtReOrderQty.setText("");
        txtRackNo.setText("");

    }

    public void checkButtons() {
        int status = -1;
        status = tblItems.getRowCount();
        if (status > 0) {
            btnRemoveItem.setEnabled(true);
            btnRemoveAllItems.setEnabled(true);
            btnAddItemsToDB.setEnabled(true);
        } else {
            btnRemoveItem.setEnabled(false);
            btnRemoveAllItems.setEnabled(false);
            btnAddItemsToDB.setEnabled(false);

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
        int selectedRow[] = tblItems.getSelectedRows();
        while (selectedRow.length > 0) {
            int option = JOptionPane.showConfirmDialog(this, "Do u want to remove  this item...?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == 0) {
                itemDtm.removeRow(selectedRow[0]);
                selectedRow = tblItems.getSelectedRows();
            } else {
                tblItems.clearSelection();
                break;

            }
            checkButtons();

        }
    }

    public void removeAllTableItems() {
        int allRows = tblItems.getRowCount();
        int option = JOptionPane.showConfirmDialog(this, "Do u want to remove  these all item...?", "Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == 0) {
            while (allRows > 0) {
                itemDtm.removeRow(0);
                allRows = tblItems.getRowCount();
                checkButtons();
            }
        }

    }

    public void addAllTableItemsToDB() {
        int rowCount = tblItems.getRowCount();
        Item itemModel = null;
        ArrayList<Integer> addedItems = new ArrayList();
        int option = JOptionPane.showConfirmDialog(this, "Do you want to add all these items to the database..?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (option == 0) {
            for (int i = 0; i < rowCount; i++) {
                try {

                    int itemBrandID = ((Brand) tblItems.getValueAt(i, 0)).getBrandID();//BrandController.getBrandIDforBrandName(((String) tblItems.getValueAt(i, 0)).trim());
                    int typeID = ((VehicleModel) tblItems.getValueAt(i, 2)).getModelID(); //PartTypeController.getPartTypeIDForName(((String) tblItems.getValueAt(i, 2)).trim());
                    int keyID = ((SearchCategory) tblItems.getValueAt(i, 6)).getCategoryID();//SearchCategoryController.getCategoryIDForName(((String) tblItems.getValueAt(i, 6)).trim());
                    int supplierID = ((Supplier) tblItems.getValueAt(i, 5)).getSupplierID();
                    String itemName = (String) tblItems.getValueAt(i, 1);

                    String buyingPrice = (String) tblItems.getValueAt(i, 3);
                    double doubleBuyingPrize = 0.0;
                    if (buyingPrice.equals("") || buyingPrice.equals(null)) {
                        doubleBuyingPrize = 0.0;
                    } else {
                        doubleBuyingPrize = Double.parseDouble(buyingPrice);
                    }

                    String sellingPrice = (String) tblItems.getValueAt(i, 4);
                    double doubleSellingPrize = 0.0;
                    if (sellingPrice.equals("") || sellingPrice.equals(null)) {
                        doubleSellingPrize = 0.0;
                    } else {
                        doubleSellingPrize = Double.parseDouble(sellingPrice);
                    }
                    
                    String commision = (String) tblItems.getValueAt(i, 10);
                    double doublecommision = 0;
                    if (commision.equals("") || commision.equals(null)) {
                        doublecommision = 0;
                    } else {
                        doublecommision= Double.parseDouble(commision);
                    }

                    
                    String searchKey = (String) tblItems.getValueAt(i, 6).toString();
                    String stringReOrderQty = tblItems.getValueAt(i, 7).toString();
                    double reOrderQty = 0.00;
                    if (stringReOrderQty.equals("") || stringReOrderQty.equals(null)) {
                        reOrderQty = 0.0;
                    } else {
                        reOrderQty = Double.parseDouble(stringReOrderQty);
                    }
                    String itemDesc = (String) tblItems.getValueAt(i, 8);
                    String remarks = tblItems.getValueAt(i, 9).toString();
                    String rackNo = tblItems.getValueAt(i, 12).toString();

                    int status = 1;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String addedDate = sdf.format(new Date());
                    String image = (String) tblItems.getValueAt(i, 11);
                    Item item = new Item(itemBrandID, itemName, doubleBuyingPrize, doubleSellingPrize, addedDate, image, itemDesc, remarks,doublecommision, reOrderQty, keyID, supplierID, typeID, status, rackNo);

                    addedItems.add(ItemController.addNewItem(item));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            if (addedItems.size() > 0) {
                JOptionPane.showMessageDialog(this, "All the items has successfully added to the database.", "Succeed", JOptionPane.INFORMATION_MESSAGE);
                int allRows = tblItems.getRowCount();
                while (allRows > 0) {
                    itemDtm.removeRow(0);
                    allRows = tblItems.getRowCount();
                }
                checkButtons();

            } else {
            }

        }
    }

    class MyCustomFilter extends javax.swing.filechooser.FileFilter {

        @Override
        public boolean accept(File file) {
            // Allow only directories, or files with ".txt" extension
            return file.isDirectory() || file.getAbsolutePath().endsWith("txt");
        }

        @Override
        public String getDescription() {
            // This description will be displayed in the dialog,
            // hard-coded = ugly, should be done via I18N
            return "Text documents (*.txt)";
        }
    }

    public void fillAlternatives() throws ClassNotFoundException, SQLException {

        ResultSet rstItem = ItemController.getAllPartsForAlternative();
        cmbSearchCategory.removeAllItems();
        cmbSearchCategory.addItem("Select");
        while (rstItem.next()) {
            cmbSearchCategory.addItem(rstItem.getString(1));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        new MyCustomFilter();
        imageChooser = new javax.swing.JFileChooser();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cmbItemBrand = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtItemName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnAddImage = new javax.swing.JButton();
        btnNewBrand = new javax.swing.JButton();
        lblImage = new javax.swing.JLabel();
        btnRemoveImage = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtReOrderQty = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cmbSearchCategory = new javax.swing.JComboBox();
        txtItemDesc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtRemarks = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbPartType = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        cmbSupplier = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        txtBuyingPrice = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtSellingPrice = new javax.swing.JTextField();
        btnNewType = new javax.swing.JButton();
        btnNewSupplier = new javax.swing.JButton();
        btnNewSearchKey = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtRackNo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_commi = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        btnAddItemsToDB = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnAddItemToTable = new javax.swing.JButton();
        btnRemoveAllItems = new javax.swing.JButton();
        btnRemoveItem = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Item");
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true)));
        jPanel3.setMaximumSize(new java.awt.Dimension(821, 181));
        jPanel3.setMinimumSize(new java.awt.Dimension(821, 181));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Item Brand :");

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

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Part No.:");

        txtItemName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtItemName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItemNameActionPerformed(evt);
            }
        });
        txtItemName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtItemNameKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Description :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel3.setText("Image:");

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

        lblImage.setBorder(javax.swing.BorderFactory.createTitledBorder("Preview"));

        btnRemoveImage.setText("X");
        btnRemoveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveImageActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Re-Order Qty:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel1.setText("Category:");

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

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel5.setText("Remarks:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Model :");

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

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Supplier:");

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

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Buying Price :");

        txtBuyingPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtBuyingPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuyingPriceActionPerformed(evt);
            }
        });
        txtBuyingPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuyingPriceKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Selling Price :");

        txtSellingPrice.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSellingPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSellingPriceActionPerformed(evt);
            }
        });
        txtSellingPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSellingPriceKeyReleased(evt);
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

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel12.setText("Rack No.:");

        txtRackNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRackNoActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Sales Man Commision  (%)");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtItemDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbItemBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbSearchCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbPartType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnNewType)
                                .addGap(37, 37, 37)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnNewBrand)
                                        .addGap(37, 37, 37)
                                        .addComponent(jLabel7))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnNewSupplier)
                                            .addComponent(btnNewSearchKey))
                                        .addGap(37, 37, 37)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtRackNo, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSellingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuyingPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtReOrderQty, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRemarks, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_commi, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddImage, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoveImage))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbItemBrand, cmbPartType, cmbSearchCategory, cmbSupplier, txtItemDesc});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtBuyingPrice, txtItemName, txtReOrderQty, txtRemarks, txtSellingPrice});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddImage)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemoveImage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(cmbItemBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cmbPartType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbSearchCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtItemDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNewBrand)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuyingPrice))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNewType)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSellingPrice))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNewSupplier)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtReOrderQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNewSearchKey)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRemarks))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_commi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRackNo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtItemName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbItemBrand, cmbPartType, txtBuyingPrice, txtRemarks});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cmbSearchCategory, cmbSupplier, txtItemName, txtReOrderQty, txtSellingPrice});

        tblItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Brand", "Part No", "Model", "Buying Price", "Selling Price", "Supplier", "Category", "Re-Order Qty", "Description", "Remarks", "Commision", "Image", "Rack No."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true, true, false, false, true, true, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblItemsMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblItems);
        if (tblItems.getColumnModel().getColumnCount() > 0) {
            tblItems.getColumnModel().getColumn(7).setMinWidth(70);
            tblItems.getColumnModel().getColumn(7).setPreferredWidth(70);
            tblItems.getColumnModel().getColumn(7).setMaxWidth(70);
            tblItems.getColumnModel().getColumn(11).setMinWidth(0);
            tblItems.getColumnModel().getColumn(11).setPreferredWidth(0);
            tblItems.getColumnModel().getColumn(11).setMaxWidth(0);
        }

        btnAddItemsToDB.setText("Add");
        btnAddItemsToDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemsToDBActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddItemsToDB, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddItemsToDB, btnCancel});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddItemsToDB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addGap(0, 33, Short.MAX_VALUE)
                .addComponent(btnAddItemToTable, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoveItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoveAllItems))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddItemToTable, btnRemoveAllItems, btnRemoveItem});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoveItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRemoveAllItems)
                    .addComponent(btnAddItemToTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAddItemToTable, btnRemoveAllItems, btnRemoveItem});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(419, 419, 419)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
btnAddItemToTable.requestFocus();       //txtSerialNo.requestFocus();
    }//GEN-LAST:event_txtItemNameActionPerformed

    private void txtBuyingPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuyingPriceActionPerformed
        txtSellingPrice.requestFocus();
    }//GEN-LAST:event_txtBuyingPriceActionPerformed

    private void txtSellingPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSellingPriceActionPerformed
        //txtItemQty.requestFocus();
    }//GEN-LAST:event_txtSellingPriceActionPerformed

    private void btnAddItemToTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemToTableActionPerformed
        try {
            addNewItemsToTable();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NullPointerException npe) {
        } catch (Exception ex) {
            ex.printStackTrace();
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

    private void btnAddItemsToDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemsToDBActionPerformed
        addAllTableItemsToDB();
    }//GEN-LAST:event_btnAddItemsToDBActionPerformed

    private void tblItemsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItemsMousePressed
        checkButtons();
    }//GEN-LAST:event_tblItemsMousePressed

    private void btnAddImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddImageActionPerformed
        int value = imageChooser.showOpenDialog(this);
        if (value == JFileChooser.APPROVE_OPTION) {
            File file = imageChooser.getSelectedFile();
            itemImage = file.getAbsolutePath().replace("\\", "/");
            BufferedImage bImage = null;
            try {
                bImage = ImageIO.read(new File(itemImage));
                Image dimg = bImage.getScaledInstance(lblImage.getWidth(), lblImage.getHeight(), Image.SCALE_SMOOTH);

                lblImage.setIcon(new javax.swing.ImageIcon(dimg));
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

    private void txtBuyingPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuyingPriceKeyReleased
        ValidateValues.validateTextFieldForNumber(txtBuyingPrice);
    }//GEN-LAST:event_txtBuyingPriceKeyReleased

    private void txtSellingPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSellingPriceKeyReleased
        ValidateValues.validateTextFieldForNumber(txtSellingPrice);
    }//GEN-LAST:event_txtSellingPriceKeyReleased

    private void btnRemoveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveImageActionPerformed
        lblImage.setIcon(null);
        lblImage.setText("No Image");
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

    }//GEN-LAST:event_cmbSupplierPopupMenuWillBecomeInvisible

    private void btnNewTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewTypeActionPerformed
        try {
            NewVehicleModel nvm = new NewVehicleModel(new JFrame(), true);
            nvm.setVisible(true);
            fillPartTypes();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
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

    private void txtRackNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRackNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRackNoActionPerformed

    private void txtItemNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemNameKeyTyped
      //  System.out.println((int)evt.getKeyChar());        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemNameKeyTyped

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
txtItemDesc.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSearchCategoryActionPerformed

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
                NewItem dialog = new NewItem(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAddItemsToDB;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnNewBrand;
    private javax.swing.JButton btnNewSearchKey;
    private javax.swing.JButton btnNewSupplier;
    private javax.swing.JButton btnNewType;
    private javax.swing.JButton btnRemoveAllItems;
    private javax.swing.JButton btnRemoveImage;
    private javax.swing.JButton btnRemoveItem;
    private javax.swing.JComboBox cmbItemBrand;
    private javax.swing.JComboBox cmbPartType;
    private javax.swing.JComboBox cmbSearchCategory;
    private javax.swing.JComboBox cmbSupplier;
    private javax.swing.JFileChooser imageChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblImage;
    private javax.swing.JTable tblItems;
    private javax.swing.JTextField txtBuyingPrice;
    private javax.swing.JTextField txtItemDesc;
    private javax.swing.JTextField txtItemName;
    private javax.swing.JTextField txtRackNo;
    private javax.swing.JTextField txtReOrderQty;
    private javax.swing.JTextField txtRemarks;
    private javax.swing.JTextField txtSellingPrice;
    private javax.swing.JTextField txt_commi;
    // End of variables declaration//GEN-END:variables
}
