/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph.formasgeometricas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * O objeto FormaGeometrica ocupa 50% do item do treemap
 * @author Anderson Soares
 */
public abstract class FormaGeometrica {

    private Rectangle bounds;
    private String name;
    private Color cor;

    public FormaGeometrica(Rectangle bounds, String name) {
        this.bounds = bounds;
        this.name = name;
    }

    public void setColor(Color cor) {
        this.cor = cor;
    }

    public Rectangle getBounds() {
        return bounds;
    }
    
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
    
    public abstract int getArea();

    public abstract void paint(Graphics g);

}
