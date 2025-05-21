package org.example;

import java.util.Scanner;

public class Demo3 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String input = scan.nextLine();
        StringBuilder result = new StringBuilder();

        for (int i = input.length() - 1; i >= 0; i--) {
            char symbol = input.charAt(i);
            if (Character.isUpperCase(symbol)) {
                result.append(Character.toLowerCase(symbol));
            } else if (Character.isLowerCase(symbol)) {
                result.append(Character.toUpperCase(symbol));
            } else {
                result.append(symbol);
            }
        }

        System.out.println(result);

    }
}
