import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 随机队列
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;

    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[8];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null element is not allowed");
        }

        if (size == items.length) {
            resize(2 * size);
        }
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniformInt(size);
        Item item = items[index];
        size--;
        items[index] = items[size];
        items[size] = null;
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        int index = StdRandom.uniformInt(size);
        return items[index];

    }

    private void resize(int len) {
        Item[] newItems = (Item[]) new Object[len];
        System.arraycopy(items, 0, newItems, 0, Math.min(items.length, len));
        items = newItems;
    }

    private void shuffleArray(Item[] array) {
        for (int i = 0; i < size; i++) {
            int rand = StdRandom.uniformInt(size - i) + i;
            swap(array, i, rand);
        }
    }

    private void swap(Item[] array, int x, int y) {
        Item temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {

        private final Item[] shuffledItems;

        private int current;

        public RandomizedIterator() {
            this.shuffledItems = (Item[]) new Object[size];
            System.arraycopy(items, 0, shuffledItems, 0, size);
            shuffleArray(shuffledItems);
            this.current = 0;
        }

        @Override
        public boolean hasNext() {
            return current != size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("The queue is empty");
            }
            return shuffledItems[current++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Unsupported remove operation");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        rq.enqueue(StdIn.readString());
        rq.enqueue(StdIn.readString());
        rq.enqueue(StdIn.readString());
        while (!rq.isEmpty()) {
            StdOut.println(rq.dequeue());
        }

        rq.enqueue(StdIn.readString());
        rq.enqueue(StdIn.readString());
        rq.enqueue(StdIn.readString());
        for (String s : rq) {
            StdOut.println(s);
        }
    }

}