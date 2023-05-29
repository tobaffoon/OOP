package ru.nsu.amazyar;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.amazyar.leaderboard.LeaderboardEntry;
import ru.nsu.amazyar.leaderboard.LeaderboardManager;

public class SnakeTest {
    private final LeaderboardManager testManager = new LeaderboardManager("src/test/resources/test_leaderboard.txt");

    @BeforeAll
    public static void init(){
    }

    @Test
    public void testLeaderboardManager() throws IOException {
        testManager.loadLeaderboardFromFile();
        String leaderboardLoadContent = """
            NAME | SCORE
            FIVE;5
            THREE;3
            TWO;2
            ONE;1
            """;
        Assertions.assertEquals(testManager.getLeaderboard(), leaderboardLoadContent);

        testManager.addEntry(new LeaderboardEntry("SIX", 6));
        String leaderboardSaveContent = """
            NAME | SCORE
            SIX;6
            FIVE;5
            THREE;3
            TWO;2
            ONE;1
            """;
        Assertions.assertEquals(testManager.getLeaderboard(), leaderboardSaveContent);
    }
}
