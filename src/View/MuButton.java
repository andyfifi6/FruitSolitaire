package View;

import javax.swing.*;

public class MuButton extends JButton {

    public int x;
    public int y;
    public MuButton(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public MuButton(int x, int y, ImageIcon img) {
        super(img);
        this.x = x;
        this.y = y;
    }
    public MuButton(ImageIcon img) {
        super(img);
    }

    public MuButton(String s) {
        super(s);
    }

    public MuButton() {
        super();
    }

    public void changeImage(ImageIcon img) {
        setIcon(img);
    }
}
