package com.maxim.documentfiller.MongoDB.templates.Properties;

import com.maxim.documentfiller.DocumentFilling.FileAttributesDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;

@Service
public class MongoDBAttributesDataSource implements FileAttributesDataSource {
    @Autowired
    private FilePropertiesRepository repository;

    @Override
    public FileAttributes getFileProperties(String filename) throws NoSuchFileException {
        return repository.findByFilename(filename).orElseThrow(()->new NoSuchFileException(filename));
    }

    /**
     * Put into MongoDB given attributes
     * <br> Automatically replaces attributes if there are
     * @param fileAttributes attributes to save
     */
    @Override
    public void putFileProperties(FileAttributes fileAttributes) {
        repository.save(fileAttributes);
    }

    @Override
    public void addFileProperties(FileAttributes fileAttributes) throws FileAlreadyExistsException {
        if (repository.existsFileAttributesByFilename(fileAttributes.getFilename()))
            throw new FileAlreadyExistsException(fileAttributes.getFilename());
        putFileProperties(fileAttributes);
    }
}
