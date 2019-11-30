package com.portal.webapp.model;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "document")
public class DemoDocument {
    @Id
    @Field
    private String id;

    @Field
    private String emailId;

    @Field
    private String docType;

    @Field
    private Binary file;
}
