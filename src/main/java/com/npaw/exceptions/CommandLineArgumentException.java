package com.npaw.exceptions;

public class CommandLineArgumentException extends Exception{
    public CommandLineArgumentException(String errorMessage){
        super(errorMessage);
    }
}