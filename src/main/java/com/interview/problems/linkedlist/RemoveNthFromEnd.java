package com.interview.problems.linkedlist;

import com.interview.problems.datastructures.ListNode;

/**
 * Problem 5: Remove Nth Node From End of List
 * 
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 * 
 * Time Complexity: O(n) where n is the number of nodes in the linked list
 * Space Complexity: O(1)
 */
public class RemoveNthFromEnd {
    
    /**
     * Remove the nth node from the end of the list using the two-pass approach
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static ListNode removeNthFromEndTwoPass(ListNode head, int n) {
        if (head == null || n <= 0) {
            return head;
        }
        
        // First pass: count the number of nodes
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        
        // Handle edge case where n is greater than length
        if (n > length) {
            return head;
        }
        
        // Handle edge case where we need to remove the head
        if (n == length) {
            return head.next;
        }
        
        // Second pass: remove the nth node from the end
        current = head;
        for (int i = 0; i < length - n - 1; i++) {
            current = current.next;
        }
        
        // Skip the nth node from the end
        current.next = current.next.next;
        
        return head;
    }
    
    /**
     * Remove the nth node from the end of the list using the one-pass (two-pointer) approach
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static ListNode removeNthFromEndOnePass(ListNode head, int n) {
        if (head == null || n <= 0) {
            return head;
        }
        
        // Create a dummy node to handle edge cases
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode first = dummy;
        ListNode second = dummy;
        
        // Advance first pointer by n+1 steps
        for (int i = 0; i <= n; i++) {
            if (first == null) {
                return head; // n is greater than length
            }
            first = first.next;
        }
        
        // Move both pointers until first reaches the end
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        
        // Remove the nth node from the end
        second.next = second.next.next;
        
        return dummy.next;
    }
    
    /**
     * Alternative approach using recursion
     * Time Complexity: O(n)
     * Space Complexity: O(n) for the call stack
     */
    private static int counter;
    
    public static ListNode removeNthFromEndRecursive(ListNode head, int n) {
        counter = 0;
        return removeNode(head, n);
    }
    
    private static ListNode removeNode(ListNode node, int n) {
        if (node == null) {
            return null;
        }
        
        node.next = removeNode(node.next, n);
        counter++;
        
        if (counter == n) {
            return node.next; // Skip this node
        }
        
        return node;
    }
    
    public static void main(String[] args) {
        int[][] testCases = {
            {1, 2, 3, 4, 5},
            {1},
            {1, 2},
            {1, 2, 3, 4, 5, 6, 7, 8},
            {1, 2, 3}
        };
        
        int[] nValues = {2, 1, 1, 4, 3};
        
        for (int i = 0; i < testCases.length; i++) {
            int[] values = testCases[i];
            int n = nValues[i];
            
            ListNode head = ListNode.createLinkedList(values);
            
            System.out.println("Original List:");
            ListNode.printLinkedList(head);
            System.out.println("Remove " + n + "th node from the end");
            
            // Clone lists for each removal method
            ListNode twoPassList = ListNode.createLinkedList(values);
            ListNode onePassList = ListNode.createLinkedList(values);
            ListNode recursiveList = ListNode.createLinkedList(values);
            
            ListNode result1 = removeNthFromEndTwoPass(twoPassList, n);
            System.out.println("Result (Two Pass):");
            ListNode.printLinkedList(result1);
            
            ListNode result2 = removeNthFromEndOnePass(onePassList, n);
            System.out.println("Result (One Pass):");
            ListNode.printLinkedList(result2);
            
            ListNode result3 = removeNthFromEndRecursive(recursiveList, n);
            System.out.println("Result (Recursive):");
            ListNode.printLinkedList(result3);
            
            System.out.println("------------------------");
        }
    }
}