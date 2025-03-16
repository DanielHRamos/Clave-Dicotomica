/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

/**
 *
 * @author Daniel
 */
public class SimpleList<T> {
    
    private SimpleNode<T> pFirst;
    private SimpleNode<T> pLast;
    private int size;
    
    public SimpleList(){
        this.pFirst = null;
        this.pLast = null;
        this.size = 0;            
    }
    
    public boolean isEmpty(){
        return this.pFirst == null;        
    }
    
    // Insertar al inicio
    public void addStart(T data){
        SimpleNode<T> node = new SimpleNode<>(data);
        
        if (this.isEmpty()){
            this.pFirst = node;
            this.pLast = node;            
        } else {
            node.setpNext(this.pFirst);
            this.pFirst = node;
        }
        
        this.size++;
        
    }
    
    // Insertar al final
    public void addEnd(T data) {
        
        SimpleNode<T> node = new SimpleNode<>(data);
        if (this.isEmpty()){
            this.pFirst = node;
            this.pLast = node;            
        } else {
            this.pLast.setpNext(node);
            this.pLast = node;
        }
        
        this.size++;
    }
    
    public void delete(T data) {
        SimpleNode<T> currentNode = this.pFirst;
        SimpleNode<T> previousNode = null;
        
        while (currentNode != null && !currentNode.getData().equals(data)){
            previousNode = currentNode;
            currentNode = currentNode.getpNext();
        }
        
        if (currentNode != null) {
            if (previousNode == null) {
                this.pFirst = currentNode.getpNext();
            } else {
                previousNode.setpNext(currentNode.getpNext());                
            }
            
            this.size--;
        }
    }

    public SimpleNode<T> getpFirst() {
        return pFirst;
    }

    public void setpFirst(SimpleNode<T> pFirst) {
        this.pFirst = pFirst;
    }

    public SimpleNode<T> getpLast() {
        return pLast;
    }

    public void setpLast(SimpleNode<T> pLast) {
        this.pLast = pLast;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    
}
