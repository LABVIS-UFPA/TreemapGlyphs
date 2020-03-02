package doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;

public class Quadrado implements DrawBehavior {

    private int[] xPoints;
    private int[] yPoints;
    private Color cor;
    private Rectangle bounds;

    public Quadrado() {
        cor = Color.decode("#f0f0f0");
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getCor());
        g2d.fillRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        g2d.setColor(Color.WHITE);
        g2d.drawRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
    }

    @Override
    public void tornarGlyphQuadrado(int[] point) {
        if (point[0] > point[1]) {
            point[0] = point[1];
        } else if (point[0] < point[1]) {
            point[1] = point[0];
        }
    }

    private void montarQuadrado() {
        int[] points = new int[2];

        Rectangle glyphBounds = getBounds();

        points[0] = glyphBounds.width;
        points[1] = glyphBounds.height;

        tornarGlyphQuadrado(points);
        montarQuadradoSobreposicao(points);

    }

    public void montarQuadradoSobreposicao(int[] points) {
        int widthSobreposicao = (int) Math.round(points[0] * percentSobreposicao);
        int heightSobreposicao = (int) Math.round(points[1] * percentSobreposicao);

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
        montarQuadrado();
    }

    @Override
    public int getArea() {
        return xPoints[1] * yPoints[1];
    }

    @Override
    public Shape getClipShape() {
        return getBounds();
    }

    @Override
    public void drawForeground(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
    }

    @Override
    public Quadrado clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (Quadrado) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    @Override
    public String toString() {
        super.toString();
        return Quadrado.class.getSimpleName();
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
