package com.mtzz.application.service.services;


import com.mtzz.application.service.builders.ClienteBuilder;
import com.mtzz.application.service.controller.dto.ClienteRequest;
import com.mtzz.application.service.model.entity.Cliente;
import com.mtzz.application.service.repository.ClienteRepository;
import com.mtzz.application.service.services.exceptions.*;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

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
        when(clienteService.addCliente(clienteRequest)).thenReturn(new Cliente());
        Cliente cliente = clienteService.addCliente(clienteRequest);
        assertNotNull(cliente.getId());
    }

    @Test(expected = CreateClientException.class)
    public void should_return_creation_error_due_to_blank_name_field()
    {
        ClienteRequest clienteRequest = ClienteBuilder.generateClient().now();
        clienteRequest.setNome("");
        when(clienteService.addCliente(clienteRequest)).thenReturn(new Cliente());
        Cliente cliente = clienteService.addCliente(clienteRequest);
    }

    @Test(expected = CPFAlreadyExistsException.class)
    public void should_return_creation_error_due_to_blank_cpf_field()
    {
        ClienteRequest clienteRequest = ClienteBuilder.generateClient().now();
        Cliente cliente = clienteService.addCliente(clienteRequest);
        when(clienteRepository.findByCpf(clienteRequest.getCpf())).thenReturn(cliente);
        Cliente clienteWithCpfAlreadyExist = clienteService.addCliente(clienteRequest);
    }

    @Test(expected = InvalidCpfException.class)
    public void should_return_creation_error_due_to_invalid_cpf_field()
    {
        ClienteRequest clienteRequest = ClienteBuilder.generateClient().now();
        clienteRequest.setCpf("111.111.111-11");
        Cliente cliente = clienteService.addCliente(clienteRequest);
    }

    @Test(expected = InvalidCpfException.class)
    public void should_return_creation_error_due_to_missing_numbers_in_cpf_field()
    {
        ClienteRequest clienteRequest = ClienteBuilder.generateClient().now();
        clienteRequest.setCpf("3333333333"); //10 numbers
        Cliente cliente = clienteService.addCliente(clienteRequest);
    }

    public void should_return_client_when_call_method_find_client()
    {
        ClienteRequest clienteRequest = ClienteBuilder.generateClient().now();

        when(clienteService.addCliente(clienteRequest)).thenReturn(new Cliente());
        Cliente cliente = clienteService.addCliente(clienteRequest);
        when(clienteService.findCliente(cliente.getId())).thenReturn(cliente);
        Cliente searchReturn = clienteService.findCliente(1L);

        assertNotNull(searchReturn.getId());
    }

    @Test(expected = ClientNotFoundException.class)
    public void should_client_not_found_when_call_method_find_client()
    {
        Cliente searchReturn = clienteService.findCliente(1L);
    }

    @Test
    public void should_return_client_updated()
    {
        ClienteRequest primaryCliente = ClienteBuilder.generateClient().now();
        String name = "Jose Amado";
        String cpf = "33333333333";

        when(clienteService.addCliente(primaryCliente)).thenReturn(new Cliente());
        Cliente cliente = clienteService.addCliente(primaryCliente);
        when(clienteRepository.findById(1L)).thenReturn(Optional.ofNullable(cliente));
        clienteService.updateCliente(cliente.getId(), name, cpf);
        assertEquals(cliente.getNome(), name);
        assertEquals(cliente.getCpf(), cpf);
    }

    @Test
    public void should_return_client_update_on_name_field()
    {
        ClienteRequest primaryCliente = ClienteBuilder.generateClient().now();
        String name = "Jose Amado";

        when(clienteService.addCliente(primaryCliente)).thenReturn(new Cliente());
        Cliente cliente = clienteService.addCliente(primaryCliente);
        when(clienteRepository.findById(1L)).thenReturn(Optional.ofNullable(cliente));
        clienteService.updateCliente(cliente.getId(), name, null);
        assertEquals(cliente.getNome(), name);
    }

    @Test
    public void should_return_client_update_on_cpf_field()
    {
        ClienteRequest primaryCliente = ClienteBuilder.generateClient().now();
        String cpf = "33333333333";

        when(clienteService.addCliente(primaryCliente)).thenReturn(new Cliente());
        Cliente cliente = clienteService.addCliente(primaryCliente);
        when(clienteRepository.findById(1L)).thenReturn(Optional.ofNullable(cliente));
        clienteService.updateCliente(cliente.getId(), null, cpf);
        assertEquals(cliente.getCpf(), cpf);
    }

    @Test(expected = UpdateClientException.class)
    public void should_return_update_error_when_trying_to_update_name_and_cpf_fields()
    {
        ClienteRequest primaryCliente = ClienteBuilder.generateClient().now();

        when(clienteService.addCliente(primaryCliente)).thenReturn(new Cliente());
        Cliente cliente = clienteService.addCliente(primaryCliente);
        when(clienteRepository.findById(1L)).thenReturn(Optional.ofNullable(cliente));
        clienteService.updateCliente(1L, null, null);
    }


    @Test(expected = UpdateClientException.class)
    public void should_return_update_error_when_trying_to_update_name_field()
    {
        ClienteRequest primaryCliente = ClienteBuilder.generateClient().now();
        String name = "";

        when(clienteService.addCliente(primaryCliente)).thenReturn(new Cliente());
        Cliente cliente = clienteService.addCliente(primaryCliente);
        when(clienteRepository.findById(1L)).thenReturn(Optional.ofNullable(cliente));
        clienteService.updateCliente(cliente.getId(), name, null);
    }

    @Test(expected = InvalidCpfException.class)
    public void should_return_update_error_when_trying_to_update_cpf_field()
    {
        ClienteRequest primaryCliente = ClienteBuilder.generateClient().now();
        String cpf = "";

        when(clienteService.addCliente(primaryCliente)).thenReturn(new Cliente());
        Cliente cliente = clienteService.addCliente(primaryCliente);
        when(clienteRepository.findById(1L)).thenReturn(Optional.ofNullable(cliente));
        clienteService.updateCliente(cliente.getId(), null, cpf);
    }

    @Test(expected = CPFAlreadyExistsException.class)
    public void should_return_update_error_when_trying_to_update_cpf_field_because_cpf_already_exists()
    {
        ClienteRequest primaryCliente = ClienteBuilder.generateClient().now();
        String cpf = primaryCliente.getCpf();

        when(clienteService.addCliente(primaryCliente)).thenReturn(new Cliente());
        Cliente cliente = clienteService.addCliente(primaryCliente);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.findByCpf(cpf)).thenReturn(cliente);
        clienteService.updateCliente(cliente.getId(), null, cpf);
    }

    @Test(expected = ClientNotFoundException.class)
    public void should_delete_client()
    {
        ClienteRequest clienteRequest = ClienteBuilder.generateClient().now();

        when(clienteService.addCliente(clienteRequest)).thenReturn(new Cliente());
        Cliente cliente = clienteService.addCliente(clienteRequest);

        clienteService.deleteCliente(cliente.getId());

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        Cliente searchReturn = clienteService.findCliente(1L);
    }


}
