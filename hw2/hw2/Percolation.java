package hw2;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    boolean[][] set;
    int size;
    int numberofopen;
    WeightedQuickUnionUF disjointset;
    WeightedQuickUnionUF disjointset_percolation;

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
        disjointset_percolation = new WeightedQuickUnionUF(N * N + 1);
        numberofopen = 0;
    }

    public int xyTo1D(int r, int c) {
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
                disjointset_percolation.union(0, xyTo1D(row, col));
                checkdown(row, col);
                checkright(row, col);
            } else if (row == size - 1 & col == 0) {
                disjointset.union(size * size + 1, xyTo1D(row, col));
                checkright(row, col);
                checkup(row, col);
            } else if (row == 0 & col == size - 1) {
                disjointset.union(0, xyTo1D(row, col));
                disjointset_percolation.union(0, xyTo1D(row, col));
                checkdown(row, col);
                checkleft(row, col);
            } else if (row == size - 1 & col == size - 1) {
                disjointset.union(size * size + 1, xyTo1D(row, col));
                checkup(row, col);
                checkleft(row, col);
            } else if (row == 0) {
                disjointset.union(0, xyTo1D(row, col));
                disjointset_percolation.union(0, xyTo1D(row, col));
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

    public void checkup(int row, int col) {
        if (set[row - 1][col]) {
            disjointset.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            disjointset_percolation.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
    }

    public void checkdown(int row, int col) {
        if (set[row + 1][col]) {
            disjointset.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            disjointset_percolation.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
    }

    public void checkleft(int row, int col) {
        if (set[row][col - 1]) {
            disjointset.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            disjointset_percolation.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
    }

    public void checkright(int row, int col) {
        if (set[row][col + 1]) {
            disjointset.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            disjointset_percolation.union(xyTo1D(row, col), xyTo1D(row, col + 1));
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
        return disjointset_percolation.connected(0, xyTo1D(row, col));
    }

    public boolean percolates() {
        return disjointset.connected(0, size * size + 1);
    }
}
