package auxiliar;

import java.io.File;
import java.io.FileFilter;

public class TextFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        Boolean res = pathname.getName().toLowerCase().endsWith(".txt") || pathname.getName().toLowerCase().endsWith(".html");
        return res;
    }
}
