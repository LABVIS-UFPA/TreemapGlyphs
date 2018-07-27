/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph;

import doutorado.tese.visualizacao.glyph.alfabeto.Letra;
import doutorado.tese.visualizacao.glyph.formasgeometricas.FormaGeometrica;
import doutorado.tese.visualizacao.glyph.numeros.Numeral;
import doutorado.tese.visualizacao.glyph.texture.Textura;

/**
 *
 * @author Anderson Soares
 */
public class Glyph_old {
    private Textura textura;
    private FormaGeometrica corForma;
    private FormaGeometrica formaGeometrica;
    private Letra letra;
    private Numeral numero;

    public Glyph_old() {
    }

    public Textura getTextura() {
        return textura;
    }

    public void setTextura(Textura textura) {
        this.textura = textura;
    }

    public FormaGeometrica getCorForma() {
        return corForma;
    }

    public void setCorForma(FormaGeometrica corForma) {
        this.corForma = corForma;
    }

    public FormaGeometrica getFormaGeometrica() {
        return formaGeometrica;
    }

    public void setFormaGeometrica(FormaGeometrica formaGeometrica) {
        this.formaGeometrica = formaGeometrica;
    }

    public Letra getLetra() {
        return letra;
    }

    public void setLetra(Letra letra) {
        this.letra = letra;
    }

    public Numeral getNumero() {
        return numero;
    }

    public void setNumero(Numeral numero) {
        this.numero = numero;
    }
    
    
}
