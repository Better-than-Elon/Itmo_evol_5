package lab3;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Collections;
import java.util.Random;

public class TspFactory extends AbstractCandidateFactory<TspSolution> {
    int dimention;
    TspFactory(int dimention) {
        this.dimention = dimention;
    }

    public TspSolution generateRandomCandidate(Random random) {
        TspSolution solution = new TspSolution(dimention);
        Collections.shuffle(solution.getSolution());
        return solution;
    }
}

