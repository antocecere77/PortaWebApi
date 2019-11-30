package com.portal.webapp.service;

import com.portal.webapp.model.DemoDocument;
import com.portal.webapp.model.User;

import java.util.List;

public interface DemoDocumentService {

    List<DemoDocument> getAll();

    void save(DemoDocument demoDocument);

    void delete(DemoDocument demoDocument);

}
