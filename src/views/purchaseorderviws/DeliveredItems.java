/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.purchaseorderviws;

import controllers.DealerReturnController;
import controllers.DeliverOrderController;
import controllers.ProfileController;
import controllers.PurchesOrderController;
import controllers.StockController;
import java.awt.HeadlessException;
import java.awt.Point;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import models.DealerReturn;
import models.DealerReturnDetail;
import models.PurchesOrder;
import models.Stock;
import models.StockDetail;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utilities_new.CheckConnection;
import utilities_new.JSonJava;
import views.MainFrame;

/**
 *
 * @author SHDINESH
 */
public class DeliveredItems extends javax.swing.JDialog {

    private DefaultTableModel deliverOrderDtm, deliveredItemTableModel;
    //private static int purchaseOrderID;
    //private String acceptedStats;
    private int deliverOrderID;
    private Object oldValue;
    private String invoiceNo;
    private double wipNo;

    /**
     * Creates new form DeliveredItems
     */
    public DeliveredItems(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);

    }

    public DeliveredItems(java.awt.Frame parent, boolean modal, DefaultTableModel dtm, String invoiceNo, int deliverOrderID, double wipNo) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Invoice No.: " + invoiceNo + " WIP No.: " + wipNo);
        this.invoiceNo = invoiceNo;
        this.deliveredItemTableModel = (DefaultTableModel) tblDeliveredItems.getModel();
        this.deliverOrderID = deliverOrderID;
        this.deliverOrderDtm = dtm;
        this.wipNo = wipNo;
        try {
            btnAcceptToStock.setEnabled(true);
            tblDeliveredItems.setEnabled(true);
            DeliverOrderController.getall(deliverOrderID, deliveredItemTableModel);
            DeliverdItemsListner dil = new DeliverdItemsListner();
        } catch (ClassNotFoundException ex) {

        } catch (SQLException ex) {

        }

    }

    /* OLD 
     public static synchronized int sendDealerReturnsAsaJson(int dealerReturnID, String invoiceNo, double wipNo) {
     int returnValue = 0;
     try {
     ResultSet rst = MainFrame.getDealerRST();
     rst.next();
     String accountNo = rst.getString(3);
     String serverURL = rst.getString(9);
     int adminReturnID = 0;

     int maxReturnID = dealerReturnID;
     SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
     String time = sdf.format(new Date());
     sdf.applyPattern("yyyy-MM-dd");
     String date = sdf.format(new Date());

     JSONObject jsonObject = new JSONObject();
     jsonObject.put("invoiceNO", invoiceNo);
     jsonObject.put("wipNo", wipNo);
     jsonObject.put("date", date);
     jsonObject.put("time", time);
     jsonObject.put("acc_no", accountNo);
     //jsonObject.put("amount", PurchesOrderController.getPurchaseOrderAmount(maxPurchaseOrderID));
     jsonObject.put("status", 3);

     ArrayList<JSONObject> jsonArrayList = DealerReturnController.getDealerReturnDetails(dealerReturnID);
     JSONArray jSONArray = new JSONArray(jsonArrayList);
     jSONArray.put(jsonObject);

     String replaceAll = jSONArray.toString().replaceAll("\\s", "%20");
     String replace = replaceAll.replaceAll("\\n", "%30");
     //System.out.println("http://gateway.ceylonlinux.com/dimo_lanka/dealer_return/addNewDealerReturn?args1=" + replace);
     JSONObject returnedObject = new JSonJava().readJsonFromUrl(serverURL + "dimo_lanka/dealer_return/addNewDealerReturn?args1=" + replace);
     adminReturnID = Integer.parseInt((String) returnedObject.get("id"));
     DealerReturn dealerReturn = new DealerReturn(dealerReturnID, replace, time, WIDTH);
     //returnValue = DealerReturnController.saveAdminReturnID(dealerReturn);

     } catch (ClassNotFoundException ex) {
     } catch (SQLException ex) {
     } catch (MalformedURLException mex) {
     } catch (IOException ioex) {
     } catch (JSONException ex) {
     ex.printStackTrace();

     } finally {
     return returnValue;
     }

     }*/
    public static synchronized int sendDealerReturnAsaJson(JSONObject mainJSON) throws ClassNotFoundException, SQLException {
        ResultSet dealerRST = ProfileController.getDealerDetails();
        dealerRST.next();
        String serverURL = dealerRST.getString(9);
        int updateStatus = 0;
        try {
            String replaceAll = mainJSON.toString().replaceAll("\\s", "%20");
            String replace = replaceAll.replaceAll("\\n", "%30");
            String url = serverURL + "dimo_lanka/pos_services/insertNewDealerReturn";
            System.out.println(url + "?return_data=" + replace);
            JSONObject returnedObject = new JSonJava().postJSONObject(mainJSON, url, "return_data");
            //System.out.println(returnedObject);
            String timeStamp = returnedObject.getString("time_stamp");
            int admin_ret_id = returnedObject.getInt("admin_ret_id");
            DealerReturn dReturn = new DealerReturn();
            dReturn.setTimeStamp(timeStamp);
            dReturn.setAdmintReturnID(admin_ret_id);
            dReturn.setReturnID(mainJSON.getJSONObject("return_data").getInt("dealer_ret_id"));
            updateStatus = DealerReturnController.updateDealerReturn(dReturn);

        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException mex) {
            mex.printStackTrace();
        } catch (IOException ioex) {
        } catch (JSONException ex) {
            ex.printStackTrace();
        } finally {
            return updateStatus;
        }

    }

    public JSONObject addNewDealerReturn(int deliverOrderID, String date, String time, int rowCount, int status) throws ClassNotFoundException, SQLException, JSONException {
        int dealerReturnValue = 0;
        int returnID = 0;
        DealerReturn dealerReturn = new DealerReturn(deliverOrderID, date, time, status);
        dealerReturnValue = DealerReturnController.addNewDealerReturn(dealerReturn);
        returnID = DealerReturnController.getLastInsertedReturnID();
        JSONObject returnData = new JSONObject();
        JSONArray returnItems = new JSONArray();
        JSONObject returnrdPart = null;
        JSONObject mainReturnJSON = new JSONObject();
        if (dealerReturnValue > 0) {
            ResultSet rst = ProfileController.getDealerDetails();
            rst.next();
            returnData.put("dealer_acc_no", rst.getString(3));
            returnData.put("dealer_ret_id", returnID);
            returnData.put("invoice_no", invoiceNo);
            returnData.put("wip_no", wipNo);
            returnData.put("ret_date", date);
            returnData.put("ret_time", time);
            returnData.put("dealer_ret_id", returnID);

            for (int i = 0; i < rowCount; i++) {
                if (Double.parseDouble(tblDeliveredItems.getValueAt(i, 7).toString()) > 0) {
                    int itemID = Integer.parseInt(tblDeliveredItems.getValueAt(i, 2).toString());
                    double qty = Double.parseDouble(tblDeliveredItems.getValueAt(i, 3).toString());
                    double returndQty = Double.parseDouble(tblDeliveredItems.getValueAt(i, 7).toString());
                    String dealerReturnReason = tblDeliveredItems.getValueAt(i, 8).toString();
                    int delivered = (tblDeliveredItems.getValueAt(i, 6).toString() == "true" ? 1 : 0);
                    double unitPrice = Double.parseDouble(tblDeliveredItems.getValueAt(i, 4).toString()) / qty;
                    returnrdPart = new JSONObject();
                    returnrdPart.put("part_no", tblDeliveredItems.getValueAt(i, 1).toString());
                    returnrdPart.put("ret_qty", returndQty);
                    returnrdPart.put("ret_reason", dealerReturnReason);
                    returnrdPart.put("is_delivered", delivered);
                    returnrdPart.put("selling_val", String.format("%.2f", unitPrice));
                    returnrdPart.put("status", status);
                    returnItems.put(returnrdPart);
                    DealerReturnDetail dReturn = new DealerReturnDetail(returnID, itemID, returndQty, dealerReturnReason, delivered, unitPrice, status);
                    DealerReturnController.addNewDealerReturnDetail(dReturn);
                }
            }
            mainReturnJSON.put("return_data", returnData);
            mainReturnJSON.put("return_parts", returnItems);
            JOptionPane.showMessageDialog(this, "Return Items added.", "Successful", JOptionPane.INFORMATION_MESSAGE);
            DeliverOrderController.updateDeliverOrderStatus(deliverOrderID);
            DeliverOrderController.loadAllDeliverOrders(deliverOrderDtm);
        }
        return mainReturnJSON;
    }

    class DeliverdItemsListner implements TableModelListener {

        public DeliverdItemsListner() {
            tblDeliveredItems.getModel().addTableModelListener(this);
        }

        @Override
        public void tableChanged(TableModelEvent e) {

            TableModel model = (TableModel) e.getSource();
            int type = e.getType();
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (type == 0) {

                if (column == 7) {
                    try {
                        String rQty = model.getValueAt(row, 7).toString();
                        String iQty = model.getValueAt(row, 3).toString();
                        //int status = Integer.parseInt(model.getValueAt(row, 7).toString());
                        double initialQty = Double.parseDouble(iQty);
                        double returnQty = Double.parseDouble(rQty);
                        if ((returnQty > initialQty) || (rQty == null) || (rQty == "")) {
                            JOptionPane.showMessageDialog(null, "Return qty cannot be over than delivered qty.", "Error in quantity", JOptionPane.WARNING_MESSAGE);
                            model.setValueAt(oldValue, row, column);
                        } else {

                        }
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Quantities cannot be empty.", "Error in quantity", JOptionPane.WARNING_MESSAGE);
                        model.setValueAt(oldValue, row, column);
                    } catch (HeadlessException headlessException) {
                    }
                }
            } else if (type == 1) {

                // model.setValueAt(1, row, 8);
            } else if (type == -1) {
                /// System.out.println("removed");
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     *///cstlgamagepriyantha@gmail.com
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane6 = new javax.swing.JScrollPane();
        tblDeliveredItems = new javax.swing.JTable();
        btnAcceptToStock = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSearchParts = new javax.swing.JTextField();
        selectAllCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblDeliveredItems.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblDeliveredItems.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tblDeliveredItems.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Description", "Part No.", "Code", "Qty", "Selling Val (Rs.)", "Retail val (Rs.)", "Delivered", "Rejected Qty", "If return - reason"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDeliveredItems.setToolTipText("");
        tblDeliveredItems.setColumnSelectionAllowed(true);
        tblDeliveredItems.setRowHeight(20);
        tblDeliveredItems.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDeliveredItemsMouseClicked(evt);
            }
        });
        tblDeliveredItems.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tblDeliveredItemsMouseMoved(evt);
            }
        });
        jScrollPane6.setViewportView(tblDeliveredItems);
        tblDeliveredItems.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tblDeliveredItems.getColumnModel().getColumnCount() > 0) {
            tblDeliveredItems.getColumnModel().getColumn(0).setMinWidth(200);
            tblDeliveredItems.getColumnModel().getColumn(0).setPreferredWidth(200);
            tblDeliveredItems.getColumnModel().getColumn(0).setMaxWidth(200);
            tblDeliveredItems.getColumnModel().getColumn(1).setMinWidth(120);
            tblDeliveredItems.getColumnModel().getColumn(1).setPreferredWidth(120);
            tblDeliveredItems.getColumnModel().getColumn(1).setMaxWidth(120);
            tblDeliveredItems.getColumnModel().getColumn(2).setMinWidth(0);
            tblDeliveredItems.getColumnModel().getColumn(2).setPreferredWidth(0);
            tblDeliveredItems.getColumnModel().getColumn(2).setMaxWidth(0);
            tblDeliveredItems.getColumnModel().getColumn(3).setMinWidth(80);
            tblDeliveredItems.getColumnModel().getColumn(3).setPreferredWidth(80);
            tblDeliveredItems.getColumnModel().getColumn(3).setMaxWidth(80);
            tblDeliveredItems.getColumnModel().getColumn(4).setMinWidth(100);
            tblDeliveredItems.getColumnModel().getColumn(4).setPreferredWidth(100);
            tblDeliveredItems.getColumnModel().getColumn(4).setMaxWidth(100);
            tblDeliveredItems.getColumnModel().getColumn(6).setMinWidth(70);
            tblDeliveredItems.getColumnModel().getColumn(6).setPreferredWidth(70);
            tblDeliveredItems.getColumnModel().getColumn(6).setMaxWidth(70);
            tblDeliveredItems.getColumnModel().getColumn(7).setMinWidth(80);
            tblDeliveredItems.getColumnModel().getColumn(7).setPreferredWidth(80);
            tblDeliveredItems.getColumnModel().getColumn(7).setMaxWidth(80);
        }

        btnAcceptToStock.setText("Accept");
        btnAcceptToStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptToStockActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("* All prices are with current vat percentage.");

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Search:");

        txtSearchParts.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSearchParts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchPartsKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSearchParts, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSearchParts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        selectAllCheckBox.setText("Select all");
        selectAllCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 868, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(selectAllCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(btnAcceptToStock, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAcceptToStock)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(selectAllCheckBox))
                    .addComponent(btnCancel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAcceptToStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptToStockActionPerformed
        int rowCount = tblDeliveredItems.getRowCount();
        double totalReturn = 0;
        double totalQty = 0;
        double totalStockQty = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        sdf.applyPattern("HH:mm:ss");
        String time = sdf.format(new Date());
        ResultSet rst = null;
        //String invoiceNo = txtInvoiceNo.getText();
        Stock stock = new Stock(1, date, time, 1);
        stock.setDeliverOrderId(deliverOrderID);
        //int adminPurchaseOorderId, String invoiceNo, String addedDate, String addedTime, int status
        for (int i = 0; i < rowCount; i++) {
            if (!Boolean.parseBoolean(tblDeliveredItems.getValueAt(i, 6).toString())) {
                double returnQty = Double.parseDouble(tblDeliveredItems.getValueAt(i, 3).toString());
                tblDeliveredItems.setValueAt(returnQty, i, 7);
            }
        }
        for (int i = 0; i < rowCount; i++) {
            //System.out.println(tblDeliveredItems.getValueAt(i, 7).toString());
            totalReturn += Double.parseDouble(tblDeliveredItems.getValueAt(i, 7).toString());
            totalQty += Integer.parseInt(tblDeliveredItems.getValueAt(i, 3).toString());

        }
        totalStockQty = totalQty - totalReturn;
        // DealerReturn dealerReturn = new DealerReturn(adminPOID, invoiceNo, date, time, 1);
        int addNewStock = 0;
        int stockID = 0;

        try {
            rst = ProfileController.getDealerDetails();
            rst.next();

            if (totalStockQty > 0) {
                addNewStock = StockController.addNewStock(stock);
                stockID = StockController.getLastInsertedStockID();

            }

            if (addNewStock > 0) {

                for (int i = 0; i < rowCount; i++) {
                    if (Boolean.parseBoolean(tblDeliveredItems.getValueAt(i, 6).toString())) {
                        int itemID = Integer.parseInt(tblDeliveredItems.getValueAt(i, 2).toString());
                        int qty = Integer.parseInt(tblDeliveredItems.getValueAt(i, 3).toString());
                        int returnQty = Integer.parseInt(tblDeliveredItems.getValueAt(i, 7).toString());
                        double buyingPrice = Double.parseDouble(tblDeliveredItems.getValueAt(i, 4).toString()) / qty;     //1 item akak price aka balaganna
                        double sellingPrice = Double.parseDouble(tblDeliveredItems.getValueAt(i, 5).toString()) / qty;
                        double buyingPriceWithVat = buyingPrice;
                        double sellingPriceWithVat = sellingPrice;
                        int stockQty = qty - returnQty;
                        if (stockQty > 0) {
                            StockDetail stockDetail = new StockDetail();
                            stockDetail.setBuyingPrice(buyingPriceWithVat);
                            stockDetail.setItemID(itemID);
                            stockDetail.setRemainingQty(stockQty);
                            stockDetail.setSellingPrice(sellingPriceWithVat);
                            stockDetail.setStatus(1);
                            stockDetail.setStockID(stockID);
                            stockDetail.setStockQuontity(stockQty);
                            StockController.addNewStockDetail(stockDetail);
                        }

                    }
                }
                DeliverOrderController.updateDeliverOrderStatus(deliverOrderID);
                DeliverOrderController.loadAllDeliverOrders(deliverOrderDtm);
                JOptionPane.showMessageDialog(this, "Parts Successfully added to the stock.", "Successful", JOptionPane.INFORMATION_MESSAGE);

            }

            if (totalReturn > 0) {
                if (CheckConnection.isInternetReachable()) {
                    JSONObject returnData = addNewDealerReturn(deliverOrderID, date, time, rowCount, 1);
                    sendDealerReturnAsaJson(returnData);

                } else if (!CheckConnection.isInternetReachable()) {
                    JSONObject returnData = addNewDealerReturn(deliverOrderID, date, time, rowCount, 1);
                    JOptionPane.showMessageDialog(this, "Return Order Not Sent. Please try again.", "Connection Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (ClassNotFoundException cnfEx) {
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        } finally {

            this.dispose();
        }


    }//GEN-LAST:event_btnAcceptToStockActionPerformed

    private void tblDeliveredItemsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDeliveredItemsMouseMoved
        try {
            Point p = evt.getPoint();
            p.getX();
            int row = tblDeliveredItems.rowAtPoint(p);
            int column = tblDeliveredItems.columnAtPoint(p);
            String value = tblDeliveredItems.getValueAt(row, column).toString();
            tblDeliveredItems.setToolTipText(value);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblDeliveredItemsMouseMoved

    private void tblDeliveredItemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDeliveredItemsMouseClicked
        Point p = evt.getPoint();
        int row = tblDeliveredItems.rowAtPoint(p);
        int column = tblDeliveredItems.columnAtPoint(p);
        this.oldValue = tblDeliveredItems.getValueAt(row, column);

    }//GEN-LAST:event_tblDeliveredItemsMouseClicked

    private void txtSearchPartsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchPartsKeyReleased
        String text = txtSearchParts.getText();

        try {

            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(deliveredItemTableModel);
            tblDeliveredItems.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(("(?i)" + text)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txtSearchPartsKeyReleased

    private void selectAllCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllCheckBoxActionPerformed
        boolean selected = selectAllCheckBox.isSelected();
        int rowCount = tblDeliveredItems.getRowCount();
        if (selected) {
            for (int i = 0; i < rowCount; i++) {
                tblDeliveredItems.setValueAt(true, i, 6);
            }

        } else {
            for (int i = 0; i < rowCount; i++) {
                tblDeliveredItems.setValueAt(false, i, 6);
            }
        }
    }//GEN-LAST:event_selectAllCheckBoxActionPerformed

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
            java.util.logging.Logger.getLogger(DeliveredItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DeliveredItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DeliveredItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DeliveredItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DeliveredItems dialog = new DeliveredItems(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAcceptToStock;
    private javax.swing.JButton btnCancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JCheckBox selectAllCheckBox;
    private javax.swing.JTable tblDeliveredItems;
    private javax.swing.JTextField txtSearchParts;
    // End of variables declaration//GEN-END:variables
}
