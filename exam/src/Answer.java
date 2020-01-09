import java.util.*;



public class Answer {

    private String   name;
    private Answer   firstChild;
    private Answer   nextSibling;

    Answer (String n, Answer d, Answer r) {
        setName (n);
        setFirstChild (d);
        setNextSibling (r);
    }

    public void   setName (String n)        { name = n; }
    public String getName()                 { return name; }
    public void   setFirstChild (Answer d)  { firstChild = d; }
    public Answer getFirstChild()           { return firstChild; }
    public void   setNextSibling (Answer r) { nextSibling = r; }
    public Answer getNextSibling()          { return nextSibling; }

    @Override
    public String toString() {
        return leftParentheticRepresentation();
    }

    public String leftParentheticRepresentation() {
        StringBuffer b = new StringBuffer();
        b.append (getName());
        if (getFirstChild() != null) {
            b.append ("(");
            b.append (getFirstChild().leftParentheticRepresentation());
            Answer right = getFirstChild().getNextSibling();
            while (right != null) {
                b.append ("," + right.leftParentheticRepresentation());
                right = right.getNextSibling();
            }
            b.append (")");
        }
        return b.toString();
    }

    public static Answer parseTree (String s) {
        if (s == null) return null;
        if (s.length() == 0) return null;
        Answer root = null;
        Answer curr = null;
        Answer last = null;
        int state = 0; // begin
        Stack<Answer> stk = new Stack<Answer>();
        StringTokenizer tok = new StringTokenizer (s, "(),", true);
        while (tok.hasMoreTokens()) {
            String w = tok.nextToken().trim();
            if (w.equals ("(")) {
                state = 1; // from up
            } else if (w.equals (",")) {
                state = 2; // from left
            } else if (w.equals (")")) {
                state = 3; // from down
                stk.pop();
            } else {
                curr = new Answer (w, null, null);
                switch (state) {
                    case 0: {
                        root = curr;
                        break;
                    }
                    case 1: {
                        last = stk.peek();
                        last.setFirstChild (curr);
                        break;
                    }
                    case 2: {
                        last = stk.pop();
                        last.setNextSibling (curr);
                        break;
                    }
                    default: {
                        // do not pop here but after ")"
                    }
                } // switch
                stk.push (curr);
            }
        } // next w
        return root;
    }

    private int maxWidth = 0;

    private void countWidth(Answer a) {
        if (a.firstChild == null) return;
        int count = 1;
        Answer child = a.firstChild;
        while (child.nextSibling != null) {
            count++;
            child = child.nextSibling;
            countWidth(child);
        }
        maxWidth = Math.max(maxWidth, count);
    }

    public int maxWidth() {
        countWidth(this);
        return maxWidth;
    }

    public static void main (String[] param) {
        Answer v = Answer.parseTree ("A(B,C(D,F(K,L,M,N(O)),P))");
        System.out.println (v);
        int n = v.maxWidth();
        System.out.println ("Maximum number of children: " + n); // 4

        v = Answer.parseTree("A(B(C))");
        System.out.println (v);
        n = v.maxWidth();
        System.out.println ("Maximum number of children: " + n); // 1

        v = Answer.parseTree("A");
        System.out.println (v);
        n = v.maxWidth();
        System.out.println ("Maximum number of children: " + n); // 0
    }
}