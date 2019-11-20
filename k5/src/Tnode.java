import java.util.*;

public class Tnode {

    private String name;
    private Tnode firstChild = null;
    private Tnode nextSibling = null;

    public Tnode(String name, Tnode firstChild) {
        this.name = name;
        this.firstChild = firstChild;
    }

    public Tnode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder(name);
        Tnode node = firstChild;
        if (firstChild != null) b.append('(');
        while (node != null) {
            b.append(node.toString());
            node = node.nextSibling;
            if (node != null) b.append(',');
        }
        if (firstChild != null) b.append(')');
        return b.toString();
    }


    public static Tnode buildFromRPN(String pol) {
        Stack<Tnode> stack = new Stack<>();
        for (String node : pol.split("\\s+")) {
            if (!node.matches("-?\\d+|[+\\-*/]|SWAP|ROT"))
                throw new IllegalArgumentException(String.format("Expression %s contains illegal symbol %s!", pol, node));
            if (node.matches("[+\\-*/]|SWAP|ROT")) {
                try {
                    if (node.matches("SWAP")) {
                        Tnode a = stack.pop();
                        Tnode b = stack.pop();
                        stack.push(a);
                        stack.push(b);
                    } else if (node.matches("ROT")) {
                        Tnode a = stack.pop();
                        Tnode b = stack.pop();
                        Tnode c = stack.pop();
                        Arrays.asList(b, a, c).forEach(stack::push);
                    } else {
                        Tnode nextSibling = stack.pop();
                        Tnode firstChild = stack.pop();
                        firstChild.nextSibling = nextSibling;
                        stack.push(new Tnode(node, firstChild));
                    }
                } catch (RuntimeException e) {
                    throw new RuntimeException(String.format("Expression %s contains too few numbers!", pol));
                }
            } else stack.push(new Tnode(node));
        }
        if (stack.isEmpty()) throw new RuntimeException(String.format("Expression %s contains too few numbers!", pol));
        Tnode root = stack.pop();
        if (!stack.isEmpty())
            throw new RuntimeException(String.format("Expression %s contains too many numbers!", pol));
        return root;
    }

    public static void main(String[] param) {
        String[] testStrings = new String[] {
                "2 5 SWAP -",         // -(5,2)
                "2 5 9 ROT - +",      // +(5,-(9,2))
                "2 5 9 ROT + SWAP -"  // -(+(9,2),5)
        };
        Arrays.asList(testStrings).forEach(rpn -> {
            System.out.println("RPN: " + rpn);
            Tnode res = buildFromRPN(rpn);
            System.out.println("Tree: " + res);
        });
    }
}