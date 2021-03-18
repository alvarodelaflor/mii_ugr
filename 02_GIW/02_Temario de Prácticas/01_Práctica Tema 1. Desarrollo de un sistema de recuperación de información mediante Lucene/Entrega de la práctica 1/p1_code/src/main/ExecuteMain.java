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

public class ExecuteMain {
    String indexDir = "/home/Index/";
    String dataDir = "/home/Data/";
    Index indexer;
    Search searcher;
    public static void main(String[] args) {
        ExecuteMain tester;
        try {
            tester = new ExecuteMain();
            //tester.createIndex();
            tester.search("Princeton");
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
