import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

/**
 * Created by catavlas on 2/26/2017.
 */
public abstract class GeneticAlgorithm {

    protected HashMap<String, Double> parameters;
    protected ArrayList<BitSet> population = new ArrayList<>();
    protected FitnessFunction fitnessFunction;
    protected String selectionType;
    protected static GeneticAlgorithmStandardHelper helper = new GeneticAlgorithmStandardHelper();

    public GeneticAlgorithm(HashMap<String, Double> p, String selectionType){
        this.parameters = p;
        this.selectionType = selectionType;
        init();
    }

    public void init() {
        //generate initial population
        for (int i=0; i<parameters.get("PopSize").intValue(); ++i){
            BitSet aux = new BitSet(parameters.get("ChrLength").intValue());

            for (int j=0; j<parameters.get("ChrLength").intValue(); ++j)
                if (helper.getRandomInt(0,1)==1)
                    aux.set(j);

            population.add(aux);
        }
    }

    public void setParameters(HashMap<String, Double> p){
        parameters = p;
    }
    public void setParameter(String name, Double value){
        parameters.put(name,value);
    }
    public double getParameter(String name){
        return parameters.get(name);
    }

    public HashMap<String, Double> getParameters(){
        return parameters;
    }

    public ArrayList<BitSet> getPopulation(){
        return population;
    }

    public void setFitnessFunction(FitnessFunction fn) {
        fitnessFunction = fn;
    }

    public FitnessFunction getFitnessFunction(){
        return fitnessFunction;
    }

    public abstract void applyGeneticOperators();
    public abstract void applySelection();
    public abstract void generateNextGeneration();

}
