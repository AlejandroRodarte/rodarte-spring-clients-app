package com.rodarte.models.controller;

import com.rodarte.models.entity.Client;
import com.rodarte.models.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateClient(@Valid @RequestBody Client client, @PathVariable Long id, BindingResult bindingResult) {

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
