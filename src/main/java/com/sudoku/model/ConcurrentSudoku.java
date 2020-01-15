package com.sudoku.model;

import com.sudoku.operations.Validator;

public class ConcurrentSudoku extends Sudoku{

    public static final int GRID_HEIGHT = 9;
    public static final int GRID_WIDTH = 9;

    private byte nextX;
    private byte nextY;
    private StackSync taskStack;
    private Validator v;

    public ConcurrentSudoku(int[][] grid){
        super(grid);
    }

    public ConcurrentSudoku(int[][] grid, byte nextX, byte nextY, StackSync taskStack){
        super(grid);
        this.nextX = nextX;
        this.nextY = nextY;
        this.taskStack = taskStack;
        this.v = new Validator(this);
    }

    public ConcurrentSudoku cloneAndMod(byte nextX, byte nextY){
        int[][] grid2 = new int[grid.length][grid[0].length];
        for(int i = 0; i < grid2.length; i++)
            System.arraycopy(grid[i], 0, grid2[i], 0, grid[i].length);
        return new ConcurrentSudoku(grid2, nextX, nextY, taskStack);
    }


    public void solve(){
        if(nextX >= GRID_WIDTH){
            nextY++;
            nextX = 0;
        }

        if(nextY >= GRID_HEIGHT){
            taskStack.solutions.add(this);
            return;
        }

        if(v.isNotCaseEmpty(nextX, nextY)){
            nextX = (byte)(nextX+1);
            taskStack.push(this);
            return;
        }

        for(byte i = 1; i <= 9; i++){
            if(v.isValidInput(i, nextX, nextY)){
                ConcurrentSudoku s = cloneAndMod((byte)(nextX+1), nextY);
                s.getGrid()[nextX][nextY] = i;
                taskStack.push(s);
            }
        }
    }
}
