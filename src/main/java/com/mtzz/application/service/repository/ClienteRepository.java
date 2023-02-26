package com.mtzz.application.service.repository;

import com.mtzz.application.service.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>
{
    public Cliente findByCpf(String cpf);
}
