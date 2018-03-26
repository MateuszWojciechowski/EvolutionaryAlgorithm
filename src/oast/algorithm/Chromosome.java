package oast.algorithm;

import oast.network.Demand;
import oast.network.DemandPath;
import oast.program.Main;

import java.util.Random;

public class Chromosome {
    private Gene[] genes;
    public Gene[] getGenes() {
        return genes;
    }

    private int genesNum;
    public int getGenesNum() {
        return genesNum;
    }

    public Chromosome(Demand[] demands) {
        genesNum = demands.length;
        genes = new Gene[genesNum];

        //losowanie początkowej populacji
        for(int i=0; i < genesNum; i++) {
            int volume = demands[i].getVolume();    //bierzemy wielkosc zapotrzebowania
            int[] allocation = new int[demands[i].getPaths().length];   //tablica alokująca przepustowosci na poszczegolne sciezki
            for(int j=0; j < allocation.length; j++) {
                allocation[i] = Main.rnd.nextInt(volume);    //losujemy liczbe z zakresu 0 - wielkosc zapotrzebowania
                volume -= allocation[i];    //pomniejszamy wielkosc zapotrzebowania o przepustowosc juz przydzielona
            }
            genes[i] = new Gene(allocation);    //tworzymy gen
        }
    }
    //konstruktor do krzyzowania
    public Chromosome(Gene[] genes) {
        this.genes = genes;
    }

    //krzyzowka chromosomow
    public static Chromosome[] crossover(Chromosome chromosome1, Chromosome chromosome2) {
        Gene[] genes1 = chromosome1.getGenes();
        Gene[] genes2 = chromosome2.getGenes();
        int genesNum = chromosome1.getGenesNum();
        Gene[][] offspringsGenes = new Gene[2][genesNum];    //dwaj potomkowie o liczbie genow rownej genesNum

        for(int i=0; i < 2; i++) {
            for(int j=0; j < genesNum; j++) {
                float random = Main.rnd.nextFloat();
                if(random <= 0.5) {     //gen bierzemy z pierwszego chromosomu
                    offspringsGenes[i][j] = genes1[i];
                } else {    //gen bierzemy z drugiego chromosomu
                    offspringsGenes[i][j] = genes2[i];
                }
            }
        }

        Chromosome[] offsprings = new Chromosome[2];
        offsprings[0] = new Chromosome(offspringsGenes[0]);
        offsprings[1] = new Chromosome(offspringsGenes[1]);
        return offsprings;
    }
}
