package View;

import Controller.Constant;
import Controller.SolitaireController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SolitaireView extends JFrame implements ActionListener {
    JPanel p1, p2, p3;
    JButton undo, reset, help, solution, exitGame, back;
    MuButton[][] board;
    ImageIcon occupy = Constant.lemon;
    ImageIcon empty = Constant.hole;
    ImageIcon select = Constant.watermelon;
    ImageIcon backgroundImage = new ImageIcon("images/background.jpg");

    ImageIcon kiwiOccupy = Constant.kiwi;
    ImageIcon cocoOccupy = Constant.coco;
    ImageIcon pineappleOccupy = Constant.pineapple;
    ImageIcon orangeOccupy = Constant.orange;

    String panelFruit, panelType;
    private Front frontView;
    public SolitaireController myController;
    JLabel user, marbleLeft, time;


    public SolitaireView(String fruit, String typeChosen, Front frontView) {

        super("Fruit Solitaire");

        this.panelFruit = fruit;
        this.panelType = typeChosen;
        this.frontView = frontView;
        changeStyle(fruit);

        // Layout using NORTH, CENTER and SOUTH
        setLayout(new BorderLayout());

        // Three parts
        p1 = new JPanel();
        p2 = new FruitPanel(backgroundImage.getImage());
        p3 = new JPanel();

        // 1. Upper part (function bottoms)
        p1.setLayout(new FlowLayout());
        undo = new JButton("UNDO");
        reset =new JButton("RESET");
        help = new JButton("HELP");
        solution = new JButton("SOLUTION");
        exitGame = new JButton("EXIT");
        back = new JButton("BACK TO MENU");

        undo.addActionListener(this);
        reset.addActionListener(this);
        help.addActionListener(this);
        solution.addActionListener(this);
        exitGame.addActionListener(this);
        back.addActionListener(this);

        p1.add(undo);
        p1.add(reset);
        p1.add(solution);
        p1.add(help);
        p1.add(back);
        p1.add(exitGame);


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
        user = new JLabel("Welcome");
        marbleLeft = new JLabel("");
        time = new JLabel("                    00:00");
        user.setSize(100, 20);
        marbleLeft.setSize(300, 20);
        setMarbleLeft();
        time.setSize(100, 20);
        p3.setLayout(new GridLayout(0, 3));
        p3.add(user);
        p3.add(marbleLeft);
        p3.add(time);
        add(p3, BorderLayout.SOUTH);

        // set the size of frame
        setSize(800,800);
        setVisible(true);
        setResizable(false);

        //playMusic();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(new Rectangle(200,50,800,800));

    }

    private void changeStyle(String fruit) {
        switch (fruit) {
            case "kiwi" -> occupy = kiwiOccupy;
            case "coco" -> occupy = cocoOccupy;
            case "pineapple" -> occupy = pineappleOccupy;
            case "orange" -> occupy = orangeOccupy;
        }
    }

    void setTime (String s) {
        time.setText(s);
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

        JButton bt = (JButton)e.getSource();

        if (bt == undo) {
            myController.undoB();
        }else if (bt == reset) {
            myController.resetB();
        }else if (bt == help) {
            myController.helpB();
        }else if (bt == solution) {
            myController.Solution(panelFruit, panelType);
        }else if (bt == exitGame) {
            myController.exitGameB();
        }else if (bt == back) {
            myController.resetB();
            setVisible(false);
            //music.stop();
            this.frontView.setVisible(true);
            //new Front();
            this.dispose();
        }else {
            MuButton bt1 = (MuButton) e.getSource();
            myController.chooseB(bt1);

        }
        updateMap();
    }

}































