package com.rodarte.models.controller;

import com.rodarte.models.entity.Client;
import com.rodarte.models.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

// allow our angular app to access this resource; enabled http methods: GET, POST, PUT, DELETE
// if not specified, it allows the selected origins to perform all operations
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @GetMapping
    public List<Client> getClients() {
        return clientService.findAll();
    }

    @GetMapping("/page/{page}")
    public Page<Client> getClients(@PathVariable Integer page) {
        return clientService.findAll(PageRequest.of(page, 4));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClient(@PathVariable Long id) {

        Client client = null;
        Map<String, Object> errorResponse = new HashMap<>();

        try {
            client = clientService.findById(id);
        } catch (DataAccessException e) {

            errorResponse.put("message", "Error accessing the database!");
            errorResponse.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());

            return new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if (client == null) {
            errorResponse.put("message", "The client with ID of " + id + " does not exist in the database!");
            return new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Client>(client, HttpStatus.OK);

    }

    // serve an image to client
    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {

        // get absolute path from image filename
        Path filePath = Paths.get("uploads").resolve(filename).toAbsolutePath();

        logger.info(filePath.toString());

        Resource resource = null;

        // get resource from the path's URI
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (!resource.exists() && !resource.isReadable()) {
            throw new RuntimeException("Image could not be served: " + filename);
        }

        // set Content-Disposition header to force browser to download image upon accessing route
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveClient(@Valid @RequestBody Client client, BindingResult bindingResult) {

        Client newClient = null;
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {

            List<String> errors =
                    bindingResult
                            .getFieldErrors()
                            .stream()
                            .map(fieldError -> "The field '" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
                            .collect(Collectors.toList());

            response.put("errors", errors);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

        }

        try {
            newClient = clientService.save(client);
        } catch (DataAccessException e) {

            response.put("message", "Error persisting client into the database!");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "Client persisted successfully!");
        response.put("client", newClient);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    // upload image route
    // expects: form-data with two args: the image itself (multipartFile) and the client id
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile multipartFile, @RequestParam("id") Long id) {

        Map<String, Object> response = new HashMap<>();

        Client client = clientService.findById(id);

        if (client == null) {
            response.put("message", "The client with ID of " + id + " does not exist in the database!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        // check if file is not empty
        if (!multipartFile.isEmpty()) {

            // get file original name
            String originalFilename = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename().replace(" ", "");

            // get absolute path where we will save this image on this computer
            Path filePath = Paths.get("uploads").resolve(originalFilename).toAbsolutePath();

            logger.info(filePath.toString());

            // copy file into the computer: requires the input stream of data (multipartFile) and the path where
            // the data will be copied to (filePath)
            try {
                Files.copy(multipartFile.getInputStream(), filePath);
            } catch (IOException e) {
                response.put("message", "There was a problem uploading the image to this. Filename: " + originalFilename);
                response.put("error", e.getMessage() + ": " + e.getCause().getMessage());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // verify if user had an associated image
            String previousFilename = client.getImage();

            // if so
            if (previousFilename != null && previousFilename.length() > 0) {

                // get filepath to that image and parse path to a file object
                Path previousFilePath = Paths.get("uploads").resolve(previousFilename).toAbsolutePath();
                File previousFile = previousFilePath.toFile();

                // if such file exists and can be read, delete
                if (previousFile.exists() && previousFile.canRead()) {
                    previousFile.delete();
                }

            }

            // set image filename on database
            client.setImage(originalFilename);

            clientService.save(client);

            response.put("client", client);
            response.put("message", "Image uploaded successfully into the server. Filename: " + originalFilename);

        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateClient(@Valid @RequestBody Client client, BindingResult bindingResult, @PathVariable Long id) {

        Client clientToUpdate = clientService.findById(id);
        Client updatedClient = null;

        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {

            List<String> errors =
                    bindingResult
                            .getFieldErrors()
                            .stream()
                            .map(fieldError -> "The field '" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
                            .collect(Collectors.toList());

            response.put("errors", errors);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

        }

        if (clientToUpdate == null) {
            response.put("message", "Can not update, client with id " + id + " does not exist in the database!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            clientToUpdate.setFirstName(client.getFirstName());
            clientToUpdate.setLastName(client.getLastName());
            clientToUpdate.setEmail(client.getEmail());
            clientToUpdate.setCreatedAt(client.getCreatedAt());

            updatedClient = clientService.save(clientToUpdate);

        } catch (DataAccessException e) {

            response.put("message", "Error updating client into the database!");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "Client updated successfully!");
        response.put("client", updatedClient);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {

            Client client = clientService.findById(id);

            String previousFilename = client.getImage();

            if (previousFilename != null && previousFilename.length() > 0) {

                Path previousFilePath = Paths.get("uploads").resolve(previousFilename).toAbsolutePath();
                File previousFile = previousFilePath.toFile();

                if (previousFile.exists() && previousFile.canRead()) {
                    previousFile.delete();
                }

            }

            clientService.deleteById(id);

        } catch (DataAccessException e) {

            response.put("message", "Error deleting client from the database!");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "The client was successfully deleted!");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

}
