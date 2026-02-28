package gui;

import module1.BSTLocation;
import module1.CityGraph;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * GUI Panel for Module 1: Smart City Route Planner.
 * Provides visual interface for graph operations, BST display, and traversals.
 * 
 * @author 22UG1-0938 E.K.B.H.JAYARATHNA
 */
public class RoutePlannerGUI extends JPanel {

    private MainGUI parent;
    private CityGraph graph;
    private BSTLocation bst;
    private JTextArea outputArea;
    private JComboBox<String> locationCombo1, locationCombo2;

    public RoutePlannerGUI(MainGUI parent) {
        this.parent = parent;
        this.graph = new CityGraph();
        this.bst = new BSTLocation();
        loadSampleData();
        initUI();
    }

    private void loadSampleData() {
        String[] locations = {"Colombo", "Kandy", "Galle", "Jaffna", "Negombo",
                "Anuradhapura", "Trincomalee", "Batticaloa", "Matara", "Kurunegala"};
        for (String loc : locations) {
            bst.insert(loc);
            graph.addLocation(loc);
        }
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

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(35, 35, 55));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top bar with title and back button
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(46, 139, 87));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton backBtn = new JButton("< Back to Main Menu");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(new Color(36, 109, 67));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> parent.goHome());

        JLabel titleLabel = new JLabel("Module 1: Smart City Route Planner");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        topBar.add(backBtn, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);

        // Left panel - buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(40, 40, 60));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setPreferredSize(new Dimension(250, 0));

        // Location management section
        addSectionLabel(leftPanel, "Location Management");
        addButton(leftPanel, "Add Location", new Color(46, 139, 87), e -> addLocation());
        addButton(leftPanel, "Remove Location", new Color(180, 60, 60), e -> removeLocation());

        leftPanel.add(Box.createVerticalStrut(10));
        addSectionLabel(leftPanel, "Road Management");
        addButton(leftPanel, "Add Road", new Color(46, 139, 87), e -> addRoad());
        addButton(leftPanel, "Remove Road", new Color(180, 60, 60), e -> removeRoad());

        leftPanel.add(Box.createVerticalStrut(10));
        addSectionLabel(leftPanel, "Display");
        addButton(leftPanel, "Show All Connections", new Color(70, 130, 180), e -> showConnections());
        addButton(leftPanel, "BST In-Order (Sorted)", new Color(70, 130, 180), e -> showBSTInOrder());
        addButton(leftPanel, "BST Tree Structure", new Color(70, 130, 180), e -> showBSTTree());

        leftPanel.add(Box.createVerticalStrut(10));
        addSectionLabel(leftPanel, "Traversals & Pathfinding");

        // Combo boxes for location selection
        JPanel comboPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboPanel1.setBackground(new Color(40, 40, 60));
        comboPanel1.setMaximumSize(new Dimension(250, 35));
        JLabel fromLabel = new JLabel("From:");
        fromLabel.setForeground(Color.WHITE);
        locationCombo1 = new JComboBox<>();
        locationCombo1.setPreferredSize(new Dimension(170, 25));
        comboPanel1.add(fromLabel);
        comboPanel1.add(locationCombo1);
        leftPanel.add(comboPanel1);

        JPanel comboPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboPanel2.setBackground(new Color(40, 40, 60));
        comboPanel2.setMaximumSize(new Dimension(250, 35));
        JLabel toLabel = new JLabel("To:");
        toLabel.setForeground(Color.WHITE);
        locationCombo2 = new JComboBox<>();
        locationCombo2.setPreferredSize(new Dimension(180, 25));
        comboPanel2.add(toLabel);
        comboPanel2.add(locationCombo2);
        leftPanel.add(comboPanel2);

        updateCombos();

        addButton(leftPanel, "BFS Traversal (Queue)", new Color(178, 102, 178), e -> bfsTraversal());
        addButton(leftPanel, "DFS Traversal (Stack)", new Color(178, 102, 178), e -> dfsTraversal());
        addButton(leftPanel, "Find Shortest Path", new Color(200, 150, 50), e -> findShortestPath());

        // Output area
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setBackground(new Color(25, 25, 40));
        outputArea.setForeground(new Color(0, 255, 150));
        outputArea.setCaretColor(Color.WHITE);
        outputArea.setEditable(false);
        outputArea.setMargin(new Insets(10, 10, 10, 10));
        outputArea.setText("Welcome to Smart City Route Planner!\n\nPre-loaded with 10 Sri Lankan cities and 13 roads.\nUse the buttons on the left to interact.\n");

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 90)),
                " Output Console ",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12), new Color(150, 150, 170)));

        add(topBar, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addSectionLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(150, 200, 255));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
        panel.add(label);
    }

    private void addButton(JPanel panel, String text, Color color, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(230, 32));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(color.brighter()); }
            public void mouseExited(MouseEvent e) { btn.setBackground(color); }
        });
        panel.add(Box.createVerticalStrut(3));
        panel.add(btn);
    }

    private void updateCombos() {
        String sel1 = (String) locationCombo1.getSelectedItem();
        String sel2 = (String) locationCombo2.getSelectedItem();
        locationCombo1.removeAllItems();
        locationCombo2.removeAllItems();
        for (String loc : graph.getLocations()) {
            locationCombo1.addItem(loc);
            locationCombo2.addItem(loc);
        }
        if (sel1 != null) locationCombo1.setSelectedItem(sel1);
        if (sel2 != null) locationCombo2.setSelectedItem(sel2);
    }

    // ==================== Operations ====================

    private void addLocation() {
        String name = JOptionPane.showInputDialog(this, "Enter location name:", "Add Location", JOptionPane.PLAIN_MESSAGE);
        if (name == null || name.trim().isEmpty()) return;
        name = name.trim();
        if (!name.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(this, "Location name must contain only letters and spaces.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (name.length() < 2) {
            JOptionPane.showMessageDialog(this, "Location name must be at least 2 characters.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!bst.insert(name)) {
            JOptionPane.showMessageDialog(this, "Location '" + name + "' already exists.", "Duplicate", JOptionPane.WARNING_MESSAGE);
            return;
        }
        graph.addLocation(name);
        updateCombos();
        appendOutput("+ Location '" + name + "' added successfully! Total: " + graph.getLocationCount());
    }

    private void removeLocation() {
        String name = (String) JOptionPane.showInputDialog(this, "Select location to remove:",
                "Remove Location", JOptionPane.PLAIN_MESSAGE, null,
                graph.getLocations().toArray(), null);
        if (name == null) return;
        graph.removeLocation(name);
        bst.delete(name);
        updateCombos();
        appendOutput("- Location '" + name + "' and all its roads removed.");
    }

    private void addRoad() {
        String[] locations = graph.getLocations().toArray(new String[0]);
        if (locations.length < 2) {
            JOptionPane.showMessageDialog(this, "Need at least 2 locations to add a road.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String from = (String) JOptionPane.showInputDialog(this, "Select source location:",
                "Add Road - Step 1", JOptionPane.PLAIN_MESSAGE, null, locations, locations[0]);
        if (from == null) return;
        String to = (String) JOptionPane.showInputDialog(this, "Select destination location:",
                "Add Road - Step 2", JOptionPane.PLAIN_MESSAGE, null, locations, locations.length > 1 ? locations[1] : locations[0]);
        if (to == null) return;
        if (from.equals(to)) {
            JOptionPane.showMessageDialog(this, "Cannot create a road from a location to itself.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String distStr = JOptionPane.showInputDialog(this, "Enter distance (km):", "Add Road - Step 3", JOptionPane.PLAIN_MESSAGE);
        if (distStr == null) return;
        try {
            int dist = Integer.parseInt(distStr.trim());
            if (dist <= 0) throw new NumberFormatException();
            if (graph.addRoad(from, to, dist)) {
                appendOutput("+ Road added: " + from + " <-> " + to + " (" + dist + " km)");
            } else {
                JOptionPane.showMessageDialog(this, "Road already exists between these locations.", "Duplicate", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive number for distance.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removeRoad() {
        String[] locations = graph.getLocations().toArray(new String[0]);
        String from = (String) JOptionPane.showInputDialog(this, "Select source location:",
                "Remove Road - Step 1", JOptionPane.PLAIN_MESSAGE, null, locations, null);
        if (from == null) return;
        String to = (String) JOptionPane.showInputDialog(this, "Select destination location:",
                "Remove Road - Step 2", JOptionPane.PLAIN_MESSAGE, null, locations, null);
        if (to == null) return;
        if (graph.removeRoad(from, to)) {
            appendOutput("- Road removed between " + from + " and " + to);
        } else {
            JOptionPane.showMessageDialog(this, "No road found between these locations.", "Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showConnections() {
        // Capture graph output
        appendOutput("\n========== ALL CONNECTIONS ==========");
        StringBuilder sb = new StringBuilder();
        for (String loc : graph.getLocations()) {
            sb.append("\n[").append(loc).append("]");
        }
        // Use redirected output
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        graph.displayAllConnections();
        System.setOut(oldOut);
        appendOutput(baos.toString());
    }

    private void showBSTInOrder() {
        appendOutput("\n========== BST IN-ORDER (SORTED) ==========");
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        bst.displayInOrder();
        System.setOut(oldOut);
        appendOutput(baos.toString());
    }

    private void showBSTTree() {
        appendOutput("\n========== BST TREE STRUCTURE ==========");
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        bst.displayTree();
        System.setOut(oldOut);
        appendOutput(baos.toString());
    }

    private void bfsTraversal() {
        String start = (String) locationCombo1.getSelectedItem();
        if (start == null) {
            JOptionPane.showMessageDialog(this, "Please select a starting location.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        appendOutput("\n========== BFS TRAVERSAL (Queue) from '" + start + "' ==========");
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        graph.bfsTraversal(start);
        System.setOut(oldOut);
        appendOutput(baos.toString());
    }

    private void dfsTraversal() {
        String start = (String) locationCombo1.getSelectedItem();
        if (start == null) {
            JOptionPane.showMessageDialog(this, "Please select a starting location.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        appendOutput("\n========== DFS TRAVERSAL (Stack) from '" + start + "' ==========");
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        graph.dfsTraversal(start);
        System.setOut(oldOut);
        appendOutput(baos.toString());
    }

    private void findShortestPath() {
        String from = (String) locationCombo1.getSelectedItem();
        String to = (String) locationCombo2.getSelectedItem();
        if (from == null || to == null) {
            JOptionPane.showMessageDialog(this, "Please select both From and To locations.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (from.equals(to)) {
            JOptionPane.showMessageDialog(this, "Source and destination must be different.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        appendOutput("\n========== SHORTEST PATH: " + from + " -> " + to + " ==========");
        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        graph.findShortestPath(from, to);
        System.setOut(oldOut);
        appendOutput(baos.toString());
    }

    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
}
