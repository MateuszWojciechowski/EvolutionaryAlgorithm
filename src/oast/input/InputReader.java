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
	
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = "";
		try {
			path = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputReader reader = new InputReader(path);
		reader.read();
	}
	
	public Network read() {
		Link[] links;
		try {
			String currentLine;
			currentLine = bufferedReader.readLine();
			links = new Link[Integer.parseInt(currentLine)];
			int lineNum = 0;
			while(!(currentLine = bufferedReader.readLine()).equals("-1")) {
				String[] line = currentLine.split(" ");
				if(line.length > 4){
					int startID = Integer.parseInt(line[0]);
					int endID = Integer.parseInt(line[1]);
					int fibresNum = Integer.parseInt(line[2]);
					float fibreCost = Float.parseFloat(line[3]);
					int lambdas = Integer.parseInt(line[4]);
					links[lineNum] = new Link(startID, endID, fibresNum, fibreCost, lambdas);
					lineNum++;
				}
			}
//			TEST WCZYTYWANIA ŁĄCZY
//			for(int i=0; i < links.length; i++) {
//				System.out.println(links[i].testFunction());
//			}
			
			
			return new Network(new Demand[2], links);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		return new Network();
	}
}
