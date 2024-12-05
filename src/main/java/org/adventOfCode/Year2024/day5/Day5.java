package org.adventOfCode.Year2024.day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day5 {
    private static Map<Integer, List<Integer>> rules = new HashMap<>();

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay5.txt"));

        boolean isRule = true;
        long res = 0L;

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if(input.isBlank()) {
                isRule = false;
                continue;
            }

            if(isRule) {
                String[] temp = input.split("\\|");
                Integer key = Integer.parseInt(temp[0]);
                Integer value = Integer.parseInt(temp[1]);
                if(!rules.containsKey(key)){
                    rules.put(key, new ArrayList<>(){{add(value);}});
                } else {
                    rules.get(key).add(value);
                }
            } else {
                res += processUpdate(part, input);
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Day 5 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long processUpdate(int part, String input) {
        String[] update = input.split(",");

        if(validateUpdate(update)){
            return (part == 1) ? Long.parseLong(update[update.length / 2]) : 0L;
        } else {
            return (part == 1) ? 0L : fixOrder(update);
        }
    }

    private static long fixOrder(String[] update) {
        int[] correctOrder = new int[update.length];

        for(int i=0; i<update.length; i++){
            int curVal = Integer.parseInt(update[i]);
            List<Integer> rule = rules.getOrDefault(curVal, new ArrayList<>());
            int count = 0;

            for(int j=0; j<update.length; j++){
                if(i != j && rule.contains(Integer.parseInt(update[j])))
                    count++;
            }

            correctOrder[update.length - 1 - count] = curVal;
        }

        return correctOrder[correctOrder.length / 2];
    }

    private static boolean validateUpdate(String[] update) {
        for(int i=0; i<update.length-1; i++){
            List<Integer> rule = rules.get(Integer.parseInt(update[i]));
            if(rule == null)
                return false;

            for(int j=i+1; j<update.length; j++){
                if(!rule.contains(Integer.parseInt(update[j])))
                    return false;
            }
        }

        return true;
    }
}
