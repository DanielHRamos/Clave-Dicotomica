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

    public BinaryTree() {
        this.root = null;
        this.nodeIdCounter = 0; 
    }

    
    public void buildTreeFromJson(String filePath) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            String rootKey = (String) jsonObject.keySet().iterator().next();
            JSONArray speciesArray = (JSONArray) jsonObject.get(rootKey);

            if (speciesArray == null || speciesArray.isEmpty()) {
                throw new IllegalArgumentException("El JSON no contiene especies.");
            }

            
            String rootQuestion = null;
            for (Object obj : speciesArray) {
                JSONObject species = (JSONObject) obj;
                String speciesName = (String) species.keySet().iterator().next();
                JSONArray questions = (JSONArray) species.get(speciesName);

                if (questions == null || questions.isEmpty()) {
                    throw new IllegalArgumentException("La especie '" + speciesName + "' no tiene preguntas.");
                }

                JSONObject firstQuestion = (JSONObject) questions.get(0);
                String currentQuestion = (String) firstQuestion.keySet().iterator().next();

                if (rootQuestion == null) {
                    rootQuestion = currentQuestion; 
                } else if (!rootQuestion.equals(currentQuestion)) {
                    throw new IllegalArgumentException(
                            "Error: La primera pregunta no coincide.\n"
                            + "Esperado: '" + rootQuestion + "'\n"
                            + "Encontrado: '" + currentQuestion + "' en la especie '" + speciesName + "'"
                    );
                }
            }

            
            this.root = new TreeNode(rootQuestion);

            
            for (Object obj : speciesArray) {
                JSONObject species = (JSONObject) obj;
                buildSubTree(this.root, species);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void buildSubTree(TreeNode root, JSONObject species) {
        String speciesName = (String) species.keySet().iterator().next();
        JSONArray questions = (JSONArray) species.get(speciesName);
        TreeNode currentNode = root;

        
        for (int i = 1; i < questions.size(); i++) {
            JSONObject questionObj = (JSONObject) questions.get(i);
            String questionText = (String) questionObj.keySet().iterator().next();
            boolean answer = (boolean) questionObj.get(questionText);

            
            if (currentNode != this.root && currentNode.getHijoSi() == null && currentNode.getHijoNo() == null) {
                TreeNode oldNode = new TreeNode(currentNode.getQuestion());
                currentNode.setQuestion(questionText);
                currentNode.setHijoSi(oldNode);
            }

            
            TreeNode nextNode = answer ? currentNode.getHijoSi() : currentNode.getHijoNo();
            if (nextNode == null) {
                nextNode = new TreeNode(questionText);
                if (answer) {
                    currentNode.setHijoSi(nextNode);
                } else {
                    currentNode.setHijoNo(nextNode);
                }
            }
            currentNode = nextNode;
        }

        
        if (currentNode.getHijoSi() == null && currentNode.getHijoNo() == null) {
            currentNode.setQuestion(speciesName);
        } else {
            TreeNode leaf = new TreeNode(speciesName);
            if (currentNode.getHijoSi() == null) {
                currentNode.setHijoSi(leaf);
            } else {
                currentNode.setHijoNo(leaf);
            }
        }
    }

    
    public void visualizeTree() {
        
        Graph graph = new SingleGraph("Árbol Binario");

        
        addNodesToGraph(graph, null, root, 0, 0);

        
        graph.setAttribute("ui.stylesheet", """
            node {
                size: 20px;
                text-size: 15;
                fill-color: white;
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

}
