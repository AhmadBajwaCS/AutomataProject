package com.company;/* Project #1 - Automata Theory
 *
 * Group: 
 * Keshav Raghavan (KAR190002)
 * Joseph Wright (JSW190005)
 * Akhil Kanagala (AXK190110)
 * Ahmad Bajwa (AIB190004)
 * 
 */


import java.util.Arrays;

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

    public static DFA union(DFA m1, DFA m2) {
        // Get the transition tables and final states of the two DFAs
        int[][] tt1 = m1.getTransitionTable();
        int[] fs1 = m1.getFinalStates();
        int[][] tt2 = m2.getTransitionTable();
        int[] fs2 = m2.getFinalStates();

        // Calculate the number of states in the new DFA
        int numStates = tt1.length * tt2.length;

        // Create a new transition table and final states array for the new DFA
        int[][] tt3 = new int[numStates][2];

        int numFinalStates = 10;
        /*for (int f : fs1) {
            for (int g : fs2) {
                if (f * tt2.length + g < numStates) {
                    numFinalStates++;
                }
            }
        }*/
        System.out.println(" Final States Count:" + numFinalStates);
        int[] fs3 = new int[numFinalStates];

        // Calculate the transition table and final states of the new DFA
        int k = 0;
        for (int i = 0; i < tt1.length; i++) {
            for (int j = 0; j < tt2.length; j++) {
                // Calculate the new state based on the combination of the two states
                System.out.println("Testing i: " + i + " and j: " + j);

                int newState = i * tt2.length + j;

                // Calculate the transition for input 0
                int t1 = tt1[i][0];
                int t2 = tt2[j][0];
                int newT = t1 * tt2.length + t2;
                tt3[newState][0] = newT;

                // Calculate the transition for input 1
                t1 = tt1[i][1];
                t2 = tt2[j][1];
                newT = t1 * tt2.length + t2;
                tt3[newState][1] = newT;

                // Check if the new state is an accepting state
                boolean isFinal1 = false;
                boolean isFinal2 = false;
                for (int f : fs1) {
                    if (i == f) {
                        isFinal1 = true;
                        break;
                    }
                }
                for (int f : fs2) {
                    if (j == f) {
                        isFinal2 = true;
                        break;
                    }
                }
                if (isFinal1 || isFinal2) {
                    System.out.println("New Final State: \n ");
                    fs3[k++] = newState;
                }
            }
        }

        // Create the new DFA and return it
        DFA m3 = new DFA(tt3, Arrays.copyOf(fs3, k));
        return m3;
    }

    private int[] getFinalStates() {
        return this.finalStates;
    }

    private int[][] getTransitionTable() {
        return this.transitionTable;
    }


    public void printDFA() {
        System.out.println("Transition Table:");

        for(int i = 0; i < transitionTable.length; i++){
            for (int j = 0; j < transitionTable[i].length; j++){
                System.out.println("At State: " + i + " Given: " + j + " ---> State " + transitionTable[i][j] );
            }
        }
        System.out.println(Arrays.deepToString(transitionTable));

        System.out.println("\nFinal States:");
        System.out.println(Arrays.toString(finalStates));

    }
}
