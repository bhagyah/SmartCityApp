package gui;

import module2.SortingAlgorithms;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

/**
 * GUI Panel for Module 2: Data Sorter - Sorting Algorithm Comparison.
 * Provides visual interface for sorting operations and performance comparison.
 * 
 * @author 22UG1-0938 E.K.B.H.JAYARATHNA
 */
public class DataSorterGUI extends JPanel {

    private MainGUI parent;
    private JTextArea outputArea;
    private JTextField dataInputField;
    private JSpinner sizeSpinner;
    private int[] currentData = null;
    private Random random = new Random();

    public DataSorterGUI(MainGUI parent) {
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(35, 35, 55));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(70, 130, 180));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton backBtn = new JButton("< Back to Main Menu");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(new Color(50, 100, 150));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> parent.goHome());

        JLabel titleLabel = new JLabel("Module 2: Data Sorter - Sorting Comparison");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        topBar.add(backBtn, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);

        // Left panel - controls
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(40, 40, 60));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setPreferredSize(new Dimension(280, 0));

        // Data Input Section
        addSectionLabel(leftPanel, "Data Input");

        // Manual entry
        JPanel manualPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        manualPanel.setBackground(new Color(40, 40, 60));
        manualPanel.setMaximumSize(new Dimension(270, 35));
        JLabel inputLabel = new JLabel("Numbers:");
        inputLabel.setForeground(Color.WHITE);
        inputLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        dataInputField = new JTextField(14);
        dataInputField.setToolTipText("Enter comma-separated numbers (e.g., 5,3,8,1,9)");
        manualPanel.add(inputLabel);
        manualPanel.add(dataInputField);
        leftPanel.add(manualPanel);

        addButton(leftPanel, "Load Manual Data", new Color(46, 139, 87), e -> loadManualData());

        leftPanel.add(Box.createVerticalStrut(8));

        // Random generation
        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sizePanel.setBackground(new Color(40, 40, 60));
        sizePanel.setMaximumSize(new Dimension(270, 35));
        JLabel sizeLabel = new JLabel("Size:");
        sizeLabel.setForeground(Color.WHITE);
        sizeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        sizeSpinner = new JSpinner(new SpinnerNumberModel(20, 5, 100000, 10));
        sizeSpinner.setPreferredSize(new Dimension(100, 25));
        sizePanel.add(sizeLabel);
        sizePanel.add(sizeSpinner);
        leftPanel.add(sizePanel);

        addButton(leftPanel, "Generate Random Data", new Color(70, 130, 180), e -> generateRandomData());

        leftPanel.add(Box.createVerticalStrut(15));
        addSectionLabel(leftPanel, "Sort Individual");
        addButton(leftPanel, "Bubble Sort", new Color(200, 100, 50), e -> sortWith("Bubble Sort"));
        addButton(leftPanel, "Merge Sort", new Color(100, 150, 50), e -> sortWith("Merge Sort"));
        addButton(leftPanel, "Quick Sort", new Color(150, 50, 150), e -> sortWith("Quick Sort"));

        leftPanel.add(Box.createVerticalStrut(15));
        addSectionLabel(leftPanel, "Compare All");
        addButton(leftPanel, "Compare All Algorithms", new Color(200, 150, 50), e -> compareAll());

        leftPanel.add(Box.createVerticalStrut(15));
        addButton(leftPanel, "Clear Output", new Color(100, 100, 120), e -> outputArea.setText(""));

        // Output area
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        outputArea.setBackground(new Color(25, 25, 40));
        outputArea.setForeground(new Color(100, 200, 255));
        outputArea.setCaretColor(Color.WHITE);
        outputArea.setEditable(false);
        outputArea.setMargin(new Insets(10, 10, 10, 10));
        outputArea.setText("Welcome to Data Sorter!\n\nEnter numbers manually or generate a random dataset,\nthen sort using different algorithms to compare performance.\n");

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
        btn.setMaximumSize(new Dimension(260, 32));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(color.brighter()); }
            public void mouseExited(MouseEvent e) { btn.setBackground(color); }
        });
        panel.add(Box.createVerticalStrut(3));
        panel.add(btn);
    }

    // ==================== Operations ====================

    private void loadManualData() {
        String input = dataInputField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter comma-separated numbers.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String[] parts = input.split("[,\\s]+");
            currentData = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                currentData[i] = Integer.parseInt(parts[i].trim());
            }
            appendOutput("\n--- Manual Data Loaded ---");
            appendOutput("Size: " + currentData.length + " elements");
            appendOutput("Data: " + Arrays.toString(currentData));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Enter comma-separated integers.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generateRandomData() {
        int size = (int) sizeSpinner.getValue();
        currentData = new int[size];
        for (int i = 0; i < size; i++) {
            currentData[i] = random.nextInt(10000) + 1;
        }
        appendOutput("\n--- Random Data Generated ---");
        appendOutput("Size: " + size + " elements (range: 1-10000)");
        if (size <= 50) {
            appendOutput("Data: " + Arrays.toString(currentData));
        } else {
            appendOutput("First 20: " + Arrays.toString(Arrays.copyOf(currentData, 20)) + " ...");
        }
    }

    private void sortWith(String algorithm) {
        if (currentData == null) {
            JOptionPane.showMessageDialog(this, "No data loaded. Please enter or generate data first.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int[] sorted;
        long timeNano;

        switch (algorithm) {
            case "Bubble Sort":
                timeNano = System.nanoTime();
                sorted = SortingAlgorithms.bubbleSort(currentData);
                timeNano = System.nanoTime() - timeNano;
                break;
            case "Merge Sort":
                timeNano = System.nanoTime();
                sorted = SortingAlgorithms.mergeSort(currentData);
                timeNano = System.nanoTime() - timeNano;
                break;
            case "Quick Sort":
                timeNano = System.nanoTime();
                sorted = SortingAlgorithms.quickSort(currentData);
                timeNano = System.nanoTime() - timeNano;
                break;
            default:
                return;
        }

        appendOutput("\n--- " + algorithm + " Result ---");
        appendOutput("Elements: " + sorted.length);
        if (sorted.length <= 50) {
            appendOutput("Sorted: " + Arrays.toString(sorted));
        } else {
            appendOutput("First 20: " + Arrays.toString(Arrays.copyOf(sorted, 20)) + " ...");
        }
        appendOutput(String.format("Time: %,d ns (%.4f ms)", timeNano, timeNano / 1_000_000.0));
    }

    private void compareAll() {
        if (currentData == null) {
            JOptionPane.showMessageDialog(this, "No data loaded. Please enter or generate data first.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        appendOutput("\n╔═══════════════════════════════════════════════════╗");
        appendOutput("║     SORTING ALGORITHM COMPARISON                  ║");
        appendOutput("╠═══════════════════════════════════════════════════╣");
        appendOutput("  Dataset size: " + currentData.length + " elements\n");

        long bubbleTime = System.nanoTime();
        int[] bubbleSorted = SortingAlgorithms.bubbleSort(currentData);
        bubbleTime = System.nanoTime() - bubbleTime;

        long mergeTime = System.nanoTime();
        int[] mergeSorted = SortingAlgorithms.mergeSort(currentData);
        mergeTime = System.nanoTime() - mergeTime;

        long quickTime = System.nanoTime();
        int[] quickSorted = SortingAlgorithms.quickSort(currentData);
        quickTime = System.nanoTime() - quickTime;

        boolean allCorrect = Arrays.equals(bubbleSorted, mergeSorted) && Arrays.equals(mergeSorted, quickSorted);

        appendOutput(String.format("  %-15s  %15s  %12s", "Algorithm", "Time (ns)", "Time (ms)"));
        appendOutput("  --------------------------------------------------");
        appendOutput(String.format("  %-15s  %,15d  %12.4f", "Bubble Sort", bubbleTime, bubbleTime / 1_000_000.0));
        appendOutput(String.format("  %-15s  %,15d  %12.4f", "Merge Sort", mergeTime, mergeTime / 1_000_000.0));
        appendOutput(String.format("  %-15s  %,15d  %12.4f", "Quick Sort", quickTime, quickTime / 1_000_000.0));
        appendOutput("  --------------------------------------------------");

        String fastest = bubbleTime <= mergeTime && bubbleTime <= quickTime ? "Bubble Sort" :
                mergeTime <= quickTime ? "Merge Sort" : "Quick Sort";
        appendOutput("  Fastest: " + fastest);
        appendOutput("  All results match: " + (allCorrect ? "YES" : "NO"));
        appendOutput("╚═══════════════════════════════════════════════════╝");
    }

    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
}
