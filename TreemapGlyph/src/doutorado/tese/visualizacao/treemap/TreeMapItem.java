/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.treemap;

import doutorado.tese.util.Coluna;
import doutorado.tese.visualizacao.glyph.Glyph;
import doutorado.tese.visualizacao.glyph.alfabeto.Letra;
import doutorado.tese.visualizacao.glyph.formasgeometricas.FormaGeometrica;
import doutorado.tese.visualizacao.glyph.numeros.Numeral;
import doutorado.tese.visualizacao.glyph.texture.Textura;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

/**
 * A simple implementation of the AbstractNode interface.
 */
public class TreeMapItem extends TreeMapNode {

    private String columnLabel;
    private int [] what2Draw;
    private Textura textura;
    private FormaGeometrica corForma;
    private FormaGeometrica formaGeometrica;
    private Letra letra;
    private Numeral numero;
    private boolean possuiGlyphResposta;

    
    public TreeMapItem(double size, int order) {
        this.size = size;
        this.classificationOrder = order;
        bounds = new Rect();
        this.mapaDetalhesItem = new HashMap<>();
        children = new ArrayList<>();
        what2Draw = new int[]{1,1,1,1,1};
    }

    public TreeMapItem(double size, TreeMapLevel paiLevel) {
        this.size = size;
        this.paiLevel = paiLevel;
        bounds = new Rect();
        mapaDetalhesItem = new HashMap<>();
        children = new ArrayList<>();
    }

    public TreeMapItem() {
        this(1, 0);
    }

    /**
     * @return the folha
     */
    public boolean isFolha() {
        return getPaiLevel() != null;
    }

    @Override
    public void setSize(Coluna colunaTamanho) {
        this.size = Double.parseDouble(mapaDetalhesItem.get(colunaTamanho));
    }

    /**
     * @param coluna o obj coluna para definir o label
     */
    public void setLabel(Coluna coluna) {
        this.columnLabel = coluna.getName();
    }
    
    @Override
    public void inserirFilhos(Queue<String> hierarquia, TreeMapNode item, TreeMapNode pai) {
        throw new UnsupportedOperationException("A TreeMapItem can't have children.");
    }
    
    /**
     * Verifica se pelo menos um dos filhos do glyph pai é um dos glyphs sorteados
     * @param pai
     * @return True se encontrar um filho sorteado, falso caso contrário.
     */
    public boolean hasGlyphResposta(Glyph pai) {
        Glyph filho = pai.getChild();
        if (!pai.getChildren().isEmpty()) {
            if (filho.isGlyphResposta()) {
                return true;
            } else {
                return hasGlyphResposta(filho);
            }
        } else {
            return false;
        }
    }
    
    public int[] getWhat2Draw() {
        return what2Draw;
    }

    public void setWhat2Draw(int[] whatToDraw) {
        this.what2Draw = whatToDraw;
    }
    
    /**
     * Metodo usado para indicar que o ItemGrid em questão possui um gliph com 
     * pelo menos um de seus filhos sendo a resposta correta (gabarito) da 
     * questão (pergunta) proposta.
     * @return the possuiGlyphResposta
     */
    public boolean isPossuiGlyphResposta() {
        return possuiGlyphResposta;
    }
    
    /**
     * 
     * @param possuiGlyphResposta the possuiGlyphResposta to set
     */
    public void setPossuiGlyphResposta(boolean possuiGlyphResposta) {
        this.possuiGlyphResposta = possuiGlyphResposta;
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
