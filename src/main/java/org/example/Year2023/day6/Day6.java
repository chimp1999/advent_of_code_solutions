package org.example.Year2023.day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day6 {
    private static List<Long> time = new ArrayList<>();
    private static List<Long> distance = new ArrayList<>();

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2023/inputDay6.txt"));

        String timeRaw = "", distanceRaw = "";
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if(input.contains("Time"))timeRaw = input.split(":")[1].trim();
            if(input.contains("Distance"))distanceRaw = input.split(":")[1].trim();
        }

        extractRaceData(part, timeRaw, distanceRaw);

        long result = calculateWaysToWin();

        long endTime = System.currentTimeMillis();

        System.out.println("\nDay 6 Part " + part + " : " + result + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long calculateWaysToWin() {
        int totalRaces = time.size();
        long total = 1;

        for(int i=0; i< totalRaces; i++){
            total *= findWinningCombination(distance.get(i), time.get(i));
        }

        return total;
    }

    private static void extractRaceData(int part, String timeStr, String distanceStr) {
        switch (part) {
            case 1:
                for (String t : timeStr.split(" "))
                    if (!t.isBlank()) time.add(Long.parseLong(t.trim()));
                for (String d : distanceStr.split(" "))
                    if (!d.isBlank()) distance.add(Long.parseLong(d.trim()));
                return;
            case 2:
                time.add(Long.parseLong(timeStr.replace(" ", "")));
                distance.add(Long.parseLong(distanceStr.replace(" ", "")));
                return;
            default:
                System.out.println("Invalid part!");
        }
    }

    private static long findWinningCombination(long distance, long time){
        long j = (distance / time) + 1;

        while(j <= time/2){
            long x = j * (time - j);

            if(x > distance){
                return (time-j) - j + 1;
            }
            j++;
        }

        return 0L;
    }
}
