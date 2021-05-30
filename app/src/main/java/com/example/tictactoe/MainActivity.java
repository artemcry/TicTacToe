package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public enum Mode {
        OnePlayer,
        TwoPlayer
    }

    private static int currentModeIndex;
    private static int currentDiffIndex;

    private TextView modeText;
    private TextView diffText;

    private LinearLayout diffLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Rating.Initialize(this);

        setContentView(R.layout.activity_main);
        modeText = (TextView) findViewById(R.id.modeText);


        ((Button) findViewById(R.id.diff_l)).setText("<");
        ((Button) findViewById(R.id.diff_r)).setText(">");
        ((Button) findViewById(R.id.mode_l)).setText("<");
        ((Button) findViewById(R.id.mode_r)).setText(">");

        diffText = findViewById(R.id.diffText);
        diffLayout = findViewById(getResources().getIdentifier("diffLayout", "id", getPackageName()));

        updateMode(currentModeIndex);
        updateDiff(currentDiffIndex);
    }

    private void updateDiff(int diff) {
        currentDiffIndex = diff;
        diffText.setText(getDifficultyLevelAsWord(AIPlayer.DifficultyLevel.values()[currentDiffIndex]));
    }

    public static String getDifficultyLevelAsWord(AIPlayer.DifficultyLevel lvl) {
        switch (lvl) {
            case Easy:
                return "Легко";
            case Medium:
                return "Нормально";
            case Hard:
                return "Важко";
        }
        return null;
    }

    private void updateMode(int mode) {
        currentModeIndex = mode;

        Mode m = Mode.values()[mode];

        switch (m) {
            case OnePlayer:
                modeText.setText("Один Гравець");
                break;
            case TwoPlayer:
                modeText.setText("Двоє Гравців");
                break;
            default:
                break;
        }

        if (m == Mode.OnePlayer)
            diffLayout.setVisibility(LinearLayout.VISIBLE);
        else
            diffLayout.setVisibility(LinearLayout.GONE);
    }

    static Mode getCurrentMode() {
        return Mode.values()[currentModeIndex];
    }

    static AIPlayer.DifficultyLevel getCurrentDifficulty() {
        return AIPlayer.DifficultyLevel.values()[currentDiffIndex];
    }

    public void changeModeRight(View v) {
        updateMode((currentModeIndex + 1) % Mode.values().length);
    }

    public void changeModeLeft(View v) {
        updateMode((Mode.values().length + currentModeIndex - 1) % Mode.values().length);
    }

    public void changeDiffRight(View v) {
        updateDiff((currentDiffIndex + 1) % AIPlayer.DifficultyLevel.values().length);
    }

    public void changeDiffLeft(View v) {
        updateDiff((AIPlayer.DifficultyLevel.values().length + currentDiffIndex - 1) % AIPlayer.DifficultyLevel.values().length);
    }

    public void login(View v) {
        LoginActivity.nextActivity = MainActivity.class;
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void play(View v) {
        LoginActivity.nextActivity = GameActivity.class;

        if (LoginActivity.getPlayerAName().isEmpty() || (getCurrentMode() == Mode.TwoPlayer && LoginActivity.getPlayerBName().isEmpty()))
            startActivity(new Intent(this, LoginActivity.class));
        else
            startActivity(new Intent(this, GameActivity.class));
    }

    public void showRating(View v) {
        startActivity(new Intent(this, RatingActivity.class));
    }

}