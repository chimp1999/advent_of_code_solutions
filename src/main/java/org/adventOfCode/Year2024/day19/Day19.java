package org.adventOfCode.Year2024.day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day19 {
    private static List<String> availableTowelPatterns;
    private static Map<String, Boolean> memory1 = new HashMap<>();
    private static Map<String, Long> memory2 = new HashMap<>();

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay19.txt"));

        availableTowelPatterns = new ArrayList<>(Arrays.asList(sc.nextLine().split(", ")));

        sc.nextLine();

        long res = 0L;

        while (sc.hasNextLine()) {
            String input = sc.nextLine();

            if (part == 1) {
                if (isDesignPossible(input))
                    res++;
            } else {
                res += countNumberOfWaysOfArrangement(input, "");
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Day 19 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static boolean isDesignPossible(String input) {
        if (input.isBlank())
            return true;

        if (memory1.containsKey(input))
            return memory1.get(input);

        List<String> potentialPatterns = availableTowelPatterns.stream()
                .filter(input::startsWith)
                .toList();

        for (String pattern : potentialPatterns) {
            boolean res = isDesignPossible(input.substring(pattern.length()));
            memory1.put(input.substring(pattern.length()), res);
            if (res)
                return true;
        }
        return false;
    }

    private static long countNumberOfWaysOfArrangement(String input, String combination) {
        if (input.isBlank())
            return 1;

        if (memory2.containsKey(input))
            return memory2.get(input);

        List<String> potentialPatterns = availableTowelPatterns.stream()
                .filter(input::startsWith)
                .toList();

        long res = 0;
        for (String pattern : potentialPatterns) {
            res += countNumberOfWaysOfArrangement(input.substring(pattern.length()),
                    combination + (combination.isEmpty() ? "" : ":") + pattern);
            memory2.put(input, res);
        }

        return res;
    }
}
