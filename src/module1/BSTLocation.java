package module1;

/**
 * Binary Search Tree (BST) for storing location data.
 * Locations are stored in the BST before being mapped into the graph.
 * Each node holds a location name (String), ordered alphabetically.
 * 
 * @author 22UG1-0938 E.K.B.H.JAYARATHNA
 */
public class BSTLocation {

    // ==================== Inner Node Class ====================
    /**
     * Represents a single node in the BST.
     */
    private class BSTNode {
        String locationName;
        BSTNode left, right;

        BSTNode(String locationName) {
            this.locationName = locationName;
            this.left = null;
            this.right = null;
        }
    }

    // ==================== Fields ====================
    private BSTNode root;
    private int size;

    // ==================== Constructor ====================
    public BSTLocation() {
        root = null;
        size = 0;
    }

    // ==================== Insert ====================
    /**
     * Inserts a new location into the BST.
     * @param location the location name to insert
     * @return true if inserted, false if already exists
     */
    public boolean insert(String location) {
        if (contains(location)) {
            return false; // Duplicate not allowed
        }
        root = insertRec(root, location);
        size++;
        return true;
    }

    private BSTNode insertRec(BSTNode node, String location) {
        if (node == null) {
            return new BSTNode(location);
        }
        int cmp = location.compareToIgnoreCase(node.locationName);
        if (cmp < 0) {
            node.left = insertRec(node.left, location);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, location);
        }
        return node;
    }

    // ==================== Delete ====================
    /**
     * Removes a location from the BST.
     * @param location the location name to remove
     * @return true if removed, false if not found
     */
    public boolean delete(String location) {
        if (!contains(location)) {
            return false;
        }
        root = deleteRec(root, location);
        size--;
        return true;
    }

    private BSTNode deleteRec(BSTNode node, String location) {
        if (node == null) {
            return null;
        }
        int cmp = location.compareToIgnoreCase(node.locationName);
        if (cmp < 0) {
            node.left = deleteRec(node.left, location);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, location);
        } else {
            // Node found - handle three cases
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            // Two children: find in-order successor
            BSTNode successor = findMin(node.right);
            node.locationName = successor.locationName;
            node.right = deleteRec(node.right, successor.locationName);
        }
        return node;
    }

    private BSTNode findMin(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // ==================== Search ====================
    /**
     * Checks if a location exists in the BST.
     * @param location the location name to search
     * @return true if found, false otherwise
     */
    public boolean contains(String location) {
        return containsRec(root, location);
    }

    private boolean containsRec(BSTNode node, String location) {
        if (node == null) return false;
        int cmp = location.compareToIgnoreCase(node.locationName);
        if (cmp == 0) return true;
        if (cmp < 0) return containsRec(node.left, location);
        return containsRec(node.right, location);
    }

    // ==================== In-Order Traversal ====================
    /**
     * Returns all locations in sorted (in-order) order.
     * @return array of location names sorted alphabetically
     */
    public String[] getInOrder() {
        String[] result = new String[size];
        int[] index = {0};
        inOrderRec(root, result, index);
        return result;
    }

    private void inOrderRec(BSTNode node, String[] result, int[] index) {
        if (node == null) return;
        inOrderRec(node.left, result, index);
        result[index[0]++] = node.locationName;
        inOrderRec(node.right, result, index);
    }

    // ==================== Display ====================
    /**
     * Displays the BST structure visually (in-order).
     */
    public void displayInOrder() {
        if (root == null) {
            System.out.println("  (No locations stored in BST)");
            return;
        }
        System.out.println("  BST Locations (In-Order / Sorted):");
        displayInOrderRec(root, "  ");
    }

    private void displayInOrderRec(BSTNode node, String indent) {
        if (node == null) return;
        displayInOrderRec(node.left, indent);
        System.out.println(indent + "-> " + node.locationName);
        displayInOrderRec(node.right, indent);
    }

    /**
     * Displays the BST tree structure.
     */
    public void displayTree() {
        if (root == null) {
            System.out.println("  (Empty BST)");
            return;
        }
        System.out.println("  BST Tree Structure:");
        displayTreeRec(root, "", true);
    }

    private void displayTreeRec(BSTNode node, String prefix, boolean isLast) {
        if (node == null) return;
        System.out.println(prefix + (isLast ? "└── " : "├── ") + node.locationName);
        String newPrefix = prefix + (isLast ? "    " : "│   ");
        if (node.left != null || node.right != null) {
            displayTreeRec(node.left, newPrefix, node.right == null);
            if (node.right != null) {
                displayTreeRec(node.right, newPrefix, true);
            }
        }
    }

    // ==================== Utility ====================
    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
