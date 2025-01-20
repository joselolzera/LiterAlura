# LiterAlura

<p align="center">
  <img src="https://github.com/a-mais/LiterAlura/blob/main/badge%20literalura.webp" alt="Badge LiterAlura" width="400">
</p>


![Badge Status: FINALIZADO](http://img.shields.io/static/v1?label=STATUS&message=%20FINALIZADO&color=red&style=for-the-badge)

Aplicação desenvolvida para gerenciamento de livros e autores, interagindo com a **Gutendex API** para buscar dados de obras e armazená-los em um banco de dados relacional. O projeto faz parte do *Challenge* **LiterAlura**, onde são propostas tarefas para criar e evoluir uma biblioteca online.

---

## Sumário

- [Funcionalidades](#funcionalidades)
- [Pré-requisitos](#pré-requisitos)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Executar](#como-executar)

---

## Funcionalidades

1. **Buscar livro pelo título**  
   - Realiza a consulta diretamente na [Gutendex API](https://gutendex.com/) e insere os dados do livro no banco de dados.
2. **Listar livros registrados**  
   - Exibe todos os livros que foram salvos no banco.
3. **Listar autores**  
   - Mostra os dados de cada autor cadastrado no sistema.
4. **Listar autores por ano**  
   - Permite filtrar os autores de acordo com um ano específico (por exemplo, ano de nascimento/falecimento).
5. **Listar livros por idioma**  
   - Oferece quatro opções de idioma para busca (Espanhol, Inglês, Francês e Português).
0. **Sair**  
   - Encerra a aplicação.

---

## Pré-requisitos

- **Java 17+**  
  Para compilar e executar a aplicação Spring Boot adequadamente.
- **Maven** ou **Gradle**  
  Para gerenciar dependências e compilar o projeto.
- **Banco de dados PostgreSQL** (ou outro compatível), configurado nas propriedades da aplicação (`application.properties` ou `application.yml`).
- **IDE** (IntelliJ, Eclipse, VS Code ou outra de sua preferência) ou **terminal** com os comandos Maven/Gradle.

---

## Tecnologias Utilizadas

- **Spring Boot 3+**
- **Spring Data JPA** (para o acesso e persistência dos dados)
- **Hibernate** (implementação JPA)
- **PostgreSQL** (banco de dados relacional)
- **Gutendex API** (API utilizada para buscar os dados dos livros)
- **Maven** *(ou Gradle)* para gerenciamento de dependências

---

## Como Executar

1. **Clonar o repositório**:
   ```bash
   git clone https://github.com/usuario/LiterAlura.git
