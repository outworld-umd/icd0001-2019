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
            if (!node.matches("-?\\d+") && !node.matches("[+\\-*/]"))
                throw new IllegalArgumentException(String.format("Expression %s contains illegal symbol!", pol));
            if (node.matches("[+\\-*/]")) {
                try {
                    Tnode nextSibling = stack.pop();
                    Tnode firstChild = stack.pop();
                    firstChild.nextSibling = nextSibling;
                    stack.push(new Tnode(node, firstChild));
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
        String rpn = "5 1 - 7 * 6 3 / +";
        System.out.println("RPN: " + rpn);
        Tnode res = buildFromRPN(rpn);
        System.out.println("Tree: " + res);
    }
}