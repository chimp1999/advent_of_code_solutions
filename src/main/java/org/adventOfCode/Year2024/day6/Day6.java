package org.adventOfCode.Year2024.day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day6 {
    private static char[][] grid;

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay6.txt"));

        int rowCount = 0;
        List<Integer> startPosition = new ArrayList<>();

        while (sc.hasNextLine()) {
            String input = sc.nextLine();

            if (rowCount == 0)
                grid = new char[input.length()][input.length()];

            for (int j = 0; j < input.length(); j++) {
                if (input.charAt(j) == '^') {
                    startPosition.add(rowCount);
                    startPosition.add(j);
                }
                grid[rowCount][j] = input.charAt(j);
            }

            rowCount++;
        }

        long res = part == 1 ? countVisitedLocations(startPosition) : countObstructionPossibilities(startPosition);

        long endTime = System.currentTimeMillis();

        System.out.println("Day 6 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long countVisitedLocations(List<Integer> position) {
        return identifyPath(position).size() - 1;
    }

    private static long countObstructionPossibilities(List<Integer> position) {
        long res = 0L;
        Set<String> path = identifyPath(new ArrayList<>(position));
        path.remove("Done");
        path.remove(position.get(0) + "," + position.get(1));

        for (String p : path) {
            int x = Integer.parseInt(p.split(",")[0]);
            int y = Integer.parseInt(p.split(",")[1]);
            char temp = grid[x][y];

            grid[x][y] = '#';
            Set<String> newPath = identifyPath(new ArrayList<>(position));

            if (newPath.contains("Loop"))
                res++;

            grid[x][y] = temp;

        }

        return res;
    }

    private static Set<String> identifyPath(List<Integer> position) {
        char direction = 'U';
        Set<String> path = new HashSet<>();
        List<String> t = new ArrayList<>();
        boolean loopChecker = false;
        String loopStart = "";

        while (true) {
            if (path.contains("Done"))
                break;

            direction = switch (direction) {
                case 'U' -> {
                    t = colMove(position.get(0), position.get(1), -1);
                    yield 'R';
                }
                case 'R' -> {
                    t = rowMove(position.get(0), position.get(1), 1);
                    yield 'D';
                }
                case 'D' -> {
                    t = colMove(position.get(0), position.get(1), 1);
                    yield 'L';
                }
                case 'L' -> {
                    t = rowMove(position.get(0), position.get(1), -1);
                    yield 'U';
                }
                default -> direction;
            };
            if (!t.isEmpty()) {
                String lastItem = t.get(t.size() - 1);
                if (path.containsAll(t)) {
                    if (t.contains(loopStart)) {
                        path.add("Loop");
                        break;
                    }
                    if (!loopChecker) {
                        loopChecker = true;
                        loopStart = t.get(0);
                    }
                } else loopChecker = false;
                if (!lastItem.equals("Done")) {
                    position.set(0, Integer.parseInt(lastItem.split(",")[0]));
                    position.set(1, Integer.parseInt(lastItem.split(",")[1]));
                }
                path.addAll(t);
            }
        }
        return path;
    }

    private static List<String> rowMove(int row, int col, int direction) {
        List<String> path = new ArrayList<>();

        while (true) {
            if (isPositionInvalid(row, col)) {
                path.add("Done");
                break;
            }
            if (grid[row][col] == '#')
                break;

            path.add(row + "," + col);
            col = col + direction;
        }

        return path;
    }

    private static List<String> colMove(int row, int col, int direction) {
        List<String> path = new ArrayList<>();

        while (true) {
            if (isPositionInvalid(row, col)) {
                path.add("Done");
                break;
            }
            if (grid[row][col] == '#')
                break;

            path.add(row + "," + col);
            row = row + direction;
        }

        return path;
    }

    private static boolean isPositionInvalid(int row, int col) {
        return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
    }
}
