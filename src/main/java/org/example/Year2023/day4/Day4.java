package org.example.Year2023.day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

public class Day4 {
    private static int[] cardCount = new int[209];

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2023/inputDay4.txt"));

        BigInteger result = BigInteger.ZERO;
        Arrays.fill(cardCount, 1);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (part == 1)
                result = result.add(calculatePointsPart1(input.split(":")[1].trim()));
            else {
                String[] val = input.split(":");
                String[] temp = val[0].trim().split(" ");
                int gameId = Integer.parseInt(temp[temp.length - 1]) - 1;
                calculatePointsPart2(gameId, val[1].trim());
            }
        }

        if (part == 2) result = BigInteger.valueOf(Arrays.stream(cardCount).sum());

        long endTime = System.currentTimeMillis();

        System.out.println("Day 4 Part " + part + " : " + result + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static BigInteger calculatePointsPart1(String card) {
        Set<Integer> matches = findMatches(card);
        return matches.isEmpty() ? BigInteger.ZERO : BigInteger.valueOf((long) Math.pow(2, matches.size() - 1));
    }

    private static void calculatePointsPart2(int gameId, String card) {
        Set<Integer> matches = findMatches(card);

        int incrementVal = cardCount[gameId];

        for (int i = gameId + 1; i <= gameId + matches.size(); i++) {
            cardCount[i] += incrementVal;
        }
    }

    private static Set<Integer> findMatches(String card) {
        Set<Integer> winningNumbers = new HashSet<>();

        for (String s : card.split("\\|")[0].trim().split(" ")) {
            if (!s.isEmpty())
                winningNumbers.add(Integer.valueOf(s));
        }

        Set<Integer> numbersWeGot = new HashSet<>();

        for (String s : card.split("\\|")[1].trim().split(" ")) {
            if (!s.isEmpty())
                numbersWeGot.add(Integer.valueOf(s));
        }

        numbersWeGot.retainAll(winningNumbers);

        return numbersWeGot;
    }
}
