package com.sudoku.model;

import java.util.LinkedList;
import java.util.Stack;

public class StackSync {

    private Stack<ConcurrentSudoku> st = new Stack<>();
    public LinkedList<ConcurrentSudoku> solutions = new LinkedList<>();
    private boolean oneSolution;

    public StackSync(boolean oneSolution){
        this.oneSolution = oneSolution;
    }

    public synchronized void push(ConcurrentSudoku s){
        st.push(s);
    }

    public synchronized ConcurrentSudoku pop(){
        return st.pop();
    }

    public synchronized boolean isFinished(){
        boolean one = oneSolution && !solutions.isEmpty();
        boolean multi = !oneSolution && st.isEmpty();
        return one || multi;
    }
}
