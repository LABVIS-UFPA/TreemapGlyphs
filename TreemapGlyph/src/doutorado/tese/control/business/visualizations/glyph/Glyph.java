/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.glyph;

import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.color.ColorHue;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.orientation.Orientation;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.position.Position;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.text.Text;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.shapes.GeometricShape;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.texture.Texture;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.ProfileGlyph;
import doutorado.tese.model.TreeMapItem;
import doutorado.tese.model.TreeMapNode;
import doutorado.tese.util.Constantes;
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

    private Glyph child;
    private Rectangle bounds;
    public float pectSobreposicao;
    private Glyph father;
    private final ArrayList children = new ArrayList();
    public Boolean selectedByUser = false;
    private boolean overlappingActivated = false;
    private boolean glyphResposta;
    private String letter;
    protected int[] xPoints;
    protected int[] yPoints;
    private boolean visible;
    private TreeMapNode nodeTreemap;

    public Glyph() {
        visible = true;
        this.pectSobreposicao = 0.7f;
    }

    public void paint(Graphics2D g2d) {
        if (getChild() != null) {
            if (getTexturePaint() != null) {
                g2d.setPaint(getTexturePaint());
            }
            if (getClipShape() != null) {
                g2d.clip(getClipShape());
            }
            if (Constantes.DECISION_TREE_ACTIVATED) {
                getChild().setVisible(false);
                TreeMapItem item = (TreeMapItem) nodeTreemap;
                if (item.getWhat2Draw()[Constantes.PRESENCA_TEXTURA] == 1 && getChild().whoAmI() == Texture.class) {
                    getChild().setVisible(true);
                } else if (item.getWhat2Draw()[Constantes.PRESENCA_COR] == 1 && getChild().whoAmI() == ColorHue.class) {
                    getChild().setVisible(true);
                } else if (item.getWhat2Draw()[Constantes.PRESENCA_FORMA] == 1 && getChild().whoAmI() == GeometricShape.class) {
                    getChild().setVisible(true);
                } else if (item.getWhat2Draw()[Constantes.PRESENCA_TEXTO] == 1 && getChild().whoAmI() == Text.class) {
                    getChild().setVisible(true);
                } else if (item.getWhat2Draw()[Constantes.PRESENCA_POSICAO] == 1 && getChild().whoAmI() == Position.class) {
                    getChild().setVisible(true);
                } else if (item.getWhat2Draw()[Constantes.PRESENCA_ORIENTACAO] == 1 && getChild().whoAmI() == Orientation.class) {
                    getChild().setVisible(true);
                } else if (item.getWhat2Draw()[Constantes.PRESENCA_PROFILE_GLYPH] == 1 && getChild().whoAmI() == ProfileGlyph.class) {
                    getChild().setVisible(true);
                }
            }

            getChild().paint(g2d);
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
//            this.child.father = this;
        }
    }

    public void getChildren(ArrayList<Glyph> children) {
        children.add(this);
        if (this.getChild() != null) {
            this.getChild().getChildren(children);
        }
    }

    protected void transformarRetanguloEmQuadrado(int[] point) {
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
            this.getChild().setBounds(
                    new Rectangle(  
                            bounds.x + Math.round(((bounds.width * (1 - pectSobreposicao)) / 2)),
                            bounds.y + Math.round(((bounds.height * (1 - pectSobreposicao)) / 2)),
                            Math.round(bounds.width * pectSobreposicao),
                            Math.round(bounds.height * pectSobreposicao)
                    )
            );
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
     * @return the letter
     */
    public String getLetter() {
        return letter;
    }

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

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
