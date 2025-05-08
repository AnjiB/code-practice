package com.interview.problems.datastructures;

/**
 * Definition for singly-linked list node.
 * This class represents a node in a singly linked list.
 */
public class ListNode {
    public int val;
    public ListNode next;
    
    // Constructors
    public ListNode() {
    }
    
    public ListNode(int val) {
        this.val = val;
    }
    
    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
    
    /**
     * Creates a linked list from an array of values
     * @param values the array of values to create the list from
     * @return the head of the created linked list
     */
    public static ListNode createLinkedList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        
        return head;
    }
    
    /**
     * Converts a linked list to a string representation
     * @return string representation of the linked list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode current = this;
        
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        
        return sb.toString();
    }
    
    /**
     * Prints a linked list
     * @param head the head of the linked list to print
     */
    public static void printLinkedList(ListNode head) {
        if (head == null) {
            System.out.println("null");
            return;
        }
        
        System.out.println(head.toString());
    }
    
    /**
     * Gets the length of a linked list
     * @param head the head of the linked list
     * @return the length of the linked list
     */
    public static int getLength(ListNode head) {
        int length = 0;
        ListNode current = head;
        
        while (current != null) {
            length++;
            current = current.next;
        }
        
        return length;
    }
}