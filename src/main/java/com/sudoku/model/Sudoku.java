package com.sudoku.model;

public class Sudoku {
    int[][] grid;

    public Sudoku(int[][] grid) {
        this.grid = grid;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void display(){
        StringBuilder sb = new StringBuilder();
        sb.append("╔═════╦═════╦═════╗\n");

        for(int x = 0; x < grid.length; x++) {
            sb.append("║ ");
            for (int y = 0; y < grid[x].length; y++) {
                sb.append(grid[x][y]);
                if(y == 2 || y == 5)
                    sb.append(" ║ ");
            }
            sb.append(" ║\n");

            if(x == 2 || x == 5)
                sb.append("╠═════╬═════╬═════╣\n");
        }

        sb.append("╚═════╩═════╩═════╝");
        System.out.println(sb.toString());
    }
}