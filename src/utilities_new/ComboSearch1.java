/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities_new;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Kavinda
 */
public class ComboSearch1 {

    private static ArrayList<Object> obectArrayList;
    private static JTextField textfield1;

    public ComboSearch1() {
    }

    public static void setSearchableComboBox(final JComboBox comboBox, final String message) {
        obectArrayList = new ArrayList<>();
        final int count = comboBox.getItemCount();
        for (int i = 0; i < count; i++) {
            obectArrayList.add(comboBox.getItemAt(i).toString());
        }

        comboBox.setLightWeightPopupEnabled(true);

        textfield1 = (JTextField) comboBox.getEditor().getEditorComponent();
        textfield1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        comboBox.setSelectedItem(textfield1.getText());
                        comboBox.hidePopup();
                        if (comboBox.getSelectedIndex() == -1) {
                            if (comboBox.getItemCount() == 1 && !comboBox.getItemAt(0).equals(message)) {
                                comboBox.setSelectedIndex(0);
                            } else if (comboBox.getItemCount() > 1) {
                                comboBox.setSelectedIndex(0);
                            }
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_DOWN:
                        break;
                    default:
                        if (comboBox.getSelectedIndex() == -1) {
                            if (comboBox.getItemCount() == 1 && !comboBox.getItemAt(0).equals(message)) {
                                comboBox.setSelectedIndex(0);
                            } else if (comboBox.getItemCount() > 1) {
                                comboBox.setSelectedIndex(0);
                            }
                        } else {
                            String text = textfield1.getText();
                            int caret = textfield1.getCaretPosition();
                            comboBox.hidePopup();
                            comboBox.removeAllItems();
                            for (int i = 0; i < obectArrayList.size(); i++) {
                                if (obectArrayList.get(i).toString().toUpperCase().startsWith(text.substring(0, caret).toUpperCase())) {
                                    comboBox.addItem(obectArrayList.get(i));
                                }
                            }
                            comboBox.showPopup();
                            if (comboBox.getItemCount() == 0) {
                                comboBox.addItem(message);
                            }
                            textfield1.setText(text);
                            textfield1.setCaretPosition(caret);
                        }
                }
            }
        });

        textfield1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                comboBox.setEditable(false);
            }
        });
        comboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                comboBox.setEditable(true);
                textfield1.requestFocus();
            }
        });
    }
}
