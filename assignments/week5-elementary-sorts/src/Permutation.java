/**
 * @author zhaomeixia
 * @since 2025/9/1
 */
public class Permutation {

    public static boolean isPermutation(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }
        quickSort(a);
        quickSort(b);

        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    private static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        int partition = partition(arr, low, high);
        quickSort(arr, low, partition - 1);
        quickSort(arr, partition + 1, high);
    }

    private static int partition(int[] arr, int low, int high) {
        int left = low;
        int right = high + 1;
        while (true) {
            while (arr[++left] < arr[low]) {
                if (left == high) {
                    break;
                }
            }
            while (arr[low] < arr[--right]) {
                if (right == low) {
                    break;
                }
            }

            if (left >= right) {
                break;
            }

            exch(arr, left, right);
        }

        exch(arr, low, right);
        return right;
    }

    private static void exch(int[] arr, int x, int y) {
        int val = arr[x];
        arr[x] = arr[y];
        arr[y] = val;
    }
}
