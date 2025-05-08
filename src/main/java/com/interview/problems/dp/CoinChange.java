package com.interview.problems.dp;

import java.util.*;

/**
 * Problem 3: Coin Change Problems
 * 
 * This class provides solutions to various coin change problems:
 * 1. Minimum number of coins needed to make a given amount
 * 2. Number of ways to make change for a given amount
 * 3. Coin change with limited supply of each coin
 * 
 * Time Complexity: O(amount * n) where n is the number of coin denominations
 * Space Complexity: O(amount) for the DP array
 */
public class CoinChange {
    
    /**
     * Finds the minimum number of coins needed to make the given amount
     * 
     * @param coins array of available coin denominations
     * @param amount the target amount
     * @return minimum number of coins or -1 if it's not possible to make the amount
     */
    public static int minCoins(int[] coins, int amount) {
        if (coins == null || amount < 0) {
            return -1;
        }
        
        if (amount == 0) {
            return 0;
        }
        
        // DP array: dp[i] = min coins needed to make amount i
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // Initialize with a value larger than any possible answer
        dp[0] = 0;
        
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        
        return dp[amount] > amount ? -1 : dp[amount];
    }
    
    /**
     * Finds the minimum coins and the actual coins used
     * 
     * @param coins array of available coin denominations
     * @param amount the target amount
     * @return a Result object containing the count and list of coins used, or null if not possible
     */
    public static Result minCoinsWithCoins(int[] coins, int amount) {
        if (coins == null || amount < 0) {
            return null;
        }
        
        if (amount == 0) {
            return new Result(0, new ArrayList<>());
        }
        
        // DP array: dp[i] = min coins needed to make amount i
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        
        // To track which coin was used for each amount
        int[] coinUsed = new int[amount + 1];
        
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                int coin = coins[j];
                if (coin <= i && dp[i - coin] + 1 < dp[i]) {
                    dp[i] = dp[i - coin] + 1;
                    coinUsed[i] = coin;
                }
            }
        }
        
        if (dp[amount] > amount) {
            return null; // Not possible to make the amount
        }
        
        // Backtrack to find the coins used
        List<Integer> coinsUsed = new ArrayList<>();
        int remaining = amount;
        
        while (remaining > 0) {
            coinsUsed.add(coinUsed[remaining]);
            remaining -= coinUsed[remaining];
        }
        
        return new Result(dp[amount], coinsUsed);
    }
    
    /**
     * Counts the number of different ways to make the given amount
     * Order doesn't matter (combinations)
     * 
     * @param coins array of available coin denominations
     * @param amount the target amount
     * @return number of ways to make the amount
     */
    public static int countWays(int[] coins, int amount) {
        if (coins == null || amount < 0) {
            return 0;
        }
        
        // DP array: dp[i] = number of ways to make amount i
        int[] dp = new int[amount + 1];
        dp[0] = 1; // Base case: 1 way to make amount 0 (use no coins)
        
        // For each coin, update the dp array
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i - coin];
            }
        }
        
        return dp[amount];
    }
    
    /**
     * Counts the number of different ways to make the given amount
     * Order matters (permutations)
     * 
     * @param coins array of available coin denominations
     * @param amount the target amount
     * @return number of ways to make the amount
     */
    public static int countWaysOrderMatters(int[] coins, int amount) {
        if (coins == null || amount < 0) {
            return 0;
        }
        
        // DP array: dp[i] = number of ways to make amount i
        int[] dp = new int[amount + 1];
        dp[0] = 1; // Base case: 1 way to make amount 0 (use no coins)
        
        // For each amount, try each coin
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] += dp[i - coin];
                }
            }
        }
        
        return dp[amount];
    }
    
    /**
     * Coin change with limited supply of each coin
     * 
     * @param coins array of available coin denominations
     * @param counts array of counts for each coin denomination
     * @param amount the target amount
     * @return minimum number of coins or -1 if it's not possible
     */
    public static int minCoinsWithLimitedSupply(int[] coins, int[] counts, int amount) {
        if (coins == null || counts == null || coins.length != counts.length || amount < 0) {
            return -1;
        }
        
        if (amount == 0) {
            return 0;
        }
        
        int n = coins.length;
        
        // DP array: dp[i] = min coins needed to make amount i
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        
        // For each coin type
        for (int i = 0; i < n; i++) {
            int coin = coins[i];
            int count = counts[i];
            
            // For each amount
            for (int j = amount; j >= coin; j--) {
                // Try using 1 to count coins of this type
                for (int k = 1; k <= count && k * coin <= j; k++) {
                    dp[j] = Math.min(dp[j], dp[j - k * coin] + k);
                }
            }
        }
        
        return dp[amount] > amount ? -1 : dp[amount];
    }
    
    /**
     * Alternative implementation for limited supply using the binary representation technique
     * More efficient for large counts
     */
    public static int minCoinsWithLimitedSupplyBinary(int[] coins, int[] counts, int amount) {
        if (coins == null || counts == null || coins.length != counts.length || amount < 0) {
            return -1;
        }
        
        if (amount == 0) {
            return 0;
        }
        
        // Convert to unbounded knapsack by creating a new array of coins
        List<Integer> newCoins = new ArrayList<>();
        
        for (int i = 0; i < coins.length; i++) {
            int coin = coins[i];
            int count = counts[i];
            
            // Binary representation: 1, 2, 4, 8, ... to represent any number efficiently
            int power = 1;
            while (count > 0) {
                int use = Math.min(power, count);
                newCoins.add(coin * use);
                count -= use;
                power *= 2;
            }
        }
        
        // Convert to array
        int[] coinsArray = new int[newCoins.size()];
        for (int i = 0; i < newCoins.size(); i++) {
            coinsArray[i] = newCoins.get(i);
        }
        
        // Use the regular minCoins function
        return minCoins(coinsArray, amount);
    }
    
    /**
     * Result class to hold the coin change result
     */
    public static class Result {
        private final int count;
        private final List<Integer> coins;
        
        public Result(int count, List<Integer> coins) {
            this.count = count;
            this.coins = coins;
        }
        
        public int getCount() {
            return count;
        }
        
        public List<Integer> getCoins() {
            return coins;
        }
    }
    
    /**
     * Application: ATM Cash Dispenser
     * 
     * Given a requested amount and available notes in the ATM,
     * determine if and how the amount can be dispensed.
     */
    public static class ATMCashDispenser {
        private final int[] denominations;
        private final int[] counts;
        
        public ATMCashDispenser(int[] denominations, int[] counts) {
            this.denominations = denominations;
            this.counts = counts;
        }
        
        public DispenserResult dispense(int amount) {
            if (amount <= 0) {
                return null;
            }
            
            // DP array: dp[i] = min notes needed to make amount i
            int[] dp = new int[amount + 1];
            Arrays.fill(dp, amount + 1);
            dp[0] = 0;
            
            // To track which denomination was used for each amount
            int[] denomUsed = new int[amount + 1];
            
            // Track remaining counts of each denomination
            int[] remaining = Arrays.copyOf(counts, counts.length);
            
            // Process larger denominations first for ATM scenario
            Integer[] indices = new Integer[denominations.length];
            for (int i = 0; i < indices.length; i++) {
                indices[i] = i;
            }
            
            // Sort indices based on denomination values (descending)
            Arrays.sort(indices, (a, b) -> Integer.compare(denominations[b], denominations[a]));
            
            // For each amount
            for (int i = 1; i <= amount; i++) {
                // For each denomination (from largest to smallest)
                for (int idx : indices) {
                    int denom = denominations[idx];
                    
                    if (denom <= i && remaining[idx] > 0 && dp[i - denom] + 1 < dp[i]) {
                        dp[i] = dp[i - denom] + 1;
                        denomUsed[i] = denom;
                    }
                }
            }
            
            if (dp[amount] > amount) {
                return null; // Not possible to dispense the amount
            }
            
            // Backtrack to find the notes used
            Map<Integer, Integer> notesDispensed = new HashMap<>();
            int remainingAmount = amount;
            
            while (remainingAmount > 0) {
                int denom = denomUsed[remainingAmount];
                notesDispensed.put(denom, notesDispensed.getOrDefault(denom, 0) + 1);
                
                // Update remaining counts
                for (int i = 0; i < denominations.length; i++) {
                    if (denominations[i] == denom) {
                        remaining[i]--;
                        break;
                    }
                }
                
                remainingAmount -= denom;
            }
            
            return new DispenserResult(dp[amount], notesDispensed);
        }
        
        public static class DispenserResult {
            private final int totalNotes;
            private final Map<Integer, Integer> notesDispensed;
            
            public DispenserResult(int totalNotes, Map<Integer, Integer> notesDispensed) {
                this.totalNotes = totalNotes;
                this.notesDispensed = notesDispensed;
            }
            
            public int getTotalNotes() {
                return totalNotes;
            }
            
            public Map<Integer, Integer> getNotesDispensed() {
                return notesDispensed;
            }
        }
    }
    
    public static void main(String[] args) {
        // Example 1: Minimum coins
        int[] coins = {1, 2, 5};
        int amount = 11;
        
        System.out.println("=== MINIMUM COINS ===");
        System.out.println("Coins: " + Arrays.toString(coins));
        System.out.println("Amount: " + amount);
        
        int minCount = minCoins(coins, amount);
        System.out.println("Minimum coins needed: " + minCount);
        
        Result result = minCoinsWithCoins(coins, amount);
        if (result != null) {
            System.out.println("Coins used: " + result.getCoins());
        }
        
        // Example 2: Count ways (combinations)
        System.out.println("\n=== COUNT WAYS (COMBINATIONS) ===");
        
        int ways = countWays(coins, amount);
        System.out.println("Number of ways to make " + amount + ": " + ways);
        
        // Different example for counting ways
        int[] coins2 = {2, 3, 5, 10};
        int amount2 = 15;
        
        System.out.println("\nCoins: " + Arrays.toString(coins2));
        System.out.println("Amount: " + amount2);
        
        int ways2 = countWays(coins2, amount2);
        System.out.println("Number of ways to make " + amount2 + ": " + ways2);
        
        // Example 3: Count ways (permutations)
        System.out.println("\n=== COUNT WAYS (PERMUTATIONS) ===");
        
        int permutations = countWaysOrderMatters(coins, amount);
        System.out.println("Number of ordered ways to make " + amount + ": " + permutations);
        
        // Example 4: Limited supply
        int[] counts = {5, 3, 2}; // 5 coins of 1, 3 coins of 2, 2 coins of 5
        
        System.out.println("\n=== LIMITED SUPPLY ===");
        System.out.println("Coins: " + Arrays.toString(coins));
        System.out.println("Counts: " + Arrays.toString(counts));
        
        int minWithLimited = minCoinsWithLimitedSupply(coins, counts, amount);
        System.out.println("Minimum coins with limited supply: " + minWithLimited);
        
        int minWithBinary = minCoinsWithLimitedSupplyBinary(coins, counts, amount);
        System.out.println("Minimum coins with binary approach: " + minWithBinary);
        
        // Example 5: ATM Cash Dispenser
        int[] notes = {100, 50, 20, 10, 5, 1};
        int[] available = {5, 10, 15, 20, 25, 100};
        
        ATMCashDispenser atm = new ATMCashDispenser(notes, available);
        
        System.out.println("\n=== ATM CASH DISPENSER ===");
        System.out.println("Available notes: " + Arrays.toString(notes));
        System.out.println("Counts: " + Arrays.toString(available));
        
        int withdrawAmount = 185;
        System.out.println("Withdraw amount: $" + withdrawAmount);
        
        ATMCashDispenser.DispenserResult dispenserResult = atm.dispense(withdrawAmount);
        
        if (dispenserResult != null) {
            System.out.println("Total notes dispensed: " + dispenserResult.getTotalNotes());
            System.out.println("Notes breakdown:");
            
            for (Map.Entry<Integer, Integer> entry : dispenserResult.getNotesDispensed().entrySet()) {
                System.out.println("  $" + entry.getKey() + " notes: " + entry.getValue());
            }
        } else {
            System.out.println("Unable to dispense the requested amount");
        }
        
        // Test impossible amount
        int impossibleAmount = 123456;
        System.out.println("\nWithdraw amount: $" + impossibleAmount);
        
        ATMCashDispenser.DispenserResult impossibleResult = atm.dispense(impossibleAmount);
        
        if (impossibleResult == null) {
            System.out.println("Unable to dispense the requested amount (as expected)");
        } else {
            System.out.println("Unexpectedly able to dispense the amount!");
        }
    }
}