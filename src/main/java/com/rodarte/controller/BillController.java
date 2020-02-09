package com.rodarte.controller;

import com.rodarte.models.dto.BillDto;
import com.rodarte.models.dto.ProductDto;
import com.rodarte.models.entity.Bill;
import com.rodarte.models.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper modelMapper;

    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BillDto getBill(@PathVariable Long id) {
        return modelMapper.map(clientService.findBillById(id), BillDto.class);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/products/{name}")
    public List<ProductDto> getProductsByName(@PathVariable String name) {
        return clientService.findProductByName(name).stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BillDto createBill(@RequestBody Bill bill) {
        return modelMapper.map(clientService.saveBill(bill), BillDto.class);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBill(@PathVariable Long id) {
        clientService.deleteBillById(id);
    }

}
