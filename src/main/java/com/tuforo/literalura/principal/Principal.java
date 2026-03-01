package com.tuforo.literalura.principal;

import com.tuforo.literalura.repository.AutorRepository;
import com.tuforo.literalura.repository.LibroRepository;
import com.tuforo.literalura.service.ConsumoAPI;
import com.tuforo.literalura.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {

    private final ConsumoAPI consumoAPI;
    private final ConvierteDatos convierteDatos;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final Scanner teclado = new Scanner(System.in);

    public Principal(ConsumoAPI consumoAPI, ConvierteDatos convierteDatos, LibroRepository libroRepository,
            AutorRepository autorRepository) {
        this.consumoAPI = consumoAPI;
        this.convierteDatos = convierteDatos;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        mostrarMenu();
    }

    private void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """

                    *** Bienvenido a literAlura ***
                    Elija una de las siguientes opciones:
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un año
                    5 - Listar libros por idioma

                    0 - Salir
                    ********************************
                    Ingrese su opción:\s""";
            System.out.print(menu);

            if (teclado.hasNextInt()) {
                opcion = teclado.nextInt();
                teclado.nextLine(); // Consumir salto de línea

                switch (opcion) {
                    case 1 -> buscarLibroPorTitulo();
                    case 2 -> listarLibrosRegistrados();
                    case 3 -> listarAutoresRegistrados();
                    case 4 -> listarAutoresVivos();
                    case 5 -> listarLibrosPorIdioma();
                    case 0 -> System.out.println("Saliendo de la aplicación...");
                    default -> System.out.println("Opción no válida. Intente de nuevo.");
                }
            } else {
                System.out.println("Entrada no válida. Por favor, ingrese un número válido.");
                teclado.nextLine(); // Consumir la entrada inválida
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Buscando libro por título... (En construcción)");
        // TODO: Implementar búsqueda en Gutendex y guardar en BD
    }

    private void listarLibrosRegistrados() {
        var libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        System.out.println("\n--- LIBROS REGISTRADOS ---");
        libros.forEach(libro -> {
            System.out.printf("""
                    ------------- LIBRO -------------
                    Título: %s
                    Autor: %s
                    Idioma: %s
                    Número de descargas: %d
                    ---------------------------------
                    """,
                    libro.getTitulo(),
                    libro.getAutor().getNombre(),
                    libro.getIdioma(),
                    libro.getNumeroDescargas());
        });
    }

    private void listarAutoresRegistrados() {
        var autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        System.out.println("\n--- AUTORES REGISTRADOS ---");
        autores.forEach(autor -> {
            var librosDelAutor = autor.getLibros().stream()
                    .map(com.tuforo.literalura.model.Libro::getTitulo)
                    .toList();

            System.out.printf("""
                    ------------- AUTOR -------------
                    Nombre: %s
                    Fecha de nacimiento: %s
                    Fecha de fallecimiento: %s
                    Libros registrados: %s
                    ---------------------------------
                    """,
                    autor.getNombre(),
                    autor.getFechaNacimiento() != null ? autor.getFechaNacimiento() : "Desconocida",
                    autor.getFechaFallecimiento() != null ? autor.getFechaFallecimiento() : "Desconocida",
                    librosDelAutor.isEmpty() ? "Ninguno" : librosDelAutor);
        });
    }

    private void listarAutoresVivos() {
        System.out.println("Ingrese el año para consultar autores vivos:");
        try {
            var anio = Integer.parseInt(teclado.nextLine());
            var autores = autorRepository.findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(anio,
                    anio);

            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos registrados en el año " + anio);
                return;
            }

            System.out.println("\n--- AUTORES VIVOS EN " + anio + " ---");
            autores.forEach(autor -> {
                System.out.printf("""
                        ------------- AUTOR -------------
                        Nombre: %s
                        Fecha de nacimiento: %s
                        Fecha de fallecimiento: %s
                        ---------------------------------
                        """,
                        autor.getNombre(),
                        autor.getFechaNacimiento(),
                        autor.getFechaFallecimiento());
            });
        } catch (NumberFormatException e) {
            System.out.println("Año no válido. Debe ingresar un número.");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma para buscar los libros:
                es -> Español
                en -> Inglés
                fr -> Francés
                pt -> Portugués
                """);
        var idioma = teclado.nextLine().trim().toLowerCase();

        if (idioma.isEmpty()) {
            System.out.println("No ha ingresado un idioma válido.");
            return;
        }

        var libros = libroRepository.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros registrados en ese idioma.");
            return;
        }

        System.out.println("\n--- LIBROS EN " + idioma.toUpperCase() + " ---");
        libros.forEach(libro -> {
            System.out.printf("""
                    ------------- LIBRO -------------
                    Título: %s
                    Autor: %s
                    Idioma: %s
                    Número de descargas: %d
                    ---------------------------------
                    """,
                    libro.getTitulo(),
                    libro.getAutor().getNombre(),
                    libro.getIdioma(),
                    libro.getNumeroDescargas());
        });
    }
}
