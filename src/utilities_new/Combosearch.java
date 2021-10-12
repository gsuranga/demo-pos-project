/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities_new;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * 
 */
public class Combosearch {

    private ArrayList<String> ar = new ArrayList<String>();
    JTextField txt;

    public void setSearchableCombo(final JComboBox cmb, boolean mustSort, final String noReultsText) {
        ar = new ArrayList<String>();
        final int s = cmb.getItemCount();
        for (int i = 0; i < s; i++) {
            boolean exists = false;
            for (int j = 0; j < ar.size(); j++) {
                if (ar.get(j).equalsIgnoreCase(cmb.getItemAt(i) + "")) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                ar.add(cmb.getItemAt(i) + "");
            }
        }
        if (mustSort) {
            Collections.sort(ar);
        }

        txt = (JTextField) cmb.getEditor().getEditorComponent();
        txt.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent evt) {
                int key = evt.getKeyCode();
                if (!(key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_ENTER || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_UP || key == KeyEvent.VK_BACK_SPACE)) {
                    String s = txt.getText();
                    int caret = txt.getCaretPosition();
                    cmb.hidePopup();
                    cmb.removeAllItems();
                    for (int i = 0; i < ar.size(); i++) {
                        if (ar.get(i).toUpperCase().startsWith(s.substring(0, caret).toUpperCase())) {
                            cmb.addItem(ar.get(i));
                        }
                    }
                    cmb.showPopup();
                    if (cmb.getItemCount() == 0) {
                        cmb.addItem(noReultsText);
                    }
                    //txt.setText(s);
                    txt.setText(cmb.getSelectedItem().toString());
                    txt.setCaretPosition(caret);
                } else if (key == KeyEvent.VK_ESCAPE) {
                    cmb.setSelectedItem(txt.getText());
                    cmb.hidePopup();
                } else if (key == KeyEvent.VK_ENTER && cmb.getSelectedIndex() == -1) {
                    if (cmb.getItemCount() == 1 && !cmb.getItemAt(0).equals(noReultsText)) {
                        cmb.setSelectedIndex(0);
                        cmb.transferFocus();
                    } else if (cmb.getItemCount() > 1) {
                        cmb.setSelectedIndex(0);
                    }
                }
            }
        });
        txt.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                txt.selectAll();
            }

        });
        txt.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                txt.selectAll();
            }

        });

    }
}
