package com.maxim.documentfiller.MongoDB.Exceptions;

public class NoSuchFileAttributesException extends Exception{
    public NoSuchFileAttributesException(String message) {
        super(message);
    }

    public NoSuchFileAttributesException() {
    }
}
