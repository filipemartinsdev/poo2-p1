package com.poo2p1.controller;

import com.poo2p1.dto.CreateFuncionarioRequest;
import com.poo2p1.dto.SearchFilters;
import com.poo2p1.dto.TableBuscarItem;
import com.poo2p1.model.Cargo;
import com.poo2p1.model.Endereco;
import com.poo2p1.model.Funcionario;
import com.poo2p1.service.FuncionarioService;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainController {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final FuncionarioService funcionarioService;

    public MainController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @FXML
    private Label labelFiltroMatricula;

    @FXML
    private TextField inputFiltroMatricula;

    @FXML
    private Label labelFiltroCargo;

    @FXML
    private ComboBox<Cargo> cbFiltroCargo;

    @FXML
    private Label labelFiltroEstado;

    @FXML
    private TextField inputFiltroEstado;

    @FXML
    private TableView<TableBuscarItem> tableBuscar;

    private ObservableList<TableBuscarItem> tableBuscarItems = FXCollections.observableArrayList();

    @FXML
    private TableColumn<TableBuscarItem, Cargo> colBuscarCargo;

    @FXML
    private TableColumn<TableBuscarItem, LocalDate> colBuscarContratacao;

    @FXML
    private TableColumn<TableBuscarItem, String> colBuscarCpf;

    @FXML
    private TableColumn<TableBuscarItem, String> colBuscarMatricula;

    @FXML
    private TableColumn<TableBuscarItem, LocalDate> colBuscarNascimento;

    @FXML
    private TableColumn<TableBuscarItem, String> colBuscarNome;

    @FXML
    private TableColumn<TableBuscarItem, BigDecimal> colBuscarSalario;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnLimparFiltros;

    @FXML
    private Button btnRegistrar;

    @FXML
    private ComboBox<Cargo> cbCargo;

    @FXML
    private DatePicker dateDataContratacao;

    @FXML
    private DatePicker dateDataNascimento;

    @FXML
    private TextField inputBairro;

    @FXML
    private TextField inputCep;

    @FXML
    private TextField inputCidade;

    @FXML
    private TextField inputComplemento;

    @FXML
    private TextField inputCpf;

    @FXML
    private TextField inputEstado;

    @FXML
    private TextField inputLogradouro;

    @FXML
    private TextField inputNome;

    @FXML
    private TextField inputNumero;

    @FXML
    private Spinner<Double> spinSalario;

    @FXML
    private Label labelBairro;

    @FXML
    private Label labelCargo;

    @FXML
    private Label labelCep;

    @FXML
    private Label labelCidade;

    @FXML
    private Label labelComplemento;

    @FXML
    private Label labelCpf;

    @FXML
    private Label labelDataContratacao;

    @FXML
    private Label labelDataNascimento;

    @FXML
    private Label labelEstado;

    @FXML
    private Label labelLogradouro;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelNumero;

    @FXML
    private Label labelSalario;


    @FXML
    void initialize(){
        Platform.runLater(this::setupCbFiltroCargo);
        Platform.runLater(this::setupInputFiltroMatricula);
        Platform.runLater(this::setupInputFiltroEstado);
        Platform.runLater(this::setupTableBuscar);

        Platform.runLater(this::setupInputNome);
        Platform.runLater(this::setupInputCpf);
        Platform.runLater(this::setupCbCargo);
        Platform.runLater(this::setupSpinSalario);
        Platform.runLater(this::setupDateNascimento);
        Platform.runLater(this::setupDateContratacao);
        Platform.runLater(this::setupInputEstado);
        Platform.runLater(this::setupInputCidade);
        Platform.runLater(this::setupInputCep);
        Platform.runLater(this::setupInputLogradouro);
        Platform.runLater(this::setupInputNumero);
        Platform.runLater(this::setupInputBairro);
        Platform.runLater(this::setupInputComplemento);
    }


    private void setupCbFiltroCargo() {
        this.cbFiltroCargo.getItems().addAll(Cargo.values());

        this.cbFiltroCargo.setConverter(new StringConverter<Cargo>() {
            @Override
            public String toString(Cargo object) {
                return object != null ? object.getNome() : "";
            }

            @Override
            public Cargo fromString(String string) {
                return null;
            }
        });

        this.labelFiltroCargo.setLabelFor(this.cbFiltroCargo);
    }

    private void setupInputFiltroMatricula(){
        this.labelFiltroMatricula.setLabelFor(this.inputFiltroMatricula);
    }

    private void setupInputFiltroEstado(){
        this.labelFiltroEstado.setLabelFor(this.inputFiltroEstado);
    }

    private void setupTableBuscar() {
        this.tableBuscar.setItems(tableBuscarItems);

        this.setupColBuscarMatricula();
        this.setupColBuscarNome();
        this.setupColBuscarCpf();
        this.setupColBuscarDataNascimento();
        this.setupColBuscarCargo();
        this.setupColBuscarSalario();
        this.setupColBuscarDataContratacao();
    }

    private void setupColBuscarMatricula(){
        this.colBuscarMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
    }

    private void setupColBuscarNome(){
        this.colBuscarNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }

    private void setupColBuscarCpf(){
        this.colBuscarCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    }

    private void setupColBuscarDataNascimento(){
        this.colBuscarNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));

        this.colBuscarNascimento.setCellFactory(column -> new TableCell<>(){
            @Override
            protected void updateItem(LocalDate item, boolean empty){
                setText(item != null ? dateFormatter.format(item) : "");
            }
        });
    }

    private void setupColBuscarCargo(){
        this.colBuscarCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));

        this.colBuscarCargo.setCellFactory(column -> new TableCell<>(){
            @Override
            protected void updateItem(Cargo item, boolean empty){
                setText(item != null ? item.getNome() : "");
            }
        });
    }

    private void setupColBuscarSalario(){
        this.colBuscarSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));

        this.colBuscarSalario.setCellFactory(column -> new TableCell<>(){
            @Override
            protected void updateItem(BigDecimal item, boolean empty){
                setText(item != null ? String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", item) : "");
            }
        });
    }

    private void setupColBuscarDataContratacao(){
        this.colBuscarContratacao.setCellValueFactory(new PropertyValueFactory<>("dataContratacao"));

        this.colBuscarContratacao.setCellFactory(column -> new TableCell<>(){
            @Override
            protected void updateItem(LocalDate item, boolean empty){
                setText(item != null ? dateFormatter.format(item) : "");
            }
        });
    }


    private void setupInputNome(){
        this.labelNome.setLabelFor(this.inputNome);
    }

    private void setupInputCpf(){
        this.labelCpf.setLabelFor(this.inputCpf);
    }

    private void setupCbCargo() {
        this.cbCargo.getItems().addAll(Cargo.values());

        this.cbCargo.setConverter(new StringConverter<Cargo>() {
            @Override
            public String toString(Cargo object) {
                return object != null ? object.getNome() : "";
            }

            @Override
            public Cargo fromString(String string) {
                return null;
            }
        });

        this.labelCargo.setLabelFor(this.cbCargo);
    }

    private void setupSpinSalario(){
        this.spinSalario.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0, 100));
        this.spinSalario.setEditable(true);

        this.labelSalario.setLabelFor(this.spinSalario);
    }

    private void setupDateNascimento(){
        this.dateDataNascimento.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String text) {
                if (text == null || text.isBlank()) {
                    return null;
                }

                return LocalDate.parse(text, dateFormatter);
            }
        });

        this.labelDataNascimento.setLabelFor(this.dateDataNascimento);
    }

    private void setupDateContratacao(){
        this.dateDataContratacao.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String text) {
                if (text == null || text.isBlank()) {
                    return null;
                }

                return LocalDate.parse(text, dateFormatter);
            }
        });

        this.labelDataContratacao.setLabelFor(this.dateDataContratacao);
    }

    private void setupInputEstado(){
        this.labelEstado.setLabelFor(this.inputEstado);
    }

    private void setupInputCidade(){
        this.labelCidade.setLabelFor(this.inputCidade);
    }

    private void setupInputCep(){
        this.labelCep.setLabelFor(this.inputCep);
    }

    private void setupInputLogradouro(){
        this.labelLogradouro.setLabelFor(this.inputLogradouro);
    }

    private void setupInputNumero(){
        this.labelNumero.setLabelFor(this.inputNumero);
    }

    private void setupInputBairro(){
        this.labelBairro.setLabelFor(this.inputBairro);
    }

    private void setupInputComplemento(){
        this.labelComplemento.setLabelFor(this.inputComplemento);
    }

    @FXML
    void limparFiltros(){
        inputFiltroMatricula.clear();
        inputFiltroEstado.clear();
        cbFiltroCargo.setValue(null);
    }

    @FXML
    void buscar(ActionEvent event) {
        Task<List<Funcionario>> task = new Task<>() {
            @Override
            protected List<Funcionario> call() throws Exception {
                return funcionarioService.getAllBy(
                    inputFiltroMatricula.getText(),
                    cbFiltroCargo.getValue(),
                    inputFiltroEstado.getText()
                );
            }
        };

        task.setOnFailed(e -> {
            showError("Não encontrado", "Não foi possível encontrar os funcionarios");
        });

        task.setOnSucceeded(e -> {
            if (task.getValue().isEmpty()) {
                tableBuscarItems.clear();
                showInfo("Arquivo vazio", "Nenhum funcionário cadastrado");
            }
            else
                updateTableBuscar(task.getValue());
        });

        new Thread(task).start();
    }

    private void updateTableBuscar(List<Funcionario> value) {
        tableBuscarItems.clear();

        tableBuscarItems.addAll(value.stream()
                .map(this::toTableItem)
                .toList()
        );
    }

    private TableBuscarItem toTableItem(Funcionario funcionario) {
        return new TableBuscarItem(
                funcionario.getMatricula(),
                funcionario.getNome(),
                funcionario.getCPF(),
                funcionario.getDataNascimento(),
                funcionario.getCargo(),
                funcionario.getSalario(),
                funcionario.getDataContratacao(),
                funcionario.getEndereco()
        );
    }

    @FXML
    void registrar(ActionEvent event) {
        Task<Funcionario> task = new Task<Funcionario>() {
            @Override
            protected Funcionario call() throws Exception {
                return funcionarioService.create(new CreateFuncionarioRequest(
                        inputNome.getText(),
                        inputCpf.getText(),
                        dateDataNascimento.getValue(),
                        cbCargo.getValue(),
                        spinSalario.getValue(),
                        dateDataContratacao.getValue(),
                        new CreateFuncionarioRequest.Endereco(
                                inputEstado.getText(),
                                inputCidade.getText(),
                                inputCep.getText(),
                                inputLogradouro.getText(),
                                inputNumero.getText(),
                                inputBairro.getText(),
                                inputComplemento.getText()
                        )
                ));
            }
        };

        task.setOnSucceeded(e -> {
            showInfo("Funcionário cadastrado com sucesso", "Funcionário cadastrado com a matrícula " + task.getValue().getMatricula());
        });

        task.setOnFailed(e -> {
            showError("Erro ao cadastrar usuário", e.getSource().getException().getMessage());
        });

        new Thread(task).start();
    }

    @FXML
    void verEndereco(ActionEvent event){
        Endereco endereco = tableBuscar.getSelectionModel().getSelectedItem().getEndereco();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Info");
        alert.setHeaderText("Endereço do cliente");

        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("Estado: " + endereco.getEstado()),
                new Label("Cidade: " + endereco.getCidade()),
                new Label("CEP: " + endereco.getCep()),
                new Label("Bairro: " + endereco.getBairro()),
                new Label("Logradouro: " + endereco.getLogradouro()),
                new Label("Numero: " + endereco.getNumero()),
                new Label("Complemento: " + endereco.getComplemento())
        );

        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    @FXML
    void excluirFuncionario(ActionEvent event){
        String matricula = tableBuscar.getSelectionModel().getSelectedItem().getMatricula();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                funcionarioService.deleteByMatricula(matricula);
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            tableBuscarItems.clear();
            showInfo("Funcionário excluído", "Funcionário com a matrícula " + matricula + "excluído com sucesso");
        });

        task.setOnFailed(e -> {
            showError("Erro ao excluir funcionário", e.getSource().getException().getMessage());
        });

        new Thread(task).start();
    }

    private void showError(String headerText, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String headerText, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
