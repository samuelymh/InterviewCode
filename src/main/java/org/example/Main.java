package org.example;

import java.util.Objects;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println(problem1(1000));
        System.out.println(problem2("abcdaabcdeabaaacbfaaaabcab"));
        System.out.println(problem2("123aabcaabca35aa"));
    }

    /**
     * Computes the nth member of the series (1 2 5 20 25 150...)
     * a_n = a_{n-1} * n if n is even
     * a_n = a_{n-1} + n if n is odd
     *
     * @param n nth member to compute
     * @return nth member
     */
    public static long problem1(int n) {
        long currentTerm = 1;

        for (int i = 1; i < n; i++) {
            // To compute the next member while i still refers to a_{n-1}
            long nextMember = i + 1; // n
            if (i % 2 != 0) { // Next member has an even n, so multiply
                currentTerm *= nextMember;
            } else { // Next member has an odd n, so add
                currentTerm += nextMember;
            }
        }

        return currentTerm;
    }

    /**
     * Parses through string and replaces string according to two rules:
     * RULE 1: If you find a single ‘a’, replace it with ‘#’.
     * RULE 2: If you find an ‘a’ followed by another ‘a’, do not replace the first ‘a’, but replace
     * each subsequent ‘a’ in the sequence with ‘$’
     *
     * @param str Some string to compute
     * @return computed string
     */
    public static String problem2(String str) {
        if (str == null || str.isBlank()) {
            return str;
        }

        StringBuilder parsedString = new StringBuilder();
        boolean aFound = false;

        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);

            if (currentChar == 'a') {
                if (aFound) { // subsequent 'a' found
                    parsedString.append('$'); // rule 2
                } else { // first 'a' found, check if it's single
                    if (i + 1 < str.length() && str.charAt(i + 1) == 'a') {
                        parsedString.append('a'); // rule 2
                        aFound = true;
                    } else { // single 'a' found
                        parsedString.append('#'); // rule 1
                    }
                }
            } else {
                // non 'a' found
                parsedString.append(currentChar);
                aFound = false; // reset flag
            }
        }

        return parsedString.toString();
    }
}