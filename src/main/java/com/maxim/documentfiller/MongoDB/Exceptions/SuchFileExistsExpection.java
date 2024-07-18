package com.maxim.documentfiller.MongoDB.Exceptions;

public class SuchFileExistsExpection extends RuntimeException{
    public SuchFileExistsExpection(String message) {
        super(message);
    }
}
