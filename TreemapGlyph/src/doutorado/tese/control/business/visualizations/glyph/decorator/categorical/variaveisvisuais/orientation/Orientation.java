/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.orientation;

import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.DrawBehavior;
import doutorado.tese.control.business.visualizations.glyph.Glyph;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 *
 * @author Anderson Soares
 */
public class Orientation extends Glyph {

    private Rectangle bounds;
    private DrawBehavior drawBehavior;
    private Color cor;

    public Orientation() {
        this.drawBehavior = new DrawBehavior() {
            @Override
            public void paint(Graphics2D g) {
            }

            @Override
            public int getArea() {
                return 0;
            }

            @Override
            public void setGlyphBounds(Rectangle bounds) {
            }

            @Override
            public Shape getClipShape() {
                return new Polygon();
            }

            @Override
            public void drawForeground(Graphics2D g2d) {
            }

            @Override
            public DrawBehavior clone() throws CloneNotSupportedException {
                try {
                    // call clone in Object.
                    return (DrawBehavior) super.clone();
                } catch (CloneNotSupportedException e) {
                    System.err.println("Cloning not allowed.");
                    return this;
                }
            }

            @Override
            public void tornarGlyphQuadrado(int[] point) {
            }
        };
    }

    @Override
    public void paint(Graphics2D g2d) {
        if (isVisible()) {
            drawBehavior.paint(g2d);
            if (isOverlappingActivated()) {
                drawBehavior.drawForeground(g2d);
            }
        }
        super.paint(g2d);
        if (isVisible()) {
            if (!isOverlappingActivated()) {
                drawBehavior.drawForeground(g2d);
            }
        }        
    }
    
    @Override
    public Object whoAmI() {
        return this.getClass();
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public void setColor(Color cor) {
        this.setCor(cor);
    }

    @Override
    public void setBounds(Rectangle bounds) {
        super.setBounds(bounds);
        drawBehavior.setGlyphBounds(bounds);
    }

    public DrawBehavior getDrawBehavior() {
        return drawBehavior;
    }

    public void setDrawBehavior(DrawBehavior drawBehavior) {
        this.drawBehavior = drawBehavior;
    }

    @Override
    public int getArea() {
        return drawBehavior.getArea();
    }

    @Override
    public Shape getClipShape() {
        if (isOverlappingActivated()) {
            return this.getBounds();
        } else {
            return this.drawBehavior.getClipShape();
        }
    }

    @Override
    public Paint getTexturePaint() {
        return null;
    }

    @Override
    public Glyph clone() throws CloneNotSupportedException {
        try {
            DrawBehavior drawBehaviorClone = this.getDrawBehavior().clone();
            Orientation formaClonada = ((Orientation) super.clone());
            formaClonada.setDrawBehavior(drawBehaviorClone);
            formaClonada.killAllChild();
            return (Glyph) formaClonada;
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    @Override
    public String getVarValue() {
        return getDrawBehavior().toString();
    }

    /**
     * @return the cor
     */
    public Color getCor() {
        return cor;
    }

    /**
     * @param cor the cor to set
     */
    public void setCor(Color cor) {
        this.cor = cor;
    }

}
