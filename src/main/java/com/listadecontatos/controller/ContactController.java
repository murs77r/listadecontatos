package com.listadecontatos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import com.listadecontatos.model.Contact;
import com.listadecontatos.service.StorageService;
import com.listadecontatos.util.PhoneFormatter;

import java.util.regex.Pattern;

/**
 * Controller para a view MainView.fxml.
 * Esta classe gerencia a lógica de interação entre a interface do usuário e os dados da aplicação.
 * Ela lida com eventos de botões, validação de entrada e comunicação com o serviço de armazenamento.
 */
public class ContactController {

    // @FXML: Anotação que injeta os componentes definidos no arquivo FXML nesta classe.
    @FXML private ListView<Contact> contactListView; // Exibe a lista de contatos.
    @FXML private TextField nameField; // Campo de texto para o nome do contato.
    @FXML private TextField phoneField; // Campo de texto para o telefone do contato.
    @FXML private Label statusLabel; // Rótulo para exibir mensagens de status ao usuário.

    // Uma lista observável que permite que a ListView seja atualizada automaticamente quando a lista muda.
    private ObservableList<Contact> contactList;
    // Serviço responsável por salvar e carregar os contatos.
    private StorageService storageService;
    // Armazena o contato atualmente selecionado na ListView.
    private Contact currentSelection;

    // Expressão regular para validar o formato do telefone: (XX) XXXXX-XXXX ou (XX) XXXX-XXXX.
    private static final String PHONE_REGEX = "^\\((?:[1-9]{2})\\)\\s(?:[2-8]|9[1-9])\\d{3}-\\d{4}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    /**
     * Método de inicialização, chamado automaticamente pelo FXMLLoader após a injeção dos campos @FXML.
     * É usado para configurar o estado inicial do controller.
     */
    @FXML
    public void initialize() {
        storageService = new StorageService();
        contactList = FXCollections.observableArrayList();
        
        loadContacts(); // Carrega os contatos do armazenamento.
        contactListView.setItems(contactList); // Associa a lista observável à ListView.
        setupSelectionListener(); // Configura o listener para seleção de itens na lista.
        setupPhoneFormatting(); // Configura a formatação automática do campo de telefone.
    }

    /**
     * Manipulador de evento para o botão "Adicionar".
     * Valida os campos, cria um novo contato, o adiciona à lista e salva.
     */
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

    /**
     * Manipulador de evento para o botão "Salvar Edição".
     * Atualiza os dados do contato selecionado com as informações dos campos de texto.
     */
    @FXML
    private void handleSaveButton() {
        if (currentSelection != null) {
             currentSelection.setFullName(nameField.getText());
             currentSelection.setPhoneNumber(PhoneFormatter.format(phoneField.getText()));
             phoneField.setText(currentSelection.getPhoneNumber()); // Garante que o campo exiba o valor formatado.
             contactListView.refresh(); // Força a atualização da exibição do item na ListView.
             saveContacts();
             statusLabel.setText("Editado com sucesso!");
        }
    }

    /**
     * Manipulador de evento para o botão "Excluir".
     * Remove o contato selecionado da lista e salva as alterações.
     */
    @FXML
    private void handleDeleteButton() {
        if (currentSelection != null) {
            contactList.remove(currentSelection);
            saveContacts();
            clearFields();
            statusLabel.setText("Contato excluído.");
        }
    }

    /**
     * Manipulador de evento para o botão "Limpar".
     * Limpa os campos de texto e a seleção da lista.
     */
    @FXML
    private void handleClearButton() {
        clearFields();
        contactListView.getSelectionModel().clearSelection();
        statusLabel.setText("");
    }

    /**
     * Configura um listener que observa mudanças na seleção da ListView.
     * Quando um item é selecionado, seus dados são carregados nos campos de texto.
     */
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

    /**
     * Configura um listener no campo de telefone para formatar o número em tempo real
     * enquanto o usuário digita.
     */
    private void setupPhoneFormatting() {
        phoneField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) return;
            String formatted = PhoneFormatter.format(newVal);
            // Para evitar um loop infinito, só atualiza o texto se ele realmente mudou.
            if (!formatted.equals(newVal)) {
                phoneField.setText(formatted);
                phoneField.positionCaret(formatted.length()); // Move o cursor para o final.
            }
        });
    }

    /**
     * Carrega os contatos do arquivo de armazenamento usando o StorageService.
     * Normaliza o formato do telefone de cada contato carregado.
     */
    private void loadContacts() {
        java.util.List<Contact> loaded = storageService.loadContacts();
        for (Contact c : loaded) {
            c.setPhoneNumber(PhoneFormatter.format(c.getPhoneNumber()));
        }
        contactList.setAll(loaded); // Adiciona todos os contatos carregados à lista observável.
    }

    /**
     * Salva a lista de contatos atual no arquivo de armazenamento.
     */
    private void saveContacts() {
        storageService.saveContacts(contactList);
    }

    /**
     * Limpa os campos de texto e a variável de seleção atual.
     */
    private void clearFields() {
        nameField.clear();
        phoneField.clear();
        currentSelection = null;
    }

    /**
     * Valida se uma string de telefone corresponde ao formato definido pela expressão regular.
     * @param phone O número de telefone a ser validado.
     * @return true se o formato for válido, false caso contrário.
     */
    private boolean validatePhone(String phone) {
        return PHONE_PATTERN.matcher(phone).matches();
    }
}