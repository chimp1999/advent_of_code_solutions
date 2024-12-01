package org.example.Year2023;

import org.example.Year2023.day1.Day1;
import org.example.Year2023.day2.Day2;
import org.example.Year2023.day3.Day3;
import org.example.Year2023.day4.Day4;
import org.example.Year2023.day5.Day5;
import org.example.Year2023.day6.Day6;

import java.io.FileNotFoundException;

public class Runner2023 {
    public static void execute() throws FileNotFoundException {
        // Day 1
        Day1.run(1);
        Day1.run(2);

        // Day 2
        Day2.run(1);
        Day2.run(2);

        // Day 3
        Day3.run(1);
        Day3.run(2);

        // Day 4
        Day4.run(1);
        Day4.run(2);

        // Day 5
        Day5.run(1);
//        Day5.run(2); // Unsolved

        // Day 6
        Day6.run(1);
        Day6.run(2);

        // Day 7
//        Day7.run(1);
    }
}