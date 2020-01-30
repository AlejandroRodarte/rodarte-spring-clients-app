package com.rodarte.models.controller;

import com.rodarte.models.entity.Client;
import com.rodarte.models.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> saveClient(@RequestBody Client client) {

        Client newClient = null;
        Map<String, Object> response = new HashMap<>();

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
    public Client updateClient(@RequestBody Client client, @PathVariable Long id) {

        Client clientToUpdate = clientService.findById(id);

        clientToUpdate.setFirstName(client.getFirstName());
        clientToUpdate.setLastName(client.getLastName());
        clientToUpdate.setEmail(client.getEmail());

        return clientService.save(clientToUpdate);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
    }

}
