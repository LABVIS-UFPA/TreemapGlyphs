/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.numbers;

import doutorado.tese.control.business.visualizations.glyph.Glyph;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;

/**
 *
 * @author Anderson Soares
 */
public class Numeral extends Glyph {

    private String letra;
    private boolean letraAtiva = false;
    private Font fonte;
    
    private Rectangle newBounds;
    private Shape shapeNumero;
    private int widthNumero;
    private int heightNumero;
    private int x, y;
    private boolean ativo = false;

    public Numeral() {
    }

    @Override
    public void paint(Graphics2D g2d) {
        int fontSize = Math.round(newBounds.height * 1.2f);
        setFonte(new Font("Arial black", Font.PLAIN, fontSize));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(getFonte());

        g2d.setColor(new Color(255, 255, 255, 0));
        g2d.fillRect(newBounds.x, newBounds.y, newBounds.width, newBounds.height);
        g2d.setColor(Color.white);
        shapeNumero = getShapeNumero(letra);
        g2d.draw(shapeNumero);

        g2d.setColor(Color.black);
        g2d.fill(shapeNumero);

        super.paint(g2d);
    }

    /**
     * Calculo do centro das letras
     *
     * @return
     */
    private Point calcularFontMetrics(Graphics2D g2d) {
        FontMetrics metrics = g2d.getFontMetrics(getFonte());
        heightNumero = metrics.getHeight();
        widthNumero = metrics.stringWidth(getLetra());
        int pX = Math.round(getBounds().x + (getBounds().width - widthNumero) / 2);
        int pY = Math.round(getBounds().y + ((getBounds().height - heightNumero) / 2) + metrics.getAscent());
        return new Point(pX, pY);
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
        newBounds = new Rectangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
    }

    @Override
    public Object whoAmI() {
        return this.getClass();
    }
    
    @Override
    public Shape getClipShape() {
        if (isOverlappingActivated()) {
            return this.getBounds();
        } else {
            return shapeNumero;
        }
    }

    public Shape getShapeNumero(String letra) {
        BufferedImage textImage = new BufferedImage(
                getBounds().width,
                getBounds().height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = textImage.createGraphics();
        FontRenderContext frc = g2d.getFontRenderContext();
        Point centroLetra = calcularFontMetrics(g2d);
        x = centroLetra.x;
        y = centroLetra.y;
        return getFonte().createGlyphVector(frc, letra).getOutline(x, y);
    }

    /**
     * @return the fonte
     */
    public Font getFonte() {
        return fonte;
    }

    /**
     * @param fonte the fonte to set
     */
    public void setFonte(Font fonte) {
        this.fonte = fonte;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    /**
     * @return the ativo
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getArea() {
        return heightNumero * widthNumero;
    }

    @Override
    public Paint getTexturePaint() {
        return null;
    }

    @Override
    public String getVarValue() {
        return getLetra();
    }
}
