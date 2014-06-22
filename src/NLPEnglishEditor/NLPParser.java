/*
 * English Dependencies Demo Project
 * Author: jcyuyi@gmail.com
 */

package NLPEnglishEditor;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse raw string to NLP represent format
 */
public class NLPParser {
    private final String raw;
    private List<CoreLabel> rawWords;
    private Tree parseTree;
    private List<TypedDependency> dependencys;
    private static LexicalizedParser lp;
    public NLPParser(String rawString) {
        raw = rawString;
    }
    
    //Lazy Calculate
    public List<CoreLabel> getRawWords() {
        if (rawWords == null) {
            TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
            rawWords = tokenizerFactory.getTokenizer(new StringReader(raw)).tokenize();
            System.out.println("raw words: \n" + rawWords);
        }

        return rawWords;
    }
    
    public List<String> getRawStringWords() {
        List<CoreLabel> raw = getRawWords();
        List<String> r = new ArrayList<String>();
        for (int i = 0; i < raw.size(); i++) {
            r.add(raw.get(i).toString() + "-" + String.valueOf(i+1));
        }
        return r;
    }
    
    public Tree getTree() 
    {
        if (parseTree == null) {
            if (lp == null) {
                lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
            }
            parseTree = lp.apply(getRawWords());
        }
        return parseTree;
    }
    
    public String getTreeString() {
        return getTree().toString();
    }
    
    public List<TypedDependency> getTypedDependencyList() {
        if (dependencys == null) {
            TreebankLanguagePack tlp = new PennTreebankLanguagePack();
            GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
            GrammaticalStructure gs = gsf.newGrammaticalStructure(getTree());
            dependencys = gs.typedDependenciesCCprocessed();
        }
        return dependencys;
    }
    
    public List<NLPDependency> getDependencyList() {
        List<NLPDependency> dependency = new ArrayList<>();
        List<TypedDependency> dep = getTypedDependencyList();
        for (int i = 0; i < dep.size(); i++) {
            NLPDependency d = new NLPDependency();
            TypedDependency td = dep.get(i);
            d.setDep(td.dep().label().toString());
            d.setGov(td.gov().label().toString());
            d.setReln(td.reln().getShortName());
            dependency.add(d);
        }
        return dependency;
    }
}
