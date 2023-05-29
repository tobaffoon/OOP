package ru.nsu.amazyar.leaderboard;

public class LeaderboardEntry {
    public final static String SEPARATOR = ";";
    private final String name;
    private final int score;

    public LeaderboardEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return getName() + SEPARATOR + getScore();
    }
}
