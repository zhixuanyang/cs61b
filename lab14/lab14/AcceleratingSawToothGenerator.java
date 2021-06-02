package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;
    public AcceleratingSawToothGenerator(int i, double v) {
        state = 0;
        period = i;
        factor = v;
    }

    public double next() {
        state += 1;
        if (state == period) {
            period = (int) (period * factor);
            state = 0;
        }
        double result = normalize(state);
        return result;
    }

    private double normalize(int temp) {
        double percentage = (double) temp * 2 / (period - 1.0);
        double result = percentage * 2;
        return result - 1.0;
    }
}
