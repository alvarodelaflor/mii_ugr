package index;

import auxiliar.Constants;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author Alvaro de la Flor Bonilla
 * @version 1.0.0
 */
public class Indexer {

    private IndexWriter writer;

    public Indexer(String indexDirectoryPath) throws IOException {

        Directory indexDirectory =  FSDirectory.open(Paths.get(indexDirectoryPath));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        writer = new IndexWriter(indexDirectory, iwc);
    }

    public void close() throws CorruptIndexException, IOException {
        writer.close();
    }

    private Document getDocument(File file) throws IOException {
        Document document = new Document();

        TextField contentField = new TextField(Constants.CONTENTS, new FileReader(file));
        TextField fileNameField = new TextField(Constants.FILE_NAME, file.getName(),TextField.Store.YES);
        TextField filePathField = new TextField(Constants.FILE_PATH, file.getCanonicalPath(),TextField.Store.YES);

        document.add(contentField);
        document.add(fileNameField);
        document.add(filePathField);

        return document;
    }
    private void indexFile(File file) throws IOException {

        System.out.println("Indexando el documento: "+file.getCanonicalPath());
        Document document = getDocument(file);
        writer.addDocument(document);

    }
    public int createIndex(String dataDirPath, FileFilter filter) throws IOException {

        File[] files = new File(dataDirPath).listFiles();

        for (File file : files) {
            if(!file.isDirectory()
                    && !file.isHidden()
                    && file.exists()
                    && file.canRead()
                    && filter.accept(file)
            ){
                indexFile(file);
            }
        }

        return writer.numRamDocs();
    }
}
