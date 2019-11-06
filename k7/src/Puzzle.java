import java.util.ArrayList;
import java.util.HashMap;

public class Puzzle {

    private HashMap<Character, Integer> solution = new HashMap<>();
    private ArrayList<String> solutions = new ArrayList<>();

    public static void main(String[] input) {
        ArrayList result = new Puzzle().solve(input);
        System.out.println(result);
        System.out.println(result.size());
    }

    private ArrayList<String> solve(String[] input) {
        if (input.length != 3) throw new RuntimeException("Puzzle solver only takes an array with size 3.");
        StringBuilder sb = new StringBuilder();
        for (Character ch : String.join("", input).toCharArray()) {
            if (sb.indexOf(ch.toString()) == -1) sb.append(ch.toString());
        }
        if (sb.length() > 10) return solutions;
        solveRecursively(sb.toString(), input[0], input[1], input[2]);
        return solutions;
    }

    private void solveRecursively(String availableLetters, String w1, String w2, String w3) {
        if (availableLetters.isEmpty()) {
            if (!(solution.get(w1.charAt(0)) == 0 || solution.get(w2.charAt(0)) == 0 || solution.get(w3.charAt(0)) == 0)
                    && valueOf(w1) + valueOf(w2) == valueOf(w3)) {
                solutions.add(solution.toString());
            }
            return;
        }
        for (int digit = 0; digit < 10; digit++) {
            if (!solution.containsValue(digit)) {
                solution.put(availableLetters.charAt(0), digit);
                solveRecursively(availableLetters.substring(1), w1, w2, w3);
                solution.remove(availableLetters.charAt(0));
            }
        }
    }

    private int valueOf(String w) {
        int val = 0;
        for (int i = 0; i < w.length(); i++)
            val = val * 10 + solution.get(w.charAt(i));
        return val;
    }
}