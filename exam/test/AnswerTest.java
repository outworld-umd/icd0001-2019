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
    public void testBigTree() {
        Answer v = Answer.parseTree("A(B,C(D,F(K,L,M,N(O)),P))");
        assertThat(v.maxWidth(), is(4));
    }

    @Test
    public void testNullTree() {
        Answer v = Answer.parseTree("A");
        assertThat(v.maxWidth(), is(0));
    }
}

