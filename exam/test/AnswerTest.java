import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class AnswerTest {

    @Test
    public void testSmallTree() {
        Answer v = Answer.parseTree("A(B(C))");
        assertThat(v.maxWidth(), is(1));
    }

    @Test
    public void testLongestInTheMiddle() {
        Answer v = Answer.parseTree("A(B,C(D,F(K,L,M,N(O)),G),P))");
        assertThat(v.maxWidth(), is(4));
    }

    @Test
    public void testLongestOnTheLastChild() {
        Answer v = Answer.parseTree("A(B,C(D,F(K,L,M,N(O)),P))");
        assertThat(v.maxWidth(), is(4));
    }

    @Test
    public void testLongestOnTheFirstChild() {
        Answer v = Answer.parseTree("A(A(A,A,A,A,A),A(A,A(A,A,A,A(A)),A))");
        assertThat(v.maxWidth(), is(5));
    }

    @Test
    public void testNullTree() {
        Answer v = Answer.parseTree("A");
        assertThat(v.maxWidth(), is(0));
    }

}

