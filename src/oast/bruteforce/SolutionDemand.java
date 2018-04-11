package oast.bruteforce;

public class SolutionDemand {
    private int demandID;
    private int[] allocation;
    private int volume;

    public int getDemandID() {
        return demandID;
    }

    public int getVolume() {
        return volume;
    }

    public int[] getAllocation() {
        return allocation;
    }

    public String getAllocationString() {
        String string = "";
        for(int i : allocation) {
            string += String.valueOf(i) + " ";
        }
        return string;
    }

    public void setAllocation(int[] allocation) {
        this.allocation = allocation;
    }

    public int getDemandPathsNumber() {
        return allocation.length;
    }

    public SolutionDemand(int demandID, int volume, int[] allocation) {
        this.demandID = demandID;
        this.allocation = allocation;
        this.volume = volume;
    }
}
