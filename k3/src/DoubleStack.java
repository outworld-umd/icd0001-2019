import java.util.*;

public class DoubleStack implements Cloneable {

    public static void main (String[] args) {
        System.out.println(interpret("2 5 SWAP -"));
    }

    private LinkedList<Double> list;

    DoubleStack() {
        this.list = new LinkedList<>();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DoubleStack d = (DoubleStack) super.clone();
        d.list = new LinkedList<>(list);
        return d;
    }

    public boolean stEmpty() {
        return list.isEmpty();
    }

    public void push(double a) {
        list.add(a);
    }

    public double pop() {
        if (this.stEmpty()) throw new EmptyStackException();
        return list.removeLast();
    }

    public void op(String s) {
        if (!s.matches("[+\\-*/]|SWAP|ROT")) throw new IllegalArgumentException();
        double a = this.pop();
        double b = this.pop();
        switch (s) {
            case "+": this.push(a + b); break;
            case "-": this.push(b - a); break;
            case "*": this.push(a * b); break;
            case "/": this.push(b / a); break;
            case "SWAP": this.push(a); this.push(b); break;
            case "ROT": double c = this.pop(); Arrays.asList(b, a, c).forEach(this::push); break;
        }
    }

    public double tos() {
        if (this.stEmpty()) throw new EmptyStackException();
        return list.getLast();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoubleStack)) return false;
        DoubleStack stack = (DoubleStack) o;
        return list.equals(stack.list);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    public static double interpret(String pol) {
        try {
            DoubleStack stack = new DoubleStack();
            Arrays.asList(pol.replaceAll("^\\s+|\\s+$", "").split("\\s+")).forEach(i -> {
                if (i.matches("[+\\-*/]|SWAP|ROT")) stack.op(i);
                else stack.push(Double.parseDouble(i));
            });
            double res = stack.pop();
            if (!stack.stEmpty())
                throw new RuntimeException(String.format("Expression '%s' contains too many numbers!", pol));
            return res;
        } catch (NumberFormatException e) {
            throw new RuntimeException(String.format("Expression '%s' contains illegal operator!", pol));
        } catch (EmptyStackException e) {
            throw new RuntimeException(String.format("Expression '%s' does not contain enough numbers!", pol));
        } catch (NullPointerException e) {
            throw new RuntimeException("Expression is empty!");
        }
    }
}