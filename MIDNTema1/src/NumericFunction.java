/**
 * Created by catavlas on 3/4/2017.
 */
import java.util.ArrayList;
import java.util.BitSet;

/**
 * Created by catavlas on 2/26/2017.
 */
public abstract class NumericFunction extends FitnessFunction {

    protected ArrayList<Pair<Double, Double>> intervals;
    protected ArrayList<Pair<Integer, Integer>> segm = new ArrayList<>();
    protected int precision;
    protected long p;
    protected ArrayList<Double> lseg = new ArrayList<>();

    public NumericFunction(ArrayList<Pair<Double,Double>> intervals, int precision) {
        this.intervals = intervals;
        this.precision = precision;

        init();
    }

    private void init() {

        p=1;
        for (int i=1; i<=precision; ++i) p*=(long)10;

        for (int i=0; i<intervals.size(); ++i) {
            long nrseg = (long)(intervals.get(i).getValue()-intervals.get(i).getKey() * (double)p);

            int aux = 0;
            long auxp = 1;

            while (nrseg > 0) {
                nrseg/=(long)2;
                ++aux;
                auxp *= (long)2;
            }

            if (i==0) {
                segm.add(new Pair<>(0,aux-1));
            }
            else {
                segm.add(new Pair<>(segm.get(i-1).getValue()+1,segm.get(i-1).getValue()+aux));
            }

            chrLength+=aux;

            lseg.add((intervals.get(i).getValue()-intervals.get(i).getKey())/(double)auxp);
        }

    }

    public ArrayList<Double> getRealVector(BitSet chromosome) {
        ArrayList<Double> realValues = new ArrayList<>();

        for (int i=0; i<segm.size(); ++i) {
            long offset = 0;
            long p=1;
            for (int j=segm.get(i).getKey(); j<=segm.get(i).getValue(); ++j) {
                if (chromosome.get(j)) offset += p;

                p*= (long)2;
            }

            realValues.add(intervals.get(i).getKey()+(double)offset*lseg.get(i));
        }

        return realValues;
    }

    public abstract double getRealValue(BitSet chromosome);

    @Override
    public double getFitness(BitSet chromosome) {
        double f = getRealValue(chromosome);
        f+=1000.00;
        return 1.0/(f+0.0000000001);
    }
}
