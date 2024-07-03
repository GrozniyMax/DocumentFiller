package com.maxim.documentfiller.DB.templates.Properties;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilePropertiesRepository extends MongoRepository<FileProperties,String> {

    public FileProperties findByFilename(String fileName);
    
}
