# Graph Traversal and MST/SPT Algorithms in Java

## Overview

This Java project implements several fundamental graph algorithms, including:

* **Depth First Search (DFS):** Explores a graph by going as far as possible along each branch before backtracking.
* **Breadth First Search (BFS):** Explores a graph level by level.
* **Prim's Algorithm:** Finds a minimum spanning tree (MST) for a weighted, undirected graph.
* **Dijkstra's Algorithm:** Finds the shortest-path tree (SPT) from a single source node to all other nodes in a weighted graph where edge weights are non-negative.

The code utilizes an adjacency list representation for the graph and a custom min-heap implementation to efficiently support Prim's and Dijkstra's algorithms.

## Features

* **Graph Representation:** Uses an adjacency list to store the graph, which is efficient for sparse graphs.
* **Custom Min-Heap:** Includes a `Heap` class implementing a min-priority queue used in Prim's and Dijkstra's algorithms.
* **DFS and BFS:** Implements the classic Depth First Search and Breadth First Search algorithms.
* **Prim's Algorithm:** Computes the Minimum Spanning Tree of the input graph.
* **Dijkstra's Algorithm:** Calculates the Shortest Path Tree from a specified starting vertex.
* **Clear Output:** Prints the traversal order for DFS and BFS, the edges of the MST found by Prim's algorithm, and the shortest paths from the source vertex found by Dijkstra's algorithm.
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

2.  **Run the Program:** Execute the compiled `GraphLists` class from your terminal:
    ```bash
    java GraphLists
    ```

3.  **Provide Input:** The program will prompt you to enter:
    * The name of the graph input file (e.g., `graph.txt`).
    * The starting vertex for the graph traversals and algorithms (enter the vertex number as an integer).

## Output

The program will output the results of each algorithm to the console:

* **Depth First Search (Cormen's version):** Prints the order in which vertices are visited during the DFS traversal, starting from the specified vertex.
* **Breadth First Search (Cormen's version):** Prints the order in which vertices are visited during the BFS traversal, starting from the specified vertex.
* **Prim's Algorithm:** Prints the edges included in the Minimum Spanning Tree found by Prim's algorithm, starting the search from the specified vertex.
* **Dijkstra's Algorithm:** Prints the shortest paths from the specified starting vertex to all other reachable vertices in the graph.

## Implementation Details

* **Adjacency List:** The `Graph` class uses an array of `Node` objects (`adj`) to represent the adjacency list. Each element `adj[v]` points to the head of the linked list containing the neighbors of vertex `v`.
* **Sentinel Node:** A sentinel node (`z`) is used at the end of each adjacency list to simplify the traversal logic.
* **Heap:** The `Heap` class implements a binary min-heap. It stores vertex indices based on their current shortest distance (`dist`) and maintains the position of each vertex in the heap using the `hPos` array. The `siftUp` and `siftDown` methods ensure the min-heap property is maintained during insertions and removals.
* **Vertex Coloring:** The `C` enum (`White`, `Grey`, `Black`) is used in the DFS and BFS implementations to track the visitation status of vertices.
* **Vertex Representation:** Vertices are internally represented by integers. The `toChar(int u)` helper function is used to display vertices as uppercase characters (A, B, C, ...), assuming the number of vertices is within the range that can be represented by these characters.

## Further Potential Enhancements 

* Add visualization of the graph and the resulting MST or SPT.
* Implement other graph algorithms like Kruskal's algorithm for MST or Bellman-Ford for shortest paths with negative edge weights.
* Allow the user to specify if the graph is directed or undirected.
* Allow the user to use adjacency matrix for packed graphs.
