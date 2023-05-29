package ru.nsu.amazyar;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.amazyar.leaderboard.LeaderboardManager;

public class SnakeTest {
    private final LeaderboardManager testManager = new LeaderboardManager();

    @BeforeAll
    public static void init(){
    }

    @Test
    public void testLeaderboardManager() throws IOException {
        testManager.loadLeaderboardFromFile("src/test/resources/test_leaderboard.txt");
        String leaderboardContent = """
            NAME | SCORE
            ONE;1
            TWO;2
            THREE;3
            FIVE;5
            """;
        Assertions.assertEquals(testManager.getLeaderboard(), leaderboardContent);
    }
}
