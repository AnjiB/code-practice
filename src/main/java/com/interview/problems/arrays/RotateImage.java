package com.interview.problems.arrays;

import java.util.Arrays;

/**
 * Problem 6: Rotate Image
 * 
 * You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).
 * 
 * You have to rotate the image in-place, which means you have to modify the input 2D matrix directly.
 * DO NOT allocate another 2D matrix and do the rotation.
 * 
 * Time Complexity: O(n²) where n is the width/height of the matrix
 * Space Complexity: O(1) since we rotate in-place
 */
public class RotateImage {
    
    /**
     * Rotate the image by 90 degrees clockwise using the transpose and reverse approach
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     */
    public static void rotate(int[][] matrix) {
        if (matrix == null || matrix.length <= 1) {
            return;
        }
        
        int n = matrix.length;
        
        // Step 1: Transpose the matrix (swap rows with columns)
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                // Swap matrix[i][j] with matrix[j][i]
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        
        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                // Swap matrix[i][j] with matrix[i][n-1-j]
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }
    
    /**
     * Rotate the image by 90 degrees clockwise using the layers approach
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     */
    public static void rotateByLayers(int[][] matrix) {
        if (matrix == null || matrix.length <= 1) {
            return;
        }
        
        int n = matrix.length;
        
        // Rotate one layer at a time, from outer to inner
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            
            for (int i = first; i < last; i++) {
                int offset = i - first;
                
                // Save top
                int top = matrix[first][i];
                
                // Left -> Top
                matrix[first][i] = matrix[last - offset][first];
                
                // Bottom -> Left
                matrix[last - offset][first] = matrix[last][last - offset];
                
                // Right -> Bottom
                matrix[last][last - offset] = matrix[i][last];
                
                // Top -> Right
                matrix[i][last] = top;
            }
        }
    }
    
    /**
     * Rotate the image by 90 degrees clockwise using an extra matrix
     * This is not in-place, but shown for completeness
     * Time Complexity: O(n²)
     * Space Complexity: O(n²)
     */
    public static int[][] rotateWithExtraSpace(int[][] matrix) {
        if (matrix == null) {
            return null;
        }
        
        int n = matrix.length;
        int[][] rotated = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][n - 1 - i] = matrix[i][j];
            }
        }
        
        return rotated;
    }
    
    /**
     * Rotate the image by 90 degrees counter-clockwise
     * Shown for completeness
     * Time Complexity: O(n²)
     * Space Complexity: O(1)
     */
    public static void rotateCounterClockwise(int[][] matrix) {
        if (matrix == null || matrix.length <= 1) {
            return;
        }
        
        int n = matrix.length;
        
        // Transpose
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        
        // Reverse columns (not rows, unlike clockwise rotation)
        for (int j = 0; j < n / 2; j++) {
            for (int i = 0; i < n; i++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }
    
    // Helper method to print a matrix
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }
    
    public static void main(String[] args) {
        int[][][] testCases = {
            {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
            },
            {
                {5, 1, 9, 11},
                {2, 4, 8, 10},
                {13, 3, 6, 7},
                {15, 14, 12, 16}
            },
            {
                {1}
            },
            {
                {1, 2},
                {3, 4}
            }
        };
        
        for (int[][] matrix : testCases) {
            System.out.println("Original Matrix:");
            printMatrix(matrix);
            
            // Clone the matrix for each rotation method
            int[][] matrix1 = new int[matrix.length][matrix.length];
            int[][] matrix2 = new int[matrix.length][matrix.length];
            
            for (int i = 0; i < matrix.length; i++) {
                matrix1[i] = matrix[i].clone();
                matrix2[i] = matrix[i].clone();
            }
            
            System.out.println("\nAfter rotation (Transpose and Reverse):");
            rotate(matrix1);
            printMatrix(matrix1);
            
            System.out.println("\nAfter rotation (Layer by Layer):");
            rotateByLayers(matrix2);
            printMatrix(matrix2);
            
            System.out.println("\nRotation with extra space:");
            int[][] rotated = rotateWithExtraSpace(matrix);
            printMatrix(rotated);
            
            System.out.println("\nCounter-clockwise rotation:");
            int[][] counter = new int[matrix.length][matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                counter[i] = matrix[i].clone();
            }
            rotateCounterClockwise(counter);
            printMatrix(counter);
            
            System.out.println("\n------------------------\n");
        }
    }
}