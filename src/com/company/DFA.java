package com.company;

/* Project #1 - Automata Theory
 *
 * Group: 
 * Keshav Raghavan (KAR190002)
 * Joseph Wright (JSW190005)
 * Akhil Kanagala (AXK190110)
 * Ahmad Bajwa (AIB190004)
 * 
 */

import java.util.*;

public class DFA {

    //create state variable to keep the location of the DFA
    //stored.
    private int state;

    //Creating a 2D transition table array, and a final state array.
    private int[][] transitionTable;
    private int[] finalStates;

    DFA (int[][] transitionTable, int[] finalStates) {
        this.state = 0;
        this.transitionTable = transitionTable;
        this.finalStates = finalStates;
    }

    public static DFA intersection (DFA m1, DFA m2) {
        int[][] intersectionTable = new int[m1.transitionTable.length][2];
        int[] newFinalStates = new int[0];

        for (int i = 0; i < intersectionTable.length; i++) {
            for (int j = 0; j < 2; j++) {
                int newState1 = m1.transitionTable[i][j];
                int newState2 = m2.transitionTable[i][j];
                intersectionTable[i][j] = newState1 == newState2 ? newState1 : -1;
            }
            if (Arrays.binarySearch(m1.finalStates, i) >= 0 && Arrays.binarySearch(m2.finalStates, i) >= 0) {
                newFinalStates = Arrays.copyOf(newFinalStates, newFinalStates.length + 1);
                newFinalStates[newFinalStates.length - 1] = i;
            }
        }

        return new DFA(intersectionTable, newFinalStates);
    }

    public boolean run (String input) {
        char[] inputCharArray = input.toCharArray();
        state = 0;
        /*
         * loops through the strings character input and based on the bit character,
         * adjust the state to a new place in the transition table in the below format
         * 
         * format state = transitionTable[currentState][char]
         */
        for (char c : inputCharArray) {
            if (c == '0') {
                state = transitionTable[state][0];
            } 
            else if (c == '1') {
                state = transitionTable[state][1];
            }

            state++;
        }

        //if the state we have reached is equal to the final state of the DFA, return true
        if (finalStates[0] == state) {
            return true;
        }

        return false;
    }
}
