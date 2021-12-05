/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Misael
 */
public class Tipo {
    private String[] tipo = {"Sin especificar","lugar","población","mueble", "vegetales"};
    
    public int getMaxNumTipos() {
        return tipo.length;
    }
    
    public String getTipo(int nt) {
        if(nt>=0 && nt<tipo.length)
            return tipo[nt];
        return tipo[0];
    }
}
