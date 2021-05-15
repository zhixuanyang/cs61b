package hw2;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] set;
    private int size;
    private int numberofopen;
    private WeightedQuickUnionUF disjointset;
    private WeightedQuickUnionUF disjointset2;

    public Percolation(int N) {
        set = new boolean[N][N];
        if (N <= 0) {
            throw new IllegalArgumentException("N should be a positive number!");
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                set[i][j] = false;
            }
        }
        size = N;
        disjointset = new WeightedQuickUnionUF(N * N + 2);
        disjointset2 = new WeightedQuickUnionUF(N * N + 1);
        numberofopen = 0;
    }

    private int xyTo1D(int r, int c) {
        return r * size + c + 1;
    }

    public void open(int row, int col) {
        if (row >= size || col >= size || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("The input out of the range");
        }
        if (!set[row][col]) {
            set[row][col] = true;
            numberofopen += 1;
            if (row == 0 & col == 0) {
                disjointset.union(0, xyTo1D(row, col));
                disjointset2.union(0, xyTo1D(row, col));
                checkdown(row, col);
                checkright(row, col);
            } else if (row == size - 1 & col == 0) {
                disjointset.union(size * size + 1, xyTo1D(row, col));
                checkright(row, col);
                checkup(row, col);
            } else if (row == 0 & col == size - 1) {
                disjointset.union(0, xyTo1D(row, col));
                disjointset2.union(0, xyTo1D(row, col));
                checkdown(row, col);
                checkleft(row, col);
            } else if (row == size - 1 & col == size - 1) {
                disjointset.union(size * size + 1, xyTo1D(row, col));
                checkup(row, col);
                checkleft(row, col);
            } else if (row == 0) {
                disjointset.union(0, xyTo1D(row, col));
                disjointset2.union(0, xyTo1D(row, col));
                checkdown(row, col);
                checkleft(row, col);
                checkright(row, col);
            } else if (row == size - 1) {
                disjointset.union(size * size + 1, xyTo1D(row, col));
                checkup(row, col);
                checkleft(row, col);
                checkright(row, col);
            } else if (col == 0) {
                checkdown(row, col);
                checkup(row, col);
                checkright(row, col);
            } else if (col == size - 1) {
                checkdown(row, col);
                checkup(row, col);
                checkleft(row, col);
            } else {
                checkdown(row, col);
                checkup(row, col);
                checkleft(row, col);
                checkright(row, col);
            }
        }
    }

    private void checkup(int row, int col) {
        if (set[row - 1][col]) {
            disjointset.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            disjointset2.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
    }

    private void checkdown(int row, int col) {
        if (set[row + 1][col]) {
            disjointset.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            disjointset2.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
    }

    private void checkleft(int row, int col) {
        if (set[row][col - 1]) {
            disjointset.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            disjointset2.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
    }

    private void checkright(int row, int col) {
        if (set[row][col + 1]) {
            disjointset.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            disjointset2.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        if (row >= size || col >= size || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("The input out of the range");
        }
        return set[row][col];
    }

    public int numberOfOpenSites() {
        return numberofopen;
    }

    public boolean isFull(int row, int col) {
        if (row >= size || col >= size || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("The input out of the range");
        }
        return disjointset2.connected(0, xyTo1D(row, col));
    }

    public boolean percolates() {
        return disjointset.connected(0, size * size + 1);
    }

    public static void main(String[] args) {

    }
}
