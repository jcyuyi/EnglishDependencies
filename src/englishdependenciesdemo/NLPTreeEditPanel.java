/*
 * English Dependencies Demo Project
 * Author: jcyuyi@gmail.com
 */

package englishdependenciesdemo;

import edu.stanford.nlp.ling.StringLabelFactory;
import edu.stanford.nlp.trees.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.tree.*;

/**
 *
 * @author airjcy
 */
public class NLPTreeEditPanel extends javax.swing.JPanel {

    private Tree nlpTree; //NLP tree
    
    private JTree jTree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;
    
    private JComboBox cbParent;
    private JButton btnAddParent;
    private JButton btnDeleteNode;
    private JButton btnDiscardChanges;
    private JButton btnApply;
    
    //======================= public methods ==============================
     
    /**
     * Creates new form DJTreeEditPanel
     */

    public NLPTreeEditPanel() {
         initComponents();
         init();
         setTreeString("");
         
    }
    public void setTitle(String title)
    {
        JLabel titleLabel = new JLabel("Tree Edit: " + title);
        add(BorderLayout.NORTH,titleLabel);
    }
    
    public void setTreeString(String treeString) {
        PennTreeReader tr = new PennTreeReader(new StringReader(treeString), new LabeledScoredTreeFactory(new StringLabelFactory()));
        if (tr != null) {
            try {
                setTree(tr.readTree());
            } catch (Exception e) {
                System.err.println("Error init NLP tree");
                setTree(null);
            }

        }
    }
    
    public void setTree(Tree aTree)
    {
        nlpTree = aTree;
        if (nlpTree == null) { //handle empty tree
            rootNode = new DefaultMutableTreeNode("Empty Tree!");
        }
        else {
            rootNode = new DefaultMutableTreeNode(nlpTree.label());
        }
        treeModel = new DefaultTreeModel(rootNode);
        jTree.setModel(treeModel);
        //init all JTree node from Tree
        if (nlpTree != null) {
            addChildrenToNode(rootNode, nlpTree);
            btnAddParent.setEnabled(true);
            btnApply.setEnabled(true);
            btnDeleteNode.setEnabled(true);
            btnDiscardChanges.setEnabled(true);
        }
        else {
            btnAddParent.setEnabled(false);
            btnApply.setEnabled(false);
            btnDeleteNode.setEnabled(false);
            btnDiscardChanges.setEnabled(false);
        }
        showAllTree();
    }
    
    //======================= private methods ==============================
    private void init() {
        //init head
        setTitle("");
        
        //init tree root
        rootNode = new DefaultMutableTreeNode();
        treeModel = new DefaultTreeModel(rootNode);
        jTree = new JTree(treeModel);
        
        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.setShowsRootHandles(true);
        ImageIcon leafIcon = new ImageIcon();
        if (leafIcon != null) {
            DefaultTreeCellRenderer renderer
                    = new DefaultTreeCellRenderer();
            renderer.setClosedIcon(leafIcon);
            renderer.setOpenIcon(leafIcon);
            jTree.setCellRenderer(renderer);
        }
        
        jTree.setDragEnabled(true);
        jTree.setDropMode(DropMode.INSERT);
        jTree.setTransferHandler(new TreeTransferHandler());
        showAllTree();

        
        JScrollPane scrollPane = new JScrollPane(jTree);
        add(BorderLayout.CENTER, scrollPane);
        
        //set controllPanel to Edit JTree
        JPanel controllPanel = new JPanel();
        controllPanel.setLayout(new GridLayout(1,4)); // 1 * 4 button: [Add parent...] [Delete parent] [Discard Changes] [Apply]
        cbParent = new JComboBox(NLPSettings.getGramms().toArray());
        
        
        btnAddParent = new JButton("+");
        btnAddParent.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddParentActionPerformed(evt);
            }
        });
        btnDeleteNode = new JButton("-");
        btnDeleteNode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDeleteNodeActionPerformed(evt);
            }
        });
        btnDiscardChanges = new JButton("Discard");
        btnDiscardChanges.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDiscardChangesActionPerformed(evt);
            }
        });
        
        btnApply = new JButton("Apply");
        btnApply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });
        controllPanel.add(cbParent);
        controllPanel.add(btnAddParent);
        controllPanel.add(btnDeleteNode);
        controllPanel.add(btnDiscardChanges);
        controllPanel.add(btnApply);
        add(BorderLayout.SOUTH,controllPanel);
    }
    
    private void addChildrenToNode(DefaultMutableTreeNode node, Tree aTree) {
        for (int i = 0; i < aTree.children().length; i ++)
        {
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(aTree.children()[i].label());
            node.add(newNode);
            addChildrenToNode(newNode, aTree.children()[i]);
        }
    }
    
    private void btnAddParentActionPerformed(ActionEvent evt) {
        DefaultMutableTreeNode currentNode = null;
        DefaultMutableTreeNode parentNode = null;
         TreePath selectPath = jTree.getSelectionPath();
         if (selectPath != null) { //if not root node
             if (selectPath.getParentPath() != null)
             {
                 String parent = cbParent.getSelectedItem().toString();
                DefaultMutableTreeNode newParent = new DefaultMutableTreeNode(parent); 
                parentNode = (DefaultMutableTreeNode)selectPath.getParentPath().getLastPathComponent();
                currentNode = (DefaultMutableTreeNode)selectPath.getLastPathComponent();
                
                treeModel.insertNodeInto(newParent, parentNode, parentNode.getIndex(currentNode));
                currentNode = (DefaultMutableTreeNode)selectPath.getLastPathComponent();
                
                treeModel.removeNodeFromParent(currentNode);
                treeModel.insertNodeInto(currentNode, newParent, 0);
                 showAllTree();
                
                 System.out.println(parentNode);
                 System.out.println(newParent);
                 System.out.println(currentNode);
                 
             }
        }
    }
    
    private void btnDeleteNodeActionPerformed(ActionEvent evt) {
        DefaultMutableTreeNode currentNode = null;
        DefaultMutableTreeNode parentNode = null;
        TreePath selectPath = jTree.getSelectionPath();
        if (selectPath != null) { //if not root node
            if (selectPath.getParentPath() != null) {
                currentNode = (DefaultMutableTreeNode) selectPath.getLastPathComponent();
                if (!currentNode.isLeaf()) {
                    parentNode = (DefaultMutableTreeNode) selectPath.getParentPath().getLastPathComponent();
                    List<MutableTreeNode> nodes = new ArrayList<>();
                    int pos = parentNode.getIndex(currentNode);
                    for (int i = 0; i < currentNode.getChildCount(); i++) {
                        nodes.add((MutableTreeNode) currentNode.getChildAt(i));
                    }
                     for (int i = 0; i < nodes.size(); i++) {
                        treeModel.removeNodeFromParent(nodes.get(i));
                    }
                                            
                    treeModel.removeNodeFromParent(currentNode);
                    for (int i = 0; i < nodes.size(); i++) {
                        treeModel.insertNodeInto(nodes.get(i), parentNode, i + pos);
                    }
                    showAllTree();

                    System.out.println(parentNode);
                    System.out.println(currentNode);
                }
            }
        }

    }
    
     private void btnDiscardChangesActionPerformed(ActionEvent evt) {
        rootNode = new DefaultMutableTreeNode(nlpTree.label());
        treeModel = new DefaultTreeModel(rootNode);
        jTree.setModel(treeModel);
        //init all JTree node from Tree
        addChildrenToNode(rootNode, nlpTree);
        showAllTree();
     }
     
     private void btnApplyActionPerformed(ActionEvent evt) {
         //Gen tree String 
     }
     
    private void showAllTree()
    {
        //expand all node
        for (int i = 0; i < jTree.getRowCount(); i++) {
         jTree.expandRow(i);
        }
    }
        /* 
            ====================     Main Test      ===========================
             */
    public static void main(String[] args) throws IOException {
        String ptbTreeString = "(ROOT (S (NP (NNP Interactive_Tregex)) (VP (VBZ works)) (PP (IN for) (PRP me)) (. !))))";
        Tree tree = (new PennTreeReader(new StringReader(ptbTreeString), new LabeledScoredTreeFactory(new StringLabelFactory()))).readTree();
        NLPTreeEditPanel nlpTreeEditPanel = new NLPTreeEditPanel();
        nlpTreeEditPanel.setTree(tree);
        JFrame frame = new JFrame();
        frame.getContentPane().add(nlpTreeEditPanel, BorderLayout.CENTER);
        nlpTreeEditPanel.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //frame.pack();
        frame.setSize(500, 400);
        frame.setVisible(true);
     }
             
             
    /* 
            ====================     AUTO GEN     ===========================
             */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
