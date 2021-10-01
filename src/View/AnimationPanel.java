package View;

import Controller.Constant;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;



public class AnimationPanel extends JPanel {

    private ArrayList<JPanel> movingObjects = new ArrayList<>();
    private int width;
    private int height;

    public AnimationPanel(int width, int height, ArrayList<ImageIcon> iconList, boolean reverse) {
        this.width = width;
        this.height = height;
        setBackground(Constant.BackgroundColor);
        setLayout(null);
        for (ImageIcon icon : iconList) {
            JPanel panel = new JPanel();
            JLabel fruit = new JLabel(icon);
            panel.setBackground(Constant.BackgroundColor);
            panel.add(fruit);
            add(panel);
            movingObjects.add(panel);
        }
        Dimension size = getPreferredSize();


        int delta = size.width / movingObjects.size();
        for (int i = 0; i < movingObjects.size(); i++) {
            int fromWidth = delta * i;
            int toWidth = delta * i + size.width;
            Rectangle from = new Rectangle(fromWidth, 5, 105, 105);
            Rectangle to = new Rectangle(toWidth, 5, 105, 105);
            Animate animate = new Animate(movingObjects.get(i), from, to, reverse);
            animate.start();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public static class Animate {

        public static final int RUN_TIME = 3000;
    
        private JPanel panel;
        private Rectangle from;
        private Rectangle to;
        private boolean reverse;
    
        private long startTime;
    
        public Animate(JPanel panel, Rectangle from, Rectangle to, boolean reverse) {
            this.panel = panel;
            this.from = from;
            this.to = to;
            this.reverse = reverse;
        }
    
        public void start() {
            Timer timer = new Timer(0, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    long duration = System.currentTimeMillis() - startTime;
                    double progress = ((double)duration % (double)RUN_TIME) / (double)RUN_TIME;
                    if (reverse) {
                        progress = 1.0d - progress;
                    }
                    Rectangle target = calculateRectangle(from, to, progress);
                    panel.setBounds(target);
                }
            });
            timer.setRepeats(true);
            timer.setCoalesce(true);
            timer.setInitialDelay(0);
            startTime = System.currentTimeMillis();
            timer.start();
            timer.restart();
        }
    
        public Rectangle calculateRectangle(Rectangle startBounds, Rectangle targetBounds, double progress) {
    
            Rectangle bounds = new Rectangle();
    
            if (startBounds != null && targetBounds != null) {
    
                bounds.setLocation(calculateRectangle(startBounds.getLocation(), targetBounds.getLocation(), progress));
                bounds.setSize(calculateRectangle(startBounds.getSize(), targetBounds.getSize(), progress));
    
            }
    
            return bounds;
    
        }
    
        public Point calculateRectangle(Point startPoint, Point targetPoint, double progress) {
    
            Point point = new Point();
    
            if (startPoint != null && targetPoint != null) {
    
                point.x = calculateRectangle(startPoint.x, targetPoint.x, progress);
                point.y = calculateRectangle(startPoint.y, targetPoint.y, progress);
    
            }
    
            return point;
    
        }
    
        public int calculateRectangle(int startValue, int endValue, double fraction) {
    
            int value = 0;
            int distance = endValue - startValue;
            value = (int)Math.round((double)distance * fraction);
            value += startValue;
    
            return value % 800;
        }
    
        public Dimension calculateRectangle(Dimension startSize, Dimension targetSize, double progress) {
    
            Dimension size = new Dimension();
    
            if (startSize != null && targetSize != null) {
    
                size.width = calculateRectangle(startSize.width, targetSize.width, progress);
                size.height = calculateRectangle(startSize.height, targetSize.height, progress);
    
            }
    
            return size;
    
        }
    
    }
    
}
