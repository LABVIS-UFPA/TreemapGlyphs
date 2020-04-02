/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.model;

import doutorado.tese.control.business.visualizations.glyph.Glyph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

/**
 * A simple implementation of the AbstractNode interface.
 */
public class TreeMapItem extends TreeMapNode {

    private long id;
    private int[] what2Draw;
//    private Textura textura;
//    private FormaGeometrica corForma;
//    private FormaGeometrica formaGeometrica;
//    private Text letra;
//    private Numeral numero;
    private boolean possuiGlyphResposta;
//    private Glyph glyphClone;

    public TreeMapItem(double size, int order) {
        this.size = size;
        this.classificationOrder = order;
        bounds = new Rect();
        this.mapaDetalhesItem = new HashMap<>();
        children = new ArrayList<>();
        what2Draw = new int[]{1, 1, 1, 1, 1, 1, 1};
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
    
    @Override
    public void inserirFilhos(Queue<String> hierarquia, TreeMapNode item, TreeMapNode pai) {
        throw new UnsupportedOperationException("A TreeMapItem can't have children.");
    }

    /**
     * Verifica se pelo menos um dos filhos do glyph pai é um dos glyphs
     * sorteados
     *
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

    /**
     * Metodo que retorna todos a composicao de um glyph, desde um glyph
     * concreto ate a ultima camada do glyph. Dessa forma, o metodo retorna, de
     * forma recursiva uma lista contendo todos os glyphs filhos (familia) de um
     * dado glyph.
     *
     * @param glyph objeto de pesquisa da familia
     * @param familia lista que ira armazenar a familia do obj pesquisado
     * @return lista contendo a familia do obj glyph pesquisado.
     */
    public List<Glyph> getGlyphFamily(Glyph glyph, List<Glyph> familia) {
        familia.add(glyph);
        boolean semFilhos = glyph.getChildren().isEmpty();
        if (!semFilhos) {
            return getGlyphFamily(glyph.getChild(), familia);
        } else {
            return familia;
        }
    }
     
    /**
    * Define which layer will be drawn
    * @return 
    */
    public int[] getWhat2Draw() {
        return what2Draw;
    }

    public void setWhat2Draw(int[] whatToDraw) {
        this.what2Draw = whatToDraw;
    }

    /**
     * Metodo usado para indicar que o Item em questão possui um gliph com
     * pelo menos um de seus filhos sendo a resposta correta (gabarito) da
     * questão (pergunta) proposta.
     *
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

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    

}
