package doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation;

import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.DrawBehavior;
import java.awt.BasicStroke;
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
public class Arrow180Esq implements DrawBehavior {

    private int[] xPoints;
    private int[] yPoints;
    private final Color cor;
    private Rectangle bounds;
    private Path2D path;
    private List<Polygon> triangulos;
    private List<Line2D.Float> retas;

    public Arrow180Esq() {
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

    private int[][] montarTriangulo(int scaleheight) {
        int[][] pontosTriangulo = new int[3][2];
        //center    
        pontosTriangulo[0][0] = xPoints[0];
        pontosTriangulo[0][1] = yPoints[0] + scaleheight;
        //top
        pontosTriangulo[1][0] = xPoints[0] + scaleheight;
        pontosTriangulo[1][1] = (int) Math.round(yPoints[0] + scaleheight * 0.7);

        //botton
        pontosTriangulo[2][0] = xPoints[0] + scaleheight;
        pontosTriangulo[2][1] = (int) Math.round(yPoints[0] + scaleheight * 1.4);
        return pontosTriangulo;
    }

    @Override
    public void tornarGlyphQuadrado(int[] point) {
        if (point[0] > point[1]) {
            point[0] = point[1];
        } else if (point[0] < point[1]) {
            point[1] = point[0];
        }
    }

    private void montarSetas180() {
        int[] points = new int[2];

        points[0] = getBounds().width;
        points[1] = getBounds().height;

        tornarGlyphQuadrado(points);
        montarQuadradoSobreposicao(points);

        int slices = 6;
        int slice = yPoints[1] / slices;

        int scalewidth = (int) (xPoints[1] * 0.17);

        triangulos = new ArrayList<>();
        retas = new ArrayList<>();

        int pontosTriangulo[][] = montarTriangulo(scalewidth);

        for (int i = 0; i < slices - 1; i++) {
            Polygon p = new Polygon();
            //center
            p.addPoint(pontosTriangulo[0][0], (i * slice) + pontosTriangulo[0][1]);
            //top
            p.addPoint(pontosTriangulo[1][0], (i * slice) + pontosTriangulo[1][1]);
            //botton
            p.addPoint(pontosTriangulo[2][0], (i * slice) + pontosTriangulo[2][1]);
            triangulos.add(p);
        }
        for (int i = 1; i < slices; i++) {
            retas.add(new Line2D.Float(xPoints[0], yPoints[0] + (i * slice), xPoints[0] + xPoints[1], yPoints[0] + (i * slice)));
        }
        drawSetas();
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
        montarSetas180();
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
    public Arrow180Esq clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (Arrow180Esq) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    @Override
    public String toString() {
        super.toString();
        return Arrow180Esq.class.getSimpleName();
    }
}
