package org.adventOfCode.Year2024.day23;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day23 {
    private static Map<String, Set<String>> memory = new HashMap<>();

    public static void run(int part) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        Scanner sc = new Scanner(new File("src/main/resources/Input Data/Year2024/inputDay23.txt"));

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            processInput(input);
        }
//        System.out.println(memory);
        String res = part == 1 ? Long.toString(findSetOfThreeComputers()) : findLanPartyPassword();

        long endTime = System.currentTimeMillis();

        System.out.println("Day 23 Part " + part + " : " + res + "     Execution Time : " + (endTime - startTime) + "ms");
    }

    private static String findLanPartyPassword(){
        // Part 2 requires us to find the largest clique in an undirected graph.
        // An efficient solution to find it is to use the Bron–Kerbosch algorithm with pivot.

        Set<String> R = new HashSet<>();
        Set<String> P = memory.keySet();
        Set<String> X = new HashSet<>();
        List<String> allMaximalCliques = new ArrayList<>();

        bronKerboschAlgorithm(R, P, X, allMaximalCliques);
//        System.out.println(allMaximalCliques);

        String longestClique = allMaximalCliques.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");
        String[] computerNames = longestClique.split("-");
        Arrays.sort(computerNames);

        return String.join(",", computerNames);
    }

    private static void bronKerboschAlgorithm(Set<String> R,
                                     Set<String> P,
                                     Set<String> X,
                                     List<String> allMaximalCliques) {
        if (P.isEmpty() && X.isEmpty()) {
//            System.out.println(R.toString().substring(1, R.toString().length() - 1));
            allMaximalCliques.add(R.toString().substring(1, R.toString().length() - 1).replace(", ", "-"));
            return;
        }

        Set<String> pivotOptions = new HashSet<>(P);
        pivotOptions.addAll(X);
        String pivot = null;
        if(!pivotOptions.isEmpty())
            pivot = pivotOptions.iterator().next();

        // Consider vertices in P \ N(pivot)
        Set<String> candidates = new HashSet<>(P);
        if (pivot != null) {
            candidates.removeAll(memory.getOrDefault(pivot, new HashSet<>()));
        }

        for (String selected : candidates) {
                Set<String> newR = new HashSet<>(R);
                newR.add(selected);
                Set<String> newP = new HashSet<>(P);
                newP.retainAll(memory.get(selected));
                Set<String> newX = new HashSet<>(X);
                newX.retainAll(memory.get(selected));

                bronKerboschAlgorithm(newR, newP, newX, allMaximalCliques);
                P.remove(selected);
                X.add(selected);
        }
    }

    private static long findSetOfThreeComputers() {
        Set<String> setOfThreeComputers = new HashSet<>();

        for(String node1 : memory.keySet()) {
            Set<String> connectedComputers = memory.get(node1);
            for(String node2 : connectedComputers) {
                Set<String> connectedComputers1 = memory.get(node2);
                Set<String> temp = new HashSet<>(connectedComputers1);
                temp.retainAll(connectedComputers);

                for(String node3 : temp) {
                    if(node1.charAt(0) == 't' || node2.charAt(0) == 't' || node3.charAt(0) == 't'){
                        String triangle = Stream.of(node1, node2, node3).sorted().collect(Collectors.joining("-"));
                        setOfThreeComputers.add(triangle);
                    }
                }
            }
        }

//        System.out.println(setOfThreeComputers);
        return setOfThreeComputers.size();
    }

    private static void processInput(String input) {
        String[] parts = input.split("-");

        memory.computeIfAbsent(parts[0], k -> new HashSet<>());
        memory.get(parts[0]).add(parts[1]);
        memory.computeIfAbsent(parts[1], k -> new HashSet<>());
        memory.get(parts[1]).add(parts[0]);
    }
}
