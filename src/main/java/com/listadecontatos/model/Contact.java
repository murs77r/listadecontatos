package com.listadecontatos.model;

/**
 * Classe de modelo que representa um Contato.
 * Esta é uma classe POJO (Plain Old Java Object) que encapsula os dados de um contato,
 * incluindo nome completo e número de telefone.
 * Ela é usada para transportar dados entre as camadas da aplicação (controller, service)
 * e para ser serializada/desserializada para/de JSON.
 */
public class Contact {
    private String fullName;    // Armazena o nome completo do contato.
    private String phoneNumber; // Armazena o número de telefone do contato.

    /**
     * Construtor padrão (sem argumentos).
     * Necessário para frameworks como o Jackson (para desserialização de JSON),
     * que precisam instanciar o objeto antes de popular seus campos.
     */
    public Contact() {}

    /**
     * Construtor parametrizado para criar uma instância de Contato com dados iniciais.
     * @param fullName O nome completo do contato.
     * @param phoneNumber O número de telefone do contato.
     */
    public Contact(String fullName, String phoneNumber) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    // --- Getters e Setters ---
    // Métodos de acesso padrão para ler e modificar os atributos privados da classe,
    // seguindo o princípio de encapsulamento.

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    /**
     * Sobrescreve o método toString() da classe Object.
     * O JavaFX ListView usa este método por padrão para determinar o texto a ser exibido
     * para cada item na lista. Neste caso, exibirá apenas o nome do contato.
     * @return O nome completo do contato.
     */
    @Override
    public String toString() {
        return fullName;
    }
}