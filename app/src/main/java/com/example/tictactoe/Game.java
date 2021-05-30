package com.example.tictactoe;

public class Game {
    private final Board board;
    private Player pFirst;
    private Player pSecond;
    private int draws;
    private int pFirstWins;
    private int pSecondWins;

    Game() {
        board = new Board();
    }

    void NewGame(Player p1, Player p2) {

        board.clear();
        pFirst = p1;
        pSecond = p2;
        pFirst.setBoard(board);
        pSecond.setBoard(board);
        resetCounters();
    }

    void saveRating() {
        Rating.setPlayerScore(LoginActivity.getPlayerAName(), firstPlayerWinsCount(), drawsCount(), pSecondWins);
        String name = "ШI(" + MainActivity.getDifficultyLevelAsWord(MainActivity.getCurrentDifficulty()) + ")";

        if (MainActivity.getCurrentMode() == MainActivity.Mode.TwoPlayer)
            name = LoginActivity.getPlayerBName();
        Rating.setPlayerScore(name, secondPlayerWinsCount(), drawsCount(), pFirstWins);
        Rating.save();
    }

    void newRound() {
        board.clear();
    }

    void resetCounters() {
        draws = pFirstWins = pSecondWins = 0;
    }

    void increaseDraws() {
        draws++;
    }

    void increaseFirstPlayerWins() {
        pFirstWins++;
    }

    void increaseSecondPlayerWins() {
        pSecondWins++;
    }

    public int firstPlayerWinsCount() {
        return pFirstWins;
    }

    public int secondPlayerWinsCount() {
        return pSecondWins;
    }

    public int drawsCount() {
        return draws;
    }

    public Board getBoard() {
        return board;
    }
}
