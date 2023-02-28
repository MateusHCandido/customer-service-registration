package com.mtzz.application.service.controller.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

}
