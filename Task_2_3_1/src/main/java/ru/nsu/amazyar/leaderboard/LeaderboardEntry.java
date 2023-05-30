package ru.nsu.amazyar.leaderboard;

/**
 * Entry in leaderboard table.
 */
public class LeaderboardEntry {

    public final static String SEPARATOR = ";";
    private final String name;
    private final int score;

    /**
     * Create new leaderboard entry.
     *
     * @param name  player's name
     * @param score player's score
     */
    public LeaderboardEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Name getter.
     */
    public String getName() {
        return name;
    }

    /**
     * Score getter.
     */
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return getName() + SEPARATOR + getScore();
    }
}
