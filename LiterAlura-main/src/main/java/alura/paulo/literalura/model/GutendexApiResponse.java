package alura.paulo.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexApiResponse(
        @JsonAlias("count") Integer quantidade,
        @JsonAlias("previous") String linkPaginaAnterior,
        @JsonAlias("next") String linkPaginaPosterior,
        @JsonAlias("results") List<LivroDTO> livros
) {
}
