package com.mtzz.application.service.builders;

import com.mtzz.application.service.controller.dto.ClienteRequest;

import java.time.LocalDate;

public class ClienteBuilder {

    private ClienteRequest cliente;

    private ClienteBuilder() {
    }

    public static ClienteBuilder generateClient()
    {
        ClienteBuilder builder = new ClienteBuilder();
        builder.cliente = new ClienteRequest();
        builder.cliente.setId(1L);
        builder.cliente.setNome("MATEUS HENRIQUE");
        builder.cliente.setCpf("11111111111");
        builder.cliente.setDataCadastro(LocalDate.now());

        return builder;
    }

    public ClienteRequest now() {return cliente;}
}
