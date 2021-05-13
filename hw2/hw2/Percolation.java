package hw2;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    boolean[][] set;
    int size;
    int numberofopen;
    WeightedQuickUnionUF disjointset;
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be a positive number!");
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; i++) {
                set[i][j] = false;
            }
        }
        size = N;
        disjointset = new WeightedQuickUnionUF(N * N);
        numberofopen = 0;
    }

    public int xyTo1D(int r, int c) {
        return r * size + c;
    }

    public void open(int row, int col) {
        if (row >= size || col >= size || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("The input out of the range");
        }
        set[row][col] = true;
        numberofopen += 1;
        if (set[row - 1][col]) {
            disjointset.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        if (set[row + 1][col]) {
            disjointset.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        if (set[row][col - 1]) {
            disjointset.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
        if (set[row][col + 1]) {
            disjointset.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        return set[row][col];
    }

    public int numberOfOpenSites() {
        return numberofopen;
    }
}
