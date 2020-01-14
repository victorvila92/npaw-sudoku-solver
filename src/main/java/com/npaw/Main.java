package com.npaw;

import com.npaw.exceptions.CommandLineArgumentException;
import com.npaw.model.Sudoku;
import com.npaw.operations.SudokuOperation;
import com.npaw.solver.ForkJoinSolverTask;
import com.npaw.solver.RecursiveBacktrackingSolverTask;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        boolean mustSolveWithMultiThreading = args[1].equals("1");
        Sudoku sudoku = getSudokuFromFile(args[2]);

        if(mustSolveWithMultiThreading){
            LOGGER.log(Level.INFO, "Trying to solve sudoku with MultiThreading (Fork join)...");
            ForkJoinSolverTask.getInstance().solve(sudoku);
        }else {
            LOGGER.log(Level.INFO, "Trying to solve sudoku with BruteForce (Recursive Backtracking)...");
            RecursiveBacktrackingSolverTask.getInstance().solve(sudoku);

        }
    }

    private static Sudoku getSudokuFromFile(String filename) throws IOException {

        Sudoku sudoku = new Sudoku(new int[9][9]);

        try (FileInputStream inputStream = new FileInputStream(filename); Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            int lineNumber = 0;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] digits = line.replace("x", "0").split(",");

                for (int i = 0; i < digits.length; i++){
                    sudoku.getGrid()[lineNumber][i] = Byte.parseByte(digits[i]);
                }
                ++lineNumber;
            }
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        }
        return sudoku;
    }

    private static boolean argumentsAreWrong(String[] args) {
        return
                args.length != ARGUMENT_SIZE ||
                        !args[0].equals(ARGUMENT_MULTI_THREAD_INDICATOR) ||
                        (!Integer.valueOf(args[1]).equals(ARGUMENT_MULTI_THREAD_OFF) && !Integer.valueOf(args[1]).equals(ARGUMENT_MULTI_THREAD_ON));
    }
}