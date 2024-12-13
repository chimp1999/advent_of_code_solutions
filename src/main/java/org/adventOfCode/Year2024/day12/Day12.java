package org.adventOfCode.Year2024.day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day12 {
    private static List<String[]> farm = new ArrayList<>();

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay12.txt"));

        while (sc.hasNextLine()){
            farm.add(sc.nextLine().split(""));
        }

        long res = part == 1 ? calculateFenceCostPart1() : calculateFenceCostPart2();

        long endTime = System.currentTimeMillis();

        System.out.println("Day 12 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long calculateFenceCostPart1() {
        Set<String> visited = new HashSet<>();
        long totalCost = 0L;

        for(int i=0; i<farm.size(); i++){
            String[] row = farm.get(i);

            for (int j=0; j<row.length; j++){
                if(!visited.contains(i+":"+j)){
                    visited.add(i+":"+j);
                    long area = 1L, perimeter = 0L;

                    Stack<String> stack = new Stack<>();
                    stack.push(i+":"+j);

                    while (!stack.isEmpty()){
                        List<Integer> loc = Arrays.stream(stack.pop().split(":")).map(Integer::parseInt).toList();
                        List<List<Integer>> adjacentLocations = new ArrayList<>(){{
                            add(new ArrayList<>(){{add(loc.get(0)-1); add(loc.get(1));}});
                            add(new ArrayList<>(){{add(loc.get(0)+1); add(loc.get(1));}});
                            add(new ArrayList<>(){{add(loc.get(0)); add(loc.get(1)-1);}});
                            add(new ArrayList<>(){{add(loc.get(0)); add(loc.get(1)+1);}});
                        }};

                        for(List<Integer> curr : adjacentLocations){
                            if(isLocationInvalid(curr.get(0), curr.get(1)) ||
                                    !farm.get(curr.get(0))[curr.get(1)].equals(farm.get(loc.get(0))[loc.get(1)])){
                                perimeter++;
                            } else if (!visited.contains(curr.get(0)+":"+curr.get(1))) {
                                area++;
                                visited.add(curr.get(0)+":"+curr.get(1));
                                stack.push(curr.get(0)+":"+curr.get(1));
                            }
                        }
                    }
                    totalCost += area * perimeter;
                }
            }
        }
        return totalCost;
    }

    private static long calculateFenceCostPart2() {
        Set<String> visited = new HashSet<>();
        long totalCost = 0L;

        for(int i=0; i<farm.size(); i++){
            String[] row = farm.get(i);

            for (int j=0; j<row.length; j++){
                if(!visited.contains(i+":"+j)){
                    visited.add(i+":"+j);
                    List<String> shape = new ArrayList<>();
                    shape.add(i+":"+j);

                    Stack<String> stack = new Stack<>();
                    stack.push(i+":"+j);

                    while (!stack.isEmpty()){
                        List<Integer> loc = Arrays.stream(stack.pop().split(":")).map(Integer::parseInt).toList();
                        List<List<Integer>> adjacentLocations = new ArrayList<>(){{
                            add(new ArrayList<>(){{add(loc.get(0)-1); add(loc.get(1));}});
                            add(new ArrayList<>(){{add(loc.get(0)+1); add(loc.get(1));}});
                            add(new ArrayList<>(){{add(loc.get(0)); add(loc.get(1)-1);}});
                            add(new ArrayList<>(){{add(loc.get(0)); add(loc.get(1)+1);}});
                        }};

                        for(List<Integer> curr : adjacentLocations){
                            if (!isLocationInvalid(curr.get(0), curr.get(1)) &&
                                    farm.get(loc.get(0))[loc.get(1)].equals(farm.get(curr.get(0))[curr.get(1)]) &&
                                    !shape.contains(curr.get(0)+":"+curr.get(1))) {
                                shape.add(curr.get(0)+":"+curr.get(1));
                                visited.add(curr.get(0)+":"+curr.get(1));
                                stack.push(curr.get(0)+":"+curr.get(1));
                            }
                        }
                    }
                    long corners = 0L;
                    for(String loc : shape){
                        corners += countCorners(loc, shape);
                    }
                    totalCost += corners * shape.size();
                }
            }
        }
        return totalCost;
    }

    private static long countCorners(String loc, List<String> shape) {
        long num_of_corners = 0L;
        List<Integer> current = Arrays.stream(loc.split(":")).map(Integer::parseInt).toList();
        List<List<Integer>> cornerLocations = new ArrayList<>(){{
            add(new ArrayList<>(){{add(current.get(0)-1); add(current.get(1)-1);}});
            add(new ArrayList<>(){{add(current.get(0)-1); add(current.get(1)+1);}});
            add(new ArrayList<>(){{add(current.get(0)+1); add(current.get(1)-1);}});
            add(new ArrayList<>(){{add(current.get(0)+1); add(current.get(1)+1);}});
        }};

        for(List<Integer> cornerLocation : cornerLocations){
            List<Integer> neighbour1 = new ArrayList<>(){{add(current.get(0)); add(cornerLocation.get(1));}};
            List<Integer> neighbour2 = new ArrayList<>(){{add(cornerLocation.get(0)); add(current.get(1));}};

            if(shape.contains(cornerLocation.get(0)+":"+cornerLocation.get(1))) {
                if (!shape.contains(neighbour1.get(0) + ":" + neighbour1.get(1)) && !shape.contains(neighbour2.get(0) + ":" + neighbour2.get(1)))
                    num_of_corners++;
            }
            else if (shape.contains(neighbour1.get(0) + ":" + neighbour1.get(1)) == shape.contains(neighbour2.get(0) + ":" + neighbour2.get(1))) {
                num_of_corners++;
            }
        }

        return num_of_corners;
    }

    private static boolean isLocationInvalid(int row, int col){
        return row < 0 || row >= farm.size() || col < 0 || col >= farm.get(0).length;
    }
}
