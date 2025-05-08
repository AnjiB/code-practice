package com.interview.problems.arrays;

import java.util.*;

/**
 * Problem 3: 3Sum
 * 
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] 
 * such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 * 
 * The solution set must not contain duplicate triplets.
 * 
 * Time Complexity: O(n²) using sorting and two-pointer approach
 * Space Complexity: O(n) for the result list (not counting the input)
 */
public class ThreeSum {
    
    /**
     * Naive approach - check all triplets
     * Time Complexity: O(n³)
     * Space Complexity: O(n) for the result set
     */
    public static List<List<Integer>> threeSumBruteForce(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        
        Set<List<Integer>> resultSet = new HashSet<>();
        
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        // Create a sorted triplet to avoid duplicates
                        List<Integer> triplet = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(triplet);
                        resultSet.add(triplet);
                    }
                }
            }
        }
        
        return new ArrayList<>(resultSet);
    }
    
    /**
     * Optimized approach using sorting and two pointers
     * Time Complexity: O(n²)
     * Space Complexity: O(n) for the result list
     */
    public static List<List<Integer>> threeSumOptimized(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        
        // Sort the array first
        Arrays.sort(nums);
        
        List<List<Integer>> result = new ArrayList<>();
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates for the first element
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // If current element is positive, no triplet can sum to zero
            if (nums[i] > 0) {
                break;
            }
            
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum < 0) {
                    // Need a bigger sum, move left pointer to the right
                    left++;
                } else if (sum > 0) {
                    // Need a smaller sum, move right pointer to the left
                    right--;
                } else {
                    // Found a triplet
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicates for the second element
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    
                    // Skip duplicates for the third element
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    // Move both pointers to find more triplets
                    left++;
                    right--;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Approach using HashMap - for each pair, check if the complement exists
     * Time Complexity: O(n²)
     * Space Complexity: O(n) for the HashMap
     */
    public static List<List<Integer>> threeSumHashMap(int[] nums) {
        if (nums == null || nums.length < 3) {
            return new ArrayList<>();
        }
        
        // Sort to handle duplicates efficiently
        Arrays.sort(nums);
        
        List<List<Integer>> result = new ArrayList<>();
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates for the first element
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // Use a set to track seen values for the current i
            Set<Integer> seen = new HashSet<>();
            
            for (int j = i + 1; j < nums.length; j++) {
                // Skip duplicates for the second element (when using HashSet)
                if (j > i + 2 && nums[j] == nums[j - 1] && nums[j - 1] == nums[j - 2]) {
                    continue;
                }
                
                // Calculate the complement needed to make the sum zero
                int complement = -nums[i] - nums[j];
                
                if (seen.contains(complement)) {
                    result.add(Arrays.asList(nums[i], complement, nums[j]));
                    // Skip duplicates for the third element
                    while (j + 1 < nums.length && nums[j] == nums[j + 1]) {
                        j++;
                    }
                }
                
                seen.add(nums[j]);
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        int[][] testCases = {
            {-1, 0, 1, 2, -1, -4},       // Expected: [[-1,-1,2],[-1,0,1]]
            {},                           // Expected: []
            {0},                          // Expected: []
            {0, 0, 0},                    // Expected: [[0,0,0]]
            {-2, 0, 0, 2, 2}              // Expected: [[-2,0,2]]
        };
        
        for (int[] nums : testCases) {
            System.out.println("Input: " + Arrays.toString(nums));
            
            System.out.println("Brute Force Approach:");
            List<List<Integer>> result1 = threeSumBruteForce(nums);
            printTriplets(result1);
            
            System.out.println("Two Pointer Approach:");
            List<List<Integer>> result2 = threeSumOptimized(nums);
            printTriplets(result2);
            
            System.out.println("HashMap Approach:");
            List<List<Integer>> result3 = threeSumHashMap(nums);
            printTriplets(result3);
            
            System.out.println();
        }
    }
    
    private static void printTriplets(List<List<Integer>> triplets) {
        if (triplets.isEmpty()) {
            System.out.println("  []");
            return;
        }
        
        for (List<Integer> triplet : triplets) {
            System.out.println("  " + triplet);
        }
    }
}