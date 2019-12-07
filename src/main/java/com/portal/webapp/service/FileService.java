package com.portal.webapp.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileService {

    private static final String FILE_DIRECTORY = "/var/files";

    private enum ResourceType {
        FILE_SYSTEM,
        CLASSPATH
    }

    public void storeFile(MultipartFile file) throws IOException {
        Path filePath = Paths.get(file.getOriginalFilename());

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * @param filename filename
     * @param response Http response.
     * @return file from system.
     */
    public Resource getFileSystem(String filename, HttpServletResponse response) throws IOException {
        return getResource(filename, response, ResourceType.FILE_SYSTEM);
    }

    /**
     * @param filename filename
     * @param response Http response.
     * @return file from classpath.
     */
    public Resource getClassPathFile(String filename, HttpServletResponse response) throws IOException {
        return getResource(filename, response, ResourceType.CLASSPATH);
    }

    private Resource getResource(String filename, HttpServletResponse response, ResourceType resourceType) throws IOException {
        response.setContentType("text/csv; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.setHeader("filename", filename);

        Resource resource = null;
        switch (resourceType) {
            case FILE_SYSTEM:
                resource = new FileSystemResource(FILE_DIRECTORY + filename);
                break;
            case CLASSPATH:
                resource = new ClassPathResource("data/" + filename);
                break;
        }
        return resource;
    }

}
