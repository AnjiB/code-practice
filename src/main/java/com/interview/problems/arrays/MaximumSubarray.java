package com.interview.problems.arrays;

/**
 * Problem 4: Maximum Subarray
 * 
 * Given an integer array nums, find the subarray with the largest sum, and return its sum.
 * 
 * Time Complexity: O(n) using Kadane's algorithm
 * Space Complexity: O(1)
 */
public class MaximumSubarray {
    
    /**
     * Brute force approach - check all possible subarrays
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     */
    public static int maxSubArrayBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int maxSum = Integer.MIN_VALUE;
        
        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        
        return maxSum;
    }
    
    /**
     * Kadane's algorithm - dynamic programming approach
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int maxSubArrayKadane(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int currentSum = nums[0];
        int maxSum = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            // Choose whether to start a new subarray or extend the existing one
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            
            // Update the maximum sum seen so far
            maxSum = Math.max(maxSum, currentSum);
        }
        
        return maxSum;
    }
    
    /**
     * Divide and conquer approach
     * Time Complexity: O(n log n)
     * Space Complexity: O(log n) due to recursion stack
     */
    public static int maxSubArrayDivideConquer(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        return maxSubArrayRecursive(nums, 0, nums.length - 1);
    }
    
    private static int maxSubArrayRecursive(int[] nums, int left, int right) {
        // Base case: single element
        if (left == right) {
            return nums[left];
        }
        
        int mid = left + (right - left) / 2;
        
        // Find maximum subarray sum in left half
        int leftMax = maxSubArrayRecursive(nums, left, mid);
        
        // Find maximum subarray sum in right half
        int rightMax = maxSubArrayRecursive(nums, mid + 1, right);
        
        // Find maximum subarray sum that crosses the middle
        int crossMax = maxCrossingSum(nums, left, mid, right);
        
        // Return the maximum of the three
        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }
    
    private static int maxCrossingSum(int[] nums, int left, int mid, int right) {
        // Find maximum sum starting from mid and going left
        int leftSum = 0;
        int maxLeftSum = Integer.MIN_VALUE;
        
        for (int i = mid; i >= left; i--) {
            leftSum += nums[i];
            maxLeftSum = Math.max(maxLeftSum, leftSum);
        }
        
        // Find maximum sum starting from mid+1 and going right
        int rightSum = 0;
        int maxRightSum = Integer.MIN_VALUE;
        
        for (int i = mid + 1; i <= right; i++) {
            rightSum += nums[i];
            maxRightSum = Math.max(maxRightSum, rightSum);
        }
        
        // Return the sum of the two halves
        return maxLeftSum + maxRightSum;
    }
    
    /**
     * Kadane's algorithm with subarray tracking
     * Returns the start and end indices of the maximum subarray
     */
    public static int[] maxSubArrayWithIndices(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[] {-1, -1, 0};
        }
        
        int currentSum = nums[0];
        int maxSum = nums[0];
        int start = 0;
        int end = 0;
        int tempStart = 0;
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > currentSum + nums[i]) {
                // Start a new subarray
                currentSum = nums[i];
                tempStart = i;
            } else {
                // Extend the existing subarray
                currentSum = currentSum + nums[i];
            }
            
            // Update the maximum sum and indices if necessary
            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
            }
        }
        
        return new int[] {start, end, maxSum};
    }
    
    public static void main(String[] args) {
        int[][] testCases = {
            {-2, 1, -3, 4, -1, 2, 1, -5, 4},    // Expected: 6 [4,-1,2,1]
            {1},                                // Expected: 1 [1]
            {5, 4, -1, 7, 8},                   // Expected: 23 [5,4,-1,7,8]
            {-1, -2, -3, -4},                   // Expected: -1 [-1]
            {-2, -3, 4, -1, -2, 1, 5, -3}       // Expected: 7 [4,-1,-2,1,5]
        };
        
        for (int[] nums : testCases) {
            System.out.print("Input: [");
            for (int i = 0; i < nums.length; i++) {
                System.out.print(nums[i]);
                if (i < nums.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
            
            System.out.println("Brute Force: " + maxSubArrayBruteForce(nums));
            System.out.println("Kadane's Algorithm: " + maxSubArrayKadane(nums));
            System.out.println("Divide and Conquer: " + maxSubArrayDivideConquer(nums));
            
            int[] result = maxSubArrayWithIndices(nums);
            System.out.print("Maximum Subarray: [");
            for (int i = result[0]; i <= result[1]; i++) {
                System.out.print(nums[i]);
                if (i < result[1]) {
                    System.out.print(", ");
                }
            }
            System.out.println("] with sum " + result[2]);
            
            System.out.println();
        }
    }
}