public class Puzzle {

    static void main(String[] input)    // w1 + w2 = w3
    {
        usedLetter = new boolean[26];    // usedLetter[i] = true iff letter i appears in w1, w2 or w3
        usedDigit = new boolean[26];    // usedDigit[i] = true iff digit i is used by a letter (used in backtracking)
        assignedDigit = new int[26];    // assignedDigiti[i] = digit assigned to letter i (used in backtracking)
        markLetters(input[0]);
        markLetters(input[1]);
        markLetters(input[2]);
        backtrack(0, input[0], input[1], input[2]);
        System.out.println("No more solutions :(");
    }

    static boolean[] usedLetter;
    static boolean[] usedDigit;
    static int[] assignedDigit;

    // mark the letters appeared in w1, w2 and w3 to use them in the search.
    static void markLetters(String w) {
        for (int i = 0; i < w.length(); ++i)
            usedLetter[w.charAt(i) - 'A'] = true;
    }

    static boolean check(String w1, String w2, String w3) {
        if (leadingZero(w1) || leadingZero(w2) || leadingZero(w3))
            return false;
        return value(w1) + value(w2) == value(w3);
    }

    static boolean leadingZero(String w) {
        return assignedDigit[w.charAt(0) - 'A'] == 0;
    }

    // if w = ABCD, then the function returns A * 1000 + B * 100 + C * 10 + D.
    static int value(String w) {
        int val = 0;
        for (int i = 0; i < w.length(); ++i)
            val = val * 10 + assignedDigit[w.charAt(i) - 'A'];
        return val;
    }

    // do the backtracking (brute force)
    private static void backtrack(int char_idx, String w1, String w2, String w3) {
        if (char_idx == 26) {
            // finished assigning values for the 26 letters
            if (check(w1, w2, w3)) {
                System.out.println("Found a solution!");
                for (int i = 0; i < 26; ++i)
                    if (usedLetter[i])
                        System.out.printf("[%c = %d]", (char) (i + 'A'), assignedDigit[i]);
                System.out.println("\n------");
            }
            return;
        }

        if (!usedLetter[char_idx]) {
            // skip this letter, it was not used in the input.
            backtrack(char_idx + 1, w1, w2, w3);
            return;
        }
        // try assigning different digits for this letter
        for (int digit = 0; digit < 10; ++digit)
            if (!usedDigit[digit])    // this condition guarantees that no digit is used for more than one letter
            {
                usedDigit[digit] = true;
                assignedDigit[char_idx] = digit;
                backtrack(char_idx + 1, w1, w2, w3);
                usedDigit[digit] = false;
            }
    }
}