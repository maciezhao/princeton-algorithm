import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 双端队列
 * <p> 要求：Your deque implementation must support each deque operation (including construction) in constant worst-case time.<br\>
 * A deque containing n items must use at most 48n + 192 bytes of memory. <br\>
 * Additionally, your iterator implementation must support each operation (including construction) in constant worst-case time.
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;

    private Node<Item> last;

    private int n;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null || last == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Illegal item value: " + item);
        }
        n++;
        Node<Item> oldFirst = first;
        first = new Node<>(item);
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Illegal item value: " + item);
        }
        n++;
        Node<Item> oldLast = last;
        last = new Node<>(item);
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
            last.prev = oldLast;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty now");
        }
        n--;
        Item item = first.item;
        first = first.next;
        if (isEmpty()) {
            last = first;
        } else {
            first.prev = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty now");
        }
        n--;
        Item item = last.item;
        last = last.prev;
        if (isEmpty()) {
            first = last;
        } else {
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(StdIn.readInt());
        deque.addFirst(StdIn.readInt());
        deque.addFirst(StdIn.readInt());
        deque.addFirst(StdIn.readInt());
        deque.addFirst(StdIn.readInt());
        StdOut.println(deque.size());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        deque.addLast(StdIn.readInt());
        deque.addLast(StdIn.readInt());
        deque.addFirst(StdIn.readInt());
        deque.addLast(StdIn.readInt());
        StdOut.println(deque.isEmpty());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeLast());
        StdOut.println(deque.isEmpty());

    }

    private static class Node<Item> {
        private final Item item;

        private Node<Item> prev;

        private Node<Item> next;

        public Node(Item item) {
            this.item = item;
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Deque.Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no next element");
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Unsupported remove operation");
        }
    }
}