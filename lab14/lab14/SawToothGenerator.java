package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int i) {
        state = 0;
        period = i;
    }

    public double next() {
        state += 1;
        int temp = state % period;
        double result = normalize(temp);
        return result;

    }

    private double normalize(int temp) {
        double percentage = (double) temp / (double) (period - 1.0);
        double result = percentage * 2;
        return result - 1.0;
    }
}
