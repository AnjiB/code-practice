package com.interview.problems.dp;

import java.util.Arrays;

/**
 * Problem 2: Longest Common Subsequence
 * 
 * Given two strings, find the length of their longest common subsequence (LCS).
 * A subsequence is a sequence that appears in the same relative order, but not necessarily contiguous.
 * 
 * Time Complexity: O(m*n) where m and n are the lengths of the strings
 * Space Complexity: O(m*n) for the standard approach, O(min(m,n)) for the optimized approach
 */
public class LongestCommonSubsequence {
    
    /**
     * Finds the length of the longest common subsequence of two strings
     * 
     * @param text1 the first string
     * @param text2 the second string
     * @return the length of the longest common subsequence
     */
    public static int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0;
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // DP table: dp[i][j] = LCS of text1[0...i-1] and text2[0...j-1]
        int[][] dp = new int[m + 1][n + 1];
        
        // Fill the dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    // Characters match, add 1 to the diagonal
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // Characters don't match, take the maximum of left or top
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }
    
    /**
     * Finds the longest common subsequence (not just the length)
     * 
     * @param text1 the first string
     * @param text2 the second string
     * @return the longest common subsequence
     */
    public static String getLongestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return "";
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // DP table: dp[i][j] = LCS of text1[0...i-1] and text2[0...j-1]
        int[][] dp = new int[m + 1][n + 1];
        
        // Fill the dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Backtrack to find the LCS
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        
        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                // Characters match, add to LCS
                lcs.append(text1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // Move up
                i--;
            } else {
                // Move left
                j--;
            }
        }
        
        // Reverse the result (we built it backwards)
        return lcs.reverse().toString();
    }
    
    /**
     * Space-optimized version of the LCS algorithm
     * 
     * @param text1 the first string
     * @param text2 the second string
     * @return the length of the longest common subsequence
     */
    public static int longestCommonSubsequenceOptimized(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0;
        }
        
        // Ensure text1 is the shorter string to minimize space
        if (text1.length() > text2.length()) {
            String temp = text1;
            text1 = text2;
            text2 = temp;
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // Only need two rows: current and previous
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];
        
        // Fill the dp array
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    curr[j] = prev[j - 1] + 1;
                } else {
                    curr[j] = Math.max(prev[j], curr[j - 1]);
                }
            }
            
            // Swap arrays for next iteration
            int[] temp = prev;
            prev = curr;
            curr = temp;
            
            // Reset current row
            Arrays.fill(curr, 0);
        }
        
        return prev[n];
    }
    
    /**
     * Finds the length of the longest common substring (contiguous subsequence)
     * 
     * @param text1 the first string
     * @param text2 the second string
     * @return the length of the longest common substring
     */
    public static int longestCommonSubstring(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0;
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // DP table: dp[i][j] = length of LCS ending at text1[i-1] and text2[j-1]
        int[][] dp = new int[m + 1][n + 1];
        int maxLength = 0;
        
        // Fill the dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    maxLength = Math.max(maxLength, dp[i][j]);
                }
                // If characters don't match, the substring ends
                // So we don't need an else clause (defaults to 0)
            }
        }
        
        return maxLength;
    }
    
    /**
     * Gets the longest common substring (not just the length)
     * 
     * @param text1 the first string
     * @param text2 the second string
     * @return the longest common substring
     */
    public static String getLongestCommonSubstring(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return "";
        }
        
        int m = text1.length();
        int n = text2.length();
        
        // DP table: dp[i][j] = length of LCS ending at text1[i-1] and text2[j-1]
        int[][] dp = new int[m + 1][n + 1];
        
        int maxLength = 0;
        int endIndex = 0;
        
        // Fill the dp table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    
                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        endIndex = i - 1;
                    }
                }
            }
        }
        
        // Extract the substring
        return text1.substring(endIndex - maxLength + 1, endIndex + 1);
    }
    
    /**
     * Finds the shortest common supersequence (SCS) length of two strings
     * SCS is the shortest string that has both strings as subsequences
     * 
     * @param text1 the first string
     * @param text2 the second string
     * @return the length of the shortest common supersequence
     */
    public static int shortestCommonSupersequence(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return 0;
        }
        
        int lcsLength = longestCommonSubsequence(text1, text2);
        return text1.length() + text2.length() - lcsLength;
    }
    
    /**
     * Finds the shortest common supersequence of two strings
     * 
     * @param text1 the first string
     * @param text2 the second string
     * @return the shortest common supersequence
     */
    public static String getShortestCommonSupersequence(String text1, String text2) {
        if (text1 == null || text2 == null) {
            return "";
        }
        if (text1.isEmpty()) return text2;
        if (text2.isEmpty()) return text1;
        
        int m = text1.length();
        int n = text2.length();
        
        // DP table for LCS
        int[][] dp = new int[m + 1][n + 1];
        
        // Fill the dp table for LCS
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // Build the SCS
        StringBuilder scs = new StringBuilder();
        int i = m, j = n;
        
        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                // Common character - add once
                scs.append(text1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // Character from text1
                scs.append(text1.charAt(i - 1));
                i--;
            } else {
                // Character from text2
                scs.append(text2.charAt(j - 1));
                j--;
            }
        }
        
        // Add remaining characters
        while (i > 0) {
            scs.append(text1.charAt(i - 1));
            i--;
        }
        
        while (j > 0) {
            scs.append(text2.charAt(j - 1));
            j--;
        }
        
        // Reverse the result
        return scs.reverse().toString();
    }
    
    /**
     * Checks if a string is a subsequence of another string
     * 
     * @param subsequence the potential subsequence
     * @param text the main text
     * @return true if subsequence is a subsequence of text, false otherwise
     */
    public static boolean isSubsequence(String subsequence, String text) {
        if (subsequence == null || text == null) {
            return false;
        }
        
        if (subsequence.isEmpty()) {
            return true;
        }
        
        int i = 0, j = 0;
        
        while (i < subsequence.length() && j < text.length()) {
            if (subsequence.charAt(i) == text.charAt(j)) {
                i++;
            }
            j++;
        }
        
        return i == subsequence.length();
    }
    
    /**
     * Application: DNA Sequence Analysis
     * 
     * Given two DNA sequences, find their longest common subsequence.
     * Can be used to identify common genetic patterns.
     */
    public static class DNAAnalysis {
        public static String findCommonGeneticPattern(String dna1, String dna2) {
            return getLongestCommonSubsequence(dna1, dna2);
        }
        
        public static boolean isValidDNA(String sequence) {
            if (sequence == null || sequence.isEmpty()) {
                return false;
            }
            
            for (char c : sequence.toCharArray()) {
                if (c != 'A' && c != 'C' && c != 'G' && c != 'T') {
                    return false;
                }
            }
            
            return true;
        }
    }
    
    /**
     * Application: Differential File Analysis
     * 
     * Given two versions of a file, find their differences by identifying
     * common parts and changes.
     */
    public static class DiffAnalysis {
        public static DiffResult getDiff(String text1, String text2) {
            String lcs = getLongestCommonSubsequence(text1, text2);
            
            // Mark changes in each text
            String diff1 = markDiff(text1, lcs);
            String diff2 = markDiff(text2, lcs);
            
            return new DiffResult(diff1, diff2, lcs);
        }
        
        private static String markDiff(String text, String lcs) {
            if (lcs.isEmpty()) {
                return "[-" + text + "]";
            }
            
            StringBuilder result = new StringBuilder();
            int i = 0, j = 0;
            
            while (i < text.length()) {
                if (j < lcs.length() && text.charAt(i) == lcs.charAt(j)) {
                    // Character is in common
                    result.append(text.charAt(i));
                    i++;
                    j++;
                } else {
                    // Character is different
                    result.append("[-").append(text.charAt(i)).append("]");
                    i++;
                }
            }
            
            return result.toString();
        }
        
        public static class DiffResult {
            private final String diff1;
            private final String diff2;
            private final String common;
            
            public DiffResult(String diff1, String diff2, String common) {
                this.diff1 = diff1;
                this.diff2 = diff2;
                this.common = common;
            }
            
            public String getDiff1() {
                return diff1;
            }
            
            public String getDiff2() {
                return diff2;
            }
            
            public String getCommon() {
                return common;
            }
        }
    }
    
    public static void main(String[] args) {
        // Example 1: Basic LCS
        String text1 = "abcde";
        String text2 = "ace";
        
        System.out.println("=== LONGEST COMMON SUBSEQUENCE ===");
        System.out.println("Text 1: " + text1);
        System.out.println("Text 2: " + text2);
        
        int lcsLength = longestCommonSubsequence(text1, text2);
        System.out.println("LCS Length: " + lcsLength);
        
        String lcs = getLongestCommonSubsequence(text1, text2);
        System.out.println("LCS: " + lcs);
        
        int optimizedLcsLength = longestCommonSubsequenceOptimized(text1, text2);
        System.out.println("LCS Length (Optimized): " + optimizedLcsLength);
        
        // Example 2: Longest Common Substring
        String str1 = "ABABC";
        String str2 = "BABCA";
        
        System.out.println("\n=== LONGEST COMMON SUBSTRING ===");
        System.out.println("String 1: " + str1);
        System.out.println("String 2: " + str2);
        
        int lcSubstringLength = longestCommonSubstring(str1, str2);
        System.out.println("LCS Length: " + lcSubstringLength);
        
        String lcSubstring = getLongestCommonSubstring(str1, str2);
        System.out.println("LCS: " + lcSubstring);
        
        // Example 3: Shortest Common Supersequence
        System.out.println("\n=== SHORTEST COMMON SUPERSEQUENCE ===");
        
        int scsLength = shortestCommonSupersequence(text1, text2);
        System.out.println("SCS Length: " + scsLength);
        
        String scs = getShortestCommonSupersequence(text1, text2);
        System.out.println("SCS: " + scs);
        
        // Example 4: Subsequence Check
        String mainText = "abcdefghijklmnopqrstuvwxyz";
        String[] tests = {"abe", "acefk", "xyz", ""};
        
        System.out.println("\n=== SUBSEQUENCE CHECK ===");
        System.out.println("Main Text: " + mainText);
        
        for (String test : tests) {
            boolean isSubseq = isSubsequence(test, mainText);
            System.out.println("Is \"" + test + "\" a subsequence? " + isSubseq);
        }
        
        // Example 5: DNA Analysis
        String dna1 = "ACGTACGT";
        String dna2 = "GTACGTAC";
        
        System.out.println("\n=== DNA ANALYSIS ===");
        System.out.println("DNA 1: " + dna1);
        System.out.println("DNA 2: " + dna2);
        
        boolean isValid1 = DNAAnalysis.isValidDNA(dna1);
        boolean isValid2 = DNAAnalysis.isValidDNA(dna2);
        
        System.out.println("Is DNA 1 valid? " + isValid1);
        System.out.println("Is DNA 2 valid? " + isValid2);
        
        if (isValid1 && isValid2) {
            String commonPattern = DNAAnalysis.findCommonGeneticPattern(dna1, dna2);
            System.out.println("Common Genetic Pattern: " + commonPattern);
        }
        
        // Example 6: Diff Analysis
        String version1 = "ABCDEFG";
        String version2 = "ABDCEFK";
        
        System.out.println("\n=== DIFF ANALYSIS ===");
        System.out.println("Version 1: " + version1);
        System.out.println("Version 2: " + version2);
        
        DiffAnalysis.DiffResult diff = DiffAnalysis.getDiff(version1, version2);
        
        System.out.println("Common: " + diff.getCommon());
        System.out.println("Diff 1: " + diff.getDiff1());
        System.out.println("Diff 2: " + diff.getDiff2());
    }
}