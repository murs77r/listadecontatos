package com.listadecontatos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Classe principal da aplicação "Lista de Contatos".
 * Esta classe é o ponto de entrada para uma aplicação JavaFX.
 * Ela estende javafx.application.Application e é responsável por carregar a interface gráfica inicial.
 */
public class Main extends Application {

    /**
     * O método principal da aplicação JavaFX, chamado após o método init().
     * É aqui que a interface gráfica do usuário (UI) é inicializada.
     *
     * @param primaryStage O "palco" principal da aplicação, fornecido pelo runtime do JavaFX.
     *                     O Stage é a janela principal onde a cena (Scene) é exibida.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // FXMLLoader é usado para carregar a hierarquia de objetos de um documento FXML.
            // getClass().getResource("/MainView.fxml") localiza o arquivo FXML no diretório de resources.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
            Parent root = loader.load(); // Carrega o FXML e infla a árvore de componentes da UI.

            // A Scene contém o grafo de cena (scene graph), que é a árvore de todos os nós visuais.
            Scene scene = new Scene(root);
            
            // Configurações da janela principal (Stage).
            primaryStage.setTitle("Lista de Contatos"); // Define o título da janela.
            primaryStage.setScene(scene); // Associa a cena à janela.
            primaryStage.setResizable(false); // Impede que o usuário redimensione a janela.
            primaryStage.show(); // Exibe a janela para o usuário.

        } catch (IOException e) {
            // Captura exceções que podem ocorrer durante o carregamento do FXML.
            e.printStackTrace();
            System.err.println("Erro: Não foi possível carregar /MainView.fxml. Verifique se o arquivo está no local correto e se não contém erros.");
        }
    }

    /**
     * O método main é o ponto de entrada padrão para qualquer aplicação Java.
     * Para aplicações JavaFX, ele geralmente apenas chama o método launch(),
     * que inicializa o toolkit do JavaFX e chama o método start().
     *
     * @param args Argumentos de linha de comando (não utilizados nesta aplicação).
     */
    public static void main(String[] args) {
        launch(args);
    }
}