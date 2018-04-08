package oast.output;

import oast.algorithm.Gene;
import oast.network.Demand;
import oast.network.DemandPath;
import oast.network.Link;
import oast.program.Main;

import java.io.*;

public class OutputWriter {
    private File file;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    public OutputWriter() {
        file = new File("/Users/mateuszwojciechowski/Documents/studia/studia magisterskie/semestr 1/OAST/solution.txt");
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        bufferedWriter = new BufferedWriter(fileWriter);
    }

    public void write() {
        try{
//            bufferedWriter.write(Main.network.getLinksNumber());
            //number of links
            bufferedWriter.write(String.valueOf(Main.network.getLinksNumber()));
            bufferedWriter.newLine(); bufferedWriter.newLine();
            //link load
            Link[] links = Main.network.getLinks();
            for(int i=0; i < links.length; i++) {
                bufferedWriter.write(String.valueOf(links[i].getLinkID()) + " " + String.valueOf(links[i].getLinkLoad(Main.solution)) + " " + String.valueOf(links[i].getCapacity()));
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            //number of demands
            bufferedWriter.write(String.valueOf(Main.network.getDemandsNumber()));
            bufferedWriter.newLine(); bufferedWriter.newLine();
            //demand flow
            Demand[] demands = Main.network.getDemands();
            for(int i=0; i < demands.length; i++) {
                DemandPath[] paths = demands[i].getPaths();
                bufferedWriter.write(String.valueOf(i+1) + " " + String.valueOf(demands[i].getPaths().length));
                bufferedWriter.newLine();
                for(int j=0; j < paths.length; j++) {
                    Gene[] genes = Main.solution.getGenes();
                    bufferedWriter.write(String.valueOf(paths[j].getID()) + " " + genes[i].getAllocation()[j]);
                    bufferedWriter.newLine();
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
