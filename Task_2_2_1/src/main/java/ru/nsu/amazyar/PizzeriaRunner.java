package ru.nsu.amazyar;

import java.io.IOException;
import java.util.List;

public class PizzeriaRunner implements Runnable{

    @Override
    public void run() {
        try {
            Pizzeria pizzeria = PizzeriaJsonReader.readPizzeria(PizzeriaJsonReader.defaultPizzeriaPath);
            pizzeria.runPizzeria();
            
            List<Client> clients = PizzeriaJsonReader.readClients(PizzeriaJsonReader.defaultClientsPath);
            for(Client client : clients){
                client.setPizzeria(pizzeria);
                new Thread(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
