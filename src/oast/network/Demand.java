package oast.network;

public class Demand {
	private int startNode, endNode, volume;
	private int[][] path;	//pierwszy indeks to numer ścieżki, drugi to przebieg tej ścieżki w postaci ID łączy
	public Demand(int startID, int endID, int volume, int[][] path) {
		startNode = startID;
		endNode = endID;
		this.volume = volume;
		this.path = path;
	}
}
