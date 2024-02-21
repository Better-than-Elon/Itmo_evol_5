package lab3;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.*;

public class TspFitnessFunction implements FitnessEvaluator<TspSolution> {


    public double getFitness(TspSolution solution, List<? extends TspSolution> list) {
        return findCollisions(solution).size();
    }

    public Set<Integer> findCollisions(TspSolution solution) {
        Set<Integer> res = new HashSet<>();
        for (int i = 0; i < solution.getDim(); i++) {
            for (int j = i + 1; j < solution.getDim(); j++) {
                if (i == j) {
                    continue;
                }
                if (collise(i, solution.getInd(i), j, solution.getInd(j))) {
                    res.add(i);
                    res.add(j);
                }
            }
        }
        return res;
    }

    public boolean collise(int x0, int y0, int x1, int y1) {
        return Math.abs(x1 - x0) == Math.abs(y1 - y0);
    }

    public boolean isNatural() {
        return false;
    }

    public static void main(String[] args) {
        TspSolution solution = new TspSolution(Arrays.asList(2, 4, 0, 1, 3));
        System.out.println(solution.getSolution());
        double res = new TspFitnessFunction().getFitness(solution, null);
        System.out.println(res);
    }
}
