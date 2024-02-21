package lab3;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.*;

public class TspCrossover extends AbstractCrossover<TspSolution> {
    protected TspCrossover() {
        super(1);
    }

    protected List<TspSolution> mate(TspSolution p1, TspSolution p2, int i, Random random) {
        ArrayList children = new ArrayList();
        // your implementation:
        children.add(new TspSolution(orderedCrossover(p1,p2,random)));
        children.add(new TspSolution(orderedCrossover(p2,p1,random)));
        children.add(new TspSolution(orderedCrossover(p1,p2,random)));
        children.add(new TspSolution(orderedCrossover(p2,p1,random)));
        children.add(new TspSolution(orderedCrossover(p1,p2,random)));
        children.add(new TspSolution(orderedCrossover(p2,p1,random)));
        return children;
    }

    private List<Integer> orderedCrossover(TspSolution p1, TspSolution p2, Random random) {
        int a = random.nextInt(p1.getDim() - 1);
        int b = a + random.nextInt(p1.getDim()- 1 - a) + 1 + 1;

        List<Integer> child = new ArrayList<>(p1.getSolution());
        Set<Integer> used = new HashSet<>(child.subList(a, b));

        int toAdd = b % p2.getDim();
        for (int j = 0; j < p2.getDim(); j++) {
            Integer cur = p2.getInd((b + j) % p2.getDim());

            if (!used.contains(cur)) {
                assert(toAdd < child.size());
                child.set(toAdd, cur);
                toAdd = (toAdd + 1) % p2.getDim();
            }
        }
        return child;
    }
    private List<Integer> natCrossover(TspSolution p1, TspSolution p2, Random random) {
        Set<Integer>all = new HashSet<>(p1.getSolution());
        Set<Integer> same = new HashSet<>();
        List<Integer> child = new ArrayList<>(p1.getSolution());

        for(int i = 0; i < p1.getDim(); i++) {
            if(Objects.equals(p1.getInd(i), p2.getInd(i))) {
                same.add(p1.getInd(i));
            }
        }
        all.removeAll(same);
        List<Integer> left = new ArrayList<>(all);
        Collections.shuffle(left, random);
        int leftIndex = 0;
        for(int i = 0; i < p1.getDim(); i++) {
            if(!Objects.equals(p1.getInd(i), p2.getInd(i))) {
                child.set(i, left.get(leftIndex++));
            }
        }
        return child;
    }
}
