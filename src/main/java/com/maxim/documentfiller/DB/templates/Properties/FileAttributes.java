package com.maxim.documentfiller.DB.templates.Properties;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Document(collection = "attributes")
@Document(collection = "test")
public class FileProperties {
    //TODO write db name etc

    @Id
    private String id;
    private String filename;
    private List<Attribute> properties;


}
