/*
 * English Dependencies Demo Project
 * Author: jcyuyi@gmail.com
 */
package NLPEnglishEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class NLPXMLHelper {

    /**
     * save Entries to a file in XML format
     */
    public static void saveToXml(List<NLPEntry> entries, File f) throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document xmldoc = db.newDocument();
            Element root = xmldoc.createElement("root");
            root.setAttribute("info", "nlp");
            for (int i = 0; i < entries.size(); i++) {
                NLPEntry e = entries.get(i);
                //create entry
                Element entry = xmldoc.createElement("entry");
                //status
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
                entry.setAttribute("status", e.getStatus().name());
                entry.setAttribute("addDate", fmt.format(e.getAddDate()));
                entry.setAttribute("editDate", fmt.format(e.getEditDate()));
                //text
                Element text = xmldoc.createElement("text");
                text.setTextContent(e.getText());
                //tree
                Element treeString = xmldoc.createElement("treeString");
                treeString.setTextContent(e.getTreeString());
                entry.appendChild(text);
                entry.appendChild(treeString);
                //dependency
                List<NLPDependency> ds = e.getDependency();
                for (int j = 0; j < ds.size(); j++) {
                    NLPDependency d = ds.get(j);
                    Element dependency = xmldoc.createElement("dependency");
                    entry.appendChild(dependency);
                    dependency.setAttribute("reln", d.getReln());
                    dependency.setAttribute("gov", d.getGov());
                    dependency.setAttribute("dep", d.getDep());
                }
                //words
                List<String> words = e.getWords();
                for (int j = 0; j < words.size(); j++) {
                    String w = words.get(j);
                    Element word = xmldoc.createElement("word");
                    entry.appendChild(word);
                    word.setTextContent(w);
                }               
                root.appendChild(entry);
            }
            xmldoc.appendChild(root);
            saveXml(f, xmldoc);
        } catch (Exception e) {
            System.err.println("Error to save xml: " + e.toString());
            throw e;
        }

    }

    /** get NLP Entries from xml file     */
    public static List<NLPEntry> getEntriesFromFile(File f) throws Exception {
        List<NLPEntry> entries = new ArrayList<NLPEntry>();
        System.out.println("Try to import from file: " + f.toString());
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document xmldoc = db.parse(f);
            Element root = xmldoc.getDocumentElement();
            NodeList nl = xmldoc.getElementsByTagName("entry");
            //add nodes
            for (int i = 0; i < nl.getLength(); i++) {
                Node eNode = nl.item(i);
                NLPEntry entry = new NLPEntry();
                NamedNodeMap nm = eNode.getAttributes();
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
                Date addDate = fmt.parse(nm.getNamedItem("addDate").getNodeValue());
                Date editDate = fmt.parse(nm.getNamedItem("editDate").getNodeValue());
                entry.setAddDate(addDate);
                entry.setEditDate(editDate);
                entry.setStatus(NLPEntry.EntryStatus.valueOf(nm.getNamedItem("status").getNodeValue()));
                //add properties
                NodeList propList = eNode.getChildNodes();
                for (int j = 0; j < propList.getLength(); j++) {
                    Node n = propList.item(j);
                    switch (n.getNodeName()) {
                        case "text":
                            entry.setText(n.getTextContent());
                            break;
                        case "treeString":
                            entry.setTreeString(n.getTextContent());
                            break;
                        case "dependency":
                            NamedNodeMap dm = n.getAttributes();
                            NLPDependency dependency = new NLPDependency();
                            dependency.setDep(dm.getNamedItem("dep").getNodeValue());
                            dependency.setGov(dm.getNamedItem("gov").getNodeValue());
                            dependency.setReln(dm.getNamedItem("reln").getNodeValue());
                            entry.getDependency().add(dependency);
                        case "word":
                            String word = n.getTextContent();
                            if (word != "") {
                                entry.getWords().add(word);
                            }
                        default:

                    }
                }//end for properties
                entries.add(entry);
            }//end for entry
        } catch (Exception e) {
            System.err.println("Error to save xml: " + e.toString());
            throw e;
        }
        return entries;
    }

    private static void saveXml(File f, Document doc) {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); //set indent
            DOMSource source = new DOMSource();
            source.setNode(doc);
            StreamResult result = new StreamResult();
            result.setOutputStream(new FileOutputStream(f));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
