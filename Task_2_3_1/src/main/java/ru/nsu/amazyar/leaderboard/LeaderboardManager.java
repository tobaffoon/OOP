package ru.nsu.amazyar.leaderboard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages leaderboard's serialization and deserialization.
 */
public class LeaderboardManager {

    private static final String LEADERBOARD_FILE = "leaderboard.txt";
    private final List<LeaderboardEntry> leaderboard;

    /**
     * Create empty leaderboard.
     */
    public LeaderboardManager() {
        leaderboard = new ArrayList<>();
    }

    /**
     * Returns leaderboard table as a string.
     */
    public String getLeaderboard() {
        StringBuilder builder = new StringBuilder();
        builder.append("NAME | SCORE\n");
        leaderboard.forEach(entry -> builder.append(entry.toString()).append("\n"));
        return builder.toString();
    }

    /**
     * Add new entry to the leaderboard. Safes resulting leaderboard to designated file
     *
     * @throws IOException if problems with designated file occurred
     */
    public void addEntry(LeaderboardEntry entry) throws IOException {
        leaderboard.add(entry);
        sortLeaderboard();
        saveLeaderboardToFile();
    }

    /**
     * Empty leaderboard.
     */
    public void clear() {
        leaderboard.clear();
    }

    private void sortLeaderboard() {
        leaderboard.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));
    }

    private void saveLeaderboardToFile() throws IOException {
        File leaderboardFile = new File(LEADERBOARD_FILE);
        try (BufferedWriter output = new BufferedWriter(new FileWriter(leaderboardFile))) {
            for (LeaderboardEntry entry : leaderboard) {
                output.write(entry.getName() + LeaderboardEntry.SEPARATOR + entry.getScore());
                output.newLine();
            }
        }
    }

    /**
     * Load leaderboard from default file.
     *
     * @throws IOException if problems with default file occurred
     */
    public void loadLeaderboardFromFile() throws IOException {
        this.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(LeaderboardEntry.SEPARATOR);
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    LeaderboardEntry playerScore = new LeaderboardEntry(name, score);
                    leaderboard.add(playerScore);
                }
            }
        }
    }
}
