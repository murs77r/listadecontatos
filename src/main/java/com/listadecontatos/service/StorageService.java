package com.listadecontatos.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.listadecontatos.model.Contact; // Importação atualizada

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StorageService {

    private final ObjectMapper MAPPER = new ObjectMapper();
    private final Path FILE_PATH;

    public StorageService() {
        String userHome = System.getProperty("user.home");
        // Salva em AppData/Local/Lista Contatos
        Path appData = Paths.get(userHome, "AppData", "Local", "Lista Contatos");
        this.FILE_PATH = appData.resolve("contacts.json");
        initializeFile();
    }

    private void initializeFile() {
        try {
            if (Files.notExists(FILE_PATH.getParent())) {
                Files.createDirectories(FILE_PATH.getParent());
            }
            if (Files.notExists(FILE_PATH)) {
                Files.write(FILE_PATH, "[]".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveContacts(List<Contact> contacts) {
        try {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(FILE_PATH.toFile(), contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Contact> loadContacts() {
        try {
            if (Files.exists(FILE_PATH)) {
                return MAPPER.readValue(FILE_PATH.toFile(), new TypeReference<List<Contact>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}