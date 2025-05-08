package com.interview.problems.maps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem 1: Two Sum
 * 
 * Given an array of integers nums and an integer target, return indices of the 
 * two numbers such that they add up to target.
 * 
 * You may assume that each input would have exactly one solution, and you may 
 * not use the same element twice.
 * 
 * Time Complexity: O(n) using HashMap approach
 * Space Complexity: O(n) for storing elements in the HashMap
 */
public class TwoSum {
    
    /**
     * HashMap approach - use a map to track the elements we've seen so far
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[0];
        }
        
        // Map to store number -> index
        Map<Integer, Integer> numMap = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            
            // If we've seen the complement, we found a solution
            if (numMap.containsKey(complement)) {
                return new int[] {numMap.get(complement), i};
            }
            
            // Store current number and its index
            numMap.put(nums[i], i);
        }
        
        // No solution found
        return new int[0];
    }
    
    /**
     * Same approach but returning the values instead of indices
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static int[] twoSumValues(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[0];
        }
        
        // Map to store number -> its frequency
        Map<Integer, Integer> numMap = new HashMap<>();
        
        // Count frequencies of each number
        for (int num : nums) {
            numMap.put(num, numMap.getOrDefault(num, 0) + 1);
        }
        
        for (int num : nums) {
            int complement = target - num;
            
            // Handle special case when num is half of target
            if (num == complement) {
                if (numMap.get(num) > 1) {
                    return new int[] {num, num};
                }
            } else if (numMap.containsKey(complement)) {
                return new int[] {num, complement};
            }
        }
        
        // No solution found
        return new int[0];
    }
    
    /**
     * Finds all pairs of numbers that sum to the target
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static int[][] allTwoSumPairs(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[0][0];
        }
        
        Map<Integer, Integer> numMap = new HashMap<>();
        for (int num : nums) {
            numMap.put(num, numMap.getOrDefault(num, 0) + 1);
        }
        
        // Use a list to store all pairs
        java.util.List<int[]> pairs = new java.util.ArrayList<>();
        
        // To avoid duplicates, use a set to track processed numbers
        java.util.Set<Integer> processed = new java.util.HashSet<>();
        
        for (int num : nums) {
            int complement = target - num;
            
            // Skip if we've already processed this number
            if (processed.contains(num)) {
                continue;
            }
            
            if (num == complement) {
                // Special case: number is half of target
                int count = numMap.get(num);
                // Calculate how many pairs we can form
                int pairCount = count / 2;
                
                for (int i = 0; i < pairCount; i++) {
                    pairs.add(new int[] {num, num});
                }
            } else if (numMap.containsKey(complement)) {
                int numCount = numMap.get(num);
                int complementCount = numMap.get(complement);
                
                // Add pairs based on the minimum of counts
                int pairCount = Math.min(numCount, complementCount);
                
                for (int i = 0; i < pairCount; i++) {
                    pairs.add(new int[] {num, complement});
                }
            }
            
            processed.add(num);
            processed.add(complement);
        }
        
        // Convert list to array
        return pairs.toArray(new int[pairs.size()][]);
    }
    
    /**
     * Using a two sum approach to check if there are three numbers that sum to zero
     * Time Complexity: O(n²)
     * Space Complexity: O(n)
     */
    public static boolean threeSum(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return false;
        }
        
        // Sort the array for easier processing
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // Use two sum approach to find the remaining pairs
            int newTarget = target - nums[i];
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == newTarget) {
                    return true; // Found a triplet
                } else if (sum < newTarget) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return false;
    }
    
    public static void main(String[] args) {
        int[][] testCases = {
            {2, 7, 11, 15},
            {3, 2, 4},
            {3, 3},
            {1, 5, 9, 13, 15, 2, 7},
            {-1, -2, -3, -4, -5}
        };
        
        int[] targets = {9, 6, 6, 8, -8};
        
        for (int i = 0; i < testCases.length; i++) {
            int[] nums = testCases[i];
            int target = targets[i];
            
            System.out.println("Array: " + Arrays.toString(nums));
            System.out.println("Target: " + target);
            
            int[] indices = twoSum(nums, target);
            System.out.println("Indices: " + Arrays.toString(indices));
            
            int[] values = twoSumValues(nums, target);
            System.out.println("Values: " + Arrays.toString(values));
            
            int[][] allPairs = allTwoSumPairs(nums, target);
            System.out.println("All pairs:");
            for (int[] pair : allPairs) {
                System.out.println("  " + Arrays.toString(pair));
            }
            
            boolean hasThreeSum = threeSum(nums, target);
            System.out.println("Has three numbers that sum to " + target + ": " + hasThreeSum);
            
            System.out.println();
        }
    }
}