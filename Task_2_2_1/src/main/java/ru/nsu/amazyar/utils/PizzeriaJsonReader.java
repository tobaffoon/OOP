package ru.nsu.amazyar.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.amazyar.pizzeria.Client;
import ru.nsu.amazyar.pizzeria.Pizzeria;

/**
 * Pizzeria and clients config reader.
 */
public class PizzeriaJsonReader {

    private static final ObjectMapper objectMapper = getDefaultObjectMapper();

    /**
     * Default pizzeria config path.
     */
    public static final String defaultPizzeriaPath = "src/main/resources/pizzeriaConfigure.json";

    /**
     * Default clients config path.
     */
    public static final String defaultClientsPath = "src/main/resources/clientsConfigure.json";

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();

        return defaultObjectMapper;
    }

    /**
     * Reads pizzeria config from File.
     *
     * @param str - file's path as String
     */
    public static Pizzeria readPizzeria(String str) throws IOException {
        return readPizzeria(new File(str));
    }

    /**
     * Reads pizzeria config from File.
     */
    public static Pizzeria readPizzeria(File file) throws IOException {
        JsonNode configuration = objectMapper.readTree(file);

        //-----Int fields deserialisation-----
        int maxOrders = configuration.get("maxOrders").asInt();
        int storageCapacity = configuration.get("storageCapacity").asInt();

        //-----Array fields deserialisation-----
        JsonNode trunkCapacitiesJson = configuration.get("truckCapacities");
        List<Long> truckCapacities = new ArrayList<>();
        for (JsonNode trunkCapacity : trunkCapacitiesJson) {
            truckCapacities.add(trunkCapacity.asLong());
        }

        JsonNode ordersPerMinuteJson = configuration.get("chefsOrderPerMinute");
        List<Long> chefsOrdersPerMinute = new ArrayList<>();
        for (JsonNode ordersPerMinute : ordersPerMinuteJson) {
            chefsOrdersPerMinute.add(ordersPerMinute.asLong());
        }

        Pizzeria pizzeria =
            new Pizzeria(maxOrders, storageCapacity, chefsOrdersPerMinute, truckCapacities);
        return pizzeria;
    }

    /**
     * Reads pizzeria config from File.
     *
     * @param str - file's path as String
     */
    public static List<Client> readClients(String str) throws IOException {
        return readClients(new File(str));
    }

    /**
     * Reads pizzeria config from File.
     */
    public static List<Client> readClients(File file) throws IOException {
        JsonNode configuration = objectMapper.readTree(file);
        JsonNode distancesJson = configuration.get("distanceFromPizzeria");

        List<Client> clients = new ArrayList<>();
        for (JsonNode distance : distancesJson) {
            clients.add(new Client(distance.asInt()));
        }
        return clients;
    }
}
