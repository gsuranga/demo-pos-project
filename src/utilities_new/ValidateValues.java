/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities_new;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.text.*;
import java.util.regex.*;

public class ValidateValues {

    public static boolean checkNumber(String s) {
        boolean isNumber = true;
        try {
            Double.parseDouble(s);
            isNumber = true;
        } catch (Exception e) {
            isNumber = false;
        }
        return isNumber;
    }

    public static boolean checkName(String s) {
        boolean isName = true;
        try {
            Double.parseDouble(s);
            isName = false;
        } catch (Exception e) {
            isName = true;
        }
        return isName;
    }

    public static boolean checkNumberForCurrency(String s) {
        boolean isNumber = true;
        try {
            Double.parseDouble(s + "0");
            isNumber = true;
        } catch (Exception e) {
            isNumber = false;
        }
        return isNumber;
    }

    public static void validateTextFieldForNumber(JTextField txtContact) {
        try {
            String s = txtContact.getText();
            while (!checkNumber(s)) {
                String s1 = s.substring(0, s.length() - 1);
                txtContact.setText(s1);
                s = s1;
            }
        } catch (Exception e) {
        }
    }

    public static void validateTextFieldForName(JTextField txtContact) {
        try {
            String s = txtContact.getText();
            while (!checkName(s)) {
                String s1 = s.substring(0, s.length() - 1);
                txtContact.setText(s1);
                s = s1;
            }
        } catch (Exception e) {
            
        }
    }

    public static void validateTextFieldForNumberForCurrency(JTextField txtContact) {
        try {
            String s = txtContact.getText();
            while (!checkNumberForCurrency(s)) {
                String s1 = s.substring(0, s.length() - 1);
                txtContact.setText(s1);
                s = s1;
            }
        } catch (Exception e) {
        }
    }

    public static void validateTextFieldForContactNumber(JTextField txtContact) {
        try {
            String s = txtContact.getText();
            while (!checkNumber(s)) {
                String s1 = s.substring(0, s.length() - 1);
                txtContact.setText(s1);
                s = s1;
            }
        } catch (Exception ex) {
        }
        String s2 = txtContact.getText();
        if (s2.length() > 10) {
            txtContact.setText(s2.substring(0, 10));
        }
    }

    public static String convertStringValueFromCurrency(String value) {
        double currencyD = Double.parseDouble(value);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);

        String currency = nf.format(currencyD);
        return currency;
    }

    public static void checkEMailAddres(JTextField tf) {
        String mail = tf.getText();
        String x = mail.substring(0, 1);
        x = x.toLowerCase();
        mail = x + mail.substring(1);
        tf.setText(mail);
        String s = "not mail";

        boolean o = mail.contains(".") && (!mail.contains(" ") && mail.contains("@"));

        if (o) {

            int a = mail.lastIndexOf(".");
            int l = mail.length();
            String com = mail.substring(a + 1, l);

            Pattern p = Pattern.compile("[0-9]");
            Matcher m = p.matcher(com);
            int i = 0;
            while (m.find()) {
                i++;
            }

            String notcom = mail.substring(0, a);

            boolean z = notcom.contains("@");

            String surche = null;
            String name = null;
            if (z) {

                boolean z2 = com.contains("@");

                int n = notcom.length();
                int b = notcom.lastIndexOf("@");
                surche = notcom.substring(b + 1, n);
                name = notcom.substring(0, b);


                boolean z3 = name.contains("@");
                boolean z4 = surche.contains(".");

                if (z2 || z3 || z4 || com.length() < 2 || surche.length() < 3 || name.length() < 3 || i > 0) {
                    s = "not mail";
                    tf.setText(s);
                    tf.requestFocus();
                    tf.selectAll();
                }

            } else {
                s = "not mail";
                tf.setText(s);
                tf.requestFocus();
                tf.selectAll();

            }
        } else {
            s = "not mail";
            tf.setText(s);
            tf.requestFocus();
            tf.selectAll();

        }

    }

    public static String filterName(String value) {

        StringBuilder sb = new StringBuilder(value);

        Pattern p = Pattern.compile("[0-9]");
        Matcher n = p.matcher(value);
        int x = 0;
        while (n.find()) {
            x++;
        }

        for (int y = 0; y < x; y++) {
            Matcher m = p.matcher(sb);
            while (m.find()) {
                System.out.println(m.start() + "  " + m.end());
                sb = sb.delete(m.start(), m.end());
                break;
            }
        }

        String out = new String(sb);

        return out;
    }

    public static String filterNumber(String value) {

        StringBuilder sb = new StringBuilder(value);

        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher n = p.matcher(value);
        int x = 0;
        while (n.find()) {
            x++;
        }

        for (int y = 0; y < x; y++) {
            Matcher m = p.matcher(sb);
            while (m.find()) {
                System.out.println(m.start() + "  " + m.end());
                sb = sb.delete(m.start(), m.end());
                break;
            }
        }

        String out = new String(sb);

        return out;
    }

    public static String filterWordCharacter(String value) {

        StringBuilder sb = new StringBuilder(value);

        Pattern p = Pattern.compile("[^\\w]");
        Matcher n = p.matcher(value);
        int x = 0;
        while (n.find()) {
            x++;
        }

        for (int y = 0; y < x; y++) {
            Matcher m = p.matcher(sb);
            while (m.find()) {
                System.out.println(m.start() + "  " + m.end());
                sb = sb.delete(m.start(), m.end());
                break;
            }
        }

        String out = new String(sb);

        return out;
    }

    public static String filterFullWordCharacter(String value) {

        StringBuilder sb = new StringBuilder(value);

        Pattern p = Pattern.compile("[^a-zA-Z. ]");
        Matcher n = p.matcher(value);
        int x = 0;
        while (n.find()) {
            x++;
        }

        for (int y = 0; y < x; y++) {
            Matcher m = p.matcher(sb);
            while (m.find()) {
                System.out.println(m.start() + "  " + m.end());
                sb = sb.delete(m.start(), m.end());
                break;
            }
        }

        String out = new String(sb);

        return out;
    }

    public static String filterMailCharacter(String value) {

        StringBuilder sb = new StringBuilder(value);

        Pattern p = Pattern.compile("[^\\w.@]");
        Matcher n = p.matcher(value);
        int x = 0;
        while (n.find()) {
            x++;
        }

        for (int y = 0; y < x; y++) {
            Matcher m = p.matcher(sb);
            while (m.find()) {
                System.out.println(m.start() + "  " + m.end());
                sb = sb.delete(m.start(), m.end());
                break;
            }
        }

        String out = new String(sb);

        return out;
    }

    public static String deleteSpaceCharacter(String value) {

        StringBuilder sb = new StringBuilder(value);

        Pattern p = Pattern.compile("\\s");
        Matcher n = p.matcher(value);
        int x = 0;
        while (n.find()) {
            x++;
        }

        for (int y = 0; y < x; y++) {
            Matcher m = p.matcher(sb);
            while (m.find()) {
                System.out.println(m.start() + "  " + m.end());
                sb = sb.delete(m.start(), m.end());
                break;
            }
        }

        String out = new String(sb);

        return out;
    }
}
