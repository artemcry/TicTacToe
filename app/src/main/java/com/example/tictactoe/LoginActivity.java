package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private static String playerAName = "";
    private static String playerBName = "";
    private EditText editA;
    private EditText editB;

    static Class nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editA = findViewById(R.id.playerAName);
        editB = findViewById(R.id.playerBName);
        editA.setText(playerAName);
        editB.setText(playerBName);

        findViewById(R.id.errorText).setVisibility(TextView.GONE);

        if (MainActivity.getCurrentMode() == MainActivity.Mode.OnePlayer) {
            findViewById(R.id.bPlayerTextView).setVisibility(TextView.GONE);
            findViewById(R.id.playerBName).setVisibility(TextView.GONE);
            findViewById(R.id.bottomSpacer).setVisibility(Space.GONE);
        }
    }

    public void play(View v) {
        String textA = editA.getText().toString();
        String textB = editB.getText().toString();
        TextView error = findViewById(R.id.errorText);

        if (textA.trim().isEmpty() || (MainActivity.getCurrentMode() == MainActivity.Mode.TwoPlayer && textB.trim().isEmpty())) {
            error.setVisibility(TextView.VISIBLE);
            error.setText("Введіть ім'я");
            error.postDelayed(new Runnable() {
                @Override
                public void run() {
                    error.setVisibility(TextView.GONE);
                }
            }, 1000);
        } else if (textA.equals(textB)) {
            error.setVisibility(TextView.VISIBLE);
            error.setText("Введіть різні імена");
            error.postDelayed(new Runnable() {
                @Override
                public void run() {
                    error.setVisibility(TextView.GONE);
                }
            }, 1000);
        } else {
            playerAName = editA.getText().toString().trim();
            playerBName = editB.getText().toString().trim();

            startActivity(new Intent(this, nextActivity));
        }
    }

    public static String getPlayerAName() {
        return playerAName;
    }

    public static String getPlayerBName() {
        return playerBName;
    }
}
