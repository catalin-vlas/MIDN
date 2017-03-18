import java.util.ArrayList;
import java.util.BitSet;

/**
 * Created by catavlas on 3/4/2017.
 */
public class SixHumpCamelBack extends NumericFunction {
    public SixHumpCamelBack(ArrayList<Pair<Double, Double>> intervals, int precision) {
        super(intervals, precision);
    }

    @Override
    public double getRealValue(BitSet chromosome) {
        ArrayList<Double> realValues = getRealVector(chromosome);

        double x1 = realValues.get(0);
        double x2 = realValues.get(1);

        double f = (4.0-2.1*x1*x1+Math.pow(x1,4.0)/3.0)*x1*x1 + x1*x2 + (4.0*x2*x2-4.0)*x2*x2;

        return f;
    }
}
