/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes;

import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.DrawBehavior;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;

public class Pentagono implements DrawBehavior {

    private int[] xPoints;
    private int[] yPoints;
    private Polygon p;
    private Rectangle bounds;
    private Color cor;

    public Pentagono() {
        this.cor = Color.decode("#f0f0f0");
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Color.BLACK);

        g2d.setColor(getCor());
        g2d.fillPolygon(p);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(p);
    }

    @Override
    public void tornarGlyphQuadrado(int[] point) {
        if (point[0] > point[1]) {
            point[0] = point[1];
        } else if (point[0] < point[1]) {
            point[1] = point[0];
        }
    }

    private void montarPentagono() {
        int[] points = new int[2];

        Rectangle rect = getBounds();

        points[0] = rect.width;
        points[1] = rect.height;

        tornarGlyphQuadrado(points);
        
        int width = (int) Math.round(points[0] * PERCENT_SOBREPOSICAO);
        int height = (int) Math.round(points[1] * PERCENT_SOBREPOSICAO);

        int halfWidth = width / 2;
        int halfHeight = height / 2;
        int innerWidth = width / 4;

        halfWidth += rect.x + rect.width / 2 - width / 2;
        halfHeight += rect.y + rect.height / 2 - height / 2;

        xPoints = new int[5];
        yPoints = new int[5];

        xPoints[0] = halfWidth;
        yPoints[0] = (int) Math.round(rect.y + rect.height / 2 - height / 2);

        xPoints[1] = (int) Math.round(rect.x + rect.width / 2 - width / 2);
        yPoints[1] = halfHeight;

        xPoints[2] = halfWidth - innerWidth;
        yPoints[2] = height + (int) Math.round(rect.y + rect.height / 2 - height / 2);

        xPoints[3] = halfWidth + innerWidth;
        yPoints[3] = height + (int) Math.round(rect.y + rect.height / 2 - height / 2);

        xPoints[4] = width + (int) Math.round(rect.x + rect.width / 2 - width / 2);
        yPoints[4] = halfHeight;

        p = new Polygon();

        p.addPoint(xPoints[0], yPoints[0]);
        p.addPoint(xPoints[1], yPoints[1]);
        p.addPoint(xPoints[2], yPoints[2]);
        p.addPoint(xPoints[3], yPoints[3]);
        p.addPoint(xPoints[4], yPoints[4]);
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public void setGlyphBounds(Rectangle bounds) {
        this.bounds = bounds;
        montarPentagono();
    }

    @Override
    public int getArea() {
        int base = (xPoints[4] - xPoints[1]);
        int altura = (yPoints[3] - yPoints[0]);
        return base * altura;
    }

    @Override
    public Shape getClipShape() {
        return p;
    }

    @Override
    public void drawForeground(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.draw(p);
    }
    
    @Override
    public Pentagono clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (Pentagono) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }
    
    @Override
    public String toString() {
        super.toString();
        return Pentagono.class.getSimpleName();
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
