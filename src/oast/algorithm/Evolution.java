package oast.algorithm;

import oast.network.Link;
import oast.program.Main;

public class Evolution {
    private static float fitnessFunction(Chromosome chromosome) {
        Link[] links = Main.network.getLinks();
        int[] loads = new int[links.length];
        int fitness = 0;
        for(int i=0; i < links.length; i++) {
            loads[i] = links[i].getLinkLoad(chromosome);
            if(loads[i] > fitness) fitness = loads[i];
        }

        return fitness;
    }

    public static void start(Population population) {

    }
}
