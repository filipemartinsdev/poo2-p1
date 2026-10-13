package com.poo2p1;

import atlantafx.base.theme.NordLight;
import com.poo2p1.controller.MainController;
import com.poo2p1.model.Funcionario;
import com.poo2p1.persistence.FuncionarioRepositoryCsv;
import com.poo2p1.service.FuncionarioService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FuncionarioApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FuncionarioApplication.class.getResource("index.fxml"));

        fxmlLoader.setControllerFactory(type -> {
            if (type == MainController.class)
                return new MainController(
                        new FuncionarioService(
                                new FuncionarioRepositoryCsv()
                        )
                );

            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        FuncionarioApplication.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());

        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("Registro de funcionários");
        stage.setScene(scene);
        stage.show();
    }
}
