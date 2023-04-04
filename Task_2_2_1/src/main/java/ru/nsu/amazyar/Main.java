package ru.nsu.amazyar;

import java.io.IOException;
import java.util.List;
import ru.nsu.amazyar.pizzeria.Client;
import ru.nsu.amazyar.pizzeria.Pizzeria;
import ru.nsu.amazyar.utils.PizzeriaJsonReader;
import ru.nsu.amazyar.utils.ThreadRunner;

public class Main{
    public static void main(String[] args) {
        try {
            Pizzeria pizzeria = PizzeriaJsonReader.readPizzeria(PizzeriaJsonReader.defaultPizzeriaPath);
            pizzeria.runPizzeria();

            List<Client> clients = PizzeriaJsonReader.readClients(PizzeriaJsonReader.defaultClientsPath);
            for(Client client : clients){
                client.setPizzeria(pizzeria);
            }
            ThreadRunner.createAndRunThreads(clients, "Client");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}