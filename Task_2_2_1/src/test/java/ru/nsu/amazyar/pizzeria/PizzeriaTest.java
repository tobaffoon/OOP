package ru.nsu.amazyar.pizzeria;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.amazyar.utils.PizzeriaJsonReader;
import ru.nsu.amazyar.utils.ThreadRunner;

/**
 * Test class for pizzeria and utils packages.
 */
public class PizzeriaTest {
    /**
     * Tests if the whole system by Scanning logs.
     */
    @Test
    public void testLogs() throws IOException {
        ExecutorService pizzeriaThreads = Executors.newFixedThreadPool(4);
        Pizzeria pizzeria =
            PizzeriaJsonReader.readPizzeria("src/test/resources/pizzeriaConfigure.json");

        List<Client> clients =
            PizzeriaJsonReader.readClients("src/test/resources/clientsConfigure.json");
        for (Client client : clients) {
            client.setPizzeria(pizzeria);
        }
        clients.get(0).setConsistentSleep(2000);
        clients.get(1).setConsistentSleep(4000);

        List<Thread> staffThreads = pizzeria.runPizzeriaStaff();
        for (Thread staffThread : staffThreads) {
            pizzeriaThreads.submit(staffThread);
        }

        List<Thread> clientsThreads = ThreadRunner.createAndRunThreads(clients, "Client");
        for (Thread clientThread : clientsThreads) {
            pizzeriaThreads.submit(clientThread);
        }

        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        pizzeriaThreads.shutdown();
        String cookedLogs = pizzeria.getReserveLogs();

        // These are carefully calculated minimal volumes of logs that can be produced
        for (int i = 0; i <= 8; i++) {
            String ordered = "[" + i + "] ORDERED";
            Assertions.assertTrue(cookedLogs.contains(ordered));
        }

        for (int i = 0; i <= 4; i++) {
            String cooking = "[" + i + "] COOKING";
            Assertions.assertTrue(cookedLogs.contains(cooking));
        }

        for (int i = 0; i <= 3; i++) {
            String stored = "[" + i + "] STORED";
            Assertions.assertTrue(cookedLogs.contains(stored));
        }

        for (int i = 0; i <= 3; i++) {
            String delivering = "[" + i + "] DELIVERING";
            Assertions.assertTrue(cookedLogs.contains(delivering));
        }

        for (int i = 0; i <= 1; i++) {
            String delivered = "[" + i + "] DELIVERED";
            Assertions.assertTrue(cookedLogs.contains(delivered));
        }
    }
}
