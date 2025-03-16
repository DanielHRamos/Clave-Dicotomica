/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

/**
 *
 * @author Daniel
 */
public class SimpleNode<T>{
    
    private T data;
    private SimpleNode pNext;
    
    // Constructor que recibe unicamente el dato
    public SimpleNode(T data){
        
       this.data = data;
       this.pNext = null;
       
    }
    
    // Constructor que recibe el dato y el apuntador al siguiente
    public SimpleNode(T data, SimpleNode<T> pNext){
        
        this.data = data;
        this.pNext = pNext;
        
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SimpleNode getpNext() {
        return pNext;
    }

    public void setpNext(SimpleNode pNext) {
        this.pNext = pNext;
    }    
    
}
