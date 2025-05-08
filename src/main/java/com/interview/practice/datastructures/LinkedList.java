package com.interview.practice.datastructures;

/**
 * A simple implementation of a singly linked list.
 */
public class LinkedList<T> {
    
    private Node<T> head;
    private int size;
    
    /**
     * Node class for the linked list.
     */
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    /**
     * Adds an element to the end of the list.
     * 
     * @param data the element to add
     */
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        
        size++;
    }
    
    /**
     * Returns the size of the list.
     * 
     * @return the number of elements in the list
     */
    public int size() {
        return size;
    }
    
    /**
     * Checks if the list is empty.
     * 
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
}