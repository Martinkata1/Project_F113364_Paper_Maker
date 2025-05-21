package org.example;

import java.util.Scanner;

public class Demo2_1 {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        String input = scan.nextLine();
        String[] words = input.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (isPalindrome(word)) {
                sb.append(word);
            }
        }

        System.out.println(sb);
    }
    private static boolean isPalindrome(String word) {
        int left = 0, right = word.length() - 1;
        while (left < right) {
            if (Character.toLowerCase(word.charAt(left)) != Character.toLowerCase(word.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return word.length() > 1;
    }
}
