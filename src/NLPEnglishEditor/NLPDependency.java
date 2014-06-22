/*
 * English Dependencies Demo Project
 * Author: jcyuyi@gmail.com
 */

package NLPEnglishEditor;

public class NLPDependency {
    private String reln;
    private String gov;
    private String dep;
    public NLPDependency() {
        reln = "root";
        gov = "ROOT-0";
        dep = "ROOT-0";
    }
    public String getReln() {
        return reln;
    }

    public void setReln(String reln) {
        this.reln = reln;
    }

    public String getGov() {
        return gov;
    }

    public void setGov(String gov) {
        this.gov = gov;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }
    
    @Override
    public String toString() {
        return reln + "(" + gov + "," + dep + ")";
    }
}
