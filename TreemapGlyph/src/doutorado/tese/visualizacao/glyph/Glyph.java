/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph;

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
public abstract class Glyph implements Cloneable{

    private Glyph child;
    private Rectangle bounds;
    public float pectSobreposicao;
    private Glyph father;
    private final ArrayList children = new ArrayList();
    public Boolean selectedByUser = false;
    private boolean overlappingActivated = false;
    private boolean glyphResposta;

    public Glyph() {
    }
    
    public void paint(Graphics2D g2d) {
        if (getChild() != null) {
            if(getTexturePaint() != null){
                g2d.setPaint(getTexturePaint());
            }
            if (getClipShape() != null) {
                g2d.clip(getClipShape());
            }
            getChild().paint(g2d);
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
    
    public List<Glyph> getChildren() {
        return children;
    }

    public void killAllChild() {
        this.child = null;
    }

    public abstract Shape getClipShape();
    
    public abstract Paint getTexturePaint();

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

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public abstract String getVarValue();

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
     * @param glyphResposta the glyphResposta to set
     */
    public void setGlyphResposta(boolean glyphResposta) {
        this.glyphResposta = glyphResposta;
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
    
    
}
