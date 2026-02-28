import module1.RoutePlannerMenu;
import module2.DataSorterMenu;
import module3.PerformanceAnalyzerMenu;

import java.util.Scanner;

/**
 * ╔═══════════════════════════════════════════════════════════════════╗
 * ║              SMART CITY CONSOLE APPLICATION                      ║
 * ║                                                                   ║
 * ║  A menu-driven, console-based Java application with three         ║
 * ║  integrated modules:                                              ║
 * ║                                                                   ║
 * ║  Module 1: Smart City Route Planner (Graphs & Data Structures)   ║
 * ║  Module 2: Data Sorter (Sorting Algorithm Comparison)            ║
 * ║  Module 3: Algorithm Performance Analyzer (Time Complexity)      ║
 * ║                                                                   ║
 * ║  Team Members:                                                    ║
 * ║  Member 1 - Graph implementation, location/road management       ║
 * ║  Member 2 - Sorting algorithms, performance measurement          ║
 * ║  Member 3 - Searching algorithms, tree-based data, integration   ║
 * ╚═══════════════════════════════════════════════════════════════════╝
 * 
 * @author 22UG1-0938 E.K.B.H.JAYARATHNA
 * @version 1.0
 */
public class SmartCityApplication {

    // ==================== Fields ====================
    private static Scanner scanner = new Scanner(System.in);

    // ==================== Main Method ====================
    public static void main(String[] args) {
        // Initialize module menus
        RoutePlannerMenu routePlanner = new RoutePlannerMenu(scanner);
        DataSorterMenu dataSorter = new DataSorterMenu(scanner);
        PerformanceAnalyzerMenu performanceAnalyzer = new PerformanceAnalyzerMenu(scanner);

        // Display welcome banner
        displayWelcomeBanner();

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getValidInt();

            switch (choice) {
                case 1:
                    routePlanner.showMenu();
                    break;
                case 2:
                    dataSorter.showMenu();
                    break;
                case 3:
                    performanceAnalyzer.showMenu();
                    break;
                case 4:
                    displayAbout();
                    break;
                case 0:
                    running = false;
                    displayExitBanner();
                    break;
                default:
                    System.out.println("  ⚠ Invalid choice. Please enter a number between 0 and 4.");
            }
        }

        scanner.close();
    }

    // ==================== Menu Display ====================

    /**
     * Displays the welcome banner.
     */
    private static void displayWelcomeBanner() {
        System.out.println();
        System.out.println("  ╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("  ║                                                               ║");
        System.out.println("  ║     ███████╗███╗   ███╗ █████╗ ██████╗ ████████╗              ║");
        System.out.println("  ║     ██╔════╝████╗ ████║██╔══██╗██╔══██╗╚══██╔══╝              ║");
        System.out.println("  ║     ███████╗██╔████╔██║███████║██████╔╝   ██║                 ║");
        System.out.println("  ║     ╚════██║██║╚██╔╝██║██╔══██║██╔══██╗   ██║                 ║");
        System.out.println("  ║     ███████║██║ ╚═╝ ██║██║  ██║██║  ██║   ██║                 ║");
        System.out.println("  ║     ╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝                 ║");
        System.out.println("  ║                                                               ║");
        System.out.println("  ║          ██████╗██╗████████╗██╗   ██╗                         ║");
        System.out.println("  ║         ██╔════╝██║╚══██╔══╝╚██╗ ██╔╝                         ║");
        System.out.println("  ║         ██║     ██║   ██║    ╚████╔╝                          ║");
        System.out.println("  ║         ██║     ██║   ██║     ╚██╔╝                           ║");
        System.out.println("  ║         ╚██████╗██║   ██║      ██║                            ║");
        System.out.println("  ║          ╚═════╝╚═╝   ╚═╝      ╚═╝                            ║");
        System.out.println("  ║                                                               ║");
        System.out.println("  ║         SMART CITY CONSOLE APPLICATION v1.0                   ║");
        System.out.println("  ║         Data Structures & Algorithms Project                  ║");
        System.out.println("  ║                                                               ║");
        System.out.println("  ╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    /**
     * Displays the main menu.
     */
    private static void displayMainMenu() {
        System.out.println("\n  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║              MAIN MENU                           ║");
        System.out.println("  ╠══════════════════════════════════════════════════╣");
        System.out.println("  ║                                                  ║");
        System.out.println("  ║  1. 🗺  Smart City Route Planner (Module 1)     ║");
        System.out.println("  ║  2. 📊 Data Sorter - Sorting Comparison (Mod 2) ║");
        System.out.println("  ║  3. ⏱  Algorithm Performance Analyzer (Mod 3)   ║");
        System.out.println("  ║  4. ℹ  About This Application                  ║");
        System.out.println("  ║  0. 🚪 Exit                                     ║");
        System.out.println("  ║                                                  ║");
        System.out.println("  ╚══════════════════════════════════════════════════╝");
        System.out.print("  Enter your choice: ");
    }

    /**
     * Displays information about the application.
     */
    private static void displayAbout() {
        System.out.println("\n  ╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("  ║                    ABOUT THIS APPLICATION                     ║");
        System.out.println("  ╠═══════════════════════════════════════════════════════════════╣");
        System.out.println("  ║                                                               ║");
        System.out.println("  ║  Smart City Console Application v1.0                          ║");
        System.out.println("  ║  Data Structures & Algorithms Group Project                   ║");
        System.out.println("  ║                                                               ║");
        System.out.println("  ║  MODULE 1: Smart City Route Planner                           ║");
        System.out.println("  ║  • Graph-based city map (adjacency list)                      ║");
        System.out.println("  ║  • BST for location data storage                              ║");
        System.out.println("  ║  • BFS (Queue) and DFS (Stack) traversals                     ║");
        System.out.println("  ║  • Shortest path finding                                      ║");
        System.out.println("  ║  • Add/Remove locations and roads                             ║");
        System.out.println("  ║                                                               ║");
        System.out.println("  ║  MODULE 2: Data Sorter                                        ║");
        System.out.println("  ║  • Bubble Sort, Merge Sort, Quick Sort                        ║");
        System.out.println("  ║  • Manual data entry or random generation                     ║");
        System.out.println("  ║  • Execution time measurement                                 ║");
        System.out.println("  ║  • Performance comparison table                               ║");
        System.out.println("  ║                                                               ║");
        System.out.println("  ║  MODULE 3: Algorithm Performance Analyzer                     ║");
        System.out.println("  ║  • Linear Search and Binary Search analysis                   ║");
        System.out.println("  ║  • Sorting algorithm performance on various sizes              ║");
        System.out.println("  ║  • Tabular results with time complexity info                  ║");
        System.out.println("  ║  • Full performance report generation                         ║");
        System.out.println("  ║                                                               ║");
        System.out.println("  ║  STUDENT:                                                      ║");
        System.out.println("  ║  22UG1-0938 - E.K.B.H.JAYARATHNA                              ║");
        System.out.println("  ║                                                               ║");
        System.out.println("  ╚═══════════════════════════════════════════════════════════════╝");
    }

    /**
     * Displays the exit banner.
     */
    private static void displayExitBanner() {
        System.out.println("\n  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║                                                  ║");
        System.out.println("  ║   Thank you for using Smart City Application!    ║");
        System.out.println("  ║   Goodbye! 👋                                   ║");
        System.out.println("  ║                                                  ║");
        System.out.println("  ╚══════════════════════════════════════════════════╝");
    }

    // ==================== Input Validation ====================
    /**
     * Reads and validates integer input from the user.
     * @return valid integer, or -1 if invalid
     */
    private static int getValidInt() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("  ⚠ Invalid input. Please enter a valid number.");
            return -1;
        }
    }
}
