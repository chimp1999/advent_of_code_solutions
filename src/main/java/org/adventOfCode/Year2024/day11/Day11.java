package org.adventOfCode.Year2024.day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day11 {
private static final Map<Long, Long> cache = new HashMap<>();

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay11.txt"));

        List<Long> stoneArrangement = new ArrayList<>(Arrays.stream(sc.nextLine().split(" ")).map(Long::parseLong).toList());

        long res = (part == 1) ?
                stoneArrangement.stream().mapToLong(stone -> calculateNumberOfStones(stone, 25)).sum() :
                stoneArrangement.stream().mapToLong(stone -> calculateNumberOfStones(stone, 75)).sum();

        long endTime = System.currentTimeMillis();

        System.out.println("Day 11 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long calculateNumberOfStones(long stone, int numberOfBlinks) {
        if (numberOfBlinks == 0) {
            return 1L;
        }

        if (cache.containsKey(stone * 100 + numberOfBlinks)) {
            return cache.get(stone * 100 + numberOfBlinks);
        }

        long result = processStone(stone).stream().mapToLong(num -> calculateNumberOfStones(num, numberOfBlinks - 1)).sum();
        cache.put(stone * 100 + numberOfBlinks, result);
        return result;
    }

    private static List<Long> processStone(long n) {
        if (n == 0L) {
            return List.of(1L);
        }

        String numStr = String.valueOf(n);
        if (numStr.length() % 2 == 0) {
            int mid = numStr.length() / 2;
            return List.of(Long.parseLong(numStr.substring(0, mid)), Long.parseLong(numStr.substring(mid)));
        }

        return List.of(n * 2024L);
    }
}
