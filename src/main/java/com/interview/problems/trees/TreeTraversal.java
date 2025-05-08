package com.interview.problems.trees;

import com.interview.problems.datastructures.TreeNode;
import java.util.*;

/**
 * Problem 1: Tree Traversal Algorithms
 * 
 * Implement different tree traversal algorithms:
 * 1. Depth-First Search (DFS): Pre-order, In-order, Post-order
 * 2. Breadth-First Search (BFS): Level-order
 * 3. Iterative implementations of DFS
 * 4. Zigzag traversal
 * 5. Boundary traversal
 * 
 * Time Complexity: O(n) where n is the number of nodes in the tree
 * Space Complexity: O(h) for recursive traversals, where h is the height of the tree
 *                  O(n) for iterative traversals in the worst case
 */
public class TreeTraversal {
    
    /**
     * Pre-order traversal (recursive): Root -> Left -> Right
     * 
     * @param root the root of the binary tree
     * @return list of values in pre-order
     */
    public static List<Integer> preOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preOrderHelper(root, result);
        return result;
    }
    
    private static void preOrderHelper(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        result.add(node.val);               // Visit root
        preOrderHelper(node.left, result);  // Visit left subtree
        preOrderHelper(node.right, result); // Visit right subtree
    }
    
    /**
     * In-order traversal (recursive): Left -> Root -> Right
     * 
     * @param root the root of the binary tree
     * @return list of values in in-order
     */
    public static List<Integer> inOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inOrderHelper(root, result);
        return result;
    }
    
    private static void inOrderHelper(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        inOrderHelper(node.left, result);  // Visit left subtree
        result.add(node.val);              // Visit root
        inOrderHelper(node.right, result); // Visit right subtree
    }
    
    /**
     * Post-order traversal (recursive): Left -> Right -> Root
     * 
     * @param root the root of the binary tree
     * @return list of values in post-order
     */
    public static List<Integer> postOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postOrderHelper(root, result);
        return result;
    }
    
    private static void postOrderHelper(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        postOrderHelper(node.left, result);  // Visit left subtree
        postOrderHelper(node.right, result); // Visit right subtree
        result.add(node.val);                // Visit root
    }
    
    /**
     * Level-order traversal (BFS): Visit nodes level by level
     * 
     * @param root the root of the binary tree
     * @return list of values in level-order
     */
    public static List<Integer> levelOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            result.add(node.val);
            
            if (node.left != null) {
                queue.offer(node.left);
            }
            
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        
        return result;
    }
    
    /**
     * Level-order traversal grouped by level
     * 
     * @param root the root of the binary tree
     * @return list of lists, each inner list contains nodes from one level
     */
    public static List<List<Integer>> levelOrderByLevel(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            result.add(currentLevel);
        }
        
        return result;
    }
    
    /**
     * Pre-order traversal (iterative)
     * 
     * @param root the root of the binary tree
     * @return list of values in pre-order
     */
    public static List<Integer> preOrderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        
        if (root == null) {
            return result;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            
            // Push right first so left is processed first (LIFO)
            if (node.right != null) {
                stack.push(node.right);
            }
            
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        
        return result;
    }
    
    /**
     * In-order traversal (iterative)
     * 
     * @param root the root of the binary tree
     * @return list of values in in-order
     */
    public static List<Integer> inOrderIterative(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        
        if (root == null) {
            return result;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            // Go as far left as possible
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // Process node
            current = stack.pop();
            result.add(current.val);
            
            // Go to right subtree
            current = current.right;
        }
        
        return result;
    }
    
    /**
     * Post-order traversal (iterative)
     * 
     * @param root the root of the binary tree
     * @return list of values in post-order
     */
    public static List<Integer> postOrderIterative(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<>();
        
        if (root == null) {
            return result;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        // Process in reverse post-order (Root -> Right -> Left)
        // and add to the front of the result list to get correct order
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.addFirst(node.val);
            
            if (node.left != null) {
                stack.push(node.left);
            }
            
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        
        return result;
    }
    
    /**
     * Zigzag level order traversal
     * 
     * @param root the root of the binary tree
     * @return list of lists, each inner list contains nodes from one level in zigzag order
     */
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                if (leftToRight) {
                    currentLevel.add(node.val);
                } else {
                    currentLevel.add(0, node.val); // Add to front to reverse order
                }
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            result.add(currentLevel);
            leftToRight = !leftToRight; // Toggle direction
        }
        
        return result;
    }
    
    /**
     * Boundary traversal of a binary tree (anti-clockwise)
     * Order: root, left boundary (except leaf), leaves (left to right), right boundary (except leaf) in reverse
     * 
     * @param root the root of the binary tree
     * @return list of values in boundary traversal
     */
    public static List<Integer> boundaryTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        
        if (root == null) {
            return result;
        }
        
        // Add root
        result.add(root.val);
        
        // Get left boundary (except leaves)
        getLeftBoundary(root.left, result);
        
        // Get all leaves
        getLeaves(root.left, result);
        getLeaves(root.right, result);
        
        // Get right boundary in reverse (except leaves)
        getRightBoundary(root.right, result);
        
        return result;
    }
    
    private static void getLeftBoundary(TreeNode node, List<Integer> result) {
        if (node == null || (node.left == null && node.right == null)) {
            return;
        }
        
        result.add(node.val);
        
        if (node.left != null) {
            getLeftBoundary(node.left, result);
        } else {
            getLeftBoundary(node.right, result);
        }
    }
    
    private static void getLeaves(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        if (node.left == null && node.right == null) {
            result.add(node.val);
            return;
        }
        
        getLeaves(node.left, result);
        getLeaves(node.right, result);
    }
    
    private static void getRightBoundary(TreeNode node, List<Integer> result) {
        if (node == null || (node.left == null && node.right == null)) {
            return;
        }
        
        if (node.right != null) {
            getRightBoundary(node.right, result);
        } else {
            getRightBoundary(node.left, result);
        }
        
        result.add(node.val); // Add after recursion (to reverse the order)
    }
    
    /**
     * Vertical order traversal of a binary tree
     * 
     * @param root the root of the binary tree
     * @return list of lists, each inner list contains nodes from one vertical level
     */
    public static List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        
        if (root == null) {
            return result;
        }
        
        // Map to store nodes at each column
        Map<Integer, List<Integer>> columnMap = new TreeMap<>();
        
        // Queue to do level order traversal
        Queue<Pair<TreeNode, Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(root, 0));
        
        while (!queue.isEmpty()) {
            Pair<TreeNode, Integer> pair = queue.poll();
            TreeNode node = pair.key;
            int column = pair.value;
            
            // Add the node to its column list
            columnMap.putIfAbsent(column, new ArrayList<>());
            columnMap.get(column).add(node.val);
            
            // Add left and right children to queue with their columns
            if (node.left != null) {
                queue.add(new Pair<>(node.left, column - 1));
            }
            
            if (node.right != null) {
                queue.add(new Pair<>(node.right, column + 1));
            }
        }
        
        // Add all column lists to the result
        result.addAll(columnMap.values());
        
        return result;
    }
    
    // Helper class for pair of values
    static class Pair<K, V> {
        K key;
        V value;
        
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    /**
     * Morris traversal for in-order (without recursion or stack)
     * Space Complexity: O(1)
     * 
     * @param root the root of the binary tree
     * @return list of values in in-order
     */
    public static List<Integer> morrisInorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        
        while (current != null) {
            if (current.left == null) {
                // No left subtree, visit current and move right
                result.add(current.val);
                current = current.right;
            } else {
                // Find the inorder predecessor
                TreeNode predecessor = current.left;
                
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                if (predecessor.right == null) {
                    // Make a temporary link from predecessor to current
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Restore the tree (remove the temporary link)
                    predecessor.right = null;
                    result.add(current.val);
                    current = current.right;
                }
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        // Create a sample binary tree
        //      1
        //     / \
        //    2   3
        //   / \   \
        //  4   5   6
        //     /
        //    7
        Integer[] values = {1, 2, 3, 4, 5, null, 6, null, null, 7};
        TreeNode root = TreeNode.createFromArray(values);
        
        System.out.println("=== SAMPLE BINARY TREE ===");
        TreeNode.printTree(root);
        
        // Test different traversals
        System.out.println("\n=== DEPTH-FIRST TRAVERSALS ===");
        System.out.println("Pre-order (recursive): " + preOrderTraversal(root));
        System.out.println("In-order (recursive): " + inOrderTraversal(root));
        System.out.println("Post-order (recursive): " + postOrderTraversal(root));
        
        System.out.println("\n=== ITERATIVE TRAVERSALS ===");
        System.out.println("Pre-order (iterative): " + preOrderIterative(root));
        System.out.println("In-order (iterative): " + inOrderIterative(root));
        System.out.println("Post-order (iterative): " + postOrderIterative(root));
        
        System.out.println("\n=== BREADTH-FIRST TRAVERSAL ===");
        System.out.println("Level-order: " + levelOrderTraversal(root));
        
        System.out.println("\n=== LEVEL-ORDER BY LEVEL ===");
        List<List<Integer>> levels = levelOrderByLevel(root);
        for (int i = 0; i < levels.size(); i++) {
            System.out.println("Level " + i + ": " + levels.get(i));
        }
        
        System.out.println("\n=== ZIGZAG TRAVERSAL ===");
        List<List<Integer>> zigzag = zigzagLevelOrder(root);
        for (int i = 0; i < zigzag.size(); i++) {
            System.out.println("Level " + i + ": " + zigzag.get(i));
        }
        
        System.out.println("\n=== BOUNDARY TRAVERSAL ===");
        System.out.println("Boundary: " + boundaryTraversal(root));
        
        System.out.println("\n=== VERTICAL ORDER TRAVERSAL ===");
        List<List<Integer>> columns = verticalOrder(root);
        for (int i = 0; i < columns.size(); i++) {
            System.out.println("Column " + i + ": " + columns.get(i));
        }
        
        System.out.println("\n=== MORRIS TRAVERSAL (O(1) SPACE) ===");
        System.out.println("Morris In-order: " + morrisInorderTraversal(root));
        
        // Test edge cases
        System.out.println("\n=== EDGE CASES ===");
        System.out.println("Empty tree:");
        TreeNode emptyRoot = null;
        System.out.println("Pre-order: " + preOrderTraversal(emptyRoot));
        System.out.println("In-order: " + inOrderTraversal(emptyRoot));
        System.out.println("Post-order: " + postOrderTraversal(emptyRoot));
        System.out.println("Level-order: " + levelOrderTraversal(emptyRoot));
        
        System.out.println("\nSingle node tree:");
        TreeNode singleNode = new TreeNode(1);
        System.out.println("Pre-order: " + preOrderTraversal(singleNode));
        System.out.println("In-order: " + inOrderTraversal(singleNode));
        System.out.println("Post-order: " + postOrderTraversal(singleNode));
        System.out.println("Level-order: " + levelOrderTraversal(singleNode));
    }
}