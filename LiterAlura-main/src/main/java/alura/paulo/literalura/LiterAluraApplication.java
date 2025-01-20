package alura.paulo.literalura;

import alura.paulo.literalura.controller.Controller;
import alura.paulo.literalura.service.GutendexAPI;
import alura.paulo.literalura.repository.LivroRepository;
import alura.paulo.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private GutendexAPI gutendexAPI;

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Controller controller = new Controller(livroRepository, autorRepository, gutendexAPI);
        controller.exibeMenu();
    }
}
