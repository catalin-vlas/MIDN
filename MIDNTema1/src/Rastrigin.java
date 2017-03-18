import java.util.ArrayList;
import java.util.BitSet;

/**
 * Created by catavlas on 3/4/2017.
 */
public class Rastrigin extends NumericFunction {
    public Rastrigin(ArrayList<Pair<Double, Double>> intervals, int precision) {
        super(intervals, precision);
    }

    @Override
    public double getRealValue(BitSet chromosome) {
        ArrayList<Double> realValues = getRealVector(chromosome);

        double f = 0;

        for (int i=0; i<intervals.size(); ++i)
            f += Math.pow(realValues.get(i), 2.0) - 10.0*Math.cos(2.0*Math.PI*realValues.get(i));

        f+= 10.0 * intervals.size();

        return f;
    }
}
