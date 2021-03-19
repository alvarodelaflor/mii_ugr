package main;

import auxiliar.Execute;

public class ExecuteMain {
    public static String indexDir = "./src/indexDir/";
    public static String dataDir = "./src/docs/";
    public static Execute execute = new Execute();

    public static void main(String[] args) {
        execute.console(indexDir, dataDir);
    }

}
