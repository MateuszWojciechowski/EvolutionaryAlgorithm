package oast.network;

public class Link {
	private int startNode, endNode, fibreNum, lambdasInFibre;
	private float fibreCost;
	public Link(int startID, int endID, int fNum, float fCost, int lambdas) {
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
}
