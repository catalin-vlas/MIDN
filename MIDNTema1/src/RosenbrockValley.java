import java.util.ArrayList;
import java.util.BitSet;

/**
 * Created by catavlas on 3/4/2017.
 */
public class RosenbrockValley extends NumericFunction {

    public RosenbrockValley(ArrayList<Pair<Double, Double>> intervals, int precision) {
        super(intervals, precision);
    }

    @Override
    public double getRealValue(BitSet chromosome) {
        ArrayList<Double> realValues = getRealVector(chromosome);

        double f = 0;

        for (int i=0; i<intervals.size()-1; ++i)
            f += 100.0*Math.pow(realValues.get(i+1)-realValues.get(i)*realValues.get(i), 2.0)+Math.pow(1.0-realValues.get(i), 2.0);

        return f;
    }
}
