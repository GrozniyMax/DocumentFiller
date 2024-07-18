package com.maxim.documentfiller.DocumentFilling.Exceptions;

import lombok.Getter;
import lombok.Setter;


public class IncorrectFilePropertiesException extends RuntimeException{
    @Getter
    @Setter
    private String filename;

    @Getter
    private String fullpath;

    private String lastParh;

    public IncorrectFilePropertiesException() {
    }


    public IncorrectFilePropertiesException(String fullpath, String lastParh) {
        this.fullpath = fullpath;
        this.lastParh = lastParh;
    }
}
