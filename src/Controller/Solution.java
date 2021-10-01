package Controller;

import View.FruitPanel;
import View.MuButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Solution extends JFrame implements ActionListener {

    JPanel p1, p2, p3;
    JButton pre, next, exit;
    MuButton[][] board;
    ImageIcon occupy = Constant.lemon;
    ImageIcon empty = Constant.hole;
    ImageIcon select = Constant.watermelon;
    ImageIcon backgroundImage = new ImageIcon("images/background.jpg");

    ImageIcon kiwiOccupy = Constant.kiwi;
    ImageIcon cocoOccupy = Constant.coco;
    ImageIcon pineappleOccupy = Constant.pineapple;
    ImageIcon orangeOccupy = Constant.orange;

    SolitaireController myController;
    JLabel marbleLeft;

    Solution(String fruit, String typeChosen) {
        super("Fruit Solitaire Solution");

        changeStyle(fruit);
        // Layout using NORTH, CENTER and SOUTH
        setLayout(new BorderLayout());

        // Three parts
        p1 = new JPanel();
        p2 = new FruitPanel(backgroundImage.getImage());
        p3 = new JPanel();


        // 1. Upper part (function bottoms)
        p1.setLayout(new FlowLayout());
        pre = new JButton("PREVIOUS STEP");
        next =new JButton("NEXT STEP");
        exit = new JButton("EXIT SOLUTION");

        pre.addActionListener(this);
        next.addActionListener(this);
        exit.addActionListener(this);

        p1.add(pre);
        p1.add(next);
        p1.add(exit);

        add(p1, BorderLayout.NORTH);

        // 2. Middle part (game part)
        myController = new SolitaireController(typeChosen);
        p2.setLayout(new GridLayout(7, 7));
        board = new MuButton[7][7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (myController.validPos(i, j) && myController.getCellInfo(i, j) == '_') {
                    board[i][j] = new MuButton(i, j, empty);
                    board[i][j].setOpaque(false);
                    board[i][j].setFocusPainted(false);
                    board[i][j].setBorderPainted(false);
                    board[i][j].setContentAreaFilled(false);
                    board[i][j].addActionListener(this);
                }
                else if (myController.validPos(i, j) && myController.getCellInfo(i, j) == 'O') {
                    board[i][j] = new MuButton(i, j, occupy);
                    board[i][j].setOpaque(false);
                    board[i][j].setFocusPainted(false);
                    board[i][j].setBorderPainted(false);
                    board[i][j].setContentAreaFilled(false);
                    board[i][j].addActionListener(this);

                }
                else {
                    board[i][j] = new MuButton(i, j);
                    board[i][j].setOpaque(false);
                    board[i][j].setContentAreaFilled(false);
                    board[i][j].setBorderPainted(false);
                    board[i][j].setEnabled(false);
                }
                p2.add(board[i][j]);
            }
        }
        add(p2, BorderLayout.CENTER);


        // 3. Lower part (status part)

        marbleLeft = new JLabel("");

        marbleLeft.setSize(300, 20);
        setMarbleLeft();

        p3.setLayout(new GridLayout(0, 1));
        p3.add(marbleLeft);
        add(p3, BorderLayout.SOUTH);

        // set the size of frame
        setSize(800,800);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBounds(new Rectangle(400,50,800,800));

    }

    private void changeStyle(String fruit) {
        switch (fruit) {
            case "kiwi" -> occupy = kiwiOccupy;
            case "coco" -> occupy = cocoOccupy;
            case "pineapple" -> occupy = pineappleOccupy;
            case "orange" -> occupy = orangeOccupy;
        }
    }

    void setMarbleLeft() {
        marbleLeft.setText("Marble Left =" + myController.getScore());
    }

    private void updateMap() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (myController.validPos(i, j) && myController.getCellInfo(i, j) == '_') {
                    board[i][j].changeImage(empty);
                }
                else if (myController.validPos(i, j) && myController.getCellInfo(i, j) == 'O') {
                    board[i][j].changeImage(occupy);
                }
                else if (myController.validPos(i, j) && myController.getCellInfo(i, j) == 'S') {
                    board[i][j].changeImage(select);
                }
            }
        }
        setMarbleLeft();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bt = (JButton) e.getSource();
        if (bt == next) {
            myController.nextB();
        } else if (bt == pre) {
            myController.preB();
        } else if (bt == exit) {
            setVisible(false);
        }
        updateMap();
    }
}
