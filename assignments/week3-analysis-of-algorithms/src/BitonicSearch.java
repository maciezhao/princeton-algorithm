import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class BitonicSearch {
    /**
     * ~ 3 log n
     * @param array
     * @param key
     * @return
     */
    public static boolean bitonicSearch(int[] array, int key) {
        int peak = findPeak(array);
        if (array[peak] == key) {
            return true;
        }
        return binarySearch(array, key, 0, peak - 1, true)
                || binarySearch(array, key, peak + 1, array.length - 1, false);
    }

    /**
     * ~2 log n
     * @param array
     * @param key
     * @return
     */
    public static boolean bitonicSearch2(int[] array, int key) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (array[mid] == key) {
                return true;
            }
            boolean asc = mid == 0 || array[mid] > array[mid - 1];
            boolean desc = mid == array.length - 1 || array[mid] > array[mid + 1];
            if (asc && desc) {
                return false;
            }
            if (asc) {
                if (array[mid] < key) {
                    low = mid + 1;
                } else {
                    return binarySearch(array, key, low, mid - 1, true)
                            || binarySearch(array, key,mid + 1, high, false);
                }
            } else {
                if (array[mid] < key) {
                    high = mid - 1;
                } else {
                    return binarySearch(array, key, mid + 1, high, false)
                            || binarySearch(array, key, low, mid - 1, true);
                }
            }
        }

        return  false;
    }
        

    public static int findPeak(int[] array) {
        int low = 0;
        int high = array.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (array[mid] < array[mid + 1]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return high;
    }

    public static boolean binarySearch(int[] array, int key, int low, int high, boolean asc) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (array[mid] == key) {
                return true;
            } else if (asc && array[mid] < key
             || (!asc && array[mid] > key)) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        List<Integer> aList = new ArrayList<>();
        int key = StdIn.readInt();
        while (!StdIn.isEmpty()) {
            int i = StdIn.readInt();
            aList.add(i);
        }
        int[] array = aList.stream().mapToInt(Integer::intValue).toArray();
        StdOut.println(bitonicSearch(array, key));
        StdOut.println(bitonicSearch2(array, key));

    }
}
