package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main GUI Window for the Smart City Application.
 * Provides a visual menu-driven interface using Java Swing.
 * 
 * @author 22UG1-0938 E.K.B.H.JAYARATHNA
 */
public class MainGUI extends JFrame {

    private JPanel mainPanel;
    private CardLayout cardLayout;
    private RoutePlannerGUI routePlannerGUI;
    private DataSorterGUI dataSorterGUI;
    private PerformanceAnalyzerGUI performanceGUI;

    public MainGUI() {
        setTitle("Smart City Application - Data Structures & Algorithms");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));

        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default
        }

        // Initialize card layout for switching panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create module panels
        routePlannerGUI = new RoutePlannerGUI(this);
        dataSorterGUI = new DataSorterGUI(this);
        performanceGUI = new PerformanceAnalyzerGUI(this);

        // Add panels to card layout
        mainPanel.add(createHomePanel(), "HOME");
        mainPanel.add(routePlannerGUI, "MODULE1");
        mainPanel.add(dataSorterGUI, "MODULE2");
        mainPanel.add(performanceGUI, "MODULE3");

        add(mainPanel);
        cardLayout.show(mainPanel, "HOME");
    }

    /**
     * Creates the home/main menu panel.
     */
    private JPanel createHomePanel() {
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(new Color(30, 30, 50));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(30, 30, 50));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        JLabel titleLabel = new JLabel("SMART CITY APPLICATION");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 200, 255));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Data Structures & Algorithms Project");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(new Color(180, 180, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(subtitleLabel);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(new Color(30, 30, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton module1Btn = createMenuButton(
                "Module 1: Smart City Route Planner",
                "Graph-based city map, BST, BFS/DFS traversals, Shortest Path",
                new Color(46, 139, 87));
        module1Btn.addActionListener(e -> showPanel("MODULE1"));

        JButton module2Btn = createMenuButton(
                "Module 2: Data Sorter",
                "Bubble Sort, Merge Sort, Quick Sort - Performance Comparison",
                new Color(70, 130, 180));
        module2Btn.addActionListener(e -> showPanel("MODULE2"));

        JButton module3Btn = createMenuButton(
                "Module 3: Algorithm Performance Analyzer",
                "Linear/Binary Search, Sorting Analysis on various input sizes",
                new Color(178, 102, 178));
        module3Btn.addActionListener(e -> showPanel("MODULE3"));

        JButton exitBtn = createMenuButton(
                "Exit Application",
                "Close the application",
                new Color(180, 60, 60));
        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });

        gbc.gridy = 0;
        buttonsPanel.add(module1Btn, gbc);
        gbc.gridy = 1;
        buttonsPanel.add(module2Btn, gbc);
        gbc.gridy = 2;
        buttonsPanel.add(module3Btn, gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(30, 20, 10, 20);
        buttonsPanel.add(exitBtn, gbc);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(30, 30, 50));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        JLabel footerLabel = new JLabel("22UG1-0938 | E.K.B.H.JAYARATHNA | Data Structures & Algorithms Project");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        footerLabel.setForeground(new Color(150, 150, 170));
        footerPanel.add(footerLabel);

        homePanel.add(titlePanel, BorderLayout.NORTH);
        homePanel.add(buttonsPanel, BorderLayout.CENTER);
        homePanel.add(footerPanel, BorderLayout.SOUTH);

        return homePanel;
    }

    /**
     * Creates a styled menu button.
     */
    private JButton createMenuButton(String title, String description, Color color) {
        JButton button = new JButton("<html><div style='text-align:center;padding:10px;'>"
                + "<b style='font-size:14px;'>" + title + "</b><br>"
                + "<span style='font-size:11px;color:#cccccc;'>" + description + "</span>"
                + "</div></html>");
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(550, 70));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    /**
     * Switches to a specific panel.
     */
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    /**
     * Returns to the home panel.
     */
    public void goHome() {
        cardLayout.show(mainPanel, "HOME");
    }

    // ==================== Main Entry Point ====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }
}
