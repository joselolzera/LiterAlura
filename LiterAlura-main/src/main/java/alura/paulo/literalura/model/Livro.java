package alura.paulo.literalura.model;

import jakarta.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Integer downloads;

    // Ajuste no cascade para incluir MERGE caso queira atualizar Autor também
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Autor autor;

    @Version
    private Integer version; // Controle de concorrência otimista

    /**
     * Converte um LivroDTO em um Livro.
     * <p>
     * IMPORTANTE:
     * - Não definimos o ID manualmente aqui para evitar problemas de detached entity
     *   quando for um novo registro.
     * - Se for um UPDATE, faça a busca pelo ID no banco e atualize a entidade retornada.
     */
    public static Livro fromLivroDTO(LivroDTO livroDTO) {
        Livro livro = new Livro();

        // Não setamos o ID aqui, pois ele é auto-gerado (caso seja novo).
        // Se o registro já existir, busque antes no banco e atualize o objeto "existente".

        // Validação e atribuição de título
        livro.titulo = Optional.ofNullable(livroDTO.titulo())
                .filter(t -> !t.isBlank())
                .orElse("Título não informado");

        // Validação e atribuição de downloads
        livro.downloads = Optional.ofNullable(livroDTO.downloads())
                .orElse(0);

        // Processa idioma com fallback
        livro.idioma = livroDTO.idiomas().stream()
                .findFirst()
                .orElse("Idioma desconhecido");

        // Processa autor, caso exista
        livro.autor = livroDTO.autores().stream()
                .findFirst()
                .map(Autor::fromAutorDto)
                .orElse(null);

        return livro;
    }

    // ======================
    // Getters e Setters
    // ======================
    public Long getId() {
        return id;
    }

    // Em geral, não se expõe setId para evitar inconsistência de IDs.
    // Mas, se necessário, mantenha ciente de que "forçar ID" pode gerar entidades detached.

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Integer getVersion() {
        return version;
    }

    // Normalmente não se expõe setVersion() publicamente, pois é um campo do Hibernate.

    @Override
    public String toString() {
        return "Titulo: " + titulo +
                "\nAutor(a): " + (autor == null ? "Desconhecido" : autor.toString()) +
                "\nIdioma: " + (idioma == null ? "Desconhecido" : idioma) +
                "\nNúmero de downloads: " + downloads;
    }
}
