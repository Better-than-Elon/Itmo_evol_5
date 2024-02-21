package lab3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TspSolution {

    private List<Integer> solution = new ArrayList<>();

    TspSolution(int n) {
        for (int i = 0; i < n; i++) {
            solution.add(i);
        }
    }

    TspSolution(List<Integer> solution) {
        this.solution = solution;
    }

    public int getDim(){
        return solution.size();
    }

    public List<Integer> getSolution() {
        return solution;
    }
    public Integer getInd(int i) {
        return solution.get(i);
    }

    public Integer setInd(int i, Integer v) {
        return solution.set(i, v);
    }

    public String toString() {
        System.out.println(solution);
        return solution.stream().limit(20).map(x -> String.valueOf(x + 1))
                .collect(Collectors.joining(", ")) + "...";

    }
}
