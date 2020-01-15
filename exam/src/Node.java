import java.util.*;

public class Node {

    private String name;
    private Node   firstChild;
    private Node   nextSibling;

    Node (String n, Node d, Node r) {
        setName (n);
        setFirstChild (d);
        setNextSibling (r);
    }

    public void   setName (String n)        { name = n; }
    public String getName()                 { return name; }
    public void   setFirstChild (Node d)    { firstChild = d; }
    public Node   getFirstChild()           { return firstChild; }
    public void   setNextSibling (Node r)   { nextSibling = r; }
    public Node   getNextSibling()          { return nextSibling; }

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
            Node right = getFirstChild().getNextSibling();
            while (right != null) {
                b.append (",");
                b.append (right.leftParentheticRepresentation());
                right = right.getNextSibling();
            }
            b.append (")");
        }
        return b.toString();
    }

    public static Node parseTree (String s) {
        if (s == null) return null;
        if (s.length() == 0) return null;
        Node root = null;
        Node curr = null;
        Node last = null;
        int state = 0; // begin
        Stack<Node> stk = new Stack<Node>();
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
                curr = new Node (w, null, null);
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
                    }
                } // switch
                stk.push (curr);
            }
        } // next w
        return root;
    }

    private int countWidth(Node n) {
        if (n.getFirstChild() == null) return 0;
        Node child = n.getFirstChild();
        int maxWidth = countWidth(child);
        int count = 1;
        while ((child = child.getNextSibling()) != null) {
            count++;
            maxWidth = Math.max(countWidth(child), maxWidth);
        }
        return Math.max(maxWidth, count);
    }

    public int maxWidth() {
        return countWidth(this);
    }

    private int countLeaves(Node n) {
        if (n.getFirstChild() == null) return 1;
        Node child = n.getFirstChild();
        int count = countLeaves(child);
        while ((child = child.getNextSibling()) != null) count += countLeaves(child);
        return count;
    }

    public int numberOfLeaves() {
        return countLeaves(this);
    }

    public static void main (String[] param) {
        Node v = Node.parseTree ("A(B,C(D,F(K,L,M,N(O)),P))");
        System.out.println(v);
        System.out.println("Number of leaves: " + v.numberOfLeaves()); // 7
        System.out.println("Max width: " + v.maxWidth()); // 4

    }
}
