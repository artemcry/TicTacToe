package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;


public class GameActivity extends AppCompatActivity {
    GameBoard gameBoard;
    Game game;
    Player P1;
    Player P2;
    TextView oCounter, xCounter, drawCounter;
    long lastClick;
    boolean moveFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameBoard = new GameBoard(findViewById(getResources().getIdentifier("table", "id", getPackageName())));
        xCounter = findViewById(R.id.score_x);
        drawCounter = findViewById(R.id.score_draw);
        oCounter = findViewById(R.id.score_o);

        fitSizeButtons();
        P1 = new Player(Mark.X);

        if (MainActivity.getCurrentMode() == MainActivity.Mode.OnePlayer)
            P2 = new AIPlayer(Mark.O, P1.getMark(), MainActivity.getCurrentDifficulty());
        else
            P2 = new Player(Mark.O);

        game = new Game();
        game.NewGame(P1, P2);
    }

    public void clickButton(View v) {
        if (System.currentTimeMillis() - lastClick < 200)
            return;
        lastClick = System.currentTimeMillis();
        if (MainActivity.getCurrentMode() == MainActivity.Mode.OnePlayer)
            moveOnePlayer(v);
        else
            moveTwoPlayers(v);
    }

    void moveOnePlayer(View v) {
        Point p = gameBoard.getButtonCoord((Button) v);

        Player.MoveResult moveRes = P1.move(p.Y, p.X);

        if (moveRes == Player.MoveResult.Error)
            return;

        gameBoard.setCellAt(p.Y, p.X, P1.getMark());

        if (moveRes == Player.MoveResult.Draw) {
            game.increaseDraws();
            newRound(P1, moveRes);
        } else if (moveRes == Player.MoveResult.Winner) {
            game.increaseFirstPlayerWins();
            newRound(P1, moveRes);
        } else
            moveAi();
    }

    void moveTwoPlayers(View v) {
        Point p = gameBoard.getButtonCoord((Button) v);
        Player movePlayer = moveFirst ? P1 : P2;
        Player.MoveResult moveRes = movePlayer.move(p.Y, p.X);

        if (moveRes == Player.MoveResult.Error)
            return;

        gameBoard.setCellAt(p.Y, p.X, movePlayer.getMark());

        if (moveRes == Player.MoveResult.Draw)
            game.increaseDraws();
        else if (moveRes == Player.MoveResult.Winner) {
            if (moveFirst)
                game.increaseFirstPlayerWins();
            else
                game.increaseSecondPlayerWins();
        }

        if (moveRes == Player.MoveResult.Success)
            moveFirst = !moveFirst;
        else
            newRound(movePlayer, moveRes);

    }

    void moveAi() {
        AIPlayer.MoveInfo r = ((AIPlayer) P2).Move();
        gameBoard.setCellAt(r.poit.Y, r.poit.X, P2.getMark());
        if (r.result == Player.MoveResult.Draw) {
            game.increaseDraws();
            newRound(P2, r.result);
        } else if (r.result == Player.MoveResult.Winner) {
            game.increaseSecondPlayerWins();
            newRound(P2, r.result);
        }
    }

    void resetRound() {
        game.newRound();
        gameBoard.clear();
        updateCounters();
        moveFirst = !moveFirst;
        if (MainActivity.getCurrentMode() == MainActivity.Mode.OnePlayer && !moveFirst)
            moveAi();
    }

    void newRound(Player lastMovePlayer, Player.MoveResult lastMoveRes) {
        if (lastMoveRes == Player.MoveResult.Winner) {
            LinkedList<Point> w = game.getBoard().getWinPosition(lastMovePlayer.getMark());

            Button b1 = gameBoard.getCellAt(w.get(0).Y, w.get(0).X);
            Button b2 = gameBoard.getCellAt(w.get(1).Y, w.get(1).X);
            Button b3 = gameBoard.getCellAt(w.get(2).Y, w.get(2).X);

            int c1 = b1.getTextColors().getDefaultColor();
            int c2 = b2.getTextColors().getDefaultColor();
            int c3 = b3.getTextColors().getDefaultColor();

            int newC = Color.rgb(255, 89, 89);
            b1.setTextColor(newC);
            b2.setTextColor(newC);
            b3.setTextColor(newC);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    b1.setTextColor(c1);
                    b2.setTextColor(c2);
                    b3.setTextColor(c3);

                    resetRound();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }, 1000);
        } else if (lastMoveRes == Player.MoveResult.Draw) {
            drawCounter.setText("Нiчия");
            int c = drawCounter.getTextColors().getDefaultColor();
            drawCounter.setTextColor(Color.rgb(255, 89, 89));

            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawCounter.setTextColor(c);
                    resetRound();
                }
            }, 1000);
        } else
            resetRound();
    }

    @SuppressLint("DefaultLocale")
    void updateCounters() {
        xCounter.setText(String.format("X: %d", game.firstPlayerWinsCount()));
        drawCounter.setText(String.valueOf(game.drawsCount()));
        oCounter.setText(String.format("%d :O", game.secondPlayerWinsCount()));
    }

    public void resetRound(View v) {
        game.resetCounters();
        resetRound();
    }

    @Override
    protected void onStop() {
        super.onStop();
        game.saveRating();
    }

    void fitSizeButtons() {
        Display display = getWindowManager().getDefaultDisplay();
        android.graphics.Point size = new android.graphics.Point();
        display.getSize(size);
        int width = size.x / Board.WIDTH - 1;

        for (int y = 0; y < Board.WIDTH; y++) {
            for (int x = 0; x < Board.WIDTH; x++) {
                gameBoard.getCellAt(y, x).setWidth(width);
                gameBoard.getCellAt(y, x).setHeight(width);
            }
        }
    }

    public void backToMenu(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}