package lab3;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.*;
import java.util.stream.Collectors;

import static lab3.TspAlg.generations;


public class TspMutation implements EvolutionaryOperator<TspSolution> {
    double iteration = 0;
    TspFitnessFunction fitnessFunction = new TspFitnessFunction();

    public List<TspSolution> apply(List<TspSolution> population, Random random) {
        // your implementation:
        double p = iteration / generations;
        for (TspSolution solution : population) {
            int a = random.nextInt(solution.getDim() - 1);
            int b = a + random.nextInt(solution.getDim() - 1 - a) + 1 + 1;

            List<Integer> modify = solution.getSolution().subList(a, b);

            Set<Integer> collised = fitnessFunction.findCollisions(solution);

            if (random.nextDouble() > Math.min(p, 0.9)) {
                if (random.nextDouble() > (0.8)) {
                    invert(modify);
                } else {
                    shuffle(solution.getSolution(), collised, random);
                }
            } else {
                if (random.nextDouble() > 0.8) {
                    swap(solution.getSolution(), collised, random);
                } else {
                    shuffle(solution.getSolution(), collised, random);
                }
            }
        }
        iteration++;
        return population;
    }

    private void swap(List<Integer> modify, Set<Integer> collised, Random random) {
        if (collised.size() == 0) return;
        int badInd = new ArrayList<>(collised).get(random.nextInt(collised.size()));
        int swapInd;
        do {
            swapInd = random.nextInt(modify.size());
        } while (fitnessFunction.collise(badInd, modify.get(badInd), swapInd, modify.get(swapInd)));
        Collections.swap(modify, badInd, swapInd);
        //Collections.swap(modify, 0, modify.size() - 1);
    }

    private Set<Integer> nSwap(int n, TspSolution bestSolution, Random random) {
        Set<Integer> resCollised = new HashSet<>();
        for (int i = 0; i < n; i++) {
            TspSolution curSolution = new TspSolution(new ArrayList<>(bestSolution.getSolution()));
            Set<Integer> collised = fitnessFunction.findCollisions(curSolution);
            double curFintess = collised.size();
            if (i == 0) {
                resCollised = collised;
            }

            swap(curSolution.getSolution(), collised, random);

            Set<Integer> newCollised = fitnessFunction.findCollisions(curSolution);
            double newFintess = newCollised.size();

            if (newFintess < curFintess) {
                bestSolution = curSolution;
                resCollised = newCollised;
            }
        }
        return resCollised;
    }

    private void shuffle(List<Integer> modify, Set<Integer> collised, Random random) {
        int cnt = 0;
        List<Integer> colliseList = new ArrayList<>(collised);
        if (collised.size() == 0) return;
        for (int i = 0; i < colliseList.size(); i++) {
            if (random.nextDouble() < 1 / (collised.size() * 1.)) {
                continue;
            }
            cnt++;
            swap(modify, new HashSet<>(colliseList.get(i)), random);
        }
        if (cnt == 0) swap(modify, collised, random);
    }

    private void insert(List<Integer> modify) {
        Collections.rotate(modify, 1);
    }

    private void randomshuffle(List<Integer> modify, Random rnd) {
        Collections.shuffle(modify, rnd);
    }

    private void invert(List<Integer> modify) {
        Collections.reverse(modify);
    }
}
