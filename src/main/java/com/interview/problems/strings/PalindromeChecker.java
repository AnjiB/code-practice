package com.interview.problems.strings;

/**
 * Problem 2: Palindrome Checker
 * 
 * This class provides methods to check if a string is a palindrome.
 * A palindrome reads the same backward as forward (ignoring case, spaces, and punctuation).
 * 
 * Time Complexity: O(n) where n is the length of the string
 * Space Complexity: O(1) for the two-pointer approach, O(n) for the clean and reverse approach
 */
public class PalindromeChecker {
    
    /**
     * Checks if a string is a palindrome using the two-pointer technique
     * More efficient as it uses constant extra space
     */
    public static boolean isPalindromeUsingTwoPointers(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }
        
        // Convert to lowercase and prepare for character-by-character comparison
        input = input.toLowerCase();
        int left = 0;
        int right = input.length() - 1;
        
        while (left < right) {
            // Skip non-alphanumeric characters from left
            while (left < right && !Character.isLetterOrDigit(input.charAt(left))) {
                left++;
            }
            
            // Skip non-alphanumeric characters from right
            while (left < right && !Character.isLetterOrDigit(input.charAt(right))) {
                right--;
            }
            
            // Compare characters
            if (input.charAt(left) != input.charAt(right)) {
                return false;
            }
            
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Checks if a string is a palindrome by cleaning it and comparing with its reverse
     * Less efficient but more straightforward
     */
    public static boolean isPalindromeUsingReverse(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }
        
        // Clean the string - remove non-alphanumeric characters and convert to lowercase
        String cleanedInput = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        
        // Reverse the cleaned string
        String reversedInput = new StringBuilder(cleanedInput).reverse().toString();
        
        // Compare original cleaned string with its reverse
        return cleanedInput.equals(reversedInput);
    }
    
    public static void main(String[] args) {
        String[] testCases = {
            "A man, a plan, a canal: Panama",
            "race a car",
            "No 'x' in Nixon",
            "Was it a car or a cat I saw?",
            "hello"
        };
        
        for (String test : testCases) {
            boolean isPalindrome1 = isPalindromeUsingTwoPointers(test);
            boolean isPalindrome2 = isPalindromeUsingReverse(test);
            
            System.out.println("Input: \"" + test + "\"");
            System.out.println("Two Pointers Approach: " + (isPalindrome1 ? "Palindrome" : "Not Palindrome"));
            System.out.println("Clean and Reverse Approach: " + (isPalindrome2 ? "Palindrome" : "Not Palindrome"));
            System.out.println();
        }
    }
}