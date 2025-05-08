package com.interview.problems.linkedlist;

import com.interview.problems.datastructures.ListNode;

/**
 * Problem 1: Reverse Linked List
 * 
 * Given the head of a singly linked list, reverse the list, and return the reversed list.
 * 
 * Time Complexity: O(n) where n is the number of nodes in the linked list
 * Space Complexity: O(1) for iterative approach, O(n) for recursive approach due to call stack
 */
public class ReverseLinkedList {
    
    /**
     * Reverses a linked list using the iterative approach
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static ListNode reverseListIterative(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            // Store next node
            ListNode nextTemp = current.next;
            
            // Reverse the link
            current.next = prev;
            
            // Move prev and current one step forward
            prev = current;
            current = nextTemp;
        }
        
        // prev is the new head of the reversed list
        return prev;
    }
    
    /**
     * Reverses a linked list using the recursive approach
     * Time Complexity: O(n)
     * Space Complexity: O(n) for call stack
     */
    public static ListNode reverseListRecursive(ListNode head) {
        // Base case: empty list or list with single node
        if (head == null || head.next == null) {
            return head;
        }
        
        // Recursively reverse the rest of the list after head
        ListNode newHead = reverseListRecursive(head.next);
        
        // Reverse the link between head and head.next
        head.next.next = head;
        head.next = null;
        
        // Return the new head of the reversed list
        return newHead;
    }
    
    /**
     * Reverses a linked list using a stack
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static ListNode reverseListUsingStack(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        // Create a stack to store nodes
        java.util.Stack<ListNode> stack = new java.util.Stack<>();
        
        // Push all nodes to the stack
        ListNode current = head;
        while (current != null) {
            stack.push(current);
            current = current.next;
        }
        
        // Pop from stack to reverse the list
        ListNode newHead = stack.pop();
        current = newHead;
        
        while (!stack.isEmpty()) {
            current.next = stack.pop();
            current = current.next;
        }
        
        // Set the last node's next to null
        current.next = null;
        
        return newHead;
    }
    
    /**
     * Reverse a linked list between positions m and n
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || head.next == null || m == n) {
            return head;
        }
        
        // Create a dummy node to handle edge cases
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Find the node before the reversal starts
        ListNode prev = dummy;
        for (int i = 1; i < m; i++) {
            prev = prev.next;
        }
        
        // Current will be the first node to be reversed
        ListNode current = prev.next;
        
        // Reverse nodes from position m to n
        for (int i = 0; i < n - m; i++) {
            ListNode nextNode = current.next;
            current.next = nextNode.next;
            nextNode.next = prev.next;
            prev.next = nextNode;
        }
        
        return dummy.next;
    }
    
    public static void main(String[] args) {
        int[][] testCases = {
            {1, 2, 3, 4, 5},
            {1, 2},
            {1},
            {},
            {1, 2, 3, 4, 5, 6, 7}
        };
        
        for (int[] values : testCases) {
            ListNode head = ListNode.createLinkedList(values);
            
            System.out.println("Original List:");
            ListNode.printLinkedList(head);
            
            // Clone the list for each reversal method
            ListNode iterativeHead = ListNode.createLinkedList(values);
            ListNode recursiveHead = ListNode.createLinkedList(values);
            ListNode stackHead = ListNode.createLinkedList(values);
            ListNode partialHead = ListNode.createLinkedList(values);
            
            // Reverse lists with different methods
            ListNode reversed1 = reverseListIterative(iterativeHead);
            System.out.println("Reversed (Iterative):");
            ListNode.printLinkedList(reversed1);
            
            ListNode reversed2 = reverseListRecursive(recursiveHead);
            System.out.println("Reversed (Recursive):");
            ListNode.printLinkedList(reversed2);
            
            ListNode reversed3 = reverseListUsingStack(stackHead);
            System.out.println("Reversed (Using Stack):");
            ListNode.printLinkedList(reversed3);
            
            // Test reversing a portion of the list if it has more than 3 nodes
            if (values.length > 3) {
                int m = 2, n = 4; // Reverse positions 2 to 4
                ListNode partialReversed = reverseBetween(partialHead, m, n);
                System.out.println("Reversed from position " + m + " to " + n + ":");
                ListNode.printLinkedList(partialReversed);
            }
            
            System.out.println("------------------------");
        }
    }
}