package oast.program;

import oast.algorithm.Chromosome;
import oast.algorithm.Evolution;
import oast.algorithm.Population;
import oast.bruteforce.BruteForce;
import oast.bruteforce.Solution;
import oast.input.InputReader;
import oast.network.Network;
import oast.output.OutputWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
    public static Random rnd;
    public static Network network;
    public static Chromosome solution;
    public static Solution bruteForceSolution;

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //wczytanie pliku
        System.out.println("Network configuration file:");
//        String path = "/Users/mateuszwojciechowski/Documents/studia/studia magisterskie/semestr 1/OAST/net12_1.txt";
//        String path = "/Users/mateuszwojciechowski/Documents/studia/studia magisterskie/semestr 1/OAST/net4.txt";
        String path = "";
        try {
            path = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputReader reader = new InputReader(path);
        network = reader.read();

        int choice = 0;
        System.out.println("[1] Genetic algorithm");
        System.out.println("[2] Brute force");
        try {
            String algorithmString = br.readLine();
            choice = Integer.valueOf(algorithmString);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        switch(choice) {
            case 1:
                /*
                1. Określenie liczności populacji
                2. Określenie prawdopodobieństwa krzyżowania i mutacji
                3. Wybór kryterium stopu (czas, liczba generacji, liczba mutacji, brak poprawy w N kolejnych generacjach)
                4. Wskazanie ziarna dla generatora pseudolosowego
                */

                //1
                System.out.println("Population size: ");
                int populationSize = 10;
                try {
                    String sizeString = br.readLine();
                    populationSize = Integer.valueOf(sizeString);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                //2
                System.out.println("Crossover probability (from 0 to 1): ");
                try {
                    String crossoverString = br.readLine();
                    Evolution.crossoverProbability = Double.valueOf(crossoverString);
                    if(Evolution.crossoverProbability < 0 || Evolution.crossoverProbability > 1) {
                        System.out.println("Probability must be a value from 0 to 1.0.");
                        System.out.println("Set default probability 1.0.");
                        Evolution.crossoverProbability = 1;
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("Mutation probability (from 0 to 1): ");
                try {
                    String mutationString = br.readLine();
                    Evolution.mutationProbability = Double.valueOf(mutationString);
                    if(Evolution.mutationProbability < 0 || Evolution.mutationProbability > 1) {
                        System.out.println("Probability must be a value from 0 to 1.0.");
                        System.out.println("Set default probability 1.0.");
                        Evolution.mutationProbability = 1;
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                //3
                System.out.println("Stop criterion:");
                System.out.println("[1] Time");
                System.out.println("[2] Generations number");
                System.out.println("[3] Mutations number");
                System.out.println("[4] Lack of improvement in N generations");
                System.out.println("[5] Until problem is solved");
                int stopCriterion = 0;
                try {
                    String stopCriterionString = br.readLine();
                    stopCriterion  = Integer.valueOf(stopCriterionString);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                switch(stopCriterion) {
                    case 1:
                        Evolution.stopCriterion = Evolution.StopCriterion.Time;
                        System.out.println("Time to stop: ");
                        try {
                            String timeString = br.readLine();
                            Evolution.stopCriterionNumber  = Integer.valueOf(timeString);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        Evolution.stopCriterion = Evolution.StopCriterion.GenerationsNumber;
                        System.out.println("Generations number to stop: ");
                        try {
                            String generationsString = br.readLine();
                            Evolution.stopCriterionNumber  = Integer.valueOf(generationsString);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        Evolution.stopCriterion = Evolution.StopCriterion.MutationsNumber;
                        System.out.println("Mutations number to stop: ");
                        try {
                            String mutationsString = br.readLine();
                            Evolution.stopCriterionNumber  = Integer.valueOf(mutationsString);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        Evolution.stopCriterion = Evolution.StopCriterion.LackOfImprovement;
                        System.out.println("Number of generations without improvement to stop: ");
                        try {
                            String generationsString = br.readLine();
                            Evolution.stopCriterionNumber  = Integer.valueOf(generationsString);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        Evolution.stopCriterion = Evolution.StopCriterion.ProblemSolved;
                        break;
                    default:
                        Evolution.stopCriterion = Evolution.StopCriterion.ProblemSolved;
                        break;
                }
                //4
                System.out.println("Pseudorandom numbers generator seed:");
                long seed = 0;
                try {
                    String seedString = br.readLine();
                    seed = Long.valueOf(seedString);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                rnd = new Random(seed);

                Population population = new Population(populationSize, network.getDemands());
                solution = Evolution.start(population);
                //zapis wyników
                System.out.println("Results file: ");
                String resultsPath = "";
                try {
                    resultsPath = br.readLine();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                OutputWriter writer = new OutputWriter(resultsPath);
                writer.write();
                break;
            case 2:
                bruteForceSolution = BruteForce.start(network.getDemands());
                //zapis wyników
                System.out.println("Results file: ");
                String bruteForceResultsPath = "";
                try {
                    bruteForceResultsPath = br.readLine();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                OutputWriter bruteForceWriter = new OutputWriter(bruteForceResultsPath);
                bruteForceWriter.writeBruteForce();
                break;
        }
    }
}
