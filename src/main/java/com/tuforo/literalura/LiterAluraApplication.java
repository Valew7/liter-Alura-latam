package com.tuforo.literalura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements org.springframework.boot.CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        com.tuforo.literalura.principal.Principal principal = new com.tuforo.literalura.principal.Principal(consumoAPI, convierteDatos, libroRepository, autorRepository);
        principal.run();
    }

	private final com.tuforo.literalura.service.ConsumoAPI consumoAPI;
    private final com.tuforo.literalura.service.ConvierteDatos convierteDatos;
    private final com.tuforo.literalura.repository.LibroRepository libroRepository;
    private final com.tuforo.literalura.repository.AutorRepository autorRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public LiterAluraApplication(com.tuforo.literalura.service.ConsumoAPI consumoAPI, com.tuforo.literalura.service.ConvierteDatos convierteDatos, com.tuforo.literalura.repository.LibroRepository libroRepository, com.tuforo.literalura.repository.AutorRepository autorRepository) {
        this.consumoAPI = consumoAPI;
        this.convierteDatos = convierteDatos;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

}
