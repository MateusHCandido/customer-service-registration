package com.mtzz.application.service.services;


import com.mtzz.application.service.builders.ClienteBuilder;
import com.mtzz.application.service.controller.dto.ClienteRequest;
import com.mtzz.application.service.model.entity.Cliente;
import com.mtzz.application.service.repository.ClienteRepository;
import com.mtzz.application.service.services.exceptions.CPFAlreadyExistsException;
import com.mtzz.application.service.services.exceptions.CreateClientException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ClienteServiceTest {

    @TestConfiguration
    static class ClienteServiceTestConfiguration
    {
        @Bean
        public ClienteService clienteService()
        {
            return new ClienteService();
        }
    }

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;


    @Test
    public void should_create_client()
    {
        ClienteRequest clienteRequest = ClienteBuilder.generateClient().now();
        Mockito.when(clienteService.addCliente(clienteRequest)).thenReturn(new Cliente());
        Cliente cliente = clienteService.addCliente(clienteRequest);
        Assertions.assertNotNull(cliente.getId());
    }

    @Test(expected = CreateClientException.class)
    public void should_return_creation_error_due_to_blank_name_field()
    {
        ClienteRequest clienteRequest = ClienteBuilder.generateClient().now();
        clienteRequest.setNome("");
        Mockito.when(clienteService.addCliente(clienteRequest)).thenReturn(new Cliente());
        Cliente cliente = clienteService.addCliente(clienteRequest);
    }

    @Test(expected = CPFAlreadyExistsException.class)
    public void should_return_creation_error_due_to_blank_cpf_field()
    {
        ClienteRequest clienteRequest = ClienteBuilder.generateClient().now();
        Cliente cliente = clienteService.addCliente(clienteRequest);
        Mockito.when(clienteRepository.findByCpf(clienteRequest.getCpf())).thenReturn(cliente);
        Cliente clienteWithCpfAlreadyExist = clienteService.addCliente(clienteRequest);

    }
}
