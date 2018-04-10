package oast.algorithm;

import oast.network.Link;
import oast.program.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Evolution {
    public static double crossoverProbability = 1;
    public static double mutationProbability = 1;
    public enum StopCriterion { Time, GenerationsNumber, MutationsNumber, LackOfImprovement, ProblemSolved }
    public static StopCriterion stopCriterion = StopCriterion.Time;
    public static int stopCriterionNumber = 0;

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

    public static Chromosome start(Population p) {
        long startTime = System.currentTimeMillis();
        boolean stop = false;
        int populationNum = p.getChromosomesNumber();
        ArrayList<Chromosome> chromosomes = p.getChromosomes();
        int generation = 1;
        int mutations = 0;
        int crossovers = 0;
        int generationsWithoutImprovement = 0;
        double previousFitness = 0;
        while(!stop) {
            /*
            1. Tworzymy zbiór par chromosomów
            2. Crossover par chromosomów z danym prawdpopodobieństwem
            3. Mutacja genów z danym prawdopodobieństwem.
            4. Sortowanie chromosomów od najlepszego do najgorszego.
            5. Selekcja najlepszych.
            6. Kryterium stopu.
             */
            System.out.println("=============");
            System.out.println("Generation " + generation);
            System.out.println("-------------");
            //1
            Collections.shuffle(chromosomes, Main.rnd);
            //2
            Population offsprings = new Population();
            for(int i=0; i < chromosomes.size(); i=i+2) {
                float random = Main.rnd.nextFloat();
                if(random < crossoverProbability) {
                    Chromosome.crossover(chromosomes.get(i), chromosomes.get(i+1), offsprings);
                    crossovers++;
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
                    mutations++;
                }
            }
            //4
            Collections.sort(chromosomes, p);
            //5
            int numberOfChromosomes = chromosomes.size();
            for(int i=populationNum; i < numberOfChromosomes; i++) {
                chromosomes.remove(populationNum);
            }
            //6
            double fitness = fitnessFunction(chromosomes.get(0));
            System.out.println("Best chromosome fitness: " + fitness);
            System.out.println("Crossovers: " + crossovers);
            System.out.println("Mutations: " + mutations);
            if(fitness <= 0)
                stop = true;
            switch(stopCriterion) {
                case Time:
                    if(System.currentTimeMillis() - startTime > stopCriterionNumber*1000)
                        stop = true;
                    break;
                case GenerationsNumber:
                    if(generation >= stopCriterionNumber)
                        stop = true;
                    break;
                case MutationsNumber:
                    if(mutations > stopCriterionNumber)
                        stop = true;
                    break;
                case LackOfImprovement:
                    if(generation > 1) {
                        if(previousFitness <= fitness)
                            generationsWithoutImprovement++;
                        else
                            generationsWithoutImprovement = 0;
                    }
                    if(generationsWithoutImprovement >= stopCriterionNumber)
                        stop = true;
                    break;
                default:
            }
            previousFitness = fitness;
            generation++;
        }
        return chromosomes.get(0);
    }
}
