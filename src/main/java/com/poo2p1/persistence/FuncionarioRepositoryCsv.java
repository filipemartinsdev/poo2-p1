package com.poo2p1.persistence;

import com.poo2p1.dto.SearchFilters;
import com.poo2p1.model.Cargo;
import com.poo2p1.model.Endereco;
import com.poo2p1.model.Funcionario;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FuncionarioRepositoryCsv implements FuncionarioRepository {
    private final Path FUNCIONARIOS_FILE_PATH = Path.of("funcionarios.csv");
    private final Path ENDERECOS_FILE_PATH = Path.of("enderecos.csv");

    private void createFuncionariosFile(){
        try {
            Files.createFile(FUNCIONARIOS_FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createEnderecosFile(){
        try {
            Files.createFile(ENDERECOS_FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Funcionario> findAll(SearchFilters filters) {
        Map<Integer, Endereco> enderecos = readAllEnderecos().stream()
                .collect(Collectors.toMap(Endereco::getId, e -> e));

        Predicate<Funcionario> matriculaFilter = filters.matricula != null
                ? (funcionario -> funcionario.getMatricula().equals(filters.matricula))
                : funcionario -> true;

        Predicate<Funcionario> cargoFilter = filters.cargo != null
                ? (funcionario -> funcionario.getCargo().equals(filters.cargo))
                : funcionario -> true;

        Predicate<Funcionario> estadoFilter = filters.estado != null
                ? (funcionario -> funcionario.getEndereco().getEstado().equalsIgnoreCase(filters.estado))
                : funcionario -> true;

        Predicate<Funcionario> salarioMinFilter = filters.salarioMin != null
                ? (funcionario -> funcionario.getSalario().compareTo(BigDecimal.valueOf(filters.salarioMin)) >= 0)
                : funcionario -> true;

        Predicate<Funcionario> salarioMaxFilter = filters.salarioMax != null
                ? (funcionario -> funcionario.getSalario().compareTo(BigDecimal.valueOf(filters.salarioMax)) <= 0)
                : funcionario -> true;

        return readAllFuncionarios().stream()
                .map(funcionario -> {
                    var enderecoId = funcionario.getEndereco().getId();
                    funcionario.setEndereco(enderecos.get(enderecoId));
                    return funcionario;
                })
                .filter(matriculaFilter)
                .filter(cargoFilter)
                .filter(estadoFilter)
                .filter(salarioMinFilter)
                .filter(salarioMaxFilter)
                .toList();
    }

    private List<Funcionario> readAllFuncionarios(){
        if (Files.notExists(FUNCIONARIOS_FILE_PATH))
            createFuncionariosFile();

        try {
            return new ArrayList<>(
                    Files.readAllLines(FUNCIONARIOS_FILE_PATH).stream()
                            .map(EntityParser::toFuncionario)
                            .toList()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Endereco> readAllEnderecos(){
        if (Files.notExists(ENDERECOS_FILE_PATH))
            createEnderecosFile();

        try {
            return new ArrayList<>(Files.readAllLines(ENDERECOS_FILE_PATH).stream()
                    .map(EntityParser::toEndereco)
                    .toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void appendEndereco(Endereco endereco){
        if (Files.notExists(ENDERECOS_FILE_PATH))
            createEnderecosFile();

        List<Endereco> lines = readAllEnderecos();
        lines.add(endereco);

        try {
            Files.write(ENDERECOS_FILE_PATH, lines.stream().map(EntityParser::toCsvLine).toList());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void appendFuncionario(Funcionario funcionario){
        if (Files.notExists(FUNCIONARIOS_FILE_PATH))
            createFuncionariosFile();

        List<Funcionario> lines = readAllFuncionarios();
        lines.add(funcionario);

        try {
            Files.write(FUNCIONARIOS_FILE_PATH, lines.stream().map(EntityParser::toCsvLine).toList());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void writeFuncionarios(List<Funcionario> funcionarios){
        if (Files.notExists(FUNCIONARIOS_FILE_PATH))
            createFuncionariosFile();

        try {
            Files.write(FUNCIONARIOS_FILE_PATH, funcionarios.stream().map(EntityParser::toCsvLine).toList());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void writeEnderecos(List<Endereco> enderecos){
        if (Files.notExists(ENDERECOS_FILE_PATH))
            createEnderecosFile();

        try {
            Files.write(ENDERECOS_FILE_PATH, enderecos.stream().map(EntityParser::toCsvLine).toList());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Funcionario save(Funcionario funcionario) {
        if (funcionario.getMatricula() == null)
            createIDs(funcionario);

        appendEndereco(funcionario.getEndereco());
        appendFuncionario(funcionario);

        return funcionario;
    }

    @Override
    public void deleteByMatricula(String matricula) {
        List<Funcionario> funcionarios = readAllFuncionarios().stream()
                .toList();

        Integer enderecoId = funcionarios.stream()
                .filter(f -> f.getMatricula().equals(matricula))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"))
                .getEndereco().getId();

        List<Funcionario> newFuncionarios = funcionarios.stream()
                .filter(f -> !f.getMatricula().equals(matricula))
                .toList();

        writeFuncionarios(newFuncionarios);

        List<Endereco> newEnderecos = readAllEnderecos().stream()
                .filter(e -> !e.getId().equals(enderecoId))
                .toList();

        writeEnderecos(newEnderecos);
    }

    private void createIDs(Funcionario funcionario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");

        Integer lastFuncionarioId = getLastFuncionarioId();

        String textId = String.format("%06d", lastFuncionarioId + 1);

        if (textId.length() > 6)
            throw new RuntimeException("Quantidade de matrículas no limite");

        funcionario.setMatricula(
                String.join("-",
                        LocalDate.now().format(formatter),
                        textId
                )
        );

        funcionario.getEndereco().setId(
                getLastEnderecoId() + 1
        );
    }

    private Integer getLastFuncionarioId(){
        List<Funcionario> funcionarios = readAllFuncionarios();

        if (funcionarios.isEmpty())
            return -1;

        Funcionario funcionario = funcionarios.getLast();

        return Integer.parseInt(
            funcionario.getMatricula().split("-")[1]
        );
    }

    private Integer getLastEnderecoId(){
        List<Endereco> enderecos = readAllEnderecos();

        if (enderecos.isEmpty())
            return -1;

        Endereco endereco = enderecos.getLast();

        return endereco.getId();
    }

    public static class EntityParser {
        public static Funcionario toFuncionario(String csvLine){
            String[] attributes = csvLine.split(";");

            Endereco endereco = new Endereco();
            endereco.setId(Integer.parseInt(attributes[7]));

            return new Funcionario(
                    attributes[0],
                    attributes[1],
                    attributes[2],
                    LocalDate.parse(attributes[3]),
                    Enum.valueOf(Cargo.class, attributes[4]),
                    new BigDecimal(attributes[5]),
                    LocalDate.parse(attributes[6]),
                    endereco
            );
        }

        public static Endereco toEndereco(String csvLine){
            String[] attributes = csvLine.split(";");

            return new Endereco(
                    Integer.parseInt(attributes[0]),
                    attributes[1],
                    attributes[2],
                    attributes[3],
                    attributes[4],
                    attributes[5],
                    attributes[6],
                    attributes[7]
            );
        }

        public static String toCsvLine(Funcionario funcionario){
            return String.join(";",
                    funcionario.getMatricula(),
                    funcionario.getNome(),
                    funcionario.getCPF(),
                    funcionario.getDataNascimento().toString(),
                    funcionario.getCargo().toString(),
                    funcionario.getSalario().toString(),
                    funcionario.getDataContratacao().toString(),
                    funcionario.getEndereco().getId().toString()
            );
        }

        public static String toCsvLine(Endereco endereco){
            return String.join(";",
                    endereco.getId().toString(),
                    endereco.getCidade(),
                    endereco.getEstado().toUpperCase(),
                    endereco.getCep(),
                    endereco.getBairro(),
                    endereco.getLogradouro(),
                    endereco.getNumero(),
                    endereco.getComplemento()
            );
        }
    }
}
