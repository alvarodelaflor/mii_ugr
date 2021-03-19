package search;

import auxiliar.Constants;
import auxiliar.Execute;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ExecuteSearch {

    public static Searcher searcher;
    public static void search(String searchQuery, String indexDir) throws IOException, ParseException {
        searcher = new Searcher(indexDir);
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

    public static void createSearch(String indexDir) throws IOException, ParseException {
        Scanner scanner = new Scanner (System.in);
        String input = "";
        Boolean res = true;

        System.out.println("Establezca el término que desea buscar:");
        input = scanner.nextLine ();

        ExecuteSearch.search(input, indexDir);

        while (res) {
            Map<Boolean, String> aux = Execute.ask("¿Desea realizar otra búsqueda?", Arrays.asList("s", "n").stream().collect(Collectors.toSet()), 5);
            if (aux.get(true).equals("s")) {
                System.out.println("Establezca el término que desea buscar:");
                input = scanner.nextLine();
                ExecuteSearch.search(input, indexDir);
            } else {
                res = false;
            }
        }
    }
}
