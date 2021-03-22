package auxiliar;

import index.ExecuteIndex;
import org.apache.lucene.queryparser.classic.ParseException;
import search.ExecuteSearch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Execute {

    public static Map<Boolean, String> ask(String question, Set<String> options, int maxAttempts) {
        Map<Boolean, String> res = new HashMap<>();
        int i = 1;
        String input = "";
        Scanner scanner = new Scanner (System.in);
        System.out.println(question + " " + options.toString());
        input = scanner.nextLine ();
        while (!options.contains(input) && i <= maxAttempts) {
            System.out.println("Ha elegido: " + input + "\nOpción inválida, inténtelo otra vez.\nIntento número " + i + ". Máximo número de intentos: 5.\nElección: ");
            input = scanner.nextLine ();
            i++;
        }
        if (i > maxAttempts) {
            res.put(true, "-1");
        } else {
            res.put(true, input);
        }
        return res;
    }

    public void editPath() {
        String question = "¿Qué desea hacer?\n" +
                "   (1) Modificar el directorio del índice. Actual: " + Constants.indexDir + "\n" +
                "   (2) Modificar el directorio de los documentos. Actual: " + Constants.dataDir + "\n" +
                "   (3) Editar el idioma de palabras vacías. Actual: " + Constants.language + "\n" +
                "   (0) Salir" +
                "\nElija entre";
        Map<Boolean, String> res = ask(question, Arrays.asList("0", "1", "2", "3").stream().collect(Collectors.toSet()), 3);
        if (res.get(true).equals("1")) {
            String lastIndex = Constants.indexDir;
            System.out.println("Introduzca el nuevo directorio para el índice");
            Scanner scanner = new Scanner (System.in);
            String input = scanner.nextLine ();
            Constants.indexDir = input;
            System.out.println("Índice anterior: " + lastIndex+ "\nNuevo índice: " + Constants.indexDir);
        } else if (res.get(true).equals("2")){
            String lastIndex = Constants.dataDir;
            System.out.println("Introduzca el nuevo directorio para los documentos");
            Scanner scanner = new Scanner (System.in);
            String input = scanner.nextLine ();
            Constants.dataDir = input;
            System.out.println("Directorio anterior: " + lastIndex+ "\nNuevo directorio: " + Constants.dataDir);
        } else if (res.get(true).equals("3")){
            res = ask("Introduzca el idioma que desea.\nOpciones (inglés, español):", Arrays.asList("EN", "ES").stream().collect(Collectors.toSet()), 3);
            if (!res.get(true).equals("-1")) {
                Constants.language = res.get(true);
            }
        }
    }

    public void console() {
        Boolean res = true;
        while (res) {

            res = false;
            try {
                String question = "En primer lugar, que desea hacer:" +
                        "\n   (1)  Realizar búsqueda" +
                        "\n   (2)  Volver a indexar" +
                        "\n   (3)  Configurar el sistema" +
                        "\n   (0)  Salir" +
                        "\nElija entre";

                Map<Boolean, String> aux = this.ask(question, Arrays.asList("1", "2", "3", "0").stream().collect(Collectors.toSet()), 5);

                if (aux.get(true).equals("1")) {
                    ExecuteSearch.createSearch();
                } else if (aux.get(true).equals("2")) {
                    try {
                        System.out.println("Borrando el índice anterior...");
                        this.deleteDirectoryStream(Path.of(Constants.indexDir));
                    } catch (Exception e) {
                        System.out.println("Se ha producido un error:\nError: " + e);
                    }
                    ExecuteIndex.createIndex();
                } else if (aux.get(true).equals("3")) {
                    editPath();
                } else {
                    System.out.println("¡Adiós!");
                    System.exit(1);
                }

                aux = this.ask("¿Desea realizar otra operación?", Arrays.asList("s", "n").stream().collect(Collectors.toSet()), 5);
                if (aux.get(true).equals("s")) {
                    res = true;
                } else {
                    System.out.println("¡Adiós!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteDirectoryStream(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}
