package module3;

import module2.SortingAlgorithms;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Menu-driven interface for the Algorithm Performance Analyzer (Module 3).
 * Analyzes searching and sorting algorithms on different input sizes.
 * Measures execution time and displays results in tabular format.
 * 
 * @author 22UG1-0938 E.K.B.H.JAYARATHNA
 */
public class PerformanceAnalyzerMenu {

    // ==================== Fields ====================
    private Scanner scanner;
    private Random random;

    /** Default input sizes for analysis */
    private static final int[] DEFAULT_SIZES = {100, 500, 1000, 2000, 5000};

    // ==================== Constructor ====================
    public PerformanceAnalyzerMenu(Scanner scanner) {
        this.scanner = scanner;
        this.random = new Random();
    }

    // ==================== Main Menu ====================
    /**
     * Displays and handles the Performance Analyzer menu.
     */
    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n  ╔══════════════════════════════════════════════════╗");
            System.out.println("  ║   MODULE 3: ALGORITHM PERFORMANCE ANALYZER       ║");
            System.out.println("  ╠══════════════════════════════════════════════════╣");
            System.out.println("  ║  1. Analyze Linear Search Performance            ║");
            System.out.println("  ║  2. Analyze Binary Search Performance            ║");
            System.out.println("  ║  3. Compare Linear vs Binary Search              ║");
            System.out.println("  ║  4. Analyze Bubble Sort Performance              ║");
            System.out.println("  ║  5. Analyze Merge Sort Performance               ║");
            System.out.println("  ║  6. Analyze Quick Sort Performance               ║");
            System.out.println("  ║  7. Compare All Sorting Algorithms               ║");
            System.out.println("  ║  8. Full Performance Report (Search + Sort)      ║");
            System.out.println("  ║  9. Custom Input Sizes Analysis                  ║");
            System.out.println("  ║  0. Back to Main Menu                            ║");
            System.out.println("  ╚══════════════════════════════════════════════════╝");
            System.out.print("  Enter your choice: ");

            int choice = getValidInt();

            switch (choice) {
                case 1: analyzeLinearSearch(); break;
                case 2: analyzeBinarySearch(); break;
                case 3: compareSearchAlgorithms(); break;
                case 4: analyzeSortAlgorithm("Bubble Sort"); break;
                case 5: analyzeSortAlgorithm("Merge Sort"); break;
                case 6: analyzeSortAlgorithm("Quick Sort"); break;
                case 7: compareAllSortAlgorithms(); break;
                case 8: fullPerformanceReport(); break;
                case 9: customSizeAnalysis(); break;
                case 0: running = false; break;
                default:
                    System.out.println("  ⚠ Invalid choice. Please enter 0-9.");
            }
        }
    }

    // ==================== Search Analysis ====================

    /**
     * Analyzes Linear Search performance across different input sizes.
     */
    private void analyzeLinearSearch() {
        System.out.println("\n  ═══════════════════════════════════════════════════════");
        System.out.println("  ║   LINEAR SEARCH PERFORMANCE ANALYSIS                ║");
        System.out.println("  ═══════════════════════════════════════════════════════");
        System.out.println("  Time Complexity: O(n) - Linear");
        System.out.println("  Testing with worst case (element not found)...\n");

        System.out.println("  ┌──────────────┬──────────────────┬──────────────────┐");
        System.out.println("  │  Input Size   │  Time (nano)     │  Time (ms)       │");
        System.out.println("  ├──────────────┼──────────────────┼──────────────────┤");

        for (int size : DEFAULT_SIZES) {
            int[] data = generateRandomArray(size);
            int target = -1; // Worst case: element not found

            // Warm up JVM
            SearchingAlgorithms.linearSearch(data, target);

            // Measure average of 5 runs
            long totalTime = 0;
            int runs = 5;
            for (int r = 0; r < runs; r++) {
                totalTime += SearchingAlgorithms.timeLinearSearch(data, target);
            }
            long avgTime = totalTime / runs;

            System.out.printf("  │  %,10d  │  %,14d  │  %13.4f  │%n",
                    size, avgTime, avgTime / 1_000_000.0);
        }

        System.out.println("  └──────────────┴──────────────────┴──────────────────┘");
        System.out.println("  * Results averaged over 5 runs per size");
    }

    /**
     * Analyzes Binary Search performance across different input sizes.
     */
    private void analyzeBinarySearch() {
        System.out.println("\n  ═══════════════════════════════════════════════════════");
        System.out.println("  ║   BINARY SEARCH PERFORMANCE ANALYSIS                ║");
        System.out.println("  ═══════════════════════════════════════════════════════");
        System.out.println("  Time Complexity: O(log n) - Logarithmic");
        System.out.println("  Note: Array is pre-sorted before search.\n");

        System.out.println("  ┌──────────────┬──────────────────┬──────────────────┐");
        System.out.println("  │  Input Size   │  Time (nano)     │  Time (ms)       │");
        System.out.println("  ├──────────────┼──────────────────┼──────────────────┤");

        for (int size : DEFAULT_SIZES) {
            int[] data = generateRandomArray(size);
            Arrays.sort(data); // Pre-sort for binary search
            int target = -1; // Worst case: element not found

            // Warm up JVM
            SearchingAlgorithms.binarySearch(data, target);

            // Measure average of 5 runs
            long totalTime = 0;
            int runs = 5;
            for (int r = 0; r < runs; r++) {
                totalTime += SearchingAlgorithms.timeBinarySearch(data, target);
            }
            long avgTime = totalTime / runs;

            System.out.printf("  │  %,10d  │  %,14d  │  %13.4f  │%n",
                    size, avgTime, avgTime / 1_000_000.0);
        }

        System.out.println("  └──────────────┴──────────────────┴──────────────────┘");
        System.out.println("  * Results averaged over 5 runs per size");
    }

    /**
     * Compares Linear Search vs Binary Search performance.
     */
    private void compareSearchAlgorithms() {
        System.out.println("\n  ═══════════════════════════════════════════════════════════════════════");
        System.out.println("  ║   LINEAR SEARCH vs BINARY SEARCH - PERFORMANCE COMPARISON           ║");
        System.out.println("  ═══════════════════════════════════════════════════════════════════════");

        System.out.println("\n  ┌──────────────┬──────────────────┬──────────────────┬──────────────┐");
        System.out.println("  │  Input Size   │  Linear (nano)   │  Binary (nano)   │  Speedup     │");
        System.out.println("  ├──────────────┼──────────────────┼──────────────────┼──────────────┤");

        for (int size : DEFAULT_SIZES) {
            int[] data = generateRandomArray(size);
            int target = -1; // Worst case

            // Warm up
            SearchingAlgorithms.linearSearch(data, target);
            int[] sortedData = data.clone();
            Arrays.sort(sortedData);
            SearchingAlgorithms.binarySearch(sortedData, target);

            // Measure Linear Search (average of 5 runs)
            long linearTotal = 0;
            int runs = 5;
            for (int r = 0; r < runs; r++) {
                linearTotal += SearchingAlgorithms.timeLinearSearch(data, target);
            }
            long linearAvg = linearTotal / runs;

            // Measure Binary Search (average of 5 runs)
            long binaryTotal = 0;
            for (int r = 0; r < runs; r++) {
                binaryTotal += SearchingAlgorithms.timeBinarySearch(sortedData, target);
            }
            long binaryAvg = binaryTotal / runs;

            double speedup = binaryAvg > 0 ? (double) linearAvg / binaryAvg : 0;

            System.out.printf("  │  %,10d  │  %,14d  │  %,14d  │  %9.2fx  │%n",
                    size, linearAvg, binaryAvg, speedup);
        }

        System.out.println("  └──────────────┴──────────────────┴──────────────────┴──────────────┘");
        System.out.println("  * Speedup = Linear Time / Binary Time");
        System.out.println("  * Binary Search requires sorted array (sort time not included)");
        System.out.println("  * Results averaged over 5 runs per size");
    }

    // ==================== Sort Analysis ====================

    /**
     * Analyzes a specific sorting algorithm across different input sizes.
     * @param algorithm name of the sorting algorithm
     */
    private void analyzeSortAlgorithm(String algorithm) {
        System.out.println("\n  ═══════════════════════════════════════════════════════");
        System.out.printf("  ║   %s PERFORMANCE ANALYSIS%n", algorithm.toUpperCase());
        System.out.println("  ═══════════════════════════════════════════════════════");

        String complexity;
        switch (algorithm) {
            case "Bubble Sort": complexity = "O(n²) average & worst"; break;
            case "Merge Sort": complexity = "O(n log n) all cases"; break;
            case "Quick Sort": complexity = "O(n log n) avg, O(n²) worst"; break;
            default: complexity = "Unknown"; break;
        }
        System.out.println("  Time Complexity: " + complexity + "\n");

        System.out.println("  ┌──────────────┬──────────────────┬──────────────────┐");
        System.out.println("  │  Input Size   │  Time (nano)     │  Time (ms)       │");
        System.out.println("  ├──────────────┼──────────────────┼──────────────────┤");

        for (int size : DEFAULT_SIZES) {
            int[] data = generateRandomArray(size);

            // Warm up JVM
            runSort(algorithm, data);

            // Measure average of 3 runs
            long totalTime = 0;
            int runs = 3;
            for (int r = 0; r < runs; r++) {
                int[] freshData = generateRandomArray(size);
                totalTime += timeSort(algorithm, freshData);
            }
            long avgTime = totalTime / runs;

            System.out.printf("  │  %,10d  │  %,14d  │  %13.4f  │%n",
                    size, avgTime, avgTime / 1_000_000.0);
        }

        System.out.println("  └──────────────┴──────────────────┴──────────────────┘");
        System.out.println("  * Results averaged over 3 runs per size");
    }

    /**
     * Compares all three sorting algorithms across different input sizes.
     */
    private void compareAllSortAlgorithms() {
        System.out.println("\n  ═══════════════════════════════════════════════════════════════════════════════════");
        System.out.println("  ║   SORTING ALGORITHM COMPARISON - PERFORMANCE ANALYSIS                           ║");
        System.out.println("  ═══════════════════════════════════════════════════════════════════════════════════");

        System.out.println("\n  ┌──────────────┬──────────────────┬──────────────────┬──────────────────┐");
        System.out.println("  │  Input Size   │  Bubble (ms)     │  Merge (ms)      │  Quick (ms)      │");
        System.out.println("  ├──────────────┼──────────────────┼──────────────────┼──────────────────┤");

        for (int size : DEFAULT_SIZES) {
            // Warm up
            int[] warmup = generateRandomArray(size);
            SortingAlgorithms.bubbleSort(warmup);
            SortingAlgorithms.mergeSort(warmup);
            SortingAlgorithms.quickSort(warmup);

            // Measure each algorithm (average of 3 runs)
            long bubbleTotal = 0, mergeTotal = 0, quickTotal = 0;
            int runs = 3;

            for (int r = 0; r < runs; r++) {
                int[] data1 = generateRandomArray(size);
                int[] data2 = data1.clone();
                int[] data3 = data1.clone();

                bubbleTotal += timeSort("Bubble Sort", data1);
                mergeTotal += timeSort("Merge Sort", data2);
                quickTotal += timeSort("Quick Sort", data3);
            }

            double bubbleMs = (bubbleTotal / runs) / 1_000_000.0;
            double mergeMs = (mergeTotal / runs) / 1_000_000.0;
            double quickMs = (quickTotal / runs) / 1_000_000.0;

            System.out.printf("  │  %,10d  │  %13.4f  │  %13.4f  │  %13.4f  │%n",
                    size, bubbleMs, mergeMs, quickMs);
        }

        System.out.println("  └──────────────┴──────────────────┴──────────────────┴──────────────────┘");
        System.out.println("  * Results averaged over 3 runs per size");
        System.out.println("\n  Theoretical Complexities:");
        System.out.println("  • Bubble Sort: O(n²)       - Slow for large datasets");
        System.out.println("  • Merge Sort:  O(n log n)  - Consistent performance");
        System.out.println("  • Quick Sort:  O(n log n)  - Fast average, O(n²) worst case");
    }

    // ==================== Full Report ====================

    /**
     * Generates a comprehensive performance report for all algorithms.
     */
    private void fullPerformanceReport() {
        System.out.println("\n  ╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("  ║          FULL ALGORITHM PERFORMANCE REPORT                       ║");
        System.out.println("  ╚═══════════════════════════════════════════════════════════════════╝");

        // ---- SEARCHING ALGORITHMS ----
        System.out.println("\n  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  SECTION 1: SEARCHING ALGORITHMS");
        System.out.println("  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        System.out.println("\n  ┌──────────────┬──────────────────┬──────────────────┬──────────────┐");
        System.out.println("  │  Input Size   │  Linear (ns)     │  Binary (ns)     │  Speedup     │");
        System.out.println("  ├──────────────┼──────────────────┼──────────────────┼──────────────┤");

        for (int size : DEFAULT_SIZES) {
            int[] data = generateRandomArray(size);
            int target = -1;

            // Warm up
            SearchingAlgorithms.linearSearch(data, target);
            int[] sorted = data.clone();
            Arrays.sort(sorted);
            SearchingAlgorithms.binarySearch(sorted, target);

            long linearAvg = 0, binaryAvg = 0;
            int runs = 5;
            for (int r = 0; r < runs; r++) {
                linearAvg += SearchingAlgorithms.timeLinearSearch(data, target);
                binaryAvg += SearchingAlgorithms.timeBinarySearch(sorted, target);
            }
            linearAvg /= runs;
            binaryAvg /= runs;
            double speedup = binaryAvg > 0 ? (double) linearAvg / binaryAvg : 0;

            System.out.printf("  │  %,10d  │  %,14d  │  %,14d  │  %9.2fx  │%n",
                    size, linearAvg, binaryAvg, speedup);
        }
        System.out.println("  └──────────────┴──────────────────┴──────────────────┴──────────────┘");

        // ---- SORTING ALGORITHMS ----
        System.out.println("\n  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  SECTION 2: SORTING ALGORITHMS");
        System.out.println("  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        System.out.println("\n  ┌──────────────┬──────────────────┬──────────────────┬──────────────────┐");
        System.out.println("  │  Input Size   │  Bubble (ms)     │  Merge (ms)      │  Quick (ms)      │");
        System.out.println("  ├──────────────┼──────────────────┼──────────────────┼──────────────────┤");

        for (int size : DEFAULT_SIZES) {
            // Warm up
            int[] warmup = generateRandomArray(size);
            SortingAlgorithms.bubbleSort(warmup);
            SortingAlgorithms.mergeSort(warmup);
            SortingAlgorithms.quickSort(warmup);

            long bubbleTotal = 0, mergeTotal = 0, quickTotal = 0;
            int runs = 3;
            for (int r = 0; r < runs; r++) {
                int[] d1 = generateRandomArray(size);
                int[] d2 = d1.clone();
                int[] d3 = d1.clone();
                bubbleTotal += timeSort("Bubble Sort", d1);
                mergeTotal += timeSort("Merge Sort", d2);
                quickTotal += timeSort("Quick Sort", d3);
            }

            System.out.printf("  │  %,10d  │  %13.4f  │  %13.4f  │  %13.4f  │%n",
                    size,
                    (bubbleTotal / runs) / 1_000_000.0,
                    (mergeTotal / runs) / 1_000_000.0,
                    (quickTotal / runs) / 1_000_000.0);
        }
        System.out.println("  └──────────────┴──────────────────┴──────────────────┴──────────────────┘");

        // ---- SUMMARY ----
        System.out.println("\n  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  SECTION 3: COMPLEXITY SUMMARY");
        System.out.println("  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("\n  ┌─────────────────────┬──────────────────┬──────────────────┬──────────────────┐");
        System.out.println("  │  Algorithm           │  Best Case       │  Average Case    │  Worst Case      │");
        System.out.println("  ├─────────────────────┼──────────────────┼──────────────────┼──────────────────┤");
        System.out.println("  │  Linear Search       │  O(1)            │  O(n)            │  O(n)            │");
        System.out.println("  │  Binary Search       │  O(1)            │  O(log n)        │  O(log n)        │");
        System.out.println("  │  Bubble Sort         │  O(n)            │  O(n²)           │  O(n²)           │");
        System.out.println("  │  Merge Sort          │  O(n log n)      │  O(n log n)      │  O(n log n)      │");
        System.out.println("  │  Quick Sort          │  O(n log n)      │  O(n log n)      │  O(n²)           │");
        System.out.println("  └─────────────────────┴──────────────────┴──────────────────┴──────────────────┘");

        System.out.println("\n  ┌─────────────────────┬──────────────────┐");
        System.out.println("  │  Algorithm           │  Space Complexity│");
        System.out.println("  ├─────────────────────┼──────────────────┤");
        System.out.println("  │  Linear Search       │  O(1)            │");
        System.out.println("  │  Binary Search       │  O(1)            │");
        System.out.println("  │  Bubble Sort         │  O(1)            │");
        System.out.println("  │  Merge Sort          │  O(n)            │");
        System.out.println("  │  Quick Sort          │  O(log n)        │");
        System.out.println("  └─────────────────────┴──────────────────┘");

        System.out.println("\n  Report generated successfully!");
    }

    // ==================== Custom Size Analysis ====================

    /**
     * Allows user to specify custom input sizes for analysis.
     */
    private void customSizeAnalysis() {
        System.out.println("\n  --- Custom Input Size Analysis ---");
        System.out.print("  How many different sizes to test? ");
        int count = getValidInt();

        if (count <= 0 || count > 10) {
            System.out.println("  ⚠ Please enter a number between 1 and 10.");
            return;
        }

        int[] sizes = new int[count];
        for (int i = 0; i < count; i++) {
            System.out.print("  Enter size " + (i + 1) + ": ");
            sizes[i] = getValidInt();
            if (sizes[i] <= 0 || sizes[i] > 50000) {
                System.out.println("  ⚠ Size must be between 1 and 50,000.");
                return;
            }
        }

        System.out.println("\n  Running analysis with custom sizes...\n");

        // Sorting comparison
        System.out.println("  ┌──────────────┬──────────────────┬──────────────────┬──────────────────┐");
        System.out.println("  │  Input Size   │  Bubble (ms)     │  Merge (ms)      │  Quick (ms)      │");
        System.out.println("  ├──────────────┼──────────────────┼──────────────────┼──────────────────┤");

        for (int size : sizes) {
            int[] warmup = generateRandomArray(size);
            SortingAlgorithms.bubbleSort(warmup);
            SortingAlgorithms.mergeSort(warmup);
            SortingAlgorithms.quickSort(warmup);

            long bubbleTotal = 0, mergeTotal = 0, quickTotal = 0;
            int runs = 3;
            for (int r = 0; r < runs; r++) {
                int[] d1 = generateRandomArray(size);
                int[] d2 = d1.clone();
                int[] d3 = d1.clone();
                bubbleTotal += timeSort("Bubble Sort", d1);
                mergeTotal += timeSort("Merge Sort", d2);
                quickTotal += timeSort("Quick Sort", d3);
            }

            System.out.printf("  │  %,10d  │  %13.4f  │  %13.4f  │  %13.4f  │%n",
                    size,
                    (bubbleTotal / runs) / 1_000_000.0,
                    (mergeTotal / runs) / 1_000_000.0,
                    (quickTotal / runs) / 1_000_000.0);
        }
        System.out.println("  └──────────────┴──────────────────┴──────────────────┴──────────────────┘");

        // Search comparison
        System.out.println("\n  ┌──────────────┬──────────────────┬──────────────────┐");
        System.out.println("  │  Input Size   │  Linear (ns)     │  Binary (ns)     │");
        System.out.println("  ├──────────────┼──────────────────┼──────────────────┤");

        for (int size : sizes) {
            int[] data = generateRandomArray(size);
            int target = -1;
            int[] sorted = data.clone();
            Arrays.sort(sorted);

            // Warm up
            SearchingAlgorithms.linearSearch(data, target);
            SearchingAlgorithms.binarySearch(sorted, target);

            long linearAvg = 0, binaryAvg = 0;
            int runs = 5;
            for (int r = 0; r < runs; r++) {
                linearAvg += SearchingAlgorithms.timeLinearSearch(data, target);
                binaryAvg += SearchingAlgorithms.timeBinarySearch(sorted, target);
            }
            linearAvg /= runs;
            binaryAvg /= runs;

            System.out.printf("  │  %,10d  │  %,14d  │  %,14d  │%n",
                    size, linearAvg, binaryAvg);
        }
        System.out.println("  └──────────────┴──────────────────┴──────────────────┘");
    }

    // ==================== Helper Methods ====================

    /**
     * Generates a random integer array of the specified size.
     * @param size number of elements
     * @return random integer array
     */
    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(100000) + 1;
        }
        return arr;
    }

    /**
     * Runs a sorting algorithm (without timing).
     * @param algorithm name of the algorithm
     * @param data the array to sort
     */
    private void runSort(String algorithm, int[] data) {
        switch (algorithm) {
            case "Bubble Sort": SortingAlgorithms.bubbleSort(data); break;
            case "Merge Sort": SortingAlgorithms.mergeSort(data); break;
            case "Quick Sort": SortingAlgorithms.quickSort(data); break;
        }
    }

    /**
     * Measures execution time of a sorting algorithm.
     * @param algorithm name of the algorithm
     * @param data the array to sort
     * @return execution time in nanoseconds
     */
    private long timeSort(String algorithm, int[] data) {
        long start = System.nanoTime();
        switch (algorithm) {
            case "Bubble Sort": SortingAlgorithms.bubbleSort(data); break;
            case "Merge Sort": SortingAlgorithms.mergeSort(data); break;
            case "Quick Sort": SortingAlgorithms.quickSort(data); break;
        }
        return System.nanoTime() - start;
    }

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
