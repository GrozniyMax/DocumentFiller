package com.maxim.documentfiller.MongoDB.templates.Properties;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilePropertiesRepository extends MongoRepository<FileAttributes,String> {

    public Optional<FileAttributes> findByFilename(String fileName);

    public Boolean existsFileAttributesByFilename(String fileName);
    
}
