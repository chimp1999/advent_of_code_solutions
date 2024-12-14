package org.adventOfCode.Year2024.day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 {
    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay13.txt"));

        long ax = 0L,ay = 0L,bx = 0L,by = 0L,px = 0L,py = 0L;
        long res = 0L;
        Pattern pattern = Pattern.compile("(\\d+)\\D*(\\d+)");
        Matcher matcher;
        while (sc.hasNextLine()){
            matcher = pattern.matcher(sc.nextLine());
            if (matcher.find()) {
                ax = Long.parseLong(matcher.group(1));
                ay = Long.parseLong(matcher.group(2));
            }

            matcher = pattern.matcher(sc.nextLine());
            if (matcher.find()) {
                bx = Long.parseLong(matcher.group(1));
                by = Long.parseLong(matcher.group(2));
            }

            matcher = pattern.matcher(sc.nextLine());
            if (matcher.find()) {
                px = Long.parseLong(matcher.group(1));
                py = Long.parseLong(matcher.group(2));
            }

            // Using Cramer's rule to solve 2 variable simultaneous equations
            res += (part == 1) ? solveClawMachinePart1(ax,ay,bx,by,px,py) : solveClawMachinePart2(ax,ay,bx,by,px,py);

            if(sc.hasNextLine())
                sc.nextLine();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Day 13 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long solveClawMachinePart2(long ax, long ay, long bx, long by, long px, long py) {
        long increment = 10000000000000L;
        long D = findDeterminant(ax,bx,ay,by);

        if(D == 0L) return 0L;

        px += increment;
        py += increment;
        long Da = findDeterminant(px,bx,py,by);
        long Db = findDeterminant(ax,px,ay,py);

        if(Da % D == 0){
            long a = Da/D, b = Db/D;
//            if(a > 100 || b > 100)
//                return 0L;
//            else a-3,085,655,318,434+6,914,344,694,314
//            answ = 88584689879723
//            System.out.println(a+","+b);
                return (3 * a) + (1 * b);
        }

        return 0L;
    }

    private static long solveClawMachinePart1(long ax, long ay, long bx, long by, long px, long py) {
        long D = findDeterminant(ax,bx,ay,by);

        if(D == 0L) return 0L;

        long Da = findDeterminant(px,bx,py,by);
        long Db = findDeterminant(ax,px,ay,py);

        if(Da % D == 0){
            return (3 * (Da/D)) + (1 * (Db/D));
        }

        return 0L;
    }

    private static long findDeterminant(long a, long b, long c, long d) {
        return (a*d) - (b*c);
    }
}
