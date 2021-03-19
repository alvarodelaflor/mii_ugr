package auxiliar;

import index.ExecuteIndex;
import org.apache.lucene.queryparser.classic.ParseException;
import search.ExecuteSearch;

import java.io.IOException;
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

    public void console() {
        Boolean res = true;
        while (res) {
            String indexDir = Constants.indexDir;
            String dataDir = Constants.dataDir;

            res = false;
            try {
                String question = "En primer lugar, que desea hacer:" +
                        "\n   (1)  Realizar búsqueda" +
                        "\n   (2)  Volver a indexar" +
                        "\n   (3)  Configurar el sistema" +
                        "\n   (0) Salir" +
                        "\nElija entre";

                Map<Boolean, String> aux = this.ask(question, Arrays.asList("1", "2", "3", "0").stream().collect(Collectors.toSet()), 5);

                if (aux.get(true).equals("1")) {
                    ExecuteSearch.createSearch(indexDir);
                } else if (aux.get(true).equals("2")) {
                    ExecuteIndex.createIndex();
                } else if (aux.get(true).equals("3")) {

                } else {
                    System.out.println("¡Adiós!");
                    System.exit(1);
                }

                aux = this.ask("¿Desea realizar otra operación?", Arrays.asList("s", "n").stream().collect(Collectors.toSet()), 5);
                if (aux.get(true).equals("s")) {
                    res = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
