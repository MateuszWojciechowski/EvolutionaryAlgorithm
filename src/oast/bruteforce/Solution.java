package oast.bruteforce;

import oast.network.Demand;

public class Solution {
    private SolutionDemand[] solutionDemands;

    public SolutionDemand[] getSolutionDemands() {
        return solutionDemands;
    }

    public int getSolutionDemandsNumber() {
        return solutionDemands.length;
    }

    public Solution(Demand[] demands) {
        solutionDemands = new SolutionDemand[demands.length];
        for(int i = 0; i < solutionDemands.length; i++) {
            int[] allocation = new int[demands[i].getPaths().length];
            for(int j=0; j < demands[i].getPaths().length; j++) {
                allocation[j] = 0;
            }
            solutionDemands[i] = new SolutionDemand(i+1, demands[i].getVolume(), allocation);
        }
    }
}
