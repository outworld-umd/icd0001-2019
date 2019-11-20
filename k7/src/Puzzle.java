import java.util.*;

public class Puzzle {

    private static final String ILLEGAL_ARGS_MSG = "Puzzle solver takes exactly 3 arguments!";
    private static final String TOO_MANY_CHARS_MSG = "Given string array contains more chars than there are digits!";
    private static final String RESULT_MSG = "Puzzle %s + %s = %s has %s %s.\n%s\n";

    private ArrayList<String> solutions = new ArrayList<>();
    private HashMap<Character, Integer> solution = new HashMap<>();
    private String add1;
    private String add2;
    private String sum;

    public static void main(String[] input) {
        System.out.println(new Puzzle().solve(input));
    }

    private String solve(String[] input) {
        if (input.length != 3) throw new RuntimeException(ILLEGAL_ARGS_MSG);
        Set<String> letters = new LinkedHashSet<>();
        add1 = input[0];
        add2 = input[1];
        sum = input[2];
        for (int i = 0; i < 10; i++) {
            if (i < add1.length()) letters.add(Character.toString(add1.charAt(i)));
            if (i < add2.length()) letters.add(Character.toString(add2.charAt(i)));
            if (i < sum.length()) letters.add(Character.toString(sum.charAt(i)));
        }
        if (letters.size() > 10) throw new RuntimeException(TOO_MANY_CHARS_MSG);
        solveRecursively(String.join("", letters));
        return String.format(RESULT_MSG, add1, add2, sum,
                solutions.size() > 0 ? solutions.size() : "no",
                solutions.size() == 1 ? "solution" : "solutions", solutions);
    }

    private void solveRecursively(String availableLetters) {
        if (availableLetters.isEmpty()) {
            if (checkSolution()) solutions.add(solution.toString());
            return;
        }
        for (int digit = 0; digit < 10; digit++) {
            if (!solution.containsValue(digit)) {
                solution.put(availableLetters.charAt(0), digit);
                solveRecursively(availableLetters.substring(1));
                solution.remove(availableLetters.charAt(0));
            }
        }
    }

    private boolean checkSolution() {
        return !(solution.get(add1.charAt(0)) == 0 || solution.get(add2.charAt(0)) == 0 ||
                solution.get(sum.charAt(0)) == 0) && valueOf(add1) + valueOf(add2) == valueOf(sum);
    }

    private int valueOf(String w) {
        int val = 0;
        for (int i = 0; i < w.length(); i++)
            val = val * 10 + solution.get(w.charAt(i));
        return val;
    }
}