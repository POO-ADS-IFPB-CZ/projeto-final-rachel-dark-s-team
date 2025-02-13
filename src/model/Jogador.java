package src.model;

import java.io.Serializable;

public class Jogador implements Serializable {
    private String nome;
    private int vitorias;
    private int derrotas;
    private int empates;

    public Jogador(String nome, int empates, int derrotas, int vitorias) {
        this.nome = nome;
        this.empates = 0;
        this.derrotas = 0;
        this.vitorias = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVitorias() {
        return vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getEmpates() {
        return empates;
    }
}
