package alura.paulo.literalura.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    /**
     * Se o Autor for o "lado pai" do relacionamento (e 'mappedBy' em Livro),
     * mantenha o cascade apropriado.
     * Aqui optamos por {CascadeType.PERSIST, CascadeType.MERGE} para evitar
     * problemas de detached entity caso o Autor já exista no banco.
     */
    @OneToMany(mappedBy = "autor", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Livro> livros = new HashSet<>();

    public Autor() {
    }

    /**
     * Converte um AutorDTO em um Autor "novo".
     * Importante:
     * Se você precisar atualizar um Autor existente, busque primeiro o Autor
     * no banco (via repository) e então atualize os campos desejados.
     */
    public static Autor fromAutorDto(AutorDTO autorDTO) {
        Autor autor = new Autor();
        // Apenas popula os campos básicos. Não atribuimos ID manualmente.
        autor.nome = autorDTO.getNome();        // Usa o getter customizado que lida com null
        autor.anoNascimento = autorDTO.getAnoNascimento();
        autor.anoFalecimento = autorDTO.getAnoMorte();
        return autor;
    }

    // ======================
    // Getters e Setters
    // ======================

    public Long getId() {
        return id;
    }

    // Em geral, não setamos manualmente o ID se for gerado por IDENTITY.
    // public void setId(Long id) { this.id = id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public Set<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Set<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        var texto = nome;
        if (anoNascimento != null && anoFalecimento != null) {
            texto += " (" + anoNascimento + "-" + anoFalecimento + ")";
        } else if (anoNascimento != null) {
            texto += " (" + anoNascimento + "-)";
        } else if (anoFalecimento != null) {
            texto += " (-" + anoFalecimento + ")";
        }
        return texto;
    }
}
