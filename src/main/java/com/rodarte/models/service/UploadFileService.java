package com.rodarte.models.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

// serve file, save file, delete file, get filePath interface
public interface UploadFileService {
    Resource serve(String filename) throws MalformedURLException;
    String save(MultipartFile multipartFile) throws IOException;
    boolean remove(String filename);
    Path getPath(String filename);
}
