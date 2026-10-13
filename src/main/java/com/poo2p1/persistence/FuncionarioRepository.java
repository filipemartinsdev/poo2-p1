package com.poo2p1.persistence;

import com.poo2p1.dto.SearchFilters;
import com.poo2p1.model.Funcionario;

import java.util.List;

public interface FuncionarioRepository {
    List<Funcionario> findAll(SearchFilters filters);
    Funcionario save(Funcionario funcionario);
    void deleteByMatricula(String matricula);
}
