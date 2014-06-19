/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
