import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class NodeTest {

    @Test
    public void testMaxWidthSmallTree() {
        Node v = Node.parseTree("A(B(C))");
        assertThat(v.maxWidth(), is(1));
    }

    @Test
    public void testMaxWidthLongestInTheMiddle() {
        Node v = Node.parseTree("A(B,C(D,F(K,L,M,N(O)),G),P))");
        assertThat(v.maxWidth(), is(4));
    }

    @Test
    public void testMaxWidthLongestOnTheLastChild() {
        Node v = Node.parseTree("A(B,C(D,F(K,L,M,N(O)),P))");
        assertThat(v.maxWidth(), is(4));
    }

    @Test
    public void testMaxWidthLongestOnTheFirstChild() {
        Node v = Node.parseTree("A(A(A,A,A,A,A),A(A,A(A,A,A,A(A)),A))");
        assertThat(v.maxWidth(), is(5));
    }

    @Test
    public void testMaxWidthNullTree() {
        Node v = Node.parseTree("A");
        assertThat(v.maxWidth(), is(0));
    }

    @Test
    public void testCountLeavesNormalTree() {
        Node v = Node.parseTree ("A(B,C(D,F(K,L,M,N(O)),P))");
        assertThat(v.numberOfLeaves(), is(7));
    }

    @Test
    public void testCountLeavesOneNode() {
        Node v = Node.parseTree ("A");
        assertThat(v.numberOfLeaves(), is(1));
    }

}

