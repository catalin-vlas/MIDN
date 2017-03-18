import java.util.BitSet;

/**
 * Created by catavlas on 2/26/2017.
 */
public abstract class FitnessFunction {

    protected int chrLength;

    public abstract double getFitness(BitSet chromosome);

    public int getChrLength(){
        return chrLength;
    }
}
