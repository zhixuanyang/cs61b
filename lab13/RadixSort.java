/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int max_Length = 0;
        for (String item : asciis) {
            max_Length = max_Length > item.length() ? max_Length : item.length();
        }
        String [] temp = new String[asciis.length];

        for (int i = 0; i < asciis.length; i++) {
            temp[i] = asciis[i];
        }

        for (int i = 0; i < max_Length; i++) {
            sortHelperLSD(temp, max_Length - i - 1);
        }
        return temp;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        int[] count = new int[256 + 1];
        for (String item : asciis) {
            int code = charAtOrMinChar(index, item);
            count[code]++;
        }
        int[] start = new int[256 + 1];
        int pos = 0;
        for (int i = 0; i < start.length; i++) {
            start[i] = pos;
            pos += count[i];
        }

        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            String item = asciis[i];
            int code = charAtOrMinChar(index, item);
            int place = start[code];
            sorted[place] = item;
            start[code] += 1;
        }

        for (int i = 0; i < asciis.length; i++) {
            asciis[i] = sorted[i];
        }
    }

    private static int charAtOrMinChar(int index, String item) {
        if (index < item.length() && index >= 0) {
            return item.charAt(index) + 1;
        } else {
            return 0;
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
