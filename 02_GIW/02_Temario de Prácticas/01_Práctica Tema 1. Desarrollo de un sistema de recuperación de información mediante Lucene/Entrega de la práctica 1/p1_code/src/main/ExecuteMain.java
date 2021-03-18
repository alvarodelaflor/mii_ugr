package main;

import auxiliar.Constants;
import auxiliar.TextFileFilter;
import index.Index;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import search.Search;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class ExecuteMain {
    String indexDir = "./src/indexDir/";
    String dataDir = "./src/docs/";
    Index indexer;
    Search searcher;
    public static void main(String[] args) {
        ExecuteMain tester;
        try {
            String input = "";
            System.out.println("En primer lugar, que desea hacer:" +
                    "\n   (1)  Realizar búsqueda" +
                    "\n   (2)  Volver a indexar" +
                    "\n   (3)  Configurar el sistema" +
                    "\n   (-1) Salir" +
                    "\nElección: ");

            Scanner scanner = new Scanner (System.in);
            Set<String> options = Arrays.asList("1", "2", "3").stream().collect(Collectors.toSet());
            int i = 0;
            input = scanner.nextLine ();
            while (!options.contains(input) && i < 5) {
                System.out.println("Ha elegido: " + input + "\nOpción inválida, inténtelo otra vez.\nIntento número " + i + ". Máximo número de intentos: 5.\nElección: ");
                input = scanner.nextLine ();
                i++;
            }

            tester = new ExecuteMain();
            if (input.equals("1")) {
                System.out.println("Establezca el término que desea buscar:");
                input = scanner.nextLine ();
                tester.search(input);
            } else if (input.equals("2")) {
                tester.createIndex();
            } else if (input.equals("3")) {

            } else {
                System.out.println("¡Adiós!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void createIndex() throws IOException {
        indexer = new Index(indexDir);
        int numIndexed;
        long startTime = System.currentTimeMillis();
        numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
        long endTime = System.currentTimeMillis();
        indexer.close();
        System.out.println(numIndexed+" File indexed, time taken: "
                +(endTime-startTime)+" ms");
    }
    private void search(String searchQuery) throws IOException, ParseException {
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
}
