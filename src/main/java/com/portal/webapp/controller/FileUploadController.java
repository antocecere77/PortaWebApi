package com.portal.webapp.controller;

import com.portal.webapp.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileUploadController {

    private final FileService fileService;

    @Autowired
    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload/files")
    @ResponseStatus(HttpStatus.OK)
    public void handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.storeFile(file);
    }

}