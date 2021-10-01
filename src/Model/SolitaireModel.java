package Model;

import java.util.Arrays;

public class SolitaireModel {
    int arm = 3;
    int sRow = 3;
    int sCol = 3;
    public char[][] map = new char[7][7];

    public SolitaireModel(String typeChosen) {
        if (typeChosen.equals("cross")) {
            setDefaultMap();
        } else if (typeChosen.equals("diamond")) {
            setDiamond();
        } else {
            setPyramid();

        }

    }

    private void setPyramid() {
        for (char[] row: this.map) {
            Arrays.fill(row, 'O');
        }
        map[0][2] = '_';
        map[0][3] = '_';
        map[0][4] = '_';
        map[1][2] = '_';
        map[1][4] = '_';
        map[2][0] = '_';
        map[2][1] = '_';
        map[2][5] = '_';
        map[2][6] = '_';
        map[3][0] = '_';
        map[3][6] = '_';
        map[5][2] = '_';
        map[5][3] = '_';
        map[5][4] = '_';
        map[6][2] = '_';
        map[6][3] = '_';
        map[6][4] = '_';

    }

    private void setDiamond() {
        for (char[] row: this.map) {
            Arrays.fill(row, 'O');
        }
        map[sRow][sCol] = '_';
        map[0][2] = '_';
        map[0][4] = '_';
        map[2][0] = '_';
        map[2][6] = '_';
        map[4][0] = '_';
        map[4][6] = '_';
        map[6][2] = '_';
        map[6][4] = '_';

    }

    private void setDefaultMap() {
        for (char[] row: this.map) {
            Arrays.fill(row, 'O');
        }
        map[sRow][sCol] = '_';
    }


    public boolean validPos(int row, int col) {
        int limit = (this.arm + 1) / 2;
        int totalArm = 2 * this.arm;
        if (((0 <= row && row < limit) || (totalArm - limit < row && row <= totalArm))
                && (limit <= col && col <= totalArm - limit)) {
            return true;
        }
        // middle part
        return (limit <= row) && (row <= totalArm - limit) && (0 <= col) && (col <= totalArm);
    }

    public void move(int fromRow, int fromCol, int toRow, int toCol) {
        if (validMove(fromRow, fromCol, toRow, toCol) && map[fromRow][fromCol] == 'S') {
            map[fromRow][fromCol] = '_';
            map[(fromRow + toRow) / 2][(fromCol + toCol) / 2] = '_';
            map[toRow][toCol] = 'O';
        }
    }

    public boolean validMove(int fromRow, int fromCol, int toRow, int toCol) {

        if (validPos(fromRow, fromCol) && validPos(toRow, toCol)
                && (this.map[fromRow][fromCol] == 'O' || this.map[fromRow][fromCol] == 'S') && this.map[toRow][toCol] == '_') {
            return (Math.abs(fromRow - toRow) == 2 && fromCol == toCol && this.map[(fromRow + toRow) / 2][fromCol] == 'O')
                    || (Math.abs(fromCol - toCol) == 2 && fromRow == toRow && this.map[fromRow][(fromCol + toCol) / 2] == 'O');
        }
        return false;
    }

    public boolean isGameOver() {
        for (int i = 0; i < arm * 2 + 1; i++) {
            for (int j = 0; j < arm * 2 + 1; j++) {
                if (this.validPos(i, j)) {
                    int[][] dir = {{0, 2}, {2, 0}, {0, -2}, {-2, 0}};
                    for (int[] d: dir) {
                        if (this.validMove(i, j, i + d[0], j + d[1])) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public String getGameState() {
        int limit = (this.arm + 1) / 2;
        int totalArm = 2 * this.arm;
        StringBuilder state = new StringBuilder();
        int i;
        for (i = 0; i < limit; i++) {
            state.append(getLine(i, totalArm, limit, totalArm - limit));
        }
        for (i = limit; i <= totalArm - limit; i++) {
            state.append(getLine(i, totalArm, 0, totalArm));
        }
        for (i = totalArm - limit + 1; i <= totalArm; i++) {
            state.append(getLine(i, totalArm, limit, totalArm - limit));
        }
        state.setLength(state.length() - 1);
        return state.toString();
    }

    private String getLine(int line, int totalArm, int beg, int end) {
        StringBuilder newLine = new StringBuilder();
        int j = 0;
        while (j < beg) {
            newLine.append("  ");
            j++;
        }
        while (j < end) {
            newLine.append(map[line][j]).append(" ");
            j++;
        }
        newLine.append(map[line][end]);
        j++;
        while (j <= totalArm) {
            newLine.append("  ");
            j++;
        }
        newLine.append("\n");
        return newLine.toString();
    }

    public int getScore() {
        int score = 0;
        for (int i = 0; i < arm * 2 + 1; i++) {
            for (int j = 0; j < arm * 2 + 1; j++) {
                if (this.validPos(i, j) && (this.map[i][j] == 'O' || this.map[i][j] == 'S')) {
                    score += 1;
                }
            }
        }
        return score;
    }
}




