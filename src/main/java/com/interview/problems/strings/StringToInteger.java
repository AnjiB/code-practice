package com.interview.problems.strings;

/**
 * Problem 7: String to Integer (atoi)
 * 
 * Implement the myAtoi(string s) function, which converts a string to a 32-bit signed integer.
 * 
 * The algorithm for myAtoi(string s) is as follows:
 * 1. Read in and ignore any leading whitespace.
 * 2. Check if the next character (if not already at the end of the string) is '-' or '+'.
 *    Read this character in if it is either. This determines if the final result is negative or positive.
 *    Assume the result is positive if neither is present.
 * 3. Read in next the characters until the next non-digit character or the end of the input is reached.
 *    The rest of the string is ignored.
 * 4. Convert these digits into an integer. If no digits were read, then the integer is 0.
 * 5. If the integer is out of the 32-bit signed integer range [-2^31, 2^31 - 1], then clamp the integer
 *    so that it remains in the range.
 * 
 * Time Complexity: O(n) where n is the length of the string
 * Space Complexity: O(1)
 */
public class StringToInteger {
    
    /**
     * Implementation of the atoi function
     */
    public static int myAtoi(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        // Step 1: Ignore leading whitespaces
        int i = 0;
        while (i < s.length() && s.charAt(i) == ' ') {
            i++;
        }
        
        // Check if the string is just whitespace
        if (i >= s.length()) {
            return 0;
        }
        
        // Step 2: Check for sign
        boolean isNegative = false;
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            isNegative = s.charAt(i) == '-';
            i++;
        }
        
        // Step 3 & 4: Convert digits to integer
        int result = 0;
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            
            // Step 5: Check for overflow
            // Check if result will overflow after this operation
            if (result > Integer.MAX_VALUE / 10 || 
                (result == Integer.MAX_VALUE / 10 && digit > 7)) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            
            result = result * 10 + digit;
            i++;
        }
        
        // Apply sign
        return isNegative ? -result : result;
    }
    
    /**
     * Alternative implementation using exceptions to handle overflow
     */
    public static int myAtoiWithException(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        
        s = s.trim(); // Remove leading and trailing whitespaces
        
        if (s.isEmpty()) {
            return 0;
        }
        
        // Check for sign
        boolean isNegative = false;
        int i = 0;
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            isNegative = s.charAt(0) == '-';
            i = 1;
        }
        
        // Convert digits
        int result = 0;
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            
            try {
                // This will throw an exception if it would overflow
                result = Math.multiplyExact(result, 10);
                result = Math.addExact(result, digit);
            } catch (ArithmeticException e) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            
            i++;
        }
        
        return isNegative ? -result : result;
    }
    
    public static void main(String[] args) {
        String[] testCases = {
            "42",               // Expected: 42
            "   -42",           // Expected: -42
            "4193 with words",  // Expected: 4193
            "words and 987",    // Expected: 0
            "-91283472332",     // Expected: -2147483648 (INT_MIN)
            "2147483648",       // Expected: 2147483647 (INT_MAX)
            "+1",               // Expected: 1
            "+-12",             // Expected: 0
            ""                  // Expected: 0
        };
        
        for (String test : testCases) {
            System.out.println("Input: \"" + test + "\"");
            System.out.println("Manual Overflow Check: " + myAtoi(test));
            System.out.println("Exception Handling: " + myAtoiWithException(test));
            System.out.println();
        }
    }
}