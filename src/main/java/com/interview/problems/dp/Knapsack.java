package com.interview.problems.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Problem 1: 0/1 Knapsack Problem
 * 
 * Given weights and values of n items, put these items in a knapsack of capacity W
 * to get the maximum total value in the knapsack.
 * 
 * Time Complexity: O(n*W) where n is the number of items and W is the capacity
 * Space Complexity: O(n*W) for the 2D table approach, O(W) for the optimized approach
 */
public class Knapsack {
    
    /**
     * Solves the 0/1 Knapsack problem using dynamic programming with a 2D table
     * 
     * @param weights array of item weights
     * @param values array of item values
     * @param capacity knapsack capacity
     * @return maximum value that can be put in the knapsack
     */
    public static int knapsack(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null || weights.length != values.length || capacity < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        int n = weights.length;
        
        // DP table: dp[i][w] = max value with first i items and capacity w
        int[][] dp = new int[n + 1][capacity + 1];
        
        // Fill the dp table
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (i == 0 || w == 0) {
                    // Base case: no items or no capacity
                    dp[i][w] = 0;
                } else if (weights[i - 1] <= w) {
                    // Item i can fit in the knapsack
                    // Choose max of: (1) including item i, (2) excluding item i
                    dp[i][w] = Math.max(
                            values[i - 1] + dp[i - 1][w - weights[i - 1]], // Include item
                            dp[i - 1][w]                                   // Exclude item
                    );
                } else {
                    // Item i cannot fit in the knapsack
                    dp[i][w] = dp[i - 1][w]; // Exclude item
                }
            }
        }
        
        return dp[n][capacity];
    }
    
    /**
     * Solves the 0/1 Knapsack problem and returns the selected items
     * 
     * @param weights array of item weights
     * @param values array of item values
     * @param capacity knapsack capacity
     * @return a Result object containing the maximum value and selected items
     */
    public static Result knapsackWithItems(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null || weights.length != values.length || capacity < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        int n = weights.length;
        
        // DP table: dp[i][w] = max value with first i items and capacity w
        int[][] dp = new int[n + 1][capacity + 1];
        
        // Fill the dp table
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                } else if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(
                            values[i - 1] + dp[i - 1][w - weights[i - 1]],
                            dp[i - 1][w]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        
        // Backtrack to find the selected items
        List<Integer> selectedItems = new ArrayList<>();
        int w = capacity;
        
        for (int i = n; i > 0; i--) {
            // If the value comes from including this item
            if (dp[i][w] != dp[i - 1][w]) {
                selectedItems.add(i - 1); // Add this item (0-indexed)
                w -= weights[i - 1];      // Reduce the remaining capacity
            }
        }
        
        return new Result(dp[n][capacity], selectedItems);
    }
    
    /**
     * Solves the 0/1 Knapsack problem using dynamic programming with optimized space (1D array)
     * 
     * @param weights array of item weights
     * @param values array of item values
     * @param capacity knapsack capacity
     * @return maximum value that can be put in the knapsack
     */
    public static int knapsackOptimized(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null || weights.length != values.length || capacity < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        int n = weights.length;
        
        // DP array: dp[w] = max value with capacity w
        int[] dp = new int[capacity + 1];
        
        // Fill the dp array
        for (int i = 0; i < n; i++) {
            // Process in reverse to avoid using items multiple times
            for (int w = capacity; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], values[i] + dp[w - weights[i]]);
            }
        }
        
        return dp[capacity];
    }
    
    /**
     * Solves the Fractional Knapsack problem using the greedy approach
     * Unlike 0/1 Knapsack, we can take fractions of items
     * 
     * @param weights array of item weights
     * @param values array of item values
     * @param capacity knapsack capacity
     * @return maximum value that can be put in the knapsack
     */
    public static double fractionalKnapsack(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null || weights.length != values.length || capacity < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        int n = weights.length;
        
        // Create item pairs (value/weight ratio, index)
        double[][] items = new double[n][2];
        for (int i = 0; i < n; i++) {
            items[i][0] = (double) values[i] / weights[i]; // Value per unit weight
            items[i][1] = i;                               // Original index
        }
        
        // Sort items by value/weight ratio in descending order
        Arrays.sort(items, (a, b) -> Double.compare(b[0], a[0]));
        
        double totalValue = 0.0;
        int remainingCapacity = capacity;
        
        for (int i = 0; i < n; i++) {
            int idx = (int) items[i][1];
            
            if (weights[idx] <= remainingCapacity) {
                // Take the whole item
                totalValue += values[idx];
                remainingCapacity -= weights[idx];
            } else {
                // Take a fraction of the item
                double fraction = (double) remainingCapacity / weights[idx];
                totalValue += values[idx] * fraction;
                break; // Knapsack is full
            }
        }
        
        return totalValue;
    }
    
    /**
     * Solves the Unbounded Knapsack problem (items can be used multiple times)
     * 
     * @param weights array of item weights
     * @param values array of item values
     * @param capacity knapsack capacity
     * @return maximum value that can be put in the knapsack
     */
    public static int unboundedKnapsack(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null || weights.length != values.length || capacity < 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        int n = weights.length;
        
        // DP array: dp[w] = max value with capacity w
        int[] dp = new int[capacity + 1];
        
        // Fill the dp array
        for (int w = 0; w <= capacity; w++) {
            for (int i = 0; i < n; i++) {
                if (weights[i] <= w) {
                    dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
                }
            }
        }
        
        return dp[capacity];
    }
    
    /**
     * Class to hold the result of knapsack problem
     */
    public static class Result {
        private final int maxValue;
        private final List<Integer> selectedItems;
        
        public Result(int maxValue, List<Integer> selectedItems) {
            this.maxValue = maxValue;
            this.selectedItems = selectedItems;
        }
        
        public int getMaxValue() {
            return maxValue;
        }
        
        public List<Integer> getSelectedItems() {
            return selectedItems;
        }
    }
    
    /**
     * Application: Budget Allocation
     * 
     * Given a budget and a list of projects with costs and values,
     * select projects to maximize total value within the budget.
     */
    public static class BudgetAllocation {
        private final int budget;
        private final List<Project> projects;
        
        public BudgetAllocation(int budget, List<Project> projects) {
            this.budget = budget;
            this.projects = projects;
        }
        
        public List<Project> selectProjects() {
            int n = projects.size();
            
            // Convert projects to arrays
            int[] costs = new int[n];
            int[] values = new int[n];
            
            for (int i = 0; i < n; i++) {
                costs[i] = projects.get(i).getCost();
                values[i] = projects.get(i).getValue();
            }
            
            // Solve knapsack
            Result result = knapsackWithItems(costs, values, budget);
            
            // Convert selected indices to projects
            List<Project> selectedProjects = new ArrayList<>();
            for (int index : result.getSelectedItems()) {
                selectedProjects.add(projects.get(index));
            }
            
            return selectedProjects;
        }
        
        public static class Project {
            private final String name;
            private final int cost;
            private final int value;
            
            public Project(String name, int cost, int value) {
                this.name = name;
                this.cost = cost;
                this.value = value;
            }
            
            public String getName() {
                return name;
            }
            
            public int getCost() {
                return cost;
            }
            
            public int getValue() {
                return value;
            }
            
            @Override
            public String toString() {
                return name + " (Cost: " + cost + ", Value: " + value + ")";
            }
        }
    }
    
    public static void main(String[] args) {
        // Example 1: Basic 0/1 Knapsack
        int[] weights = {2, 3, 4, 5};
        int[] values = {3, 4, 5, 6};
        int capacity = 8;
        
        System.out.println("=== 0/1 KNAPSACK PROBLEM ===");
        System.out.println("Weights: " + Arrays.toString(weights));
        System.out.println("Values: " + Arrays.toString(values));
        System.out.println("Capacity: " + capacity);
        
        int maxValue1 = knapsack(weights, values, capacity);
        System.out.println("Maximum value (2D DP): " + maxValue1);
        
        int maxValue2 = knapsackOptimized(weights, values, capacity);
        System.out.println("Maximum value (1D DP): " + maxValue2);
        
        Result result = knapsackWithItems(weights, values, capacity);
        System.out.println("Maximum value with items: " + result.getMaxValue());
        
        System.out.println("Selected items (0-indexed): " + result.getSelectedItems());
        int totalWeight = 0;
        int totalValue = 0;
        for (int i : result.getSelectedItems()) {
            totalWeight += weights[i];
            totalValue += values[i];
            System.out.println("  Item " + i + ": Weight = " + weights[i] + ", Value = " + values[i]);
        }
        System.out.println("Total weight: " + totalWeight);
        System.out.println("Total value: " + totalValue);
        
        // Example 2: Fractional Knapsack
        System.out.println("\n=== FRACTIONAL KNAPSACK ===");
        double fractionalValue = fractionalKnapsack(weights, values, capacity);
        System.out.println("Maximum value (Fractional): " + fractionalValue);
        
        // Example 3: Unbounded Knapsack
        System.out.println("\n=== UNBOUNDED KNAPSACK ===");
        int[] rodLengths = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] rodPrices = {1, 5, 8, 9, 10, 17, 17, 20};
        int rodLength = 8;
        
        System.out.println("Lengths: " + Arrays.toString(rodLengths));
        System.out.println("Prices: " + Arrays.toString(rodPrices));
        System.out.println("Rod length: " + rodLength);
        
        int maxRodValue = unboundedKnapsack(rodLengths, rodPrices, rodLength);
        System.out.println("Maximum value (Unbounded): " + maxRodValue);
        
        // Example 4: Budget Allocation Application
        System.out.println("\n=== BUDGET ALLOCATION ===");
        List<BudgetAllocation.Project> projects = new ArrayList<>();
        projects.add(new BudgetAllocation.Project("Project A", 3, 6));
        projects.add(new BudgetAllocation.Project("Project B", 2, 3));
        projects.add(new BudgetAllocation.Project("Project C", 4, 5));
        projects.add(new BudgetAllocation.Project("Project D", 5, 8));
        projects.add(new BudgetAllocation.Project("Project E", 6, 7));
        
        int budget = 10;
        
        BudgetAllocation budgetAllocation = new BudgetAllocation(budget, projects);
        System.out.println("Budget: " + budget);
        
        System.out.println("Available projects:");
        for (BudgetAllocation.Project project : projects) {
            System.out.println("  " + project);
        }
        
        List<BudgetAllocation.Project> selectedProjects = budgetAllocation.selectProjects();
        
        System.out.println("Selected projects:");
        int totalCost = 0;
        int totalProjectValue = 0;
        for (BudgetAllocation.Project project : selectedProjects) {
            System.out.println("  " + project);
            totalCost += project.getCost();
            totalProjectValue += project.getValue();
        }
        
        System.out.println("Total cost: " + totalCost);
        System.out.println("Total value: " + totalProjectValue);
    }
}