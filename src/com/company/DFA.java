package com.company;/* Project #1 - Automata Theory
 *
 * Group: 
 * Keshav Raghavan (KAR190002)
 * Joseph Wright (JSW190005)
 * Akhil Kanagala (AXK190110)
 * Ahmad Bajwa (AIB190004)
 * 
 */


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
            if (state == 0) {
                if (c == '0') {
                    state = transitionTable[0][0];
                } 
                else if (c == '1') {
                    state = transitionTable[0][1];
                }
            } 
            else if (state == 1) {
                if (c == '0') {
                    state = transitionTable[1][0];
                } 
                else if (c == '1') {
                    state = transitionTable[1][1];
                }
            } 
            else if (state == 2) {
                if (c == '0') {
                    state = transitionTable[2][0];
                }
                else if (c == '1') {
                    state = transitionTable[2][1];
                }
            }
            else if (state == 3) {
                if (c == '0') {
                    state = transitionTable[3][0];
                }
                else if (c == '1') {
                    state = transitionTable[3][1];
                }
            }
        }

        //if the state we have reached is equal to the final state of the DFA, return true
        if (finalStates[0] == state) {
            return true;
        }

        return false;
    }
}
