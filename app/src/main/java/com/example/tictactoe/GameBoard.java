package com.example.tictactoe;

import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class GameBoard {
    private final Button[][] table;

    GameBoard(TableLayout layout) {
        table = new Button[Board.WIDTH][Board.WIDTH];
        for (int i = 0; i < Board.WIDTH; i++) {
            TableRow row = (TableRow) layout.getChildAt(i);
            for (int j = 0; j < Board.WIDTH; j++)
                table[i][j] = (Button) row.getChildAt(j);
        }
    }

    Point getButtonCoord(Button b) {
        for (int i = 0; i < Board.WIDTH; i++)
            for (int j = 0; j < Board.WIDTH; j++)
                if (table[i][j] == b)
                    return new Point(i, j);
        return null;
    }

    void clear() {
        for (int i = 0; i < Board.WIDTH; i++)
            for (int j = 0; j < Board.WIDTH; j++)
                table[i][j].setText("");
    }

    Button getCellAt(int col, int row) {
        return table[col][row];
    }


    void setCellAt(int col, int row, Mark m) {
        table[col][row].setText(m == Mark.X ? "X" : "O");
    }
}
