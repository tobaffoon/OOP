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
        int l = 0;
        int r = 0;
        int[] z = new int[str.length()];
        z[0] = str.length();
        for (int i = 1; i < str.length(); i++) {
            z[i] = (r > i) ? Math.min(z[i - l], r - i) : 0;
            while (i + z[i] < str.length() && str.charAt(z[i]) == str.charAt(i + z[i])) {
                z[i]++;
            }
            if ((i + z[i]) > r) {
                l = i;
                r = i + z[i];
            }
        }
        return z;
    }
}
