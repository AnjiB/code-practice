package com.interview.problems.sets;

import java.util.*;

/**
 * Problem 2: LRU Cache Implementation with LinkedHashSet
 * 
 * Implement a Least Recently Used (LRU) cache using LinkedHashSet. The cache should support:
 * - Adding elements with a maximum capacity
 * - Automatically removing the least recently used element when capacity is reached
 * - Accessing elements and marking them as recently used
 * 
 * Time Complexity: O(1) for add, get, and remove operations
 * Space Complexity: O(n) where n is the capacity of the cache
 */
public class LRUCacheWithSet<T> {
    
    private final int capacity;
    private final LinkedHashSet<T> cache;
    
    /**
     * Initialize the LRU cache with the given capacity
     * 
     * @param capacity the maximum number of elements the cache can hold
     */
    public LRUCacheWithSet(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.cache = new LinkedHashSet<>(capacity);
    }
    
    /**
     * Add an element to the cache, removing the least recently used element if at capacity
     * 
     * @param element the element to add
     * @return true if the element was added, false if it already exists
     */
    public boolean add(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        
        // If element already exists, move it to the end (most recently used)
        if (cache.contains(element)) {
            cache.remove(element);
            cache.add(element);
            return false;
        }
        
        // If at capacity, remove the least recently used element (first)
        if (cache.size() >= capacity) {
            Iterator<T> iterator = cache.iterator();
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }
        
        // Add the new element
        return cache.add(element);
    }
    
    /**
     * Get an element from the cache and mark it as recently used
     * 
     * @param element the element to get
     * @return true if the element exists in the cache, false otherwise
     */
    public boolean get(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        
        if (cache.contains(element)) {
            // Move element to the end (most recently used)
            cache.remove(element);
            cache.add(element);
            return true;
        }
        
        return false;
    }
    
    /**
     * Remove an element from the cache
     * 
     * @param element the element to remove
     * @return true if the element was removed, false if it wasn't in the cache
     */
    public boolean remove(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        
        return cache.remove(element);
    }
    
    /**
     * Check if an element exists in the cache without updating its position
     * 
     * @param element the element to check
     * @return true if the element exists, false otherwise
     */
    public boolean contains(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
        
        return cache.contains(element);
    }
    
    /**
     * Get the current size of the cache
     * 
     * @return the number of elements in the cache
     */
    public int size() {
        return cache.size();
    }
    
    /**
     * Check if the cache is empty
     * 
     * @return true if the cache is empty, false otherwise
     */
    public boolean isEmpty() {
        return cache.isEmpty();
    }
    
    /**
     * Clear the cache
     */
    public void clear() {
        cache.clear();
    }
    
    /**
     * Get the least recently used element (first element)
     * 
     * @return the least recently used element, or null if the cache is empty
     */
    public T getLeastRecentlyUsed() {
        if (cache.isEmpty()) {
            return null;
        }
        
        return cache.iterator().next();
    }
    
    /**
     * Get all elements in the cache as a list, ordered from least recently used to most recently used
     * 
     * @return a list of all elements in the cache
     */
    public List<T> getAll() {
        return new ArrayList<>(cache);
    }
    
    @Override
    public String toString() {
        return cache.toString();
    }
    
    /**
     * A more complete LRU cache implementation that can retrieve values by key
     */
    public static class LRUCacheMap<K, V> {
        private final int capacity;
        private final LinkedHashMap<K, V> cache;
        
        public LRUCacheMap(int capacity) {
            this.capacity = capacity;
            this.cache = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                    return size() > capacity;
                }
            };
        }
        
        public V get(K key) {
            return cache.get(key);
        }
        
        public void put(K key, V value) {
            cache.put(key, value);
        }
        
        public boolean containsKey(K key) {
            return cache.containsKey(key);
        }
        
        public V remove(K key) {
            return cache.remove(key);
        }
        
        public int size() {
            return cache.size();
        }
        
        public Set<K> keySet() {
            return cache.keySet();
        }
        
        public Collection<V> values() {
            return cache.values();
        }
        
        public Set<Map.Entry<K, V>> entrySet() {
            return cache.entrySet();
        }
        
        public void clear() {
            cache.clear();
        }
        
        @Override
        public String toString() {
            return cache.toString();
        }
    }
    
    public static void main(String[] args) {
        // Test LRUCacheWithSet
        LRUCacheWithSet<String> cache = new LRUCacheWithSet<>(3);
        
        System.out.println("=== LRU Cache with LinkedHashSet ===");
        System.out.println("Adding 'A'");
        cache.add("A");
        System.out.println("Cache: " + cache);
        
        System.out.println("Adding 'B'");
        cache.add("B");
        System.out.println("Cache: " + cache);
        
        System.out.println("Adding 'C'");
        cache.add("C");
        System.out.println("Cache: " + cache);
        
        System.out.println("Getting 'A' (marks it as most recently used)");
        cache.get("A");
        System.out.println("Cache: " + cache);
        
        System.out.println("Adding 'D' (should evict 'B')");
        cache.add("D");
        System.out.println("Cache: " + cache);
        
        System.out.println("Adding 'A' again (no eviction, just moves it)");
        cache.add("A");
        System.out.println("Cache: " + cache);
        
        System.out.println("Least recently used: " + cache.getLeastRecentlyUsed());
        System.out.println("All elements: " + cache.getAll());
        System.out.println();
        
        // Test LRUCacheMap
        LRUCacheMap<String, Integer> cacheMap = new LRUCacheMap<>(3);
        
        System.out.println("=== LRU Cache with LinkedHashMap ===");
        cacheMap.put("A", 1);
        cacheMap.put("B", 2);
        cacheMap.put("C", 3);
        System.out.println("Cache after adding A, B, C: " + cacheMap);
        
        System.out.println("Getting 'A': " + cacheMap.get("A"));
        System.out.println("Cache after getting A: " + cacheMap);
        
        cacheMap.put("D", 4);
        System.out.println("Cache after adding D: " + cacheMap);
        
        System.out.println("Contains key 'B': " + cacheMap.containsKey("B"));
        System.out.println("Contains key 'C': " + cacheMap.containsKey("C"));
        
        System.out.println("All keys: " + cacheMap.keySet());
        System.out.println("All values: " + cacheMap.values());
        
        System.out.println("Removing 'A': " + cacheMap.remove("A"));
        System.out.println("Cache after removing A: " + cacheMap);
    }
}