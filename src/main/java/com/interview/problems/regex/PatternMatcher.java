package com.interview.problems.regex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Problem 2: Pattern Matcher
 * 
 * Implement functions to match patterns in strings and perform pattern-based replacements using regex.
 * The program demonstrates various regex patterns and their uses.
 * 
 * Topics covered:
 * 1. Basic pattern matching
 * 2. Capturing groups
 * 3. Named groups
 * 4. Backreferences
 * 5. Lookahead and lookbehind assertions
 * 6. Pattern replacement
 * 
 * Time Complexity: O(n) for simple patterns, can be worse for complex patterns with backtracking
 * Space Complexity: O(n) for storing matches and replacements
 */
public class PatternMatcher {
    
    /**
     * Checks if a string matches a given pattern
     * 
     * @param text the text to check
     * @param pattern the regex pattern to match
     * @return true if the text matches the pattern, false otherwise
     */
    public static boolean matches(String text, String pattern) {
        if (text == null || pattern == null) {
            return false;
        }
        
        return Pattern.matches(pattern, text);
    }
    
    /**
     * Finds all occurrences of a pattern in a text
     * 
     * @param text the text to search in
     * @param pattern the regex pattern to find
     * @return a list of matched strings
     */
    public static List<String> findAll(String text, String pattern) {
        if (text == null || pattern == null) {
            return new ArrayList<>();
        }
        
        List<String> matches = new ArrayList<>();
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        
        while (m.find()) {
            matches.add(m.group());
        }
        
        return matches;
    }
    
    /**
     * Extracts capturing groups from pattern matches
     * 
     * @param text the text to search in
     * @param pattern the regex pattern with capturing groups
     * @return a list of arrays containing the groups for each match
     */
    public static List<String[]> extractGroups(String text, String pattern) {
        if (text == null || pattern == null) {
            return new ArrayList<>();
        }
        
        List<String[]> results = new ArrayList<>();
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        
        while (m.find()) {
            String[] groups = new String[m.groupCount() + 1];
            for (int i = 0; i <= m.groupCount(); i++) {
                groups[i] = m.group(i);
            }
            results.add(groups);
        }
        
        return results;
    }
    
    /**
     * Extracts named groups from pattern matches
     * 
     * @param text the text to search in
     * @param pattern the regex pattern with named groups
     * @param groupNames the names of the groups to extract
     * @return a list of maps containing the named groups for each match
     */
    public static List<Map<String, String>> extractNamedGroups(String text, String pattern, String[] groupNames) {
        if (text == null || pattern == null || groupNames == null) {
            return new ArrayList<>();
        }
        
        List<Map<String, String>> results = new ArrayList<>();
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        
        while (m.find()) {
            Map<String, String> groupMap = new HashMap<>();
            for (String name : groupNames) {
                try {
                    groupMap.put(name, m.group(name));
                } catch (IllegalArgumentException e) {
                    // Group doesn't exist
                    groupMap.put(name, null);
                }
            }
            results.add(groupMap);
        }
        
        return results;
    }
    
    /**
     * Replaces all occurrences of a pattern with a replacement string
     * 
     * @param text the text to perform replacements in
     * @param pattern the regex pattern to replace
     * @param replacement the replacement string
     * @return the text with replacements
     */
    public static String replaceAll(String text, String pattern, String replacement) {
        if (text == null || pattern == null || replacement == null) {
            return text;
        }
        
        return text.replaceAll(pattern, replacement);
    }
    
    /**
     * Replaces matches with a dynamically generated replacement
     * 
     * @param text the text to perform replacements in
     * @param pattern the regex pattern to replace
     * @return the text with replacements
     */
    public static String replaceWithFunction(String text, String pattern) {
        if (text == null || pattern == null) {
            return text;
        }
        
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        StringBuffer result = new StringBuffer();
        
        while (m.find()) {
            String match = m.group();
            // Example function: convert matched text to uppercase
            String replacement = match.toUpperCase();
            m.appendReplacement(result, replacement);
        }
        
        m.appendTail(result);
        return result.toString();
    }
    
    /**
     * Demonstrates various regex patterns and techniques
     */
    public static void demonstratePatterns() {
        System.out.println("=== PATTERN DEMONSTRATIONS ===\n");
        
        // Basic patterns
        System.out.println("1. Basic Patterns:");
        checkPattern("123-456-7890", "\\d{3}-\\d{3}-\\d{4}", "Phone number");
        checkPattern("2023-05-15", "\\d{4}-\\d{2}-\\d{2}", "Date in YYYY-MM-DD format");
        checkPattern("john.doe@example.com", "[\\w.]+@[\\w.]+\\.\\w+", "Email address");
        
        // Character classes
        System.out.println("\n2. Character Classes:");
        String text = "The price is $19.99 and the code is ABC-123.";
        System.out.println("   Text: " + text);
        System.out.println("   Digits: " + findAll(text, "\\d+"));
        System.out.println("   Letters: " + findAll(text, "[a-zA-Z]+"));
        System.out.println("   Alphanumeric: " + findAll(text, "\\w+"));
        System.out.println("   Non-whitespace: " + findAll(text, "\\S+"));
        
        // Quantifiers
        System.out.println("\n3. Quantifiers:");
        String html = "<div><p>First paragraph</p><p>Second paragraph</p></div>";
        System.out.println("   Text: " + html);
        System.out.println("   Greedy: " + findAll(html, "<p>.*</p>"));
        System.out.println("   Reluctant: " + findAll(html, "<p>.*?</p>"));
        
        // Capturing groups
        System.out.println("\n4. Capturing Groups:");
        String dateText = "Date: 2023-05-15, Updated: 2023-06-30";
        System.out.println("   Text: " + dateText);
        List<String[]> dates = extractGroups(dateText, "(\\d{4})-(\\d{2})-(\\d{2})");
        for (String[] date : dates) {
            System.out.println("   Full match: " + date[0]);
            System.out.println("   Year: " + date[1] + ", Month: " + date[2] + ", Day: " + date[3]);
        }
        
        // Named groups
        System.out.println("\n5. Named Groups:");
        String namedPattern = "(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})";
        List<Map<String, String>> namedDates = extractNamedGroups(dateText, namedPattern, 
                new String[] {"year", "month", "day"});
        
        for (Map<String, String> date : namedDates) {
            System.out.println("   Year: " + date.get("year") + 
                               ", Month: " + date.get("month") + 
                               ", Day: " + date.get("day"));
        }
        
        // Backreferences
        System.out.println("\n6. Backreferences:");
        String[] words = {"radar", "level", "hello", "madam", "refer"};
        for (String word : words) {
            boolean isPalindrome = matches(word, "^(\\w)(\\w*)(\\1)$");
            System.out.println("   " + word + " is" + (isPalindrome ? "" : " not") + " a palindrome");
        }
        
        String duplicateWords = "The the quick brown fox fox jumps over the lazy dog";
        System.out.println("   Text: " + duplicateWords);
        System.out.println("   With duplicates: " + duplicateWords);
        System.out.println("   Without duplicates: " + 
                replaceAll(duplicateWords, "\\b(\\w+)\\s+\\1\\b", "$1"));
        
        // Lookahead and lookbehind
        System.out.println("\n7. Lookahead and Lookbehind:");
        String passwordText = "Passwords: Abc123!, Weak1, StrongP@ss22";
        System.out.println("   Text: " + passwordText);
        
        // Strong password: at least 8 chars, contains digits, letters, and special chars
        String strongPattern = "\\b(?=\\w{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])\\S+\\b";
        System.out.println("   Strong passwords: " + findAll(passwordText, strongPattern));
        
        // Negative lookbehind: find words not preceded by 'the'
        String lookbehindText = "The car and a bike. Look at the tree and an apple.";
        System.out.println("   Text: " + lookbehindText);
        System.out.println("   Words not preceded by 'the': " + 
                findAll(lookbehindText, "(?<!the\\s)\\b\\w+\\b"));
        
        // Replacements
        System.out.println("\n8. Replacements:");
        String htmlText = "<p>This is <b>bold</b> and <i>italic</i> text.</p>";
        System.out.println("   Original HTML: " + htmlText);
        
        // Remove all HTML tags
        System.out.println("   Without tags: " + replaceAll(htmlText, "<[^>]+>", ""));
        
        // Replace <b> with <strong> and <i> with <em>
        String modernHtml = htmlText.replaceAll("<b>(.*?)</b>", "<strong>$1</strong>")
                                   .replaceAll("<i>(.*?)</i>", "<em>$1</em>");
        System.out.println("   Modernized tags: " + modernHtml);
        
        // Format phone numbers
        String phoneText = "Contact us at 1234567890 or (123) 456-7890 or 123-456-7890";
        System.out.println("   Original phones: " + phoneText);
        
        // Standardize all phone formats to XXX-XXX-XXXX
        String standardizedPhones = phoneText
                .replaceAll("\\(?(\\d{3})\\)?[\\s-]*(\\d{3})[\\s-]*(\\d{4})", "$1-$2-$3");
        System.out.println("   Standardized phones: " + standardizedPhones);
        
        // Dynamic replacements
        String mixedCase = "This Is Mixed Case Text";
        System.out.println("   Original: " + mixedCase);
        System.out.println("   Uppercase words: " + replaceWithFunction(mixedCase, "\\b\\w+\\b"));
    }
    
    /**
     * Helper method to check and print if a string matches a pattern
     */
    private static void checkPattern(String text, String pattern, String description) {
        boolean matches = matches(text, pattern);
        System.out.println("   " + description + " (" + text + "): " + 
                (matches ? "Matches" : "Doesn't match"));
    }
    
    public static void main(String[] args) {
        demonstratePatterns();
    }
}