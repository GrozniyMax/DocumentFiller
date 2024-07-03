package com.maxim.documentfiller.DocumentFilling;

import com.maxim.documentfiller.DB.templates.Properties.FileAttributes;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;


public interface FileAttributesDataSource {

    public FileAttributes getFileProperties(String filename) throws NoSuchFileException;

    public void putFileProperties(FileAttributes fileAttributes);

    public void addFileProperties(FileAttributes fileAttributes) throws FileAlreadyExistsException;
}
