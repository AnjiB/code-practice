package com.interview.problems.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Problem 10: Minimum Window Substring
 * 
 * Given two strings s and t of lengths m and n respectively, return the minimum window substring
 * of s such that every character in t (including duplicates) is included in the window.
 * If there is no such substring, return the empty string "".
 * 
 * Time Complexity: O(m + n) where m and n are the lengths of strings s and t
 * Space Complexity: O(1) since we're dealing with a fixed character set (at most 128 ASCII chars)
 */
public class MinimumWindowSubstring {
    
    /**
     * Finds the minimum window substring containing all characters in t
     * using the sliding window approach
     */
    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.isEmpty() || t.isEmpty() || s.length() < t.length()) {
            return "";
        }
        
        // Character frequency map for string t
        Map<Character, Integer> targetMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetMap.put(c, targetMap.getOrDefault(c, 0) + 1);
        }
        
        int required = targetMap.size(); // Number of unique characters in t
        int formed = 0; // Number of unique characters in current window
        
        Map<Character, Integer> windowMap = new HashMap<>();
        int[] result = {-1, 0, 0}; // [window_length, left, right]
        
        // Sliding window pointers
        int left = 0;
        int right = 0;
        
        while (right < s.length()) {
            // Add one character from the right to the window
            char c = s.charAt(right);
            windowMap.put(c, windowMap.getOrDefault(c, 0) + 1);
            
            // Check if we've found all occurrences of the current character
            if (targetMap.containsKey(c) && windowMap.get(c).intValue() == targetMap.get(c).intValue()) {
                formed++;
            }
            
            // Try to minimize the window by moving the left pointer
            while (left <= right && formed == required) {
                c = s.charAt(left);
                
                // Update result if current window is smaller
                if (result[0] == -1 || right - left + 1 < result[0]) {
                    result[0] = right - left + 1;
                    result[1] = left;
                    result[2] = right;
                }
                
                // Remove the leftmost character from the window
                windowMap.put(c, windowMap.get(c) - 1);
                
                // If the removed character was essential, decrement formed count
                if (targetMap.containsKey(c) && windowMap.get(c).intValue() < targetMap.get(c).intValue()) {
                    formed--;
                }
                
                left++;
            }
            
            right++;
        }
        
        return result[0] == -1 ? "" : s.substring(result[1], result[2] + 1);
    }
    
    /**
     * Alternative implementation using arrays instead of HashMaps for better performance
     * Assumes ASCII character set (for simplicity)
     */
    public static String minWindowOptimized(String s, String t) {
        if (s == null || t == null || s.isEmpty() || t.isEmpty() || s.length() < t.length()) {
            return "";
        }
        
        // Character frequency arrays for ASCII characters
        int[] targetCount = new int[128];
        int[] windowCount = new int[128];
        
        // Count characters in t
        for (char c : t.toCharArray()) {
            targetCount[c]++;
        }
        
        int required = 0;
        // Count unique characters in t
        for (int count : targetCount) {
            if (count > 0) {
                required++;
            }
        }
        
        int formed = 0;
        int[] result = {-1, 0, 0}; // [window_length, left, right]
        
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            windowCount[c]++;
            
            // Check if we've found all occurrences of the current character
            if (targetCount[c] > 0 && windowCount[c] == targetCount[c]) {
                formed++;
            }
            
            // Try to minimize the window
            while (left <= right && formed == required) {
                c = s.charAt(left);
                
                // Update result if current window is smaller
                if (result[0] == -1 || right - left + 1 < result[0]) {
                    result[0] = right - left + 1;
                    result[1] = left;
                    result[2] = right;
                }
                
                // Remove the leftmost character
                windowCount[c]--;
                
                // If the removed character was essential, decrement formed count
                if (targetCount[c] > 0 && windowCount[c] < targetCount[c]) {
                    formed--;
                }
                
                left++;
            }
        }
        
        return result[0] == -1 ? "" : s.substring(result[1], result[2] + 1);
    }
    
    public static void main(String[] args) {
        String[][] testCases = {
            {"ADOBECODEBANC", "ABC"},        // Expected: "BANC"
            {"a", "a"},                      // Expected: "a"
            {"a", "aa"},                     // Expected: ""
            {"ab", "b"},                     // Expected: "b"
            {"bba", "ab"},                   // Expected: "ba"
            {"cabwefgewcwaefgcf", "cae"}     // Expected: "cwae"
        };
        
        for (String[] test : testCases) {
            System.out.println("s = \"" + test[0] + "\", t = \"" + test[1] + "\"");
            System.out.println("HashMap Approach: \"" + minWindow(test[0], test[1]) + "\"");
            System.out.println("Array Approach: \"" + minWindowOptimized(test[0], test[1]) + "\"");
            System.out.println();
        }
    }
}