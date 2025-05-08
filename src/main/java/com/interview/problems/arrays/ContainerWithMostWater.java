package com.interview.problems.arrays;

/**
 * Problem 2: Container With Most Water
 * 
 * You are given an integer array height of length n. There are n vertical lines drawn such that 
 * the two endpoints of the ith line are (i, 0) and (i, height[i]).
 * 
 * Find two lines that together with the x-axis form a container, such that the container contains 
 * the most water (area).
 * 
 * Return the maximum amount of water a container can store.
 * 
 * Time Complexity: O(n) using two-pointer approach
 * Space Complexity: O(1)
 */
public class ContainerWithMostWater {
    
    /**
     * Brute force approach - check all pairs of lines
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     */
    public static int maxAreaBruteForce(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        
        int maxArea = 0;
        
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                // Calculate area between lines at i and j
                int minHeight = Math.min(height[i], height[j]);
                int width = j - i;
                int area = minHeight * width;
                
                // Update maximum area
                maxArea = Math.max(maxArea, area);
            }
        }
        
        return maxArea;
    }
    
    /**
     * Optimized approach using two pointers
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int maxAreaTwoPointer(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;
        
        while (left < right) {
            // Calculate area between lines at left and right
            int minHeight = Math.min(height[left], height[right]);
            int width = right - left;
            int area = minHeight * width;
            
            // Update maximum area
            maxArea = Math.max(maxArea, area);
            
            // Move the pointer with the smaller height inward
            // (moving the taller pointer can only reduce area)
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxArea;
    }
    
    /**
     * Optimized approach with early termination checks
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static int maxAreaOptimized(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        
        int maxArea = 0;
        int left = 0;
        int right = height.length - 1;
        
        while (left < right) {
            // Calculate current height and area
            int h = Math.min(height[left], height[right]);
            int area = h * (right - left);
            
            // Update maximum area
            maxArea = Math.max(maxArea, area);
            
            // Skip lines that are shorter than the current minimum height
            // as they cannot form a larger container
            if (height[left] < height[right]) {
                left++;
                while (left < right && height[left] <= h) {
                    left++;
                }
            } else {
                right--;
                while (left < right && height[right] <= h) {
                    right--;
                }
            }
        }
        
        return maxArea;
    }
    
    public static void main(String[] args) {
        int[][] testCases = {
            {1, 8, 6, 2, 5, 4, 8, 3, 7},  // Expected: 49
            {1, 1},                        // Expected: 1
            {4, 3, 2, 1, 4},              // Expected: 16
            {1, 2, 1},                     // Expected: 2
            {1, 8, 100, 2, 100, 4, 8, 3, 7} // Expected: 200
        };
        
        for (int[] height : testCases) {
            System.out.print("Heights: [");
            for (int i = 0; i < height.length; i++) {
                System.out.print(height[i]);
                if (i < height.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
            
            System.out.println("Brute Force: " + maxAreaBruteForce(height));
            System.out.println("Two Pointer: " + maxAreaTwoPointer(height));
            System.out.println("Optimized: " + maxAreaOptimized(height));
            System.out.println();
        }
    }
}