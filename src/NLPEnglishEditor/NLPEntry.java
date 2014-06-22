/*
 * English Dependencies Demo Project
 * Author: jcyuyi@gmail.com
 */

package NLPEnglishEditor;

import edu.stanford.nlp.trees.TypedDependency;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * a NLP Entry represent a NLP task item
 */
public class NLPEntry {
    
    private String text;    //raw text 
    private EntryStatus status; //edit status
    private Date addDate;
    private Date editDate;
    private String treeString;
    private List<NLPDependency> dependency;
    private List<String> words;
    
    public enum EntryStatus { 
        RAW,OPEN,CLOSE  
    }
    public NLPEntry() {
        dependency = new ArrayList<NLPDependency>();
        words = new ArrayList<String>();
    }
    
    public  NLPEntry(NLPEntry aEntry) {
        status = aEntry.status;
        if (aEntry.addDate != null) {
            addDate = (Date)(aEntry.addDate.clone());
        }
        if (aEntry.editDate != null) {
            editDate = (Date)(aEntry.editDate.clone());
        }
        
        treeString = aEntry.treeString;
        dependency = new ArrayList<NLPDependency>();
        for (int i = 0; i < aEntry.getDependency().size(); i++) {
            NLPDependency d = new NLPDependency();
            d.setDep(aEntry.getDependency().get(i).getDep());
            d.setGov(aEntry.getDependency().get(i).getGov());
            d.setReln(aEntry.getDependency().get(i).getReln());
            dependency.add(d);
        }
        words = aEntry.words;
    }
    
    /** Factory method to get raw entry from a string */
    public static NLPEntry getRawEntryFromString(String str){
        NLPEntry entry = new NLPEntry();
        entry.setStatus(EntryStatus.RAW);
        entry.setAddDate(new Date());
        entry.setEditDate(new Date());
        entry.setText(str);
        entry.setTreeString("");
        return entry;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EntryStatus getStatus() {
        return status;
    }

    public void setStatus(EntryStatus status) {
        this.status = status;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }

    public String getTreeString() {
        return treeString;
    }

    public void setTreeString(String treeString) {
        this.treeString = treeString;
    }

    public List<NLPDependency> getDependency() {
        if (dependency == null) {
            dependency = new ArrayList<>();
        }
        return dependency;
    }

    public void setDependency(List<NLPDependency> dep) {
        this.dependency = dep;
    }
    
    public List<String> getWords() {
        if (words == null) {
            words = new ArrayList<>();
        }
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
    
}
