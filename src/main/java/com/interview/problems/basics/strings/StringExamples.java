package com.interview.problems.basics.strings;

import java.util.*;

/**
 * More examples of common string operations and problem solutions
 */
public class StringExamples {
    
    // Program 1: Count the number of vowels in a string
    public static int countVowels(String str) {
        if (str == null) {
            return 0;
        }
        
        int count = 0;
        String vowels = "aeiouAEIOU";
        
        for (char c : str.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                count++;
            }
        }
        
        return count;
    }
    
    // Program 2: Count the number of consonants in a string
    public static int countConsonants(String str) {
        if (str == null) {
            return 0;
        }
        
        int count = 0;
        str = str.toLowerCase();
        
        for (char c : str.toCharArray()) {
            if (c >= 'a' && c <= 'z' && "aeiou".indexOf(c) == -1) {
                count++;
            }
        }
        
        return count;
    }
    
    // Program 3: Check if a string contains only digits
    public static boolean containsOnlyDigits(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        
        return true;
    }
    
    // Program 4: Check if a string contains only alphabets
    public static boolean containsOnlyAlphabets(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        
        return true;
    }
    
    // Program 5: Remove all spaces from a string
    public static String removeSpaces(String str) {
        if (str == null) {
            return null;
        }
        
        return str.replaceAll("\\s", "");
    }
    
    // Program 6: Count the occurrences of a substring in a string
    public static int countSubstring(String str, String sub) {
        if (str == null || sub == null || sub.isEmpty()) {
            return 0;
        }
        
        int count = 0;
        int index = 0;
        
        while ((index = str.indexOf(sub, index)) != -1) {
            count++;
            index += sub.length();
        }
        
        return count;
    }
    
    // Program 7: Convert first letter of each word to uppercase
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;
        
        for (char c : str.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
                result.append(c);
            } else if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    // Program 8: Find the first non-repeated character in a string
    public static char firstNonRepeatedChar(String str) {
        if (str == null || str.isEmpty()) {
            return '\0';
        }
        
        Map<Character, Integer> charCount = new LinkedHashMap<>();
        
        // Count occurrences of each character
        for (char c : str.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        
        // Find the first non-repeated character
        for (Map.Entry<Character, Integer> entry : charCount.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        
        return '\0'; // No non-repeated character found
    }
    
    // Program 9: Check if two strings are anagrams
    public static boolean areAnagrams(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }
        
        // Remove spaces and convert to lowercase
        str1 = str1.replaceAll("\\s", "").toLowerCase();
        str2 = str2.replaceAll("\\s", "").toLowerCase();
        
        if (str1.length() != str2.length()) {
            return false;
        }
        
        // Sort both strings and compare
        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        
        return Arrays.equals(arr1, arr2);
    }
    
    // Program 10: Check if a string is a valid palindrome ignoring non-alphanumeric characters
    public static boolean isValidPalindrome(String str) {
        if (str == null) {
            return false;
        }
        
        int left = 0;
        int right = str.length() - 1;
        
        while (left < right) {
            // Skip non-alphanumeric characters from left
            while (left < right && !Character.isLetterOrDigit(str.charAt(left))) {
                left++;
            }
            
            // Skip non-alphanumeric characters from right
            while (left < right && !Character.isLetterOrDigit(str.charAt(right))) {
                right--;
            }
            
            // Compare characters (ignoring case)
            if (Character.toLowerCase(str.charAt(left)) != Character.toLowerCase(str.charAt(right))) {
                return false;
            }
            
            left++;
            right--;
        }
        
        return true;
    }
    
    // Program 11: Find the longest substring without repeating characters
    public static String longestSubstringWithoutRepeating(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        
        int start = 0;
        int maxLength = 0;
        int currentStart = 0;
        Map<Character, Integer> charMap = new HashMap<>();
        
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            
            if (charMap.containsKey(c) && charMap.get(c) >= currentStart) {
                // If character is repeated, update current start
                currentStart = charMap.get(c) + 1;
            } else if (i - currentStart + 1 > maxLength) {
                // Update the maximum substring
                maxLength = i - currentStart + 1;
                start = currentStart;
            }
            
            // Update character position
            charMap.put(c, i);
        }
        
        return str.substring(start, start + maxLength);
    }
    
    // Program 12: Count words in a string
    public static int countWords(String str) {
        if (str == null || str.trim().isEmpty()) {
            return 0;
        }
        
        return str.trim().split("\\s+").length;
    }
    
    // Program 13: Reverse words in a string
    public static String reverseWords(String str) {
        if (str == null) {
            return null;
        }
        
        String[] words = str.trim().split("\\s+");
        StringBuilder reversed = new StringBuilder();
        
        for (int i = words.length - 1; i >= 0; i--) {
            reversed.append(words[i]);
            if (i > 0) {
                reversed.append(" ");
            }
        }
        
        return reversed.toString();
    }
    
    // Program 14: Check if a string is a rotation of another string
    public static boolean isRotation(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }
        
        // Concatenate str1 with itself and check if str2 is a substring
        String concatenated = str1 + str1;
        return concatenated.contains(str2);
    }
    
    // Program 15: Convert string to integer (atoi implementation)
    public static int stringToInt(String str) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid input string");
        }
        
        str = str.trim();
        int index = 0;
        int sign = 1;
        int result = 0;
        
        // Handle sign
        if (str.charAt(index) == '+' || str.charAt(index) == '-') {
            sign = str.charAt(index) == '-' ? -1 : 1;
            index++;
        }
        
        // Process digits
        while (index < str.length() && Character.isDigit(str.charAt(index))) {
            int digit = str.charAt(index) - '0';
            
            // Check for overflow
            if (result > Integer.MAX_VALUE / 10 || 
                (result == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            
            result = result * 10 + digit;
            index++;
        }
        
        return result * sign;
    }
    
    // Program 16: Find all permutations of a string
    public static List<String> findPermutations(String str) {
        List<String> result = new ArrayList<>();
        
        if (str == null) {
            return result;
        }
        
        if (str.length() <= 1) {
            result.add(str);
            return result;
        }
        
        char firstChar = str.charAt(0);
        String restOfString = str.substring(1);
        
        List<String> subPermutations = findPermutations(restOfString);
        
        for (String subPerm : subPermutations) {
            for (int i = 0; i <= subPerm.length(); i++) {
                String newPermutation = subPerm.substring(0, i) + firstChar + subPerm.substring(i);
                result.add(newPermutation);
            }
        }
        
        return result;
    }
    
    // Program 17: Longest common prefix among an array of strings
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        
        // Take first string as the initial prefix
        String prefix = strs[0];
        
        // Compare with other strings and reduce prefix as needed
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                
                // If prefix becomes empty, there is no common prefix
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        
        return prefix;
    }
    
    // Program 18: Check if brackets are balanced in a string
    public static boolean areBracketsBalanced(String str) {
        if (str == null) {
            return false;
        }
        
        Stack<Character> stack = new Stack<>();
        
        for (char c : str.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                // Push opening brackets onto stack
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                // Check closing brackets
                if (stack.isEmpty()) {
                    return false;
                }
                
                char top = stack.pop();
                
                if ((c == ')' && top != '(') ||
                    (c == ']' && top != '[') ||
                    (c == '}' && top != '{')) {
                    return false;
                }
            }
        }
        
        // Stack should be empty if all brackets are balanced
        return stack.isEmpty();
    }
    
    // Program 19: Encode a string by run-length encoding
    public static String runLengthEncode(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        
        StringBuilder encoded = new StringBuilder();
        int count = 1;
        
        for (int i = 1; i <= str.length(); i++) {
            // If current character matches previous one, increment count
            if (i < str.length() && str.charAt(i) == str.charAt(i - 1)) {
                count++;
            } else {
                // Append the previous character and its count
                encoded.append(str.charAt(i - 1));
                encoded.append(count);
                count = 1;
            }
        }
        
        return encoded.toString();
    }
    
    // Program 20: Decode a run-length encoded string
    public static String runLengthDecode(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        
        StringBuilder decoded = new StringBuilder();
        
        for (int i = 0; i < str.length(); i += 2) {
            char c = str.charAt(i);
            int count = Character.getNumericValue(str.charAt(i + 1));
            
            for (int j = 0; j < count; j++) {
                decoded.append(c);
            }
        }
        
        return decoded.toString();
    }
    
    public static void main(String[] args) {
        System.out.println("===== String Examples =====");
        
        String test = "Hello, World! This is a test string.";
        System.out.println("Test string: \"" + test + "\"");
        
        System.out.println("Count of vowels: " + countVowels(test));
        System.out.println("Count of consonants: " + countConsonants(test));
        
        System.out.println("Contains only digits: " + containsOnlyDigits("12345"));
        System.out.println("Contains only alphabets: " + containsOnlyAlphabets("abc"));
        
        System.out.println("After removing spaces: \"" + removeSpaces(test) + "\"");
        System.out.println("Count of 'is' substring: " + countSubstring(test, "is"));
        
        System.out.println("Capitalized first letter: \"" + capitalizeFirstLetter(test.toLowerCase()) + "\"");
        
        String repeatedChars = "swiss cheese";
        System.out.println("First non-repeated character in '" + repeatedChars + "': " + firstNonRepeatedChar(repeatedChars));
        
        String str1 = "listen";
        String str2 = "silent";
        System.out.println("Are '" + str1 + "' and '" + str2 + "' anagrams? " + areAnagrams(str1, str2));
        
        String palindrome = "A man, a plan, a canal: Panama";
        System.out.println("Is '" + palindrome + "' a valid palindrome? " + isValidPalindrome(palindrome));
        
        String repeatingChars = "abcabcbb";
        System.out.println("Longest substring without repeating characters in '" + repeatingChars + "': " + 
                          longestSubstringWithoutRepeating(repeatingChars));
        
        System.out.println("Word count in test string: " + countWords(test));
        System.out.println("Reversed words: \"" + reverseWords(test) + "\"");
        
        String original = "rotation";
        String rotated = "tationro";
        System.out.println("Is '" + rotated + "' a rotation of '" + original + "'? " + isRotation(original, rotated));
        
        String number = "  -42";
        System.out.println("String '" + number + "' to integer: " + stringToInt(number));
        
        String shortStr = "abc";
        System.out.println("Permutations of '" + shortStr + "': " + findPermutations(shortStr));
        
        String[] prefixArray = {"flower", "flow", "flight"};
        System.out.println("Longest common prefix: " + longestCommonPrefix(prefixArray));
        
        String brackets = "{[()]}";
        System.out.println("Are brackets in '" + brackets + "' balanced? " + areBracketsBalanced(brackets));
        
        String toEncode = "AAABBBCCDAA";
        String encoded = runLengthEncode(toEncode);
        System.out.println("Run-length encoded '" + toEncode + "': " + encoded);
        System.out.println("Decoded: " + runLengthDecode(encoded));
    }
}