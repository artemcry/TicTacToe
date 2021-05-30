package com.example.tictactoe;

public class Player {
    enum MoveResult {
        Winner,
        Error,
        Draw,
        Success
    }

    protected Mark mark;
    protected Board board;

    Player(Mark mark) {
        setMark(mark);
    }

    MoveResult move(int column, int row) {
        if (board.get(column, row) != Mark.BLANK)
            return MoveResult.Error;
        board.set(column, row, mark);
        return checkMove(mark);
    }

    protected MoveResult checkMove(Mark m) {
        if (board.checkWin(m))
            return MoveResult.Winner;

        if (board.isBoardFilled())
            return MoveResult.Draw;

        return MoveResult.Success;
    }

    Board getBoard() {
        return board;
    }

    void setBoard(Board board) {
        this.board = board;
    }

    Mark getMark() {
        return mark;
    }

    void setMark(Mark mark) {
        this.mark = mark;
    }


}
