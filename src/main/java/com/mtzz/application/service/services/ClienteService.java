package com.mtzz.application.service.services;

import com.mtzz.application.service.controller.dto.ClienteRequest;
import com.mtzz.application.service.mapper.ClienteMapper;
import com.mtzz.application.service.model.entity.Cliente;
import com.mtzz.application.service.repository.ClienteRepository;
import com.mtzz.application.service.services.exceptions.CPFAlreadyExistsException;
import com.mtzz.application.service.services.exceptions.CreateClientException;
import org.springframework.beans.factory.annotation.Autowired;

public class ClienteService
{
    @Autowired
    private ClienteRepository clienteRepository;


    public void checkIfCpfExists(String cpf)
    {
        Cliente searchReturn = clienteRepository.findByCpf(cpf);
        if(searchReturn != null)
        {
            throw new CPFAlreadyExistsException("CPF already exists");
        }
    }

    public Cliente addCliente(ClienteRequest clienteRequest)
    {
        if(clienteRequest.getNome() == null)
        {
            throw new CreateClientException("Name cannot be blank");
        }else if(clienteRequest.getCpf() == null)
        {
            throw new CreateClientException("Cpf cannot be blank");
        }
        checkIfCpfExists(clienteRequest.getCpf());
        Cliente cliente = ClienteMapper.toClient(clienteRequest);
        clienteRepository.save(cliente);
        return cliente;
    }
}
