package org.example.Year2023.day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;

public class Day2 {
    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2023/inputDay2.txt"));

        int result1 = 0;
        BigInteger result2 = BigInteger.ZERO;

        while(sc.hasNextLine()){
            String input = sc.nextLine();
            String[] game = input.split(":");
            int gameId = Integer.parseInt(game[0].split(" ")[1]);

            if(part == 1){
                boolean isGameValid = checkGamePart1(game[1]);
                if(isGameValid) {
                    result1 += gameId;
                }
            } else {
                result2 = result2.add(checkGamePart2(game[1]));
            }
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Day 2 Part "+part+" : "+ (part == 1 ? result1 : result2)+"     Execution Time : "+(endTime-startTime)+"ms");
    }

    private static boolean checkGamePart1(String gameDetails) {
        String[] shows = gameDetails.split(";");

        int maxRed = 12,maxGreen = 13, maxBlue = 14;
        //1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        for(String show: shows){
            String[] items = show.split(",");
            for(String item:items){
                String[] details = item.trim().split(" ");
                int val = Integer.parseInt(details[0].trim());
//                System.out.println(Arrays.toString(details)+" "+val+" "+details[1]);
                switch (details[1]){
                    case "red": if(val >maxRed) return false; else break;
                    case "blue": if(val >maxBlue) return false; else break;
                    case "green": if(val >maxGreen) return false; else break;
                }
            }
        }

        return true;
    }

    private static BigInteger checkGamePart2(String gameDetails) {
        String[] shows = gameDetails.split(";");

        int maxRed = 0,maxGreen = 0, maxBlue = 0;
        //1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        for(String show: shows){
            String[] items = show.split(",");
            for(String item:items){
                String[] details = item.trim().split(" ");
                int val = Integer.parseInt(details[0].trim());
//                System.out.println(Arrays.toString(details)+" "+val+" "+details[1]);
                switch (details[1]){
                    case "red": maxRed = Math.max(maxRed,val); break;
                    case "blue": maxBlue = Math.max(maxBlue,val); break;
                    case "green": maxGreen = Math.max(maxGreen,val); break;
                }
            }
        }
//        System.out.println(maxRed+","+maxBlue+","+maxGreen);
        return BigInteger.valueOf(maxRed).multiply(BigInteger.valueOf(maxBlue).multiply(BigInteger.valueOf(maxGreen)));
    }
}
