package com.interview.problems.strings;

/**
 * Problem 5: String Compression
 * 
 * This class compresses a string by replacing consecutive repeated characters 
 * with the character followed by the count of repetitions.
 * For example, "aabcccccaaa" would become "a2b1c5a3".
 * If the compressed string is not smaller than the original, return the original string.
 * 
 * Time Complexity: O(n) where n is the length of the string
 * Space Complexity: O(n) for the compressed string
 */
public class StringCompression {
    
    /**
     * Compresses a string using StringBuilder
     * Returns the original string if the compressed version is not smaller
     */
    public static String compress(String input) {
        if (input == null || input.length() <= 2) {
            return input;
        }
        
        StringBuilder compressed = new StringBuilder();
        char currentChar = input.charAt(0);
        int count = 1;
        
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == currentChar) {
                // Increment count for consecutive characters
                count++;
            } else {
                // Append current character and its count
                compressed.append(currentChar).append(count);
                // Reset for the new character
                currentChar = input.charAt(i);
                count = 1;
            }
        }
        
        // Append the last character and its count
        compressed.append(currentChar).append(count);
        
        // Return original string if compressed is not smaller
        return compressed.length() < input.length() ? compressed.toString() : input;
    }
    
    /**
     * Alternative approach that calculates compressed length first
     * More efficient as it avoids creating a compressed string if it's not needed
     */
    public static String compressEfficient(String input) {
        if (input == null || input.length() <= 2) {
            return input;
        }
        
        // First, calculate the length of the compressed string
        int compressedLength = 0;
        char currentChar = input.charAt(0);
        int count = 1;
        
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == currentChar) {
                count++;
            } else {
                compressedLength += 1 + String.valueOf(count).length();
                currentChar = input.charAt(i);
                count = 1;
            }
        }
        
        // Add length for the last character and its count
        compressedLength += 1 + String.valueOf(count).length();
        
        // If compressed string is not smaller, return original
        if (compressedLength >= input.length()) {
            return input;
        }
        
        // Otherwise, create and return the compressed string
        StringBuilder compressed = new StringBuilder(compressedLength);
        currentChar = input.charAt(0);
        count = 1;
        
        for (int i = 1; i < input.length(); i++) {
            if (input.charAt(i) == currentChar) {
                count++;
            } else {
                compressed.append(currentChar).append(count);
                currentChar = input.charAt(i);
                count = 1;
            }
        }
        
        compressed.append(currentChar).append(count);
        return compressed.toString();
    }
    
    /**
     * In-place version for character arrays (often asked in interviews)
     * Time Complexity: O(n)
     * Space Complexity: O(1) - we're modifying the input array in-place
     */
    public static int compressInPlace(char[] chars) {
        if (chars == null || chars.length <= 1) {
            return chars == null ? 0 : chars.length;
        }
        
        int writeIdx = 0; // Index to write the compressed string
        int count = 1;    // Count of consecutive characters
        
        for (int readIdx = 1; readIdx <= chars.length; readIdx++) {
            // If we're still seeing the same character or we've reached the end
            if (readIdx < chars.length && chars[readIdx] == chars[readIdx - 1]) {
                count++;
            } else {
                // Write the character
                chars[writeIdx++] = chars[readIdx - 1];
                
                // Write the count if it's more than 1
                if (count > 1) {
                    String countStr = String.valueOf(count);
                    for (char c : countStr.toCharArray()) {
                        chars[writeIdx++] = c;
                    }
                }
                
                // Reset count for the next character
                count = 1;
            }
        }
        
        return writeIdx; // Length of the compressed array
    }
    
    public static void main(String[] args) {
        String[] testCases = {
            "aabcccccaaa",  // Should become "a2b1c5a3"
            "abcdef",       // Should remain "abcdef" (compressed would be longer)
            "aabb",         // Should remain "aabb" (compressed would be same length)
            "aaa",          // Should become "a3"
            "a",            // Should remain "a"
            ""              // Should remain ""
        };
        
        for (String test : testCases) {
            System.out.println("Original: \"" + test + "\"");
            System.out.println("Compressed: \"" + compress(test) + "\"");
            System.out.println("Compressed (Efficient): \"" + compressEfficient(test) + "\"");
            
            // Test in-place compression
            if (!test.isEmpty()) {
                char[] chars = test.toCharArray();
                int newLength = compressInPlace(chars);
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < newLength; i++) {
                    result.append(chars[i]);
                }
                System.out.println("In-place: \"" + result.toString() + "\"");
            }
            
            System.out.println();
        }
    }
}