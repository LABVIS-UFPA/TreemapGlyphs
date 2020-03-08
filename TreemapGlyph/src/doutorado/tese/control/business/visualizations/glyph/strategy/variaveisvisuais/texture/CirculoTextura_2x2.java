package doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture;

import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.DrawBehavior;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

/**
 * O objeto Circulo com cor ocupa 65% do item do treemap
 *
 * @author Anderson Soares
 */
public class CirculoTextura_2x2 implements DrawBehavior {

    private int[] xPoints;
    private int[] yPoints;
    private Color cor;
    private Rectangle bounds;
    private Path2D p;

    public CirculoTextura_2x2() {
        cor = Color.BLACK;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);

        g2d.setColor(cor);
        g2d.draw(p);
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
        int widthSobreposicao = (int) Math.round(points[0] * PERCENT_SOBREPOSICAO);
        int heightSobreposicao = (int) Math.round(points[1] * PERCENT_SOBREPOSICAO);

        xPoints= new int[2];
        yPoints = new int[2];

        xPoints[0] = getGlyphBounds().x + Math.round(getGlyphBounds().width / 2) - Math.round(widthSobreposicao / 2);
        yPoints[0] = getGlyphBounds().y + Math.round(getGlyphBounds().height / 2) - Math.round(heightSobreposicao / 2);

        xPoints[1] = widthSobreposicao;
        yPoints[1] = heightSobreposicao;
    }

    private void montarCirculo() {
        int[] points = new int[2];

        points[0] = getGlyphBounds().width;
        points[1] = getGlyphBounds().height;

        tornarGlyphQuadrado(points);
        montarQuadradoSobreposicao(points);

        int width = xPoints[1];
        int height = yPoints[1];

        int x = xPoints[0];
        int y = yPoints[0];
        int spacex = width / 16;
        int spacey = height / 16;

        p = new Path2D.Double();

        p.append(new Ellipse2D.Double(x, y, width / 2.2, height / 2.2), false);
        p.append(new Ellipse2D.Double(spacex + x + width / 2, y, width / 2.2, height / 2.2), false);
        p.append(new Ellipse2D.Double(x, spacey + y + height / 2, width / 2.2, height / 2.2), false);
        p.append(new Ellipse2D.Double(spacex + x + width / 2, spacey + y + height / 2, width / 2.2, height / 2.2), false);
    }

    public Rectangle getGlyphBounds() {
        return this.bounds;
    }

    @Override
    public void setGlyphBounds(Rectangle bounds) {
        this.bounds = bounds;
        montarCirculo();
    }

    @Override
    public int getArea() {
        return xPoints[1] * yPoints[1];
    }

    @Override
    public Shape getClipShape() {
        return p;
    }

    @Override
    public void drawForeground(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.draw(p);
        g2d.setColor(Color.BLACK);
        g2d.fill(p);
        g2d.setStroke(new BasicStroke(1f));
    }

    @Override
    public CirculoTextura_2x2 clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (CirculoTextura_2x2) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    @Override
    public String toString() {
        super.toString();
        return CirculoTextura_2x2.class.getSimpleName();
    }

}
