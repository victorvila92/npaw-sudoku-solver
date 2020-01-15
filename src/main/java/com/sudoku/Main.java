package com.sudoku;

import com.sudoku.exceptions.CommandLineArgumentException;
import com.sudoku.model.Sudoku;
import com.sudoku.solver.ForkJoinSolverTask;
import com.sudoku.solver.RecursiveBacktrackingSolverTask;
import com.sudoku.solver.SudokuSolver;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.sudoku.operations.SudokuOperation.getSudokuFromFile;

public class Main {

    private static final Integer ARGUMENT_SIZE = 3;
    private static final String ARGUMENT_MULTI_THREAD_INDICATOR = "-p";
    private static final Integer ARGUMENT_MULTI_THREAD_ON = 1;
    private static final Integer ARGUMENT_MULTI_THREAD_OFF = 0;
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws CommandLineArgumentException, IOException {

        if (argumentsAreWrong(args)){
            throw new CommandLineArgumentException("CommandLineArgument ERROR. Please follow the command: 'java -jar sudoku.jar -p n filename'");
        }

        Sudoku sudoku = getSudokuFromFile(args[2]);
        SudokuSolver solver;

        if(mustSolveWithMultiThreading(args[1])){
            LOGGER.log(Level.INFO, "Trying to solve sudoku with MultiThreading (Fork join)...");
            solver = new ForkJoinSolverTask();
        }else {
            LOGGER.log(Level.INFO, "Trying to solve sudoku with BruteForce (Recursive Backtracking)...");
            solver = new RecursiveBacktrackingSolverTask();
        }
        boolean solved = solver.solve(sudoku);

        if(solved){
            LOGGER.log(Level.INFO, "Solution found!");
            sudoku.display();
        }else {
            LOGGER.log(Level.INFO, "No Solution found!");
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