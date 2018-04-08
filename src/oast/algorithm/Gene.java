package oast.algorithm;

import oast.network.Demand;
import oast.program.Main;

public class Gene {
    private int[] allocation;

    public int[] getAllocation() {
        return allocation;
    }

    public void setAllocation(int[] allocation) {
        this.allocation = allocation;
    }

    public Gene() {

    }

    public Gene(int[] allocation) {
        this.allocation = allocation;
    }

    //mutacja genu
    public void mutate() {
        int path1;
        do {
            path1 = Main.rnd.nextInt(allocation.length);
        } while(allocation[path1] == 0);    //z tej sciezki bedziemy zabierac, jesli jest zero to nie ma co zabierac
        int path2 = Main.rnd.nextInt(allocation.length);    //do tej sciezki bedziemy dodawac
        allocation[path1] -= 1;
        allocation[path2] += 1;
    }
}