package org.example.Year2023.day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

public class Day3 {
    static List<char[]> inputArray = new ArrayList<>();
    static List<List<String>> numberList = new ArrayList<>();

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2023/inputDay3.txt"));

        while(sc.hasNextLine()) {
            String input = sc.nextLine();
            inputArray.add(input.toCharArray());
        }

        BigInteger result = part == 1? scanPart1() : scanPart2();
        long endTime = System.currentTimeMillis();

        System.out.println("Day 3 Part "+part+" : "+result+"     Execution Time : "+(endTime-startTime)+"ms");
    }

    private static BigInteger scanPart1(){
        BigInteger result = BigInteger.ZERO;

        for(int r =0; r<inputArray.size(); r++){
            int i=0;
            char[] row = inputArray.get(r);
            while(i < row.length){
                char c = row[i];

                if (c != '.' && Character.isDigit(c)) {
                    StringBuilder num = new StringBuilder();
                    int start = i;
                    while (i < row.length && Character.isDigit(row[i])) {
                        num.append(row[i]);
                        i++;
                    }
                    int end = i - 1;

                    if (checkAround(r, start, end)) {
//                        System.out.println(num);
                        result = result.add(BigInteger.valueOf(Long.parseLong(num.toString())));
                    }
                } else i++;

            }
        }

        return result;
    }

    private static boolean checkAround(int row, int startCol, int endCol){
        for(int i = row-1; i <= row+1; i++){
            if(!isValid("row",i)) continue;

            for(int j = startCol-1; j <= endCol+1; j++){
                if(!isValid("col",j) || (i==row && j >=startCol && j <= endCol)) continue;

                if(inputArray.get(i)[j] != '.' && !Character.isDigit(inputArray.get(i)[j]))
                    return true;
            }
        }
        return false;
    }

    private static boolean isValid(String type, int val) {
        if ((type.equals("row") && val >= 0 && val < inputArray.size()) ||
                (type.equals("col") && val >= 0 && val < inputArray.get(0).length)) {
            return true;
        }

        return false;
    }

    private static BigInteger scanPart2(){
        catalogueNumbers();
        return calculateSumOfGearRatios();
    }

    private static void catalogueNumbers(){
        for (char[] chars : inputArray) {
            int i = 0;
            List<String> temp = new ArrayList<>();
            while (i < chars.length) {
                char c = chars[i];

                if (c != '.' && Character.isDigit(c)) {
                    StringBuilder num = new StringBuilder();
                    int start = i;
                    while (i < chars.length && Character.isDigit(chars[i])) {
                        num.append(chars[i]);
                        i++;
                    }
                    int end = i - 1;

                    temp.add(start + "," + end + "," + num);
                } else i++;

            }
            numberList.add(temp);
        }
    }

    private static BigInteger calculateSumOfGearRatios(){
        BigInteger result = BigInteger.ZERO;

        for(int r=0; r < inputArray.size(); r++) {
            char[] chars = inputArray.get(r);
            int i = 0;
            while (i < chars.length) {
                char c = chars[i];

                if (c == '*') {
                    HashSet<String> adjacentNumbers = findAdjacentNumbers(r,i);

                    if(adjacentNumbers.size() == 2){
                        BigInteger gearRatio = BigInteger.ONE;
                        for(String item: adjacentNumbers){
                            gearRatio = gearRatio.multiply(BigInteger.valueOf(Long.parseLong(item.split(",")[2])));
                        }

                        result = result.add(gearRatio);
                    }
                }
                i++;
            }
        }

        return result;
    }

    private static HashSet<String> findAdjacentNumbers(int row, int col) {
        HashSet<String> adjacentNumbers = new HashSet<>();

        if (isValid("row", row - 1)) {
            for (String val : numberList.get(row - 1)) {
                if(adjacentNumbers.size()>2) return adjacentNumbers;

                String[] t = val.split(",");

                if ((col >= Integer.parseInt(t[0]) && col <= Integer.parseInt(t[1])) ||
                        (col - 1 >= Integer.parseInt(t[0]) && col - 1 <= Integer.parseInt(t[1])) ||
                        (col + 1 >= Integer.parseInt(t[0]) && col + 1 <= Integer.parseInt(t[1]))
                )
                    adjacentNumbers.add(val);
            }
        }

        if (isValid("row", row + 1)) {
            for (String val : numberList.get(row + 1)) {
                if(adjacentNumbers.size()>2) return adjacentNumbers;

                String[] t = val.split(",");
                if ((col >= Integer.parseInt(t[0]) && col <= Integer.parseInt(t[1])) ||
                        (col - 1 >= Integer.parseInt(t[0]) && col - 1 <= Integer.parseInt(t[1])) ||
                        (col + 1 >= Integer.parseInt(t[0]) && col + 1 <= Integer.parseInt(t[1]))
                )
                    adjacentNumbers.add(val);
            }
        }

        if (isValid("row", row)) {
            for (String val : numberList.get(row)) {
                if(adjacentNumbers.size()>2) return adjacentNumbers;

                String[] t = val.split(",");
                if ((col - 1 >= Integer.parseInt(t[0]) && col - 1 <= Integer.parseInt(t[1])) ||
                        (col + 1 >= Integer.parseInt(t[0]) && col + 1 <= Integer.parseInt(t[1]))
                )
                    adjacentNumbers.add(val);
            }
        }

        return adjacentNumbers;
    }
}
