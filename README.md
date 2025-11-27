# Lista de Contatos em JavaFX

Este projeto é uma aplicação de desktop simples para gerenciamento de uma lista de contatos, desenvolvida em Java com a biblioteca JavaFX.

## Objetivo

O principal objetivo deste projeto é aplicar os conceitos de Programação Orientada a Objetos (POO) em um cenário prático. A aplicação permite ao usuário:

* Adicionar novos contatos (nome e telefone).
* Visualizar a lista de contatos existentes.
* Editar as informações de um contato selecionado.
* Excluir um contato da lista.
* Limpar os campos do formulário.

Os dados são persistidos localmente em um arquivo `contacts.json`, localizado na pasta `AppData/Local/Lista Contatos` do diretório do usuário.

## Contexto Acadêmico

Este projeto foi desenvolvido como parte da avaliação da disciplina de **Linguagem de Programação Orientada a Objetos I**, ministrada pelo Professor **Alexsander Holanda Barreto**.

## Tecnologias Utilizadas

* **Java 17**
* **JavaFX 21**: Para a construção da interface gráfica.
* **Maven**: Para gerenciamento de dependências e build do projeto.
* **Jackson**: Para serialização e desserialização dos dados dos contatos em formato JSON.
* **JUnit 5**: Para a implementação de testes unitários.

---

## Como Executar o Projeto

### Pré-requisitos
Certifique-se de ter instalado em sua máquina:
* Java JDK 17 ou superior.
* Apache Maven.

### Passo a Passo para Execução
1.  Clone este repositório ou baixe os arquivos.
2.  Abra o terminal na pasta raiz do projeto (onde está o arquivo `pom.xml`).
3.  Compile o projeto e baixe as dependências executando:
    ```bash
    mvn clean install
    ```
4.  Execute a aplicação com o plugin do JavaFX:
    ```bash
    mvn javafx:run
    ```

---

## Como Usar a Aplicação

A interface é dividida em duas partes: a **Lista de Contatos** à esquerda e o **Formulário de Contato** à direita.

### 1. Adicionar um Novo Contato
1.  Localize o formulário à direita da tela.
2.  No campo **"Nome Completo"**, digite o nome do contato (ex: *Ana Silva*).
3.  No campo **"Telefone com DDD"**, digite o número.
    * *Nota:* O campo formata automaticamente o número enquanto você digita para o padrão `(XX) XXXXX-XXXX`.
4.  Clique no botão **"Adicionar"**.
    * Se os dados estiverem válidos, a mensagem "Contato salvo!" aparecerá em verde e o nome será adicionado à lista à esquerda.
    * Se o formato do telefone estiver incorreto ou faltar o nome, uma mensagem de erro aparecerá.

### 2. Editar um Contato Existente
1.  Na lista à esquerda, clique sobre o nome do contato que deseja alterar.
    * Os dados do contato selecionado serão carregados automaticamente nos campos do formulário.
2.  Altere o **Nome** ou o **Telefone** conforme necessário.
3.  Clique no botão **"Salvar Edição"**.
    * A lista será atualizada com as novas informações e a mensagem "Editado com sucesso!" será exibida.

### 3. Excluir um Contato
1.  Selecione o contato que deseja remover na lista à esquerda.
2.  Clique no botão vermelho **"Excluir"**.
    * O contato será removido permanentemente da lista e do arquivo de salvamento.

### 4. Limpar o Formulário
* Para limpar os campos de texto e cancelar a seleção atual da lista, clique no botão **"Limpar"**.
    * Isso é útil se você começou a editar um contato mas decidiu adicionar um novo em vez disso.
