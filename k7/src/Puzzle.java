import java.util.*;

public class Puzzle {

    private HashMap<Character, Integer> solution = new HashMap<>();
    private ArrayList<String> solutions = new ArrayList<>();
    private String add1;
    private String add2;
    private String sum;

    public static void main(String[] input) {
        ArrayList<? extends String> result = new Puzzle().solve(input);
        System.out.println(result);
        System.out.println(result.size());
    }

    private ArrayList<String> solve(String[] input) {
        if (input.length != 3) throw new RuntimeException("Puzzle solver only takes an array with size 3.");
        Set<String> letters = new LinkedHashSet<>();
        add1 = input[0];
        add2 = input[1];
        sum = input[2];
        for (int i = 0; i < 10; i++) {
            if (i < add1.length()) letters.add(Character.toString(add1.charAt(i)));
            if (i < add2.length()) letters.add(Character.toString(add2.charAt(i)));
            if (i < sum.length()) letters.add(Character.toString(sum.charAt(i)));
        }
        if (letters.size() > 10) return solutions;
        solveRecursively(String.join("", letters));
        return solutions;
    }

    private boolean solveRecursively(String availableLetters) {
        if (availableLetters.isEmpty()) {
            if (!(solution.get(add1.charAt(0)) == 0 || solution.get(add2.charAt(0)) == 0 || solution.get(sum.charAt(0)) == 0)
                    && valueOf(add1) + valueOf(add2) == valueOf(sum)) {
                solutions.add(solution.toString());
            }
            return false;
        }
        for (int digit = 0; digit < 10; digit++) {
            if (!solution.containsValue(digit)) {
                solution.put(availableLetters.charAt(0), digit);
                if (solveRecursively(availableLetters.substring(1))) return true;
                solution.remove(availableLetters.charAt(0));
            }
        }
        return false;
    }

    private int valueOf(String w) {
        int val = 0;
        for (int i = 0; i < w.length(); i++)
            val = val * 10 + solution.get(w.charAt(i));
        return val;
    }
}