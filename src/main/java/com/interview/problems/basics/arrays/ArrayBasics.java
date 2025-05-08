package com.interview.problems.basics.arrays;

import java.util.Arrays;
import java.util.Random;

/**
 * Basic Array operations and manipulations
 */
public class ArrayBasics {
    
    // Program 1: Create and initialize an array
    public static int[] createArray(int size, int defaultValue) {
        int[] array = new int[size];
        Arrays.fill(array, defaultValue);
        return array;
    }
    
    // Program 2: Print an array
    public static void printArray(int[] array) {
        if (array == null) {
            System.out.println("null");
            return;
        }
        
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    // Program 3: Find the sum of all elements in an array
    public static int sum(int[] array) {
        if (array == null) {
            return 0;
        }
        
        int total = 0;
        for (int value : array) {
            total += value;
        }
        return total;
    }
    
    // Program 4: Find the average of elements in an array
    public static double average(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        
        return (double) sum(array) / array.length;
    }
    
    // Program 5: Find the maximum value in an array
    public static int findMax(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }
        
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
    
    // Program 6: Find the minimum value in an array
    public static int findMin(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }
        
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }
    
    // Program 7: Check if an array contains a specific value
    public static boolean contains(int[] array, int value) {
        if (array == null) {
            return false;
        }
        
        for (int element : array) {
            if (element == value) {
                return true;
            }
        }
        return false;
    }
    
    // Program 8: Find the index of a value in an array
    public static int indexOf(int[] array, int value) {
        if (array == null) {
            return -1;
        }
        
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    // Program 9: Reverse an array
    public static void reverse(int[] array) {
        if (array == null) {
            return;
        }
        
        int left = 0;
        int right = array.length - 1;
        
        while (left < right) {
            // Swap elements
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            
            left++;
            right--;
        }
    }
    
    // Program 10: Sort an array in ascending order
    public static void sortAscending(int[] array) {
        if (array == null) {
            return;
        }
        
        Arrays.sort(array);
    }
    
    // Program 11: Sort an array in descending order
    public static void sortDescending(int[] array) {
        if (array == null) {
            return;
        }
        
        // First sort in ascending order
        Arrays.sort(array);
        
        // Then reverse the array
        reverse(array);
    }
    
    // Program 12: Copy an array
    public static int[] copyArray(int[] array) {
        if (array == null) {
            return null;
        }
        
        return Arrays.copyOf(array, array.length);
    }
    
    // Program 13: Merge two arrays
    public static int[] mergeArrays(int[] array1, int[] array2) {
        if (array1 == null) {
            return array2 != null ? Arrays.copyOf(array2, array2.length) : null;
        }
        
        if (array2 == null) {
            return Arrays.copyOf(array1, array1.length);
        }
        
        int[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        
        return result;
    }
    
    // Program 14: Find the second largest element in an array
    public static int findSecondLargest(int[] array) {
        if (array == null || array.length < 2) {
            throw new IllegalArgumentException("Array must have at least two elements");
        }
        
        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;
        
        for (int value : array) {
            if (value > max) {
                secondMax = max;
                max = value;
            } else if (value > secondMax && value != max) {
                secondMax = value;
            }
        }
        
        if (secondMax == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("No second largest element found");
        }
        
        return secondMax;
    }
    
    // Program 15: Remove duplicates from a sorted array
    public static int[] removeDuplicates(int[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }
        
        int uniqueCount = 1;
        for (int i = 1; i < array.length; i++) {
            if (array[i] != array[i-1]) {
                array[uniqueCount++] = array[i];
            }
        }
        
        return Arrays.copyOf(array, uniqueCount);
    }
    
    // Program 16: Find even numbers in an array
    public static int[] findEvenNumbers(int[] array) {
        if (array == null) {
            return null;
        }
        
        // Count even numbers first
        int count = 0;
        for (int value : array) {
            if (value % 2 == 0) {
                count++;
            }
        }
        
        // Create result array and fill it
        int[] result = new int[count];
        int index = 0;
        for (int value : array) {
            if (value % 2 == 0) {
                result[index++] = value;
            }
        }
        
        return result;
    }
    
    // Program 17: Find odd numbers in an array
    public static int[] findOddNumbers(int[] array) {
        if (array == null) {
            return null;
        }
        
        // Count odd numbers first
        int count = 0;
        for (int value : array) {
            if (value % 2 != 0) {
                count++;
            }
        }
        
        // Create result array and fill it
        int[] result = new int[count];
        int index = 0;
        for (int value : array) {
            if (value % 2 != 0) {
                result[index++] = value;
            }
        }
        
        return result;
    }
    
    // Program 18: Generate a random array
    public static int[] generateRandomArray(int size, int min, int max) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }
        
        Random random = new Random();
        int[] array = new int[size];
        
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(max - min + 1) + min;
        }
        
        return array;
    }
    
    // Program 19: Count occurrences of a value in an array
    public static int countOccurrences(int[] array, int value) {
        if (array == null) {
            return 0;
        }
        
        int count = 0;
        for (int element : array) {
            if (element == value) {
                count++;
            }
        }
        return count;
    }
    
    // Program 20: Rotate an array to the right by k steps
    public static void rotateRight(int[] array, int k) {
        if (array == null || array.length <= 1 || k % array.length == 0) {
            return;
        }
        
        k = k % array.length; // Handle case where k > array.length
        reverse(array, 0, array.length - 1);
        reverse(array, 0, k - 1);
        reverse(array, k, array.length - 1);
    }
    
    // Helper method for rotating array
    private static void reverse(int[] array, int start, int end) {
        while (start < end) {
            int temp = array[start];
            array[start] = array[end];
            array[end] = temp;
            start++;
            end--;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("===== Array Basics Examples =====");
        
        // Create and initialize an array
        int[] arr = createArray(5, 10);
        System.out.print("Created array: ");
        printArray(arr);
        
        // Modify array for examples
        arr = new int[]{5, 2, 9, 1, 5, 6};
        System.out.print("Sample array: ");
        printArray(arr);
        
        System.out.println("Sum: " + sum(arr));
        System.out.println("Average: " + average(arr));
        System.out.println("Max: " + findMax(arr));
        System.out.println("Min: " + findMin(arr));
        
        System.out.println("Contains 9: " + contains(arr, 9));
        System.out.println("Index of 9: " + indexOf(arr, 9));
        
        int[] copy = copyArray(arr);
        System.out.print("Copy array: ");
        printArray(copy);
        
        reverse(copy);
        System.out.print("Reversed: ");
        printArray(copy);
        
        int[] sorted = copyArray(arr);
        sortAscending(sorted);
        System.out.print("Sorted ascending: ");
        printArray(sorted);
        
        sortDescending(sorted);
        System.out.print("Sorted descending: ");
        printArray(sorted);
        
        int[] arr2 = {10, 11, 12};
        int[] merged = mergeArrays(arr, arr2);
        System.out.print("Merged array: ");
        printArray(merged);
        
        System.out.println("Second largest: " + findSecondLargest(arr));
        
        int[] withDuplicates = {1, 1, 2, 2, 3, 4, 4, 5};
        System.out.print("Array with duplicates: ");
        printArray(withDuplicates);
        
        int[] noDuplicates = removeDuplicates(withDuplicates);
        System.out.print("After removing duplicates: ");
        printArray(noDuplicates);
        
        System.out.print("Even numbers: ");
        printArray(findEvenNumbers(arr));
        
        System.out.print("Odd numbers: ");
        printArray(findOddNumbers(arr));
        
        int[] random = generateRandomArray(5, 1, 100);
        System.out.print("Random array: ");
        printArray(random);
        
        System.out.println("Count of 5 in sample array: " + countOccurrences(arr, 5));
        
        int[] rotateTest = {1, 2, 3, 4, 5};
        System.out.print("Before rotation: ");
        printArray(rotateTest);
        
        rotateRight(rotateTest, 2);
        System.out.print("After rotating right by 2 steps: ");
        printArray(rotateTest);
    }
}