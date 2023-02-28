package com.mtzz.application.service.controller.dto;

import com.mtzz.application.service.model.entity.Cliente;
import com.mtzz.application.service.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/clients")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @PostMapping(path = "/add")
    public ResponseEntity<Cliente> addCliente(@RequestBody ClienteRequest clienteRequest)
    {
        Cliente cliente = clienteService.addCliente(clienteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @GetMapping(path = "/find/id/{id}")
    public ResponseEntity<Object> findCliente(@PathVariable Long id)
    {
        Object clientLocalized = clienteService.findCliente(id);
        return ResponseEntity.ok(clientLocalized);
    }

    @PutMapping(path = "/update/id/{id}")
    public ResponseEntity<Cliente> updateClient(@PathVariable Long id, @RequestBody ClienteRequest newDataRequest)
    {
        clienteService.updateCliente(id, newDataRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/delete/id/{id}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable Long id)
    {
        clienteService.deleteCliente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
