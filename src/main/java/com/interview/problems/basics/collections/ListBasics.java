package com.interview.problems.basics.collections;

import java.util.*;

/**
 * Basic List operations and manipulations
 */
public class ListBasics {
    
    // Program 1: Create an ArrayList
    public static List<String> createArrayList() {
        return new ArrayList<>();
    }
    
    // Program 2: Create a LinkedList
    public static List<String> createLinkedList() {
        return new LinkedList<>();
    }
    
    // Program 3: Add elements to a list
    public static void addElements(List<String> list, String... elements) {
        if (list == null || elements == null) {
            return;
        }
        
        for (String element : elements) {
            list.add(element);
        }
    }
    
    // Program 4: Print a list
    public static void printList(List<?> list) {
        if (list == null) {
            System.out.println("null");
            return;
        }
        
        System.out.println(list);
    }
    
    // Program 5: Get an element at a specific index
    public static <T> T getElement(List<T> list, int index) {
        if (list == null || index < 0 || index >= list.size()) {
            return null;
        }
        
        return list.get(index);
    }
    
    // Program 6: Update an element at a specific index
    public static <T> boolean updateElement(List<T> list, int index, T newValue) {
        if (list == null || index < 0 || index >= list.size()) {
            return false;
        }
        
        list.set(index, newValue);
        return true;
    }
    
    // Program 7: Remove an element by index
    public static <T> boolean removeByIndex(List<T> list, int index) {
        if (list == null || index < 0 || index >= list.size()) {
            return false;
        }
        
        list.remove(index);
        return true;
    }
    
    // Program 8: Remove an element by value
    public static <T> boolean removeByValue(List<T> list, T value) {
        if (list == null) {
            return false;
        }
        
        return list.remove(value);
    }
    
    // Program 9: Check if a list contains a specific element
    public static <T> boolean contains(List<T> list, T element) {
        if (list == null) {
            return false;
        }
        
        return list.contains(element);
    }
    
    // Program 10: Find the index of an element
    public static <T> int indexOf(List<T> list, T element) {
        if (list == null) {
            return -1;
        }
        
        return list.indexOf(element);
    }
    
    // Program 11: Get the size of a list
    public static <T> int size(List<T> list) {
        if (list == null) {
            return 0;
        }
        
        return list.size();
    }
    
    // Program 12: Check if a list is empty
    public static <T> boolean isEmpty(List<T> list) {
        if (list == null) {
            return true;
        }
        
        return list.isEmpty();
    }
    
    // Program 13: Clear a list
    public static <T> void clear(List<T> list) {
        if (list == null) {
            return;
        }
        
        list.clear();
    }
    
    // Program 14: Convert a list to an array
    public static <T> Object[] toArray(List<T> list) {
        if (list == null) {
            return new Object[0];
        }
        
        return list.toArray();
    }
    
    // Program 15: Convert an array to a list
    public static <T> List<T> fromArray(T[] array) {
        if (array == null) {
            return new ArrayList<>();
        }
        
        return new ArrayList<>(Arrays.asList(array));
    }
    
    // Program 16: Sort a list (natural order)
    public static <T extends Comparable<? super T>> void sort(List<T> list) {
        if (list == null) {
            return;
        }
        
        Collections.sort(list);
    }
    
    // Program 17: Sort a list with a custom comparator
    public static <T> void sort(List<T> list, Comparator<? super T> comparator) {
        if (list == null || comparator == null) {
            return;
        }
        
        list.sort(comparator);
    }
    
    // Program 18: Reverse a list
    public static <T> void reverse(List<T> list) {
        if (list == null) {
            return;
        }
        
        Collections.reverse(list);
    }
    
    // Program 19: Swap two elements in a list
    public static <T> boolean swap(List<T> list, int i, int j) {
        if (list == null || i < 0 || j < 0 || i >= list.size() || j >= list.size()) {
            return false;
        }
        
        Collections.swap(list, i, j);
        return true;
    }
    
    // Program 20: Get a sublist
    public static <T> List<T> subList(List<T> list, int fromIndex, int toIndex) {
        if (list == null || fromIndex < 0 || toIndex > list.size() || fromIndex > toIndex) {
            return null;
        }
        
        return list.subList(fromIndex, toIndex);
    }
    
    public static void main(String[] args) {
        System.out.println("===== List Basics Examples =====");
        
        // Create lists
        List<String> arrayList = createArrayList();
        System.out.println("Created ArrayList: " + arrayList);
        
        List<String> linkedList = createLinkedList();
        System.out.println("Created LinkedList: " + linkedList);
        
        // Add elements
        addElements(arrayList, "Apple", "Banana", "Cherry", "Date", "Elderberry");
        System.out.println("ArrayList after adding elements: " + arrayList);
        
        addElements(linkedList, "Red", "Green", "Blue", "Yellow", "Purple");
        System.out.println("LinkedList after adding elements: " + linkedList);
        
        // Get and update elements
        String element = getElement(arrayList, 2);
        System.out.println("Element at index 2 in ArrayList: " + element);
        
        updateElement(arrayList, 2, "Coconut");
        System.out.println("ArrayList after updating index 2: " + arrayList);
        
        // Remove elements
        removeByIndex(arrayList, 3);
        System.out.println("ArrayList after removing index 3: " + arrayList);
        
        removeByValue(linkedList, "Green");
        System.out.println("LinkedList after removing 'Green': " + linkedList);
        
        // Check contents
        System.out.println("ArrayList contains 'Apple': " + contains(arrayList, "Apple"));
        System.out.println("Index of 'Banana' in ArrayList: " + indexOf(arrayList, "Banana"));
        
        // Size and empty check
        System.out.println("Size of ArrayList: " + size(arrayList));
        System.out.println("Is ArrayList empty: " + isEmpty(arrayList));
        
        // Convert to array and back
        Object[] array = toArray(arrayList);
        System.out.println("ArrayList converted to array: " + Arrays.toString(array));
        
        String[] stringArray = {"One", "Two", "Three"};
        List<String> listFromArray = fromArray(stringArray);
        System.out.println("List from array: " + listFromArray);
        
        // Sort
        sort(arrayList);
        System.out.println("ArrayList after sorting: " + arrayList);
        
        // Custom sort (by length)
        sort(linkedList, Comparator.comparing(String::length));
        System.out.println("LinkedList after sorting by length: " + linkedList);
        
        // Reverse
        reverse(arrayList);
        System.out.println("ArrayList after reversing: " + arrayList);
        
        // Swap
        swap(arrayList, 0, arrayList.size() - 1);
        System.out.println("ArrayList after swapping first and last: " + arrayList);
        
        // Sublist
        List<String> sublist = subList(arrayList, 1, 3);
        System.out.println("Sublist of ArrayList (1 to 3): " + sublist);
        
        // Clear
        clear(linkedList);
        System.out.println("LinkedList after clearing: " + linkedList);
    }
}