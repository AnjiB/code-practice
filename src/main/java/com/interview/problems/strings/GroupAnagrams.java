package com.interview.problems.strings;

import java.util.*;

/**
 * Problem 9: Group Anagrams
 * 
 * Given an array of strings, group anagrams together. An anagram is a word or phrase formed
 * by rearranging the letters of a different word or phrase, using all the original letters exactly once.
 * 
 * Time Complexity: O(n * k) where n is the number of strings and k is the maximum length of a string
 * Space Complexity: O(n * k) for storing all strings
 */
public class GroupAnagrams {
    
    /**
     * Groups anagrams by sorting characters in each string
     * Time Complexity: O(n * k log k)
     */
    public static List<List<String>> groupAnagramsBySorting(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        
        Map<String, List<String>> map = new HashMap<>();
        
        for (String s : strs) {
            // Sort the string to create a key
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            
            // Add to map
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }
        
        // Return all groups as a list
        return new ArrayList<>(map.values());
    }
    
    /**
     * Groups anagrams using character count
     * Time Complexity: O(n * k)
     */
    public static List<List<String>> groupAnagramsByCount(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        
        Map<String, List<String>> map = new HashMap<>();
        
        for (String s : strs) {
            // Count characters (assuming lowercase English letters)
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            
            // Build key from character counts
            StringBuilder keyBuilder = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                if (count[i] > 0) {
                    keyBuilder.append((char) ('a' + i)).append(count[i]);
                }
            }
            String key = keyBuilder.toString();
            
            // Add to map
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }
        
        // Return all groups as a list
        return new ArrayList<>(map.values());
    }
    
    /**
     * Alternative approach using prime number multiplication
     * Each character is assigned a unique prime number, and the product is used as a key.
     * This approach has a risk of overflow for large strings but is interesting.
     */
    public static List<List<String>> groupAnagramsByPrimes(String[] strs) {
        if (strs == null || strs.length == 0) {
            return new ArrayList<>();
        }
        
        // Prime number for each lowercase letter
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 
                         43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
        
        Map<Long, List<String>> map = new HashMap<>();
        
        for (String s : strs) {
            // Calculate product of prime numbers
            long key = 1;
            for (char c : s.toCharArray()) {
                key *= primes[c - 'a'];
            }
            
            // Add to map
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }
        
        // Return all groups as a list
        return new ArrayList<>(map.values());
    }
    
    public static void main(String[] args) {
        String[][] testCases = {
            {"eat", "tea", "tan", "ate", "nat", "bat"},  // Expected: [["ate","eat","tea"],["nat","tan"],["bat"]]
            {""},                                        // Expected: [[""]]
            {"a"},                                       // Expected: [["a"]]
            {"huh", "tit"}                               // Expected: [["huh"], ["tit"]]
        };
        
        for (String[] test : testCases) {
            System.out.println("Input: " + Arrays.toString(test));
            
            System.out.println("Sorting Approach:");
            List<List<String>> result1 = groupAnagramsBySorting(test);
            for (List<String> group : result1) {
                System.out.println("  " + group);
            }
            
            System.out.println("Count Approach:");
            List<List<String>> result2 = groupAnagramsByCount(test);
            for (List<String> group : result2) {
                System.out.println("  " + group);
            }
            
            System.out.println("Prime Number Approach:");
            List<List<String>> result3 = groupAnagramsByPrimes(test);
            for (List<String> group : result3) {
                System.out.println("  " + group);
            }
            
            System.out.println();
        }
    }
}