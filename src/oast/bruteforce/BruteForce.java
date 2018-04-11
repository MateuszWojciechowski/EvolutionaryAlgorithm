package oast.bruteforce;

import oast.network.Demand;
import oast.network.Link;
import oast.program.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteForce {
    private static Solution solution;
    private static boolean stop = false;
    public static Solution start(Demand[] demands) {
//        boolean stop = false;
//        solution = new Solution(demands);
//        SolutionDemand[] solutionDemands = solution.getSolutionDemands();
//        while(!stop) {
//            /*
//            1. Wygenerowanie kolejnego rozwiązania
//            2. Sprawdzenie fitness, jeśli 0 -> stop
//             */
//            //1
//            //przejście po wszystkich zapotrzebowaniach
//            for(int i=0; i < solutionDemands.length; i++) {
//                int volume = solutionDemands[i].getVolume();
//                int pathsNumber = solutionDemands[i].getDemandPathsNumber();
//                int[] allocation = new int[pathsNumber];
//                //ograniczenie na ilość składników w sumie, jeśli ścieżek jest mniej niż składników to dalsze rozkładanie nie ma sensu
//                for(int j=1; j < pathsNumber; j++) {
//                    int x = volume;
//                    if(j != 1) {
//                        x -= j;
//                    }
//                    //przejście po tablicy alokacji
//                    for(int k=0; k < j+1; k++) {
//                        allocation[k] = x;
//                    }
//                }
//            }
//        }

        solution = new Solution(demands);
//        //generuj początkowe alokacje
//        for(int i=0; i < solutionDemands.length; i++) {
//            solutionDemands[i].setAllocation(computeNextAllocation(solutionDemands[i].getVolume(), solutionDemands[i].getAllocation()));
//        }
        computeDemand(solution.getSolutionDemands()[0], true);
        return solution;
    }

    private static void computeDemand(SolutionDemand demand, boolean newAllocation) {
        /*
        1. Generuj alokację
        2. Jeśli nie jesteś ostatni, wywołaj algorytm dla następnego zapotrzebowania
        3. Generuj permutację
        4. Sprawdź fitness
         */
        //1
        //Jeśli ma być nowa alokacja to generujemy
        if(newAllocation) {
            demand.setAllocation(computeNextAllocation(demand.getVolume(), demand.getAllocation()));
            System.out.println("Demand " + String.valueOf(demand.getDemandID()) + ": New allocation - " + demand.getAllocationString());
        }
        Arrays.sort(demand.getAllocation());
        System.out.println("Demand " + String.valueOf(demand.getDemandID()) + ": New permutation - " + demand.getAllocationString());
        //2
        if(demand.getDemandID() < Main.network.getDemandsNumber())
            computeDemand(solution.getSolutionDemands()[demand.getDemandID()], newAllocation);
        //3
        int[] prevAllocation;
        while(!stop) {
            //4
            double fitness = fitnessFunction(solution);
            System.out.println("Fitness: " + String.valueOf(fitness));
            if(fitness == 0){
                stop = true;
                break;
            }
            prevAllocation = demand.getAllocation().clone();
            demand.setAllocation(nextPermutation(demand.getAllocation()));
            if(Arrays.equals(prevAllocation, demand.getAllocation()))
                break;
            System.out.println("Demand " + String.valueOf(demand.getDemandID()) + ": New permutation - " + demand.getAllocationString());
            if(demand.getDemandID() < Main.network.getDemandsNumber())
                computeDemand(solution.getSolutionDemands()[demand.getDemandID()], false);
        }
    }

    private static int factorial(int x) {
        int result = 1;
        for(int i=1; i <= x; i++)
            result *= i;
        return result;
    }

    private static int[] nextPermutation(int[] allocation) {
        /*
        Lecę od końca po tablicy alokacji i szukam sytuacji, gdy element bliżej końca będzie większy od poprzedniego.
        Wtedy szukam najmniejszej większej wartości od pozycji dalej od końca.
        Resztę sortuję rosnąco.
         */
        for(int i=allocation.length-1; i > 0; i--) {
            if(allocation[i] > allocation[i-1]) {
                int nextIndex = i;
                for(int j=i; j < allocation.length; j++) {
                    if(allocation[j] < allocation[nextIndex] && allocation[j] > allocation[i-1]) {
                        nextIndex = j;
                    }
                }
                int temp = allocation[i-1];
                allocation[i-1] = allocation[nextIndex];
                allocation[nextIndex] = temp;
                Arrays.sort(allocation, i, allocation.length);
                break;
            }
        }
        return allocation;
    }

    private static int[] computeNextAllocation(int volume, int[] allocation) {
        int[] newAllocation = allocation;
        //badanie głębokości alkoacji - ile pierwszych miejsc w tablicy jest zajętych
        int depth = 0;
        for(int i=0; i < newAllocation.length; i++) {
            if(newAllocation[i] != 0)
                depth++;
            else {
                if(i+1 == newAllocation.length)
                    break;
                else {
                    if(newAllocation[i+1] == 0)
                        break;
                    else
                        depth++;
                }
            }
        }
        //jeśli na pierwszej pozycji doszliśmy do zera to znaczy że zwiększamy głębokość alokacji
        if(newAllocation[0] == 0) {
            if(depth == 0){
                allocation[0] = volume;
                return newAllocation;
            }
            else
                depth++;
            if(depth > newAllocation.length)
                return allocation;
            newAllocation[0] = volume - depth + 1;  //ustawiamy na pierwszej pozycji odpowiednią liczbę
            //a na kolejnych jedynki
            for(int i=1; i < depth; i++) {
                newAllocation[i] = 1;
            }
        }
        else {
            newAllocation[0]--;
            newAllocation[1]++;
        }

        return newAllocation;
    }

    public static double fitnessFunction(Solution solution) {
        Link[] links = Main.network.getLinks();
        int[] loads = new int[links.length];
        int fitness = 0;
        for(int i=0; i < links.length; i++) {
            loads[i] = links[i].getLinkLoad(solution);
            if((loads[i] > links[i].getCapacity()) && loads[i] - links[i].getCapacity() > fitness)
                fitness = loads[i] - links[i].getCapacity();
        }

        return fitness;
    }
}
