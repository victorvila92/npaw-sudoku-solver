package com.sudoku.operations;

import com.sudoku.model.Sudoku;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.IntStream;

public abstract class SudokuOperation {

    public static final int BOARD_SIZE = 9;
    public static final int SUBSECTION_SIZE = 3;
    public static final int BOARD_START_INDEX = 0;

    public static final int NO_VALUE = 0;
    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE = 9;

    public SudokuOperation(){}

    public static Sudoku getSudokuFromFile(String filename) throws IOException {
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

    public static boolean isValid(int[][] board, int row, int column) {
        return rowConstraint(board, row) &&
                columnConstraint(board, column) &&
                subsectionConstraint(board, row, column);
    }

    private static boolean subsectionConstraint(int[][] board, int row, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        int subsectionRowStart = (row / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionRowEnd = subsectionRowStart + SUBSECTION_SIZE;

        int subsectionColumnStart = (column / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionColumnEnd = subsectionColumnStart + SUBSECTION_SIZE;

        for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                if (!checkConstraint(board, r, constraint, c)) return false;
            }
        }
        return true;
    }

    private static boolean columnConstraint(int[][] board, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
                .allMatch(row -> checkConstraint(board, row, constraint, column));
    }

    private static boolean rowConstraint(int[][] board, int row) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
                .allMatch(column -> checkConstraint(board, row, constraint, column));
    }

    private static boolean checkConstraint(int[][] board, int row, boolean[] constraint, int column) {
        if (board[row][column] != NO_VALUE) {
            if (!constraint[board[row][column] - 1]) {
                constraint[board[row][column] - 1] = true;
            } else {
                return false;
            }
        }
        return true;
    }
}
