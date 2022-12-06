package ru.nsu.amazyar;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalReader {
    public static String readLine() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            return reader.readLine();
        } catch (IOException e){
            throw new IOError(e);
        }
    }
}
