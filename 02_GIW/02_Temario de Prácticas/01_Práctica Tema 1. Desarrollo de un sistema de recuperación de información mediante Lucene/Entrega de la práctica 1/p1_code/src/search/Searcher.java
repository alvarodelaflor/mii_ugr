package search;

import auxiliar.Constants;
import auxiliar.Execute;
import index.ExecuteIndex;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Alvaro de la Flor Bonilla
 * @version 1.0.0
 */
public class Searcher {

    IndexSearcher indexSearcher;
    QueryParser queryParser;
    Query query;

    public Searcher(String indexDirectoryPath) throws IOException {
        Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
        Boolean res = false;
        try {
            IndexReader reader = DirectoryReader.open(indexDirectory);
            indexSearcher = new IndexSearcher(reader);
            queryParser = new QueryParser(Constants.CONTENTS, new StandardAnalyzer());
        } catch (Exception e) {
            System.out.println("Se ha producido un error, posiblemente no hay ningún índice creado.\nSe creará automáticamente y volveremos a intentarlo...");
            res = true;
        }
        if (res) {
            try {
                ExecuteIndex.createIndex();
                System.out.println("Repitiendo búsqueda");
                IndexReader reader = DirectoryReader.open(indexDirectory);
                indexSearcher = new IndexSearcher(reader);

                // Add stop words
                List<String> words = Execute.readWords();
                CharArraySet stopSet = StopFilter.makeStopSet(words);

                // Builds an analyzer with stop words
                StandardAnalyzer analyzer = new StandardAnalyzer(stopSet);
                queryParser = new QueryParser(Constants.CONTENTS, analyzer);
            } catch (Exception e) {
                System.out.println("Se ha vuelto a producir un error.\nError: " + e.getMessage());
            }
        }
    }

    public TopDocs search(String searchQuery) throws IOException, ParseException {
        query = queryParser.parse(searchQuery);
        return indexSearcher.search(query, Constants.MAX_SEARCH);
    }

    public Document getDocument(ScoreDoc scoreDoc) throws IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }
}
