/**
 * Given an array of buckets, each containing a red, white, or blue pebble, sort them by color
 *
 * @author zhaomeixia
 * @since 2025/9/2
 */
public class DutchNationalFlag {

    public static void sortDutchNationalFlag(int[] arr) {
        if (arr.length <= 1) {
            return;
        }
        int x = 0;
        int y = 0;
        int z = arr.length - 1;

        while (y <= z) {
            var color = color(arr[y]);
            if ("red".equals(color)) {
                if (x < y) {
                    swap(x, y);
                }
                x++;
                y++;
            } else ("white".equals(color)) {
                y++;
            } else {
                swap(y, z);
                z--;
            }
        }
    }
}
