package oast.network;

import java.util.ArrayList;

public class DemandPath {
		private int ID;
		private int length;
		public int getLength() {
			return length;
		}
		
		private ArrayList<Integer> route = new ArrayList<Integer>();
		
		public DemandPath(int ID, int... links) {
			for(int i=0; i < links.length; i++) {
				route.add(links[i]);
			}
			this.ID = ID;
			length = links.length;
		}
		
		public String testFunction() {
			String string = ID + " " + length + " ";
			for(int i=0; i < route.size(); i++) {
				string += route.get(i) + " ";
			}
			string += "\n";
			return string;
		}
}