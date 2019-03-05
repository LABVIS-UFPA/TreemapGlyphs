/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.negocio.visualizacao.glyph.decorator.categorical.variaveisvisuais.texture;

import doutorado.tese.controle.negocio.visualizacao.glyph.factorys.variaveisvisuais.TMPatternFactory;
import doutorado.tese.controle.negocio.visualizacao.glyph.Glyph;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 *
 * @author Anderson Soares
 */
public class Textura extends Glyph {

    private String nomeTextura;
    TMPatternFactory textura;
    private Color cor;
    private Color backgroundColor;
    BufferedImage clone;

    public Textura(Color cor, Color backgroundColor) {
        textura = TMPatternFactory.getInstance(cor, backgroundColor);
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(textura.get(getNomeTextura()));
        g2d.fillRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        super.paint(g2d);
    }

    @Override
    public Object whoAmI() {
        return this.getClass();
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

    /**
     * @return the nomeTextura
     */
    public String getNomeTextura() {
        return nomeTextura;
    }

    /**
     * @param nomeTextura the nomeTextura to set
     */
    public void setNomeTextura(String nomeTextura) {
        this.nomeTextura = nomeTextura;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
//        textura.setTextureColor(this.cor);
//        textura.resetTextures();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public Shape getClipShape() {
        return getBounds();
    }

    @Override
    public Paint getTexturePaint() {
        Rectangle r = new Rectangle(0, 0, 16, 16);
        BufferedImage original = ((TexturePaint) textura.get(getNomeTextura())).getImage();
        if (clone == null) {
            clone = deepCopy(original);
        }
        Paint pattern = new TexturePaint(clone, r);

        return pattern;
    }

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public static BufferedImage copyImage(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics2D g = (Graphics2D) b.createGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    @Override
    public String getVarValue() {
        return getNomeTextura();
    }
}
