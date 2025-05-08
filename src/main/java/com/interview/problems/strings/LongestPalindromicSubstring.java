package com.interview.problems.strings;

/**
 * Problem 3: Longest Palindromic Substring
 * 
 * This class finds the longest palindromic substring in a given string.
 * A palindrome reads the same backward as forward.
 * 
 * Time Complexity: O(n²) where n is the length of the string
 * Space Complexity: O(1) as we only store the result substring
 */
public class LongestPalindromicSubstring {
    
    /**
     * Main function to find the longest palindromic substring
     */
    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        
        int start = 0;  // Start index of the longest palindromic substring
        int maxLength = 1;  // Length of the longest palindromic substring
        
        for (int i = 0; i < s.length(); i++) {
            // For odd length palindromes (like "aba")
            int len1 = expandAroundCenter(s, i, i);
            
            // For even length palindromes (like "abba")
            int len2 = expandAroundCenter(s, i, i + 1);
            
            // Get the maximum of the two palindromic lengths
            int currentMaxLength = Math.max(len1, len2);
            
            // Update the longest palindrome if current one is longer
            if (currentMaxLength > maxLength) {
                maxLength = currentMaxLength;
                // Calculate the starting position based on center and length
                start = i - (currentMaxLength - 1) / 2;
            }
        }
        
        return s.substring(start, start + maxLength);
    }
    
    /**
     * Helper function that expands around the center of a potential palindrome
     * Returns the length of the palindrome
     */
    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;   // Expand to the left
            right++;  // Expand to the right
        }
        
        // Return the length of the palindrome
        // Note: left+1 and right-1 are the actual valid palindrome boundaries after the while loop
        return right - left - 1;
    }
    
    /**
     * Alternative approach using dynamic programming
     * Time Complexity: O(n²)
     * Space Complexity: O(n²) for the DP table
     */
    public static String longestPalindromeDP(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }
        
        int n = s.length();
        boolean[][] dp = new boolean[n][n]; // dp[i][j] = true if s[i..j] is palindrome
        
        int start = 0;  // Start index of the longest palindromic substring
        int maxLength = 1;  // Length of the longest palindromic substring
        
        // All substrings of length 1 are palindromes
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }
        
        // Check for substrings of length 2
        for (int i = 0; i < n - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                start = i;
                maxLength = 2;
            }
        }
        
        // Check for lengths greater than 2
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1; // Ending index of current substring
                
                // Check if s[i+1..j-1] is palindrome and s[i] equals s[j]
                if (dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = true;
                    
                    if (len > maxLength) {
                        start = i;
                        maxLength = len;
                    }
                }
            }
        }
        
        return s.substring(start, start + maxLength);
    }
    
    public static void main(String[] args) {
        String[] testCases = {
            "babad",    // Expected: "bab" or "aba"
            "cbbd",     // Expected: "bb"
            "a",        // Expected: "a"
            "ac",       // Expected: "a" or "c"
            "racecar",  // Expected: "racecar"
            "aacabdkacaa" // Expected: "aca"
        };
        
        for (String test : testCases) {
            System.out.println("Input: \"" + test + "\"");
            System.out.println("Expand from Center: \"" + longestPalindrome(test) + "\"");
            System.out.println("Dynamic Programming: \"" + longestPalindromeDP(test) + "\"");
            System.out.println();
        }
    }
}