package com.poo2p1.model;

public enum Cargo {
    ADMINISTRACAO("Administração"),
    FINANCEIRO("Financeiro"),
    TECNOLOGIA_DA_INFORMACAO("Tecnologia da Informação (TI)"),
    VENDAS("Vendas"),
    RECURSOS_HUMANOS("Recursos Humanos"),
    GERENCIA("Gerência"),
    PRODUCAO("Produção"),
    LOGISTICA("Logística"),
    OUTRO("Outro");

    private final String nome;

    Cargo(String nome) {
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }
}
