package com.interview.practice.arrays;

/**
 * Utility class for array manipulation problems.
 */
public class ArrayUtils {
    
    /**
     * Finds the maximum value in an array.
     * 
     * @param arr the input array
     * @return the maximum value in the array
     * @throws IllegalArgumentException if the array is null or empty
     */
    public static int findMax(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        
        return max;
    }
}