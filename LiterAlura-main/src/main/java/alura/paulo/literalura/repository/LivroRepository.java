package alura.paulo.literalura.repository;

import alura.paulo.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Buscar livros por idioma
    List<Livro> findByIdioma(String idioma);

    // Contar livros por idioma
    Integer countByIdioma(String idioma);

    // Verificar se um livro com o título especificado existe
    boolean existsByTitulo(String titulo);
}
