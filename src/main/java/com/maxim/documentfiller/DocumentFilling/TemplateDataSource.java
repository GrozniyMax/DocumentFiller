package com.maxim.documentfiller.DocumentFilling;

import lombok.NonNull;

import java.io.InputStream;
import java.nio.file.NoSuchFileException;

public interface TemplateDataSource {

    public InputStream loadFile(@NonNull String filename) throws NoSuchFileException;

}
