package org.adventOfCode.Year2024.day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day15 {
    private static List<Integer> robotPosition = new ArrayList<>();
    private static List<String> boxPositions = new ArrayList<>();
    private static List<String> wallPositions = new ArrayList<>();
    private static int MAX_ROWS = 0, MAX_COLS = 0;

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay15.txt"));

        int rowCount = 0;
        while (sc.hasNextLine()){
            String input = sc.nextLine();

            if(input.isBlank())
                break;

            if(MAX_COLS == 0)
                MAX_COLS = input.length() * (part == 1 ? 1 : 2);

            for(int i=0; i<input.length(); i++){
                char ch = input.charAt(i);

                if(ch == '@'){
                    robotPosition.add(rowCount);
                    robotPosition.add(part == 1 ? i : (i*2));
                } else {
                    String position = rowCount + ":" + (part == 1 ? i : (i*2) + ":" + (i * 2 +1));
                    if (ch == 'O') {
                        boxPositions.add(position);
                    } else if (ch == '#') {
                        wallPositions.add(position);
                    }
                }
            }

            rowCount++;
        }
        MAX_ROWS = rowCount;

        while (sc.hasNextLine()){
            String[] input = sc.nextLine().split("");

            for(String ch : input){
                if (part == 1)
                    processMovePart1(ch.charAt(0));
                else
                    processMovePart2(ch.charAt(0));
            }
        }
        System.out.println("Final Layout -->");
        displayMap(part);

        long res = sumOfGpsCoordinates();

        long endTime = System.currentTimeMillis();

        System.out.println("Day 15 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static void processMovePart1(char ch) {
        List<Integer> position = new ArrayList<>(robotPosition);

        int i;
        boolean isMovePossible = false;

        switch (ch) {
            case '^':
                position.set(0, position.get(0) - 1);
                for (i = position.get(0); i > 0; i--) {
                    String p = i + ":" + position.get(1);

                    if (wallPositions.contains(p))
                        break;
                    if (!boxPositions.contains(p)) {
                        isMovePossible = true;
                        break;
                    }
                }

                if (isMovePossible) {
                    for (int j = 0; j < boxPositions.size(); j++) {
                        String[] temp = boxPositions.get(j).split(":");
                        if (Integer.parseInt(temp[0]) > i && Integer.parseInt(temp[0]) <= position.get(0) && Integer.parseInt(temp[1]) == position.get(1)) {
                            boxPositions.set(j, (Integer.parseInt(temp[0]) - 1) + ":" + temp[1]);
                        }
                    }
                    robotPosition = new ArrayList<>(position);
                }
                break;
            case 'v':
                position.set(0, position.get(0) + 1);
                for(i=position.get(0); i<MAX_ROWS; i++){
                    String p = i+":"+ position.get(1);

                    if(wallPositions.contains(p))
                        break;
                    if(!boxPositions.contains(p)){
                        isMovePossible = true;
                        break;
                    }
                }

                if(isMovePossible){
                    for(int j = 0; j<boxPositions.size(); j++){
                        String[] temp = boxPositions.get(j).split(":");
                        if(Integer.parseInt(temp[0]) < i && Integer.parseInt(temp[0]) >= position.get(0) && Integer.parseInt(temp[1]) == position.get(1)){
                            boxPositions.set(j, (Integer.parseInt(temp[0])+1) +":" + temp[1]);
                        }
                    }
                    robotPosition = new ArrayList<>(position);
                }
                break;
            case '<':
                position.set(1, position.get(1) - 1);
                for(i=position.get(1); i>0; i--){
                    String p = position.get(0) + ":" + i;

                    if(wallPositions.contains(p))
                        break;
                    if(!boxPositions.contains(p)){
                        isMovePossible = true;
                        break;
                    }
                }

                if(isMovePossible){
                    for(int j = 0; j<boxPositions.size(); j++){
                        String[] temp = boxPositions.get(j).split(":");
                        if(Integer.parseInt(temp[1]) > i && Integer.parseInt(temp[1]) <= position.get(1) && Integer.parseInt(temp[0]) == position.get(0)){
                            boxPositions.set(j, temp[0] + ":" + (Integer.parseInt(temp[1])-1));
                        }
                    }
                    robotPosition = new ArrayList<>(position);
                }
                break;
            case '>':
                position.set(1, position.get(1) + 1);
                for(i=position.get(1); i<MAX_COLS; i++){
                    String p = position.get(0) + ":" + i;

                    if(wallPositions.contains(p))
                        break;
                    if(!boxPositions.contains(p)){
                        isMovePossible = true;
                        break;
                    }
                }

                if(isMovePossible){
                    for(int j = 0; j<boxPositions.size(); j++){
                        String[] temp = boxPositions.get(j).split(":");
                        if(Integer.parseInt(temp[1]) < i && Integer.parseInt(temp[1]) >= position.get(1) && Integer.parseInt(temp[0]) == position.get(0)){
                            boxPositions.set(j, temp[0] + ":" + (Integer.parseInt(temp[1])+1));
                        }
                    }
                    robotPosition = new ArrayList<>(position);
                }
                break;
        }
    }

    private static void processMovePart2(char ch) {
        List<Integer> position = new ArrayList<>(robotPosition);

        int i;
        boolean isMovePossible = false;

        switch (ch) {
            case '^':
                position.set(0, position.get(0) - 1);
                Set<Integer> checkCols1 = new HashSet<>();
                checkCols1.add(position.get(1));
                for (i = position.get(0); i > 0; i--) {
                    Set<Integer> t = new HashSet<>();
                    int cnt = 0;
                    for (int c : checkCols1) {
                        String p1 = i + ":" + c + ":" + (c + 1),
                                p2 = i + ":" + (c - 1) + ":" + c;
                        if (wallPositions.contains(p1) || wallPositions.contains(p2))
                            return;
                        if(boxPositions.contains(p1)) {
                            t.add(c); t.add(c+1);
                        }
                        if(boxPositions.contains(p2)) {
                            t.add(c-1); t.add(c);
                        }
                        if (!boxPositions.contains(p1) && !boxPositions.contains(p2)) {
                            cnt++;
                        }
                    }
                    if(cnt==checkCols1.size()) {
                        isMovePossible = true;
                        break;
                    }
                    checkCols1 = new HashSet<>(t);
                }

                if (isMovePossible) {
                    int x = position.get(0);
                    List<String> boxPositionsCopy = new ArrayList<>(boxPositions);
                    Set<Integer> affectedCols = new HashSet<>();
                    affectedCols.add(position.get(1));
                    while(x>i){
                        Set<Integer> newSet = new HashSet<>();
                        for(int j = 0; j<boxPositions.size(); j++){
                            String[] temp = boxPositions.get(j).split(":");
                            if(Integer.parseInt(temp[0]) == x &&
                                    (affectedCols.contains(Integer.parseInt(temp[1])) ||
                                            affectedCols.contains(Integer.parseInt(temp[2])))){
                                newSet.add(Integer.parseInt(temp[1]));
                                newSet.add(Integer.parseInt(temp[2]));
                                boxPositionsCopy.set(j, (Integer.parseInt(temp[0]) - 1) + ":" + temp[1] + ":" + temp[2]);
                            }
                        }
                        affectedCols = new HashSet<>(newSet);
                        x--;
                    }
                    boxPositions = new ArrayList<>(boxPositionsCopy);
                    robotPosition = new ArrayList<>(position);
                }
                break;
            case 'v':
                position.set(0, position.get(0) + 1);
                Set<Integer> checkCols2 = new HashSet<>();
                checkCols2.add(position.get(1));
                for (i = position.get(0); i < MAX_ROWS; i++) {
                    Set<Integer> t = new HashSet<>();
                    int cnt = 0;
                    for (int c : checkCols2) {
                        String p1 = i + ":" + c + ":" + (c + 1),
                                p2 = i + ":" + (c - 1) + ":" + c;
                        if (wallPositions.contains(p1) || wallPositions.contains(p2))
                            return;
                        if(boxPositions.contains(p1)) {
                            t.add(c); t.add(c+1);
                        }
                        if(boxPositions.contains(p2)) {
                            t.add(c-1); t.add(c);
                        }
                        if (!boxPositions.contains(p1) && !boxPositions.contains(p2)) {
                            cnt++;
                        }
                    }
                    if(cnt==checkCols2.size()) {
                        isMovePossible = true;
                        break;
                    }
                    checkCols2 = new HashSet<>(t);
                }

                if (isMovePossible) {
                    int x = position.get(0);
                    List<String> boxPositionsCopy = new ArrayList<>(boxPositions);
                    Set<Integer> affectedCols = new HashSet<>();
                    affectedCols.add(position.get(1));
                    while(x<i){
                        Set<Integer> newSet = new HashSet<>();
                        for(int j = 0; j<boxPositions.size(); j++){
                            String[] temp = boxPositions.get(j).split(":");
                            if(Integer.parseInt(temp[0]) == x &&
                                    (affectedCols.contains(Integer.parseInt(temp[1])) ||
                                            affectedCols.contains(Integer.parseInt(temp[2])))){
                                newSet.add(Integer.parseInt(temp[1]));
                                newSet.add(Integer.parseInt(temp[2]));
                                boxPositionsCopy.set(j, (Integer.parseInt(temp[0]) + 1) + ":" + temp[1] + ":" + temp[2]);
                            }
                        }
                        affectedCols = new HashSet<>(newSet);
                        x++;
                    }
                    boxPositions = new ArrayList<>(boxPositionsCopy);
                    robotPosition = new ArrayList<>(position);
                }
                break;
            case '<':
                position.set(1, position.get(1) - 1);
                for (i = position.get(1); i > 1; i -= 2) {
                    String p = position.get(0) + ":" + (i - 1) + ":" + i;

                    if (wallPositions.contains(p))
                        break;
                    if (!boxPositions.contains(p)) {
                        isMovePossible = true;
                        break;
                    }
                }

                if (isMovePossible) {
                    for (int j = 0; j < boxPositions.size(); j++) {
                        String[] temp = boxPositions.get(j).split(":");
                        if (Integer.parseInt(temp[1]) > i && Integer.parseInt(temp[1]) <= position.get(1) && Integer.parseInt(temp[0]) == position.get(0)) {
                            boxPositions.set(j, temp[0] + ":" + (Integer.parseInt(temp[1]) - 1) + ":" + (Integer.parseInt(temp[2]) - 1));
                        }
                    }
                    robotPosition = new ArrayList<>(position);
                }
                break;
            case '>':
                position.set(1, position.get(1) + 1);
                for (i = position.get(1); i < MAX_COLS; i += 2) {
                    String p = position.get(0) + ":" + i + ":" + (i + 1);

                    if (wallPositions.contains(p))
                        break;
                    if (!boxPositions.contains(p)) {
                        isMovePossible = true;
                        break;
                    }
                }

                if (isMovePossible) {
                    for (int j = 0; j < boxPositions.size(); j++) {
                        String[] temp = boxPositions.get(j).split(":");
                        if (Integer.parseInt(temp[1]) < i && Integer.parseInt(temp[1]) >= position.get(1) && Integer.parseInt(temp[0]) == position.get(0)) {
                            boxPositions.set(j, temp[0] + ":" + (Integer.parseInt(temp[1]) + 1) + ":" + (Integer.parseInt(temp[2]) + 1));
                        }
                    }
                    robotPosition = new ArrayList<>(position);
                }
                break;
        }
    }

    private static long sumOfGpsCoordinates(){
        long res = 0L;

        for(String pos : boxPositions){
            String[] coordinates = pos.split(":");
            res += (100 * Long.parseLong(coordinates[0])) + Long.parseLong(coordinates[1]);
        }

        return res;
    }

    private static void displayMap(int part){
        for(int i=0; i<MAX_ROWS; i++){
            for (int j=0; j<MAX_COLS; j++){
                String pos = part == 1 ? i+":"+j : i+":"+(j)+":"+(j+1);

                if(robotPosition.get(0) == i && robotPosition.get(1) == (j))
                    System.out.print("@");
                else if (boxPositions.contains(pos)) {
                    System.out.print(part == 1 ? "O" : "[]");
                    if(part==2) j++;
                }
                else if (wallPositions.contains(pos)) {
                    System.out.print(part == 1 ? "#" : "##");
                    if(part==2) j++;
                }
                else
                    System.out.print(".");
            }
            System.out.println();
        }
    }
}
