import java.util.ArrayList;
import java.util.BitSet;

/**
 * Created by catavlas on 3/4/2017.
 */
public class Griewangk extends NumericFunction {
    public Griewangk(ArrayList<Pair<Double, Double>> intervals, int precision) {
        super(intervals, precision);
    }

    @Override
    public double getRealValue(BitSet chromosome) {
        ArrayList<Double> realValues = getRealVector(chromosome);

        double f = 0;

        for (int i=0; i<intervals.size(); ++i)
            f += Math.pow(realValues.get(i), 2.0)/4000.00;

        double p = 1;
        for (int i=0; i<intervals.size(); ++i)
            p *= Math.cos(realValues.get(i)/Math.sqrt((double)(i+1)));

        return f-p+1;
    }
}
