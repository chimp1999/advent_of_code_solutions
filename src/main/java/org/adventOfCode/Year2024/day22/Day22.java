package org.adventOfCode.Year2024.day22;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day22 {
    private static Map<Long, Map<String, Long>> memory = new HashMap<>();
    private static Set<String> all_sequences = new HashSet<>();

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay22.txt"));

        long res = 0L;
        while (sc.hasNextLine()) {
            long input = Long.parseLong(sc.nextLine());
            if (part == 1)
                res += solvePart1(input);
            else
                solvePart2(input);
        }

        if (part == 2)
            res = findBestResult();

        long endTime = System.currentTimeMillis();

        System.out.println("Day 22 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long findBestResult() {
        long max_bananas = 0L;

        for (String sequence : all_sequences) {
            long res = 0L;

            for (Long number : memory.keySet()) {
                res += memory.get(number).getOrDefault(sequence, 0L);
            }

            max_bananas = Math.max(max_bananas, res);
        }

        return max_bananas;
    }

    private static long solvePart1(long secretNummber) {
        for (int i = 0; i < 2000; i++) {
            secretNummber = generateNewSecretNumber(secretNummber);
        }

        return secretNummber;
    }

    private static void solvePart2(long secretNumber) {
        List<Long> diff_list = new ArrayList<>();
        memory.put(secretNumber, new HashMap<>());
        Map<String, Long> this_number_memory = memory.get(secretNumber);
        long prevDigit = secretNumber % 10;

        for (int i = 0; i < 2000; i++) {
            secretNumber = generateNewSecretNumber(secretNumber);
            long curDigit = secretNumber % 10;
            long diff = curDigit - prevDigit;
            prevDigit = curDigit;

            diff_list.add(diff);

            if (diff_list.size() < 4) continue;

            if (diff_list.size() > 4)
                diff_list.remove(0);

            String sequence = diff_list.toString();

            if (this_number_memory.containsKey(sequence)) continue;

            all_sequences.add(sequence);

            this_number_memory.put(sequence, curDigit);
        }
    }

    private static long generateNewSecretNumber(long secretNummber) {
        secretNummber = ((secretNummber * 64) ^ secretNummber) % 16777216;
        secretNummber = ((long) Math.floor(secretNummber / 32.0) ^ secretNummber) % 16777216;
        secretNummber = ((secretNummber * 2048) ^ secretNummber) % 16777216;
        return secretNummber;
    }
}
