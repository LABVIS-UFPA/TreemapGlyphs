package doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation;

import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.DrawBehavior;
import java.awt.Color;
import java.awt.Graphics2D;
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
public class Arrow135 implements DrawBehavior {

    private int[] xPoints;
    private int[] yPoints;
    private final Color cor;
    private Rectangle bounds;
    private Path2D path;
    private List<Polygon> triangulos;
    private List<Line2D.Float> retas;

    public Arrow135() {
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

    private void montarSetas135() {
        int[] points = new int[2];

        points[0] = getBounds().width;
        points[1] = getBounds().height;

        tornarGlyphQuadrado(points);
        montarQuadradoSobreposicao(points);

        int sliceheight = yPoints[1] / 6;
        int slice = yPoints[1] / 6;
        int scalewidth = (int) (xPoints[1] * 0.15);
        int scaleheight = (int) (yPoints[1] * 0.15);

        triangulos = new ArrayList<>();
        retas = new ArrayList<>();

        montarRetas(slice);
        montarTriangulo(sliceheight, scalewidth, scalewidth, scaleheight);

        drawSetas();
    }

    private void montarTriangulo(int sliceheight, int slicewidth, int scalewidth, int scaleheight) {
        int x = 0;
        int y = 0;
        while (x <= xPoints[0] + xPoints[1]) {
            if (x > 0 && x < 5 * slicewidth) {
                Polygon p = new Polygon();
                p.addPoint(xPoints[0] + xPoints[1] - x, yPoints[0]);
                //top
                p.addPoint((int) (xPoints[0] + xPoints[1] - x + scalewidth * 1.25), (int) (yPoints[0] + scaleheight * 0.7));
                //botton
                p.addPoint((int) (xPoints[0] + xPoints[1] - x + scalewidth * 0.6), (int) (yPoints[0] + scaleheight * 1.4));
                triangulos.add(p);
            }
            x += 2 * slicewidth;
        }
        while (y < yPoints[0] + yPoints[1]) {
            if (y < 5 * sliceheight) {
                Polygon p = new Polygon();
                p.addPoint(xPoints[0], yPoints[0] + y);
                //top
                p.addPoint((int) (xPoints[0] + scalewidth * 1.25), (int) (yPoints[0] + y + scaleheight * 0.7));
                //botton
                p.addPoint((int) (xPoints[0] + scalewidth * 0.6), (int) (yPoints[0] + y + scaleheight * 1.4));
                triangulos.add(p);
            }
            y += 2 * sliceheight;
        }
    }

    private void montarRetas(int slice) {
        int[] line1 = new int[4];
        line1[0] = xPoints[0] + xPoints[1];
        line1[1] = yPoints[0] + 2 * slice;
        line1[2] = xPoints[0] + xPoints[1] - 2 * slice;
        line1[3] = yPoints[0];

        int[] line2 = new int[4];
        line2[0] = xPoints[0] + xPoints[1];
        line2[1] = yPoints[0] + 4 * slice;
        line2[2] = xPoints[0] + xPoints[1] - 4 * slice;
        line2[3] = yPoints[0];

        int[] line3 = new int[4];
        line3[0] = xPoints[0] + xPoints[1];
        line3[1] = yPoints[0] + yPoints[1];
        line3[2] = xPoints[0];
        line3[3] = yPoints[0];

        int[] line4 = new int[4];
        line4[0] = xPoints[0] + xPoints[1] - 2 * slice;
        line4[1] = yPoints[0] + yPoints[1];
        line4[2] = xPoints[0];
        line4[3] = yPoints[0] + 2 * slice;

        int[] line5 = new int[4];
        line5[0] = xPoints[0] + xPoints[1] - 4 * slice;
        line5[1] = yPoints[0] + yPoints[1];
        line5[2] = xPoints[0];
        line5[3] = yPoints[0] + 4 * slice;

        retas.add(new Line2D.Float(line1[0], line1[1], line1[2], line1[3]));
        retas.add(new Line2D.Float(line2[0], line2[1], line2[2], line2[3]));
        retas.add(new Line2D.Float(line3[0], line3[1], line3[2], line3[3]));
        retas.add(new Line2D.Float(line4[0], line4[1], line4[2], line4[3]));
        retas.add(new Line2D.Float(line5[0], line5[1], line5[2], line5[3]));
    }

    private void drawSetas() {
        path = new Path2D.Double();
        triangulos.forEach((triangulo) -> {
            path.append(triangulo, false);
        });
        retas.forEach((reta) -> {
            path.append(reta, false);
        });
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public void setGlyphBounds(Rectangle bounds) {
        this.bounds = bounds;
        montarSetas135();
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
    public Arrow135 clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (Arrow135) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    @Override
    public String toString() {
        super.toString();
        return Arrow135.class.getSimpleName();
    }
}
