package ru.nsu.amazyar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Finds pattern in reader. Contains static methods to return all entries of pattern in reader
 */
public class SubstringFinder {

    /**
     * Finds pattern in file from path.
     *
     * @param pattern    required pattern
     * @param stringPath path to the file
     * @return list of indexes of beginnings of pattern entries
     */
    public static List<Integer> getAllEntries(String pattern, String stringPath)
        throws IOException {
        Path path = Paths.get(stringPath);
        return getAllEntries(pattern, path);
    }

    /**
     * Finds pattern in file from path.
     *
     * @param pattern required pattern
     * @param path    path to the file
     * @return list of indexes of beginnings of pattern entries
     */
    public static List<Integer> getAllEntries(String pattern, Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return getAllEntries(pattern, reader);
        }
    }

    /**
     * Finds pattern in reader.
     *
     * @param pattern required pattern
     * @param reader  source of the text
     * @return list of indexes of beginnings of pattern entries
     */
    public static List<Integer> getAllEntries(String pattern, Reader reader) throws IOException {
        if(pattern.equals("")){
            throw new IllegalStateException("Empty patterns aren't allowed");
        }
        List<Integer> entries = new ArrayList<>();       //contains indices of patterns in reader
        int[] zpattern = ZfunctionCreator.getZfunction(pattern);
        int patternSize = pattern.length();

        TwoCharBuffer twoBuffer = new TwoCharBuffer(patternSize);   //buffer with two subbuffers
        int bufferCapacity = reader.read(twoBuffer.getSecondBuffer());   //n of elements of last subbufer

        //text is smaller than pattern => no need to check for entries
        if (bufferCapacity < patternSize) {
            return entries;
        }

        int[] textZArray;

        //i is counter for buffers filled
        for (int i = 0; ; i++) {
            twoBuffer.switchBuffers();  //replace exhausted buffer with next one
            bufferCapacity = reader.read(twoBuffer.getSecondBuffer());

            textZArray = ZfunctionCreator.getZfunction(pattern + "\0" + Arrays.toString(
                twoBuffer.getFirstBuffer()) + Arrays.toString(twoBuffer.getSecondBuffer()), patternSize + 1, zpattern);

            //if entry length matches length of the pattern then pattern is the entry
            for (int entryLength : textZArray) {
                if (entryLength == patternSize) {
                    entries.add();
                }
            }
        }

        return entries;
    }
}
