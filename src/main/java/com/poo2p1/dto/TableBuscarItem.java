package com.poo2p1.dto;

import com.poo2p1.model.Cargo;
import com.poo2p1.model.Endereco;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class TableBuscarItem {
    private final String matricula;
    private final String nome;
    private final String cpf;
    private final LocalDate dataNascimento;

    private final Cargo cargo;
    private final BigDecimal salario;
    private final LocalDate dataContratacao;

    private final Endereco endereco;

    public TableBuscarItem(String matricula, String nome, String cpf, LocalDate dataNascimento, Cargo cargo, BigDecimal salario, LocalDate dataContratacao, Endereco endereco) {
        this.matricula = matricula;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.cargo = cargo;
        this.salario = salario;
        this.dataContratacao = dataContratacao;
        this.endereco = endereco;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public Endereco getEndereco() {
        return endereco;
    }
}
