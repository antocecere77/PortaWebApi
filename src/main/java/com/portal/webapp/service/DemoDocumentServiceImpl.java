package com.portal.webapp.service;

import com.portal.webapp.model.DemoDocument;
import com.portal.webapp.repository.DemoDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DemoDocumentServiceImpl implements  DemoDocumentService {

    @Autowired
    DemoDocumentRepository demoDocumentRepository;

    @Override
    public List<DemoDocument> getAll() {
        return demoDocumentRepository.findAll();
    }

    @Override
    public void save(DemoDocument demoDocument) {
        demoDocumentRepository.save(demoDocument);
    }

    @Override
    public void delete(DemoDocument demoDocument) {
        demoDocumentRepository.delete(demoDocument);
    }
}
