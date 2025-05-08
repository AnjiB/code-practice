package com.interview.problems.basics.strings;

/**
 * Basic String operations and manipulations
 */
public class StringBasics {
    
    // Program 1: Check if a string is empty or null
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
    // Program 2: Get the length of a string
    public static int getLength(String str) {
        return str == null ? 0 : str.length();
    }
    
    // Program 3: Convert string to uppercase
    public static String toUpperCase(String str) {
        return str == null ? null : str.toUpperCase();
    }
    
    // Program 4: Convert string to lowercase
    public static String toLowerCase(String str) {
        return str == null ? null : str.toLowerCase();
    }
    
    // Program 5: Trim whitespace from a string
    public static String trimWhitespace(String str) {
        return str == null ? null : str.trim();
    }
    
    // Program 6: Check if a string starts with a specific prefix
    public static boolean startsWith(String str, String prefix) {
        return str != null && prefix != null && str.startsWith(prefix);
    }
    
    // Program 7: Check if a string ends with a specific suffix
    public static boolean endsWith(String str, String suffix) {
        return str != null && suffix != null && str.endsWith(suffix);
    }
    
    // Program 8: Check if a string contains a specific substring
    public static boolean contains(String str, String substring) {
        return str != null && substring != null && str.contains(substring);
    }
    
    // Program 9: Find the index of a substring in a string
    public static int indexOf(String str, String substring) {
        return str != null && substring != null ? str.indexOf(substring) : -1;
    }
    
    // Program 10: Replace all occurrences of a substring
    public static String replaceAll(String str, String target, String replacement) {
        return str != null && target != null && replacement != null 
               ? str.replace(target, replacement) 
               : str;
    }
    
    // Program 11: Split a string by delimiter
    public static String[] split(String str, String delimiter) {
        return str != null && delimiter != null ? str.split(delimiter) : new String[0];
    }
    
    // Program 12: Join an array of strings with a delimiter
    public static String join(String[] strings, String delimiter) {
        if (strings == null || delimiter == null) {
            return "";
        }
        return String.join(delimiter, strings);
    }
    
    // Program 13: Concatenate two strings
    public static String concatenate(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return null;
        }
        if (str1 == null) {
            return str2;
        }
        if (str2 == null) {
            return str1;
        }
        return str1 + str2;
    }
    
    // Program 14: Extract a substring from a string
    public static String substring(String str, int start, int end) {
        if (str == null || start < 0 || end > str.length() || start > end) {
            return null;
        }
        return str.substring(start, end);
    }
    
    // Program 15: Check if two strings are equal ignoring case
    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }
    
    // Program 16: Convert a string to character array
    public static char[] toCharArray(String str) {
        return str == null ? new char[0] : str.toCharArray();
    }
    
    // Program 17: Convert a character array to string
    public static String fromCharArray(char[] chars) {
        return chars == null ? "" : new String(chars);
    }
    
    // Program 18: Check if a string is a palindrome
    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }
        
        int left = 0;
        int right = str.length() - 1;
        
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    // Program 19: Count occurrences of a character in a string
    public static int countChar(String str, char target) {
        if (str == null) {
            return 0;
        }
        
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == target) {
                count++;
            }
        }
        
        return count;
    }
    
    // Program 20: Reverse a string
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        
        return new StringBuilder(str).reverse().toString();
    }
    
    public static void main(String[] args) {
        System.out.println("===== String Basics Examples =====");
        
        String test = "  Hello, World!  ";
        System.out.println("Test string: \"" + test + "\"");
        
        System.out.println("Is null or empty: " + isNullOrEmpty(test));
        System.out.println("Length: " + getLength(test));
        System.out.println("Uppercase: " + toUpperCase(test));
        System.out.println("Lowercase: " + toLowerCase(test));
        System.out.println("Trimmed: \"" + trimWhitespace(test) + "\"");
        
        System.out.println("Starts with 'Hello': " + startsWith(trimWhitespace(test), "Hello"));
        System.out.println("Ends with '!': " + endsWith(trimWhitespace(test), "!"));
        System.out.println("Contains 'World': " + contains(test, "World"));
        System.out.println("Index of 'World': " + indexOf(test, "World"));
        
        System.out.println("Replace 'World' with 'Java': " + replaceAll(test, "World", "Java"));
        
        String[] parts = split(trimWhitespace(test), ", ");
        System.out.println("Split by ', ': " + java.util.Arrays.toString(parts));
        
        System.out.println("Join with '-': " + join(parts, "-"));
        System.out.println("Concatenate parts: " + concatenate(parts[0], parts[1]));
        
        System.out.println("Substring (7, 12): " + substring(trimWhitespace(test), 7, 12));
        System.out.println("Equals 'hello, world!' ignoring case: " + 
                          equalsIgnoreCase(trimWhitespace(test), "hello, world!"));
        
        System.out.println("To char array: " + java.util.Arrays.toString(toCharArray(test)));
        System.out.println("From char array: " + fromCharArray(new char[]{'J', 'a', 'v', 'a'}));
        
        String palindrome = "racecar";
        System.out.println("Is '" + palindrome + "' palindrome: " + isPalindrome(palindrome));
        
        System.out.println("Count of 'l' in '" + test + "': " + countChar(test, 'l'));
        System.out.println("Reversed: " + reverse(test));
    }
}