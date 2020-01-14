package com.npaw.solver;

import com.npaw.model.Sudoku;

public class ForkJoinSolverTask implements SudokuSolver {

    private static final ForkJoinSolverTask instance = new ForkJoinSolverTask();

    public ForkJoinSolverTask() {}

    public static ForkJoinSolverTask getInstance() {
        return instance;
    }

    @Override
    public boolean solve(Sudoku sudoku) {

        // TODO: FALTA

        return true;
    }
}
