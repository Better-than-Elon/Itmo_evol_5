package lab3;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

import java.util.ArrayList;
import java.util.Random;

public class TspAlg {
//16 - 150 - 1000
    static int populationSize = 1000; // size of population
    static int generations = 150*2*2; // number of generations
    static int dimention = 16*2*2; //64 - 637;879;713{pop = 2000; gen = 10000} 64 - 430; 486; 378; 275;306;300 {pop=2000, generations=1000, cross=3}
    //128 - 783;673 || 256 - 1287 || 512 - 2040
    public static void main(String[] args) {

        Random random = new Random(); // random

        CandidateFactory<TspSolution> factory = new TspFactory(dimention); // generation of solutions

        ArrayList<EvolutionaryOperator<TspSolution>> operators = new ArrayList<EvolutionaryOperator<TspSolution>>();
        operators.add(new TspCrossover()); // Crossover
        operators.add(new TspMutation()); // Mutation
        EvolutionPipeline<TspSolution> pipeline = new EvolutionPipeline<TspSolution>(operators);

//        SelectionStrategy<Object> selection = new RouletteWheelSelection(); // Selection operator
        SelectionStrategy<Object> selection = new TournamentSelection(new Probability(0.97)); // Selection operator

        FitnessEvaluator<TspSolution> evaluator = new TspFitnessFunction(); // Fitness function

        EvolutionEngine<TspSolution> algorithm = new SteadyStateEvolutionEngine<TspSolution>(
                factory, pipeline, evaluator, selection, populationSize, false, random);

        algorithm.addEvolutionObserver(new EvolutionObserver() {
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit);
//                TspSolution best = (TspSolution)populationData.getBestCandidate();
//                System.out.println("\tBest solution = " + best.toString());
            }
        });
//        TerminationCondition terminate = new GenerationCount(generations);
//        TerminationCondition terminate = new TargetFitness(0d,false);//new GenerationCount(generations);
        TerminationCondition terminate = new TerminationCondition() {
            @Override
            public boolean shouldTerminate(PopulationData<?> populationData) {
                return populationData.getBestCandidateFitness() <= 0d ||
                        populationData.getGenerationNumber() + 1 >= generations;
            }
        };
        algorithm.evolve(populationSize, Math.max(Math.min(populationSize/10,1000), 5), terminate);
    }
}
