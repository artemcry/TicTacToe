package com.example.tictactoe;

import java.util.LinkedList;
import java.util.Random;

public class AIPlayer extends Player {
    enum DifficultyLevel {
        Easy,
        Medium,
        Hard
    }

    public static class MoveInfo {
        MoveInfo(MoveResult m, Point p) {
            result = m;
            poit = p;
        }

        public MoveResult result;
        public Point poit;
    }

    private Mark playerMark;
    private DifficultyLevel diff;
    private static final int MAX_DEPTH = 12;

    AIPlayer(Mark mark, Mark playerMark, DifficultyLevel diff) {
        super(mark);
        this.playerMark = playerMark;
        this.diff = diff;
    }

    MoveInfo Move() {
        Point move = null;
        switch (diff) {
            case Easy:
                move = getEasyMove(board);
                break;
            case Medium:
                move = getMidMove(board);
                break;
            case Hard:
                move = getHardMove(board);
                break;
        }
        board.set(move.Y, move.X, mark);
        return new MoveInfo(checkMove(mark), move);
    }

    private Point getEasyMove(Board board) {
        LinkedList<Point> answer = new LinkedList<>();
        for (int i = 0; i < Board.WIDTH; i++)
            for (int j = 0; j < Board.WIDTH; j++)
                if (board.get(i, j).equals(Mark.BLANK))
                    answer.add(new Point(i, j));

        if (answer.isEmpty())
            return null;

        return answer.get(Math.abs(new Random().nextInt() % answer.size()));
    }

    private Point getMidMove(Board board) {
        LinkedList<Point> wins = getWinPoint(mark);
        if (wins.isEmpty()) {
            wins = getWinPoint(playerMark);
            if (wins.isEmpty())
                return getEasyMove(board);
        }
        return wins.get(Math.abs(new Random().nextInt() % wins.size()));
    }

    private Point getHardMove(Board board) {
        Point bestMove = new Point(-1, -1);
        int bestValue = Integer.MIN_VALUE;

        for (int col = 0; col < Board.WIDTH; col++) {
            for (int row = 0; row < Board.WIDTH; row++) {
                if (board.isCellEmpty(col, row)) {
                    board.set(col, row, mark);
                    int moveValue = miniMax(MAX_DEPTH, Integer.MIN_VALUE,
                            Integer.MAX_VALUE, false);
                    board.set(col, row, Mark.BLANK);
                    if (moveValue > bestValue) {
                        bestMove.Y = col;
                        bestMove.X = row;
                        bestValue = moveValue;
                    }
                }
            }
        }
        return bestMove;
    }

    private int miniMax(int depth, int alpha, int beta, boolean isMax) {
        int boardVal = 0;

        if (board.checkWin(mark))
            boardVal = 10 + depth;
        else if (board.checkWin(playerMark))
            boardVal = -10 - depth;

        if (Math.abs(boardVal) > 0 || depth == 0 || board.isBoardFilled())
            return boardVal;

        if (isMax) {
            int highestVal = Integer.MIN_VALUE;
            for (int col = 0; col < Board.WIDTH; col++) {
                for (int row = 0; row < Board.WIDTH; row++) {
                    if (board.isCellEmpty(col, row)) {
                        board.set(col, row, mark);
                        highestVal = Math.max(highestVal, miniMax(depth - 1, alpha, beta, false));
                        board.set(col, row, Mark.BLANK);
                        alpha = Math.max(alpha, highestVal);
                        if (alpha >= beta)
                            return highestVal;
                    }
                }
            }
            return highestVal;
        } else {
            int lowestVal = Integer.MAX_VALUE;
            for (int col = 0; col < Board.WIDTH; col++) {
                for (int row = 0; row < Board.WIDTH; row++) {
                    if (board.isCellEmpty(col, row)) {
                        board.set(col, row, playerMark);
                        lowestVal = Math.min(lowestVal, miniMax(depth - 1, alpha, beta, true));
                        board.set(col, row, Mark.BLANK);
                        beta = Math.min(beta, lowestVal);

                        if (beta <= alpha)
                            return lowestVal;
                    }
                }
            }
            return lowestVal;
        }
    }

    private LinkedList<Point> getWinPoint(Mark mark) {
        LinkedList<Point> wins = new LinkedList<>();

        int[][] b = new int[Board.WIDTH][Board.WIDTH];

        for (int y = 0; y < Board.WIDTH; y++) {
            for (int x = 0; x < Board.WIDTH; x++) {
                if (board.get(y, x) == Mark.BLANK)
                    b[y][x] = 0;
                else if (board.get(y, x) == mark)
                    b[y][x] = 1;
                else
                    b[y][x] = -1;
            }
        }
        // check rows
        for (int y = 0; y < Board.WIDTH; y++) {
            if (b[y][0] + b[y][1] + b[y][2] == 2) {
                if (b[y][0] == 0)
                    wins.add(new Point(y, 0));
                else if (b[y][1] == 0)
                    wins.add(new Point(y, 1));
                else if (b[y][2] == 0)
                    wins.add(new Point(y, 2));
            }
        }
        // check columns
        for (int x = 0; x < Board.WIDTH; x++) {
            if (b[0][x] + b[1][x] + b[2][x] == 2) {
                if (b[0][x] == 0)
                    wins.add(new Point(0, x));
                else if (b[1][x] == 0)
                    wins.add(new Point(1, x));
                else if (b[2][x] == 0)
                    wins.add(new Point(2, x));
            }
        }
        // check diagonals
        if (b[0][0] + b[1][1] + b[2][2] == 2) {
            if (b[0][0] == 0)
                wins.add(new Point(0, 0));
            else if (b[1][1] == 0)
                wins.add(new Point(1, 1));
            else if (b[2][2] == 0)
                wins.add(new Point(2, 2));
        }
        if (b[0][2] + b[1][1] + b[2][0] == 2) {
            if (b[0][2] == 0)
                wins.add(new Point(0, 2));
            else if (b[1][1] == 0)
                wins.add(new Point(1, 1));
            else if (b[2][0] == 0)
                wins.add(new Point(2, 0));
        }
        return wins;
    }

}
