/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph.strategy.variaveisvisuais.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;

public class Trapezio implements DrawBehavior {

    private int[] xPoints;
    private int[] yPoints;
    private Rectangle bounds;
    private Polygon polygon;

    public Trapezio() {
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.white);
        g2d.fillPolygon(polygon);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(polygon);
    }
    
    @Override
    public void tornarGlyphQuadrado(int[] point) {
        if (point[0] > point[1]) {
            point[0] = point[1];
        } else if (point[0] < point[1]) {
            point[1] = point[0];
        }
    }

    private void montarTrapezio() {
        int[] points = new int[2];

        points[0] = getBounds().width;
        points[1] = getBounds().height;

        tornarGlyphQuadrado(points);
        
        int width = (int) Math.round(points[0] * percentSobreposicao);
        int height = (int) Math.round(points[1] * percentSobreposicao);

        int halfWidth = width / 2;
        int halfHeight = height / 2;
        int innerWidth = width / 4;
        int innerHeight = height / 4;

        halfWidth += getBounds().x + getBounds().width / 2 - width / 2;
        halfHeight += getBounds().y + getBounds().height / 2 - height / 2;

        xPoints = new int[4];
        yPoints = new int[4];

        xPoints[0] = halfWidth + innerWidth;
        yPoints[0] = (int) Math.round(getBounds().y + getBounds().height / 2 - height / 2);

        xPoints[1] = halfWidth - innerWidth;
        yPoints[1] = (int) Math.round(getBounds().y + getBounds().height / 2 - height / 2);

        xPoints[2] = (int) Math.round(getBounds().x + getBounds().width / 2 - width / 2);
        yPoints[2] = height + (int) Math.round(getBounds().y + getBounds().height / 2 - height / 2);

        xPoints[3] = width + (int) Math.round(getBounds().x + getBounds().width / 2 - width / 2);
        yPoints[3] = height + (int) Math.round(getBounds().y + getBounds().height / 2 - height / 2);

        polygon = new Polygon();

        polygon.addPoint(xPoints[0], yPoints[0]);
        polygon.addPoint(xPoints[1], yPoints[1]);
        polygon.addPoint(xPoints[2], yPoints[2]);
        polygon.addPoint(xPoints[3], yPoints[3]);
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
        montarTrapezio();
    }

    @Override
    public int getArea() {
        return (yPoints[2] - yPoints[0]) * (xPoints[3] - xPoints[2]);
    }

    @Override
    public Shape getClipShape() {
        return polygon;
    }

    @Override
    public void drawForeground(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(polygon);
    }
    
    @Override
    public Trapezio clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (Trapezio) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }
    
    @Override
    public String toString() {
        super.toString();
        return Trapezio.class.getSimpleName();
    }
}
