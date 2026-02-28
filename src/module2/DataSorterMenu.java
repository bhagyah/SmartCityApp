package module2;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Menu-driven interface for the Data Sorter module (Module 2).
 * Allows users to enter data manually or generate random datasets,
 * then sort using Bubble Sort, Merge Sort, and Quick Sort with timing comparison.
 * 
 * @author 22UG1-0938 E.K.B.H.JAYARATHNA
 */
public class DataSorterMenu {

    // ==================== Fields ====================
    private Scanner scanner;
    private Random random;

    // ==================== Constructor ====================
    public DataSorterMenu(Scanner scanner) {
        this.scanner = scanner;
        this.random = new Random();
    }

    // ==================== Main Menu ====================
    /**
     * Displays and handles the Data Sorter menu.
     */
    public void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n  ╔══════════════════════════════════════════════════╗");
            System.out.println("  ║     MODULE 2: DATA SORTER - SORTING COMPARISON   ║");
            System.out.println("  ╠══════════════════════════════════════════════════╣");
            System.out.println("  ║  1. Enter Numbers Manually                      ║");
            System.out.println("  ║  2. Generate Random Dataset                     ║");
            System.out.println("  ║  3. Sort with Bubble Sort Only                  ║");
            System.out.println("  ║  4. Sort with Merge Sort Only                   ║");
            System.out.println("  ║  5. Sort with Quick Sort Only                   ║");
            System.out.println("  ║  6. Compare All Sorting Algorithms              ║");
            System.out.println("  ║  0. Back to Main Menu                           ║");
            System.out.println("  ╚══════════════════════════════════════════════════╝");
            System.out.print("  Enter your choice: ");

            int choice = getValidInt();

            switch (choice) {
                case 1: manualEntry(); break;
                case 2: randomDataset(); break;
                case 3: sortWithAlgorithm("Bubble Sort"); break;
                case 4: sortWithAlgorithm("Merge Sort"); break;
                case 5: sortWithAlgorithm("Quick Sort"); break;
                case 6: compareAllAlgorithms(); break;
                case 0: running = false; break;
                default:
                    System.out.println("  ⚠ Invalid choice. Please enter 0-6.");
            }
        }
    }

    // ==================== Data Input Methods ====================

    /** Stores the current dataset */
    private int[] currentData = null;

    /**
     * Allows user to enter numbers manually.
     */
    private void manualEntry() {
        System.out.println("\n  --- Manual Data Entry ---");
        System.out.print("  How many numbers do you want to enter? ");
        int count = getValidInt();

        if (count <= 0) {
            System.out.println("  ⚠ Count must be a positive number.");
            return;
        }
        if (count > 10000) {
            System.out.println("  ⚠ Maximum 10,000 numbers allowed for manual entry.");
            return;
        }

        currentData = new int[count];
        System.out.println("  Enter " + count + " numbers (one per line or space-separated):");

        for (int i = 0; i < count; i++) {
            System.out.print("    Number " + (i + 1) + ": ");
            int num = getValidInt();
            if (num == Integer.MIN_VALUE) {
                System.out.println("  ⚠ Invalid input. Please try again.");
                i--; // Retry this index
                continue;
            }
            currentData[i] = num;
        }

        System.out.println("\n  ✓ " + count + " numbers entered successfully!");
        displayArray("  Original Data", currentData);
    }

    /**
     * Generates a random dataset.
     */
    private void randomDataset() {
        System.out.println("\n  --- Generate Random Dataset ---");
        System.out.print("  Enter the number of elements to generate: ");
        int count = getValidInt();

        if (count <= 0) {
            System.out.println("  ⚠ Count must be a positive number.");
            return;
        }
        if (count > 100000) {
            System.out.println("  ⚠ Maximum 100,000 elements allowed.");
            return;
        }

        System.out.print("  Enter maximum value for random numbers (default 10000): ");
        String maxInput = scanner.nextLine().trim();
        int maxVal = 10000;
        if (!maxInput.isEmpty()) {
            try {
                maxVal = Integer.parseInt(maxInput);
                if (maxVal <= 0) maxVal = 10000;
            } catch (NumberFormatException e) {
                maxVal = 10000;
            }
        }

        currentData = new int[count];
        for (int i = 0; i < count; i++) {
            currentData[i] = random.nextInt(maxVal) + 1;
        }

        System.out.println("\n  ✓ Generated " + count + " random numbers (range: 1 to " + maxVal + ")");
        if (count <= 50) {
            displayArray("  Generated Data", currentData);
        } else {
            System.out.println("  (Dataset too large to display. First 20 elements shown)");
            displayArray("  First 20 elements", Arrays.copyOf(currentData, 20));
        }
    }

    // ==================== Sorting Operations ====================

    /**
     * Sorts the current dataset with a specific algorithm.
     * @param algorithm name of the sorting algorithm
     */
    private void sortWithAlgorithm(String algorithm) {
        if (currentData == null || currentData.length == 0) {
            System.out.println("  ⚠ No data loaded. Please enter data manually or generate a random dataset first.");
            return;
        }

        System.out.println("\n  --- Sorting with " + algorithm + " ---");
        System.out.println("  Dataset size: " + currentData.length + " elements");

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
                System.out.println("  ⚠ Unknown algorithm.");
                return;
        }

        // Display results
        if (sorted.length <= 50) {
            displayArray("  Sorted Output", sorted);
        } else {
            System.out.println("  (Sorted dataset too large to display fully)");
            displayArray("  First 20 sorted elements", Arrays.copyOf(sorted, 20));
            displayArray("  Last 20 sorted elements", Arrays.copyOfRange(sorted, sorted.length - 20, sorted.length));
        }

        System.out.println("\n  ┌─────────────────────────────────────────┐");
        System.out.printf("  │  Algorithm: %-28s │%n", algorithm);
        System.out.printf("  │  Elements:  %-28d │%n", sorted.length);
        System.out.printf("  │  Time:      %-28s │%n", formatTime(timeNano));
        System.out.println("  └─────────────────────────────────────────┘");
    }

    /**
     * Compares all three sorting algorithms on the current dataset.
     */
    private void compareAllAlgorithms() {
        if (currentData == null || currentData.length == 0) {
            System.out.println("  ⚠ No data loaded. Please enter data manually or generate a random dataset first.");
            return;
        }

        System.out.println("\n  ═══════════════════════════════════════════════════════");
        System.out.println("  ║   SORTING ALGORITHM COMPARISON                      ║");
        System.out.println("  ═══════════════════════════════════════════════════════");
        System.out.println("  Dataset size: " + currentData.length + " elements\n");

        // Run each algorithm and measure time
        System.out.println("  Running Bubble Sort...");
        long bubbleTime = System.nanoTime();
        int[] bubbleSorted = SortingAlgorithms.bubbleSort(currentData);
        bubbleTime = System.nanoTime() - bubbleTime;

        System.out.println("  Running Merge Sort...");
        long mergeTime = System.nanoTime();
        int[] mergeSorted = SortingAlgorithms.mergeSort(currentData);
        mergeTime = System.nanoTime() - mergeTime;

        System.out.println("  Running Quick Sort...");
        long quickTime = System.nanoTime();
        int[] quickSorted = SortingAlgorithms.quickSort(currentData);
        quickTime = System.nanoTime() - quickTime;

        // Verify all produce same result
        boolean allCorrect = Arrays.equals(bubbleSorted, mergeSorted) && Arrays.equals(mergeSorted, quickSorted);

        // Display sorted output (if small enough)
        if (currentData.length <= 30) {
            displayArray("  Sorted Output", bubbleSorted);
        }

        // Display comparison table
        System.out.println("\n  ╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("  ║              PERFORMANCE COMPARISON TABLE                        ║");
        System.out.println("  ╠═══════════════════╦═══════════════════╦═════════════════════════╣");
        System.out.println("  ║    Algorithm       ║   Time (nano)     ║   Time (ms)             ║");
        System.out.println("  ╠═══════════════════╬═══════════════════╬═════════════════════════╣");
        System.out.printf("  ║  Bubble Sort       ║  %,15d  ║  %20.4f  ║%n", bubbleTime, bubbleTime / 1_000_000.0);
        System.out.printf("  ║  Merge Sort        ║  %,15d  ║  %20.4f  ║%n", mergeTime, mergeTime / 1_000_000.0);
        System.out.printf("  ║  Quick Sort        ║  %,15d  ║  %20.4f  ║%n", quickTime, quickTime / 1_000_000.0);
        System.out.println("  ╠═══════════════════╩═══════════════════╩═════════════════════════╣");

        // Determine fastest
        String fastest;
        if (bubbleTime <= mergeTime && bubbleTime <= quickTime) fastest = "Bubble Sort";
        else if (mergeTime <= quickTime) fastest = "Merge Sort";
        else fastest = "Quick Sort";

        String slowest;
        if (bubbleTime >= mergeTime && bubbleTime >= quickTime) slowest = "Bubble Sort";
        else if (mergeTime >= quickTime) slowest = "Merge Sort";
        else slowest = "Quick Sort";

        System.out.printf("  ║  Fastest: %-55s║%n", fastest);
        System.out.printf("  ║  Slowest: %-55s║%n", slowest);
        System.out.printf("  ║  All results match: %-45s║%n", allCorrect ? "YES ✓" : "NO ✗");
        System.out.printf("  ║  Dataset size: %-50d║%n", currentData.length);
        System.out.println("  ╚═════════════════════════════════════════════════════════════════╝");

        // Theoretical complexity reminder
        System.out.println("\n  ┌─────────────────────────────────────────────────────┐");
        System.out.println("  │  Theoretical Time Complexities:                     │");
        System.out.println("  │  • Bubble Sort: O(n²) average & worst case          │");
        System.out.println("  │  • Merge Sort:  O(n log n) all cases                │");
        System.out.println("  │  • Quick Sort:  O(n log n) avg, O(n²) worst case    │");
        System.out.println("  └─────────────────────────────────────────────────────┘");
    }

    // ==================== Utility Methods ====================

    /**
     * Displays an array with a label.
     * @param label description label
     * @param arr the array to display
     */
    private void displayArray(String label, int[] arr) {
        System.out.print(label + ": [");
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(arr[i]);
        }
        System.out.println("]");
    }

    /**
     * Formats nanoseconds into a readable string.
     * @param nanos time in nanoseconds
     * @return formatted time string
     */
    private String formatTime(long nanos) {
        if (nanos < 1_000) {
            return nanos + " ns";
        } else if (nanos < 1_000_000) {
            return String.format("%.2f µs", nanos / 1_000.0);
        } else if (nanos < 1_000_000_000) {
            return String.format("%.4f ms", nanos / 1_000_000.0);
        } else {
            return String.format("%.4f s", nanos / 1_000_000_000.0);
        }
    }

    /**
     * Reads and validates integer input.
     * @return valid integer, or Integer.MIN_VALUE if invalid
     */
    private int getValidInt() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("  ⚠ Invalid input. Please enter a valid number.");
            return Integer.MIN_VALUE;
        }
    }
}
