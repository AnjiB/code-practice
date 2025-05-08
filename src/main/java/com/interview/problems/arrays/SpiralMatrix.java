package com.interview.problems.arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem 8: Spiral Matrix
 * 
 * Given an m x n matrix, return all elements of the matrix in spiral order.
 * 
 * Time Complexity: O(m*n) where m and n are the dimensions of the matrix
 * Space Complexity: O(m*n) for the result list
 */
public class SpiralMatrix {
    
    /**
     * Return the elements of the matrix in spiral order using the layer-by-layer approach
     * Time Complexity: O(m*n)
     * Space Complexity: O(m*n) for the result
     */
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return result;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        int top = 0;
        int bottom = rows - 1;
        int left = 0;
        int right = cols - 1;
        
        while (top <= bottom && left <= right) {
            // Traverse right
            for (int j = left; j <= right; j++) {
                result.add(matrix[top][j]);
            }
            top++;
            
            // Traverse down
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;
            
            // Traverse left (if there are still rows to traverse)
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    result.add(matrix[bottom][j]);
                }
                bottom--;
            }
            
            // Traverse up (if there are still columns to traverse)
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }
        }
        
        return result;
    }
    
    /**
     * Alternative approach using direction simulation
     * Time Complexity: O(m*n)
     * Space Complexity: O(m*n) for the result
     */
    public static List<Integer> spiralOrderSimulation(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return result;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int total = rows * cols;
        
        // Directions: right, down, left, up
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int directionIndex = 0;
        
        // Starting position
        int row = 0;
        int col = 0;
        
        // Keep track of visited cells
        boolean[][] visited = new boolean[rows][cols];
        
        for (int i = 0; i < total; i++) {
            result.add(matrix[row][col]);
            visited[row][col] = true;
            
            // Calculate next position
            int nextRow = row + directions[directionIndex][0];
            int nextCol = col + directions[directionIndex][1];
            
            // Check if the next position is valid and not visited
            if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols || visited[nextRow][nextCol]) {
                // Change direction
                directionIndex = (directionIndex + 1) % 4;
                
                // Recalculate next position with new direction
                nextRow = row + directions[directionIndex][0];
                nextCol = col + directions[directionIndex][1];
            }
            
            // Move to the next position
            row = nextRow;
            col = nextCol;
        }
        
        return result;
    }
    
    /**
     * Generate a spiral matrix - the inverse of the original problem
     * Given n, generate an n x n matrix filled with elements from 1 to n² in spiral order
     * Time Complexity: O(n²)
     * Space Complexity: O(n²) for the result matrix
     */
    public static int[][] generateSpiralMatrix(int n) {
        if (n <= 0) {
            return new int[0][0];
        }
        
        int[][] matrix = new int[n][n];
        int num = 1; // Start with 1
        
        int top = 0;
        int bottom = n - 1;
        int left = 0;
        int right = n - 1;
        
        while (top <= bottom && left <= right) {
            // Traverse right
            for (int j = left; j <= right; j++) {
                matrix[top][j] = num++;
            }
            top++;
            
            // Traverse down
            for (int i = top; i <= bottom; i++) {
                matrix[i][right] = num++;
            }
            right--;
            
            // Traverse left
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    matrix[bottom][j] = num++;
                }
                bottom--;
            }
            
            // Traverse up
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    matrix[i][left] = num++;
                }
                left++;
            }
        }
        
        return matrix;
    }
    
    /**
     * Helper method to print a matrix
     */
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.printf("%4d", num);
            }
            System.out.println();
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
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
            },
            {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
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
            System.out.println("Matrix:");
            printMatrix(matrix);
            
            List<Integer> spiral1 = spiralOrder(matrix);
            System.out.println("Spiral Order (Layer-by-Layer): " + spiral1);
            
            List<Integer> spiral2 = spiralOrderSimulation(matrix);
            System.out.println("Spiral Order (Simulation): " + spiral2);
            
            System.out.println();
        }
        
        // Test generating spiral matrices
        int[] sizes = {3, 4, 5};
        for (int size : sizes) {
            System.out.println("Generated " + size + "x" + size + " Spiral Matrix:");
            int[][] generated = generateSpiralMatrix(size);
            printMatrix(generated);
            System.out.println();
        }
    }
}