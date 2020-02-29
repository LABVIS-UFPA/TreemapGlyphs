/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;

public class Cruz implements DrawBehavior {

    private int[] xPoints;
    private int[] yPoints;
    private int[] xPointsSobreposicao;
    private int[] yPointsSobreposicao;
    private Polygon p;
    private Rectangle glyphBounds;
    private Color cor;

    public Cruz() {
        this.cor = Color.decode("#c4c4c4");
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //desenha background da forma
//        g2d.setColor(Color.decode("#A9A9A9"));
//        g2d.fillRect(xPointsSobreposicao[0], yPointsSobreposicao[0], xPointsSobreposicao[1], yPointsSobreposicao[1]);

        g2d.setColor(cor);
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

    public void montarQuadradoSobreposicao(int[] points) {
        int widthSobreposicao = (int) Math.round(points[0] * percentSobreposicao);
        int heightSobreposicao = (int) Math.round(points[1] * percentSobreposicao);

        xPointsSobreposicao = new int[2];
        yPointsSobreposicao = new int[2];

        xPointsSobreposicao[0] = getGlyphBounds().x + Math.round(getGlyphBounds().width / 2) - Math.round(widthSobreposicao / 2);
        yPointsSobreposicao[0] = getGlyphBounds().y + Math.round(getGlyphBounds().height / 2) - Math.round(heightSobreposicao / 2);

        xPointsSobreposicao[1] = widthSobreposicao;
        yPointsSobreposicao[1] = heightSobreposicao;
    }
    
    private void montarCruz() {
        int[] points = new int[2];

        Rectangle rect = getGlyphBounds();

        points[0] = getGlyphBounds().width;
        points[1] = getGlyphBounds().height;

        tornarGlyphQuadrado(points);
        montarQuadradoSobreposicao(points);
        
        int width = (int) Math.round(points[0] * percentSobreposicao);
        int height = (int) Math.round(points[1] * percentSobreposicao);

        int halfWidth = width / 2;
        int halfHeight = height / 2;
        int innerWidth = width / 4;
        int innerHeight = height / 4;

        halfWidth += rect.x + rect.width / 2 - width / 2;
        halfHeight += rect.y + rect.height / 2 - height / 2;

        xPoints = new int[12];
        yPoints = new int[12];

        xPoints[0] = halfWidth - innerWidth;
        yPoints[0] = (int) Math.round(rect.y + rect.height / 2 - height / 2);

        xPoints[1] = halfWidth - innerWidth;
        yPoints[1] = halfHeight - innerHeight;

        xPoints[2] = (int) Math.round(rect.x + rect.width / 2 - width / 2);
        yPoints[2] = halfHeight - innerHeight;

        xPoints[3] = (int) Math.round(rect.x + rect.width / 2 - width / 2);
        yPoints[3] = halfHeight + innerHeight;

        xPoints[4] = halfWidth - innerWidth;
        yPoints[4] = halfHeight + innerHeight;

        xPoints[5] = halfWidth - innerWidth;
        yPoints[5] = height + (int) Math.round(rect.y + rect.height / 2 - height / 2);

        xPoints[6] = halfWidth + innerWidth;
        yPoints[6] = height + (int) Math.round(rect.y + rect.height / 2 - height / 2);

        xPoints[7] = halfWidth + innerWidth;
        yPoints[7] = halfHeight + innerHeight;

        xPoints[8] = width + (int) Math.round(rect.x + rect.width / 2 - width / 2);
        yPoints[8] = halfHeight + innerHeight;

        xPoints[9] = width + (int) Math.round(rect.x + rect.width / 2 - width / 2);
        yPoints[9] = halfHeight - innerHeight;

        xPoints[10] = halfWidth + innerWidth;
        yPoints[10] = halfHeight - innerHeight;

        xPoints[11] = halfWidth + innerWidth;
        yPoints[11] = (int) Math.round(rect.y + rect.height / 2 - height / 2);

        p = new Polygon();
        p.addPoint(xPoints[0], yPoints[0]);
        p.addPoint(xPoints[1], yPoints[1]);
        p.addPoint(xPoints[2], yPoints[2]);
        p.addPoint(xPoints[3], yPoints[3]);
        p.addPoint(xPoints[4], yPoints[4]);
        p.addPoint(xPoints[5], yPoints[5]);
        p.addPoint(xPoints[6], yPoints[6]);
        p.addPoint(xPoints[7], yPoints[7]);
        p.addPoint(xPoints[8], yPoints[8]);
        p.addPoint(xPoints[9], yPoints[9]);
        p.addPoint(xPoints[10], yPoints[10]);
        p.addPoint(xPoints[11], yPoints[11]);
    }

    public Rectangle getGlyphBounds() {
        return this.glyphBounds;
    }

    @Override
    public void setGlyphBounds(Rectangle bounds) {
        this.glyphBounds = bounds;
        montarCruz();
    }

    @Override
    public int getArea() {
        return (xPoints[8] - xPoints[2]) * (yPoints[5] - yPoints[0]);
    }

    @Override
    public Shape getClipShape() {
        return p;
    }

    @Override
    public void drawForeground(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(p);
    }
    
    @Override
    public Cruz clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (Cruz) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    @Override
    public String toString() {
        super.toString();
        return Cruz.class.getSimpleName();
    }
    
}
