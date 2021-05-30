package com.example.tictactoe;

import android.app.Activity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class Rating {

    public static class PlayerScore implements Serializable {
        int wins;
        int draws;
        int losses;

        public PlayerScore(int wins, int draws, int losses) {
            this.wins = wins;
            this.draws = draws;
            this.losses = losses;
        }
    }
    private static Activity activity;

    private static HashMap<String, PlayerScore> players = null;

    public static void Initialize(Activity a) {
        activity = a;
        if (players == null || players.isEmpty())
            try {
                FileInputStream fileInput = new FileInputStream(activity.getFilesDir() + "/rating.save");
                ObjectInputStream objectInput = new ObjectInputStream(fileInput);
                players = (HashMap) objectInput.readObject();
                objectInput.close();
                fileInput.close();
            } catch (IOException | ClassNotFoundException obj1) {
                players = new HashMap<>();
            }
    }

    public static void setPlayerScore(String name, int wins, int draws, int losses) {
        if (players.containsKey(name)) {
            PlayerScore o = players.get(name);
            o.wins += wins;
            o.draws += draws;
            o.losses += losses;
        } else
            players.put(name, new PlayerScore(wins, draws, losses));
    }

    public static void clear() {
        players.clear();
        save();
    }

    public static void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream(activity.getFilesDir() + "/rating.save");
            ObjectOutputStream streamOut = new ObjectOutputStream(fileOut);
            streamOut.writeObject(players);
            streamOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, PlayerScore> getPlayers() {
        return players;
    }
}
