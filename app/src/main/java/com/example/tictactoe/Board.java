package com.example.tictactoe;

import java.util.LinkedList;

import static com.example.tictactoe.Mark.*;

public class Board {

    private final Mark[][] board;
    public static final int WIDTH = 3;

    Board() {
        board = new Mark[WIDTH][WIDTH];
        clear();
    }

    private int getWinPositionBin(Mark m) {
        int[] wins = {7, 56, 448, 73, 146, 292, 273, 84};
        int score = 0, w = WIDTH;

        for (int i = 0; i < w; i++)
            for (int j = 0; j < w; j++)
                if (get(i, j) == m)
                    score |= 1 << (i * w + j);

        for (int win : wins)
            if ((win & score) == win)
                return win;
        return 0;
    }

    LinkedList<Point> getWinPosition(Mark m) {
        LinkedList<Point> points = new LinkedList<>();
        StringBuffer s = new StringBuffer(Integer.toBinaryString(getWinPositionBin(m)));

        s.reverse();
        int l = Board.WIDTH*Board.WIDTH - s.length();
        for (int i = 0; i < l; i++)
            s.append('0');

        for (int y = 0; y < Board.WIDTH; y++)
            for (int x = 0; x < Board.WIDTH; x++)
                if (s.charAt(y * Board.WIDTH + x) == '1')
                    points.add(new Point(y, x));

        return points;
    }

    void clear() {
        for (int col = 0; col < WIDTH; col++) {
            for (int row = 0; row < WIDTH; row++) {
                board[col][row] = BLANK;
            }
        }
    }

    boolean isBoardFilled() {
        for (int col = 0; col < WIDTH; col++)
            for (int row = 0; row < WIDTH; row++)
                if (board[col][row] == BLANK)
                    return false;
        return true;
    }

    boolean checkWin(Mark m) {
        return getWinPositionBin(m) != 0;
    }

    boolean isCellEmpty(int column, int row) {
        return board[column][row] == BLANK;
    }

    Mark get(int column, int row) {
        return board[column][row];
    }

    void set(int column, int row, Mark newMark) {
        board[column][row] = newMark;
    }

}
