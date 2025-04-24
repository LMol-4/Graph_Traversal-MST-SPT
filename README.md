# Graph Traversal and MST/SPT Algorithms in Java

## Overview

This Java project implements several fundamental graph algorithms, including:

* **Depth First Search (DFS):** Explores a graph by going as far as possible along each branch before backtracking.
* **Breadth First Search (BFS):** Explores a graph level by level.
* **Prim's Algorithm:** Finds a minimum spanning tree (MST) for a weighted, undirected graph.
* **Dijkstra's Algorithm:** Finds the shortest-path tree (SPT) from a single source node to all other nodes in a weighted graph where edge weights are non-negative.

The code utilizes an adjacency list representation for the graph and a custom min-heap implementation to support Prim's and Dijkstra's algorithms.

## Features

* **Graph Representation:** Uses an adjacency list to store the graph, which is efficient for sparse graphs.
* **Custom Detailed Min-Heap:** Includes a `DetailedHeap` class implementing a min-priority queue used in Prim's and Dijkstra's algorithms, with added features for detailed output during operations.
* **DFS and BFS:** Implements the Depth First Search and Breadth First Search algorithms (Cormen's version).
* **Prim's Algorithm:** Computes the Minimum Spanning Tree of the input graph, providing detailed step-by-step output.
* **Dijkstra's Algorithm:** Calculates the Shortest Path Tree from a specified starting vertex, providing detailed step-by-step output.
* **Detailed Output:** Prints the traversal order for DFS and BFS. For Prim's and Dijkstra's, it provides detailed output at each step, including array states (`dist`, `parent`), heap contents, and examination of edges, before printing the final MST edges or shortest paths.
* **Input from File:** Reads graph data (number of vertices, number of edges, and edge details with weights) from a specified input file.

## Usage

1.  **Create a Graph Input File:** The program expects the graph data to be in a text file with the following format:
    ```
    [number of vertices] [number of edges]
    [vertex 1] [vertex 2] [weight of edge (1, 2)]
    [vertex 3] [vertex 4] [weight of edge (3, 4)]
    ...
    ```
    * Vertices are represented by integers starting from 1.
    * The graph is assumed to be undirected, meaning an edge between vertex `u` and `v` is the same as an edge between `v` and `u`. The input should reflect this if you intend to represent an undirected graph.
    * For Prim's and Dijkstra's algorithms, the weights should be non-negative.

    **Example `graph.txt`:**
    ```
    5 7
    1 2 2
    1 4 1
    2 3 3
    2 4 4
    2 5 5
    3 5 1
    4 5 4
    ```

2.  **Run the Program:** Execute the compiled `GraphAlgorithms` class from your terminal:
    ```bash
    java GraphAlgorithms
    ```

3.  **Provide Input:** The program will prompt you to enter:
    * The name of the graph input file (e.g., `graph.txt`).
    * The starting vertex for the graph traversals and algorithms (enter the vertex number as an integer).

## Implementation Details

* **Graph Representation:** An adjacency list (`adj` array of `Node`s with a sentinel `z`) is used to store the graph.
* **Algorithm Data Structures:** Vertex visitation status is tracked using the `C` enum (White, Grey, Black). Arrays like `parent`, `dist`, `d`, `f`, and `hPos` are used to store information for DFS, BFS, Prim's, and Dijkstra's algorithms.
* **Custom Heap:** A `DetailedHeap` class implements a min-priority queue, primarily used for Prim's and Dijkstra's, and includes helper methods for printing its internal state.
* **Vertex Display:** Vertex integers are converted to uppercase characters (A, B, C, ...) for display using the `toChar` helper function.

## Further Potential Enhancements

* Add visualization of the graph and the resulting MST or SPT.
* Implement other graph algorithms like Kruskal's algorithm for MST or Bellman-Ford for shortest paths with negative edge weights.
* Allow the user to specify if the graph is directed or undirected.
* Allow the user to use adjacency matrix for packed graphs.
