/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.suggest;

import controllers.SuggestController;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Suggest;
import org.json.JSONException;

/**
 *
 * @author insaf
 */
public class SuggestPanel extends javax.swing.JPanel {

    public DefaultTableModel dtm;

    /**
     * Creates new form SuggestPanel
     */
    public SuggestPanel() {
        initComponents();
        dtm = (DefaultTableModel) suggestTable.getModel();
        send();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        suggestTable = new javax.swing.JTable();
        viewButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        suggestTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order No", "Sales Officer", "Date", "Time", "discount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(suggestTable);
        if (suggestTable.getColumnModel().getColumnCount() > 0) {
            suggestTable.getColumnModel().getColumn(4).setMinWidth(0);
            suggestTable.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        viewButton.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        viewButton.setText("View Order");
        viewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewButtonActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(viewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewButton)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, viewButton});

    }// </editor-fold>//GEN-END:initComponents

    private void viewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewButtonActionPerformed

        boolean online = true;//CheckConnection.isInternetReachable();

        if (online) {
            if (suggestTable.getSelectedRowCount() > 0) {

                try {
                    int selectedRow = suggestTable.getSelectedRow();
                    Object valueAt = dtm.getValueAt(selectedRow, 0);
                    Object discount = dtm.getValueAt(selectedRow, 4);
                    String selectRo = String.valueOf(valueAt);
                    int pur_order_id = Integer.parseInt(selectRo);
                    double discounts = Double.parseDouble(String.valueOf(discount));
                    String vat = SuggestController.get_vat();

                    JDialog detailDialog = new DetailDialog(null, true, pur_order_id, dtm, discounts, vat, this);
                    detailDialog.setSize(850, 350);
                    detailDialog.setLocationRelativeTo(this);
                    detailDialog.setVisible(true);

                } catch (IOException ex) {
                    Logger.getLogger(SuggestPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(SuggestPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SuggestPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(SuggestPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please Select Order ");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No Internet Connection...");
            // Code when offline
        }


    }//GEN-LAST:event_viewButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        send();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable suggestTable;
    private javax.swing.JButton viewButton;
    // End of variables declaration//GEN-END:variables

    public void send() {
        boolean online = true;//CheckConnection.isInternetReachable();

        if (online) {
            dtm.setRowCount(0);
            try {
                ArrayList<Suggest> suggestOrder = SuggestController.getSuggestOrder();
                for (Suggest s : suggestOrder) {
                    Object row[] = {s.getOrder_no(), s.getSales_officer_name(), s.getDate(), s.getTime(), s.getDiscount_percentage()};
                    dtm.addRow(row);

                }

            } catch (IOException ex) {
                Logger.getLogger(SuggestPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(SuggestPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SuggestPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SuggestPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No Internet Connection...");
            // Code when offline
        }

    }

    //-------------------------Check Connection---------------------------
    public static boolean isInternetReachable() {
        try {
            //make a URL to a known source
            URL url = new URL("http://www.google.com");

            //open a connection to that source
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();

            //trying to retrieve data from the source. If there
            //is no connection, this line will fail
            Object objData = urlConnect.getContent();

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

/////////Json Read---------------------------
}
