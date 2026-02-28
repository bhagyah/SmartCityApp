# Smart City Console Application

A menu-driven, console-based Java application featuring three integrated modules for Data Structures & Algorithms.

## 📋 Project Overview

This application demonstrates the implementation and analysis of various data structures and algorithms through three interconnected modules:

### Module 1: Smart City Route Planner (Data Structures & Graphs)
- **Graph representation** using adjacency list for locations and roads
- **Binary Search Tree (BST)** for storing location data before mapping to graph
- **BFS traversal** using Queue data structure
- **DFS traversal** using Stack data structure
- **Shortest path finding** between locations
- Add/Remove locations and roads with input validation

### Module 2: Data Sorter – Sorting Algorithm Comparison Tool
- **Bubble Sort** - O(n²) comparison-based sorting
- **Merge Sort** - O(n log n) divide-and-conquer sorting
- **Quick Sort** - O(n log n) average partition-based sorting
- Manual data entry or random dataset generation
- Execution time measurement using `System.nanoTime()`
- Performance comparison table

### Module 3: Algorithm Performance Analyzer (Time Complexity)
- **Linear Search** - O(n) sequential search
- **Binary Search** - O(log n) divide-and-conquer search
- Analysis on different input sizes (100, 500, 1000, 2000, 5000)
- Tabular performance results
- Full comprehensive performance report
- Time complexity summary

## 🏗️ Project Structure

```
SmartCityApp/
├── src/
│   ├── SmartCityApplication.java    # Main entry point
│   ├── module1/
│   │   ├── BSTLocation.java         # Binary Search Tree for locations
│   │   ├── CityGraph.java           # Graph (adjacency list) implementation
│   │   └── RoutePlannerMenu.java    # Module 1 menu interface
│   ├── module2/
│   │   ├── SortingAlgorithms.java   # Bubble, Merge, Quick Sort
│   │   └── DataSorterMenu.java      # Module 2 menu interface
│   └── module3/
│       ├── SearchingAlgorithms.java  # Linear & Binary Search
│       └── PerformanceAnalyzerMenu.java  # Module 3 menu interface
└── README.md
```

## 🚀 How to Compile and Run

### Using Command Line

```bash
# Navigate to the src directory
cd SmartCityApp/src

# Compile all Java files
javac SmartCityApplication.java module1/*.java module2/*.java module3/*.java

# Run the application
java SmartCityApplication
```

### Using an IDE (IntelliJ / Eclipse / VS Code)
1. Open the `SmartCityApp` folder as a project
2. Set `src` as the source root
3. Run `SmartCityApplication.java`

## 📊 Data Structures Used

| Data Structure | Usage |
|---------------|-------|
| **Graph (Adjacency List)** | City map - locations as vertices, roads as edges |
| **Binary Search Tree (BST)** | Storing and organizing location data |
| **Queue (LinkedList)** | BFS traversal of the city graph |
| **Stack** | DFS traversal and path reconstruction |
| **Arrays** | Sorting and searching algorithm operations |
| **HashMap/LinkedHashMap** | Graph adjacency list storage |

## ⏱️ Algorithms Implemented

### Sorting Algorithms
| Algorithm | Best Case | Average Case | Worst Case | Space |
|-----------|-----------|-------------|------------|-------|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) |

### Searching Algorithms
| Algorithm | Best Case | Average Case | Worst Case | Space |
|-----------|-----------|-------------|------------|-------|
| Linear Search | O(1) | O(n) | O(n) | O(1) |
| Binary Search | O(1) | O(log n) | O(log n) | O(1) |

### Graph Algorithms
| Algorithm | Time Complexity | Data Structure Used |
|-----------|----------------|-------------------|
| BFS | O(V + E) | Queue |
| DFS | O(V + E) | Stack |
| Shortest Path (BFS) | O(V + E) | Queue + Stack |

## 📝 Assessment Criteria

| Component | Marks |
|-----------|-------|
| Correct implementation of data structures & algorithms | 30% |
| Algorithm performance measurement & analysis | 25% |
| Code quality, structure & validation | 20% |
| Team collaboration & GitHub usage | 15% |
| Demo video & explanation | 10% |

## 📜 License

This project is developed for academic purposes as part of the Data Structures & Algorithms course.

