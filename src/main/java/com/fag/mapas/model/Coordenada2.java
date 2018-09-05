package com.fag.mapas.model;

/**
 *
 * @author Alexandro
 */
public class Coordenada2 {
    private double x;
    private double y;
    private String cor;
    private String nome;

    public Coordenada2() {
    }

    public Coordenada2(double x, double y, String cor, String nome) {
        this.x = x;
        this.y = y;
        this.cor = cor;
        this.nome = nome;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
 
}