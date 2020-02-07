package com.rodarte.controller;

import com.rodarte.models.dto.ClientDto;
import com.rodarte.models.dto.RawClientDto;
import com.rodarte.models.entity.Client;
import com.rodarte.models.entity.Region;
import com.rodarte.models.service.ClientService;
import com.rodarte.models.service.UploadFileService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// allow our angular app to access this resource; enabled http methods: GET, POST, PUT, DELETE
// if not specified, it allows the selected origins to perform all operations
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @GetMapping
    public List<RawClientDto> getClients() {
        return clientService.findAll().stream().map(client -> modelMapper.map(client, RawClientDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/regions")
    @Secured("ROLE_ADMIN")
    public List<Region> getRegions() {
        return clientService.findAllRegions();
    }

    @GetMapping("/page/{page}")
    public Page<RawClientDto> getClients(@PathVariable Integer page) {
        return clientService.findAll(PageRequest.of(page, 4)).map(client -> modelMapper.map(client, RawClientDto.class));
    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
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

        ClientDto clientDto = modelMapper.map(client, ClientDto.class);

        return new ResponseEntity<ClientDto>(clientDto, HttpStatus.OK);

    }

    // serve an image to client
    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {

        Resource resource = null;

        try {
            resource = uploadFileService.serve(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // set Content-Disposition header to force browser to download image upon accessing route
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);

    }

    @PostMapping
    @Secured("ROLE_ADMIN")
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
        response.put("client", modelMapper.map(newClient, RawClientDto.class));

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    // upload image route
    // expects: form-data with two args: the image itself (multipartFile) and the client id
    @PostMapping("/upload")
    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile multipartFile, @RequestParam("id") Long id) {

        Map<String, Object> response = new HashMap<>();

        Client client = clientService.findById(id);

        if (client == null) {
            response.put("message", "The client with ID of " + id + " does not exist in the database!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        // check if file is not empty
        if (!multipartFile.isEmpty()) {

            String uniqueFilename = null;

            try {
                uniqueFilename = uploadFileService.save(multipartFile);
            } catch (IOException e) {
                response.put("message", "There was a problem uploading the image to this server.");
                response.put("error", e.getMessage() + ": " + e.getCause().getMessage());
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // verify if user had an associated image
            String previousFilename = client.getImage();

            // attempt to remove existing image (if exists)
            uploadFileService.remove(previousFilename);

            // set image filename on database
            client.setImage(uniqueFilename);

            clientService.save(client);

            response.put("client", modelMapper.map(client, RawClientDto.class));
            response.put("message", "Image uploaded successfully into the server. Filename: " + uniqueFilename);

        }

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateClient(@Valid @RequestBody Client client, BindingResult bindingResult, @PathVariable Long id) {

        Client clientToUpdate = clientService.findById(id);

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
            clientToUpdate.setRegion(client.getRegion());

            clientService.save(clientToUpdate);

        } catch (DataAccessException e) {

            response.put("message", "Error updating client into the database!");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("message", "Client updated successfully!");
        response.put("client", modelMapper.map(clientToUpdate, RawClientDto.class));

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {

            Client client = clientService.findById(id);

            String filename = client.getImage();

            // attempt to delete image and delete client
            uploadFileService.remove(filename);
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
