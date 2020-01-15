package com.sudoku.operations;

import com.sudoku.model.ConcurrentSudoku;

public class Validator {
    private ConcurrentSudoku s;

    public Validator(ConcurrentSudoku s){
        this.s = s;
    }

    public boolean isValidInput(byte val, byte x, byte y){
        if(isNotCaseEmpty(x, y))
            return false;
        if(!isValidVal(val))
            return false;
        if(!isValidAddVertLine(val, x))
            return false;
        if(!isValidAddHorLine(val, y))
            return false;
        return isValidAddSquare(val, x, y);
    }

    public boolean isNotCaseEmpty(int x, int y){
        return s.getGrid()[x][y] != 0;
    }

    private boolean isValidVal(byte val){
        return val > 0 && val < 10;
    }


    private boolean isValidAddVertLine(byte val, byte x){
        for(byte i = 0; i < ConcurrentSudoku.GRID_HEIGHT; i++)
            if(s.getGrid()[x][i] == val)
                return false;
        return true;
    }

    private boolean isValidAddHorLine(byte val, byte y){
        for(byte i =0; i < ConcurrentSudoku.GRID_WIDTH; i++)
            if(s.getGrid()[i][y] == val)
                return false;
        return true;
    }
    private boolean isValidAddSquare(byte val, byte x, byte y){
        byte xMin = (byte)(x-x%3);
        byte yMin = (byte)(y-y%3);
        for(byte i = xMin; i < xMin+3; i++)
            for(byte j = yMin; j < yMin+3; j++) {
                if (s.getGrid()[i][j] == val)
                    return false;
            }
        return true;
    }
}