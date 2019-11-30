package com.portal.webapp.repository;

import com.portal.webapp.model.DemoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DemoDocumentRepository extends MongoRepository<DemoDocument, String> {
}
