package com.mtzz.application.service.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.PrePersist;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class ClienteRequest
{
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataCadastro;


    @PrePersist
    public void prePersist()
    {
        setDataCadastro(LocalDate.now());
    }
}
