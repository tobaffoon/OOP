package ru.nsu.amazyar.pizzeria;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.amazyar.utils.PizzeriaJsonReader;
import ru.nsu.amazyar.utils.ThreadRunner;

public class PizzeriaTest {

    private static final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    /**
     * Changes standard output stream before each method.
     */
    @BeforeAll
    public static void setUpOut() {
        System.setOut(new PrintStream(out));
    }

    /**
     * Restores standard output system after each method.
     */
    @AfterAll
    public static void restoreOut() {
        System.setOut(originalOut);
    }

    /**
     * Tests if the whole system by Scanning logs.
     */
    @Test
    public void testLogs() throws IOException {
        ExecutorService pizzeriaThreads = Executors.newFixedThreadPool(4);
        Pizzeria pizzeria =
            PizzeriaJsonReader.readPizzeria("src/test/resources/pizzeriaConfigure.json");
        List<Thread> staff_threads = pizzeria.runPizzeriaStaff();

        List<Client> clients =
            PizzeriaJsonReader.readClients("src/test/resources/clientsConfigure.json");
        for (Client client : clients) {
            client.setPizzeria(pizzeria);
        }
        clients.get(0).setConsistentSleep(2000);
        clients.get(1).setConsistentSleep(4000);
        List<Thread> clients_threads = ThreadRunner.createAndRunThreads(clients, "Client");

        for (Thread staff_thread : staff_threads){
            pizzeriaThreads.submit(staff_thread);
        }
        for (Thread client_thread : clients_threads){
            pizzeriaThreads.submit(client_thread);
        }

        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        pizzeriaThreads.shutdown();
        String resultLogs = out.toString();

        // These are carefully calculated minimal volumes of logs that can be produced
        for (int i = 0; i <= 8; i++) {
            String ordered = "INFO - [" + i + "] ORDERED";
            Assertions.assertTrue(resultLogs.contains(ordered));
        }

        for (int i = 0; i <= 4; i++) {
            String cooking = "INFO - [" + i + "] COOKING";
            Assertions.assertTrue(resultLogs.contains(cooking));
        }

        for (int i = 0; i <= 3; i++) {
            String stored = "INFO - [" + i + "] STORED";
            Assertions.assertTrue(resultLogs.contains(stored));
        }

        for (int i = 0; i <= 3; i++) {
            String delivering = "INFO - [" + i + "] DELIVERING";
            Assertions.assertTrue(resultLogs.contains(delivering));
        }

        for (int i = 0; i <= 1; i++) {
            String delivered = "INFO - [" + i + "] DELIVERED";
            Assertions.assertTrue(resultLogs.contains(delivered));
        }
    }
}
