package org.adventOfCode.Year2024.day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day4 {

    private static List<char[]> grid = new ArrayList<>();
    private static final String referenceWord = "XMAS";

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay4.txt"));

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            grid.add(input.toCharArray());
        }

        long res = countXmas(part);

        long endTime = System.currentTimeMillis();

        System.out.println("Day 4 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long countXmas(int part) {
        long count = 0L;
        int[] dirX = {-1,-1,-1, 0,0,1,1,1};
        int[] dirY = {-1, 0, 1,-1,1,-1,0,1};

        for(int row = 0; row < grid.size(); row++){
            for(int col = 0; col < grid.get(row).length; col++){
                if(part == 1 && grid.get(row)[col] == referenceWord.charAt(0)){
                    // Search in 8 directions
                    for(int k = 0 ; k < dirX.length ; k++){
                        if(searchWord(row, col, new int[]{dirX[k], dirY[k]}, 0)) {
                            count++;
                        }
                    }
                }

                if(part == 2 && grid.get(row)[col] == 'A' && searchXmas(row, col)){
                    count++;
                }
            }
        }

        return count;
    }

    private static boolean searchXmas(int row, int col) {
        if (isCoordInvalid(row - 1, col - 1) ||
                isCoordInvalid(row - 1, col + 1) ||
                isCoordInvalid(row + 1, col - 1) ||
                isCoordInvalid(row + 1, col + 1)
        )
            return false;

        return ((grid.get(row - 1)[col - 1] == 'M' && grid.get(row + 1)[col + 1] == 'S') ||
                (grid.get(row - 1)[col - 1] == 'S' && grid.get(row + 1)[col + 1] == 'M')) &&
                ((grid.get(row - 1)[col + 1] == 'M' && grid.get(row + 1)[col - 1] == 'S') ||
                        (grid.get(row - 1)[col + 1] == 'S' && grid.get(row + 1)[col - 1] == 'M'));
    }

    private static boolean searchWord(int row, int col, int[] direction, int index) {
        if(index == referenceWord.length())
            return true;

        if(isCoordInvalid(row, col) || grid.get(row)[col] != referenceWord.charAt(index))
            return false;

        return searchWord(row+direction[0], col+direction[1], direction, index+1);
    }

    private static boolean isCoordInvalid(int row, int col) {
        return row < 0 || row >= grid.size() || col < 0 || col >= grid.get(0).length;
    }
}
