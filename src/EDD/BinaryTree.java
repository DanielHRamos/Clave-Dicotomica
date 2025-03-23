/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import java.io.FileReader;
import java.io.IOException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Daniel
 */
public class BinaryTree {

    private TreeNode root;
    private int nodeIdCounter; 
    
    private HashTable<String> speciesHashTable; 

    public BinaryTree() {
        this.root = null;
        this.nodeIdCounter = 0; 
        this.speciesHashTable = new HashTable<>();
    }

    

    public void buildTreeFromJson(String filePath) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            String rootKey = (String) jsonObject.keySet().iterator().next();
            JSONArray speciesArray = (JSONArray) jsonObject.get(rootKey);

            if (speciesArray == null || speciesArray.isEmpty()) {
                throw new IllegalArgumentException("El JSON no contien especies");
            }

           
            JSONObject firstSpecies = (JSONObject) speciesArray.get(0);
            String speciesName = (String) firstSpecies.keySet().iterator().next();
            JSONArray questions = (JSONArray) firstSpecies.get(speciesName);
            JSONObject firstQuestion = (JSONObject) questions.get(0);
            String firstQuestionText = (String) firstQuestion.keySet().iterator().next();

            
            this.root = new TreeNode(firstQuestionText);

            
            for (Object obj : speciesArray) {
                JSONObject species = (JSONObject) obj;
                buildSubTree(this.root, species, "Inicio: " + firstQuestionText);
            }

            
            construirHashDesdeArbol();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void buildSubTree(TreeNode root, JSONObject species, String path) {
    String speciesName = (String) species.keySet().iterator().next();
    JSONArray questions = (JSONArray) species.get(speciesName);
    TreeNode currentNode = root;
    String currentPath = path;

    for (int i = 1; i < questions.size(); i++) {
        JSONObject questionObj = (JSONObject) questions.get(i);
        String questionText = (String) questionObj.keySet().iterator().next();
        boolean answer = (boolean) questionObj.get(questionText);

        
        currentPath += " → " + (answer ? "Sí: " : "No: ") + questionText;

        if (answer) {
            if (currentNode.getHijoSi() == null) {
                currentNode.setHijoSi(new TreeNode(questionText));
            }
            currentNode = currentNode.getHijoSi();
        } else {
            if (currentNode.getHijoNo() == null) {
                currentNode.setHijoNo(new TreeNode(questionText));
            }
            currentNode = currentNode.getHijoNo();
        }
    }

    
    currentNode.setQuestion(speciesName);

    
    speciesHashTable.Insert(speciesName, "Ruta a la especie " + speciesName + ": " + currentPath);
} 
    public void visualizeTree() {
        
        Graph graph = new SingleGraph("Árbol Binario");

        
        addNodesToGraph(graph, null, root, 0, 0);

        
        graph.setAttribute("ui.stylesheet", """
            node {
                size: 20px;
                text-size: 15;
                fill-color: green;
                stroke-mode: plain;
                stroke-color: black;
            }
            edge {
                text-size: 12;
                text-alignment: above;
            }
        """);
        System.setProperty("org.graphstream.ui", "swing");
       
        Viewer viewer = graph.display();
        viewer.disableAutoLayout();
        
    }

    private void addNodesToGraph(Graph graph, Node parentNode, TreeNode treeNode, double x, int level) {
        if (treeNode == null) {
            return;
        }

       
        String nodeId = "node_" + nodeIdCounter++;
        String nodeLabel = treeNode.getQuestion();

      
        Node node = graph.addNode(nodeId);
        node.setAttribute("ui.label", nodeLabel);

        
        double y = -level * 1; 
        node.setAttribute("x", x);
        node.setAttribute("y", y);

        
        if (parentNode != null) {
            String edgeId = parentNode.getId() + "-" + node.getId();
            graph.addEdge(edgeId, parentNode, node)
                    .setAttribute("ui.label", (x < 0) ? "No" : "Yes");
        }

        
        addNodesToGraph(graph, node, treeNode.getHijoSi(), x + 1, level + 1);

        
        addNodesToGraph(graph, node, treeNode.getHijoNo(), x - 1, level + 1);
    }
    public String buscarEspecieEnArbol(String nombre) {
    return buscarRecursivo(root, nombre, "Inicio: " + root.getQuestion());
    }

    private String buscarRecursivo(TreeNode node, String nombre, String path) {
        if (node == null) return "Especie no encontrada en la lista ";

        
        if (node.getHijoSi() == null && node.getHijoNo() == null) {
            if (node.getQuestion().equalsIgnoreCase(nombre)) {
                return "Ruta a la especie " + nombre + ": " + path;
            }
            return "Especie no enconrada";
        }
        if (node.getHijoSi() != null) {
            String leftSearch = buscarRecursivo(node.getHijoSi(), nombre, path + " → Sí: " + node.getHijoSi().getQuestion());
            if (!leftSearch.contains("Especie no encontrada")) return leftSearch;
        }

        
        if (node.getHijoNo() != null) {
            return buscarRecursivo(node.getHijoNo(), nombre, path + " → No: " + node.getHijoNo().getQuestion());
        }

        return "Especie no encontrada";
    }
    
    public void printTree(TreeNode node, String prefix) {
        if (node == null) {
            return; 
        }

       
        System.out.println(prefix + node.getQuestion());

        
        printTree(node.getHijoSi(), prefix + "  Yes -> ");

       
        printTree(node.getHijoNo(), prefix + "  No -> ");
    }
    
    // Prueba arbol (Visualizar construccion del arbol)
    /*
    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.buildTreeFromJson("arboles_templados.json"); 
        
        System.out.println("Estructura del arbol:");
        binaryTree.printTree(binaryTree.getRoot(), "");
        
        binaryTree.visualizeTree();
    }     
  
    */
    public TreeNode getRoot() {
        return root;
    }
    
    public void construirHashDesdeArbol() {
        if (root != null) {
            agregarEspecieAlHash(root, "Inicio: " + root.getQuestion());
        }
    }
    private void agregarEspecieAlHash(TreeNode node, String path) {
        if (node.isLeaf()) {
            speciesHashTable.Insert(node.getQuestion(), "Ruta a la especie " + node.getQuestion() + ": " + path);
        } else {
            if (node.getHijoSi() != null) 
                agregarEspecieAlHash(node.getHijoSi(), path + " → Sí: " + node.getQuestion());
            if (node.getHijoNo() != null) 
                agregarEspecieAlHash(node.getHijoNo(), path + " → No: " + node.getQuestion());
        }
    }
    public String buscarEspecieEnHash(String nombre) {
        String resultado = speciesHashTable.Search(nombre);
        return (resultado != null) ? resultado : "Especie no encontrada.";
    }

}
