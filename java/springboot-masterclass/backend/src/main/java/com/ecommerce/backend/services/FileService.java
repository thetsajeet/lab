package com.ecommerce.backend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    public String uploadFile(String path, MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        assert filename != null;
        String uploadedFilename = UUID.randomUUID().toString().concat(filename.substring(filename.lastIndexOf('.')));
        String filePath = path + File.separator + uploadedFilename;
        File folder = new File(path);

        if (!folder.exists()) folder.mkdir();

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return uploadedFilename;
    }
}
