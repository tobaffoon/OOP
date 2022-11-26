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
        return getZfunction(str, new int[0]);
    }

    public static int[] getZfunction(String str, int[] zarray) {
        int startIndex = zarray.length + 1;
        int l = zarray.length;
        int r = zarray.length;
        int[] z = new int[str.length()];
        System.arraycopy(zarray, 0, z, 0, zarray.length);
        //for condition means that subText which is smaller that pattern can't match it
        for (int i = startIndex; i < str.length() - zarray.length; i++) {
            z[i] = (r > i) ? Math.min(z[i - l], r - i) : 0;
            while (i + z[i] < str.length()
                && str.charAt(z[i]) == str.charAt(i + z[i])
                && str.charAt(z[i]) != '\0') {
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
