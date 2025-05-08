package com.interview.problems.basics.collections;

import java.util.*;

/**
 * Basic Map operations and manipulations
 */
public class MapBasics {
    
    // Program 1: Create a HashMap
    public static <K, V> Map<K, V> createHashMap() {
        return new HashMap<>();
    }
    
    // Program 2: Create a TreeMap
    public static <K extends Comparable<? super K>, V> Map<K, V> createTreeMap() {
        return new TreeMap<>();
    }
    
    // Program 3: Create a LinkedHashMap
    public static <K, V> Map<K, V> createLinkedHashMap() {
        return new LinkedHashMap<>();
    }
    
    // Program 4: Put a key-value pair in a map
    public static <K, V> V put(Map<K, V> map, K key, V value) {
        if (map == null) {
            return null;
        }
        
        return map.put(key, value);
    }
    
    // Program 5: Put multiple key-value pairs in a map
    public static <K, V> void putAll(Map<K, V> map, Map<K, V> source) {
        if (map == null || source == null) {
            return;
        }
        
        map.putAll(source);
    }
    
    // Program 6: Get a value by key
    public static <K, V> V get(Map<K, V> map, K key) {
        if (map == null) {
            return null;
        }
        
        return map.get(key);
    }
    
    // Program 7: Get a value by key or return a default value if key is not present
    public static <K, V> V getOrDefault(Map<K, V> map, K key, V defaultValue) {
        if (map == null) {
            return defaultValue;
        }
        
        return map.getOrDefault(key, defaultValue);
    }
    
    // Program 8: Remove a key-value pair by key
    public static <K, V> V remove(Map<K, V> map, K key) {
        if (map == null) {
            return null;
        }
        
        return map.remove(key);
    }
    
    // Program 9: Check if a map contains a key
    public static <K, V> boolean containsKey(Map<K, V> map, K key) {
        if (map == null) {
            return false;
        }
        
        return map.containsKey(key);
    }
    
    // Program 10: Check if a map contains a value
    public static <K, V> boolean containsValue(Map<K, V> map, V value) {
        if (map == null) {
            return false;
        }
        
        return map.containsValue(value);
    }
    
    // Program 11: Get the size of a map
    public static <K, V> int size(Map<K, V> map) {
        if (map == null) {
            return 0;
        }
        
        return map.size();
    }
    
    // Program 12: Check if a map is empty
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        if (map == null) {
            return true;
        }
        
        return map.isEmpty();
    }
    
    // Program 13: Clear a map
    public static <K, V> void clear(Map<K, V> map) {
        if (map == null) {
            return;
        }
        
        map.clear();
    }
    
    // Program 14: Get all keys from a map
    public static <K, V> Set<K> keySet(Map<K, V> map) {
        if (map == null) {
            return new HashSet<>();
        }
        
        return map.keySet();
    }
    
    // Program 15: Get all values from a map
    public static <K, V> Collection<V> values(Map<K, V> map) {
        if (map == null) {
            return new ArrayList<>();
        }
        
        return map.values();
    }
    
    // Program 16: Get all key-value pairs from a map
    public static <K, V> Set<Map.Entry<K, V>> entrySet(Map<K, V> map) {
        if (map == null) {
            return new HashSet<>();
        }
        
        return map.entrySet();
    }
    
    // Program 17: Count frequency of elements in an array
    public static <T> Map<T, Integer> countFrequency(T[] array) {
        Map<T, Integer> frequencyMap = new HashMap<>();
        
        if (array == null) {
            return frequencyMap;
        }
        
        for (T item : array) {
            frequencyMap.put(item, frequencyMap.getOrDefault(item, 0) + 1);
        }
        
        return frequencyMap;
    }
    
    // Program 18: Find all duplicates in an array
    public static <T> Set<T> findDuplicates(T[] array) {
        Set<T> duplicates = new HashSet<>();
        
        if (array == null) {
            return duplicates;
        }
        
        Set<T> uniqueItems = new HashSet<>();
        
        for (T item : array) {
            if (!uniqueItems.add(item)) {
                duplicates.add(item);
            }
        }
        
        return duplicates;
    }
    
    // Program 19: Check if two maps are equal
    public static <K, V> boolean areEqual(Map<K, V> map1, Map<K, V> map2) {
        if (map1 == null && map2 == null) {
            return true;
        }
        
        if (map1 == null || map2 == null) {
            return false;
        }
        
        return map1.equals(map2);
    }
    
    // Program 20: Merge two maps (if there are duplicate keys, use the value from the second map)
    public static <K, V> Map<K, V> merge(Map<K, V> map1, Map<K, V> map2) {
        if (map1 == null && map2 == null) {
            return new HashMap<>();
        }
        
        if (map1 == null) {
            return new HashMap<>(map2);
        }
        
        if (map2 == null) {
            return new HashMap<>(map1);
        }
        
        Map<K, V> result = new HashMap<>(map1);
        result.putAll(map2);
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println("===== Map Basics Examples =====");
        
        // Create maps
        Map<String, Integer> hashMap = createHashMap();
        System.out.println("Created HashMap: " + hashMap);
        
        Map<String, Integer> treeMap = createTreeMap();
        System.out.println("Created TreeMap: " + treeMap);
        
        Map<String, Integer> linkedHashMap = createLinkedHashMap();
        System.out.println("Created LinkedHashMap: " + linkedHashMap);
        
        // Put elements
        put(hashMap, "Apple", 10);
        put(hashMap, "Banana", 20);
        put(hashMap, "Cherry", 30);
        System.out.println("HashMap after adding elements: " + hashMap);
        
        // Create a map to demonstrate putAll
        Map<String, Integer> moreItems = new HashMap<>();
        moreItems.put("Date", 40);
        moreItems.put("Elderberry", 50);
        
        putAll(hashMap, moreItems);
        System.out.println("HashMap after putAll: " + hashMap);
        
        // Get values
        Integer appleCount = get(hashMap, "Apple");
        System.out.println("Value for key 'Apple': " + appleCount);
        
        Integer grapeCount = getOrDefault(hashMap, "Grape", 0);
        System.out.println("Value for key 'Grape' (default 0): " + grapeCount);
        
        // Remove elements
        remove(hashMap, "Cherry");
        System.out.println("HashMap after removing 'Cherry': " + hashMap);
        
        // Check contents
        System.out.println("hashMap contains key 'Banana': " + containsKey(hashMap, "Banana"));
        System.out.println("hashMap contains value 50: " + containsValue(hashMap, 50));
        
        // Size and empty check
        System.out.println("Size of hashMap: " + size(hashMap));
        System.out.println("Is hashMap empty: " + isEmpty(hashMap));
        
        // Keys, values, and entries
        Set<String> keys = keySet(hashMap);
        System.out.println("Keys in hashMap: " + keys);
        
        Collection<Integer> values = values(hashMap);
        System.out.println("Values in hashMap: " + values);
        
        Set<Map.Entry<String, Integer>> entries = entrySet(hashMap);
        System.out.println("Entries in hashMap: " + entries);
        
        // Count frequency example
        String[] fruits = {"apple", "banana", "apple", "orange", "banana", "apple"};
        Map<String, Integer> fruitFrequency = countFrequency(fruits);
        System.out.println("Frequency of fruits: " + fruitFrequency);
        
        // Find duplicates example
        Set<String> duplicateFruits = findDuplicates(fruits);
        System.out.println("Duplicate fruits: " + duplicateFruits);
        
        // Check if maps are equal
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("A", 1);
        map1.put("B", 2);
        
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("A", 1);
        map2.put("B", 2);
        
        Map<String, Integer> map3 = new HashMap<>();
        map3.put("A", 1);
        map3.put("C", 3);
        
        System.out.println("map1: " + map1);
        System.out.println("map2: " + map2);
        System.out.println("map3: " + map3);
        
        System.out.println("map1 equals map2: " + areEqual(map1, map2));
        System.out.println("map1 equals map3: " + areEqual(map1, map3));
        
        // Merge maps
        Map<String, Integer> merged = merge(map1, map3);
        System.out.println("Merged map1 and map3: " + merged);
        
        // Clear a map
        clear(linkedHashMap);
        System.out.println("LinkedHashMap after clearing: " + linkedHashMap);
    }
}