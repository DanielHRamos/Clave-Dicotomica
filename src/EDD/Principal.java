/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import GUI.InterfazInicio;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Daniel
 */
public class Principal {

    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

        InterfazInicio inicio = new InterfazInicio();
        inicio.setVisible(true);
        inicio.setLocationRelativeTo(null);        
    }
}
