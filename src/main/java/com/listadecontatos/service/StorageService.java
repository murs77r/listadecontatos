package com.listadecontatos.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.listadecontatos.model.Contact;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pela persistência (salvar e carregar) dos dados dos contatos.
 * Utiliza um arquivo JSON para armazenar a lista de contatos no sistema de arquivos local do usuário.
 */
public class StorageService {

    // ObjectMapper é a classe principal da biblioteca Jackson para converter Java -> JSON e JSON -> Java.
    private final ObjectMapper MAPPER = new ObjectMapper();
    // Path representa o caminho do arquivo onde os contatos são armazenados.
    private final Path FILE_PATH;

    /**
     * Construtor do serviço de armazenamento.
     * Define o local do arquivo de armazenamento e garante que o diretório e o arquivo existam.
     */
    public StorageService() {
        // Obtém o diretório home do usuário (ex: C:\Users\username).
        String userHome = System.getProperty("user.home");
        // Define um diretório específico para a aplicação dentro de AppData/Local para evitar poluir a pasta do usuário.
        // Este é um local padrão para dados de aplicativos no Windows.
        Path appData = Paths.get(userHome, "AppData", "Local", "Lista Contatos");
        this.FILE_PATH = appData.resolve("contacts.json"); // Combina o caminho do diretório com o nome do arquivo.
        initializeFile(); // Garante que o diretório e o arquivo inicial existam.
    }

    /**
     * Inicializa o arquivo de armazenamento.
     * Cria o diretório se ele não existir.
     * Cria um arquivo JSON vazio (com uma lista vazia "[]") se o arquivo não existir.
     */
    private void initializeFile() {
        try {
            // Verifica se o diretório pai do arquivo não existe.
            if (Files.notExists(FILE_PATH.getParent())) {
                // Cria todos os diretórios necessários no caminho.
                Files.createDirectories(FILE_PATH.getParent());
            }
            // Verifica se o arquivo em si não existe.
            if (Files.notExists(FILE_PATH)) {
                // Cria o arquivo e escreve "[]" para representar uma lista de contatos vazia em JSON.
                Files.write(FILE_PATH, "[]".getBytes());
            }
        } catch (IOException e) {
            // Imprime o stack trace em caso de erro de I/O, o que ajuda na depuração.
            e.printStackTrace();
        }
    }

    /**
     * Salva uma lista de contatos no arquivo JSON.
     * A lista de objetos Contact é serializada para o formato JSON.
     * @param contacts A lista de contatos a ser salva.
     */
    public void saveContacts(List<Contact> contacts) {
        try {
            // writerWithDefaultPrettyPrinter() formata o JSON de forma legível (com indentação).
            // writeValue() serializa a lista de contatos e a escreve no arquivo especificado.
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(FILE_PATH.toFile(), contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carrega a lista de contatos do arquivo JSON.
     * O conteúdo do arquivo JSON é desserializado para uma lista de objetos Contact.
     * @return Uma lista de contatos. Se o arquivo não existir ou ocorrer um erro, retorna uma lista vazia.
     */
    public List<Contact> loadContacts() {
        try {
            // Verifica se o arquivo existe antes de tentar lê-lo.
            if (Files.exists(FILE_PATH)) {
                // readValue() desserializa o JSON do arquivo.
                // TypeReference é usado para informar ao Jackson que o resultado deve ser uma Lista de Contatos (List<Contact>),
                // preservando as informações de tipo genérico que seriam perdidas de outra forma.
                return MAPPER.readValue(FILE_PATH.toFile(), new TypeReference<List<Contact>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Retorna uma lista vazia como fallback para evitar NullPointerException.
        return new ArrayList<>();
    }
}