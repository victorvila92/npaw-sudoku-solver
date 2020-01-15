package com.sudoku.model;

import java.util.Arrays;

public class Sudoku {
    int[][] grid;

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
}