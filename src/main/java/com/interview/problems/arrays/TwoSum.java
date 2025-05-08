package com.interview.problems.arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Problem 1: Two Sum
 * 
 * Given an array of integers nums and an integer target, return indices of the two numbers 
 * such that they add up to target.
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * 
 * Time Complexity: O(n) using HashMap approach
 * Space Complexity: O(n) for storing elements in the HashMap
 */
public class TwoSum {
    
    /**
     * Brute force approach - check all pairs
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     */
    public static int[] twoSumBruteForce(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[0];
        }
        
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        
        // No solution found
        return new int[0];
    }
    
    /**
     * Optimized approach using HashMap
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static int[] twoSumOptimized(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[0];
        }
        
        Map<Integer, Integer> numMap = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            
            // If we've seen the complement before, we found a solution
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
     * Two-pointer approach (requires sorted array)
     * Time Complexity: O(n log n) due to sorting
     * Space Complexity: O(n) for the sorted array
     * Note: This approach returns the elements, not their indices in the original array
     */
    public static int[] twoSumTwoPointer(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[0];
        }
        
        // Create a copy of the array with original indices
        int[][] numWithIndices = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            numWithIndices[i][0] = nums[i];  // value
            numWithIndices[i][1] = i;        // original index
        }
        
        // Sort the array based on values
        Arrays.sort(numWithIndices, (a, b) -> Integer.compare(a[0], b[0]));
        
        int left = 0;
        int right = numWithIndices.length - 1;
        
        while (left < right) {
            int sum = numWithIndices[left][0] + numWithIndices[right][0];
            
            if (sum == target) {
                return new int[] {numWithIndices[left][1], numWithIndices[right][1]};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        // No solution found
        return new int[0];
    }
    
    public static void main(String[] args) {
        int[][] testArrays = {
            {2, 7, 11, 15},
            {3, 2, 4},
            {3, 3},
            {1, 5, 9, 13, 15, 2, 7},
            {-1, -2, -3, -4, -5}
        };
        
        int[] targets = {9, 6, 6, 8, -8};
        
        for (int i = 0; i < testArrays.length; i++) {
            int[] nums = testArrays[i];
            int target = targets[i];
            
            System.out.println("Array: " + Arrays.toString(nums));
            System.out.println("Target: " + target);
            
            int[] result1 = twoSumBruteForce(nums, target);
            System.out.println("Brute Force: " + Arrays.toString(result1));
            if (result1.length == 2) {
                System.out.println("Found: " + nums[result1[0]] + " + " + nums[result1[1]] + " = " + target);
            }
            
            int[] result2 = twoSumOptimized(nums, target);
            System.out.println("HashMap: " + Arrays.toString(result2));
            if (result2.length == 2) {
                System.out.println("Found: " + nums[result2[0]] + " + " + nums[result2[1]] + " = " + target);
            }
            
            int[] result3 = twoSumTwoPointer(nums, target);
            System.out.println("Two Pointer: " + Arrays.toString(result3));
            if (result3.length == 2) {
                System.out.println("Found: " + nums[result3[0]] + " + " + nums[result3[1]] + " = " + target);
            }
            
            System.out.println();
        }
    }
}