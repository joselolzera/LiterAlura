package alura.paulo.literalura.controller;

import alura.paulo.literalura.model.Livro;
import alura.paulo.literalura.repository.AutorRepository;
import alura.paulo.literalura.repository.LivroRepository;
import alura.paulo.literalura.service.GutendexAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Controller {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final GutendexAPI gutendexAPI;
    private final Scanner leitura = new Scanner(System.in);
    private final List<String> possibleQueryLanguages;

    private final String ENDERECO = "https://gutendex.com/books";
    private String complemento_titulo_autores = "?search=";
    private String complemento_idioma = "?languages=";

    public Controller(LivroRepository livroRepository,
                      AutorRepository autorRepository,
                      GutendexAPI gutendexAPI) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.gutendexAPI = gutendexAPI;

        this.possibleQueryLanguages = new ArrayList<>();
        this.possibleQueryLanguages.add("pt");
        this.possibleQueryLanguages.add("en");
        this.possibleQueryLanguages.add("es");
        this.possibleQueryLanguages.add("fr");
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {

            var menu = """
                    |////////////////////////////////////////////////|
                    
                    [1] - BUSCAR LIVRO (TÍTULO)
                    [2] - LISTAR LIVROS
                    [3] - LISTAR AUTORES
                    [4] - LISTAR AUTORES POR ANO
                    [5] - LISTAR LIVROS POR IDIOMA
                    0 - Sair
                    
                    |////////////////////////////////////////////////|
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1 -> buscarLivropeloTitulo();
                case 2 -> listarLivrosRegistrados();
                case 3 -> listarAutores();
                case 4 -> listarAutoresnoAnoDeterminado();
                case 5 -> listarLivrosnoIdiomaSelecionado();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("\n[!] - Opção inválida!!!");
            }
        }
    }

    private void buscarLivropeloTitulo() {
        System.out.println("\nBUSCA POR TÍTULO ********************************************");
        System.out.print("Digite o título do livro: ");
        var titulo = leitura.nextLine();

        System.out.println("\nPesquisando...\n");
        var bookData = gutendexAPI.search(titulo).stream().findFirst();

        if (bookData.isEmpty()) {
            System.out.println(" ******************* Nenhum livro encontrado\n");
        } else {
            var livroDTO = bookData.get();
            var livro = Livro.fromLivroDTO(livroDTO);

            // ==> REMOVEMOS autorRepository.save(livro.getAutor());
            // Agora somente salvamos o livro, e com cascade PERSIST/MERGE,
            // o Autor (se for novo) será criado automaticamente.

            livroRepository.save(livro);

            System.out.println(livro + "\n");
        }
    }

    private void listarLivrosRegistrados() {
        System.out.println("\nLISTAGEM DE LIVROS CADASTRADOS ********************************************");
        var livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("*********************** Nenhum livro encontrado\n");
        }
        livros.forEach(l -> System.out.println(l + "\n"));
    }

    private void listarAutores() {
        System.out.println("\nLISTAGEM DE AUTORES ********************************************************");
        var autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("****************************** Nenhum autor encontrado\n");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresnoAnoDeterminado() {
        System.out.println("\nBUSCAR AUTORES POR ANO ***************************************");
        System.out.print("Digite o ano desejado: ");
        var year = leitura.nextInt();

        System.out.println("\nPesquisando...\n");
        var autores = autorRepository.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(year, year);

        if (autores.isEmpty()) {
            System.out.println("********************* Nenhum autor encontrado\n");
        } else {
            autores.forEach(System.out::println);
        }
        System.out.println(" ");
    }

    private void listarLivrosnoIdiomaSelecionado() {
        System.out.println("\nBUSCAR LIVROS POR IDIOMA ********************************************");

        boolean validLanguage = false;
        String idioma = "";
        while (!validLanguage) {
            System.out.println("******************** pt => Português ********************");
            System.out.println("******************** en => Inglês ***********************");
            System.out.println("******************** es => Espanhol *********************");
            System.out.println("******************** fr => Francês **********************");
            System.out.print("********************** Escolha o idioma: ******************");

            var inputUsuario = leitura.nextLine();
            validLanguage = possibleQueryLanguages.stream()
                    .anyMatch(l -> l.equalsIgnoreCase(inputUsuario));

            if (!validLanguage) System.out.println("\n Idioma inválido\n");
            idioma = inputUsuario;
        }
        var livros = livroRepository.findByIdioma(idioma);
        var quantidade = livroRepository.countByIdioma(idioma);

        System.out.println("\nBUSCANDO " + quantidade + " LIVRO" + (quantidade != 1 ? "S" : ""));
        livros.forEach(l -> System.out.println(l + "\n"));
        if (livros.isEmpty()) {
            System.out.println("******************************* Nenhum livro encontrado\n");
        }
    }
}
