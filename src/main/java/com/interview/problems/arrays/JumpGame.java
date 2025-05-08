package com.interview.problems.arrays;

import java.util.Arrays;

/**
 * Problem 9: Jump Game
 * 
 * You are given an integer array nums. You are initially positioned at the first index,
 * and each element in the array represents your maximum jump length at that position.
 * 
 * Return true if you can reach the last index, or false otherwise.
 * 
 * Time Complexity: O(n) using greedy approach
 * Space Complexity: O(1)
 */
public class JumpGame {
    
    /**
     * Greedy approach - work backwards from the end
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        
        if (nums.length == 1) {
            return true; // Already at the end
        }
        
        // The position we need to reach (initially the last index)
        int targetPosition = nums.length - 1;
        
        // Start from the second-to-last position and work backwards
        for (int i = nums.length - 2; i >= 0; i--) {
            // If we can jump from position i to the target position
            if (i + nums[i] >= targetPosition) {
                // Update the target position to i
                targetPosition = i;
            }
        }
        
        // If the target position is 0, we can reach the end from the start
        return targetPosition == 0;
    }
    
    /**
     * Greedy approach - work forwards
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static boolean canJumpForward(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        
        // Maximum position we can reach
        int maxReach = 0;
        
        for (int i = 0; i < nums.length; i++) {
            // If we can't reach the current position, return false
            if (i > maxReach) {
                return false;
            }
            
            // Update the maximum position we can reach
            maxReach = Math.max(maxReach, i + nums[i]);
            
            // If we can reach the last index, return true
            if (maxReach >= nums.length - 1) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Dynamic programming approach
     * Time Complexity: O(n²) in worst case
     * Space Complexity: O(n)
     */
    public static boolean canJumpDP(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        
        if (nums.length == 1) {
            return true;
        }
        
        int n = nums.length;
        boolean[] dp = new boolean[n]; // dp[i] = can reach the end from position i
        dp[n - 1] = true; // Base case: we can reach the end from the end
        
        for (int i = n - 2; i >= 0; i--) {
            // Maximum jump length from position i
            int maxJump = Math.min(i + nums[i], n - 1);
            
            // Check if we can reach any position that can reach the end
            for (int j = i + 1; j <= maxJump; j++) {
                if (dp[j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        
        return dp[0];
    }
    
    /**
     * Jump Game II - Find minimum number of jumps to reach the end
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int minJumps(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        
        int jumps = 0;
        int currentMaxReach = 0;
        int nextMaxReach = 0;
        
        for (int i = 0; i < nums.length - 1; i++) {
            // Update the farthest we can reach
            nextMaxReach = Math.max(nextMaxReach, i + nums[i]);
            
            // If we've reached the current boundary, make a jump
            if (i == currentMaxReach) {
                jumps++;
                currentMaxReach = nextMaxReach;
                
                // If we can already reach the end, break
                if (currentMaxReach >= nums.length - 1) {
                    break;
                }
            }
        }
        
        return jumps;
    }
    
    public static void main(String[] args) {
        int[][] testCases = {
            {2, 3, 1, 1, 4},          // Expected: true, 2 jumps
            {3, 2, 1, 0, 4},           // Expected: false
            {0},                       // Expected: true, 0 jumps
            {2, 0},                    // Expected: true, 1 jump
            {1, 1, 1, 1, 1},           // Expected: true, 4 jumps
            {5, 9, 3, 2, 1, 0, 2, 3, 3, 1, 0, 0} // Expected: true, 3 jumps
        };
        
        for (int[] nums : testCases) {
            System.out.println("Array: " + Arrays.toString(nums));
            
            boolean canJump1 = canJump(nums);
            System.out.println("Can jump (Greedy Backward): " + canJump1);
            
            boolean canJump2 = canJumpForward(nums);
            System.out.println("Can jump (Greedy Forward): " + canJump2);
            
            boolean canJump3 = canJumpDP(nums);
            System.out.println("Can jump (Dynamic Programming): " + canJump3);
            
            if (canJump1) {
                int minJumps = minJumps(nums);
                System.out.println("Minimum jumps needed: " + minJumps);
            }
            
            System.out.println();
        }
    }
}