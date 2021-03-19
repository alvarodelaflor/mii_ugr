package auxiliar;

import index.Index;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import search.Search;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Execute {
    public Index indexer;
    public Search searcher;

    public void createIndex(String indexDir, String dataDir) throws IOException {
        indexer = new Index(indexDir);
        int numIndexed;
        long startTime = System.currentTimeMillis();
        numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
        long endTime = System.currentTimeMillis();
        indexer.close();
        System.out.println(numIndexed+" File indexed, time taken: "
                +(endTime-startTime)+" ms");
    }

    public void search(String searchQuery, String indexDir) throws IOException, ParseException {
        searcher = new Search(indexDir);
        long startTime = System.currentTimeMillis();
        TopDocs hits = searcher.search(searchQuery);
        long endTime = System.currentTimeMillis();

        System.out.println(hits.totalHits +
                " documents found. Time :" + (endTime - startTime));
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            System.out.println("File: "
                    + doc.get(Constants.FILE_PATH));
        }
    }

    public void searchAux(String indexDir) throws IOException, ParseException {
        Scanner scanner = new Scanner (System.in);
        String input = "";

        System.out.println("Establezca el término que desea buscar:");
        input = scanner.nextLine ();

        this.search(input, indexDir);

        Map<Boolean, String> aux = this.ask("¿Desea realizar otra búsqueda?", Arrays.asList("s", "n").stream().collect(Collectors.toSet()), 5);
        if (!aux.get(true).equals("-1") && !aux.get(true).equals("n")) {
            this.search(input, indexDir);
        }
    }

    public Boolean consoleAux(String indexDir, String dataDir) {
        Boolean res = false;
        try {
            String question = "En primer lugar, que desea hacer:" +
                                "\n   (1)  Realizar búsqueda" +
                                "\n   (2)  Volver a indexar" +
                                "\n   (3)  Configurar el sistema" +
                                "\n   (0) Salir" +
                                "\nElija entre";

            Map<Boolean, String> aux = this.ask(question, Arrays.asList("1", "2", "3", "0").stream().collect(Collectors.toSet()), 5);

            if (aux.get(true).equals("1")) {
                this.searchAux(indexDir);
            } else if (aux.get(true).equals("2")) {
                this.createIndex(indexDir, dataDir);
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
        return res;
    }

    public Map<Boolean, String> ask(String question, Set<String> options, int maxAttempts) {
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

    public void console(String indexDir, String dataDir) {
        Boolean res = true;
        while (res) {
            res = this.consoleAux(indexDir, dataDir);
        }
    }
}
