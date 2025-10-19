package Trees;
import java.util.*;

/**
 * Node class representing a single element in the BST.
 */
class Node {
    int val;
    Node left, right;
    int sum, size;

    Node(int val) {
        this.val = val;
        this.sum = val;
        this.size = 1;
    }
}

/**
 * Binary Search Tree (BST) implementation.
 * Supports insertion, deletion, traversal, path finding, and visualization.
 */
class BST {

    /** Inserts a new node while maintaining BST properties. */
    public Node insert(Node root, int val) {
        if (root == null) return new Node(val);

        if (val < root.val) root.left = insert(root.left, val);
        else root.right = insert(root.right, val);

        updateNode(root);
        return root;
    }

    /** Deletes a node by value from the BST. */
    public Node delete(Node root, int val) {
        if (root == null) return null;

        if (val < root.val) root.left = delete(root.left, val);
        else if (val > root.val) root.right = delete(root.right, val);
        else {
            // Node found
            if (root.left == null && root.right == null) return null; // Leaf
            else if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            else {
                // Two children: Replace with inorder successor
                Node successor = minValueNode(root.right);
                root.val = successor.val;
                root.right = delete(root.right, successor.val);
            }
        }

        updateNode(root);
        return root;
    }

    /** Finds the minimum node in a subtree (used during deletion). */
    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) current = current.left;
        return current;
    }

    /** Updates the size and sum of a node based on its children. */
    private void updateNode(Node node) {
        int leftSize = (node.left != null) ? node.left.size : 0;
        int rightSize = (node.right != null) ? node.right.size : 0;
        int leftSum = (node.left != null) ? node.left.sum : 0;
        int rightSum = (node.right != null) ? node.right.sum : 0;

        node.size = 1 + leftSize + rightSize;
        node.sum = node.val + leftSum + rightSum;
    }

    /** Preorder traversal: Root → Left → Right */
    public void preOrder(Node root) {
        if (root == null) return;
        System.out.println(root.val + " [Sum: " + root.sum + ", Size: " + root.size + "]");
        preOrder(root.left);
        preOrder(root.right);
    }

    /** Finds the path from root to a specific target node. */
    public boolean findPath(Node root, int target, List<Integer> path) {
        if (root == null) return false;

        path.add(root.val);
        if (root.val == target) return true;

        if (findPath(root.left, target, path) || findPath(root.right, target, path))
            return true;

        path.remove(path.size() - 1); // Backtrack
        return false;
    }

    /** Prints the tree visually (rotated 90° for clarity). */
    public void printTree(Node root) {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }
        System.out.println("\n--- Visual Tree (Right → Root → Left) ---");
        printTree(root, "", true);
    }

    private void printTree(Node node, String prefix, boolean isRight) {
        if (node == null) return;

        if (node.right != null)
            printTree(node.right, prefix + (isRight ? "    " : "│   "), false);

        System.out.println(prefix + (isRight ? "└── " : "┌── ") + node.val);

        if (node.left != null)
            printTree(node.left, prefix + (isRight ? "│   " : "    "), true);
    }
}

/**
 * Demonstrates BST insert, delete, path, and traversal.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BST bst = new BST();
        Node root = null;

        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();

        System.out.println("Enter " + n + " node values:");
        for (int i = 0; i < n; i++) {
            root = bst.insert(root, sc.nextInt());
        }

        System.out.println("\nPreorder Traversal:");
        bst.preOrder(root);

        System.out.print("\nEnter a value to delete: ");
        int delVal = sc.nextInt();
        root = bst.delete(root, delVal);

        System.out.println("\nPreorder Traversal after Deletion:");
        bst.preOrder(root);

        System.out.print("\nEnter a value to find path: ");
        int target = sc.nextInt();
        List<Integer> path = new ArrayList<>();
        if (bst.findPath(root, target, path))
            System.out.println("Path to " + target + ": " + path);
        else
            System.out.println("Node " + target + " not found.");

        bst.printTree(root);
        sc.close();
    }
}
