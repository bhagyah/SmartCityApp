package module1;

import java.util.Scanner;

/**
 * Menu-driven interface for the Smart City Route Planner (Module 1).
 * Integrates BST for location storage and Graph for route management.
 * 
 * @author 22UG1-0938 E.K.B.H.JAYARATHNA
 */
public class RoutePlannerMenu {

    // ==================== Fields ====================
    private CityGraph graph;
    private BSTLocation bst;
    private Scanner scanner;

    // ==================== Constructor ====================
    public RoutePlannerMenu(Scanner scanner) {
        this.graph = new CityGraph();
        this.bst = new BSTLocation();
        this.scanner = scanner;
        loadSampleData(); // Load sample data for demonstration
    }

    // ==================== Sample Data ====================
    /**
     * Loads sample locations and roads for demonstration purposes.
     */
    private void loadSampleData() {
        // Add locations to BST first, then map to graph
        String[] sampleLocations = {
            "Colombo", "Kandy", "Galle", "Jaffna", "Negombo",
            "Anuradhapura", "Trincomalee", "Batticaloa", "Matara", "Kurunegala"
        };

        for (String loc : sampleLocations) {
            bst.insert(loc);
            graph.addLocation(loc);
        }

        // Add sample roads with distances (km)
        graph.addRoad("Colombo", "Kandy", 115);
        graph.addRoad("Colombo", "Galle", 126);
        graph.addRoad("Colombo", "Negombo", 37);
        graph.addRoad("Colombo", "Kurunegala", 94);
        graph.addRoad("Kandy", "Kurunegala", 42);
        graph.addRoad("Kandy", "Anuradhapura", 138);
        graph.addRoad("Kandy", "Trincomalee", 206);
        graph.addRoad("Kandy", "Batticaloa", 230);
        graph.addRoad("Galle", "Matara", 46);
        graph.addRoad("Jaffna", "Anuradhapura", 200);
        graph.addRoad("Anuradhapura", "Trincomalee", 110);
        graph.addRoad("Trincomalee", "Batticaloa", 114);
        graph.addRoad("Negombo", "Kurunegala", 78);
    }

    // ==================== Main Menu ====================
    /**
     * Displays and handles the Route Planner menu.
     */
    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n  ╔══════════════════════════════════════════════════╗");
            System.out.println("  ║     MODULE 1: SMART CITY ROUTE PLANNER           ║");
            System.out.println("  ╠══════════════════════════════════════════════════╣");
            System.out.println("  ║  1. Add a Location                               ║");
            System.out.println("  ║  2. Remove a Location                            ║");
            System.out.println("  ║  3. Add a Road (Edge)                            ║");
            System.out.println("  ║  4. Remove a Road (Edge)                         ║");
            System.out.println("  ║  5. Display All Connections                      ║");
            System.out.println("  ║  6. Display Locations (BST In-Order)             ║");
            System.out.println("  ║  7. Display BST Tree Structure                   ║");
            System.out.println("  ║  8. BFS Traversal (Queue-based)                  ║");
            System.out.println("  ║  9. DFS Traversal (Stack-based)                  ║");
            System.out.println("  ║  10. Find Shortest Path                          ║");
            System.out.println("  ║  0. Back to Main Menu                            ║");
            System.out.println("  ╚══════════════════════════════════════════════════╝");
            System.out.print("  Enter your choice: ");

            int choice = getValidInt();

            switch (choice) {
                case 1: addLocation(); break;
                case 2: removeLocation(); break;
                case 3: addRoad(); break;
                case 4: removeRoad(); break;
                case 5: graph.displayAllConnections(); break;
                case 6: bst.displayInOrder(); break;
                case 7: bst.displayTree(); break;
                case 8: bfsTraversal(); break;
                case 9: dfsTraversal(); break;
                case 10: findShortestPath(); break;
                case 0: running = false; break;
                default:
                    System.out.println("  ⚠ Invalid choice. Please enter 0-10.");
            }
        }
    }

    // ==================== Menu Operations ====================

    /**
     * Adds a new location to both BST and Graph.
     */
    private void addLocation() {
        System.out.println("\n  --- Add a New Location ---");
        System.out.print("  Enter location name: ");
        String name = scanner.nextLine().trim();

        // Input validation
        if (name.isEmpty()) {
            System.out.println("  ⚠ Location name cannot be empty.");
            return;
        }
        if (name.length() < 2) {
            System.out.println("  ⚠ Location name must be at least 2 characters.");
            return;
        }
        if (!name.matches("[a-zA-Z\\s]+")) {
            System.out.println("  ⚠ Location name must contain only letters and spaces.");
            return;
        }

        // Insert into BST first
        boolean bstResult = bst.insert(name);
        if (!bstResult) {
            System.out.println("  ⚠ Location '" + name + "' already exists.");
            return;
        }

        // Then add to graph
        graph.addLocation(name);
        System.out.println("  ✓ Location '" + name + "' added successfully!");
        System.out.println("  Total locations: " + graph.getLocationCount());
    }

    /**
     * Removes a location from both BST and Graph.
     */
    private void removeLocation() {
        System.out.println("\n  --- Remove a Location ---");
        graph.displayLocations();
        System.out.print("  Enter location name to remove: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("  ⚠ Location name cannot be empty.");
            return;
        }

        if (!graph.hasLocation(name)) {
            System.out.println("  ⚠ Location '" + name + "' not found.");
            return;
        }

        graph.removeLocation(name);
        bst.delete(name);
        System.out.println("  ✓ Location '" + name + "' and all its roads removed successfully!");
    }

    /**
     * Adds a road between two locations.
     */
    private void addRoad() {
        System.out.println("\n  --- Add a Road ---");
        graph.displayLocations();

        System.out.print("  Enter source location: ");
        String from = scanner.nextLine().trim();
        System.out.print("  Enter destination location: ");
        String to = scanner.nextLine().trim();

        // Validate locations exist
        if (!graph.hasLocation(from)) {
            System.out.println("  ⚠ Source location '" + from + "' not found.");
            return;
        }
        if (!graph.hasLocation(to)) {
            System.out.println("  ⚠ Destination location '" + to + "' not found.");
            return;
        }
        if (from.equalsIgnoreCase(to)) {
            System.out.println("  ⚠ Cannot create a road from a location to itself.");
            return;
        }

        System.out.print("  Enter distance (km): ");
        int distance = getValidInt();
        if (distance <= 0) {
            System.out.println("  ⚠ Distance must be a positive number.");
            return;
        }

        boolean result = graph.addRoad(from, to, distance);
        if (result) {
            System.out.println("  ✓ Road added: " + from + " ↔ " + to + " (" + distance + " km)");
        } else {
            System.out.println("  ⚠ Road already exists between " + from + " and " + to + ".");
        }
    }

    /**
     * Removes a road between two locations.
     */
    private void removeRoad() {
        System.out.println("\n  --- Remove a Road ---");
        graph.displayLocations();

        System.out.print("  Enter source location: ");
        String from = scanner.nextLine().trim();
        System.out.print("  Enter destination location: ");
        String to = scanner.nextLine().trim();

        boolean result = graph.removeRoad(from, to);
        if (result) {
            System.out.println("  ✓ Road removed between " + from + " and " + to + ".");
        } else {
            System.out.println("  ⚠ Road not found between " + from + " and " + to + ".");
        }
    }

    /**
     * Performs BFS traversal from a user-specified location.
     */
    private void bfsTraversal() {
        System.out.println("\n  --- BFS Traversal (Queue-based) ---");
        graph.displayLocations();
        System.out.print("  Enter starting location: ");
        String start = scanner.nextLine().trim();
        graph.bfsTraversal(start);
    }

    /**
     * Performs DFS traversal from a user-specified location.
     */
    private void dfsTraversal() {
        System.out.println("\n  --- DFS Traversal (Stack-based) ---");
        graph.displayLocations();
        System.out.print("  Enter starting location: ");
        String start = scanner.nextLine().trim();
        graph.dfsTraversal(start);
    }

    /**
     * Finds shortest path between two locations.
     */
    private void findShortestPath() {
        System.out.println("\n  --- Find Shortest Path ---");
        graph.displayLocations();
        System.out.print("  Enter source location: ");
        String from = scanner.nextLine().trim();
        System.out.print("  Enter destination location: ");
        String to = scanner.nextLine().trim();
        graph.findShortestPath(from, to);
    }

    // ==================== Input Validation ====================
    /**
     * Reads and validates integer input.
     * @return valid integer, or -1 if invalid
     */
    private int getValidInt() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("  ⚠ Invalid input. Please enter a valid number.");
            return -1;
        }
    }
}
