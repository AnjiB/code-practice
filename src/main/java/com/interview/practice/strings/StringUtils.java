package com.interview.practice.strings;

/**
 * Utility class for string manipulation problems.
 */
public class StringUtils {
    
    /**
     * Reverses a given string.
     * 
     * @param input the string to reverse
     * @return the reversed string
     */
    public static String reverse(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        char[] chars = input.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        
        return new String(chars);
    }
}