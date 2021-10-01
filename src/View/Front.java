package View;

import Controller.Constant;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Front extends JFrame implements ActionListener{

    JPanel menuPanel, titlePanel, namePanel, optionPanel;
    JButton click;
    MuButton orangeClick, kiwiClick, cocoClick, lemonClick, pineappleClick, crossClick, diamondClick, pyramidClick;
    MuButton[][] optionBoard;
    JLabel title, title2;
    JLabel w1;
    JTextField t1;
    String lastChosenFruit = "No", lastChosenMode = "No";
    Clip clip;

    Front() {
        super("Fruit Solitaire");

        setLayout(new BorderLayout());

        // top and down animation
        ArrayList<ImageIcon> colorList = new ArrayList<>();
        colorList.add(Constant.kiwi);
        colorList.add(Constant.coco);
        colorList.add(Constant.lemon);
        colorList.add(Constant.watermelon);
        colorList.add(Constant.orange);
        add(new AnimationPanel(800, 110, colorList, false), BorderLayout.NORTH);
        add(new AnimationPanel(800, 110, colorList, true), BorderLayout.SOUTH);

        // center
        menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());

        titlePanel = new JPanel();

        namePanel = new JPanel();
        namePanel.setLayout(new BorderLayout());
        title = new JLabel("      Fruit");
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 110));
        title.setForeground(Constant.iconColor);
        namePanel.add(title, BorderLayout.NORTH);

        title2 = new JLabel("    Solitaire");
        title2.setFont(new Font("Comic Sans MS", Font.BOLD, 110));
        title2.setForeground(Constant.iconColor);
        namePanel.add(title2, BorderLayout.SOUTH);
        namePanel.setBackground(Constant.BackgroundColor);
        menuPanel.add(namePanel, BorderLayout.NORTH);


        //Enter Username Section
        w1 = new JLabel("Enter Your Name");
        w1.setFont(Constant.iconFont);
        w1.setBackground(Constant.iconColor);
        w1.setForeground(Color.white);

        t1 = new JTextField(1);

        click = new JButton("Play Game");
        click.setFont(Constant.iconFont);
        click.setBackground(Constant.iconColor);
        click.setForeground(Color.white);
        click.setUI(new FrontPageButtonUI());
        click.addActionListener(this);

        optionPanel = new JPanel();
        optionPanel.setBackground(Constant.BackgroundColor);
        optionPanel.setLayout(new GridLayout(2,4));
        optionBoard = new MuButton[2][8];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                optionBoard[i][j] = new MuButton();
            }
        }
        optionBoard[0][1] = new MuButton("Fruit");
        optionBoard[0][1].setForeground(Constant.iconColor);
        optionBoard[0][1].setFont(Constant.iconFont);
        lemonClick = new MuButton(Constant.lemonSmall);
        kiwiClick = new MuButton(Constant.kiwiSmall);
        cocoClick = new MuButton(Constant.cocoSmall);
        orangeClick = new MuButton(Constant.orangeSmall);
        pineappleClick = new MuButton(Constant.pineappleSmall);

        optionBoard[0][2] = lemonClick;
        optionBoard[0][3] = kiwiClick;
        optionBoard[0][4] = cocoClick;
        optionBoard[0][5] = orangeClick;
        optionBoard[0][6] = pineappleClick;

        optionBoard[1][1] = new MuButton("Mode");
        optionBoard[1][1].setForeground(Constant.iconColor);
        optionBoard[1][1].setFont(Constant.iconFont);
        crossClick = new MuButton(Constant.crossSmall);
        diamondClick = new MuButton(Constant.diamondSmall);
        pyramidClick = new MuButton(Constant.pyramidSmall);
        optionBoard[1][2] = crossClick;
        optionBoard[1][3] = diamondClick;
        optionBoard[1][4] = pyramidClick;

        for (int j = 2; j < 7; j++) {
            optionBoard[0][j].addActionListener(this);
        }
        for (int j = 2; j < 5; j++) {
            optionBoard[1][j].addActionListener(this);
        }


        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                optionBoard[i][j].setOpaque(false);
                optionBoard[i][j].setFocusPainted(false);
                optionBoard[i][j].setBorderPainted(false);
                optionBoard[i][j].setContentAreaFilled(false);
                optionPanel.add(optionBoard[i][j]);
            }
        }
        menuPanel.add(optionPanel, BorderLayout.CENTER);
        JPanel clickPanel = new JPanel();
        clickPanel.setLayout(new GridLayout(1,5));
        clickPanel.setBackground(Constant.BackgroundColor);
        JButton[][] clickBoard = new JButton[1][5];
        for (int j = 0; j < 5; j++) {
            clickBoard[0][j] = new JButton();
        }
        clickBoard[0][2] = click;
        for (int j = 0; j < 5; j++) {
            clickBoard[0][j].setOpaque(false);
            clickBoard[0][j].setFocusPainted(false);
            clickBoard[0][j].setBorderPainted(false);
            clickBoard[0][j].setContentAreaFilled(false);
            clickPanel.add(clickBoard[0][j]);
        }
        menuPanel.add(clickPanel, BorderLayout.SOUTH);
        add(menuPanel, BorderLayout.CENTER);

        playMusic();
        setVisible(true);
        setResizable(false);
        setBounds(new Rectangle(200,50,800,800));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton begin = (JButton)e.getSource();
        if (begin == click) {
            if (!lastChosenFruit.equals("No") && (!lastChosenMode.equals("No"))) {
                SolitaireView v = new SolitaireView(lastChosenFruit, lastChosenMode, this);
                new Timer(v);
                setVisible(false);
            }
        } else {
            MuButton bt = (MuButton) e.getSource();
            if (bt == kiwiClick || bt == cocoClick || bt == lemonClick || bt == orangeClick || bt == pineappleClick) {

                fruitSelected(bt);
            } else if (bt == pyramidClick || bt == crossClick || bt == diamondClick) {
                modeSelected(bt);
            }
        }

    }

    private void modeSelected(MuButton bt) {
        optionBoard[1][2].changeImage(Constant.crossSmall);
        optionBoard[1][3].changeImage(Constant.diamondSmall);
        optionBoard[1][4].changeImage(Constant.pyramidSmall);
        if (bt == crossClick) {
            optionBoard[1][2].changeImage(Constant.cross);
            lastChosenMode = "cross";
        } else if (bt == diamondClick) {
            optionBoard[1][3].changeImage(Constant.diamond);
            lastChosenMode = "diamond";
        } else if (bt == pyramidClick) {
            optionBoard[1][4].changeImage(Constant.pyramid);
            lastChosenMode = "pyramid";
        }
    }

    private void fruitSelected(MuButton bt) {
        optionBoard[0][2].changeImage(Constant.lemonSmall);
        optionBoard[0][3].changeImage(Constant.kiwiSmall);
        optionBoard[0][4].changeImage(Constant.cocoSmall);
        optionBoard[0][5].changeImage(Constant.orangeSmall);
        optionBoard[0][6].changeImage(Constant.pineappleSmall);
        if (bt == lemonClick) {
            optionBoard[0][2].changeImage(Constant.lemon);
            lastChosenFruit = "lemon";
        } else if (bt == kiwiClick) {
            optionBoard[0][3].changeImage(Constant.kiwi);
            lastChosenFruit = "kiwi";
        } else if (bt == cocoClick) {
            optionBoard[0][4].changeImage(Constant.coco);
            lastChosenFruit = "coco";
        } else if (bt == orangeClick) {
            optionBoard[0][5].changeImage(Constant.orange);
            lastChosenFruit = "orange";
        } else if (bt == pineappleClick) {
            optionBoard[0][6].changeImage(Constant.pineapple);
            lastChosenFruit = "pineapple";
        }
    }

    public void playMusic(){
        try {
            File bgmFile = new File("music/Unity.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bgmFile);
            clip = AudioSystem.getClip();

            clip.open(audioIn);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Front();
    }
}
