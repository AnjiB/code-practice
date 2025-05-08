package com.interview.problems.strings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Problem 6: Longest Substring Without Repeating Characters
 * 
 * This class provides methods to find the length of the longest substring without repeating characters.
 * 
 * Time Complexity: O(n) where n is the length of the string
 * Space Complexity: O(min(n, m)) where m is the size of the character set
 */
public class LongestSubstringWithoutRepeating {
    
    /**
     * Finds the length of the longest substring without repeating characters
     * using the sliding window approach with HashSet
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        Set<Character> charSet = new HashSet<>();
        int maxLength = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // If the character is already in our set, move the left pointer
            // until we remove the duplicate character
            while (charSet.contains(currentChar)) {
                charSet.remove(s.charAt(left));
                left++;
            }
            
            // Add current character to the set
            charSet.add(currentChar);
            
            // Update the maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Optimized sliding window approach using HashMap
     * Instead of removing one character at a time, we can jump the left pointer
     * directly to the position after the duplicate character
     */
    public static int lengthOfLongestSubstringOptimized(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int maxLength = 0;
        
        // Using 'j' as left pointer and 'i' as right pointer
        for (int i = 0, j = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            
            // If current character is already in the map, move left pointer
            // to the position after the previous occurrence
            if (charIndexMap.containsKey(currentChar)) {
                // Use Math.max to handle cases where the duplicate character
                // is behind the current left pointer
                j = Math.max(j, charIndexMap.get(currentChar) + 1);
            }
            
            // Update the character's index in the map
            charIndexMap.put(currentChar, i);
            
            // Update the maximum length
            maxLength = Math.max(maxLength, i - j + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Further optimized approach using an integer array instead of HashMap
     * Assumes ASCII characters (for simplicity)
     */
    public static int lengthOfLongestSubstringASCII(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        // Use array instead of HashMap (more efficient for ASCII)
        int[] charIndex = new int[128]; // ASCII has 128 characters
        
        // Initialize with -1 (not seen yet)
        for (int i = 0; i < 128; i++) {
            charIndex[i] = -1;
        }
        
        int maxLength = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // If character was seen before and its index is >= left pointer
            if (charIndex[currentChar] >= left) {
                // Move left pointer to the position after the duplicate
                left = charIndex[currentChar] + 1;
            }
            
            // Update character's index
            charIndex[currentChar] = right;
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    public static void main(String[] args) {
        String[] testCases = {
            "abcabcbb",   // Expected: 3 ("abc")
            "bbbbb",      // Expected: 1 ("b")
            "pwwkew",     // Expected: 3 ("wke")
            "",           // Expected: 0
            "au",         // Expected: 2 ("au")
            "dvdf",       // Expected: 3 ("vdf")
            "tmmzuxt"     // Expected: 5 ("mzuxt")
        };
        
        for (String test : testCases) {
            System.out.println("Input: \"" + test + "\"");
            System.out.println("HashSet Approach: " + lengthOfLongestSubstring(test));
            System.out.println("HashMap Approach: " + lengthOfLongestSubstringOptimized(test));
            System.out.println("ASCII Array Approach: " + lengthOfLongestSubstringASCII(test));
            System.out.println();
        }
    }
}