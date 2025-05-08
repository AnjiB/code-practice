package com.interview.problems.sets;

import java.util.*;

/**
 * Problem 1: Set Operations
 * 
 * Implement various set operations (union, intersection, difference, symmetric difference)
 * and demonstrate their applications in solving problems.
 * 
 * Time Complexity: O(n) for most operations where n is the size of the sets
 * Space Complexity: O(n) for storing the result sets
 */
public class SetOperations {
    
    /**
     * Computes the union of two sets
     * 
     * @param <T> the type of elements in the sets
     * @param setA the first set
     * @param setB the second set
     * @return a new set containing all elements from both sets
     */
    public static <T> Set<T> union(Set<T> setA, Set<T> setB) {
        if (setA == null || setB == null) {
            throw new IllegalArgumentException("Sets cannot be null");
        }
        
        Set<T> resultSet = new HashSet<>(setA);
        resultSet.addAll(setB);
        return resultSet;
    }
    
    /**
     * Computes the intersection of two sets
     * 
     * @param <T> the type of elements in the sets
     * @param setA the first set
     * @param setB the second set
     * @return a new set containing only elements that exist in both sets
     */
    public static <T> Set<T> intersection(Set<T> setA, Set<T> setB) {
        if (setA == null || setB == null) {
            throw new IllegalArgumentException("Sets cannot be null");
        }
        
        Set<T> resultSet = new HashSet<>(setA);
        resultSet.retainAll(setB);
        return resultSet;
    }
    
    /**
     * Computes the difference of two sets (setA - setB)
     * 
     * @param <T> the type of elements in the sets
     * @param setA the first set
     * @param setB the second set
     * @return a new set containing elements in setA but not in setB
     */
    public static <T> Set<T> difference(Set<T> setA, Set<T> setB) {
        if (setA == null || setB == null) {
            throw new IllegalArgumentException("Sets cannot be null");
        }
        
        Set<T> resultSet = new HashSet<>(setA);
        resultSet.removeAll(setB);
        return resultSet;
    }
    
    /**
     * Computes the symmetric difference of two sets
     * 
     * @param <T> the type of elements in the sets
     * @param setA the first set
     * @param setB the second set
     * @return a new set containing elements in either set but not in their intersection
     */
    public static <T> Set<T> symmetricDifference(Set<T> setA, Set<T> setB) {
        if (setA == null || setB == null) {
            throw new IllegalArgumentException("Sets cannot be null");
        }
        
        Set<T> resultSet = new HashSet<>(setA);
        Set<T> tempSet = new HashSet<>(setB);
        
        // Elements in A but not in B, combined with elements in B but not in A
        resultSet.removeAll(setB);
        tempSet.removeAll(setA);
        resultSet.addAll(tempSet);
        
        return resultSet;
    }
    
    /**
     * Alternative implementation of symmetric difference using union and intersection
     */
    public static <T> Set<T> symmetricDifferenceAlt(Set<T> setA, Set<T> setB) {
        if (setA == null || setB == null) {
            throw new IllegalArgumentException("Sets cannot be null");
        }
        
        Set<T> unionSet = union(setA, setB);
        Set<T> intersectionSet = intersection(setA, setB);
        unionSet.removeAll(intersectionSet);
        
        return unionSet;
    }
    
    /**
     * Checks if setA is a subset of setB
     * 
     * @param <T> the type of elements in the sets
     * @param setA the potential subset
     * @param setB the potential superset
     * @return true if setA is a subset of setB, false otherwise
     */
    public static <T> boolean isSubset(Set<T> setA, Set<T> setB) {
        if (setA == null || setB == null) {
            throw new IllegalArgumentException("Sets cannot be null");
        }
        
        return setB.containsAll(setA);
    }
    
    /**
     * Checks if two sets are disjoint (have no elements in common)
     * 
     * @param <T> the type of elements in the sets
     * @param setA the first set
     * @param setB the second set
     * @return true if the sets are disjoint, false otherwise
     */
    public static <T> boolean areDisjoint(Set<T> setA, Set<T> setB) {
        if (setA == null || setB == null) {
            throw new IllegalArgumentException("Sets cannot be null");
        }
        
        // Optimize by iterating through the smaller set
        Set<T> smallerSet = setA.size() < setB.size() ? setA : setB;
        Set<T> largerSet = setA.size() < setB.size() ? setB : setA;
        
        for (T element : smallerSet) {
            if (largerSet.contains(element)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Alternative implementation to check if sets are disjoint using intersection
     */
    public static <T> boolean areDisjointAlt(Set<T> setA, Set<T> setB) {
        if (setA == null || setB == null) {
            throw new IllegalArgumentException("Sets cannot be null");
        }
        
        return intersection(setA, setB).isEmpty();
    }
    
    /**
     * Finds the power set (set of all subsets) of a given set
     * 
     * @param <T> the type of elements in the set
     * @param originalSet the original set
     * @return a set containing all possible subsets of the original set
     */
    public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
        if (originalSet == null) {
            throw new IllegalArgumentException("Set cannot be null");
        }
        
        Set<Set<T>> powerSet = new HashSet<>();
        powerSet.add(new HashSet<>()); // Add the empty set
        
        // Convert set to array for easier iteration
        Object[] elements = originalSet.toArray();
        
        // For each element in the original set, add it to all existing subsets
        // to create new subsets
        for (Object element : elements) {
            Set<Set<T>> newSets = new HashSet<>();
            
            // For each existing subset, create a new subset with the current element added
            for (Set<T> subset : powerSet) {
                Set<T> newSubset = new HashSet<>(subset);
                newSubset.add((T) element);
                newSets.add(newSubset);
            }
            
            // Add all new subsets to the power set
            powerSet.addAll(newSets);
        }
        
        return powerSet;
    }
    
    /**
     * Finds the Cartesian product of two sets
     * 
     * @param <T> the type of elements in the first set
     * @param <U> the type of elements in the second set
     * @param setA the first set
     * @param setB the second set
     * @return a set of pairs representing the Cartesian product
     */
    public static <T, U> Set<Pair<T, U>> cartesianProduct(Set<T> setA, Set<U> setB) {
        if (setA == null || setB == null) {
            throw new IllegalArgumentException("Sets cannot be null");
        }
        
        Set<Pair<T, U>> product = new HashSet<>();
        
        for (T elementA : setA) {
            for (U elementB : setB) {
                product.add(new Pair<>(elementA, elementB));
            }
        }
        
        return product;
    }
    
    /**
     * A simple pair class to hold two elements
     */
    public static class Pair<T, U> {
        private final T first;
        private final U second;
        
        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
        
        public T getFirst() {
            return first;
        }
        
        public U getSecond() {
            return second;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            
            Pair<?, ?> pair = (Pair<?, ?>) obj;
            return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }
        
        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }
    
    /**
     * Demonstration: Find all pairs of elements from two arrays whose sum equals a target value
     */
    public static List<int[]> findPairsWithSum(int[] arr1, int[] arr2, int target) {
        if (arr1 == null || arr2 == null) {
            throw new IllegalArgumentException("Arrays cannot be null");
        }
        
        List<int[]> result = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        
        // Add all elements from first array to a set
        for (int num : arr1) {
            set.add(num);
        }
        
        // Check if the complement exists in set for each element in second array
        for (int num : arr2) {
            int complement = target - num;
            if (set.contains(complement)) {
                result.add(new int[] {complement, num});
            }
        }
        
        return result;
    }
    
    /**
     * Demonstration: Find the longest sequence of consecutive integers in an array
     */
    public static int longestConsecutiveSequence(int[] nums) {
        if (nums == null) {
            return 0;
        }
        
        // Add all numbers to a set for O(1) lookup
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }
        
        int longestStreak = 0;
        
        for (int num : numSet) {
            // Only check sequences starting with the smallest number in the sequence
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                
                // Check for consecutive numbers
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }
                
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }
        
        return longestStreak;
    }
    
    /**
     * Demonstration: Check if a string has all unique characters using a set
     */
    public static boolean hasUniqueCharacters(String str) {
        if (str == null) {
            return true;
        }
        
        Set<Character> charSet = new HashSet<>();
        
        for (char c : str.toCharArray()) {
            // If character is already in set, it's not unique
            if (!charSet.add(c)) {
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        // Create sample sets
        Set<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> setB = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));
        Set<Integer> setC = new HashSet<>(Arrays.asList(2, 3, 4));
        
        System.out.println("Set A: " + setA);
        System.out.println("Set B: " + setB);
        System.out.println("Set C: " + setC);
        
        // Demonstrate set operations
        System.out.println("\n=== SET OPERATIONS ===");
        System.out.println("Union (A ∪ B): " + union(setA, setB));
        System.out.println("Intersection (A ∩ B): " + intersection(setA, setB));
        System.out.println("Difference (A - B): " + difference(setA, setB));
        System.out.println("Difference (B - A): " + difference(setB, setA));
        System.out.println("Symmetric Difference (A △ B): " + symmetricDifference(setA, setB));
        
        // Demonstrate subset and disjoint checks
        System.out.println("\n=== SUBSET AND DISJOINT CHECKS ===");
        System.out.println("Is C a subset of A? " + isSubset(setC, setA));
        System.out.println("Is A a subset of C? " + isSubset(setA, setC));
        
        Set<Integer> setD = new HashSet<>(Arrays.asList(9, 10, 11));
        System.out.println("Set D: " + setD);
        System.out.println("Are sets A and D disjoint? " + areDisjoint(setA, setD));
        System.out.println("Are sets A and B disjoint? " + areDisjoint(setA, setB));
        
        // Demonstrate power set
        Set<Integer> smallSet = new HashSet<>(Arrays.asList(1, 2, 3));
        System.out.println("\n=== POWER SET ===");
        System.out.println("Set: " + smallSet);
        System.out.println("Power Set: " + powerSet(smallSet));
        
        // Demonstrate Cartesian product
        Set<String> set1 = new HashSet<>(Arrays.asList("a", "b"));
        Set<Integer> set2 = new HashSet<>(Arrays.asList(1, 2));
        System.out.println("\n=== CARTESIAN PRODUCT ===");
        System.out.println("Set 1: " + set1);
        System.out.println("Set 2: " + set2);
        System.out.println("Cartesian Product: " + cartesianProduct(set1, set2));
        
        // Demonstrate applications of sets
        System.out.println("\n=== APPLICATIONS ===");
        
        // Finding pairs with a target sum
        int[] array1 = {1, 2, 3, 4, 5};
        int[] array2 = {2, 3, 4, 5, 6};
        int targetSum = 7;
        
        System.out.println("Array 1: " + Arrays.toString(array1));
        System.out.println("Array 2: " + Arrays.toString(array2));
        System.out.println("Pairs with sum " + targetSum + ":");
        
        List<int[]> pairs = findPairsWithSum(array1, array2, targetSum);
        for (int[] pair : pairs) {
            System.out.println("  " + Arrays.toString(pair));
        }
        
        // Finding longest consecutive sequence
        int[] nums = {100, 4, 200, 1, 3, 2, 5};
        System.out.println("\nArray: " + Arrays.toString(nums));
        System.out.println("Longest consecutive sequence: " + longestConsecutiveSequence(nums));
        
        // Checking for unique characters
        String[] strings = {"abcde", "hello", "world", "unique"};
        for (String str : strings) {
            System.out.println("String \"" + str + "\" has unique characters: " + hasUniqueCharacters(str));
        }
    }
}