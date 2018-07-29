/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph;

import doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.color.Cor;
import doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.letters.Letra;
import doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.numbers.Numeral;
import doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.shapes.FormaGeometrica;
import doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.texture.Textura;
import doutorado.tese.visualizacao.treemap.TreeMapItem;
import doutorado.tese.visualizacao.treemap.TreeMapNode;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anderson Soares
 */
public abstract class Glyph implements Cloneable {

    /**
     * @return the nodeTreemap
     */
    public TreeMapNode getNodeTreemap() {
        return nodeTreemap;
    }

    /**
     * @param nodeTreemap the nodeTreemap to set
     */
    public void setNodeTreemap(TreeMapNode nodeTreemap) {
        this.nodeTreemap = nodeTreemap;
    }

    private Glyph child;
    private Rectangle bounds;
    public float pectSobreposicao;
    private Glyph father;
    private final ArrayList children = new ArrayList();
    public Boolean selectedByUser = false;
    private boolean overlappingActivated = false;
    private boolean glyphResposta;
    private boolean hasLetter;
    private String letter;
    protected int[] xPoints;
    protected int[] yPoints;
    private String number;
    private boolean hasNumber;
    private boolean activatedDecisionTree;
    private TreeMapNode nodeTreemap;

    public Glyph() {
    }

    public void paint(Graphics2D g2d) {
        if (getChild() != null) {
            if (getTexturePaint() != null) {
                g2d.setPaint(getTexturePaint());
            }
            if (getClipShape() != null) {
                g2d.clip(getClipShape());
            }
            if (activatedDecisionTree) {
                TreeMapItem item = (TreeMapItem) nodeTreemap;
                if (item.getWhat2Draw()[0] == 1 && getChild().whoAmI() == Textura.class) {
                    getChild().setDecisionTreeActivate(activatedDecisionTree);
                    getChild().paint(g2d);
                } else if (item.getWhat2Draw()[1] == 1 && getChild().whoAmI() == Cor.class) {
                    getChild().setDecisionTreeActivate(activatedDecisionTree);
                    getChild().paint(g2d);
                } else if (item.getWhat2Draw()[2] == 1 && getChild().whoAmI() == FormaGeometrica.class) {
                    getChild().setDecisionTreeActivate(activatedDecisionTree);
                    getChild().paint(g2d);
                } else if (item.getWhat2Draw()[3] == 1 && getChild().whoAmI() == Letra.class) {
                    getChild().setDecisionTreeActivate(activatedDecisionTree);
                    getChild().paint(g2d);
                } else if (item.getWhat2Draw()[4] == 1 && getChild().whoAmI() == Numeral.class) {
                    getChild().setDecisionTreeActivate(activatedDecisionTree);
                    getChild().paint(g2d);
                }
            } else {
                getChild().paint(g2d);
            }
        }
    }

    /**
     * Metodo que faz a inclusao de um Glyph filho ao GLyphs pai. Este metodo
     * tambem atualiza o pai do glyph filho.
     *
     * @param glyphChild glyph filho a ser incluido
     */
    public void appendChild(Glyph glyphChild) {
        Glyph filho = this.getChild();
        if (filho != null) {
            filho.appendChild(glyphChild);
        } else {
            filho = glyphChild;
            this.child = filho;
            this.children.add(filho);
            this.child.father = this;
        }
    }

    public void getChildren(ArrayList<Glyph> children) {
        children.add(this);
        if (this.getChild() != null) {
            this.getChild().getChildren(children);
        }
    }

    protected void verificarRetangulo(int[] point) {
        if (point[0] > point[1]) {
            point[0] = point[1];
        } else if (point[0] < point[1]) {
            point[1] = point[0];
        }
    }

    public int getArea() {
        return xPoints[1] * yPoints[1];
    }

    public void killAllChild() {
        this.child = null;
    }

    public abstract Shape getClipShape();

    public abstract Paint getTexturePaint();

    public abstract String getVarValue();

    public abstract Object whoAmI();

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Glyph clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (Glyph) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
        if (this.getChild() != null) {
            this.getChild().setBounds(new Rectangle(bounds.x + Math.round(((bounds.width * (1 - pectSobreposicao)) / 2)),
                    bounds.y + Math.round(((bounds.height * (1 - pectSobreposicao)) / 2)),
                    Math.round(bounds.width * pectSobreposicao), Math.round(bounds.height * pectSobreposicao)));
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public List<Glyph> getChildren() {
        return children;
    }

    public float getPectSobreposicao() {
        return pectSobreposicao;
    }

    public void setPectSobreposicao(float pectSobreposicao) {
        this.pectSobreposicao = pectSobreposicao;
    }

    protected void setFather(Glyph father) {
        this.father = father;
    }

    /**
     * @return the father
     */
    protected Glyph getFather() {
        return father;
    }

    public Boolean isSelectedByUser() {
        return selectedByUser;
    }

    public void setSelectedByUser(Boolean selectedByUser) {
        this.selectedByUser = selectedByUser;
    }

    /**
     * @param glyphResposta the glyphResposta to set
     */
    public void setGlyphResposta(boolean glyphResposta) {
        this.glyphResposta = glyphResposta;
    }

    /**
     * @return the overlappingActivated
     */
    public boolean isOverlappingActivated() {
        return overlappingActivated;
    }

    /**
     * @param overlappingActivated the overlappingActivated to set
     */
    public void setOverlappingActivated(boolean overlappingActivated) {
        this.overlappingActivated = overlappingActivated;
    }

    /**
     * @return the child
     */
    public Glyph getChild() {
        return child;
    }

    public boolean isGlyphResposta() {
        return glyphResposta;
    }

    /**
     * @return the hasLetter
     */
    public boolean hasLetter() {
        return hasLetter;
    }

    /**
     * @param hasLetter the hasLetter to set
     */
    public void usingLetter(boolean hasLetter) {
        this.hasLetter = hasLetter;
    }

    public boolean hasNumber() {
        return hasNumber;
    }

    public void usingNumber(boolean hasNumber) {
        this.hasNumber = hasNumber;
    }

    /**
     * @return the letter
     */
    public String getLetter() {
        return letter;
    }

    /**
     * @param letter the letter to set
     */
    public void setLetter(String letter) {
        this.letter = letter;
    }

    void setNumber(String numeroUtilizado) {
        number = numeroUtilizado;
    }

    void setDecisionTreeActivate(boolean activatedDT) {
        this.activatedDecisionTree = activatedDT;
    }

}
