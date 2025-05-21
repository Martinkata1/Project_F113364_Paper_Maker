package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Demo4 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String input = scan.nextLine();
        //StringBuilder result = new StringBuilder();
        List<String> result = new ArrayList<>();
        String[] words = input.split("\\s+");
        for (String word : words) {
            if (word.matches("^-?\\d+$")) {
                result.add(word);
                System.out.println(word);
            }
        }
        System.out.println(result);

    }
}
