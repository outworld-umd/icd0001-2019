/**
 * Container class to different classes, that makes the whole
 * set of classes one class formally.
 */
public class GraphTask {

    /**
     * Main method.
     */
    public static void main(String[] args) {
        GraphTask a = new GraphTask();
        a.run();
    }

    /**
     * Actual main method to run examples and everything.
     */
    public void run() {
        Graph graph = new Graph("G");
        graph.createRandomSimpleGraph(2000, 2000);
        long start = System.currentTimeMillis();
        System.out.println(graph.createTransitiveClosure());
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - start) / 1000D + " s");
    }

    /**
     * Vertex represents one vertex of the graph. Class fields are id (name),
     * next vertex of the graph, and first arc of the given vertex.
     */
    class Vertex {

        private String id;
        private Vertex next;
        private Arc first;
        private int info = 0;

        Vertex(String s, Vertex v, Arc e) {
            id = s;
            next = v;
            first = e;
        }

        Vertex(String s) {
            this(s, null, null);
        }

        @Override
        public String toString() {
            return id;
        }
    }


    /**
     * Arc represents one arrow in the graph. Two-directional edges are
     * represented by two Arc objects (for both directions).
     */
    class Arc {

        private String id;
        private Vertex target;
        private Arc next;
        private int info = 0;

        Arc(String s, Vertex v, Arc a) {
            id = s;
            target = v;
            next = a;
        }

        Arc(String s) {
            this(s, null, null);
        }

        @Override
        public String toString() {
            return id;
        }
    }


    class Graph {

        private String id;
        private Vertex first;
        private int info = 0;

        Graph(String s, Vertex v) {
            id = s;
            first = v;
        }

        Graph(String s) {
            this(s, null);
        }

        @Override
        public String toString() {
            String nl = "\n";
            StringBuilder sb = new StringBuilder(nl);
            sb.append(id);
            sb.append(nl);
            Vertex v = first;
            while (v != null) {
                sb.append(v.toString());
                sb.append(" -->");
                Arc a = v.first;
                while (a != null) {
                    sb.append(" ");
                    sb.append(a.toString());
                    sb.append(" (");
                    sb.append(v.toString());
                    sb.append("->");
                    sb.append(a.target.toString());
                    sb.append(")");
                    a = a.next;
                }
                sb.append(nl);
                v = v.next;
            }
            return sb.toString();
        }

        public Vertex createVertex(String vid) {
            Vertex res = new Vertex(vid);
            res.next = first;
            first = res;
            return res;
        }

        public Arc createArc(String aid, Vertex from, Vertex to) {
            Arc res = new Arc(aid);
            res.next = from.first;
            from.first = res;
            res.target = to;
            return res;
        }

        /**
         * Create a connected undirected random tree with n vertices.
         * Each new vertex is connected to some random existing vertex.
         * @param n number of vertices added to this graph
         */
        public void createRandomTree (int n) {
            if (n <= 0)
                return;
            Vertex[] varray = new Vertex [n];
            for (int i = 0; i < n; i++) {
                varray [i] = createVertex ("v" + String.valueOf(n-i));
                if (i > 0) {
                    int vnr = (int)(Math.random()*i);
                    createArc ("a" + varray [vnr].toString() + "_"
                            + varray [i].toString(), varray [vnr], varray [i]);
                    createArc ("a" + varray [i].toString() + "_"
                            + varray [vnr].toString(), varray [i], varray [vnr]);
                } else {}
            }
        }

        /**
         * Create an adjacency matrix of this graph.
         * Side effect: corrupts info fields in the graph
         * @return adjacency matrix
         */
        public int[][] createAdjMatrix() {
            info = 0;
            Vertex v = first;
            while (v != null) {
                v.info = info++;
                v = v.next;
            }
            int[][] res = new int[info][info];
            v = first;
            while (v != null) {
                int i = v.info;
                Arc a = v.first;
                while (a != null) {
                    int j = a.target.info;
                    res[i][j]++;
                    a = a.next;
                }
                v = v.next;
            }
            return res;
        }

        /**
         * Create a connected simple (undirected, no loops, no multiple
         * arcs) random graph with n vertices and m edges.
         * @param n number of vertices
         * @param m number of edges
         */
        public void createRandomSimpleGraph (int n, int m) {
            if (n <= 0)
                return;
            if (n > 2500)
                throw new IllegalArgumentException ("Too many vertices: " + n);
            if (m < n-1 || m > n*(n-1)/2)
                throw new IllegalArgumentException
                        ("Impossible number of edges: " + m);
            first = null;
            createRandomTree (n);       // n-1 edges created here
            Vertex[] vert = new Vertex [n];
            Vertex v = first;
            int c = 0;
            while (v != null) {
                vert[c++] = v;
                v = v.next;
            }
            int[][] connected = createAdjMatrix();
            int edgeCount = m - n + 1;  // remaining edges
            while (edgeCount > 0) {
                int i = (int)(Math.random()*n);  // random source
                int j = (int)(Math.random()*n);  // random target
                if (i==j)
                    continue;  // no loops
                if (connected [i][j] != 0 || connected [j][i] != 0)
                    continue;  // no multiple edges
                Vertex vi = vert [i];
                Vertex vj = vert [j];
                createArc ("a" + vi.toString() + "_" + vj.toString(), vi, vj);
                connected [i][j] = 1;
                createArc ("a" + vj.toString() + "_" + vi.toString(), vj, vi);
                connected [j][i] = 1;
                edgeCount--;  // a new edge happily created
            }
        }

        /**
         * Create a transitive closure of a graph.
         * Transitive closure is a result of adding to source graph all arcs (u, v), for which exists a path
         * from vertex u to vertex v in the source graph (but without new loops).
         *
         * @return graph+, transitive closure
         */
        public Graph createTransitiveClosure() {
            if (first == null) return new Graph(id + "+");
            int[][] matrix = createAdjMatrix();
            int V = matrix.length;
            for (int w = 0; w < V; w++) {
                for (int u = 0; u < V; u++) {
                    for (int v = 0; v < V; v++) {
                        if (u != v && matrix[u][v] == 0 && matrix[u][w] != 0 && matrix[w][v] != 0) matrix[u][v] = 1;
                    }
                }
            }
            Vertex[] vertices = new Vertex[V];
            Vertex vertex = first;
            int i = 0;
            vertices[i] = new Vertex(vertex.id);
            while (vertex.next != null) {
                vertices[++i] = new Vertex(vertex.next.id);
                vertices[--i].next = vertices[++i];
                vertex = vertex.next;
            }
            for (int u = 0; u < V; u++) {
                for (int v = V - 1; v >= 0; v--) {
                    for (int times = 0; times < matrix[u][v]; times++) {
                        String arcId = "a" + vertices[u].toString() + "_" + vertices[v].toString();
                        createArc(arcId, vertices[u], vertices[v]);
                    }
                }
            }
            return new Graph(id + "+", vertices[0]);
        }
    }

} 

