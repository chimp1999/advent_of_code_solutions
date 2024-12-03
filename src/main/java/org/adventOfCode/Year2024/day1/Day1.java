package org.adventOfCode.Year2024.day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day1 {
    private static List<Long> list1 = new ArrayList<>(), list2 = new ArrayList<>();

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay1.txt"));

        while(sc.hasNextLine()){
            String[] input = sc.nextLine().split(" {3}");
            list1.add(Long.parseLong(input[0]));
            list2.add(Long.parseLong(input[1]));
        }

        long res = (part == 1) ? calculateTotalDistance() : calculateSimilarityScore();

        long endTime = System.currentTimeMillis();

        System.out.println("Day 1 Part "+part+" : "+ res+"     Execution Time : "+(endTime-startTime)+"ms");
    }

    private static long calculateTotalDistance() {
        Collections.sort(list1);
        Collections.sort(list2);

        long totalDistance = 0L;

        for(int i=0; i<list1.size(); i++){
            totalDistance += Math.abs(list1.get(i) - list2.get(i));
        }

        return totalDistance;
    }

    private static long calculateSimilarityScore() {
        // create freq map for both then iterate the map and calculate score.
        Map<Long, Long> map1 = generateFrequencyMap(list1);
        Map<Long, Long> map2 = generateFrequencyMap(list2);

        long similarityScore = 0L;

        for(Map.Entry<Long, Long> entry : map1.entrySet()){
            similarityScore += entry.getKey() * map2.getOrDefault(entry.getKey(), 0L) * entry.getValue();
        }

        return similarityScore;
    }

    private static Map<Long, Long> generateFrequencyMap(List<Long> inputList){
        Map<Long, Long> frequencyMap = new HashMap<>();

        for(Long value : inputList){
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0L)+1L);
        }

        return frequencyMap;
    }
}
