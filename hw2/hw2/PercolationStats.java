package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private Percolation per;
    private int size;
    private int times;
    private double[] result;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T should be positive numbers!");
        }
        result = new double[T];
        size = N;
        times = T;
        for (int i = 0; i < T; i++) {
            per = pf.make(N);
            while (!per.percolates()) {
                int row = StdRandom.uniform(size);
                int col = StdRandom.uniform(size);
                if (!per.isOpen(row, col)) {
                    per.open(row, col);
                }
            }
            result[i] = (double) per.numberOfOpenSites() / (double) (size * size);
        }
    }

    public double mean() {
        return StdStats.mean(result);
    }

    public double stddev() {
        return StdStats.stddev(result);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(times);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(times);
    }
}
