package com.rodarte.models.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    private final Logger logger = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    private final static String UPLOAD_DIRECTORY_NAME = "uploads";

    @Override
    public Resource serve(String filename) throws MalformedURLException {

        // get absolute path from image filename
        Path filePath = getPath(filename);

        logger.info(filePath.toString());

        // get resource from filepath
        Resource resource = new UrlResource(filePath.toUri());

        // if image actually does not exist, send default image from static files
        if (!resource.exists() && !resource.isReadable()) {

            filePath = Paths.get("src/main/resources/images").resolve("no-user.png").toAbsolutePath();

            try {
                resource = new UrlResource(filePath.toUri());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            logger.error("Image could not be loaded. Filename: " + filename);

        }

        return resource;

    }

    @Override
    public String save(MultipartFile multipartFile) throws IOException {

        // get file original name
        String uniqueFilename = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename().replace(" ", "");

        // get absolute path where we will save this image on this computer
        Path filePath = Paths.get("uploads").resolve(uniqueFilename).toAbsolutePath();

        logger.info(filePath.toString());

        // copy file into the computer: requires the input stream of data (multipartFile) and the path where
        // the data will be copied to (filePath)
        Files.copy(multipartFile.getInputStream(), filePath);

        return uniqueFilename;

    }

    @Override
    public boolean remove(String filename) {

        if (filename != null && filename.length() > 0) {

            Path previousFilePath = Paths.get("uploads").resolve(filename).toAbsolutePath();
            File previousFile = previousFilePath.toFile();

            if (previousFile.exists() && previousFile.canRead()) {
                previousFile.delete();
                return true;
            }

        }

        return false;

    }

    @Override
    public Path getPath(String filename) {
        return Paths.get(UPLOAD_DIRECTORY_NAME).resolve(filename).toAbsolutePath();
    }

}
