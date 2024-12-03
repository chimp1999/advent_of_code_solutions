package org.adventOfCode.Year2024.day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    private static boolean isEnabled = true;

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay3.txt"));

        long res = 0L;

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            res += solveProblem(part, input);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Day 3 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long solveProblem(int part, String input) {
        long sum = 0L;
        String regexPart1 = "mul\\(\\d{1,3},\\d{1,3}\\)";
        String regexPart2 = "mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\)";

        Matcher matcher = Pattern.compile(part == 1 ? regexPart1 : regexPart2).matcher(input);

        while (matcher.find()) {
            String group = matcher.group();

            if (part == 2) {
                if (!isEnabled) {
                    if (group.equals("do()"))
                        isEnabled = true;
                    continue;
                } else {
                    if (group.equals("don't()")) {
                        isEnabled = false;
                        continue;
                    }
                    if (group.equals("do()")) continue;
                }
            }

            sum += extractNumbers(group);
        }

        return sum;
    }

    private static long extractNumbers(String group) {
        long product = 1L;
        String regex = "\\d{1,3}";

        Matcher m = Pattern.compile(regex).matcher(group);

        while (m.find()) {
            product *= Integer.parseInt(m.group());
        }

        return product;
    }
}
