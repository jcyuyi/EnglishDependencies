/*
 * English Dependencies Demo Project
 * Author: jcyuyi@gmail.com
 */

package englishdependenciesdemo;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JMenu;

/**
 *
 * @author airjcy
 */
public class MainFrame extends javax.swing.JFrame {
    
    /**
     * Creates new form mainFrame
     */
    public MainFrame() {
        initComponents();
        taskPanel.setParentFrame(this);
        nLPTreeEditPanel1.setParentFrame(this);
    }
    private List<NLPEntry> entries;
    private NLPEntry workingEntry;
    
    public List<NLPEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<NLPEntry> entries) {
        this.entries = entries;
        taskPanel.setTasks(entries);
    }
    
    public void setWorkingEntryTreeString(String treeString) {
        workingEntry.setTreeString(treeString);
        updateTreePanels();
    }
    
    public void setWorkingEntry(NLPEntry entry) {
        this.workingEntry = entry;
        updateTreePanels();
    }
    
    private void updateTreePanels()
    {
        nLPTreeEditPanel1.setTreeString(workingEntry.getTreeString());
        nLPTreePanel1.setTreeString(workingEntry.getTreeString());
        this.validate();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        nLPTreeEditPanel1 = new englishdependenciesdemo.NLPTreeEditPanel();
        nLPTreePanel1 = new englishdependenciesdemo.NLPTreePanel();
        taskPanel = new englishdependenciesdemo.NLPTaskPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        btnOpen = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jSplitPane1.setMinimumSize(new java.awt.Dimension(100, 72));

        nLPTreeEditPanel1.setMinimumSize(new java.awt.Dimension(100, 68));
        jSplitPane1.setLeftComponent(nLPTreeEditPanel1);
        jSplitPane1.setRightComponent(nLPTreePanel1);

        jSplitPane2.setLeftComponent(jSplitPane1);
        jSplitPane2.setRightComponent(taskPanel);

        getContentPane().add(jSplitPane2, java.awt.BorderLayout.CENTER);

        jMenu3.setText("File");

        btnOpen.setText("Open text file...");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        jMenu3.add(btnOpen);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        OpenFileFrame f = new OpenFileFrame();
        f.setParentFrame(this);
        f.setVisible(true);
    }//GEN-LAST:event_btnOpenActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnOpen;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private englishdependenciesdemo.NLPTreeEditPanel nLPTreeEditPanel1;
    private englishdependenciesdemo.NLPTreePanel nLPTreePanel1;
    private englishdependenciesdemo.NLPTaskPanel taskPanel;
    // End of variables declaration//GEN-END:variables
}
