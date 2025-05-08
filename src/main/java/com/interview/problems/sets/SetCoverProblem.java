package com.interview.problems.sets;

import java.util.*;

/**
 * Problem 3: Set Cover Problem
 * 
 * Given a universal set and a collection of subsets, find the minimum number of subsets
 * whose union equals the universal set.
 * 
 * This is an NP-hard problem, but we can use a greedy approximation algorithm that provides
 * a logarithmic approximation guarantee.
 * 
 * Time Complexity: O(n * m) where n is the number of subsets and m is the size of the universal set
 * Space Complexity: O(m) for storing the remaining elements
 */
public class SetCoverProblem {
    
    /**
     * Solves the set cover problem using a greedy approach
     * 
     * @param <T> the type of elements in the sets
     * @param universalSet the universal set to cover
     * @param subsets the collection of subsets
     * @return a list of subset indices that form a cover
     */
    public static <T> List<Integer> greedySetCover(Set<T> universalSet, List<Set<T>> subsets) {
        if (universalSet == null || subsets == null) {
            throw new IllegalArgumentException("Input sets cannot be null");
        }
        
        // Indices of chosen subsets for the cover
        List<Integer> cover = new ArrayList<>();
        
        // Elements that still need to be covered
        Set<T> remainingElements = new HashSet<>(universalSet);
        
        // Continue until all elements are covered
        while (!remainingElements.isEmpty()) {
            // Find the subset that covers the most remaining elements
            int bestSubsetIndex = -1;
            int maxCovered = 0;
            
            for (int i = 0; i < subsets.size(); i++) {
                // Skip if already in cover
                if (cover.contains(i)) {
                    continue;
                }
                
                // Count how many remaining elements this subset covers
                int covered = 0;
                for (T element : subsets.get(i)) {
                    if (remainingElements.contains(element)) {
                        covered++;
                    }
                }
                
                // Update if this is the best subset so far
                if (covered > maxCovered) {
                    maxCovered = covered;
                    bestSubsetIndex = i;
                }
            }
            
            // If no subset covers any remaining elements, we cannot form a cover
            if (bestSubsetIndex == -1) {
                break;
            }
            
            // Add the best subset to the cover
            cover.add(bestSubsetIndex);
            
            // Remove covered elements from the remaining set
            remainingElements.removeAll(subsets.get(bestSubsetIndex));
        }
        
        // Return the indices of subsets in the cover
        return cover;
    }
    
    /**
     * Solves the weighted set cover problem using a greedy approach
     * 
     * @param <T> the type of elements in the sets
     * @param universalSet the universal set to cover
     * @param subsets the collection of subsets
     * @param weights the weights of the subsets
     * @return a list of subset indices that form a cover
     */
    public static <T> List<Integer> weightedSetCover(Set<T> universalSet, List<Set<T>> subsets, List<Double> weights) {
        if (universalSet == null || subsets == null || weights == null) {
            throw new IllegalArgumentException("Input parameters cannot be null");
        }
        
        if (subsets.size() != weights.size()) {
            throw new IllegalArgumentException("Number of subsets must match number of weights");
        }
        
        // Indices of chosen subsets for the cover
        List<Integer> cover = new ArrayList<>();
        
        // Elements that still need to be covered
        Set<T> remainingElements = new HashSet<>(universalSet);
        
        // Continue until all elements are covered
        while (!remainingElements.isEmpty()) {
            // Find the subset with the best coverage-to-weight ratio
            int bestSubsetIndex = -1;
            double bestRatio = 0;
            
            for (int i = 0; i < subsets.size(); i++) {
                // Skip if already in cover
                if (cover.contains(i)) {
                    continue;
                }
                
                // Count how many remaining elements this subset covers
                int covered = 0;
                for (T element : subsets.get(i)) {
                    if (remainingElements.contains(element)) {
                        covered++;
                    }
                }
                
                // Calculate the coverage-to-weight ratio
                if (covered > 0) {
                    double ratio = (double) covered / weights.get(i);
                    
                    // Update if this is the best ratio so far
                    if (ratio > bestRatio) {
                        bestRatio = ratio;
                        bestSubsetIndex = i;
                    }
                }
            }
            
            // If no subset covers any remaining elements, we cannot form a cover
            if (bestSubsetIndex == -1) {
                break;
            }
            
            // Add the best subset to the cover
            cover.add(bestSubsetIndex);
            
            // Remove covered elements from the remaining set
            remainingElements.removeAll(subsets.get(bestSubsetIndex));
        }
        
        // Return the indices of subsets in the cover
        return cover;
    }
    
    /**
     * Verifies that a set cover solution is valid
     * 
     * @param <T> the type of elements in the sets
     * @param universalSet the universal set to cover
     * @param subsets the collection of subsets
     * @param cover the indices of subsets in the cover
     * @return true if the cover is valid, false otherwise
     */
    public static <T> boolean isValidCover(Set<T> universalSet, List<Set<T>> subsets, List<Integer> cover) {
        if (universalSet == null || subsets == null || cover == null) {
            throw new IllegalArgumentException("Input parameters cannot be null");
        }
        
        // The union of all subsets in the cover
        Set<T> coveredElements = new HashSet<>();
        
        // Add all elements from the selected subsets
        for (int index : cover) {
            if (index < 0 || index >= subsets.size()) {
                throw new IndexOutOfBoundsException("Invalid subset index: " + index);
            }
            coveredElements.addAll(subsets.get(index));
        }
        
        // Check if the cover includes all elements from the universal set
        return coveredElements.containsAll(universalSet);
    }
    
    /**
     * Application: Course Selection Problem
     * 
     * Given a set of skills to learn and courses that teach different sets of skills,
     * find the minimum number of courses to take to learn all skills.
     */
    public static class CourseSelection {
        private final Set<String> requiredSkills;
        private final List<Course> availableCourses;
        
        public CourseSelection(Set<String> requiredSkills, List<Course> availableCourses) {
            this.requiredSkills = requiredSkills;
            this.availableCourses = availableCourses;
        }
        
        public List<Course> selectCourses() {
            // Create list of skill sets for each course
            List<Set<String>> skillSets = new ArrayList<>();
            for (Course course : availableCourses) {
                skillSets.add(course.getSkills());
            }
            
            // Find the set cover
            List<Integer> cover = greedySetCover(requiredSkills, skillSets);
            
            // Convert indices to courses
            List<Course> selectedCourses = new ArrayList<>();
            for (int index : cover) {
                selectedCourses.add(availableCourses.get(index));
            }
            
            return selectedCourses;
        }
        
        public static class Course {
            private final String name;
            private final Set<String> skills;
            
            public Course(String name, Set<String> skills) {
                this.name = name;
                this.skills = new HashSet<>(skills);
            }
            
            public String getName() {
                return name;
            }
            
            public Set<String> getSkills() {
                return new HashSet<>(skills);
            }
            
            @Override
            public String toString() {
                return name + ": " + skills;
            }
        }
    }
    
    /**
     * Application: Minimum Vertex Cover in a Graph
     * 
     * Given an undirected graph, find the minimum set of vertices such that each edge
     * of the graph is incident to at least one vertex in the set.
     */
    public static class VertexCover {
        private final int vertices;
        private final List<List<Integer>> adjacencyList;
        
        public VertexCover(int vertices) {
            this.vertices = vertices;
            this.adjacencyList = new ArrayList<>(vertices);
            
            for (int i = 0; i < vertices; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }
        
        public void addEdge(int u, int v) {
            if (u < 0 || u >= vertices || v < 0 || v >= vertices) {
                throw new IndexOutOfBoundsException("Vertex index out of bounds");
            }
            
            adjacencyList.get(u).add(v);
            adjacencyList.get(v).add(u);
        }
        
        public Set<Integer> findMinimumVertexCover() {
            // Create the universal set of all edges
            Set<Edge> edges = new HashSet<>();
            for (int u = 0; u < vertices; u++) {
                for (int v : adjacencyList.get(u)) {
                    if (u < v) { // Add each edge only once
                        edges.add(new Edge(u, v));
                    }
                }
            }
            
            // Create subsets of edges covered by each vertex
            List<Set<Edge>> vertexSubsets = new ArrayList<>();
            for (int v = 0; v < vertices; v++) {
                Set<Edge> subset = new HashSet<>();
                for (int neighbor : adjacencyList.get(v)) {
                    // Ensure edge is added in correct order (smaller vertex first)
                    int min = Math.min(v, neighbor);
                    int max = Math.max(v, neighbor);
                    subset.add(new Edge(min, max));
                }
                vertexSubsets.add(subset);
            }
            
            // Find the set cover
            List<Integer> cover = greedySetCover(edges, vertexSubsets);
            
            // Convert list to set
            return new HashSet<>(cover);
        }
        
        private static class Edge {
            final int u;
            final int v;
            
            Edge(int u, int v) {
                this.u = u;
                this.v = v;
            }
            
            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                
                Edge edge = (Edge) obj;
                return u == edge.u && v == edge.v;
            }
            
            @Override
            public int hashCode() {
                return Objects.hash(u, v);
            }
            
            @Override
            public String toString() {
                return "(" + u + ", " + v + ")";
            }
        }
    }
    
    public static void main(String[] args) {
        // Example 1: Simple set cover problem
        Set<Integer> universalSet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<Set<Integer>> subsets = new ArrayList<>();
        
        subsets.add(new HashSet<>(Arrays.asList(1, 2, 3, 8)));
        subsets.add(new HashSet<>(Arrays.asList(1, 2, 3, 4, 8)));
        subsets.add(new HashSet<>(Arrays.asList(1, 2, 3, 4)));
        subsets.add(new HashSet<>(Arrays.asList(5, 6, 7)));
        subsets.add(new HashSet<>(Arrays.asList(8, 9, 10)));
        subsets.add(new HashSet<>(Arrays.asList(1, 2, 5, 7, 8, 9, 10)));
        
        System.out.println("=== SET COVER PROBLEM ===");
        System.out.println("Universal set: " + universalSet);
        System.out.println("Subsets:");
        for (int i = 0; i < subsets.size(); i++) {
            System.out.println("  Subset " + i + ": " + subsets.get(i));
        }
        
        List<Integer> cover = greedySetCover(universalSet, subsets);
        System.out.println("Greedy cover: " + cover);
        
        System.out.println("Validation: Cover is " + 
                (isValidCover(universalSet, subsets, cover) ? "valid" : "invalid"));
        
        System.out.println("Selected subsets:");
        for (int index : cover) {
            System.out.println("  Subset " + index + ": " + subsets.get(index));
        }
        
        // Example 2: Weighted set cover
        List<Double> weights = Arrays.asList(2.0, 3.0, 1.5, 1.0, 2.5, 4.0);
        
        System.out.println("\n=== WEIGHTED SET COVER ===");
        System.out.println("Weights: " + weights);
        
        List<Integer> weightedCover = weightedSetCover(universalSet, subsets, weights);
        System.out.println("Weighted cover: " + weightedCover);
        
        System.out.println("Validation: Cover is " + 
                (isValidCover(universalSet, subsets, weightedCover) ? "valid" : "invalid"));
        
        System.out.println("Selected subsets:");
        double totalWeight = 0;
        for (int index : weightedCover) {
            System.out.println("  Subset " + index + ": " + subsets.get(index) + 
                    " (weight: " + weights.get(index) + ")");
            totalWeight += weights.get(index);
        }
        System.out.println("Total weight: " + totalWeight);
        
        // Example 3: Course selection problem
        Set<String> requiredSkills = new HashSet<>(Arrays.asList(
                "Python", "Java", "SQL", "Machine Learning", "Data Structures", 
                "Algorithms", "Web Development", "Cloud Computing"));
        
        List<CourseSelection.Course> courses = new ArrayList<>();
        courses.add(new CourseSelection.Course("CS101", 
                new HashSet<>(Arrays.asList("Python", "Data Structures"))));
        courses.add(new CourseSelection.Course("CS102", 
                new HashSet<>(Arrays.asList("Java", "Data Structures", "Algorithms"))));
        courses.add(new CourseSelection.Course("CS103", 
                new HashSet<>(Arrays.asList("SQL", "Database Design"))));
        courses.add(new CourseSelection.Course("CS201", 
                new HashSet<>(Arrays.asList("Python", "Machine Learning"))));
        courses.add(new CourseSelection.Course("CS202", 
                new HashSet<>(Arrays.asList("Web Development", "JavaScript"))));
        courses.add(new CourseSelection.Course("CS301", 
                new HashSet<>(Arrays.asList("Cloud Computing", "DevOps"))));
        courses.add(new CourseSelection.Course("CS401", 
                new HashSet<>(Arrays.asList("Python", "Machine Learning", "SQL"))));
        
        CourseSelection courseSelection = new CourseSelection(requiredSkills, courses);
        List<CourseSelection.Course> selectedCourses = courseSelection.selectCourses();
        
        System.out.println("\n=== COURSE SELECTION ===");
        System.out.println("Required skills: " + requiredSkills);
        System.out.println("Available courses:");
        for (CourseSelection.Course course : courses) {
            System.out.println("  " + course);
        }
        
        System.out.println("Selected courses:");
        for (CourseSelection.Course course : selectedCourses) {
            System.out.println("  " + course);
        }
        
        // Example 4: Minimum vertex cover in a graph
        VertexCover graph = new VertexCover(6);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 5);
        graph.addEdge(4, 5);
        
        Set<Integer> vertexCover = graph.findMinimumVertexCover();
        
        System.out.println("\n=== MINIMUM VERTEX COVER ===");
        System.out.println("Graph edges:");
        for (int u = 0; u < graph.vertices; u++) {
            for (int v : graph.adjacencyList.get(u)) {
                if (u < v) { // Print each edge only once
                    System.out.println("  " + u + " -- " + v);
                }
            }
        }
        
        System.out.println("Minimum vertex cover: " + vertexCover);
    }
}