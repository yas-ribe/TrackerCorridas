package br.edu.fecap.teste;

public class Corrida {
    private String distancia;
    private String tempo;
    private String terreno;
    private int dificuldade;


    public Corrida(String distancia, String tempo, String terreno, int dificuldade) {
        this.distancia = distancia;
        this.tempo = tempo;
        this.terreno = terreno;
        this.dificuldade = dificuldade;

    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getTerreno() {
        return terreno;
    }

    public void setTerreno(String terreno) {
        this.terreno = terreno;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }
}
