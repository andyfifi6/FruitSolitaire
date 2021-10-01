package Controller;

import Model.SolitaireModel;
import View.MuButton;

import java.io.*;

public class SolitaireController {

    SolitaireModel myModel;
    int xSelected = -1;
    int ySelected = -1;
    FileWriter fw;
    File un = new File("files/undo.txt");
    File sol = new File("files/solution.txt");
    File diaUn = new File("files/diaUndo.txt");
    File diaSol = new File("files/diaSolu.txt");
    File pyUn = new File("files/pyUndo.txt");
    File pySol = new File("files/pySolu.txt");
    int idx = 0;



    public SolitaireController(String typeChosen) {

        myModel = new SolitaireModel(typeChosen);
        chooseFile(typeChosen);
    }

    private void chooseFile(String selectType) {
        if (selectType.equals("diamond")) {
            un = diaUn;
            sol = diaSol;
        } else if (selectType.equals("pyramid")) {
            un = pyUn;
            sol = pySol;
        }
    }

    public boolean validPos(int row, int col) {
        return myModel.validPos(row, col);
    }

    public int getScore() {
        return myModel.getScore();
    }

    public boolean validMove(int fromRow, int fromCol, int toRow, int toCol) {
        return myModel.validMove(fromRow, fromCol, toRow, toCol);
    }
    public void move(int fromRow, int fromCol, int toRow, int toCol) {
        myModel.move(fromRow, fromCol, toRow, toCol);
    }
    public char getCellInfo(int row, int col) {
        return myModel.map[row][col];
    }

    public void defaultSelect() {

        xSelected = -1;
        ySelected = -1;
    }
    public boolean noSelect() {
        return xSelected == -1 && ySelected == -1;
    }
    void writeFile(int f1,int f2,int t1,int t2) throws Exception
    {
        fw = new FileWriter (un,true);
        fw.write(""+f1+f2+t1+t2);
        fw.close();
    }

    public boolean gameOver() {
        return myModel.isGameOver();
    }

    public char getInfo(int i, int j) {
        return myModel.map[i][j];
    }

    public boolean undoB() {
        int from1 = -1;
        int from2 = -1;
        int to1 = -1;
        int to2 = -1;
        int len = (int) un.length();
        char[] trace = new char[len];
        StringBuffer st;

        try {
            FileReader fr = new FileReader(un);
            fr.read(trace);
            if (len > 3) {
                to2 = trace[len - 1] - 48;
                to1 = trace[len - 2] - 48;
                from2 = trace[len - 3] - 48;
                from1 = trace[len - 4] - 48;
                myModel.map[to1][to2] = '_';
                myModel.map[from1][from2] = 'O';
                myModel.map[(from1 + to1) / 2][(from2 + to2) / 2] = 'O';
            }
            else {
                return false;
            }
            fr.close();
            st = new StringBuffer(new String(trace));
            if (len > 3) {
                st = st.delete(len - 4, len);
            }
            FileWriter fw = new FileWriter(un, false);
            fw.write(st.toString());
            fw.close();

        } catch (IOException e) {
            return false;
        }
        if (from1 < 0 || from2 < 0 || to1 < 0 || to2 < 0) {
            return false;
        }
        return true;
    }

    public void chooseB(MuButton bt1) {
        if (!gameOver()) {
            if (noSelect()) {
                if (getInfo(bt1.x, bt1.y) == 'O') {
                    xSelected = bt1.x;
                    ySelected = bt1.y;
                    myModel.map[xSelected][ySelected] = 'S';
                }
            }else {
                if (validMove(xSelected, ySelected, bt1.x, bt1.y)) {
                    move(xSelected, ySelected, bt1.x, bt1.y);


                    try {
                        writeFile(xSelected, ySelected, bt1.x, bt1.y);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        defaultSelect();
                    }
                } else {
                    if (xSelected != -1 && ySelected != -1) {
                        myModel.map[xSelected][ySelected] = 'O';
                    }
                }
                defaultSelect();
            }

        }
    }

    public void resetB() {
        while(undoB()) {
            continue;
        }
    }

    public void Solution(String colorChosen, String typeChosen) {
        new Solution(colorChosen, typeChosen);
    }

    public void nextB() {
        int len = (int) sol.length();
        if (idx + 4 > len) {
            return;
        }
        char[] trace = new char[len];
        try {
            FileReader fr = new FileReader(sol);
            fr.read(trace);
            int to2 = trace[idx + 3] - 48;
            int to1 = trace[idx + 2] - 48;
            int from2 = trace[idx + 1] - 48;
            int from1 = trace[idx] - 48;
            myModel.map[to1][to2] = 'O';
            myModel.map[from1][from2] = '_';
            myModel.map[(from1 + to1) / 2][(from2 + to2) / 2] = '_';
            fr.close();
            idx += 4;
        } catch (IOException e) {
            return;
        }

    }

    public void preB() {
        int len = (int) sol.length();
        if (idx - 4 < 0) {
            return;
        }
        char[] trace = new char[len];
        try {
            FileReader fr = new FileReader(sol);
            fr.read(trace);
            int to2 = trace[idx - 1] - 48;
            int to1 = trace[idx - 2] - 48;
            int from2 = trace[idx - 3] - 48;
            int from1 = trace[idx - 4] - 48;
            myModel.map[to1][to2] = '_';
            myModel.map[from1][from2] = 'O';
            myModel.map[(from1 + to1) / 2][(from2 + to2) / 2] = 'O';
            fr.close();
            idx -= 4;
        } catch (IOException ignored) {
        }

    }

    public void helpB() {
        new helpRule();
    }

    public void exitGameB(){
        System.exit(0);
    }
}
