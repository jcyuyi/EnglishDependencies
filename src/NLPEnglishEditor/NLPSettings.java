/*
 * English Dependencies Demo Project
 * Author: jcyuyi@gmail.com
 */
package NLPEnglishEditor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * custom NLP grammar and dependency settings
 */
public class NLPSettings {

    public static List<String> getGramms() {
        File file = new File("gra.txt");
        List<String> gra;
        try {
            gra = Files.readAllLines(Paths.get(file.toURI()));
        } catch (Exception e) {
            gra = new ArrayList<String>();
            System.err.println("Error read gramms");
        }
        return gra;
    }

    public static List<String> getDependencyStrings() {
        File file = new File("dep.txt");
        List<String> dep;
        try {
            dep = Files.readAllLines(Paths.get(file.toURI()));
        } catch (Exception e) {
            dep = new ArrayList<String>();
            System.err.println("Error read gramms");
        }
        return dep;
    }
}
