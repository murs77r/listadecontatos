package com.listadecontatos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import com.listadecontatos.model.Contact;        // Importação atualizada
import com.listadecontatos.service.StorageService; // Importação atualizada

import java.util.regex.Pattern;
import com.listadecontatos.util.PhoneFormatter;

public class ContactController {

    @FXML private ListView<Contact> contactListView;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private Label statusLabel;

    private ObservableList<Contact> contactList;
    private StorageService storageService;
    private Contact currentSelection;

    private static final String PHONE_REGEX = "^\\((?:[1-9]{2})\\)\\s(?:[2-8]|9[1-9])\\d{3}-\\d{4}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    @FXML
    public void initialize() {
        storageService = new StorageService();
        contactList = FXCollections.observableArrayList();
        
        loadContacts();
        contactListView.setItems(contactList);
        setupSelectionListener();
        setupPhoneFormatting();
    }

    @FXML
    private void handleAddButton() {
        String name = nameField.getText();
        String phone = PhoneFormatter.format(phoneField.getText());

        if (name.isEmpty() || phone.isEmpty()) {
            statusLabel.setText("Preencha todos os campos.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!validatePhone(phone)) {
            statusLabel.setText("Telefone inválido! Use (XX) XXXXX-XXXX");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        Contact newContact = new Contact(name, phone);
        contactList.add(newContact);
        saveContacts();
        clearFields();
        statusLabel.setText("Contato salvo!");
        statusLabel.setStyle("-fx-text-fill: green;");
    }

    @FXML
    private void handleSaveButton() {
        if (currentSelection != null) {
             currentSelection.setFullName(nameField.getText());
             currentSelection.setPhoneNumber(PhoneFormatter.format(phoneField.getText()));
             // Make sure the field shows the formatted value
             phoneField.setText(currentSelection.getPhoneNumber());
             contactListView.refresh();
             saveContacts();
             statusLabel.setText("Editado com sucesso!");
        }
    }

    @FXML
    private void handleDeleteButton() {
        if (currentSelection != null) {
            contactList.remove(currentSelection);
            saveContacts();
            clearFields();
            statusLabel.setText("Contato excluído.");
        }
    }

    @FXML
    private void handleClearButton() {
        clearFields();
        contactListView.getSelectionModel().clearSelection();
        statusLabel.setText("");
    }

    private void setupSelectionListener() {
        contactListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                currentSelection = newValue;
                if (newValue != null) {
                    nameField.setText(newValue.getFullName());
                    phoneField.setText(PhoneFormatter.format(newValue.getPhoneNumber()));
                }
            }
        );
    }

    private void setupPhoneFormatting() {
        phoneField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) return;
            String formatted = PhoneFormatter.format(newVal);
            if (!formatted.equals(newVal)) {
                phoneField.setText(formatted);
                phoneField.positionCaret(formatted.length());
            }
        });
    }

    private void loadContacts() {
        java.util.List<Contact> loaded = storageService.loadContacts();
        // Normalize phone formatting for contacts loaded from storage
        for (Contact c : loaded) {
            c.setPhoneNumber(PhoneFormatter.format(c.getPhoneNumber()));
        }
        contactList.setAll(loaded);
    }

    private void saveContacts() {
        storageService.saveContacts(contactList);
    }

    private void clearFields() {
        nameField.clear();
        phoneField.clear();
        currentSelection = null;
    }

    // phone formatting is handled by com.listadecontatos.util.PhoneFormatter

    private boolean validatePhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }
}