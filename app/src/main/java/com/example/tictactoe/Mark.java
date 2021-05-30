package com.example.tictactoe;

class Point {
    public int X, Y;

    Point(int Y, int X) {
        this.X = X;
        this.Y = Y;
    }
}

public enum Mark {
    X,
    O,
    BLANK
}
