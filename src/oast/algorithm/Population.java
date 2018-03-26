package oast.algorithm;

import oast.network.Demand;

public class Population {
    private Chromosome[] chromosomes;
    public Chromosome[] getChromosomes() {
        return chromosomes;
    }

    public Population(int chromosomesNum, Demand[] demands) {
        chromosomes = new Chromosome[chromosomesNum];
        for(int i=0; i < chromosomesNum; i++) {
            chromosomes[i] = new Chromosome(demands);
        }
    }
}
