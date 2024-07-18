package com.maxim.documentfiller.MongoDB.Exceptions;

public class NoSuchFileException extends RuntimeException {
    private String fileName;

    public NoSuchFileException(String fileName) {
        this.fileName = fileName;
    }
}
