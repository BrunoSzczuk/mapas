package com.fag.mapas.model;

/**
 *
 * @author Alexandro
 */
public class Contorno {
    
    private String nome;
    private String geometry;

    public Contorno() {
    }

    public Contorno(String nome, String geometry) {
        this.nome = nome;
        this.geometry = geometry;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    
    
}
