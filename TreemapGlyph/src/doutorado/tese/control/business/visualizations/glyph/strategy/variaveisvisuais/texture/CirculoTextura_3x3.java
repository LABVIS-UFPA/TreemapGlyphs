package doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture;

import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.DrawBehavior;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

/**
 * O objeto Circulo com cor ocupa 65% do item do treemap
 *
 * @author Anderson Soares
 */
public class CirculoTextura_3x3 implements DrawBehavior {

    private int[] xPoints;
    private int[] yPoints;
    private final Color corPreenchimento;
    private final Color corBorda;
    private final Color corFundo;
    private Rectangle bounds;
    private Path2D p;

    public CirculoTextura_3x3() {
        corPreenchimento = Color.BLACK;
        corBorda = Color.WHITE;
        corFundo = Color.WHITE;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(corFundo);
        g2d.fillRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        
        //desenha o Path2D no drawForeground()
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

        int textureWidth = xPoints[1];

        int slices = 3;
        double porcentSlice = (100 / slices) / 100d;
        int diametroCirculo = (int) Math.round(textureWidth * porcentSlice);
        diametroCirculo = (int) (diametroCirculo - Math.round(diametroCirculo * 0.01));
        calcularCirculos(diametroCirculo, slices);
    }

    public void calcularCirculos(int diametro, int slices) {
        p = new Path2D.Double();
        for (int coluna = 0; coluna < slices; coluna++) {
            for (int linha = 0; linha < slices; linha++) {
                Ellipse2D.Double circulo = new Ellipse2D.Double(
                        xPoints[0] + (linha * diametro),
                        yPoints[0] + (coluna * diametro),
                        diametro,
                        diametro);
                p.append(circulo, false);
            }
        }
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
//        g2d.setStroke(new BasicStroke(2.5f));
        g2d.setColor(corBorda);
        g2d.draw(p);
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(corPreenchimento);
        g2d.fill(p);
    }

    @Override
    public CirculoTextura_3x3 clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (CirculoTextura_3x3) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    @Override
    public String toString() {
        super.toString();
        return CirculoTextura_3x3.class.getSimpleName();
    }
}
