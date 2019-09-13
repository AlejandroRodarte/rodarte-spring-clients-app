package com.rodarte.models.controller;

import com.rodarte.models.entity.Client;
import com.rodarte.models.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Client getClient(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client saveClient(@RequestBody Client client) {
        return clientService.save(client);
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
