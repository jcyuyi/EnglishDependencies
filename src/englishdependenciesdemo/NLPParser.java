/*
 * English Dependencies Demo Project
 * Author: jcyuyi@gmail.com
 */

package englishdependenciesdemo;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

/**
 *
 * @author airjcy
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
}
