package com.sudoku.solver;

import com.sudoku.model.ConcurrentSudoku;
import com.sudoku.model.StackSync;

import java.util.EmptyStackException;
import java.util.logging.Logger;

public class ParallelSolverTask extends Thread{

    private static final Logger LOGGER = Logger.getLogger(ParallelSolverTask.class.getName());

    public ParallelSolverTask() {}

    private StackSync st;

    public ParallelSolverTask(StackSync st){
        this.st = st;
    }

    public void run(){
        while(!st.isFinished()){
            ConcurrentSudoku sudoku;

            try {
                sudoku = st.pop();
            }
            catch(EmptyStackException ignore){
                sudoku = null;
            }
            if(sudoku != null) {
                sudoku.solve();
            }
        }
    }

    public static void solve(int [][] grid, boolean oneSol, int threads){
        StackSync st = new StackSync(oneSol);
        ConcurrentSudoku sudoku = new ConcurrentSudoku(grid, (byte) 0,(byte)0, st);
        st.push(sudoku);

        ParallelSolverTask[] tasks = new ParallelSolverTask[threads];
        for(int i =0; i < threads; i++)
            tasks[i] = new ParallelSolverTask(st);


        for(ParallelSolverTask task : tasks) {
            task.start();
        }

        for(ParallelSolverTask task : tasks){
            try {
                task.join();
            } catch (InterruptedException e) {
                LOGGER.warning("Join of the threads have failed.");
                e.printStackTrace();
            }
        }

        LOGGER.info(st.solutions.size() + " solution"+ (st.solutions.size() > 1 ? "s" : "") + " found !");

        for (ConcurrentSudoku solvedSudoku : st.solutions){
            solvedSudoku.display();
        }
    }
}
