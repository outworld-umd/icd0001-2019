import static org.junit.Assert.*;

import org.junit.Test;


public class GraphTaskTest {

    @Test
    public void testEmptyGraph() {
        GraphTask.Graph g = new GraphTask().new Graph("G");

        GraphTask.Vertex c = g.createVertex("C");
        GraphTask.Vertex b = g.createVertex("B");
        GraphTask.Vertex a = g.createVertex("A");

        String expectedEmptyGraphString = "G+\n" +
                "A -->\n" +
                "B -->\n" +
                "C -->";
        String actualEmptyGraphString = g.createTransitiveClosure().toString().strip();

        assertEquals(expectedEmptyGraphString, actualEmptyGraphString);
    }

    @Test
    public void testSimpleGraph() {
        GraphTask.Graph g = new GraphTask().new Graph("G");

        GraphTask.Vertex f = g.createVertex("F");
        GraphTask.Vertex e = g.createVertex("E");
        GraphTask.Vertex d = g.createVertex("D");
        GraphTask.Vertex c = g.createVertex("C");
        GraphTask.Vertex b = g.createVertex("B");
        GraphTask.Vertex a = g.createVertex("A");

        g.createArc("aA-C", a, c);
        g.createArc("aA-F", a, f);
        g.createArc("aB-C", b, c);
        g.createArc("aB-E", b, e);
        g.createArc("aC-A", c, a);
        g.createArc("aC-B", c, b);
        g.createArc("aC-F", c, f);
        g.createArc("aD-E", d, e);
        g.createArc("aD-F", d, f);
        g.createArc("aE-B", e, b);
        g.createArc("aE-D", e, d);
        g.createArc("aE-F", e, f);
        g.createArc("aF-A", f, a);
        g.createArc("aF-C", f, c);
        g.createArc("aF-D", f, d);
        g.createArc("aF-E", f, e);

        String expectedSimpleGraphString = "G+\n" +
                "A --> aA_B (A->B) aA_C (A->C) aA_D (A->D) aA_E (A->E) aA_F (A->F)\n" +
                "B --> aB_A (B->A) aB_C (B->C) aB_D (B->D) aB_E (B->E) aB_F (B->F)\n" +
                "C --> aC_A (C->A) aC_B (C->B) aC_D (C->D) aC_E (C->E) aC_F (C->F)\n" +
                "D --> aD_A (D->A) aD_B (D->B) aD_C (D->C) aD_E (D->E) aD_F (D->F)\n" +
                "E --> aE_A (E->A) aE_B (E->B) aE_C (E->C) aE_D (E->D) aE_F (E->F)\n" +
                "F --> aF_A (F->A) aF_B (F->B) aF_C (F->C) aF_D (F->D) aF_E (F->E)";
        String actualSimpleGraphString = g.createTransitiveClosure().toString().strip();

        assertEquals(expectedSimpleGraphString, actualSimpleGraphString);
    }

    @Test
    public void testSmallDirectedGraph() {
        GraphTask.Graph g = new GraphTask().new Graph("G");

        GraphTask.Vertex d = g.createVertex("D");
        GraphTask.Vertex c = g.createVertex("C");
        GraphTask.Vertex b = g.createVertex("B");
        GraphTask.Vertex a = g.createVertex("A");

        g.createArc("aA-B", a, b);
        g.createArc("aB-C", b, c);
        g.createArc("aB-D", b, d);
        g.createArc("aC-A", c, a);

        String expectedSmallDirectedGraphString = "G+\n" +
                "A --> aA_B (A->B) aA_C (A->C) aA_D (A->D)\n" +
                "B --> aB_A (B->A) aB_C (B->C) aB_D (B->D)\n" +
                "C --> aC_A (C->A) aC_B (C->B) aC_D (C->D)\n" +
                "D -->";
        String actualSmallDirectedGraphString = g.createTransitiveClosure().toString().strip();

        assertEquals(expectedSmallDirectedGraphString, actualSmallDirectedGraphString);
    }

    @Test
    public void testDirectedGraph() {
        GraphTask.Graph g = new GraphTask().new Graph("G");

        GraphTask.Vertex f = g.createVertex("F");
        GraphTask.Vertex e = g.createVertex("E");
        GraphTask.Vertex d = g.createVertex("D");
        GraphTask.Vertex c = g.createVertex("C");
        GraphTask.Vertex b = g.createVertex("B");
        GraphTask.Vertex a = g.createVertex("A");

        g.createArc("aA-B", a, b);
        g.createArc("aA-C", a, c);
        g.createArc("aB-D", b, d);
        g.createArc("aC-E", c, e);
        g.createArc("aC-F", c, f);
        g.createArc("aE-A", e, a);
        g.createArc("aE-B", e, b);
        g.createArc("aF-B", f, b);


        String expectedDirectedGraphString = "G+\n" +
                "A --> aA_B (A->B) aA_C (A->C) aA_D (A->D) aA_E (A->E) aA_F (A->F)\n" +
                "B --> aB_D (B->D)\n" +
                "C --> aC_A (C->A) aC_B (C->B) aC_D (C->D) aC_E (C->E) aC_F (C->F)\n" +
                "D -->\n" +
                "E --> aE_A (E->A) aE_B (E->B) aE_C (E->C) aE_D (E->D) aE_F (E->F)\n" +
                "F --> aF_B (F->B) aF_D (F->D)";
        String actualDirectedGraphString = g.createTransitiveClosure().toString().strip();

        assertEquals(expectedDirectedGraphString, actualDirectedGraphString);
}

    @Test
    public void testGraphWithLoops() {
        GraphTask.Graph g = new GraphTask().new Graph("G");

        GraphTask.Vertex e = g.createVertex("E");
        GraphTask.Vertex d = g.createVertex("D");
        GraphTask.Vertex c = g.createVertex("C");
        GraphTask.Vertex b = g.createVertex("B");
        GraphTask.Vertex a = g.createVertex("A");

        g.createArc("aA-A", a, a);
        g.createArc("aA-B", a, b);
        g.createArc("aA-C", a, c);
        g.createArc("aB-D", b, d);
        g.createArc("aC-E", c, e);
        g.createArc("aD-D", d, d);
        g.createArc("aE-A", e, a);
        g.createArc("aE-B", e, b);

        String expectedDirectedGraphString = "G+\n" +
                "A --> aA_A (A->A) aA_B (A->B) aA_C (A->C) aA_D (A->D) aA_E (A->E)\n" +
                "B --> aB_D (B->D)\n" +
                "C --> aC_A (C->A) aC_B (C->B) aC_D (C->D) aC_E (C->E)\n" +
                "D --> aD_D (D->D)\n" +
                "E --> aE_A (E->A) aE_B (E->B) aE_C (E->C) aE_D (E->D)";
        String actualDirectedGraphString = g.createTransitiveClosure().toString().strip();

        assertEquals(expectedDirectedGraphString, actualDirectedGraphString);
    }

    @Test
    public void testMultigraph() {
        GraphTask.Graph g = new GraphTask().new Graph("G");

        GraphTask.Vertex e = g.createVertex("E");
        GraphTask.Vertex d = g.createVertex("D");
        GraphTask.Vertex c = g.createVertex("C");
        GraphTask.Vertex b = g.createVertex("B");
        GraphTask.Vertex a = g.createVertex("A");

        g.createArc("aA-B", a, b);
        g.createArc("aA-B", a, b);
        g.createArc("aA-E", a, e);
        g.createArc("aB-C", b, c);
        g.createArc("aB-C", b, c);
        g.createArc("aC-D", c, d);
        g.createArc("aC-D", c, d);
        g.createArc("aC-D", c, d);
        g.createArc("aC-E", c, e);
        g.createArc("aD-D", d, d);
        g.createArc("aE-D", e, d);
        g.createArc("aE-E", e, e);

        String expectedDirectedGraphString = "G+\n" +
                "A --> aA_B (A->B) aA_B (A->B) aA_C (A->C) aA_D (A->D) aA_E (A->E)\n" +
                "B --> aB_C (B->C) aB_C (B->C) aB_D (B->D) aB_E (B->E)\n" +
                "C --> aC_D (C->D) aC_D (C->D) aC_D (C->D) aC_E (C->E)\n" +
                "D --> aD_D (D->D)\n" +
                "E --> aE_D (E->D) aE_E (E->E)";
        String actualDirectedGraphString = g.createTransitiveClosure().toString().strip();

        assertEquals(expectedDirectedGraphString, actualDirectedGraphString);
    }

}

