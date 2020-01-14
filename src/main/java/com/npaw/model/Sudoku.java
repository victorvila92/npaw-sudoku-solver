package com.npaw.model;

import java.util.Arrays;

public class Sudoku {
    int[][] grid;
    boolean solved;

    public Sudoku(int[][] grid) {
        this.grid = grid;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void display(){
        for (int[] row : grid){
            System.out.println(Arrays.toString(row));
        }
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}