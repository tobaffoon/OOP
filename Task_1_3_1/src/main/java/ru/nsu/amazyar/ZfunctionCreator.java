package ru.nsu.amazyar;

/**
 * Creates Z array of a string. Z arrays are used in finding all entries of a pattern in a text
 * problem
 */
public class ZfunctionCreator {

    /**
     * Creates Z array of a string.
     */
    public static int[] getZfunction(String str) {
        return getZfunction(str, 0, new int[str.length()]);
    }

    public static int[] getZfunction(String str, int startIndex, int[] zArray) {
        int l = startIndex;
        int r = startIndex;
        for (int i = 1; i < str.length(); i++) {
            zArray[i] = (r > i) ? Math.min(zArray[i - l], r - i) : 0;
            while (i + zArray[i] < str.length() && str.charAt(zArray[i]) == str.charAt(i + zArray[i])) {
                zArray[i]++;
            }
            if ((i + zArray[i]) > r) {
                l = i;
                r = i + zArray[i];
            }
        }
        return zArray;
    }
}
