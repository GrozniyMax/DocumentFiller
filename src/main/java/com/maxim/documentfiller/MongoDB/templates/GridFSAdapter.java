package com.maxim.documentfiller.MongoDB.templates;

import com.maxim.documentfiller.MongoDB.Exceptions.NoSuchFileException;
import com.maxim.documentfiller.MongoDB.Exceptions.SuchFileExistsExpection;
import com.maxim.documentfiller.DocumentFilling.TemplateDataSource;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.InputStream;

import java.util.Map;
import java.util.Objects;

/**
 * Service that encapsulates all interaction with GridFS of MongoDB
 * @author Taranenko Maxim
 */
@Service
public class GridFSAdapter implements TemplateDataSource {

    @Autowired
    private GridFsTemplate fileStorage;

    private InputStream getFile(@NonNull String filename) throws NoSuchFileException {
        try {
            var gridFSFile = Objects.requireNonNull(fileStorage.findOne(findByFilenameQuery(filename)));
            return Objects.requireNonNull(fileStorage.getResource(gridFSFile).getContent());
        } catch (NullPointerException e) {
            throw new NoSuchFileException(filename);
        }
    }

    public void storeFile(@NonNull String filename, @NonNull InputStream content, @Nullable Map<String,Object> metadata) throws SuchFileExistsExpection {
        try {
            getFile(filename);
            throw new SuchFileExistsExpection("filename");
        }catch (NoSuchFileException e){
            Document meta = null;
            if (metadata != null) {
                meta = new Document(metadata);
            }
            fileStorage.store(content,filename, meta);
        }
    }

    public void storeWithReplacement(@NonNull String filename, @NonNull InputStream content, @Nullable Map<String,Object> metadata){
        try {
            getFile(filename);
            delete(filename);
            throw new NoSuchFileException(filename);
        }catch (NoSuchFileException e){
            storeFile(filename,content,metadata);
        }
    }

    public void delete(@NonNull String filename){
        fileStorage.delete(findByFilenameQuery(filename));
    }

    private Query findByFilenameQuery(@NonNull String filename){
        return new Query(Criteria.where("filename").is(filename));
    }

    @Override
    public InputStream loadFile(@lombok.NonNull String filename) throws NoSuchFileException {
        return getFile(filename);
    }

    @Override
    public void putFile(@lombok.NonNull String filename, @lombok.NonNull InputStream inputStream, Map<String, Object> metadata) {
        storeWithReplacement(filename, inputStream,metadata);
    }

    @Override
    public void addFile(@lombok.NonNull String filename, @lombok.NonNull InputStream inputStream, Map<String, Object> metadata) throws SuchFileExistsExpection {
        storeFile(filename,inputStream,metadata);
    }

}
