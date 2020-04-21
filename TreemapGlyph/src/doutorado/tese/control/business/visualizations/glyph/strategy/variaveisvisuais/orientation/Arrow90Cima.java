package doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation;

import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.DrawBehavior;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

/**
 * O objeto Circulo com cor ocupa 65% do item do treemap
 *
 * @author Anderson Soares
 */
public class Arrow90Cima implements DrawBehavior {

    private int[] xPoints;
    private int[] yPoints;
    private final Color cor;
    private Rectangle bounds;
    private Path2D path;
    private List<Polygon> triangulos;
    private List<Point> pontoRetas;

    public Arrow90Cima() {
        cor = Color.BLACK;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);

        g2d.setColor(cor);
        g2d.fill(path);
        g2d.draw(path);
    }

    @Override
    public void tornarGlyphQuadrado(int[] point) {
        if (point[0] > point[1]) {
            point[0] = point[1];
        } else if (point[0] < point[1]) {
            point[1] = point[0];
        }
    }

    private void montarSetas90() {
        int[] points = new int[2];

        points[0] = getBounds().width;
        points[1] = getBounds().height;

        tornarGlyphQuadrado(points);
        montarQuadradoSobreposicao(points);

        int slices = 6;
        int slicewidth = xPoints[1] / slices;
        int scalewidth = (int) (xPoints[1] * 0.16);

        triangulos = new ArrayList<>();
        pontoRetas = new ArrayList<>();
        
        for (int i = 0; i < slices - 1; i++) {
            Polygon p = new Polygon();
            //left
            p.addPoint((int) ((i * slicewidth + 1) + xPoints[0] + scalewidth * 0.6), yPoints[0] + scalewidth);
            //center
            p.addPoint((i * slicewidth + 1) + xPoints[0] + scalewidth, yPoints[0]);
            //right
            p.addPoint((int) ((i * slicewidth + 1) + xPoints[0] + scalewidth * 1.4), yPoints[0] + scalewidth);
            triangulos.add(p);
        }
        for (int i = 1; i < slices; i++) {
            pontoRetas.add(new Point(xPoints[0] + (i * slicewidth), yPoints[0]));
            pontoRetas.add(new Point(xPoints[0] + (i * slicewidth), yPoints[0] + yPoints[1]));
        }

        drawSetas();
    }

    private void drawSetas() {
        path = new Path2D.Double();
        triangulos.forEach((triangulo) -> {
            path.append(triangulo, false);
        });
        for (int i = 0; i < pontoRetas.size() - 1; i+= 2) {
            path.append(new Line2D.Float(pontoRetas.get(i), pontoRetas.get(i + 1)), false);
        }
    }

    public void montarQuadradoSobreposicao(int[] points) {
        int widthSobreposicao = (int) Math.round(points[0] * PERCENT_SOBREPOSICAO);
        int heightSobreposicao = (int) Math.round(points[1] * PERCENT_SOBREPOSICAO);

        xPoints = new int[2];
        yPoints = new int[2];

        xPoints[0] = getBounds().x + (getBounds().width / 2) - (widthSobreposicao / 2);
        yPoints[0] = getBounds().y + (getBounds().height / 2) - (heightSobreposicao / 2);

        xPoints[1] = widthSobreposicao;
        yPoints[1] = heightSobreposicao;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public void setGlyphBounds(Rectangle bounds) {
        this.bounds = bounds;
        montarSetas90();
    }

    @Override
    public int getArea() {
        return xPoints[1] * yPoints[1];
    }

    @Override
    public Shape getClipShape() {
        return path;
    }

    @Override
    public void drawForeground(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.draw(path);
    }

    @Override
    public Arrow90Cima clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (Arrow90Cima) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    @Override
    public String toString() {
        super.toString();
        return Arrow90Cima.class.getSimpleName();
    }
}
