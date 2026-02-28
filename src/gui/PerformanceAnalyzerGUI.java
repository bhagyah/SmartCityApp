package gui;

import module2.SortingAlgorithms;
import module3.SearchingAlgorithms;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

/**
 * GUI Panel for Module 3: Algorithm Performance Analyzer.
 * Provides visual interface for analyzing search and sort algorithm performance.
 * 
 * @author 22UG1-0938 E.K.B.H.JAYARATHNA
 */
public class PerformanceAnalyzerGUI extends JPanel {

    private MainGUI parent;
    private JTextArea outputArea;
    private Random random = new Random();
    private static final int[] DEFAULT_SIZES = {100, 500, 1000, 2000, 5000};

    public PerformanceAnalyzerGUI(MainGUI parent) {
        this.parent = parent;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(35, 35, 55));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(178, 102, 178));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton backBtn = new JButton("< Back to Main Menu");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(new Color(138, 72, 138));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> parent.goHome());

        JLabel titleLabel = new JLabel("Module 3: Algorithm Performance Analyzer");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        topBar.add(backBtn, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);

        // Left panel - buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(40, 40, 60));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setPreferredSize(new Dimension(280, 0));

        addSectionLabel(leftPanel, "Search Analysis");
        addButton(leftPanel, "Analyze Linear Search", new Color(200, 100, 50), e -> analyzeLinearSearch());
        addButton(leftPanel, "Analyze Binary Search", new Color(100, 150, 50), e -> analyzeBinarySearch());
        addButton(leftPanel, "Compare Linear vs Binary", new Color(200, 150, 50), e -> compareSearches());

        leftPanel.add(Box.createVerticalStrut(15));
        addSectionLabel(leftPanel, "Sort Analysis");
        addButton(leftPanel, "Analyze Bubble Sort", new Color(200, 80, 80), e -> analyzeSortAlgorithm("Bubble Sort"));
        addButton(leftPanel, "Analyze Merge Sort", new Color(80, 150, 80), e -> analyzeSortAlgorithm("Merge Sort"));
        addButton(leftPanel, "Analyze Quick Sort", new Color(150, 80, 150), e -> analyzeSortAlgorithm("Quick Sort"));
        addButton(leftPanel, "Compare All Sorting", new Color(70, 130, 180), e -> compareAllSorts());

        leftPanel.add(Box.createVerticalStrut(15));
        addSectionLabel(leftPanel, "Reports");
        addButton(leftPanel, "Full Performance Report", new Color(200, 150, 50), e -> fullReport());
        addButton(leftPanel, "Complexity Summary", new Color(100, 100, 160), e -> complexitySummary());

        leftPanel.add(Box.createVerticalStrut(15));
        addButton(leftPanel, "Clear Output", new Color(100, 100, 120), e -> outputArea.setText(""));

        // Output area
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        outputArea.setBackground(new Color(25, 25, 40));
        outputArea.setForeground(new Color(220, 180, 255));
        outputArea.setCaretColor(Color.WHITE);
        outputArea.setEditable(false);
        outputArea.setMargin(new Insets(10, 10, 10, 10));
        outputArea.setText("Welcome to Algorithm Performance Analyzer!\n\nAnalyze searching and sorting algorithms on different input sizes.\nClick any button to run an analysis.\n\nDefault test sizes: 100, 500, 1000, 2000, 5000 elements\n");

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
        label.setForeground(new Color(200, 180, 255));
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

    // ==================== Analysis Operations ====================

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = random.nextInt(100000) + 1;
        return arr;
    }

    private void analyzeLinearSearch() {
        appendOutput("\n========== LINEAR SEARCH PERFORMANCE ==========");
        appendOutput("Time Complexity: O(n) - Linear");
        appendOutput("Testing worst case (element not found)\n");
        appendOutput(String.format("  %-12s  %-16s  %-12s", "Input Size", "Time (ns)", "Time (ms)"));
        appendOutput("  ------------------------------------------------");

        for (int size : DEFAULT_SIZES) {
            int[] data = generateRandomArray(size);
            SearchingAlgorithms.linearSearch(data, -1); // warm up
            long total = 0;
            for (int r = 0; r < 5; r++) total += SearchingAlgorithms.timeLinearSearch(data, -1);
            long avg = total / 5;
            appendOutput(String.format("  %,10d    %,14d    %10.4f", size, avg, avg / 1_000_000.0));
        }
        appendOutput("  ------------------------------------------------");
        appendOutput("  * Averaged over 5 runs per size");
    }

    private void analyzeBinarySearch() {
        appendOutput("\n========== BINARY SEARCH PERFORMANCE ==========");
        appendOutput("Time Complexity: O(log n) - Logarithmic");
        appendOutput("Array pre-sorted before search\n");
        appendOutput(String.format("  %-12s  %-16s  %-12s", "Input Size", "Time (ns)", "Time (ms)"));
        appendOutput("  ------------------------------------------------");

        for (int size : DEFAULT_SIZES) {
            int[] data = generateRandomArray(size);
            Arrays.sort(data);
            SearchingAlgorithms.binarySearch(data, -1); // warm up
            long total = 0;
            for (int r = 0; r < 5; r++) total += SearchingAlgorithms.timeBinarySearch(data, -1);
            long avg = total / 5;
            appendOutput(String.format("  %,10d    %,14d    %10.4f", size, avg, avg / 1_000_000.0));
        }
        appendOutput("  ------------------------------------------------");
        appendOutput("  * Averaged over 5 runs per size");
    }

    private void compareSearches() {
        appendOutput("\n========== LINEAR vs BINARY SEARCH COMPARISON ==========\n");
        appendOutput(String.format("  %-12s  %-14s  %-14s  %-10s", "Input Size", "Linear (ns)", "Binary (ns)", "Speedup"));
        appendOutput("  ----------------------------------------------------------");

        for (int size : DEFAULT_SIZES) {
            int[] data = generateRandomArray(size);
            int[] sorted = data.clone();
            Arrays.sort(sorted);

            SearchingAlgorithms.linearSearch(data, -1);
            SearchingAlgorithms.binarySearch(sorted, -1);

            long linearTotal = 0, binaryTotal = 0;
            for (int r = 0; r < 5; r++) {
                linearTotal += SearchingAlgorithms.timeLinearSearch(data, -1);
                binaryTotal += SearchingAlgorithms.timeBinarySearch(sorted, -1);
            }
            long linearAvg = linearTotal / 5, binaryAvg = binaryTotal / 5;
            double speedup = binaryAvg > 0 ? (double) linearAvg / binaryAvg : 0;

            appendOutput(String.format("  %,10d    %,12d    %,12d    %8.2fx", size, linearAvg, binaryAvg, speedup));
        }
        appendOutput("  ----------------------------------------------------------");
        appendOutput("  * Speedup = Linear Time / Binary Time");
    }

    private void analyzeSortAlgorithm(String algorithm) {
        appendOutput("\n========== " + algorithm.toUpperCase() + " PERFORMANCE ==========\n");
        appendOutput(String.format("  %-12s  %-16s  %-12s", "Input Size", "Time (ns)", "Time (ms)"));
        appendOutput("  ------------------------------------------------");

        for (int size : DEFAULT_SIZES) {
            int[] warmup = generateRandomArray(size);
            timeSort(algorithm, warmup); // warm up

            long total = 0;
            for (int r = 0; r < 3; r++) {
                total += timeSort(algorithm, generateRandomArray(size));
            }
            long avg = total / 3;
            appendOutput(String.format("  %,10d    %,14d    %10.4f", size, avg, avg / 1_000_000.0));
        }
        appendOutput("  ------------------------------------------------");
        appendOutput("  * Averaged over 3 runs per size");
    }

    private void compareAllSorts() {
        appendOutput("\n========== SORTING ALGORITHM COMPARISON ==========\n");
        appendOutput(String.format("  %-12s  %-14s  %-14s  %-14s", "Input Size", "Bubble (ms)", "Merge (ms)", "Quick (ms)"));
        appendOutput("  ----------------------------------------------------------");

        for (int size : DEFAULT_SIZES) {
            int[] warmup = generateRandomArray(size);
            SortingAlgorithms.bubbleSort(warmup);
            SortingAlgorithms.mergeSort(warmup);
            SortingAlgorithms.quickSort(warmup);

            long bubbleTotal = 0, mergeTotal = 0, quickTotal = 0;
            for (int r = 0; r < 3; r++) {
                int[] d1 = generateRandomArray(size);
                int[] d2 = d1.clone();
                int[] d3 = d1.clone();
                bubbleTotal += timeSort("Bubble Sort", d1);
                mergeTotal += timeSort("Merge Sort", d2);
                quickTotal += timeSort("Quick Sort", d3);
            }

            appendOutput(String.format("  %,10d    %12.4f    %12.4f    %12.4f",
                    size,
                    (bubbleTotal / 3) / 1_000_000.0,
                    (mergeTotal / 3) / 1_000_000.0,
                    (quickTotal / 3) / 1_000_000.0));
        }
        appendOutput("  ----------------------------------------------------------");
        appendOutput("  * Averaged over 3 runs per size");
    }

    private void fullReport() {
        appendOutput("\n================================================================");
        appendOutput("          FULL ALGORITHM PERFORMANCE REPORT");
        appendOutput("================================================================");

        // Search section
        appendOutput("\n--- SECTION 1: SEARCHING ALGORITHMS ---\n");
        appendOutput(String.format("  %-12s  %-14s  %-14s  %-10s", "Input Size", "Linear (ns)", "Binary (ns)", "Speedup"));
        appendOutput("  ----------------------------------------------------------");

        for (int size : DEFAULT_SIZES) {
            int[] data = generateRandomArray(size);
            int[] sorted = data.clone();
            Arrays.sort(sorted);
            SearchingAlgorithms.linearSearch(data, -1);
            SearchingAlgorithms.binarySearch(sorted, -1);

            long linearAvg = 0, binaryAvg = 0;
            for (int r = 0; r < 5; r++) {
                linearAvg += SearchingAlgorithms.timeLinearSearch(data, -1);
                binaryAvg += SearchingAlgorithms.timeBinarySearch(sorted, -1);
            }
            linearAvg /= 5;
            binaryAvg /= 5;
            double speedup = binaryAvg > 0 ? (double) linearAvg / binaryAvg : 0;
            appendOutput(String.format("  %,10d    %,12d    %,12d    %8.2fx", size, linearAvg, binaryAvg, speedup));
        }

        // Sort section
        appendOutput("\n--- SECTION 2: SORTING ALGORITHMS ---\n");
        appendOutput(String.format("  %-12s  %-14s  %-14s  %-14s", "Input Size", "Bubble (ms)", "Merge (ms)", "Quick (ms)"));
        appendOutput("  ----------------------------------------------------------");

        for (int size : DEFAULT_SIZES) {
            int[] warmup = generateRandomArray(size);
            SortingAlgorithms.bubbleSort(warmup);
            SortingAlgorithms.mergeSort(warmup);
            SortingAlgorithms.quickSort(warmup);

            long bT = 0, mT = 0, qT = 0;
            for (int r = 0; r < 3; r++) {
                int[] d1 = generateRandomArray(size);
                bT += timeSort("Bubble Sort", d1);
                mT += timeSort("Merge Sort", d1.clone());
                qT += timeSort("Quick Sort", d1.clone());
            }
            appendOutput(String.format("  %,10d    %12.4f    %12.4f    %12.4f",
                    size, (bT / 3) / 1e6, (mT / 3) / 1e6, (qT / 3) / 1e6));
        }

        appendOutput("\n--- SECTION 3: COMPLEXITY SUMMARY ---\n");
        complexitySummaryContent();
        appendOutput("\n================================================================");
        appendOutput("  Report generated successfully!");
    }

    private void complexitySummary() {
        appendOutput("\n========== TIME COMPLEXITY SUMMARY ==========\n");
        complexitySummaryContent();
    }

    private void complexitySummaryContent() {
        appendOutput(String.format("  %-20s  %-14s  %-14s  %-14s", "Algorithm", "Best Case", "Average", "Worst Case"));
        appendOutput("  ------------------------------------------------------------------");
        appendOutput(String.format("  %-20s  %-14s  %-14s  %-14s", "Linear Search", "O(1)", "O(n)", "O(n)"));
        appendOutput(String.format("  %-20s  %-14s  %-14s  %-14s", "Binary Search", "O(1)", "O(log n)", "O(log n)"));
        appendOutput(String.format("  %-20s  %-14s  %-14s  %-14s", "Bubble Sort", "O(n)", "O(n^2)", "O(n^2)"));
        appendOutput(String.format("  %-20s  %-14s  %-14s  %-14s", "Merge Sort", "O(n log n)", "O(n log n)", "O(n log n)"));
        appendOutput(String.format("  %-20s  %-14s  %-14s  %-14s", "Quick Sort", "O(n log n)", "O(n log n)", "O(n^2)"));
        appendOutput("  ------------------------------------------------------------------");
        appendOutput(String.format("\n  %-20s  %-14s", "Algorithm", "Space"));
        appendOutput("  ------------------------------------");
        appendOutput(String.format("  %-20s  %-14s", "Linear Search", "O(1)"));
        appendOutput(String.format("  %-20s  %-14s", "Binary Search", "O(1)"));
        appendOutput(String.format("  %-20s  %-14s", "Bubble Sort", "O(1)"));
        appendOutput(String.format("  %-20s  %-14s", "Merge Sort", "O(n)"));
        appendOutput(String.format("  %-20s  %-14s", "Quick Sort", "O(log n)"));
    }

    private long timeSort(String algorithm, int[] data) {
        long start = System.nanoTime();
        switch (algorithm) {
            case "Bubble Sort": SortingAlgorithms.bubbleSort(data); break;
            case "Merge Sort": SortingAlgorithms.mergeSort(data); break;
            case "Quick Sort": SortingAlgorithms.quickSort(data); break;
        }
        return System.nanoTime() - start;
    }

    private void appendOutput(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
}
