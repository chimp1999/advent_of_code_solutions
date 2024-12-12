package org.adventOfCode.Year2024.day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

public class Day10 {
    private static List<String[]> topographicMap = new ArrayList<>();
    private static List<String> trailheads = new ArrayList<>();

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay10.txt"));

        while (sc.hasNextLine()) {
            String[] input = sc.nextLine().split("");
            topographicMap.add(input);
            for (int i = 0; i < input.length; i++) {
                if (input[i].equals("0"))
                    trailheads.add((topographicMap.size() - 1) + ":" + i);
            }
        }

        long res = part == 1 ? calculateTrailheadScores() : calculateTrailheadRatings();

        long endTime = System.currentTimeMillis();

        System.out.println("Day 10 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long calculateTrailheadRatings() {
        AtomicLong res = new AtomicLong();

        trailheads.forEach(trailhead ->
                res.addAndGet(calculateRating(Integer.parseInt(trailhead.split(":")[0]),
                        Integer.parseInt(trailhead.split(":")[1]))));

        return res.get();
    }

    private static long calculateRating(int row, int col) {
        if (isInvalidPosition(row, col))
            return 0;

        char currentVal = topographicMap.get(row)[col].charAt(0);

        if (currentVal == '9') {
            return 1;
        }

        long up = 0, down = 0, left = 0, right = 0;

        if (!isInvalidPosition(row - 1, col) && topographicMap.get(row - 1)[col].charAt(0) == currentVal + 1)
            up = calculateRating(row - 1, col);

        if (!isInvalidPosition(row + 1, col) && topographicMap.get(row + 1)[col].charAt(0) == currentVal + 1)
            down = calculateRating(row + 1, col);

        if (!isInvalidPosition(row, col - 1) && topographicMap.get(row)[col - 1].charAt(0) == currentVal + 1)
            left = calculateRating(row, col - 1);

        if (!isInvalidPosition(row, col + 1) && topographicMap.get(row)[col + 1].charAt(0) == currentVal + 1)
            right = calculateRating(row, col + 1);

        return up + down + left + right;
    }

    private static long calculateTrailheadScores() {
        AtomicLong res = new AtomicLong();

        trailheads.forEach(trailhead ->
                res.addAndGet(calculateScore(Integer.parseInt(trailhead.split(":")[0]),
                        Integer.parseInt(trailhead.split(":")[1]), new ArrayList<>())));

        return res.get();
    }

    private static long calculateScore(int row, int col, List<String> trailends) {
        if (isInvalidPosition(row, col))
            return 0;

        char currentVal = topographicMap.get(row)[col].charAt(0);

        if (currentVal == '9') {
            if (trailends.contains(row + ":" + col))
                return 0;
            else {
                trailends.add(row + ":" + col);
                return 1;
            }
        }

        long up = 0, down = 0, left = 0, right = 0;

        if (!isInvalidPosition(row - 1, col) && topographicMap.get(row - 1)[col].charAt(0) == currentVal + 1)
            up = calculateScore(row - 1, col, trailends);

        if (!isInvalidPosition(row + 1, col) && topographicMap.get(row + 1)[col].charAt(0) == currentVal + 1)
            down = calculateScore(row + 1, col, trailends);

        if (!isInvalidPosition(row, col - 1) && topographicMap.get(row)[col - 1].charAt(0) == currentVal + 1)
            left = calculateScore(row, col - 1, trailends);

        if (!isInvalidPosition(row, col + 1) && topographicMap.get(row)[col + 1].charAt(0) == currentVal + 1)
            right = calculateScore(row, col + 1, trailends);

        return up + down + left + right;
    }

    private static boolean isInvalidPosition(int row, int col) {
        return (row < 0 || row >= topographicMap.size() || col < 0 || col >= topographicMap.get(0).length);
    }
}
