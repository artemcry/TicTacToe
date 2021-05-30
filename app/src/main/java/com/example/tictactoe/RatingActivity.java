package com.example.tictactoe;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RatingActivity extends AppCompatActivity {
    private TableLayout t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        t = findViewById(R.id.ratingTable);

        if(Rating.getPlayers().isEmpty())
            return;

        int color = Color.rgb(180, 210, 237);

        Map<String, Rating.PlayerScore> sortedRating = sortMap(Rating.getPlayers());

        TableRow header = new TableRow(t.getContext());

        TextView name = new TextView(header.getContext());
        name.setText("Ім'я\t\t");
        name.setTextColor(color);

        TextView wins = new TextView(header.getContext());
        wins.setText("Перемоги\t\t");
        wins.setTextColor(color);

        TextView draws = new TextView(header.getContext());
        draws.setText("Нічиї\t\t");
        draws.setTextColor(color);

        TextView losses = new TextView(header.getContext());
        losses.setText("Поразки");
        losses.setTextColor(color);

        header.addView(name);
        header.addView(wins);
        header.addView(draws);
        header.addView(losses);
        t.addView(header);

        Object[] keys = sortedRating.keySet().toArray();
        for (Object k : keys) {
            TableRow tb = new TableRow(t.getContext());

            TextView tn = new TextView(tb.getContext());
            tn.setText(String.format("%s\t", k.toString()));
            tn.setTextColor(color);

            TextView tw = new TextView(tb.getContext());
            tw.setText(String.valueOf(sortedRating.get(k).wins));
            tw.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tw.setTextColor(color);

            TextView td = new TextView(tb.getContext());
            td.setText(String.valueOf(sortedRating.get(k).draws));
            td.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            td.setTextColor(color);

            TextView tl = new TextView(tb.getContext());
            tl.setText(String.valueOf(sortedRating.get(k).losses));
            tl.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tl.setTextColor(color);

            tn.setTextSize(20);
            tw.setTextSize(20);
            td.setTextSize(20);
            tl.setTextSize(20);

            tb.addView(tn);
            tb.addView(tw);
            tb.addView(td);
            tb.addView(tl);
            TableLayout.LayoutParams p = new TableLayout.LayoutParams();
            p.bottomMargin = 7;
            tb.setLayoutParams(p);

            t.addView(tb);
        }
    }

    private static Map<String, Rating.PlayerScore> sortMap(Map<String, Rating.PlayerScore> unsortMap) {
        List<Map.Entry<String, Rating.PlayerScore>> list = new LinkedList<>(unsortMap.entrySet());
        Collections.sort(list, (o1, o2) -> Integer.compare(o2.getValue().wins, o1.getValue().wins));

        Map<String, Rating.PlayerScore> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Rating.PlayerScore> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public void clearRating(View v) {
        Rating.clear();
        System.out.println(t == null);
        t.removeAllViews();
    }

    public void showMenu(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}