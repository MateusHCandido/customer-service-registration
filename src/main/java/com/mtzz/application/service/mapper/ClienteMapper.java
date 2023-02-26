package com.mtzz.application.service.mapper;

import com.mtzz.application.service.controller.dto.ClienteRequest;
import com.mtzz.application.service.model.entity.Cliente;

public class ClienteMapper
{

    public static Cliente toClient(ClienteRequest clienteRequest)
    {
        Cliente cliente = new Cliente();
        cliente.setId(clienteRequest.getId());
        cliente.setNome(clienteRequest.getNome());
        cliente.setCpf(clienteRequest.getCpf());
        cliente.setDataCadastro(clienteRequest.getDataCadastro());
        return cliente;
    }
}
