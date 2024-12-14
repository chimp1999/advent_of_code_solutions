package org.adventOfCode.Year2024.day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {
    private static List<List<Integer>> robots = new ArrayList<>();
    private static final long MAX_COLS = 101L;
    private static final long MAX_ROWS = 103L;
    private static final long TOTAL_TIME = 100L;

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay14.txt"));

        long q1 = 0L, q2 = 0L, q3 = 0L, q4 = 0L;
        Pattern pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");
        while (sc.hasNextLine()){
            String input = sc.nextLine();
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                int px = Integer.parseInt(matcher.group(1));
                int py = Integer.parseInt(matcher.group(2));
                int vx = Integer.parseInt(matcher.group(3));
                int vy = Integer.parseInt(matcher.group(4));

                if(part == 1) {
                    String finalQuadrant = getFinalQuadrant(px, py, vx, vy);
                    switch (finalQuadrant) {
                        case "Q1" -> q1++;
                        case "Q2" -> q2++;
                        case "Q3" -> q3++;
                        case "Q4" -> q4++;
                    }
                } else {
                    robots.add(new ArrayList<>(){{add(px);add(py);add(vx);add(vy);}});
                }
            }
        }

        BigInteger res = (part == 1) ?
                BigInteger.valueOf(q1).multiply(BigInteger.valueOf(q2).multiply(BigInteger.valueOf(q3).multiply(BigInteger.valueOf(q4)))) :
                BigInteger.valueOf(findChristmasTree());

        long endTime = System.currentTimeMillis();

        System.out.println("Day 14 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static int findChristmasTree() {
        /* No clue how to find a hidden christmas tree. Hence Just checking at what time do all robots have unique locations
        * i.e. no overlapping.
        */
        for(int time=1; time<10000; time++){
            HashSet<String> filled_locations = new HashSet<>();
            for(List<Integer> robot:robots){
                List<Long> final_position = getFinalPosition(robot.get(0),robot.get(1),robot.get(2),robot.get(3),time);
                String loc = final_position.get(0)+":"+final_position.get(1);
                filled_locations.add(loc);
            }
            if(filled_locations.size() == robots.size()){
                System.out.println("Found at time => "+time);
                for (int i = 0; i < 103; i++) {
                    for (int j = 0; j < 101; j++) {
                        if(filled_locations.contains(j+":"+i))
                            System.out.print("*");
                        else
                            System.out.print(" ");
                    }
                    System.out.println();
                }
                return time;
            }
        }
        return 0;
    }

    private static String getFinalQuadrant(int px, int py, int vx, int vy) {
        List<Long> final_position = getFinalPosition(px,py,vx,vy,TOTAL_TIME);

        if (final_position.get(0) < MAX_COLS/2 && final_position.get(1) < MAX_ROWS/2)
            return "Q1";

        if (final_position.get(0) > MAX_COLS/2 && final_position.get(1) < MAX_ROWS/2)
            return "Q2";

        if (final_position.get(0) < MAX_COLS/2 && final_position.get(1) > MAX_ROWS/2)
            return "Q3";

        if (final_position.get(0) > MAX_COLS/2 && final_position.get(1) > MAX_ROWS/2)
            return "Q4";

        return "INVALID";
    }

    private static List<Long> getFinalPosition(int px, int py, int vx, int vy, long time){
        long fx = (px + (time * vx)) % MAX_COLS;
        long fy = (py + (time * vy)) % MAX_ROWS;

        if(fx<0) fx += MAX_COLS;
        if(fy<0) fy += MAX_ROWS;

        List<Long> res = new ArrayList<>();
        res.add(fx);
        res.add(fy);
        return res;
    }
}
