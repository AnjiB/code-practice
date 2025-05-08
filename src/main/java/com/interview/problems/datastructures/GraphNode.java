package com.interview.problems.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition for a graph node.
 * This class represents a node in a graph.
 */
public class GraphNode {
    public int val;
    public List<GraphNode> neighbors;
    
    public GraphNode() {
        this.val = 0;
        this.neighbors = new ArrayList<>();
    }
    
    public GraphNode(int val) {
        this.val = val;
        this.neighbors = new ArrayList<>();
    }
    
    public GraphNode(int val, List<GraphNode> neighbors) {
        this.val = val;
        this.neighbors = neighbors;
    }
    
    /**
     * Creates a graph from an adjacency list
     * For example, [[2,3],[1],[1],[]] represents:
     * 1 -- 2
     * |
     * 3    4
     * 
     * @param adjList the adjacency list representation
     * @return array of graph nodes
     */
    public static GraphNode[] createFromAdjList(int[][] adjList) {
        if (adjList == null || adjList.length == 0) {
            return new GraphNode[0];
        }
        
        int n = adjList.length;
        GraphNode[] nodes = new GraphNode[n];
        
        // Create nodes
        for (int i = 0; i < n; i++) {
            nodes[i] = new GraphNode(i + 1);
        }
        
        // Add edges
        for (int i = 0; i < n; i++) {
            for (int neighbor : adjList[i]) {
                nodes[i].neighbors.add(nodes[neighbor - 1]);
            }
        }
        
        return nodes;
    }
    
    /**
     * Converts a graph to its adjacency list representation
     * 
     * @param node the starting node of the graph
     * @return the adjacency list representation
     */
    public static int[][] toAdjList(GraphNode node) {
        if (node == null) {
            return new int[0][0];
        }
        
        // Use BFS to traverse the graph and find all nodes
        java.util.Queue<GraphNode> queue = new java.util.LinkedList<>();
        java.util.Map<Integer, Integer> indexMap = new java.util.HashMap<>();
        java.util.Set<GraphNode> visited = new java.util.HashSet<>();
        
        queue.offer(node);
        visited.add(node);
        
        List<GraphNode> allNodes = new ArrayList<>();
        allNodes.add(node);
        indexMap.put(node.val, 0);
        
        while (!queue.isEmpty()) {
            GraphNode current = queue.poll();
            
            for (GraphNode neighbor : current.neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                    allNodes.add(neighbor);
                    indexMap.put(neighbor.val, allNodes.size() - 1);
                }
            }
        }
        
        // Create adjacency list
        int n = allNodes.size();
        int[][] adjList = new int[n][];
        
        for (int i = 0; i < n; i++) {
            GraphNode current = allNodes.get(i);
            int[] neighbors = new int[current.neighbors.size()];
            
            for (int j = 0; j < neighbors.length; j++) {
                neighbors[j] = current.neighbors.get(j).val;
            }
            
            adjList[i] = neighbors;
        }
        
        return adjList;
    }
    
    /**
     * Print the graph as an adjacency list
     * 
     * @param node the starting node of the graph
     */
    public static void printGraph(GraphNode node) {
        if (node == null) {
            System.out.println("Empty graph");
            return;
        }
        
        // BFS traversal
        java.util.Queue<GraphNode> queue = new java.util.LinkedList<>();
        java.util.Set<GraphNode> visited = new java.util.HashSet<>();
        
        queue.offer(node);
        visited.add(node);
        
        while (!queue.isEmpty()) {
            GraphNode current = queue.poll();
            
            System.out.print("Node " + current.val + " -> ");
            for (GraphNode neighbor : current.neighbors) {
                System.out.print(neighbor.val + " ");
                
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Deep clone a graph, preserving its structure
     * 
     * @param node the starting node of the graph
     * @return the cloned graph
     */
    public static GraphNode cloneGraph(GraphNode node) {
        if (node == null) {
            return null;
        }
        
        // Map to store original to clone mapping
        java.util.Map<GraphNode, GraphNode> visited = new java.util.HashMap<>();
        
        // BFS to traverse the graph
        java.util.Queue<GraphNode> queue = new java.util.LinkedList<>();
        queue.offer(node);
        
        // Clone the start node
        visited.put(node, new GraphNode(node.val));
        
        while (!queue.isEmpty()) {
            GraphNode current = queue.poll();
            GraphNode cloneNode = visited.get(current);
            
            // Clone all neighbors
            for (GraphNode neighbor : current.neighbors) {
                GraphNode cloneNeighbor;
                
                if (!visited.containsKey(neighbor)) {
                    // Create new node and add to queue
                    cloneNeighbor = new GraphNode(neighbor.val);
                    visited.put(neighbor, cloneNeighbor);
                    queue.offer(neighbor);
                } else {
                    cloneNeighbor = visited.get(neighbor);
                }
                
                // Add to neighbor list of clone
                cloneNode.neighbors.add(cloneNeighbor);
            }
        }
        
        return visited.get(node);
    }
}