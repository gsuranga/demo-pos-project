/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.DealerReturnController;
import controllers.ItemController;
import controllers.LossSaleController;
import controllers.ProfileController;
import controllers.PurchesOrderController;
import controllers.SalesOrderController;
import controllers.StockController;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utilities_new.BackupAndRestore;
import utilities_new.CheckConnection;
import views.customer.CustomerPanel;
import views.dealer_returns.DealerReturnPanel;
import views.expences.ExpencesPanel;
import views.debtviews.AllDebtsDetail;
import views.employee.Employee;
import views.estimate.EstimatePanel;
import views.itemviews.ItemPanel;
import views.purchaseorderviws.PerchusOrder;
import views.salesorderviws.SalesOrderPanel;
import views.stockviws.StockDetailPanel;
import views.userviws.UserSettingsPanel;
import views.report.salessummery.ReportPanel;
import views.salesorderviws.NewJob;
import views.serviceviews.ServicePanel;
import views.userviws.LogingUser_new;
import views.vehicleviews.VehiclePanel;

/**
 *
 * @author SHdinesh Madushanka
 */
public class MainFrame extends javax.swing.JFrame {

    private static int panelID = 0;
    private static ResultSet dealerRST;
    private int count = 0;

    /**
     * @return the dealerRST
     */
    public static ResultSet getDealerRST() {
        return dealerRST;
    }

    /**
     * @param aDealerRST the dealerRST to set
     */
    public static void setDealerRST(ResultSet aDealerRST) {
        dealerRST = aDealerRST;
    }
    private final Timer purchaseOrderTimer, deliverOrderTimer, dealerReturnTimer, salesAndStockTimer, lossSalesTimer, stockTimer, priceUpdateTimer,
            repAcceptedReturnsTimer, adminAcceptedReturnsTimer;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {

        Date date = new Date();
        this.purchaseOrderTimer = new Timer();
        this.deliverOrderTimer = new Timer();
        this.dealerReturnTimer = new Timer();
        this.salesAndStockTimer = new Timer();
        this.lossSalesTimer = new Timer();
        this.stockTimer = new Timer();
        this.priceUpdateTimer = new Timer();
        this.repAcceptedReturnsTimer = new Timer();
        this.adminAcceptedReturnsTimer = new Timer();

        /*comment */
        purchaseOrderTimer.schedule(sendingPurchaseOrder, date, 3600 * 1000);
        deliverOrderTimer.schedule(deliverOrderTask, date, 3600 * 1000 * 24);
        repAcceptedReturnsTimer.schedule(repAcceptedReturnsTask, date, 3600 * 1000 * 24);
        adminAcceptedReturnsTimer.schedule(adminAcceptedReturnsTask, date, 3600 * 1000 * 24);
////      dealerReturnTimer.schedule(dealerRerutnTask, date, 3600 * 1000);
        salesAndStockTimer.schedule(sendingSalesAndStocks, new Date(), 3600 * 1000);
        lossSalesTimer.schedule(sendingLossSales, date, 3600 * 1000);
        stockTimer.schedule(stockDetailsTask, date, 3600 * 1000);
        priceUpdateTimer.schedule(priceUpdateTask, date, 3600 * 1000 * 24);
        /*comment */
        initComponents();
        mainPanel.add(new SalesOrderPanel(true, btnSalesOrders));

        // this.setIconImage(new ImageIcon(getClass().getResource("../images/dimo.png")).getImage());
        //setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(this);
    }
    TimerTask sendingPurchaseOrder = new TimerTask() {

        @Override
        public void run() {
            //System.out.println ("started");
            HashSet<Integer> hs = new HashSet<Integer>();
            if (CheckConnection.isInternetReachable()) {
                try {
                    ResultSet rst = PurchesOrderController.getAllUnAssignedPurchaseOrdersToSend();
                    ResultSet dealerRst = ProfileController.getDealerDetails();
                    dealerRst.next();
                    while (rst.next()) {
                        int id = rst.getInt("id");

                        if (!hs.contains(id)) {
                            hs.add(id);
                            JSONObject poData = new JSONObject();
                            double totalAmount = rst.getDouble("total");
                            double finalAmount = rst.getDouble("final_amount");
                            poData.put("dealerPurchaseOrderID", id);
                            poData.put("date", rst.getString("date"));
                            poData.put("time", rst.getString("time"));
                            poData.put("dealerAccountNo", dealerRst.getString(3));
                            poData.put("final_amount", finalAmount);
                            poData.put("total_amount", totalAmount);
                            poData.put("status", 1);
                            poData.put("tour_status", 0);
                            poData.put("is_sales_officer", 0);
                            poData.put("discount_percentage", dealerRst.getDouble(4));
                            poData.put("current_vat", dealerRst.getDouble(5));
                            poData.put("tot_disc", (totalAmount - finalAmount));
                            JSONArray partsArray = new JSONArray();
                            ResultSet partsRst = PurchesOrderController.getOrderData(id);
                            while (partsRst.next()) {
                                JSONObject partsData = new JSONObject();
                                partsData.put("partNo", partsRst.getString("item_part_no"));
                                partsData.put("description", partsRst.getString("Description"));
                                partsData.put("unit_price", partsRst.getDouble("unitprice"));
                                partsData.put("qty", partsRst.getDouble("quantity"));
                                partsData.put("status", 1);
                                partsArray.put(partsData);
                            }
                            JSONObject mainJSON = new JSONObject();
                            mainJSON.put("po_data", poData);
                            mainJSON.put("parts_data", partsArray);
                           // System.out.println(mainJSON);
                            PerchusOrder.sendPurchaseOrderAsaJson(mainJSON);

                        }
                    }
                } catch (ClassNotFoundException classNotFoundException) {
                } catch (SQLException sQLException) {
                } catch (NullPointerException ex) {
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            } else {
            }

        }
    };
    TimerTask deliverOrderTask = new TimerTask() {

        @Override
        public void run() {

            if (CheckConnection.isInternetReachable()) {

                PerchusOrder.getPurchaseOrderStatus();

            }

        }
    };
//    TimerTask dealerRerutnTask = new TimerTask() {
//
//        @Override
//        public void run() {
//
//            if (CheckConnection.isInternetReachable()) {
//                try {
//                    ResultSet rst = DealerReturnController.getAllUnAssignedReturnsToSend();
//                    while (rst.next()) {
//                        int id = rst.getInt("dealer_return_id");
//                        String invoiceNo = rst.getString("invoice_no");
//                        int adminPOID = rst.getInt("admin_purchase_order_id");
//                        //DeliveredItems.sendDealerReturnsAsaJson(id, adminPOID, invoiceNo);
//                    }
//                } catch (ClassNotFoundException classNotFoundException) {
//                } catch (SQLException sQLException) {
//                } catch (NullPointerException ex) {
//                }
//            } else {
//            }
//
//        }
//    };

    //************sales & stock task*****************//
    TimerTask sendingSalesAndStocks = new TimerTask() {

        @Override
        public void run() {

            if (CheckConnection.isInternetReachable()) {
                try {
                    ResultSet rst = SalesOrderController.loadAllUnAssignedSalesOrders();
                    while (rst.next()) {
                        int id = rst.getInt(1);
                        int value = SalesOrderPanel.sendSalesDetailsAsJSON(id);
                        if (value == 1) {
                            SalesOrderController.updateSalesOrderStatus(id);
                        }

                    }
                } catch (ClassNotFoundException classNotFoundException) {
                } catch (SQLException sQLException) {
                } catch (NullPointerException ex) {
                }
            } else {
            }

        }
    };
    //************sales & stock task

    TimerTask sendingLossSales = new TimerTask() {

        @Override
        public void run() {

            if (CheckConnection.isInternetReachable()) {
                try {
                    ArrayList arrayList = LossSaleController.loadAllUnAssignedLossSales();
                    if (arrayList.size() > 0) {
                        LossSaleController.sendLossSalesAsJSON(arrayList);

                    }
                } catch (JSONException ex) {
                } catch (ClassNotFoundException ex) {
                } catch (SQLException ex) {
                }

            } else {
            }

        }
    };

    //***********stock task
    TimerTask stockDetailsTask = new TimerTask() {

        @Override
        public void run() {

            if (CheckConnection.isInternetReachable()) {
                try {
                    ArrayList stockData = StockController.loadAllUnAssignedStocks();

                    if (stockData.size() > 0) {
                        StockController.sendStockDetailsAsaJson(stockData);
                    }
                } catch (ClassNotFoundException ex) {
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }

            }

        }
    };

    //--------------------------price update task---------------------------------
    TimerTask priceUpdateTask = new TimerTask() {

        @Override
        public void run() {

            if (CheckConnection.isInternetReachable()) {
                try {
                    ItemController.updatePartData();
                    System.out.println("######");
                } catch (ClassNotFoundException ex) {
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                } catch (Exception ex) {
                }

            } else {
                System.out.println("No Connection");
            }

        }
    };
    TimerTask repAcceptedReturnsTask = new TimerTask() {

        @Override
        public void run() {

            if (CheckConnection.isInternetReachable()) {

                DealerReturnController.getAllRepAcceptedReturns();

            }

        }
    };
    TimerTask adminAcceptedReturnsTask = new TimerTask() {

        @Override
        public void run() {

            if (CheckConnection.isInternetReachable()) {

                DealerReturnController.getAllAdminAcceptedReturns();

            }

        }
    };

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPane = new javax.swing.JPanel();
        btnSalesOrders = new javax.swing.JButton();
        btnStock = new javax.swing.JButton();
        btnSettings = new javax.swing.JButton();
        btnCustomers = new javax.swing.JButton();
        btnItems = new javax.swing.JButton();
        btnDebts = new javax.swing.JButton();
        btnReports = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnGoodsOrders = new javax.swing.JButton();
        btnVehicles = new javax.swing.JButton();
        btnSuppliers = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnEmployee = new javax.swing.JButton();
        btnSuppliers1 = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        mainMenu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dimo Dealer System - Sales Orders");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        buttonPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Adobe Arabic", 1, 10))); // NOI18N
        buttonPane.setMaximumSize(new java.awt.Dimension(193, 605));
        buttonPane.setMinimumSize(new java.awt.Dimension(193, 605));

        btnSalesOrders.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSalesOrders.setText("Sales Orders");
        btnSalesOrders.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalesOrders.setPreferredSize(new java.awt.Dimension(107, 22));
        btnSalesOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalesOrdersActionPerformed(evt);
            }
        });

        btnStock.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnStock.setText("Stocks");
        btnStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStockActionPerformed(evt);
            }
        });

        btnSettings.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSettings.setText("Settings");
        btnSettings.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingsActionPerformed(evt);
            }
        });

        btnCustomers.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCustomers.setText("Customers");
        btnCustomers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomersActionPerformed(evt);
            }
        });

        btnItems.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnItems.setText("Parts");
        btnItems.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemsActionPerformed(evt);
            }
        });

        btnDebts.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDebts.setText("Debtors");
        btnDebts.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDebts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDebtsActionPerformed(evt);
            }
        });

        btnReports.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnReports.setText("Reports");
        btnReports.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportsActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rsz_1tgp_-_english.png"))); // NOI18N
        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        btnGoodsOrders.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnGoodsOrders.setText("Purchase Orders");
        btnGoodsOrders.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGoodsOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoodsOrdersActionPerformed(evt);
            }
        });

        btnVehicles.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnVehicles.setText("Vehicles");
        btnVehicles.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVehicles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVehiclesActionPerformed(evt);
            }
        });

        btnSuppliers.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSuppliers.setText("Services");
        btnSuppliers.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSuppliers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuppliersActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Expenses");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnEmployee.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEmployee.setText("Employee");
        btnEmployee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeActionPerformed(evt);
            }
        });

        btnSuppliers1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSuppliers1.setText("Returns");
        btnSuppliers1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSuppliers1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuppliers1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPaneLayout = new javax.swing.GroupLayout(buttonPane);
        buttonPane.setLayout(buttonPaneLayout);
        buttonPaneLayout.setHorizontalGroup(
            buttonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPaneLayout.createSequentialGroup()
                .addGroup(buttonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buttonPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(buttonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGoodsOrders, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(btnItems, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(btnSalesOrders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSuppliers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCustomers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnVehicles, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(btnEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDebts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2)
                            .addComponent(btnReports, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(btnSettings, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)))
                    .addGroup(buttonPaneLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(buttonPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnSuppliers1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonPaneLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCustomers, btnDebts, btnEmployee, btnGoodsOrders, btnItems, btnReports, btnSalesOrders, btnSettings, btnStock, btnSuppliers, btnSuppliers1, btnVehicles, jButton2});

        buttonPaneLayout.setVerticalGroup(
            buttonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPaneLayout.createSequentialGroup()
                .addComponent(btnSalesOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btnGoodsOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(btnItems, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCustomers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVehicles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEmployee)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStock, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSuppliers1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDebts, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReports, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        buttonPaneLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCustomers, btnDebts, btnEmployee, btnGoodsOrders, btnItems, btnReports, btnSalesOrders, btnSettings, btnStock, btnSuppliers, btnSuppliers1, btnVehicles, jButton2});

        mainPanel.setLayout(new java.awt.CardLayout());

        jMenu1.setText("File");

        jMenuItem1.setText("Sync");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("New Job");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setText("Sign Out");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        mainMenu.add(jMenu1);

        setJMenuBar(mainMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(buttonPane, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStockActionPerformed
        if (panelID != 5) {
            mainPanel.removeAll();
            JPanel stockDetailPanel = new StockDetailPanel();
            this.setTitle("Dimo Dealer System - Stock Details");
            mainPanel.add(stockDetailPanel);
            mainPanel.revalidate();
            panelID = 5;
        }
    }//GEN-LAST:event_btnStockActionPerformed

    private void btnGoodsOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoodsOrdersActionPerformed
        if (panelID != 2) {
            mainPanel.removeAll();
            PerchusOrder perchusOrder = new PerchusOrder();
            this.setTitle("Dimo Dealer System - Purchase Orders");
            mainPanel.add(perchusOrder);
            mainPanel.revalidate();
            panelID = 2;
        }
    }//GEN-LAST:event_btnGoodsOrdersActionPerformed

    private void btnCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomersActionPerformed
        if (panelID != 3) {
            mainPanel.removeAll();
            JPanel customerPanel = new CustomerPanel();
            this.setTitle("Dimo Dealer System - Customers");
            mainPanel.add(customerPanel);
            mainPanel.revalidate();
            panelID = 3;
        }

    }//GEN-LAST:event_btnCustomersActionPerformed

    private void btnItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemsActionPerformed
        if (panelID != 4) {
            mainPanel.removeAll();
            JPanel itemPanel = new ItemPanel();
            this.setTitle("Dimo Dealer System - Parts");
            mainPanel.add(itemPanel);
            mainPanel.revalidate();
            panelID = 4;
        }

    }//GEN-LAST:event_btnItemsActionPerformed

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSettingsActionPerformed
        if (panelID != 8) {
            mainPanel.removeAll();
            JPanel itemPanel = new UserSettingsPanel();
            this.setTitle("Dimo Dealer System - Settings");
            mainPanel.add(itemPanel);
            mainPanel.revalidate();
            panelID = 8;
        }
    }//GEN-LAST:event_btnSettingsActionPerformed

    private void btnSuppliersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuppliersActionPerformed
        if (panelID != 6) {
            mainPanel.removeAll();
            JPanel servicePanel = new ServicePanel();
            mainPanel.add(servicePanel);
            mainPanel.revalidate();
            this.setTitle("Dimo Dealer System - Services");
            panelID = 6;
        }
    }//GEN-LAST:event_btnSuppliersActionPerformed

    private void btnSalesOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalesOrdersActionPerformed
        if (panelID != 1) {
            mainPanel.removeAll();
            JPanel salesOrderPanel = new SalesOrderPanel(true, btnSalesOrders);
            this.setTitle("Dimo Dealer System - Sales Orders");
            mainPanel.add(salesOrderPanel);
            mainPanel.revalidate();
            panelID = 1;
        }
    }//GEN-LAST:event_btnSalesOrdersActionPerformed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
    }//GEN-LAST:event_formWindowStateChanged

    private void btnDebtsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDebtsActionPerformed
        if (panelID != 9) {
            mainPanel.removeAll();
            JPanel debtPanel = new AllDebtsDetail();
            this.setTitle("Dimo Dealer System - Debtor Details");
            mainPanel.add(debtPanel);
            mainPanel.revalidate();
            panelID = 9;
        }
    }//GEN-LAST:event_btnDebtsActionPerformed

    private void btnReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportsActionPerformed
        try {
            if (panelID != 11) {
                mainPanel.removeAll();
                JPanel returnPanel = new ReportPanel();
                this.setTitle("Dimo Dealer System - Reports");
                mainPanel.add(returnPanel);
                mainPanel.revalidate();
                panelID = 11;
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnReportsActionPerformed

    private void btnVehiclesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVehiclesActionPerformed
        if (panelID != 12) {
            mainPanel.removeAll();
            JPanel vehiclePanel = new VehiclePanel();
            this.setTitle("Dimo Dealer System - Vehicles");
            mainPanel.add(vehiclePanel);
            mainPanel.revalidate();
            panelID = 12;
        }

    }//GEN-LAST:event_btnVehiclesActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (panelID != 13) {
            mainPanel.removeAll();
            JPanel expencePanel = new ExpencesPanel();
            this.setTitle("Dimo Dealer System - Expenses");
            mainPanel.add(expencePanel);
            mainPanel.revalidate();
            panelID = 13;
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        BackupAndRestore.backupDB();
    }//GEN-LAST:event_formWindowClosing

    private void btnEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeActionPerformed
        if (panelID != 14) {
            mainPanel.removeAll();
            JPanel employeePanel = new Employee();
            this.setTitle("Dimo Dealer System - Employee");
            mainPanel.add(employeePanel);
            mainPanel.revalidate();
            panelID = 14;
        }
    }//GEN-LAST:event_btnEmployeeActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        NewJob nj = new NewJob(this, btnSalesOrders);
        nj.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        this.dispose();
        LogingUser_new login = new LogingUser_new();
        login.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void btnSuppliers1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuppliers1ActionPerformed
        if (panelID != 15) {
            mainPanel.removeAll();
            JPanel returnPanel = new DealerReturnPanel();
            this.setTitle("Dimo Dealer System - Returns");
            mainPanel.add(returnPanel);
            mainPanel.revalidate();
            panelID = 15;
        
         
        }
    }//GEN-LAST:event_btnSuppliers1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //   System.setProperty("http.proxyHost", "192.168.1.1");
        // System.setProperty("http.proxyPort", "3128");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCustomers;
    private javax.swing.JButton btnDebts;
    private javax.swing.JButton btnEmployee;
    private javax.swing.JButton btnGoodsOrders;
    private javax.swing.JButton btnItems;
    private javax.swing.JButton btnReports;
    private javax.swing.JButton btnSalesOrders;
    private javax.swing.JButton btnSettings;
    private javax.swing.JButton btnStock;
    private javax.swing.JButton btnSuppliers;
    private javax.swing.JButton btnSuppliers1;
    private javax.swing.JButton btnVehicles;
    private javax.swing.JPanel buttonPane;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuBar mainMenu;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

}
