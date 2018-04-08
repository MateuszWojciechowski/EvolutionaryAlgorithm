package oast.network;

import oast.algorithm.Chromosome;
import oast.algorithm.Gene;
import oast.program.Main;

import java.util.ArrayList;

public class Link {
	private int linkID, startNode, endNode, fibreNum, lambdasInFibre;
	private float fibreCost;
	public Link(int id, int startID, int endID, int fNum, float fCost, int lambdas) {
		linkID = id;
		startNode = startID;
		endNode = endID;
		fibreNum = fNum;
		fibreCost = fCost;
		lambdasInFibre = lambdas;
	}
	public String testFunction() {
		String string = startNode + " " + endNode + " " + fibreNum + " " + fibreCost + " " + lambdasInFibre;
		return string;
	}

	public int getLinkLoad(Chromosome chromosome) {
		int linkLoad = 0;
		Gene[] genes = chromosome.getGenes();
		Demand[] demands = Main.network.getDemands();
		for(int demand=0; demand < demands.length; demand++) {
			DemandPath[] paths = demands[demand].getPaths();
			for(int path=0; path < paths.length; path++) {
				ArrayList<Integer> route = paths[path].getRoute();
				for(int i=0; i < route.size(); i++) {
					if(route.get(i) == linkID) {
						int load = genes[demand].getAllocation()[path];
						linkLoad += load;
					}
				}
			}
		}
//		return new Double(Math.ceil(linkLoad/lambdasInFibre)).intValue();
		return linkLoad;
	}

	public int getFibreNum() {
		return fibreNum;
	}

	public int getCapacity() {
		return fibreNum*lambdasInFibre;
	}

	public int getLinkID() {
		return linkID;
	}
}
