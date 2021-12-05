/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Misael
 */
public class Palabra {
    private String texto;
    private int ntipo;
    
    public Palabra() {
        texto = "";
        ntipo = 0;
    }
    
    public Palabra(String texto, int tipo) {
        this.texto = texto;
        this.ntipo = tipo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getNtipo() {
        return ntipo;
    }

    public void setNtipo(int ntipo) {
        this.ntipo = ntipo;
    }
    
    
}
