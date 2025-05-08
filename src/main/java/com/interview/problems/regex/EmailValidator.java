package com.interview.problems.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Problem 1: Email Validator
 * 
 * Implement a program that validates email addresses using regular expressions.
 * The program should check if an email address follows a standard format and extract
 * components like username, domain, and top-level domain.
 * 
 * A valid email address should match the following criteria:
 * 1. Username can contain letters, numbers, dots, underscores, percent signs, plus or minus signs
 * 2. Domain name can contain letters, numbers, dots, and hyphens
 * 3. Top-level domain should be at least 2 characters
 * 
 * Time Complexity: O(n) where n is the length of the email string
 * Space Complexity: O(1) for the validation, O(n) for extracted components
 */
public class EmailValidator {
    
    // Basic email validation pattern
    private static final String EMAIL_PATTERN = 
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    
    // Pattern to extract components
    private static final String EMAIL_COMPONENTS_PATTERN = 
            "^([A-Za-z0-9._%+-]+)@([A-Za-z0-9.-]+)\\.([A-Za-z]{2,})$";
    
    // More comprehensive pattern from RFC 5322
    private static final String EMAIL_RFC5322_PATTERN = 
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    
    // Pattern with length restrictions
    private static final String EMAIL_WITH_LENGTH_PATTERN = 
            "^[A-Za-z0-9._%+-]{1,64}@[A-Za-z0-9.-]{1,255}\\.[A-Za-z]{2,}$";
    
    // Compiled patterns for better performance
    private static final Pattern basicPattern = Pattern.compile(EMAIL_PATTERN);
    private static final Pattern componentsPattern = Pattern.compile(EMAIL_COMPONENTS_PATTERN);
    private static final Pattern rfcPattern = Pattern.compile(EMAIL_RFC5322_PATTERN);
    private static final Pattern lengthPattern = Pattern.compile(EMAIL_WITH_LENGTH_PATTERN);
    
    /**
     * Validates an email address using the basic pattern
     * 
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        
        return basicPattern.matcher(email).matches();
    }
    
    /**
     * Validates an email address using the RFC 5322 pattern
     * 
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmailRFC5322(String email) {
        if (email == null) {
            return false;
        }
        
        return rfcPattern.matcher(email).matches();
    }
    
    /**
     * Validates an email address with length restrictions
     * 
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmailWithLengthCheck(String email) {
        if (email == null) {
            return false;
        }
        
        return lengthPattern.matcher(email).matches();
    }
    
    /**
     * Extracts components (username, domain, TLD) from an email address
     * 
     * @param email the email address to extract components from
     * @return an array of [username, domain, TLD] or null if the email is invalid
     */
    public static String[] extractEmailComponents(String email) {
        if (email == null) {
            return null;
        }
        
        Matcher matcher = componentsPattern.matcher(email);
        
        if (matcher.matches()) {
            return new String[] {
                    matcher.group(1), // username
                    matcher.group(2), // domain
                    matcher.group(3)  // TLD
            };
        }
        
        return null;
    }
    
    /**
     * Finds all valid email addresses in a text
     * 
     * @param text the text to search for email addresses
     * @return a list of valid email addresses found in the text
     */
    public static List<String> findEmailsInText(String text) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<String> emails = new ArrayList<>();
        Matcher matcher = basicPattern.matcher(text);
        
        while (matcher.find()) {
            emails.add(matcher.group());
        }
        
        return emails;
    }
    
    /**
     * Validates email by breaking it down into steps:
     * 1. Check if it contains exactly one @ symbol
     * 2. Check if the username is valid
     * 3. Check if the domain is valid
     * 
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmailStepByStep(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        // Step 1: Check if it contains exactly one @ symbol
        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex != email.lastIndexOf('@') || atIndex == email.length() - 1) {
            return false;
        }
        
        // Split email into username and domain
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);
        
        // Step 2: Check if username is valid
        if (username.isEmpty() || username.length() > 64) {
            return false;
        }
        
        // Username can contain letters, numbers, dots, underscores, percent signs, plus or minus signs
        if (!username.matches("[A-Za-z0-9._%+-]+")) {
            return false;
        }
        
        // Step 3: Check if domain is valid
        if (domain.isEmpty() || !domain.contains(".")) {
            return false;
        }
        
        // Split domain into domain name and TLD
        int dotIndex = domain.lastIndexOf('.');
        if (dotIndex <= 0 || dotIndex == domain.length() - 1) {
            return false;
        }
        
        String domainName = domain.substring(0, dotIndex);
        String tld = domain.substring(dotIndex + 1);
        
        // Domain name can contain letters, numbers, dots, and hyphens
        if (!domainName.matches("[A-Za-z0-9.-]+")) {
            return false;
        }
        
        // TLD should be at least 2 characters
        if (tld.length() < 2 || !tld.matches("[A-Za-z]+")) {
            return false;
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        // Test cases
        String[] testEmails = {
            "john.doe@example.com",
            "jane_doe+test@gmail.com",
            "invalid@email",
            "no_at_symbol.com",
            "double@@at.com",
            "user@.com",
            "user@domain.",
            ".user@domain.com",
            "user@-domain.com",
            "user@domain-with-very-long-name-that-exceeds-maximum-length-restrictions-for-domain-names.com",
            "user.with.dots@example.co.uk",
            "user123@sub.domain.co.in",
            "user+tag@domain.org",
            "user@123.123.123.123",
            "\"quoted\"@domain.com",
            "!#$%&'*+-/=?^_`{|}~@domain.com"
        };
        
        for (String email : testEmails) {
            System.out.println("Email: " + email);
            System.out.println("  Basic Validation: " + isValidEmail(email));
            System.out.println("  RFC 5322 Validation: " + isValidEmailRFC5322(email));
            System.out.println("  Length Check Validation: " + isValidEmailWithLengthCheck(email));
            System.out.println("  Step-by-Step Validation: " + isValidEmailStepByStep(email));
            
            String[] components = extractEmailComponents(email);
            if (components != null) {
                System.out.println("  Components: Username = " + components[0] 
                        + ", Domain = " + components[1] 
                        + ", TLD = " + components[2]);
            } else {
                System.out.println("  Components: Could not extract (invalid email)");
            }
            
            System.out.println();
        }
        
        // Test finding emails in text
        String text = "Contact us at support@example.com or sales@example.org. "
                + "For technical issues, email tech.support@sub.example.co.uk. "
                + "Invalid emails: user@domain, @domain.com";
        
        System.out.println("Emails found in text:");
        List<String> foundEmails = findEmailsInText(text);
        for (String email : foundEmails) {
            System.out.println("  " + email);
        }
    }
}