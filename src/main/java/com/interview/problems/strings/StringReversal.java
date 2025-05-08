package com.interview.problems.strings;

/**
 * Problem 1: String Reversal
 * 
 * This class demonstrates various approaches to reverse a string.
 * Time Complexity: O(n) where n is the length of the string
 * Space Complexity: O(n) for storing the reversed string
 */
public class StringReversal {
    
    /**
     * Reverses a string using StringBuilder
     * This is the most efficient approach in Java
     */
    public static String reverseUsingStringBuilder(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        return new StringBuilder(input).reverse().toString();
    }
    
    /**
     * Reverses a string manually using character array
     * Good for understanding the mechanics behind string reversal
     */
    public static String reverseManually(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        char[] chars = input.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        
        while (left < right) {
            // Swap characters
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            
            // Move towards the center
            left++;
            right--;
        }
        
        return new String(chars);
    }
    
    /**
     * Reverses a string recursively
     * Less efficient but demonstrates recursive approach
     */
    public static String reverseRecursively(String input) {
        if (input == null || input.length() <= 1) {
            return input;
        }
        
        return reverseRecursively(input.substring(1)) + input.charAt(0);
    }
    
    public static void main(String[] args) {
        String test = "Hello, World!";
        
        System.out.println("Original: " + test);
        System.out.println("Reversed (StringBuilder): " + reverseUsingStringBuilder(test));
        System.out.println("Reversed (Manually): " + reverseManually(test));
        System.out.println("Reversed (Recursively): " + reverseRecursively(test));
    }
}