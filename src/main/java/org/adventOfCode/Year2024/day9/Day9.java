package org.adventOfCode.Year2024.day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day9 {
    private static List<String> disk_map = new ArrayList<>();
    private static Map<Integer, String> fileMap = new HashMap<>();
    private static SortedMap<Integer, List<String>> spaceMap = new TreeMap<>();

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay9.txt"));

        char[] input = sc.nextLine().toCharArray();
        int fileId = 0;

        for (int i = 0; i < input.length; i += 2) {
            //temp
            fileMap.put(fileId, disk_map.size()+":"+(disk_map.size()+Integer.parseInt(input[i] + "")));

            disk_map.addAll(Collections.nCopies(Integer.parseInt(input[i] + ""), Integer.toString(fileId)));

            if (i + 1 < input.length) {
                //temp
                int size = Integer.parseInt(input[i + 1] + "");
                if(size > 0)
                    if(spaceMap.containsKey(size))
                        spaceMap.get(size).add(disk_map.size()+":"+(disk_map.size()+size));
                    else
                        spaceMap.put(size, new ArrayList<>(){{add(disk_map.size()+":"+(disk_map.size()+size));}});

                disk_map.addAll(Collections.nCopies(Integer.parseInt(input[i + 1] + ""), "."));
            }

            fileId++;
        }
//        System.out.println(disk_map);
//        System.out.println(fileMap);
//        System.out.println(spaceMap);
        long res = part == 1 ? compressDiskPart1() : compressDiskPart22(fileId);
//        System.out.println(disk_map);6353648838485:6338731303389

        long endTime = System.currentTimeMillis();

        System.out.println("Day 9 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static long compressDiskPart2(int fileId) {
        int spaceStart = 0, spaceEnd = 0, fileStart = disk_map.size()-1, fileEnd = disk_map.size() - 1;

        while(fileEnd > 0) {
            fileId--;

            while (fileStart > 0 && (disk_map.get(fileStart).equals(".") || !disk_map.get(fileStart).equals(fileId+"")))
                fileStart--;

            fileEnd = fileStart;

            while (fileEnd > 0 && disk_map.get(fileEnd).equals(disk_map.get(fileStart)))
                fileEnd--;

//            System.out.println("File " + disk_map.get(fileStart) + " - " + fileEnd + "," + fileStart);

//            if(processedElements.contains(disk_map.get(fileStart))){
//                fileStart = fileEnd;
//                continue;
//            } else {
//                processedElements.add(disk_map.get(fileStart));
//            }

            spaceStart = 0;

            while (spaceStart < fileEnd) {
                while (spaceStart < fileEnd && !disk_map.get(spaceStart).equals("."))
                    spaceStart++;

                if (spaceStart >= fileEnd) {
                    fileStart = fileEnd;
                    break;
                }

                spaceEnd = spaceStart;

                while (spaceEnd < fileEnd && spaceEnd - spaceStart < fileStart - fileEnd && disk_map.get(spaceEnd).equals("."))
                    spaceEnd++;

//                System.out.println("Space - " + spaceStart + "," + spaceEnd);

                if (spaceEnd - spaceStart == fileStart - fileEnd) {
                    while (fileStart != fileEnd) {
                        disk_map.set(spaceStart, disk_map.get(fileStart));
                        disk_map.set(fileStart, ".");
                        fileStart--;
                        spaceStart++;
                    }
//                    System.out.println(disk_map);
                    break;
                }

                spaceStart = spaceEnd;
            }
        }

        return calculateChecksum(2);
    }

    private static long compressDiskPart22(int fileId){
        while (fileId > 0){
            fileId--;
            String[] file = fileMap.get(fileId).split(":");
//            System.out.println(Arrays.toString(file));

            int fileSize = Integer.parseInt(file[1]) - Integer.parseInt(file[0]);

            while(fileSize <= spaceMap.lastKey()){
                if(!spaceMap.containsKey(fileSize))
                    fileSize++;
                else {
                    String[] space = spaceMap.get(fileSize).get(0).split(":");

                    int startFile = Integer.parseInt(file[0]), endFile = Integer.parseInt(file[1]);
                    int startSpace = Integer.parseInt(space[0]);
                    if(startSpace > startFile)
                        break;

                    while(startFile < endFile){
                        disk_map.set(startSpace, disk_map.get(startFile));
                        disk_map.set(startFile, ".");
                        startFile++;
                        startSpace++;
                    }

                    if(spaceMap.get(fileSize).size() > 1)
                        spaceMap.get(fileSize).remove(0);
                    else
                        spaceMap.remove(fileSize);

                    space[0] = String.valueOf(Integer.parseInt(space[0]) + Integer.parseInt(file[1]) - Integer.parseInt(file[0]));

                    if(Integer.parseInt(space[0]) < Integer.parseInt(space[1])){
                        int leftoverSpace = Integer.parseInt(space[1]) - Integer.parseInt(space[0]);

                        if(!spaceMap.containsKey(leftoverSpace))
                            spaceMap.put(leftoverSpace, new ArrayList<>(){{add(space[0]+":"+space[1]);}});
                        else {
                            List<String> newSpaceList = spaceMap.get(leftoverSpace);
                            newSpaceList.add(space[0]+":"+space[1]);
                            newSpaceList.sort((o1, o2) -> {
                                int v1 = Integer.parseInt(o1.split(":")[0]);
                                int v2 = Integer.parseInt(o2.split(":")[0]);

                                return v1 - v2;
                            });
                            spaceMap.put(leftoverSpace, newSpaceList);
                        }
                    }
//                    System.out.println(disk_map);
//System.out.println(spaceMap);
                    break;
                }
            }
        }
        return calculateChecksum(2);
    }

    private static long compressDiskPart1() {
        int left = 0, right = disk_map.size() - 1;

        while (left < right){
            if(!disk_map.get(left).equals("."))
                left++;
            else if (disk_map.get(right).equals(".")) {
                right--;
            } else {
                disk_map.set(left, disk_map.get(right));
                disk_map.set(right, ".");
            }
        }

        return calculateChecksum(1);
    }

    private static long calculateChecksum(int part){
        long res = 0L;

        if(part == 1)
            for (int i = 0; !disk_map.get(i).equals("."); i++) {
                if(!disk_map.get(i).equals("."))
                    res += i * Long.parseLong(disk_map.get(i));
            }
        else
            for (int i = 0; i < disk_map.size(); i++) {
                if(!disk_map.get(i).equals("."))
                    res += i * Long.parseLong(disk_map.get(i));
            }

        return res;
    }
}
