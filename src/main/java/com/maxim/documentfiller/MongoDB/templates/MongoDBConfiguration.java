package com.maxim.documentfiller.MongoDB.templates;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * For storing files and templates we use MongoDB
 * This is configuration of MongoDB
 */

@Configuration
@EnableMongoRepositories(basePackages = "com.maxim.documentfiller.MongoDB.templates")
public class MongoDBConfiguration extends AbstractMongoClientConfiguration {

    @Autowired
    private MappingMongoConverter mappingMongoConverter;

    @Override
    protected String getDatabaseName() {
        return "test";
    }




    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(buildConnectionURL());
        System.out.println(buildConnectionURL());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter);
    }

    private String buildConnectionURL(){
        return String.format("mongodb://%s:%s@%s:%s/%s",
                System.getenv("MONGO_USERNAME"),
                System.getenv("MONGO_PASSWORD"),
                System.getenv("MONGO_HOST"),
                System.getenv("MONGO_PORT"),
                System.getenv("MONGO_DB_NAME")
                );
    }

}
