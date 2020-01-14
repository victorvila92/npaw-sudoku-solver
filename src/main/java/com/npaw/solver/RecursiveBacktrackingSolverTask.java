package com.npaw.solver;

import com.npaw.model.Sudoku;
import com.npaw.operations.SudokuOperation;

import static com.npaw.operations.SudokuOperation.*;

public class RecursiveBacktrackingSolverTask implements SudokuSolver {

    private static final RecursiveBacktrackingSolverTask instance = new RecursiveBacktrackingSolverTask();

    private RecursiveBacktrackingSolverTask() {}

    public static RecursiveBacktrackingSolverTask getInstance() {
        return instance;
    }

    @Override
    public boolean solve(Sudoku sudoku) {

        int[][] board = sudoku.getGrid();

        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
            for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
                if (board[row][column] == NO_VALUE) {
                    for (int k = MIN_VALUE; k <= MAX_VALUE; k++) {
                        board[row][column] = k;
                        if (SudokuOperation.isValid(board, row, column) && solve(sudoku)) {
                            return true;
                        }
                        board[row][column] = NO_VALUE;
                    }
                    sudoku.setSolved(false);
                    return false;
                }
            }
        }
        sudoku.setSolved(true);
        return true;
    }
}
