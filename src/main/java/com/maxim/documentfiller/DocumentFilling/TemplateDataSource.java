package com.maxim.documentfiller.DocumentFilling;

import com.maxim.documentfiller.MongoDB.Exceptions.NoSuchFileException;
import com.maxim.documentfiller.MongoDB.Exceptions.SuchFileExistsExpection;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.io.InputStream;

import java.util.Map;


public interface TemplateDataSource {

    public InputStream loadFile(@NonNull String filename) throws NoSuchFileException;

    public void putFile(@NonNull String filename, @NonNull InputStream inputStream, @Nullable Map<String,Object> metadata);

    public void addFile(@NonNull String filename, @NonNull InputStream inputStream, @Nullable Map<String,Object> metadata) throws SuchFileExistsExpection;

}
