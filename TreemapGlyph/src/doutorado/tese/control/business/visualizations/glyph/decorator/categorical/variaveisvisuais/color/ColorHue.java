/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.color;

import doutorado.tese.control.business.visualizations.glyph.Glyph;
import doutorado.tese.util.Constantes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;

/**
 *
 * @author Anderson Soares
 */
public class ColorHue extends Glyph {

    private Color cor;

    @Override
    public void paint(Graphics2D g2d) {
        if (isVisible()) {
            if (isOverlappingActivated()) {
                g2d.setColor(getCor());
            } else {
                if (g2d.getPaint() instanceof TexturePaint) {
                    TexturePaint paint = (TexturePaint) g2d.getPaint();
                    for (int i = 0; i < paint.getImage().getWidth(); i++) {
                        for (int j = 0; j < paint.getImage().getHeight(); j++) {
                            if (paint.getImage().getRGB(i, j) == Color.GRAY.getRGB()) {
                                paint.getImage().setRGB(i, j, getCor().getRGB());
                            }
                        }
                    }
                } else {
                    g2d.setColor(getCor());
                }
            }
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.fillRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        }
        super.paint(g2d);
    }

    @Override
    public void setBounds(Rectangle rect) {
        super.setBounds(rect);
        montarRetangulo();
    }

    private void montarRetangulo() {
        int[] points = new int[2];

        points[0] = getBounds().width;
        points[1] = getBounds().height;

        transformarRetanguloEmQuadrado(points);

        int width = Math.round(points[0] * getPectSobreposicao());
        int height = Math.round(points[1] * getPectSobreposicao());

        xPoints = new int[2];
        yPoints = new int[2];

        xPoints[0] = getBounds().x + getBounds().width / 2 - width / 2;
        yPoints[0] = getBounds().y + getBounds().height / 2 - height / 2;

        xPoints[1] = width;
        yPoints[1] = height;
    }

    @Override
    public Object whoAmI() {
        return this.getClass();
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

    @Override
    public Shape getClipShape() {
        return getBounds();
    }

    @Override
    public Paint getTexturePaint() {
        return null;
    }

    @Override
    public String getVarValue() {
//        return "R:" + getCor().getRed() + " G:" + getCor().getGreen() + " B:" + getCor().getBlue();
        return getCor()+"";
    }

    @Override
    public int presenca() {
        return Constantes.PRESENCA_COR;
    }

}
