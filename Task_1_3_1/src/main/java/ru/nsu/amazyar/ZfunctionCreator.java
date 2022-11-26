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
        int[] z = new int[str.length()];
        //copy existing subArray to new subArray. StartIndex always equals to zArray.size()+1
        //TODO add checks for "StartIndex equals to zArray.size()+1" and staff like that
        System.arraycopy(zArray, 0, z, 0, startIndex-1);
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
