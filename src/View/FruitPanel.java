package View;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;

public class FruitPanel extends JPanel {
    private final Image backGroundImage;
    public FruitPanel(Image backGroundImage) {
        super();
        this.backGroundImage = backGroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGroundImage, 0, 0, null);
    }
}
