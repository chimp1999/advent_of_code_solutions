package org.adventOfCode.Year2024.day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day8 {
    private static Map<String, List<String>> antenna_map = new HashMap<>();
    private static long totalRows = 0L, totalCols = 0L;

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay8.txt"));

        while (sc.hasNextLine()) {
            String[] input = sc.nextLine().split("");

            if (totalCols == 0L)
                totalCols = input.length;

            for (int col = 0; col < input.length; col++) {
                if (!input[col].equals(".")) {
                    if (antenna_map.containsKey(input[col])) {
                        antenna_map.get(input[col]).add(totalRows + "," + col);
                    } else {
                        int finalCol = col;
                        antenna_map.put(input[col], new ArrayList<>() {{
                            add(totalRows + "," + finalCol);
                        }});
                    }
                }
            }
            totalRows++;
        }

        long res = countAntinodes(part);

        long endTime = System.currentTimeMillis();

        System.out.println("Day 8 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long countAntinodes(int part) {
        Set<String> res = new HashSet<>();

        antenna_map.forEach((key, value) -> {
            if(part == 2) res.addAll(value);

            for (int i = 0; i < value.size(); i++) {
                String[] pos = value.get(i).split(",");
                int r = Integer.parseInt(pos[0]);
                int c = Integer.parseInt(pos[1]);
                long rowRange = Math.min(r, totalRows - r);
                long colRange = Math.min(c, totalCols - c);

                for (int j = 0; j < value.size(); j++) {
                    if (j == i) continue;

                    String[] node = value.get(j).split(",");
                    if (Integer.parseInt(node[0]) >= r - rowRange &&
                            Integer.parseInt(node[0]) <= r + rowRange &&
                            Integer.parseInt(node[1]) >= c - colRange &&
                            Integer.parseInt(node[1]) <= c + colRange) {

                        int antinodeX = (r + (r - Integer.parseInt(node[0])));
                        int antinodeY = (c + (c - Integer.parseInt(node[1])));

                        if (part == 1) {
                            if (antinodeX >= 0 && antinodeX < totalRows && antinodeY >= 0 && antinodeY < totalCols) {
                                res.add(antinodeX + "," + antinodeY);

                                System.out.println(value.get(j) + " -> " + antinodeX + "," + antinodeY);
                            }
                        } else {
                            while (antinodeX >= 0 && antinodeX < totalRows && antinodeY >= 0 && antinodeY < totalCols) {
                                res.add(antinodeX + "," + antinodeY);

                                antinodeX = (antinodeX + (r - Integer.parseInt(node[0])));
                                antinodeY = (antinodeY + (c - Integer.parseInt(node[1])));
                            }
                        }
                    }
                }
            }
        });

        return res.size();
    }
}
