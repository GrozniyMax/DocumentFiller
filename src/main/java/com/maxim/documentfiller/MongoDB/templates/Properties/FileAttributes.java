package com.maxim.documentfiller.MongoDB.templates.Properties;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Document(collection = "attributes")
@Document(collection= FileAttributes.collectionName)
public class FileAttributes {
    //TODO write db name etc

    @Value("MONGO_COLLECTION_NAME")
    public static final String collectionName="attributes";

    @Id
    private String id;
    private String filename;
    private List<Attribute> properties;


}
