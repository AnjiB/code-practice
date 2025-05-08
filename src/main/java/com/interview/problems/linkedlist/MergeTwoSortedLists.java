package com.interview.problems.linkedlist;

import com.interview.problems.datastructures.ListNode;

/**
 * Problem 2: Merge Two Sorted Lists
 * 
 * You are given the heads of two sorted linked lists list1 and list2.
 * Merge the two lists in a one sorted list. The list should be made by splicing 
 * together the nodes of the first two lists.
 * 
 * Return the head of the merged linked list.
 * 
 * Time Complexity: O(n + m) where n and m are the lengths of the two lists
 * Space Complexity: O(1) for iterative approach, O(n + m) for recursive approach
 */
public class MergeTwoSortedLists {
    
    /**
     * Merge two sorted lists using the iterative approach
     * Time Complexity: O(n + m)
     * Space Complexity: O(1)
     */
    public static ListNode mergeTwoListsIterative(ListNode list1, ListNode list2) {
        // Create a dummy head to make merging easier
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        
        // If one list is not exhausted, append the remaining nodes
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }
        
        return dummy.next;
    }
    
    /**
     * Merge two sorted lists using the recursive approach
     * Time Complexity: O(n + m)
     * Space Complexity: O(n + m) for call stack
     */
    public static ListNode mergeTwoListsRecursive(ListNode list1, ListNode list2) {
        // Base cases
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        
        // Recursive case: choose the smaller node and merge the rest
        if (list1.val <= list2.val) {
            list1.next = mergeTwoListsRecursive(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoListsRecursive(list1, list2.next);
            return list2;
        }
    }
    
    /**
     * Merge two sorted lists by creating a new list
     * Time Complexity: O(n + m)
     * Space Complexity: O(n + m) for the new list
     */
    public static ListNode mergeTwoListsNewList(ListNode list1, ListNode list2) {
        // Create a dummy head for the new list
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // Pointers for traversing the original lists
        ListNode p1 = list1;
        ListNode p2 = list2;
        
        while (p1 != null && p2 != null) {
            if (p1.val <= p2.val) {
                current.next = new ListNode(p1.val);
                p1 = p1.next;
            } else {
                current.next = new ListNode(p2.val);
                p2 = p2.next;
            }
            current = current.next;
        }
        
        // Add remaining nodes from list1
        while (p1 != null) {
            current.next = new ListNode(p1.val);
            p1 = p1.next;
            current = current.next;
        }
        
        // Add remaining nodes from list2
        while (p2 != null) {
            current.next = new ListNode(p2.val);
            p2 = p2.next;
            current = current.next;
        }
        
        return dummy.next;
    }
    
    /**
     * Merges k sorted linked lists
     * A more general version of the problem
     * Time Complexity: O(N log k) where N is total number of nodes, k is number of lists
     * Space Complexity: O(k) for the priority queue
     */
    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // Create a priority queue to merge lists efficiently
        java.util.PriorityQueue<ListNode> pq = new java.util.PriorityQueue<>(
            (a, b) -> a.val - b.val
        );
        
        // Add the head of each list to the priority queue
        for (ListNode head : lists) {
            if (head != null) {
                pq.add(head);
            }
        }
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // Merge lists by picking the smallest node from the queue
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            current.next = node;
            current = current.next;
            
            if (node.next != null) {
                pq.add(node.next);
            }
        }
        
        return dummy.next;
    }
    
    public static void main(String[] args) {
        int[][] testCases = {
            {1, 2, 4},
            {1, 3, 4},
            {1, 3, 5, 7},
            {2, 4, 6},
            {},
            {1, 2, 3},
            {},
            {5},
            {}
        };
        
        for (int i = 0; i < testCases.length; i += 2) {
            // Get two lists to merge
            ListNode list1 = ListNode.createLinkedList(i < testCases.length ? testCases[i] : new int[0]);
            ListNode list2 = ListNode.createLinkedList(i + 1 < testCases.length ? testCases[i + 1] : new int[0]);
            
            System.out.println("List 1:");
            ListNode.printLinkedList(list1);
            
            System.out.println("List 2:");
            ListNode.printLinkedList(list2);
            
            // Clone lists for different merge methods
            ListNode iterList1 = ListNode.createLinkedList(i < testCases.length ? testCases[i] : new int[0]);
            ListNode iterList2 = ListNode.createLinkedList(i + 1 < testCases.length ? testCases[i + 1] : new int[0]);
            
            ListNode recList1 = ListNode.createLinkedList(i < testCases.length ? testCases[i] : new int[0]);
            ListNode recList2 = ListNode.createLinkedList(i + 1 < testCases.length ? testCases[i + 1] : new int[0]);
            
            ListNode newList1 = ListNode.createLinkedList(i < testCases.length ? testCases[i] : new int[0]);
            ListNode newList2 = ListNode.createLinkedList(i + 1 < testCases.length ? testCases[i + 1] : new int[0]);
            
            // Merge using different methods
            ListNode mergedIter = mergeTwoListsIterative(iterList1, iterList2);
            System.out.println("Merged (Iterative):");
            ListNode.printLinkedList(mergedIter);
            
            ListNode mergedRec = mergeTwoListsRecursive(recList1, recList2);
            System.out.println("Merged (Recursive):");
            ListNode.printLinkedList(mergedRec);
            
            ListNode mergedNew = mergeTwoListsNewList(newList1, newList2);
            System.out.println("Merged (Creating new list):");
            ListNode.printLinkedList(mergedNew);
            
            // Test merge k lists if we have at least 3 lists
            if (i + 2 < testCases.length) {
                ListNode list3 = ListNode.createLinkedList(testCases[i + 2]);
                System.out.println("List 3:");
                ListNode.printLinkedList(list3);
                
                ListNode[] kLists = {
                    ListNode.createLinkedList(i < testCases.length ? testCases[i] : new int[0]),
                    ListNode.createLinkedList(i + 1 < testCases.length ? testCases[i + 1] : new int[0]),
                    list3
                };
                
                ListNode mergedK = mergeKLists(kLists);
                System.out.println("Merged K Lists:");
                ListNode.printLinkedList(mergedK);
            }
            
            System.out.println("------------------------");
        }
    }
}