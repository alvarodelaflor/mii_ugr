package index;

import auxiliar.Constants;
import auxiliar.TextFileFilter;

import java.io.IOException;

public class ExecuteIndex {
    public static void createIndex() throws IOException {
        String indexDir = Constants.indexDir;
        String dataDir = Constants.dataDir;

        Indexer indexer = new Indexer(indexDir);
        int numIndexed;
        long startTime = System.currentTimeMillis();
        numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
        long endTime = System.currentTimeMillis();
        indexer.close();

        System.out.println("\nSe han indexado un total de " + numIndexed+" archivos en " +(endTime-startTime)+" ms");
    }
}
