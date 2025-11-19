package com.listadecontatos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carrega o arquivo FXML da pasta resources
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("Lista de Contatos JavaFX");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro: Não foi possível carregar /MainView.fxml");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}