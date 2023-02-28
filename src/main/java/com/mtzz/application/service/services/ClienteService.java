package com.mtzz.application.service.services;

import com.mtzz.application.service.controller.dto.ClienteRequest;
import com.mtzz.application.service.mapper.ClienteMapper;
import com.mtzz.application.service.model.entity.Cliente;
import com.mtzz.application.service.repository.ClienteRepository;
import com.mtzz.application.service.services.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClienteService
{
    @Autowired
    private ClienteRepository clienteRepository;


    public void cpfValidation(String cpf)
    {
        if(cpf.isBlank())
        {
            throw new InvalidCpfException("CPF cannot be blank");
        }else if(cpf.contains(".") || cpf.contains("-"))
        {
            throw new InvalidCpfException("Only numbers are accepted in the CPF");
        }else if(cpf.length() != 11)
        {
            throw new InvalidCpfException("The CPF must contain 11 numbers");
        }
        Cliente searchReturn = clienteRepository.findByCpf(cpf);
        if(searchReturn != null)
        {
            throw new CPFAlreadyExistsException("CPF already exists");
        }
    }

    public Cliente addCliente(ClienteRequest clienteRequest)
    {
        if(clienteRequest.getNome().isBlank())
        {
            throw new CreateClientException("Name cannot be blank");
        }
        cpfValidation(clienteRequest.getCpf());
        Cliente cliente = ClienteMapper.toClient(clienteRequest);
        clienteRepository.save(cliente);
        return cliente;
    }

    public Cliente findCliente(Long id)
    {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        return cliente;
    }

    public void updateCliente(Long id, ClienteRequest newDataRequest)
    {

        Cliente cliente = findCliente(id);
        String name = newDataRequest.getNome();
        String cpf = newDataRequest.getCpf();
        if(name == null && cpf == null)
        {throw new UpdateClientException("No data has been entered for update");}
        else if(Objects.equals(name, ""))
        {
            throw new UpdateClientException("Name cannot be blank");
        }
        if(cpf == null)
        {
            cliente.setNome(name);
        }
        else if(name == null)
        {
            cpfValidation(cpf);
            cliente.setCpf(cpf);
        }
        else
        {
            cpfValidation(cpf);
            cliente.setNome(name);
            cliente.setCpf(cpf);
        }
        clienteRepository.save(cliente);
    }

    public void deleteCliente(Long id)
    {
        Cliente cliente = findCliente(id);
        clienteRepository.delete(cliente);
    }
}
