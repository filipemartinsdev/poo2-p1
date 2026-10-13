package com.poo2p1.dto;

import com.poo2p1.model.Cargo;

import java.time.LocalDate;

public record CreateFuncionarioRequest(
        String nome,
        String cpf,
        LocalDate dataNascimento,
        Cargo cargo,
        Double salario,
        LocalDate dataContratacao,
        Endereco endereco
) {

    public record Endereco(
            String estado,
            String cidade,
            String cep,
            String logradouro,
            String numero,
            String bairro,
            String complemento
    ) {
    }
}
