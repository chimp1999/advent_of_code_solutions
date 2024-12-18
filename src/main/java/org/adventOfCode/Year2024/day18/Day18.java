package org.adventOfCode.Year2024.day18;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day18 {
    private static List<String> corruptedLocation = new ArrayList<>();
    private static int size = 71;
    private static final List<List<Integer>> directions = new ArrayList<>(){{
        add(new ArrayList<>(){{add(0); add(1);}});
        add(new ArrayList<>(){{add(0); add(-1);}});
        add(new ArrayList<>(){{add(-1); add(0);}});
        add(new ArrayList<>(){{add(1); add(0);}});
    }};

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay18.txt"));

        int rowCount = 0;
        while (sc.hasNextLine()){
            String[] input = sc.nextLine().split(",");
            corruptedLocation.add(input[1]+","+input[0]);
            rowCount++;
            if(part == 1 && rowCount == 1024) break;
        }

        if(part == 1) displayMap(corruptedLocation.size());

        String res = (part == 1) ? String.valueOf(findShortestPath(rowCount)) : findFirstByteThatBlocksPath();

        long endTime = System.currentTimeMillis();

        System.out.println("Day 18 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static String findFirstByteThatBlocksPath() {
        // We already know that after 1024 bytes fall, there is still a path available. So we start after that.
        for(int i = 1024; i<corruptedLocation.size(); i++){
            if(findShortestPath(i) == -1){
                displayMap(i);
                String[] temp = corruptedLocation.get(i-1).split(",");
                return temp[1]+","+temp[0];
            }
        }

        return null;
    }

    private static long findShortestPath(int range) {
        // Using BFS to find the shortest path
        Queue<List<Integer>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>(corruptedLocation.subList(0, range));

        queue.offer(new ArrayList<>(){{add(0);add(0);add(0);}});
        visited.add("0,0");

        while (!queue.isEmpty()){
            List<Integer> current = queue.poll();

            if(current.get(0) == size-1 && current.get(1) == size-1)
                return current.get(2);

            for(List<Integer> direction : directions){
                int r = current.get(0)+direction.get(0);
                int c = current.get(1)+direction.get(1);

                if(isPositionValid(r, c) && !visited.contains(r+","+c)){
                    queue.offer(new ArrayList<>(){{
                        add(r);
                        add(c);
                        add(current.get(2)+1);
                    }});
                    visited.add(r+","+c);
                }
            }
        }

        return -1;
    }

    private static boolean isPositionValid(int row, int col){
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    private static void displayMap(int limit) {
        System.out.println("Matrix size --> "+size);
        System.out.println("Number of bytes --> "+limit);

        List<String> blockage = new ArrayList<>(corruptedLocation.subList(0,limit));

        for(int i=0; i< size; i++){
            for (int j=0; j< size; j++){
                if(blockage.contains(i+","+j))
                    System.out.print("#");
                else
                    System.out.print(".");
            }
            System.out.println();
        }
    }
}
