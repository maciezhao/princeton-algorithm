import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 三数之和
 */
public class ThreeSum {

    public static int[] threesum(int[] array) {
        Arrays.sort(array);
        int x;
        int y;
        int sum;
        for (int i = 0; i < array.length; i++) {
            x = i + 1;
            y = array.length - 1;
            sum = -array[i];
            while (x < y) {
                if (array[x] + array[y] == sum) {
                    return new int[]{array[i], array[x], array[y]};
                } else if (array[x] + array[y] < sum) {
                    x++;
                } else {
                    y--;
                }
            }
        }
        return new int[0];
    }

    public static void main(String[] args) {
        List<Integer> aList = new ArrayList<>();
        while (!StdIn.isEmpty()) {
            int i = StdIn.readInt();
            aList.add(i);
        }
        int[] array = threesum(aList.stream().mapToInt(Integer::intValue).toArray());
        System.out.println(Arrays.toString(array));
    }
}
