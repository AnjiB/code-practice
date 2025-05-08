package com.interview.problems.linkedlist;

import com.interview.problems.datastructures.ListNode;
import java.util.HashSet;
import java.util.Set;

/**
 * Problem 3: Linked List Cycle
 * 
 * Given head, the head of a linked list, determine if the linked list has a cycle in it.
 * 
 * There is a cycle in a linked list if there is some node in the list that can be reached again 
 * by continuously following the next pointer.
 * 
 * Return true if there is a cycle in the linked list. Otherwise, return false.
 * 
 * Time Complexity: O(n) where n is the number of nodes in the linked list
 * Space Complexity: O(1) for the two-pointer approach, O(n) for the hash set approach
 */
public class LinkedListCycle {
    
    /**
     * Detects a cycle in a linked list using the two-pointer (Floyd's Cycle-Finding) algorithm
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        // Initialize slow and fast pointers
        ListNode slow = head;
        ListNode fast = head;
        
        // Move slow pointer by one step and fast pointer by two steps
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            // If the two pointers meet, there is a cycle
            if (slow == fast) {
                return true;
            }
        }
        
        // If fast pointer reaches the end, there is no cycle
        return false;
    }
    
    /**
     * Detects a cycle using a hash set to track visited nodes
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static boolean hasCycleHashSet(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        // Set to store visited nodes
        Set<ListNode> visited = new HashSet<>();
        
        ListNode current = head;
        
        while (current != null) {
            // If we've seen this node before, there's a cycle
            if (visited.contains(current)) {
                return true;
            }
            
            // Add current node to the set
            visited.add(current);
            
            // Move to the next node
            current = current.next;
        }
        
        // If we reach the end, there's no cycle
        return false;
    }
    
    /**
     * Finds the node where the cycle begins
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        
        // Initialize slow and fast pointers
        ListNode slow = head;
        ListNode fast = head;
        
        // First, determine if there is a cycle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            // If the two pointers meet, there is a cycle
            if (slow == fast) {
                break;
            }
        }
        
        // If there's no cycle, return null
        if (fast == null || fast.next == null) {
            return null;
        }
        
        // Reset slow pointer to head
        slow = head;
        
        // Move both pointers one step at a time
        // They will meet at the start of the cycle
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;
    }
    
    /**
     * Creates a linked list with a cycle for testing
     * @param values array of values for the linked list
     * @param pos position to create the cycle (connect the last node to the node at this position)
     * @return head of the linked list with a cycle
     */
    public static ListNode createLinkedListWithCycle(int[] values, int pos) {
        if (values == null || values.length == 0 || pos >= values.length) {
            return null;
        }
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        ListNode cycleNode = pos == 0 ? head : null;
        
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
            
            if (i == pos) {
                cycleNode = current;
            }
        }
        
        // Create cycle if pos is valid
        if (pos >= 0 && pos < values.length) {
            current.next = cycleNode;
        }
        
        return head;
    }
    
    public static void main(String[] args) {
        // Test cases: {array of values, position to create cycle}
        Object[][] testCases = {
            {new int[]{3, 2, 0, -4}, 1},
            {new int[]{1, 2}, 0},
            {new int[]{1}, -1},
            {new int[]{1, 2, 3, 4, 5}, -1},
            {new int[]{1, 2, 3, 4, 5}, 4}
        };
        
        for (Object[] testCase : testCases) {
            int[] values = (int[]) testCase[0];
            int pos = (int) testCase[1];
            
            ListNode head = createLinkedListWithCycle(values, pos);
            
            System.out.println("List: " + java.util.Arrays.toString(values) + ", Cycle at position: " + pos);
            
            boolean hasCycle1 = hasCycle(head);
            System.out.println("Has cycle (Two Pointers): " + hasCycle1);
            
            boolean hasCycle2 = hasCycleHashSet(head);
            System.out.println("Has cycle (Hash Set): " + hasCycle2);
            
            if (hasCycle1) {
                ListNode cycleStart = detectCycle(head);
                System.out.println("Cycle starts at node with value: " + cycleStart.val);
            }
            
            System.out.println("------------------------");
        }
    }
}