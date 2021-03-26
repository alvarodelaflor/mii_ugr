package index;

import auxiliar.Constants;
import auxiliar.Execute;
import auxiliar.ProgressBar;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alvaro de la Flor Bonilla
 * @mail alvdebon@correo.ugr.es
 * @version 1.0.0
 * @since 2021-03-21
 */
public class Indexer {

    private IndexWriter writer;

    /**
     * Indexer builder. The generated object will be used to add the documents to the index.
     *
     * @author Alvaro de la Flor Bonilla
     * @param indexDirectoryPath
     * @since 2021-03-21
     * @throws IOException
     */
    public Indexer(String indexDirectoryPath) throws IOException {
        // Add stop words
        List<String> words = Execute.readWords();
        CharArraySet stopSet = StopFilter.makeStopSet(words);
        // Builds an analyzer with stop words
        StandardAnalyzer analyzer = new StandardAnalyzer(stopSet);
        // A Directory provides an abstraction layer for storing a list of files. A directory contains only files (no sub-folder hierarchy)
        Directory indexDirectory =  FSDirectory.open(Paths.get(indexDirectoryPath));
        // Holds all the configuration that is used to create an IndexWriter
        IndexWriterConfig iwriter = new IndexWriterConfig(analyzer);
        // An IndexWriter creates and maintains an index
        writer = new IndexWriter(indexDirectory, iwriter);
    }

    /**
     * Method in charge of creating the "document" to be indexed.
     *
     * @author Alvaro de la Flor Bonilla
     * @param file
     * @since 2021-03-21
     * @throws IOException
     */
    private Document getDocument(File file) throws IOException {
        // First, we create the document that we are going to index
        Document doc = new Document();

        // We set as parameters to search for the title, location and content.
        TextField contentField = new TextField(Constants.CONTENTS, new FileReader(file));
        TextField fileNameField = new TextField(Constants.FILE_NAME, file.getName(),TextField.Store.YES);
        TextField filePathField = new TextField(Constants.FILE_PATH, file.getCanonicalPath(),TextField.Store.YES);
        doc.add(contentField);
        doc.add(fileNameField);
        doc.add(filePathField);

        return doc;
    }

    /**
     * Individual Document Indexer
     * @param file
     * @throws IOException
     */
    private void indexFile(File file) throws IOException {
        try {
            Document document = getDocument(file);
            writer.addDocument(document);
        } catch (Exception e) {
            String msg = "Se ha producido un error indexando el archivo.\n";
            String path = "Localización del archivo: " + file.getCanonicalPath() + "\n";
            String error = "Error: " + e.getLocalizedMessage();
            System.out.println(msg + path + error);
        }
    }

    /**
     * Indexer for the set of files present in a directory
     * @param dataDirPath
     * @param filter
     * @return
     * @throws IOException
     */
    public Long createIndex(String dataDirPath, FileFilter filter) throws IOException {

        File[] files = new File(dataDirPath).listFiles();
        ProgressBar progressBar = new ProgressBar();

        System.out.println("Indexando archivos.");

        int i = 1;

        try {
            for (File file : files) {
                progressBar.processing(i, files.length);
                Boolean checkIsDirectory = !file.isDirectory();
                Boolean checkIsHidden = !file.isHidden();
                Boolean checkExists = file.exists();
                Boolean checkCanRead = file.canRead();

                List<Boolean> check = Arrays.asList(checkIsDirectory, checkIsHidden, checkExists, checkCanRead);
                List<String> msgs = Arrays.asList("Fallo en el directorio", "Archivo oculto", "Archivo no existente", "Archivo no leíble", "Archivo no aceptado");
                Boolean checkAux = true;
                int msg = 0;
                for (Boolean pass: check) {
                    if (!pass) {
                        System.out.println("No se puede indexar por un fallo en el archivo\nError: " + msgs.get(msg));
                        checkAux = false;
                    }
                    msg++;
                }
                if(checkAux){
                    indexFile(file);
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println("Se ha producido un error, posiblemente el directorio de archivos es incorrecto.\nError: " + e.getMessage());
        }

        return writer.getMaxCompletedSequenceNumber();
    }

    /**
     * Auxiliary method to close the use of the indexer.
     *
     * @author Alvaro de la Flor Bonilla
     * @since 2021-03-21
     */
    public void close() throws IOException {
        writer.close();
    }
}
