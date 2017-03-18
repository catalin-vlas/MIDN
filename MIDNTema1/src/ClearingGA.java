import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

/**
 * Created by catavlas on 3/3/2017.
 */
public class ClearingGA extends GeneticAlgorithm {

    public ClearingGA(HashMap<String, Double> p, String selectionType) {
        super(p, selectionType);
    }

    @Override
    public void applyGeneticOperators() {
        population = helper.applyMutation(population, parameters.get("MutationProb"), parameters.get("ChrLength").intValue());
        population = helper.applyCrossover(population, parameters.get("CrossoverProb"), parameters.get("ChrLength").intValue());
    }

    @Override
    public void applySelection() {
        ArrayList<Pair<BitSet,Double>> aux_pop = new ArrayList<>();

        for (int i=0; i<population.size(); ++i)
            aux_pop.add(new Pair<>(population.get(i),fitnessFunction.getFitness(population.get(i))));

        if (selectionType.equals("fortuneWheel")) population = helper.applyFortuneWheelSelection(aux_pop);
        else if (selectionType.equals("tournament")) population = helper.applyTournamentSelection(aux_pop);
    }

    public void applySelection(ArrayList<Pair<BitSet,Double>> aux_pop) {
        if (selectionType.equals("fortuneWheel")) population = helper.applyFortuneWheelSelection(aux_pop);
        else if (selectionType.equals("tournament")) population = helper.applyTournamentSelection(aux_pop);
    }

    @Override
    public void generateNextGeneration() {
        ArrayList<Pair<BitSet,Double>> aux_pop = new ArrayList<>();

        for (int i=0; i<population.size(); ++i)
            aux_pop.add(new Pair<>(population.get(i),fitnessFunction.getFitness(population.get(i))));

        aux_pop = helper.applyClearing(aux_pop, parameters.get("sigma").intValue(), parameters.get("kappa").intValue(), this.fitnessFunction);

        applySelection(aux_pop);
        applyGeneticOperators();
    }
}
