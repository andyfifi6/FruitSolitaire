package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class helpRule extends JFrame implements ActionListener {
    JButton ok;
    JPanel p1, p2;
    JLabel info;
    ImageIcon ruleImg = new ImageIcon("images/rules.png");


    helpRule(){
        super("Help");
        p1 = new JPanel();
        p2 = new JPanel();
        info = new JLabel(ruleImg);
        info.setPreferredSize(new Dimension(500,480));
        p1.add(info);
        ok = new JButton("OK");
        ok.addActionListener(this);
        p1.add(ok);
        p1.setBackground(Color.white);
        p2.setBackground(Color.white);

        add(p1);
        setVisible(true);
        setResizable(false);
        setBounds(new Rectangle(500,175,500,550));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
    }
}
