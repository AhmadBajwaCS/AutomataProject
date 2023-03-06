package com.company;/* Project #1 - Automata Theory
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
        }

        //if the state we have reached is equal to the final state of the DFA, return true
        for (int finalState : finalStates) {
            if (finalState == state) {
                return true;
            }
        }
        return false;
    }


    public static DFA CartesianProd(DFA m1, DFA m2, String toTest) {

        // Get the transition tables and final states of the two DFAs
        int[][] transitionTableM1 = m1.getTransitionTable();
        int[] finalStatesM1 = m1.getFinalStates();
        int[][] transitionTableM2 = m2.getTransitionTable();
        int[] finalStatesM2 = m2.getFinalStates();

        // Calculate the number of states in the new DFA
        int numStates = transitionTableM1.length * transitionTableM2.length;

        // Create a new transition table and final states array for the new DFA
        int[][] tt3 = new int[numStates][2];

        int numFinalStates = numStates;
        System.out.println(" Final States Count:" + numFinalStates);
        int[] fs3 = new int[numFinalStates];

        // Calculate the transition table and final states of the new DFA
        int k = 0;
        for (int i = 0; i < transitionTableM1.length; i++) {
            for (int j = 0; j < transitionTableM2.length; j++) {
                // Calculate the new state based on the combination of the two states
                //System.out.println("Testing i: " + i + " and j: " + j);

                int newState = i * transitionTableM2.length + j;

                // Calculate the transition for input 0
                int t1 = transitionTableM1[i][0];
                int t2 = transitionTableM2[j][0];
                int newT = t1 * transitionTableM2.length + t2;
                tt3[newState][0] = newT;

                // Calculate the transition for input 1
                t1 = transitionTableM1[i][1];
                t2 = transitionTableM2[j][1];
                newT = t1 * transitionTableM2.length + t2;
                tt3[newState][1] = newT;

                // Check if the new state is an accepting state
                boolean isFinal1 = false;
                boolean isFinal2 = false;

                /*if ( java.util.Arrays.asList(finalStatesM1).indexOf(i) != -1)
                    isFinal1 = true;

                if ( java.util.Arrays.asList(finalStatesM2).indexOf(j) != -1)
                    isFinal2 = true; */

                for (int f : finalStatesM1) {
                    if (i == f) {
                        isFinal1 = true;
                        break;
                    }
                }
                for (int f : finalStatesM2) {
                    if (j == f) {
                        isFinal2 = true;
                        break;
                    }
                }


                if (checkFinal(toTest, isFinal1, isFinal2)) {
                    //System.out.println("New Final State: \n ");
                    fs3[k++] = newState;
                }
            }
        }

        // Create the new DFA and return it
        DFA m3 = new DFA(tt3, Arrays.copyOf(fs3, k));
        return m3;

    }

    public static boolean checkFinal(String toMake, boolean isFinal1, boolean isFinal2){
        if(toMake.equals("union")){
            return (isFinal1 || isFinal2);
        }
        else if (toMake.equals("intersection")){
            return (isFinal1 && isFinal2);
        }
        else if (toMake.equals("difference")){
            return (isFinal1 && !isFinal2);
        }

        return false;
    }

    public static DFA union(DFA m1, DFA m2) {

        // Create the new DFA and return it
        DFA m3 = CartesianProd(m1, m2, "union");
        return m3;
    }


    public static DFA difference(DFA m1, DFA m2) {
        DFA m3 = CartesianProd(m1, m2, "difference");
        return m3;
    }

    public static DFA intersection(DFA m1, DFA m2) {
        DFA m3 = CartesianProd(m1, m2, "intersection");
        return m3;
    }

    public static DFA complement(DFA m1){
        ArrayList<Integer> tempList = new ArrayList<>();
        int numStates = m1.transitionTable.length;

        //Creating arraylist of all states
        for(int i = 0; i < numStates; ++i)
            tempList.add(i);

        //Removing non-final states from complement DFA
        for(int i = 0; i < tempList.size(); ++i){
            for(int j = 0; j < m1.finalStates.length; ++j){
                if(m1.finalStates[j] == tempList.get(i))
                    tempList.remove(i);
            }
        }

        int[] newFinalStates = new int[tempList.size()];

        for(int i = 0; i < newFinalStates.length; ++i){
            newFinalStates[i] = tempList.get(i);
        }

        return new DFA(m1.transitionTable, newFinalStates); //New DFA
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

    public static String credits() {
        String names = "\n Keshav Raghavan \n Joseph Wright \n Akhil Kanagala \n Ahmad Bajwa";
        return names;
    }

    public boolean isEmptyLanguage () {
        if (this.finalStates.length == 0) {
            return true;
        }

        return false;
    }

    public boolean isUniversalLanguage () {
        if (this.finalStates.length == this.transitionTable.length) {
            return true;
        }

        return false;
    }

    public boolean isInfinite() {
        Set<Integer> visitedStates = new HashSet<>();
        Stack<Integer> currentStateStack = new Stack<>();

        // Push the current state onto the currentStateStack.
        currentStateStack.push(state);

        // Loop through the DFA.
        while (!currentStateStack.isEmpty()) {

            int currentState = currentStateStack.pop();

            // If the current state is a final state, it is infinite.
            for (int finalState : finalStates) {
                if (finalState == currentState) {
                    return true;
                }
            }

            // Add the current state to the set of visitedStates.
            visitedStates.add(currentState);

            // Loop through the transition table for the current state.
            for (int i = 0; i < transitionTable[currentState].length; i++) {
                int nextState = transitionTable[currentState][i];

                // Check if the next state has already been visited.
                if (!visitedStates.contains(nextState)) {
                    // Add the next state to the currentStateStack.
                    currentStateStack.push(nextState);
                }
            }
        }

        // L(M) is finite, so return false.
        return false;
    }

    public boolean equals (DFA m2) {

        DFA m3 = DFA.difference(this, m2);
        DFA m4 = DFA.difference(m2, this);
        m3.printDFA();

        if(m3.isEmptyLanguage() && m4.isEmptyLanguage()){
            System.out.println("m3 is empty");
            return true;
        }

        return false;
    }

    public boolean isSubsetOf(DFA m2){

        m2 = DFA.complement(m2);
        DFA m3 = DFA.intersection(this, m2);

        if(m3.isEmptyLanguage()){
            return true;
        }

        return false;
    }

}
