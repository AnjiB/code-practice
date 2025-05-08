package com.interview.problems.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Problem 5: Merge Intervals
 * 
 * Given an array of intervals where intervals[i] = [start_i, end_i], merge all 
 * overlapping intervals, and return an array of the non-overlapping intervals 
 * that cover all the intervals in the input.
 * 
 * Time Complexity: O(n log n) due to sorting
 * Space Complexity: O(n) for the result
 */
public class MergeIntervals {
    
    /**
     * Merge overlapping intervals
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
     */
    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        
        List<int[]> result = new ArrayList<>();
        int[] currentInterval = intervals[0];
        result.add(currentInterval);
        
        for (int i = 1; i < intervals.length; i++) {
            int currentEnd = currentInterval[1];
            int nextStart = intervals[i][0];
            int nextEnd = intervals[i][1];
            
            // If current interval overlaps with the next interval, merge them
            if (currentEnd >= nextStart) {
                // Update the end of the current interval if necessary
                currentInterval[1] = Math.max(currentEnd, nextEnd);
            } else {
                // No overlap, add the next interval to result
                currentInterval = intervals[i];
                result.add(currentInterval);
            }
        }
        
        return result.toArray(new int[result.size()][]);
    }
    
    /**
     * Alternative approach using in-place modification of input array
     * Time Complexity: O(n log n)
     * Space Complexity: O(1) excluding the space for the result
     */
    public static int[][] mergeInPlace(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        
        int count = 0; // Count of merged intervals
        
        for (int i = 1; i < intervals.length; i++) {
            // Check if current interval overlaps with the last merged interval
            if (intervals[count][1] >= intervals[i][0]) {
                // Merge by updating the end time of the last merged interval
                intervals[count][1] = Math.max(intervals[count][1], intervals[i][1]);
            } else {
                // No overlap, move to the next position in the result
                count++;
                intervals[count] = intervals[i];
            }
        }
        
        // Create and return the result array with only merged intervals
        return Arrays.copyOf(intervals, count + 1);
    }
    
    /**
     * Merge intervals when the input is not sorted
     * Demonstrates how to handle more general cases
     */
    public static int[][] mergeUnsorted(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        
        // Sort intervals by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        List<int[]> result = new ArrayList<>();
        
        for (int[] interval : intervals) {
            // If result is empty or no overlap with the last interval in result
            if (result.isEmpty() || result.get(result.size() - 1)[1] < interval[0]) {
                result.add(interval);
            } else {
                // Merge with the last interval in result
                result.get(result.size() - 1)[1] = Math.max(result.get(result.size() - 1)[1], interval[1]);
            }
        }
        
        return result.toArray(new int[result.size()][]);
    }
    
    public static void main(String[] args) {
        int[][][] testCases = {
            {{1, 3}, {2, 6}, {8, 10}, {15, 18}},   // Expected: [[1,6],[8,10],[15,18]]
            {{1, 4}, {4, 5}},                       // Expected: [[1,5]]
            {{1, 4}, {2, 3}},                       // Expected: [[1,4]]
            {{1, 4}, {0, 0}},                       // Expected: [[0,0],[1,4]]
            {{1, 4}, {0, 1}}                        // Expected: [[0,4]]
        };
        
        for (int[][] intervals : testCases) {
            System.out.println("Input intervals: " + Arrays.deepToString(intervals));
            
            int[][] merged = merge(intervals.clone());
            System.out.println("Merged intervals: " + Arrays.deepToString(merged));
            
            int[][] mergedInPlace = mergeInPlace(intervals.clone());
            System.out.println("Merged in-place: " + Arrays.deepToString(mergedInPlace));
            
            // Shuffle the intervals to demonstrate the unsorted approach
            if (intervals.length > 1) {
                // Create a manually shuffled copy for demonstration
                int[][] shuffled = intervals.clone();
                if (shuffled.length > 1) {
                    int[] temp = shuffled[0];
                    shuffled[0] = shuffled[shuffled.length - 1];
                    shuffled[shuffled.length - 1] = temp;
                }
                
                System.out.println("Shuffled intervals: " + Arrays.deepToString(shuffled));
                int[][] mergedUnsorted = mergeUnsorted(shuffled);
                System.out.println("Merged unsorted: " + Arrays.deepToString(mergedUnsorted));
            }
            
            System.out.println();
        }
    }
}