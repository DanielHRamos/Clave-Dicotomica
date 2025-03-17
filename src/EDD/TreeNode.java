/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

/**
 *
 * @author Daniel
 */
public class TreeNode {
    private String question;
    private SimpleNode<TreeNode> hijos;
    
    public TreeNode(String question){
        this.question = question;
        this.hijos = null;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public SimpleNode<TreeNode> getHijos() {
        return hijos;
    }

    public void addHijo(TreeNode hijo){
        SimpleNode<TreeNode> newNode = new SimpleNode<>(hijo);
        if (hijos == null){
            hijos = newNode;            
        } else {
            SimpleNode<TreeNode> actual = hijos;
            while (actual.getpNext() != null){
                actual = actual.getpNext();
            }
            actual.setpNext(newNode);
        }        
    }
    
    
}
