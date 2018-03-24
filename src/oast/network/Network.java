package oast.network;

public class Network {
	private Demand[] demands;
	private Link[] links;
	public Network() {
		
	}
	public Network(Demand[] demands, Link[] links) {
		this.demands = demands;
		this.links = links;
	}
}
