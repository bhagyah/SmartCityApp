package module1;

import java.util.*;

/**
 * Graph representation for the Smart City Route Planner.
 * Uses an adjacency list to represent locations (vertices) and roads (edges).
 * Supports weighted edges (road distances).
 * 
 * @author 22UG1-0938 E.K.B.H.JAYARATHNA
 */
public class CityGraph {

    // ==================== Inner Edge Class ====================
    /**
     * Represents a road (edge) between two locations with a distance (weight).
     */
    public static class Edge {
        String destination;
        int distance;

        public Edge(String destination, int distance) {
            this.destination = destination;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return destination + " (distance: " + distance + " km)";
        }
    }

    // ==================== Fields ====================
    /** Adjacency list: maps each location to its list of connected edges */
    private Map<String, List<Edge>> adjacencyList;

    // ==================== Constructor ====================
    public CityGraph() {
        adjacencyList = new LinkedHashMap<>();
    }

    // ==================== Location (Vertex) Operations ====================

    /**
     * Adds a new location (vertex) to the graph.
     * @param location the location name
     * @return true if added, false if already exists
     */
    public boolean addLocation(String location) {
        if (adjacencyList.containsKey(location)) {
            return false;
        }
        adjacencyList.put(location, new ArrayList<>());
        return true;
    }

    /**
     * Removes a location and all its connected roads from the graph.
     * @param location the location name to remove
     * @return true if removed, false if not found
     */
    public boolean removeLocation(String location) {
        if (!adjacencyList.containsKey(location)) {
            return false;
        }
        // Remove all edges pointing to this location from other locations
        for (Map.Entry<String, List<Edge>> entry : adjacencyList.entrySet()) {
            entry.getValue().removeIf(edge -> edge.destination.equalsIgnoreCase(location));
        }
        // Remove the location itself
        adjacencyList.remove(location);
        return true;
    }

    /**
     * Checks if a location exists in the graph.
     * @param location the location name
     * @return true if exists
     */
    public boolean hasLocation(String location) {
        return adjacencyList.containsKey(location);
    }

    // ==================== Road (Edge) Operations ====================

    /**
     * Adds a road (undirected edge) between two locations.
     * @param from source location
     * @param to destination location
     * @param distance road distance in km
     * @return true if added, false if locations don't exist or road already exists
     */
    public boolean addRoad(String from, String to, int distance) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            return false;
        }
        if (from.equalsIgnoreCase(to)) {
            return false; // No self-loops
        }
        // Check if road already exists
        for (Edge e : adjacencyList.get(from)) {
            if (e.destination.equalsIgnoreCase(to)) {
                return false;
            }
        }
        // Add undirected edge (both directions)
        adjacencyList.get(from).add(new Edge(to, distance));
        adjacencyList.get(to).add(new Edge(from, distance));
        return true;
    }

    /**
     * Removes a road between two locations.
     * @param from source location
     * @param to destination location
     * @return true if removed, false if not found
     */
    public boolean removeRoad(String from, String to) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            return false;
        }
        boolean removed1 = adjacencyList.get(from).removeIf(e -> e.destination.equalsIgnoreCase(to));
        boolean removed2 = adjacencyList.get(to).removeIf(e -> e.destination.equalsIgnoreCase(from));
        return removed1 || removed2;
    }

    // ==================== Display Operations ====================

    /**
     * Displays all locations and their connections.
     */
    public void displayAllConnections() {
        if (adjacencyList.isEmpty()) {
            System.out.println("  (No locations in the graph)");
            return;
        }
        System.out.println("  ╔══════════════════════════════════════════════════════╗");
        System.out.println("  ║          SMART CITY - ALL CONNECTIONS                ║");
        System.out.println("  ╠══════════════════════════════════════════════════════╣");
        for (Map.Entry<String, List<Edge>> entry : adjacencyList.entrySet()) {
            String location = entry.getKey();
            List<Edge> edges = entry.getValue();
            System.out.printf("  ║  📍 %-48s║%n", location);
            if (edges.isEmpty()) {
                System.out.printf("  ║     └── %-43s║%n", "(No roads connected)");
            } else {
                for (int i = 0; i < edges.size(); i++) {
                    Edge e = edges.get(i);
                    String prefix = (i == edges.size() - 1) ? "└──" : "├──";
                    String roadInfo = prefix + " → " + e.destination + " (" + e.distance + " km)";
                    System.out.printf("  ║     %-48s║%n", roadInfo);
                }
            }
            System.out.println("  ║                                                      ║");
        }
        System.out.println("  ╚══════════════════════════════════════════════════════╝");
    }

    /**
     * Displays all locations as a list.
     */
    public void displayLocations() {
        if (adjacencyList.isEmpty()) {
            System.out.println("  (No locations in the graph)");
            return;
        }
        System.out.println("  Current Locations:");
        int i = 1;
        for (String loc : adjacencyList.keySet()) {
            System.out.println("    " + i + ". " + loc);
            i++;
        }
    }

    // ==================== Traversal Operations (Using Stack & Queue) ====================

    /**
     * Performs Breadth-First Search (BFS) using a Queue.
     * Lists all reachable locations from the start location.
     * @param start the starting location
     */
    public void bfsTraversal(String start) {
        if (!adjacencyList.containsKey(start)) {
            System.out.println("  Error: Location '" + start + "' not found.");
            return;
        }

        System.out.println("\n  ═══ BFS Traversal (Using Queue) from '" + start + "' ═══");
        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>(); // Queue for BFS

        queue.add(start);
        visited.add(start);

        int step = 1;
        while (!queue.isEmpty()) {
            String current = queue.poll();
            System.out.println("  Step " + step + ": Visited -> " + current);
            step++;

            // Add unvisited neighbors to the queue
            for (Edge edge : adjacencyList.get(current)) {
                if (!visited.contains(edge.destination)) {
                    visited.add(edge.destination);
                    queue.add(edge.destination);
                }
            }
        }
        System.out.println("  ═══ BFS Complete. Total locations visited: " + visited.size() + " ═══");
    }

    /**
     * Performs Depth-First Search (DFS) using a Stack.
     * Lists all reachable locations from the start location.
     * @param start the starting location
     */
    public void dfsTraversal(String start) {
        if (!adjacencyList.containsKey(start)) {
            System.out.println("  Error: Location '" + start + "' not found.");
            return;
        }

        System.out.println("\n  ═══ DFS Traversal (Using Stack) from '" + start + "' ═══");
        Set<String> visited = new LinkedHashSet<>();
        Stack<String> stack = new Stack<>(); // Stack for DFS

        stack.push(start);

        int step = 1;
        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                System.out.println("  Step " + step + ": Visited -> " + current);
                step++;

                // Push unvisited neighbors onto the stack (reverse order for consistent traversal)
                List<Edge> neighbors = adjacencyList.get(current);
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    if (!visited.contains(neighbors.get(i).destination)) {
                        stack.push(neighbors.get(i).destination);
                    }
                }
            }
        }
        System.out.println("  ═══ DFS Complete. Total locations visited: " + visited.size() + " ═══");
    }

    /**
     * Finds the shortest path between two locations using BFS (unweighted).
     * Uses a Queue for traversal.
     * @param from source location
     * @param to destination location
     */
    public void findShortestPath(String from, String to) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            System.out.println("  Error: One or both locations not found.");
            return;
        }

        System.out.println("\n  ═══ Finding Shortest Path: " + from + " → " + to + " ═══");

        // BFS to find shortest path
        Map<String, String> parentMap = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(from);
        visited.add(from);
        parentMap.put(from, null);

        boolean found = false;
        while (!queue.isEmpty() && !found) {
            String current = queue.poll();
            for (Edge edge : adjacencyList.get(current)) {
                if (!visited.contains(edge.destination)) {
                    visited.add(edge.destination);
                    parentMap.put(edge.destination, current);
                    queue.add(edge.destination);
                    if (edge.destination.equals(to)) {
                        found = true;
                        break;
                    }
                }
            }
        }

        if (!found) {
            System.out.println("  No path found between " + from + " and " + to);
            return;
        }

        // Reconstruct path using a Stack
        Stack<String> pathStack = new Stack<>();
        String current = to;
        int totalDistance = 0;
        while (current != null) {
            pathStack.push(current);
            String parent = parentMap.get(current);
            if (parent != null) {
                // Find the distance for this edge
                for (Edge e : adjacencyList.get(parent)) {
                    if (e.destination.equals(current)) {
                        totalDistance += e.distance;
                        break;
                    }
                }
            }
            current = parent;
        }

        // Display path from stack
        System.out.print("  Route: ");
        boolean first = true;
        while (!pathStack.isEmpty()) {
            if (!first) System.out.print(" → ");
            System.out.print(pathStack.pop());
            first = false;
        }
        System.out.println();
        System.out.println("  Total Distance: " + totalDistance + " km");
        System.out.println("  ═══════════════════════════════════════════════════");
    }

    // ==================== Utility ====================

    /**
     * Gets all location names.
     * @return set of location names
     */
    public Set<String> getLocations() {
        return adjacencyList.keySet();
    }

    /**
     * Gets the number of locations.
     * @return number of locations
     */
    public int getLocationCount() {
        return adjacencyList.size();
    }

    /**
     * Gets the total number of roads (edges).
     * @return number of roads (each undirected edge counted once)
     */
    public int getRoadCount() {
        int count = 0;
        for (List<Edge> edges : adjacencyList.values()) {
            count += edges.size();
        }
        return count / 2; // Undirected graph, each edge counted twice
    }
}
