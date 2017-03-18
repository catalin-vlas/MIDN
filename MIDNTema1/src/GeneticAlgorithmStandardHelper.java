import java.util.*;

/**
 * Created by catavlas on 2/26/2017.
 */
public class GeneticAlgorithmStandardHelper {

    public static Random rn = new Random(new Date().getTime());

    public int getRandomInt(int l, int r) {
        int res=0;

        int n = r - l + 1;
        res = rn.nextInt(n);
        res += l;

        return res;
    }

    public double getRandomDouble(double l, double r) {
        double res=0;

        double n=r-l;
        res = rn.nextDouble();
        res = n*res + l;

        return res;
    }

    public int getHammingDistance(BitSet b1, BitSet b2){
        BitSet b11 = (BitSet) b1.clone();
        BitSet b22 = (BitSet) b2.clone();

        b11.or(b22);
        return b11.cardinality();
    }

    public double getFenotipicDistance(BitSet c1, BitSet c2, NumericFunction fn){
        ArrayList<Double> v1 = fn.getRealVector(c1);
        ArrayList<Double> v2 = fn.getRealVector(c2);

        double d=0;

        for (int i=0; i<v1.size(); ++i) d+=Math.pow(v1.get(i)-v2.get(i),2.0);

        return Math.sqrt(d);
    }

    public BitSet applyMutation(BitSet chromosome, int len) {
        BitSet result = (BitSet) chromosome.clone();
        result.flip(getRandomInt(0,len-1));
        return result;
    }

    public ArrayList<BitSet> applyMutation(ArrayList<BitSet> population, Double prob, int len) {

        ArrayList<BitSet> result = new ArrayList<>();

        for (int i=0; i<population.size(); ++i) {
            if (getRandomDouble(0,1)<prob) result.add(applyMutation(population.get(i),len));
            else result.add(population.get(i));
        }

        return result;
    }

    public Pair<BitSet,BitSet> applyCrossover(BitSet c1, BitSet c2, int len) {
        Pair<BitSet,BitSet> result = new Pair<>((BitSet)c1.clone(),(BitSet)c2.clone());

        int l=getRandomInt(0,len-1);
        int r=getRandomInt(l,len-1);

        for (int i=l; i<=r; ++i) {
            boolean bitc1 = result.getKey().get(i);
            boolean bitc2 = result.getValue().get(i);

            if (bitc1 != bitc2) {
                result.getKey().flip(i);
                result.getValue().flip(i);
            }
        }

        return result;
    }

    public ArrayList<BitSet> applyCrossover(ArrayList<BitSet> population, Double prob, int len){

        ArrayList<BitSet> result = new ArrayList<>();
        int last=-1;

        for (int i=0; i<population.size(); ++i)
            if ( getRandomDouble(0,1)<prob ) {

                if (last==-1) last=i;
                else {

                    Pair<BitSet,BitSet> aux = applyCrossover(population.get(last), population.get(i), len);

                    result.add(aux.getKey());
                    result.add(aux.getValue());

                    last=-1;
                }

            }
            else result.add(population.get(i));

        if (last!=-1) result.add(population.get(last));

        return result;
    }

    class BitsetFitnessComparator implements Comparator<Pair<BitSet,Double>> {

        @Override
        public int compare(Pair<BitSet, Double> bitSetDoublePair, Pair<BitSet, Double> t1) {
            if (Math.abs(bitSetDoublePair.getValue()-t1.getValue())<0.0000000000001) return 0;
            if (bitSetDoublePair.getValue()-t1.getValue()>0.0000000000001) return -1;
            return 1;
        }
    }

    public ArrayList<BitSet> applyFortuneWheelSelection(ArrayList<Pair<BitSet,Double>> population){
        ArrayList<BitSet> result = new ArrayList<>();

        Collections.sort(population, new BitsetFitnessComparator());

        double sum = 0;
        for (int i=0; i<population.size(); ++i) sum+=population.get(i).getValue();
        for (int i=0; i<population.size(); ++i) population.get(i).setValue(population.get(i).getValue()/sum);

        for (int i=0; i<population.size(); ++i) {
            double currentSum = getRandomDouble(0,1);

            int j=0;

            while (j<population.size() && currentSum>=0) {
                currentSum -= population.get(j).getValue();
                ++j;
            }

            result.add(population.get(j-1).getKey());
        }

        return result;
    }

    public ArrayList<BitSet> applyTournamentSelection(ArrayList<Pair<BitSet,Double>> population){
        ArrayList<BitSet> result = new ArrayList<>();

        for (int i=0; i<population.size(); ++i) {

            int chr1 = getRandomInt(0,population.size()-1);
            int chr2 = getRandomInt(0,population.size()-1);

            while (chr1==chr2){
                chr2 = getRandomInt(0,population.size()-1);
            }

            if (population.get(chr1).getValue()>=population.get(chr2).getValue()) result.add(population.get(chr1).getKey());
            else result.add(population.get(chr2).getKey());
        }

        return result;
    }

    public ArrayList<Pair<BitSet,Double>> applyClearing(ArrayList<Pair<BitSet,Double>> population, int sigma, int kappa, FitnessFunction fn){

        Collections.sort(population, new BitsetFitnessComparator());

        for (int i=0; i<population.size()-1; ++i)
            if (population.get(i).getValue()>0) {
                int nrWinners = kappa;
                for (int j=i+1; j<population.size(); ++j)
                    if (population.get(j).getValue()>0 && getHammingDistance(population.get(i).getKey(),population.get(j).getKey())<sigma
                                                           /* getFenotipicDistance(population.get(i).getKey(),population.get(j).getKey(),(NumericFunction)fn)<sigma*/){
                        if (nrWinners>0) --nrWinners;
                        else population.get(j).setValue(new Double(0));
                    }
            }

        return population;
    }

}
