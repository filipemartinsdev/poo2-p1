package com.poo2p1.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private Label LabelFiltroCargo;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnRegistrar;

    @FXML
    private ComboBox<?> cbCargo;

    @FXML
    private ComboBox<?> cbFiltroCargo;

    @FXML
    private TableColumn<?, ?> colBuscarCargo;

    @FXML
    private TableColumn<?, ?> colBuscarContratacao;

    @FXML
    private TableColumn<?, ?> colBuscarCpf;

    @FXML
    private TableColumn<?, ?> colBuscarMatricula;

    @FXML
    private TableColumn<?, ?> colBuscarNascimento;

    @FXML
    private TableColumn<?, ?> colBuscarNome;

    @FXML
    private TableColumn<?, ?> colBuscarSalario;

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
    private TextField inputFiltroEstado;

    @FXML
    private TextField inputFiltroMatricula;

    @FXML
    private TextField inputLogradouro;

    @FXML
    private TextField inputNome;

    @FXML
    private TextField inputNumero;

    @FXML
    private TextField inputSalario;

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
    private Label labelFiltroEstado;

    @FXML
    private Label labelFiltroMatricula;

    @FXML
    private Label labelLogradouro;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelNumero;

    @FXML
    private Label labelSalario;

    @FXML
    private TableView<?> tableBuscar;

    @FXML
    void buscar(ActionEvent event) {

    }

    @FXML
    void registrar(ActionEvent event) {

    }

}
