package org.adventOfCode.Year2024.day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2 {
    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay2.txt"));

        int resCount = 0;
        while(sc.hasNextLine()){
            String report = sc.nextLine();
            String[] levels = report.split(" ");

            if(checkLevel(part, levels)) {
                resCount ++;
//                System.out.println(Arrays.toString(levels));
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Day 2 Part "+part+" : "+ resCount +"     Execution Time : "+(endTime-startTime)+"ms");
    }

    private static boolean checkLevel(int part, String[] levels) {
        boolean isAscending = Integer.parseInt(levels[1]) - Integer.parseInt(levels[0]) > 0;

        for (int i = 1; i < levels.length; i++) {
            int diff = Integer.parseInt(levels[i]) - Integer.parseInt(levels[i - 1]);
            int tolerance = 0;

            if (Math.abs(diff) < 1 ||
                    Math.abs(diff) > 3 ||
                    (isAscending &&  diff <= 0) ||
                    (!isAscending && diff >= 0)) {
                if(part == 1)
                    return false;
                if(part == 2)
                    tolerance++;
            }

        }
//        if(tolerance > 1) return false;

        return true;
    }
}
