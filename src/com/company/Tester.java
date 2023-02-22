package com.company;

public class Tester {
    public static int testNumber = 1;
    public static void main(String[] args) {
        String[] testStrings = { "000010", "1110001", "0010", "1100100" };
        /*
         * Answer sheet:
         * DFA one should accept: 000010, 0010
         * DFA two should accept: 1100100
         * DFA three should accept: 1110001
         */

        int score = 0;
        int questions = 0;

        // A DFA that will check if a string ends in "10"
        int[][] tOne = new int[3][2];

        // Populating the transition table
        tOne[0][1] = 1;
        tOne[0][0] = 0;
        tOne[1][0] = 2;
        tOne[1][1] = 0;
        tOne[2][0] = tOne[2][1] = 0;

        // Declaring the final states
        int[] fOne = { 2 };

        // A DFA that will check if a string has an odd number of "1" and ends in at
        // least two "0"
        int[][] tTwo = new int[4][2];
        tTwo[0][1] = 1;
        tTwo[0][0] = tTwo[1][1] = tTwo[2][1] = tTwo[3][1] = 0;
        tTwo[1][0] = 2;
        tTwo[2][0] = tTwo[3][0] = 3;
        int[] fTwo = { 3 };

        // A DFA that will check if a string has an even number of "1" and an odd
        // number of "0"
        int[][] tThree = new int[4][2];
        tThree[0][0] = 1;
        tThree[0][1] = 2;
        tThree[1][0] = 0;
        tThree[1][1] = 3;
        tThree[2][0] = 3;
        tThree[2][1] = 0;
        tThree[3][0] = 2;
        tThree[3][1] = 1;
        int[] fThree = { 1 };

        DFA one = new DFA(tOne, fOne);
        DFA two = new DFA(tTwo, fTwo);
        DFA three = new DFA(tThree, fThree);

        // Test each
        score += test(one, testStrings, new boolean[] { true, false, true, false });
        score += test(two, testStrings, new boolean[] { false, false, false, true });
        score += test(three, testStrings, new boolean[] { false, true, false, false });

        questions += testStrings.length * (testNumber-1);
        System.out.print("\n\nYou got " + score + "/" + questions + " correct.\n\n");

        //Testing Unions:
        System.out.println("------ TEST Union ---------");

        /*int[][] tm1 = new int[4][2];
        tm1[0][0] = 1;
        tm1[0][1] = 3;
        tm1[1][0] = 1;
        tm1[1][1] = 2;
        tm1[2][0] = 1;
        tm1[2][1] = 2;
        tm1[3][0] = 3;
        tm1[3][1] = 3;
        int[] fm1 = { 3 };
        DFA m1 = new DFA(tm1, fm1);

        int[][] tm2 = new int[4][2];
        tm2[0][0] = 3;
        tm2[0][1] = 1;
        tm2[1][0] = 2;
        tm2[1][1] = 1;
        tm2[2][0] = 2;
        tm2[2][1] = 1;
        tm2[3][0] = 3;
        tm2[3][1] = 3;
        int[] fm2 = { 3 };

        DFA m2 = new DFA(tm2, fm2); */

        int[][] tm1 = new int[2][2];
        tm1[0][0] = 0;
        tm1[0][1] = 1;
        tm1[1][0] = 1;
        tm1[1][1] = 1;
        int[] fm1 = { 1 };
        DFA m1 = new DFA(tm1, fm1);

        int[][] tm2 = new int[3][2];
        tm2[0][0] = 1;
        tm2[0][1] = 2;
        tm2[1][0] = 1;
        tm2[1][1] = 1;
        tm2[2][0] = 2;
        tm2[2][1] = 2;
        int[] fm2 = { 1 };

        DFA m2 = new DFA(tm2, fm2);

        DFA m3 = DFA.union(m1, m2);
        m3.printDFA();
    }

    public static int test(DFA dfa, String[] test, boolean[] answers) {
        int score = 0;

        for (int i = 0; i < test.length; i++) {
            System.out.print("DFA " + testNumber + " with input, " + test[i] + ": ");
            if (dfa.run(test[i])) {
                    System.out.println("Accepted");
                    if(answers[i]) score++;
            } else {
                    System.out.println("Rejected");
                    if(!answers[i]) score++;
            }
        }

        testNumber++;
        return score;
    }



}