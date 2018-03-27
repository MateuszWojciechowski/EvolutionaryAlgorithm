package oast.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import oast.network.*;

public class InputReader {
	private File file;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	
	public InputReader(String filePath) {
		file = new File(filePath);
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		bufferedReader = new BufferedReader(fileReader);
	}
	
	public Network read() {
		Link[] links;
		Demand[] demands;
		try {
			String currentLine;
			
			//wczytanie liczby łączy
			while(true) {
				currentLine = bufferedReader.readLine();
				if(!currentLine.isEmpty()) {
					links = new Link[Integer.parseInt(currentLine)];
					break;
				}
			}
			
			//wczytanie łączy do tablicy
			int lineNum = 0;
			while(!(currentLine = bufferedReader.readLine()).equals("-1")) {
				String[] line = currentLine.split(" ");
				if(line.length > 4){
					int startID = Integer.parseInt(line[0]);
					int endID = Integer.parseInt(line[1]);
					int fibresNum = Integer.parseInt(line[2]);
					float fibreCost = Float.parseFloat(line[3]);
					int lambdas = Integer.parseInt(line[4]);
					links[lineNum] = new Link(lineNum + 1, startID, endID, fibresNum, fibreCost, lambdas);
					lineNum++;
				}
			}
//			TEST WCZYTYWANIA ŁĄCZY
			System.out.println("---ŁĄCZA---");
			for(int i=0; i < links.length; i++) {
				System.out.println(links[i].testFunction());
			}
			
			//wczytanie liczby zapotrzebowań
			while(true) {
				currentLine = bufferedReader.readLine();
				if(!currentLine.isEmpty()) {
					demands = new Demand[Integer.parseInt(currentLine)];
					break;
				}
			}
			
			//wczytywanie zapotrzebowań
			for(int i=0; i < demands.length; i++) {
				int startID, endID, volume;
				while(true) {
					currentLine = bufferedReader.readLine();
					if(!currentLine.isEmpty()) {
						String[] line = currentLine.split(" ");
						startID = Integer.parseInt(line[0]);
						endID = Integer.parseInt(line[1]);
						volume = Integer.parseInt(line[2]);
						break;
					}
				}
				
				//wczytanie ścieżek zapotrzebowań
				currentLine = bufferedReader.readLine();
				int pathsNum = Integer.parseInt(currentLine);
				DemandPath[] paths = new DemandPath[pathsNum];
				for(int j=0; j < pathsNum; j++) {
					currentLine = bufferedReader.readLine();
					String[] line = currentLine.split(" ");
					int pathID = Integer.parseInt(line[0]);
					int[] path = new int[line.length - 1];
					for(int k=1; k < line.length; k++) {
						path[k-1] = Integer.parseInt(line[k]);
					}
					paths[j] = new DemandPath(pathID, path);
				}
				demands[i] = new Demand(startID, endID, volume, paths);
			}
			
			//TEST WCZYTANIA ZAPOTRZEBOWAŃ
			System.out.println("---ZAPOTRZEBOWANIA---");
			for(int i=0; i < demands.length; i++) {
				System.out.println(demands[i].testFunction());
			}
			
			return new Network(demands, links);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		return new Network();
	}
}
