package org.adventOfCode.Year2023.day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day5 {
    private static List<String> values = new LinkedList<>();
    private static boolean[] isChanged;

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2023/inputDay5.txt"));

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (input.isEmpty()) continue;

            if (input.contains("seeds")) {
                if(part == 1)
                    values = new ArrayList<>(List.of(input.split(":")[1].trim().split(" ")));
                else
                    getSeeds(input.split(":")[1].trim().split(" "));
            } else if (input.equals("seed-to-soil map:") ||
                    input.equals("soil-to-fertilizer map:") ||
                    input.equals("fertilizer-to-water map:") ||
                    input.equals("water-to-light map:") ||
                    input.equals("light-to-temperature map:") ||
                    input.equals("temperature-to-humidity map:") ||
                    input.equals("humidity-to-location map:")) {

                String category = input;
                isChanged = new boolean[values.size()];
                Arrays.fill(isChanged, false);

                input = sc.nextLine();
                while (!input.isEmpty()) {
                    mapper(input);
                    if (sc.hasNextLine())
                        input = sc.nextLine();
                    else break;
                }

                System.out.println(category + values);
            }
        }

        OptionalLong result = values.stream().mapToLong(Long::parseLong).min();

        long endTime = System.currentTimeMillis();

        System.out.println("\nDay 5 Part " + part + " : " + result.getAsLong() + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static void getSeeds(String[] input) {
        long total = 0;
        for(int i=18; i<20; i+=2){
            long c = Long.parseLong(input[i]);
            long limit = Long.parseLong(input[i])+20;
total += Long.parseLong(input[i+1]);
            while (c < limit){
                values.add(Long.toString(c));
                c++;
            }
//            System.out.println(values.size());
        }

        System.out.println("Input - "+total+values);
    }

    private static void mapper(String map) {
        String[] mapValues = map.split(" ");

        for (int i = 0; i < values.size(); i++) {
            long key = Long.parseLong(values.get(i));

            if (!isChanged[i] && key >= Long.parseLong(mapValues[1]) && key < Long.parseLong(mapValues[1]) + Long.parseLong(mapValues[2])) {
                values.set(i, Long.toString(Long.parseLong(mapValues[0]) + (key - Long.parseLong(mapValues[1]))));
                isChanged[i] = true;
            }
        }
    }
}
