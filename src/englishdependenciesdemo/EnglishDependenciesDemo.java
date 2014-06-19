/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package englishdependenciesdemo;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.StringLabelFactory;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.ui.TreeJPanel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 *
 * @author airjcy
 */
public class EnglishDependenciesDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        LexicalizedParser lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
        demoAPI(lp);
    }
    public static void demoAPI(LexicalizedParser lp) throws IOException {

    // This option shows loading and using an explicit tokenizer
    String sent2 = "This is another sentence.";
    
    TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
    List<CoreLabel> rawWords2 =  tokenizerFactory.getTokenizer(new StringReader(sent2)).tokenize();
    System.out.println("raw words: \n" + rawWords2);
    
    Tree parse = lp.apply(rawWords2);
    
    TreebankLanguagePack tlp = new PennTreebankLanguagePack();
    GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
    GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
    List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
    //[nsubj(sentence-4, This-1), cop(sentence-4, is-2), det(sentence-4, another-3), root(ROOT-0, sentence-4)]
    System.out.println("TypedDependency: \n" + tdl);
    System.out.println();

    TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
    System.out.println("tree print print tree: ");
    tp.printTree(parse);
    
     System.out.println();
     System.out.println();
     
     StringWriter sw = new StringWriter();
     PrintWriter pw= new PrintWriter(sw);
     tp.printTree(parse, pw);
     System.out.println("TEST:" + parse.toString());
     
     //construct a tree from a string and draw
     Tree tree2 = (new PennTreeReader(new StringReader(parse.toString()), new LabeledScoredTreeFactory(new StringLabelFactory()))).readTree();
     GrammaticalStructure gs2 = gsf.newGrammaticalStructure(tree2);
     List<TypedDependency> tdl2 = gs2.typedDependenciesCCprocessed();
     System.out.println("TypedDependency 2: \n" + tdl);
     
    TreeJPanel treeJPanel = new TreeJPanel();
    treeJPanel.setTree(tree2);
    MainFrame mainFrame = new MainFrame();
    mainFrame.setContentPane(treeJPanel);
    mainFrame.setVisible(true);
    treeJPanel.setVisible(true);
    
  }
}
