package oast.algorithm;

import oast.network.Link;
import oast.program.Main;

import java.util.ArrayList;
import java.util.Collections;

public class Evolution {
    public static double crossoverProbability = 1;
    public static double mutationProbability = 1;

    public static double fitnessFunction(Chromosome chromosome) {
        Link[] links = Main.network.getLinks();
        int[] loads = new int[links.length];
        int fitness = 0;
        for(int i=0; i < links.length; i++) {
            loads[i] = links[i].getLinkLoad(chromosome);
            if((loads[i] > links[i].getCapacity()) && loads[i] - links[i].getCapacity() > fitness)
                fitness = loads[i] - links[i].getCapacity();
            }

        return fitness;
    }

    public static void start(Population p) {
        boolean stopCriterion = false;
        int populationNum = p.getChromosomesNumber();
        ArrayList<Chromosome> chromosomes = p.getChromosomes();
        int generation = 1;
        while(!stopCriterion) {
            /*
            1. Tworzymy zbiór par chromosomów
            2. Crossover par chromosomów z danym prawdpopodobieństwem
            3. Mutacja genów z danym prawdopodobieństwem.
            4. Sortowanie chromosomów od najlepszego do najgorszego.
            5. Selekcja najlepszych.
            6. Kryterium stopu.
             */
            System.out.println("===========");
            System.out.println("Generation" + generation);
            System.out.println("===========");
            //1
            Collections.shuffle(chromosomes, Main.rnd);
            //2
            Population offsprings = new Population();
            for(int i=0; i < chromosomes.size()/2; i=i+2) {
                float random = Main.rnd.nextFloat();
                if(random < crossoverProbability) {
                    Chromosome.crossover(chromosomes.get(i), chromosomes.get(i+1), offsprings);
                }
            }
            chromosomes.addAll(offsprings.getChromosomes());
            //3
            for(int i=0; i < chromosomes.size(); i++) {
                float random = Main.rnd.nextFloat();
                if(random < mutationProbability) {
                    Gene[] genes = chromosomes.get(i).getGenes();
                    genes[Main.rnd.nextInt(chromosomes.get(i).getGenesNum())].mutate();
                    chromosomes.set(i, new Chromosome(genes));
                }
            }
            //4
            Collections.sort(chromosomes, p);
            //5
            for(int i=populationNum; i < chromosomes.size(); i++) {
                chromosomes.remove(i);
            }
            //6
//            for(int i=0; i < chromosomes.size(); i++) {
//                System.out.println(i+1 + ": " + fitnessFunction(chromosomes.get(i)));
//            }
            double fitness = fitnessFunction(chromosomes.get(0));
            System.out.println("Fitness: " + fitness);
            if(fitness <= 0)
                stopCriterion = true;
            generation++;
        }
    }
}
