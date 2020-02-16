/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.model.teste;

/**
 *
 * @author Anderson Soares
 */
public class Pergunta {
    
    private String texto;

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
    
}
