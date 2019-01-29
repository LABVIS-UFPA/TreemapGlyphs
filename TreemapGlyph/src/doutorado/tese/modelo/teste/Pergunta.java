/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.modelo.teste;

/**
 *
 * @author Anderson Soares
 */
public class Pergunta {
    
    private String texto;
    private long tempoInicio;
    private long tempoFinal;   

    public Pergunta() {
    }

    public Pergunta(String texto) {
        this.texto = texto;
    }
    
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public long getTempoInicio() {
        return tempoInicio;
    }

    public void setTempoInicio(long tempoInicio) {
        this.tempoInicio = tempoInicio;
    }

    public long getTempoFinal() {
        return tempoFinal;
    }

    public void setTempoFinal(long tempoFinal) {
        this.tempoFinal = tempoFinal;
    }    
    
}
