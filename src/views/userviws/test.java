package views.userviws;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class test {

    private void display() {
        JFrame f = new JFrame("Top-Right Frame");
        f.add(new JPanel() {

            @Override // placeholder for actual content
            public Dimension getPreferredSize() {
                return new Dimension(320, 240);
            }

        });
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (int) rect.getMaxX() - screenSize.width;
        int y = 0;
     
     // f.setLocation(, 0);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new test().display();
            }
        });
    }
}
