package oast.algorithm;

import oast.network.Demand;

import java.util.ArrayList;
import java.util.Comparator;

public class Population implements Comparator<Chromosome> {
    private ArrayList<Chromosome> chromosomes;
    public ArrayList<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public Population() {
        chromosomes = new ArrayList<>();
    }

    public Population(int chromosomesNum, Demand[] demands) {
        chromosomes = new ArrayList<>();
        for(int i=0; i < chromosomesNum; i++) {
            chromosomes.add(new Chromosome(demands));
        }
    }

    public int getChromosomesNumber() {
        return chromosomes.size();
    }

    public void addChromosome(Chromosome chromosome) {
        chromosomes.add(chromosome);
    }

    @Override
    public int compare(Chromosome o1, Chromosome o2) {
        double fitness1 = Evolution.fitnessFunction(o1);
        double fitness2 = Evolution.fitnessFunction(o2);

        if(fitness1 < fitness2)
            return -1;
        else if(fitness1 == fitness2)
            return 0;
        else
            return 1;
    }
}
