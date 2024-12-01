package org.example.Year2023.day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day7 {
    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2023/inputDay7.txt"));

        while (sc.hasNextLine()) {
            String[] input = sc.nextLine().split(" ");
            String hand = input[0];
            long bid = Long.parseLong(input[1]);
        }

        long endTime = System.currentTimeMillis();

//        System.out.println("\nDay 7 Part " + part + " : " + result + "     Execution Time : " + (endTime - startTime) + "ms");
    }
}
