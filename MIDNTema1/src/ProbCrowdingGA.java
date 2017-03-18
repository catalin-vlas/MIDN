import java.util.BitSet;
import java.util.HashMap;

/**
 * Created by catavlas on 3/4/2017.
 */
public class ProbCrowdingGA extends DetCrowdingGA {
    public ProbCrowdingGA(HashMap<String, Double> p, String selectionType) {
        super(p, selectionType);
    }

    @Override
    boolean wins(BitSet c1, BitSet c2) {
        double fx = fitnessFunction.getFitness(c1);
        double fy = fitnessFunction.getFitness(c2);
        double pwin = fx/(fx+fy);

        if (helper.getRandomDouble(0,1)<=pwin) return true;
        else return false;
    }

}
