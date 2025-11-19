# Lista de Contatos em JavaFX

Este projeto é uma aplicação de desktop simples para gerenciamento de uma lista de contatos, desenvolvida em Java com a biblioteca JavaFX.

## Objetivo

O principal objetivo deste projeto é aplicar os conceitos de Programação Orientada a Objetos (POO) em um cenário prático. A aplicação permite ao usuário:

*   Adicionar novos contatos (nome e telefone).
*   Visualizar a lista de contatos existentes.
*   Editar as informações de um contato selecionado.
*   Excluir um contato da lista.
*   Limpar os campos do formulário.

Os dados são persistidos localmente em um arquivo `contacts.json`, localizado na pasta `AppData/Local/Lista Contatos` do diretório do usuário.

## Contexto Acadêmico

Este projeto foi desenvolvido como parte da avaliação da disciplina de **Linguagem de Programação Orientada a Objetos I**, ministrada pelo Professor **Alexsander Holanda Barreto**.

## Tecnologias Utilizadas

*   **Java 17**
*   **JavaFX 21**: Para a construção da interface gráfica.
*   **Maven**: Para gerenciamento de dependências e build do projeto.
*   **Jackson**: Para serialização e desserialização dos dados dos contatos em formato JSON.
*   **JUnit 5**: Para a implementação de testes unitários.

## Como Executar

1.  Certifique-se de ter o Java 17 (ou superior) e o Maven instalados e configurados no seu ambiente.
2.  Clone este repositório.
3.  Abra um terminal na raiz do projeto e execute o seguinte comando Maven para iniciar a aplicação:
    ```sh
    mvn clean javafx:run
    ```
