package cn;


import javax.swing.*;
import java.awt.*;

public class DotComponent extends JComponent {
    public DotComponent(int x, int y, int size) {
        super();
        setLocation(x, y);
        setSize(size, size);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.fillOval(0, 0, getWidth(), getHeight());
    }
}
