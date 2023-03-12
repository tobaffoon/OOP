package ru.nsu.amazyar;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PizzeriaJsonReader {
    private static ObjectMapper objectMapper;

    private static ObjectMapper getDefaultObjectMapper(){
        ObjectMapper defaultObjectMapper = new ObjectMapper();

        return defaultObjectMapper;
    }

    public static Pizzeria readPizzeria(File file){
        return null;
    }
}
