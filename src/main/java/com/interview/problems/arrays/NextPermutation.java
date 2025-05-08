package com.interview.problems.arrays;

import java.util.Arrays;

/**
 * Problem 7: Next Permutation
 * 
 * Implement next permutation, which rearranges numbers into the lexicographically next greater 
 * permutation of numbers. If such an arrangement is not possible, it must rearrange it as the 
 * lowest possible order (i.e., sorted in ascending order).
 * 
 * The replacement must be in place and use only constant extra memory.
 * 
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
public class NextPermutation {
    
    /**
     * Find the next permutation
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }
        
        int n = nums.length;
        
        // Step 1: Find the first pair of adjacent elements in descending order (from right to left)
        // i.e., find the first element that is smaller than its next element
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        
        // If such pair exists
        if (i >= 0) {
            // Step 2: Find the smallest element to the right of nums[i] that is greater than nums[i]
            int j = n - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            
            // Step 3: Swap nums[i] and nums[j]
            swap(nums, i, j);
        }
        
        // Step 4: Reverse the subarray starting at nums[i+1]
        reverse(nums, i + 1, n - 1);
    }
    
    /**
     * Helper method to swap two elements in an array
     */
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    /**
     * Helper method to reverse a subarray
     */
    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }
    
    /**
     * Brute force approach: Generate all permutations and find the next one
     * Very inefficient, included for demonstration
     * Time Complexity: O(n!)
     * Space Complexity: O(n)
     */
    public static int[] nextPermutationBruteForce(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        
        // Generate all permutations and sort them
        // This is extremely inefficient and just for demonstration
        
        // Clone the array to avoid modifying the original
        int[] result = nums.clone();
        
        // Convert to Integer array for easier manipulation
        Integer[] numbers = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            numbers[i] = nums[i];
        }
        
        // Find all permutations (in practice, you would never do this)
        java.util.List<java.util.List<Integer>> permutations = new java.util.ArrayList<>();
        generatePermutations(numbers, 0, permutations);
        
        // Sort the permutations
        permutations.sort((a, b) -> {
            for (int i = 0; i < a.size(); i++) {
                int cmp = a.get(i).compareTo(b.get(i));
                if (cmp != 0) {
                    return cmp;
                }
            }
            return 0;
        });
        
        // Find the current permutation in the list
        int currentIndex = -1;
        for (int i = 0; i < permutations.size(); i++) {
            boolean isMatch = true;
            for (int j = 0; j < nums.length; j++) {
                if (permutations.get(i).get(j) != nums[j]) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                currentIndex = i;
                break;
            }
        }
        
        // Find the next permutation or wrap around to the first
        int nextIndex = (currentIndex + 1) % permutations.size();
        
        // Convert back to int array
        for (int i = 0; i < nums.length; i++) {
            result[i] = permutations.get(nextIndex).get(i);
        }
        
        return result;
    }
    
    /**
     * Helper method to generate all permutations of an array recursively
     */
    private static void generatePermutations(Integer[] nums, int start, java.util.List<java.util.List<Integer>> permutations) {
        if (start == nums.length - 1) {
            permutations.add(new java.util.ArrayList<>(Arrays.asList(nums)));
            return;
        }
        
        for (int i = start; i < nums.length; i++) {
            // Swap elements
            swap(nums, start, i);
            // Recursively generate permutations for the rest of the array
            generatePermutations(nums, start + 1, permutations);
            // Backtrack
            swap(nums, start, i);
        }
    }
    
    /**
     * Helper method to swap two elements in an Integer array
     */
    private static void swap(Integer[] nums, int i, int j) {
        Integer temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    /**
     * Alternative approach using Java's built-in nextPermutation method
     * from the Collections class (not available for primitive arrays)
     */
    public static int[] nextPermutationJava(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        
        // Convert to Integer array
        Integer[] numbers = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            numbers[i] = nums[i];
        }
        
        // Use Java's method to get next permutation
        boolean hasNext = java.util.Collections.next_permutation(Arrays.asList(numbers));
        
        if (!hasNext) {
            // If no next permutation, sort the array
            Arrays.sort(numbers);
        }
        
        // Convert back to int array
        int[] result = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            result[i] = numbers[i];
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        int[][] testCases = {
            {1, 2, 3},          // Expected: [1,3,2]
            {3, 2, 1},          // Expected: [1,2,3]
            {1, 1, 5},          // Expected: [1,5,1]
            {1},                // Expected: [1]
            {1, 3, 2},          // Expected: [2,1,3]
            {2, 3, 1},          // Expected: [3,1,2]
            {5, 4, 7, 5, 3, 2}   // Expected: [5,5,2,3,4,7]
        };
        
        for (int[] nums : testCases) {
            System.out.println("Original: " + Arrays.toString(nums));
            
            // Clone arrays for each method
            int[] nums1 = nums.clone();
            int[] nums2 = nums.clone();
            int[] nums3 = nums.clone();
            
            // Method 1: Optimal approach
            nextPermutation(nums1);
            System.out.println("Next Permutation (Optimal): " + Arrays.toString(nums1));
            
            // Method 2: Brute force (for small arrays only)
            if (nums.length <= 6) { // Too slow for larger arrays
                int[] next = nextPermutationBruteForce(nums2);
                System.out.println("Next Permutation (Brute Force): " + Arrays.toString(next));
            }
            
            // Method 3: Using Java's Collections
            int[] next2 = nextPermutationJava(nums3);
            System.out.println("Next Permutation (Java Collections): " + Arrays.toString(next2));
            
            System.out.println();
        }
    }
}