package com.interview.problems.strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Problem 8: Valid Parentheses
 * 
 * This class checks if a given string containing just the characters '(', ')', '{', '}', '[' and ']'
 * is valid. An input string is valid if:
 * 1. Open brackets must be closed by the same type of brackets.
 * 2. Open brackets must be closed in the correct order.
 * 3. Every close bracket has a corresponding open bracket of the same type.
 * 
 * Time Complexity: O(n) where n is the length of the string
 * Space Complexity: O(n) for the stack in worst case
 */
public class ValidParentheses {
    
    /**
     * Checks if the input string has valid parentheses using a stack
     */
    public static boolean isValid(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        
        // Map to match closing brackets with their opening brackets
        Map<Character, Character> brackets = new HashMap<>();
        brackets.put(')', '(');
        brackets.put('}', '{');
        brackets.put(']', '[');
        
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            // If it's a closing bracket
            if (brackets.containsKey(c)) {
                // Get the top element of the stack. If the stack is empty, set a dummy value
                char topElement = stack.empty() ? '#' : stack.pop();
                
                // If the mapping for this bracket doesn't match the top element of the stack
                if (topElement != brackets.get(c)) {
                    return false;
                }
            } else {
                // It's an opening bracket, push to stack
                stack.push(c);
            }
        }
        
        // If the stack is empty, all brackets have been matched
        return stack.isEmpty();
    }
    
    /**
     * Alternative approach using switch statement and without HashMap
     * Sometimes more efficient in interviews
     */
    public static boolean isValidSwitch(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            switch (c) {
                case '(':
                case '{':
                case '[':
                    stack.push(c);
                    break;
                case ')':
                    if (stack.isEmpty() || stack.pop() != '(') {
                        return false;
                    }
                    break;
                case '}':
                    if (stack.isEmpty() || stack.pop() != '{') {
                        return false;
                    }
                    break;
                case ']':
                    if (stack.isEmpty() || stack.pop() != '[') {
                        return false;
                    }
                    break;
                default:
                    // Invalid character
                    return false;
            }
        }
        
        return stack.isEmpty();
    }
    
    /**
     * Alternative approach that's faster for small strings with simple validations
     * Not as robust as the stack-based approach, but interesting to know
     */
    public static boolean isValidWithoutStack(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        
        // This only works for valid combinations of parentheses
        // Won't work for cases like "([)]"
        while (s.contains("()") || s.contains("{}") || s.contains("[]")) {
            s = s.replace("()", "")
                 .replace("{}", "")
                 .replace("[]", "");
        }
        
        return s.isEmpty();
    }
    
    public static void main(String[] args) {
        String[] testCases = {
            "()",           // Expected: true
            "()[]{}",       // Expected: true
            "(]",           // Expected: false
            "([)]",         // Expected: false
            "{[]}",         // Expected: true
            "",             // Expected: true
            "(((",          // Expected: false
            "))))",         // Expected: false
            "{[()]}",       // Expected: true
            "({[()]}))",    // Expected: false
        };
        
        for (String test : testCases) {
            System.out.println("Input: \"" + test + "\"");
            System.out.println("Using HashMap: " + isValid(test));
            System.out.println("Using Switch: " + isValidSwitch(test));
            System.out.println("Using String Replace: " + isValidWithoutStack(test));
            System.out.println();
        }
    }
}