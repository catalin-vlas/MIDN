import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static int NR_Generations = 200;
    public static int Pop_Size = 100;
    public static double Prob_Mutation = 0.1;
    public static double Prob_Crossover = 0.6;
    public static int NR_IT = 1; //number of iterations for mean value
    public static int Clearing_Sigma = 20; //maximum distance between chromosomes
    public static int Clearing_Kappa = 5; //numbers of survivors in the same niche
    public static int precision = 3; //precision for fitness function
    public static int nrVar = 2; //number os function dimensions

    public static HashMap<String, Double> params = new HashMap<>();
    public static PrintWriter out= null;

    public static void init() throws IOException {
        params.put("PopSize", (double)Pop_Size);
        params.put("MutationProb", Prob_Mutation);
        params.put("CrossoverProb", Prob_Crossover);
        params.put("sigma",(double)Clearing_Sigma);
        params.put("kappa",(double)Clearing_Kappa);
        params.put("nr_gen",(double)NR_Generations);

        out = new PrintWriter(new FileWriter("C:\\Users\\catavlas\\Desktop\\points.txt"));
    }

    public static double getMean(ArrayList<Double> data,double percentile) {
        Collections.sort(data);

        int nr = (int)(percentile/100*data.size());
        double sum = 0;
        for (int i=0; i<nr; ++i) sum+=data.get(i);

        return sum/nr;
    }

    public static void printPoints(ArrayList<BitSet> population, NumericFunction fn){
        ArrayList<Double> cx = new ArrayList<>();
        ArrayList<Double> cy = new ArrayList<>();
        ArrayList<Double> cz = new ArrayList<>();

        for (int i = 0; i < population.size(); ++i) {
            ArrayList<Double> xy = fn.getRealVector(population.get(i));
            double z = fn.getRealValue(population.get(i));

            cx.add(xy.get(0));
            cy.add(xy.get(1));
            cz.add(z);
        }


        for (int i=0; i<cx.size(); ++i) out.printf("%.7f ",cx.get(i));
        out.println();
        for (int i=0; i<cy.size(); ++i) out.printf("%.7f ",cy.get(i));
        out.println();
        for (int i=0; i<cz.size(); ++i) out.printf("%.7f ",cz.get(i));
        out.println();

        out.flush();
    }

    public static void main(String[] args) throws IOException {
        init();

        //init rosenbrock function
        ArrayList<Pair<Double,Double>> intervals = new ArrayList<>();
        for (int i=0; i<nrVar; ++i) intervals.add(new Pair<>(-2.048,2.048));

        RosenbrockValley fn0 = new RosenbrockValley(intervals, precision);

        //init six-hump camel back function
        ArrayList<Pair<Double,Double>> intervals1 = new ArrayList<>();
        intervals1.add(new Pair<>(-3.0,3.0));
        intervals1.add(new Pair<>(-2.0,2.0));

        SixHumpCamelBack fn1 = new SixHumpCamelBack(intervals1,precision);

        //init rastrigin
        ArrayList<Pair<Double,Double>> intervals2 = new ArrayList<>();
        for (int i=0; i<nrVar; ++i) intervals2.add(new Pair<>(-5.12,5.12));

        Rastrigin fn2 = new Rastrigin(intervals2, precision);

        //init griewang
        ArrayList<Pair<Double,Double>> intervals3 = new ArrayList<>();
        for (int i=0; i<nrVar; ++i) intervals3.add(new Pair<>(-600.0,600.0));

        Griewangk fn3 = new Griewangk(intervals3, precision);

        NumericFunction fn = fn3;

        params.put("ChrLength",(double) fn.getChrLength());

        ArrayList<Double> result = new ArrayList<>();

        for (int t=1; t<=NR_IT; ++t) {

            //GeneticAlgorithm geneticAlgorithm = new ClearingGA(params, "fortuneWheel");
            //GeneticAlgorithm geneticAlgorithm = new ClearingGA(params, "tournament");
            GeneticAlgorithm geneticAlgorithm = new DetCrowdingGA(params, "fortuneWheel");
            //GeneticAlgorithm geneticAlgorithm = new ProbCrowdingGA(params, "fortuneWheel");

            geneticAlgorithm.setFitnessFunction(fn);

            ArrayList<BitSet> initial_pop = geneticAlgorithm.getPopulation();

            printPoints(initial_pop,fn);

            for (int i = 0; i < NR_Generations; ++i) geneticAlgorithm.generateNextGeneration();

            ArrayList<BitSet> final_pop = geneticAlgorithm.getPopulation();

            printPoints(final_pop,fn);

            //ArrayList<Double> solution = new ArrayList<>();
            double bestValue = 1000000000.00;

            for (int i = 0; i < final_pop.size(); ++i)
                if (fn.getRealValue(final_pop.get(i)) < bestValue) {
                    bestValue = fn.getRealValue(final_pop.get(i));
                    //solution = fn.getRealVector(final_pop.get(i));
                }

           // System.out.println("Best fitness: " + 1.0 / bestFitness);
           // System.out.println("Solution: " + Arrays.toString(solution.toArray()));

            result.add(bestValue);
        }

        System.out.println("P100: " + getMean(result,100));
        System.out.println("P90: " + getMean(result,90));
        System.out.println("P70: " + getMean(result,70));
        System.out.println("P50: " + getMean(result,50));
        System.out.println("MIN: " + result.get(0) + " MAX: " + result.get(result.size()-1));

        out.close();
    }
}
