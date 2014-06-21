/*
 * English Dependencies Demo Project
 * Author: jcyuyi@gmail.com
 */

package englishdependenciesdemo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author airjcy
 */
public class NLPSettings {
    public static List<String> getGramms()
    {
        List<String> gra = new ArrayList<>();
        gra.add("NNP");
        gra.add("DT");
        gra.add("NN");
        return gra;
    }
}
