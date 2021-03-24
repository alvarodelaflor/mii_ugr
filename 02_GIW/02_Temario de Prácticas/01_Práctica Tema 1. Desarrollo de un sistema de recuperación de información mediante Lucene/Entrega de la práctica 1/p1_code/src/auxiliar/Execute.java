package auxiliar;

import index.ExecuteIndex;
import org.apache.lucene.queryparser.classic.ParseException;
import search.ExecuteSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Execute {

    public static Map<Boolean, String> ask(String question, Set<String> options, int maxAttempts) {
        return ask(question, options, maxAttempts, true);
    }

    public static Map<Boolean, String> ask(String question, Set<String> options, int maxAttempts, Boolean showOptions) {
        Map<Boolean, String> res = new HashMap<>();
        int i = 1;
        String input = "";
        Scanner scanner = new Scanner (System.in);
        if (showOptions) {
            System.out.println(question + " " + options.toString());
        } else {
            System.out.println(question);
        }
        input = scanner.nextLine ();
        while (!options.contains(input) && i <= maxAttempts) {
            System.out.println("Ha elegido: " + input + "\nOpción inválida, inténtelo otra vez.\nIntento número " + i + ". Máximo número de intentos: 5.\nElección: ");
            input = scanner.nextLine();
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
                "   (4) Editar máximo número de resultados. Actual: " + Constants.MAX_SEARCH + "\n" +
                "   (5) ¿Usar umbral?. Actual: " + Constants.useUmbral + "\n" +
                "   (6) Editar el valor del umbral. Actual: " + Constants.umbral + "\n" +
                "   (0) Salir" +
                "\nElija entre";
        Map<Boolean, String> res = ask(question, IntStream.rangeClosed(0, 6).boxed().map(x -> x.toString()).collect(Collectors.toSet()), 3);
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
        } else if (res.get(true).equals("4")){
            res = ask("Introduzca el número máximo de resultados que desea mostrar [1-25]: ", IntStream.rangeClosed(1, 25).boxed().map(x -> x.toString()).collect(Collectors.toSet()), 3, false);
            if (!res.get(true).equals("-1")) {
                try {
                    Integer value = Integer.valueOf(res.get(true));
                    Constants.MAX_SEARCH = value;
                } catch (Exception e) {
                    System.out.println("No se ha podido establecer el valor. Restableciento valor 10");
                    Constants.MAX_SEARCH = 10;
                }
            }
        } else if (res.get(true).equals("5")){
            res = ask("Introduzca si desea utilizar o no un filtro por umbral (sí/no)", Arrays.asList("s", "n").stream().collect(Collectors.toSet()), 3);
            if (res.get(true).equals("s")) {
                Constants.useUmbral = true;
            } else if (res.get(true).equals("n")) {
                Constants.useUmbral = false;
            } else {
                System.out.println("No se detecta el valor, no será modificado. Actual: " + Constants.useUmbral);
            }
        } else if (res.get(true).equals("6")){
            System.out.println("Introduzca el nuevo valor de umbral");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            try {
                Float value = Float.valueOf(input);
                Constants.umbral = value;
            } catch (Exception e) {
                System.out.println("El valor insertado no es válido");
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
    public static List<String> readWords() {
        List<String> resEn = new ArrayList<>();
        List<String> resEs = new ArrayList<>();
        List<String> res = new ArrayList<>();
        String lineEn;
        String lineEs;
        BufferedReader bufferedReaderEn;
        BufferedReader bufferedReaderEs;
        try {
            bufferedReaderEn = new BufferedReader(new FileReader("./src/index/words/en.txt"));
            bufferedReaderEs = new BufferedReader(new FileReader("./src/index/words/es.txt"));

            while ((lineEn = bufferedReaderEn.readLine()) != null) {
                resEn.add(lineEn);
            }
            bufferedReaderEn.close();
            while ((lineEs = bufferedReaderEs.readLine()) != null) {
                resEs.add(lineEs);
            }
            bufferedReaderEs.close();
        } catch (IOException e) {
            System.out.println("Se ha producido un error.\nError: " + e.getMessage());
        }
        if (Constants.language.equals("EN")) {
            res.addAll(resEn);
        } else {
            res.addAll(resEs);
        }
        return res;
    }
}
