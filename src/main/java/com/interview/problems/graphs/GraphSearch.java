package com.interview.problems.graphs;

import java.util.*;

/**
 * Problem 2: Graph Search Algorithms
 * 
 * Implement different graph search algorithms:
 * 1. Depth-First Search (DFS)
 * 2. Breadth-First Search (BFS)
 * 3. Bidirectional Search
 * 4. Applications such as detecting cycles and finding paths
 * 
 * Time Complexity: O(V + E) where V is the number of vertices and E is the number of edges
 * Space Complexity: O(V) for the visited set and recursion/queue
 */
public class GraphSearch {
    
    /**
     * Performs DFS on a graph represented as an adjacency list
     * 
     * @param graph the adjacency list representation of the graph
     * @param start the starting vertex
     * @return the list of vertices in DFS order
     */
    public static List<Integer> dfs(List<List<Integer>> graph, int start) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        
        dfsHelper(graph, start, visited, result);
        
        return result;
    }
    
    private static void dfsHelper(List<List<Integer>> graph, int vertex, Set<Integer> visited, List<Integer> result) {
        // Mark vertex as visited
        visited.add(vertex);
        result.add(vertex);
        
        // Visit all adjacent vertices
        for (int neighbor : graph.get(vertex)) {
            if (!visited.contains(neighbor)) {
                dfsHelper(graph, neighbor, visited, result);
            }
        }
    }
    
    /**
     * Performs DFS iteratively using a stack
     * 
     * @param graph the adjacency list representation of the graph
     * @param start the starting vertex
     * @return the list of vertices in DFS order
     */
    public static List<Integer> dfsIterative(List<List<Integer>> graph, int start) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        
        stack.push(start);
        visited.add(start);
        result.add(start);
        
        while (!stack.isEmpty()) {
            int vertex = stack.peek();
            
            // Find an unvisited neighbor
            int unvisitedNeighbor = -1;
            for (int neighbor : graph.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    unvisitedNeighbor = neighbor;
                    break;
                }
            }
            
            if (unvisitedNeighbor != -1) {
                // Visit this neighbor
                visited.add(unvisitedNeighbor);
                result.add(unvisitedNeighbor);
                stack.push(unvisitedNeighbor);
            } else {
                // No unvisited neighbors, backtrack
                stack.pop();
            }
        }
        
        return result;
    }
    
    /**
     * Performs BFS on a graph represented as an adjacency list
     * 
     * @param graph the adjacency list representation of the graph
     * @param start the starting vertex
     * @return the list of vertices in BFS order
     */
    public static List<Integer> bfs(List<List<Integer>> graph, int start) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        
        queue.offer(start);
        visited.add(start);
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            result.add(vertex);
            
            // Visit all adjacent vertices
            for (int neighbor : graph.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Checks if a graph contains a cycle
     * 
     * @param graph the adjacency list representation of the graph
     * @return true if the graph contains a cycle, false otherwise
     */
    public static boolean hasCycle(List<List<Integer>> graph) {
        int n = graph.size();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> recursionStack = new HashSet<>();
        
        for (int i = 0; i < n; i++) {
            if (!visited.contains(i) && hasCycleHelper(graph, i, visited, recursionStack)) {
                return true;
            }
        }
        
        return false;
    }
    
    private static boolean hasCycleHelper(List<List<Integer>> graph, int vertex, Set<Integer> visited, Set<Integer> recursionStack) {
        visited.add(vertex);
        recursionStack.add(vertex);
        
        for (int neighbor : graph.get(vertex)) {
            if (!visited.contains(neighbor)) {
                if (hasCycleHelper(graph, neighbor, visited, recursionStack)) {
                    return true;
                }
            } else if (recursionStack.contains(neighbor)) {
                // If the neighbor is in the recursion stack, we found a cycle
                return true;
            }
        }
        
        recursionStack.remove(vertex);
        return false;
    }
    
    /**
     * Finds the shortest path between two vertices using BFS
     * 
     * @param graph the adjacency list representation of the graph
     * @param start the starting vertex
     * @param end the target vertex
     * @return the shortest path from start to end, or an empty list if no path exists
     */
    public static List<Integer> shortestPath(List<List<Integer>> graph, int start, int end) {
        if (start == end) {
            return Collections.singletonList(start);
        }
        
        int n = graph.size();
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            
            for (int neighbor : graph.get(vertex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = vertex;
                    queue.offer(neighbor);
                    
                    if (neighbor == end) {
                        // Reconstruct the path
                        return reconstructPath(parent, start, end);
                    }
                }
            }
        }
        
        return new ArrayList<>(); // No path found
    }
    
    private static List<Integer> reconstructPath(int[] parent, int start, int end) {
        LinkedList<Integer> path = new LinkedList<>();
        
        for (int at = end; at != -1; at = parent[at]) {
            path.addFirst(at);
        }
        
        return path;
    }
    
    /**
     * Bidirectional search to find a path between two vertices
     * 
     * @param graph the adjacency list representation of the graph
     * @param start the starting vertex
     * @param end the target vertex
     * @return the shortest path from start to end, or an empty list if no path exists
     */
    public static List<Integer> bidirectionalSearch(List<List<Integer>> graph, int start, int end) {
        if (start == end) {
            return Collections.singletonList(start);
        }
        
        int n = graph.size();
        
        // Forward BFS
        Queue<Integer> queueForward = new LinkedList<>();
        boolean[] visitedForward = new boolean[n];
        int[] parentForward = new int[n];
        Arrays.fill(parentForward, -1);
        
        queueForward.offer(start);
        visitedForward[start] = true;
        
        // Backward BFS
        Queue<Integer> queueBackward = new LinkedList<>();
        boolean[] visitedBackward = new boolean[n];
        int[] parentBackward = new int[n];
        Arrays.fill(parentBackward, -1);
        
        queueBackward.offer(end);
        visitedBackward[end] = true;
        
        int intersectionPoint = -1;
        
        while (!queueForward.isEmpty() && !queueBackward.isEmpty()) {
            // Forward BFS
            intersectionPoint = bfsStep(graph, queueForward, visitedForward, parentForward, visitedBackward);
            if (intersectionPoint != -1) {
                break;
            }
            
            // Backward BFS
            intersectionPoint = bfsStep(graph, queueBackward, visitedBackward, parentBackward, visitedForward);
            if (intersectionPoint != -1) {
                break;
            }
        }
        
        if (intersectionPoint == -1) {
            return new ArrayList<>(); // No path found
        }
        
        // Reconstruct the path
        return reconstructBidirectionalPath(parentForward, parentBackward, start, end, intersectionPoint);
    }
    
    private static int bfsStep(List<List<Integer>> graph, Queue<Integer> queue, boolean[] visited, int[] parent, boolean[] otherVisited) {
        int vertex = queue.poll();
        
        for (int neighbor : graph.get(vertex)) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                parent[neighbor] = vertex;
                queue.offer(neighbor);
                
                if (otherVisited[neighbor]) {
                    return neighbor; // Intersection found
                }
            }
        }
        
        return -1; // No intersection
    }
    
    private static List<Integer> reconstructBidirectionalPath(int[] parentForward, int[] parentBackward, int start, int end, int intersection) {
        LinkedList<Integer> path = new LinkedList<>();
        
        // Add path from start to intersection
        for (int at = intersection; at != -1; at = parentForward[at]) {
            path.addFirst(at);
        }
        
        // Remove intersection to avoid duplication
        if (!path.isEmpty()) {
            path.removeLast();
        }
        
        // Add path from intersection to end
        for (int at = intersection; at != -1; at = parentBackward[at]) {
            path.addLast(at);
        }
        
        return path;
    }
    
    /**
     * Performs topological sort on a directed acyclic graph (DAG)
     * 
     * @param graph the adjacency list representation of the graph
     * @return the topologically sorted vertices, or an empty list if the graph has a cycle
     */
    public static List<Integer> topologicalSort(List<List<Integer>> graph) {
        int n = graph.size();
        LinkedList<Integer> result = new LinkedList<>();
        boolean[] visited = new boolean[n];
        boolean[] inStack = new boolean[n];
        
        for (int i = 0; i < n; i++) {
            if (!visited[i] && !topologicalSortHelper(graph, i, visited, inStack, result)) {
                return new ArrayList<>(); // Graph has a cycle
            }
        }
        
        return result;
    }
    
    private static boolean topologicalSortHelper(List<List<Integer>> graph, int vertex, boolean[] visited, boolean[] inStack, LinkedList<Integer> result) {
        visited[vertex] = true;
        inStack[vertex] = true;
        
        for (int neighbor : graph.get(vertex)) {
            if (!visited[neighbor]) {
                if (!topologicalSortHelper(graph, neighbor, visited, inStack, result)) {
                    return false;
                }
            } else if (inStack[neighbor]) {
                return false; // Cycle detected
            }
        }
        
        inStack[vertex] = false;
        result.addFirst(vertex); // Add in reverse order
        return true;
    }
    
    /**
     * Counts the number of connected components in an undirected graph
     * 
     * @param graph the adjacency list representation of the graph
     * @return the number of connected components
     */
    public static int countConnectedComponents(List<List<Integer>> graph) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        int count = 0;
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                count++;
                dfsMarkComponent(graph, i, visited);
            }
        }
        
        return count;
    }
    
    private static void dfsMarkComponent(List<List<Integer>> graph, int vertex, boolean[] visited) {
        visited[vertex] = true;
        
        for (int neighbor : graph.get(vertex)) {
            if (!visited[neighbor]) {
                dfsMarkComponent(graph, neighbor, visited);
            }
        }
    }
    
    /**
     * Creates a sample graph for testing
     * 
     * @return a directed graph as an adjacency list
     */
    private static List<List<Integer>> createSampleDirectedGraph() {
        List<List<Integer>> graph = new ArrayList<>();
        
        // 0 -> 1 -> 2
        // |    |
        // v    v
        // 3 <- 4 -> 5
        
        graph.add(Arrays.asList(1, 3));     // 0 -> 1, 3
        graph.add(Arrays.asList(2, 4));     // 1 -> 2, 4
        graph.add(new ArrayList<>());       // 2 -> (none)
        graph.add(new ArrayList<>());       // 3 -> (none)
        graph.add(Arrays.asList(3, 5));     // 4 -> 3, 5
        graph.add(new ArrayList<>());       // 5 -> (none)
        
        return graph;
    }
    
    /**
     * Creates a sample undirected graph for testing
     * 
     * @return an undirected graph as an adjacency list
     */
    private static List<List<Integer>> createSampleUndirectedGraph() {
        List<List<Integer>> graph = new ArrayList<>();
        
        // 0 -- 1    4 -- 5
        // |    |    |
        // 2 -- 3    6
        
        graph.add(Arrays.asList(1, 2));     // 0 -- 1, 2
        graph.add(Arrays.asList(0, 3));     // 1 -- 0, 3
        graph.add(Arrays.asList(0, 3));     // 2 -- 0, 3
        graph.add(Arrays.asList(1, 2));     // 3 -- 1, 2
        graph.add(Arrays.asList(5, 6));     // 4 -- 5, 6
        graph.add(Arrays.asList(4));        // 5 -- 4
        graph.add(Arrays.asList(4));        // 6 -- 4
        
        return graph;
    }
    
    public static void main(String[] args) {
        // Test with a directed graph
        List<List<Integer>> directedGraph = createSampleDirectedGraph();
        
        System.out.println("=== DIRECTED GRAPH ===");
        printGraph(directedGraph);
        
        System.out.println("\n=== DFS Traversal ===");
        List<Integer> dfsResult = dfs(directedGraph, 0);
        System.out.println("Recursive: " + dfsResult);
        
        List<Integer> dfsIterResult = dfsIterative(directedGraph, 0);
        System.out.println("Iterative: " + dfsIterResult);
        
        System.out.println("\n=== BFS Traversal ===");
        List<Integer> bfsResult = bfs(directedGraph, 0);
        System.out.println("Result: " + bfsResult);
        
        System.out.println("\n=== Cycle Detection ===");
        boolean hasCycle = hasCycle(directedGraph);
        System.out.println("Has cycle: " + hasCycle);
        
        System.out.println("\n=== Shortest Path ===");
        List<Integer> path = shortestPath(directedGraph, 0, 5);
        System.out.println("Shortest path from 0 to 5: " + path);
        
        System.out.println("\n=== Topological Sort ===");
        List<Integer> topoSort = topologicalSort(directedGraph);
        System.out.println("Result: " + topoSort);
        
        // Test with an undirected graph
        List<List<Integer>> undirectedGraph = createSampleUndirectedGraph();
        
        System.out.println("\n=== UNDIRECTED GRAPH ===");
        printGraph(undirectedGraph);
        
        System.out.println("\n=== Connected Components ===");
        int components = countConnectedComponents(undirectedGraph);
        System.out.println("Number of connected components: " + components);
        
        System.out.println("\n=== Bidirectional Search ===");
        List<Integer> biPath = bidirectionalSearch(undirectedGraph, 0, 6);
        System.out.println("Bidirectional path from 0 to 6: " + biPath);
    }
    
    /**
     * Utility method to print a graph
     * 
     * @param graph the adjacency list representation of the graph
     */
    private static void printGraph(List<List<Integer>> graph) {
        for (int i = 0; i < graph.size(); i++) {
            System.out.println(i + " -> " + graph.get(i));
        }
    }
}