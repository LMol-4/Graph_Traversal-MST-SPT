// Luke Molony - C23339021
// 4/4/25

// Imports
import java.io.*;
import java.util.*;

// Enum to represent colours of vertices during graph traversal
enum C {White, Grey, Black}; // White: not visited, Grey: currently visiting, Black: finished visiting

// heap class
class Heap {
    private int[] a;      // heap elements (vertex indices)
    private int[] hPos;   // position of each vertex in the heap
    private int[] dist;   // current shortest distance to each vertex
    private int N;        // number of elements in the heap

    public Heap(int maxSize, int[] _dist, int[] _hPos) {
        N = 0;
        a = new int[maxSize + 1]; // heap array 1 indexed
        dist = _dist;
        hPos = _hPos; // (largest)
    }

    public boolean isEmpty() {
        return N == 0;
    }

    // moves element up in the heap to maintain the min-heap property
    public void siftUp(int k) {
        int v = a[k]; // vertex to sift up

        // while current node not root and its distance less than parent's distance
        while (k > 1 && dist[v] < dist[a[k / 2]]) {
            a[k] = a[k / 2];       // move parent down
            hPos[a[k]] = k;        // update the parents position in the heap
            k = k / 2;             // move up to parent
        }
        a[k] = v;                  // place the sifting vertex in correct position
        hPos[v] = k;               // update vertex position in heap
    }

    // moves element down in the heap to maintain the min-heap property
    public void siftDown(int k) {
        int v, j;
        v = a[k];

        // while the current node has at least one child
        while (2 * k <= N) {
            j = 2 * k; // left child index
            // if right child and has a smaller distance than the left child
            if (j < N && dist[a[j + 1]] < dist[a[j]]) {
                j++; // right child index
            }
            // if current vertex distance is greater than its smaller childs distance
            if (dist[v] > dist[a[j]]) {
                a[k] = a[j];       // child up
                hPos[a[k]] = k;
                k = j;             // move to child   
            } else {
                break; // satisified
            }
        }
        a[k] = v;
        hPos[v] = k;
    }

    // insert to heap
    public void insert(int x) {
        a[++N] = x;
        siftUp(N);
    }

    // remove root from heap
    public int remove() {
        int v = a[1];      // root (remember 1 indexed)
        hPos[v] = 0;       // mark removed
        a[1] = a[N--];     // move last element to root and decrease heap size
        siftDown(1);
        return v;
    }
}

// graph using adjacency lists
class Graph {
    // node in the adjacency list
    class Node {
        public int vert;
        public int wgt;
        public Node next;
    }

    private int V, E;       // vertices and edges
    private Node[] adj;     // (indexed by vertex number)
    private Node z;         // Sentinel node
    private int[] mst;      // array to store the parent of each vertex in MST
    private C[] colour;     // cormens implementation
    private int[] parent, d, f; // parent, discovery time, finish time for DFS/BFS
    private int time;      // Time counter for DFS

    // build from input file
    public Graph(String graphFile) throws IOException {
        int u, v, e, wgt;
        Node t;

        BufferedReader reader = new BufferedReader(new FileReader(graphFile));

        // header
        String line = reader.readLine();
        String[] parts = line.split(" +"); // split line by spaces

        V = Integer.parseInt(parts[0]); // vertices
        E = Integer.parseInt(parts[1]); // edges

        // sentinel node
        z = new Node();
        z.next = z;

        // adjacency list array
        adj = new Node[V + 1];
        for (v = 1; v <= V; ++v)
            adj[v] = z; // Each adjacency list starts with the sentinel

        // edges
        for (e = 1; e <= E; ++e) {
            line = reader.readLine();
            parts = line.split(" +");
            u = Integer.parseInt(parts[0]); // vertex from
            v = Integer.parseInt(parts[1]); // vertex to
            wgt = Integer.parseInt(parts[2]); // weight

            // add edge (u, v) with weight to the adjacency list of u
            t = new Node();
            t.vert = v;
            t.wgt = wgt;
            t.next = adj[u];
            adj[u] = t;

            // add edge (v, u) with weight to the adjacency list of v
            t = new Node();
            t.vert = u;
            t.wgt = wgt;
            t.next = adj[v];
            adj[v] = t;
        }
        reader.close();
    }

    // Depth First Search (Cormen's version)
    public void DF(int s) {
        // Initilisations
        colour = new C[V + 1];
        parent = new int[V + 1];
        d = new int[V + 1];
        f = new int[V + 1];
        time = 0;

        Arrays.fill(colour, C.White); // all white (not visited)
        Arrays.fill(parent, 0);    // parent of all vertices as null

        System.out.println("\nDepth First Graph Traversal (Cormen's version)");
        dfVisit(s);

        // explore any remaining unvisited vertices
        for (int v = 1; v <= V; ++v) {
            if (colour[v] == C.White)
                dfVisit(v);
        }
    }

    // recurisve helper for dfs
    private void dfVisit(int v) {
        time++;
        d[v] = time; // record discovery tim
        colour[v] = C.Grey; // grey (currently visiting)
        System.out.println("DF visited " + toChar(v));

        // neighbors of the current vertex
        for (Node t = adj[v]; t != z; t = t.next) {
            if (colour[t.vert] == C.White) { // if not visited
                parent[t.vert] = v;
                dfVisit(t.vert); // recursively visit the neighbor
            }
        }

        colour[v] = C.Black; // black (finished visiting)
        time++;
        f[v] = time; //finish time
    }

    // Breadth First Search (Cormen's version)
    public void BF(int s) {
        // init
        colour = new C[V + 1];
        parent = new int[V + 1];
        d = new int[V + 1];
        Queue<Integer> queue = new LinkedList<>(); // queue

        Arrays.fill(colour, C.White);
        Arrays.fill(parent, 0);

        System.out.println("\nBreadth First Graph Traversal (Cormen's version)");
        colour[s] = C.Grey; // source vertex grey
        d[s] = 0;           // Distance to source is 0
        queue.add(s);       // enqueuee source

        // While not empty
        while (!queue.isEmpty()) {
            int v = queue.poll(); // dequeu (poll)
            System.out.println("BF visited " + toChar(v));

            // Iterate through neighbors of dequeued vertex
            for (Node t = adj[v]; t != z; t = t.next) {
                if (colour[t.vert] == C.White) {
                    colour[t.vert] = C.Grey;
                    parent[t.vert] = v;
                    d[t.vert] = d[v] + 1;
                    queue.add(t.vert);
                }
            }
            colour[v] = C.Black;
        }
    }

    // Prims algorithm for MST
    public void MST_Prim(int s) {
        int[] dist = new int[V + 1]; // store the minimum distance in MST
        int[] parent = new int[V + 1];
        int[] hPos = new int[V + 1];
        Node t;

        Arrays.fill(dist, Integer.MAX_VALUE); // infinity
        dist[s] = 0;

        Heap h = new Heap(V, dist, hPos); // create heap
        h.insert(s);
        System.out.println("\nPrim's Algorithm:");

        // While heap not empty
        while (!h.isEmpty()) {
            int v = h.remove(); // min distance
            System.out.println("MST Prim: Removed vertex " + toChar(v) + " from heap.");

            // iterate through the neighbors of theremoved vertex
            for (t = adj[v]; t != z; t = t.next) {
                int u = t.vert;
                int wgt = t.wgt;

                // if the neighbor is still in the heap (hPos[u] > 0) and the current edge weight is smaller than its current distance
                if (hPos[u] > 0 && wgt < dist[u]) {
                    dist[u] = wgt;       // update the distance to the neighbor
                    parent[u] = v;       // set the parent of the neighbor in the MST
                    h.siftUp(hPos[u]);   // update the neighbor's priority in the heap
                    System.out.println("MST Prim: Updated distance of " + toChar(u) + " to " + wgt + ", parent is " + toChar(v) + ".");
                } else if (hPos[u] == 0 && dist[u] == Integer.MAX_VALUE) {
                    dist[u] = wgt;
                    parent[u] = v;
                    h.insert(u);
                    System.out.println("MST Prim: Inserted " + toChar(u) + " into heap with distance " + wgt + ", parent is " + toChar(v) + ".");
                }
            }
            hPos[v] = 0; // mark vertex v as no longer in the heap
        }

        mst = parent;
        System.out.println("\nMinimum Spanning Tree:");
        printMST();
    }

    // Dijkstras algorithm to find the SPT
    public void SPT_Dijkstra(int s) {
        int[] dist = new int[V + 1];
        int[] parent = new int[V + 1];
        int[] hPos = new int[V + 1];
        Node t;

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[s] = 0;

        Heap h = new Heap(V, dist, hPos);
        h.insert(s);
        System.out.println("\nDijkstra's Algorithm:");

        while (!h.isEmpty()) {
            int v = h.remove(); // extract min from heap
            System.out.println("SPT Dijkstra: Removed vertex " + toChar(v) + " from heap.");

            // iterate through neighbouts
            for (t = adj[v]; t != z; t = t.next) {
                int u = t.vert;
                int wgt = t.wgt;

                // if shorter path to the neighbor is found
                if (dist[v] + wgt < dist[u]) {
                    dist[u] = dist[v] + wgt; // update the shortest distance
                    parent[u] = v;           // set the parent of the neighbor in the SPT
                    if (hPos[u] == 0) {
                        h.insert(u); // Insert into heap if not already there
                        System.out.println("SPT Dijkstra: Inserted " + toChar(u) + " into heap with distance " + dist[u] + ", parent is " + toChar(v) + ".");
                    } else {
                        h.siftUp(hPos[u]); // Update priority in heap
                        System.out.println("SPT Dijkstra: Updated distance of " + toChar(u) + " to " + dist[u] + ", parent is " + toChar(v) + ".");
                    }
                }
            }
            hPos[v] = 0;
        }

        System.out.println("\nShortest Path Tree (from source " + toChar(s) + "):");
        printSPT(s, parent);
    }

    // helper function for pretty display
    private char toChar(int u) {
        return (char) (u + 64);
    }

    // helper function to print MST
    private void printMST() {
        for (int i = 1; i <= V; i++) {
            if (mst[i] != 0) {
                System.out.println("Edge: " + toChar(mst[i]) + " -- " + toChar(i));
            }
        }
    }

    // helper function to print SPT
    private void printSPT(int startNode, int[] parent) {
        for (int i = 1; i <= V; i++) {
            if (i != startNode) {
                System.out.print("Shortest path to " + toChar(i) + ": ");
                printPath(startNode, i, parent);
                System.out.println();
            }
        }
    }

    // recursive helper function to print the path
    private void printPath(int start, int end, int[] parent) {
        if (end == start) {
            System.out.print(toChar(start));
        } else if (parent[end] == 0) {
            System.out.print("No path");
        } else {
            printPath(start, parent[end], parent);
            System.out.print(" -> " + toChar(end));
        }
    }
}

public class GraphLists {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("|Luke's Graph Traveral Implementation|\n");
        System.out.print("Enter graph file: ");
        String fname = scanner.nextLine();
        System.out.print("Enter starting vertex (as number): ");
        int s = scanner.nextInt();
        scanner.close();

        Graph g = new Graph(fname);
        g.DF(s);
        g.BF(s);
        g.MST_Prim(s);
        g.SPT_Dijkstra(s);
    }
}