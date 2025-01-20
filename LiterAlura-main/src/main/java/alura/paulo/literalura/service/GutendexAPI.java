package alura.paulo.literalura.service;

import alura.paulo.literalura.model.*;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class GutendexAPI {

    private final String baseUrl = "https://gutendex.com/books/";

    public List<LivroDTO> search(String text) {
        var json = ApiConsumer.getData(
                baseUrl + "?search="
                        + URLEncoder.encode(text.trim().toLowerCase(), StandardCharsets.UTF_8)
        );
        return new TratadorDeDados().convert(json, GutendexApiResponse.class).livros();
    }

    public List<AutorDTO> getAuthorsAliveOnYear(Integer year) {
        var endpoint = baseUrl + "?author_year_start=" + year
                + "&author_year_end=" + year;
        var json = ApiConsumer.getData(endpoint);
        return new TratadorDeDados()
                .convert(json,  GutendexApiResponse.class)
                .livros()
                .stream()
                .flatMap(l -> l.autores().stream())
                .toList();
    }
}