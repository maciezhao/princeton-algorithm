import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Write a program RandomWord.java that reads a sequence of words from standard input and prints one
 * of those words uniformly at random. </br>
 * Do not store the words in an array or list. Instead, use Knuth’s method: when reading the ith
 * word, select it with probability 1/i </br>
 * to be the champion, replacing the previous champion. After reading all of the words, print the
 * surviving champion.
 *
 * @author zhaomeixia
 * @since 2025/5/22
 */
public class RandomWord {

    public static void main(String[] args) {
        String champion = null;
        int i = 1;
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            double p = (double) 1 / i;
            if (StdRandom.bernoulli(p)) {
                champion = word;
            }
            i++;
        }
        StdOut.println(champion);
    }
}
