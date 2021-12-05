/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Misael
 */
public class Palabrass {
    
    private final String[] palabras={"ACERTASTE","OAXACA","HAMACA","AHORITA"};
    private ArrayList<Palabra> palabrass;
    
    public Palabrass() {
        palabrass = new ArrayList();
        agregar();
    }
    
    public String getTextoPalabra(int np) {
        if(np>=0 && np<palabrass.size())
            return palabrass.get(np).getTexto();
        return "";
    }
    
    public void setPalabra(String texto, int tipo) {
        Palabra palabra = new Palabra(texto, tipo);
        palabrass.add(palabra);
    }
    
    private void agregar() {
        for(int p = 0; p<palabras.length; p++)
            palabrass.add(new Palabra(palabras[p], 0));
    }
    
    public int getSize() {
        return palabrass.size();
    }
    
}
