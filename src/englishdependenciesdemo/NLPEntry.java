/*
 * English Dependencies Demo Project
 * Author: jcyuyi@gmail.com
 */

package englishdependenciesdemo;

import edu.stanford.nlp.trees.TypedDependency;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author airjcy
 */
public class NLPEntry {
    
    private EntryStatus status; //edit status
    private Date addDate;
    private Date editDate;
    private String treeString;
    private List<TypedDependency> dependency;
    private String text;    //raw text 
    
    
    public enum EntryStatus { 
        RAW,OPEN,CLOSE  
    }
    
    public static NLPEntry getRawEntryFromString(String str){
        NLPEntry entry = new NLPEntry();
        entry.setStatus(EntryStatus.RAW);
        entry.setAddDate(new Date());
        entry.setEditDate(new Date());
        entry.setText(str);
        entry.setTreeString("");
        entry.setDependency(new ArrayList<TypedDependency>());
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

    public List<TypedDependency> getDependency() {
        return dependency;
    }

    public void setDependency(List<TypedDependency> dependency) {
        this.dependency = dependency;
    }

    
}
