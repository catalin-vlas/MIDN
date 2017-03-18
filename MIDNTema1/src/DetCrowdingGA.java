import java.util.BitSet;
import java.util.HashMap;

/**
 * Created by catavlas on 3/4/2017.
 */
public class DetCrowdingGA extends GeneticAlgorithm {
    public DetCrowdingGA(HashMap<String, Double> p, String selectionType) {
        super(p, selectionType);
    }

    @Override
    public void applyGeneticOperators() {

    }

    @Override
    public void applySelection() {

    }

    boolean wins(BitSet c1, BitSet c2){
        if (fitnessFunction.getFitness(c1)>=fitnessFunction.getFitness(c2)) return true;
        else return false;
    }

    @Override
    public void generateNextGeneration() {

       for (int i=0; i<population.size(); i+=2) {
           int c1 = helper.getRandomInt(0,population.size()-1);
           int c2 = helper.getRandomInt(0,population.size()-1);

           while (c1==c2){
               c2 = helper.getRandomInt(0,population.size()-1);
           }

           Pair<BitSet, BitSet> offspring = helper.applyCrossover(population.get(c1),population.get(c2),parameters.get("ChrLength").intValue());

           if (helper.getRandomDouble(0,1)<parameters.get("MutationProb"))
               offspring.setKey(helper.applyMutation(offspring.getKey(),parameters.get("ChrLength").intValue()));

           if (helper.getRandomDouble(0,1)<parameters.get("MutationProb"))
               offspring.setValue(helper.applyMutation(offspring.getValue(),parameters.get("ChrLength").intValue()));

           if (helper.getFenotipicDistance(population.get(c1),offspring.getKey(),(NumericFunction) fitnessFunction)
                   <= helper.getFenotipicDistance(population.get(c2),offspring.getValue(),(NumericFunction)fitnessFunction)){

               if (wins(offspring.getKey(),population.get(c1))) population.set(c1,offspring.getKey());
               if (wins(offspring.getValue(),population.get(c2))) population.set(c2,offspring.getValue());

           }
           else {

               if (wins(offspring.getKey(),population.get(c2))) population.set(c2,offspring.getKey());
               if (wins(offspring.getValue(),population.get(c1))) population.set(c1,offspring.getValue());

           }
       }

    }
}
