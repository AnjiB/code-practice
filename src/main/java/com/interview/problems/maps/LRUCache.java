package com.interview.problems.maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Problem 2: LRU Cache
 * 
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 * 
 * Implement the LRUCache class:
 * - LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 * - int get(int key) Return the value of the key if the key exists, otherwise return -1.
 * - void put(int key, int value) Update the value of the key if the key exists. Otherwise, 
 *   add the key-value pair to the cache. If the number of keys exceeds the capacity from this 
 *   operation, evict the least recently used key.
 * 
 * The functions get and put must each run in O(1) average time complexity.
 * 
 * Time Complexity: O(1) for both get and put operations
 * Space Complexity: O(capacity) for storing the key-value pairs
 */
public class LRUCache {
    
    // Node class for doubly linked list
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        public Node() {
        }
        
        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private final int capacity;
    private final Map<Integer, Node> cache;
    private final Node head; // Dummy head of doubly linked list
    private final Node tail; // Dummy tail of doubly linked list
    
    /**
     * Initialize the LRU cache with the given capacity
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // Initialize dummy head and tail
        this.head = new Node();
        this.tail = new Node();
        
        // Connect head and tail
        head.next = tail;
        tail.prev = head;
    }
    
    /**
     * Get the value of the key if it exists in the cache
     * Time Complexity: O(1)
     * @param key the key to get
     * @return the value if key exists, otherwise -1
     */
    public int get(int key) {
        Node node = cache.get(key);
        
        if (node == null) {
            return -1; // Key doesn't exist
        }
        
        // Move node to the front (most recently used)
        moveToFront(node);
        
        return node.value;
    }
    
    /**
     * Update or insert a key-value pair in the cache
     * Time Complexity: O(1)
     * @param key the key to update or insert
     * @param value the value to set
     */
    public void put(int key, int value) {
        Node node = cache.get(key);
        
        if (node != null) {
            // Key exists, update value and move to front
            node.value = value;
            moveToFront(node);
        } else {
            // Key doesn't exist, create new node
            Node newNode = new Node(key, value);
            
            // Add to cache
            cache.put(key, newNode);
            
            // Add to front of list
            addToFront(newNode);
            
            // If over capacity, remove least recently used (tail)
            if (cache.size() > capacity) {
                removeLRU();
            }
        }
    }
    
    /**
     * Move a node to the front of the list (most recently used)
     */
    private void moveToFront(Node node) {
        // Remove node from its current position
        removeNode(node);
        
        // Add to front
        addToFront(node);
    }
    
    /**
     * Add a node to the front of the list
     */
    private void addToFront(Node node) {
        // Connect node with head and head.next
        node.next = head.next;
        node.prev = head;
        
        // Update head.next and head.next.prev
        head.next.prev = node;
        head.next = node;
    }
    
    /**
     * Remove a node from the list
     */
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    /**
     * Remove the least recently used node (from the tail)
     */
    private void removeLRU() {
        Node lru = tail.prev;
        removeNode(lru);
        cache.remove(lru.key);
    }
    
    /**
     * Get the current size of the cache
     */
    public int size() {
        return cache.size();
    }
    
    /**
     * Clear the cache
     */
    public void clear() {
        cache.clear();
        head.next = tail;
        tail.prev = head;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LRUCache [");
        
        Node current = head.next;
        while (current != tail) {
            sb.append("(").append(current.key).append(":").append(current.value).append(")");
            current = current.next;
            if (current != tail) {
                sb.append(" -> ");
            }
        }
        
        sb.append("], size=").append(cache.size()).append("/").append(capacity);
        return sb.toString();
    }
    
    /**
     * Alternative implementation using Java's LinkedHashMap
     * Much simpler but hides the implementation details
     */
    public static class LRUCacheUsingLinkedHashMap {
        private final int capacity;
        private final Map<Integer, Integer> cache;
        
        public LRUCacheUsingLinkedHashMap(int capacity) {
            this.capacity = capacity;
            // Use accessOrder=true to maintain LRU order
            this.cache = new java.util.LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                    return size() > capacity;
                }
            };
        }
        
        public int get(int key) {
            return cache.getOrDefault(key, -1);
        }
        
        public void put(int key, int value) {
            cache.put(key, value);
        }
        
        @Override
        public String toString() {
            return "LRUCache " + cache.toString() + ", size=" + cache.size() + "/" + capacity;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Testing custom LRU Cache implementation:");
        testLRUCache(new LRUCache(2));
        
        System.out.println("\nTesting LinkedHashMap LRU Cache implementation:");
        testLRUCache(new LRUCacheUsingLinkedHashMap(2));
    }
    
    private static void testLRUCache(Object cache) {
        boolean isCustom = cache instanceof LRUCache;
        
        // Test operations
        if (isCustom) {
            LRUCache lruCache = (LRUCache) cache;
            
            // Test put and get
            lruCache.put(1, 1);
            System.out.println("After put(1, 1): " + lruCache);
            
            lruCache.put(2, 2);
            System.out.println("After put(2, 2): " + lruCache);
            
            System.out.println("get(1): " + lruCache.get(1));
            System.out.println("After get(1): " + lruCache);
            
            lruCache.put(3, 3); // This will evict key 2
            System.out.println("After put(3, 3): " + lruCache);
            
            System.out.println("get(2): " + lruCache.get(2)); // Should return -1
            
            lruCache.put(4, 4); // This will evict key 1
            System.out.println("After put(4, 4): " + lruCache);
            
            System.out.println("get(1): " + lruCache.get(1)); // Should return -1
            System.out.println("get(3): " + lruCache.get(3)); // Should return 3
            System.out.println("get(4): " + lruCache.get(4)); // Should return 4
            
            // Clear and test again
            lruCache.clear();
            System.out.println("After clear(): " + lruCache);
            
            // Another test case
            lruCache.put(1, 10);
            lruCache.put(2, 20);
            lruCache.put(3, 30);
            lruCache.put(4, 40);
            System.out.println("After multiple puts: " + lruCache);
            
            lruCache.get(2);
            System.out.println("After get(2): " + lruCache);
            
            lruCache.put(5, 50);
            System.out.println("After put(5, 50): " + lruCache);
        } else {
            LRUCacheUsingLinkedHashMap lruCache = (LRUCacheUsingLinkedHashMap) cache;
            
            lruCache.put(1, 1);
            System.out.println("After put(1, 1): " + lruCache);
            
            lruCache.put(2, 2);
            System.out.println("After put(2, 2): " + lruCache);
            
            System.out.println("get(1): " + lruCache.get(1));
            System.out.println("After get(1): " + lruCache);
            
            lruCache.put(3, 3); // This will evict key 2
            System.out.println("After put(3, 3): " + lruCache);
            
            System.out.println("get(2): " + lruCache.get(2)); // Should return -1
            
            lruCache.put(4, 4); // This will evict key 1
            System.out.println("After put(4, 4): " + lruCache);
            
            System.out.println("get(1): " + lruCache.get(1)); // Should return -1
            System.out.println("get(3): " + lruCache.get(3)); // Should return 3
            System.out.println("get(4): " + lruCache.get(4)); // Should return 4
            
            // Another test case
            lruCache = new LRUCacheUsingLinkedHashMap(2);
            System.out.println("New cache: " + lruCache);
            
            lruCache.put(1, 10);
            lruCache.put(2, 20);
            lruCache.put(3, 30);
            lruCache.put(4, 40);
            System.out.println("After multiple puts: " + lruCache);
            
            lruCache.get(2);
            System.out.println("After get(2): " + lruCache);
            
            lruCache.put(5, 50);
            System.out.println("After put(5, 50): " + lruCache);
        }
    }
}