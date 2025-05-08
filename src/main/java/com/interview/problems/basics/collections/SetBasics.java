package com.interview.problems.basics.collections;

import java.util.*;

/**
 * Basic Set operations and manipulations
 */
public class SetBasics {
    
    // Program 1: Create a HashSet
    public static <T> Set<T> createHashSet() {
        return new HashSet<>();
    }
    
    // Program 2: Create a TreeSet
    public static <T extends Comparable<? super T>> Set<T> createTreeSet() {
        return new TreeSet<>();
    }
    
    // Program 3: Create a LinkedHashSet
    public static <T> Set<T> createLinkedHashSet() {
        return new LinkedHashSet<>();
    }
    
    // Program 4: Add elements to a set
    public static <T> void addElements(Set<T> set, T... elements) {
        if (set == null || elements == null) {
            return;
        }
        
        for (T element : elements) {
            set.add(element);
        }
    }
    
    // Program 5: Print a set
    public static void printSet(Set<?> set) {
        if (set == null) {
            System.out.println("null");
            return;
        }
        
        System.out.println(set);
    }
    
    // Program 6: Remove an element from a set
    public static <T> boolean remove(Set<T> set, T element) {
        if (set == null) {
            return false;
        }
        
        return set.remove(element);
    }
    
    // Program 7: Check if a set contains an element
    public static <T> boolean contains(Set<T> set, T element) {
        if (set == null) {
            return false;
        }
        
        return set.contains(element);
    }
    
    // Program 8: Get the size of a set
    public static <T> int size(Set<T> set) {
        if (set == null) {
            return 0;
        }
        
        return set.size();
    }
    
    // Program 9: Check if a set is empty
    public static <T> boolean isEmpty(Set<T> set) {
        if (set == null) {
            return true;
        }
        
        return set.isEmpty();
    }
    
    // Program 10: Clear a set
    public static <T> void clear(Set<T> set) {
        if (set == null) {
            return;
        }
        
        set.clear();
    }
    
    // Program 11: Convert a set to an array
    public static <T> Object[] toArray(Set<T> set) {
        if (set == null) {
            return new Object[0];
        }
        
        return set.toArray();
    }
    
    // Program 12: Convert an array to a set
    public static <T> Set<T> fromArray(T[] array) {
        if (array == null) {
            return new HashSet<>();
        }
        
        return new HashSet<>(Arrays.asList(array));
    }
    
    // Program 13: Find the union of two sets
    public static <T> Set<T> union(Set<T> set1, Set<T> set2) {
        if (set1 == null && set2 == null) {
            return new HashSet<>();
        }
        
        if (set1 == null) {
            return new HashSet<>(set2);
        }
        
        if (set2 == null) {
            return new HashSet<>(set1);
        }
        
        Set<T> result = new HashSet<>(set1);
        result.addAll(set2);
        return result;
    }
    
    // Program 14: Find the intersection of two sets
    public static <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        if (set1 == null || set2 == null) {
            return new HashSet<>();
        }
        
        Set<T> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }
    
    // Program 15: Find the difference of two sets (set1 - set2)
    public static <T> Set<T> difference(Set<T> set1, Set<T> set2) {
        if (set1 == null) {
            return new HashSet<>();
        }
        
        if (set2 == null) {
            return new HashSet<>(set1);
        }
        
        Set<T> result = new HashSet<>(set1);
        result.removeAll(set2);
        return result;
    }
    
    // Program 16: Check if one set is a subset of another
    public static <T> boolean isSubset(Set<T> subset, Set<T> superset) {
        if (subset == null) {
            return true; // Empty set is a subset of any set
        }
        
        if (superset == null) {
            return subset.isEmpty();
        }
        
        return superset.containsAll(subset);
    }
    
    // Program 17: Check if two sets are disjoint (have no elements in common)
    public static <T> boolean areDisjoint(Set<T> set1, Set<T> set2) {
        if (set1 == null || set2 == null) {
            return true;
        }
        
        Set<T> intersection = intersection(set1, set2);
        return intersection.isEmpty();
    }
    
    // Program 18: Find the symmetric difference of two sets
    public static <T> Set<T> symmetricDifference(Set<T> set1, Set<T> set2) {
        Set<T> union = union(set1, set2);
        Set<T> intersection = intersection(set1, set2);
        
        return difference(union, intersection);
    }
    
    // Program 19: Convert a set to a list
    public static <T> List<T> toList(Set<T> set) {
        if (set == null) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(set);
    }
    
    // Program 20: Count the number of unique elements in an array
    public static <T> int countUniqueElements(T[] array) {
        if (array == null) {
            return 0;
        }
        
        Set<T> uniqueElements = new HashSet<>(Arrays.asList(array));
        return uniqueElements.size();
    }
    
    public static void main(String[] args) {
        System.out.println("===== Set Basics Examples =====");
        
        // Create sets
        Set<String> hashSet = createHashSet();
        System.out.println("Created HashSet: " + hashSet);
        
        Set<String> treeSet = createTreeSet();
        System.out.println("Created TreeSet: " + treeSet);
        
        Set<String> linkedHashSet = createLinkedHashSet();
        System.out.println("Created LinkedHashSet: " + linkedHashSet);
        
        // Add elements
        addElements(hashSet, "Apple", "Banana", "Cherry", "Apple"); // Note: duplicate "Apple" will be ignored
        System.out.println("HashSet after adding elements: " + hashSet);
        
        addElements(treeSet, "Zebra", "Elephant", "Lion", "Tiger");
        System.out.println("TreeSet after adding elements: " + treeSet);
        
        addElements(linkedHashSet, "Red", "Green", "Blue", "Red"); // Note: duplicate "Red" will be ignored
        System.out.println("LinkedHashSet after adding elements: " + linkedHashSet);
        
        // Remove elements
        remove(hashSet, "Banana");
        System.out.println("HashSet after removing 'Banana': " + hashSet);
        
        // Check contents
        System.out.println("HashSet contains 'Apple': " + contains(hashSet, "Apple"));
        System.out.println("HashSet contains 'Banana': " + contains(hashSet, "Banana"));
        
        // Size and empty check
        System.out.println("Size of HashSet: " + size(hashSet));
        System.out.println("Is HashSet empty: " + isEmpty(hashSet));
        
        // Convert to array and back
        Object[] array = toArray(hashSet);
        System.out.println("HashSet converted to array: " + Arrays.toString(array));
        
        String[] stringArray = {"One", "Two", "Three", "One"};
        Set<String> setFromArray = fromArray(stringArray);
        System.out.println("Set from array: " + setFromArray);
        
        // Set operations
        Set<String> set1 = new HashSet<>(Arrays.asList("A", "B", "C", "D"));
        Set<String> set2 = new HashSet<>(Arrays.asList("C", "D", "E", "F"));
        
        System.out.println("Set1: " + set1);
        System.out.println("Set2: " + set2);
        
        System.out.println("Union: " + union(set1, set2));
        System.out.println("Intersection: " + intersection(set1, set2));
        System.out.println("Difference (Set1 - Set2): " + difference(set1, set2));
        System.out.println("Symmetric Difference: " + symmetricDifference(set1, set2));
        
        // Subset and disjoint check
        Set<String> subset = new HashSet<>(Arrays.asList("A", "B"));
        System.out.println("Subset: " + subset);
        System.out.println("Is subset a subset of Set1: " + isSubset(subset, set1));
        
        Set<String> disjointSet = new HashSet<>(Arrays.asList("X", "Y", "Z"));
        System.out.println("Disjoint Set: " + disjointSet);
        System.out.println("Are Set1 and Disjoint Set disjoint: " + areDisjoint(set1, disjointSet));
        
        // Convert to list
        List<String> list = toList(set1);
        System.out.println("Set1 converted to list: " + list);
        
        // Count unique elements
        String[] duplicates = {"a", "b", "c", "a", "b", "d"};
        System.out.println("Array with duplicates: " + Arrays.toString(duplicates));
        System.out.println("Count of unique elements: " + countUniqueElements(duplicates));
        
        // Clear a set
        clear(linkedHashSet);
        System.out.println("LinkedHashSet after clearing: " + linkedHashSet);
    }
}