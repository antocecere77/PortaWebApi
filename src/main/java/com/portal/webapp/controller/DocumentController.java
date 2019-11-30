package com.portal.webapp.controller;

import com.portal.webapp.model.DemoDocument;
import com.portal.webapp.service.DemoDocumentService;
import com.portal.webapp.service.UtentiService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("api/document")
public class DocumentController {

    @Autowired
    DemoDocumentService demoDocumentService;

    @PostMapping("/upload")
    public String singleFileUpload() {
        try {
            String filePath = "./docs/Test.xlsx";

            byte[] bFile = Files.readAllBytes(new File(filePath).toPath());

            DemoDocument demoDocument = new DemoDocument();
            demoDocument.setEmailId("test@test.com");
            demoDocument.setDocType("Excel");
            demoDocument.setFile(new Binary(BsonBinarySubType.BINARY, bFile));
            demoDocumentService.save(demoDocument);
            System.out.println(demoDocument);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }

    @PostMapping("/retrieve")
    public String retrieveFile() {
        List<DemoDocument> demoDocumentList = demoDocumentService.getAll();
        if(demoDocumentList.isEmpty()) {
            return "failure";
        }

        DemoDocument demoDocument = demoDocumentList.get(0);
        System.out.println(demoDocument);
        Binary document = demoDocument.getFile();
        if(document != null) {
            FileOutputStream fileOuputStream = null;
            try {
                fileOuputStream = new FileOutputStream("./retrieve/" + "Test_" + System.currentTimeMillis() + ".xlsx");
                fileOuputStream.write(document.getData());
            } catch (Exception e) {
                e.printStackTrace();
                return "failure";
            } finally {
                if (fileOuputStream != null) {
                    try {
                        fileOuputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "failure";
                    }
                }
            }
        }
        return "success";
    }

}
