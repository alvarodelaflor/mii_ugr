package search;

import auxiliar.Constants;
import auxiliar.Execute;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

        List<ScoreDoc> scoreDocs = Arrays.stream(hits.scoreDocs).collect(Collectors.toList());
        Float maxScore = (float) scoreDocs.stream().mapToDouble(x -> x.score).max().orElse(0.0);
        Float minScore = (float) scoreDocs.stream().mapToDouble(x -> x.score).min().orElse(0.0);

        // Using umbral
        if (Constants.useUmbral) {
            scoreDocs = scoreDocs.stream().filter(x -> calculateIndividualScore(x.score, searchQuery, maxScore, minScore)).collect(Collectors.toList());
        }

        System.out.println("Se han encontrato un total de " + scoreDocs.size() + " documentos en " + (endTime - startTime) + " milisegundos para la búsqueda:\n   " + searchQuery);

        for(ScoreDoc scoreDoc : scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            System.out.println("Enlace del documento: " + doc.get(Constants.FILE_PATH) + " (SCORE: " + scoreDoc.score + ")");
        }
    }

    public static Boolean calculateIndividualScore(Float score, String searchQuery, Float maxScore, Float minScore) {
        Integer words = Arrays.stream(searchQuery.split(" ")).filter(x -> !x.equals(" ")).collect(Collectors.toList()).size();

        Boolean res = false;
        Float scoreNormalize = ((score + words * 0.47f) * 10) / maxScore;

        Float normalizeGen = normalize(scoreNormalize);

        if ((1 - normalizeGen) < Constants.umbral) {
            res = true;
        }

        return res;
    }

    public static Float normalize(Float val) {
        return (val - 0) / (10 - 0);
    }

    public static void createSearch() throws IOException, ParseException {
        Scanner scanner = new Scanner (System.in);
        String input = "";
        Boolean res = true;

        System.out.println("Establezca el término que desea buscar:");
        input = scanner.nextLine ();

        ExecuteSearch.search(input, Constants.indexDir);

        while (res) {
            Map<Boolean, String> aux = Execute.ask("¿Desea realizar otra búsqueda?", Arrays.asList("s", "n").stream().collect(Collectors.toSet()), 5);
            if (aux.get(true).equals("s")) {
                System.out.println("Establezca el término que desea buscar:");
                input = scanner.nextLine();
                ExecuteSearch.search(input, Constants.indexDir);
            } else {
                res = false;
            }
        }
    }
}
