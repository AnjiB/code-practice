package com.interview.problems.basics.arrays;

import java.util.*;

/**
 * More examples of common array operations and problem solutions
 */
public class ArrayExamples {
    
    // Program 1: Check if an array has duplicate elements
    public static boolean hasDuplicates(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return false;
        }
        
        Set<Integer> seen = new HashSet<>();
        
        for (int num : arr) {
            if (seen.contains(num)) {
                return true;
            }
            seen.add(num);
        }
        
        return false;
    }
    
    // Program 2: Find all duplicate elements in an array
    public static List<Integer> findDuplicates(int[] arr) {
        if (arr == null) {
            return new ArrayList<>();
        }
        
        List<Integer> duplicates = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        Set<Integer> duplicateSet = new HashSet<>();
        
        for (int num : arr) {
            if (!seen.add(num) && !duplicateSet.contains(num)) {
                duplicates.add(num);
                duplicateSet.add(num);
            }
        }
        
        return duplicates;
    }
    
    // Program 3: Move all zeros to the end of the array
    public static void moveZerosToEnd(int[] arr) {
        if (arr == null) {
            return;
        }
        
        int index = 0;
        
        // Move all non-zero elements to the front
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                arr[index++] = arr[i];
            }
        }
        
        // Fill the rest with zeros
        while (index < arr.length) {
            arr[index++] = 0;
        }
    }
    
    // Program 4: Find common elements in three sorted arrays
    public static List<Integer> findCommonElements(int[] arr1, int[] arr2, int[] arr3) {
        List<Integer> result = new ArrayList<>();
        
        if (arr1 == null || arr2 == null || arr3 == null) {
            return result;
        }
        
        int i = 0, j = 0, k = 0;
        
        while (i < arr1.length && j < arr2.length && k < arr3.length) {
            if (arr1[i] == arr2[j] && arr2[j] == arr3[k]) {
                result.add(arr1[i]);
                i++;
                j++;
                k++;
            } else if (arr1[i] < arr2[j]) {
                i++;
            } else if (arr2[j] < arr3[k]) {
                j++;
            } else {
                k++;
            }
        }
        
        return result;
    }
    
    // Program 5: Rearrange array in alternating positive and negative items
    public static void rearrangeAlternating(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        // Sort the array
        Arrays.sort(arr);
        
        int[] temp = arr.clone();
        int posIndex = 0;
        int negIndex = 0;
        
        // Find the index of the first positive element
        while (posIndex < arr.length && temp[posIndex] < 0) {
            posIndex++;
        }
        
        // Set the negative index to first negative element
        negIndex = 0;
        
        // Fill array with alternating positive and negative elements
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0 && posIndex < arr.length) {
                // Even position - positive number
                arr[i] = temp[posIndex++];
            } else if (negIndex < posIndex) {
                // Odd position - negative number
                arr[i] = temp[negIndex++];
            } else {
                // If we run out of negative numbers, fill with the remaining positive numbers
                arr[i] = temp[posIndex++];
            }
        }
    }
    
    // Program 6: Find peak element in an array
    public static int findPeakElement(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }
        
        if (arr.length == 1) {
            return 0;
        }
        
        if (arr[0] > arr[1]) {
            return 0;
        }
        
        if (arr[arr.length - 1] > arr[arr.length - 2]) {
            return arr.length - 1;
        }
        
        // Check for peak element
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
                return i;
            }
        }
        
        return -1; // No peak found
    }
    
    // Program 7: Calculate the maximum sum of contiguous subarray (Kadane's algorithm)
    public static int maxSubArraySum(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }
        
        int maxSoFar = arr[0];
        int maxEndingHere = arr[0];
        
        for (int i = 1; i < arr.length; i++) {
            maxEndingHere = Math.max(arr[i], maxEndingHere + arr[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        
        return maxSoFar;
    }
    
    // Program 8: Find the missing number in an array of 1 to n
    public static int findMissingNumber(int[] arr, int n) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        
        int expectedSum = n * (n + 1) / 2;
        int actualSum = 0;
        
        for (int num : arr) {
            actualSum += num;
        }
        
        return expectedSum - actualSum;
    }
    
    // Program 9: Find pair with given sum in an array
    public static int[] findPairWithSum(int[] arr, int targetSum) {
        if (arr == null) {
            return new int[0];
        }
        
        Map<Integer, Integer> numMap = new HashMap<>();
        
        for (int i = 0; i < arr.length; i++) {
            int complement = targetSum - arr[i];
            
            if (numMap.containsKey(complement)) {
                return new int[] {numMap.get(complement), i};
            }
            
            numMap.put(arr[i], i);
        }
        
        return new int[0]; // No pair found
    }
    
    // Program 10: Check if subarray with 0 sum exists
    public static boolean hasZeroSumSubarray(int[] arr) {
        if (arr == null) {
            return false;
        }
        
        Set<Integer> sumSet = new HashSet<>();
        int sum = 0;
        
        // Add 0 to handle the case when subarray starts from index 0
        sumSet.add(0);
        
        for (int num : arr) {
            sum += num;
            
            // If prefix sum already exists, there's a zero-sum subarray
            if (sumSet.contains(sum)) {
                return true;
            }
            
            sumSet.add(sum);
        }
        
        return false;
    }
    
    // Program 11: Count pairs with a given sum
    public static int countPairsWithSum(int[] arr, int sum) {
        if (arr == null) {
            return 0;
        }
        
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        int count = 0;
        
        for (int num : arr) {
            int complement = sum - num;
            
            if (frequencyMap.containsKey(complement)) {
                count += frequencyMap.get(complement);
            }
            
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        return count;
    }
    
    // Program 12: Find the longest consecutive sequence in an array
    public static int longestConsecutiveSequence(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        
        Set<Integer> numSet = new HashSet<>();
        
        // Add all numbers to the set
        for (int num : arr) {
            numSet.add(num);
        }
        
        int longestStreak = 0;
        
        for (int num : numSet) {
            // Only check for sequences starting with the smallest number
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                
                // Check for consecutive elements
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }
                
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        
        return longestStreak;
    }
    
    // Program 13: Find leaders in an array (elements greater than all elements to their right)
    public static List<Integer> findLeaders(int[] arr) {
        List<Integer> leaders = new ArrayList<>();
        
        if (arr == null || arr.length == 0) {
            return leaders;
        }
        
        int max = arr[arr.length - 1];
        leaders.add(max);
        
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] > max) {
                max = arr[i];
                leaders.add(max);
            }
        }
        
        Collections.reverse(leaders);
        return leaders;
    }
    
    // Program 14: Rearrange array such that arr[i] becomes arr[arr[i]]
    public static void rearrange(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        
        int n = arr.length;
        
        // First step: Increase all values by (arr[arr[i]] % n) * n
        for (int i = 0; i < n; i++) {
            arr[i] += (arr[arr[i]] % n) * n;
        }
        
        // Second step: Divide all values by n to get the new values
        for (int i = 0; i < n; i++) {
            arr[i] /= n;
        }
    }
    
    // Program 15: Find the majority element in an array (occurs more than n/2 times)
    public static int findMajorityElement(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }
        
        int candidate = arr[0];
        int count = 1;
        
        // Find candidate for majority element using Boyer-Moore Voting Algorithm
        for (int i = 1; i < arr.length; i++) {
            if (count == 0) {
                candidate = arr[i];
                count = 1;
            } else if (candidate == arr[i]) {
                count++;
            } else {
                count--;
            }
        }
        
        // Verify if the candidate is the majority element
        count = 0;
        for (int num : arr) {
            if (num == candidate) {
                count++;
            }
        }
        
        if (count > arr.length / 2) {
            return candidate;
        } else {
            throw new IllegalArgumentException("No majority element exists");
        }
    }
    
    // Program 16: Sort 0s, 1s, and 2s in an array (Dutch National Flag problem)
    public static void sortColors(int[] arr) {
        if (arr == null) {
            return;
        }
        
        int low = 0, mid = 0, high = arr.length - 1;
        
        // Sort the array in a single pass
        while (mid <= high) {
            switch (arr[mid]) {
                case 0:
                    // Swap arr[low] and arr[mid]
                    int temp = arr[low];
                    arr[low] = arr[mid];
                    arr[mid] = temp;
                    low++;
                    mid++;
                    break;
                case 1:
                    mid++;
                    break;
                case 2:
                    // Swap arr[mid] and arr[high]
                    temp = arr[mid];
                    arr[mid] = arr[high];
                    arr[high] = temp;
                    high--;
                    break;
            }
        }
    }
    
    // Program 17: Find the kth smallest element in an array
    public static int findKthSmallest(int[] arr, int k) {
        if (arr == null || k <= 0 || k > arr.length) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        // Using Arrays.sort() for simplicity
        Arrays.sort(arr);
        return arr[k - 1];
    }
    
    // Program 18: Find equilibrium index in an array
    public static int findEquilibriumIndex(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        
        int totalSum = 0;
        for (int num : arr) {
            totalSum += num;
        }
        
        int leftSum = 0;
        
        for (int i = 0; i < arr.length; i++) {
            // Right sum is total sum minus current element and left sum
            int rightSum = totalSum - leftSum - arr[i];
            
            if (leftSum == rightSum) {
                return i;
            }
            
            leftSum += arr[i];
        }
        
        return -1; // No equilibrium index found
    }
    
    // Program 19: Merge two sorted arrays
    public static int[] mergeSortedArrays(int[] arr1, int[] arr2) {
        if (arr1 == null && arr2 == null) {
            return new int[0];
        }
        
        if (arr1 == null) {
            return arr2.clone();
        }
        
        if (arr2 == null) {
            return arr1.clone();
        }
        
        int[] merged = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        
        // Merge arrays
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                merged[k++] = arr1[i++];
            } else {
                merged[k++] = arr2[j++];
            }
        }
        
        // Copy remaining elements from arr1
        while (i < arr1.length) {
            merged[k++] = arr1[i++];
        }
        
        // Copy remaining elements from arr2
        while (j < arr2.length) {
            merged[k++] = arr2[j++];
        }
        
        return merged;
    }
    
    // Program 20: Find triplet with zero sum
    public static List<List<Integer>> findTriplets(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        
        if (arr == null || arr.length < 3) {
            return result;
        }
        
        // Sort the array
        Arrays.sort(arr);
        
        for (int i = 0; i < arr.length - 2; i++) {
            // Skip duplicates
            if (i > 0 && arr[i] == arr[i - 1]) {
                continue;
            }
            
            int left = i + 1;
            int right = arr.length - 1;
            
            while (left < right) {
                int sum = arr[i] + arr[left] + arr[right];
                
                if (sum == 0) {
                    result.add(Arrays.asList(arr[i], arr[left], arr[right]));
                    
                    // Skip duplicates
                    while (left < right && arr[left] == arr[left + 1]) {
                        left++;
                    }
                    
                    while (left < right && arr[right] == arr[right - 1]) {
                        right--;
                    }
                    
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println("===== Array Examples =====");
        
        int[] arr1 = {1, 2, 3, 1, 4, 5};
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("Has duplicates: " + hasDuplicates(arr1));
        System.out.println("Duplicates: " + findDuplicates(arr1));
        
        int[] arr2 = {1, 0, 2, 0, 0, 3, 4};
        System.out.println("\nArray: " + Arrays.toString(arr2));
        moveZerosToEnd(arr2);
        System.out.println("After moving zeros to end: " + Arrays.toString(arr2));
        
        int[] commonArr1 = {1, 5, 10, 20, 40, 80};
        int[] commonArr2 = {6, 7, 20, 80, 100};
        int[] commonArr3 = {3, 4, 15, 20, 30, 70, 80, 120};
        System.out.println("\nCommon elements in three arrays: " + 
                          findCommonElements(commonArr1, commonArr2, commonArr3));
        
        int[] alternatingArr = {-1, 2, -3, 4, 5, 6, -7, 8, 9};
        System.out.println("\nArray: " + Arrays.toString(alternatingArr));
        rearrangeAlternating(alternatingArr);
        System.out.println("After alternating rearrangement: " + Arrays.toString(alternatingArr));
        
        int[] peakArr = {1, 3, 20, 4, 1, 0};
        System.out.println("\nArray: " + Arrays.toString(peakArr));
        int peakIndex = findPeakElement(peakArr);
        System.out.println("Peak element index: " + peakIndex + ", value: " + peakArr[peakIndex]);
        
        int[] kadaneArr = {-2, -3, 4, -1, -2, 1, 5, -3};
        System.out.println("\nArray: " + Arrays.toString(kadaneArr));
        System.out.println("Maximum subarray sum: " + maxSubArraySum(kadaneArr));
        
        int[] missingArr = {1, 2, 3, 5, 6, 7};
        System.out.println("\nArray: " + Arrays.toString(missingArr));
        System.out.println("Missing number: " + findMissingNumber(missingArr, 7));
        
        int[] sumPairArr = {8, 7, 2, 5, 3, 1};
        int target = 10;
        System.out.println("\nArray: " + Arrays.toString(sumPairArr));
        int[] pair = findPairWithSum(sumPairArr, target);
        if (pair.length == 2) {
            System.out.println("Pair with sum " + target + ": (" + sumPairArr[pair[0]] + ", " + sumPairArr[pair[1]] + ")");
        } else {
            System.out.println("No pair found with sum " + target);
        }
        
        int[] zeroSumArr = {4, 2, -3, 1, 6};
        System.out.println("\nArray: " + Arrays.toString(zeroSumArr));
        System.out.println("Has zero sum subarray: " + hasZeroSumSubarray(zeroSumArr));
        
        int[] pairCountArr = {1, 5, 7, 1, 5, 7, 1};
        int pairSum = 6;
        System.out.println("\nArray: " + Arrays.toString(pairCountArr));
        System.out.println("Count of pairs with sum " + pairSum + ": " + countPairsWithSum(pairCountArr, pairSum));
        
        int[] consecutiveArr = {100, 4, 200, 1, 3, 2};
        System.out.println("\nArray: " + Arrays.toString(consecutiveArr));
        System.out.println("Longest consecutive sequence: " + longestConsecutiveSequence(consecutiveArr));
        
        int[] leadersArr = {16, 17, 4, 3, 5, 2};
        System.out.println("\nArray: " + Arrays.toString(leadersArr));
        System.out.println("Leaders: " + findLeaders(leadersArr));
        
        int[] rearrangeArr = {3, 2, 0, 1};
        System.out.println("\nArray: " + Arrays.toString(rearrangeArr));
        rearrange(rearrangeArr);
        System.out.println("After rearrangement: " + Arrays.toString(rearrangeArr));
        
        int[] majorityArr = {3, 3, 4, 2, 4, 4, 2, 4, 4};
        System.out.println("\nArray: " + Arrays.toString(majorityArr));
        System.out.println("Majority element: " + findMajorityElement(majorityArr));
        
        int[] colorsArr = {2, 0, 2, 1, 1, 0};
        System.out.println("\nArray: " + Arrays.toString(colorsArr));
        sortColors(colorsArr);
        System.out.println("After sorting colors: " + Arrays.toString(colorsArr));
        
        int[] kthArr = {7, 10, 4, 3, 20, 15};
        int k = 3;
        System.out.println("\nArray: " + Arrays.toString(kthArr));
        System.out.println(k + "th smallest element: " + findKthSmallest(kthArr, k));
        
        int[] equilibriumArr = {-7, 1, 5, 2, -4, 3, 0};
        System.out.println("\nArray: " + Arrays.toString(equilibriumArr));
        int eqIndex = findEquilibriumIndex(equilibriumArr);
        System.out.println("Equilibrium index: " + eqIndex + (eqIndex != -1 ? ", value: " + equilibriumArr[eqIndex] : ""));
        
        int[] mergeArr1 = {1, 3, 5, 7};
        int[] mergeArr2 = {2, 4, 6, 8};
        System.out.println("\nArray 1: " + Arrays.toString(mergeArr1));
        System.out.println("Array 2: " + Arrays.toString(mergeArr2));
        System.out.println("Merged: " + Arrays.toString(mergeSortedArrays(mergeArr1, mergeArr2)));
        
        int[] tripletArr = {0, -1, 2, -3, 1};
        System.out.println("\nArray: " + Arrays.toString(tripletArr));
        System.out.println("Triplets with zero sum: " + findTriplets(tripletArr));
    }
}