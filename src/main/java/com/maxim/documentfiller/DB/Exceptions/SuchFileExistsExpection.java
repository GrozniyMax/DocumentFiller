package com.maxim.documentfiller.DB.Exceptions;

public class SuchFileExists extends RuntimeException{
    public SuchFileExists(String message) {
        super(message);
    }
}
