import java.util.HashSet;
import java.util.Set;

/**
 * 求两个数组的交集
 * @author zhaomeixia
 * @since 2025/9/1
 */
public class Intersection {

    public Set<Integer> intersection(int[] a, int[] b) {
        Set<Integer> setA = new HashSet<>();
        for (int num : a) {
            setA.add(num);
        }
        Set<Integer> result = new HashSet<>();
        for (int num : b) {
            if (setA.contains(num)) {
                result.add(num);
            }
        }
        return result;
    }
}
