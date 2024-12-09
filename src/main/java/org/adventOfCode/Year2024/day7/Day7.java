package org.adventOfCode.Year2024.day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day7 {
    private static List<List<String>> equations = new ArrayList<>();

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay7.txt"));

        while (sc.hasNextLine()) {
            String[] input = sc.nextLine().split(":");
            equations.add(new ArrayList<>() {{
                add(input[0]);
                addAll(List.of(input[1].trim().split(" ")));
            }});
        }

        long res = calculateTotalCalibrationResult(part);

        long endTime = System.currentTimeMillis();

        System.out.println("Day 7 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long calculateTotalCalibrationResult(int part) {
        long res = 0;

        for (List<String> equation : equations) {
            if (isEquationValid(Long.parseLong(equation.get(0)),
                    new ArrayList<>(equation.subList(1, equation.size())), "+", part) ||
                    isEquationValid(Long.parseLong(equation.get(0)),
                            new ArrayList<>(equation.subList(1, equation.size())), "*", part) ||
                    (part == 2 && isEquationValid(Long.parseLong(equation.get(0)),
                            new ArrayList<>(equation.subList(1, equation.size())), "||", part)))
                res += Long.parseLong(equation.get(0));
        }

        return res;
    }

    private static boolean isEquationValid(long target, List<String> values, String operator, int part) {
        if (Long.parseLong(values.get(0)) > target)
            return false;

        if (values.size() == 1)
            return Long.parseLong(values.get(0)) == target;


        values.set(0, operator.equals("+") ?
                Long.toString(Long.parseLong(values.get(0)) + Long.parseLong(values.get(1))) :
                operator.equals("*") ?
                        Long.toString(Long.parseLong(values.get(0)) * Long.parseLong(values.get(1))) :
                        values.get(0) + values.get(1));
        values.remove(1);


        return isEquationValid(target, new ArrayList<>(values), "+", part) ||
                isEquationValid(target, new ArrayList<>(values), "*", part) ||
                (part ==2 && isEquationValid(target, new ArrayList<>(values), "||", part));
    }
}
