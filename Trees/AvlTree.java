package Trees;
import java.util.*;

// ✅ Node class for AVL Tree
class AVLNode {
    int val;            // Value of the node
    AVLNode left;       // Left child
    AVLNode right;      // Right child
    int height;         // Height of the node

    AVLNode(int val) {
        this.val = val;
        this.height = 1;  // New node is initially added at leaf
    }
}

// ✅ AVL Tree Class
class AVLTree {

    // --- Utility Methods ---

    // Get height of a node
    public int getHeight(AVLNode node) {
        if (node == null) return 0;
        return node.height;
    }

    // Update height of a node based on its children
    public void updateHeight(AVLNode node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    // Get balance factor (leftHeight - rightHeight)
    public int balanceFactor(AVLNode node) {
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    // --- Rotation Methods ---

    // Right Rotation (LL Case)
    public AVLNode rightRotate(AVLNode P) {
        AVLNode Q = P.left;    // Left child
        AVLNode T2 = Q.right;  // Subtree to be reattached

        // Perform rotation
        Q.right = P;
        P.left = T2;

        // Update heights
        updateHeight(P);
        updateHeight(Q);

        // New root after rotation
        return Q;
    }

    // Left Rotation (RR Case)
    public AVLNode leftRotate(AVLNode P) {
        AVLNode Q = P.right;   // Right child
        AVLNode T2 = Q.left;   // Subtree to be reattached

        // Perform rotation
        Q.left = P;
        P.right = T2;

        // Update heights
        updateHeight(P);
        updateHeight(Q);

        // New root after rotation
        return Q;
    }

    // --- Insertion Method ---

    public AVLNode insert(AVLNode root, int x) {
        // Step 1: Perform normal BST insertion
        if (root == null)
            return new AVLNode(x);

        if (x < root.val)
            root.left = insert(root.left, x);
        else if (x > root.val)
            root.right = insert(root.right, x);
        else
            return root; // Duplicate keys are not allowed

        // Step 2: Update height of the current node
        updateHeight(root);

        // Step 3: Get balance factor to check if unbalanced
        int bf = balanceFactor(root);

        // --- Step 4: Handle 4 rotation cases ---

        // Case 1: Left Left (LL)
        if (bf > 1 && x < root.left.val)
            return rightRotate(root);

        // Case 2: Right Right (RR)
        if (bf < -1 && x > root.right.val)
            return leftRotate(root);

        // Case 3: Left Right (LR)
        if (bf > 1 && x > root.left.val) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Case 4: Right Left (RL)
        if (bf < -1 && x < root.right.val) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        // Return the (unchanged) node pointer
        return root;
    }

    // --- Traversal Method ---

    // Preorder Traversal (Root → Left → Right)
    public void preOrder(AVLNode root) {
        if (root == null) return;
        System.out.print(root.val + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    // --- Main Method ---

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AVLTree tree = new AVLTree();
        AVLNode root = null;

        // Input: number of nodes
        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();

        // Input: node values
        System.out.println("Enter " + n + " elements:");
        while (n-- > 0) {
            int ele = sc.nextInt();
            root = tree.insert(root, ele);
        }

        // Output: Preorder traversal of balanced AVL Tree
        System.out.println("Preorder traversal of AVL tree:");
        tree.preOrder(root);
    }
}
// | Concept                | Description                                                                                                                |
// | ---------------------- | -------------------------------------------------------------------------------------------------------------------------- |
// | **AVL Tree**           | Self-balancing Binary Search Tree (BST). It maintains a balance factor (difference in left and right subtree heights ≤ 1). |
// | **Height Update**      | After every insertion, the height of each visited node is recalculated.                                                    |
// | **Rotation**           | When imbalance occurs, rotations (LL, RR, LR, RL) are performed to restore balance.                                        |
// | **Time Complexity**    | O(log n) for insertion and search due to balancing.                                                                        |
// | **Preorder Traversal** | Prints elements in the order Root → Left → Right.                                                                          |
