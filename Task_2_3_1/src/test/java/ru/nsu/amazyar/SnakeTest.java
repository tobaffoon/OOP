package ru.nsu.amazyar;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.amazyar.game_model.Game;
import ru.nsu.amazyar.leaderboard.LeaderboardEntry;
import ru.nsu.amazyar.leaderboard.LeaderboardManager;

/**
 * Main snake game test class.
 */
public class SnakeTest {
    private final LeaderboardManager testManager = new LeaderboardManager("src/test/resources/test_leaderboard.txt");

    /**
     * Test loading and saving leaderboard.
     */
    @Test
    public void testLeaderboardManager() throws IOException {
        testManager.clear();
        testManager.addEntry(new LeaderboardEntry("ONE", 1));
        testManager.addEntry(new LeaderboardEntry("TWO", 2));
        testManager.addEntry(new LeaderboardEntry("THREE", 3));
        testManager.addEntry(new LeaderboardEntry("FIVE", 5));
        String leaderboardLoadContent = """
            NAME | SCORE
            FIVE;5
            THREE;3
            TWO;2
            ONE;1
            """;
        Assertions.assertEquals(leaderboardLoadContent, testManager.getLeaderboard());

        testManager.addEntry(new LeaderboardEntry("SIX", 6));
        String leaderboardSaveContent = """
            NAME | SCORE
            SIX;6
            FIVE;5
            THREE;3
            TWO;2
            ONE;1
            """;
        Assertions.assertEquals(leaderboardSaveContent, testManager.getLeaderboard());
    }

    /**
     * Test immediate victory and lost.
     */
    @Test
    public void simpleGameTest(){
        Game testGame = new Game(2, 1, 1, 2, 0);
        testGame.update();
        System.out.println(testGame.getScore());
        Assertions.assertTrue(testGame.isGameWon());

        // bigger grid is required because bricks can spawn in 3x3 area near (0,0) point
        testGame = new Game(4, 4, 0, 3, 7);
        testGame.update();
        testGame.update();
        testGame.update();
        Assertions.assertTrue(testGame.isGameLost());
    }
}
