/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.userviws;

import controllers.LogingHistroyController;
import controllers.ProfileController;
import controllers.UserController;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import models.LoginHistroy;
import models.User;
import views.MainFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author insaf
 */
public class LogingUser_new extends javax.swing.JFrame {

    private Timer timer;
    public static String server_path;
    public static String account_number;

    /**
     * Creates new form LogingUser
     */
    public LogingUser_new() {
        try {
            ResultSet dealerData = ProfileController.getDealerDetails();
            dealerData.next();
            server_path = dealerData.getString(9);
            account_number = dealerData.getString(3);
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
        }
        /*try {
         UIManager.setLookAndFeel(new GraphiteLookAndFeel());
         } catch (UnsupportedLookAndFeelException ex) {
         Logger.getLogger(LogingUser_new.class.getName()).log(Level.SEVERE, null, ex);
         }*/
        initComponents();

        /* String account_code = account_number;

         String replaceAll = account_code.toString().replaceAll("\\s", "%20");
         String replace = replaceAll.replaceAll("\\n", "%30");
         try {
         System.out.println(LogingUser_new.server_path + "dealer_purchase_order/addNewPurchaseOrderDetail?delar_account_no=" + replace);
         JSONObject returnedObject = new JSonJava().readJsonFromUrl(LogingUser_new.server_path + "serverdetails/getServerdetails?delar_account_no=" + replace);
         System.out.println("" + returnedObject);
         Integer get_status = Integer.parseInt(returnedObject.get("status").toString());
         if (get_status == 1) {
         JSONArray jsonArray = returnedObject.getJSONArray("dicount");
         String string = jsonArray.getJSONObject(0).getString("vat");
         String string1 = jsonArray.getJSONObject(0).getString("discount_percentage");
         VatDis vatDis = new VatDis();
         vatDis.setVat(string);
         vatDis.setDiscount(string1);
         vatDis.setStatus("1");
         vatDis.setAccount_no(account_number);
         try {
         boolean vat_exsists = VatDisController.vat_exsists(vatDis);
         if (!vat_exsists) {
         VatDisController.insertVat(vatDis);
         } else {
         VatDisController.updateVat(vatDis);
         }

         } catch (ClassNotFoundException ex) {
         Logger.getLogger(LogingUser_new.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
         Logger.getLogger(LogingUser_new.class.getName()).log(Level.SEVERE, null, ex);
         }

         }

         } catch (IOException ex) {
         Logger.getLogger(LogingUser_new.class.getName()).log(Level.SEVERE, null, ex);
         } catch (JSONException ex) {
         Logger.getLogger(LogingUser_new.class.getName()).log(Level.SEVERE, null, ex);
         }*/
//        LogingUser logingUser = new LogingUser();
//        URL url = getClass().getResource("images/favicon (3).ico");
//        ImageIcon imageIcon = new ImageIcon(url);
//        logingUser.setIconImage(imageIcon.getImage());
        goWebsite(jLabel4, "http://www.ceylonlinux.com/", "Powered by: www.ceylonlinux.com");
        timer = new Timer(3000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MainFrame orderFrame = new MainFrame();
                orderFrame.setVisible(true);
                if (timer.getDelay() == 3000) {
                    timer.stop();
                }
            }
        });
        this.setLocationRelativeTo(null);
        loding_gif.setVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        userNameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        loding_gif = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("User Login");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("DIMO DISTRIBUTOR SYSTEM");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(150, 30, 238, 20);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Username    :");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(138, 99, 79, 15);

        userNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameFieldActionPerformed(evt);
            }
        });
        userNameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                userNameFieldKeyReleased(evt);
            }
        });
        jPanel1.add(userNameField);
        userNameField.setBounds(248, 99, 203, 20);

        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });
        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                passwordFieldKeyReleased(evt);
            }
        });
        jPanel1.add(passwordField);
        passwordField.setBounds(248, 139, 203, 20);

        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
        });
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogin);
        btnLogin.setBounds(302, 187, 77, 31);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(389, 187, 73, 31);

        jPanel2.setBackground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 50, 560, 20);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(80, 1, 1, 1, new javax.swing.ImageIcon(getClass().getResource("/images/Q.png")))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 94, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(24, 88, 96, 81);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Password    :");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(138, 139, 79, 15);

        jLabel4.setBackground(new java.awt.Color(0, 102, 102));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel4.setText("Powered by : www.ceylonlinux.com");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel4);
        jLabel4.setBounds(360, 246, 190, 13);

        loding_gif.setBackground(new java.awt.Color(255, 255, 255));
        loding_gif.setBorder(javax.swing.BorderFactory.createMatteBorder(70, 1, 1, 1, new javax.swing.ImageIcon(getClass().getResource("/images/3 (1).GIF")))); // NOI18N

        javax.swing.GroupLayout loding_gifLayout = new javax.swing.GroupLayout(loding_gif);
        loding_gif.setLayout(loding_gifLayout);
        loding_gifLayout.setHorizontalGroup(
            loding_gifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 68, Short.MAX_VALUE)
        );
        loding_gifLayout.setVerticalGroup(
            loding_gifLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(loding_gif);
        loding_gif.setBounds(469, 99, 70, 70);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed

        if (userNameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No selected User", "Error", JOptionPane.ERROR_MESSAGE);;
        } else {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            String year = timeStamp.substring(0, 4);
            String month = timeStamp.substring(4, 6);
            String date = timeStamp.substring(6, 8);
            String hour = timeStamp.substring(9, 11);
            String minute = timeStamp.substring(11, 13);
            String second = timeStamp.substring(13, 15);

            String systemDate = year + "-" + month + "-" + date;
            String systemTime = hour + ":" + minute + ":" + second;
            String userName = userNameField.getText();
            char[] ch = passwordField.getPassword();
            String password = new String(ch);

            User user = new User();
            user.setUserName(userName);
            user.setPasword(password);

            try {
                User searchUser = UserController.searchUser(user);

                if (searchUser.getId() > 0) {
                    System.out.println("my ID" + searchUser.getId());
                    LoginHistroy loginHistroy = new LoginHistroy();
                    loginHistroy.setLid(searchUser.getId());
                    loginHistroy.setDate(systemDate);
                    loginHistroy.setTime(systemTime);

                    UserController.updateStateUser(searchUser.getId());

                    int addLoginHistroy = LogingHistroyController.addLoginHistroy(loginHistroy);
                    loding_gif.setVisible(true);
                    timer.start();
                } else {
                    JOptionPane.showMessageDialog(null, "Username or Password is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (ClassNotFoundException ex) {

                JOptionPane.showMessageDialog(this, "Class Error" + ex);

            } catch (SQLException ex) {
                ex.printStackTrace();

            }

        }


    }//GEN-LAST:event_btnLoginActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //Exit code here
        int showConfirmDialog = JOptionPane.showConfirmDialog(this, "Do you really want to exit from the Dimo System ?", "Exit", JOptionPane.YES_NO_OPTION);
        if (showConfirmDialog == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked

    }//GEN-LAST:event_btnLoginMouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked

    }//GEN-LAST:event_jLabel4MouseClicked

    private void userNameFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userNameFieldKeyReleased

        //iresha
        if (evt.getKeyCode() == evt.VK_ENTER) {
            passwordField.requestFocus();
        }
    }//GEN-LAST:event_userNameFieldKeyReleased

    private void passwordFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordFieldKeyReleased

        if (evt.getKeyCode() == evt.VK_ENTER) {
            btnLogin.requestFocus();

        }

    }//GEN-LAST:event_passwordFieldKeyReleased

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        btnLogin.doClick();
    }//GEN-LAST:event_passwordFieldActionPerformed

    private void userNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameFieldActionPerformed
//passwordField.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_userNameFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ParseException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {

        } catch (IllegalAccessException ex) {

        } catch (UnsupportedLookAndFeelException ex) {

        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LogingUser_new().setVisible(true);
            }
        });
    }

    private void goWebsite(JLabel website, final String url, String text) {
        website.setText("<html> <a href=\"\">" + text + "</a></html>");
        website.setCursor(new Cursor(Cursor.HAND_CURSOR));
        website.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (URISyntaxException | IOException ex) {
                    System.out.println("" + ex.getMessage());
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel loding_gif;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField userNameField;
    // End of variables declaration//GEN-END:variables
}

class check extends Thread {

    @Override
    public void run() {
        while (true) {

        }
    }

}
