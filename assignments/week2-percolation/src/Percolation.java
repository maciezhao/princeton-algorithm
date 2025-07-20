import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[] openSites;

    private final WeightedQuickUnionUF uf;

    private final WeightedQuickUnionUF ufBackwash;

    private final int topVirtual;

    private final int bottomVirtual;

    private int openNum;

    private final int n;

    private final int[][] steps = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal N");
        }
        uf = new WeightedQuickUnionUF(n * n + 1);
        ufBackwash = new WeightedQuickUnionUF(n * n + 2);
        openSites = new boolean[n * n + 1];
        topVirtual = n * n;
        bottomVirtual = n * n + 1;
        openNum = 0;
        this.n = n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int index = toIndex(row, col);
        if (openSites[index]) {
            return;
        }
        openSites[index] = true;
        openNum++;
        if (row == 1) {
            uf.union(index, topVirtual);
            ufBackwash.union(index, topVirtual);
        }

        if (row == n) {
            ufBackwash.union(index, bottomVirtual);
        }

        for (int[] step : steps) {
            int neignborRow = row + step[0];
            int neignborCol = col + step[1];
            if (neignborRow > 0 && neignborRow <= n
                    && neignborCol > 0 && neignborCol <= n
                    && isOpen(neignborRow, neignborCol)) {
                uf.union(index, toIndex(neignborRow, neignborCol));
                ufBackwash.union(index, toIndex(neignborRow, neignborCol));
            }
        }
    }

    private int toIndex(int row, int col) {
        return (row - 1) * n + col - 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openSites[toIndex(row, col)];
    }

    private void validate(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException(
                    String.format("error, Out of bounds row %d col %d", row, col));
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return uf.find(topVirtual) == uf.find(toIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openNum;

    }

    // does the system percolate?
    public boolean percolates() {
        return ufBackwash.find(topVirtual) == ufBackwash.find(bottomVirtual);
    }


    // test client (optional)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            percolation.open(x, y);
            if (percolation.percolates()) {
                StdOut.println(percolation.percolates());
                break;
            }
        }
    }
}
