package oast.network;

import java.util.ArrayList;

public class Demand {
	private int startNode, endNode, volume;
	private DemandPath[] paths;

	public Demand(int startID, int endID, int volume, DemandPath[] paths) {
		startNode = startID;
		endNode = endID;
		this.volume = volume;
		this.paths = paths;
	}
	
	public String testFunction() {
		String string = startNode + " " + endNode + " " + volume + "\n\n";
		for(int i=0; i < paths.length; i++) {
			string += paths[i].testFunction();
		}
		return string;
	}

	public DemandPath[] getPaths() {
		return paths;
	}

    public int getVolume() {
        return volume;
    }
}
