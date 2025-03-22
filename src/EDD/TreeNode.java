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
    private TreeNode hijoSi;
    private TreeNode hijoNo;
    
    public TreeNode(String question){
        this.question = question;
        this.hijoSi = null;
        this.hijoNo = null;
    }   

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public TreeNode getHijoSi() {
        return hijoSi;
    }

    public void setHijoSi(TreeNode hijoSi) {
        this.hijoSi = hijoSi;
    }

    public TreeNode getHijoNo() {
        return hijoNo;
    }

    public void setHijoNo(TreeNode hijoNo) {
        this.hijoNo = hijoNo;
    }                
}
