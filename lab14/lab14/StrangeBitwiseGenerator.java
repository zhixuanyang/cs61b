package lab14;
import lab14lib.Generator;
public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int i) {
        state = 0;
        period = i;
    }

    public double next() {
        state += 1;
        int weirdState = state & (state >> 3) & (state >> 8) % period;
        double result = normalize(weirdState);
        return result;
    }

    private double normalize(int temp) {
        double percentage = (double) temp / (period - 1.0);
        double result = percentage * 2;
        return result - 1.0;
    }
}