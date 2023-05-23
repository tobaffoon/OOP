package ru.nsu.amazyar.leaderboard;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.nsu.amazyar.SnakeApplication;

public class LeaderboardManager {
    private static final String LEADERBOARD_FILE = "leaderboard.txt";
    private List<LeaderboardEntry> leaderboard;

    public LeaderboardManager() {
        leaderboard = new ArrayList<>();
    }

    public String getLeaderboard() {
        StringBuilder builder = new StringBuilder();
        builder.append("NAME | SCORE\n");
        leaderboard.forEach(entry -> builder.append(entry.toString()).append("\n"));
        return builder.toString();
    }

    public void addEntry(LeaderboardEntry entry) {
        leaderboard.add(entry);
        sortLeaderboard();
        saveLeaderboardToFile();
    }

    private void sortLeaderboard() {
        leaderboard.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));
    }

    private void saveLeaderboardToFile() {
        File leaderboardFile = new File(LEADERBOARD_FILE);
        try (BufferedWriter output = new BufferedWriter(new FileWriter(leaderboardFile))) {
            for (LeaderboardEntry entry : leaderboard) {
                output.write(entry.getName() + ";" + entry.getScore());
                output.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLeaderboardFromFile() {
        leaderboard.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    LeaderboardEntry playerScore = new LeaderboardEntry(name, score);
                    leaderboard.add(playerScore);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
