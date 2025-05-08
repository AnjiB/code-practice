package com.interview.problems.datastructures;

/**
 * Definition for a binary tree node.
 * This class represents a node in a binary tree.
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    
    public TreeNode() {
    }
    
    public TreeNode(int val) {
        this.val = val;
    }
    
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
    
    /**
     * Creates a binary tree from an array representation where null elements represent empty nodes
     * For example, [1, 2, 3, null, 4] represents:
     *    1
     *   / \
     *  2   3
     *   \
     *    4
     * 
     * @param values the array of integer values with null for empty nodes
     * @return the root of the created binary tree
     */
    public static TreeNode createFromArray(Integer[] values) {
        if (values == null || values.length == 0 || values[0] == null) {
            return null;
        }
        
        TreeNode root = new TreeNode(values[0]);
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        
        for (int i = 1; i < values.length; i++) {
            TreeNode current = queue.poll();
            
            if (values[i] != null) {
                current.left = new TreeNode(values[i]);
                queue.offer(current.left);
            }
            
            i++;
            if (i < values.length && values[i] != null) {
                current.right = new TreeNode(values[i]);
                queue.offer(current.right);
            }
        }
        
        return root;
    }
    
    /**
     * Convert a binary tree to its array representation
     * 
     * @param root the root of the binary tree
     * @return the array representation of the tree
     */
    public static Integer[] toArray(TreeNode root) {
        if (root == null) {
            return new Integer[0];
        }
        
        java.util.List<Integer> result = new java.util.ArrayList<>();
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        
        // Count trailing nulls
        int lastNonNullIndex = -1;
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            if (node != null) {
                result.add(node.val);
                lastNonNullIndex = result.size() - 1;
                queue.offer(node.left);
                queue.offer(node.right);
            } else {
                result.add(null);
                queue.offer(null);
                queue.offer(null);
            }
            
            // Break if queue only contains nulls
            boolean allNull = true;
            for (TreeNode n : queue) {
                if (n != null) {
                    allNull = false;
                    break;
                }
            }
            
            if (allNull) {
                break;
            }
        }
        
        // Remove trailing nulls
        return result.subList(0, lastNonNullIndex + 1).toArray(new Integer[0]);
    }
    
    /**
     * Print the binary tree in a readable format
     * 
     * @param root the root of the binary tree
     */
    public static void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        
        // Get tree height
        int height = getHeight(root);
        
        // Calculate width of the tree
        int width = (1 << height) - 1;
        
        // Create a 2D array to represent the tree
        String[][] treeArray = new String[height][width];
        for (String[] row : treeArray) {
            java.util.Arrays.fill(row, " ");
        }
        
        // Fill the array with tree nodes
        fillTreeArray(treeArray, root, 0, 0, width - 1);
        
        // Print the tree
        for (String[] row : treeArray) {
            for (String cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }
    
    private static void fillTreeArray(String[][] treeArray, TreeNode node, int row, int left, int right) {
        if (node == null) {
            return;
        }
        
        int mid = (left + right) / 2;
        treeArray[row][mid] = String.valueOf(node.val);
        
        fillTreeArray(treeArray, node.left, row + 1, left, mid - 1);
        fillTreeArray(treeArray, node.right, row + 1, mid + 1, right);
    }
    
    private static int getHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
    
    /**
     * Print the tree in level order (Breadth-First)
     * 
     * @param root the root of the binary tree
     */
    public static void printLevelOrder(TreeNode root) {
        if (root == null) {
            System.out.println("[]");
            return;
        }
        
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        
        System.out.print("[");
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                if (node != null) {
                    System.out.print(node.val);
                    
                    queue.offer(node.left);
                    queue.offer(node.right);
                } else {
                    System.out.print("null");
                }
                
                if (!queue.isEmpty()) {
                    System.out.print(", ");
                }
            }
        }
        
        System.out.println("]");
    }
    
    /**
     * Print the tree in-order (Left, Root, Right)
     * 
     * @param root the root of the binary tree
     */
    public static void printInOrder(TreeNode root) {
        System.out.print("In-order: [");
        inOrderTraversal(root);
        System.out.println("]");
    }
    
    private static void inOrderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }
        
        inOrderTraversal(node.left);
        System.out.print(node.val + " ");
        inOrderTraversal(node.right);
    }
    
    /**
     * Print the tree pre-order (Root, Left, Right)
     * 
     * @param root the root of the binary tree
     */
    public static void printPreOrder(TreeNode root) {
        System.out.print("Pre-order: [");
        preOrderTraversal(root);
        System.out.println("]");
    }
    
    private static void preOrderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }
        
        System.out.print(node.val + " ");
        preOrderTraversal(node.left);
        preOrderTraversal(node.right);
    }
    
    /**
     * Print the tree post-order (Left, Right, Root)
     * 
     * @param root the root of the binary tree
     */
    public static void printPostOrder(TreeNode root) {
        System.out.print("Post-order: [");
        postOrderTraversal(root);
        System.out.println("]");
    }
    
    private static void postOrderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }
        
        postOrderTraversal(node.left);
        postOrderTraversal(node.right);
        System.out.print(node.val + " ");
    }
}