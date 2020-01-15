package com.sudoku.solver;

import com.sudoku.model.Sudoku;
import com.sudoku.operations.SudokuOperation;

import static com.sudoku.operations.SudokuOperation.*;

public class RecursiveBacktrackingSolverTask implements SudokuSolver {

    public RecursiveBacktrackingSolverTask() {}

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
                    return false;
                }
            }
        }
        return true;
    }
}
