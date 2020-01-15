package com.sudoku;

import com.sudoku.exceptions.CommandLineArgumentException;
import com.sudoku.model.ConcurrentSudoku;
import com.sudoku.model.Sudoku;
import com.sudoku.solver.ParallelSolverTask;
import com.sudoku.solver.RecursiveBacktrackingSolverTask;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.sudoku.operations.SudokuOperation.getConcurrentSudokuFromFile;
import static com.sudoku.operations.SudokuOperation.getSudokuFromFile;

public class SudokuMain {

    private static final Integer ARGUMENT_SIZE = 3;
    private static final String ARGUMENT_MULTI_THREAD_INDICATOR = "-p";
    private static final Integer ARGUMENT_MULTI_THREAD_ON = 1;
    private static final Integer ARGUMENT_MULTI_THREAD_OFF = 0;
    private static final Logger LOGGER = Logger.getLogger(SudokuMain.class.getName());

    public static void main(String[] args) throws CommandLineArgumentException, IOException {

        if (argumentsAreWrong(args)){
            throw new CommandLineArgumentException("CommandLineArgument ERROR. Please follow the command: 'java -jar sudoku.jar -p n filename'");
        }

        if(mustSolveWithMultiThreading(args[1])){
            ConcurrentSudoku sudoku = getConcurrentSudokuFromFile(args[2]);
            LOGGER.log(Level.INFO, "Trying to solve sudoku with MultiThreading...");
            ParallelSolverTask.solve(sudoku.getGrid(), false, 5);
        }else {
            Sudoku sudoku = getSudokuFromFile(args[2]);
            LOGGER.log(Level.INFO, "Trying to solve sudoku with BruteForce (Recursive Backtracking)...");
            RecursiveBacktrackingSolverTask solver = new RecursiveBacktrackingSolverTask();
            solver.solve(sudoku);
        }
    }

    private static boolean mustSolveWithMultiThreading(String parameter){
        return parameter.equals("1");
    }

    private static boolean argumentsAreWrong(String[] args) {
        return args.length != ARGUMENT_SIZE ||
                !args[0].equals(ARGUMENT_MULTI_THREAD_INDICATOR) ||
                (!Integer.valueOf(args[1]).equals(ARGUMENT_MULTI_THREAD_OFF) && !Integer.valueOf(args[1]).equals(ARGUMENT_MULTI_THREAD_ON));
    }
}