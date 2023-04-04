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
            }
            createAndRunThreads(clients, "Client");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createAndRunThreads(List<? extends Runnable> runnables, String nameTemplate){
        for(int i = 0; i < runnables.size(); i++){
            Thread new_thread = new Thread(runnables.get(i), nameTemplate + "-" + i);
            new_thread.start();
        }
    }
}
