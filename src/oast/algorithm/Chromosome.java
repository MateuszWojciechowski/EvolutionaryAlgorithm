package oast.algorithm;

import oast.network.Demand;
import oast.network.DemandPath;
import oast.program.Main;

import java.util.Comparator;
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
                allocation[j] = Main.rnd.nextInt(volume);    //losujemy liczbe z zakresu 0 - wielkosc zapotrzebowania
                volume -= allocation[j];    //pomniejszamy wielkosc zapotrzebowania o przepustowosc juz przydzielona
                if((j == allocation.length - 1) && volume != 0) //jesli to ostatnia oteracja to dodajemy pozostala przepustowosc na ostatnia sciezke zeby nic nie zostalo
                    allocation[j] += volume;
            }
            genes[i] = new Gene(allocation);    //tworzymy gen
        }
    }
    //konstruktor do krzyzowania
    public Chromosome(Gene[] genes) {
        this.genes = genes;
        genesNum = genes.length;
    }

    //krzyzowka chromosomow
    public static void crossover(Chromosome chromosome1, Chromosome chromosome2, Population population) {
        Gene[] genes1 = chromosome1.getGenes();
        Gene[] genes2 = chromosome2.getGenes();
        int genesNum = chromosome1.getGenesNum();
        Gene[][] offspringsGenes = new Gene[2][genesNum];    //dwaj potomkowie o liczbie genow rownej genesNum

        for(int i=0; i < 2; i++) {
            for(int j=0; j < genesNum; j++) {
                float random = Main.rnd.nextFloat();
                if(random <= 0.5) {     //gen bierzemy z pierwszego chromosomu
                    offspringsGenes[i][j] = genes1[j];
                } else {    //gen bierzemy z drugiego chromosomu
                    offspringsGenes[i][j] = genes2[j];
                }
            }
        }

//        Chromosome[] offsprings = new Chromosome[2];
//        offsprings[0] = new Chromosome(offspringsGenes[0]);
//        offsprings[1] = new Chromosome(offspringsGenes[1]);
//        return offsprings;
        population.addChromosome(new Chromosome(offspringsGenes[0]));
        population.addChromosome(new Chromosome(offspringsGenes[1]));
    }
}
