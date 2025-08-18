import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * 仿真
 */
public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Only Support one arg");
        }
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String i = StdIn.readString();
            randomQueue.enqueue(i);
        }

        while (!randomQueue.isEmpty() && k-- > 0) {
            StdOut.println(randomQueue.dequeue());
        }
    }
}
