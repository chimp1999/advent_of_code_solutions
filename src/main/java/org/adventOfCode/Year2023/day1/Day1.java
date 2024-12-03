package org.adventOfCode.Year2023.day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 {
    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2023/inputDay1.txt"));

        BigInteger res = BigInteger.ZERO;

        while(sc.hasNextLine()){
            String input = sc.nextLine();
            int val = part == 1 ? findCodePart1(input) : findCodePart2(input);
            res = res.add(BigInteger.valueOf(val));
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Day 1 Part "+part+" : "+ res+"     Execution Time : "+(endTime-startTime)+"ms");
    }

    private static int findCodePart1(String input){
        int j=-1;
        int res = 0;

        for(int i = 0; i < input.length(); i++){
            if(Character.isDigit(input.charAt(i))){
                if(j==-1) {
                    res = Integer.parseInt(""+input.charAt(i));
                }
                j = i;
            }
        }
        res = (res*10) + Integer.parseInt(""+input.charAt(j));
//        System.out.println(input + " " + res);
        return res;
    }

    private static int findCodePart2(String input){
        String regex = "^(one|two|three|four|five|six|seven|eight|nine|\\d)";
        Pattern pattern = Pattern.compile(regex);
        List<Integer> digits = new ArrayList<>();
        int i = 0;
        while (i < input.length()) {
            Matcher matcher = pattern.matcher(input.substring(i));
            if (matcher.find()) {
                String digitStr = matcher.group(1);
                int digit = switch (digitStr) {
                    case "one" -> 1;
                    case "two" -> 2;
                    case "three" -> 3;
                    case "four" -> 4;
                    case "five" -> 5;
                    case "six" -> 6;
                    case "seven" -> 7;
                    case "eight" -> 8;
                    case "nine" -> 9;
                    default -> Integer.parseInt(digitStr);
                };
                digits.add(digit);
            }
            i++;
        }
        return (digits.get(0)*10)+digits.get(digits.size()-1);
    }
}
