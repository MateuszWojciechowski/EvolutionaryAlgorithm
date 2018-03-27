package oast.program;

import oast.input.InputReader;
import oast.network.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
    public static Random rnd;
    public static Network network;

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Pseudorandom numbers generator seed:");
        long seed = 0;
        try {
            String seedString = br.readLine();
            seed = Long.valueOf(seedString);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        rnd = new Random(seed);
        System.out.println("Network configuration file:");
        String path = "/Users/mateuszwojciechowski/Documents/studia/studia magisterskie/semestr 1/OAST/net4.txt";
//        try {
//            path = br.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        InputReader reader = new InputReader(path);
        network = reader.read();
    }
}
