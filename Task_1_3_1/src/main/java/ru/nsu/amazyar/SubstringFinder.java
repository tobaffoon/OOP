package ru.nsu.amazyar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SubstringFinder {

    //TODO посмотреть что с PR, написать доки
    public static List<Integer> getAllEntries(String pattern, String stringPath)
        throws IOException {
        Path path = Paths.get(stringPath);
        return getAllEntries(pattern, path);
    }

    public static List<Integer> getAllEntries(String pattern, Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return getAllEntries(pattern, reader);
        }
    }

    public static List<Integer> getAllEntries(String pattern, Reader reader) throws IOException {
        List<Integer> entries = new ArrayList<>();       //contains indices of patterns in reader
        int[] zPattern = ZFunctionCreator.getZfunction(pattern);
        int patternSize = pattern.length();

        TwoCharBuffer twoBuffer = new TwoCharBuffer(patternSize);   //buffer with two subbuffers
        int bufferCap = reader.read(twoBuffer.getSecondBuffer());   //n of elements of last subbufer
        //text is smaller than pattern => no need to check for entries
        if (bufferCap < patternSize) {
            return entries;
        }
        int bufferSize = bufferCap;                                 //n of elements of twoBuffer

        //boarders of the largest matched (with pattern) block of text from reader
        int leftBoarder = 0;
        int rightBoarder = 0;

        int effectiveIdx;                                           //index used for buffer
        int currentEntryLength;                 //length of substring of text that matches pattern

        //condition is false when buffered isn't fully filled (including empty buffer), and we've
        //read all symbols from this half filled buffer
        for (int i = 0; i % patternSize <= bufferCap; i++) {
            effectiveIdx = i % patternSize;

            //condition is true when first buffer is exhausted
            if (effectiveIdx == 0) {
                twoBuffer.switchBuffers();  //replace exhausted buffer with next one
                bufferCap = reader.read(twoBuffer.getSecondBuffer());   //read new buffer
                //second buffer is empty, but first one is full (thanks to condition of cycle)
                if (bufferCap == -1) {
                    bufferSize = patternSize;
                }
                //second buffer isn't empty
                else {
                    bufferSize = patternSize + bufferCap;
                }
            }

            //doesn't equal to 0, when it's beginning substring already matched beginning of pattern
            //here `rightBoarder - i` shows how far matching with pattern was already calculated
            currentEntryLength = (rightBoarder > i) ?
                Math.min(zPattern[i - leftBoarder], rightBoarder - i)
                : 0;

            //break if pattern is exhausted or buffer is exhausted (when it was not fully filled)
            //or when symbols from pattern and buffer (text) don't match
            while (currentEntryLength != patternSize &&
                effectiveIdx + currentEntryLength < bufferSize &&
                pattern.charAt(currentEntryLength) ==
                    twoBuffer.get(effectiveIdx + currentEntryLength)
            ) {
                currentEntryLength++;
            }

            //if entry length matches length of the pattern then pattern is the entry
            if (currentEntryLength == patternSize) {
                entries.add(i);
            }

            if (i + currentEntryLength > rightBoarder) {
                leftBoarder = i;
                rightBoarder = i + currentEntryLength;
            }
        }

        return entries;
    }
}
