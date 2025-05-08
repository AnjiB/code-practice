package com.interview.problems.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem 4: Anagram Checker
 * 
 * This class provides methods to check if two strings are anagrams of each other.
 * An anagram is a word or phrase formed by rearranging the letters of another.
 * 
 * Time Complexity: O(n log n) for sorting approach, O(n) for character count approach
 * Space Complexity: O(n) for both approaches
 */
public class Anagrams {
    
    /**
     * Checks if two strings are anagrams using the sorting approach
     * Time Complexity: O(n log n) due to sorting
     */
    public static boolean areAnagramsBySorting(String s1, String s2) {
        // Check if strings are null or different lengths
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        
        // Convert strings to char arrays
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        
        // Sort both arrays
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        
        // Compare sorted arrays
        return Arrays.equals(chars1, chars2);
    }
    
    /**
     * Checks if two strings are anagrams using character count approach
     * Time Complexity: O(n) where n is the length of the strings
     */
    public static boolean areAnagramsByCharCount(String s1, String s2) {
        // Check if strings are null or different lengths
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        
        // Use an array to count character occurrences (assuming ASCII)
        int[] charCount = new int[256]; // Assuming extended ASCII
        
        // Count occurrences in first string
        for (char c : s1.toCharArray()) {
            charCount[c]++;
        }
        
        // Decrement counts for second string
        for (char c : s2.toCharArray()) {
            charCount[c]--;
            // If character count becomes negative, not an anagram
            if (charCount[c] < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Checks if two strings are anagrams using HashMap approach
     * Useful for Unicode strings or when character set is large
     * Time Complexity: O(n)
     */
    public static boolean areAnagramsByHashMap(String s1, String s2) {
        // Check if strings are null or different lengths
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        
        // Use a HashMap to count character occurrences
        Map<Character, Integer> charCount = new HashMap<>();
        
        // Count occurrences in first string
        for (char c : s1.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        
        // Decrement counts for second string
        for (char c : s2.toCharArray()) {
            // If character doesn't exist or count becomes negative, not an anagram
            if (!charCount.containsKey(c) || charCount.get(c) <= 0) {
                return false;
            }
            charCount.put(c, charCount.get(c) - 1);
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        // Test cases
        String[][] testCases = {
            {"listen", "silent"},      // true
            {"hello", "world"},        // false
            {"anagram", "nagaram"},    // true
            {"rat", "car"},            // false
            {"debit card", "bad credit"}, // true (ignoring spaces)
            {"", ""},                  // true (empty strings)
            {"a", "a"}                 // true (single character)
        };
        
        for (String[] test : testCases) {
            String s1 = test[0].replaceAll("\\s", "");
            String s2 = test[1].replaceAll("\\s", "");
            
            System.out.println("Strings: \"" + test[0] + "\" and \"" + test[1] + "\"");
            System.out.println("Sorting Approach: " + 
                               (areAnagramsBySorting(s1, s2) ? "Anagrams" : "Not Anagrams"));
            System.out.println("Character Count Approach: " + 
                               (areAnagramsByCharCount(s1, s2) ? "Anagrams" : "Not Anagrams"));
            System.out.println("HashMap Approach: " + 
                               (areAnagramsByHashMap(s1, s2) ? "Anagrams" : "Not Anagrams"));
            System.out.println();
        }
    }
}