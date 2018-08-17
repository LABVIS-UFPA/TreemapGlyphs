package doutorado.tese.controle.negocio.visualizacao.glyph.strategy.variaveisvisuais.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;

public class Retangulo implements DrawBehavior {

    private int[] xPoints;
    private int[] yPoints;
    private Color cor;
    private Rectangle bounds;
    private boolean isLegenda;

    public Retangulo() {
        isLegenda = false;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Color.WHITE);
        if (isLegenda) {
            g2d.setColor(getCor());
        }else{
            g2d.setColor(Color.WHITE);
        }
        g2d.fillRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        g2d.setColor(Color.BLACK);
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

    private void montarRetangulo() {
        int[] points = new int[2];

        Rectangle rect = getBounds();

        points[0] = rect.width;
        points[1] = rect.height;

        tornarGlyphQuadrado(points);

        int width = (int) Math.round(points[0] * percentSobreposicao);
        int height = (int) Math.round(points[1] * percentSobreposicao);

        xPoints = new int[2];
        yPoints = new int[2];

        xPoints[0] = rect.x + rect.width / 2 - width / 2;
        yPoints[0] = rect.y + rect.height / 2 - height / 2;

        xPoints[1] = width;
        yPoints[1] = height;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
        montarRetangulo();
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
    public Retangulo clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (Retangulo) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    @Override
    public String toString() {
        super.toString();
        return Retangulo.class.getSimpleName();
    }

    /**
     * @return the isLegenda
     */
    public boolean isIsLegenda() {
        return isLegenda;
    }

    /**
     * @param isLegenda the isLegenda to set
     */
    public void setIsLegenda(boolean isLegenda) {
        this.isLegenda = isLegenda;
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
