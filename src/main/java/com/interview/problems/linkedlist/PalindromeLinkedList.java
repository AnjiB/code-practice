package com.interview.problems.linkedlist;

import com.interview.problems.datastructures.ListNode;
import java.util.Stack;

/**
 * Problem 4: Palindrome Linked List
 * 
 * Given the head of a singly linked list, determine if it is a palindrome.
 * A palindrome is a sequence that reads the same forward and backward.
 * 
 * Time Complexity: O(n) where n is the number of nodes in the linked list
 * Space Complexity: O(1) for the reversal approach, O(n) for the stack approach
 */
public class PalindromeLinkedList {
    
    /**
     * Check if a linked list is a palindrome using the stack approach
     * Time Complexity: O(n)
     * Space Complexity: O(n) for the stack
     */
    public static boolean isPalindromeStack(ListNode head) {
        if (head == null || head.next == null) {
            return true; // Empty list or single node is a palindrome
        }
        
        // Push all elements onto a stack
        Stack<Integer> stack = new Stack<>();
        ListNode current = head;
        
        while (current != null) {
            stack.push(current.val);
            current = current.next;
        }
        
        // Compare stack elements with the list
        current = head;
        while (current != null) {
            if (current.val != stack.pop()) {
                return false;
            }
            current = current.next;
        }
        
        return true;
    }
    
    /**
     * Check if a linked list is a palindrome using the reversal approach
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static boolean isPalindromeReversal(ListNode head) {
        if (head == null || head.next == null) {
            return true; // Empty list or single node is a palindrome
        }
        
        // Find the middle of the linked list
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Reverse the second half of the linked list
        ListNode secondHalf = reverseList(slow);
        ListNode firstHalf = head;
        
        // Compare first half with reversed second half
        while (secondHalf != null) {
            if (firstHalf.val != secondHalf.val) {
                return false;
            }
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }
        
        return true;
    }
    
    /**
     * Helper method to reverse a linked list
     */
    private static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }
        
        return prev;
    }
    
    /**
     * Check if a linked list is a palindrome by copying to an array
     * Time Complexity: O(n)
     * Space Complexity: O(n) for the array
     */
    public static boolean isPalindromeArray(ListNode head) {
        if (head == null || head.next == null) {
            return true; // Empty list or single node is a palindrome
        }
        
        // Count the number of nodes
        int count = 0;
        ListNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        
        // Copy values to an array
        int[] values = new int[count];
        current = head;
        for (int i = 0; i < count; i++) {
            values[i] = current.val;
            current = current.next;
        }
        
        // Check if the array is a palindrome
        for (int i = 0; i < count / 2; i++) {
            if (values[i] != values[count - 1 - i]) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Check if a linked list is a palindrome using recursion
     * Time Complexity: O(n)
     * Space Complexity: O(n) for the call stack
     */
    private static ListNode frontPointer;
    
    public static boolean isPalindromeRecursive(ListNode head) {
        frontPointer = head;
        return checkPalindrome(head);
    }
    
    private static boolean checkPalindrome(ListNode currentNode) {
        if (currentNode == null) {
            return true;
        }
        
        // Recursively check the rest of the list and current node
        if (!checkPalindrome(currentNode.next)) {
            return false;
        }
        
        // Check if the current front and current recursion node have same value
        if (currentNode.val != frontPointer.val) {
            return false;
        }
        
        // Move front pointer forward
        frontPointer = frontPointer.next;
        
        return true;
    }
    
    public static void main(String[] args) {
        int[][] testCases = {
            {1, 2, 2, 1},
            {1, 2},
            {1},
            {1, 2, 3, 2, 1},
            {1, 2, 3, 3, 2, 1},
            {1, 1, 2, 1},
            {}
        };
        
        for (int[] values : testCases) {
            ListNode head = ListNode.createLinkedList(values);
            
            System.out.print("List: ");
            ListNode.printLinkedList(head);
            
            // Clone the list for each method
            ListNode stackList = ListNode.createLinkedList(values);
            ListNode reversalList = ListNode.createLinkedList(values);
            ListNode arrayList = ListNode.createLinkedList(values);
            ListNode recursiveList = ListNode.createLinkedList(values);
            
            boolean isPalindrome1 = isPalindromeStack(stackList);
            System.out.println("Is Palindrome (Stack): " + isPalindrome1);
            
            boolean isPalindrome2 = isPalindromeReversal(reversalList);
            System.out.println("Is Palindrome (Reversal): " + isPalindrome2);
            
            boolean isPalindrome3 = isPalindromeArray(arrayList);
            System.out.println("Is Palindrome (Array): " + isPalindrome3);
            
            boolean isPalindrome4 = isPalindromeRecursive(recursiveList);
            System.out.println("Is Palindrome (Recursive): " + isPalindrome4);
            
            System.out.println("------------------------");
        }
    }
}