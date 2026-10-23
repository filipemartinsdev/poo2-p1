package com.poo2p1.service;

import com.poo2p1.dto.CreateFuncionarioRequest;
import com.poo2p1.dto.SearchFilters;
import com.poo2p1.model.Cargo;
import com.poo2p1.model.Endereco;
import com.poo2p1.model.Funcionario;
import com.poo2p1.persistence.FuncionarioRepository;

import java.math.BigDecimal;
import java.util.List;

public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Funcionario> getAllBy(String matricula, Cargo cargo, String estado, Double salarioMin, Double salarioMax){
        SearchFilters filters = new SearchFilters(
                matricula != null && !matricula.isBlank() ? matricula : null,
                cargo,
                estado != null && !estado.isBlank() ? estado : null,
                salarioMin,
                salarioMax
        );

        return funcionarioRepository.findAll(filters);
    }

    public Funcionario create(CreateFuncionarioRequest request){
        validateRequest(request);

        Funcionario funcionario = new Funcionario(
                null,
                request.nome(),
                request.cpf(),
                request.dataNascimento(),
                request.cargo(),
                BigDecimal.valueOf(request.salario()),
                request.dataContratacao(),
                new Endereco(
                        null,
                        request.endereco().cidade(),
                        request.endereco().estado(),
                        request.endereco().cep(),
                        request.endereco().bairro(),
                        request.endereco().logradouro(),
                        request.endereco().numero() != null && !request.endereco().numero().isBlank() ? request.endereco().numero() : null,
                        request.endereco().complemento() != null && !request.endereco().complemento().isBlank() ? request.endereco().complemento() : null
                )
        );

        return funcionarioRepository.save(funcionario);
    }

    private void validateRequest(CreateFuncionarioRequest request) {
        if (request.nome() == null || request.nome().isBlank())
            throw new RuntimeException("Nome é obrigatório");

        if (request.cpf() == null || request.cpf().isBlank())
            throw new RuntimeException("CPF é obrigatório");

        if (request.cpf().length() != 11)
            throw new RuntimeException("Formato de CPF inválido");

        if (request.cargo() == null)
            throw new RuntimeException("Cargo é obrigatório");

        if (request.salario() == null)
            throw new RuntimeException("Salário é obrigatório");

        if (request.salario() <= 0)
            throw new RuntimeException("Salário deve ser maior que zero");

        if (request.dataNascimento() == null)
            throw new RuntimeException("Data de nascimento é obrigatória");

        if (request.dataContratacao() == null)
            throw new RuntimeException("Data de contratação é obrigatória");

        validateEnderecoRequest(request.endereco());
    }

    private void validateEnderecoRequest(CreateFuncionarioRequest.Endereco endereco) {
        if (endereco == null)
            throw new RuntimeException("Endereço é obrigatório");

        if (endereco.estado() == null || endereco.estado().isBlank())
            throw new RuntimeException("Estado é obrigatório");

        if (endereco.estado().length() != 2)
            throw new RuntimeException("Formato de estado inválido");

        if (endereco.cidade() == null || endereco.cidade().isBlank())
            throw new RuntimeException("Cidade é obrigatória");

        if (endereco.cep() == null || endereco.cep().isBlank())
            throw new RuntimeException("CEP é obrigatório");

        if (endereco.cep().length() != 8)
            throw new RuntimeException("Formato de CEP inválido");

        if (endereco.logradouro() == null || endereco.logradouro().isBlank())
            throw new RuntimeException("Logradouro é obrigatório");

        if (endereco.bairro() == null || endereco.bairro().isBlank())
            throw new RuntimeException("Bairro é obrigatório");
    }

    public void deleteByMatricula(String matricula) {
        funcionarioRepository.deleteByMatricula(matricula);
    }
}
