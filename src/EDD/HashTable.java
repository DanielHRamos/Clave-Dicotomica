/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

/**
 *
 * @author Daniel
 */
public class HashTable <T> {
    private static final int Capacity = 256;
    private SimpleList<Entry<T>>[] table;
    
    public HashTable() {
        table = new SimpleList[Capacity];
        for (int i = 0; i < Capacity; i++){
            table[i] = new SimpleList<>();            
        }
    }
    
    private int Hash(String key){
        return Math.abs(key.hashCode() % Capacity);
    }
    
    public void Insert(String key, T value){
        int index = Hash(key);
        table[index].addEnd(new Entry<>(key, value));                
    }
    
    public T Search(String key){
        int index = Hash(key);
        SimpleList<Entry<T>> list = table[index];
        SimpleNode<Entry<T>> actual = list.getpFirst();
        
        
        while (actual != null){
            if (actual.getData().getKey().equals(key)){
                return actual.getData().getValue();
            }
            actual = actual.getpNext();
        }
        return null;
    }
    
    private static class Entry<T>{
        private String key;
        private T value;
        
        public Entry(String key, T value){
            this.key = key;
            this.value = value;
            
        }

        public String getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }
        
    }
}
