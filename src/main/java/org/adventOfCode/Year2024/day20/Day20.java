package org.adventOfCode.Year2024.day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day20 {
    private static List<char[]> racetrack = new ArrayList<>();
    private static List<String> path = new ArrayList<>();
    private static List<Integer> startPosition = new ArrayList<>();
    private static List<Integer> endPosition = new ArrayList<>();
    private static final int LIMIT = 100;
    private static final int RADIUS = 20;

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay20.txt"));

        int rowCount = 0;
        while (sc.hasNextLine()) {
            char[] ch = sc.nextLine().toCharArray();

            for(int i=0; i< ch.length; i++){
                if(ch[i] == 'S') {
                    startPosition.add(rowCount);
                    startPosition.add(i);
                }
                if(ch[i] == 'E') {
                    endPosition.add(rowCount);
                    endPosition.add(i);
                }
            }
            racetrack.add(ch);
            rowCount++;
        }

        findPath();
        long res = part == 1 ? countCheatPositionsPart1() : countCheatPositionsPart2();

        long endTime = System.currentTimeMillis();

        System.out.println("Day 20 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long countCheatPositionsPart1() {
        long res = 0L;

        for(int i=1; i<racetrack.size()-1; i++) {
            char[] row = racetrack.get(i);
            for (int j = 1; j < row.length-1; j++) {
                if(row[j] == '#'){
                    if(racetrack.get(i-1)[j] == '.' && racetrack.get(i+1)[j] == '.' &&
                            calculateTimeSaved(Arrays.asList(i-1, j), Arrays.asList(i+1, j))>=LIMIT){
                        res++;
//                        System.out.println(i+","+j+" --> "+
//                                calculateTimeSaved(Arrays.asList(i-1, j), Arrays.asList(i+1, j)));
                    } else if (racetrack.get(i)[j-1] == '.' && racetrack.get(i)[j+1] == '.' &&
                            calculateTimeSaved(Arrays.asList(i, j-1), Arrays.asList(i, j+1))>=LIMIT) {
                        res++;
//                        System.out.println(i+","+j+" --> "+
//                                calculateTimeSaved(Arrays.asList(i, j-1), Arrays.asList(i, j+1)));
                    }
                }
            }
        }

        return res;
    }

    private static long countCheatPositionsPart2() {
        long res = 0L;

        for(int i=0; i<path.size()-LIMIT; i++){
            String[] start = path.get(i).split(",");

            for(int j=i+LIMIT; j<path.size(); j++){
                String[] end = path.get(j).split(",");

                int dist = Math.abs(Integer.parseInt(start[0])-Integer.parseInt(end[0])) + Math.abs(Integer.parseInt(start[1]) - Integer.parseInt(end[1]));
                int time = j-i-dist;

                if(dist <= RADIUS && time >= LIMIT)
                    res++;
            }
        }

        return res;
    }

    private static int calculateTimeSaved(List<Integer> start, List<Integer> end){
        return Math.abs(path.indexOf(end.get(0)+","+end.get(1)) - path.indexOf(start.get(0)+","+start.get(1))) - 2;
    }

    private static void findPath(){
        path.add(startPosition.get(0) + "," + startPosition.get(1));
        racetrack.get(startPosition.get(0))[startPosition.get(1)] = '.';
        racetrack.get(endPosition.get(0))[endPosition.get(1)] = '.';

        while (!startPosition.equals(endPosition)){
            // UP
            if(!path.contains((startPosition.get(0)-1) + "," + startPosition.get(1)) &&
                    racetrack.get(startPosition.get(0)-1)[startPosition.get(1)] == '.') {
                startPosition.set(0, startPosition.get(0)-1);
            }
            // Down
            else if(!path.contains((startPosition.get(0)+1) + "," + startPosition.get(1)) &&
                    racetrack.get(startPosition.get(0)+1)[startPosition.get(1)] == '.') {
                startPosition.set(0, startPosition.get(0)+1);
            }
            // Left
            else if(!path.contains(startPosition.get(0) + "," + (startPosition.get(1)-1)) &&
                    racetrack.get(startPosition.get(0))[startPosition.get(1)-1] == '.') {
                startPosition.set(1, startPosition.get(1)-1);
            }
            // Right
            else if(!path.contains(startPosition.get(0) + "," + (startPosition.get(1)+1)) &&
                    racetrack.get(startPosition.get(0))[startPosition.get(1)+1] == '.') {
                startPosition.set(1, startPosition.get(1)+1);
            }
//            System.out.println(startPosition);
            path.add(startPosition.get(0) + "," + startPosition.get(1));
        }
    }


    private static void displayRacetrack(){
        for(int i=0; i<racetrack.size(); i++){
            char[] row = racetrack.get(i);
            for(int j=0; j<row.length; j++){
                if(path.contains(i + "," + j))
                    System.out.print("O");
                else
                    System.out.print(row[j]);
            }
            System.out.println();
        }
    }
}
