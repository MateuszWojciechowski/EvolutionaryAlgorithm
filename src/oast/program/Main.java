package oast.program;

import oast.input.InputReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
    public static Random rnd;

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Pseudorandom numbers generator seed:");
        long seed = 0;
        try {
            seed = Long.getLong(br.readLine());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        rnd = new Random(seed);
        System.out.println("Podaj ścieżkę do pliku opisującego sieć:");
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
}
